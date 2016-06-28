drop table if exists bbs_post;

/*==============================================================*/
/* Table: bbs_post                                              */
/*==============================================================*/
create table bbs_post
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�ʺ���',
   bbs_user_profiler_id int(20) comment '���˻�����ϢID',
   bbs_post_category_id int(20) comment 'ģ��ID',
   bbs_title_style_id   int(20) comment '������ʽID',
   bbs_topics_id        int(20) comment '��������',
   title                varchar(200) comment '����',
   content              text comment '����',
   front_category_id    int(20) comment 'ǰ̨����',
   is_show              char(1) default '1' comment '0:��,1:��',
   is_del               char(1) default '0' comment '0:��,1:��',
   check_person         varchar(100) comment '�����',
   check_status         char(1) default '0' comment '0:����,1:�Ѷ�,2:�˻�',
   check_time           datetime comment '���ʱ��',
   unchecked_check_status char(1) default '0' comment '0:δ���,1:�����',
   unpass_reason        varchar(500) comment 'δͨ��ԭ��',
   is_hot_post          char(1) default '0' comment '0:��;1:��',
   post_type            char(1) default '1' comment '0:����,1:����;2:ţ��;3:ͷ��;4:���¶�̬;5:���Ż���',
   visited_count        int(10) default 0 comment '��������',
   reply_count          int(10) default 0 comment '�ظ���',
   post_time            datetime comment '����ʱ��',
   reply_time           datetime comment '���ظ�ʱ��',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post comment '����';

drop table if exists bbs_post_reply;

/*==============================================================*/
/* Table: bbs_post_reply                                        */
/*==============================================================*/
create table bbs_post_reply
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�ʺ���',
   bbs_post_id          int(20) comment '����ID',
   title                varchar(254) comment '����',
   content              text comment '����',
   is_del               char(1) default '0' comment '0:��,1:��',
   check_person         varchar(100) comment '�����',
   check_status         char(1) default '0' comment '0:����,1:�Ѷ�,2:�˻�',
   check_time           datetime comment '���ʱ��',
   unpass_reason        text comment 'δͨ��ԭ��',
   unchecked_check_status char(1) default '0' comment '0:δ���,1:�����',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_reply comment '�ظ���';

drop table if exists bbs_post_category;

