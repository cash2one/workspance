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
sql="select top 100 com_mobile,com_tel,com_id from v_salescomp where  EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id) order by com_id asc"
set rs=conn.execute(sql)
if not rs.eof then
while not rs.eof
	mobile=right(rs("com_mobile"),6)
	com_id=rs("com_id")
	tel=right(rs("com_tel"),6)
	existsmobile=0
		sqlr="select com_id from comp_info where (com_mobile like '%"&mobile&"'" 
						if tel<>"" then
						sqlr=sqlr&" or com_tel like '%"&tel&"'"
						end if
						sqlr=sqlr&" ) and com_id<>"&com_id&" and com_regtime>'2008-2-1'"
		set rsr=conn.execute(sqlr)
		if not rsr.eof then
		while not rsr.eof
			existsmobile=1
			exiatcom=rsr("com_id")
			
			sqlb="select com_id from crm_webPipeiComp where com_id="&exiatcom&""
			set rsb=conn.execute(sqlb)
			if  rsb.eof then
				sqlu="insert into crm_webPipeiComp(fromcom_id,com_id) values("&com_id&","&exiatcom&")"
				conn.execute(sqlu)
			end if
		rsr.movenext
		wend
		end if
		rsr.close
		set rsr=nothing
		
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
response.Write("<script>//setTimeout(""window.location='crm_compfenpei_pipei.asp'"",1000)</script>")
end if
conn.close
set conn=nothing
connserver.close
set connserver=nothing

%>
</body>
</html>
