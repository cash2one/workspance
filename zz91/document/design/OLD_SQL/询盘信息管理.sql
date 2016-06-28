
/*==============================================================*/
/* Table: inquiry                                               */
/*==============================================================*/
create table inquiry
(
   id                   int(20) not null,
   title                varchar(200) comment '询盘标题',
   content              text comment '询盘内容',
   group_id             int(20) default 0,
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

alter table inquiry comment '询盘信息';

/*==============================================================*/
/* Table: inquiry_group                                         */
/*==============================================================*/
create table inquiry_group
(
   id                   int(20) not null auto_increment,
   name                 varchar(50),
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

alter table inquiry_sensitive comment '敏感字符表';