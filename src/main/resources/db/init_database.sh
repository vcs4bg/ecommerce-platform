#!/bin/bash
export PGCLIENTENCODING=UTF8

# PostgreSQL接続情報（スーパーユーザー）
DB_USER="postgres"
DB_PASSWORD="postgres"
DB_NAME="ecommerce_db"
DB_HOST="localhost"
DB_PORT="5432"

# 管理者ロール（DDL実行可能、読み書き可能）
ADMIN_USER="ecommerce_admin"
ADMIN_PASSWORD="admin_password"

# アプリケーションロール（読み書きのみ）
APP_USER="ecommerce_app"
APP_PASSWORD="app_password"

# スキーマ名
SCHEMA_NAME="ecommerce"

# スクリプトのディレクトリを取得
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "=========================================="
echo "eCommerce Platform DB初期化"
echo "=========================================="

# ========== 必須: PostgreSQL接続確認 ==========
echo ""
echo "[必須] PostgreSQL接続確認中..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c '\q' 2>/dev/null
if [ $? -ne 0 ]; then
    echo "エラー: PostgreSQLに接続できません"
    echo "ホスト: $DB_HOST, ポート: $DB_PORT, ユーザー: $DB_USER"
    exit 1
fi
echo "✓ 接続成功"

# ========== 必須: 既存データベース削除 ==========
echo ""
echo "[必須] 既存データベース削除中..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "DROP DATABASE IF EXISTS $DB_NAME;" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ データベース削除完了"
else
    echo "警告: データベース削除に失敗（存在しない可能性があります）"
fi

# スキーマ削除は不要（DBをDROP/CREATEしているため）
# もしスキーマのみを再初期化したい場合は "schema-only" モードを追加してください

# ========== 必須: 既存ロール削除 ==========
echo ""
echo "[必須] 既存ロール削除中..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres <<EOF 2>/dev/null
DROP ROLE IF EXISTS $ADMIN_USER;
DROP ROLE IF EXISTS $APP_USER;
EOF
echo "✓ ロール削除完了"

# ========== 必須: ロール作成 ==========
echo ""
echo "[必須] ロール作成中..."
# 管理者ロール（DDL実行可能）
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "CREATE ROLE $ADMIN_USER WITH LOGIN PASSWORD '$ADMIN_PASSWORD';"
if [ $? -ne 0 ]; then
    echo "エラー: 管理者ロール作成に失敗しました"
    exit 1
fi
echo "✓ 管理者ロール作成完了: $ADMIN_USER"

# アプリケーションロール（読み書きのみ）
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "CREATE ROLE $APP_USER WITH LOGIN PASSWORD '$APP_PASSWORD';"
if [ $? -ne 0 ]; then
    echo "エラー: アプリケーションロール作成に失敗しました"
    exit 1
fi
echo "✓ アプリケーションロール作成完了: $APP_USER"

# ========== 必須: データベース作成 ==========
echo ""
echo "[必須] データベース作成中..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "CREATE DATABASE $DB_NAME ENCODING 'UTF8' OWNER $ADMIN_USER;"
if [ $? -ne 0 ]; then
    echo "エラー: データベース作成に失敗しました"
    exit 1
fi
echo "✓ データベース作成完了: $DB_NAME"

# ========== 必須: スキーマ作成 ==========
echo ""
echo "[必須] スキーマ作成中..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "CREATE SCHEMA $SCHEMA_NAME AUTHORIZATION $ADMIN_USER;"
if [ $? -ne 0 ]; then
    echo "エラー: スキーマ作成に失敗しました"
    exit 1
fi
echo "✓ スキーマ作成完了: $SCHEMA_NAME"

# ========== 必須: 権限設定 ==========
echo ""
echo "[必須] 権限設定中..."
# アプリユーザーにスキーマ使用権限を付与
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME <<EOF
GRANT USAGE ON SCHEMA $SCHEMA_NAME TO $APP_USER;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA $SCHEMA_NAME TO $APP_USER;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA $SCHEMA_NAME TO $APP_USER;
-- DEFAULT PRIVILEGES は、$ADMIN_USER が作成するオブジェクトに対して適用されるよう明示的に FOR ROLE を指定
ALTER DEFAULT PRIVILEGES FOR ROLE $ADMIN_USER IN SCHEMA $SCHEMA_NAME GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO $APP_USER;
ALTER DEFAULT PRIVILEGES FOR ROLE $ADMIN_USER IN SCHEMA $SCHEMA_NAME GRANT USAGE, SELECT ON SEQUENCES TO $APP_USER;
EOF
if [ $? -ne 0 ]; then
    echo "エラー: 権限設定に失敗しました"
    exit 1
