<!-- #include file="../../include/ad!$#5V.asp" -->
<%
comid=request("comid")
groupid=request("groupid")
arrcomid=split(comid,",")
flag=request("flag")
if ubound(arrcomid)=0 then 
	response.Write("<script>alert('不能只提交一个客户！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.End()
end if

aper=""
for i=0 to ubound(arrcomid)
	sql="select com_id from crm_assign where com_id="&trim(arrcomid(i))&""
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		aper=aper&rs("com_id")&","
	end if
	rs.close
	set rs=nothing
next
	if aper="" then
		response.Write("<script>alert('关联客户必须有一个所有者！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		response.End()
	end if
	personidcomid=left(aper,len(aper)-1)
	if personidcomid="" then
		response.Write("<script>alert('关联客户必须有一个所有者！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		response.End()
	end if
	
	arrperson=split(personidcomid,",")
	if ubound(arrperson)>0 then
		response.Write("<script>alert('关联客户只能有一个所有者！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		response.End()
	end if
sql="update crm_compLink set shflag="&flag&" where groupid='"&groupid&"' and com_id in ("&comid&")"
conn.execute(sql)
'
sql="update crm_compLink set flag=0 where groupid='"&groupid&"'"
conn.execute(sql)
if flag=1 then
sql="update crm_compLink set flag=1 where groupid='"&groupid&"' and com_id = "&personidcomid&""
conn.execute(sql)
end if
sql="update crm_compLink_main set shflag="&flag&" where groupid='"&groupid&"'"
conn.execute(sql)
'
conn.close
set conn=nothing
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
%>