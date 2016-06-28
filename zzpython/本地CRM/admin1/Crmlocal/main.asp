<!-- #include file="../jumptolog.asp" -->
<%
sql="select realname from users where id="&session("personid")
set rs=conn.execute(sql)
if not rs.eof then
realname=rs(0)
end if
rs.close
set rs=nothing
%><html>
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>中国再生资源交易网 | RecycleChina.com</title>
<script language=Javascript>
<!--
function showHideMenu() {
	if (frmMenu.style.display=="") {
		frmMenu.style.display="none"
		//switchPoint.innerText=4
		document.all.jiantou.src="../newimages/jiantou1.jpg"
		}
	else {
		frmMenu.style.display=""
		document.all.jiantou.src="../newimages/jiantou.jpg"
		//switchPoint.innerText=3
		}
}
function showHideMenu1() {
	if (mainright.style.display=="") {
		mainright.style.display="none"
		//switchPoint.innerText=4
		}
	else {
		mainright.style.display=""
		//switchPoint.innerText=3
		}
}
-->
</script>
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

</head>
	
<body scroll="no" style="MARGIN: 0px" leftmargin="0" topmargin="0">






<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="26">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="26" background="../newimages/topbg.gif">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="4%"><a href="#" onClick="showHideMenu()"><img src="../newimages/jiantou.jpg" width="25" id="jiantou" height="26" border="0"></a></td>
          <td width="25%" class="f">用 户 名：<%=session.Contents("admin_user")%></td>
          <td nowrap class="f">&nbsp;</td>
          <td align="right" class="f">             <a href="admin/admin_user_mod.asp?lmcode=1403" class="f" target="main">修改密码</a>  <a href="loginout.asp"  class="f">用户注销</a> &nbsp;</td>
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
					<iframe id=BoardTitle name=BoardTitle  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 181px; Z-INDEX: 2" frameborder="0" src="../menu.asp">
</iframe></td><!--工具栏显示区结束--><!--工具栏控制开关-->
				
             <td style="WIDTH: 2pt" bgcolor="#7386A5" onClick="showHideMenu()" >	
      <span id="switchPoint" title="关闭/开启菜单栏" class="navPoint"></span> </td>
    <!--工具栏控制开关结束-->
			<td width=100% id="mainmid">
            <!--内容显示对应-->
            <iframe id="frmRight" name="main" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1" frameborder="0" src="about:blank"></iframe>
			</td>
            <td id="mainright" style="display:none ">
			
			</td>
              <!--内容显示对应结束-->
		  
		  </tr>
			
</table>
	</td>
  </tr>
</table>
</body>
</html>