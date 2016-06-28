-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.46-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema zzmail
--

CREATE DATABASE IF NOT EXISTS zzmail;
USE zzmail;

--
-- Definition of table `zzmail`.`account`
--

DROP TABLE IF EXISTS `zzmail`.`account`;
CREATE TABLE  `zzmail`.`account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `email_account` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `zzmail`.`account`
--

/*!40000 ALTER TABLE `account` DISABLE KEYS */;
LOCK TABLES `account` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


--
-- Definition of table `zzmail`.`mail_info`
--

DROP TABLE IF EXISTS `zzmail`.`mail_info`;
CREATE TABLE  `zzmail`.`mail_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `sender` varchar(100) DEFAULT NULL,
  `receiver` varchar(100) DEFAULT NULL,
  `email_title` varchar(500) DEFAULT NULL,
  `email_content` text,
  `template_id` int(11) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `send_status` int(11) DEFAULT NULL,
  `gmt_post` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `zzmail`.`mail_info`
--

/*!40000 ALTER TABLE `mail_info` DISABLE KEYS */;
LOCK TABLES `mail_info` WRITE;
INSERT INTO `zzmail`.`mail_info` VALUES  (3,'2011-11-10 16:26:50','2011-11-10 16:26:50','sender@zz91.com','receiver@zz91.net','emailTitle','content',1,1,1,'2011-11-10 16:38:06'),
 (5,'2011-11-10 17:02:50','2011-11-10 17:02:50','sender@zz91.com','receiver@zz91.net','emailTitle','content',1,1,1,'2011-11-10 17:14:06'),
 (7,'2011-11-10 17:03:14','2011-11-10 17:03:14','sender@zz91.com','receiver@zz91.net','emailTitle','content',1,1,1,'2011-11-10 17:14:30');
UNLOCK TABLES;
/*!40000 ALTER TABLE `mail_info` ENABLE KEYS */;


--
-- Definition of table `zzmail`.`template`
--

