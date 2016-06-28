<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%

if request.Form("add")="1" then
	sql="update zz91_TechQuestion set sdoman='"&request.Form("Sdoman")&"',dodate='"&request.Form("dodate")&"' where id="&request.Form("id")
	Conn.execute(sql)
	response.Write("<script>alert('提交成功！');window.close()</script>")
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<title>无标题文档</title>

<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <form id="form1" name="form1" method="post" action=""><tr>
    <td align="right">分配给
      
      <input name="add" type="hidden" id="add" value="1" />
      <input name="id" type="hidden" id="id" value="<%response.Write(request.QueryString("id"))%>"/></td>
    <td><select name="Sdoman" id="sdoman">
      <option value="0">技术部</option>
      <%
			
			sqlu="select id,realname from users where userid=14 and closeflag=1"
			set rsu=server.CreateObject("adodb.recordset")
			rsu.open sqlu,connAdmin,1,1
			if not rsu.eof then
			do while not rsu.eof 
			%>
      <option value="<%=rsu("id")%>"><%=rsu("realname")%></option>
      <%
			rsu.movenext
			loop
			end if
			rsu.close
			set rsu=nothing
			%>
    </select></td>
  </tr>
    <tr>
      <td align="right">要求完成时间</td>
      <td><script language=javascript>createDatePicker("dodate",true,"",false,true,true,true)</script></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><input type="submit" name="Submit" value="提交" /></td>
    </tr>
  </form>   
</table>
</body>
</html>
<%
endConnection()%>
