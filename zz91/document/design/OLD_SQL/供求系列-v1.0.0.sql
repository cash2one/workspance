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
   company_id           int(20) comment '��˾ID',
   account              varchar(50) comment '�ʺ���',
   name                 varchar(200) comment 'ϵ������',
   series_details       text comment 'ϵ������',
   series_order         int(4) comment 'ϵ������',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table products_series comment '����ϵ��';

/*==============================================================*/
/* Table: products_series_contacts                              */
/*==============================================================*/
create table products_series_contacts
(
   id                   int(20) not null,
   products_id          int(20) comment '��������ID',
   products_series_id   int(20) comment '��������ϵ��ID',
   gmt_created          datetime comment '����ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table products_series_contacts comment '����ϵ����ϵ';
