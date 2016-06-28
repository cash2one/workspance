/*==============================================================*/
/* Table: tags_relate_article                                   */
/*==============================================================*/
create table tags_relate_article
(
   id                   int(20) not null auto_increment,
   tags_info_id         int(20) comment '对应标签的id',
   article_id           int(20) comment '对应的信息id,还要根据对应的类别来综合考虑',
   tags_article_category_code varchar(200) comment '对应信息的类型,如新闻，供求...',
   is_admin             char(1) default '0' comment '是否为后台发布',
   tags_category_products_code varchar(200) comment '如果为供求标签时的供求大类,如废金属,废塑料...',
   primary key (id)
)
type = MYISAM;

alter table tags_relate_article comment '标签与文章关联表';

/*==============================================================*/
/* Table: tags_info                                             */
/*==============================================================*/
create table tags_info
(
   id                   int(20) not null auto_increment,
   name                 varchar(200),
   type_id              char(1) comment '0：默认，1：数量标签，2：颜色标签',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table tags_info comment '标签信息表';

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
   stat_type            char(1) comment '0：最热标签；1：稀缺标签',
   gmt_created          datetime,
   gmt_modified         datetime,
   primary key (id)
)
type = MYISAM;

alter table tags_most_or_least comment '热门标签或稀缺标签';
