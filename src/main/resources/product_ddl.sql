CREATE TABLE `product`
(
    `product_no`   bigint         NOT NULL AUTO_INCREMENT COMMENT '상품 구별하는 PK',
    `product_name` varchar(100)   NOT NULL,
    `seller_no`    bigint         NOT NULL,
    `created_at`   datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시간',
    `updated_at`   datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정시간',
    `deleted_at`   datetime                DEFAULT NULL COMMENT '삭제시간',
    `category`     int            NOT NULL COMMENT '상품 카테고리',
    `tax_type`     char(4)        NOT NULL DEFAULT 'TAX',
    `sell_price`   decimal(14, 5) NOT NULL DEFAULT '0.0',
    `supply_price` decimal(14, 5) NOT NULL DEFAULT '0.0' COMMENT '삭제시간',
    `is_active`    tinyint(1)              DEFAULT '1',

    primary key (`product_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='상품 정보 관리 테이블';
