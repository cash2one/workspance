<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<!-- #include file="../../include/ad!$#5V.asp" -->
<%
FileName=request.QueryString("FileName")
Filename1=request.QueryString("Filename1")
set connx=Server.CreateObject("Adodb.Connection")
Driver = "Driver={Microsoft Excel Driver (*.xls)};" 
DBPath = "DBQ=" & Server.MapPath(FileName)
connx.open Driver&DBPath

sqlt="delete from crm_lyXSL where fdate='"&Filename1&"'"
conn.execute(sqlt)

sql="select * from ["&Filename1&"$]"
set rs=connx.execute(sql)
'response.Write("<table width=""100%"" border=""0"" cellpadding=""3"" cellspacing=""1"" bgcolor=""#000000""><tr>")
'for i=0 to Rs.Fields.Count-1
'	response.write "<td bgcolor=""#FFFFFF"">"&rs(i).name&"</td>"
'next
'response.write "</tr>"
do while not rs.eof
	'response.Write("<tr>")
	'for i=0 to Rs.Fields.Count-1
		'response.write "<td bgcolor=""#FFFFFF"">"&trim(rs(i).value)&"</td>"
	'next
	'response.write "</tr>"
	hasj=0
	localtel=trim(rs(0).value)
	tel=trim(rs(1).value)
	fx=trim(rs(2).value)
    sj=replace(trim(rs(5).value),".","-")
	
	thsj=trim(rs(6).value)
	'fileUrl=trim(rs(9).value)
	fileUrl=""
	aa=cdate(thsj)
	bb=DateDiff("s",0,aa)
	cc=DateAdd("s", bb, 0)
	
	if len(localtel)>2 then
		if cdate(sj)>cdate(Filename1&" 8:10:0") and cdate(sj)<cdate(Filename1&" 22:30:0") then
		sqle="insert into crm_lyXSL(localtel,tel,fx,sj,thsj,fdate,fileUrl) values('"&localtel&"','"&tel&"','"&fx&"','"&sj&"','"&bb&"','"&Filename1&"','"&fileUrl&"')"
		conn.execute(sqle)
		response.Write(sj&"<br>")
		end if
	end if
	hasj=sj
	rs.movenext
loop
conn.execute "exec zz91_updateSalesContactcount '"&Filename1&"'",ly
'response.Write("</table>")
if ly then
	response.Write("导入成功！")
end if
rs.close
set rs=nothing
connx.close
set connx=nothing
'sql="select localtel,tel from crm_lyXSL where fdate='"&date&"'"
'set rs=conn.execute(sql)
'if not rs.eof or not rs.bof then
'	while not rs.eof
'		sqlc="select personid from crm_assign where exists(select com_id from comp_info where com_mobile like '%"&right(rs("tel"),8)&"' and com_id=crm_assign.com_id)"
'		set rsc=conn.execute(sqlc)
'		if not rsc.eof or not rsc.bof then
'			sqld="update crm_lyXSL set personid="&rsc(0)&" where localtel='"&rs("localtel")&"'"
'			conn.execute(sqld)
'		end if
'		rsc.close
'		set rsc=nothing
'	rs.movenext
'	wend
'end if
'rs.close
'set rs=nothing
%>
</body>
</html>
