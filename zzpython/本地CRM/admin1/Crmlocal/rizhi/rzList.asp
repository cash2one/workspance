<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<%
tcheck=request.QueryString("tcheck")
id=request.QueryString("id")
if tcheck<>"" then
	sql="update  crm_awoke set tcheck="&tcheck&" where id="&id&""
	conn.execute(sql)
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input type="button" name="button" id="button" value="添加CRM修改日志" onClick="window.location='rzadd.asp'" /></td>
  </tr>
  <tr>
    <td>
	<%
	sear=sear&"n="
	sql=""
	if session("userid")<>"10" then
		'sql=sql&" and personid="&session("personid")&""
	end if
	               sqlorder=" order by tdate desc"
	               Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "id,ttitle,tcontent,tdate,personid"
					 .FROMTbl = "crm_rizhi"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select id from test where id=crm_rizhi.id) "&sql
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
	</td>
  </tr>
</table>
<table width="100%" border="0" class=ee id=ListTable cellspacing="0" cellpadding="5">
  <tr  class="topline">
    <td>标题</td>
    <td>时间</td>
    <td>详情</td>
    <td>添加用户</td>
    <td width="80">操作</td>
  </tr>
  <%
  if not rs.eof then
  do while not rs.eof
  %>
  <tr>
    <td><%response.Write(rs("ttitle"))%></td>
    <td><%response.Write(rs("tdate"))%></td>
    <td><%response.Write(rs("Tcontent"))%></td>
    <td>
      <%
	sqlu="select realname from users where id="&rs("personid")&""
	set rsu=conn.execute(sqlu)
	if not rsu.eof then
	response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%>
    </td>
    <td><a href="rzedit.asp?id=<%=rs("id")%>">编辑</a> | <a href="rzdel.asp?id=<%=rs("id")%>" onClick="return confirm('确实要删除吗？')">删除</a></td>
  </tr>
  <%
  rs.movenext
  loop
  end if
  rs.close
  set rs=nothing
  %>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
