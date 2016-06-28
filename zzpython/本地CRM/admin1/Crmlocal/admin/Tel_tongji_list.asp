<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
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
    <td>
	<%
	if request("del")="1" then
		conn.execute("delete from crm_tel_content where id="&request("id"))
	end if
	page=request("page")
	sear="1=1"
	sql=""
	
	               sqlorder=" order by sdate desc,fdate desc"
	               Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "id,sdate,content,fdate"
					 .FROMTbl = "crm_tel_content"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select id from test where id=crm_tel_content.id) "&sql
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 15
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=cint(total/15)
				   if total/15 > totalpg then
				   totalpg=totalpg+1
				   end if
	%>
	</td>
  </tr>
</table>
<table width="100%" border="0" class=ee id=ListTable cellspacing="0" cellpadding="5">
  <tr  class="topline">
    <td>时间</td>
    <td width="80">更新时间</td>
    <td width="80">操作</td>
  </tr>
  <%
  if not rs.eof then
  do while not rs.eof
  %>
  <tr>
    <td><a href="tel_tongji_show.asp?id=<%=rs("id")%>" target="_blank"><%response.Write(rs("sdate"))%></a></td>
    <td><%response.Write(rs("fdate"))%></td>
    <td><a href="tel_tongji_list.asp?id=<%=rs("id")%>&page=<%= page %>&del=1" onClick="return confirm('确实要删除吗？')">删除</a></td>
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
