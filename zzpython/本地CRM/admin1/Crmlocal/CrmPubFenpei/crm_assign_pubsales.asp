<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
usrs=request.Form("user")
if request.Form("assignpublic")="1" then
	
	if usrs<>"" then
		sql="update users set assignpub=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignpub=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
	else
		sql="update users set assignpub=0 "
		conn.execute(sql)
	end if
end if
if request.Form("assignpeiming")="1" then
	if usrs<>"" then
		sql="update users set assignpeiming=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignpeiming=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
	else
		sql="update users set assignpeiming=0"
		conn.execute(sql)
	end if
end if
if request.Form("assigngonghai")="1" then
	if usrs<>"" then
		sql="update users set assigngonghai=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assigngonghai=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
	else
		sql="update users set assigngonghai=0"
		conn.execute(sql)
	end if
end if

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ͻ�����</title>
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
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#000000">
  <form name="form2" method="post" action="">
 <%
sqlu="select code,meno from cate_adminuser where (code like '13%' or code like '24%') and closeflag=1"
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
	sql="select id,realname,assignpeiming from users where userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	%>
      <input name="user" type="checkbox" <%if rs("assignpeiming")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
		if (confirm("ȷʵ������"))
		{
			frm.submit()
		}
	}
	</script>
      <input type="button" class=button name="Submit" value="���÷�����б" onClick="fenp(this.form)">
      <input name="assignpeiming" type="hidden" id="assignpeiming" value="1"></td>
  </tr></form>
</table>
<br>
<br>
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#000000">
<form name="form1" method="post" action="">
 <%
sqlu="select code,meno from cate_adminuser where (code like '13%' or code like '24%') and closeflag=1"
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
	sql="select id,realname,assignpub from users where userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	%>
      <input name="user" type="checkbox" <%if rs("assignpub")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
	
      <input type="button" class=button name="Submit" value="���÷�����Ա" onClick="fenp(this.form)">
      <input name="assignpublic" type="hidden" id="assignpublic" value="1"></td>
  </tr>
  </form>
</table>
<br>
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#000000">
  <form name="form1" method="post" action="">
    <%
sqlu="select code,meno from cate_adminuser where (code like '13%' or code like '24%')  and closeflag=1"
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
	sql="select id,realname,assigngonghai from users where userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	%>
        <input name="user" type="checkbox" <%if rs("assigngonghai")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
        
        <input type="button" class=button name="Submit" value="���ù���������Ա" onClick="fenp(this.form)">
      <input name="assigngonghai" type="hidden" id="assigngonghai" value="1"></td>
    </tr>
  </form>
</table>
<script>
if (request.Form("user")=="")
{
	form1.submit()
}
</script>
</body>
</html>
