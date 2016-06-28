<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
 selectcb=request("selectcb")
 dostay=request("dostay")
 doflag=request("doflag")
 dotype=request("dotype")
 
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid,usertel from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 mytel=rsuser("usertel")
 rsuser.close
 set rsuser=nothing

if lcase(dostay)="waitopenzst" then	'待开通
	if session("userid")="1315" then 
	    mbflag=1
	else
		mbflag=2
	end if
	'http://admin.zz91.com/admin1/compinfo/cominfo_openConfirm.asp
	response.Redirect("/admin1/compinfo/openconfirm.asp?com_id="&request("selectcb")&"&userid="&session("userid")&"&mbflag="&mbflag&"&personid="&session("personid")&"&username="&request("userName")&"")
 	'response.Redirect("http://adminasto.zz91.com/openConfirm/?selectcb="&request("selectcb")&"&userName="&request("userName")&"&personid="&session("personid")&"&com_email="&com_email&"&com_name="&com_name&"")
	'response.End()
end if
if ywadminid<>"" and not isnull(ywadminid)  then
	maxrccomp=1000
else
	maxrccomp=500
end if
if session("userid")="13" or session("userid")="10" then
	maxrccomp=200000
end if
function closeconn()
	 conn.close
	 set conn=nothing
end function
'sql1=" and not exists(select com_id from Crm_Active_CompAll where com_id=crm_assign.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id))" 
		sql1=sql1&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=crm_assign.com_id)"
		sql1=sql1&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=crm_assign.com_id and Ccheck=0)"
		'sql1=sql1&" and not EXISTS(select com_id from crm_OtherComp where com_id=crm_assign.com_id)" 
		sql1=sql1&" and not EXISTS(select com_id from Comp_ZSTinfo where com_id=crm_assign.com_id and closeflag=0) "
		sql1=sql1&" and not EXISTS(select com_id from comp_info where com_id=crm_assign.com_id and vipflag=2 and vip_check=1) "
		sql1=sql1&" and not EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=crm_assign.com_id) "

'----------VAP放入死海
if selectcb<>"" and dostay="droptosihai" and left(dotype,3)="vap" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
		if session("userid")="10" then
			sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &")"
		end if
		conn.Execute sqldel,ly
		'------------放到公海库
		sqlp="select com_id from crm_publiccomp_vap where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_publiccomp_vap(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		'----------还到死海
		sqlp="select com_id from crm_notBussiness where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_notBussiness(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"放到vap死海"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'---------seo客户已经下线
if selectcb<>"" and dostay="seounline" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sqlp="update company_service set online=0 where company_id="&com_id&""
		conn.execute(sqlp)
		
		sDetail=request.Cookies("admin_user")&"放到已下线SEO客户"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'---------seo客户已经下线
if selectcb<>"" and dostay="compblock" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sqlp="select com_id from crm_block where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_block(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"放到黑名单"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if

'---------seo客户上线
if selectcb<>"" and dostay="seoonline" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sqlp="update company_service set online=1 where company_id="&com_id&""
		conn.execute(sqlp)
		
		sDetail=request.Cookies("admin_user")&"放到已上线SEO客户"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'--------------------------begin
'-------放到“废品池”

if selectcb<>"" and dostay="selec1twastecom" then
    arrcom=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(arrcom)
			sql="select com_id from comp_sales where com_id="&trim(arrcom(i))&""
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				sqlu="update comp_sales set com_type=13 where com_id="&trim(arrcom(i))&" AND com_id in (select com_id from crm_assignvap where personid="&session("personid")&")"
				conn.execute sqlu,ly
			else
				sqlm="select com_id from crm_assignvap where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
				set rsm=conn.execute(sqlm)
				if not rsm.eof or not rsm.bof then
					sqlu="insert into comp_sales(com_id,com_type) values("&trim(arrcom(i))&",13)"
					conn.execute sqlu,ly
					'------------写入客户分配记录
					com_id=trim(arrcom(i))
					sDetail=request.Cookies("admin_user")&"放入vap废品池"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				end if
				rsm.close
				set rsm=nothing
			end if
			rs.close
			set rs=nothing
			if ly then
				response.Write("<script>alert('成功！该客户已经放到“废品池”')</script>")
			else
				response.Write("<script>alert('失败！该客户不是您的客户不能放到“废品池”')</script>")
			end if
		next
	else
		for i=0 to ubound(arrcom)
			sql="select com_id from comp_sales where com_id="&trim(arrcom(i))&""
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				sqlu="update comp_sales set com_type=13 where com_id="&trim(arrcom(i))&" AND com_id in (select com_id from crm_assign where personid="&session("personid")&")"
				conn.execute sqlu,ly
			else
				sqlm="select com_id from crm_assign where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
				set rsm=conn.execute(sqlm)
				if not rsm.eof or not rsm.bof then
					sqlu="insert into comp_sales(com_id,com_type) values("&trim(arrcom(i))&",13)"
					conn.execute sqlu,ly
					'------------写入客户分配记录
					com_id=trim(arrcom(i))
					sDetail=request.Cookies("admin_user")&"放入废品池"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				end if
				rsm.close
				set rsm=nothing
			end if
			rs.close
			set rs=nothing
			if ly then
				response.Write("<script>alert('成功！该客户已经放到“废品池”')</script>")
			else
				response.Write("<script>alert('失败！该客户不是您的客户不能放到“废品池”')</script>")
			end if
		next
	end if
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'--------------------------------------end
'------------------放到临时库
if selectcb<>"" and dostay="assignTotemp" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sql="select com_id from crm_AssignTemp where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rs=conn.execute(sql)
		if rs.eof or rs.bof then
			sqlu="insert into crm_AssignTemp(com_id,personid) values("&trim(arrcom(i))&","&session("personid")&")"
			conn.execute sqlu,ly
			'------------写入客户分配记录
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"放入临时库"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
		rs.close
		set rs=nothing
		if ly then
			response.Write("<script>alert('成功！该客户已经放到“临时库”')</script>")
		else
			response.Write("<script>alert('失败！该客户不是您的客户不能放到“临时库”')</script>")
		end if
	next
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if

