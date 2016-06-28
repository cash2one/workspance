/*==============================================================*/
/* Table: company_customers                                     */
/*==============================================================*/
create table company_customers
(
   id                   int(20) not null,
   company_contacts_id  int(20) default 0 comment '��˾��ϵ��ID',
   company_customers_group_id int(20) default 0 comment '����ID',
   name                 varchar(50) comment '��ϵ��',
   position             varchar(50) comment 'ְ��',
   mobile               varchar(20) comment '�ֻ�',
   email                varchar(50) comment 'Email',
   sex                  char(1) default '0' comment '�Ա�',
   tel_country_code     varchar(10) comment '�绰-����',
   tel_area_code        varchar(10) comment '�绰-����',
   tel                  varchar(10) comment '�绰-����',
   fax_country_code     varchar(10) comment '����-����',
   fax_area_code        varchar(10) comment '����-����',
   fax                  varchar(10) comment '����-����',
   company              varchar(100) comment '��˾����',
   country_code         varchar(50) comment '����/����',
   area_code            varchar(50) comment 'ʡ��',
   address              varchar(254) comment '��ַ',
   post_code            varchar(10) comment '�ʱ�',
   rank                 tinyint default 0 comment '�ͻ��ȼ�',
   status               tinyint default 0 comment '��˾״̬',
   remark               varchar(254) comment '��ע',
   company_id           int(20),
   account              varchar(50),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table company_customers comment '�ҵĿͻ���Ϣ';

/*==============================================================*/
/* Table: company_customers_group                               */
/*==============================================================*/
create table company_customers_group
(
   id                   int(20) not null auto_increment,
   name                 varchar(50),
   remark               varchar(254),
   company_id           int(20),
   account              varchar(50),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table company_customers_group comment '�ͻ�����';