CREATE TABLE TBL_PRODUCT_FILE (
	product_id       BIGINT          NOT NULL,
	file_id          BIGINT          NOT NULL,
	display_order    INT             NOT NULL DEFAULT 1,
	created_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_tbl_product_file
		PRIMARY KEY (product_id, file_id),
	CONSTRAINT fk_tbl_product_file_product
		FOREIGN KEY (product_id) REFERENCES MST_PRODUCT(product_id),
	CONSTRAINT fk_tbl_product_file_file
		FOREIGN KEY (file_id) REFERENCES TBL_FILE_MANAGE(file_id)
);

CREATE INDEX idx_tbl_product_file_file_id
	ON TBL_PRODUCT_FILE(file_id);

COMMENT ON TABLE TBL_PRODUCT_FILE IS '商品ファイル関連テーブル';
COMMENT ON COLUMN TBL_PRODUCT_FILE.product_id IS '商品ID';
COMMENT ON COLUMN TBL_PRODUCT_FILE.file_id IS 'ファイルID';
COMMENT ON COLUMN TBL_PRODUCT_FILE.display_order IS '表示順';
COMMENT ON COLUMN TBL_PRODUCT_FILE.created_at IS '作成日時';
COMMENT ON COLUMN TBL_PRODUCT_FILE.updated_at IS '更新日時';
