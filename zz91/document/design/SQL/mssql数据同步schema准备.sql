-- used in sql server 
USE [rcu]
/******************clean**********************/
if object_id('rcu.dbo.data_sync') is not null 
	drop table data_sync
go
if object_id('rcu.dbo.UP_sync') is not null 
	drop procedure UP_sync
go
if object_id('rcu.dbo.t_sync_company') is not null 
	drop trigger t_sync_company
go
if object_id('rcu.dbo.t_sync_products') is not null 
	drop trigger t_sync_products
go
if object_id('rcu.dbo.t_sync_products_notShowPrice') is not null 
	drop trigger t_sync_products_notShowPrice
go
if object_id('rcu.dbo.t_sync_products_others') is not null 
	drop trigger t_sync_products_others
go
if object_id('rcu.dbo.t_sync_products_check') is not null 
	drop trigger t_sync_products_check
go
if object_id('rcu.dbo.t_sync_products_sort1') is not null 
	drop trigger t_sync_products_sort1
go
if object_id('rcu.dbo.t_sync_products_sort2') is not null 
	drop trigger t_sync_products_sort2
go
if object_id('rcu.dbo.t_sync_products_sort3') is not null 
	drop trigger t_sync_products_sort3
go
if object_id('rcu.dbo.t_sync_products_sort4') is not null 
	drop trigger t_sync_products_sort4
go
if object_id('rcu.dbo.t_sync_products_sort5') is not null 
	drop trigger t_sync_products_sort5
go
if object_id('rcu.dbo.t_sync_products_sort6') is not null 
	drop trigger t_sync_products_sort6
go
if object_id('rcu.dbo.t_sync_products_sort10') is not null 
	drop trigger t_sync_products_sort10
go
if object_id('rcu.dbo.t_sync_products_sort12') is not null 
	drop trigger t_sync_products_sort12
go
if object_id('rcu.dbo.t_sync_products_sort13') is not null 
	drop trigger t_sync_products_sort13
go
if object_id('rcu.dbo.t_sync_products_sort14') is not null 
	drop trigger t_sync_products_sort14
go
if object_id('rcu.dbo.t_sync_products_sort15') is not null 
	drop trigger t_sync_products_sort15
go
/******************table**********************/
create table data_sync
(
    id bigint identity(1,1) primary key not null,
    primary_id bigint not null,
    table_name varchar(100) not null,
    is_imported tinyint not null default(0),
    gmt_updated datetime not null default(getdate())
) 
go

/****** Procedure ******/
create PROCEDURE [dbo].[UP_sync]
  @table_name nvarchar(100),
  @primary_field_name nvarchar(50),
  @primary_id bigint
AS
	declare @sql nvarchar(200),@sql2 nvarchar(200)
	declare @RecordCount  int 

	set @sql='select @a=count(0) from '+@table_name+' where '+@table_name+'.'+@primary_field_name+'='+
			cast(@primary_id as nvarchar(50))
	exec sp_executesql @sql,N'@a int output',@RecordCount output
	if (@RecordCount)>0
	begin
		set @sql='select @a=count(0) from data_sync where data_sync.primary_id='+cast(@primary_id as nvarchar(50))
		exec sp_executesql @sql,N'@a int output',@RecordCount output

		if(@RecordCount)>0
			begin
				update data_sync set is_imported=0,gmt_updated=getdate()
				where data_sync.primary_id=@primary_id
			end
		else
			begin
				insert data_sync(table_name,primary_id,gmt_updated,is_imported)
				values(@table_name,@primary_id,getdate(),0)
			end
	end
go
/*******************triggers****************/
--company
create TRIGGER [dbo].[t_sync_company]
on [dbo].[comp_Info]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.com_id from Inserted)
	exec UP_sync 'comp_info','com_id',@primary_id
go

--products
create TRIGGER [dbo].[t_sync_products]
on [dbo].[products]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_notShowPrice
create TRIGGER [dbo].[t_sync_products_notShowPrice]
on [dbo].[products_notShowPrice]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_others
create TRIGGER [dbo].[t_sync_products_others]
on [dbo].[products_others]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_check
create TRIGGER [dbo].[t_sync_products_check]
on [dbo].[products_check]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort1
create TRIGGER [dbo].[t_sync_products_sort1]
on [dbo].[products_sort1]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort2
create TRIGGER [dbo].[t_sync_products_sort2]
on [dbo].[products_sort2]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort3
create TRIGGER [dbo].[t_sync_products_sort3]
on [dbo].[products_sort3]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort4
create TRIGGER [dbo].[t_sync_products_sort4]
on [dbo].[products_sort4]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort5
create TRIGGER [dbo].[t_sync_products_sort5]
on [dbo].[products_sort5]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort6
create TRIGGER [dbo].[t_sync_products_sort6]
on [dbo].[products_sort6]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go


--products_sort10
create TRIGGER [dbo].[t_sync_products_sort10]
on [dbo].[products_sort10]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort12
create TRIGGER [dbo].[t_sync_products_sort12]
on [dbo].[products_sort12]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort13
create TRIGGER [dbo].[t_sync_products_sort13]
on [dbo].[products_sort13]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort14
create TRIGGER [dbo].[t_sync_products_sort14]
on [dbo].[products_sort14]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

--products_sort15
create TRIGGER [dbo].[t_sync_products_sort15]
on [dbo].[products_sort15]
AFTER INSERT, UPDATE
AS 
    declare @primary_id bigint
	set @primary_id=(select Inserted.pdt_id from Inserted)
	exec UP_sync 'products','pdt_id',@primary_id
go

