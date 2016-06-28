<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<%
idprod=request("idprod")
cemail=request("cemail")
if request("TemplateName")<>"" then
sql="select * from crm_Email_Template"
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,2
rs.addnew()
rs("PersonID")=session("personid")
rs("Etype")=request.Form("Etype")
rs("TemplateName")=request.Form("TemplateName")
rs("TemplateContent")=request.Form("TemplateContent")
rs("emailtype")=1
rs.update()
rs.close
set rs=nothing
response.Redirect("email_template.asp?idprod="&idprod&"&cemail="&cemail)
end if
%>

