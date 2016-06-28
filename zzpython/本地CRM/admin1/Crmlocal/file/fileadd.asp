<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="500" border="0" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <form action="fileadd_save.asp" method="post" enctype="multipart/form-data" name="form1" id="form1">
    <tr>
    <td width="100" align="right" bgcolor="#FFFFFF">文件名称：</td>
    <td bgcolor="#FFFFFF"><input type="text" name="filename" id="filename" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">部门：</td>
    <td bgcolor="#FFFFFF">
    <select name="userid" id="userid">
    <%
	sql="select code,meno from cate_adminuser where code like '13__'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	while not rs.eof
	%>
      <option value="<%=rs("code")%>"><%=rs("meno")%></option>
    <%
	rs.movenext
	wend
	end if
	rs.close
	set rs=nothing
	%>
    </select>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">文件：</td>
    <td bgcolor="#FFFFFF"><input type="file" name="fileField" id="fileField" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">
      <input type="submit" name="button" id="button" value="提交" />
    
    </td>
  </tr></form>
</table>
</body>
</html>
