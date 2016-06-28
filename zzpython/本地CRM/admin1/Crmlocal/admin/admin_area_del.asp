<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
sql="delete from cate_qx where id="&request("id")
conn.execute(sql)
conn.close
set conn=nothing
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
%>
