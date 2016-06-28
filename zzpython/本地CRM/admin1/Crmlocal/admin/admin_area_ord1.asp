<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
sql="update cate_qx set ord='"&request("ord")&"' where id="&request("id")
conn.execute(sql)
response.Redirect("admin_area.asp?code="&request("code"))
%>
<!-- #include file="../../../conn_end.asp" -->