mui.init({
	swipeBack: true,
});

// H5 plus事件处理
var at = 100; // 默认动画时间
function plusReady() {
	compatibleAdjust();
}
if (window.plus) {
	plusReady();
} else {
	document.addEventListener('plusready', plusReady, false);
}
// DOMContentLoaded事件处理
var _domReady = false;
document.addEventListener('DOMContentLoaded', function() {
	_domReady = true;
	compatibleAdjust();
}, false);
// 兼容性样式调整
var _adjust = false;

function compatibleAdjust() {
		if (_adjust || !window.plus || !_domReady) {
			return;
		}
		_adjust = true;
		// iOS平台使用div的滚动条
		if ("iOS" == plus.os.name) {
			at = 300;
			//document.getElementById('content').className='scontent';
		}
		// 关闭启动界面
		setTimeout(function() {
			plus.navigator.closeSplashscreen();
		}, 500);
	}
	// 处理点击事件
var _openw = null;

function clicked(id) {
	if (_openw) {
		return;
	}
	_openw = preate[id];
	if (_openw) {
		if (_openw.showded) {
			_openw.show('auto', at);
		} else {
			_openw.show('slide-in-right', at);
			_openw.showded = true;
		}
		_openw = null;
	} else {
		var wa = plus.nativeUI.showWaiting();
		_openw = plus.webview.create(id, id, {
			scrollIndicator: 'none',
			scalable: false
		}, {
			preate: true
		});
		preate[id] = _openw;
		_openw.addEventListener('loaded', function() { //叶面加载完成后才显示
			setTimeout(function() { //延后显示避免低端机上闪屏
				wa.close();
				_openw.show('slide-in-right', at);
				_openw.showded = true;
				_openw = null;
			}, 500);
		}, false);
		_openw.addEventListener('close', function() { //页面关闭后可再次打开
			_openw = null;
			preate[id] && (preate[id] = null); //兼容窗口的关闭
		}, false);
	}
}
// 空函数
function shield() {
	return false;
}
document.addEventListener('touchstart', shield, false); //取消浏览器的所有事件，使得active的样式在手机上正常生效
document.oncontextmenu = shield; //屏蔽选择函数
//首次加载
function loadappbody(){
	var netstatus = 0;
	var info = plus.push.getClientInfo();
	clientid=info.clientid;
	mui.get("http://app.zz91.com/logininfo.html?appid="+clientid, function(data) {
		if (data=="0"){
			mui.get("http://app.zz91.com/login/", function(data) {
					document.body.innerHTML = data;
					return false;
			});
		}else{
			company_id=data;
			mui.get("http://app.zz91.com/appbody.html", function(data) {
				document.body.innerHTML = data;
				gotourl(nowurl,"index");
				netstatus = 1
			});
		}
	});
}
//重新加载
function reloadapp() {
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	loadcheck("http://app.zz91.com/appbody.html");
}

function loadcheck(url) {
	var netweb = null;
	netweb = new XMLHttpRequest();
	var protocol = /^([\w-]+:)\/\//.test(url) ? RegExp.$1 : window.location.protocol;
	netweb.onreadystatechange = function() {
		switch (netweb.readyState) {
			case 4:
				if ((netweb.status >= 200 && netweb.status < 300) || netweb.status === 304 || (netweb.status === 0 && protocol === 'file:')) {
					loadappbody();
				} else {
					if (confirm('网络不给力,确定要退出吗?')) {
						plus.navigator.closeSplashscreen();
						plus.runtime.quit();
					}
				}
				break;
			default:
				break;
		}
		if (nWaiting) {
			nWaiting.close();
		}
	}
	netweb.open("GET", url);
	netweb.send();
}