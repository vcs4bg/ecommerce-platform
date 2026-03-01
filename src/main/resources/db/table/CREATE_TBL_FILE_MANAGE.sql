CREATE TABLE TBL_FILE_MANAGE (
	file_id             BIGSERIAL       PRIMARY KEY,
	original_filename   VARCHAR(255)    NOT NULL,
	file_path           VARCHAR(500)    NOT NULL UNIQUE,
	file_size           BIGINT          NOT NULL,
	file_extension      VARCHAR(10),
	created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
	deleted_at          TIMESTAMP
);

COMMENT ON TABLE TBL_FILE_MANAGE IS 'ファイル管理テーブル';
COMMENT ON COLUMN TBL_FILE_MANAGE.file_id IS 'ファイルID';
COMMENT ON COLUMN TBL_FILE_MANAGE.original_filename IS '元ファイル名';
COMMENT ON COLUMN TBL_FILE_MANAGE.file_path IS 'ファイルパス';
COMMENT ON COLUMN TBL_FILE_MANAGE.file_size IS 'ファイルサイズ';
COMMENT ON COLUMN TBL_FILE_MANAGE.file_extension IS 'ファイル拡張子';
COMMENT ON COLUMN TBL_FILE_MANAGE.created_at IS '作成日時';
COMMENT ON COLUMN TBL_FILE_MANAGE.updated_at IS '更新日時';
COMMENT ON COLUMN TBL_FILE_MANAGE.deleted_at IS '削除日時';
