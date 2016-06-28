drop table if exists customer_message;

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

alter table customer_message comment '�ͻ�����';

drop table if exists customer_message_reply;

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

alter table customer_message_reply comment '�ͻ����Իظ�';
