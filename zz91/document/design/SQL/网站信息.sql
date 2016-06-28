/*==============================================================*/
/* Table: friend_link                                           */
/*==============================================================*/
create table friend_link
(
   id                   int(20) not null auto_increment,
   link_name            varchar(200),
   link_category_code   varchar(50),
   height               varchar(10),
   width                varchar(10),
   pic_address          varchar(254),
   text_color           varchar(50),
   ischecked            char(1),
   link                 varchar(254),
   show_index           varchar(4),
   add_time             datetime,
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table friend_link comment '友情链接';

/*==============================================================*/
/* Table: recommend                                             */
/*==============================================================*/
create table recommend
(
   id                   int(20) not null auto_increment comment '编号',
   company_id           int(20) comment '企业编号',
   sender_account       varchar(20) comment '发件人账号',
   sender_name          varchar(50) comment '发件人名称',
   from_email           varchar(100) comment '发件人邮箱',
   to_email             varchar(100) comment '收件人邮箱',
   subject              varchar(200) comment '邮件主题',
   content              text comment '邮件内容',
   gmt_created          datetime comment '发送时间',
   primary key (id)
)
type = MYISAM;

alter table recommend comment '推荐表';

/*==============================================================*/
/* Table: customer_message                                      */
/*==============================================================*/
create table customer_message
(
   id                   int(20) not null auto_increment comment '主键',
   company_id           int(11) comment '公司ID',
   message_user         varchar(96) comment '留言者',
   email                varchar(200) comment '邮箱',
   topic                varchar(200) comment '留言主题',
   message_time         datetime comment '留言时间',
   check_user           varchar(30) comment '审核者',
   is_checked           char(10) comment '审核状态',
   massage_content      text comment '留言内容',
   is_read              char(1) comment '是否已读',
   is_reply             char(1) comment '是否回复',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table customer_message comment '留言';

/*==============================================================*/
/* Table: customer_message_reply                                */
/*==============================================================*/
create table customer_message_reply
(
   id                   int(20) not null auto_increment comment '主键',
   company_id           int(11) comment '公司ID',
   customer_message_id  int(20) comment '客户留言ID',
   reply_content        text comment '回复内容',
   reply_time           datetime comment '回复日期',
   reply_type           char(1) comment '留言识别',
   reply_user           varchar(30) comment '回复人',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table customer_message_reply comment '回复';

/*==============================================================*/
/* Table: member_rule                                           */
/*==============================================================*/
create table member_rule
(
   id                   int(20) not null auto_increment,
   membership_code      varchar(100),
   operation            varchar(254),
   operation_code       varchar(254),
   results              varchar(254),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;
