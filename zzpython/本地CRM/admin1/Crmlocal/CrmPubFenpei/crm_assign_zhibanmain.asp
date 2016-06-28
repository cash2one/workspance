<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Email定时发送</title>
<style type="text/css">
<!--
.timekuang {
	background-color: #DCEDDC;
	padding: 4px;
	border: 1px solid #666666;
	width: 100px;
}
td {
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script LANGUAGE=javascript>
<!--
var m=0
function startemail()
{
var d = new Date();
window.setTimeout("startfenpei()",10000)
setTimeout("showTime1()", 8000);
}
function startfenpei()
{
fenpei.form1.submit()
//setTimeout("showTime1()", 8000);
}
//-->
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center" bgcolor="#FFFFCC"><strong>星期六值班人员客户分配</strong></td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <form name="form1" id="form1" method="post" action="qf_mail_save.asp" target="sendmail">
    <tr>
      <td height="25" align="center">设置时间
        <input name="hou" type="text" id="hou" value="2" size="5">
      时
      <input name="minl" type="text" id="minl" value="0" size="5">
      分
      <input name="sec" type="text" id="sec" value="0" size="5">
      秒      </td>
    </tr>
    
  </form>
  <tr>
    <td align="center"><IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="crm_assign_zhibanfenpai.asp" width="100%" height="400px" name="fenpei" id="fenpei"></Iframe></td>
  </tr>
 
</table>
	</td>
  </tr>
</table>
</body>
</html>
<script>
showTime1();
var d = new Date();
//alert (d.getDay())
function showTime1()
{
sHours=form1.hou.value
sMinutes=form1.minl.value
sSeconds=form1.sec.value
	var d = new Date();
	//weektest.innerText=d.getDay()
	//form1.test.value=d.getDay()
	if (parseInt(d.getDay())==6)
	{
	//alert (d.getDay())
		if (d.getHours()==sHours && d.getMinutes()==sMinutes && d.getSeconds()==sSeconds)
		{
			startemail()
		}else
		{
			setTimeout("showTime1()", 100);
		}
	}
}
</script>