/*==============================================================*/
/* Table: tags_relate_article                                   */
/*==============================================================*/
create table tags_relate_article
(
   id                   int(20) not null auto_increment,
   tags_info_id         int(20) comment '��Ӧ��ǩ��id',
   article_id           int(20) comment '��Ӧ����Ϣid,��Ҫ���ݶ�Ӧ��������ۺϿ���',
   tags_article_category_code varchar(200) comment '��Ӧ��Ϣ������,�����ţ�����...',
   is_admin             char(1) default '0' comment '�Ƿ�Ϊ��̨����',
   tags_category_products_code varchar(200) comment '���Ϊ�����ǩʱ�Ĺ������,��Ͻ���,������...',
   primary key (id)
)
type = MYISAM;

alter table tags_relate_article comment '��ǩ�����¹�����';

/*==============================================================*/
/* Table: tags_info                                             */
/*==============================================================*/
create table tags_info
(
   id                   int(20) not null auto_increment,
   name                 varchar(200),
   type_id              char(1) comment '0��Ĭ�ϣ�1��������ǩ��2����ɫ��ǩ',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table tags_info comment '��ǩ��Ϣ��';

drop table if exists tags_most_or_least;

/*==============================================================*/
/* Table: tags_most_or_least                                    */
/*==============================================================*/
create table tags_most_or_least
(
   id                   int(20) not null auto_increment,
   tags_info_id         int(20),
   tags_category_products_code varchar(200),
   article_count        int(20),
   stat_type            char(1) comment '0�����ȱ�ǩ��1��ϡȱ��ǩ',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table tags_most_or_least comment '���ű�ǩ��ϡȱ��ǩ';
