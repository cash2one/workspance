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
set rs=server.createobject("adodb.recordset")
if request("m")="1" then
sql="select * from users where id="&request("id")
rs.open sql,conn,1,2
  
else

sql="select * from users where name='"&request("uname")&"'"
rs.open sql,conn,1,2
if rs.eof then
rs.addnew
else
response.write "<script languang='javascript'>alert('该用户已经存在！');</script>"
response.write "<script languang='javascript'>javascript:history.back(1);</script>"
response.End()
end if  
  rs("userqx")="0"
end if 
  rs("userID")=userID
  rs("name")=trim(uname)
  rs("password")=trim(password)
  rs("safepass")=cstr(md5(password,16))
  rs("realname")=trim(realname)
  rs.update()
  rs.close()
  set rs=nothing
  response.Redirect("admin_user_list.asp?userid="&userID)
%>
<!-- #include file="../../conn_end.asp" -->