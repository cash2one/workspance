<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
set rs=server.createobject("adodb.recordset")
sql="select * from user_qx where uname='"&request("uname")&"'"
rs.open sql,conn,1,3
if not rs.eof then
	rs("myqx")=request("qx")
else
	rs.addnew()
	rs("uname")=request("uname")
	rs("myqx")=request("qx")
end if 
rs.update()
rs.close()
set rs=nothing
response.Redirect("admin_user_list.asp?uname="&request("uname")&"")
conn.close
set conn=nothing
%>

