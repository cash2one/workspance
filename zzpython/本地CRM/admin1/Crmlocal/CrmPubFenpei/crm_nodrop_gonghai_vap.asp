<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
if request.Form("user")<>"" and request.Form("assignpublic")="1" then
	conn.execute("delete from crm_notdropTogonghai_vap")
	sql="insert into crm_notdropTogonghai_vap(personid) select id from users where id in ("&trim(request.Form("user"))&")"
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
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#000000">
<form name="form1" method="post" action="">
 <%
sqlu="select code,meno from cate_adminuser where (code like '13%' and closeflag=1) or code like '26%'"
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
	sql="select id,realname from users where userid='"&rsu("code")&"' and closeflag=1 and chatclose=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	
				  sqlg="select personid from crm_notdropTogonghai_vap where personid="&rs("id")&""
				  set rsg=conn.execute(sqlg)
				  if not rsg.eof or not rsg.bof then
				  	gonghaiflag="1"
				  else
				  	gonghaiflag="0"
				  end if
				  rsg.close
				  set rsg=nothing
				  
	%>
      <input name="user" type="checkbox" <%if gonghaiflag="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
      <input type="button" class=button name="Submit" value="设置人员" onClick="fenp(this.form)">
      <input name="assignpublic" type="hidden" id="assignpublic" value="1"></td>
  </tr>
  <tr>
    <td height="30" align="center" bgcolor="#F2F2F2" style="color:#F00">注明：打勾不掉公海</td>
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
