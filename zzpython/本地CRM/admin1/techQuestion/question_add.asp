<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
if request.Form("add")<>"" then
	sql="select * from zz91_TechQuestion"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	rs.addnew
	rs("stitle")=request.Form("stitle")
	rs("scontent")=request.Form("scontent")
	rs("adminuser")=session("personid")
	rs("sdoman")=request.form("sdoman")
	rs("shidden")=request.form("shidden")
	rs.update()
	response.Redirect("question_list.asp")
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="s.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE2 {color: #666666}
-->
</style>
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
        <td class="btitle"> 我要提交问题</td>
        <td align="right" class="btitle"><a href="question_list.asp">返回</a></td>
      </tr>
    </table>
      <table width="700" border="0" align="center" cellpadding="5" cellspacing="0">
       <form id="form1" name="form1" method="post" action=""> 
         <tr>
           <td>标题： </td>
         </tr>
         <tr>
          <td>
            <input name="stitle" type="text" id="stitle" size="70" />
            <input name="add" type="hidden" id="add" value="1" /></td>
        </tr>
         <tr>
           <td>反馈给：
             <select name="Sdoman" id="sdoman">
			 <option value="0">技术部</option>
               <%
			
			sqlu="select id,realname from users where userid=14 and closeflag=1"
			set rsu=server.CreateObject("adodb.recordset")
			rsu.open sqlu,conn,1,1
			if not rsu.eof then
			do while not rsu.eof 
			%>
               <option value="<%=rsu("id")%>"><%=rsu("realname")%></option>
               <%
			rsu.movenext
			loop
			end if
			rsu.close
			set rsu=nothing
			%>
             </select>
             <span class="STYLE2">如果有具体负责人请指定</span></td>
         </tr>
         <tr>
           <td>显示状态：
            <input type="radio" name="shidden" value="1" />
            隐藏 
            <input name="shidden" type="radio" value="0" checked="checked" />
            显示</td>
         </tr>
         <tr>
           <td>附件：<iframe src="trade_file.asp" name="text" width=100% height=30px scrolling=auto marginwidth=0 marginheight=0 vspale="0" frameborder=no  class="ifriame"> </iframe></td>
         </tr>
         <tr>
          <td>
		  <textarea name="sContent" cols="60" rows="5" id="sContent" style="display:none"><%=scontent%>
	</textarea>
					  <IFRAME ID="eWebEditor1" src="../../../ewebeditor/ewebeditor.htm?id=sContent&style=mini" frameborder="0" scrolling="no" width="100%" height="350"></IFRAME>	  </td>
        </tr>
        <tr>
          <td><input type="submit" name="Submit" value="提交" style="font-size:14px; font-weight:bold"></td>
        </tr>
       </form>
    </table></td>
  </tr>
</table>
</body>
</html>
<%
endConnection()%>
