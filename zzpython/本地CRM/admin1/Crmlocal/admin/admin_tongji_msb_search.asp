<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
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
<table width="100%" border="0" cellspacing="0" cellpadding="5">
 <form id="form1" name="form1" method="post" action=""> <tr>
    <td align="center">email:
      
        <input type="text" name="com_email" id="com_email" />
      <input type="submit" name="button" id="button" value="搜索" />
      <input type="hidden" name="s" id="s" value="1" /></td>
  </tr></form>
</table>
<%if request("s")<>"" and trim(request("com_email"))<>"" then%>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#999999">
  <tr>
    <td bgcolor="#f2f2f2">公司名称</td>
    <td bgcolor="#f2f2f2">EMAIL</td>
    <td bgcolor="#f2f2f2">流量量</td>
  </tr>
  <%
  sqlt=" exists (select com_id from comp_info where com_email like '%"&request("com_email")&"%' and com_id=shop_viewCount.com_id)"
  sql="select top 10 * from shop_viewCount where "&sqlt
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
  sqlc="select com_name,com_email,com_subname from comp_info where com_id="&rs("com_id")&""
  set rsc=conn.execute(sqlc)
  if not rsc.eof or not rsc.bof then
  	com_name=rsc(0)
	com_email=rsc(1)
	com_subname=rsc(2)
  else
  	com_name=""
	com_email=""
	com_subname=""
  end if
  rsc.close
  set rsc=nothing
  sqlp="select personid from crm_assign where com_id="&rs("com_id")&""
  set rsp=conn.execute(sqlp)
  if not rsp.eof or not rsp.bof then
  	personid=rsp(0)
  else
  	personid="0"
  end if
  rsp.close
  set rsp=nothing
  %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="http://192.168.1.2/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=com_name%></a></td>
    <td bgcolor="#FFFFFF"><%=com_email%></td>
    <td bgcolor="#FFFFFF"><%=rs("viewCount")%></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
</table>
<%end if%>
</body>
</html>
<%conn.close
set conn=nothing
%>