'--------------------------------begin
'------------------取消放到临时库
if selectcb<>"" and dostay="cancerAssignTotemp" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sql="select com_id from crm_AssignTemp where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			sqlu="delete from crm_AssignTemp where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
			conn.execute sqlu,ly
			'------------写入客户分配记录
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"取消放入临时库"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
		rs.close
		set rs=nothing
		if ly then
			response.Write("<script>alert('成功！该客户已经取消放到“临时库”')</script>")
		else
			response.Write("<script>alert('失败！该客户不是您的客户不能操作')</script>")
		end if
	next
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'--------------------------------begin
'------------------设置为大客户
if selectcb<>"" and dostay="dakehu" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		'if session("userid")="13" or session("userid")="10" then
			'sql="select com_id from crm_assign where com_id="&trim(arrcom(i))&""
		'else
			'sql="select com_id from crm_assign where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		'end if
		'set rs=conn.execute(sql)
		'if not rs.eof or not rs.bof then
			sqlr="select com_id from crm_bigcomp where com_id="&trim(arrcom(i))&""
			set rsr=conn.execute(sqlr)
			if rsr.eof or rsr.bof then
				sqlu="insert into crm_bigcomp(com_id,checked) values("&trim(arrcom(i))&",1)"
				conn.execute sqlu,ly
			end if
			rsr.close
			set rsr=nothing
			
		'end if
		'rs.close
		'set rs=nothing
		if ly then
			'------------写入客户分配记录
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"放入大客户库"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('成功！该客户已经放入大客户库')</script>")
		else
			response.Write("<script>alert('失败！该客户已经放入大客户库')</script>")
		end if
	next
	url=request.ServerVariables("HTTP_REFERER")
	url=replace(url,"&","~amp~")
	response.Write("<script>window.location='http://admin.zz91.com/admin1/localcontrol/tobigcomp.asp?comlist="&selectcb&"&url="&url&"'</script>")
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'临时已合作SEO客户添加
if selectcb<>"" and dostay="hezuoseo" then
	arrcom=split(selectcb,",")
	url=request.ServerVariables("HTTP_REFERER")
	if ubound(arrcom)>0 then
		response.Write("<script>alert('一次只能提交一个客户')</script>")
		response.Write("<script>window.location='"&url&"'</script>")
		response.End()
	end if
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="insert into company_service(id,company_id,crm_service_code,apply_status,apply_group) values(0,"&com_id&",'10001002','1','')"
		conn.execute(sql)
	next
	
	url=replace(url,"&","~amp~")
	response.Write("<script>window.location='http://adminasto.zz91.com/saveseocompany/?comlist="&selectcb&"&url="&url&"'</script>")
