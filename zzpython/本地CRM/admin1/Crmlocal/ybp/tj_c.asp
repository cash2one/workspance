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
today=request.QueryString("today")
Never=request.QueryString("Never")
NoCon=request.QueryString("NoCon")
sqla=""
sqlb=""
comtype=request.QueryString("type")
sqlTemp="select count(0) from ybp_company WHERE id>0 "
    tuohuiflag=request.QueryString("tuohuiflag")
	yubeippt=request.QueryString("yubeippt")
	seoflag=request.QueryString("seoflag")
	
	sqla1=sqla1&" and id in (select cid from ybp_assign where id>0 "
	if request.QueryString("personid")<>"" then
		sqla1=sqla1&" and personid="&request.QueryString("personid")&" "
	end if
	sqla1=sqla1&")"
	
	
	if tomotor="1" then
		sqla1=sqla1&" and interviewTime>='"&date()+1&"' and interviewTime<='"&date()+2&"'"
	end if
	
	if today="1" then
		sqla1=sqla1&" and interviewTime>='"&date()&"' and interviewTime<='"&date()+1&"'"
	end if
	
	if Never="1" then
		sqla1=sqla1&" and not exists(select cid from ybp_tel where cid=ybp_company.id and personid="&request.QueryString("personid")&")"
	end if
	
	if NoCon="1" then
		sqla1=sqla1&" and interviewTime<'"&date()&"' and interviewTime is not null"
	end if

	if request.QueryString("star")<>"" then
		sqla1=sqla1&" and star="&left(request.QueryString("star"),1)&" "
	end if
	
	
	sqlTemp=sqlTemp&sqla1
	sql=sqlTemp
	frompagequrstr=Request.ServerVariables("QUERY_STRING")
	
	set rs=conn.execute(sql)
	%>
    <a  href="tj_list.asp?<%=frompagequrstr%>" target="_blank"><%=cint(rs(0))%></a>
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
