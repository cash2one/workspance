<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
if request.Form("user")<>"" then
		sql="update users set xuQianFlag=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set xuQianFlag=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>客户分配</title>
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
</head>

<body>
<br>
<br>
<br>
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#000000">
<form name="form1" method="post" action="">
 <%
sqlu="select code,meno from cate_adminuser where code like '13%'"
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
while not rsu.eof
%>
  <tr>
    <td bgcolor="#E8E8E8"><%=rsu("meno")%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">
	<%
	sql="select id,realname,xuQianFlag from users where userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" <%if rs("xuQianFlag")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
	<%
	rs.movenext
	loop
	end if
	rs.close
	set rs=nothing
	%>    	</td>
  </tr>
<%
rsu.movenext
wend
end if
rsu.close
set rsu=nothing
%>
  <tr>
    <td align="center" bgcolor="#F2F2F2">
	<script>
	function fenp(frm)
	{
	if (confirm("确实设置吗？"))
	{
	frm.submit()
	}
	}
	</script>
      <input type="button" class=button name="Submit" value="设  置" onClick="fenp(this.form)">    </td>
  </tr></form>
</table>
</body>
</html>
