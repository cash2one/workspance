SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER TABLE `shop` DROP COLUMN `name` , ADD COLUMN `sid` INT(11) NOT NULL DEFAULT 0  AFTER `id` 
, ADD INDEX `idx_sid` (`sid` ASC) 
, DROP INDEX `idx_name` , RENAME TO  `tb_shop_access` ;

ALTER TABLE `sys_task` ADD COLUMN `sid` INT(11) NOT NULL  AFTER `id` , ADD COLUMN `access_token` VARCHAR(45) NOT NULL DEFAULT ''  AFTER `sid` , ADD COLUMN `task_id` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'TOP返回的TASK ID'  AFTER `access_token` , ADD COLUMN `gmt_read_start` DATETIME NULL DEFAULT NULL COMMENT '读取数据开始时间'  AFTER `task_id` , ADD COLUMN `gmt_read_end` DATETIME NULL DEFAULT NULL COMMENT '读取数据结束时间'  AFTER `gmt_read_start` , ADD COLUMN `read_fields` VARCHAR(45) NULL DEFAULT '' COMMENT '读取字段'  AFTER `gmt_read_end` , ADD COLUMN `gmt_submit` DATETIME NULL DEFAULT NULL COMMENT '任务提交时间'  AFTER `read_fields` , ADD COLUMN `status` INT(11) NULL DEFAULT NULL COMMENT '任务状态\n0：创建（默认）\n1：提交\n2：new\n3：doing\n4：done\n5：completed'  AFTER `gmt_submit` , ADD COLUMN `tb_error` VARCHAR(45) NULL DEFAULT NULL COMMENT '淘宝返回的错误信息'  AFTER `status` , ADD COLUMN `tb_error_discription` VARCHAR(254) NULL DEFAULT NULL COMMENT '淘宝返回的错误描述'  AFTER `tb_error` , ADD COLUMN `check_code` VARCHAR(45) NULL DEFAULT NULL COMMENT '下载文件的MD5校验码，通过此校验码可以检查下载的文件是否是完整的。'  AFTER `tb_error_discription` , ADD COLUMN `download_url` VARCHAR(45) NULL DEFAULT NULL COMMENT '大任务结果下载地址。当创建的认任务是大数据量的任务时，获取结果会返回此字段，同时subtasks列表会为空。 通过这个地址可以下载到结果的结构体，格式同普通任务下载的一样。 每次获取到的地址只能下载一次。下载的文件加上后缀名.zip打开。'  AFTER `check_code` , ADD COLUMN `method` VARCHAR(45) NULL DEFAULT NULL COMMENT '此任务是由哪个api产生的'  AFTER `download_url` , ADD COLUMN `gmt_modified` VARCHAR(45) NOT NULL  AFTER `method` , ADD COLUMN `gmt_created` VARCHAR(45) NOT NULL  AFTER `gmt_modified` 
, ADD INDEX `idx_sid` () 
, ADD INDEX `idx_task_id` () 
, ADD INDEX `idx_status` () 
, ADD INDEX `idx_gmt_read_start` () 
, ADD INDEX `idx_gmt_read_end` () 
, ADD INDEX `idx_tb_error` () ;

CREATE  TABLE IF NOT EXISTS `tb_shop` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `company_id` INT(20) NULL DEFAULT NULL ,
  `sid` INT(11) NULL DEFAULT NULL COMMENT '店铺编号。shop+sid.taobao.com即店铺地址，如shop123456.taobao.com' ,
  `cid` INT(11) NULL DEFAULT NULL COMMENT '店铺所属的类目编号' ,
  `nick` VARCHAR(45) NULL DEFAULT NULL COMMENT '卖家昵称' ,
  `title` VARCHAR(45) NULL DEFAULT NULL COMMENT '店铺标题' ,
  `desc` TEXT NULL DEFAULT NULL COMMENT '店铺描述' ,
  `bulletin` VARCHAR(254) NULL DEFAULT NULL COMMENT '店铺公告' ,
  `pic_path` VARCHAR(254) NULL DEFAULT NULL COMMENT '店标地址。返回相对路径，可以用\"http://logo.taobao.com/shop-logo\"来拼接成绝对路径' ,
  `created` DATETIME NULL DEFAULT NULL COMMENT '开店时间。格式：yyyy-MM-dd HH:mm:ss' ,
  `modified` DATETIME NULL DEFAULT NULL COMMENT '最后修改时间。格式：yyyy-MM-dd HH:mm:ss' ,
  `gmt_created` DATETIME NOT NULL ,
  `gmt_modified` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_company_id` () ,
  INDEX `idx_sid` () ,
  INDEX `idx_cid` () ,
  INDEX `idx_nick` () ,
  INDEX `idx_created` () ,
  INDEX `idx_modifed` () )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '淘宝店铺信息';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
