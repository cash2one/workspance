<%
localip=Request.ServerVariables("REMOTE_ADDR")
%><style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style>
<table width="60%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#333333">
<form name="form2" method="post" action="http://admin.zz91.com/admin1/guoqing_save.asp">
<tr>
    <td align="right" bgcolor="#D6D6D6">请输入验证密码:<input name="ip" type="hidden" id="ip" value="<%=localip%>"></td>
    <td bgcolor="#D6D6D6"><input name="pass" type="password" id="pass" class="txtLen"></td>
</tr>
<tr>
  <td align="right" bgcolor="#D6D6D6">&nbsp;</td>
  <td bgcolor="#D6D6D6"><input type="submit" name="Submit" value="提交"></td>
</tr>
</form>
</table>