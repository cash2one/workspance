/*==============================================================*/
/* Table: tags_count                                            */
/*==============================================================*/
create table tags_count
(
   id                   int(20) not null auto_increment,
   hits                 int(20) default 0 comment '�����',
   tags_id              int(20) comment '�����ı�ǩid',
   ip                   varchar(15),
   account              varchar(50) comment '����ѵ�¼�����¼��¼�ߵ��ʺţ�����Ϊ��',
   company_id           int(20) comment '����ѵ�¼����¼�õ�¼�ߵĹ�˾ID������Ϊ0',
   gmt_created          datetime,
   primary key (id)
)
type = MYISAM;

alter table tags_count comment '��ǩ���ͳ�ƣ�ÿ��һ����¼';