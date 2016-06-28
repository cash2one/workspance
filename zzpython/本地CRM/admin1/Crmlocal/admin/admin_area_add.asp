<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
parent_id=request("parent_id")
if parent_id="" then parent_id=0
if request("m")="1" then
	sql="select * from cate_qx where id="&request("id")&""
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	rs("meno")=request("meno")
	rs("link")=request("link")
	'rs("ord")=request("ord")
else
	sql="select * from cate_qx "
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	rs.addnew()
	'rs("code")=num
	rs("meno")=request("meno")
	rs("link")=request("link")
	rs("ord")=request("ord")
	rs("parent_id")=parent_id
end if
rs.update()
rs.close
set rs=nothing
'response.End()
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
'response.Redirect("admin_area.asp?parent_id="&request("subid"))
%>
<%
conn.close
set conn=nothing
%>
