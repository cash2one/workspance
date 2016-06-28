/*==============================================================*/
/* Table: member_apply                                          */
/*==============================================================*/
create table member_apply
(
   id                   int(20) not null auto_increment comment '编号',
   username             varchar(50) comment '用户名',
   membership_code      varchar(200) comment '申请类型',
   tel                  varchar(50) comment '固定电话',
   mobile               varchar(50) comment '移动电话',
   email                varchar(50) comment '邮箱地址',
   process_status       char(1) comment '处理状态',
   process_person       varchar(50) comment '处理人',
   gmt_created          datetime comment '申请时间',
   gmt_modified         datetime comment '处理时间',
   remark               varchar(254) comment '备注',
   company_contacts_id  int(20),
   primary key (id)
)
type = MYISAM;

alter table member_apply comment '会员申请';

/*==============================================================*/
/* Table: category_garden                                       */
/*==============================================================*/
create table category_garden
(
   id                   int(20) not null auto_increment,
   name                 varchar(200),
   shorter_name         varchar(200),
   industry_code        varchar(200),
   area_code            varchar(200),
   garden_type_code     varchar(200),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: company                                               */
/*==============================================================*/
create table company
(
   id                   int(20) not null auto_increment,
   name                 varchar(96),
   business             varchar(2000),
   level_code           varchar(200),
   service_code         varchar(200),
   membership_code      varchar(200),
   classified_code      varchar(200),
   area_code            varchar(200),
   industry_code        varchar(200),
   category_garden_id   int(20),
   regfrom_code         varchar(200),
   introduction         varchar(5000),
   regtime              datetime,
   comp_info_backup     text,
   is_main              char(1),
   is_block             char(1),
   foreignCity          varchar(50) comment '地区选外国时的对应城市',
   business_type        char(1) comment '主营方向,0:两者,1:销售,2:采购',
   buy_details          varchar(200) comment '当主营方向为采购时填的详细内容',
   sale_details         varchar(200) comment '当主营方向为销售时填的详细内容',
   gmt_created          datetime,
   gmt_modified         datetime,
   old_id               int(20),
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: company_access_grade                                  */
/*==============================================================*/
create table company_access_grade
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   login_id             int(20),
   reason               varchar(200),
   gmt_created          char(10),
   gmt_modified         char(10),
   access_grade_code    char(10),
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: company_membership                                    */
/*==============================================================*/
create table company_membership
(
   id                   int(20) not null,
   company_id           int(20),
   membership_type_code varchar(200),
   date_from            datetime,
   date_end             datetime,
   审核                   char(1),
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: company_contacts                                      */
/*==============================================================*/
create table company_contacts
(
   id                   int(20) not null,
   account              varchar(50),
   company_id           int(20),
   default_contact      char(1),
   tel_country_code     varchar(10),
   tel_area_code        varchar(20),
   tel                  varchar(50),
   mobile               varchar(50),
   fax_country_code     varchar(10),
   fax_area_code        varchar(20),
   fax                  varchar(50),
   email                varchar(50),
   website              varchar(100),
   contact              varchar(100),
   sex                  char(1),
   position             varchar(50),
   address              varchar(200),
   zip                  varchar(30),
   qq                   varchar(20),
   msn                  varchar(50),
   hidden_contacts      char(1),
   gmt_created          datetime,
   gmt_modified         datetime,
   info_source_code     varchar(200),
   back_email           varchar(50),
   is_use_back_email    char(1) default '0',
   is_show_email        char(1) default '0',
   password             varchar(50),
   primary key (id)
)
type = MYISAM;