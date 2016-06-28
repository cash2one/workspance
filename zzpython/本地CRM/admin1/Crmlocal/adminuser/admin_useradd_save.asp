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
ktqx=request.Form("ktqx")

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
    sql="select * from users where name='"&uname&"'"
    rs.open sql,conn,1,1
	if not rs.eof or not rs.bof then
		response.write "<script languang='javascript'>alert('该用户已经存在！');</script>"
		response.write "<script languang='javascript'>javascript:history.back(1);</script>"
		response.End()
	end if
	rs.close()
	set rs=server.createobject("adodb.recordset")
	
  sql="select * from users where id is null"
  rs.open sql,conn,1,3
  rs.addnew()
  rs("userID")=userID
  rs("chat_userID")=userID
  rs("name")=trim(uname)
  rs("password")=trim(password)
  rs("safepass")=cstr(md5(password,16))
  rs("realname")=trim(realname)
  rs("nicheng")=trim(realname)
  rs("closeflag")=trim(closeflag)
  if ktqx="1" then
  	userdengji="10010001,10010019"
  end if
  if ktqx="2" then
  	userdengji="10010001,10010002"
  end if
  if ktqx="3" then
  	userdengji="10010008"
  end if
  rs("userdengji")=userdengji
  rs("recordflag")=1

  rs.update()
  rs.close()
  set rs=nothing
  
  sqlm="select max(id) from users where name='"&trim(uname)&"'"
  set rsm=conn.execute(sqlm)
  if not rsm.eof or not rsm.bof then
  	maxpersonid=rsm(0)
  end if
  rsm.close
  set rsm=nothing
  
  set rs=server.createobject("adodb.recordset")
  sql="select * from users_history where id is null"
  rs.open sql,conn,1,3
  rs.addnew()
  rs("personid")=maxpersonid
  rs("userID")=userID
  
  rs.update()
  rs.close()
  set rs=nothing
  
 
  if userID="23" then
  	  response.Redirect("userlist.asp?touserid=23")
  else
  	  response.Redirect("userlist.asp")
  end if
  
%>
<!-- #include file="../../../conn_end.asp" -->