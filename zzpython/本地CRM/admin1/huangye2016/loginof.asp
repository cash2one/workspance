<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../../cn/sources/Md5.asp"-->
<%
uid=trim(request.form("uid"))
pwd=trim(request.form("pwd"))
localip=Request.ServerVariables("REMOTE_ADDR")
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if mid(server_v1,8,len(server_v2))<>server_v2 then
	response.write "����<br>"
	response.end
end if
rf=ucase(request.form)
if instr(rf,"'")>0 or instr(rf,"%27")>0 or instr(rf,";")>0 or instr(rf,"%3B")>0 then
	response.redirect "login.asp"
	response.end
end if'

'-------------------
sql="select * from users where name='" & uid & "' and Password='" & pwd & "' and safepass='"&cstr(md5(pwd,16))&"'"
set rs = conn.Execute(sql)
if not rs.eof or not rs.bof then
	Response.Cookies("personid")=rs("id")
	Response.Cookies("UserId")=rs("userid")
	Response.Cookies("admin_user")=rs("name")
	Response.Cookies("littleuserID")=rs("userid")
	Response.Cookies("userClass")=rs("userqx")
	Response.Cookies("Partadmin")=rs("partuserid")
	session("PersonId")=rs("id")
	session("UserId")=rs("userid")
	session("admin_user")=trim(rs("name"))
	session("littleuserID")=rs("userid")
	session("userClass")=trim(rs("userqx"))
	session("Partadmin")=trim(rs("partuserid"))
	session("UserName")=trim(rs("name"))
	session("UserMeno")=rs("userid")
	response.redirect "list.asp"
else
	response.write "<script language='javascript'>alert('������˶�!');window.location.href='login.asp?uid="&uid&"';</script>"
	response.end
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>���ڵ�¼</title>
</head>

<body>

</body>
</html>