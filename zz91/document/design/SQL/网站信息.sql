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

alter table friend_link comment '��������';

/*==============================================================*/
/* Table: recommend                                             */
/*==============================================================*/
create table recommend
(
   id                   int(20) not null auto_increment comment '���',
   company_id           int(20) comment '��ҵ���',
   sender_account       varchar(20) comment '�������˺�',
   sender_name          varchar(50) comment '����������',
   from_email           varchar(100) comment '����������',
   to_email             varchar(100) comment '�ռ�������',
   subject              varchar(200) comment '�ʼ�����',
   content              text comment '�ʼ�����',
   gmt_created          datetime comment '����ʱ��',
   primary key (id)
)
type = MYISAM;

alter table recommend comment '�Ƽ���';

/*==============================================================*/
/* Table: customer_message                                      */
/*==============================================================*/
create table customer_message
(
   id                   int(20) not null auto_increment comment '����',
   company_id           int(11) comment '��˾ID',
   message_user         varchar(96) comment '������',
   email                varchar(200) comment '����',
   topic                varchar(200) comment '��������',
   message_time         datetime comment '����ʱ��',
   check_user           varchar(30) comment '�����',
   is_checked           char(10) comment '���״̬',
   massage_content      text comment '��������',
   is_read              char(1) comment '�Ƿ��Ѷ�',
   is_reply             char(1) comment '�Ƿ�ظ�',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table customer_message comment '����';

/*==============================================================*/
/* Table: customer_message_reply                                */
/*==============================================================*/
create table customer_message_reply
(
   id                   int(20) not null auto_increment comment '����',
   company_id           int(11) comment '��˾ID',
   customer_message_id  int(20) comment '�ͻ�����ID',
   reply_content        text comment '�ظ�����',
   reply_time           datetime comment '�ظ�����',
   reply_type           char(1) comment '����ʶ��',
   reply_user           varchar(30) comment '�ظ���',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table customer_message_reply comment '�ظ�';

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
