
/*==============================================================*/
/* Table: inquiry                                               */
/*==============================================================*/
create table inquiry
(
   id                   int(20) not null,
   title                varchar(200) comment 'ѯ�̱���',
   content              text comment 'ѯ������',
   group_id             int(20) default 0,
   be_inquired_type     char(1) default '0' comment 'ѯ�̶�������� 0:������Ϣ 1:��˾ 2: ѯ��',
   be_inquired_id       int(20) comment 'ѯ�̶����ID��',
   inquired_type        char(1) default '0',
   inquired_id          int(20),
   sender_id            int(20) comment '������Id',
   receiver_id          int(20) comment '������Id',
   batch_send_type      char(1) default '0' comment 'Ⱥ����־',
   export_status        char(1) default '0' comment '��������״̬��־',
   export_person        varchar(50) comment '������,�������ĵ�¼�˺�',
   is_rubbish           char(1) default '0' comment '�Ƿ�Ϊ��������(0:��,1:��)',
   is_viewed            char(1) default '0' comment '�Ƿ�鿴��־',
   is_del               char(1) default '0' comment 'ɾ����־',
   send_time            datetime comment '����ʱ��',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   account              varchar(50),
   primary key (id)
)
type = MYISAM;

alter table inquiry comment 'ѯ����Ϣ';

/*==============================================================*/
/* Table: inquiry_group                                         */
/*==============================================================*/
create table inquiry_group
(
   id                   int(20) not null auto_increment,
   name                 varchar(50),
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: inquiry_sensitive                                     */
/*==============================================================*/
create table inquiry_sensitive
(
   keywords             text comment '�����ַ�'
)
type = MYISAM;

alter table inquiry_sensitive comment '�����ַ���';