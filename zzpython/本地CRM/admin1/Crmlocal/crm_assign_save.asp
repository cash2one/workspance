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

if lcase(dostay)="waitopenzst" then	'����ͨ
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

'----------VAP��������
if selectcb<>"" and dostay="droptosihai" and left(dotype,3)="vap" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
		if session("userid")="10" then
			sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &")"
		end if
		conn.Execute sqldel,ly
		'------------�ŵ�������
		sqlp="select com_id from crm_publiccomp_vap where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_publiccomp_vap(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		'----------��������
		sqlp="select com_id from crm_notBussiness where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_notBussiness(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"�ŵ�vap����"
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
'---------seo�ͻ��Ѿ�����
if selectcb<>"" and dostay="seounline" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sqlp="update company_service set online=0 where company_id="&com_id&""
		conn.execute(sqlp)
		
		sDetail=request.Cookies("admin_user")&"�ŵ�������SEO�ͻ�"
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
'---------seo�ͻ��Ѿ�����
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
		sDetail=request.Cookies("admin_user")&"�ŵ�������"
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

'---------seo�ͻ�����
if selectcb<>"" and dostay="seoonline" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sqlp="update company_service set online=1 where company_id="&com_id&""
		conn.execute(sqlp)
		
		sDetail=request.Cookies("admin_user")&"�ŵ�������SEO�ͻ�"
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
'-------�ŵ�����Ʒ�ء�

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
					'------------д��ͻ������¼
					com_id=trim(arrcom(i))
					sDetail=request.Cookies("admin_user")&"����vap��Ʒ��"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				end if
				rsm.close
				set rsm=nothing
			end if
			rs.close
			set rs=nothing
			if ly then
				response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����Ʒ�ء�')</script>")
			else
				response.Write("<script>alert('ʧ�ܣ��ÿͻ��������Ŀͻ����ܷŵ�����Ʒ�ء�')</script>")
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
					'------------д��ͻ������¼
					com_id=trim(arrcom(i))
					sDetail=request.Cookies("admin_user")&"�����Ʒ��"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				end if
				rsm.close
				set rsm=nothing
			end if
			rs.close
			set rs=nothing
			if ly then
				response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����Ʒ�ء�')</script>")
			else
				response.Write("<script>alert('ʧ�ܣ��ÿͻ��������Ŀͻ����ܷŵ�����Ʒ�ء�')</script>")
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
'------------------�ŵ���ʱ��
if selectcb<>"" and dostay="assignTotemp" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sql="select com_id from crm_AssignTemp where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rs=conn.execute(sql)
		if rs.eof or rs.bof then
			sqlu="insert into crm_AssignTemp(com_id,personid) values("&trim(arrcom(i))&","&session("personid")&")"
			conn.execute sqlu,ly
			'------------д��ͻ������¼
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"������ʱ��"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
		rs.close
		set rs=nothing
		if ly then
			response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����ʱ�⡱')</script>")
		else
			response.Write("<script>alert('ʧ�ܣ��ÿͻ��������Ŀͻ����ܷŵ�����ʱ�⡱')</script>")
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
'------------------ȡ���ŵ���ʱ��
if selectcb<>"" and dostay="cancerAssignTotemp" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sql="select com_id from crm_AssignTemp where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			sqlu="delete from crm_AssignTemp where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
			conn.execute sqlu,ly
			'------------д��ͻ������¼
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"ȡ��������ʱ��"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
		rs.close
		set rs=nothing
		if ly then
			response.Write("<script>alert('�ɹ����ÿͻ��Ѿ�ȡ���ŵ�����ʱ�⡱')</script>")
		else
			response.Write("<script>alert('ʧ�ܣ��ÿͻ��������Ŀͻ����ܲ���')</script>")
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
'------------------����Ϊ��ͻ�
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
			'------------д��ͻ������¼
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"�����ͻ���"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('�ɹ����ÿͻ��Ѿ������ͻ���')</script>")
		else
			response.Write("<script>alert('ʧ�ܣ��ÿͻ��Ѿ������ͻ���')</script>")
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
'��ʱ�Ѻ���SEO�ͻ����
if selectcb<>"" and dostay="hezuoseo" then
	arrcom=split(selectcb,",")
	url=request.ServerVariables("HTTP_REFERER")
	if ubound(arrcom)>0 then
		response.Write("<script>alert('һ��ֻ���ύһ���ͻ�')</script>")
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
'SEOȷ������
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
			sDetail=request.Cookies("admin_user")&"SEO����ȷ��"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			
		end if
	next
	response.Write("<script>alert('�ɹ����ÿͻ�ȷ��SEO����')</script>")
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
			sDetail=request.Cookies("admin_user")&"ȡ��SEO����ȷ��"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
	next
	response.Write("<script>alert('�ɹ����ÿͻ�ȡ����ȷ��SEO����')</script>")
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

