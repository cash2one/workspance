<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../../cn/sources/Md5.asp"-->
<%
uid=trim(request.form("uid"))
pwd=trim(request.form("pwd"))
com_id=request.Form("com_id")
mbflag=request.Form("mbflag")
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
end if'

'-------------------
sql="select * from users where name='" & uid & "' and Password='" & pwd & "' and safepass='"&cstr(md5(pwd,16))&"' and closeflag=1"
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
	response.redirect "http://adminasto.zz91.com/openConfirm1/?com_id="&com_id&"&userid="&rs("userid")&"&mbflag="&mbflag&"&personid="&rs("id")&"&username="&rs("realname")&""
else
	response.write "<script language='javascript'>alert('错误，请核对!');</script>"
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"';</script>")
	response.end
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>