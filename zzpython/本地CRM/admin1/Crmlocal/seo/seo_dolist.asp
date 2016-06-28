<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>SEO小计</title>
<SCRIPT language=javascript src="/cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="/admin1/main.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/province.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/list.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/jquery-1.8.0.min.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="s.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<style>
.input
{
	width:90%
}
.red
{
	color:#F00
}
</style>
</head>

<body>
<%
if request.Form("action")="add" then
	sql="select * from seo_dolist where id is null"
	set rscc=server.CreateObject("ADODB.recordset")
	rscc.open sql,conn,1,3
	if rscc.eof or rscc.bof then
		rscc.addnew()
		rscc("com_id")=request.Form("com_id")
		rscc("sid")=request.Form("sid")
		rscc("detail")=request.Form("detail")
		rscc("personid")=session("personid")
		rscc.update()
		rscc.close
		set rscc=nothing
		response.Redirect("seo_dolist.asp?sid="&request.Form("sid")&"&com_id="&request.Form("com_id")&"")
	end if
end if
com_id=request.QueryString("com_id")
sid=request.QueryString("sid")
sql=""
if sid<>"" then
	sql=sql&" and sid="&sid
end if
if com_id<>"" then
	sql=sql&" and com_id="&com_id&" "
end if
sear=sear&"&com_id="&request("com_id")
Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "seo_dolist"
	 .sqlOrder= "order by gmt_time desc"
	 .sqlWhere= "WHERE  id>0 "&sql
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 15
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=cint(total/15)
   if total/15 > totalpg then
	  totalpg=totalpg+1
   end if
%>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#666666">
  <tr>
    <td width="200" bgcolor="#CCCCCC">时间</td>
    <td bgcolor="#CCCCCC">小计</td>
  </tr>
  <%
  if not rs.eof  then 
  While Not rs.EOF
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("gmt_time")%></td>
    <td bgcolor="#FFFFFF"><%=rs("detail")%></td>
  </tr>
  <%
  rs.movenext
  wend
 END IF
 rs.close
 set rs=nothing
  %>
</table>
<br />
<br /><form id="form1" name="form1" method="post" action="seo_dolist.asp">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td bgcolor="#FFFFFF">
      <textarea name="detail" id="detail" cols="45" rows="5"></textarea>
    </td>
    </tr>
  <tr>
    <td bgcolor="#FFFFFF">
      <input type="submit" name="button" id="button" value="提交" />
      <input name="action" type="hidden" id="action" value="add" />
      <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" />
      <input type="hidden" name="sid" id="sid" value="<%=sid%>" /></td>
    </tr>
</table></form>
</body>
</html>
