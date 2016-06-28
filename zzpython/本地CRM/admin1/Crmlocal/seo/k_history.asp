<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>历史记录</title><link href="s.css" rel="stylesheet" type="text/css" />

</head>

<body>
<%
sear="n=1"
kid=request.QueryString("kid")
sid=getsid(kid)
if kid="" then
	response.Write("err")
	response.End()
end if
sear=sear&"&kid="&kid&"&sid="&sid&""
sql=" and kid="&kid&""
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "seo_keywords_history"
	 .sqlOrder= "order by gmt_created desc"
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
<table width="300" border="0" cellspacing="1" cellpadding="5" bgcolor="#666666">
  <tr>
    <td bgcolor="#FFFFFF">日期</td>
    <td bgcolor="#FFFFFF">百度排名</td>
    <td bgcolor="#FFFFFF">更新日期</td>
  </tr>
  <%
  if not rs.eof  then 
  While Not rs.EOF
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("kdate")%></td>
    <td bgcolor="#FFFFFF"><%=rs("baidu_ranking")%></td>
    <td bgcolor="#FFFFFF"><%=rs("gmt_created")%></td>
  </tr>
  <%
  rs.movenext
  wend
 END IF
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
