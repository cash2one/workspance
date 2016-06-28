drop table if exists friend_link;

/*==============================================================*/
/* Table: friend_link                                            */
/*==============================================================*/
create table friend_link
(
   id                   int not null auto_increment comment '����',
   link_name            varchar(200) comment '��վ����',
   link_category_code   varchar(50) comment '���',
   height               varchar(10) comment '����',
   width                varchar(10) comment '���',
   pic_address          varchar(254) comment 'ͼƬ',
   link                 varchar(254) comment '���ӵ�ַ',
   show_index           int(4) comment '����',
   add_time             datetime comment '���ʱ��',
   text_color           varchar(20) comment 'ͼƬ��ɫ',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table friend_link comment '��������';