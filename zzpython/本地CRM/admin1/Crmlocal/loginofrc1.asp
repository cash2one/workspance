<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<!--#include file="../../cn/sources/Md5.asp"-->
<%
uid=trim(request.form("uid"))
pwd=trim(request.form("pwd"))
localip=Request.ServerVariables("REMOTE_ADDR")
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
rf=ucase(request.form)
	if instr(rf,"'")>0 or instr(rf,"%27")>0 or instr(rf,";")>0 or instr(rf,"%3B")>0 then
		response.redirect "loginofrc.asp"
		response.end
end if
sql="select * from users where name='" & uid & "' and Password='" & pwd & "' and safepass='"&cstr(md5(pwd,16))&"'"
set rs = conn.Execute(sql)
if not (rs.eof or err) then
	    if rs("closeflag")=0 then
		response.write "<script language='javascript'>alert('´íÎó£¬ÄúµÄÕÊºÅÒÑ±»¹Ø±Õ!');window.location.href='loginofrc.asp?uid="&uid&"';</script>"
		response.End()
		else
		Response.Cookies("personid")=rs("id")
		Response.Cookies("userID")=rs("userid")
		Response.Cookies("admin_user")=rs("name")
		Response.Cookies("littleuserID")=rs("userid")
		Response.Cookies("userClass")=rs("userqx")
		Response.Cookies("Partadmin")=rs("partuserid")
		session("personid")=rs("id")
		session("userID")=rs("userid")
		session("admin_user")=trim(rs("name"))
		session("littleuserID")=rs("userid")
		session("userClass")=trim(rs("userqx"))
		session("Partadmin")=trim(rs("partuserid"))
		response.redirect "main.asp"
		end if
else
response.write "<script language='javascript'>alert('´íÎó£¬ÇëºË¶Ô!');window.location.href='login.asp?uid="&uid&"';</script>"
response.end
rs.close
set rs=nothing
end if
rs.close
set rs=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ÕıÔÚµÇÂ¼</title>
</head>

<body>

</body>
</html>