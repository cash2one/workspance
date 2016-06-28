<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
if request.Form("user")<>"" then
 		sql="update users set assignflag=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignflag=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		arruser=split(request.Form("id"),",",-1,1)
		arrcount=split(request.Form("vipcount"),",",-1,1)
		for i=0 to ubound(arruser)
		sql="update users set vipcount="&trim(arrcount(i))&" where id="&trim(arruser(i))&""
		conn.execute(sql)
		next
		response.Write("设置成功")
end if

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
td {
	font-size: 14px;
}
-->
</style>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="9">
  <form name="form1" method="post" action="">
<%
sqlu="select code,meno from cate_adminuser where code like '13__'"
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
while not rsu.eof
%>
<tr>
<%
    sql="select id,realname,assignflag,vipcount from users where userid="&rsu("code")&" and closeflag=1"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	while not rs.eof
	%>
	<td align="center" bgcolor="#FFFFFF">
	<input name="user" type="checkbox" <%if rs("assignflag")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
	<br>
	<input name="id" type="hidden" id="id" value="<%=rs("id")%>">
	<input name="vipcount" type="text" size="5" value="<%response.Write(rs("vipcount"))%>">
	</td>
	<%
	rs.movenext
	wend
	end if
	rs.close
	set rs=nothing
	%>	
    
</tr>
<%
rsu.movenext
wend
end if
rsu.close
set rsu=nothing

%>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF">
      <input type="submit" name="Submit" value=" 设 置 "></td>
  </tr>
  </form>
</table>
</body>
</html>
