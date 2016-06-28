<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
if request("code")<>"" then
	sqlmax="select max(code) from cate_qx where code like '"&left(request("code"),2)&"__' "
	set rsmax=server.createobject("adodb.recordset")
	rsmax.open sqlmax,conn,1,1
	
	if rsmax(0)<>"" then
	num=cint(rsmax(0))+1
	
	end if
else
	sqlmax="select max(code) from cate_qx where code like '__00' "
	set rsmax=server.createobject("adodb.recordset")
	rsmax.open sqlmax,conn,1,1
	
	if rsmax(0)<>"" then
	num=left(rsmax(0),2)+01&"00"
	else
	num="1000"
	end if
	rsmax.close
	set rsmax=nothing
end if
if request("m")="1" then
sql="select * from cate_qx where code='"&request("code")&"'"
set rs=server.createobject("adodb.recordset")
rs.open sql,conn,1,3
rs("meno")=request("meno")
rs("link")=request("link")
if request("ord")<>"" then
rs("ord")=request("ord")
end if
else
sql="select * from cate_qx "
set rs=server.createobject("adodb.recordset")
rs.open sql,conn,1,3
rs.addnew()
rs("code")=num
rs("meno")=request("meno")
rs("link")=request("link")
rs("ord")=request("ord")
end if
rs.update()
rs.close
set rs=nothing
response.Redirect("admin_area.asp?code="&request("code"))
%><!-- #include file="../../../conn_end.asp" -->