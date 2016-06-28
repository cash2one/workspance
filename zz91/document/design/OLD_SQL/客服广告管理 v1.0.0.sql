DROP TABLE IF EXISTS servicer_ads;

DROP TABLE IF EXISTS servicer_ads_reply;

/*==============================================================*/
/* Table: servicer_ads                                          */
/*==============================================================*/
CREATE TABLE servicer_ads
(
   id                   INT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   ads_type_code        VARCHAR(200) COMMENT '广告类型',
   custom_key           VARCHAR(100) COMMENT '客户关键字',
   custom_name          VARCHAR(200) COMMENT '用户名',
   company_name         VARCHAR(300) COMMENT '公司名',
   title                VARCHAR(100) COMMENT '信息标题(排名/展播)',
   main_business        VARCHAR(50) COMMENT '主营业务',
   contact              VARCHAR(20) COMMENT '联系方式',
   contact_person       VARCHAR(200) COMMENT '联系人',
   url                  VARCHAR(200) COMMENT '门市部地址',
   ads_time             VARCHAR(50) COMMENT '广告时间',
   instruction          VARCHAR(1000) COMMENT '特定说明',
   saler                VARCHAR(50) COMMENT '负责人',
   checked              CHAR(1) COMMENT '审核(0否 1是)',
   servicer             VARCHAR(50) COMMENT '客服人员',
   gmt_created          DATETIME COMMENT '创建时间',
   gmt_modified         DATETIME COMMENT '修改时间',
   PRIMARY KEY (id)
)
TYPE = MYISAM;

/*==============================================================*/
/* Table: servicer_ads_reply                                    */
/*==============================================================*/
CREATE TABLE servicer_ads_reply
(
   id                   INT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   servicer_ads_id      INT(20) COMMENT '回复记录编号',
   content              VARCHAR(1000) COMMENT '回复内容',
   reply_user           VARCHAR(50) COMMENT '回复人',
   gmt_created          DATETIME COMMENT '创建时间',
   gmt_modified         DATETIME COMMENT '修改时间',
   PRIMARY KEY (id)
)
TYPE = MYISAM;