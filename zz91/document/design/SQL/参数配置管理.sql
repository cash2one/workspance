/*==============================================================*/
/* Table: param_type                                            */
/*==============================================================*/
create table param_type
(
   `key`                  int not null comment 'key',
   name                 varchar(50) comment '参数名称',
   gmt_created          datetime comment 'createDate',
   primary key (`key`)
)
type = MYISAM;


/*==============================================================*/
/* Table: param                                                 */
/*==============================================================*/
create table param
(
   id                   bigint(20) not null auto_increment,
   name                 varchar(20) not null comment '参数名称',
   types                varchar(20) comment '类别',
   `key`                  varchar(20) comment 'key',
   value                varchar(255) comment 'value',
   sort                 int(4) comment '排序',
   isuse                tinyint,
   gmt_created          datetime,
   primary key (id)
)
type = MYISAM;

