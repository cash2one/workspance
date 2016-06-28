<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>销售统计</title>

<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 2px;
	margin-top: 2px;
	margin-right: 2px;
	margin-bottom: 2px;
}
.top3
{
	background-color: #6CF;
	font-weight: bold;
}
.last3
{
	background-color:#FC3;
	font-weight:bold;
}
-->
</style></head>

<body>
<table border="1" align="center" cellpadding="3" cellspacing="0" bordercolordark="#ccc" bordercolorlight="#FFFFFF">
  
  <tr>
    <td align="center" bgcolor="#FFFFFF"><strong>username</strong></td>
    <td align="center" bgcolor="#FFFFFF"><strong>first name</strong></td>
    <td align="center" bgcolor="#FFFFFF"><strong>last name</strong></td>
    <td align="center" bgcolor="#FFFFFF"><strong>password</strong></td>
    </tr>
  <%
  sql1="select * from users where userid like '13__' and chatflag=1 and chatclose=1"
  set rs1=conn.execute(sql1)
  if not rs1.eof or not rs1.bof then
  while not rs1.eof
  %>
  <tr>
    <td align="center"><%="zz91-"&rs1("name")%></td>
    <td align="center"><%=rs1("realname")%></td>
    <td align="center"><%=left(rs1("realname"),1)%></td>
    <td align="center"><%
	password=rs1("password")
	if len(password)<6 then
		response.Write("123456")
	else
		response.Write(password)
	end if%></td>
    </tr>
  <%
  rs1.movenext
  wend
  end if
  rs1.close
  set rs1=nothing
  %>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
