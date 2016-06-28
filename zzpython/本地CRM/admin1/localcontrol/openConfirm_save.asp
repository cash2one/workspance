<!-- #include file="../include/adfsfs!@#.asp" -->
<%
com_id=request("com_id")
confirmID=request("confirmID")
payTime=request("payTime")
payCorp=request("payCorp")
payUserName=request("payUserName")
payService=request("payService")
payMoney=request("payMoney")
Moneydetail=request("Moneydetail")
payBank=request("payBank")
saler=request("saler")
payType=request("payType")
payFax=request("payFax")
payReturn=request("payReturn")
IDCard=request("IDCard")
remark=request("remark")
customType=request("customType")
customerFrom=request("customerFrom")
telNum=request("telNum")
personid=request("personid")

salestime=request("salestime")
bigfine=request("bigfine")
salestype=request("salestype")
webcontrol=request("webcontrol")
salesOther=request("salesOther")


sql="select * from crm_openConfirm where com_id="&com_id
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,3
rs.addnew
rs("com_id")=com_id
rs("confirmID")=confirmID
rs("payTime")=payTime
rs("payCorp")=payCorp
rs("payUserName")=payUserName
rs("payService")=payService
rs("payMoney")=payMoney
rs("Moneydetail")=Moneydetail
rs("payBank")=payBank
rs("saler")=saler
rs("payType")=payType
rs("payFax")=payFax
rs("payReturn")=payReturn
rs("IDCard")=IDCard
rs("remark")=remark
rs("customType")=customType
rs("customerFrom")=customerFrom
rs("salerID")=personid

rs("salestime")=salestime
rs("bigfine")=bigfine
rs("salestype")=salestype
rs("webcontrol")=webcontrol
rs("salesOther")=salesOther

rs.update
rs.close
set rs=nothing
	sqlc="select com_id from crm_assign where com_id="&com_id&""
	set rsc=conn.execute(sqlc)
		if rsc.eof and rsc.bof then
			sqli="insert into crm_assign(com_id,Waiter_Open) values("&com_id&",1)"
			conn.execute(sqli)
			response.Write("<script>alert('成功！已放入待开通的再生通')</script>")
		else
			sqldel="update crm_assign set Waiter_Open=1 where com_id="&com_id&""
			conn.Execute(sqldel)
			response.Write("<script>alert('成功！已放入待开通的再生通')</script>")
		end if
    rsc.close
	set rsc=nothing
		response.Write("<script>parent.parent.window.close()</script>")
	    response.End()
conn.close
set conn=nothing
%>

