<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="80%" border="0" align="center" cellpadding="5" cellspacing="0">
  <form id="form1" name="form1" method="post" action="Awoke_save.asp">
  <tr>
    <td width="80" align="right">标题： </td>
    <td>
      <input name="Ttitle" type="text"  class=text id="Ttitle"  size="50" />    </td>
  </tr>
  <tr>
    <td align="right">部门：</td>
    <td>
    
    <input name="Part" type="radio" id="radio" value="1" checked="checked" />
      A 区
        <input type="radio" name="Part" id="radio2" value="2" />
      B 区</td>
  </tr>
  <tr>
    <td align="right">时间： </td>
    <td><script language=javascript>createDatePicker("Tdate",true,"<%=now+0.005%>",false,false,false,true)</script></td>
  </tr>
  <%
  if session("userClass")="1" then
  %>
  <!--
  <tr>
    <td>给谁提醒:</td>
    <td>
	<%
	sql="select id,realname,assignpub from users where userid=13 and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
	<%
	rs.movenext
	loop
	end if
	rs.close
	set rs=nothing
	%> 
	</td>
  </tr>
  -->
  <%
  end if
  %>
  <tr>
    <td align="right">目标详情：</td>
    <td><textarea name="Tcontent" rows="10" id="Tcontent"></textarea></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="Submit" value=" 保 存 " class=button /></td>
  </tr>
  </form>
</table>
</body>
</html>
