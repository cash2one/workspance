<SCRIPT>
var ad_width = 300, ad_height = 188, speed = 5, hideafter = 0, isie = 0, objTimer;
var x, y, dx, dy, intervalMillisecond = 100000;
var oPopup = window.createPopup();
//objTimer = window.setInterval("getMsg()", intervalMillisecond); 
function getMsg(tu,msg)
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
                                        "                                                        <b>"+tu.substr(1)+"说：</b><br>"+msg+""    +"\n"+
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
getMsg("a","dfd")
</SCRIPT>
