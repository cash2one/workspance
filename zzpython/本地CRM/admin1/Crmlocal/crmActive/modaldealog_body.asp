<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
'frompagequrstr=replace(frompagequrstr,"&","~amp~")
'response.Write(frompagequrstr)
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ͻ���Ϣ</title>
<style type="text/css">
<!--
body {
	background-color: #666666;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body scroll="no">
<iframe scrolling="yes" id="mianchengdel" name="mianchengdel" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1" frameborder="0" src="<%=frompagequrstr%>"></iframe>
</body>
</html>
