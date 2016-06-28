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
tomotor=request.QueryString("tomotor")
sqla=""
sqlb=""
comtype=request.QueryString("type")
sqlTemp="select count(0) from ybp_company WHERE id>0 "
    bankcheck=request.QueryString("bankcheck")
	yubeippt=request.QueryString("yubeippt")
	seoflag=request.QueryString("seoflag")
	sqla1=sqla1&" and id in (select cid from ybp_tel where id>0 "
	
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
	
	if tomotor="1" then
		sqla=sqla&" and nextteltime>='"&cdate(todate)+1&"' and nextteltime<='"&cdate(todate)+2&"'"
	end if
	
	if request.QueryString("contactstat")<>"" then
		sqla=sqla&" and contactstat ='"&request.QueryString("contactstat")&"'"
	end if
	if request.QueryString("star")<>"" then
		sqla=sqla&" and star="&left(request.QueryString("star"),1)&" "
	end if
	
	if request.QueryString("personid")<>"" then
		sqla=sqla&" and personid="&request.QueryString("personid")&" "
	end if
	
	
	sqlTemp=sqlTemp&sqla1&" "&sqla&""
	sqlTemp=sqlTemp&")"
	
	if bankcheck="1" then
		sqlTemp=sqlTemp&" and bankcheck="&request.QueryString("bankcheck")&" "
	end if
	
	sql=sqlTemp
	frompagequrstr=Request.ServerVariables("QUERY_STRING")
	
	set rs=conn.execute(sql)
	%>
    <a  href="admintellist.asp?<%=frompagequrstr%>" target="_blank"><%=cint(rs(0))%></a>
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
