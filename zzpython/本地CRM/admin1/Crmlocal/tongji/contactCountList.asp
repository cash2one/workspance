<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
personid=request.QueryString("personid")
fromdate=request.QueryString("fromdate")
todate=request.QueryString("todate")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>联系量列表</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<strong style="color:#F00">注明：多次同一家公司通电话有效联系只统计一次
</strong>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#666666">
  <tr>
    <td width="150" bgcolor="#CCCCCC">时间</td>
    <td width="100" bgcolor="#CCCCCC">有效联系量</td>
    <td bgcolor="#CCCCCC">&nbsp;</td>
  </tr>
  <%
  allcount=0
  for i=1 to 24
  ccfrom=DateAdd("h", i, fromdate)
  ccto=DateAdd("h", i+1, fromdate)
  sqlc="select count(0) from temp_salescomp WHERE not EXISTS(select null from comp_sales where com_type=13 and com_id=temp_salescomp.com_id)  and EXISTS(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" and teldate>='"&ccfrom&"' and teldate<='"&ccto&"' and contacttype =13) and not EXISTS(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" and teldate<'"&ccfrom&"' and teldate>'"&formatdatetime(ccfrom,2)&"' and contacttype =13)"
  set rsc=conn.execute(sqlc)
  ccount=rsc(0)
  allcount=allcount+ccount
  rsc.close
  set rsc=nothing
  %>
  <tr>
    <td width="150" bgcolor="#FFFFFF"><%=ccfrom%></td>
    <td bgcolor="#FFFFFF"><img src="../../images/background.jpg" width="<%=ccount*3%>" height="8" /><a href="/admin1/crmlocal/admin/admin_tel_comp1.asp?lo=1&personid=<%=personid%>&star=&contacttype=13&fromdate=<%=ccfrom%>&todate=<%=ccto%>" target="_blank"><%=ccount%></a></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  
  <%
  next
  %><tr>
    <td bgcolor="#FFFFFF">合计</td>
    <td bgcolor="#FFFFFF"><%=allcount%></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
</table>
<br />
<br />

</body>
</html>
<%
conn.close
set conn=nothing
%>