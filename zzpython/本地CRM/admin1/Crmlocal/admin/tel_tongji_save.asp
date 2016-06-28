<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
sql="select * from crm_tel_content where sdate='"&date()&"'"
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,3
if rs.eof then
rs.addnew()
rs("sdate")=date()
rs("content")=request.Form("tabinfo")
rs.update()
response.Write("<script>alert('保存成功！');window.history.back(1)</script>")
response.end()
else
rs("content")=request.Form("tabinfo")
rs.update()
response.Write("<script>alert('该天信息已经保存！');window.history.back(1)</script>")
response.end()
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
</head>

<body>
</body>
</html>
