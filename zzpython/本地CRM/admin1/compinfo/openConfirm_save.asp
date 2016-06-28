<!-- #include file="../include/adfsfs!@#.asp" -->
<%
com_id=request.Form("com_id")
confirmID=request.Form("confirmID")
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

salestime=request.Form("salestime")
bigfine=request.Form("bigfine")
salestype=request.Form("salestype")
webcontrol=request.Form("webcontrol")
salesOther=request.Form("salesOther")

sqlm="select com_mobile from comp_info where com_id="&com_id
set rsm=conn.execute(sqlm)
if not rsm.eof or not rsm.bof  then
com_mobile=rsm(0)
end if
rsm.close
set rsm=nothing

sqlu="select realname from users where id="&personid
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
realname=rsu(0)
end if
rsu.close
set rsu=nothing

sql="select * from crm_openConfirm where com_id="&com_id
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,3

rs.addnew
rs("com_id")=com_id
rs("payTime")=payTime
rs("payUserName")=payUserName
rs("payMoney")=payMoney
rs("saler")=saler
rs("newemail")=newemail
rs("remark")=remark
rs("customType")=customType
rs("salerID")=personid

rs.update
rs.close
set rs=nothing
    '---------------添加到到单统计
	if customType="0" then sales_type="1401"
	if customType="1" then sales_type="1402"
	if customType="3" then sales_type="1403"
	service_type=request.Form("service_type")
	
	
    sql="select * from renshi_salesIncome where sales_email='"&trim(payUserName)&"' and service_type='"&trim(customType)&"'"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if rs.eof or rs.bof then
		rs.addnew
		rs("personid")=personid
		rs("realname")=realname
		rs("sales_date")=payTime
		rs("com_id")=com_id
		rs("service_type")=service_type
		rs("sales_type")=sales_type
		rs("sales_price")=payMoney
		rs("sales_email")=payUserName
		rs("sales_mobile")=com_mobile
		rs("sales_bz")=remark
		rs.update()
	end if
	rs.close
	set rs=nothing

	'sqlc="select com_id from crm_assign where com_id="&com_id&""
'	set rsc=conn.execute(sqlc)
'	if rsc.eof and rsc.bof then
'		sqli="insert into crm_assign(personid,com_id,Waiter_Open) values("&personid&","&com_id&",1)"
'		conn.execute(sqli)
'	else
'		sqldel="update crm_assign set Waiter_Open=1 where com_id="&com_id&""
'		conn.Execute(sqldel)
'	end if
'    rsc.close
'	set rsc=nothing
response.Redirect("openconfirm_saveSuc.asp")
response.Write("<script>parent.parent.window.close()</script>")
conn.close
set conn=nothing
%>

