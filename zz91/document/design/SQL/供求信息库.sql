
/*==============================================================*/
/* Table: products_series_contacts                              */
/*==============================================================*/
create table products_series_contacts
(
   id                   int(20) not null,
   products_id          int(20) comment '��������ID',
   products_series_id   int(20) comment '��������ϵ��ID',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table products_series_contacts comment '����ϵ����ϵ';


/*==============================================================*/
/* Table: products_series                                       */
/*==============================================================*/
create table products_series
(
   id                   int(20) not null,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�ʺ���',
   name                 varchar(200) comment 'ϵ������',
   series_details       text comment 'ϵ������',
   series_order         int(4) comment 'ϵ������',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   key AK_id (id)
)
type = MYISAM;

alter table products_series comment '����ϵ��';

/*==============================================================*/
/* Table: products                                              */
/*==============================================================*/
create table products
(
   id                   int(20) not null,
   company_id           int(20) comment '������˾ID',
   account              varchar(50) comment '�����ʺ���',
   products_type_code   varchar(200) comment '�������',
   source_type_code     varchar(200) comment '��Ϣ��Դ',
   title                varchar(200) comment '����',
   details              text comment '��ϸ����',
   location             varchar(100) comment '�������ڵ�',
   provide_status       char(1) comment '0:����,1:������',
   total_quantity       varchar(50) comment '��������',
   is_show_in_price     char(1) default '0' comment '�Ƿ���ʾ����ҵ����',
   price_unit           varchar(10) comment 'Ԫ,��Ԫ',
   price                float comment '�۸�',
   quantity_unit        varchar(10) comment '������λ,��,�����',
   quantity             float comment '����',
   source               varchar(200) comment '��Դ��',
   specification        varchar(100) comment '��Ʒ���',
   origin               varchar(100) comment '��Դ��Ʒ',
   impurity             varchar(20) comment '���ʺ���',
   color                varchar(10) comment '��ɫ',
   useful               varchar(10) comment '��;',
   appearance           varchar(50) comment '���',
   manufacture          varchar(100) comment '�ӹ�˵��',
   remark               text comment 'ֻ�ں�̨��ʾ,���ͷ���¼һЩ��ע��Ϣ',
   post_type            char(1) default '0' comment 'ǰ̨���Ǻ�̨����',
   is_pause             char(1) default '0' comment '�Ƿ���ͣ����',
   is_post_when_view_limit char(1) default '0' comment '�Ƿ�Ϊ�鿴����ʱ�ŷ����Ĳ�Ʒ',
   is_post_from_inquiry char(1) default '0' comment '�Ƿ�Ϊѯ�̵����Ĺ���',
   is_del               char(1) default '0' comment '�Ƿ�ɾ��',
   category_products_main_code varchar(200) comment '���������code',
   category_products_assist_code varchar(200) comment '���������',
   check_person         varchar(100) comment '�����',
   check_status         char(1) default '0' comment '0:δ���,1:�����,2:���δͨ���˻�',
   unchecked_check_status char(1) default '0' comment '0:δ���,1:�����',
   unpass_reason        varchar(500) comment 'δͨ��ԭ��',
   check_time           datetime comment '���ʱ��',
   real_time            datetime comment '��ʵ����ʱ��',
   refresh_time         datetime comment 'ˢ��ʱ��',
   expire_time          datetime comment '����ʱ��,�����ˢ��ʱ����˵',
   gmt_created          datetime,
   gmt_modified         datetime,
   min_price            float comment '�۸�����',
   max_price            float comment '�۸�����',
   goods_type_code      varchar(200) comment '�������ͣ��ڻ����ֻ�����Ӧcode(1036)',
   primary key (id),
   key AK_Key_1 (id)
)
type = MYISAM;

alter table products comment '������Ϣ';

/*==============================================================*/
/* Table: products_keywords_rank                                */
/*==============================================================*/
create table products_keywords_rank
(
   id                   int(20) not null auto_increment,
   product_id           int(20),
   name                 varchar(200) not null,
   start_time           datetime,
   end_time             datetime,
   is_checked           char(1),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_rare_match                                   */
/*==============================================================*/
create table products_rare_match
(
   id                   int(20) not null auto_increment,
   email                varchar(100) comment 'ƥ������',
   source               varchar(500) comment '��Դ',
   description          varchar(500) comment '��������',
   is_viewed            char(1) comment '�Ƿ�鿴',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_rare                                         */
/*==============================================================*/
create table products_rare
(
   id                   int(20) not null auto_increment,
   product_id           int(20) comment '����Id',
   admin_user           varchar(50) comment '�ύ��',
   old_or_new           varchar(200) comment '���ϻ�����',
   location_require     varchar(200) comment '����Ҫ��',
   around_products      varchar(200) comment '�ܱ߲�Ʒ',
   remark               varchar(500) comment '������ע',
   servicer_advice      varchar(500) comment '�������',
   business_advice      varchar(500) comment '���������',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: products_albums                                       */
/*==============================================================*/
create table products_albums
(
   id                   int(20) not null auto_increment,
   parent_id            int(20) comment '�����ID',
   name                 varchar(200) comment '�������',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table products_albums comment '��Ʒ���';

/*==============================================================*/
/* Table: products_pic                                          */
/*==============================================================*/
create table products_pic
(
   id                   int(20) not null auto_increment,
   name                 varchar(200) comment 'ͼƬ����',
   product_id           int(20),
   pic_address          varchar(200) comment 'ͼƬ��ַ',
   album_id             int(20),
   is_default           char(1) default '0' comment '�Ƿ�Ϊ�ͻ�Ĭ����ʾ��ͼƬ',
   is_cover             char(1) default '0' comment '�Ƿ�Ϊ���ķ���ͼƬ',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_relate_append                                */
/*==============================================================*/
create table products_relate_append
(
   id                   int(20) not null,
   product_id           int(20),
   category_products_append_id int(20),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: category_products_append                              */
/*==============================================================*/
create table category_products_append
(
   id                   int(20) not null auto_increment,
   parent_id            int(20),
   label              varchar(200) comment '�����',
   is_del               char(1) comment 'ɾ����־',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_relate_category                              */
/*==============================================================*/
create table products_relate_category
(
   id                   int(20) not null,
   product_id           int(20),
   product_category_code varchar(200),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: category_products                                     */
/*==============================================================*/
create table category_products
(
   id                   int(20) not null auto_increment,
   code                 varchar(200) comment '���Code,ÿ4λһ��',
   label              varchar(200) comment '�����',
   is_assist            char(1) comment '�Ƿ�Ϊ�������',
   is_del               char(1) comment 'ɾ����־',
   gmt_created          datetime,
   gmt_modified         datetime,
   old_id               int(20),
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: products_search_associate_keywords                    */
/*==============================================================*/
create table products_search_associate_keywords
(
   id                   int(20) not null,
   category_products_code varchar(200),
   keyword              varchar(500) comment '�����ؼ���',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: description_template                                  */
/*==============================================================*/
create table description_template
(
   id                   int(20) not null,
   template_code        varchar(200),
   content              varchar(500),
   gmt_created          datetime,
   gmt_modified         datetime,
   key AK_Key_1 (id)
)
type = MYISAM;

drop table if exists products_recommend;

/*==============================================================*/
/* Table: products_recommend                                    */
/*==============================================================*/
create table products_recommend
(
   id                   int(20) not null auto_increment,
   product_id           int(20) comment '����ID',
   title                varchar(200) comment '����',
   content              text comment '����',
   send_name            varchar(100) comment '����������',
   send_email           varchar(50) comment '������Email',
   receive_email        varchar(50) comment '�ռ���Email',
   real_time            datetime comment '����ʱ��',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table products_recommend comment '�����Ƽ�';