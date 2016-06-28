/*==============================================================*/
/* Table: subscribe                                             */
/*==============================================================*/
create table subscribe
(
   id                   int(20) not null auto_increment,
   company_id           int(20),
   account              varchar(200),
   keywords             varchar(200) comment '定制关键字，订制供求使用',
   is_search_by_area    char(1) comment '是否按地区筛选，订制供求使用',
   area_code            varchar(200) comment '订制供求使用',
   products_type_code   varchar(200) comment '供求类型,来自category表,，订制供求使用',
   price_code           varchar(200) comment '订制报价使用,来自category表',
   is_send_by_email     char(1) comment '是否邮件提醒',
   subscribe_type       char(1) comment '0:供求,1:报价',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table subscribe comment '定制信息表，包括供求和报价,供求通过关键字来定制，报价通过类别来定制';

/*==============================================================*/
/* Table: company_customers                                     */
/*==============================================================*/
create table company_customers
(
   id                   int(20) not null auto_increment,
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


/*==============================================================*/
/* Table: inquiry                                               */
/*==============================================================*/
create table inquiry
(
   id                   int(20) not null,
   title                varchar(200) comment '询盘标题',
   content              text comment '询盘内容',
   be_inquired_type     char(1) default '0' comment '询盘对象的类型 0:供求信息 1:公司 2: 询盘',
   be_inquired_id       int(20) comment '询盘对象的ID号',
   inquired_type        char(1) default '0',
   inquired_id          int(20),
   sender_id            int(20) comment '发送者Id',
   receiver_id          int(20) comment '接收者Id',
   batch_send_type      char(1) default '0' comment '群发标志',
   export_status        char(1) default '0' comment '导出处理状态标志',
   export_person        varchar(50) comment '处理人,处理导出的登录账号',
   is_rubbish           char(1) default '0' comment '是否为垃圾留言(0:否,1:是)',
   is_viewed            char(1) default '0' comment '是否查看标志',
   is_del               char(1) default '0' comment '删除标志',
   send_time            datetime comment '发送时间',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
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
   keywords             text comment '敏感字符'
)
type = MYISAM;

/*==============================================================*/
/* Table: esite_domain                                          */
/*==============================================================*/
create table esite_domain
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50),
   name                 varchar(200) comment '域名',
   ip                   varchar(15),
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
);

alter table esite_domain comment '商铺域名';

/*==============================================================*/
/* Table: esite_news                                            */
/*==============================================================*/
create table esite_news
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50),
   title                varchar(200) comment '标题',
   content              text comment '内容',
   post_time            datetime comment '发布时间',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
);

alter table esite_news comment '公司动态';

/*==============================================================*/
/* Table: esite_friend_link                                     */
/*==============================================================*/
create table esite_friend_link
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50),
   link_name            varchar(200) comment '名称',
   logo_url             varchar(254),
   link_address         varchar(254) comment '连接地址',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
);

alter table esite_friend_link comment '商铺友情链接';

drop table if exists credit_company;

/*==============================================================*/
/* Table: credit_company                                        */
/*==============================================================*/
create table credit_company
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   name                 varchar(96) comment '姓名',
   credit_name          varchar(254) comment '所在公司',
   address              varchar(254) comment '联系地址',
   tel                  varchar(96) comment '电话',
   fax                  varchar(96) comment '传真',
   email                varchar(48) comment '邮箱',
   details              varchar(1000) comment '详细信息',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table credit_company comment '资信参考人';

drop table if exists credit_file;

/*==============================================================*/
/* Table: credit_file                                           */
/*==============================================================*/
create table credit_file
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   category_code        varchar(200) comment '证书类型',
   file_name            varchar(254) comment '证书名称',
   start_time           datetime comment '证书生效时间',
   end_time             datetime comment '证书有效期截止',
   file_number          varchar(96) comment '证书编号',
   organization         varchar(254) comment '发证机构',
   tel                  varchar(96) comment '发证机构联系电话',
   website              varchar(200) comment '发证机构网址',
   pic_name             varchar(254) comment '证书图片',
   introduction         varchar(1000) comment '证书介绍',
   check_status         char(1) default '0' comment '0:待审核;1:已审核;2:审核未通过',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table credit_file comment '我的证书';

drop table if exists credit_customer_vote;

/*==============================================================*/
/* Table: credit_customer_vote                                  */
/*==============================================================*/
create table credit_customer_vote
(
   id                   int(20) not null auto_increment,
   to_company_id        int(20) comment '被评价公司ID',
   to_name              varchar(96) comment '被评价者',
   status               char(1) default '0' comment '0:好评;1:中评;2:差评',
   content              text comment '评价内容',
   check_status         char(1) default '0' comment '0:待审核;1:已审核;2:审核未通过',
   from_company_id      int(20) comment '评价者公司ID',
   from_account         varchar(50) comment '评价者账号名',
   from_name            varchar(96) comment '评价者',
   from_email           varchar(48) comment '评价者邮箱',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table credit_customer_vote comment '客户评价';

drop table if exists credit_customer_vote_reply;

/*==============================================================*/
/* Table: credit_customer_vote_reply                            */
/*==============================================================*/
create table credit_customer_vote_reply
(
   id                   int(20) not null,
   credit_customer_vote_id int(20) comment '客户评价',
   reply_content        text comment '回复内容',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table credit_customer_vote_reply comment '评价回复';

