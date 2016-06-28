CREATE DATABASE /*!32312 IF NOT EXISTS*/`ast_stat` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ast_stat`;

/*Table structure for table `tags_count` */

DROP TABLE IF EXISTS `tags_count`;

CREATE TABLE `tags_count` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `hits` int(20) DEFAULT '0' COMMENT '�����',
  `tags_id` int(20) DEFAULT NULL COMMENT '�����ı�ǩid',
  `ip` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '����ѵ�¼�����¼��¼�ߵ��ʺţ�����Ϊ��',
  `company_id` int(20) DEFAULT NULL COMMENT '����ѵ�¼����¼�õ�¼�ߵĹ�˾ID������Ϊ0',
  `gmt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='��ǩ���ͳ�ƣ�ÿ��һ����¼';


CREATE DATABASE /*!32312 IF NOT EXISTS*/`ast_stat_test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ast_stat_test`;

/*Table structure for table `tags_count` */

DROP TABLE IF EXISTS `tags_count`;

CREATE TABLE `tags_count` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `hits` int(20) DEFAULT '0' COMMENT '�����',
  `tags_id` int(20) DEFAULT NULL COMMENT '�����ı�ǩid',
  `ip` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '����ѵ�¼�����¼��¼�ߵ��ʺţ�����Ϊ��',
  `company_id` int(20) DEFAULT NULL COMMENT '����ѵ�¼����¼�õ�¼�ߵĹ�˾ID������Ϊ0',
  `gmt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='��ǩ���ͳ�ƣ�ÿ��һ����¼';