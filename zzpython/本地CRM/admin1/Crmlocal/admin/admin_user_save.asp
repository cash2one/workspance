<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../../cn/sources/Md5.asp"-->
<%
userID=request("userid")
uname=trim(request("uname"))
realname=trim(request("realname"))
password=trim(request("password"))
set rs=server.createobject("adodb.recordset")
if request("m")="1" then
	sql="select * from users where id="&request("id")
	rs.open sql,conn,1,3
  
else

	sql="select * from users where name='"&request("uname")&"'"
	rs.open sql,conn,1,3
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
  rs("chat_userid")=userID
  rs("name")=trim(uname)
  rs("password")=trim(password)
  rs("safepass")=cstr(md5(password,16))
  rs("realname")=trim(realname)
  rs("nicheng")=trim(realname)
  rs("userdengji")=request.Form("userdengji")
  rs.update()
  rs.close()
  set rs=nothing
  
  sqlm="select max(id) from users where name='"&trim(uname)&"'"
	  set rsm=conn.execute(sqlm)
	  if not rsm.eof or not rsm.bof then
	  	maxid=rsm(0)
	  end if
	  rsm.close
	  set rsm=nothing
	  
	  set rs=server.createobject("adodb.recordset")
	  sql="select * from users_history where id is null"
	  rs.open sql,conn,1,3
	  rs.addnew()
	  rs("personid")=maxid
	  rs("userID")=userID
	  
	  rs.update()
	  rs.close()
	  set rs=nothing
  response.Redirect("admin_user_list.asp")
%>
<!-- #include file="../../../conn_end.asp" -->