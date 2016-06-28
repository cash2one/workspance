CREATE DATABASE /*!32312 IF NOT EXISTS*/`ast_stat` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ast_stat`;

/*Table structure for table `tags_count` */

DROP TABLE IF EXISTS `tags_count`;

CREATE TABLE `tags_count` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `hits` int(20) DEFAULT '0' COMMENT '点击数',
  `tags_id` int(20) DEFAULT NULL COMMENT '关联的标签id',
  `ip` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果已登录，则记录登录者的帐号，否则为空',
  `company_id` int(20) DEFAULT NULL COMMENT '如果已登录，记录该登录者的公司ID，否则为0',
  `gmt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='标签点击统计，每次一条记录';


CREATE DATABASE /*!32312 IF NOT EXISTS*/`ast_stat_test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ast_stat_test`;

/*Table structure for table `tags_count` */

DROP TABLE IF EXISTS `tags_count`;

CREATE TABLE `tags_count` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `hits` int(20) DEFAULT '0' COMMENT '点击数',
  `tags_id` int(20) DEFAULT NULL COMMENT '关联的标签id',
  `ip` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果已登录，则记录登录者的帐号，否则为空',
  `company_id` int(20) DEFAULT NULL COMMENT '如果已登录，记录该登录者的公司ID，否则为0',
  `gmt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='标签点击统计，每次一条记录';