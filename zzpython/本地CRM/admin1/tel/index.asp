<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!-- #include file="../include/ad!$#5V.asp" -->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30"><a href="add.asp">添加号码</a></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#333333">
  <tr>
    <td bgcolor="#FFFFFF">电话号码</td>
    <td bgcolor="#FFFFFF">IP</td>
    <td bgcolor="#FFFFFF">部门</td>
    <td bgcolor="#FFFFFF">操作</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <%
  sql="select * from astotel"
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("tel")%></td>
    <td bgcolor="#FFFFFF"><%=rs("ip")%></td>
    <td bgcolor="#FFFFFF"><%=rs("bm")%></td>
    <td bgcolor="#FFFFFF">修改  |  删除</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
</table>
</body>
</html>
