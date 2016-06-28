<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
if request("del")="1" then
sql="delete from crm_personinfo where id="&request("id")
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
          <td bgcolor="#87BAEF">联系人</td>
          <td bgcolor="#87BAEF">性别</td>
          <td bgcolor="#87BAEF">职位</td>
          <td bgcolor="#87BAEF">电话</td>
          <td bgcolor="#87BAEF">传真</td>
          <td bgcolor="#87BAEF">手机</td>
          <td bgcolor="#87BAEF">Email</td>
          <td bgcolor="#87BAEF">地址</td>
          <td bgcolor="#87BAEF">添加时间</td>
          <td bgcolor="#87BAEF">修改时间<br>
          (2010-3-30 15：00开始记录)</td>
          <td bgcolor="#87BAEF">操作</td>
        </tr>
		<%
		sqlp="select * from crm_personinfo where com_id="&request.QueryString("com_id")
		set rsp=server.CreateObject("adodb.recordset")
		rsp.open sqlp,conn,1,1
		if not rsp.eof then
		do while not rsp.eof
		%>
        <tr>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonName"))%></td>
          <td bgcolor="#FFFFFF">
		  <%
		  if rsp("PersonSex")="1" then
		  response.write("先生")
		  elseif rsp("PersonSex")="0" then
		  response.Write("女士")
		  end if 
		  'response.Write(rsp("PersonSex"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonStation"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonTel"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonFax"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonMoblie"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonEmail"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("PersonAddr"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("fdate"))%></td>
          <td bgcolor="#FFFFFF"><%response.Write(rsp("editdate"))%></td>
          <td bgcolor="#FFFFFF"><a href="#kang" onClick="window.open('crm_personinfo_edit.asp?id=<%=rsp("id")%>','_a','width=700,height=300')">编辑</a> | <a href="?del=1&id=<%=rsp("id")%>" onClick="return confirm('确实要删除吗？')">删除</a></td>
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

