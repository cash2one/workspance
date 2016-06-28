<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!--#include file="../../include/pagefunction.asp"-->

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
<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">日报管理</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table></td>
      </tr>

      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE">
        <%
		isDraft=request("isDraft")
if isDraft="" then isDraft=0
if isDraft="0" then
	menuid=2
else
	menuid=3
end if
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
	.FROMTbl = "crm_diary"
	.sqlOrder= "order by id desc"
	.sqlWhere= "WHERE personid="&session("personid")&" "&sql
	.keyFld  = "id"    '不可缺少
	.pageSize= 10
	.getConn = conn
	Set Rs  = .pageRs
End With
		%>
        <!--#include file="my_diary_menu.asp"-->
		<% 

total=oPage.getTotalPage
oPage.pageNav "?"&sear,""
totalpg=cint(total/10)
if total/10 > totalpg then
	totalpg=totalpg+1
end if
 %>
		
<br>
<table width="80%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
  <tr>
    <td height="25" bgcolor="#B0CAFF">日报标题</td>
    <td bgcolor="#B0CAFF">是否有回复</td>
    <td bgcolor="#B0CAFF">修改</td>
    <td bgcolor="#B0CAFF">查看</td>
  </tr>
<% 
j=1
while not rs.eof 
if j mod 2 = 1 then
	trColor="#ffffff"
else
	trColor="#eeeeee"
end if%>
  <tr bgcolor="<%= trColor %>">
    <td width="24%"><a href="my_oldDiary.asp?diaryid=<%= rs("id") %>"><%= rs("fdate") %>的日报</a></td>
    <td width="24%">
	<%If isDraft=0 then
		if rs("haveReply")="1" then
			response.write "<font color=green>已回复</font>"
		else
			response.write "<font color=red>未回复</font>"
		end if
	end if %>	</td>
    <td width="24%">
    <%if rs("haveReply")="0" then%>
    <a href="my_oldDiary.asp?diaryid=<%= rs("id") %>&actontype=edit">修改日报</a>
    <%else%>
    已回复不能修改
    <%end if%>
    </td>
    <td width="24%"><a href="my_oldDiary.asp?diaryid=<%= rs("id") %>&actontype=look">查看日报</a></td>
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