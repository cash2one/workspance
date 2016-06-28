<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../../cn/sources/Md5.asp"-->
<%
userID=request.Form("userid")
uname=trim(request.Form("uname"))
realname=trim(request.Form("realname"))
password=trim(request.Form("password"))
email=trim(request.Form("email"))
emailpass=trim(request.Form("emailpass"))
chatclose=trim(request.Form("chatclose"))
closeflag=trim(request.Form("closeflag"))
usertel=trim(request.Form("usertel"))

sqlp="select partid from cate_adminuser where code='"&userid&"'"
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
	partid=rsp(0)
else
	partid=""
end if
rsp.close
set rsp=nothing

    set rs=server.createobject("adodb.recordset")
    sql="select * from users where name='"&uname&"' and name<>'"&request.Form("uname1")&"'"
    rs.open sql,conn,1,1
	if not rs.eof or not rs.bof then
		response.write "<script languang='javascript'>alert('该用户已经存在！');</script>"
		response.write "<script languang='javascript'>javascript:history.back(1);</script>"
		response.End()
	end if
	rs.close()
	set rs=server.createobject("adodb.recordset")
  sql="select * from users where id="&request("id")
  rs.open sql,conn,1,3
  rs("userID")=userID
  rs("name")=trim(uname)
  rs("password")=trim(password)
  rs("safepass")=cstr(md5(password,16))
  rs("realname")=trim(realname)
  rs("closeflag")=trim(closeflag)
  rs("closepart")=0
  rs("usertel")=trim(usertel)
  if request("rongyi")<>"" then
  	rs("rongyi")=request.Form("rongyi")
  end if
  rs.update()
  rs.close()
  set rs=nothing
  response.Write("<script>window.close()</script>")
%>
<!-- #include file="../../../conn_end.asp" -->