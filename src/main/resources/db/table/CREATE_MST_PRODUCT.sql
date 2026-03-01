CREATE TABLE MST_PRODUCT (
	product_id      BIGSERIAL       PRIMARY KEY,
	product_name            VARCHAR(100)    NOT NULL,
	description     TEXT,
	price           DECIMAL(10,2)   NOT NULL,
	stock           INT             NOT NULL DEFAULT 0,
	category_id     BIGINT,
	product_status  INT             NOT NULL DEFAULT 1, -- 0:非表示, 1:表示, 2:販売終了
	created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE MST_PRODUCT IS '商品マスタテーブル';
COMMENT ON COLUMN MST_PRODUCT.product_id IS '商品ID';
COMMENT ON COLUMN MST_PRODUCT.product_name IS '商品名';
COMMENT ON COLUMN MST_PRODUCT.description IS '商品説明';
COMMENT ON COLUMN MST_PRODUCT.price IS '価格';
COMMENT ON COLUMN MST_PRODUCT.stock IS '在庫数';
COMMENT ON COLUMN MST_PRODUCT.category_id IS 'カテゴリID';
COMMENT ON COLUMN MST_PRODUCT.product_status IS '商品ステータス';
COMMENT ON COLUMN MST_PRODUCT.created_at IS '作成日時';
COMMENT ON COLUMN MST_PRODUCT.updated_at IS '更新日時';
