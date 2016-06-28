<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%uid=request.QueryString("uid")%>
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../sources/style.css" type="text/css">
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
</head>
<body onLoad="form1.uid.focus();">
<table width="100" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
	<table width="100" border="0" align="center" cellpadding="0" cellspacing="0" class=table01>
        <tr> 
          <td class=left_14>&nbsp;</td>
          <td class=center_14>登录</td>
          <td class=right_14>&nbsp;</td>
        </tr>
        <tr> 
          <td class=left_13>&nbsp;</td>
          <td class=center_13><br>
		  <script language="javascript">
function chkfrm(frm)
{
if(frm.uid.value.length<=0)
{
alert("请输入用户名!");
frm.uid.focus();
return false;
}
if(frm.pwd.value.length<=0)
{
alert("请输入密码!");
frm.pwd.focus();
return false;
} 
if(frm.getcode.value.length<=0)
{
alert("请输入校验码!");
frm.getcode.focus();
return false;
}
}
</script>
<table width="300" border="0" align="center">
<form name="form1" method="post" action="loginofrc1.asp" onSubmit="return chkfrm(this)">
  <tr>
    <td align="center">用户名</td>
    <td>
      <input name="uid" type="text" id="uid" value="<%=uid%>" size="20" maxlength="24"></td>
  </tr>
  <tr>
    <td align="center">密&nbsp;&nbsp;码</td>
    <td><input name="pwd" type="password" id="pwd" size="20" maxlength="24"></td>
  </tr>
  <tr>
        <td align="center">检验码</td>
        <td><input name="getcode" type="text" id="getcode" size="10" maxlength="20">
        &nbsp;<img src="../../images/code/code.asp">
        </td>
      </tr>
  <tr>
    <td colspan="2" align="center"><input type="submit" name="Submit" value="提交">
      &nbsp;
      <input type="reset" name="reset" value="重置"></td>
    </tr>
  </form>
</table><br>
		  </td>
          <td class=right_13>&nbsp;</td>
        </tr>
        <tr> 
          <td class=left_12>&nbsp;</td>
          <td class=center_12>&nbsp;</td>
          <td class=right_12>&nbsp;</td>
        </tr>
</table>
	</td>
  </tr>
</table>


</body>
</html>