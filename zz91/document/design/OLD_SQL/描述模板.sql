/*
MySQL Data Transfer
Source Host: localhost
Source Database: ast
Target Host: localhost
Target Database: ast
Date: 2010-3-1 18:12:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for description_template
-- ----------------------------
CREATE TABLE `description_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_code` varchar(40) DEFAULT NULL COMMENT '模板类别',
  `content` varchar(500) DEFAULT NULL COMMENT '模板内容',
  `gmt_created` date DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` date DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `description_template` VALUES ('3', null, null, null, null);
INSERT INTO `description_template` VALUES ('6', '10260001', '方的', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('7', '10260001', '反对方法的', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('9', '10260001', '地方', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('10', '10260001', '大方大方', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('11', '10260001', '苟富贵', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('12', '10260001', '广告', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('15', '10260001', '地方', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('16', '10260001', '地方法', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('17', '10260001', '东方饭店', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('18', '10260001', '地方的地方', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('20', '10260001', 'gfgffd', '2010-02-24', null);
INSERT INTO `description_template` VALUES ('21', '10260001', 'hfgfg', '2010-02-26', null);
