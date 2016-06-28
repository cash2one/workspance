<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<%
doaction=request.Form("doaction")
if doaction="add" then
	tel=request.Form("tel")
	ip=request.Form("ip")
	bm=request.Form("bm")
	sql="select * from astotel where id is null"
	set rs=server.CreateObject("ADODB.recordset")
	rs.open sql,conn,1,3
	if rs.eof or rs.bof then
		rs.addnew
		rs("com_id")=com_id
	end if
end if
%>
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

<body><form id="form1" name="form1" method="post" action="add.asp">
<table width="500" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#333333">
  <tr>
    <td width="100" bgcolor="#FFFFFF">电话号码</td>
    <td bgcolor="#FFFFFF">
      <input type="text" name="tel" id="tel" />
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">ip</td>
    <td bgcolor="#FFFFFF"><input type="text" name="ip" id="ip" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">部门</td>
    <td bgcolor="#FFFFFF"><input type="text" name="bm" id="bm" />
      <input type="hidden" name="doaction" id="doaction" value="add" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="提交" /></td>
  </tr>
</table>
</form>
</body>
</html>
