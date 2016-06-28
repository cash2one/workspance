var vn="Microsoft Internet Explorer";
var some;
if(navigator.appName!=vn)
some=1900;
else
some=0;
function montharr(m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11)
{
this[0] = m0;
this[1] = m1;
this[2] = m2;
this[3] = m3;
this[4] = m4;
this[5] = m5;
this[6] = m6;
this[7] = m7;
this[8] = m8;
this[9] = m9;
this[10] = m10;
this[11] = m11;
}
function calendar()
{
var monthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
var today = new Date();
var thisDay;
var monthDays = new montharr(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
year = today.getYear();
thisDay = today.getDate();
if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) monthDays[1] = 29;
nDays = monthDays[today.getMonth()];
firstDay = today;
firstDay.setDate(1); // works fine for most systems
testMe = firstDay.getDate();
if (testMe == 2) firstDay.setDate(0);
startDay = firstDay.getDay();
document.write('<table border="0" cellspacing="0" cellpadding="0"  bgcolor="#666699"><TR><TD><table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#666699">');
document.write('<TR><th height=20 colspan="7" bgcolor="#C8E3FF">');
var dayNames = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
var monthNames = new Array("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月");
var now = new Date();
document.write("<font style=font-size:9pt;Color:#330099>" , "今天是" , " " , now.getYear() + some , "年" , " " , monthNames[now.getMonth()] , " " , now.getDate() , "日" , " " , dayNames[now.getDay()] , "</FONT>");
document.writeln('</TH></TR><TR><TH BGCOLOR="#0080FF"><font style="font-size:9pt;Color:White">日</FONT></TH>');
document.writeln('<th bgcolor="#0080FF"><font style="font-size:9pt;Color:White">一</FONT></TH>');
document.writeln('<TH BGCOLOR="#0080FF"><font style="font-size:9pt;Color:White">二</FONT></TH>');
document.writeln('<TH BGCOLOR="#0080FF"><font style="font-size:9pt;Color:White">三</FONT></TH>');
document.writeln('<TH BGCOLOR="#0080FF"><font style="font-size:9pt;Color:White">四</FONT></TH>');
document.writeln('<TH BGCOLOR="#0080FF"><font style="font-size:9pt;Color:White">五</FONT></TH>');
document.writeln('<TH BGCOLOR="#0080FF"><font style="font-size:9pt;Color:White">六</FONT></TH>');
document.writeln("</TR><TR>");
column = 0;
for (i=0; i<startDay; i++)
{
document.writeln("\n<TD><FONT style=font-size:9pt>&nbsp;</FONT></TD>");
column++;
}
for (i=1; i<=nDays; i++)
{
if (i == thisDay)
{
document.writeln('</TD><td align="CENTER" bgcolor="#FF8040"><FONT style=font-size:9pt;Color:#ffffff><B>')
}
else
{
document.writeln('</TD><TD BGCOLOR="#FFFFFF" ALIGN="CENTER"><FONT style=font-size:9pt;font-family:Arial;font-weight:bold;Color:#330066>');
}
document.writeln(i);
if (i == thisDay) document.writeln("</FONT></TD>")
column++;
if (column == 7)
{
document.writeln("<TR>"); 
column = 0;
}
}
document.writeln('<TR><td colspan="7" align="CENTER" valign="TOP" bgcolor="#0080FF"><form name="clock" onSubmit="0"><FONT style=font-size:9pt;Color:#ffffff>&nbsp;现在时间：</FONT><INPUT TYPE="Text" NAME="face" ALIGN="TOP"></TD></TR></TABLE></TD></TR></TABLE></FORM>');
}
var timerID = null;
var timerRunning = false;
function stopclock ()
{
if(timerRunning)
clearTimeout(timerID);
timerRunning = false;
}
function showtime()
{
var now = new Date()
var hours = now.getHours()
var minutes = now.getMinutes()
var seconds = now.getSeconds()
var timeValue = ""
if(hours>=1 && hours <4)
{timeValue += ("凌晨")}
if(hours>=4 && hours<6)
{timeValue += ("清晨")}
if(hours>=6 && hours<9)
{timeValue += ("早上")}
if(hours>=9 && hours<12)
{timeValue += ("上午")}
if(hours>=12 && hours<13)
{timeValue += ("中午")}
if(hours>=13 && hours<18)
{timeValue += ("下午")}
if(hours>=18 && hours <19)
{timeValue += ("傍晚")}
if(hours>=19 && hours <24)
{timeValue += ("晚上")}
if(hours<1)
{timeValue += ("午夜")}
timeValue += ((hours > 12) ? hours - 12 : hours)
timeValue += ((minutes < 10) ? ":0" : ":") + minutes
timeValue += ((seconds < 10) ? ":0" : ":") + seconds
document.clock.face.value = timeValue
timerID = setTimeout("showtime()",1000)
timerRunning = true
}
function startclock ()
{
stopclock();
showtime();
}
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
