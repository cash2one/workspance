<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
sql="update cate_qx set ord='"&request("ord")&"' where id="&request("id")
conn.execute(sql)
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
conn.close
set conn=nothing
%>
