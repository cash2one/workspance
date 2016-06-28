
/*==============================================================*/
/* Table: servicer_ads                                          */
/*==============================================================*/
create table servicer_ads
(
   id                   int(20) not null auto_increment comment '���',
   ads_type_code        varchar(200) comment '�������',
   custom_key           varchar(100) comment '�ͻ��ؼ���',
   custom_name          varchar(200) comment '�û���',
   company_name         varchar(300) comment '��˾��',
   title                varchar(100) comment '��Ϣ����(����/չ��)',
   main_business        varchar(50) comment '��Ӫҵ��',
   contact              varchar(20) comment '��ϵ��ʽ',
   contact_person       varchar(200) comment '��ϵ��',
   url                  varchar(200) comment '���в���ַ',
   ads_time             varchar(50) comment '���ʱ��',
   instruction          varchar(1000) comment '�ض�˵��',
   saler                varchar(50) comment '������',
   checked              char(1) comment '���(0�����  1������  2�Ѵ���)',
   servicer             varchar(50) comment '�ͷ���Ա',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: servicer_ads_reply                                    */
/*==============================================================*/
create table servicer_ads_reply
(
   id                   int(20) not null auto_increment comment '���',
   servicer_ads_id      int(20) comment '�ظ���¼���',
   content              varchar(1000) comment '�ظ�����',
   reply_user           varchar(50) comment '�ظ���',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: ads                                                   */
/*==============================================================*/
create table ads
(
   id                   int(20) not null auto_increment comment '���',
   ads_place_id         int(20) comment '���λ���',
   ads_group_id         int(20) comment '�������',
   show_index           int(4) comment '����ֵ(����С��������)',
   name                 varchar(200) comment '�������',
   is_custom            char(1) comment '�Ƿ�ͻ����(0�� 1��)',
   custom_email         varchar(200) comment '�ͻ���������',
   ads_type_code        varchar(200) comment '�������(��̬ͼƬ/��̬ͼƬgif/Flash)',
   height               int(4) comment '���߶�',
   width                int(4) comment '�����',
   url                  varchar(100) comment '���ӵ�ַ',
   alt_text             varchar(100) comment '��ʾ����',
   remark               text comment '��ע��',
   hits                 int(20) comment '��ʼ�������(0)',
   is_showed            char(1) comment '�Ƿ���ʾ(0�� 1��)',
   custom_id            int(20) comment '�ͻ����(0����ǿͻ����)',
   litmit_number        int(20),
   gmt_start_time       datetime comment 'Ͷ�ſ�ʼʱ��',
   gmt_end_time         datetime comment 'Ͷ�Ž���ʱ��',
   real_hits            int(20) comment '��ʵ�������(0)',
   is_checked           char(1) comment '���(0�� 1��)',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   pic_address          varchar(254),
   primary key (id)
)
type = MYISAM;

alter table ads comment '���';

/*==============================================================*/
/* Table: ads_places                                            */
/*==============================================================*/
create table ads_places
(
   id                   int not null auto_increment comment '���',
   parent_id            int(20) comment '���ڵ�(��Ϊ���ࣻ������Ϊ����)',
   name                 varchar(200) comment '����',
   width                int(4) comment '���',
   height               int(4) comment '�߶�',
   price                float comment '�۸�',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   is_delete            char(1) comment '�Ƿ�ɾ��(0����1���ǡ�)',
   primary key (id)
)
type = MYISAM;

alter table ads_places comment '���λ';

/*==============================================================*/
/* Table: ads_groups                                            */
/*==============================================================*/
create table ads_groups
(
   id                   int(20) not null auto_increment comment '���',
   parent_id            int(20) comment '���ڵ�(��Ϊ���ࣻ������Ϊ����)',
   group_name           varchar(200) comment '����',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   is_deleted           char(1) comment '�Ƿ�ɾ��(0����1���ǡ�)',
   primary key (id)
)
type = MYISAM;

alter table ads_groups comment '�����';
