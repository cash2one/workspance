<!-- #include file="../connlocal.asp" -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<%
fenpaidate=date
sqlm="select com_id,comcount from crm_assign_flag where fdate='"&fenpaidate&"' and flag='zst'"
set rsm=conn.execute(sqlm)
if not rsm.eof then
maxcomid=rsm(0)
comcount=rsm(1)
else
maxcomid=0
comcount=0
end if
rsm.close
set rsm=nothing
sql="select top 100 * from comp_zstinfo where com_id>"&maxcomid&" order by com_id asc"
set rs=connserver.execute(sql)
if not rs.eof then
while not rs.eof
    sqlu="select com_id from comp_zstinfo where com_id="&rs("com_id")&""
	set rsu=conn.execute(sqlu)
	if rsu.eof then
	sqli="insert into comp_zstinfo (com_id) values("&rs("com_id")&")"
	conn.execute(sqli)
	sqlu="update crm_assign_flag set comcount=comcount+1 where fdate='"&fenpaidate&"' and flag='zst'"
	conn.execute(sqlu)
	end if
	sqlb="select id from crm_assign_flag where fdate='"&fenpaidate&"' and flag='zst'"
	set rsb=conn.execute(sqlb)
	if not rsb.eof then
	sqlu="update crm_assign_flag set com_id="&rs("com_id")&" where fdate='"&fenpaidate&"' and flag='zst'"
	conn.execute(sqlu)
	else
	sqlu="insert into crm_assign_flag(fdate,com_id,flag) values('"&fenpaidate&"','"&rs("com_id")&"','zst')"
	conn.execute(sqlu)
	end if
rs.movenext
wend
outcom=0
else
outcom=1
response.Write("数据更新完成！<br>")
response.Write("<script>window.location='1.asp?c="&comcount&"'</script>")
end if
response.write("更新了“"&comcount&"”条数据")
rs.close
set rs=nothing
if outcom=0 then
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
response.Write("<script>setTimeout(""window.location='crm_compfenpei_zst.asp'"",1000)</script>")
end if
conn.close
set conn=nothing
connserver.close
set connserver=nothing

%>
</body>
</html>
