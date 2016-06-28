<% 

if menuid="" then menuid=1
%>
<style type="text/css">
<!--
.STYLE1 {
	color: #fff;
	font-weight: bold;
	background-color:#69f;
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

<table width="80%" border="0" align="center" cellpadding="5" cellspacing="0" style="border-bottom:1px solid #69f;margin-top:10px;">
  <tr>
    <td width="50%" align="center" <% If menuid=1 Then response.write("class='STYLE1'")%>><a href="admin_diary_list.asp?menuid=1" <% If menuid=1 Then response.write("class='b'")%>>未回复日报</a></td>
    <td width="50%" align="center" <% If menuid=2 Then response.write("class='STYLE1'")%>><a href="admin_diary_list.asp?menuid=2" <% If menuid=2 Then response.write("class='b'")%>>已回复日报</a></td>
  </tr>
</table>