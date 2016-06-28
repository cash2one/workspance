<!-- #include file="include/adfsfs!@#.asp" -->
<!-- #include file="localjumptolog.asp" -->
<%
sql="select realname from users where id="&session("personid")
set rs=conn.execute(sql)
if not rs.eof then
realname=rs(0)
end if
rs.close
set rs=nothing
sql="select personid from userZD where personid="&session("personid")&""
set rs=conn.execute(sql)
if rs.eof or rs.bof then
	response.Redirect("zduser.asp")
end if
rs.close
set rs=nothing
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>中国再生资源交易网 | RecycleChina.com</title>

<style type="text/css">
<!--
.navPoint
{
	font-family: Webdings;
	font-size:9pt;
	color:white;
	cursor:hand;
}
p
{
	font-size:9pt;
}
.tblbody
{
	BACKGROUND: #6699cc;
	BORDER-BOTTOM: #30404f 2px solid;
	BORDER-LEFT: #b8d2e9 2px solid;
	BORDER-RIGHT: #30404f 2px solid;
	BORDER-TOP: #b8d2e9 2px solid
}
.tblLeftbody
{
	BACKGROUND: #8cbde7;
	BORDER-BOTTOM: #30404f 2px solid;
	BORDER-LEFT: #b8d2e9 2px solid;
	BORDER-RIGHT: #30404f 0px solid;
	BORDER-TOP: #b8d2e9 2px solid
}.tblbody
{
	BACKGROUND: #6699cc;
	BORDER-BOTTOM: #30404f 2px solid;
	BORDER-LEFT: #b8d2e9 2px solid;
	BORDER-RIGHT: #30404f 2px solid;
	BORDER-TOP: #b8d2e9 2px solid
}
.tblLeftbody
{
	BACKGROUND: #8cbde7;
	BORDER-BOTTOM: #30404f 2px solid;
	BORDER-LEFT: #b8d2e9 2px solid;
	BORDER-RIGHT: #30404f 0px solid;
	BORDER-TOP: #b8d2e9 2px solid
}
td {
	font-size: 12px;
}
.f {
	font-size: 12px;
	color: #FFFFFF;
}
-->
</style>
<script language="javascript">
function showHideMenu() {
	if (frmMenu.style.display=="") {
		frmMenu.style.display="none"
		//switchPoint.innerText=4
		document.all.jiantou.src="newimages/jiantou1.jpg"
		}
	else {
		frmMenu.style.display=""
		document.all.jiantou.src="newimages/jiantou.jpg"
		//switchPoint.innerText=3
		}
}
	function tick() {
	   var today
	   today = new Date()
	   Clock.innerHTML = today.toLocaleString();
	   window.setTimeout("tick()", 1000);
	}
	function initial() {
	   dStyle = detail.style;
	   CLD.SY.selectedIndex=tY-1900;
	   CLD.SM.selectedIndex=tM;
	   drawCld(tY,tM);
	   tick();
	}
</script>


<style type="text/css">
<!--
.moveover {
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFFFFF;
	border-right-color: #999999;
	border-bottom-color: #999999;
	border-left-color: #FFFFFF;
}
.moveout {
	border: 1px solid #ECE9D8;
	cursor: hand;


}
body,td,th {
	font-size: 12px;
}
-->
</style>
</head>
<body scroll="no" style="MARGIN: 0px" leftmargin="0" topmargin="0">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="26">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="26" background="newimages/topbg.gif">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="4%"><a href="#" onClick="showHideMenu()"><img src="newimages/jiantou.jpg" width="25" id="jiantou" height="26" border="0"></a></td>
          <td width="25%" class="f">用 户：<%=realname%></td>
          <td nowrap class="f">&nbsp;</td>
          <td align="right" class="f">
		  <a href="mainnews.asp" target="main"><font color="#FFFF00">CRM操作说明</font></a> | <a href="http://192.168.2.200:8081/loginbbs.asp?personid=<%=Request.Cookies("personid")%>" target="_blank"><font color="#FFFF00">阿思拓论坛</font></a>  | <a href="admin_user_mod.asp?lmcode=1403" class="f" target="main">修改密码</a>  |  <a href="loginout.asp"  class="f">用户注销</a> &nbsp;&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td valign="top">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" id = "tblTotal" Name = "tblTotal">
			<tr><!--工具栏显示区-->
				<td id="frmMenu" name="frmMenu" nowrap valign="center" align="right">
					<iframe id=BoardTitle name=BoardTitle  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 181px; Z-INDEX: 2" frameborder="0" src="menu.asp">
</iframe></td><!--工具栏显示区结束--><!--工具栏控制开关-->
				
             <td style="WIDTH: 2pt" bgcolor="#7386A5" onClick="showHideMenu()" >	
      <span id="switchPoint" title="关闭/开启菜单栏" class="navPoint"></span> </td>
    <!--工具栏控制开关结束-->
			<td width=100% id="mainmid">
            <!--内容显示对应-->
            <iframe id="frmRight" name="main" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1" frameborder="0" src="mainnews.asp"></iframe>
			</td>
            <td id="mainright" style="display:none ">
			
			</td>
              <!--内容显示对应结束-->
		  
		  </tr>
			
</table>
	</td>
  </tr>
</table>
<%
username=realname
room="1"
serverIP="192.168.1.2:806"
%>

<!--<iframe border=0 src="http://192.168.1.2:806/main.asp?username=<%=realname%>&room=1" scrolling="no" name="speak" id="speak" onfocus="bScroll=true;" marginwidth=0 framespacing=0 marginheight=0 frameborder=0 noresize width="100%" height="100%" vspale="0"></iframe>-->
</body>
</html>