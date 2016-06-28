use rcu

print('/******************create table schema***************/')

/**********score_system**********/
if object_id('rcu.dbo.score_system') is not null 
  drop table score_system
go
create table score_system(
  id bigint identity(1,1) not null primary key,
  company_id bigint not null,
  score_type_id int not null default(0),
  exchange_type_id int not null default(0),
  trade_url varchar(200),
  score int not null default(0), 
  is_exchanged bit not null default(0),
  gmt_created datetime not null default(getDate()),
  gmt_modified datetime not null default(getDate())
)
exec sp_addextendedproperty N'MS_Description', N'�ӷ����,���Ϊ0����ʾ����Ϊ�������', N'user', N'dbo', N'table', N'score_system', N'column', N'score_type_id'
exec sp_addextendedproperty N'MS_Description', N'�������,���Ϊ0����ʾ����Ϊ�ӷ����', N'user', N'dbo', N'table', N'score_system', N'column', N'exchange_type_id'
exec sp_addextendedproperty N'MS_Description', N'����/���ѻ���', N'user', N'dbo', N'table', N'score_system', N'column', N'score'
exec sp_addextendedproperty N'MS_Description', N'���Ϊ�鿴��Ѷ�۵ķ֣���¼�鿴��URL', N'user', N'dbo', N'table', N'score_system', N'column', N'trade_url'
exec sp_addextendedproperty N'MS_Description', N'�Ƿ�������Ķһ�', N'user', N'dbo', N'table', N'score_system', N'column', N'is_exchanged'
go

/**********score_type**************/
if object_id('rcu.dbo.score_type') is not null 
  drop table score_type
go
create table score_type(
  id int identity(1,1) not null primary key,
  name varchar(100) not null default '' ,
  score int null,
  score_limit int null,
  gmt_created datetime not null default(getDate()),
  gmt_modified datetime not null default(getDate())
)
exec sp_addextendedproperty N'MS_Description', N'���������', N'user', N'dbo', N'table', N'score_type', N'column', N'name'
exec sp_addextendedproperty N'MS_Description', N'����ֵ,���Ϊ0,��ʾ�÷�ֵ��Ҫ�˹�ָ��', N'user', N'dbo', N'table', N'score_type', N'column', N'score'
exec sp_addextendedproperty N'MS_Description', N'��ֵ���ޣ���ÿ���������ܼӼ���,0Ϊ������', N'user', N'dbo', N'table', N'score_type', N'column', N'score_limit'
go

/**********score_exchange**************
if object_id('rcu.dbo.score_exchange') is not null 
  drop table score_exchange
go
create table score_exchange(
  id int identity(1,1) not null primary key,
  company_id bigint not null,
  exchange_type_id int null,
  used_score int not null,
  is_handled bit not null default(0),
  gmt_created datetime not null default(getDate()),
  gmt_modified datetime not null default(getDate())
)
exec sp_addextendedproperty N'MS_Description', N'�һ��ķ�ֵ', N'user', N'dbo', N'table', N'score_exchange', N'column', N'used_score'
go
*/
/***********score_exchange_type**********/
if object_id('rcu.dbo.score_exchange_type') is not null
  drop table score_exchange_type
go
create table score_exchange_type(
  id int identity(1,1) not null primary key,
  name varchar(200) not null,
  need_score int not null,
  gmt_created datetime not null default(getDate()),
  gmt_modified datetime not null default(getDate())
)
exec sp_addextendedproperty N'MS_Description', N'�����ֵ,���Ϊ0,��ʾ�÷�ֵ��Ҫ�˹�ָ��', N'user', N'dbo', N'table', N'score_exchange_type', N'column', N'need_score'
go
print('/*****************initialize score type**************/')

