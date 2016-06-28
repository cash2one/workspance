drop table if exists bbs_post;

/*==============================================================*/
/* Table: bbs_post                                              */
/*==============================================================*/
create table bbs_post
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '帐号名',
   bbs_user_profiler_id int(20) comment '个人基本信息ID',
   bbs_post_category_id int(20) comment '模块ID',
   bbs_title_style_id   int(20) comment '标题样式ID',
   bbs_topics_id        int(20) comment '所属话题',
   title                varchar(200) comment '标题',
   content              text comment '内容',
   front_category_id    int(20) comment '前台分类',
   is_show              char(1) default '1' comment '0:否,1:是',
   is_del               char(1) default '0' comment '0:否,1:是',
   check_person         varchar(100) comment '审核者',
   check_status         char(1) default '0' comment '0:正常,1:已读,2:退回',
   check_time           datetime comment '审核时间',
   unchecked_check_status char(1) default '0' comment '0:未审核,1:已审核',
   unpass_reason        varchar(500) comment '未通过原因',
   is_hot_post          char(1) default '0' comment '0:否;1:是',
   post_type            char(1) default '1' comment '0:差贴,1:好贴;2:牛贴;3:头条;4:最新动态;5:热门话题',
   visited_count        int(10) default 0 comment '被访问数',
   reply_count          int(10) default 0 comment '回复数',
   post_time            datetime comment '发布时间',
   reply_time           datetime comment '最后回复时间',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post comment '主贴';

drop table if exists bbs_post_reply;

/*==============================================================*/
/* Table: bbs_post_reply                                        */
/*==============================================================*/
create table bbs_post_reply
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '帐号名',
   bbs_post_id          int(20) comment '主贴ID',
   title                varchar(254) comment '标题',
   content              text comment '内容',
   is_del               char(1) default '0' comment '0:否,1:是',
   check_person         varchar(100) comment '审核者',
   check_status         char(1) default '0' comment '0:正常,1:已读,2:退回',
   check_time           datetime comment '审核时间',
   unpass_reason        text comment '未通过原因',
   unchecked_check_status char(1) default '0' comment '0:未审核,1:已审核',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_reply comment '回复帖';

drop table if exists bbs_post_category;

