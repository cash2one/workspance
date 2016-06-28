var nowurl = "/index.html";
var nowherf = "/index.html";
var win = 1;
var winarr = new Array();
var indexwin = new Array();
var winlenght = 20;
var nowwin = 1;
var wintype = "";
var nowwintype = "index";
//拖动
var winDrag = false;
var winfactor = 1;
//右边菜单
var tragfx = "hide";
//设备唯一标示
var clientid = "";
var company_id = 0;

//小窗口标示
var minwindow = null

//经度
var latitude = 0;
//纬度
var longitude = 0;
//
var netstatus = 0
	//
var nowprovice = "";
var nowcity = "";
var district = "";

var nWaiting;
//app系统
var appsystem;
var contactperson = "";

//出错重试次数
var reloadnum = 0;

//窗口属性  1  index.html 2  blank.html
var winflag = 1;

//时候返回
var isback = 1;
var first = null;
//版本信息
var visitoncode = "";

var blankpage=2;

var winInterval=600000;
var Intent=null,BitmapFactory=null;
var main=null;
(function($, window) {
	/**
	 * 后退(5+关闭当前窗口)
	 */
	$.back = function() {
		var isBack = true;
		isback = 1;
		var callback = false;
		if (minwindow) {
			closeoverlay();
			closeoverlayimg();
			return false;
		}
		if (winflag == 2) {
			if (winarr.length > 1) {
				backurl = winarr[winarr.length - 2];
				isback = 2;
				loaddata(backurl);
				winarr.pop();
				isback = 1;
				isBack = false;
			}
		} else {
			var backwin = indexwin[indexwin.length - 2];
			if (indexwin.length > 1) {
				isback = 2;
				gotourl(backwin[1], backwin[0])
				isback = 1;
				plus.storage.removeItem(backwin[1]);
				indexwin.pop();
			} else {
				plus.ui.confirm('', function(i) {
					if (i == 0) {
						plus.navigator.closeSplashscreen();
						plus.runtime.quit();
					}
				}, '确认退出？');
				return false;
				//				if (!first) {
				//					first = new Date().getTime();
				//					mui.toast('再按一次退出应用');
				//					setTimeout(function() {
				//						first = null;
				//					}, 1000);
				//				} else {
				//					if (new Date().getTime() - first < 1000) {
				//						plus.runtime.quit();
				//					}
				//				}
			}
			isBack = false;
		}
		
		if (!isBack) {
			return;
		}
		closewindow();
	};
})(mui, window);
(function(w) {
	var _dout_ = null,
		_dcontent_ = null;
	// 输出内容
	w.outSet = function(s) {
		//_dout_.innerHTML=s+"<br/>";
		//alert(s)
		//(0==_dout_.scrollTop)&&(_dout_.scrollTop=1);//在iOS8存在不滚动的现象
	};
	// 输出行内容
	w.outLine = function(s) {
		alert(s)
			//_dout_.innerHTML+=s+"<br/>";
			//(0==_dout_.scrollTop)&&(_dout_.scrollTop=1);//在iOS8存在不滚动的现象
	};
})(window);
//退出
function loginout() {
		var btnArray = ['确定', '取消'];
		mui.confirm('确定要退出吗？', '提示', btnArray, function(e) {
			if (e.index == 0) {
				plus.runtime.quit();
			}
		});
	}
	//情况缓存

function getcachefunction() {
		plus.cache.calculate(function(size) {
			//alert( "Application cache size: " + size + " byte!" );
		});
		plus.cache.clear(function() {
			plus.ui.alert("缓存已经清除成功!");
			var cachediv = document.getElementById("cachesize");
			if (cachediv) {
				cachediv.innerHTML = "0KB";
			}
		});
	}
	//却换账号

