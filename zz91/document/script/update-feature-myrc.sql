ALTER TABLE `products_series` MODIFY COLUMN `company_id` INT(20)  NOT NULL COMMENT '公司ID',
 MODIFY COLUMN `account` VARCHAR(50)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号名',
 MODIFY COLUMN `series_order` INT(4)  NOT NULL COMMENT '系列排序',
 ADD INDEX `idx_company_id`(`company_id`),
 ADD INDEX `idx_account`(`account`),
 ADD INDEX `idx_series_order`(`series_order`);
