<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
response.Write("dfsdfsd")
sqlf="select top 1 * from crm_Awoke where personid="&session("personid")&" order by tdate desc"
set rsf=server.createobject("adodb.recordset")
rsf.open sqlf,conn,1,2
if rsf.eof or rsf.bof then
response.End()
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="100">标题</td>
    <td><%=rsf("ttitle")%></td>
  </tr>
  <tr>
    <td>内容</td>
    <td><%=rsf("tcontent")%></td>
  </tr>
</table>
</body>
</html>
<%
rsf.close
set rsf=nothing
conn.close
set conn=nothing
%>