delete from score_type
--��ʼ���ֽ���
insert into score_type(score,score_limit,name) values(0,0,'�ϻ�Ա��ʼ���ӷ�')
insert into score_type(score,score_limit,name) values(400,0,'ע���Ա')
insert into score_type(score,score_limit,name) values(20,0,'���Ƹ�������')
insert into score_type(score,score_limit,name) values(20,0,'���ƹ�˾����')
insert into score_type(score,score_limit,name) values(50,0,'�ϴ���˾����ͼƬ')
insert into score_type(score,score_limit,name) values(10,50,'��������')
insert into score_type(score,score_limit,name) values(20,0,'��������ͼƬ')
insert into score_type(score,score_limit,name) values(10,100,'����ѯ��')
insert into score_type(score,score_limit,name) values(10,50,'��������')
insert into score_type(score,score_limit,name) values(10,30,'�����̻�')
insert into score_type(score,score_limit,name) values(10,30,'���Ʊ���')
insert into score_type(score,score_limit,name) values(50,0,'�ϴ�����֤��')
insert into score_type(score,score_limit,name) values(50,200,'��������')
--�������ֽ���
insert into score_type(score,score_limit,name) values(20,60,'��¼��������')
insert into score_type(score,score_limit,name) values(20,0,'���ƻ���������Ϣ')
insert into score_type(score,score_limit,name) values(30,0,'�ϴ�����ͷ��')
insert into score_type(score,score_limit,name) values(40,200,'��������')
insert into score_type(score,score_limit,name) values(10,200,'��������')
insert into score_type(score,score_limit,name) values(80,0,'ÿ��֮��')
insert into score_type(score,score_limit,name) values(50,0,'������')
insert into score_type(score,score_limit,name) values(30,0,'��������')
--�������
insert into score_type(score,score_limit,name) values(800,0,'�Ƽ�����ע��')
insert into score_type(score,score_limit,name) values(30,0,'�������ϻ')
insert into score_type(score,score_limit,name) values(100,0,'�μ�����չ��')
--�������ѻ���
insert into score_type(score,score_limit,name) values(0,0,'����ͨ����')
insert into score_type(score,score_limit,name) values(0,0,'Ʒ��ͨ����')
insert into score_type(score,score_limit,name) values(0,0,'��ҳ���')
insert into score_type(score,score_limit,name) values(0,0,'���ҹ��')
insert into score_type(score,score_limit,name) values(0,0,'Ʒ�ƹ��')
insert into score_type(score,score_limit,name) values(0,0,'���ű���')
insert into score_type(score,score_limit,name) values(0,0,'�ƽ�չλ')
insert into score_type(score,score_limit,name) values(0,0,'��������')
insert into score_type(score,score_limit,name) values(0,0,'��˾��ҳ')


print('/***********initailize score_exchange_type***********/')
delete from score_exchange_type
insert into score_exchange_type(name,need_score) values('��ҳ���10������',11000)
insert into score_exchange_type(name,need_score) values('�ƽ���7������',5000)
insert into score_exchange_type(name,need_score) values('ר����7������',3000)
insert into score_exchange_type(name,need_score) values('���ű���7������',3000)
insert into score_exchange_type(name,need_score) values('�鿴��Ѷ',0)

print('/*****************create function schema*************/')
if object_id('rcu.dbo.f_get_today_date') is not null 
  drop function f_get_today_date
go
create function f_get_today_date()
returns varchar(10)
as
  begin
    return convert(varchar(10),getdate(),120)
  end
go

if object_id('rcu.dbo.f_get_tomorrow_date') is not null 
  drop function f_get_tomorrow_date
go
create function f_get_tomorrow_date()
returns varchar(10)
as
  begin
    return convert(varchar(10),dateadd(day,1,getdate()),120)
  end
go

print('/*****************create procedure schema*************/')
if object_id('rcu.dbo.up_score') is not null 
  drop procedure up_score
go
create procedure up_score
  @company_id bigint,
  @score_type_name varchar(100),
  @today_post_count int
as
  --��ȡ��������Ϣ
  declare @score_type_id int
  declare @score_type_score int
  declare @score_type_limit int
  select @score_type_id=id,@score_type_score=score,@score_type_limit=score_limit from score_type where name=@score_type_name
  --�ж��Ƿ�ﵽ�ӷ�����
  declare @is_reach_limit bit
  set @is_reach_limit=0
  if @score_type_limit>0
    begin
      if @today_post_count>=@score_type_limit/@score_type_score
        begin
          set @is_reach_limit=1
        end
    end
  if @is_reach_limit=0
    begin
      --�ӷ�
      declare @record_count int
      set @record_count=(select count(0) from score_system where company_id=@company_id and score_type_id=@score_type_id)
      if(@record_count>0)
        begin
          update score_system set score=score+@score_type_score,gmt_modified=getDate() where company_id=@company_id and score_type_id=@score_type_id
        end 
      else
        begin
          insert score_system(company_id,score_type_id,score) values(@company_id,@score_type_id,@score_type_score)
        end
    end
go

print('/*****************create trigger schema**************/')

/********ע���Ա: t_score_register*************/
if object_id('rcu.dbo.t_score_register') is not null 
  drop trigger t_score_register
