<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- #include file="../dfd@#kang/dfd@#321)kang.asp" -->
<!-- #include file="function.asp" -->
<%
urll=Request.ServerVariables("QUERY_STRING")
url=mid(urll,5)
'response.Write(urll)
if url="" then url="/cn/guest_office_main.asp?"
frompage=request.querystring("frompage")
'***********************发密码
'*********************************
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>会员登录  中国再生资源交易网 www.zz91.com</title>
<link href="../css/zz91_top.css" rel="stylesheet" type="text/css" />
<link href="../css/zz91_search.css" rel="stylesheet" type="text/css" />
<link href="../css/zz91_bottom.css" rel="stylesheet" type="text/css" />
<link href="../css/zz91_offerdetail.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="sources/pop.js"></SCRIPT>
<script language="javascript">
	String.prototype.Trim  = function(){return this.replace(/^\s+|\s+$/g,"");}
function chkfrm1(frm)
{frm.email.value=frm.email.value.Trim();
if(frm.email.value.length<=0)
{
alert("请输入用户名!");
frm.email.focus();
return false;
}
if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.email.value)){  ///\w+@\w+(\.\w+)+/
alert("用户名格式错误");
frm.email.focus();
return  false;
}  
}
</script>
<style type="text/css">
<!--
.hhhhhh {
	line-height: 23px;
}
.STYLE7 {color: #990000}
.sbtishi_on {
	border:1px solid #485E00;
	background-color:#F7FFDD;
	padding: 5px;
	color: #485E00;
	margin-top: 4px;
	margin-right: 0px;
	margin-bottom: 5px;
	margin-left: 5px;
}
.sbtishi_off {
	border:1px solid #FFFFFF;
	padding: 2px;
	color: #999999;
	margin-top: 4px;
	margin-right: 0px;
	margin-bottom: 5px;
	margin-left: 5px;
}
.sbtishi_err {
	border:1px solid #FF6600;
	background-color:#FFF5D8;
	padding: 5px;
	color: #FF0000;
	background-image: url(images/icon_noteawake_16x16.gif);
	background-repeat: no-repeat;
	background-position: 3px 3px;
	text-indent: 18px;
	margin-top: 4px;
	margin-right: 0px;
	margin-bottom: 5px;
	margin-left: 5px;
}
.regline {
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #666666;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #CCCCCC;
	padding-left: 10px;
}
.regtext {
	margin-top: 4px;
	font-weight: bold;
}
.sbtishi_onE {
	border:1px solid #CC0000;
	background-color:#FFE9C8;
	padding: 5px;
	color: #FF0000;
	margin-top: 4px;
	margin-right: 0px;
	margin-bottom: 10px;
	margin-left: 0px;
}
.regline1 {	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #666666;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #CCCCCC;
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #CCCCCC;
}
.tishi {
	padding: 5px;
	border: 1px solid #CCCCCC;
}
.STYLE9 {margin-top: 4px}
.forgetpass_suc {
	font-size: 14px;
	line-height: 200px;
	text-align: center;
	height: 200px;
	color: #FF6600;
	font-weight: bolder;
}
-->
</style>
<SCRIPT language=javascript src="img/ImageSwap.js"></SCRIPT>
<SCRIPT language=javascript src="sources/pop.js"></SCRIPT>
	<script language="javascript">
	String.prototype.Trim  = function(){return this.replace(/^\s+|\s+$/g,"");}
function chkfrm1(frm)
{frm.email.value=frm.email.value.Trim();
if(frm.email.value.length<=0)
{
//alert("请输入用户名!");
//document.all.errtext.innerHTML="<li>请输入用户名！</li>"
//frm.email.focus();
//return false;
}
if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.email.value)){  ///\w+@\w+(\.\w+)+/
//alert("用户名格式错误");
//document.all.errtext.innerHTML="<li>用户名格式错误！</li>"
//frm.email.focus();
//return  false;
}  
if(frm.pwd.value.length<=0)
{
//alert("请输入密码!");
//document.all.errtext.innerHTML="<li>请输入密码！</li>"
//frm.pwd.focus();
//return false;
} 
}
</script>
</head>

<body>
<!-- #include file="2007top.asp" -->
<!-- #include file="2007midle.asp" -->
<div class="main_box">
<div id="mid_box_title">
		  <div class="mid_box_title_1">
			<div class="mid_box_title_txt">会员登录</div>
		  </div>
	      <div class="mid_box_title_3"></div>
          <div class="mid_box_title_2"></div>
  </div>
</div>

<div class="main_box">
  <div class="offer_kuan">
  <form name="form1" method="post" action="loginof.asp<%if frompage<>"" then response.write "?frompage="&frompage%>" onSubmit="return chkfrm1(this)">
