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
<table width="100%"  border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td width="100" align="center" style="display:none">
	<div class="timekuang">
	<p align="center">
		<font color="#FF0000" size="2" face="Arial, Helvetica, sans-serif"><strong>
			<span id=year></span>年<span id=month></span>月<br>
		<font size="7"><span id=day></span></font></strong></font>
		<br>
		<font face="Arial, Helvetica, sans-serif" size="4"><span id=hour></span>:<span id=minute></span>:<span id=second></span></font></p>
    </div>
	</td>
    <td align="center">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <form name="form1" id="form1" method="post" action="qf_mail_save.asp" target="sendmail">
    <tr>
      <td height="25" align="center">设置时间
      <input name="hou" type="text" id="hou" value="0" size="5">
      时
      <input name="minl" type="text" id="minl" value="10" size="5">
      分
      <input name="sec" type="text" id="sec" value="0" size="5">
      秒      </td>
    </tr>
    
  </form>
  <tr>
    <td align="center"><IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="crm_assign_pubsales.asp" width="100%" height="400px" name="fenpei" id="fenpei"></Iframe></td>
  </tr>
 
</table>
	</td>
  </tr>
</table>
<script language="javascript">

function showTime()
{
	var d = new Date();
	
	year.innerText = d.getFullYear();
	month.innerText = d.getMonth() + 1;
	day.innerText = d.getDate();
	hour.innerText = d.getHours();
	minute.innerText = d.getMinutes();
	second.innerText = d.getSeconds();
	setTimeout("showTime()", 100);
}
showTime();
</script>

</body>
</html>
<script>
showTime1();
function showTime1()
{
sHours=form1.hou.value
sMinutes=form1.minl.value
sSeconds=form1.sec.value
	var d = new Date();
	if (d.getHours()==sHours && d.getMinutes()==sMinutes && d.getSeconds()==sSeconds)
	{
	startemail()
	}else
	{
	setTimeout("showTime1()", 100);
	}
}
</script>