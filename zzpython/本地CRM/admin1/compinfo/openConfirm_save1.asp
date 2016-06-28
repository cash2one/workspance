<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../../cn/sources/Md5.asp"-->
<%
com_id=request.Form("com_id")
confirmID=request.Form("confirmID")
newemail=trim(request.Form("newemail"))
payTime=request.Form("payTime")
payCorp=request.Form("payCorp")
payUserName=request.Form("payUserName")
payService=request.Form("payService")
payMoney=request.Form("payMoney")
Moneydetail=request.Form("Moneydetail")
payBank=request.Form("payBank")
saler=request.Form("saler")
payType=request.Form("payType")
payFax=request.Form("payFax")
payReturn=request.Form("payReturn")
IDCard=request.Form("IDCard")
remark=request.Form("remark")
customType=request.Form("customType")
customerFrom=request.Form("customerFrom")
telNum=request.Form("telNum")
personid=request.Form("personid")
userid=request.Form("userid")

salestime=request.Form("salestime")
bigfine=request.Form("bigfine")
salestype=request.Form("salestype")
webcontrol=request.Form("webcontrol")
salesOther=request.Form("salesOther")

com_contactperson=request.Form("com_contactperson")
com_mobile=request.Form("com_mobile")
com_ly1=request.Form("com_ly1")
com_ly2=request.Form("com_ly2")
com_zq=request.Form("com_zq")
com_fwq=request.Form("com_fwq")
com_khdq=request.Form("com_khdq")
com_pro=request.Form("com_pro")
com_cpjb=request.Form("com_cpjb")
com_cxfs=request.Form("com_cxfs")
com_regtime=request.Form("com_regtime")
if com_regtime="" then com_regtime=date
com_hkfs=request.Form("com_hkfs")
com_logincount=request.Form("com_logincount")
com_gjd=request.Form("com_gjd")
com_servernum=request.Form("com_servernum")

order_no=request.Form("order_no")
apply_group=request.Form("apply_group")
mbradio=request.Form("mbradio")

realname=saler
order_no1=md5(order_no,16)

	
	
    '---------------添加到到单统计
	service_type2=request.Form("service_type2")
	service_type=request.Form("service_type")
	service_type1=request.Form("service_type1")
	arrservice_type2=split(service_type2,",")
	arrorder_no=split(order_no,"|")
	arrapply_group=split(apply_group,"|")
	for i=0 to ubound(arrservice_type2)
		sql="select * from renshi_salesIncome where order_no='"&md5(trim(arrorder_no(i)),16)&"'"
		set rs=server.CreateObject("adodb.recordset")
		rs.open sql,conn,1,3
		if rs.eof or rs.bof then
			rs.addnew
			
			rs("order_no")=md5(trim(arrorder_no(i)),16)
			rs("personid")=personid
			rs("userid")=userid
			rs("realname")=realname
			rs("sales_date")=payTime
			rs("com_id")=com_id
			rs("service_type")=trim(arrservice_type2(i))
			rs("service_type1")=service_type1
			rs("sales_type")=customType
			if service_type1=trim(arrservice_type2(i)) then
				rs("sales_price")=payMoney
			else
				rs("sales_price")=0
			end if
			if newemail<>"" then
				rs("sales_email")=newemail
			end if
			rs("sales_mobile")=left(com_mobile,40)
			rs("sales_bz")=remark
			rs("com_contactperson")=com_contactperson
			rs("com_mobile")=com_mobile
			rs("com_ly1")=com_ly1
			rs("com_ly2")=com_ly2
			rs("com_zq")=com_zq
			rs("com_fwq")=com_fwq
			rs("com_khdq")=com_khdq
			rs("com_pro")=com_pro
			rs("com_cpjb")=com_cpjb
			rs("com_cxfs")=com_cxfs
			rs("com_regtime")=com_regtime
			rs("com_hkfs")=com_hkfs
			rs("com_logincount")=com_logincount
			rs("com_gjd")=com_gjd
			rs("com_servernum")=com_servernum
			rs.update()
		end if
		rs.close
		set rs=nothing
		if trim(arrservice_type2(i))="定金" then
			noconfirm=1
		else
			noconfirm=0
		end if
	next
	'-------------------
	if noconfirm=0 then
		sql="select * from crm_openConfirm where com_id="&com_id&" and confirmID='"&trim(order_no1)&"'"
		set rs=server.CreateObject("adodb.recordset")
		rs.open sql,conn,1,3
		if rs.eof or rs.bof then
			rs.addnew
			rs("com_id")=com_id
			rs("confirmID")=order_no1
			rs("payTime")=payTime
			rs("payUserName")=payUserName
			rs("payMoney")=payMoney
			rs("saler")=saler
			rs("newemail")=newemail
			rs("remark")=remark
			rs("customType")=customType
			rs("salerID")=personid
			'if mbradio="1" or mbradio="3" then
			if mbradio="1" then
				rs("assignflag")="1"
				'-------------分配到VAP库里
				sqlc="select com_id,personid from crm_assignvap where com_id="&cstr(com_id)
				set rsc=conn.execute(sqlc)
				if rsc.eof or rsc.bof then
					   sqlu="insert into crm_assignvap(com_id,personid,adminpersonid) values("&cstr(com_id)&","&session("personid")&","&session("personid")&")"
					   conn.execute(sqlu)
					   'sqlu="update crm_openConfirm set assignflag=1 where com_id="&cstr(com_id)&""
					   'conn.execute(sqlu)
				end if
				rsc.close
				set rsc=nothing
				sqlt="select com_id from crm_vap_complist where com_id="&cstr(com_id)&""
				set rst=conn.execute(sqlt)
				if rst.eof or rst.bof then
					sqlu="insert into crm_vap_complist(com_id) values("&cstr(com_id)&")"
					conn.execute(sqlu)
				end if
				rst.close
				set rst=nothing
				'--------写入新分配客户库
				sqlt="select com_id from crm_Assign_vapNew where com_id="&cstr(com_id)&""
				set rst=conn.execute(sqlt)
				if rst.eof or rst.bof then
					sqlu="insert into crm_Assign_vapNew(com_id,personid) values("&cstr(com_id)&","&session("personid")&")"
					conn.execute(sqlu)
				end if
				rst.close
				set rst=nothing
					
					
				sql="delete from crm_notBussiness where com_id="&cstr(com_id)&""
				conn.execute(sql)
				'------------写入客户记录
				
				sDetail=request.Cookies("admin_user")&"vap提交到单确认分配"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail,mypersonid) values("&com_id&","&session("personid")&",'"&sDetail&"',"&session("personid")&")"
				conn.execute(sqlp)
			end if
			rs.update
		end if
		rs.close
		set rs=nothing
	end if
response.Redirect("openconfirm_saveSuc.asp?com_id="&com_id&"&apply_group="&apply_group&"&service_type2="&service_type2)
response.Write("<script>parent.parent.window.close()</script>")
conn.close
set conn=nothing
%>

