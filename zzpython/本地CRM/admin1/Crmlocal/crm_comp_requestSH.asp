<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
com_id=request.QueryString("com_id")
personid=request.QueryString("personid")
sqlc="select com_id,personid from crm_assign where com_id="&com_id&""
set rsc=conn.execute(sqlc)
if rsc.eof or rsc.bof then
	sqlu="insert into crm_assign(com_id,personid) values("&com_id&","&personid&")"
	conn.execute(sqlu)
else
	sqlu="update crm_assign set personid="&personid&" where com_id="&com_id&""
	conn.execute(sqlu)
end if
	'------------写入客户分配记录
	sDetail=request.Cookies("admin_user")&"审核预申请分配"
	sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
	conn.execute(sqlp)
rsc.close
set rsc=nothing
sql="update crm_assign_request set assignflag=1 where com_id="&com_id&""
conn.execute(sql)
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
closeconn()
response.End()
%>
