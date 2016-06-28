CREATE DATABASE /*!32312 IF NOT EXISTS*/`zzads` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zzads`;


DROP TABLE IF EXISTS `advertiser`;

CREATE  TABLE IF NOT EXISTS `zzads`.`advertiser` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(254) NOT NULL COMMENT '广告主名称，可以是公司名称也可以是个人名称\n广告主可以是自己（阿思拓），也可以是再生能客户，或者其他网站或个人' ,
  `contact` VARCHAR(45) NULL COMMENT '联系人' ,
  `phone` VARCHAR(254) NULL COMMENT '联系电话，可以是多个' ,
  `email` VARCHAR(254) NULL COMMENT '联系email，可以是多个' ,
  `remark` VARCHAR(254) NULL COMMENT '备注' ,
  `category` TINYINT NULL DEFAULT 0 COMMENT '广告主类别\n0：阿思拓\n1：再生通用户\n2：其他' ,
  `deleted` CHAR(1) NULL COMMENT '标记删除(Y/N)\nN：正常（默认）\nY：已删除' ,
  `gmt_created` DATETIME NULL ,
  `gmt_modified` DATETIME NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '广告主，广告客户';

DROP TABLE IF EXISTS `ad_material`;

CREATE  TABLE IF NOT EXISTS `zzads`.`ad_material` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `ad_id` INT(20) NOT NULL COMMENT '关联的广告' ,
  `name` VARCHAR(60) NOT NULL COMMENT '素材名称' ,
  `material_type` VARCHAR(20) NULL COMMENT '素材类别' ,
  `file_path` VARCHAR(200) NULL COMMENT '素材文件路径' ,
  `remark` VARCHAR(100) NULL COMMENT '详细的素材描述' ,
  `gmt_created` DATETIME NULL COMMENT '详细的素材描述' ,
  `gmt_modified` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_ad_id` (`ad_id` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '广告素材';

DROP TABLE IF EXISTS `ad`;

CREATE  TABLE IF NOT EXISTS `zzads`.`ad` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `position_id` INT(20) NOT NULL COMMENT '广告位ID，每个广告必需投放到特定的广告位' ,
  `advertiser_id` INT(20) NOT NULL COMMENT '广告主ID' ,
  `ad_title` VARCHAR(60) NOT NULL COMMENT '广告标题，可能会在广告展示的时候使用' ,
  `ad_description` VARCHAR(300) NULL COMMENT '广告详细描述' ,
  `ad_content` VARCHAR(300) NULL ,
  `ad_target_url` VARCHAR(300) NOT NULL ,
  `online_status` CHAR(1) NOT NULL DEFAULT 'N' COMMENT '广告上下线状态\nY：表示上线\nN：表示下线' ,
  `gmt_start` DATETIME NULL COMMENT '广告生效时间' ,
  `gmt_plan_end` DATETIME NULL COMMENT '计划下线时间\n可以不设置' ,
  `remark` VARCHAR(300) NULL COMMENT '备注，只是内部查看，不对外公开' ,
  `applicant` VARCHAR(45) NOT NULL COMMENT '广告申请人：帐号名称' ,
  `reviewer` VARCHAR(45) NULL DEFAULT '' COMMENT '广告审核人：帐号名称' ,
  `review_status` CHAR(1) NOT NULL DEFAULT '' COMMENT '广告审核状态\n空:表示广告还没正式提交\nU:表示未审核\nY:表示审核通过\nN:表示不通过' ,
  `review_comment` VARCHAR(45) NULL COMMENT '审核说明' ,
  `designer` VARCHAR(45) NULL COMMENT '广告设计人：帐号名称' ,
  `design_status` CHAR(1) NULL ,
  `gmt_created` DATETIME NULL ,
  `gmt_modified` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_position` (`position_id` ASC) ,
  INDEX `idx_advertiser` (`advertiser_id` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '广告设计信息';

DROP TABLE IF EXISTS `ad_position`;

CREATE  TABLE IF NOT EXISTS `zzads`.`ad_position` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL COMMENT '广告位名称' ,
  `parent_id` INT(20) NOT NULL DEFAULT 0 COMMENT '上级广告位ID' ,
  `request_url` VARCHAR(200) NULL COMMENT '广告位所在的URL' ,
  `delivery_style_id` INT(20) NULL COMMENT '投放（显示）方式' ,
  `sequence` INT(11) NOT NULL DEFAULT 100 COMMENT '排序，同级（同一个parent_id）排序是优先使用排序' ,
  `payment_type` TINYINT NOT NULL DEFAULT 0 COMMENT '广告付费类型\n0：按时间段付费\n1：按点击付费\n2：按有效点击付费\n3：按印象付费(每千次)' ,
  `width` INT(11) NULL COMMENT '广告位长度' ,
  `height` INT(11) NULL COMMENT '广告位宽度' ,
  `max_ad` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '同时显示的最大广告数' ,
  `has_exact_ad` TINYINT(1) NULL COMMENT '是否为精确投放广告位' ,
  `gmt_created` DATETIME NULL ,
  `gmt_modified` DATETIME NULL ,
  `deleted` CHAR(1) NULL DEFAULT 'N' COMMENT '标记删除(Y/N)\nN：正常（默认）\nY：已删除' ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_parent_id` (`parent_id` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '广告位';

DROP TABLE IF EXISTS `exact_type`;

CREATE  TABLE IF NOT EXISTS `zzads`.`exact_type` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `exact_name` VARCHAR(20) NOT NULL COMMENT '精确投放类型：\n对应参数表col:param_key' ,
  `js_function` TEXT NULL ,
  `java_key` VARCHAR(45) NULL ,
  `remark` VARCHAR(50) NULL ,
  `gmt_created` DATETIME NULL ,
  `gmt_modified` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_idx_exact_name` (`exact_name` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '精确投放类型';

DROP TABLE IF EXISTS `delivery_style`;

CREATE  TABLE IF NOT EXISTS `zzads`.`delivery_style` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL COMMENT '投放方式名称' ,
  `js_function` TEXT NULL COMMENT 'JS生成代码' ,
  `gmt_created` DATETIME NULL ,
  `gmt_modified` DATETIME NULL ,
  `modifier` VARCHAR(45) NULL COMMENT '最后操作人：帐号名称' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_idx_name` (`name` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '广告投放方式';

DROP TABLE IF EXISTS `position_exact_type`;

CREATE  TABLE IF NOT EXISTS `zzads`.`position_exact_type` (
  `ad_position_id` INT(20) NOT NULL ,
  `exact_type_id` INT(20) NOT NULL ,
  `gmt_created` DATETIME NULL ,
  `creator` VARCHAR(45) NULL ,
  PRIMARY KEY (`ad_position_id`, `exact_type_id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '广告位_精确投放关联表';

DROP TABLE IF EXISTS `ad_exact_type`;

CREATE  TABLE IF NOT EXISTS `zzads`.`ad_exact_type` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `ad_id` INT(20) NOT NULL COMMENT '要精确定位的广告信息' ,
  `exact_type_id` INT(20) NOT NULL COMMENT '精确定位的类型' ,
  `anchor_point` VARCHAR(20) NOT NULL COMMENT '锚点（定位条件）' ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '定位精确广告的详细信息表';
