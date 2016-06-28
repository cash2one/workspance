drop table if exists stat_common_day_report;

drop table if exists stat_common_details;

drop table if exists stat_common_total;

drop table if exists stat_login;

drop table if exists stat_member_total;

drop table if exists stat_view_day_report;

drop table if exists stat_view_details;

/*==============================================================*/
/* Table: stat_common_day_report                                */
/*==============================================================*/
create table stat_common_day_report
(
   id                   int(20) not null auto_increment,
   stat_id              int(20),
   hits                 int(20),
   stat_date            datetime comment '统计日期',
   stat_type            char(1) comment '统计类型：
            0：报价；1：广告',
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: stat_common_details                                   */
/*==============================================================*/
create table stat_common_details
(
   id                   int(20) not null auto_increment,
   ip                   varchar(15) comment '访问者IP',
   stat_id              int(20) comment '待统计项的ID',
   is_handled           char(1) comment '是否已处理',
   stat_type            char(1) comment '统计类型：
            0：报价；1：广告',
   stat_time            datetime comment '添加时间',
   primary key (id)
)
type = MYISAM;

alter table stat_common_details comment '通用数据访问明细表，用来记录只需要统计访问量的功能';

/*==============================================================*/
/* Table: stat_common_total                                     */
/*==============================================================*/
create table stat_common_total
(
   id                   int(20) not null auto_increment,
   stat_id              int(20),
   hits                 int(20),
   stat_date            datetime,
   stat_type            char(1) comment '统计类型：
            0：报价；1：广告',
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: stat_login                                            */
/*==============================================================*/
create table stat_login
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(50),
   ip                   varchar(15),
   redirect_url         varchar(500) comment '登录后跳转的页面',
   is_handled           char(1) comment '是否已处理，统计进总计',
   stat_time            datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: stat_member_total                                     */
/*==============================================================*/
create table stat_member_total
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(50),
   login_count          int(20) comment '会员登录数',
   product_view_count   int(20) comment '查看过的供求信息次数',
   product_viewed_count int(20) comment '被查看供求数',
   contact_view_count   int(20) comment '联系方式查看次数',
   contact_viewed_count int(20) comment '联系方式被查看次数',
   message_send_count   int(20) comment '发送留言数',
   message_receive_count int(20) comment '接收留言数',
   stat_date            datetime comment '统计时间',
   primary key (id)
)
type = MYISAM;

alter table stat_member_total comment '会员相关数据总计:1.查看供求总计2.会员登录总计3.留言数总计';

/*==============================================================*/
/* Table: stat_view_day_report                                  */
/*==============================================================*/
create table stat_view_day_report
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(50),
   view_count           int(20) comment '查看次数',
   viewed_count         int(20) comment '被查看次数',
   stat_type            char(1) comment '查看类型：0：联系方式，1：供求信息',
   stat_date            datetime,
   primary key (id)
)
type = MYISAM;

alter table stat_view_day_report comment '查看供求/联系日报表';

/*==============================================================*/
/* Table: stat_view_details                                     */
/*==============================================================*/
create table stat_view_details
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(50),
   product_id           int(20),
   to_company_id        int(20) comment '被查看的公司',
   to_account           varchar(50) comment '被查看只对公司有效，所以该字段暂时没用',
   stat_type            char(1) comment '查看类型：0：联系方式，1：供求信息',
   is_handled           char(1) comment '是否已处理，统计入总计',
   stat_time            datetime,
   primary key (id)
)
type = MYISAM;

alter table stat_view_details comment '查看/被查看明细表，包括查看供求和联系方式';
