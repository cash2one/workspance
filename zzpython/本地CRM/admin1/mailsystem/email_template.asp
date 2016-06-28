<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/pagefunction.asp"-->
<%
idprod=request("idprod")
cemail=request("cemail")

'response.Write(session("personid"))
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
function CheckAll(form)
  {
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       e.checked = form.cball.checked;
    }
  }
function deltemp(frm)
{
if (confirm("确实要删除吗？"))
{
frm.action="email_template_del.asp"
frm.submit() 
}
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
    <td height="100%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="200" valign="top" bgcolor="#66CCFF"><table width="100%" border="0" cellspacing="0" cellpadding="4">
          <tr>
            <td>
            <%
						sql1="select * from cate_mailType where code like '__'"
						set rs1=conn.execute(sql1)
						if not rs1.eof or not rs1.bof then
						while not rs1.eof
						%>
                        <a href="?etype=<%=rs1("code")%>"><%=rs1("meno")%></a><br>
						<%
						sql2="select * from cate_mailType where code like '"&rs1("code")&"__'"
						set rs2=conn.execute(sql2)
						if not rs2.eof or not rs2.bof then
						while not rs2.eof
						%>
                        <a href="?etype=<%=rs2("code")%>">┆&nbsp;&nbsp;┿<%=rs2("meno")%></a><br>
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
					    <a href="?etype=1">客户询盘邮件</a>
                      
        
            </td>
          </tr>
        </table>
                        </td>
        <td>
        <table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      
      <tr>
        <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top"><table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td>
					<button class="add" onClick="window.location='email_template_add.asp?idprod=<%response.Write(idprod)%>&cemail=<%response.Write(cemail)%>'" title="新建模板">新建</button>
					</td>
                  </tr>
                </table>
                <br>
				   <%
		           sear=sear&"idprod="&request("idprod")&"&cemail="&request("cemail")
				   if request("etype")<>"" then
				   	sqlt=" and etype="&request("etype")&""
					sear=sear&"&etype="&request("etype")
				   end if
		           Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
				     .FROMTbl = "crm_Email_Template"
				     .sqlOrder= "order by fdate desc"
				     .sqlWhere= "WHERE  not exists(select null from test where id=crm_Email_Template.id)"&sqlt
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 10
				     .getConn = conn
				     Set rs  = .pageRsid
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=int(total/10)
		    %>
                <table width="100%" class=ee id=ListTable border="0" cellspacing="0" cellpadding="0">
                  <form name="form1" method="post" action="">
				  <tr class="topline">
                    <td>&nbsp;</td>
                    <td>模板名称</td>
                    <td>创建时间</td>
                    <td>拥有者</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
				<%
			      
				   if not rs.eof then
				   do while not rs.eof 
			 %>
                  <tr>
                    <td>
                      <input name="cbb" type="checkbox" id="cbb" value="<%response.Write(rs("id"))%>">                    </td>
                    <td><%response.Write(rs("TemplateName"))%></td>
                    <td><%response.Write(rs("fdate"))%></td>
                    <td> <%
				 call selet_("name","users","id",rs("personid"))
				  %></td>
                    <td nowrap><a href="email_template_shez.asp?shez=1&id=<%response.Write(rs("id"))%>" class="<%if rs("sendtype")="1" then response.Write("dpcellselect") else response.Write("button") end if%>" style=" padding:5px"><font color="#FFFFFF">设置为发送邮件</font></a></td>
                    <td nowrap>
					 <a href="email_template_edit.asp?id=<%response.Write(rs("id"))%>&idprod=<%response.Write(idprod)%>&cemail=<%response.Write(cemail)%>" target="_blank">修改</a>
					</td>
                  </tr>
                 
				  <%
				   rs.movenext
				   loop
				   end if
				   rs.close
				   set rs=nothing
				   %>
				    <tr>
                    <td colspan="6">全选
                      <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
					  <%
					  if session("userid")="10" then
					  %>
                      <input type="button" class="button" name="Submit" value="删除" onClick="deltemp(this.form)">
                      <%end if%>
					                      <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>">
                      <input name="cemail" type="hidden" id="cemail" value="<%=cemail%>"></td>
                    </tr>
				  </form>
                </table></td>
            </tr>
          </table></td>
      </tr>
    </table>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html><!-- #include file="../../conn_end.asp" -->

