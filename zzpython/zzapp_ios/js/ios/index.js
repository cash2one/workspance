var _adjust = false;
var _domReady = false;
var appsystem="";
var visitoncode="iOS";
var clientid;
function plusReady() {
	if (window.plus){
		compatibleAdjust();
	}else{
		setTimeout(function() {
			plusReady();
		}, 1500);
	}
}
function loadjs(d, t){
	var r = d.createElement(t),
		s = d.getElementsByTagName(t)[0];
	r.async = false;
	r.src = 'http://app.zz91.com/js/ios/function.js?' + (new Date()).getTime().toString();
	s.parentNode.insertBefore(r, s);
}

function compatibleAdjust() {
	if (!window.plus) {
		return;
	}
	_adjust = true;
	// 关闭启动界面
	setTimeout(function() {
		plus.navigator.closeSplashscreen();
		clientid = "No" + (new Date()).getTime().toString();
		reloadiosapp();
		
		visitoncode=plus.runtime.version;
		var info = plus.push.getClientInfo();
		if (info){
			if (info.clientid) {
				clientid = info.clientid;
			}else{
				clientid = "No" + (new Date()).getTime().toString();
			}
		}else{
			clientid=plus.device.uuid;
		}
		
		//loadjs(document, "script");
		$(document).ready(function(e) {
			loadfirsthtml();
		});
		
		plus.storage.setItem("clientid",clientid);
	}, 1000);
}


document.addEventListener('DOMContentLoaded',function(){
	// 关闭启动界面
	_domReady = true;
	setTimeout(function() {
		if (window.plus){
			plus.navigator.closeSplashscreen();
			plusReady();
			//reloadiosapp();
		}
	}, 1000);
},false);
//判断网络环境是否正常
document.addEventListener("netchange", function() {
	if (window.plus){
		reloadiosapp()
	}
}, false);

document.addEventListener( "plusready", function(){
	plus.push.addEventListener("click", function(msg) {
		if (msgchickurl==""){
			gotourl(msg.payload,"messages");
			msgchickurl=msg.payload;
		}
		if (msg.payload!=msgchickurl){
			msgchickurl="";
		}
	}, false);
	// 监听在线消息事件
	plus.push.addEventListener("receive", function(msg) {
		if (msg.aps) { // Apple APNS message
	
		} else {
	
		}
	}, false);
});

if(window.plus){
	plusReady();
}else{
	document.addEventListener('plusready',plusReady,false);
}
