<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="include/adfsfs!@#.asp" -->
<!-- #include file="../cn/function.asp" -->
<!--#include file="../cn/sources/Md5.asp"-->
<%
uid=trim(request.form("uid"))
pwd=trim(request.form("pwd"))
localip=Request.ServerVariables("REMOTE_ADDR")
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if mid(server_v1,8,len(server_v2))<>server_v2 then
	response.write "´íÎó<br>"
	response.end
end if
rf=ucase(request.form)
if instr(rf,"'")>0 or instr(rf,"%27")>0 or instr(rf,";")>0 or instr(rf,"%3B")>0 then
		response.redirect "loginofrc.asp"
		response.end
end if'
'----ÅĞ¶ÏÃÜÂëÊÇ·ñ°²È«
sql="select password from users where name='" & uid & "' and safepass='"&cstr(md5(pwd,16))&"'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	password=rs(0)
	if IsNumeric(password) then
		errpass=1
		response.Redirect("changepasswd.asp?uid="&uid&"&errpass="&errpass)
		response.End()
	end if
	if len(password)<6 then
		errpass=2
		response.Redirect("changepasswd.asp?uid="&uid&"&errpass="&errpass)
		response.End()
	end if
	
	aaa=0
	for i=1 to len(password)
		str=password
		if IsNumeric(mid(str,i,1)) then
			aaa=aaa+1
		end if
	next
	if aaa=0 then
		errpass=3
		response.Redirect("changepasswd.asp?uid="&uid&"&errpass="&errpass)
		response.End()
	end if
end if
rs.close
set rs=nothing
'-------------------
sql="select * from users where name='" & uid & "' and Password='" & pwd & "' and safepass='"&cstr(md5(pwd,16))&"'"
set rs = conn.Execute(sql)
'jycode()
if not rs.eof or not rs.bof then
	    if rs("closeflag")=0 then
		response.write "<script language='javascript'>alert('´íÎó£¬ÄúµÄÕÊºÅÒÑ±»¹Ø±Õ!');window.location.href='loginofrc.asp?uid="&uid&"';</script>"
		response.End()
		else
		Response.Cookies("personid")=rs("id")
		Response.Cookies("UserId")=rs("userid")
		Response.Cookies("admin_user")=rs("name")
		Response.Cookies("littleuserID")=rs("userid")
		Response.Cookies("userClass")=rs("userqx")
		Response.Cookies("Partadmin")=rs("partuserid")
		'Response.Cookies("personid").Expires=600000*600000
		session("PersonId")=rs("id")
		session("UserId")=rs("userid")
		session("admin_user")=trim(rs("name"))
		session("littleuserID")=rs("userid")
		session("userClass")=trim(rs("userqx"))
		session("Partadmin")=trim(rs("partuserid"))
		
		session("UserName")=trim(rs("name"))
		session("UserMeno")=rs("userid")
		if rs("userid")="10" then
			session("UserQx")=0
		end if
		if rs("partuserid")>0 then
			session("UserQx")=1
		end if
		if rs("userid")<>"10" and rs("partuserid")="0" then
			session("UserQx")=2
		end if
		sql="insert into crm_adminlogin(personid,loginIP) values("&session("personid")&",'"&localip&"')"
		conn.execute(sql)
		response.redirect "cp_main.asp"
		end if
else
	response.write "<script language='javascript'>alert('´íÎó£¬ÇëºË¶Ô!');window.location.href='loginofrc.asp?uid="&uid&"';</script>"
	response.end
	rs.close
	set rs=nothing
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ÕıÔÚµÇÂ¼</title>
</head>

<body>

</body>
</html>