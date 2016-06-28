<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
sql="update users set closepart="&request("flag")&" where id="&request("id")
conn.execute(sql)
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
%>
<!-- #include file="../../../conn_end.asp" -->