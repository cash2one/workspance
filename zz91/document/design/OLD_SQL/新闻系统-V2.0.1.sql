DROP TABLE IF EXISTS upload_files;

DROP TABLE IF EXISTS news;

DROP TABLE IF EXISTS news_category;

DROP TABLE IF EXISTS news_r_category;


/*==============================================================*/
/* Table: upload_files                                          */
/*==============================================================*/
create table upload_files
(
   id                   int(20) not null auto_increment comment '编号',
   file_name            varchar(50) comment '文件名',
   file_type            int(4) comment '类型(0,未知;1,图片;2,Flash;3,txt;...)',
   file_extension       varchar(10) comment '扩展名',
   file_path            varchar(200) comment '文件路径',
   table_id             int(20) comment '关联项编号',
   table_name           varchar(20) comment '关联表名称',
   gmt_created          datetime comment '上传时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table upload_files comment '文件上传表';

/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
CREATE TABLE news
(
   id                   INT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
   title                VARCHAR(100) COMMENT '资讯标题',
   index_title          VARCHAR(100) COMMENT '首页标题',
   base_type_id         INT(20) COMMENT '基础类别(来自新闻类别表)',
   type_id              INT(20) COMMENT '主类别(来自新闻类别表)',
   assist_type_id       INT(20) COMMENT '辅助类别',
   go_url               VARCHAR(100) COMMENT '跳转链接',
   content              TEXT COMMENT '资讯内容',
   seo_details          VARCHAR(350) COMMENT 'SEO内容介绍',
   gmt_order            DATETIME COMMENT '排序时间',
   gmt_created          DATETIME COMMENT '创建时间',
   gmt_modified         DATETIME COMMENT '修改时间',
   is_checked           CHAR(1) COMMENT '审核与否',
   is_issue             CHAR(1) COMMENT '是否直接发布',
   tags                 VARCHAR(300) COMMENT '标签',
   font_color           VARCHAR(50) COMMENT '标题颜色(0 无 1红 2绿 3黄 4蓝)',
   font_size            VARCHAR(50) COMMENT '标题字号',
   is_blod              CHAR(1) COMMENT '是否加粗',
   click_number         INT(20) COMMENT '点击率',
   real_click_number    INT(20) COMMENT '点击率',
   is_today_review      CHAR(1) COMMENT '是否推荐为今日导读',
   PRIMARY KEY (id)
)
TYPE = MYISAM;

ALTER TABLE news COMMENT '新闻表';


/*==============================================================*/
/* Table: news_category                                         */
/*==============================================================*/
CREATE TABLE news_category
(
   id                   INT(20) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
   parent_id            INT(20) COMMENT '父节点(0代表父节点)',
   NAME                 VARCHAR(100) COMMENT '类别名称',
   go_url               VARCHAR(100) COMMENT '链接地址',
   show_index           INT(4) COMMENT '排序',
   gmt_created          DATETIME COMMENT '创建时间',
   gmt_modified         DATETIME COMMENT '修改时间',
   is_delete            TINYINT(1) COMMENT '是否删除(0否 1是)',
   PRIMARY KEY (id)
)
TYPE = MYISAM;

ALTER TABLE news_category COMMENT '新闻类别表';

/*==============================================================*/
/* Table: news_r_category                                       */
/*==============================================================*/
CREATE TABLE news_r_category
(
   id                   INT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
   news_id              INT(20) COMMENT '资讯编号',
   news_category_id     INT(20) COMMENT '类别编号',
   PRIMARY KEY (id)
)
TYPE = MYISAM;
