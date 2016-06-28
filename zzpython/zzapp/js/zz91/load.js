mui.init();
// H5 plus事件处理
var at = 100; // 默认动画时间
var nWaiting1;
function plusReady() {
	if (window.plus){
		compatibleAdjust();
	}else{
		setTimeout(function() {
			plusReady();
		}, 1500);
	}
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
	
	if ( plus.os.name == "Android" ) {
		// 导入要用到的类对象
		Intent = plus.android.importClass("android.content.Intent");
		BitmapFactory = plus.android.importClass("android.graphics.BitmapFactory");
		// 获取主Activity
		main = plus.android.runtimeMainActivity();
	}
	// 关闭启动界面
	setTimeout(function() {
		plus.navigator.closeSplashscreen();
		getcliendid();
		
		if (nWaiting) {
			nWaiting.close();
		}
		
		var netstats = getnetstats();
		var pcontent = document.getElementById("pcontent");
		var mainbody = document.getElementById("mainbody");
		
		
		if (netstats != "未连接网络") {
			
			if (pcontent && mainbody) {
				pcontent.style.display = "none";
				mainbody.style.display = "";
			}
		} else {
			if (pcontent && mainbody) {
				pcontent.style.display = "";
				mainbody.style.display = "none";
			}
		}
	}, 1000);
}
var n = 1;

function getcliendid() {
		if (nWaiting) {
			nWaiting.close();
		}
		var waiter=document.getElementById("waiter")
		if (waiter){
			waiter.innerHTML=n;
		}
		visitoncode=plus.runtime.version;
		if (clientid == "" || clientid == "null" || !clientid) {
			var info = plus.push.getClientInfo();
			if (info.token) {
				clientid = info.token;
			} else {
				if (info.clientid) {
					clientid = info.clientid;
				}
			}

			if (n > 3) {
				var reloadbtn=document.getElementById("reloadbtn");
				if (reloadbtn){
					reloadbtn.style.display = "block";
				}
			}
			n++;
			setTimeout(function() {
				plus.navigator.closeSplashscreen();
				appsystem = plus.os.name;
				loadappbody();
				
			}, 100);
			if (n > 10 && n <= 50) {
				
				var btnArray = ['重试加载', '取消'];
				mui.confirm('哎呀,网络不给力，请重试加载应用！', '提示', btnArray, function(e) {
					if (e.index == 0) {
						window.location.reload();
						if (nWaiting1) {
							nWaiting1.close();
						}
					} else if (e.index == 1) {
						if (nWaiting) {
							nWaiting.close();
						}
					}
				});
			}
			if (n > 50) {
				n = -1;
			}
			if (n > 0) {
				setTimeout(function() {
					getcliendid();
				}, 1000);
			}
		} else {
			setTimeout(function() {
				plus.navigator.closeSplashscreen();
				appsystem = plus.os.name;
				loadappbody();
			}, 100);
		}
	}

// 空函数
function shield() {
	return false;
}
document.addEventListener("netchange", function() {
	neterr()
}, false);

function getnetstats() {
	var types = {};
	types[plus.networkinfo.CONNECTION_UNKNOW] = "未知";
	types[plus.networkinfo.CONNECTION_NONE] = "未连接网络";
	types[plus.networkinfo.CONNECTION_ETHERNET] = "有线网络";
	types[plus.networkinfo.CONNECTION_WIFI] = "WiFi网络";
	types[plus.networkinfo.CONNECTION_CELL2G] = "2G蜂窝网络";
	types[plus.networkinfo.CONNECTION_CELL3G] = "3G蜂窝网络";
	types[plus.networkinfo.CONNECTION_CELL4G] = "4G蜂窝网络";
	var netstatst = types[plus.networkinfo.getCurrentType()];
	return netstatst;
}

function getPos() {
	
}
plusReady();
/**
 * 创建桌面快捷方式
 */
function createShortcut(){
	// 创建快捷方式意图
	var shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	// 设置快捷方式的名称
	shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ZZ91再生网");
	// 设置不可重复创建
	shortcut.putExtra("duplicate",false);
	// 设置快捷方式图标
	var iconPath = plus.io.convertLocalFileSystemURL("_www/icon.png"); // 将相对路径资源转换成系统绝对路径
	var bitmap = BitmapFactory.decodeFile(iconPath);
	shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON,bitmap);
	// 设置快捷方式启动执行动作
	var action = new Intent(Intent.ACTION_MAIN);
	action.setComponent(main.getComponentName());
	shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,action);
	// 广播创建快捷方式
	main.sendBroadcast(shortcut);
}

