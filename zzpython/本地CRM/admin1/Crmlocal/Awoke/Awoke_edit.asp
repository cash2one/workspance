<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
sql="select * from crm_awoke where id="&request.QueryString("id")
set rs=conn.execute(sql)
if rs.eof then
response.Write("����Ϣ��")
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="80%" border="0" align="center" cellpadding="5" cellspacing="0">
  <form id="form1" name="form1" method="post" action="Awoke_save.asp">
  <tr>
    <td width="80" align="right" nowrap="nowrap">����: </td>
    <td>
      <input name="Ttitle" type="text"  class=text id="Ttitle"  size="50" value="<%=rs("ttitle")%>"/>
      <input name="id" type="hidden" id="id" value="<%=request("id")%>"/>
      <input name="m" type="hidden" id="m" value="1" /></td>
  </tr>
  <tr>
    <td align="right">���ţ�</td>
    <td><input name="Part" type="radio" id="radio" value="1" <%if rs("part")="1" then response.Write("checked")%> />
      A ��
      <input type="radio" name="Part" id="radio2" value="2" <%if rs("part")="2" then response.Write("checked")%> />
      B ��</td>
  </tr>
  <tr>
    <td align="right">ʱ��: </td>
    <td><script language=javascript>createDatePicker("Tdate",true,"<%=rs("tdate")%>",false,false,false,true)</script></td>
  </tr>
  <tr>
    <td align="right" nowrap="nowrap">Ŀ������:</td>
    <td><textarea name="Tcontent" rows="10" id="Tcontent"><%=rs("tcontent")%></textarea></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><input type="submit" name="Submit" value=" �� �� " class=button /></td>
  </tr>
  </form>
</table>
</body>
</html>
<%
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
