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
   stat_date            datetime comment 'ͳ������',
   stat_type            char(1) comment 'ͳ�����ͣ�
            0�����ۣ�1�����',
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: stat_common_details                                   */
/*==============================================================*/
create table stat_common_details
(
   id                   int(20) not null auto_increment,
   ip                   varchar(15) comment '������IP',
   stat_id              int(20) comment '��ͳ�����ID',
   is_handled           char(1) comment '�Ƿ��Ѵ���',
   stat_type            char(1) comment 'ͳ�����ͣ�
            0�����ۣ�1�����',
   stat_time            datetime comment '���ʱ��',
   primary key (id)
)
type = MYISAM;

alter table stat_common_details comment 'ͨ�����ݷ�����ϸ��������¼ֻ��Ҫͳ�Ʒ������Ĺ���';

/*==============================================================*/
/* Table: stat_common_total                                     */
/*==============================================================*/
create table stat_common_total
(
   id                   int(20) not null auto_increment,
   stat_id              int(20),
   hits                 int(20),
   stat_date            datetime,
   stat_type            char(1) comment 'ͳ�����ͣ�
            0�����ۣ�1�����',
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
   redirect_url         varchar(500) comment '��¼����ת��ҳ��',
   is_handled           char(1) comment '�Ƿ��Ѵ���ͳ�ƽ��ܼ�',
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
   login_count          int(20) comment '��Ա��¼��',
   product_view_count   int(20) comment '�鿴���Ĺ�����Ϣ����',
   product_viewed_count int(20) comment '���鿴������',
   contact_view_count   int(20) comment '��ϵ��ʽ�鿴����',
   contact_viewed_count int(20) comment '��ϵ��ʽ���鿴����',
   message_send_count   int(20) comment '����������',
   message_receive_count int(20) comment '����������',
   stat_date            datetime comment 'ͳ��ʱ��',
   primary key (id)
)
type = MYISAM;

alter table stat_member_total comment '��Ա��������ܼ�:1.�鿴�����ܼ�2.��Ա��¼�ܼ�3.�������ܼ�';

/*==============================================================*/
/* Table: stat_view_day_report                                  */
/*==============================================================*/
create table stat_view_day_report
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(50),
   view_count           int(20) comment '�鿴����',
   viewed_count         int(20) comment '���鿴����',
   stat_type            char(1) comment '�鿴���ͣ�0����ϵ��ʽ��1��������Ϣ',
   stat_date            datetime,
   primary key (id)
)
type = MYISAM;

alter table stat_view_day_report comment '�鿴����/��ϵ�ձ���';

/*==============================================================*/
/* Table: stat_view_details                                     */
/*==============================================================*/
create table stat_view_details
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(50),
   product_id           int(20),
   to_company_id        int(20) comment '���鿴�Ĺ�˾',
   to_account           varchar(50) comment '���鿴ֻ�Թ�˾��Ч�����Ը��ֶ���ʱû��',
   stat_type            char(1) comment '�鿴���ͣ�0����ϵ��ʽ��1��������Ϣ',
   is_handled           char(1) comment '�Ƿ��Ѵ���ͳ�����ܼ�',
   stat_time            datetime,
   primary key (id)
)
type = MYISAM;

alter table stat_view_details comment '�鿴/���鿴��ϸ�������鿴�������ϵ��ʽ';
