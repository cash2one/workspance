<%
localip=Request.ServerVariables("REMOTE_ADDR")
posttype=Request.ServerVariables("REQUEST_METHOD")
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if posttype="post" then
	if mid(server_v1,8,len(server_v2))<>server_v2 then
		response.write "´íÎó<br>"
		response.end
	end if
end if
if session("personid")="" or isnull(session("personid")) then
	if Request.Cookies("personid")<>"" then
		session("userID")=Request.Cookies("userID")
		session("personid")=Request.Cookies("personid")
		session("lmcode")=Request.Cookies("lmcode")
		session("admin_user")=request.Cookies("admin_user")
		session("Partadmin")=Request.Cookies("Partadmin")
		session("userClass")=Request.Cookies("userClass")
	else
	response.Write("<script>window.location='login.asp';</script>")
	%>
	<a href="login.asp" target="_parent">ÖØÐÂµÇÂ¼</a><%
	response.End()
	end if
end if
%>