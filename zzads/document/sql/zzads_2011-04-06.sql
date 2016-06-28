/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.46-log : Database - zzads
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zzads` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zzads`;

/*Table structure for table `ad` */

DROP TABLE IF EXISTS `ad`;

CREATE TABLE `ad` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `position_id` int(20) NOT NULL COMMENT '广告位ID，每个广告必需投放到特定的广告位',
  `advertiser_id` int(20) NOT NULL COMMENT '广告主ID',
  `ad_title` varchar(60) NOT NULL COMMENT '广告标题，可能会在广告展示的时候使用',
  `ad_description` varchar(300) DEFAULT NULL COMMENT '广告详细描述',
  `ad_content` varchar(300) DEFAULT NULL COMMENT '广告内容',
  `ad_target_url` varchar(300) DEFAULT NULL COMMENT '广告跳转目标URL',
  `online_status` char(1) NOT NULL DEFAULT 'N' COMMENT '广告上下线状态\nY：表示上线\nN：表示下线',
  `gmt_start` datetime DEFAULT NULL COMMENT '广告生效时间',
  `gmt_plan_end` datetime DEFAULT NULL COMMENT '计划下线时间\n可以不设置',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注，只是内部查看，不对外公开',
  `applicant` varchar(45) NOT NULL COMMENT '广告申请人：帐号名称',
  `reviewer` varchar(45) DEFAULT NULL COMMENT '广告审核人：帐号名称',
  `review_status` char(1) NOT NULL DEFAULT 'U' COMMENT '广告审核状态\nU:表示未审核\nY:表示审核通过\nN:表示不通过',
  `review_comment` varchar(45) DEFAULT NULL COMMENT '审核说明',
  `designer` varchar(45) DEFAULT NULL COMMENT '广告设计人：帐号名称',
  `design_status` char(1) DEFAULT NULL COMMENT '设计状态',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_blank` char(1) NOT NULL DEFAULT 'N' COMMENT '是否空白占位广告\nY 是（1/广告位）\nN 否',
  PRIMARY KEY (`id`),
  KEY `idx_position` (`position_id`),
  KEY `idx_advertiser` (`advertiser_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='广告设计信息';

/*Data for the table `ad` */

insert  into `ad`(`id`,`position_id`,`advertiser_id`,`ad_title`,`ad_description`,`ad_content`,`ad_target_url`,`online_status`,`gmt_start`,`gmt_plan_end`,`remark`,`applicant`,`reviewer`,`review_status`,`review_comment`,`designer`,`design_status`,`gmt_created`,`gmt_modified`,`is_blank`) values (1,1,1,'test01_pos1','用户1在广告位1上投放的广告','ad_content: VARCHAR','ad_target_url: VARCHAR','Y','2011-02-16 15:49:16','2011-02-16 15:49:16','remark: VARCHAR','applicant11','reviewer','u','review_comment: VARCHAR','designer','0','2011-02-16 15:49:16','2011-02-16 15:49:16','Y'),(2,2,2,'test02_pos2','用户2在广告位2上投放的广告','ad_content: VARCHAR','ad_target_url: VARCHAR','Y','2011-02-16 15:49:16','2011-02-16 15:49:16','remark: VARCHAR','applicant11','reviewer','u','review_comment: VARCHAR','designer','0','2011-02-16 15:49:16','2011-02-16 15:49:16','N'),(7,1,1,'adTitle2',NULL,NULL,'www.zz91.com.cn','Y',NULL,NULL,NULL,'applicant1',NULL,'U',NULL,NULL,NULL,'2011-02-19 10:27:33','2011-02-19 10:27:33','N'),(8,1,1,'adTitle3',NULL,NULL,'www.zz91.com.cn','Y',NULL,NULL,NULL,'applicant3',NULL,'U',NULL,NULL,NULL,'2011-02-19 10:29:06','2011-02-19 10:29:06','N');

/*Table structure for table `ad_delivery_style` */

DROP TABLE IF EXISTS `ad_delivery_style`;

CREATE TABLE `ad_delivery_style` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '投放方式名称',
  `js_function` text COMMENT 'JS生成代码',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `modifier` varchar(45) DEFAULT NULL COMMENT '最后操作人：帐号名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_idx_name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='广告投放方式';

/*Data for the table `ad_delivery_style` */

insert  into `ad_delivery_style`(`id`,`name`,`js_function`,`gmt_created`,`gmt_modified`,`modifier`) values (1,'普通','js_function: TEXT','2011-02-16 15:27:36',NULL,NULL),(2,'轮询','js_function: TEXT','2011-02-16 15:27:36',NULL,NULL),(3,'弹出窗','js_function: TEXT','2011-02-16 15:27:36',NULL,NULL);

/*Table structure for table `ad_material` */

DROP TABLE IF EXISTS `ad_material`;

