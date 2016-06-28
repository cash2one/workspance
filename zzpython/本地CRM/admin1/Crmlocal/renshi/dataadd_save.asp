<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
personid=request.Form("personid")
realname=request.Form("realname")
sales_date=request.Form("sales_date")
service_type=request.Form("service_type")
sales_type=request.Form("sales_type")
sales_price=request.Form("sales_price")
sales_email=request.Form("sales_email")
sales_mobile=request.Form("sales_mobile")
sales_bz=request.Form("sales_bz")

arrservice_type=split(service_type,",")
arrsales_type=split(sales_type,",")
arrsales_price=split(sales_price,",")

arrsales_email=split(sales_email,",")
arrsales_mobile=split(sales_mobile,",")
arrsales_bz=split(sales_bz,",")

for i=0 to ubound(arrservice_type)
	sql="select com_id from comp_info where com_email='"&trim(arrsales_email(i))&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		com_id=rs(0)
	else
		com_id=0
	end if
	rs.close
	set rs=nothing
	
	sql="select * from renshi_salesIncome where sales_email='"&trim(arrsales_email(i))&"' and service_type='"&trim(arrservice_type(i))&"'"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if rs.eof or rs.bof then
	rs.addnew
	
	
	rs("personid")=personid
	rs("realname")=realname
	rs("sales_date")=sales_date
	rs("com_id")=com_id
	rs("service_type")=trim(arrservice_type(i))
	rs("sales_type")=trim(arrsales_type(i))
	rs("sales_price")=trim(arrsales_price(i))
	rs("sales_email")=trim(arrsales_email(i))
	rs("sales_mobile")=trim(arrsales_mobile(i))
	rs("sales_bz")=trim(arrsales_bz(i))
	rs.update()
	else
	response.Write(trim(arrsales_email(i))&"已经添加，请不要重复添加！")
	end if
	rs.close
	set rs=nothing
next
response.Write("保存成功")
%>