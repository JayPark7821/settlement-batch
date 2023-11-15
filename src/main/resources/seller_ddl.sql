CREATE TABLE `seller`
(
    `seller_no`               bigint      NOT NULL AUTO_INCREMENT COMMENT '셀러를 구별하는 PK',
    `seller_name`             varchar(50) NOT NULL COMMENT '셀러명',
    `business_no`             int         NOT NULL COMMENT '셀러의 사업자 번호',
    `sell_type`               char(1)     NOT NULL DEFAULT 'C' COMMENT '판매 유형( P: 매입, C: 위탁)',
    `created_at`              datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시간',
    `updated_at`              datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정시간',
    `deleted_at`              datetime             DEFAULT NULL COMMENT '삭제시간',
    `bank_type`               varchar(10)          DEFAULT NULL COMMENT '은행타입',
    `account_no`              int                  DEFAULT NULL COMMENT '계좌번호',
    `account_owner_name`      varchar(50)          DEFAULT NULL COMMENT '계좌주',
    `is_active`               tinyint(1)  NOT NULL DEFAULT '1' COMMENT '셀러 활성화 여부',
    `default_delivery_amount` int         NOT NULL DEFAULT '3000' COMMENT '기본 배송 금액',
    `commission`              int         NOT NULL DEFAULT '5' COMMENT '수수료',
    primary key (`seller_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='셀러 정보 관리 테이블';
