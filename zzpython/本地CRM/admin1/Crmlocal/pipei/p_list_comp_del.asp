<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
comid=request("comid")
groupid=request("groupid")
sql="delete from crm_compLink where com_id in ("&comid&") and groupid='"&groupid&"'"
conn.execute(sql)
conn.close
set conn=nothing
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")

%>
