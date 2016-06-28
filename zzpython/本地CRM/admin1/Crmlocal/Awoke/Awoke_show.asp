<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
sql="select * from crm_awoke where id="&request.QueryString("id")
set rs=conn.execute(sql)
if rs.eof then
response.Write("无信息！")
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>最新提醒</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
.titl {
	font-size: 16px;
	line-height: 20px;
	font-weight: bold;
	background-color: #FFCC00;
	border-top-width: 3px;
	border-top-style: solid;
	border-top-color: #E1B500;
}
.title {
	font-size: 14px;
	font-weight: bold;
	text-decoration: underline;
}
td {
	line-height: 22px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td bgcolor="#FFCC00" class="titl"><strong>最新提醒</strong></td>
  </tr>
  <tr>
    <td align="center" class="title"><%=rs("ttitle")%></td>
  </tr>
  <tr>
    <td><%=rs("tcontent")%></td>
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