CREATE TABLE `ad_material` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `ad_id` int(20) NOT NULL COMMENT '关联的广告',
  `name` varchar(60) NOT NULL COMMENT '素材名称',
  `material_type` varchar(20) DEFAULT NULL COMMENT '素材类别',
  `file_path` varchar(200) DEFAULT NULL COMMENT '素材文件路径',
  `remark` varchar(100) DEFAULT NULL COMMENT '详细的素材描述',
  `gmt_created` datetime DEFAULT NULL COMMENT '详细的素材描述',
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ad_id` (`ad_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='广告素材';

/*Data for the table `ad_material` */

/*Table structure for table `ad_position` */

DROP TABLE IF EXISTS `ad_position`;

CREATE TABLE `ad_position` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '广告位名称',
  `parent_id` int(20) NOT NULL DEFAULT '0' COMMENT '上级广告位ID',
  `request_url` varchar(200) DEFAULT NULL COMMENT '广告位所在的URL',
  `delivery_style_id` int(20) DEFAULT NULL COMMENT '投放（显示）方式',
  `sequence` int(11) NOT NULL DEFAULT '100' COMMENT '排序，同级（同一个parent_id）排序是优先使用排序',
  `payment_type` varchar(10) NOT NULL DEFAULT '0' COMMENT '广告付费类型\nCPA (Cost-per-Action) ：每次行动的费用\nCPC (Cost-per-click)： 每次点击的费用\nCPM（Cost per Thousand Impressions）：每千次印象费用\n....',
  `width` int(11) DEFAULT NULL COMMENT '广告位长度',
  `height` int(11) DEFAULT NULL COMMENT '广告位宽度',
  `max_ad` tinyint(2) NOT NULL DEFAULT '1' COMMENT '同时显示的最大广告数',
  `has_exact_ad` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为精确投放广告位',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '标记删除(Y/N)\nN：正常（默认）\nY：已删除',
  `ad_client` varchar(45) DEFAULT NULL COMMENT '广告投放目标网站编码',
  `ad_client_slot` varchar(45) DEFAULT NULL COMMENT '广告投放目标网站的广告位编号',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='广告位';

/*Data for the table `ad_position` */

insert  into `ad_position`(`id`,`name`,`parent_id`,`request_url`,`delivery_style_id`,`sequence`,`payment_type`,`width`,`height`,`max_ad`,`has_exact_ad`,`gmt_created`,`gmt_modified`,`deleted`,`ad_client`,`ad_client_slot`) values (1,'zz91首页普通广告',0,'www.zz91.com',1,1,'0',40,50,1,0,'2011-02-16 15:36:30',NULL,'N',NULL,NULL),(2,'zz91首页轮询广告',0,'www.zz91.com',2,2,'0',40,50,2,1,'2011-02-16 15:36:30',NULL,'N',NULL,NULL),(3,'zz91首页轮询广告',0,'www.zz91.com',2,2,'0',40,50,2,1,'2011-02-16 15:36:30',NULL,'N',NULL,NULL);

/*Table structure for table `ads_loginfo` */

DROP TABLE IF EXISTS `ads_loginfo`;

CREATE TABLE `ads_loginfo` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `level` char(6) NOT NULL DEFAULT 'info' COMMENT '日志级别',
  `ip` varchar(40) NOT NULL COMMENT '请求操作的IP',
  `request_url` varchar(200) NOT NULL COMMENT '请求的URL',
  `query_string` varchar(300) DEFAULT NULL COMMENT '请求参数',
  `action` varchar(20) NOT NULL COMMENT '动作类型',
  `gmt_request_time` datetime NOT NULL COMMENT '请求时间',
  `information` text COMMENT '请求返回的结果',
  `error_info` varchar(200) DEFAULT NULL COMMENT '错误信息',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `gmt_created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='日志信息';

/*Data for the table `ads_loginfo` */

insert  into `ads_loginfo`(`id`,`level`,`ip`,`request_url`,`query_string`,`action`,`gmt_request_time`,`information`,`error_info`,`remark`,`gmt_created`) values (1,'info','192.168.2.1','www.zz91.com',NULL,'action','2011-02-19 15:11:04',NULL,NULL,NULL,'2011-02-19 14:54:37'),(2,'info','192.168.2.1','www.zz91.com',NULL,'action','2011-04-01 08:50:12',NULL,NULL,NULL,'2011-04-01 08:33:04'),(3,'info','192.168.2.1','www.zz91.com',NULL,'action','2011-04-01 09:22:53',NULL,NULL,NULL,'2011-04-01 09:05:44');

/*Table structure for table `advertiser` */

DROP TABLE IF EXISTS `advertiser`;

CREATE TABLE `advertiser` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '广告主名称，可以是公司名称也可以是个人名称\n广告主可以是自己（阿思拓），也可以是再生能客户，或者其他网站或个人',
  `contact` varchar(45) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系电话，可以是多个',
  `email` varchar(100) DEFAULT NULL COMMENT '联系email，可以是多个',
  `remark` varchar(250) DEFAULT NULL COMMENT '备注',
  `category` varchar(20) DEFAULT '' COMMENT '广告主类别\n0：阿思拓\n1：再生通用户\n2：其他',
  `deleted` char(1) DEFAULT NULL COMMENT '标记删除(Y/N)\nN：正常（默认）\nY：已删除',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='广告主，广告客户';

/*Data for the table `advertiser` */

insert  into `advertiser`(`id`,`name`,`contact`,`phone`,`email`,`remark`,`category`,`deleted`,`gmt_created`,`gmt_modified`) values (1,'test01','contact_name1','123456','test01@email.com','{remark: VARCHAR}','1','N','2011-02-16 15:14:56',NULL),(2,'test02','contact_name2','123456','test02@email.com','{remark: VARCHAR}','1','N','2011-02-16 15:15:12',NULL),(3,'test03','contact_name3','123456','test03@email.com','{remark: VARCHAR}','1','N','2011-02-16 15:15:12',NULL),(4,'test04','contact_name4','123456','test04@email.com','{remark: VARCHAR}','1','N','2011-02-16 15:15:12',NULL),(5,'test05','contact_name5','123456','test05@email.com','{remark: VARCHAR}','1','N','2011-02-16 15:15:12',NULL),(6,'test06','contact_name6','123456','test06@email.com','{remark: VARCHAR}','1','N','2011-02-16 15:15:12',NULL);

/*Table structure for table `advertiser_account` */

DROP TABLE IF EXISTS `advertiser_account`;

CREATE TABLE `advertiser_account` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `advertiser_id` int(20) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `recharge` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='广告主的账户信息';

/*Data for the table `advertiser_account` */

/*Table structure for table `advertiser_account_log` */

DROP TABLE IF EXISTS `advertiser_account_log`;

CREATE TABLE `advertiser_account_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='广告主账户变动日志';

