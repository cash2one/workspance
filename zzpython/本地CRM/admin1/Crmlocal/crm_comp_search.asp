<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
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
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<%%>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
<%
sqlm=""
if request("com_name")<>"" then
	sqlm=sqlm&" and com_name like '%"&request("com_name")&"%'"
end if
if request("com_mobile")<>"" then
	sqlm=sqlm&" and com_mobile like '%"&request("com_mobile")&"%'"
end if
if request("com_email")<>"" then
	sqlm=sqlm&" and com_email like '%"&request("com_email")&"%'"
end if
if request("com_tel")<>"" then
	sqlm=sqlm&" and com_tel like '%"&request("com_tel")&"%'"
end if
sql="select top 10 com_id,com_name,com_mobile,com_tel,com_email,com_regtime from comp_info where not EXISTS(select null from test where com_id=comp_info.com_id) "&sqlm&" order by com_regtime desc"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
%>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("com_name")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_regtime")%></td>
    <td width="50" bgcolor="#FFFFFF"><input type="button" name="button" id="button" value="申请分配" onclick="window.open('http://192.168.1.2/admin1/crmlocal/crm_comp_request.asp?personid=<%=request("personid")%>&com_id=<%=rs("com_id")%>','_blank','width=400,height=300')" /></td>
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
