<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<!-- #include file="../../cn/function.asp" -->
<%
idprod=request("idprod")
cemail=request("cemail")
sql="select * from crm_Email_Template where id="&request("id")
set rs=conn.execute(sql)
if rs.eof then
	response.Write("错误！")
	response.End()
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
                  <form name="form1" method="post" action="email_template_save.asp"><tr>
                    <td width="5%" align="center" nowrap bgcolor="#FFFFFF">标题</td>
                    <td width="95%" bgcolor="#FFFFFF"><%=Server.HTMLEncode(rs("TemplateName"))%></td>
                  </tr>
                  <tr>
                    <td align="center" bgcolor="#FFFFFF" >内容</td>
                    <td bgcolor="#FFFFFF">
                      <%=rs("TemplateContent")%></td>
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

