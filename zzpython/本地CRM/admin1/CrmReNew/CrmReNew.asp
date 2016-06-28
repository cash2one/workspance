<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../jumptolog.asp" -->
<!--#include file="Cls_vbsPage.asp"-->
<%
'-----------------------------------------------------------------------------------------------
On Error Resume Next
DIM startime,endtime
startime=timer()
'-----------------------------------------------------------------------------------------------
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
table {  font-size: 12px}
a {  font-size: 12px; color: #000000; text-decoration: none}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <%
Dim ors
Set ors=new Cls_vbsPage	'创建对象
Set ors.Conn=conn		'得到数据库连接对象
With ors
	.PageSize=200		'每页记录条数
	.PageName="page"	'cookies名称
	.DbType="MSSQL"
	'数据库类型,AC为access,MSSQL为sqlserver2000存储过程版,MYSQL为mysql,PGSQL为PostGreSql
	.RecType=0
	'记录总数(>0为另外取值再赋予或者固定值,0执行count设置存cookies,-1执行count不设置cookies)
	.JsUrl=""			'Cls_jsPage.js的路径
	.Pkey="com_id"			'主键
	.Field="*"
	.Table="temp_salescomp"
	.Condition="editdate>'"&date()-1&"'"		'条件,不需要where
	.OrderBy="com_regtime asc"	'排序,不需要order by,需要asc或者desc
End With

iRecCount=ors.RecCount()'记录总数
iRs=ors.ResultSet()		'返回ResultSet
If  iRecCount<1 Then
	response.Write("无信息！")
Else     
    For i=0 To Ubound(iRs,2)
	sql="select * from temp_salescomp where com_id="&iRs(4,i)
	set rsc=conn.execute(sql)
	if rsc.eof then
	rsc.addnew()
	end if
	for n=0 to 23
	rsc(n)=iRs(n,i)
	next
	rsc.update()
	rsc.close
	set rsc=nothing
    Next	
End If

%>
</table>
<table width="760" border="0" cellspacing="2" cellpadding="2" align="center">
  <tr> 
    <td>
<%ors.ShowPage()%>
</td>
  </tr>  
</table> 
<table width="760" border="0" align="center" cellpadding="2" cellspacing="2">
  <tr> 
    <td align="center"> 
      <%endtime=timer()%>
      本页面执行时间：<%=FormatNumber((endtime-startime)*1000,3)%>毫秒</td>
  </tr>
</table>
</body>
</html>
