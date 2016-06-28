CREATE  TABLE IF NOT EXISTS `ad_booking` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `position_id` INT(20) NOT NULL DEFAULT 0 COMMENT '广告位ID' ,
  `crm_id` INT(20) NOT NULL DEFAULT 0 COMMENT '本地crm id' ,
  `account` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '提交人账户' ,
  `keywords` VARCHAR(45) NULL DEFAULT '' COMMENT '预订关键字' ,
  `email` VARCHAR(45) NULL DEFAULT '' COMMENT '客户Email' ,
  `gmt_booking` DATETIME NOT NULL DEFAULT 0 COMMENT '预订时间' ,
  `remark` VARCHAR(1000) NULL COMMENT '备注' ,
  `gmt_created` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_modified` DATETIME NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_position_id` (`position_id` ASC) ,
  INDEX `idx_crm_id` (`crm_id` ASC) ,
  INDEX `idx_account` (`account` ASC) ,
  INDEX `idx_keywords` (`keywords` ASC) ,
  INDEX `idx_email` (`email` ASC) ,
  INDEX `idx_gmt_booking` (`gmt_booking` ASC) )
ENGINE = MyISAM
COMMENT = '广告预订信息'

/**
* update index
*/
ALTER TABLE `ad_exact_type` 
 ADD INDEX `idx_ad_position_id`(`ad_position_id`),
 ADD INDEX `idx_exact_type_id`(`exact_type_id`),
 ADD INDEX `idx_ad_id`(`ad_id`),
 ADD INDEX `idx_anchor_point`(`anchor_point`);

ALTER TABLE `ad`
 ADD INDEX `idx_ad_title`(`ad_title`),
 ADD INDEX `idx_online_status`(`online_status`),
 ADD INDEX `idx_review_status`(`review_status`),
 ADD INDEX `idx_gmt_plan_end`(`gmt_plan_end`);

ALTER TABLE `advertiser`
 ADD INDEX `idx_name`(`name`),
 ADD INDEX `idx_contact`(`contact`),
 ADD INDEX `idx_category`(`category`),
 ADD INDEX `idx_deleted`(`deleted`);

ALTER TABLE `ad` 
 MODIFY COLUMN `gmt_start` DATETIME  NOT NULL COMMENT '广告生效时间',
 MODIFY COLUMN `gmt_plan_end` DATETIME  NOT NULL DEFAULT 0 COMMENT '计划下线时间
可以不设置';

