/*==============================================================*/
/* Table: news_category                                         */
/*==============================================================*/
create table news_category
(
   id                   int(20) not null auto_increment comment '自动编号',
   parent_id            int(20) comment '父节点(0代表父节点)',
   name                 varchar(100) comment '类别名称',
   go_url               varchar(100) comment '链接地址',
   show_index           int(4) comment '排序',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   is_delete            tinyint(1) comment '是否删除(0否 1是)',
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
create table news
(
   id                   int(20) not null comment 'id',
   title                varchar(100) comment '资讯标题',
   index_title          varchar(100) comment '首页标题',
   base_type_id         int(20) comment '基础类别(来自新闻类别表)',
   type_id              int(20) comment '主类别(来自新闻类别表)',
   assist_type_id       int(20) comment '辅助类别',
   go_url               varchar(100) comment '跳转链接',
   content              text comment '资讯内容',
   seo_details          varchar(350) comment 'SEO内容介绍',
   gmt_order            datetime comment '排序时间',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   is_checked           char(1) comment '审核与否',
   is_issue             char(1) comment '是否直接发布',
   tags                 varchar(300) comment '标签',
   font_color           varchar(50) comment '标题颜色(0 无 1红 2绿 3黄 4蓝)',
   font_size            varchar(50) comment '标题字号',
   is_blod              char(1) comment '是否加粗',
   click_number         int(20) comment '点击率',
   real_click_number    int(20) comment '点击率',
   is_today_review      char(1) comment '是否推荐为今日导读',
   primary key (id)
)
type = MYISAM;

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
