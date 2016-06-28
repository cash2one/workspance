DROP TABLE IF EXISTS ads;

DROP TABLE IF EXISTS ads_groups;

DROP TABLE IF EXISTS ads_places;


/*==============================================================*/
/* Table: ads                                                   */
/*==============================================================*/
create table ads
(
   id                   int(20) not null auto_increment comment '编号',
   ads_place_id         int(20) comment '广告位编号',
   ads_group_id         int(20) comment '广告组编号',
   show_index           int(4) comment '排序值(按从小到大排列)',
   name                 varchar(200) comment '广告名称',
   is_custom            char(1) comment '是否客户广告(0否 1是)',
   custom_email         varchar(200) comment '客户电子邮箱',
   ads_type_code        varchar(200) comment '广告类型(静态图片/动态图片gif/Flash)',
   height               int(4) comment '广告高度',
   width                int(4) comment '广告宽度',
   url                  varchar(100) comment '链接地址',
   alt_text             varchar(100) comment '提示文字',
   remark               text comment '简单注释',
   hits                 int(20) comment '初始点击次数(0)',
   is_showed            char(1) comment '是否显示(0否 1是)',
   custom_id            int(20) comment '客户编号(0代表非客户广告)',
   gmt_start_time       datetime comment '投放开始时间',
   gmt_end_time         datetime comment '投放结束时间',
   real_hits            int(20) comment '真实点击次数(0)',
   is_checked           char(1) comment '审核(0否 1是)',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table ads comment '广告';


/*==============================================================*/
/* Table: ads_groups                                            */
/*==============================================================*/
CREATE TABLE ads_groups
(
   id                   INT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   parent_id            INT(20) COMMENT '父节点(零为父类；大于零为子类)',
   group_name           VARCHAR(200) COMMENT '组名',
   gmt_created          DATETIME COMMENT '创建时间',
   gmt_modified         DATETIME COMMENT '修改时间',
   is_deleted           CHAR(1) COMMENT '是否删除(0，否；1，是。)',
   PRIMARY KEY (id)
)
TYPE = MYISAM;

ALTER TABLE ads_groups COMMENT '广告组';

/*==============================================================*/
/* Table: ads_places                                            */
/*==============================================================*/
CREATE TABLE ads_places
(
   id                   INT NOT NULL AUTO_INCREMENT COMMENT '编号',
   parent_id            INT(20) COMMENT '父节点(零为父类；大于零为子类)',
   `name`                 VARCHAR(200) COMMENT '名称',
   width                INT(4) COMMENT '宽度',
   height               INT(4) COMMENT '高度',
   price                FLOAT COMMENT '价格',
   gmt_created          DATETIME COMMENT '创建时间',
   gmt_modified         DATETIME COMMENT '修改时间',
   is_delete            CHAR(1) COMMENT '是否删除(0，否；1，是。)',
   PRIMARY KEY (id)
)
TYPE = MYISAM;

/*   belong_to            TINYINT(2) COMMENT '所属网站,zz91 or kl91',不需要 删掉