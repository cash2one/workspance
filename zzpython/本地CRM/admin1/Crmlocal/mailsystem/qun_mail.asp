<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Email��ʱ����</title>
<style type="text/css">
<!--
.timekuang {
	background-color: #DCEDDC;
	padding: 4px;
	border: 1px solid #666666;
	width: 150px;
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
function sendemail()
{
	m=window.form1.m.value
	allemail=mailcontent.allcom_id.value
	arrayemail=allemail.split("|")
	if (m<arrayemail.length-1)
		{
		document.all.tomailone.value=arrayemail[m]
		window.setTimeout("form1.submit()",8000);
		
		}else
		{
		document.all.sendmailall.innerHTML+="������ϣ�"
		//window.location.reload()
		//alert ("������ϣ�")
		}
	m=m+1
}
function startemail()
{
mailcontent.location.href="allmailcontent.asp"
//window.setTimeout("sendemail()",10000)
}
//-->
</script>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td align="center" style="display:none">
	<div class="timekuang">
	<p align="center">
		<font color="#FF0000" size="4" face="Arial, Helvetica, sans-serif"><strong>
			<span id=year></span>��<span id=month></span>��<br>
		<font size="7"><span id=day></span></font></strong></font>
		<br><font face="Arial, Helvetica, sans-serif" size="4"><span id=hour></span>:<span id=minute></span>:<span id=second></span></font></p>
    </div>
	</td>
    <td align="center">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <form name="form1" id="form1" method="post" action="qun_mail_save.asp" target="sendmail3">
    <tr>
      <td height="25" align="center">����Ⱥ��ʱ��
      <input name="hou" type="text" id="hou" value="0" size="5">
      ʱ
      <input name="minl" type="text" id="minl" value="10" size="5">
      ��
      <input name="sec" type="text" id="sec" value="0" size="5">
      ��      </td>
    </tr>
    <tr>
    <td height="25" align="center">
      <input name="m" type="hidden" id="m" value="0" size="10" />
      <input name="tomailone" type="text" id="tomailone" size="10" >
      <input type="button" name="Submit" value="�ֶ�Ⱥ��" onClick="startemail()">
      <input type="button" name="Submit" value="ֹͣ����" onClick="sendstop()"></td>
  </tr>
  </form>
  <tr>
    <td align="center"><IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="" width="0" height="0" name="sendmail" id="sendmail"></Iframe></td>
  </tr>
  <tr>
    <td align="center">
	<IFRAME border=0 frameBorder=0 frameSpacing=0 noResize  src="" width="0" height="0" name="mailcontent" id="mailcontent"></Iframe>
	<font id="sendmailall"></font></td>
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
function sendstop()
{
window.location.reload()
}
function tijioa(tf)
{
form1.target="_blank"
}
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
	//startemail()
	}else
	{
	setTimeout("showTime1()", 100);
	}
}
</script>