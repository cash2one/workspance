<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<%
fromcs=request("fromcs")
%>
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
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
  <tr>
    <td bgcolor="#FFFFFF">序号</td>
    <td bgcolor="#FFFFFF">手机</td>
    <td bgcolor="#FFFFFF">电话</td>
  </tr>
  <%
  mobile=request.QueryString("mobile")
  sql="select com_id,com_name,com_tel,com_mobile from comp_info where (com_mobile like '%"&right(mobile,9)&"' or com_tel like '%"&right(mobile,7)&"')"
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
  %>
  <tr>
    <td bgcolor="#FFFFFF">
      <%if fromcs="" then%>
      <a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a>
      <%else%>
      <a href="http://admin.zz91.com/admin1/compinfo/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=allcomp" target="_blank"><%=rs("com_name")%></a>
      <%end if%>
    </td>
    <td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  conn.close
  set conn=nothing
  %>
</table>
</body>
</html>