go
create trigger [dbo].[t_score_register]
on [dbo].[comp_info]
after insert
as
  declare @company_id bigint
  set @company_id=(select inserted.com_id from inserted)
  exec up_score @company_id,'ע���Ա',0
go

/********���Ƹ�������: t_score_register*************/


/********������ҵ��Ϣ: t_score_register*************/
if object_id('rcu.dbo.t_score_company_info') is not null 
  drop trigger t_score_company_info
go
create trigger [dbo].[t_score_company_info]
on [dbo].[comp_info]
after insert,update
as
  declare @company_id bigint
  declare @com_name varchar(96)
  declare @com_tel varchar(100)
  declare @com_mobile varchar(100)
  declare @com_intro varchar(max)
  declare @com_productslist_en varchar(2000)
  declare @record_count int
  select @company_id=com_id,@com_name=com_name,@com_tel=com_tel,@com_mobile=com_mobile,@com_intro=com_intro,@com_productslist_en=com_productslist_en from inserted
  select @record_count=count(0) from score_system a,score_type b where a.score_type_id=b.id and b.name='������ҵ��Ϣ' and a.company_id=@company_id
  if @record_count>0
    begin
      if (@com_name is not null and @com_name<>'') and (@com_tel is not null and @com_tel<>'') and (@com_mobile is not null and @com_mobile<>'') and (@com_intro is not null and @com_intro<>'') and (@com_productslist_en is not null and @com_productslist_en<>'')
        begin
          exec up_score @company_id,'������ҵ��Ϣ',0
        end
    end
go

/********���ƹ�˾����ͼƬ: t_score_finish_logo*************/
if object_id('rcu.dbo.t_score_finish_logo') is not null 
  drop trigger t_score_finish_logo
go

/********��������: t_score_products*************/
if object_id('rcu.dbo.t_score_products') is not null 
  drop trigger t_score_products
go

create trigger [dbo].[t_score_products]
on [dbo].[products]
after insert,update
as
  declare @company_id bigint
  declare @today_post_count int
  declare @checked tinyint
  set @checked=(select inserted.pdt_check from inserted)
  if @checked=1
    begin
      set @company_id=(select inserted.com_id from inserted)
      set @today_post_count=(select count(0) from products where pdt_check=1 and com_id=@company_id and pdt_time<dbo.f_get_today_date() and pdt_time>dbo.f_get_tomorrow_date())
    end
  exec up_score @company_id,'��������',@today_post_count
go

/********��������ͼƬ: t_score_products_pic(�������δ��ˣ�����ͼƬҲ��ӷ�)*************/
if object_id('rcu.dbo.t_score_products_pic') is not null 
  drop trigger t_score_products_pic
go
create trigger [dbo].[t_score_products_pic]
on [dbo].[productimg_biao]
after insert
as
  declare @company_id bigint
  declare @product_id bigint
  declare @upload_count tinyint
  set @upload_count=0
  set @product_id=(select inserted.pdt_id from inserted)
  set @upload_count=(select count(0) from productimg_biao where pdt_id=@product_id)
  if @upload_count=0
    begin
      set @company_id=(select com_id from products where pdt_id=@product_id)
      exec up_score @company_id,'��������ͼƬ',0
    end
go


/********����ѯ��: t_score_question*************/
if object_id('rcu.dbo.t_score_question') is not null 
  drop trigger t_score_question
go
create trigger [dbo].[t_score_question]
on [dbo].[comp_question]
after insert
as
  declare @company_id bigint
  declare @today_post_count int
  set @company_id=(select inserted.fromcom_id from inserted)
  set @today_post_count=(select count(0) from comp_question where fromcom_id=@company_id and mdate<dbo.f_get_today_date() and mdate>dbo.f_get_tomorrow_date() and outflag=0)
  exec up_score @company_id,'����ѯ��',@today_post_count
go

/********�����̻�: t_score_bizexpress*************/
if object_id('rcu.dbo.t_score_bizexpress') is not null 
  drop trigger t_score_bizexpress
go
create trigger [dbo].[t_score_bizexpress]
on [dbo].[comp_bizexpress]
after insert
as
  declare @company_id bigint
  declare @today_post_count int
  set @company_id=(select inserted.com_id from inserted)
  set @today_post_count=(select count(0) from comp_bizexpress where com_id=@company_id and fdate<dbo.f_get_today_date() and fdate>dbo.f_get_tomorrow_date() )
  exec up_score @company_id,'�����̻�',@today_post_count
go
  
