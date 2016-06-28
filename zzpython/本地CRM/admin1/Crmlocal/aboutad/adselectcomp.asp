<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200" valign="top"><table width="300" border="0" cellpadding="4" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#FFFFFF"><a href="?adgonghai=1&zsttype=1">公海再生通</a> | <a href="?adgonghai=1&zsttype=0">公海非再生通</a> | <a href="?adsihai=1">私海</a></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><a href="../crm_allcomp_list.asp?dotype=allall&adid=all&lmcode=2771&adgonghai=<%=request("adgonghai")%>&adsihai=<%=request("adsihai")%>&zsttype=<%=request("zsttype")%>" target="topt">所以客户</a></td>
  </tr>
  <%
  sqlt="select id,meno from temp_adcompselect where flag=1"
  set rst=conn.execute(sqlt)
  if not rst.eof or not rst.bof then
  while not rst.eof
  %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="../crm_allcomp_list.asp?dotype=allall&adid=<%=rst("id")%>&lmcode=2771&adgonghai=<%=request("adgonghai")%>&adsihai=<%=request("adsihai")%>&zsttype=<%=request("zsttype")%>" target="topt"><%=rst("meno")%></a></td>
  </tr>
  <%
  rst.movenext
  wend
  end if
  rst.close
  set rst=nothing
  %>
</table></td>
    <td valign="top"><iframe id=topt name=topt  style="HEIGHT: 600px; WIDTH: 600px;" frameborder="0" src=""></iframe></td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
