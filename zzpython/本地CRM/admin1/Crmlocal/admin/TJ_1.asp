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
fromdate=request.QueryString("fromdate")
todate=request.QueryString("todate")
dotype=request.QueryString("dotype")
sqla=""
sqlb=""
comtype=request.QueryString("type")
sqlTemp="select count(0) from v_salescomp WHERE not exists(select com_id from test where com_id=v_salescomp.com_id)"
'sqlTemp=sqlTemp&" not EXISTS(select null from comp_sales where (com_type=13) and com_id=v_salescomp.com_id) "
    tuohuiflag=request.QueryString("tuohuiflag")
	yubeippt=request.QueryString("yubeippt")
	seoflag=request.QueryString("seoflag")
	if tuohuiflag<>"" then
		sqla1=sqla1&" and com_id in (select com_id from crm_tuo_hui_comp where personid="&request.QueryString("personid")&" )"
	end if
	if yubeippt<>"" then
		sqla1=sqla1&" and exists(select property_id from crm_category_info where v_salescomp.com_id=property_id and property_value='10050001')"
	end if
	if seoflag<>"" then
		sqla1=sqla1&" and exists(select com_id from crm_seotel where v_salescomp.com_id=com_id)"
	end if
	sqla1=sqla1&" and com_id in (select com_id from comp_tel where "
	if comtype="xuqian" then
		sqla=sqla&" telflag=2"
	else
		if dotype="vapcomp" then
			sqla=sqla&" telflag=4"
		elseif dotype="sms" then
			sqla=sqla&" telflag=5"
		else
			sqla=sqla&" telflag<4"
		end if
	end if
	if cstr(fromdate)<>cstr(todate) then
		if fromdate<>"" then
			sqla=sqla&" and teldate>='"&fromdate&"'"
		end if
		if todate<>"" then
			sqla=sqla&" and teldate<='"&cdate(todate)+1&"'"
		end if
	else
		if fromdate<>"" and todate<>"" then
			sqla=sqla&" and teldate>='"&fromdate&"' and teldate<='"&cdate(todate)+1&"'"
		end if
	end if
	
	if request.QueryString("contacttype")<>"" then
		sqla=sqla&" and contacttype ='"&request.QueryString("contacttype")&"'"
	end if
	if request.QueryString("star")<>"" then
		if dotype="vapcomp" then
			sqla=sqla&" and vcom_rank="&left(request.QueryString("star"),1)&" "
		elseif dotype="sms" then
			sqla=sqla&" and scom_rank="&left(request.QueryString("star"),1)&" "
		else
			sqla=sqla&" and com_rank="&left(request.QueryString("star"),1)&" "
		end if
	end if
	
	if request.QueryString("personid")<>"" then
		if dotype="vapcomp" then
			sqla=sqla&" and vpersonid="&request.QueryString("personid")&" "
		elseif dotype="sms" then
			sqla=sqla&" and spersonid="&request.QueryString("personid")&" "
		else
			sqla=sqla&" and personid="&request.QueryString("personid")&" "
		end if
	end if
	sqlTemp=sqlTemp&sqla1&" "&sqla&""
	sqlTemp=sqlTemp&")"
	sql=sqlTemp
	frompagequrstr=Request.ServerVariables("QUERY_STRING")
	
	set rs=conn.execute(sql)
	%>
    <a  href="admin_tel_comp1.asp?<%=frompagequrstr%>" target="_blank"><%=cint(rs(0))%></a>
    <%
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
%>
<a href="?lo=1&<%=frompagequrstr%>"><font style="font-size:12px">查看</font></a>
<%
end if
'response.Write(sql)
%>
