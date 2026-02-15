CREATE TABLE MST_PRODUCT (
	product_id      BIGSERIAL       PRIMARY KEY,
	product_name            VARCHAR(100)    NOT NULL,
	description     TEXT,
	price           DECIMAL(10,2)   NOT NULL,
	image_url       VARCHAR(255),
	stock           INT             NOT NULL DEFAULT 0,
	category_id     BIGINT,
	product_status  INT             NOT NULL DEFAULT 1, -- 0:非表示, 1:表示, 2:販売終了
	created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);
