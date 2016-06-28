<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<%
set connserver=server.CreateObject("ADODB.connection")
strconnserver="Provider=SQLOLEDB.1;driver={SQL Server};server=www.zz91.com,2433;uid=rcu;pwd=dfsdf!@!@#sdfds$#^!*dfdsf4343749d;database=rcu"
connserver.open strconnserver
set conn=server.CreateObject("ADODB.connection")
strconn="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=cru_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu_crm"
conn.open strconn
%>


</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="4">
<form name="form1" id="form1" method="post" action="" >
  <tr align="center">
    <td colspan="4">设置时间
      <input name="hou" type="text" id="hou" value="0" size="5">
      时
      <input name="minl" type="text" id="minl" value="10" size="5">
      分
      <input name="sec" type="text" id="sec" value="0" size="5" onClick="startemail()">
      秒      </td>
  </tr>
</form>
  <tr align="center">
    <td><a href="crm_compfenpei_comp.asp" target="comp">公司数据更新</a></td>
    <td>&nbsp;</td>
    <td><a href="crm_compfenpei_login.asp" target="login">登录数据更新</a></td>
    <td><a href="crm_compfenpei_logininfo.asp" target="logininfo">登录内容更新</a></td>
  </tr>
  <tr align="center">
    <td height="100"><IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="about:blank" width="100%" height="100px" name="comp" id="comp"></Iframe></td>
    <td>&nbsp;</td>
    <td><IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="about:blank" width="100%" height="100px" name="login" id="login"></Iframe></td>
    <td><IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="about:blank" width="100%" height="100px" name="logininfo" id="logininfo"></Iframe></td>
  </tr>
</table>
<script>
function startemail()
{
comp.window.location="crm_compfenpei_comp.asp"
//zst.window.location="crm_compfenpei_zst.asp"
login.window.location="crm_compfenpei_login.asp"
logininfo.window.location="crm_compfenpei_logininfo.asp"
}
showTime1();
function showTime1()
{
sHours=form1.hou.value
sMinutes=form1.minl.value
sSeconds=form1.sec.value
	var d = new Date();
	if (d.getHours()==0 && d.getMinutes()==0 && d.getSeconds()==0)
	{
	window.location="crm_compfenpei.asp"
	}
	if (d.getHours()==sHours && d.getMinutes()==sMinutes && d.getSeconds()==sSeconds)
	{
	startemail()
	}else
	{
	setTimeout("showTime1()", 100);
	}
}
</script>
</body>
</html>
