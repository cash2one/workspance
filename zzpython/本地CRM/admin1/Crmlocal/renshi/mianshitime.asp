<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
if request("edit")<>"" then
	sql="update renshi_user set interviewTime='"&request("interviewTime")&"' where id="&request("id")&""
	conn.execute(sql)
	response.Write("<script>parent.showmiantime("&request("id")&");parent.document.getElementById('interviewTimetime"&request("id")&"').innerHTML='"&request("interviewTime")&"'</script>")
end if
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
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<form name="form1" method="post" action="">
  <input type="hidden" name="edit" value="1"/>
<input type="hidden" value="<%=request("id")%>" id="id" name="id">
<script language=javascript>createDatePicker("interviewTime",true,"<%=request("interviewTime")%>",false,true,true,true)</script><input type="submit" name="button" id="button" value="提交">
</form></body>
</html>