function changeaccount() {
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	var arg = "company_id=" + company_id;
	arg += "&clientid=" + clientid;
	mui.get("http://app.zz91.com/changeaccount.html?" + arg, '', function(data) {
		var j = JSON.parse(data);
		company_id = 0;
		plus.storage.setItem("company_id","0");
		if (j.changeflag) {
			if (nWaiting) {
				nWaiting.close();
			}
			var wobj = plus.webview.currentWebview();
			if (wobj) {
				var opener = wobj.opener();
				if (opener) {
					opener.evalJS('gotourl("/login/", "login");');
				}
			}
			wobj.close();
		}
		if (nWaiting) {
			nWaiting.close();
		}
	}, function(data) {
		plus.ui.alert("系统错误！请稍后重试.")
	});
	return false;
}

function firstlogin() {
		var wobj = plus.webview.currentWebview();
		if (wobj) {
			var opener = wobj.opener();
			if (opener) {
				opener.evalJS('gotourl("/login/", "login");');
			}
		}
		wobj.close();
	}
	//关闭窗口

function closewindow() {
	closeoverlay()
	var ws = plus.webview.currentWebview();
	ws.close();
}
var zzajax = function(method, url, arg, successCallback, errorCallback) {
	var xhr = new XMLHttpRequest();
	var protocol = /^([\w-]+:)\/\//.test(url) ? RegExp.$1 : window.location.protocol;
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4) {
			if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304 || (xhr.status === 0 && protocol === 'file:')) {
				successCallback && successCallback(xhr.responseText);
			} else {
				errorCallback && errorCallback();
			}
		}
	};
	xhr.open(method, url, true);
	if (method == "post") {
		xhr.send(arg);
	} else {
		xhr.send();
	}
};
//检查版本
var serverurl = "http://app.zz91.com/js/update.json?t=" + Math.ceil(new Date / 3600000),
	keyUpdate = "updateCheck", //取消升级键名
	keyAbort = "updateAbort", //忽略版本键名
	checkInterval = 864000, //升级检查间隔，单位为ms,7天为7*24*60*60*1000=60480000, 如果每次启动需要检查设置值为0
	dir = null;
var sjcheck=false;
function checkUpdate() {
		if (sjcheck){
			return ;
		}
		sjcheck=true
		// 判断升级检测是否过期
		var lastcheck = plus.storage.getItem(keyUpdate);
		if (lastcheck) {
			if (document.getElementById("visiton")){
				
			}else{
				var dc = parseInt(lastcheck);
				var dn = (new Date()).getTime();
				if (dn - dc < checkInterval) { // 未超过上次升级检测间隔，不需要进行升级检查
					if (document.getElementById("visiton")){
						if (appsystem=="iOS"){
							plus.ui.alert("已是最新版本");
						}else{
							mui.toast('已是最新版本');
						}
					}
					return;
				}else{
					sjcheck=false;
				}
			}
			
			// 取消已过期，删除取消标记
			plus.storage.removeItem(keyUpdate);
		}
		zzajax("get", serverurl, '', function(data) {
			var j = JSON.parse(data);
			checkUpdateData(j)
		}, '');
}
	/**
	 * 检查升级数据
	 */
function checkUpdateData(j) {
		var curVer = plus.runtime.version,
			inf = j[plus.os.name];
		if (inf) {
			var srvVer = inf.version;
			// 判断是否需要升级
			if (compareVersion(curVer, srvVer)) {
				// 提示用户是否升级
				plus.ui.confirm(inf.note, function(i) {
					if (0 == i) {
						minwindow = true;
						$("#downloadwindow").lightbox_me({
							overlaySpeed: 0,
							lightboxSpeed: 0,
							centered: true,
							onLoad: function() {
								minwindow = "downloadwindow";
							},
							onClose: function() {
								minwindow = null;
							}
						});
						downloader.down(inf.url, srvVer);
						plus.storage.setItem(keyUpdate, (new Date()).getTime().toString());
					} else {
						plus.storage.setItem(keyUpdate, (new Date()).getTime().toString());
					}
				}, "建议您更新最新版本", ["立即更新", "取　　消"]);
			} else {
				if (document.getElementById("visiton")){
					if (appsystem=="iOS"){
						plus.ui.alert("已是最新版本");
					}else{
						mui.toast('已是最新版本');
					}
				}
				//mui.toast('已是最新版本');
			}
		}
	}
	/**
	 * 比较版本大小，如果新版本nv大于旧版本ov则返回true，否则返回false
	 * @param {String} ov
	 * @param {String} nv
	 * @return {Boolean}
	 */

