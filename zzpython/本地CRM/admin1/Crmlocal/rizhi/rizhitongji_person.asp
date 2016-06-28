<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear="n="
fromdate=request("fromdate")
todate=request("todate")
if fromdate="" then fromdate=date
if todate="" then todate=date
personid=request("personid")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
<form id="form1" name="form1" method="post" action="rizhitongji_person.asp"> <tr>
    <td colspan="3" align="center" bgcolor="#FFFFFF"><script language=javascript>createDatePicker("fromdate",false,"<%=fromdate%>",false,true,true,true)</script>
      <script language=javascript>createDatePicker("todate",false,"<%=todate%>",false,true,true,true)</script>  
      <input type="submit" name="button" id="button" value="查询" />
      <input type="hidden" name="personid" id="personid" value="<%=personid%>" /></td>
 </tr></form>
 </table>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#999999">
  <tr>
    <td bgcolor="#CCCCCC">时间</td>
    <td bgcolor="#CCCCCC">数量</td>
  </tr>
  <%
  for i=cdate(fromdate) to cdate(todate)
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=i%></td>
    <td bgcolor="#FFFFFF">
    <%
	
	sqlp="select count(0) from crm_viewHistory where personid="&personid&" and fdate>'"&i&"' and fdate<='"&i+1&"'"
	set rsp=conn.execute(sqlp)
	response.Write(rsp(0))
	%>
    </td>
  </tr>
  <%
  next
  %>
</table>
</body>
</html>