/*Data for the table `advertiser_account_log` */

/*Table structure for table `exact_ad_details` */

DROP TABLE IF EXISTS `exact_ad_details`;

CREATE TABLE `exact_ad_details` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `ad_id` int(20) NOT NULL COMMENT '要精确定位的广告信息',
  `exact_put_id` int(20) NOT NULL COMMENT '精确定位的类型',
  `anchor_point` varchar(50) NOT NULL COMMENT '锚点（定位条件）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定位精确广告的详细信息表';

/*Data for the table `exact_ad_details` */

insert  into `exact_ad_details`(`id`,`ad_id`,`exact_put_id`,`anchor_point`) values (1,1,1,'keywords1'),(2,1,2,'language_en');

/*Table structure for table `exact_ad_type` */

DROP TABLE IF EXISTS `exact_ad_type`;

CREATE TABLE `exact_ad_type` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `exact_name` varchar(20) NOT NULL COMMENT '精确投放类型：\n对应参数表col:param_key',
  `match_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '匹配方式：\n1完全匹配exact match \n2模糊匹配fuzzy match\n ',
  `js_function` text,
  `java_key` varchar(45) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_idx_exact_name` (`exact_name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='精确投放类型';

/*Data for the table `exact_ad_type` */

insert  into `exact_ad_type`(`id`,`exact_name`,`match_type`,`js_function`,`java_key`,`remark`,`gmt_created`,`gmt_modified`) values (1,'keywords',1,'exactFilterJSON.append(\"keywords\", \"value\")  ','java_key: VARCHAR','通过关键字精确定位广告','2011-02-16 15:22:05',NULL),(2,'language',1,'js_function: TEXT','java_key: VARCHAR','通过浏览者使用的语言精确定位广告','2011-02-16 15:22:05',NULL),(3,'productCate',1,'js_function: TEXT','java_key: VARCHAR','通过产品类别精确定位广告','2011-02-16 15:22:05',NULL);

/*Table structure for table `position_exact_ad_type` */

DROP TABLE IF EXISTS `position_exact_ad_type`;

CREATE TABLE `position_exact_ad_type` (
  `position_id` int(20) NOT NULL,
  `exact_put_id` int(20) NOT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `creator` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`position_id`,`exact_put_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='广告位_精确投放关联表';

/*Data for the table `position_exact_ad_type` */

insert  into `position_exact_ad_type`(`position_id`,`exact_put_id`,`gmt_created`,`creator`) values (1,1,'2011-02-19 16:15:08',NULL),(1,2,'2011-02-19 16:15:08',NULL),(2,1,'2011-02-19 16:15:08',NULL),(2,2,'2011-02-19 16:15:08',NULL),(2,3,'2011-02-19 16:15:08',NULL);

/*Table structure for table `position_quote` */

DROP TABLE IF EXISTS `position_quote`;

CREATE TABLE `position_quote` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `quote_value` float DEFAULT NULL,
  `units` varchar(45) DEFAULT NULL,
  `quote_type` varchar(45) DEFAULT NULL COMMENT '付费类型：CPC，CPA等',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='广告位报价';

/*Data for the table `position_quote` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