function compareVersion(ov, nv) {
	if (!ov || !nv || ov == "" || nv == "") {
		return false;
	}
	var b = false,
		ova = ov.split(".", 4),
		nva = nv.split(".", 4);
	for (var i = 0; i < ova.length && i < nva.length; i++) {
		var so = ova[i],
			no = parseInt(so),
			sn = nva[i],
			nn = parseInt(sn);
		if (nn > no || sn.length > so.length) {
			return true;
		} else if (nn < no) {
			return false;
		}
	}
	if (nva.length > ova.length && 0 == nv.indexOf(ov)) {
		return true;
	}
}

/**
 * 更新分享服务
 */
function updateSerivces() {
		plus.share.getServices(function(s) {
			shares = {};
			for (var i in s) {
				var t = s[i];
				shares[t.id] = t;
			}
		}, function(e) {
			outSet("获取分享服务列表失败：" + e.message);
		});
	}
	/**
	 * 分享操作
	 * @param {String} id
	 */

function shareAction(id, ex) {
		var s = null;
		//shareClose();
		outSet("分享操作：");
		if (!id || !(s = shares[id])) {
			outLine("无效的分享服务！");
			return;
		}
		if (s.authenticated) {
			outLine("---已授权---");
			shareMessage(s, ex);
		} else {
			outLine("---未授权---");
			s.authorize(function() {
				shareMessage(s, ex);
			}, function(e) {
				outLine("认证授权失败：" + e.code + " - " + e.message);
			});
		}
	}
	/**
	 * 发送分享消息
	 * @param {plus.share.ShareService} s
	 */

function shareMessage(s, ex) {
		var msg = {
			content: "aa",
			extra: {
				scene: ex
			}
		};
		if (bhref) {
			msg.href = sharehref.value;
			if (sharehrefTitle && sharehrefTitle.value != "") {
				msg.title = sharehrefTitle.value;
			}
			if (sharehrefDes && sharehrefDes.value != "") {
				msg.content = sharehrefDes.value;
			}
			msg.thumbs = ["_www/logo.png"];
		} else {
			if (pic && pic.realUrl) {
				msg.pictures = [pic.realUrl];
			}
		}
		outLine(JSON.stringify(msg));
		s.send(msg, function() {
			outLine("分享到\"" + s.description + "\"成功！ ");
		}, function(e) {
			outLine("分享到\"" + s.description + "\"失败: " + e.code + " - " + e.message);
		});
	}
	// 分享链接

function shareHref() {
		updateSerivces();
		bhref = true;
		sinaweibo.style.display = "none";
		tencentweibo.style.display = "none";

		popover.style.zIndex = "10000";
		menu.style.zIndex = "11000";
		newsnav.style.zIndex = "12000";
		menu.style.display = "block";
		setTimeout(function() {
			popover.style.opacity = "0.5";
			menu.style.opacity = "1";
			menu.style.webkitTransform = "translateY(0)";
		}, 0);
		popover.style.display = "block";
	}
	// 取消分享

function shareHide() {
		setTimeout(function() {
			popover.style.display = "none";
			menu.style.display = "none";
		}, 500);
		popover.style.opacity = "0";
		menu.style.opacity = "0";
		menu.style.webkitTransform = "translateY(100%)";
	}
	// 关闭分享（无动画）

function shareClose() {
		popover.style.display = "none";
		menu.style.display = "none";
		popover.style.opacity = "0";
		menu.style.opacity = "0";
		menu.style.webkitTransform = "translateY(100%)";
	}
	/**
	 * 解除所有分享服务的授权
	 */

