<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<%
sql="select * from comp_yuyao"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
	sqld="select com_id from crm_assign where com_id="&rs("com_id")&""
	set rsd=conn.execute(sqld)
	if rsd.eof or rsd.bof then
		sqlt="insert into crm_assign (com_id,personid) values("&rs("com_id")&","&rs("personid")&")"
		conn.execute(sqlt)
		response.Write("suc<br>")
	else
		response.Write("err<br>")
	end if
	rsd.close
	set rsd=nothing
rs.movenext
wend
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
</body>
</html>
