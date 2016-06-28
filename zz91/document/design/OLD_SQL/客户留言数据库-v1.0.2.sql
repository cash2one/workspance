drop table if exists customer_message;

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

alter table customer_message comment '客户留言';

drop table if exists customer_message_reply;

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

alter table customer_message_reply comment '客户留言回复';
