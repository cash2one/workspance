<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<% 
if request("cmd")="del" then
	conn.execute("delete from crm_weeks where id="&request("id"))
end if
 %>
<html>
<head>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE8 {color: #000; font-weight: bold; }
-->
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      

      <tr>
        <td align="center" valign="top" bgcolor="#FFFFFF">
		<% 

page=Request("page")
if isnumeric(page) then
else
	page=1
end if
if page="" then 
	page=1 
end if
sql="and isDraft="&isDraft&" "
Set oPage=New clsPageRs2
With oPage
	.sltFld  = "*"
	.FROMTbl = "crm_weeks"
	.sqlOrder= "order by cyear desc,cMonth desc,cWeek desc"
	.sqlWhere= "WHERE 1=1"
	.keyFld  = "id"    '不可缺少
	.pageSize= 10
	.getConn = conn
	Set Rs  = .pageRs
End With
total=oPage.getTotalPage
'oPage.pageNav "?"&sear,""
totalpg=cint(total/10)
if total/10 > totalpg then
totalpg=totalpg+1
end if
 %>
<br>
<table width="95%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
  <tr>
    <td height="25" bgcolor="#EEEEEE">周数</td>
    <td bgcolor="#EEEEEE">时间范围</td>
    <td bgcolor="#EEEEEE">添加周目标</td>
    <td bgcolor="#EEEEEE">编辑</td>
  </tr>
<% 
j=1
while not rs.eof 
if j mod 2 = 1 then
	trColor="#ffffff"
else
	trColor="#efefef"
end if%>
  <tr bgcolor="<%= trColor %>">
    <td width="43%"><%= rs("cYear") %>年<%= rs("cMonth") %>月：第<%= rs("cWeek") %>周</td>
    <td width="25%"><%= rs("from_date") %> 至 <%= rs("to_date") %>	</td>
    <td width="18%"><input name="button" type="submit" class="button" id="button" value="添加周目标" onClick="location.href='addWeekTask.asp?wID=<%= rs("id") %>'"></td>
    <td width="14%"><a href="addWeeks.asp?id=<%= rs("id") %>&cmd=edit">修改</a> <!--| <a href="weekslist.asp?id=<%= rs("id") %>&cmd=del&page=<%= page %>" onClick="if(confirm('你确定要删除吗?')){return true;}else{return false;}">删除</a>--></td>
  </tr>
  <% rs.movenext
  j=j+1
  wend
   %>
</table>

<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25"><!-- #include file="../../include/page.asp" --></td>
  </tr>
</table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6"></td>
  </tr>
</table>
</body>
</html>
<% 
rs.close
set rs=nothing
conn.close
set conn=nothing
 %>