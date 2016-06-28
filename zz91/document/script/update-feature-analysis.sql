CREATE  TABLE IF NOT EXISTS `analysis_product` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `category_code` VARCHAR(45) NOT NULL DEFAULT '' ,
  `num` INT NOT NULL DEFAULT 0 ,
  `gmt_target` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_created` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_modified` DATETIME NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_category_code` (`category_code` ASC) ,
  INDEX `idx_num` (`num` ASC) ,
  INDEX `idx_gmt_target` (`gmt_target` ASC) )
ENGINE = MyISAM;

CREATE  TABLE IF NOT EXISTS `analysis_comp_price` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `category_code` VARCHAR(45) NOT NULL DEFAULT '' ,
  `num` INT NOT NULL DEFAULT 0 ,
  `gmt_target` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_created` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_modified` DATETIME NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_category_code` (`category_code` ASC) ,
  INDEX `idx_num` (`num` ASC) ,
  INDEX `idx_gmt_target` (`gmt_target` ASC) )
ENGINE = MyISAM;

CREATE  TABLE IF NOT EXISTS `analysis_register` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `regfrom_code` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '注册来源' ,
  `num` INT NOT NULL DEFAULT 0 COMMENT '数量' ,
  `gmt_target` DATETIME NOT NULL DEFAULT 0 COMMENT '统计日' ,
  `gmt_created` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_modified` DATETIME NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_regfrom_code` (`regfrom_code` ASC) ,
  INDEX `idx_num` (`num` ASC) ,
  INDEX `idx_gmt_target` (`gmt_target` ASC) )
ENGINE = MyISAM;

CREATE  TABLE IF NOT EXISTS `analysis_inquiry` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `inquiry_type` VARCHAR(45) NOT NULL DEFAULT '' ,
  `inquiry_target` INT(20) NOT NULL DEFAULT 0 ,
  `num` INT NOT NULL DEFAULT 0 ,
  `gmt_target` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_created` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_modified` DATETIME NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_inquiry_type` (`inquiry_type` ASC) ,
  INDEX `idx_num` (`num` ASC) ,
  INDEX `idx_target` (`gmt_target` ASC) )
ENGINE = MyISAM;



ALTER TABLE `analysis_product` ADD COLUMN `type_code` varchar(45)  NOT NULL AFTER `id`,
 ADD INDEX `idx_type_code`(`type_code`);


CREATE  TABLE IF NOT EXISTS `analysis_trade_keywords` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `kw` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '搜索关键字' ,
  `num` INT NOT NULL DEFAULT 0 ,
  `gmt_target` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_created` DATETIME NOT NULL DEFAULT 0 ,
  `gmt_modified` DATETIME NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_num` (`num` ASC) ,
  INDEX `idx_gmt_target` (`gmt_target` ASC) ,
  INDEX `idx_kw` (`kw` ASC) )
ENGINE = MyISAM;

ALTER TABLE `analysis_inquiry` ADD INDEX `idx_inquiry_target`(`inquiry_target`);

