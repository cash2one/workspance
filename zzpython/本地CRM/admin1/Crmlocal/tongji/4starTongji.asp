<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
sqltt=""
sqlTemp=""
sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=crm_to4star.com_id) "
sqlTemp=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=crm_to4star.com_id)"
sqltt=sqltt&sqlTemp

sql="select id from users where userid like '13__'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	while not rs.eof
		cout=0
		sqlc="select count(0) from crm_to4star where personid="&rs("id")&sqltt&" and fdate='"&date&"' and exists(select com_id from crm_assign where personid="&rs("id")&" and crm_to4star.com_id=com_id)"
		set rsc=conn.execute(sqlc)
		if not rsc.eof or not rsc.bof then
			cout=rsc(0)
		end if
		rsc.close
		set rsc=nothing
		'if cout>0 then
			sqlt="select personid from crm_Salestongji where personid="&rs("id")&" and Tdate='"&date&"'"
			set rst=conn.execute(sqlt)
			if rst.eof or rst.bof then
				sqlb="insert into crm_Salestongji(personid,developCount,Tdate) values("&rs("id")&","&cout&",'"&date&"')"
			else
				sqlb="update crm_Salestongji set developCount="&cout&" where personid="&rs("id")&" and Tdate='"&date&"'"
			end if
			conn.execute(sqlb)
			rst.close
			set rst=nothing
			response.Write("更新成功！<br>")
		'end if
	rs.movenext
	wend
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
