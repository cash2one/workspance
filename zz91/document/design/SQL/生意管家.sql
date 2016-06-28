/*==============================================================*/
/* Table: subscribe                                             */
/*==============================================================*/
create table subscribe
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(200),
   keywords             varchar(200) comment '���ƹؼ��֣����ƹ���ʹ��',
   is_search_by_area    char(1) comment '�Ƿ񰴵���ɸѡ�����ƹ���ʹ��',
   area_code            varchar(200) comment '���ƹ���ʹ��',
   products_type_code   varchar(200) comment '��������,����category��,�����ƹ���ʹ��',
   price_code           varchar(200) comment '���Ʊ���ʹ��,����category��',
   is_send_by_email     char(1) comment '�Ƿ��ʼ�����',
   subscribe_type       char(1) comment '0:����,1:����',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table subscribe comment '������Ϣ����������ͱ���,����ͨ���ؼ��������ƣ�����ͨ�����������';

/*==============================================================*/
/* Table: company_customers                                     */
/*==============================================================*/
create table company_customers
(
   id                   int(20) not null auto_increment,
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


/*==============================================================*/
/* Table: inquiry                                               */
/*==============================================================*/
create table inquiry
(
   id                   int(20) not null,
   title                varchar(200) comment 'ѯ�̱���',
   content              text comment 'ѯ������',
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

/*==============================================================*/
/* Table: inquiry_group                                         */
/*==============================================================*/
create table inquiry_group
(
   id                   int(20) not null auto_increment,
   name                 varchar(50),
   gmt_created          datetime,
   gmt_modified         datetime,
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

/*==============================================================*/
/* Table: esite_domain                                          */
/*==============================================================*/
create table esite_domain
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50),
   name                 varchar(200) comment '����',
   ip                   varchar(15),
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table esite_domain comment '��������';

/*==============================================================*/
/* Table: esite_news                                            */
/*==============================================================*/
create table esite_news
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50),
   title                varchar(200) comment '����',
   content              text comment '����',
   post_time            datetime comment '����ʱ��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table esite_news comment '��˾��̬';

/*==============================================================*/
/* Table: esite_friend_link                                     */
/*==============================================================*/
create table esite_friend_link
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50),
   link_name            varchar(200) comment '����',
   logo_url             varchar(254),
   link_address         varchar(254) comment '���ӵ�ַ',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table esite_friend_link comment '������������';

drop table if exists credit_company;

/*==============================================================*/
/* Table: credit_company                                        */
/*==============================================================*/
create table credit_company
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   name                 varchar(96) comment '����',
   credit_name          varchar(254) comment '���ڹ�˾',
   address              varchar(254) comment '��ϵ��ַ',
   tel                  varchar(96) comment '�绰',
   fax                  varchar(96) comment '����',
   email                varchar(48) comment '����',
   details              varchar(1000) comment '��ϸ��Ϣ',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table credit_company comment '���Ųο���';

drop table if exists credit_file;

/*==============================================================*/
/* Table: credit_file                                           */
/*==============================================================*/
create table credit_file
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   category_code        varchar(200) comment '֤������',
   file_name            varchar(254) comment '֤������',
   start_time           datetime comment '֤����Чʱ��',
   end_time             datetime comment '֤����Ч�ڽ�ֹ',
   file_number          varchar(96) comment '֤����',
   organization         varchar(254) comment '��֤����',
   tel                  varchar(96) comment '��֤������ϵ�绰',
   website              varchar(200) comment '��֤������ַ',
   pic_name             varchar(254) comment '֤��ͼƬ',
   introduction         varchar(1000) comment '֤�����',
   check_status         char(1) default '0' comment '0:�����;1:�����;2:���δͨ��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table credit_file comment '�ҵ�֤��';

drop table if exists credit_customer_vote;

/*==============================================================*/
/* Table: credit_customer_vote                                  */
/*==============================================================*/
create table credit_customer_vote
(
   id                   int(20) not null auto_increment,
   to_company_id        int(20) comment '�����۹�˾ID',
   to_name              varchar(96) comment '��������',
   status               char(1) default '0' comment '0:����;1:����;2:����',
   content              text comment '��������',
   check_status         char(1) default '0' comment '0:�����;1:�����;2:���δͨ��',
   from_company_id      int(20) comment '�����߹�˾ID',
   from_account         varchar(50) comment '�������˺���',
   from_name            varchar(96) comment '������',
   from_email           varchar(48) comment '����������',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table credit_customer_vote comment '�ͻ�����';

drop table if exists credit_customer_vote_reply;

/*==============================================================*/
/* Table: credit_customer_vote_reply                            */
/*==============================================================*/
create table credit_customer_vote_reply
(
   id                   int(20) not null,
   credit_customer_vote_id int(20) comment '�ͻ�����',
   reply_content        text comment '�ظ�����',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table credit_customer_vote_reply comment '���ۻظ�';