/*==============================================================*/
/* Table: bbs_post_category                                     */
/*==============================================================*/
create table bbs_post_category
(
   id                   int(20) not null auto_increment,
   parent_id            int(20) comment '��ID',
   l                    int comment '��ֵ',
   r                    int comment '��ֵ',
   is_del               char(1) default '0' comment '0:��;1:��',
   name                 varchar(20) comment 'ģ����',
   remark               text comment '��ע',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_category comment '���';

drop table if exists bbs_post_browse_statistics;

/*==============================================================*/
/* Table: bbs_post_browse_statistics                            */
/*==============================================================*/
create table bbs_post_browse_statistics
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�ʻ���',
   bbs_post_id          int(20) comment '����ID',
   browse_time          datetime comment '���ʱ��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_browse_statistics comment '�������ͳ�Ʊ�';

drop table if exists bbs_post_daily_statistics;

/*==============================================================*/
/* Table: bbs_post_daily_statistics                             */
/*==============================================================*/
create table bbs_post_daily_statistics
(
   id                   int(20) not null auto_increment,
   bbs_post_id          int(20) comment '����ID',
   daily_visited_count  int(10) default 0 comment 'ÿ�ձ�������',
   daily_reply_count    int(10) default 0 comment 'ÿ�ջظ���',
   statistical_time     datetime comment 'ͳ��ʱ��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_daily_statistics comment '����ÿ��ͳ�Ʊ�';

drop table if exists bbs_tags;

/*==============================================================*/
/* Table: bbs_tags                                              */
/*==============================================================*/
create table bbs_tags
(
   id                   int(20) not null auto_increment,
   name                 varchar(200) comment '��ǩ��',
   remark               varchar(254) comment '��ע',
   is_admin             char(1) default '0' comment '0:��,1:��',
   sign_type            char(1) default '0' comment '0:����;1:�ܱ�;2:�¿�;3:����ǩ',
   sort                 int(4) default 0 comment '����',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_tags comment '��ǩ';

drop table if exists bbs_post_relate_tags;

/*==============================================================*/
/* Table: bbs_post_relate_tags                                  */
/*==============================================================*/
create table bbs_post_relate_tags
(
   id                   int(20) not null auto_increment,
   bbs_post_id          int(20) comment '����ID',
   bbs_tags_id          int(20) comment '��ǩID',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_relate_tags comment '������ǩ����';

drop table if exists bbs_post_upload_file;

/*==============================================================*/
/* Table: bbs_post_upload_file                                  */
/*==============================================================*/
create table bbs_post_upload_file
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   bbs_post_id          int(20) comment '����ID',
   file_name            varchar(254) comment '�ļ���',
   file_type            varchar(254) comment '�ļ�����',
   file_path            varchar(254) comment '�ļ�·��',
   remark               text comment '��ע',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_upload_file comment '�ϴ��ļ�';

drop table if exists bbs_user_profiler;

/*==============================================================*/
/* Table: bbs_user_profiler                                     */
/*==============================================================*/
create table bbs_user_profiler
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   nickname             varchar(50) comment '�ǳ�',
   email                varchar(50) comment 'EMAIL',
   sex                  char(1) default '1' comment '0:Ů1:��',
   life_stage           char(1) default '1' comment '0:ѧ��;1:����;2:������;3:С����;4:����֮��',
   marriage_case        char(1) default '0' comment '0:δ��;1:�ѻ�',
   zip                  varchar(10) comment '�ʱ�',
   tel                  varchar(20) comment '�绰',
   mobile               varchar(20) comment '�ֻ�',
   qq                   varchar(20) comment 'QQ',
   msn                  varchar(20) comment 'MSN',
   interests            varchar(1000) comment '��Ȥ����',
   love_sports          varchar(1000) comment '��ϲ�����˶�',
   real_name            varchar(50) comment '��ʵ����',
   brithday             datetime comment '��������',
   address              varchar(254) comment '�־�ס��',
   work_scope           varchar(500) comment '�ִ�����ҵ',
   remark               text comment '��ע',
   integral             int(10) default 0 comment '����',
   picture_name         varchar(254) comment '����ͷ����',
   picture_path         varchar(254) comment '����ͷ��·��',
   post_number          int(10) default 0 comment '������',
   reply_number         int(10) default 0 comment '������',
   essence_number       int(10) default 0 comment '����',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_user_profiler comment '���˻�����Ϣ(����)';

drop table if exists bbs_user_profiler_daily_statistics;

/*==============================================================*/
/* Table: bbs_user_profiler_daily_statistics                    */
/*==============================================================*/
create table bbs_user_profiler_daily_statistics
(
   id                   int(20) not null auto_increment,
   bbs_user_profiler_id int(20) comment '���˻�����ϢID',
   daily_integral       int(10) default 0 comment 'ÿ�ջ���',
   statistical_time     datetime comment 'ͳ��ʱ��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_user_profiler_daily_statistics comment '���˻���ÿ��ͳ�Ʊ�';

drop table if exists bbs_user_profiler_detailed_statistics;

/*==============================================================*/
/* Table: bbs_user_profiler_detailed_statistics                 */
/*==============================================================*/
create table bbs_user_profiler_detailed_statistics
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   bbs_user_profiler_id int(20) comment '���˻�����ϢID',
   profiler             int(10) default 0 comment '���ֱ��',
   reason               text comment '���ԭ��',
   change_time          datetime comment '���ʱ��',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_user_profiler_detailed_statistics comment '���˻�����ϸͳ�Ʊ�';

drop table if exists bbs_sign;

/*==============================================================*/
/* Table: bbs_sign                                              */
/*==============================================================*/
create table bbs_sign
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   sign_name            varchar(200) comment 'ǩ������',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_sign comment '��ҵǩ��';

drop table if exists bbs_system_detailed_message;

/*==============================================================*/
/* Table: bbs_system_detailed_message                           */
/*==============================================================*/
create table bbs_system_detailed_message
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�˺���',
   email                varchar(50) comment '����',
   topic                varchar(200) comment '��������',
   content              text comment '��������',
   massage_time         datetime comment '����ʱ��',
   is_read              char(1) default '0' comment '0:��;1:��',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '����ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_system_detailed_message comment 'ϵͳ������ϸ��Ϣ';

drop table if exists bbs_title_style;

/*==============================================================*/
/* Table: bbs_title_style                                       */
/*==============================================================*/
create table bbs_title_style
(
   id                   int(20) not null auto_increment,
   title                varchar(20) comment '����',
   style                varchar(500) comment '��ʽ',
   remark               text comment '��ע',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table bbs_title_style comment '������ʽ';