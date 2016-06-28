<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
sql="delete from cate_qx where id="&request("id")
conn.execute(sql)
response.Redirect("admin_area.asp?code="&request("code"))
%><!-- #include file="../../../conn_end.asp" -->