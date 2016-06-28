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
	sql=""
	dotype=request.QueryString("dotype")
	comtype=request.QueryString("type")
	sqlTemp="select count(0) from v_salescomp WHERE "
	sqlTemp=sqlTemp&" not EXISTS(select null from comp_sales where (com_type=13) and com_id=v_salescomp.com_id) "
	sqlTemp=sqlTemp&" and not exists(select com_id from temp_assign_comp where com_id=v_salescomp.com_id) and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and not exists(select com_id from crm_openConfirm where v_salescomp.com_id=com_id and payTime>='2011-11-15' and assignflag=0) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and exists(select com_id from crm_assignvap where com_id=v_salescomp.com_id) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
	if request.QueryString("personid")<>"" then
			if dotype="vapcomp" then
				sql=sqlTemp&" and vpersonid="&request.QueryString("personid")
			else
				sql=sqlTemp&" and personid="&request.QueryString("personid")
			end if
	elseif request.QueryString("code")<>"" then
		sql=sqlTemp&" and personid in (select id from users where userid='"&request.QueryString("code")&"')"
	end if
	if request.QueryString("star")<>"" then
		if request.QueryString("star")="0" then
			sql=sql&" and com_id in (select com_id from crm_tuo_hui_comp where personid="&request.QueryString("personid")&" )"
		else
				if dotype="vapcomp" then
					if request.QueryString("star")="4.1" then
					sql=sql&" and vcom_rank>="&left(request.QueryString("star"),1)&" and vcom_rank<="&request.QueryString("star")&""
					else
					sql=sql&" and vcom_rank="&request.QueryString("star")&""
					end if
				else
					sql=sql&" and com_rank="&request.QueryString("star")&""
				end if
		end if
	end if
	'----------公海挑入未联系
	if request.QueryString("gonghai")<>"" then
		sql=sql&" and EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where personid<>"&request.QueryString("personid")&") and com_id=v_salescomp.com_id)"
	end if
	'----------------begin
	'--------从未联系
	if request.QueryString("Never")<>"" then
		sql=sql&" and not EXISTS (select null from comp_tel where  com_id=v_salescomp.com_id and telflag=4)"
	end if
	'----------------begin
	'--------安排但未联系
	if request.QueryString("NoCon")<>"" then
			if dotype="vapcomp" then
				sql=sql&" and vcontactnext_time<'"&date()&"' and vcontactnext_time<>'1900-1-1'"
			else
				sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
			end if
	end if
	'----------------begin
	'--------明日安排联系
	if request.QueryString("Tomotor")<>"" then
			if dotype="vapcomp" then
				sql=sql&" and vcontactnext_time >='"&date()+1&"' and vcontactnext_time<='"&date()+2&"'"
			else
				sql=sql&" and contactnext_time >='"&date()+1&"' and contactnext_time<='"&date()+2&"'"
			end if
	end if
	'----------------end
	'----------------begin
	'--------明日安排联系
	if request.QueryString("contacttype")<>"" then
		sql=sql&" and contacttype ="&request.QueryString("contacttype")&""
	end if
	if request.QueryString("type")="zst" then
		sql=sql&" and EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
	else
		if dotype="vapcomp" then
		else
		sql=sql&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
		end if
	end if
	'response.Write(sql)
	set rs=conn.execute(sql)
	frompagequrstr=Request.ServerVariables("QUERY_STRING")
	response.Write("<a href=vap_comp_detail.asp?"&frompagequrstr&" target=_blank>"&rs(0)&"</a>")
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
