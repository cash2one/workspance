<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
personid=request.QueryString("personid")
sql="select id,com_id from crm_assign where personid="&personid&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	do while not rs.eof
		id=rs("id")
		com_id=rs("com_id")
		sDetail="管理员离职人员放公海"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&personid&",'"&sDetail&"',"&session("personid")&")"
		conn.execute(sqlp)
		sql0="delete from crm_assign where id="&id&""
		conn.execute(sql0)
		response.Write("成功！<br />")
	rs.movenext
	loop
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>