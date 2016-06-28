<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<!-- #include file="../../cn/function.asp" -->
<%
idprod=request("idprod")
cemail=request("cemail")
if request.Form("TemplateName")<>"" then
sql="select * from crm_Email_Template"
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,2
rs.addnew()
rs("PersonID")=session("personid")
rs("TemplateName")=request.Form("TemplateName")
rs("TemplateContent")=request.Form("TemplateContent")
rs("emailtype")=1
rs.update()
rs.close
set rs=nothing
response.Redirect("email_template.asp?idprod="&idprod&"&cemail="&cemail)
end if
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>发送邮件</title>
<link href="../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<link href="../inc/Style.css" rel="stylesheet" type="text/css">
<script LANGUAGE=javascript>
<!--

function showSending() {
	sub(1);
}



function sub(){
	html_text=document.f1.content.value
    richedit.setHTML(html_text)
	//dec_RichEdit_Html();
}
function submi()
{
document.f1.content.value = richedit.getHTML();
}
//-->
</script>
<style type="text/css">
<!--
body {
	background-color: #006699;
}
-->
</style></head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      
      <tr>
        <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top"><br>
                <table width="90%" style="margin-top:10px;" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
                  <form name="form1" method="post" action="email_template_add_save.asp"><tr>
                    <td width="5%" align="center" nowrap bgcolor="#FFFFFF">标题</td>
                    <td width="95%" bgcolor="#FFFFFF">
                      <input name="TemplateName" type="text" class="text" id="TemplateName" size="50">
                      <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>">
                      <input name="cemail" type="hidden" id="cemail" value="<%=cemail%>"></td>
                  </tr>
                    <tr>
                      <td align="center" bgcolor="#FFFFFF" >类别</td>
                      <td bgcolor="#FFFFFF">
					 <select name="Etype" id="Etype">
					    <option value="0" selected>请选择</option>
                        <%
						sql1="select * from cate_mailType where code like '__'"
						set rs1=conn.execute(sql1)
						if not rs1.eof or not rs1.bof then
						while not rs1.eof
						%>
                        <option value="<%=rs1("code")%>" ><%=rs1("meno")%></option>
						<%
						sql2="select * from cate_mailType where code like '"&rs1("code")&"__'"
						set rs2=conn.execute(sql2)
						if not rs2.eof or not rs2.bof then
						while not rs2.eof
						%>
                        <option value="<%=rs2("code")%>" >┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
                        <%
						rs2.movenext
						wend
						end if
						rs2.close
						set rs2=nothing
						%>
					    
                        <%
						rs1.movenext
						wend
						end if
						rs1.close
						set rs1=nothing
						%>
					    <option value="2">客户询盘邮件</option>
                      </select> 
                      </td>
                    </tr>
                    <tr>
                    <td align="center" bgcolor="#FFFFFF" >内容</td>
                    <td bgcolor="#FFFFFF">
					<textarea name="TemplateContent" cols="60" rows="5" id="TemplateContent" style="display:none">
	<%=request("tcontent")&"<br>"&request("bottombanq")%>
	</textarea>
	<IFRAME ID="eWebEditor1" src="../../ewebeditor/ewebeditor.htm?id=TemplateContent&style=coolblue" frameborder="0" scrolling="no" width="100%" height="350"></IFRAME>
</td>
                  </tr>
                  <tr>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="保存"></td>
                  </tr> </form>
                </table>
                <br>
                </td>
            </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html><!-- #include file="../../conn_end.asp" -->

