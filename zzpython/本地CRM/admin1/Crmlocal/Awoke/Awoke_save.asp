<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
if request.Form("m")="" then
sql="select * from crm_awoke"
else
sql="select * from crm_awoke where id="&request.Form("id")
end if
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,2
if request.Form("m")="" then
rs.addnew()
end if
rs("Ttitle")=request.Form("Ttitle")
rs("Tdate")=request.Form("Tdate")
rs("Tcontent")=request.Form("Tcontent")
rs("PersonID")=session("personid")
rs("part")=request.Form("part")
rs.update()
rs.close
set rs=nothing

response.Write("<script>alert('保存成功！');window.location='Awoke_list.asp'</script>")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
</body>
</html>
