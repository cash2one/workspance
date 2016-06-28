/*==============================================================*/
/* Table: news_category                                         */
/*==============================================================*/
create table news_category
(
   id                   int(20) not null auto_increment comment '�Զ����',
   parent_id            int(20) comment '���ڵ�(0�����ڵ�)',
   name                 varchar(100) comment '�������',
   go_url               varchar(100) comment '���ӵ�ַ',
   show_index           int(4) comment '����',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   is_delete            tinyint(1) comment '�Ƿ�ɾ��(0�� 1��)',
   primary key (id)
)
type = MYISAM;


/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
create table news
(
   id                   int(20) not null comment 'id',
   title                varchar(100) comment '��Ѷ����',
   index_title          varchar(100) comment '��ҳ����',
   base_type_id         int(20) comment '�������(������������)',
   type_id              int(20) comment '�����(������������)',
   assist_type_id       int(20) comment '�������',
   go_url               varchar(100) comment '��ת����',
   content              text comment '��Ѷ����',
   seo_details          varchar(350) comment 'SEO���ݽ���',
   gmt_order            datetime comment '����ʱ��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   is_checked           char(1) comment '������',
   is_issue             char(1) comment '�Ƿ�ֱ�ӷ���',
   tags                 varchar(300) comment '��ǩ',
   font_color           varchar(50) comment '������ɫ(0 �� 1�� 2�� 3�� 4��)',
   font_size            varchar(50) comment '�����ֺ�',
   is_blod              char(1) comment '�Ƿ�Ӵ�',
   click_number         int(20) comment '�����',
   real_click_number    int(20) comment '�����',
   is_today_review      char(1) comment '�Ƿ��Ƽ�Ϊ���յ���',
   primary key (id)
)
type = MYISAM;

/*==============================================================*/
/* Table: upload_files                                          */
/*==============================================================*/
create table upload_files
(
   id                   int(20) not null auto_increment comment '���',
   file_name            varchar(50) comment '�ļ���',
   file_type            int(4) comment '����(0,δ֪;1,ͼƬ;2,Flash;3,txt;...)',
   file_extension       varchar(10) comment '��չ��',
   file_path            varchar(200) comment '�ļ�·��',
   table_id             int(20) comment '��������',
   table_name           varchar(20) comment '����������',
   gmt_created          datetime comment '�ϴ�ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;
