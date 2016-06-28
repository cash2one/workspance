<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
p=247
set rs=server.CreateObject("adodb.recordset")

sql="select top 4 com_id from v_salescomp WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and vip_datefrom>='2008-3-15' and vip_datefrom<='2008-3-19' and not exists(select null from users,crm_assign where crm_assign.com_id=v_salescomp.com_id and crm_assign.personid=users.id and users.id in (47,225,30,185,247)) and not exists(select null from crm_Continue_Assign where com_id=v_salescomp.com_id) and not exists(select null from crm_continue_Nodo where com_id=v_salescomp.com_id) "
rs.open sql,conn,1,3

if not (rs.eof and rs.bof) then
	'response.write rs(0)
	while not rs.eof 
		sql0="select * from crm_continue_assign where com_id ="&rs("com_id")
		
		set rs0=conn.execute(sql0)
		if not(rs0.eof and rs0.bof) then
			
			sql1="update crm_continue_assign set personid="&p&" where com_id="&rs("com_id")
			conn.execute sql1
			response.write sql1
		else
			sql1="insert crm_continue_assign(personid,com_id) values("&p&","&rs("com_id")&")"
			conn.execute sql1
			response.write sql1
		end if
		rs0.close
		set rs0=nothing
		
		sql0="select * from crm_assign where com_id ="&rs("com_id")
		
		set rs0=conn.execute(sql0)
		if not(rs0.eof and rs0.bof) then
			
			sql1="update crm_assign set personid="&p&" where com_id="&rs("com_id")
			conn.execute sql1
			response.write sql1
		else
			sql1="insert crm_assign(personid,com_id) values("&p&","&rs("com_id")&")"
			conn.execute sql1
			response.write sql1
		end if
		rs0.close
		set rs0=nothing
		
		rs.movenext
	wend
end if
rs.close
set rs=nothing
'sql="select * FROM crm_Continue_Assign WHERE PersonID IN (47,225,30,185,247)"
'set rs=conn.execute(sql)
'i=1
'while not rs.eof 
'	sql0="update crm_assign set personid="&rs("personid")&" where com_id="&rs("com_id")
'	conn.execute sql0
'	rs.movenext
'	response.write i&","
'	i=i+1
'wend



'update  user_qx set qx=qx+',2775' where uname in (select id from users where left(userID,2)='13' and closeFlag=1) and uname=30
'sql="select id from users where left(userID,2)='13' and closeFlag=1"
'set rs=conn.execute(sql)
'i=1
'while not rs.eof 
'	sql0="select qx from user_qx where uname="&rs("id")
'	set rs0=conn.execute(sql0)
'	if not(rs0.eof and rs0.bof) then
'		response.write i&"."&rs0(0)
'		'qx1=replace(rs0(0),",3600,3601","")
'		qx1=rs0(0)&",2776,2777,2778,2779,2780,2781,3908,3907"
'		conn.execute "update user_qx set qx='"&qx1&"' where uname="&rs("id")
'	end if
'	rs0.close
'	set rs0=nothing
'	
'	rs.movenext
'	response.write "<br>"&qx1
'	i=i+1
'wend
'rs.close
'set rs=nothing
conn.close
set conn=nothing

%>
