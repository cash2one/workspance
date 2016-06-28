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
	height: 17px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	margin: 0px;
	padding: 0px;
	width: 100%;
	background-image: url(images/login_r5_c4.jpg);
	background-position: 0px -1px;
	background-repeat: no-repeat;
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
<table width="967" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
   <td height="116">&nbsp;</td>
  </tr>
  <tr>
   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="967">
	  <tr>
	   <td><img name="login_r2_c1" src="images/login_r2_c1.jpg" width="222" height="336" border="0" id="login_r2_c1" alt="" /></td>
	   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="463">
		  <tr>
		   <td><img name="login_r2_c2" src="images/login_r2_c2.jpg" width="463" height="51" border="0" id="login_r2_c2" alt="" /></td>
		  </tr>
		  <tr>
		   <td><img name="login_r3_c2" src="images/login_r3_c2.jpg" width="463" height="55" border="0" id="login_r3_c2" alt="" /></td>
		  </tr>
		  <tr>
		   <td><img name="login_r4_c2" src="images/login_r4_c2.jpg" width="463" height="7" border="0" id="login_r4_c2" alt="" /></td>
		  </tr>
          <form name="form1" method="post" action="loginofrc1.asp" onSubmit="return chkfrm(this)">
		  <tr>
		   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="463">
			  <tr>
			   <td><img name="login_r5_c2" src="images/login_r5_c2.jpg" width="249" height="19" border="0" id="login_r5_c2" alt="" /></td>
			   <td width="173" height="19" valign="top" ><input name="uid" type="text" id="uid" value="<%=uid%>" size="20" maxlength="24" class="input"></td>
			   <td><img name="login_r5_c7" src="images/login_r5_c7.jpg" width="41" height="19" border="0" id="login_r5_c7" alt="" /></td>
			  </tr>
			</table></td>
		  </tr>
		  <tr>
		   <td><img name="login_r6_c2" src="images/login_r6_c2.jpg" width="463" height="19" border="0" id="login_r6_c2" alt="" /></td>
		  </tr>
          
		  <tr>
		   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="463">
			  <tr>
			   <td><img name="login_r7_c2" src="images/login_r7_c2.jpg" width="249" height="20" border="0" id="login_r7_c2" alt="" /></td>
			   <td width="197" height="20" valign="top"><input name="pwd" type="password" id="pwd" size="20" maxlength="24" class="input"></td>
			   <td><img name="login_r7_c7" src="images/login_r7_c7.jpg" width="41" height="20" border="0" id="login_r7_c7" alt="" /></td>
			  </tr>
			</table></td>
		  </tr>
          
		  <tr>
		   <td><img name="login_r8_c2" src="images/login_r8_c2.jpg" width="463" height="23" border="0" id="login_r8_c2" alt="" /></td>
		  </tr>
		  <tr>
		   <td><table align="left" border="0" cellpadding="0" cellspacing="0" width="463">
			  <tr>
			   <td><img name="login_r9_c2" src="images/login_r9_c2.jpg" width="220" height="39" border="0" id="login_r9_c2" alt="" /></td>
			   <td width="90" height="39"><input type="image" name="imageField" id="imageField" src="images/login_r9_c3.jpg" style="margin:0px; padding:0px;"/></td>
			   <td><img name="login_r9_c5" src="images/login_r9_c5.jpg" width="26" height="39" border="0" id="login_r9_c5" alt="" /></td>
			   <td><img name="login_r9_c6" src="images/login_r9_c6.jpg" width="86" height="39" border="0" id="login_r9_c6" alt="" /></td>
			   <td><img name="login_r9_c7" src="images/login_r9_c7.jpg" width="41" height="39" border="0" id="login_r9_c7" alt="" /></td>
			  </tr>
			</table></td>
		  </tr>
          </form>
		  <tr>
		   <td><img name="login_r10_c2" src="images/login_r10_c2.jpg" width="463" height="103" border="0" id="login_r10_c2" alt="" /></td>
		  </tr>
		</table></td>
	   <td><img name="login_r2_c8" src="images/login_r2_c8.jpg" width="282" height="336" border="0" id="login_r2_c8" alt="" /></td>
	  </tr>
	</table></td>
  </tr>
  <tr>
   <td height="120">&nbsp;</td>
  </tr>
</table>
</body>
</html>
