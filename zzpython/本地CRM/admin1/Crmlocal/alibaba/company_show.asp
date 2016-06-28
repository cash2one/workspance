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
    <td colspan="4" align="center" bgcolor="#FFFFFF"><input type="button" name="Submit" value=" 放入的客户 " onclick="window.locaton='company_save.asp?id=<%=request("id")%>'" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" name="Submit2" value="  关  闭  " onclick="window.close()"/></td>
  </tr>
  <tr>
    <td width="62" bgcolor="#EBEBEB">诚信通</td>
    <td width="315" bgcolor="#FFFFFF"><%
	if rs("cxt")="1" then
	response.Write("<font color=#ff0000>诚信通</font>")
	else
	response.Write("普通")
	end if
	%></td>
    <td width="36" bgcolor="#ebebeb">&nbsp;</td>
    <td width="342" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">公司名称</td>
    <td bgcolor="#FFFFFF"><%=rs("com_name")%></td>
    <td bgcolor="#ebebeb">电话</td>
    <td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">手机</td>
    <td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
    <td bgcolor="#ebebeb">传真</td>
    <td bgcolor="#FFFFFF"><%=rs("com_fax")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">联系人</td>
    <td bgcolor="#FFFFFF"><%=rs("com_contactperson")%></td>
    <td bgcolor="#ebebeb">性别</td>
    <td bgcolor="#FFFFFF"><%=rs("com_desi")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">职务</td>
    <td bgcolor="#FFFFFF"><%=rs("com_station")%></td>
    <td bgcolor="#ebebeb">省份</td>
    <td bgcolor="#FFFFFF"><%=rs("com_province")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">城市</td>
    <td bgcolor="#FFFFFF"><%=rs("com_city")%></td>
    <td bgcolor="#ebebeb">国家</td>
    <td bgcolor="#FFFFFF"><%=rs("com_country")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">地址</td>
    <td bgcolor="#FFFFFF"><%=rs("com_addr")%></td>
    <td bgcolor="#ebebeb">邮编</td>
    <td bgcolor="#FFFFFF"><%=rs("com_zip")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">主营业务</td>
    <td colspan="3" bgcolor="#FFFFFF"><%=rs("com_zyyw")%></td>
  </tr>
  <tr>
    <td bgcolor="#EBEBEB">网址</td>
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
