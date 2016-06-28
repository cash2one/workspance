<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="include/adfsfs!@#.asp" -->
<!--#include file="include.asp"-->
<!--#include file="../cn/sources/Md5.asp"-->
<!-- #include file="bjb_conn.asp" -->
<%
uid=trim(request.form("username"))
pwd=trim(request.form("passwd"))
localip=Request.ServerVariables("REMOTE_ADDR")

server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if mid(server_v1,8,len(server_v2))<>server_v2 then
response.write "错误<br>"
response.end
end if


rf=ucase(request.form)
	if instr(rf,"'")>0 or instr(rf,"%27")>0 or instr(rf,";")>0 or instr(rf,"%3B")>0 then
		response.redirect "login.asp"
		response.end
end if
	
sql="select * from users where name='" & uid & "' and Password='" & pwd & "' and safepass='"&cstr(md5(pwd,16))&"'"
set rs = conn.Execute(sql)
'jycode()'校验码
if not (rs.eof or err) then
	    if rs("closefalg")=0 then
		response.write "<script language='javascript'>alert('错误，您的帐号已被关闭!');window.location.href='login.asp?uid="&uid&"';</script>"
		response.End()
		else
		Response.Cookies("personid")=rs("id")
		Response.Cookies("userID")=rs("userid")
		Response.Cookies("admin_user")=rs("name")
		Response.Cookies("littleuserID")=rs("userid")
		Response.Cookies.Expires=600000*600000
		session("personid")=rs("id")
		session("userID")=rs("userid")
		session("admin_user")=trim(rs("name"))
		session("littleuserID")=rs("userid")
		session("userClass")=trim(rs("userqx"))
		sqlip="insert into loginip (ip,personid) values('"&localip&"','"&session("personid")&"')"
        connmain.execute(sqlip)
		response.redirect "cp_main.asp"
		end if
else
response.write "<script language='javascript'>alert('错误，请核对!');window.location.href='login.asp?uid="&uid&"';</script>"
rs.close
set rs=nothing
response.end
end if
rs.close
set rs=nothing

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>正在登录</title>
</head>

<body>

</body>
</html>