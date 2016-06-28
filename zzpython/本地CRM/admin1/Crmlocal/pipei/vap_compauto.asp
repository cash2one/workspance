<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%
sql="select a.com_id,b.realname,a.personid from crm_assignvap as a,users as b where a.personid=b.id and b.closeflag=1 and not exists(select com_id from comp_vapinfo where a.com_id=com_id)"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
	sqla="select a.com_id,b.realname,a.personid from crm_assign as a,users as b where a.personid=b.id and b.closeflag=1 and a.com_id="&rs("com_id")&" "
	set rsa=conn.execute(sqla)
	if not rsa.eof or not rsa.bof then
		exianame=rsa("realname")
		personid1=rsa("personid")
	else
		exianame=""
	end if
	rsa.close
	set rsa=nothing
	personid=rs("personid")
	if cstr(personid1)<>cstr(personid) then
		sqld="select com_email from comp_info where com_id="&rs("com_id")&""
		set rsd=conn.execute(sqld)
		if not rsd.eof or not rsd.bof then
			com_email=rsd(0)
		else
			com_email=""
		end if
		rsd.close
		set rsd=nothing
	end if
	if exianame="临时用户" then
		sqlc="update crm_assign set personid="&personid&",fdate=getdate() where com_id="&rs("com_id")&""
		conn.execute(sqlc)
	end if
	if exianame="" then
		sqlb="select com_id from crm_assign where com_id="&rs("com_id")&""
		set rsb=conn.execute(sqlb)
		if rsb.eof or rsb.bof then
			sqlc="insert into crm_assign (com_id,personid) values("&rs("com_id")&","&rs("personid")&")"
			conn.execute(sqlc)
		else
			
			sqlc="delete from crm_assign where com_id="&rs("com_id")&""
			conn.execute(sqlc)
		end if
		rsb.close
		set rsb=nothing
	end if
	response.Write(rs("com_id")&rs("realname")&"-"&exianame&"-"&com_email&"<br>")
rs.movenext
wend
end if
rs.close
set rs=nothing
%>
</body>
</html>
<%
conn.close
set conn=nothing
%>
