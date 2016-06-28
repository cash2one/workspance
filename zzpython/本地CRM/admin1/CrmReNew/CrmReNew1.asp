<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../jumptolog.asp" -->
<!--#include file="../include/pagefunction.asp"-->
<%
page=request("page")
'-----------------------------------------------------------------------------------------------
On Error Resume Next
DIM startime,endtime
startime=timer()
'-----------------------------------------------------------------------------------------------
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
table {  font-size: 12px}
a {  font-size: 12px; color: #000000; text-decoration: none}
-->
</style>
</head>

<body>
<%
                   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
					 .FROMTbl = "comp_info"
				     .sqlOrder= "order by com_email desc"
				     .sqlWhere= "where 1=1"
				     .keyFld  = "com_id"    '不可缺少
				     .pageSize= 20
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=cint(total/20)
				   if total/20 > totalpg then
				   totalpg=totalpg+1
				   end if


%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <%

If  total<1 Then%>
<tr bgcolor=""> 
    <td >暂无记录</td>    
  </tr>
<%
Else     
    Do While Not rs.EOF %>
  <tr bgcolor="#FFFFFF"> 
    <td colspan="4"><%=rs(4)%></td>
  </tr><%
    rs.movenext
	loop  
End If

%>
</table>
<table width="760" border="0" cellspacing="2" cellpadding="2" align="center">
  <tr> 
    <td>
<!-- #include file="../include/page.asp" -->
</td>
  </tr>  
</table> 
<table width="760" border="0" align="center" cellpadding="2" cellspacing="2">
  <tr> 
    <td align="center"> 
      <%endtime=timer()%>
      本页面执行时间：<%=FormatNumber((endtime-startime)*1000,3)%>毫秒</td>
  </tr>
</table>
</body>
</html>
