<% 

if menuid="" then menuid=1
%>
<style type="text/css">
<!--
.STYLE1 {
	color: #fff;
	font-weight: bold;
	background-color:#FF0000;
}
a.b:link {
	color: #FFFFFF;
	text-decoration: none;
}
a.b:visited {
	text-decoration: none;
	color: #FFFFFF;
}
a.b:hover {
	text-decoration: none;
	color: #fff;
}
a.b:active {
	text-decoration: none;
}
a:link {
	color: #000;
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000;
}
a:hover {
	text-decoration: underline;
	color: #000;
}
a:active {
	text-decoration: none;
}
form{
	margin:0;
	padding:0;
}
-->
</style>

<table width="400" border="0" align="center" cellpadding="5" cellspacing="0" style="border-bottom:1px solid #f00;margin-top:10px;">
  <tr>
    <td width="33%" align="center" <% If menuid=1 Then response.write("class='STYLE1'")%>><a href="my_writeDiary.asp" <% If menuid=1 Then response.write("class='b'")%>>写日报 (<%= date() %>)</a></td>
    <td width="33%" align="center" <% If menuid=2 Then response.write("class='STYLE1'")%>><a href="my_diary_list.asp?isDraft=0" <% If menuid=2 Then response.write("class='b'")%>>已发日报</a></td>
    <!--<td align="center" <% If menuid=3 Then response.write("class='STYLE1'")%>><a href="my_diary_list.asp?isDraft=1" <% If menuid=3 Then response.write("class='b'")%>>草稿箱</a></td>-->
  </tr>
</table>