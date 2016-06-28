SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE  TABLE IF NOT EXISTS `company` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `gmt_created` DATETIME NULL DEFAULT NULL ,
  `gmt_modified` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '公司信息';

CREATE  TABLE IF NOT EXISTS `shop` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT '' COMMENT '店铺名称' ,
  `access_token` VARCHAR(100) NULL DEFAULT NULL COMMENT 'taobao session key' ,
  `token_type` VARCHAR(45) NULL DEFAULT 'Bearer' COMMENT '值：Bearer' ,
  `expires_in` INT(11) NULL DEFAULT NULL ,
  `refresh_token` VARCHAR(100) NULL DEFAULT '' ,
  `re_expires_in` INT(11) NULL DEFAULT NULL ,
  `r1_expires_in` INT(11) NULL DEFAULT NULL ,
  `r2_expires_in` INT(11) NULL DEFAULT NULL ,
  `w1_expires_in` INT(11) NULL DEFAULT NULL ,
  `w2_expires_in` INT(11) NULL DEFAULT NULL ,
  `taobao_user_nick` VARCHAR(45) NOT NULL COMMENT '昵称' ,
  `taobao_user_id` VARCHAR(45) NOT NULL COMMENT '账号ID' ,
  `sub_taobao_user_id` VARCHAR(45) NULL DEFAULT NULL COMMENT '子账号ID' ,
  `sub_taobao_user_nick` VARCHAR(45) NULL DEFAULT NULL COMMENT '子账号昵称' ,
  `gmt_last_login` DATETIME NOT NULL COMMENT '最后授权时间' ,
  `gmt_created` DATETIME NOT NULL ,
  `gmt_modified` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_name` (`name` ASC) ,
  INDEX `idx_taobao_user_nick` (`taobao_user_nick` ASC) ,
  INDEX `idx_taobao_user_id` (`taobao_user_id` ASC) ,
  INDEX `idx_sub_taobao_user_nick` (`sub_taobao_user_nick` ASC) ,
  INDEX `idx_sub_taobao_user_id` (`sub_taobao_user_id` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '店铺信息';

CREATE  TABLE IF NOT EXISTS `tb_trade` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `tb_order` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `sys_task` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
