function showExtwin(id,wtitle,bstr,w,h,maximizable,closable)
{
	var win=Ext.get(id)
	if (!win){
		//alert("aa")
			win = new Ext.Window({
			id:id,
			stateId:id,
			title: wtitle,
			width: w,
			height:h,
			shadow:false,
			//minimizable:true,
			closable : closable,//设置关闭按钮
			minWidth: w,//当前窗口所允许的最小高度像素数
			minHeight: h,//当前窗口所允许的最小宽度像素值
			layout: 'fit',
			plain:false,
			bodyStyle:'padding:0px;border:0px;',
			buttonAlign:'right',
			maximizable:maximizable,//设置为true将显示'最大化'工具按钮
			html:bstr
		});
		win.show();
	}else{
		var xMsg=document.getElementById(id);
	    xMsg.style.display = "";
		win.focus();
		win.show();
		//alert("bb")
		//alert(win.show())
	}
}
function gradon()
{
	var screenHeight = screen.height;
	var screenWidth = screen.width;
	var aa=document.getElementById("wrap").offsetHeight
	if (screenHeight<aa)
	{
		screenHeight=aa;
	}
	document.getElementById("page_cover").style.display="";
	document.getElementById("page_cover").style.width=(screenWidth-15)+"px";
	document.getElementById("page_cover").style.height=screenHeight+"px";
	document.getElementById("page_cover").className="t"
}

//我的电话确认
function shownottel()
{
	gradon();
	var bstrd="<IFRAME border=0 name='login' id='login' marginWidth=0 frameSpacing=0 marginHeight=0 src='recordservice/mytel.asp' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='400' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","请确认你的电话记录",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000000;
}
//客户电话确认
function showkhtel(com_id,comtel)
{
	gradon();
	var bstrd="<IFRAME border=0 name='login' id='login' marginWidth=0 frameSpacing=0 marginHeight=0 src='recordservice/khtel.asp?com_id="+com_id+"&comtel="+comtel+"' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='400' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","请确认你的电话记录",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000021;
}
//五星条件及流程
function showstar5open(com_id)
{
	gradon();
	var bstrd="<IFRAME border=0 name='login' id='login' marginWidth=0 frameSpacing=0 marginHeight=0 src='crmadmin/staropen.asp?com_id="+com_id+"' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='400' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","请确认你的电话记录",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000021;
}
function showgltel(tel,com_id)
{
	gradon();
	var bstrd="<IFRAME border=0 name='gltel' id='gltel' marginWidth=0 frameSpacing=0 marginHeight=0 src='recordservice/gltel.asp?telid="+tel+"&com_id="+com_id+"' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='300' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","请确认你的电话记录",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000021;
}