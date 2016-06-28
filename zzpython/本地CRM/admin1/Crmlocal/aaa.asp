<%@ Language=VBScript %>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
set conn2=server.CreateObject("ADODB.connection")
strconn2="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=cru_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu_crm2"
conn2.open strconn2

'sql="select com_tel,com_mobile,com_email from v_salescomp WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and not EXISTS(select com_id from crm_continue_assign where com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) and exists(select null from crm_assign where com_id=v_salescomp.com_id) and personid=90 and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id ) and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id) "
sql="select * from crm_assign where personid=90"
set rs=conn2.execute(sql)
i=1
j=1
while not rs.eof
	'response.write(i&".  "&rs("com_email")&"=================")
	
	'sql0="select top 1 com_tel,com_mobile,com_email,personid from v_salescomp WHERE com_email='"&rs("com_email")&"' and not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id ) and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and com_id not in (select com_id from crm_assign) and com_id not in (select com_id from comp_sales where com_type=13 ) and not EXISTS(select null from comp_sales where com_type=13 and com_id=v_salescomp.com_id) and not exists(select null from crm_assign where com_id=v_salescomp.com_id) and not exists(select null from Crm_web_CompAll where com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)"
	sql1="select com_id from crm_assign where com_id="&rs("com_id")&""
	set rs1=conn.execute(sql1)
	if rs1.eof then
	    emph=rs("Emphases_check")
		if emph="" or isnull(emph) then emph=0
		adid=rs("adminpersonid")
		if adid="" or isnull(rs("adminpersonid")) then adid=0
		sqlt="insert into crm_assign(personid,com_id,adminpersonid,fdate,Waiter_Open,Emphases,Emphases_check) "
		sqlt=sqlt&" values("&rs("personid")&","&rs("com_id")&","&adid&",'"&rs("fdate")&"',"&rs("Waiter_Open")&","&rs("Emphases")&","&emph&")"
		response.Write(sqlt)
		conn.execute(sqlt)
		'set rs0=conn.execute(sql0)
'		if not rs0.eof then
'			response.write(j&".   "&rs0("com_email")&"("&rs0("personid")&")")
'			j=j+1
'		else
'			sql1="select personid from v_salescomp WHERE com_email='"&rs("com_email")&"'"
'			set rs1=conn.execute(sql1)
'			if not rs1.eof then
'				response.Write("("&rs1(0)&")")
'			end if
'			rs1.close
'			set rs1=nothing
'		end if
'		rs0.close
'		set rs0=nothing
	end if
	rs.movenext
	i=i+1
wend
rs.close
set rs=nothing


'sql="select com_tel,com_mobile,com_email from v_salescomp WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id ) and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and com_id not in (select com_id from crm_assign) and com_id not in (select com_id from comp_sales where com_type=13 ) and not EXISTS(select null from comp_sales where com_type=13 and com_id=v_salescomp.com_id) and not exists(select null from crm_assign where com_id=v_salescomp.com_id) and not exists(select null from Crm_web_CompAll where com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)"
'set rs=conn.execute(sql)
'while not rs.eof
'	response.write(rs("com_email")&"<br>")
'	rs.movenext
'wend
'rs.close
'set rs=nothing
%>