'����Ϊע����ͻ�

if selectcb<>"" and dostay="do_zhucejishenhe" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="select * from crm_compzhuceji where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------д��ͻ������¼
			
			
		else
			sql="insert into crm_compzhuceji(com_id) values("&com_id&")"
			conn.execute(sql)
			sDetail=request.Cookies("admin_user")&"����Ϊע����ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			
		end if
	next
	response.Write("<script>alert('�ɹ����ÿͻ�����Ϊע����ͻ�')</script>")
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

'ȡ��Ϊע����ͻ�

if selectcb<>"" and dostay="undo_zhucejishenhe" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="select * from crm_compzhuceji where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------д��ͻ������¼
			sql="delete from  crm_compzhuceji where com_id in("&com_id&")"
			conn.execute(sql)
			sDetail=request.Cookies("admin_user")&"ȡ��Ϊע����ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
	next
	response.Write("<script>alert('�ɹ����ÿͻ�ȡ��Ϊע����ͻ�')</script>")
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


'------------------���4�ǿͻ�
if selectcb<>"" and dostay="shenhe4star" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="update crm_To4star set checked=1 where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------д��ͻ������¼
			
			sDetail=request.Cookies("admin_user")&"4�����ͨ��"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('�ɹ����ÿͻ��Ѿ�ͨ��4�����')</script>")
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
'------------------���5�ǿͻ�
if selectcb<>"" and dostay="shenhe5star" then
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sql="update crm_To5star set checked=1 where com_id in ("&com_id&")"
		conn.execute sql,ly
		if ly then
		'------------д��ͻ������¼
			
			sDetail=request.Cookies("admin_user")&"5�����ͨ��"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('�ɹ����ÿͻ��Ѿ�ͨ��5�����')</script>")
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
'------------------ȡ�������ͻ���
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
		'------------д��ͻ������¼
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"ȡ�������ͻ���"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
			response.Write("<script>alert('�ɹ����ÿͻ��Ѿ�ȡ�������ͻ���')</script>")
		else
			response.Write("<script>alert('ʧ�ܣ��ÿͻ����Ǵ�ͻ���')</script>")
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

'*�ͻ�����
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
				response.Write("<script>alert('�ɹ����ѷ����ҵĹ���ͻ���')</script>")
			else
				response.Write("<script>alert('ʧ�ܣ����ܰѸÿͻ�����Ϊ����ͻ�')</script>")
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
'---------�������õĿͻ�
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
'--------------����Ҧ���´�
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
		'------------д��ͻ������¼
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"�ŵ���Ҧ���´�"
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
'---------�ŵ��ҵ���ǩ�ͻ���
if selectcb<>"" and dostay="IntoContinueCrm" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqla="select com_id,personid from crm_assign where com_id="&trim(arrcom(i))&""
		set rsa=conn.execute(sqla)
		if not (rsa.eof and rsa.bof) then
			if cstr(session("personid"))=cstr(rsa("personid")) then
			else
			response.Write("<script>alert('�ÿͻ��Ѿ���ĳ�˵�����ͨ�����棡');parent.parent.window.close()</script>")
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
		'------------д��ͻ������¼
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"�ŵ��ҵ���ǩ��"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>alert('����ɹ���');parent.parent.window.close()</script>")
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
'---------�ŵ��ݲ���ǩ�ͻ���
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
				response.Write("<script>alert('�ÿͻ������������ǩ�ͻ��⣬���ȷŵ���ǩ�ͻ��⣡');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
		'------------д��ͻ���¼
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"�ݲ��ŵ��ҵ���ǩ��"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	if request("closed")="1" then
		response.Write("<script>alert('����ɹ���');parent.parent.window.close()</script>")
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
'-------------ȡ������ 
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
					response.Write("<script>alert('�ɹ�����ȡ������˿ͻ�')</script>")
				else
					response.Write("<script>alert('ʧ�ܣ�����ȡ���ÿͻ�')</script>")
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

'�ŵ���Ʒ��ͨ����
if selectcb<>"" and dostay="selec1tToweb" then
    sql="update comp_sales set TowebContact=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	'------------д��ͻ���¼
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"�ŵ�Ʒ��ͨ��"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	if ly then
		response.Write("<script>alert('�ɹ����ѷŵ���Ʒ��ͨ����')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ��ŵ���Ʒ��ͨ����')</script>")
	end if
	
	closeconn()
	response.End()
end if
'------------�����ص�ͻ�
if selectcb<>"" and dostay="zhongdian" then
	if left(dotype,3)="vap" then
		if session("Partadmin")<>"0" then
			sql="update crm_assignvap set Emphases=1 where com_id in ("& selectcb &")"
			conn.Execute sql,ly
		else
			sql="update crm_assignvap set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
			conn.Execute sql,ly
		end if
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"����Ϊvap�ص�ͻ�"
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
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"����Ϊ�ص�ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('�ɹ�����Ϊ�ص�ͻ�')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ�����Ϊ�ҵ��ص�ͻ�')</script>")
	end if
	closeconn()
	response.End()
