// 空函数
function shield() {
	return false;
}
document.addEventListener('touchstart',shield,false);//取消浏览器的所有事件，使得active的样式在手机上正常生效
document.oncontextmenu=shield;//屏蔽选择函数
var _adjust = false;
var _domReady = false;
function plusReady() {
	if (window.plus){
		compatibleAdjust();
	}else{
		setTimeout(function() {
			plusReady();
		}, 100);
	}
}

function compatibleAdjust() {
	if (_adjust || !window.plus || !_domReady) {
		return;
	}
	_adjust = true;
	// 关闭启动界面
	reloadiosapp();
	loaddata(nowherf);
}


document.addEventListener('DOMContentLoaded',function(){
	// 关闭启动界面
	_domReady = true;
	setTimeout(function() {
		if (window.plus){
			plusReady();
		}
	}, 1000);
},false);
//判断网络环境是否正常
document.addEventListener("netchange", function() {
	if (window.plus){
		reloadiosapp()
	}
}, false);

if(window.plus){
	plusReady();
}else{
	document.addEventListener('plusready',plusReady,false);
}