fi
echo "✓ 権限設定完了"

# ========== 必須: テーブル作成 ==========
echo ""
echo "[必須] テーブル作成中..."
TABLE_DIR="$SCRIPT_DIR/table"

if [ ! -d "$TABLE_DIR" ]; then
    echo "エラー: tableフォルダが見つかりません: $TABLE_DIR"
    exit 1
fi

sql_files=("$TABLE_DIR"/*.sql)
if [ ! -f "${sql_files[0]}" ]; then
    echo "警告: tableフォルダ内にSQLファイルがありません"
else
    for sql_file in "$TABLE_DIR"/*.sql; do
        echo "  実行中: $(basename "$sql_file")"
        PGPASSWORD=$ADMIN_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $ADMIN_USER -d $DB_NAME \
            -v ON_ERROR_STOP=1 \
            -c "SET search_path TO $SCHEMA_NAME;" \
            -f "$sql_file"
        if [ $? -ne 0 ]; then
            echo "エラー: $(basename "$sql_file") の実行に失敗しました"
            exit 1
        fi
    done
    echo "✓ テーブル作成完了"
fi

# ========== 任意: インデックス作成 ==========
echo ""
echo "[任意] インデックス作成中..."
INDEX_DIR="$SCRIPT_DIR/index"

if [ ! -d "$INDEX_DIR" ]; then
    echo "  スキップ: indexフォルダが見つかりません"
else
    sql_files=("$INDEX_DIR"/*.sql)
    if [ ! -f "${sql_files[0]}" ]; then
        echo "  スキップ: indexフォルダ内にSQLファイルがありません"
    else
        for sql_file in "$INDEX_DIR"/*.sql; do
            echo "  実行中: $(basename "$sql_file")"
            PGPASSWORD=$ADMIN_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $ADMIN_USER -d $DB_NAME \
                -c "SET search_path TO $SCHEMA_NAME;" \
                -f "$sql_file"
            if [ $? -ne 0 ]; then
                echo "  警告: $(basename "$sql_file") の実行に失敗しました（続行します）"
            fi
        done
        echo "✓ インデックス作成完了"
    fi
fi

# ========== 任意: ビュー作成 ==========
echo ""
echo "[任意] ビュー作成中..."
VIEW_DIR="$SCRIPT_DIR/view"

if [ ! -d "$VIEW_DIR" ]; then
    echo "  スキップ: viewフォルダが見つかりません"
else
    sql_files=("$VIEW_DIR"/*.sql)
    if [ ! -f "${sql_files[0]}" ]; then
        echo "  スキップ: viewフォルダ内にSQLファイルがありません"
    else
        for sql_file in "$VIEW_DIR"/*.sql; do
            echo "  実行中: $(basename "$sql_file")"
            PGPASSWORD=$ADMIN_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $ADMIN_USER -d $DB_NAME \
                -c "SET search_path TO $SCHEMA_NAME;" \
                -f "$sql_file"
            if [ $? -ne 0 ]; then
                echo "  警告: $(basename "$sql_file") の実行に失敗しました（続行します）"
            fi
        done
        echo "✓ ビュー作成完了"
    fi
fi

echo ""
echo "=========================================="
echo "✓ データベース初期化完了"
echo "=========================================="
echo "データベース名: $DB_NAME"
echo "スキーマ名: $SCHEMA_NAME"
echo ""
echo "【管理者接続情報】（DDL実行可能）"
echo "  ユーザー: $ADMIN_USER"
echo "  パスワード: $ADMIN_PASSWORD"
echo "  接続文字列: jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME?currentSchema=$SCHEMA_NAME"
echo ""
echo "【アプリケーション接続情報】（読み書きのみ）"
echo "  ユーザー: $APP_USER"
echo "  パスワード: $APP_PASSWORD"
echo "  接続文字列: jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME?currentSchema=$SCHEMA_NAME"
