/*==============================================================*/
/* Table: category_company_price                                */
/*==============================================================*/
create table category_company_price
(
   id                   int(20) not null,
   label              varchar(50),
   code                 varchar(200),
   is_del               char(1) default '0' comment '是否删除 0:否,1:是',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   key AK_Key_1 (id)
)
type = MYISAM;

alter table category_company_price comment '企业报价类别';

/*==============================================================*/
/* Table: company_price                                         */
/*==============================================================*/
create table company_price
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '发布公司ID',
   account              varchar(50) comment '发布账号',
   product_id           int(20) comment '对应的供求ID',
   title                varchar(200) comment '报价标题',
   category_company_price_code varchar(200),
   price                float comment '价格',
   price_unit           varchar(50) comment '价格单位,如元/吨',
   min_price            float comment '价格下限',
   max_price            float comment '价格上限',
   area_code            varchar(200) comment '地区',
   details              varchar(500) comment '详细信息',
   is_checked           char(1) default '0' comment '是否审核',
   post_time            datetime comment '发布时间',
   check_time           datetime comment '审核时间',
   expired_time         datetime comment '过期时间,通过计算得出，与供求类似',
   refresh_time         datetime,
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table company_price comment '企业报价';