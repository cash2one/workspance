SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE  TABLE IF NOT EXISTS `asto_ec_web`.`tb_shop_access` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `sid` INT(11) NOT NULL DEFAULT 0 ,
  `access_token` VARCHAR(100) NULL DEFAULT '' COMMENT 'taobao session key' ,
  `token_type` VARCHAR(45) NULL DEFAULT 'Bearer' COMMENT '值：Bearer' ,
  `expires_in` INT(11) NULL DEFAULT NULL ,
  `refresh_token` VARCHAR(100) NULL DEFAULT '' ,
  `re_expires_in` INT(11) NULL DEFAULT NULL ,
  `r1_expires_in` INT(11) NULL DEFAULT NULL ,
  `r2_expires_in` INT(11) NULL DEFAULT NULL ,
  `w1_expires_in` INT(11) NULL DEFAULT NULL ,
  `w2_expires_in` INT(11) NULL DEFAULT NULL ,
  `taobao_user_nick` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '昵称' ,
  `taobao_user_id` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '账号ID' ,
  `sub_taobao_user_id` VARCHAR(45) NULL DEFAULT '' COMMENT '子账号ID' ,
  `sub_taobao_user_nick` VARCHAR(45) NULL DEFAULT '' COMMENT '子账号昵称' ,
  `gmt_last_login` DATETIME NOT NULL COMMENT '最后授权时间' ,
  `gmt_created` DATETIME NOT NULL ,
  `gmt_modified` DATETIME NOT NULL ,
  `company_id` INT(20) NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_taobao_user_nick` (`taobao_user_nick` ASC) ,
  INDEX `idx_taobao_user_id` (`taobao_user_id` ASC) ,
  INDEX `idx_sub_taobao_user_nick` (`sub_taobao_user_nick` ASC) ,
  INDEX `idx_sub_taobao_user_id` (`sub_taobao_user_id` ASC) ,
  INDEX `idx_sid` (`sid` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '店铺信息';

ALTER TABLE `asto_ec_web`.`sys_task` ENGINE = InnoDB , CHANGE COLUMN `sid` `sid` INT(11) NOT NULL DEFAULT 0  , CHANGE COLUMN `gmt_submit` `gmt_submit` DATETIME NOT NULL COMMENT '任务提交时间'  , CHANGE COLUMN `status` `status` INT(11) NULL DEFAULT 0 COMMENT '任务状态\n0：创建（默认）\n1：提交\n2：new\n3：doing\n4：done\n5：completed'  , CHANGE COLUMN `tb_error` `tb_error` VARCHAR(45) NULL DEFAULT '' COMMENT '淘宝返回的错误信息'  , CHANGE COLUMN `tb_error_discription` `tb_error_discription` VARCHAR(254) NULL DEFAULT '' COMMENT '淘宝返回的错误描述'  , CHANGE COLUMN `check_code` `check_code` VARCHAR(45) NULL DEFAULT '' COMMENT '下载文件的MD5校验码，通过此校验码可以检查下载的文件是否是完整的。'  , CHANGE COLUMN `download_url` `download_url` VARCHAR(45) NULL DEFAULT '' COMMENT '大任务结果下载地址。当创建的认任务是大数据量的任务时，获取结果会返回此字段，同时subtasks列表会为空。 通过这个地址可以下载到结果的结构体，格式同普通任务下载的一样。 每次获取到的地址只能下载一次。下载的文件加上后缀名.zip打开。'  , CHANGE COLUMN `method` `method` VARCHAR(45) NULL DEFAULT '' COMMENT '此任务是由哪个api产生的'  
, ADD INDEX `idx_gmt_submit` (`gmt_submit` ASC) ;

ALTER TABLE `asto_ec_web`.`tb_shop` CHANGE COLUMN `company_id` `company_id` INT(20) NULL DEFAULT 0  , CHANGE COLUMN `sid` `sid` INT(11) NULL DEFAULT 0 COMMENT '店铺编号。shop+sid.taobao.com即店铺地址，如shop123456.taobao.com'  , CHANGE COLUMN `cid` `cid` INT(11) NULL DEFAULT 0 COMMENT '店铺所属的类目编号'  , CHANGE COLUMN `nick` `nick` VARCHAR(45) NULL DEFAULT '' COMMENT '卖家昵称'  , CHANGE COLUMN `title` `title` VARCHAR(45) NULL DEFAULT '' COMMENT '店铺标题'  , CHANGE COLUMN `desc` `desc` TEXT NULL DEFAULT '' COMMENT '店铺描述'  , CHANGE COLUMN `bulletin` `bulletin` VARCHAR(254) NULL DEFAULT '' COMMENT '店铺公告'  , CHANGE COLUMN `pic_path` `pic_path` VARCHAR(254) NULL DEFAULT '' COMMENT '店标地址。返回相对路径，可以用\"http://logo.taobao.com/shop-logo\"来拼接成绝对路径'  ;

DROP TABLE IF EXISTS `asto_ec_web`.`tb_shop_access` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
