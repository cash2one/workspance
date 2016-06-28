/*
MySQL Data Transfer
Source Host: localhost
Source Database: ast
Target Host: localhost
Target Database: ast
Date: 2010-2-2 10:16:25
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for param
-- ----------------------------
CREATE TABLE `param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '参数名称',
  `types` varchar(20) DEFAULT NULL COMMENT '类别',
  `key` varchar(20) DEFAULT NULL COMMENT 'key',
  `value` varchar(255) DEFAULT NULL COMMENT 'value',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `isuse` tinyint(4) DEFAULT NULL,
  `gmt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for param_type
-- ----------------------------
CREATE TABLE `param_type` (
  `key` varchar(20) NOT NULL COMMENT 'key',
  `name` varchar(50) DEFAULT NULL COMMENT '参数名称',
  `gmt_created` datetime DEFAULT NULL COMMENT 'createDate',
  PRIMARY KEY (`key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

