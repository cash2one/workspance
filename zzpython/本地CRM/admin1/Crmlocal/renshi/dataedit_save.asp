<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
com_id=request.Form("com_id")
confirmID=request.Form("confirmID")
newemail=request.Form("newemail")
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

dingid=request.Form("dingid")

realname=saler


	sql="select * from crm_openConfirm where com_id="&com_id&" and confirmID='"&trim(order_no)&"'"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if not rs.eof or not rs.bof then
		'rs("com_id")=com_id
		'rs("confirmID")=order_no
		rs("payTime")=payTime
		'rs("payUserName")=payUserName
		rs("payMoney")=payMoney
		rs("saler")=saler
		rs("newemail")=newemail
		rs("remark")=remark
		rs("customType")=customType
		rs("salerID")=personid
		if mbradio="1" or mbradio="3" then
			rs("assignflag")="1"
		end if
		rs.update
	end if
	rs.close
	set rs=nothing
	
    '---------------添加到到单统计
	service_type=request.Form("service_type")
	service_type1=request.Form("service_type1")
    sql="select * from renshi_salesIncome where id='"&trim(dingid)&"'"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if not rs.eof or not rs.bof then
		'rs("order_no")=order_no
		rs("personid")=personid
		rs("userid")=userid
		rs("realname")=realname
		rs("sales_date")=payTime
		'rs("com_id")=com_id
		rs("service_type")=service_type
		rs("service_type1")=service_type1
		rs("sales_type")=customType
		rs("sales_price")=payMoney
		rs("sales_email")=newemail
		rs("sales_mobile")=com_mobile
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
		'rs("com_regtime")=com_regtime
		rs("com_hkfs")=com_hkfs
		'rs("com_logincount")=com_logincount
		rs("com_gjd")=com_gjd
		rs("com_servernum")=com_servernum
		rs.update()
	end if
	rs.close
	set rs=nothing

conn.close
set conn=nothing
response.Write("保存成功"&personid)
%>