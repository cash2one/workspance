
/*==============================================================*/
/* Table: products_series_contacts                              */
/*==============================================================*/
create table products_series_contacts
(
   id                   int(20) not null,
   products_id          int(20) comment '关联供求ID',
   products_series_id   int(20) comment '关联供求系列ID',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table products_series_contacts comment '供求系列联系';


/*==============================================================*/
/* Table: products_series                                       */
/*==============================================================*/
create table products_series
(
   id                   int(20) not null,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '帐号名',
   name                 varchar(200) comment '系列名称',
   series_details       text comment '系列描述',
   series_order         int(4) comment '系列排序',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   key AK_id (id)
)
type = MYISAM;

alter table products_series comment '供求系列';

/*==============================================================*/
/* Table: products                                              */
/*==============================================================*/
create table products
(
   id                   int(20) not null,
   company_id           int(20) comment '发布公司ID',
   account              varchar(50) comment '发布帐号名',
   products_type_code   varchar(200) comment '供求类别',
   source_type_code     varchar(200) comment '信息来源',
   title                varchar(200) comment '标题',
   details              text comment '详细描述',
   location             varchar(100) comment '货物所在地',
   provide_status       char(1) comment '0:长期,1:不定期',
   total_quantity       varchar(50) comment '供货总量',
   is_show_in_price     char(1) default '0' comment '是否显示在企业报价',
   price_unit           varchar(10) comment '元,美元',
   price                float comment '价格',
   quantity_unit        varchar(10) comment '数量单位,吨,公斤等',
   quantity             float comment '数量',
   source               varchar(200) comment '货源地',
   specification        varchar(100) comment '产品规格',
   origin               varchar(100) comment '来源产品',
   impurity             varchar(20) comment '杂质含量',
   color                varchar(10) comment '颜色',
   useful               varchar(10) comment '用途',
   appearance           varchar(50) comment '外观',
   manufacture          varchar(100) comment '加工说明',
   remark               text comment '只在后台显示,供客服记录一些备注信息',
   post_type            char(1) default '0' comment '前台还是后台发布',
   is_pause             char(1) default '0' comment '是否暂停发布',
   is_post_when_view_limit char(1) default '0' comment '是否为查看受限时才发布的产品',
   is_post_from_inquiry char(1) default '0' comment '是否为询盘导出的供求',
   is_del               char(1) default '0' comment '是否删除',
   category_products_main_code varchar(200) comment '供求主类别code',
   category_products_assist_code varchar(200) comment '供求辅助类别',
   check_person         varchar(100) comment '审核者',
   check_status         char(1) default '0' comment '0:未审核,1:已审核,2:审核未通过退回',
   unchecked_check_status char(1) default '0' comment '0:未审核,1:已审核',
   unpass_reason        varchar(500) comment '未通过原因',
   check_time           datetime comment '审核时间',
   real_time            datetime comment '真实发布时间',
   refresh_time         datetime comment '刷新时间',
   expire_time          datetime comment '过期时间,相对于刷新时间来说',
   gmt_created          datetime,
   gmt_modified         datetime,
   min_price            float comment '价格下限',
   max_price            float comment '价格上限',
   goods_type_code      varchar(200) comment '货物类型：期货或现货，对应code(1036)',
   primary key (id),
   key AK_Key_1 (id)
)
type = MYISAM;

alter table products comment '供求信息';

/*==============================================================*/
/* Table: products_keywords_rank                                */
/*==============================================================*/
create table products_keywords_rank
(
   id                   int(20) not null auto_increment,
   product_id           int(20),
   name                 varchar(200) not null,
   start_time           datetime,
   end_time             datetime,
   is_checked           char(1),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_rare_match                                   */
/*==============================================================*/
create table products_rare_match
(
   id                   int(20) not null auto_increment,
   email                varchar(100) comment '匹配邮箱',
   source               varchar(500) comment '来源',
   description          varchar(500) comment '基本描述',
   is_viewed            char(1) comment '是否查看',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_rare                                         */
/*==============================================================*/
create table products_rare
(
   id                   int(20) not null auto_increment,
   product_id           int(20) comment '供求Id',
   admin_user           varchar(50) comment '提交者',
   old_or_new           varchar(200) comment '废料或新料',
   location_require     varchar(200) comment '产地要求',
   around_products      varchar(200) comment '周边产品',
   remark               varchar(500) comment '其它备注',
   servicer_advice      varchar(500) comment '服务部意见',
   business_advice      varchar(500) comment '交易组意见',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: products_albums                                       */
/*==============================================================*/
create table products_albums
(
   id                   int(20) not null auto_increment,
   parent_id            int(20) comment '父结点ID',
   name                 varchar(200) comment '相册名字',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table products_albums comment '产品相册';

/*==============================================================*/
/* Table: products_pic                                          */
/*==============================================================*/
create table products_pic
(
   id                   int(20) not null auto_increment,
   name                 varchar(200) comment '图片名称',
   product_id           int(20),
   pic_address          varchar(200) comment '图片地址',
   album_id             int(20),
   is_default           char(1) default '0' comment '是否为客户默认显示的图片',
   is_cover             char(1) default '0' comment '是否为相册的封面图片',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_relate_append                                */
/*==============================================================*/
create table products_relate_append
(
   id                   int(20) not null,
   product_id           int(20),
   category_products_append_id int(20),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: category_products_append                              */
/*==============================================================*/
create table category_products_append
(
   id                   int(20) not null auto_increment,
   parent_id            int(20),
   label              varchar(200) comment '类别名',
   is_del               char(1) comment '删除标志',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_relate_category                              */
/*==============================================================*/
create table products_relate_category
(
   id                   int(20) not null,
   product_id           int(20),
   product_category_code varchar(200),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: category_products                                     */
/*==============================================================*/
create table category_products
(
   id                   int(20) not null auto_increment,
   code                 varchar(200) comment '类别Code,每4位一级',
   label              varchar(200) comment '类别名',
   is_assist            char(1) comment '是否为辅助类别',
   is_del               char(1) comment '删除标志',
   gmt_created          datetime,
   gmt_modified         datetime,
   old_id               int(20),
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_search_associate_keywords                    */
/*==============================================================*/
create table products_search_associate_keywords
(
   id                   int(20) not null,
   category_products_code varchar(200),
   keyword              varchar(500) comment '关联关键字',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: description_template                                  */
/*==============================================================*/
create table description_template
(
   id                   int(20) not null,
   template_code        varchar(200),
   content              varchar(500),
   gmt_created          datetime,
   gmt_modified         datetime,
   key AK_Key_1 (id)
)
type = MYISAM;

drop table if exists products_recommend;

/*==============================================================*/
/* Table: products_recommend                                    */
/*==============================================================*/
create table products_recommend
(
   id                   int(20) not null auto_increment,
   product_id           int(20) comment '供求ID',
   title                varchar(200) comment '主题',
   content              text comment '内容',
   send_name            varchar(100) comment '发布人姓名',
   send_email           varchar(50) comment '发布人Email',
   receive_email        varchar(50) comment '收件人Email',
   real_time            datetime comment '发布时间',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table products_recommend comment '供求推荐';