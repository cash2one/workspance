<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
if request.Form("weekflag")="1" and request.Form("user")<>"" then
		sql="update users set assignzhiban=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignzhiban=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
end if 
if request.Form("user")="" and request.Form("weekflag")="1" then
sql="update users set assignzhiban=0"
conn.execute(sql)
end if
if request.Form("weekflag")="0" and request.Form("user")<>"" then
		sql="update users set assignzhiban1=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignzhiban1=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
end if 
if request.Form("user")="" and request.Form("weekflag")="0" then
sql="update users set assignzhiban1=0"
conn.execute(sql)
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" align="center"><strong>ֵ����Ա�ͻ���������</strong></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
<form name="form1" method="post" action="">
  <tr>
    <td height="30" bgcolor="#FFFFFF"><strong><font color="#FF0000">������</font></strong></td>
  </tr>
 <%
sqlu="select code,meno from cate_adminuser where code like '13__'"
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
while not rsu.eof
%>
  <tr>
    <td bgcolor="#FFFFFF">
	<%
	sql="select id,realname,assignzhiban from users where  userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" <%if rs("assignzhiban")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
	if (confirm("ȷʵ��ʼ������"))
	{
	frm.submit()
	}
	}
	</script>
      <input type="button" class=button name="Submit" value="����" onClick="fenp(this.form)">
      <input name="weekflag" type="hidden" id="weekflag" value="1"></td>
  </tr></form>
</table>
<br>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
<form name="form2" method="post" action="">
  <tr>
    <td height="30" bgcolor="#FFFFFF"><strong><font color="#FF0000">������</font></strong></td>
  </tr>
 <%
sqlu="select code,meno from cate_adminuser where code like '13__'"
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
while not rsu.eof
%>
  <tr>
    <td bgcolor="#FFFFFF">
	<%
	sql="select id,realname,assignzhiban1 from users where userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" <%if rs("assignzhiban1")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
      <input type="button" class=button name="Submit" value="����" onClick="fenp(this.form)">
      <input name="weekflag" type="hidden" id="weekflag" value="0"></td>
  </tr></form>
</table>
<div align="center"><br>
  <input type="button" name="Submit" value="�����ڼ���ֵ�����" onClick="window.location='other_zhiban.asp'">
  <br>
  <br>
  <br>
  <%
  if request("zhibanflag")<>"" then
	  sqlu="update crm_zhibanflag set zhibanflag="&request("zhibanflag")&" where id=1"
	  conn.execute(sqlu)
  end if
  sql="select * from crm_zhibanflag where id=1"
  set rs=conn.execute(sql)
  if not rs.eof then
  	zhibanflag=rs("zhibanflag")
  end if
  rs.close
  set rs=nothing
  %>
  <%if zhibanflag="1" then%>
   <input type="button" name="Submit" value="�������ֵ��  �ѿ���" onClick="window.location='?zhibanflag=0'">
  <%else%>
   <input type="button" name="Submit" value="�������ֵ��  �ѹر�" onClick="window.location='?zhibanflag=1'" style="color:#FF0000 ">
  <%end if%>
   <br>
</div>
</body>
</html>
<%
conn.close
set conn=nothing
%>