end if
'SEO确认上线
if selectcb<>"" and dostay="SEOcomfirm" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="select * from crm_seoconfirm_online where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
			
			
		else
			sql="insert into crm_seoconfirm_online(com_id) values("&com_id&")"
			conn.execute(sql)
			sDetail=request.Cookies("admin_user")&"SEO上线确认"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			
		end if
	next
	response.Write("<script>alert('成功！该客户确认SEO上线')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if

if selectcb<>"" and dostay="noSEOcomfirm" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="select * from crm_seoconfirm_online where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
			sql="delete from crm_seoconfirm_online where com_id="&com_id&""
			conn.execute(sql)
			sDetail=request.Cookies("admin_user")&"取消SEO上线确认"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
	next
	response.Write("<script>alert('成功！该客户取消了确认SEO上线')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if

'设置为注册机客户

if selectcb<>"" and dostay="do_zhucejishenhe" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="select * from crm_compzhuceji where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------写入客户分配记录
			
			
		else
			sql="insert into crm_compzhuceji(com_id) values("&com_id&")"
			conn.execute(sql)
			sDetail=request.Cookies("admin_user")&"设置为注册机客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			
		end if
	next
	response.Write("<script>alert('成功！该客户设置为注册机客户')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if

'取消为注册机客户

if selectcb<>"" and dostay="undo_zhucejishenhe" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="select * from crm_compzhuceji where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------写入客户分配记录
			sql="delete from  crm_compzhuceji where com_id in("&com_id&")"
			conn.execute(sql)
			sDetail=request.Cookies("admin_user")&"取消为注册机客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
	next
	response.Write("<script>alert('成功！该客户取消为注册机客户')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if


'------------------审核4星客户
if selectcb<>"" and dostay="shenhe4star" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="update crm_To4star set checked=1 where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------写入客户分配记录
			
			sDetail=request.Cookies("admin_user")&"4星审核通过"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('成功！该客户已经通过4星审核')</script>")
		else
			
		end if
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'------------------审核5星客户
if selectcb<>"" and dostay="shenhe5star" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="update crm_To5star set checked=1 where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------写入客户分配记录
			
			sDetail=request.Cookies("admin_user")&"5星审核通过"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('成功！该客户已经通过5星审核')</script>")
		else
			
		end if
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'------------------取消放入大客户库
if selectcb<>"" and dostay="cencerdakehu" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		'if session("userid")="13" or session("userid")="10" then
			'sql="select com_id from crm_assign where com_id="&trim(arrcom(i))&""
		'else
			'sql="select com_id from crm_assign where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		'end if
		sql="select com_id from crm_bigcomp where com_id="&trim(arrcom(i))&""
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then

			sqlu="update crm_bigcomp set checked=0 where com_id="&trim(arrcom(i))&""
			conn.execute sqlu,ly
			
		end if
		rs.close
		set rs=nothing
		if ly then
		'------------写入客户分配记录
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"取消放入大客户库"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('成功！该客户已经取消放入大客户库')</script>")
		else
			response.Write("<script>alert('失败！该客户不是大客户库')</script>")
		end if
	next
	url=request.ServerVariables("HTTP_REFERER")
	url=replace(url,"&","~amp~")
	response.Write("<script>window.location='http://admin.zz91.com/admin1/localcontrol/tobigcomp.asp?comlist="&selectcb&"&url="&url&"&checked=0'</script>")
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if

'*客户共享
'------------------
if selectcb<>"" and dostay="selec1tToshare" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlp="select com_id from crm_Assign where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			sql="select com_id from Crm_Comp_Share where com_id = "& trim(arrcom(i)) &""
			set rsc=conn.Execute(sql)
			if rsc.eof or rsc.bof then
				sqli="insert into Crm_Comp_Share(com_id,personid,salesType) values("& trim(arrcom(i)) &","&session("personid")&",1)"
				conn.execute sqli,ly
			else
				sqli="update Crm_Comp_Share set salesType=1 where com_id = "& trim(arrcom(i)) &""
				conn.execute sqli,ly
			end if
			if ly then
				response.Write("<script>alert('成功！已放入我的共享客户区')</script>")
			else
				response.Write("<script>alert('失败！不能把该客户设置为共享客户')</script>")
			end if
		end if
		rsp.close
		set rsp=nothing
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'--------------------------------end
'------------------------------------begin
'---------给新人用的客户
if selectcb<>"" and dostay="outselectcrmPin" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlp="delete from crm_Assign where com_id = "& trim(arrcom(i)) &""
		conn.execute(sqlp)
		sqlp="insert into crm_Othercomp(com_id) values("&trim(arrcom(i))&")"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'----------------------------------end
'--------------给余姚办事处
if selectcb<>"" and dostay="selec1tToyuyao" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlp="delete from crm_Assign where com_id = "& trim(arrcom(i)) &""
		conn.execute(sqlp)
		sqlt="select com_id from crm_yuyaocomp where com_id="&trim(arrcom(i))&""
		set rst=conn.execute(sqlt)
		if rst.eof or rst.bof then
			sqlp="insert into crm_yuyaocomp(com_id) values("&trim(arrcom(i))&")"
			conn.execute(sqlp)
		end if
		rst.close
		set rst=nothing
		'------------写入客户分配记录
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"放到余姚办事处"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	url=request.ServerVariables("HTTP_REFERER")
	url=replace(url,"&","~amp~")
	response.Redirect("http://office.zz91.com/admin1/crmlocal/LocalSave/toYuyao.asp?selectcb="&selectcb&"&url="&url&"")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'------------------------------------begin
'---------放到我的续签客户库
if selectcb<>"" and dostay="IntoContinueCrm" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqla="select com_id,personid from crm_assign where com_id="&trim(arrcom(i))&""
		set rsa=conn.execute(sqla)
		if not (rsa.eof and rsa.bof) then
			if cstr(session("personid"))=cstr(rsa("personid")) then
			else
			response.Write("<script>alert('该客户已经在某人的再生通库里面！');parent.parent.window.close()</script>")
			closeconn()
			response.End()
			end if
		else
			sqlb="insert into crm_assign(com_id,personid) values("&trim(arrcom(i))&","&session("personid")&")"
		end if
		conn.execute(sqlb)
		rsa.close
		set rsa=nothing
		
		sqlt="select com_id from crm_continue_assign where com_id="&trim(arrcom(i))&""
		set rst=conn.execute(sqlt)
		if rst.eof or rst.bof then
			sqlp="insert into crm_continue_assign(com_id,personid) values("&trim(arrcom(i))&","&session("personid")&")"
			conn.execute(sqlp)
			sqld="delete from crm_continue_Nodo where com_id="&trim(arrcom(i))&""
			conn.execute(sqld)
		end if
		rst.close
		set rst=nothing
		'------------写入客户分配记录
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"放到我的续签库"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>alert('放入成功！');parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'----------------------------------end
'---------放到暂不续签客户库
if selectcb<>"" and dostay="ZNoContinueCrm" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlt="select com_id from crm_continue_Nodo where com_id="&trim(arrcom(i))&""
		set rst=conn.execute(sqlt)
		if rst.eof or rst.bof then
			sqltt="select com_id from crm_continue_assign where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
			set rstt=conn.execute(sqltt)
			if not rstt.eof or not rstt.bof then
				sqlp="insert into crm_continue_Nodo(com_id,Ntype) values("&trim(arrcom(i))&","&doflag&")"
				conn.execute(sqlp)
			else
				response.Write("<script>alert('该客户还不在你的续签客户库，请先放到续签客户库！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				closeconn()
				response.End()
			end if
			rstt.close
			set rstt=nothing
		else
			sqlp="update crm_continue_Nodo set Ntype="&doflag&" where com_id="&trim(arrcom(i))&""
			conn.execute(sqlp)
		end if
		rst.close
		set rst=nothing
		'------------写入客户记录
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"暂不放到我的续签库"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>alert('放入成功！');parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'----------------------------------end
'-------------------------begin
'-------------取消共享 
if selectcb<>"" and dostay="outselec1tToshare" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlp="select com_id from crm_Assign where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			sql="select com_id from Crm_Comp_Share where com_id = "& trim(arrcom(i)) &""
			set rsc=conn.Execute(sql)
			if not rsc.eof or not rsc.bof then
				sqli="delete from Crm_Comp_Share where com_id="&trim(arrcom(i))
				conn.execute sqli,ly
				if ly then
					response.Write("<script>alert('成功！已取消共享此客户')</script>")
				else
					response.Write("<script>alert('失败！不能取消该客户')</script>")
				end if
			end if
		end if
		rsp.close
		set rsp=nothing
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if

'放到“品牌通部”
if selectcb<>"" and dostay="selec1tToweb" then
    sql="update comp_sales set TowebContact=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	'------------写入客户记录
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"放到品牌通库"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	if ly then
		response.Write("<script>alert('成功！已放到“品牌通部”')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能把别人的客户放到“品牌通部”')</script>")
	end if
	
	closeconn()
	response.End()
end if
'------------设置重点客户
if selectcb<>"" and dostay="zhongdian" then
	if left(dotype,3)="vap" then
		if session("Partadmin")<>"0" then
			sql="update crm_assignvap set Emphases=1 where com_id in ("& selectcb &")"
			conn.Execute sql,ly
		else
			sql="update crm_assignvap set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
			conn.Execute sql,ly
		end if
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"设置为vap重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		if session("Partadmin")<>"0" then
			sql="update crm_assign set Emphases=1 where com_id in ("& selectcb &")"
			conn.Execute sql,ly
		else
			sql="update crm_assign set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
			conn.Execute sql,ly
		end if
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"设置为重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('成功！已为重点客户')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能把别人的客户设置为我的重点客户')</script>")
	end if
	closeconn()
	response.End()
end if
'---------取消重点客户
if selectcb<>"" and dostay="zhongdianout" then
	if left(dotype,3)="vap" then
		if session("Partadmin")<>"0" then
			sql="update crm_assignvap set Emphases=0 where com_id in ("& selectcb &") "
			conn.Execute sql,ly
		else
			sql="update crm_assignvap set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
			conn.Execute sql,ly
		end if
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"取消为vap重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		if session("Partadmin")<>"0" then
			sql="update crm_assign set Emphases=0 where com_id in ("& selectcb &") "
			conn.Execute sql,ly
		else
			sql="update crm_assign set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
			conn.Execute sql,ly
		end if
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"取消为重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('成功！该客户已取消为我的重点客户')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能设置别人的客户为我的重点客户')</script>")
	end if
	closeconn()
	response.End()
end if
'---------------审核重点客户
if selectcb<>"" and dostay="zhongdiansh" then
	if left(dotype,3)="vap" then
		sql="update crm_assignvap set Emphases_check=1 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"审核vap重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		sql="update crm_assign set Emphases_check=1 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"审核重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('成功！已审核了该重点客户')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能把别人的客户设置为我的重点客户')</script>")
	end if
	
	closeconn()
	response.End()
end if
'---------------------------end
'---------------取消审核重点客户
if selectcb<>"" and dostay="zhongdianshno" then
	if left(dotype,3)="vap" then
		sql="update crm_assignvap set Emphases_check=0 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"取消审核vap重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		sql="update crm_assign set Emphases_check=0 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"取消审核重点客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('成功！已审核了该重点客户')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能把别人的客户设置为我的重点客户')</script>")
	end if
	
	closeconn()
	response.End()
end if
'---------------------------end
'----------------begin
'-------------管理员分配客户
if selectcb<>"" and dostay="selec1tcrm" then
    mycrm=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(mycrm)
			sqlc="select com_id,personid from crm_assignvap where com_id="&trim(mycrm(i))
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				   sqlu="insert into crm_assignvap(com_id,personid,adminpersonid) values("&trim(mycrm(i))&","&request.QueryString("personid")&","&session("personid")&")"
				   conn.execute(sqlu)
				   sqlu="update crm_openConfirm set assignflag=1 where com_id="&trim(mycrm(i))&""
				   conn.execute(sqlu)
				   mypersonid=0
				   
				   
			else
				'-------防止管理自己更新挑入时间
				if cint(rsc("personid"))=request.QueryString("personid") then
					response.Write("<script>alert('失败！已经是您的客户不要重复分配给自己！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
					closeconn()
					response.end()
				else
					sqlu="update crm_assignvap set personid="&request.QueryString("personid")&",fdate=getdate(),adminpersonid="&session("personid")&" where com_id="&trim(mycrm(i))&""
					conn.execute(sqlu)
					sqlu="update crm_openConfirm set assignflag=1 where com_id="&trim(mycrm(i))&""
					conn.execute(sqlu)
				end if
				
				mypersonid=rsc("personid")
			end if
			rsc.close
			set rsc=nothing
			sqlt="select com_id from crm_vap_complist where com_id="&trim(mycrm(i))&""
			set rst=conn.execute(sqlt)
			if rst.eof or rst.bof then
				sqlu="insert into crm_vap_complist(com_id) values("&trim(mycrm(i))&")"
				conn.execute(sqlu)
			end if
			rst.close
			set rst=nothing
			'--------写入新分配客户库
			sqlt="select com_id from crm_Assign_vapNew where com_id="&trim(mycrm(i))&""
			set rst=conn.execute(sqlt)
			if rst.eof or rst.bof then
				sqlu="insert into crm_Assign_vapNew(com_id,personid) values("&trim(mycrm(i))&","&request.QueryString("personid")&")"
				conn.execute(sqlu)
			end if
			rst.close
			set rst=nothing
				
				
			sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
			conn.execute(sql)
			'sql="update crm_assign_request set assignflag=1 where com_id in ("&trim(mycrm(i))&")"
			'conn.execute(sql)
			'------------写入客户记录
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"管理员客户vap分配"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&mypersonid&")"
			conn.execute(sqlp)
		next
	else
		'--------------普通客户分配 / end
		sqlcount="select count(id) from crm_assign where personid="&request.QueryString("personid")&sql1&""
		set rscount=conn.execute(sqlcount)
		if not rscount.eof then
			cuscount=rscount(0)
		else
			cuscount=0
		end if 
		if cint(cuscount)>=maxrccomp then
		   response.Write("<script>alert('失败！该销售人员客户数已经达到"&maxrccomp&"个！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		   closeconn()
		   response.end()
		end if
		for i=0 to ubound(mycrm)
			if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('失败！该销售人员客户数已经达到"&maxrccomp&"个！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
			end if
			sqlc="select com_id,personid from crm_assign where com_id="&trim(mycrm(i))
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				   sqlu="insert into crm_assign(com_id,personid,adminpersonid) values("&trim(mycrm(i))&","&request.QueryString("personid")&","&session("personid")&")"
				   conn.execute(sqlu)
				   cuscount=cuscount+1
				   
				   'sqlu="update crm_assignad set personid="&request.QueryString("personid")&",fdate=getdate() where com_id="&trim(mycrm(i))&""
				   'sqluu="update crm_continue_assign set personid="&request.QueryString("personid")&",fdate=getdate() where com_id="&trim(mycrm(i))&""
				   'conn.execute(sqlu)
				   'conn.execute(sqluu)
				   if session("userid")="10" then
					  'sqlp="delete from crm_3monthexpired_vipcomp where com_id="&trim(mycrm(i))&""
					  'conn.execute(sqlp)
				   end if
				  mypersonid=0 
			else
				if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('失败！该销售人员客户数已经达到"&maxrccomp&"个！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   closeconn()
				   response.end()
				end if
				'-------防止管理自己更新挑入时间
				if cint(rsc("personid"))=request.QueryString("personid") then
					response.Write("<script>alert('失败！已经是您的客户不要重复分配给自己！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
					closeconn()
					response.end()
				else
					'--------在临时客户库才可以更新
					'if cstr(rsc("personid"))="516" or cstr(rsc("personid"))=cstr(session("personid")) then
						sqlu="update crm_assign set personid="&request.QueryString("personid")&",fdate=getdate() where com_id="&trim(mycrm(i))&""
						conn.execute(sqlu)
					'else
						'
					'end if
				end if

				if cstr(rsc("personid"))=cstr(request.QueryString("personid")) then
				else
				cuscount=cuscount+1
				end if
				mypersonid=rsc("personid")
			end if
			rsc.close
			set rsc=nothing
			sql="update crm_assign_request set assignflag=1 where com_id in ("&trim(mycrm(i))&")"
			conn.execute(sql)
			
			
			'------------写入客户记录
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"管理员客户分配"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&mypersonid&")"
			conn.execute(sqlp)
		next
		'--------------普通客户分配 / end
	end if
	response.Write("<script>alert('成功！分配成功！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
'---------------------------end 
'---------放入预备品牌通
if selectcb<>"" and dostay="yubeippt" then
	mycrm=split(selectcb,",")
	for i=0 to ubound(mycrm)
		sqlp="select * from crm_category_info where property_id='"&trim(mycrm(i))&"' and property_value='10050001'"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqli="insert into crm_category_info(property_id,property_value) values('"&trim(mycrm(i))&"','10050001')"
			conn.execute(sqli)
		end if
		rsp.close
		set rsp=nothing
	next
	dostay="selec1toutmycrm"
end if
'---------放入重点客户准备
if selectcb<>"" and dostay="zhongdiankh" then
	mycrm=split(selectcb,",")
	for i=0 to ubound(mycrm)
		sqlp="select * from crm_category_info where property_id='"&trim(mycrm(i))&"' and property_value='10050002'"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqli="insert into crm_category_info(property_id,property_value) values('"&trim(mycrm(i))&"','10050002')"
			conn.execute(sqli)
		end if
		rsp.close
		set rsp=nothing
	next
	dostay="selec1toutmycrm"
end if
'放入我的客户
'-----------------------
if selectcb<>"" and dostay="selec1toutmycrm" then
    mycrm=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(mycrm)
			suc=0
			sqlcc="select com_id from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and Ccheck=0"
			set rscc=conn.execute(sqlcc)
			if not rscc.eof or not rscc.bof then
			   response.Write("<script>alert('失败！该客户为录入客户还未通过审核，不能放到我的客户库');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
			end if
			rscc.close
			set rscc=nothing
			sqlc="select com_id,personid from crm_assignvap where com_id="&trim(mycrm(i))&""
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				sqlu="insert into crm_assignvap(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
				conn.execute(sqlu)
				sqlt="select com_id from crm_vap_complist where com_id="&trim(mycrm(i))&""
				set rst=conn.execute(sqlt)
				if rst.eof or rst.bof then
					sqlu="insert into crm_vap_complist(com_id) values("&trim(mycrm(i))&")"
					conn.execute(sqlu)
				end if
				rst.close
				set rst=nothing
				sqlu="update crm_openConfirm set assignflag=1 where com_id="&trim(mycrm(i))&""
				conn.execute(sqlu)
				response.Write("<script>alert('成功！该客户已经分配到“我的vap客户表”')</script>")
				sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				conn.execute(sql)
				suc=1
			else
				if cstr(rsc("personid"))=cstr(session("personid")) then
				   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
				   conn.execute(sqlu)
				    
					sqlt="select com_id from crm_vap_complist where com_id="&trim(mycrm(i))&""
					set rst=conn.execute(sqlt)
					if rst.eof or rst.bof then
						sqlu="insert into crm_vap_complist(com_id) values("&trim(mycrm(i))&")"
						conn.execute(sqlu)
					end if
					rst.close
					set rst=nothing
				   response.Write("<script>alert('成功！该客户已经分配到“我的vap客户表”')</script>")
				   suc=1
				   sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				   conn.execute(sql)
			    else
				   response.Write("失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！</br>")
			    end if
			end if
			
			'------------写入客户记录
			if suc=1 then
				com_id=trim(mycrm(i))
				sDetail=request.Cookies("admin_user")&"放入我的vap客户库"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
			end if
		next
	elseif left(dotype,3)="sms" then
		for i=0 to ubound(mycrm)
			suc=0
			sqlcc="select com_id from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and Ccheck=0"
			set rscc=conn.execute(sqlcc)
			if not rscc.eof or not rscc.bof then
			   response.Write("<script>alert('失败！该客户为录入客户还未通过审核，不能放到我的客户库');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
			end if
			rscc.close
			set rscc=nothing
			sqlc="select com_id,personid from crm_assignsms where com_id="&trim(mycrm(i))&""
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				sqlu="insert into crm_assignsms(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
				conn.execute(sqlu)
				
				response.Write("<script>alert('成功！该客户已经分配到“我的sms客户表”')</script>")
				sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				conn.execute(sql)
				suc=1
			else
				if cstr(rsc("personid"))=cstr(session("personid")) then
				   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
				   conn.execute(sqlu)
				   response.Write("<script>alert('成功！该客户已经分配到“我的vap客户表”')</script>")
				   suc=1
				   sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				   conn.execute(sql)
			    else
				   response.Write("失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！</br>")
			    end if
			end if
			
			'------------写入客户记录
			if suc=1 then
				com_id=trim(mycrm(i))
				sDetail=request.Cookies("admin_user")&"放入我的sms客户库"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
			end if
		next
	else
		'------------1111111111 / begin
		'************
		sqlcount="select count(id) from crm_assign where personid="&session("personid")&sql1&""
		set rscount=conn.execute(sqlcount)
		if not rscount.eof then
			cuscount=rscount(0)
		else
			cuscount=0
		end if 
		'**************
		for i=0 to ubound(mycrm)
			suc=0
			sqlcc="select com_id from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and Ccheck=0"
			set rscc=conn.execute(sqlcc)
			if not rscc.eof or not rscc.bof then
			   response.Write("<script>alert('失败！该客户为录入客户还未通过审核，不能放到我的客户库');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
			end if
			rscc.close
			set rscc=nothing
			sqlc="select com_id,personid from crm_assign where com_id="&trim(mycrm(i))&""
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				'如果是被动掉入公海,并使相关的销售人员在7天之内不准再次挑入该客户
				sqls="select id from crm_beDroppedInSea where fdate>='"&date()-7&"' and personid="&session("personid")&" and com_id="&trim(mycrm(i))
				set rss=conn.execute(sqls)
				if not (rss.eof and rss.bof) then
					   response.Write("<script>alert('失败！由于该客户是因为30天内没有有效联系而被动掉入公海,所以该销售人员在7天之内不准再次挑入该客户');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
					   closeconn()
					   response.end()
				end if
				rss.close
				set rss=nothing
				'判断结束
			   sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
			   conn.execute(sqlu)
			   suc=1
			   response.Write("<script>alert('成功！该客户已经分配到“我的客户表”')</script>")
			else
			   if cstr(rsc("personid"))=cstr(session("personid")) then
				   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
				   conn.execute(sqlu)
				   cuscount=cuscount+1
				   response.Write("<script>alert('成功！该客户已经分配到“我的客户表”')</script>")
				   suc=1
			   else
				   response.Write("失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！</br>")
			   end if
			end if
			rsc.close
			set rsc=nothing
			'------------写入客户记录
			if suc=1 then
				com_id=trim(mycrm(i))
				sDetail=request.Cookies("admin_user")&"放入我的客户库"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
			end if
		next
		'-------------------------`11111111111111 / end
	end if
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'-------------------------begin
if selectcb<>"" and dostay="cbgonghai" then
	arrcom=split(selectcb,",")
	gtype=left(dotype,3)
	for i=0 to ubound(arrcom)
		sql="select id from crm_gonghai_cb where com_id="& trim(arrcom(i)) &" and gtype='"&gtype&"'"
		set rs=conn.execute(sql)
		if rs.eof or rs.bof then
			sqli="insert into crm_gonghai_cb(com_id,gtype) values("&trim(arrcom(i))&",'"&gtype&"')"
			conn.execute(sqli)
		end if
		rs.close
		set rs=nothing
	next
	dostay="delselec1tcrm"
end if
'放入公海
if selectcb<>"" and dostay="delselec1tcrm" then
	arrcom=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(arrcom)
			sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
			if session("userid")="10" then
				sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &")"
			end if
			conn.Execute sqldel,ly
			'------------放到公海库
			sqlp="select com_id from crm_publiccomp_vap where com_id in ("& trim(arrcom(i)) &")"
			set rsp=conn.execute(sqlp)
			if rsp.eof or rsp.bof then
				sqlpp="insert into crm_publiccomp_vap(com_id) values("& trim(arrcom(i)) &")"
				conn.execute(sqlpp)
				response.Write(sqlpp)
			end if
			rsp.close
			set rsp=nothing
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"放到vap公海"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
			conn.execute(sqlp)
		next
		telflag="4"
	elseif left(dotype,3)="sms" then
		for i=0 to ubound(arrcom)
			sqldel="delete from crm_assignsms where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
			if session("userid")="10" then
				sqldel="delete from crm_assignsms where com_id in ("& trim(arrcom(i)) &")"
			end if
			conn.Execute sqldel,ly
			
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"放到sms公海"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
			conn.execute(sqlp)
		next
		telflag="5"
	else
		'----------------------------------------111111111 / start
		for i=0 to ubound(arrcom)
			'------------没有小记不得入公海
			sqlt="select count(0) from comp_tel where com_id="& trim(arrcom(i)) &""
			set rst=conn.execute(sqlt)
			if not rst.eof or not rst.bof then
				telcount=rst(0)
			else
				telcount=0
			end if
			rst.close
			set rst=nothing
			
			zhuangdanFlag=request.QueryString("zhuangdanFlag")
			guangLiangFlag=request.QueryString("guangLiangFlag")
			
			if zhuangdanFlag=0 and guangLiangFlag<1 then
			'---------------关联客户客户放到公海
				if telcount=0 then
					response.Write("<script>alert('失败，没有写小记不能入公海');window.history.back(1)</script>")
					if request("closed")="1" then
						closeconn()
						response.End()
					else
						response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
						closeconn()
						response.End()
					end if
				end if
			end if
				
				sqldel="delete from crm_assign where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
				if session("userid")="10" then
					sqldel="delete from crm_assign where com_id in ("& trim(arrcom(i)) &")"
				end if
				conn.Execute sqldel,ly
				'-------------续签客户不续签原因
				if ly then
					sqlg="update crm_assign_gonghai set outflag=1 where com_id in ("& trim(arrcom(i)) &")"
					conn.execute(sqlg)
					if trim(request.Form("CauseID"))<>"" then
						sqlx="select * from crm_droppedinseaCause"
						set rsx=server.CreateObject("adodb.recordset")
						rsx.open sqlx,conn,1,3
						rsx.addnew()
						rsx("com_id")=trim(arrcom(i))
						rsx("CauseID")=trim(request.Form("CauseID"))
						rsx("CauseOther")=trim(request.Form("CauseOther"))
						rsx("Personid")=trim(session("personid"))
						rsx.update()
						rsx.close
						set rsx=nothing
					end if
					'------------放到公海库
					sqlp="select com_id from crm_publiccomp where com_id in ("& trim(arrcom(i)) &")"
					set rsp=conn.execute(sqlp)
					if rsp.eof or rsp.bof then
						sqlpp="insert into crm_publiccomp(com_id) values("& trim(arrcom(i)) &")"
						conn.execute(sqlpp)
					end if
					rsp.close
					set rsp=nothing
					'从续签分配库里删除
					sqldel="delete from crm_continue_assign where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
					if session("userid")="10" then
						sqldel="delete from crm_continue_assign where com_id in ("& trim(arrcom(i)) &")"
					end if
					conn.Execute sqldel
	
					com_id=trim(arrcom(i))
					sDetail=request.Cookies("admin_user")&"放到公海"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
					conn.execute(sqlp)
				end if
		next
		telflag="0"
	end if
	 '----------记录最后放到公海的时间，并标注
	if selectcb<>"" then
		arrcomp=split(selectcb,",")
		
		for i=0 to ubound(arrcomp)
			sqlm="select max(id) from comp_tel where com_id="&trim(arrcomp(i))&" and telflag="&telflag&""
			set rsm=conn.execute(sqlm)
			if not rsm.eof or not rsm.bof then
				maxtelid=rsm(0)
			else
				maxtelid=0
			end if
			rsm.close
			set rsm=nothing
			if maxtelid="" or isnull(maxtelid) then maxtelid=0
			if trim(arrcomp(i))<>"" then
				sql="select * from crm_assign_out where com_id="&trim(arrcomp(i))&" and telid="&maxtelid&" and teltype=0"
				set rs=conn.execute(sql)
				if rs.eof or rs.bof then
					sqln="insert into crm_assign_out (com_id,telid,teltype) values("&trim(arrcomp(i))&","&maxtelid&","&telflag&")"
					conn.execute(sqln)
				end if
				rs.close
				set rs=nothing
			end if
		next
	end if 
	'-------------------------11111111111111  / end
	response.Write("<script>alert('成功！已入公海')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'-----------------------------------------end

'-------------------------begin
'被管理员放入公海

if selectcb<>"" and dostay="gotogonghaiAdmin" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlp="select personid from crm_assign where com_id="&trim(arrcom(i))&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			mypersonid=rsp(0)
		else
			mypersonid=0
		end if
		rsp.close
		set rsp=nothing
		if session("userid")="10" or session("userid")="13" then
			sqldel="delete from crm_assign where com_id in ("& trim(arrcom(i)) &")"
		end if
		conn.Execute sqldel,ly
		'------------------删除广告库
		'sqldel="delete from crm_assignad where com_id in ("& trim(arrcom(i)) &")"
		'conn.execute(sqldel)
		
		'------------放到公海库
		sqlp="select com_id from crm_publiccomp where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_publiccomp(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
			response.Write(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		'从续签分配库里删除
		if session("userid")="10" or session("userid")="13"  then
			sqldel="delete from crm_continue_assign where com_id in ("& trim(arrcom(i)) &")"
		end if
		conn.Execute sqldel
		
		sqldel="delete from crm_assignVIP where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
		conn.Execute sqldel
		
		com_id=trim(arrcom(i))
		sDetail="被管理员"&request.Cookies("admin_user")&"放到公海"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&mypersonid&")"
		conn.execute(sqlp)
	next
    '----------记录最后放到公海的时间，并标注
	if selectcb<>"" then
		arrcomp=split(selectcb,",")
		for i=0 to ubound(arrcomp)
			sqlm="select max(id) from comp_tel where com_id="&trim(arrcomp(i))&""
			set rsm=conn.execute(sqlm)
			if not rsm.eof or not rsm.bof then
				maxtelid=rsm(0)
			else
				maxtelid=0
			end if
			rsm.close
			set rsm=nothing
			if maxtelid="" or isnull(maxtelid) then maxtelid=0
			if trim(arrcomp(i))<>"" then
				sql="select * from crm_assign_out where com_id="&trim(arrcomp(i))&" and telid="&maxtelid&" and teltype=0"
				set rs=conn.execute(sql)
				if rs.eof or rs.bof then
					sqln="insert into crm_assign_out (com_id,telid,teltype) values("&trim(arrcomp(i))&","&maxtelid&",0)"
					conn.execute(sqln)
				end if
				rs.close
				set rs=nothing
			end if
		next
	end if 
	response.Write("<script>alert('成功！已入公海')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'-----------------------------------------end

if selectcb<>"" and dostay="outselec1tcrm" then
    sqldel="delete from crm_publiccomp where com_id in ("& selectcb &")"
	'conn.Execute(sqldel)
	response.Write("<script>alert('成功！已出公海')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'已放入待分配
if selectcb<>"" and dostay="outselec1tcrmtt" then
    sqldel="delete from crm_assign where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	sqldel="delete from temp_activeComp where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Write("<script>alert('成功！已放入待分配')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if
'--------------放到idc再生通保留库里
if selectcb<>"" and dostay="idcbaoliu" then
	if selectcb<>"" then
		arrcomp=split(selectcb,",")
		for i=0 to ubound(arrcomp)
			sqlc="select id from temp_baoliucomp where com_id="&trim(arrcomp(i))&""
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				sqlb="insert into temp_baoliucomp (com_id) values("&trim(arrcomp(i))&")"
				conn.execute(sqlb)
			end if
			rsc.close
			set rsc=nothing
		next
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
end if
'----------------审核录入的客户
if selectcb<>"" and dostay="shenheluru" then
	
	mycrm=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(mycrm)
			sqlc="select com_id from crm_assignvap where com_id="&trim(mycrm(i))&""
			set rsc=conn.execute(sqlc)
			sqld="select personid from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and ccheck=0"
			set rsd=conn.execute(sqld)
			if rsc.eof or rsc.bof then
				sqlu="insert into crm_assignvap(com_id,personid) values("&trim(mycrm(i))&","&rsd(0)&")"
				conn.execute(sqlu)
				sqlt="update crm_InsertCompWeb set Ccheck=1 where com_id in ("& trim(mycrm(i)) &")"
				conn.Execute(sqlt)
			end if
			rsd.close
			set rsd=nothing
			rsc.close
			set rsc=nothing
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"审核录入的vap客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	elseif left(dotype,3)="sms" then
		for i=0 to ubound(mycrm)
			sqlc="select com_id from crm_assignsms where com_id="&trim(mycrm(i))&""
			set rsc=conn.execute(sqlc)
			sqld="select personid from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and ccheck=0"
			set rsd=conn.execute(sqld)
			if rsc.eof or rsc.bof then
				sqlu="insert into crm_assignsms(com_id,personid) values("&trim(mycrm(i))&","&rsd(0)&")"
				conn.execute(sqlu)
				sqlt="update crm_InsertCompWeb set Ccheck=1 where com_id in ("& trim(mycrm(i)) &")"
				conn.Execute(sqlt)
			end if
			rsd.close
			set rsd=nothing
			rsc.close
			set rsc=nothing
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"审核录入的sms客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		for i=0 to ubound(mycrm)
			sqld="select personid from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and ccheck=0"
			set rsd=conn.execute(sqld)
			
			if not rsd.eof or not rsd.bof then
			while not rsd.eof
				sqlc="select com_id from crm_assign where com_id="&trim(mycrm(i))&""
				set rsc=conn.execute(sqlc)
				if rsc.eof or rsc.bof then
					sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&rsd(0)&")"
					conn.execute(sqlu)
				end if
				rsc.close
				set rsc=nothing
				sqlt="update crm_InsertCompWeb set Ccheck=1 where com_id in ("& trim(mycrm(i)) &")"
				conn.Execute(sqlt)
				'response.Write(sqlc)
			rsd.movenext
			wend
			end if
			rsd.close
			set rsd=nothing
			'response.Write(sqld)
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"审核录入的客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	closeconn()
	response.End()
end if
'--------------------------------------放入待开通
if selectcb<>"" and dostay="openzst" then
    sqldel="update crm_assign set Waiter_Open=1 where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	'------------写入客户记录
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"放入待开通"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	response.Write("<script>alert('成功！已放入待开通的再生通')</script>")
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	
	response.End()
end if

%>
