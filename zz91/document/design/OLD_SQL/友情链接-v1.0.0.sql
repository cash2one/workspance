drop table if exists friend_link;

/*==============================================================*/
/* Table: friend_link                                            */
/*==============================================================*/
create table friend_link
(
   id                   int not null auto_increment comment '主键',
   link_name            varchar(200) comment '网站名称',
   link_category_code   varchar(50) comment '类别',
   height               varchar(10) comment '长度',
   width                varchar(10) comment '宽度',
   pic_address          varchar(254) comment '图片',
   link                 varchar(254) comment '链接地址',
   show_index           int(4) comment '排序',
   add_time             datetime comment '添加时间',
   text_color           varchar(20) comment '图片颜色',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table friend_link comment '友情链接';