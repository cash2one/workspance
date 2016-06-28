<%
Response.Buffer =false
response.cachecontrol="private"
Response.Expires =0
On Error Resume Next
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>录音数据更新</title>
</head>

<body>
<iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 100px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="http://192.168.2.2/record/index1.asp?"></iframe>
<iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 100px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="http://192.168.2.2/admin1/Crmlocal/recordService/re.asp?"></iframe>
</body>
</html>
