if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[Add_CRM]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[Add_CRM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GetCountsInfor]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[GetCountsInfor]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GetPagedInfor]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[GetPagedInfor]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ShowInfor]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[ShowInfor]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[Update_Users_Pwd]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[Update_Users_Pwd]
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS OFF 
GO

CREATE PROCEDURE Add_CRM
@Crm_Id int output,
@To_Meno int,
@To_Staff int,
@Crm_Title varchar(255),
@Crm_Date datetime,
@Crm_Annex varchar(255),
@Crm_Content varchar(4000),
@From_Meno int,
@From_Staff int

 AS 
if not exists(select CRM_ID from CRM where Crm_Title=@Crm_Title and From_Staff= @From_Staff and To_Meno=@To_Meno)

begin

	INSERT INTO CRM(
	[To_Meno],[To_Staff],[Crm_Title],[Crm_Date],[Crm_Annex],[Crm_Content],[From_Meno],[From_Staff]
	)VALUES(
	@To_Meno,@To_Staff,@Crm_Title,@Crm_Date,@Crm_Annex,@Crm_Content,@From_Meno,@From_Staff)
	SET @Crm_Id = @@IDENTITY
end

else

set @Crm_Id=-1
GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS OFF 
GO

CREATE  PROCEDURE GetCountsInfor
	@tblName   	varchar(255),		-- 表名
	@strWhere	varchar(255)		-- 条件
	
AS
declare @strSQL   varchar(5000)    		-- 主语句

if @strWhere!= ''
	begin
		set @strSQL = 'select count(*) from ' + @tblName + ' where ' + @strWhere
	end
else
	begin
		set @strSQL = 'select count(*) from ' + @tblName
	end
exec (@strSQL)
GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS OFF 
GO

CREATE PROCEDURE GetPagedInfor
    @tblName   varchar(255),       -- 表名
    @strGetFields varchar(1000) = '*',  -- 需要返回的列 
    @fldName varchar(255)='',      -- 排序的字段名
    @PageSize   int = 10,          -- 页尺寸
    @PageIndex  int = 1,           -- 页码
    @OrderType bit = 0,  -- 设置排序类型, 非 0 值则降序  
    @strWhere  varchar(1500) = ''  -- 查询条件 (注意: 不要加 where)
AS
declare @strSQL   varchar(5000)       -- 主语句
declare @strTmp   varchar(110)        -- 临时变量
declare @strOrder varchar(400)        -- 排序类型
if @OrderType != 0
begin
    set @strTmp = '<(select min'
    set @strOrder = ' order by [' + @fldName +'] desc'
--如果@OrderType不是0，就执行降序，这句很重要！
end
else
begin
    set @strTmp = '>(select max'
    set @strOrder = ' order by [' + @fldName +'] asc'
end
if @PageIndex = 1
begin
    if @strWhere != ''   
        set @strSQL = 'select top ' + str(@PageSize) +' '+@strGetFields+ '  from ' + @tblName + ' where ' + @strWhere + ' ' + @strOrder
    else
        set @strSQL = 'select top ' + str(@PageSize) +' '+@strGetFields+ '  from '+ @tblName +' ' +@strOrder
--如果是第一页就执行以上代码，这样会加快执行速度
end
else
begin
--以下代码赋予了@strSQL以真正执行的SQL代码
set @strSQL = 'select top ' + str(@PageSize) +' '+@strGetFields+ '  from '
    + @tblName + ' where [' + @fldName + ']' + @strTmp + '(['+ @fldName + ']) from (select top ' + str((@PageIndex-1)*@PageSize) + ' ['+ @fldName + '] from ' + @tblName  +' '+ @strOrder + ') as tblTmp)'+ @strOrder
if @strWhere != ''
    set @strSQL = 'select top ' + str(@PageSize) +' '+@strGetFields+ '  from '
        + @tblName + ' where [' + @fldName + ']' + @strTmp + '(['
        + @fldName + ']) from (select top ' + str((@PageIndex-1)*@PageSize) + ' ['
        + @fldName + '] from ' + @tblName + ' where ' + @strWhere + ' '
        + @strOrder + ') as tblTmp) and ' + @strWhere + ' ' + @strOrder
end   
exec (@strSQL)
GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS OFF 
GO

CREATE  PROCEDURE ShowInfor
	@tbField            varchar(255),		-- 字段
	@tblName   	varchar(255),		-- 表名
	@strWhere	varchar(255)		-- 条件
	
AS
declare @strSQL   varchar(5000)    		-- 主语句

if @strWhere!= ''
	begin
		set @strSQL = 'select  '+@tbField +'  from ' + @tblName + ' where ' + @strWhere
	end
else
	begin
		set @strSQL = 'select  '+@tbField +'  from '  + @tblName
	end
exec (@strSQL)
GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS OFF 
GO

CREATE PROCEDURE Update_Users_Pwd
(
	@id int,
	@OldPwd varchar(50),
	@NewPwd varchar(50),
	@realname varchar(100),
	@return int output
) 
AS 
 if not exists(select [id] from [users] where [id]=@id and [password]=@OldPwd)

set @return=-1

else

begin
	UPDATE users SET 
	[password] = @NewPwd,[realname] = @realname
	WHERE id=@id

set @return=1

end
GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

