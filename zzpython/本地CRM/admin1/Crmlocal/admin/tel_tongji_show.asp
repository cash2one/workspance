<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
sql="select sdate,content from crm_tel_content where id="&request.QueryString("id")
set rs=conn.execute(sql)
if rs.eof then
response.Write("Error!")
conn.close
set conn=nothing
response.End()
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%response.Write(rs("sdate"))%>客户统计</title>
<style type="text/css">
<!--
td {
	font-size: 12px;
}
-->
</style>
</head>

<body>
<%response.Write(rs("content"))%>
</body>
</html>
<%
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
