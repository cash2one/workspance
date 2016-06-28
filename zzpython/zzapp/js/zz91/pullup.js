function closepulldiv() {
	var objpulldiv = document.getElementById("pulldiv"+nowwin.toString());
	if (objpulldiv!=null) {
		objpulldiv.style.display = "none";
	}
}
function loaded(url) {
	pullUpEl = document.getElementById('pulldiv'+nowwin.toString());	
	pullUpOffset = pullUpEl.offsetHeight;
	//alert(pullUpOffset)
	myScroll = new iScroll("dcontent"+nowwin.toString(), {
		useTransition: false,
		topOffset: pullUpOffset,
		vScrollbar:true,
		onRefresh: function () {
			//if (pullUpEl.className.match('loading')) {
				//pullUpEl.className = '';
				//pullUpEl.querySelector('.pulldiv').innerHTML = 'Pull up to load more...';
			//}
		},
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5)) {
				//pullUpEl.className = 'flip';
				//pullUpEl.querySelector('.pulldiv').innerHTML = 'Release to refresh...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5)) {
				//pullUpEl.className = '';
				//pullUpEl.querySelector('.pulldiv').innerHTML = 'Pull up to load more...';
				this.maxScrollY = pullUpOffset;
			}
			document.getElementById("appbottom").innerHTML=this.y;
			//this.topOffset=this.y;
			//this.y=this.y+2;
			//this.maxScrollY = 100000;
			
			//alert(this.maxScrollY)
		},
		onScrollEnd: function () {
			//if (pullUpEl.className.match('flip')) {
				//pullUpEl.className = 'loading';
				//pullUpEl.querySelector('.pulldiv').innerHTML = 'Loading...';				
				//pullUpAction();	// Execute custom function (ajax call?)
				//alert(url)
				//alert(this.y)
				//this.maxScrollY = pullUpOffset;
				//this.topOffset=this.y;
				//this.y=this.y;
				loadMore(url);
				//alert(url)
				myScroll.refresh();
				//this.disable()
			//}
		}
	});
	//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	setTimeout(function () { document.getElementById("dcontent"+nowwin.toString()).style.left = '0'; }, 800);
}
//document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
(function($) {
	var startX = 0,
		startY = 0;
	//touchstart事件
	function touchSatrtFunc(evt) {
		try {
			//evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等

			var touch = evt.touches[0]; //获取第一个触点
			var x = Number(touch.pageX); //页面触点X坐标
			var y = Number(touch.pageY); //页面触点Y坐标
			//记录触点初始位置
			startX = x;
			startY = y;

			//var text = 'TouchStart事件触发：（' + x + ', ' + y + '）';
			//document.getElementById("result").innerHTML = text;
		} catch (e) {
			//alert('touchSatrtFunc：' + e.message);
		}
	}

	//touchmove事件，这个事件无法获取坐标
	function touchMoveFunc(evt) {
		try {
			//evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
			var touch = evt.touches[0]; //获取第一个触点
			var x = Number(touch.pageX); //页面触点X坐标
			var y = Number(touch.pageY); //页面触点Y坐标

			var text = 'TouchMove事件触发：（' + x + ', ' + y + '）';
			//判断滑动方向
			if ((x - startX > 10 || x - startX < -10)) {
				text += '<br/>左右滑动';
			}
			if (y - startY != 0) {
				text += '<br/>上下滑动';
				
				//alert(2);
			}
			//document.getElementById("appbottom").innerHTML=$(window).scrollTop()
			winarr[nowwin][2]=$(window).scrollTop();
			
			//document.getElementById("result").innerHTML = text;
		} catch (e) {
			//alert('touchMoveFunc：' + e.message);
		}
	}

	//touchend事件
	function touchEndFunc(evt) {
		try {
			//evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等
			//scrollpp();
			//var text = 'TouchEnd事件触发';
			//document.getElementById("result").innerHTML = text;
		} catch (e) {
			//alert('touchEndFunc：' + e.message);
		}
	}

	//绑定事件
	function bindEvent() {
		document.addEventListener('touchstart', touchSatrtFunc, false);
		document.addEventListener('touchmove', touchMoveFunc, false);
		document.addEventListener('touchend', touchEndFunc, false);
	}

	//判断是否支持触摸事件
	function isTouchDevice() {
		//document.getElementById("version").innerHTML = navigator.appVersion;
		try {
			document.createEvent("TouchEvent");
			//alert("支持TouchEvent事件！");
			bindEvent(); //绑定事件
		} catch (e) {
			//alert("不支持TouchEvent事件！" + e.message);
		}
	}
	//window.onload = isTouchDevice;
	function isDragmore(){

		window.addEventListener('dragstart', function(event) {
			var detail = event.detail;
			var direction = detail.direction;
			var angle = detail.angle;
			
			//alert(tragfx)
//			if (direction === 'up' && tragfx=="hide") {
//				//document.body.innerHTML=event.detail.deltaX
//				//scrollpp();
//			}
			if (direction === 'left') {
				//offCanvasShow();
			}
			if (direction === 'right') {
				winDrag=true;
				//offCanvasHide();
				//alert(1);
			}
			//alert(winDrag)
			winDrag=true;
			var arrtop=document.getElementById("arrtop");
			if (arrtop){
				var scrollY = window.scrollY;
				if (scrollY>200){
					arrtop.style.display="";
				}else{
					arrtop.style.display="none";
				}
			}
			
		});
		window.addEventListener('drag', function(event,evt) {
			if (winDrag) {
				//if (!sliderRequestAnimationFrame) {
					//updateTranslate();
				//}
				//scrollpp();
				translateX = event.detail.deltaY * winfactor;
				//document.getElementById("appbottom").innerHTML=translateX
				//winarr[nowwin][2]=$(window).scrollTop();
				//event.detail.gesture.preventDefault();
			}
			var arrtop=document.getElementById("arrtop");
			if (arrtop){
				var scrollY = window.scrollY;
				if (scrollY>200){
					arrtop.style.display="";
				}else{
					arrtop.style.display="none";
				}
			}
			
		});
	
		window.addEventListener('dragend', function(event) {
			var detail = event.detail;
			var direction = detail.direction;
			var angle = detail.angle;
			winDrag=false;
			//document.getElementById("apptop").innerHTML=window.scrollY;
			
			if (direction === 'up' && tragfx=="hide") {
				//document.body.innerHTML=event.detail.deltaX
				//scrollpp();
				var arrtop=document.getElementById("arrtop");
				if (arrtop){
					var scrollY = window.scrollY;
					if (scrollY>200){
						arrtop.style.display="";
					}else{
						arrtop.style.display="none";
					}
				}
			}
			if (nWaiting) {
				nWaiting.close();
			}
			if (winDrag) {
				//endDraging(false, event.detail);
			}
		});
	}
	window.onload = isDragmore;
})(mui);

function scrolltop(){
	mui.scrollTo(0,500);
	var arrtop=document.getElementById("arrtop");
	if (arrtop){
		arrtop.style.display="none";
	}
}
