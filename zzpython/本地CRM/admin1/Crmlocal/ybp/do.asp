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


'放到我的客户库
if selectcb<>"" and dostay="tomy" then
	smsID=split(selectcb,",")
	doflagr=0
	for i=0 to ubound(smsID)
		sql="select cid from ybp_assign where cid = "& smsID(i) &""
		set rsp=conn.execute(sql)
		if rsp.eof and rsp.bof then
			conn.Execute("insert into ybp_assign(cid,personid) values("&trim(smsID(i))&","&session("personid")&")")
			cid=trim(smsID(i))
			sDetail=request.Cookies("admin_user")&"放到我的库里"
			sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&cid&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			doflagr=1
		end if
		rsp.close
		set rsp=nothing
	next
	if doflagr=1 then
		alerttext="已放入我的库中"
	else
		alerttext="操作失败"
	end if
	getsucess(alerttext)
end if
'--------分配客户
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
		
		sDetail=request.Cookies("admin_user")&"分配客户"
		sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&cid&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	alerttext="分配成功"
	getsucess(alerttext)
end if
'放到公海
if selectcb<>"" and dostay="gonghai" then
	smsID=split(selectcb,",")
	for i=0 to ubound(smsID)
		sql="select cid from ybp_assign where cid = "& smsID(i) &" and personid="&session("personid")&""
		set rsp=conn.execute(sql)
		if not rsp.eof and not rsp.bof then
			sql="insert into ybp_gonghai(cid,personid) values("&trim(smsID(i))&","&session("personid")&")"
			conn.execute(sql)
			
			cid=trim(smsID(i))
			sDetail=request.Cookies("admin_user")&"放到公海"
			sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&cid&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			
			sql="delete from ybp_assign where cid="&cid&""
			conn.execute(sql)
		end if
	next
	
	alerttext="已放入公海"
	getsucess(alerttext)
end if

%>
