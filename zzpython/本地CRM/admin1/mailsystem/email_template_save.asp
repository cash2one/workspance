<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<%
idprod=request("idprod")
cemail=request("cemail")
if request("TemplateName")<>"" then
sql="select * from crm_Email_Template where id="&request.Form("id")
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,2
'rs.addnew()
'rs("PersonID")=session("personid")
rs("TemplateName")=request.Form("TemplateName")
rs("TemplateContent")=request.Form("TemplateContent")
rs("Etype")=request.Form("Etype")
rs("TempSQL")=request.Form("TempSQL")
rs.update()
rs.close
set rs=nothing
response.Write("±£´æ³É¹¦£¡")
end if
endconnection()
%>

