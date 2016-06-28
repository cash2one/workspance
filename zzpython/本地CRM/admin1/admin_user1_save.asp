<%@ Language=VBScript %>
<!-- #include file="include/ad!$#5V.asp" -->
<!-- #include file="localjumptolog.asp" -->
<!--#include file="include/include.asp"-->
<!--#include file="../cn/sources/Md5.asp"-->
<%
uname=trim(request("uname"))
uname1=trim(request("uname1"))
realname=trim(request("realname"))
password=trim(request("password"))
password1=trim(request("password1"))
set rs=server.createobject("adodb.recordset")

    sql="select * from users where name='"&request("uname")&"' and name<>'"&request("uname1")&"'"
    rs.open sql,conn,1,1
	if not rs.eof then
	response.write "<script languang='javascript'>alert('该用户已经存在！');</script>"
	response.write "<script languang='javascript'>javascript:history.back(1);</script>"
	rs.close()
	set rs=nothing
	response.End()
	
	end if
	rs.close()
	set rs=nothing
	set rs=server.createobject("adodb.recordset")
	sql="select * from users where id="&session("personid")
    rs.open sql,conn,1,1
	if trim(password)<>trim(rs("password")) then
	response.write "<script languang='javascript'>alert('原密码有错误！');</script>"
	response.write "<script languang='javascript'>javascript:history.back(1);</script>"
	rs.close()
	set rs=nothing
	response.End()
	end if
	rs.close()
	set rs=nothing
	set rs=server.createobject("adodb.recordset")
  sql="select * from users where id="&session("personid")
  rs.open sql,conn,1,3
  rs("name")=trim(uname)
  rs("password")=trim(password1)
  rs("realname")=trim(realname)
  rs("SafePass")=trim(md5(password1,16))
  rs.update()
  rs.close()
  set rs=nothing
  response.Write("修改成功")

%><!-- #include file="../conn_end.asp" -->