end if
'---------ȡ���ص�ͻ�
if selectcb<>"" and dostay="zhongdianout" then
	if left(dotype,3)="vap" then
		if session("Partadmin")<>"0" then
			sql="update crm_assignvap set Emphases=0 where com_id in ("& selectcb &") "
			conn.Execute sql,ly
		else
			sql="update crm_assignvap set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
			conn.Execute sql,ly
		end if
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"ȡ��Ϊvap�ص�ͻ�"
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
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"ȡ��Ϊ�ص�ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('�ɹ����ÿͻ���ȡ��Ϊ�ҵ��ص�ͻ�')</script>")
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
		response.Write("<script>alert('ʧ�ܣ��������ñ��˵Ŀͻ�Ϊ�ҵ��ص�ͻ�')</script>")
	end if
	closeconn()
	response.End()
end if
'---------------����ص�ͻ�
if selectcb<>"" and dostay="zhongdiansh" then
	if left(dotype,3)="vap" then
		sql="update crm_assignvap set Emphases_check=1 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"���vap�ص�ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		sql="update crm_assign set Emphases_check=1 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"����ص�ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('�ɹ���������˸��ص�ͻ�')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ�����Ϊ�ҵ��ص�ͻ�')</script>")
	end if
	
	closeconn()
	response.End()
end if
'---------------------------end
'---------------ȡ������ص�ͻ�
if selectcb<>"" and dostay="zhongdianshno" then
	if left(dotype,3)="vap" then
		sql="update crm_assignvap set Emphases_check=0 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"ȡ�����vap�ص�ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	else
		sql="update crm_assign set Emphases_check=0 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"ȡ������ص�ͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	if ly then
		response.Write("<script>alert('�ɹ���������˸��ص�ͻ�')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ�����Ϊ�ҵ��ص�ͻ�')</script>")
	end if
	
	closeconn()
	response.End()
