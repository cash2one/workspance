<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
if request.form("user")<>"" then
arruser=split(request.form("user"),",",-1,1)
for i=0 to ubound(arruser)
sql="insert into crm_other_zhiban(personid,fdate) values("&trim(arruser(i))&",'"&request.form("fdate")&"')"
conn.execute(sql)
next
response.Redirect("other_zhiban.asp")
end if
if request("del")="1" then
sql="delete from crm_other_zhiban where id="&request("id")
conn.execute(sql)
response.Redirect("other_zhiban.asp")
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 50px;
	margin-top: 0px;
	margin-right: 50px;
	margin-bottom: 5px;
}
-->
</style>
<title>无标题文档</title>
</head>

<body>
<br>
<br>
<table width="100%"  border="0" cellpadding="5" cellspacing="1" bgcolor="#000000">
 <form name="form1" method="post" action=""> 
 <%
sqlu="select code,meno from cate_adminuser where code like '13__'"
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
while not rsu.eof
%>
<tr bgcolor="#FFFFFF">
<td align="right" nowrap>值班人员：
	</td>
    <td><%
	sql="select id,realname,assignzhiban1 from users where userid='"&rsu("code")&"' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	if not rs.eof then
	do while not rs.eof
	
	%>
      <input name="user" type="checkbox" id="user" value="<%response.Write(rs("id"))%>">
      <%response.Write(rs("realname"))%>
      <%
	rs.movenext
	loop
	end if
	rs.close
	set rs=nothing
	%></td>
 </tr>
 <%
rsu.movenext
wend
end if
rsu.close
set rsu=nothing
%>
   <tr bgcolor="#FFFFFF">
     <td align="right" nowrap>值班时间：</td>
     <td><script language=javascript>createDatePicker("Fdate",true,"<%=now%>",false,true,true,true)</script></td>
   </tr>
   <tr bgcolor="#CCCCCC">
    <td colspan="2" align="center">
      <input type="submit" name="Submit" value="确定提交">
    </td>
  </tr></form>
</table><br>
<table width="100%"  border="0" cellpadding="5" cellspacing="1" bgcolor="#000000">
  <tr bgcolor="#CCCCCC">
    <td>值班人员</td>
    <td>时间</td>
    <td>操作</td>
  </tr>
  <%
  sql="select * from crm_other_zhiban  order by fdate desc"
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof 
    sqlu="select realname from users where id="&rs("personid")&""
	set rsu=conn.execute(sqlu)
	if not rsu.eof then
	realname=rsu(0)
	end if
	rsu.close
	set rsu=nothing
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=realname%></td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><a href="?del=1&id=<%=rs("id")%>" onClick="return confirm('删除这些信息?')">删除</a></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  conn.close
  set conn=nothing
  %>
</table>

</body>
</html>
