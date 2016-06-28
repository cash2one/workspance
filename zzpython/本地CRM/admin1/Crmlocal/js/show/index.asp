<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="">
<%
sql="select code,meno from cate_products_ly where code like '__'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10">&nbsp;</td>
    <td><input type="checkbox" name="checkbox" id="checkbox" value="<%=rs("code")%>" /><%=rs("meno")%></td>
  </tr>
</table>
<%
sql1="select code,meno from cate_products_ly where code like '"&rs("code")&"__'"
set rs1=conn.execute(sql1)
if not rs1.eof or not rs1.bof then
while not rs1.eof
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="30">&nbsp;</td>
    <td><input type="checkbox" name="checkbox" id="checkbox" value="<%=rs1("code")%>" /><%=rs1("meno")%></td>
  </tr>
</table>
<%
rs1.movenext
wend
end if
rs1.close
set rs1=nothing
%>
<%
rs.movenext
wend
end if
rs.close
set rs=nothing
%>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
