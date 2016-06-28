<!-- #include file="../../include/ad!$#5V.asp" -->
<%
nowdate=cdate("2010-10-9")
sql="select id,com_id,com_rank,personid from comp_tel where telflag=4 and  teldate>='"&nowdate&"' and teldate<'"&nowdate+1&"'"
set rs=conn.execute(sql)
if not rs.eof then
while not rs.eof
	com_id=rs("com_id")
	sqle="select top 1 com_rank from comp_tel where com_id="&com_id&" and telflag=4 and  id<"&rs("id")&" order by id desc"
	set rse=conn.execute(sqle)
	if not rse.eof or not rse.bof then
		oldcomrank=rse(0)
	else
		oldcomrank=0
	end if
	rse.close
	set rse=nothing
	
	nowcom_rank=cstr(rs("com_rank"))
	response.Write(nowcom_rank&"|"&oldcomrank&"<br>")
	if (nowcom_rank="4" or nowcom_rank="5") and cdbl(nowcom_rank)>cdbl(oldcomrank) then
		sqlc="select * from crm_Tostar_vap where com_id="&com_id&" and personid="&rs("personid")&" and fdate<'"&nowdate+1&"' and fdate>='"&nowdate&"' and star="&nowcom_rank&""
		set rsc=conn.execute(sqlc)
		if rsc.eof or rsc.bof then
			sqlp="insert into crm_Tostar_vap(com_id,personid,fdate,star) values("&com_id&","&rs("personid")&",'"&nowdate&"',"&nowcom_rank&")"
			conn.execute(sqlp)
		end if
		rsc.close
		set rsc=nothing
	end if
rs.movenext
wend
end if
rs.close
set rs=nothing
%>