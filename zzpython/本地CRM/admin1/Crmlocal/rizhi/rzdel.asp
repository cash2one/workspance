<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
if session("userid")="10" then
	sql="delete from crm_rizhi where id="&request.QueryString("id")&""
else
	sql="delete from crm_rizhi where id="&request.QueryString("id")&" and personid="&session("personid")&""
end if
conn.execute sql,ly
if ly then
	conn.close
	set conn=nothing
	response.Write("<script>alert('ɾ���ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
else
	conn.close
	set conn=nothing
	response.Write("<script>alert('����Ȩɾ����������ӵ���Ϣ��');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
</head>

<body>
</body>
</html>
