<%@ Language=VBScript %>
<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../../cn/sources/Md5.asp"-->
<%
userID=request("userid")
uname=trim(request("uname"))
realname=trim(request("realname"))
password=trim(request("password"))
email=trim(request("email"))
emailpass=trim(request("emailpass"))
set rs=server.createobject("adodb.recordset")
    sql="select * from users where name='"&request("uname")&"' and name<>'"&request("uname1")&"'"
    rs.open sql,conn,1,2
	if not rs.eof then
	response.write "<script languang='javascript'>alert('该用户已经存在！');</script>"
	response.write "<script languang='javascript'>javascript:history.back(1);</script>"
	response.End()
	end if
	rs.close()
	set rs=server.createobject("adodb.recordset")
sql="select * from users where id="&request("id")
rs.open sql,conn,1,2
  rs("userID")=userID
  rs("name")=trim(uname)
  rs("password")=trim(password)
  rs("safepass")=cstr(md5(password,16))
  rs("realname")=trim(realname)
  rs("userbg")=trim(request("userbg"))
  rs("userqx")=trim(request("userqx"))
  rs("adminuserid")=request.Form("adminuserid")
  rs("email")=email
  rs("emailpass")=emailpass
  rs.update()
  rs.close()
  set rs=nothing
  
  
  response.Redirect("admin_user_list.asp?userid="&userID)
%>
<!-- #include file="../../conn_end.asp" -->