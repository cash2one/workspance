<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
personid=request.QueryString("personid")
monthmb=request.QueryString("monthmb")
nowmonth=request.QueryString("nowmonth")
userid=request.QueryString("userid")
if userid="" or isnull(userid) then userid="0"
sql="select * from renshi_salesplan where personid="&personid&" and plan_month='"&nowmonth&"' and userid="&userid&""
set rs=conn.execute(sql)
if rs.eof or rs.bof then
	sqlt="insert into renshi_salesplan(personid,plan_price,plan_month,userid) values("&personid&",'"&monthmb&"','"&nowmonth&"',"&userid&")"
	conn.execute(sqlt)
else
	sqlt="update renshi_salesplan set plan_price='"&monthmb&"' where personid="&personid&" and plan_month='"&nowmonth&"' and userid="&userid&""
	conn.execute(sqlt)
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>