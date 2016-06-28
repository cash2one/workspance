<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%

sql="select * from zz91_TechQuestion where id="&request.QueryString("id")
set rs=conn.execute(sql)
if rs.eof then
response.Write("err")
response.End()
end if
if request.Form("add")<>"" then
sql="insert into zz91_TechQuestion (stitle,scontent,adminuser,ssubid,scheck) values('"&request.Form("stitle")&"','"&request.Form("scontent")&"','"&session("personid")&"','"&request("id")&"','"&request("scheck")&"')"
conn.execute(sql)
if request.Form("add")="1" then
sql="update zz91_TechQuestion set scheck='"&request("scheck")&"',doperson='"&session("personid")&"' where id="&request("id")
conn.execute(sql)
end if
response.Redirect("question_show.asp?id="&request("id"))
end if
%><!--#include file="inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="s.css" rel="stylesheet" type="text/css" />
<script>
function InsertHTML(content)
{
	var oEditor = FCKeditorAPI.GetInstance('scontent') ;
	if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
	{
		oEditor.InsertHtml( content ) ;
	}
	else
		alert( 'You must be on WYSIWYG mode!' ) ;
}
</script>
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <td class="btitle"> 问题</td>
        <td align="right" class="btitle"><a href="question_list.asp">返回</a></td>
      </tr>
    </table>
      <br />
      <table width="90%" border="0" align="center" cellpadding="5" cellspacing="0">
      
         <tr>
           <td bgcolor="#F2F2F2">标题： </td>
           <td align="right" bgcolor="#F2F2F2">提问者：<b><%call ssname(rs("adminuser"))%></b></td>
         </tr>
         <tr>
          <td colspan="2"><%=rs("stitle")%></td>
        </tr>
         <tr>
           <td colspan="2" bgcolor="#F2F2F2">内容：</td>
         </tr>
         <tr>
          <td colspan="2"><%=rs("scontent")%></td>
        </tr>
		
        <tr>
          <td colspan="2" bgcolor="#CCCCCC"><%
			
			       Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
				     .FROMTbl = "zz91_TechQuestion "
				     .sqlOrder= "order by sdate asc "
				     .sqlWhere= "WHERE ssubid="&request.QueryString("id")&" "
				     .keyFld  = "id "    '不可缺少
				     .pageSize= 10
				     .getConn = conn
				     Set rsa  = .pageRs
				   End With
			
	               
			
				if not rsa.eof then
				   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=total/10
				   page=request("page")
				   if page="" then
				   page=1
				   end if
				   if totalpg>cint(totalpg) then
				   totalpg=cint(totalpg)+1
				   else
				   totalpg=cint(totalpg)
				   end if
				i=1
				do while not rsa.eof
			%></td>
        </tr>
		<%
		  if rsa("scheck")="0" then
		  	sname="提问："
			bgcol="#D8EBD8"
		  else
		  	sname="解决情况："
			bgcol="#f2f2f2"
		  end if
		  %>
        <tr>
          <td bgcolor="<%=bgcol%>">
		  <%=sname%>
		  <%call resalutq(rsa("Scheck"))%>		  </td>
          <td align="right" bgcolor="<%=bgcol%>">回答者：<%call ssname(rsa("adminuser"))%> 时间：<%=rsa("sdate")%></td>
        </tr>
        <tr>
          <td colspan="2"><%=rsa("Scontent")%></td>
        </tr>
       
		<%
			i=i+1
				rsa.movenext
				loop
				end if
				rsa.close
				set rsa=nothing
				%>
		<tr>
          <td colspan="2">&nbsp;</td>
        </tr>
    </table>
    <br />
	<%if session("userid")="14" or session("userid")="10" then%>
    <table width="600" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
     <form id="form1" name="form1" method="post" action=""> <tr>
        <td bgcolor="#F2F2F2">          技术人员回答</td>
      </tr>
      <tr>
        <td bgcolor="#FFFFFF">是否解决：
          <input type="radio" name="scheck" value="1" />
          已经解决
          
            <input type="radio" name="scheck" value="2" />
            待做中
            <input type="radio" name="scheck" id="scheck" value="4" />
            下周安排
            <input name="scheck" type="radio" value="3" checked="checked" />
            有问题需要咨询          
            <input name="id" type="hidden" id="id" value="<%=request("id")%>" />
            <input name="add" type="hidden" id="add" value="1" /></td>
      </tr>
      <tr>
         <td bgcolor="#FFFFFF"><iframe src="trade_file.asp" name="text" width=100% height=30px scrolling=auto marginwidth=0 marginheight=0 vspale="0" frameborder=no  class="ifriame"> </iframe></td>
       </tr>
      <tr>
        <td bgcolor="#FFFFFF">
		<textarea name="sContent" cols="60" rows="5" id="sContent" style="display:none"><%=scontent%>
	</textarea>
					  <IFRAME ID="eWebEditor1" src="../../../ewebeditor/ewebeditor.htm?id=sContent&style=mini" frameborder="0" scrolling="no" width="100%" height="350"></IFRAME>	</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#FFFFFF"><input type="submit" name="Submit2" value="提交" /></td>
      </tr>
     </form>
    </table>
	<%else%>
	<table width="600" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
     <form id="form2" name="form2" method="post" action=""> <tr>
        <td bgcolor="#F2F2F2">我有疑问：<input name="id" type="hidden" id="id" value="<%=request("id")%>" />
            <input name="add" type="hidden" id="add" value="2" />
            <textarea name="sContent" cols="60" rows="5" id="sContent" style="display:none"><%=scontent%>
	</textarea>
            </td>
      </tr>
     
       <tr>
         <td bgcolor="#FFFFFF"><iframe src="trade_file.asp" name="text" width=100% height=30px scrolling=auto marginwidth=0 marginheight=0 vspale="0" frameborder=no  class="ifriame"> </iframe></td>
       </tr>
       <tr>
        <td bgcolor="#FFFFFF">
         <IFRAME ID="eWebEditor1" src="../../../ewebeditor/ewebeditor.htm?id=sContent&style=mini" frameborder="0" scrolling="no" width="100%" height="350"></IFRAME>
		</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#FFFFFF"><input type="submit" name="Submit2" value="提交" /></td>
      </tr>
     </form>
    </table>
	<%end if%>
    <br /></td>
  </tr>
</table>
</body>
</html>
<%
rs.close
set rs=nothing
%>
<%endConnection()%>
