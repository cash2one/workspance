<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
fromdate=request("from_date")
todate=request("to_date")
if fromdate="" then
 fromdate=date
else
	fromdate=cdate(fromdate)
end if
if todate="" then
 todate=date
else
	todate=cdate(todate)
end if

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><form id="form1" name="form1" method="post" action=""><script language=javascript>createDatePicker("from_date",true,"<%=fromdate%>",false,true,true,true)</script>
    <script language=javascript>createDatePicker("to_date",true,"<%=todate%>",false,true,true,true)</script>
    
      <input type="submit" name="button" id="button" value="提交" />
    </form></td>
  </tr>
</table>
<br />
<table width="500" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#CCCCCC">日期</td>
    <td bgcolor="#CCCCCC">注册数</td>
    <td bgcolor="#CCCCCC">当天私海数</td>
    <td bgcolor="#CCCCCC">目前私海</td>
  </tr>
  <%
  for i=fromdate to todate
  sql="select count(0) from temp_salescomp where com_regtime>='"&i&"' and com_regtime<='"&i+1&"'"
  'response.Write(sql)
  set rs=conn.execute(sql)
  regcout=rs(0)
  sql="select count(0) from temp_salescomp where com_regtime>='"&i&"' and com_regtime<='"&i+1&"' and exists(select com_id from crm_assign where com_id=temp_salescomp.com_id)"
  set rs=conn.execute(sql)
  inshregcount=rs(0)
  rs.close
  set rs=nothing
  sql="select qcount from regInassign where sdate='"&i+1&"'"
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  	qcount=rs(0)
  else
	qcount=0
  end if
  
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=i%></td>
    <td bgcolor="#FFFFFF"><%=regcout%></td>
    <td bgcolor="#FFFFFF"><%=qcount%></td>
    <td bgcolor="#FFFFFF"><%=inshregcount%></td>
  </tr>
  <%
  next
  %>
</table>
</body>
</html>
