<%
if request.QueryString("lo")=1 then
%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 12px;
	text-align: center;
}
-->
</style>
</head>

<body>
<%

	sqlTemp="select count(com_id) from v_salescomp WHERE "
	sqlTemp=sqlTemp&" not EXISTS (select null from Agent_ClientCompany where com_id=v_salescomp.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where (com_type=13) and com_id=v_salescomp.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)"
	sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id)"
	'sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2)"
	sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id )"
	sqlTemp=sqlTemp&" and not EXISTS(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id))"
	if request.QueryString("personid")<>"" then
		sql=sqlTemp&" and adpersonid ="&request.QueryString("personid")
	elseif request.QueryString("code")<>"" then
		sql=sqlTemp&" and adpersonid in (select id from users where userid='"&request.QueryString("code")&"')"
	end if
	if request.QueryString("star")<>"" then
	sql=sql&" and com_rank="&request.QueryString("star")&""
	end if
	'----------------begin
	'--------从未联系
	if request.QueryString("Never")<>"" then
	sql=sql&" and not EXISTS (select null from com_telad where  com_id=v_salescomp.com_id)"
	end if
	'----------------begin
	'--------安排但未联系
	if request.QueryString("NoCon")<>"" then
	sql=sql&" and adcontactnext_time<'"&date()&"' and adcontactnext_time<>'1900-1-1'"
	end if
	'----------------begin
	'--------明日安排联系
	if request.QueryString("Tomotor")<>"" then
	sql=sql&" and adcontactnext_time >='"&date()+1&"' and adcontactnext_time<='"&date()+2&"'"
	end if
	'----------------end
	'----------------begin
	'--------明日安排联系
	if request.QueryString("contacttype")<>"" then
	sql=sql&" and adcontacttype ="&request.QueryString("contacttype")&""
	end if
	
	'sql=sql&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
	'response.Write(sql)
	set rs=conn.execute(sql)
	frompagequrstr=Request.ServerVariables("QUERY_STRING")
	response.Write("<a href=admin_tj_comp_ad.asp?"&frompagequrstr&" target=_blank>"&rs(0)&"</a>")
	rs.close
	set rs=nothing
%>
</body>
</html>
<%
conn.close
set conn=nothing
%>
<%
else
frompagequrstr=Request.ServerVariables("QUERY_STRING")
'response.Write(frompagequrstr)
%>
<a href="?lo=1&<%=frompagequrstr%>"><font style="font-size:12px">查看</font></a>
<%
end if
%>
