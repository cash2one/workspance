<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
</head>

<body>
��¼����Ŀͻ�<br />
<%
sql="select * from crm_activeCompCount order by activedate desc"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
	response.Write(formatdatetime(rs("activedate"),2)&"("&rs("compCount")&")<br>")
rs.movenext
wend
end if
rs.close
set rs=nothing
%>
3.8��ʼ����������˽���Ŀͻ�
</body>
</html>
<%
conn.close
set conn=nothing
%>