DROP TABLE IF EXISTS `zzmail`.`template`;
CREATE TABLE  `zzmail`.`template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `t_content` text,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `zzmail`.`template`
--

/*!40000 ALTER TABLE `template` DISABLE KEYS */;
LOCK TABLES `template` WRITE;
INSERT INTO `zzmail`.`template` VALUES  (1,'2011-10-25 00:00:00','2011-10-25 00:00:00','测试模板','<p>测试一下</p>','1000'),
 (2,'2011-11-04 00:00:00','2011-11-04 00:00:00','注册成功(中文)','<p>\n    尊敬的$!{name},</p>\n<p align=\"left\">\n    欢迎您加入RecycleChina.com(zz91再生网国际站)！</p>\n<p align=\"left\">\n    您的会员账号信息如下：</p>\n<p>&nbsp;</p>\n<p align=\"left\">\n    <span style=\"font-size:16px;font-weight:1px\">账号：$!{account}</span></p>\n<p>&nbsp;</p>\n<p align=\"left\">\n    <span style=\"font-size:16px;font-weight:1px\">密码：$!{password}</span></p>\n<p>&nbsp;</p>\n<p align=\"left\">\n    请点击“<a href=\"http://www.recyclechina.com/login.htm?url=/myrc/index.htm\" target=\"_blank\">My RC</a>”，用您的账号信息马上登录RecycleChina.COM寻找国外货源，开始您的网上废料生意旅程。</p>\n<p>&nbsp;</p>\n<p align=\"left\">\n    <a href=\"http:/www.recyclechina.com/myrc/myproducts/addProducts.htm\" target=\"_blank\">马上发布供求-Post Leads</a>：马上发布您的供求信息，上传清晰的产品图片， 让买家或卖家主动找上门</p>\n<p>&nbsp;</p>\n<p align=\"left\">\n    <a href=\"http://www.recyclechina.com/selloffers/leadsList---bs.htm\" target=\"_blank\">寻找国外货源</a>： 搜索产品关键字， 点击“Contact Now”联系供应商，第一时间获得国外优质货源</p>\n<p>&nbsp;</p>\n<p align=\"left\">\n    注：请点击RecycleChina.COM首页最上方或是右下角的 google 翻译按钮，选择“中文简体”，轻松访问我们网站。</p>\n<p>&nbsp;</p>\n<div>\n    <p><span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    如有任何问题，请马上联系我们:</p>\n<p align=\"left\">\n    公司：RecycleChina.COM </p>\n<p align=\"left\">\n    电话: 86-0571-5663 5663</p>\n<p align=\"left\">\n    传真: 86-0571-5663 7777</p>\n<p align=\"left\">\n    邮箱：service@recyclechina.com</p>\n<p align=\"left\">\n    注意：中英版都用 service@recyclechina.com 这个邮箱发。</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\" ><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','2'),
 (3,'2011-11-04 00:00:00','2011-11-04 00:00:00','注册成功(英文)','<p>\n    Dear $!{name},</p>\n<p align=\"left\">\n    Thank you for joining www.recyclechina.com!</p>\n<p align=\"left\">\n    Your sign in details:</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <span style=\"font-size:16px;font-weight:1px\">Account:$!{account}</span></p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <span style=\"font-size:16px;font-weight:1px\">Passwords:$!{password}</span></p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    Now sign in RecycleChina.COM with your account info. Pls click &quot;<a href=\"http://www.recyclechina.com/login.htm?url=/myrc/index.htm\" target=\"_blank\">My RC</a>&quot; to perfect your company profile and manage your scrap business.</p>\n<p>\n    &nbsp;</p>\n<h2 align=\"left\">\n    <a href=\"http://www.recyclechina.com/login.htm?url=/myrc/myproducts/addProducts.htm\" target=\"_blank\">Post your selling/buying leads online</a></h2>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    Post your trade leads with pictures to let other scrap merchants exactly what you buy or sell. Once you post your first trade leads, our system would automatically match a target buyer or seller for you.</p>\n<p>\n    &nbsp;</p>\n<h2 align=\"left\">\n    <a href=\"http://www.recyclechina.com/scrapprice/\" target=\"_blank\">Scrap Price</a></h2>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    Keep yourself abreast of the development of China scrap market. Wishing you the very best of scrap business!</p>\n<p>\n    &nbsp;</p>\n<div>\n    <p>\n        <span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    RecycleChina.COM</p>\n<p align=\"left\">\n    Tel: 86-0571-5663 5663</p>\n<p align=\"left\">\n    Fax: 86-0571-5663 7777</p>\n<p align=\"left\">\n    Email：service@recyclechina.com</p>\n<p align=\"left\">\n    MSN: recyclechina@live.com</p>\n<p align=\"left\">\n    Skype: recyclechina.com</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\"><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','3'),
 (4,'2011-11-04 00:00:00','2011-11-04 00:00:00','询盘提醒(中文)','尊敬的$!{toName},\n<p>&nbsp;</p>\n您好！ 您在<a href=\"http://www.recyclechina.com\" target=\"_blank\">www.recyclechina.com</a>收到一个来自<a href=\"http://www.recyclechina.com/companyInfo/details$!{sendCompany.id}.htm\" target=\"_blank\">$!{sendCompany.name}</a>公司的询盘。\n请点击登录<a href=\"http://www.recyclechina.com/login.htm?url=/myrc/index.htm\" target=\"_blank\">MY RC</a>查看.\n<p>&nbsp;</p>\n这是系统邮件，请勿直接回复。如有任何问题，请发邮件至 melody@recyclechina.com.\n<p>&nbsp;</p>\n<div>\n    <p>\n        <span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    RecycleChina.COM</p>\n<p align=\"left\">\n    Tel: 86-0571-5663 5663</p>\n<p align=\"left\">\n    Fax: 86-0571-5663 7777</p>\n<p align=\"left\">\n    Email：service@recyclechina.com</p>\n<p align=\"left\">\n    MSN: recyclechina@live.com</p>\n<p align=\"left\">\n    Skype: recyclechina.com</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\"><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','4'),
 (5,'2011-11-04 00:00:00','2011-11-04 00:00:00','询盘提醒(英文)','Dear $!{name},\n<p>&nbsp;</p>\nYou have received an inquiry from \n<a href=\"http://www.recyclechina.com/companyInfo/details$!{sendCompany.id}.htm\" target=\"_blank\">$!{sendCompany.name}</a> \non <a href=\"http://www.recyclechina.com\" target=\"_blank\">www.recyclechina.com</a>. \nPlease check your inbox in $!{myrcUrl}.\n<p>&nbsp;</p>\nPlease do not reply for this is a system email of RecycleChina.Com. For any question, Please send email to melody@recyclechina.com . Thank you!\n<p>&nbsp;</p>\n<div>\n    <p>\n        <span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    RecycleChina.COM</p>\n<p align=\"left\">\n    Tel: 86-0571-5663 5663</p>\n<p align=\"left\">\n    Fax: 86-0571-5663 7777</p>\n<p align=\"left\">\n    Email：service@recyclechina.com</p>\n<p align=\"left\">\n    MSN: recyclechina@live.com</p>\n<p align=\"left\">\n    Skype: recyclechina.com</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\"><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','5'),
 (6,'2011-11-04 00:00:00','2011-11-04 00:00:00','密码找回','You (or someone else) has requested to reset your password.<br />\n---------------------------------------------------------------------<br /><br />\n\nIf you follow the link below you will be able to personally reset your password.<br />\n$!{resetUrl}<br />\n(If clicking on the link does not work, try copying and pasting it into your browser.)<br />\nThis password reset request is valid for the next 24 hours.<br /><br />\n\nHere are the details of your account:<br />\n---------------------------------------------------------------------<br />\n           account: $!{account}<br />\n              Email: $!{email}<br />\n          Full Name: $!{name}<br />\n<br /><br />\n\n--<br />\nThis message is automatically generated by Recyclechina.com.<br />\n-<br />\nIf you think it was sent incorrectly,please contact us.<br />','6'),
 (7,'2011-11-04 00:00:00','2011-11-04 00:00:00','匹配客户(给买家)','<p>Dear $!{name},</p>\n<p>We are glad to inform you that our member <a href=\"$!{companyUrl}\" target=\"_blank\">$!{companyName}</a> could offer $!{product}. Please send inquiries to directly contact this company by clicking its link if you were still interested in this product. For the different languages, you could also send your inquires to elaine@recyclechina.com, we would contact this company for you. It is our pleasure to do this for your better communication. And you could also send other buying info to us. We would assist you to find targeted sellers.</p>\n<p>Please fell free to contact us for any question!</p>\n<p>&nbsp;</p>\n<div>\n    <p>\n        <span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    RecycleChina.COM</p>\n<p align=\"left\">\n    Tel: 86-0571-5663 5663</p>\n<p align=\"left\">\n    Fax: 86-0571-5663 7777</p>\n<p align=\"left\">\n    Email：service@recyclechina.com</p>\n<p align=\"left\">\n    MSN: recyclechina@live.com</p>\n<p align=\"left\">\n    Skype: recyclechina.com</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\"><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','7'),
 (8,'2011-11-04 00:00:00','2011-11-04 00:00:00','匹配客户(给卖家)','<p>Dear $!{name},</p>\n<p>We are glad to inform you that our member <a href=\"$!{companyUrl}\" target=\"_blank\">$!{companyName}</a> want to buy $!{product}. Please send inquiries to this company by clicking its link if you still had sources of this product. For the different languages, you could also send your product info to elaine@recyclechina.com, we would contact this company for you. It is our pleasure to do this for your better communication. And you could also send other selling info to us. We would assist you to find targeted buyers.</p>\n<p>Please fell free to contact us for any question!</p>\n<p>&nbsp;</p>\n<div>\n    <p>\n        <span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    RecycleChina.COM</p>\n<p align=\"left\">\n    Tel: 86-0571-5663 5663</p>\n<p align=\"left\">\n    Fax: 86-0571-5663 7777</p>\n<p align=\"left\">\n    Email：service@recyclechina.com</p>\n<p align=\"left\">\n    MSN: recyclechina@live.com</p>\n<p align=\"left\">\n    Skype: recyclechina.com</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\"><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','8'),
 (9,'2011-11-04 00:00:00','2011-11-04 00:00:00','未发布供求客户提醒','<p>Dear $!{name},</p>\n<p>You had registered our website for one week but did not post any buying or selling leads. Other scrap merchants would not know what exactly you want to buy or sell. It would not benefit you to get more good buyers or sellers. And we would match a target buyer or seller for you for your first trading leads. Do not miss any business opportunities. Please post your trading leads now!</p>\n<p>Forget your passwords? Please click to reset your new passwords</p>\n<p>Please contact us for any questions!</p>\n<p>&nbsp;</p>\n<div>\n    <p>\n        <span style=\"border-collapse: collapse; font-family: arial, sans-serif; \"><font face=\"arial, sans-serif\" size=\"4\">----</font></span></p>\n</div>\n<p align=\"left\">\n    RecycleChina.COM</p>\n<p align=\"left\">\n    Tel: 86-0571-5663 5663</p>\n<p align=\"left\">\n    Fax: 86-0571-5663 7777</p>\n<p align=\"left\">\n    Email：service@recyclechina.com</p>\n<p align=\"left\">\n    MSN: recyclechina@live.com</p>\n<p align=\"left\">\n    Skype: recyclechina.com</p>\n<p>\n    &nbsp;</p>\n<p align=\"left\">\n    <font class=\"Apple-style-span\"><span style=\"border-collapse: collapse; color: rgb(136, 136, 136); font-family: arial, sans-serif; \"><a href=\"http://www.recyclechina.com/\" style=\"color: rgb(42, 93, 176); \" target=\"_blank\">www.recyclechina.com</a>&nbsp;currently holds 100,000 registered high-quality domestic buyers and info of 70% Chinese scrap importers.</span><br />\n    <img height=\"45\" src=\"http://ww2.sinaimg.cn/bmiddle/5a78354fjw6dektfjjvuhj.jpg\" width=\"200\" /></font></p>\n<p align=\"left\">\n    &nbsp;</p>\n<div>\n    <p>\n        &nbsp;</p>\n    <p align=\"left\">\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n    <p>\n        &nbsp;</p>\n</div>','9');
UNLOCK TABLES;
/*!40000 ALTER TABLE `template` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
