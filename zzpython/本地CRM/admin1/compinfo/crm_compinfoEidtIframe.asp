<%
com_id=request("idprod")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>公司信息修改</title>
</head>
<frameset cols="502,502">
  <frame src="crm_cominfo_edit.asp?idprod=<%=com_id%>" />
  <frame src="http://admin.zz91.com/admin1/cominfoedit.asp?com_id=<%=com_id%>" />
</frameset>
<noframes><body>
</body></noframes>
</html>
