<!-- #include file="../../include/adfsfs!@#.asp" -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
</head>

<body>
<textarea name="allcom_id" cols="100%" rows="5" id="allcom_id">
<%
'sqltemp="select LastEmailComID from crm_email_template where sendtype=1 and emailtype=1"
'set rst=conn.execute(sqltemp)
'if not rst.eof then
'lastcomid=rst(0)
'end if
'rst.close
'set rst=nothing
'sql1="and com_id in (select com_id from comp_logincount)"
'sqllogin="and EXISTS(select com_id from comp_logincount where com_id=comp_info.com_id and fdate>'"&date()-120&"') "
sql="select com_id from crm_active_email order by com_id asc"
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,1
if not rs.eof then
do while not rs.eof 
response.Write(rs("com_id")&"|")
rs.movenext
loop
end if
rs.close
set rs=nothing

%>
</textarea>
<%
response.Write("<script>parent.sendemail();parent.setTimeout('showTime1()', 8000);</script>")
conn.close
set conn=nothing
%>
</body>
</html>
