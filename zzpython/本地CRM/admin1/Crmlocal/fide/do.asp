<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
selectcb=request("selectcb")
dostay=request("dostay")
doflag=request("doflag")
dotype=request("dotype")
closeflag=request("closeflag")
topersonid=request("topersonid")


function getsucess(alerttext)
	if closeflag=1 then
	response.Write("<script>window.close()</script>")
	else
	response.Write("<script>alert('"&alerttext&"');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.End()
	end if
end function


'�ŵ��ҵĿͻ���
if selectcb<>"" and dostay="tomy" then
	smsID=split(selectcb,",")
	doflagr=0
	for i=0 to ubound(smsID)
		sql="select cid from ybp_assign where cid = "& smsID(i) &""
		set rsp=conn.execute(sql)
		if rsp.eof and rsp.bof then
			conn.Execute("insert into ybp_assign(cid,personid) values("&trim(smsID(i))&","&session("personid")&")")
			cid=trim(smsID(i))
			sDetail=request.Cookies("admin_user")&"�ŵ��ҵĿ���"
			sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&cid&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			doflagr=1
		end if
		rsp.close
		set rsp=nothing
	next
	if doflagr=1 then
		alerttext="�ѷ����ҵĿ���"
	else
		alerttext="����ʧ��"
	end if
	getsucess(alerttext)
end if
'--------����ͻ�
if selectcb<>"" and dostay="assignto" then
	smsID=split(selectcb,",")
	for i=0 to ubound(smsID)
		cid=trim(smsID(i))
		sql="select cid from ybp_assign where cid = "& smsID(i) &""
		set rsp=conn.execute(sql)
		if rsp.eof and rsp.bof then
			conn.Execute("insert into ybp_assign(cid,personid) values("&trim(smsID(i))&","&request("topersonid")&")")
		else
			conn.execute("update ybp_assign set personid="&request("topersonid")&" where cid="&trim(smsID(i))&"")
		end if
		rsp.close
		set rsp=nothing
		
		sDetail=request.Cookies("admin_user")&"����ͻ�"
		sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&cid&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	alerttext="����ɹ�"
	getsucess(alerttext)
end if
'�ŵ�����
if selectcb<>"" and dostay="gonghai" then
	smsID=split(selectcb,",")
	for i=0 to ubound(smsID)
		sql="select cid from ybp_assign where cid = "& smsID(i) &" and personid="&session("personid")&""
		set rsp=conn.execute(sql)
		if not rsp.eof and not rsp.bof then
			sql="insert into ybp_gonghai(cid,personid) values("&trim(smsID(i))&","&session("personid")&")"
			conn.execute(sql)
			
			cid=trim(smsID(i))
			sDetail=request.Cookies("admin_user")&"�ŵ�����"
			sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&cid&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			
			sql="delete from ybp_assign where cid="&cid&""
			conn.execute(sql)
		end if
	next
	
	alerttext="�ѷ��빫��"
	getsucess(alerttext)
end if

%>
