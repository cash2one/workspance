<%
uid=request.QueryString("uid")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>CRM客户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<style type="text/css">
td img {display: block;}
body,td {
	background-color: #F4F4F4;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	padding:0px;
	margin:0px;
}
.input
{
	height: 15px;
	margin: 0px;
	padding: 0px;
	width: 100%;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
}
</style>
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
}
</script>
</head>
<body bgcolor="#ffffff">
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
<form name="form1" method="post" action="loginofrc1.asp" onSubmit="return chkfrm(this)">
  <tr>
   <td colspan="3"><img name="login05_r1_c1" src="images/login/login05_r1_c1.jpg" width="1003" height="290" border="0" id="login05_r1_c1" alt="" /></td>
  </tr>
  <tr>
   <td rowspan="6"><img name="login05_r2_c1" src="images/login/login05_r2_c1.jpg" width="470" height="390" border="0" id="login05_r2_c1" alt="" /></td>
   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="178">
	  <tr>
	   <td><img name="login05_r2_c2" src="images/login/login05_r2_c2.jpg" width="32" height="18" border="0" id="login05_r2_c2" alt="" /></td>
	   <td width="146" height="18"><input name="uid" type="text" id="uid" value="<%=uid%>" size="20" maxlength="24" class="input"></td>
	  </tr>
	</table></td>
   <td rowspan="6"><img name="login05_r2_c7" src="images/login/login05_r2_c7.jpg" width="355" height="390" border="0" id="login05_r2_c7" alt="" /></td>
  </tr>
  <tr>
   <td><img name="login05_r3_c2" src="images/login/login05_r3_c2.jpg" width="178" height="16" border="0" id="login05_r3_c2" alt="" /></td>
  </tr>
  <tr>
   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="178">
	  <tr>
	   <td><img name="login05_r4_c2" src="images/login/login05_r4_c2.jpg" width="32" height="18" border="0" id="login05_r4_c2" alt="" /></td>
	   <td width="146" height="18"><input name="pwd" type="password" id="pwd" size="20" maxlength="24" class="input"></td>
	  </tr>
	</table></td>
  </tr>
  <tr>
   <td><img name="login05_r5_c2" src="images/login/login05_r5_c2.jpg" width="178" height="38" border="0" id="login05_r5_c2" alt="" /></td>
  </tr>
  <tr>
   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="178">
	  <tr>
	   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="63">
		  <tr>
		   <td><input type="image" name="imageField" id="imageField" src="images/login/login05_r6_c2.jpg" /></td>
		  </tr>
		  <tr>
		   <td><img name="login05_r7_c2" src="images/login/login05_r7_c2.jpg" width="63" height="1" border="0" id="login05_r7_c2" alt="" /></td>
		  </tr>
		</table></td>
	   <td><img name="login05_r6_c4" src="images/login/login05_r6_c4.jpg" width="32" height="22" border="0" id="login05_r6_c4" alt="" /></td>
	   <td><img name="login05_r6_c5" src="images/login/login05_r6_c5.jpg" width="62" height="22" border="0" id="login05_r6_c5" alt="" /></td>
	   <td><img name="login05_r6_c6" src="images/login/login05_r6_c6.jpg" width="21" height="22" border="0" id="login05_r6_c6" alt="" /></td>
	  </tr>
	</table></td>
  </tr>
  <tr>
   <td><img name="login05_r8_c2" src="images/login/login05_r8_c2.jpg" width="178" height="278" border="0" id="login05_r8_c2" alt="" /></td>
  </tr>
 </form> 
</table>
</body>
</html>
