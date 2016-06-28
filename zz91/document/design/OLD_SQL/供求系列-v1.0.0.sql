/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2010-3-30 16:08:41                           */
/*==============================================================*/


drop table if exists products_series;

drop table if exists products_series_contacts;

/*==============================================================*/
/* Table: products_series                                       */
/*==============================================================*/
create table products_series
(
   id                   int(20) not null,
   company_id           int(20) comment '公司ID',
   account              varchar(50) comment '帐号名',
   name                 varchar(200) comment '系列名称',
   series_details       text comment '系列描述',
   series_order         int(4) comment '系列排序',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table products_series comment '供求系列';

/*==============================================================*/
/* Table: products_series_contacts                              */
/*==============================================================*/
create table products_series_contacts
(
   id                   int(20) not null,
   products_id          int(20) comment '关联供求ID',
   products_series_id   int(20) comment '关联供求系列ID',
   gmt_created          datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   primary key (id)
)
type = MYISAM;

alter table products_series_contacts comment '供求系列联系';
