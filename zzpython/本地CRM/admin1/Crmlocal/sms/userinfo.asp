<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style><!-- #include file="../../include/adfsfs!@#.asp" -->
<%
com_id=request.QueryString("com_id")
sql="select personid from crm_assignsms where com_id="&com_id&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	personid=rs(0)
	sqlu="select realname from users where id="&personid
	set rsu=conn.execute(sqlu)
	if not rsu.eof or not rsu.bof then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	exitse=0
else
	exitse=1
	
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
<%if exitse=1 then%>
<a href="http://adminasto.zz91.com/compsq/?com_id=<%=com_id%>&personid=<%=request.QueryString("personid")%>&sid=<%=request.QueryString("sid")%>&addtype=sms" target="_blank">…Í«Î∑÷≈‰</a>
<%end if%>