<SCRIPT language=javascript src="sources/WebUIValidation.js" type=text/javascript></SCRIPT>
<% response.write(session("forgetpass"))%><BR>
<DIV id=Login_Container>
<DIV id=pnlLogin>
<DIV>
<%'if request.QueryString("err")<>"" then%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="35">
	  <div  style="font-size:12px; font-weight:bold; color:#FF0000"><font style="font-size:14px" id="errtext"><%
	  select case session("errtext")
	  case "1"
	  response.Write("<li>对不起，您的操作有误，请重新登录,如还不能登录，请与我们联系!电话：0571-87016311</li>")
	  case "2"
	  response.Write("<li>对不起，您不能登录，如有问题，请与我们联系!电话：0571-87016311</li>")
	  case "3"
	  response.Write("<li>对不起，登录失败！您的密码有误！请再次登录并确认输入的密码是否正确！</li>")
	  case "4"
	  response.Write("<li>对不起，登录失败！您输入的用户名不存在或确认您的用户名格式是否为电子邮箱！格式如：test@zz91.com</li>")
	  case "5"
	  response.Write("<li>对不起，请输入您的用户名！</li>")
	  case "6"
	  response.Write("<li>对不起，请输入您的密码！</li>")
	  case "7"
	  response.Write("<li>对不起，您的用户名(Email)格式错误！</li>")
	  end select
	  session("errtext")=""
	  %></font></div></td>
    </tr>
  </table>
<%'end if%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="350"><table width="316" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="newimages/login_new_top.gif" width="316" height="24"></td>
          </tr>
          <tr>
            <td height="182" background="newimages/login_new_bg.gif">
			<TABLE width=300 align="center" >
          <TBODY>
           
            <TR>
              <TD height="25" colSpan=2><DIV align="left">&nbsp;&nbsp;&nbsp;&nbsp;<span class="mdgray">请输入用户名（Email）和密码，按“登录”即可。</span></DIV></TD>
            </TR>
            <TR>
              <TD align=right nowrap>用户名(Email): </TD>
              <TD align=left><INPUT id="email" style="WIDTH: 150px" name="email" value="<%=Request.Cookies("zz91")("comemail")%>">                </TD>
            </TR>
            <TR>
              <TD align=right>密  &nbsp;&nbsp;码: </TD>
              <TD><INPUT id=pwd style="WIDTH: 150px" type=password name=pwd>                </TD>
            </TR>
            <TR align="center">
              <TD colSpan=2><LABEL for=remember>
                <input name="AutoGet" type="checkbox" id="AutoGet" value="1" checked>
                是否保留登录用户名
              </LABEL></TD>
            </TR>
            <TR>
              <TD align=center colSpan=2><span class="grayed">（如果在公共场合，如网吧，不推荐选择此项）</span></TD>
            </TR>
            <TR>
              <TD align=middle colSpan=2><a href="forgetpass.asp">忘记密码？</a>
                <input name="url" type="hidden" id="url" value="<%=url%>"></TD>
            </TR>
            <TR>
              <TD colspan="2" align="center"><INPUT language=javascript id=loginCtrl_Submit1  type=submit value=" 登 录 " name=loginCtrl:Submit1>
                &nbsp;&nbsp;
                <input type="button" name="Submit" value="免费注册" onClick="window.location='regbefore.asp?fromp=top'"></TD>
              </TR>
          </TBODY>
        </TABLE>
			</td>
          </tr>
          <tr>
            <td><img src="newimages/login_new_bottom.gif" width="316" height="12"></td>
          </tr>
        </table>
        </td>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="newimages/login_zst_top.gif" width="425" height="24"></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="newimages/login_zst_mid1.gif" width="207" height="70"></td>
                <td><img src="newimages/login_zst_mid2.gif" width="218" height="70"></td>
              </tr>
              <tr>
                <td><img src="newimages/login_zst_mid3.gif" width="207" height="74"></td>
                <td><img src="newimages/login_zst_mid4.gif" width="218" height="74"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><img src="newimages/login_zst_login.gif" width="425" height="38" border="0" usemap="#Map"></td>
          </tr>
          <tr>
            <td><img src="newimages/login_zst_bottom.gif" width="425" height="12"></td>
          </tr>
        </table></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="50">&nbsp;</td>
      </tr>
    </table>
    <DIV id=loginCtrl_Validationsummary1 style="DISPLAY: none; COLOR: red" 
showsummary="False" showmessagebox="True" 
headertext="You must enter a value in the following fields:"></DIV></DIV></DIV></DIV>
</FORM>
  </div>
</div>
<!-- #include file="2007bottom.asp" -->
</body>
</html>