/*==============================================================*/
/* Table: bbs_post_category                                     */
/*==============================================================*/
create table bbs_post_category
(
   id                   int(20) not null auto_increment,
   parent_id            int(20) comment '父ID',
   l                    int comment '左值',
   r                    int comment '右值',
   is_del               char(1) default '0' comment '0:否;1:是',
   name                 varchar(20) comment '模块名',
   remark               text comment '备注',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_category comment '板块';

drop table if exists bbs_post_browse_statistics;

/*==============================================================*/
/* Table: bbs_post_browse_statistics                            */
/*==============================================================*/
create table bbs_post_browse_statistics
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '帐户名',
   bbs_post_id          int(20) comment '主贴ID',
   browse_time          datetime comment '浏览时间',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_browse_statistics comment '主贴浏览统计表';

drop table if exists bbs_post_daily_statistics;

/*==============================================================*/
/* Table: bbs_post_daily_statistics                             */
/*==============================================================*/
create table bbs_post_daily_statistics
(
   id                   int(20) not null auto_increment,
   bbs_post_id          int(20) comment '主贴ID',
   daily_visited_count  int(10) default 0 comment '每日被访问数',
   daily_reply_count    int(10) default 0 comment '每日回复数',
   statistical_time     datetime comment '统计时间',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_daily_statistics comment '主贴每日统计表';

drop table if exists bbs_tags;

/*==============================================================*/
/* Table: bbs_tags                                              */
/*==============================================================*/
create table bbs_tags
(
   id                   int(20) not null auto_increment,
   name                 varchar(200) comment '标签名',
   remark               varchar(254) comment '备注',
   is_admin             char(1) default '0' comment '0:否,1:是',
   sign_type            char(1) default '0' comment '0:其他;1:周报;2:月刊;3:主标签',
   sort                 int(4) default 0 comment '排序',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_tags comment '标签';

drop table if exists bbs_post_relate_tags;

/*==============================================================*/
/* Table: bbs_post_relate_tags                                  */
/*==============================================================*/
create table bbs_post_relate_tags
(
   id                   int(20) not null auto_increment,
   bbs_post_id          int(20) comment '主贴ID',
   bbs_tags_id          int(20) comment '标签ID',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_relate_tags comment '主贴标签关联';

drop table if exists bbs_post_upload_file;

/*==============================================================*/
/* Table: bbs_post_upload_file                                  */
/*==============================================================*/
create table bbs_post_upload_file
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   bbs_post_id          int(20) comment '主贴ID',
   file_name            varchar(254) comment '文件名',
   file_type            varchar(254) comment '文件类型',
   file_path            varchar(254) comment '文件路径',
   remark               text comment '备注',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_post_upload_file comment '上传文件';

drop table if exists bbs_user_profiler;

/*==============================================================*/
/* Table: bbs_user_profiler                                     */
/*==============================================================*/
create table bbs_user_profiler
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   nickname             varchar(50) comment '昵称',
   email                varchar(50) comment 'EMAIL',
   sex                  char(1) default '1' comment '0:女1:男',
   life_stage           char(1) default '1' comment '0:学生;1:单身;2:恋爱中;3:小两口;4:三口之家',
   marriage_case        char(1) default '0' comment '0:未婚;1:已婚',
   zip                  varchar(10) comment '邮编',
   tel                  varchar(20) comment '电话',
   mobile               varchar(20) comment '手机',
   qq                   varchar(20) comment 'QQ',
   msn                  varchar(20) comment 'MSN',
   interests            varchar(1000) comment '兴趣爱好',
   love_sports          varchar(1000) comment '我喜欢的运动',
   real_name            varchar(50) comment '真实姓名',
   brithday             datetime comment '出生日期',
   address              varchar(254) comment '现居住地',
   work_scope           varchar(500) comment '现从事行业',
   remark               text comment '备注',
   integral             int(10) default 0 comment '积分',
   picture_name         varchar(254) comment '个人头像名',
   picture_path         varchar(254) comment '个人头像路径',
   post_number          int(10) default 0 comment '发帖数',
   reply_number         int(10) default 0 comment '回帖数',
   essence_number       int(10) default 0 comment '精华',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_user_profiler comment '个人基本信息(积分)';

drop table if exists bbs_user_profiler_daily_statistics;

/*==============================================================*/
/* Table: bbs_user_profiler_daily_statistics                    */
/*==============================================================*/
create table bbs_user_profiler_daily_statistics
(
   id                   int(20) not null auto_increment,
   bbs_user_profiler_id int(20) comment '个人基本信息ID',
   daily_integral       int(10) default 0 comment '每日积分',
   statistical_time     datetime comment '统计时间',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_user_profiler_daily_statistics comment '个人积分每日统计表';

drop table if exists bbs_user_profiler_detailed_statistics;

/*==============================================================*/
/* Table: bbs_user_profiler_detailed_statistics                 */
/*==============================================================*/
create table bbs_user_profiler_detailed_statistics
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   bbs_user_profiler_id int(20) comment '个人基本信息ID',
   profiler             int(10) default 0 comment '积分变更',
   reason               text comment '变更原因',
   change_time          datetime comment '变更时间',
   gmt_created          datetime comment '添加时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_user_profiler_detailed_statistics comment '个人积分详细统计表';

drop table if exists bbs_sign;

/*==============================================================*/
/* Table: bbs_sign                                              */
/*==============================================================*/
create table bbs_sign
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   sign_name            varchar(200) comment '签名名称',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_sign comment '企业签名';

drop table if exists bbs_system_detailed_message;

/*==============================================================*/
/* Table: bbs_system_detailed_message                           */
/*==============================================================*/
create table bbs_system_detailed_message
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '账号名',
   email                varchar(50) comment '邮箱',
   topic                varchar(200) comment '留言主题',
   content              text comment '留言内容',
   massage_time         datetime comment '留言时间',
   is_read              char(1) default '0' comment '0:否;1:是',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '创建时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_system_detailed_message comment '系统留言详细信息';

drop table if exists bbs_title_style;

/*==============================================================*/
/* Table: bbs_title_style                                       */
/*==============================================================*/
create table bbs_title_style
(
   id                   int(20) not null auto_increment,
   title                varchar(20) comment '标题',
   style                varchar(500) comment '样式',
   remark               text comment '备注',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table bbs_title_style comment '标题样式';