<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
sqla="select * from crm_assign where com_id="&request("com_id")
set rsa=conn.execute(sqla)
if not rsa.eof or not rsa.bof then
	response.Write("�ÿͻ��Ѿ��������˵Ŀ��")
	response.End()
end if
rsa.close
set rsa=nothing
sql="select com_id from crm_Assign_Request where com_id="&request("com_id")&" and assignflag=0"
set rs=conn.execute(sql)
if rs.eof or rs.bof then
	sqla="insert into crm_Assign_Request(com_id,personid) values("&request("com_id")&","&request("personid")&")"
	conn.execute(sqla)
	response.Write("����ɹ���")
	response.Redirect("crm_comp_requestsavelocal.asp?com_id="&request("com_id"))
	rs.close
	set rs=nothing
	conn.close
	set conn=nothing
	response.End()
else
	response.Write("�ÿͻ��Ѿ������������룡")
	rs.close
	set rs=nothing
	conn.close
	set conn=nothing
	response.End()
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
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
