<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
</head>

<body>
<%
sql="update Crm_Temp_SalesComp set crmcheck=0,fcheck=1 where com_id="&request.QueryString("com_id")
conn.execute sql,ly
if ly then
	response.Write("�޸ĳɹ���")
else
	response.Write("ʧ�ܣ�")
end if
sql="update comp_provinceID set Fcheck=1 where com_id="&request.QueryString("com_id")
conn.execute(sql)
sql="update comp_salestype set editCheck=1 where com_id="&request.QueryString("com_id")
conn.execute(sql)
%>
<a href="/<%=replace(request.QueryString("frompage"),"~amp~","&")%>">����</a>
</body>
</html>
<%endConnection()%>

