<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>在线聊天</title>
<script src="main1.js"></script>
<script>
function openpic()
{
    var cf=document.getElementById("CalFrame");
	var oImg=document.getElementById("dimg1");
	//alert (cf.style.display)
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){eT+=p.offsetTop;eL+=p.offsetLeft;p=p.offsetParent;}
	cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	if(cf.style.display=="block")
	{
	cf.style.display="none";
	}else
	{
	cf.style.display="block";
	}
}
function hideCalendar()
{
	var cf=document.getElementById("CalFrame");
	cf.style.display="none";
}
function ViewMsg(tu)
{
window.focus()
UserList.Onbacksay(tu)
var screenHeight = screen.height;
var screenWidth = screen.width; 
external.left=screenWidth-900;
external.top=screenHeight-600;
external.SetTransparentMask("netbox:/1.gif");
}
function loadwin()
{
var screenHeight = screen.height;
var screenWidth = screen.width;  
external.left=screenWidth-900;
external.top=screenHeight-600;
}
loadwin()
function onMouseDown()
{
	if(event.srcElement.tagName != "A")
		external.drag();
}

var bHide = true;

function onClickClock(me)
{
    var screenHeight = screen.height;
	var screenWidth = screen.width;  
	if(bHide)
	{
	    external.left=screenWidth-100;
		external.top=screenHeight-100;
		me.innerText = "显示";
		//external.center;
		external.ToolWindow = true
        external.TopMost = true
		external.SetTransparentMask("netbox:/2.gif");
	}
	else
	{
		me.innerText = "关闭";
		//Set ht = CreateObject("NetBox.HtmlWindow")
        //external.ToolWindow = true
        //external.TopMost = true
		
		external.left=screenWidth-900;
		external.top=screenHeight-600;
		external.SetTransparentMask("netbox:/1.gif");
		//external.close;
		
	}

	bHide = !bHide;

	return false;
}
//document.onclick=hideCalendar;
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
<script>
<!--
getMsg("t","sdfsdfsdfsds")
var ad_width = 300, ad_height = 188, speed = 5, hideafter = 0, isie = 0, objTimer;
var x, y, dx, dy, intervalMillisecond = 100000;
var oPopup = window.createPopup();
//objTimer = window.setInterval("getMsg()", intervalMillisecond); 
function getMsg()
{
        x = screen.width, y = screen.height-122, dx = speed, dy = speed;
        if(window.navigator.appName=="Microsoft Internet Explorer"&&window.navigator.appVersion.substring(window.navigator.appVersion.indexOf("MSIE")+5,window.navigator.appVersion.indexOf("MSIE")+8)>=5.5) 
        {
                isie=1;
        }
        if(isie) 
        {
               var winstr =  "<table id=eMeng width='300' border='0' cellpadding='2' cellspacing='1' bgcolor='#B4C0C9'>"    +"\n"+
                                        "        <tr>"    +"\n"+
                                        "                <td bgcolor=#ffffff background=images/f1.gif>"    +"\n"+
                                        "                        <table cellSpacing=0 cellPadding=0 width=\"100%\" border=0>"    +"\n"+
                                        "                        <tbody>"    +"\n"+
                                        "                                <tr>"    +"\n"+
                                        "                                        <td style=\"FONT-SIZE: 12px; COLOR: #666666;\" height=23>&nbsp;消息提示</td>"    +"\n"+
                                        "                                        <td align=\"right\" valign=\"top\" ><img id=\"imgClose\" src=\"Images/x.gif\" border=\"0\" onClick=\"parent.hidepop()\" alt=\"关闭\"></td>"    +"\n"+
                                        "                                </tr>"    +"\n"+
                                        "                                <tr>"    +"\n"+
                                        "                                        <td width=\"100%\" align=left valign=\"top\"  colSpan=2 height=138 >"    +"\n"+
                                        "                                                <DIV style='font-size:12px;padding: 2px;' >"    +"\n"+
                                        "                                                        <b>"++"说：</b><br>"++""    +"\n"+
                                        "                                                </DIV>"    +"\n"+
                                        "                                        </td>"    +"\n"+
                                        "                                </tr>"    +"\n"+
                                        "                                <tr>"    +"\n"+
                                        "                                        <td colspan=\"2\" valign=\"bottom\" align=\"right\">"    +"\n"+
                                        "                                               <input id=\"btn_view\" onclick=\"parent.ViewMsg('"+tu+"')\" type=\"button\" value=\"回复\"> "    +"\n"+
                                        "                                                <input id=\"btn_close\" type=\"button\" value=\"关闭\" onClick=\"parent.hidepop()\">"    +"\n"+
                                        "                                        </td>"    +"\n"+
                                        "                                </tr>"    +"\n"+
                                        "                        </tbody>"    +"\n"+
                                        "                        </table>"    +"\n"+
                                        "                </td>"    +"\n"+
                                        "        </tr>"    +"\n"+
                                        "</table>";


 
                                        
                var oPopupBody = oPopup.document.body;
                oPopupBody.innerHTML = winstr;
                oPopupBody.onmouseover = new Function("window.clearInterval(objTimer)");
                oPopupBody.onmouseout = popshow;
                popshow();        
                if (hideafter>0) window.setInterval("hidepop()", hideafter*1000);        
        }
}

function popshow()
{
        if(isie) 
        {
                window.status = y;

                window.status = y+"ad_height:"+ad_height+"dy:"+dy+"hideafter:"+hideafter+"objTimer:"+objTimer;;
                
                y = y + dy;
                oPopup.show(x, y, ad_width, ad_height);
                if(((y + ad_height) > screen.height) || (y < 0)) dy = -dy;
                if (y < 617) dy = 0;
                
                objTimer = setInterval("popshow();", 10);
				window.clearInterval(objTimer);
        }        
}

function hidepop()
{
        if (objTimer) window.clearInterval(objTimer);
        oPopup.hide();
        //objTimer = window.setInterval("getMsg()", intervalMillisecond); 
}
// -->
</script>
</head>

<body oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false' onmouseup='document.selection.empty()' leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onmousedown="onMouseDown()" onKeyPress="if(window.event.keyCode==27)external.close();">
</body>
</html>
