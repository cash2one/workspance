<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>更多登录信息</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<%
sql=""
page=request("page")
com_id=request.QueryString("com_id")
sear="com_id="&com_id
       Set oPage=New clsPageRs2
	   With oPage
		 .sltFld  = "fdate,LCount"
		 .FROMTbl = "comp_logincount"
		 .sqlOrder= "order by FDate desc"
		 .sqlWhere= "WHERE com_id="&com_id&" "
		 .keyFld  = "com_id"    '不可缺少
		 .pageSize= 10
		 .getConn = conn
		 Set Rs  = .pageRs
	   End With
	   total=oPage.getTotalPage
	   oPage.pageNav "?"&sear,""
	   totalpg=int(total/10)
%>
<table width="500" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#FFFFFF">登录时间</td>
    <td bgcolor="#FFFFFF">次数</td>
  </tr>
  <%
  if not rs.eof or not rs.bof then
  while not rs.eof
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><%=rs("lcount")%></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