end if
'---------------------------end
'----------------begin
'-------------����Ա����ͻ�
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
				'-------��ֹ�����Լ���������ʱ��
				if cint(rsc("personid"))=request.QueryString("personid") then
					response.Write("<script>alert('ʧ�ܣ��Ѿ������Ŀͻ���Ҫ�ظ�������Լ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
			'--------д���·���ͻ���
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
			'------------д��ͻ���¼
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"����Ա�ͻ�vap����"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&mypersonid&")"
			conn.execute(sqlp)
		next
	else
		'--------------��ͨ�ͻ����� / end
		sqlcount="select count(id) from crm_assign where personid="&request.QueryString("personid")&sql1&""
		set rscount=conn.execute(sqlcount)
		if not rscount.eof then
			cuscount=rscount(0)
		else
			cuscount=0
		end if 
		if cint(cuscount)>=maxrccomp then
		   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		   closeconn()
		   response.end()
		end if
		for i=0 to ubound(mycrm)
			if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
				   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   closeconn()
				   response.end()
				end if
				'-------��ֹ�����Լ���������ʱ��
				if cint(rsc("personid"))=request.QueryString("personid") then
					response.Write("<script>alert('ʧ�ܣ��Ѿ������Ŀͻ���Ҫ�ظ�������Լ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
					closeconn()
					response.end()
				else
					'--------����ʱ�ͻ���ſ��Ը���
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
			
			
			'------------д��ͻ���¼
			com_id=trim(mycrm(i))
			sDetail=request.Cookies("admin_user")&"����Ա�ͻ�����"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&mypersonid&")"
			conn.execute(sqlp)
		next
		'--------------��ͨ�ͻ����� / end
	end if
	response.Write("<script>alert('�ɹ�������ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
'---------------------------end 
'---------����Ԥ��Ʒ��ͨ
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
'---------�����ص�ͻ�׼��
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
'�����ҵĿͻ�
'-----------------------
if selectcb<>"" and dostay="selec1toutmycrm" then
    mycrm=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(mycrm)
			suc=0
			sqlcc="select com_id from crm_InsertCompWeb where com_id="&trim(mycrm(i))&" and Ccheck=0"
			set rscc=conn.execute(sqlcc)
			if not rscc.eof or not rscc.bof then
			   response.Write("<script>alert('ʧ�ܣ��ÿͻ�Ϊ¼��ͻ���δͨ����ˣ����ܷŵ��ҵĿͻ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
				response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵ�vap�ͻ���')</script>")
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
				   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵ�vap�ͻ���')</script>")
				   suc=1
				   sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				   conn.execute(sql)
			    else
				   response.Write("ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡</br>")
			    end if
			end if
			
			'------------д��ͻ���¼
			if suc=1 then
				com_id=trim(mycrm(i))
				sDetail=request.Cookies("admin_user")&"�����ҵ�vap�ͻ���"
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
			   response.Write("<script>alert('ʧ�ܣ��ÿͻ�Ϊ¼��ͻ���δͨ����ˣ����ܷŵ��ҵĿͻ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
				
				response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵ�sms�ͻ���')</script>")
				sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				conn.execute(sql)
				suc=1
			else
				if cstr(rsc("personid"))=cstr(session("personid")) then
				   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
				   conn.execute(sqlu)
				   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵ�vap�ͻ���')</script>")
				   suc=1
				   sql="delete from crm_notBussiness where com_id="&trim(mycrm(i))&""
				   conn.execute(sql)
			    else
				   response.Write("ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡</br>")
			    end if
			end if
			
			'------------д��ͻ���¼
			if suc=1 then
				com_id=trim(mycrm(i))
				sDetail=request.Cookies("admin_user")&"�����ҵ�sms�ͻ���"
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
			   response.Write("<script>alert('ʧ�ܣ��ÿͻ�Ϊ¼��ͻ���δͨ����ˣ����ܷŵ��ҵĿͻ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
			end if
			rscc.close
			set rscc=nothing
			sqlc="select com_id,personid from crm_assign where com_id="&trim(mycrm(i))&""
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
				'����Ǳ������빫��,��ʹ��ص�������Ա��7��֮�ڲ�׼�ٴ�����ÿͻ�
				sqls="select id from crm_beDroppedInSea where fdate>='"&date()-7&"' and personid="&session("personid")&" and com_id="&trim(mycrm(i))
				set rss=conn.execute(sqls)
				if not (rss.eof and rss.bof) then
					   response.Write("<script>alert('ʧ�ܣ����ڸÿͻ�����Ϊ30����û����Ч��ϵ���������빫��,���Ը�������Ա��7��֮�ڲ�׼�ٴ�����ÿͻ�');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
					   closeconn()
					   response.end()
				end if
				rss.close
				set rss=nothing
				'�жϽ���
			   sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
			   conn.execute(sqlu)
			   suc=1
			   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĿͻ���')</script>")
			else
			   if cstr(rsc("personid"))=cstr(session("personid")) then
				   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
				   conn.execute(sqlu)
				   cuscount=cuscount+1
				   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĿͻ���')</script>")
				   suc=1
			   else
				   response.Write("ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡</br>")
			   end if
			end if
			rsc.close
			set rsc=nothing
			'------------д��ͻ���¼
			if suc=1 then
				com_id=trim(mycrm(i))
				sDetail=request.Cookies("admin_user")&"�����ҵĿͻ���"
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
'���빫��
if selectcb<>"" and dostay="delselec1tcrm" then
	arrcom=split(selectcb,",")
	if left(dotype,3)="vap" then
		for i=0 to ubound(arrcom)
			sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
			if session("userid")="10" then
				sqldel="delete from crm_assignvap where com_id in ("& trim(arrcom(i)) &")"
			end if
			conn.Execute sqldel,ly
			'------------�ŵ�������
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
			sDetail=request.Cookies("admin_user")&"�ŵ�vap����"
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
			sDetail=request.Cookies("admin_user")&"�ŵ�sms����"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
			conn.execute(sqlp)
		next
		telflag="5"
	else
		'----------------------------------------111111111 / start
		for i=0 to ubound(arrcom)
			'------------û��С�ǲ����빫��
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
			'---------------�����ͻ��ͻ��ŵ�����
				if telcount=0 then
					response.Write("<script>alert('ʧ�ܣ�û��дС�ǲ����빫��');window.history.back(1)</script>")
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
				'-------------��ǩ�ͻ�����ǩԭ��
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
					'------------�ŵ�������
					sqlp="select com_id from crm_publiccomp where com_id in ("& trim(arrcom(i)) &")"
					set rsp=conn.execute(sqlp)
					if rsp.eof or rsp.bof then
						sqlpp="insert into crm_publiccomp(com_id) values("& trim(arrcom(i)) &")"
						conn.execute(sqlpp)
					end if
					rsp.close
					set rsp=nothing
					'����ǩ�������ɾ��
					sqldel="delete from crm_continue_assign where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
					if session("userid")="10" then
						sqldel="delete from crm_continue_assign where com_id in ("& trim(arrcom(i)) &")"
					end if
					conn.Execute sqldel
	
					com_id=trim(arrcom(i))
					sDetail=request.Cookies("admin_user")&"�ŵ�����"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
					conn.execute(sqlp)
				end if
		next
		telflag="0"
	end if
	 '----------��¼���ŵ�������ʱ�䣬����ע
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
	response.Write("<script>alert('�ɹ������빫��')</script>")
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
'������Ա���빫��

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
		'------------------ɾ������
		'sqldel="delete from crm_assignad where com_id in ("& trim(arrcom(i)) &")"
		'conn.execute(sqldel)
		
		'------------�ŵ�������
		sqlp="select com_id from crm_publiccomp where com_id in ("& trim(arrcom(i)) &")"
		set rsp=conn.execute(sqlp)
		if rsp.eof or rsp.bof then
			sqlpp="insert into crm_publiccomp(com_id) values("& trim(arrcom(i)) &")"
			conn.execute(sqlpp)
			response.Write(sqlpp)
		end if
		rsp.close
		set rsp=nothing
		'����ǩ�������ɾ��
		if session("userid")="10" or session("userid")="13"  then
			sqldel="delete from crm_continue_assign where com_id in ("& trim(arrcom(i)) &")"
		end if
		conn.Execute sqldel
		
		sqldel="delete from crm_assignVIP where com_id in ("& trim(arrcom(i)) &") and personid="&session("personid")&""
		conn.Execute sqldel
		
		com_id=trim(arrcom(i))
		sDetail="������Ա"&request.Cookies("admin_user")&"�ŵ�����"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&mypersonid&")"
		conn.execute(sqlp)
	next
    '----------��¼���ŵ�������ʱ�䣬����ע
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
	response.Write("<script>alert('�ɹ������빫��')</script>")
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
	response.Write("<script>alert('�ɹ����ѳ�����')</script>")
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
'�ѷ��������
if selectcb<>"" and dostay="outselec1tcrmtt" then
    sqldel="delete from crm_assign where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	sqldel="delete from temp_activeComp where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Write("<script>alert('�ɹ����ѷ��������')</script>")
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
'--------------�ŵ�idc����ͨ��������
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
'----------------���¼��Ŀͻ�
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
			sDetail=request.Cookies("admin_user")&"���¼���vap�ͻ�"
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
			sDetail=request.Cookies("admin_user")&"���¼���sms�ͻ�"
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
			sDetail=request.Cookies("admin_user")&"���¼��Ŀͻ�"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
	end if
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	closeconn()
	response.End()
end if
'--------------------------------------�������ͨ
if selectcb<>"" and dostay="openzst" then
    sqldel="update crm_assign set Waiter_Open=1 where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	'------------д��ͻ���¼
	arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		com_id=trim(arrcom(i))
		sDetail=request.Cookies("admin_user")&"�������ͨ"
		sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
		conn.execute(sqlp)
	next
	response.Write("<script>alert('�ɹ����ѷ������ͨ������ͨ')</script>")
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
