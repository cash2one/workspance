<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
selectcb=request("selectcb")
personid=request("personid")

dostay=request("dostay")
doflag=request("doflag")
dotype=request("dotype")
if selectcb<>"" and dostay="assign" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sql="select sid from sms_assign where sid="&trim(arrcom(i))&""
		set rs=conn.execute(sql)
		if rs.eof or rs.bof then
			sqlp="insert into sms_assign (sid,personid) values("&trim(arrcom(i))&","&personid&")"
			conn.execute(sqlp)
		else
			sqlp="update sms_assign set personid="&personid&" where sid="&trim(arrcom(i))&""
			conn.execute(sqlp)
		end if
		rs.close
		set rs=nothing
	next
	response.Write("<script>alert('成功！已经分配成功')</script>")
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
if selectcb<>"" and dostay="havedo" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sql="select sid from sms_action where sid="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rs=conn.execute(sql)
		if rs.eof or rs.bof then
			sqlp="insert into sms_action (sid,personid,doaction) values("&trim(arrcom(i))&","&session("personid")&",1)"
			conn.execute(sqlp)
		else
			sqlp="update sms_assign set doaction=1 where sid="&trim(arrcom(i))&""
			conn.execute(sqlp)
		end if
		rs.close
		set rs=nothing
	next
	response.Write("<script>alert('成功！已经放到已处理客户里')</script>")
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
if selectcb<>"" and dostay="havenodo" then
	sql="delete from sms_action where sid in ("&selectcb&")"
	conn.execute(sql)
	response.Write("<script>alert('成功！已经放到未处理客户里')</script>")
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
conn.close
set conn=nothing
response.End()

%>