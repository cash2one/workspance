/*==============================================================*/
/* Table: company_customers                                     */
/*==============================================================*/
create table company_customers
(
   id                   int(20) not null,
   company_contacts_id  int(20) default 0 comment '公司联系人ID',
   company_customers_group_id int(20) default 0 comment '分组ID',
   name                 varchar(50) comment '联系人',
   position             varchar(50) comment '职务',
   mobile               varchar(20) comment '手机',
   email                varchar(50) comment 'Email',
   sex                  char(1) default '0' comment '性别',
   tel_country_code     varchar(10) comment '电话-国家',
   tel_area_code        varchar(10) comment '电话-区号',
   tel                  varchar(10) comment '电话-号码',
   fax_country_code     varchar(10) comment '传真-国家',
   fax_area_code        varchar(10) comment '传真-区号',
   fax                  varchar(10) comment '传真-号码',
   company              varchar(100) comment '公司名称',
   country_code         varchar(50) comment '国家/地区',
   area_code            varchar(50) comment '省份',
   address              varchar(254) comment '地址',
   post_code            varchar(10) comment '邮编',
   rank                 tinyint default 0 comment '客户等级',
   status               tinyint default 0 comment '公司状态',
   remark               varchar(254) comment '备注',
   company_id           int(20),
   account              varchar(50),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table company_customers comment '我的客户信息';

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

alter table company_customers_group comment '客户分组';