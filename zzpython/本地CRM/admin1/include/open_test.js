document.onclick=hideop;
function closeif(sImg,bOpenBound,sFld1,url,wid,hed)
{
    var fld1,fld2,url,wid,hed;
    var cf=document.getElementById("cf");
	cf.src=url;
	var wcf=window.frames.cf;
	var oImg=document.getElementById(sImg);
	if(!oImg){alert("控制对象不存在！");return;}
	if(!sFld1){alert("输入控件未指定！");return;}
	fld1=document.getElementById(sFld1);
	if(!fld1){alert("输入控件不存在！");return;}
	if(fld1.tagName!="INPUT"||fld1.type!="text"){alert("输入控件类型错误！");return;}
	//if(sFld2)
	//{
		//fld2=document.getElementById(sFld2);
		//if(!fld2){alert("参考控件不存在！");return;}
		//if(fld2.tagName!="INPUT"||fld2.type!="text"){alert("参考控件类型错误！");return;}
	//}
	//if(!wcf.bCalLoaded){alert("日历未成功装载！请刷新页面！");return;}
	if(oImg.style.display=="block"){oImg.style.display="none";return;}
	if(cf.style.display=="block"){cf.style.display="none";return;}
	
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	oImg.style.display="block";
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){eT+=p.offsetTop;eL+=p.offsetLeft;p=p.offsetParent;}
	//cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	//cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	
	
	cf.style.display="block";
	//alert (eH)
	cf.style.top=p.offsetLeft+eH;
	
	//cf.style.left=(screen.height - 600)/2;
	//cf.style.left=document.body.scrollLeft
	//cf.style.display="block";
	if(wid!="")
	{
		cf.style.width=wid;
	}else{
		cf.style.width=600;
	}
	if(hed!="")
	{
		cf.style.height=hed;
	}else{
		cf.style.height=300;
	}
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
	var cimage=document.getElementById("mxqd");
	cimage.style.display="none";
}
//'''''''''''''''''''''''''''''''
var isNav, isIE
var offsetX, offsetY
var selectedObj 
var mglasswidth = 32
var mglassheight = 32
var mglassleft = 150
var mglasstop = 110
if (parseInt(navigator.appVersion) >= 4) {
	if (navigator.appName == "Netscape") {
		isNav = true
	} else {
		isIE = true
	}
}

function setZIndex(obj, zOrder) {
	obj.zIndex = zOrder
}
function shiftTo(obj, x, y) {
	if (isNav) {
		obj.moveTo(x,y)
	} else {
		obj.pixelLeft = x
		obj.pixelTop = y
	}	
}

function setSelectedElem(evt) {
	if (isNav) {

		var testObj

		var clickX = evt.pageX

		var clickY = evt.pageY

		for (var i = document.layers.length - 1; i >= 0; i--) {

			testObj = document.layers[i]

			if ((clickX > testObj.left) && 

				(clickX < testObj.left + testObj.clip.width) && 

				(clickY > testObj.top) && 

				(clickY < testObj.top + testObj.clip.height)) {

					selectedObj = testObj

					setZIndex(selectedObj, 100)

					return

			}

		}

	} else {

		var imgObj = window.event.srcElement

		if (imgObj.parentElement.id.indexOf("mglass") != -1) {

			selectedObj = imgObj.parentElement.style

			setZIndex(selectedObj,100)

			return

		}

	}

	selectedObj = null

	return

}



function dragIt(evt) {

	if (selectedObj) {

		if (isNav) {

			shiftTo(selectedObj, (evt.pageX - offsetX), (evt.pageY - offsetY))

		} else {

			shiftTo(selectedObj, (window.event.clientX - offsetX), (window.event.clientY - offsetY))

			return false

		}

	}

}



function engage(evt) {

	setSelectedElem(evt)

	if (selectedObj) {

		if (isNav) {

			offsetX = evt.pageX - selectedObj.left

			offsetY = evt.pageY - selectedObj.top

		} else {

			offsetX = window.event.offsetX

			offsetY = window.event.offsetY

		}

	}

	return false

}



function release(evt) {

	if (selectedObj) {

		setZIndex(selectedObj, 0)

		selectedObj = null

	}

}



function setNavEventCapture() {

	if (isNav) {

		document.captureEvents(Event.MOUSEDOWN | Event.MOUSEMOVE | Event.MOUSEUP)

	}

}
function init() {
	if (document.layers) {
		//document.mglass.left=mglassleft
		//document.mglass.top=mglasstop
	}
	if (document.all) {
		//document.all.mglass.style.posLeft=mglassleft
		//document.all.mglass.style.posTop=mglasstop
	}
	document.onmousedown = engage
	document.onmousemove = dragIt
	document.onmouseup = release
}
