<%
Response.Buffer =false
response.cachecontrol="private"
Response.Expires =0
%>
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
sqlm="select udate,comcount from crm_assign_flag where fdate='"&fenpaidate&"' and flag='compinfo'"
set rsm=conn.execute(sqlm)
if not rsm.eof then
udate=rsm(0)
comcount=rsm(1)
else
maxcomid=0
udate=fenpaidate-1
comcount=0
end if
rsm.close
set rsm=nothing
sql="select top 30 * from comp_info where editdate>'"&udate&"' order by editdate asc"
'response.Write(sql)
set rs=connserver.execute(sql)
if not rs.eof then

while not rs.eof
    'response.Write(rs("com_id")&"<br>")
	sqlcc="select * from comp_info where com_id="&rs("com_id")
	set rscc=server.CreateObject("adodb.recordset")
	rscc.open sqlcc,conn,1,3
	
	if rscc.eof then
	
	rscc.addnew()
	rscc("com_id")=rs("com_id")
	end if
	
	    rscc("com_name")=rs("com_name")
		rscc("com_subname")=rs("com_subname")
		rscc("com_add")=rs("com_add")
		rscc("com_zip")=rs("com_zip")
		rscc("com_province")=rs("com_province")
		rscc("com_ctr_id")=rs("com_ctr_id")
		rscc("com_tel")=rs("com_tel")
		rscc("com_mobile")=rs("com_mobile")
		rscc("com_fax")=rs("com_fax")
		rscc("com_email")=rs("com_email")
		rscc("com_email_back")=rs("com_email_back")
		rscc("com_email_check")=rs("com_email_check")
		rscc("com_contactperson")=rs("com_contactperson")
		rscc("com_desi")=rs("com_desi")
		rscc("com_station")=rs("com_station")
		rscc("com_website")=rs("com_website")
		rscc("com_kind")=rs("com_kind")
		rscc("com_intro")=rs("com_intro")
		rscc("com_productslist")=rs("com_productslist")
		rscc("com_productslist_en")=rs("com_productslist_en")
		rscc("com_regtime")=rs("com_regtime")
		rscc("com_pass")=rs("com_pass")
		rscc("com_SafePass")=rs("com_SafePass")
		rscc("com_safekey")=rs("com_safekey")
		rscc("com_keywords")=rs("com_keywords")
		rscc("vipflag")=rs("vipflag")
		rscc("vip_date")=rs("vip_date")
		rscc("vip_check")=rs("vip_check")
		rscc("vipRequest")=rs("vipRequest")
		rscc("vip_datefrom")=rs("vip_datefrom")
		rscc("vip_dateto")=rs("vip_dateto")
		rscc("viptype")=rs("viptype")
		rscc("agent")=rs("agent")
		rscc("Com_FQR")=rs("Com_FQR")
		rscc("EditDate")=rs("EditDate")
		rscc("DelFlag")=rs("DelFlag")
		rscc("adminuser")=rs("adminuser")
		rscc("sb_cls")=rs("sb_cls")
		rscc.update()
		rscc.close
		set rscc=nothing
	
	sqlb="select id from crm_assign_flag where fdate='"&fenpaidate&"' and flag='compinfo'"
	set rsb=conn.execute(sqlb)
	if not rsb.eof then
	sqlu="update crm_assign_flag set udate='"&rs("EditDate")&"',comcount=comcount+1 where fdate='"&fenpaidate&"' and flag='compinfo'"
	conn.execute(sqlu)
	else
	
	sqlu="insert into crm_assign_flag(udate,fdate,flag) values('"&rs("EditDate")&"','"&fenpaidate&"','compinfo')"
	conn.execute(sqlu)
	end if
	
rs.movenext
wend
outcom=0
else
outcom=1
response.Write("数据更新完成！<br>")
response.Write("<script>window.location='http://localhost/admin1/crmlocal/CrmPubFenpei/crm_compfenpei_pipei.asp?c="&comcount&"'</script>")
end if
response.write("更新了“"&comcount&"”条数据")
rs.close
set rs=nothing
if outcom=0 then
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
response.Write("<script>setTimeout(""window.location='crm_compfenpei_comp.asp'"",1000)</script>")
end if
%>
</body>
</html>
