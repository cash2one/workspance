<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
applyGroup=request.QueryString("apply_group")
com_id=request.QueryString("com_id")
service_type2=request.QueryString("service_type2")
arrapplyGroup=split(applyGroup,"|")
arrservice_type2=split(service_type2,",")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����ɹ���</title>
</head>

<body>
����ɹ��� 
<%for i=0 to ubound(arrservice_type2)%>
<a href="http://admin1949.zz91.com/web/zz91/crm/open/detail.htm?applyGroup=<%=arrapplyGroup(i)%>&companyId=<%=com_id%>" target="_blank">��<%=arrservice_type2(i)%>����ͨ��</a>
<%next%>
</body>
</html>
