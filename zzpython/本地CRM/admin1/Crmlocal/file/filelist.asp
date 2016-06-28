<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input type="button" name="button" id="button" value="提交文档" onclick="window.location='fileadd.asp'" /></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <%
	function getusername(id)
		sqlu="select realname from users where id="&id
		set rsu=conn.execute(sqlu)
		if not rsu.eof then
		getusername=rsu(0)
		end if
		rsu.close
		set rsu=nothing
	end function
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "crm_UploadFile"
		.sqlOrder= "order by id desc"
		.sqlWhere= ""
		.keyFld  = "id"    '不可缺少
		.pageSize= 10
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=int(total/10)
  %></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">文件名称</td>
    <td bgcolor="#f2f2f2">提交时间</td>
    <td bgcolor="#f2f2f2">提交人</td>
  </tr>
 <%
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="download.asp?FileName=<%=rs("filepath")%>"><%=rs("filename")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><%=getusername(rs("personid"))%></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
	conn.close
	set conn=nothing
  %>
</table>
</body>
</html>
