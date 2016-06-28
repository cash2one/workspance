<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<%
if request("shez")="1" then
sql="update crm_Email_Template set sendtype=0 where emailtype=1"
conn.execute(sql)
sqlin="update crm_Email_Template set sendtype=1 where id="&request.QueryString("id")
conn.execute(sqlin)
response.Write("<script>alert('成功！该模板已经设置为“当前发送邮件”');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
%>

