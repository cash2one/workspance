<!-- #include file="../include/adfsfs!@#.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%
sql="select count(0) from v_groupcomid where personid=128"
set rs=conn.execute(sql)
response.Write(rs(0))
response.End()
sql="select * from v_groupcomid where personid=128"
set rs=conn.execute(sql)
if not rs.eof then
while not rs.eof
	sqlc="select com_id from crm_assign where com_id="&rs("com_id")
	set rsc=conn.execute(sqlc)
		if rsc.eof then
			sqlt="insert into crm_assign(com_id,personid) values("&rs("com_id")&",128)"
			conn.execute(sqlt)
			sqle="delete from Crm_PublicComp where com_id="&rs("com_id")
			conn.execute(sqle)
		end if
		rsc.close
		set rsc=nothing
	rs.movenext
	wend
end if
rs.close
set rs=nothing
%>
</body>
</html>
