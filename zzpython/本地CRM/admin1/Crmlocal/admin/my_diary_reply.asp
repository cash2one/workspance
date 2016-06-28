<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE8 {color: #000; font-weight: bold; }
-->
</style>
</head>

<body>
<%
sql=""
sear=sear&"n="
sear=sear&"&personid="&request("personid")
page=request("page")
if page="" then page=1
Set oPage=New clsPageRs2
With oPage
	.sltFld  = "id,fdate,replyContent,replyperson,share"
	.FROMTbl = "crm_diary"
	.sqlOrder= "order by id desc"
	.sqlWhere= "WHERE personid="&request("personid")&" "&sql
	.keyFld  = "id"    '不可缺少
	.pageSize= 10
	.getConn = conn
	Set Rs  = .pageRs
End With
total=oPage.getTotalPage
oPage.pageNav "?"&sear,""

totalpg=cint(total/10)
if total/10 > totalpg then
	totalpg=totalpg+1
end if
%>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#999999">
  <tr>
    <td bgcolor="#FFFFFF">日期</td>
    <td bgcolor="#FFFFFF">内容</td>
    <td bgcolor="#FFFFFF">回复内容</td>
    <td bgcolor="#FFFFFF">回复人</td>
  </tr>
  <%
  if not rs.eof then
  while not rs.eof
  %>
  <tr>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("fdate")%> <a href="my_oldDiary.asp?diaryid=<%=rs("id")%>&amp;actontype=back" style="color:#999">查看日报</a></td>
    <td bgcolor="#FFFFFF"><%=rs("share")%></td>
    <td bgcolor="#FFFFFF"><%=rs("replycontent")%></td>
    <td bgcolor="#FFFFFF">
	<%
	replyperson=rs("replyperson")
	if replyperson<>"" then
		sqlp="select realname from users where id="&replyperson&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			replyname=rsp(0)
		end if
		rsp.close
		set rsp=nothing
	else
		replyname=""
	end if
	%>
	<%=replyname%></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  %>
</table>
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25"><!-- #include file="../../include/page.asp" --></td>
  </tr>
 
</table>
</body>
</html>
<% 
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
