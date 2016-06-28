document.write('<iframe id=cf name=cf frameborder=1 scrolling=auto src=blank.asp style=display:none;position:absolute;z-index:100></iframe>');
document.onclick=hideop;
function closeif(sImg,bOpenBound,sFld1,url,sFld2,sCallback)
{
    var fld1,fld2,url;
    var cf=document.getElementById("cf");
	cf.src=url;
	var wcf=window.frames.cf;
	var oImg=document.getElementById(sImg);
	if(!oImg){alert("控制对象不存在！");return;}
	if(!sFld1){alert("输入控件未指定！");return;}
	fld1=document.getElementById(sFld1);
	if(!fld1){alert("输入控件不存在！");return;}
	if(fld1.tagName!="INPUT"||fld1.type!="text"){alert("输入控件类型错误！");return;}
	if(sFld2)
	{
		fld2=document.getElementById(sFld2);
		if(!fld2){alert("参考控件不存在！");return;}
		if(fld2.tagName!="INPUT"||fld2.type!="text"){alert("参考控件类型错误！");return;}
	}
	//if(!wcf.bCalLoaded){alert("日历未成功装载！请刷新页面！");return;}
	if(cf.style.display=="block"){cf.style.display="none";return;}
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){eT+=p.offsetTop;eL+=p.offsetLeft;p=p.offsetParent;}
	cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	//cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	//cf.style.top=(screen.width - 600)/2;
	cf.style.left=(screen.height - 600)/2;
	cf.style.display="block";
	cf.style.width=600;
	cf.style.height=200;
	//wcf.openbound=bOpenBound;
	//wcf.fld1=fld1;
	//wcf.fld2=fld2;
	//wcf.callback=sCallback;
}
function hideop()
{
    var cf=document.getElementById("cf");
	cf.style.display="none";
	var cff=document.getElementById("CalFrame");
	cff.style.display="none";
}