/*==============================================================*/
/* Table: category_company_price                                */
/*==============================================================*/
create table category_company_price
(
   id                   int(20) not null,
   label              varchar(50),
   code                 varchar(200),
   is_del               char(1) default '0' comment '�Ƿ�ɾ�� 0:��,1:��',
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   key AK_Key_1 (id)
)
type = MYISAM;

alter table category_company_price comment '��ҵ�������';

/*==============================================================*/
/* Table: company_price                                         */
/*==============================================================*/
create table company_price
(
   id                   int(20) not null auto_increment,
   company_id           int(20) comment '������˾ID',
   account              varchar(50) comment '�����˺�',
   product_id           int(20) comment '��Ӧ�Ĺ���ID',
   title                varchar(200) comment '���۱���',
   category_company_price_code varchar(200),
   price                float comment '�۸�',
   price_unit           varchar(50) comment '�۸�λ,��Ԫ/��',
   min_price            float comment '�۸�����',
   max_price            float comment '�۸�����',
   area_code            varchar(200) comment '����',
   details              varchar(500) comment '��ϸ��Ϣ',
   is_checked           char(1) default '0' comment '�Ƿ����',
   post_time            datetime comment '����ʱ��',
   check_time           datetime comment '���ʱ��',
   expired_time         datetime comment '����ʱ��,ͨ������ó����빩������',
   refresh_time         datetime,
   gmt_created          datetime comment '���ʱ��',
   gmt_modified         datetime comment '�޸�ʱ��',
   primary key (id)
)
type = MYISAM;

alter table company_price comment '��ҵ����';