function cancelAuth() {
	try {
		outSet("解除授权：");
		for (var i in shares) {
			var s = shares[i];
			if (s.authenticated) {
				outLine("取消\"" + s.description + "\"");
			}
			s.forbid();
		}
		// 取消授权后需要更新服务列表
		updateSerivces();
		outLine("操作成功！");
	} catch (e) {
		alert(e);
	}
}
function loadmorep(obj,url){
	if (url.indexOf("?")<0){
		url=url+"?page="+blankpage.toString()+"&t="+(new Date()).getTime().toString()+"&company_id="+company_id.toString()+"&clientid="+clientid+"&appsystem="+appsystem+"&visitoncode="+visitoncode;
	}else{
		url=url+"&page="+blankpage.toString()+"&t="+(new Date()).getTime().toString()+"&company_id="+company_id.toString()+"&clientid="+clientid+"&appsystem="+appsystem+"&visitoncode="+visitoncode;
	}
	obj.style.display="";
	obj.innerHTML="正在加载中...";
	if (url!="" && url!=null && (obj.style.display=="" || obj.style.display=="block")){
		zzajax("get","http://app.zz91.com/" + url, '',function(data) {
			if (data!=""){
				var newNode = document.createElement("div");
				newNode.innerHTML = data;
				obj.parentNode.insertBefore(newNode, obj);
				if (obj){
					blankpage += 1;
					obj.innerHTML="<span class='mui-icon mui-icon-down'></span><span>点此加载更多</span>";
				}
			}else{
				obj.style.display="none";
			}
		},function(){
			obj.innerHTML="<span class='mui-icon mui-icon-down'></span><span>点此加载更多</span>";
		});
	}
}
document.addEventListener( "plusscrollbottom", function(){
	var pulldiv=document.getElementById("pulldiv");
	if (pulldiv){
		loadmorep(pulldiv,pulldiv.title)
	}
}, false );
//iOS去掉支付环节
function iOSclosecontent(){
	var zhifufangshi=document.querySelector("#zhifufangshi");
	if (zhifufangshi){
		if (appsystem=="iOS"){
			zhifufangshi.style.display="none";
		}
	}
	var zhifufangshi1=document.getElementById("zhifufangshi1");
	if (zhifufangshi1){
		if (appsystem=="iOS"){
			zhifufangshi1.style.display="none";
		}
	}
	var zhifufangshi2=document.getElementById("zhifufangshi2");
	if (zhifufangshi2){
		if (appsystem=="iOS"){
			zhifufangshi2.style.display="none";
		}
	}
}
//交易中心列表导航滚动js
function showtrade(objname, h) {
	var obj = objname.getElementsByTagName("div")[0];
	var pobj = objname.parentNode.parentNode.getElementsByClassName("tradelistnav");
	for (var i = 0; i < pobj.length; i++) {
		if (pobj[i].style.display == "block" && obj != pobj[i]) {
			pobj[i].style.display = "none";
		}
	}

	if (obj.style.display == "none") {
		obj.style.display = "block";
	} else {
		obj.style.display = "none";
	}
}
function UTF8UrlEncode(input){    
        var output = "";    
        var currentChar = '';    
        for(var counter = 0; counter < input.length; counter++){    
            currentChar = input.charCodeAt(counter);    
            if((0 <= currentChar) && (currentChar <= 127))    
                output = output + UTF8UrlEncodeChar(currentChar);    
            else   
                output = output + encodeURIComponent(input.charAt(counter));     
        }     
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
    } 
function UTF8UrlEncodeChar(input){    
	if(input <= 0x7F) return "%" + input.toString(16);    
	var leadByte = 0xFF80;    
	var hexString = "";    
	var leadByteSpace = 5;    
	while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    
		hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    
		leadByte = (leadByte >> 1);    
		leadByteSpace--;    
		input = input >> 6;    
	}    
	return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    
}
isJson = function(obj) {
	var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
	return isjson;
};

