<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<br />
<table width="100%" border="0" cellspacing="0" cellpadding="4">
  <tr>
    <td>分配成功！</td>
  </tr>
  <tr>
    <td><input type="button" name="Submit" value="打印刚分配的客户" onclick="window.open('ActiveCrm_comp_printmainNow.asp?AssignTimes=<%=request("T")%>','','')"/></td>
  </tr>
</table>
</body>
</html>
