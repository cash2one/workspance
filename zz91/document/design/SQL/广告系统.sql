
/*==============================================================*/
/* Table: servicer_ads                                          */
/*==============================================================*/
create table servicer_ads
(
   id                   int(20) not null auto_increment comment '编号',
   ads_type_code        varchar(200) comment '广告类型',
   custom_key           varchar(100) comment '客户关键字',
   custom_name          varchar(200) comment '用户名',
   company_name         varchar(300) comment '公司名',
   title                varchar(100) comment '信息标题(排名/展播)',
   main_business        varchar(50) comment '主营业务',
   contact              varchar(20) comment '联系方式',
   contact_person       varchar(200) comment '联系人',
   url                  varchar(200) comment '门市部地址',
   ads_time             varchar(50) comment '广告时间',
   instruction          varchar(1000) comment '特定说明',
   saler                varchar(50) comment '负责人',
   checked              char(1) comment '审核(0待审核  1处理中  2已处理)',
   servicer             varchar(50) comment '客服人员',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: servicer_ads_reply                                    */
/*==============================================================*/
create table servicer_ads_reply
(
   id                   int(20) not null auto_increment comment '编号',
   servicer_ads_id      int(20) comment '回复记录编号',
   content              varchar(1000) comment '回复内容',
   reply_user           varchar(50) comment '回复人',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: ads                                                   */
/*==============================================================*/
create table ads
(
   id                   int(20) not null auto_increment comment '编号',
   ads_place_id         int(20) comment '广告位编号',
   ads_group_id         int(20) comment '广告组编号',
   show_index           int(4) comment '排序值(按从小到大排列)',
   name                 varchar(200) comment '广告名称',
   is_custom            char(1) comment '是否客户广告(0否 1是)',
   custom_email         varchar(200) comment '客户电子邮箱',
   ads_type_code        varchar(200) comment '广告类型(静态图片/动态图片gif/Flash)',
   height               int(4) comment '广告高度',
   width                int(4) comment '广告宽度',
   url                  varchar(100) comment '链接地址',
   alt_text             varchar(100) comment '提示文字',
   remark               text comment '简单注释',
   hits                 int(20) comment '初始点击次数(0)',
   is_showed            char(1) comment '是否显示(0否 1是)',
   custom_id            int(20) comment '客户编号(0代表非客户广告)',
   litmit_number        int(20),
   gmt_start_time       datetime comment '投放开始时间',
   gmt_end_time         datetime comment '投放结束时间',
   real_hits            int(20) comment '真实点击次数(0)',
   is_checked           char(1) comment '审核(0否 1是)',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   pic_address          varchar(254),
   primary key (id)
)
type = MYISAM;

alter table ads comment '广告';

/*==============================================================*/
/* Table: ads_places                                            */
/*==============================================================*/
create table ads_places
(
   id                   int not null auto_increment comment '编号',
   parent_id            int(20) comment '父节点(零为父类；大于零为子类)',
   name                 varchar(200) comment '名称',
   width                int(4) comment '宽度',
   height               int(4) comment '高度',
   price                float comment '价格',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   is_delete            char(1) comment '是否删除(0，否；1，是。)',
   primary key (id)
)
type = MYISAM;

alter table ads_places comment '广告位';

/*==============================================================*/
/* Table: ads_groups                                            */
/*==============================================================*/
create table ads_groups
(
   id                   int(20) not null auto_increment comment '编号',
   parent_id            int(20) comment '父节点(零为父类；大于零为子类)',
   group_name           varchar(200) comment '组名',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   is_deleted           char(1) comment '是否删除(0，否；1，是。)',
   primary key (id)
)
type = MYISAM;

alter table ads_groups comment '广告组';
