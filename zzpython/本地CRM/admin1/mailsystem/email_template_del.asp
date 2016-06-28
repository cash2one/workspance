<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<%
idprod=request("idprod")
cemail=request("cemail")
sql="delete from crm_email_template where id in ("&request.Form("cbb")&")"
conn.execute(sql)
response.Redirect("email_template.asp?idprod="&idprod&"&cemail="&cemail)
%>

