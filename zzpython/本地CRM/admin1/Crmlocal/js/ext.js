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
			closable : closable,//���ùرհ�ť
			minWidth: w,//��ǰ�������������С�߶�������
			minHeight: h,//��ǰ�������������С�������ֵ
			layout: 'fit',
			plain:false,
			bodyStyle:'padding:0px;border:0px;',
			buttonAlign:'right',
			maximizable:maximizable,//����Ϊtrue����ʾ'���'���߰�ť
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

//�ҵĵ绰ȷ��
function shownottel()
{
	gradon();
	var bstrd="<IFRAME border=0 name='login' id='login' marginWidth=0 frameSpacing=0 marginHeight=0 src='recordservice/mytel.asp' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='400' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","��ȷ����ĵ绰��¼",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000000;
}
//�ͻ��绰ȷ��
function showkhtel(com_id,comtel)
{
	gradon();
	var bstrd="<IFRAME border=0 name='login' id='login' marginWidth=0 frameSpacing=0 marginHeight=0 src='recordservice/khtel.asp?com_id="+com_id+"&comtel="+comtel+"' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='400' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","��ȷ����ĵ绰��¼",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000021;
}
//��������������
function showstar5open(com_id)
{
	gradon();
	var bstrd="<IFRAME border=0 name='login' id='login' marginWidth=0 frameSpacing=0 marginHeight=0 src='crmadmin/staropen.asp?com_id="+com_id+"' onfocus='bScroll=true;'   frameBorder=0 noResize width='500' scrolling=auto  height='400' vspale='0'></IFRAME>"
	//showExtwin("loginkuang","��ȷ����ĵ绰��¼",bstrd,400,320,false,false);
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
	//showExtwin("loginkuang","��ȷ����ĵ绰��¼",bstrd,400,320,false,false);
	var screenWidth = screen.width;
	document.getElementById("alertmsg").style.position="absolute";
	//document.getElementById("alertmsg").style.top=100+"px"
	document.getElementById("alertmsg").style.left=(screenWidth/2-300)+"px";
	document.getElementById("alertmsg").style.display="";
	document.getElementById("alertmsg").innerHTML=bstrd;
	var loginkuang=document.getElementById("alertmsg");
	loginkuang.style.zIndex=20000021;
}