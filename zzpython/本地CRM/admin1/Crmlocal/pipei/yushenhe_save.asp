<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
comid=request("comid")
groupid=request("groupid")
arrcomid=split(comid,",")
if ubound(arrcomid)>0 then 
	response.Write("<script>alert('预审只能选择一个企业给一个销售！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.End()
end if

aper=""
for i=0 to ubound(arrcomid)
	sql="select personid from crm_assign where com_id="&trim(arrcomid(i))&""
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		aper=aper&rs("personid")&","
	end if
	rs.close
	set rs=nothing
next
	personid=left(aper,len(aper)-1)
	if personid="" then
		response.Write("<script>alert('关联客户必须有一个所有者！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		response.End()
	end if
	arrperson=split(personid,",")
	if ubound(arrperson)>0 then
		response.Write("<script>alert('关联客户只能有一个所有者！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		response.End()
	end if
sql="update crm_compLink set YSFlag=0 where groupid='"&groupid&"'"
conn.execute(sql)

sql="update crm_compLink set YSFlag=1,YSpersonID="&session("personid")&" where groupid='"&groupid&"' and com_id in ("&comid&")"
conn.execute(sql)

sql="update crm_compLink_main set YSFlag=1 where groupid='"&groupid&"'"
conn.execute(sql)
conn.close
set conn=nothing
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
%>