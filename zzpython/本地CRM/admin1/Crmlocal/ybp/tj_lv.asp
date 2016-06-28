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
sqla=""
sqlTemp="select count(0) from ybp_company WHERE id>0"
if cstr(fromdate)<>cstr(todate) then
	if fromdate<>"" then
		sqla=sqla&" and fdate>='"&fromdate&"'"
	end if
	if todate<>"" then
		sqla=sqla&" and fdate<='"&cdate(todate)+1&"'"
	end if
else
	if fromdate<>"" and todate<>"" then
		sqla=sqla&" and fdate>='"&fromdate&"' and fdate<='"&cdate(todate)+1&"'"
	end if
end if
'----------------begin
'--------无效有效
sql1=sqla&" and contactstat =0"
sql2=sqla&" and contactstat =1"
sqlTemp1=sqlTemp&" and EXISTS(select cid from ybp_tel where personid="&request.QueryString("personid")&" and cid=ybp_company.id "&sql1&")"
sqlTemp2=sqlTemp&" and EXISTS(select cid from ybp_tel where personid="&request.QueryString("personid")&" and cid=ybp_company.id "&sql2&")"


sqla=sqlTemp1
set rsa=conn.execute(sqla)
sqlb=sqlTemp2
set rsb=conn.execute(sqlb)
if (rsa(0)+rsb(0))<>0 then
	response.Write(formatnumber(((rsb(0)/(rsa(0)+rsb(0)))*100),2)&"%")
else
	response.Write("0%")
end if
rsa.close
set rsa=nothing
rsb.close
set rsb=nothing
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
%>
