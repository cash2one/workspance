<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="adfsfs!@#.asp" -->
<%
sql="select * from company1 where id="&request("id")&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=rs("com_name")%></title>
<link href="../../main.css" rel="stylesheet" type="text/css" />
<link href="../../color.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="800" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#999999">
  <tr>
    <td colspan="4" align="center" bgcolor="#FFFFFF"><input type="button" name="Submit" value=" ����Ŀͻ� " onclick="window.locaton='company_save.asp?id=<%=request("id")%>'" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" name="Submit2" value="  ��  ��  " onclick="window.close()"/></td>
  </tr>
  <tr>
    <td width="62" bgcolor="#EBEBEB">����ͨ</td>
    <td width="315" bgcolor="#FFFFFF"><%
	if rs("cxt")="1" then
	response.Write("<font color=#ff0000>����ͨ</font>")
	else
	response.Write("��ͨ")
	end if
	%></td>
    <td width="36" bgcolor="#ebebeb">&nbsp;</td>
    <td width="342" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">��˾����</td>
    <td bgcolor="#FFFFFF"><%=rs("com_name")%></td>
    <td bgcolor="#ebebeb">�绰</td>
    <td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">�ֻ�</td>
    <td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
    <td bgcolor="#ebebeb">����</td>
    <td bgcolor="#FFFFFF"><%=rs("com_fax")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">��ϵ��</td>
    <td bgcolor="#FFFFFF"><%=rs("com_contactperson")%></td>
    <td bgcolor="#ebebeb">�Ա�</td>
    <td bgcolor="#FFFFFF"><%=rs("com_desi")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">ְ��</td>
    <td bgcolor="#FFFFFF"><%=rs("com_station")%></td>
    <td bgcolor="#ebebeb">ʡ��</td>
    <td bgcolor="#FFFFFF"><%=rs("com_province")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">����</td>
    <td bgcolor="#FFFFFF"><%=rs("com_city")%></td>
    <td bgcolor="#ebebeb">����</td>
    <td bgcolor="#FFFFFF"><%=rs("com_country")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">��ַ</td>
    <td bgcolor="#FFFFFF"><%=rs("com_addr")%></td>
    <td bgcolor="#ebebeb">�ʱ�</td>
    <td bgcolor="#FFFFFF"><%=rs("com_zip")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">��Ӫҵ��</td>
    <td colspan="3" bgcolor="#FFFFFF"><%=rs("com_zyyw")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">��ַ</td>
    <td colspan="3" bgcolor="#FFFFFF"><a href="<%=rs("weburl")%>" target="_blank"><%=rs("weburl")%></a></td>
  </tr>
</table>
<%
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
</body>
</html>
