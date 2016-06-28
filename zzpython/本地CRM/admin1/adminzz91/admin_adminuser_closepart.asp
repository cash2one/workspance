<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<%
sql="update users set closepart="&request("flag")&" where id="&request("id")
conn.execute(sql)
response.Redirect("admin_user_list.asp?userid="&request("userid"))
%>
<!-- #include file="../../conn_end.asp" -->