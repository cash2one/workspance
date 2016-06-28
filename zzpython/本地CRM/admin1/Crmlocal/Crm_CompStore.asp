<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
if request("del")="1" then
sql="delete from Crm_CompStore where id="&request("id")
conn.execute(sql)
response.Write("<script>alert ('删除成功！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
response.end
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.searchtable {
	border: 2px solid #243F74;
	background-color: #F5FFD7;
}
.crmcheckmod {
	background-color: #FFFFCC;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#996600">
<tr>
          <td bgcolor="#9CDA87">产品类别</td>
        <td bgcolor="#9CDA87">产品描述</td>
        <td bgcolor="#9CDA87">价格</td>
        <td bgcolor="#9CDA87">数量</td>
	    <td bgcolor="#9CDA87">操作</td>
    </tr>
		<%
		sqlp="select * from Crm_CompStore where com_id="&request.QueryString("com_id")&" order by id desc"
		set rsp=server.CreateObject("adodb.recordset")
		rsp.open sqlp,conn,1,1
		if not rsp.eof then
			do while not rsp.eof
				storeCate=rsp("storeCate")
				storeDesc=rsp("storeDesc")
				storePrice=rsp("storePrice")
				storeCount=rsp("storeCount")
		%>
        <tr>
        
          <td bgcolor="#FFFFFF">
		  <%=storeCate%></td>
          <td bgcolor="#FFFFFF"><%=storeDesc%></td>
          <td bgcolor="#FFFFFF"><%=storePrice%>元/吨</td>
          <td bgcolor="#FFFFFF"><%=storeCount%></td>
          
          <td bgcolor="#FFFFFF"><!--<a href="#kang" onClick="window.open('crm_compStore_edit.asp?id=<%=rsp("id")%>','_a','width=700,height=300')">编辑</a> | --><a href="?del=1&id=<%=rsp("id")%>" onClick="return confirm('确实要删除吗？')">删除</a></td>
        </tr>
		<%
		rsp.movenext
		loop
		end if
		rsp.close
		set rsp=nothing
		conn.close
		set conn=nothing
		%>
      </table>
</body>
</html>

