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
email=trim(request("email"))
emailpass=trim(request("emailpass"))
zdname=request("zdname")
set rs=server.createobject("adodb.recordset")
sql="select * from users where name='"&request("uname")&"' and name<>'"&request("uname1")&"'"
rs.open sql,conn,1,1
if not rs.eof then
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
  rs("chat_userid")=trim(request("chat_userid"))
  rs("userqx")=trim(request("userqx"))
  rs("adminuserid")=request.Form("adminuserid")
  
  rs("rongyi")=request("rongyi")
  rs("nicheng")=request("nicheng")
  rs("usertel")=request("usertel")
  if trim(request("userqx"))="1" then
  	rs("Partuserid")=request.Form("Partuserid")
  else
  	rs("Partuserid")=0
  end if
  rs("ywadminid")=request("ywadminid")
  rs("ywadminid_hy")=request("ywadminid_hy")
  if request("partid")<>"" then
  	rs("partid")=request("partid")
  end if
  'rs("chat_department")=request("chat_department")
  'rs("chat_warden")=request("chat_warden")
  'rs("popchat_department")=request("popchat_department")
  'rs("popchat_warden")=request("popchat_warden")
  'rs("popchat_all")=request.Form("popchat_all")
  'rs("chat_partuserid")=request.Form("chat_partuserid")
  rs("recordflag")=request.Form("recordflag")
  rs("userdengji")=request.Form("userdengji")
  if request("huangye_check")<>"" then
  	rs("huangye_check")=request("huangye_check")
  else
  	rs("huangye_check")=0
  end if
  rs.update()
  rs.close()
  set rs=nothing
  
  'sqlz="select * from userzd where personid="&request("id")
'  set rsz=conn.execute(sqlz)
'  if rsz.eof or rsz.bof then
'  	 sqlzz="insert into userzd(personid,zdname) values('"&request("id")&"','"&zdname&"')"
'  else
'    sqlzz="update userzd set zdname='"&zdname&"' where personid="&request("id")&""
'  end if
'  conn.execute(sqlzz)
'  rsz.close
'  set rsz=nothing
  
  '--------是否掉公海
  if request("gonghaiflag")="0" then
  	  sqlz="select * from crm_notdropTogonghai where personid="&request("id")
	  set rsz=conn.execute(sqlz)
	  if rsz.eof or rsz.bof then
		sqlzz="insert into crm_notdropTogonghai(personid,uname) values('"&request("id")&"','"&trim(uname)&"')"
		conn.execute(sqlzz)
	  else
		'sqlzz="update crm_notdropTogonghai set uname='"&uname&"' where personid="&request("id")&""
	  end if
	  
	  rsz.close
	  set rsz=nothing
  else
  	  sqlzz="delete from crm_notdropTogonghai where personid="&request("id")&""
	  conn.execute(sqlzz)
  end if
  
  arrOther=split(request.Form("adminuserid"),",")
  
  sqld="delete from crm_adOtherqx where personid="&request("id")
  conn.execute(sqld)
  for i=0 to ubound(arrOther)
  	sql="select * from crm_adOtherqx where userid="&trim(arrOther(i))&" and personid="&request("id")
	set rs=conn.execute(sql)
	if rs.eof or rs.bof then
		sqln="insert into crm_adOtherqx(personid,userid) values("&request("id")&","&trim(arrOther(i))&")"
		conn.execute(sqln)
	end if
	rs.close
	set rs=nothing
  next
  response.Write("<script>window.close()</script>")
%>
<!-- #include file="../../../conn_end.asp" -->