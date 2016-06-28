/*==============================================================*/
/* Table: tags_count                                            */
/*==============================================================*/
create table tags_count
(
   id                   int(20) not null auto_increment,
   hits                 int(20) default 0 comment '点击数',
   tags_id              int(20) comment '关联的标签id',
   ip                   varchar(15),
   account              varchar(50) comment '如果已登录，则记录登录者的帐号，否则为空',
   company_id           int(20) comment '如果已登录，记录该登录者的公司ID，否则为0',
   gmt_created          datetime,
   primary key (id)
)
type = MYISAM;

alter table tags_count comment '标签点击统计，每次一条记录';