﻿<!-- #include file="../include/ad!$#5V.asp" -->
<%
com_id=request.QueryString("com_id")
mbflag=request.QueryString("mbflag")
userid=request.Cookies("UserId")
personid=request.Cookies("PersonId")
username=request.Cookies("admin_user")

if personid<>"" then
	sqlu="select realname from users where id="&personid&" and chatflag=1"
	set rsu=conn.execute(sqlu)
	if not rsu.eof or not rsu.bof then
		username=rsu(0)
		response.Redirect("http://adminasto.zz91.com/openConfirm1/?com_id="&com_id&"&userid="&userid&"&mbflag="&mbflag&"&personid="&personid&"&username="&username)
	else
		response.Write("对不起，你的登录帐号不正确，请重新登录！  <a href='loginout.asp'>重新登录</a>")
		response.End()
	end if
	rsu.close
	set rsu=nothing
end if

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售业绩管理</title>
<link href="s.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="400" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#333333">
  <form id="form1" name="form1" method="post" action="loginof.asp"><tr>
    <td colspan="2" bgcolor="#CCCCCC">登录（<strong style="color:#F00">用聊天版的帐号即可登录</strong>）
      <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" />
      <input type="hidden" name="mbflag" id="mbflag" value="<%=mbflag%>" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">用户名</td>
    <td bgcolor="#FFFFFF">
      <input type="text" name="uid" id="uid" />
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">密 码</td>
    <td bgcolor="#FFFFFF"><input type="password" name="pwd" id="pwd" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="提交" />
      <input type="reset" name="button2" id="button2" value="重置" /></td>
  </tr>
  </form>
</table>
</body>
</html>