/********���Ʊ���: t_score_priceorder*************/
if object_id('rcu.dbo.t_score_priceorder') is not null 
  drop trigger t_score_priceorder
go
create trigger [dbo].[t_score_priceorder]
on [dbo].[comp_priceorder]
after insert
as
  declare @company_id bigint
  declare @today_post_count int
  set @company_id=(select inserted.com_id from inserted)
  set @today_post_count=(select count(0) from comp_priceorder where com_id=@company_id and fdate<dbo.f_get_today_date() and fdate>dbo.f_get_tomorrow_date() )
  exec up_score @company_id,'���Ʊ���',@today_post_count
go

/********�ϴ�����֤��: t_score_certificate*************/
if object_id('rcu.dbo.t_score_certificate') is not null 
  drop trigger t_score_certificate
go
create trigger [dbo].[t_score_certificate]
on [dbo].[comp_certificate]
after insert
as
  declare @company_id bigint
  declare @checked bit
  set @company_id=(select inserted.com_id from inserted)
  set @checked=(select inserted.c_check from inserted)
  if @checked=1
    begin
      exec up_score @company_id,'�ϴ�����֤��',0
    end
go

/********��������: t_score_feedback,û�м�¼company_id*************/
/*if object_id('rcu.dbo.t_score_feedback') is not null 
  drop trigger t_score_feedback
go
create trigger [dbo].[t_score_feedback]
on [dbo].[comp_feedback]
after update
as
  declare @company_id bigint
  declare @checked bit
  set @company_id=(select inserted.com_id from inserted)
  set @checked=(select inserted.c_check from inserted)
  if @checked=1
    begin
      exec up_score @company_id,'�ϴ�����֤��',0
    end
go
*/

print('******************************��������triggger**************************')
use rcu_others

/********���ƻ���������Ϣ: t_score_bbs_profile*************/
if object_id('rcu_others.dbo.t_score_bbs_profile') is not null 
  drop trigger t_score_bbs_profile
go
create trigger [dbo].[t_score_bbs_profile]
on [dbo].[forum_personinfo]
after insert,update
as
  declare @company_id bigint
  set @company_id=(select inserted.com_id from inserted)
  exec rcu.dbo.up_score @company_id,'���ƻ���������Ϣ',0
  declare @pic varchar(255)
  set @pic=(select inserted.pic from inserted)
  if @pic<>'' or @pic is not null
    begin
      exec rcu.dbo.up_score @company_id,'�ϴ�����ͷ��',0
    end
go

/********������/����: t_score_bbs_post*************/
if object_id('rcu_others.dbo.t_score_bbs_post') is not null 
  drop trigger t_score_bbs_post
go
create trigger [dbo].[t_score_bbs_post]
on [dbo].[forum_bbs]
after insert,update
as
  declare @today_post_count int
  declare @company_id bigint
  declare @checked tinyint
  declare @sub_id int
  declare @new_hot tinyint
  select @company_id=inserted.com_id,@checked=inserted.fcheck,@sub_id=inserted.subId,@new_hot=inserted.isNewAndHot from inserted
  if @checked<>0 
    begin
      --����
      if @sub_id=0
        begin
          set @today_post_count=(select count(0) from forum_bbs where com_id=@company_id and fcheck=1 and subId=0 and fdate<dbo.f_get_today_date() and fdate>dbo.f_get_tomorrow_date() )
          exec up_score @company_id,'��������',@today_post_count
          if @new_hot >0
            begin
              exec up_score @company_id,'������',0
            end
        end 
      else
        begin
          set @today_post_count=(select count(0) from forum_bbs where com_id=@company_id and fcheck=1 and subId<>0 and fdate<dbo.f_get_today_date() and fdate>dbo.f_get_tomorrow_date() )
          exec up_score @company_id,'��������',@today_post_count
          if @checked=3
            begin
              exec up_score @company_id,'��������',@today_post_count
            end
        end
    end
go

/********ÿ��֮��: t_score_bbs_star*************/
if object_id('rcu_others.dbo.t_score_bbs_star') is not null 
  drop trigger t_score_bbs_star
go
create trigger [dbo].[t_score_bbs_star]
on [dbo].[forum_star]
after insert
as
  declare @company_id bigint
  set @company_id=(select inserted.com_id from inserted)
  exec rcu.dbo.up_score @company_id,'ÿ��֮��',0
go

 
use rcu_news
if exists(select null from syscolumns where id=object_id('trades') and name='needScore')
begin
	alter table trades drop column needScore
end
alter table trades add needScore int default(0)
