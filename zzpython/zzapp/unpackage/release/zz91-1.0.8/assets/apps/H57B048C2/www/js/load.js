mui.init();
// H5 plus事件处理
var at = 100; // 默认动画时间
var nWaiting1;
function plusReady() {
	if (window.plus){
		//document.getElementById("dcontent1").innerHTML+=2;
		compatibleAdjust();
	}else{
		//document.getElementById("dcontent1").innerHTML+=1;
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
				//appsystem = "iOS";
				loadappbody();
			}, 100);
		}
	}
	//加载头部和底部

function showheader(wintype) {
	//头部底部
	var active1 = "";
	var active2 = "";
	var active3 = "";
	var active4 = "";
	var active5 = "";
	var title = "ZZ91再生网"
	if (wintype == "myrc" || wintype == "myrc_products") {
		title = "生意管家"
		active4 = "mui-active"
		
	}
	if (wintype == "login") {
		title = "用户登录"
		active2 = "mui-active"
	}
	if (wintype == "reg") {
		title = "用户注册"
		active2 = "mui-active"
	}
	if (wintype == "error") {
		title = "网络错误"
		active2 = "mui-active"
	}
	if (wintype == "trade" || wintype == "tradelist") {
		title = "交易中心"
		active2 = "mui-active"
	}
	if (wintype == "price") {
		title = "行情报价"
		active2 = "mui-active"
	}
	if (wintype == "huzhu") {
		title = "再生互助"
		active2 = "mui-active"
	}
	if (wintype == "news") {
		title = "资讯中心"
		active2 = "mui-active"
	}
	if (wintype == "tradeindex") {
		title = "商机"
		active2 = "mui-active"
	}
	if (wintype == "messages") {
		title = "消息"
		active5 = "mui-active"
	}
	if (wintype == "fj") {
		title = "附近商机"
		active1 = "mui-active"
	}
	if (wintype == "newindex") {
		title = "ZZ91再生网"
		active3 = "mui-active"
	}
	if (wintype == "order") {
		title = "商机定制"
		active2 = "mui-active"
	}
	if (wintype == "company") {
		title = "公司黄页"
		active2 = "mui-active"
	}
	if (wintype == "myorder") {
		title = "我的定制"
		active2 = "mui-active"
	}
	if (wintype == "favorite") {
		title = "我的收藏夹"
		active4 = "mui-active"
	}
	if (wintype == "qianbao") {
		title = "再生钱包"
		active4 = "mui-active"
	}
	if (wintype == "zhangdan") {
		title = "我的账单"
		active4 = "mui-active"
	}
	var topstr = "<a><img src=\"images/logo.png\"></a>" +
		"<a class=\"mui-icon mui-icon-gear mui-pull-right\" href=\"javascript:gotourl('/set.html','blank')\"></a>"
	var topstr1 = "<a class=\"mui-action-back mui-icon mui-icon-left-nav mui-pull-left\"></a>" +
		"<a class=\"mui-icon mui-icon-gear mui-pull-right\" href=\"javascript:gotourl('/set.html','blank')\"></a>" +
		"<h1 class=\"mui-title\">" + title + "</h1>"
	var topstr2 = "<a class=\"mui-pull-left\"><img src=\"images/logo.png\" /></a>" +
		//"<a class=\"mui-icon mui-icon-bars mui-pull-right\"></a>" +
		"<a class=\"mui-action-back mui-btn mui-btn-link mui-pull-right\" onclick=\"loginout()\">关闭</a>" +
		"<h1 class=\"mui-title\">" + title + "</h1>"
	var topstr3 = "<a class=\"mui-action-back mui-icon mui-icon-left-nav mui-pull-left\"></a>" +
		//"<a class=\"mui-icon mui-icon-bars mui-pull-right\"></a>" +
		"<h1 class=\"mui-title\">" + title + "</h1>"

	if (wintype == "index" || wintype == "newindex") {
		document.getElementById("apptop").innerHTML = topstr
	} else {
		document.getElementById("apptop").innerHTML = topstr1
	}
	if (wintype == "login") {
		document.getElementById("apptop").innerHTML = topstr1
	}
	if (wintype == "reg") {
		document.getElementById("apptop").innerHTML = topstr1
	}
	var botttomstr = "<nav class=\"mui-bar mui-bar-tab\" id=\"appbottom\"><div style=\"position: fixed;bottom: 0;left: 0;\">" +
		"<a class=\"mui-tab-item " + active3 + "\" href=\"javascript:gotourl('/index.html','newindex');\">" +
		"	<span class=\"mui-icon mui-icon-home\"></span><span class=\"mui-tab-label\" >首页</span>" +
		"</a>" +
		"<a class=\"mui-tab-item " + active5 + "\" href=\"javascript:gotourl('/messagelist/','messages');\">" +
		"	<span class=\"mui-badge mui-badge-danger message-num\" id='messagescount' style='display:none'>1</span><span class=\"mui-icon mui-icon-email\"></span><span class=\"mui-tab-label\">消息</span>" +
		"</a>" +
		"<a class=\"mui-tab-item " + active1 + "\" href=\"javascript:fj()\">" +
		"	<span class=\"mui-icon mui-icon-location\"></span><span class=\"mui-tab-label\">附近</span>" +
		"</a>" +
		"<a class=\"mui-tab-item " + active4 + "\" href=\"javascript:gotourl('/myrc_index/','myrc');\">" +
		"	<span class=\"mui-icon mui-icon-contact\"></span><span class=\"mui-tab-label\">管家</span>" +
		"</a>" +
		"</div></nav>";


	var bottomquestion = "<nav class=\"mui-bar mui-bar-tab\" id=\"appbottom\"><span class=\"mui-icon mui-icon-plus zz91-question-addpic\"></span>" +
		"<textarea rows=\"1\" placeholder=\"多行文本框\" class=\"zz91-question-addtext\"></textarea>" +
		"<a class=\"mui-btn mui-btn-success zz91-question-addsend\">发送</a></nav>";

	var bottomhuzhu = "<nav class=\"mui-bar mui-bar-tab\" id=\"appbottom\"><center style=\"padding-top: 10px;\">" +
		"<button onclick=\"gotourl('/huzhupost/','blank')\" class=mui-btn-success style=\"width: 90%;\">  我要提问    </button> " +
		"</center></nav>";

	
	if (wintype == "login" || wintype == "error" || wintype == "reg") {
		document.getElementById("appbottom").innerHTML = "";
	} else {
		document.getElementById("appbottom").innerHTML = botttomstr;
	}
	if (wintype == "huzhu") {
		document.getElementById("appbottom").innerHTML = bottomhuzhu;
	}
	if (wintype == "question") {
		document.getElementById("appbottom").innerHTML = bottomquestion;
	}
	getmessagesnum(company_id);
}

// 空函数
function shield() {
	return false;
}
document.addEventListener("netchange", function() {
	neterr()
}, false);

function neterr(){
	var netstats = getnetstats();
	var pcontent = document.getElementById("pcontent");
	var mainbody = document.getElementById("mainbody");
	if (netstats != "未连接网络") {
		if (pcontent) {
			pcontent.style.display = "none";
			mainbody.style.display = "";
			loadappbody();
		}
	} else {
		if (pcontent) {
			pcontent.style.display = "";
			mainbody.style.display = "none";
		}
	}
}
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
	//首次加载

function loadappbody() {
	var netstatus = 0;
	var companyid = plus.storage.getItem("companyid");
	company_id = companyid;
	if (company_id==null){company_id=0}
	if (company_id == null || company_id.toString() == "0") {
		if (clientid == "" || clientid == "null" || !clientid) {
			clientid = "No" + (new Date()).getTime().toString();
		}
		zzajax("get","http://app.zz91.com/logininfo1.html?appid=" + clientid + "&appsystem=" + appsystem, '',function(data) {
			var j = JSON.parse(data);
			if (j.company_id == 0) {
				gotourl("/index.html", "newindex");
				netstatus = 1;
			} else {
				company_id = j.company_id;
				netstatus = 1;
				gotourl("/index.html", "newindex");
			}
		}, function() {
			showheader("error");
			var pcontent = document.getElementById("pcontent");
			var mainbody = document.getElementById("mainbody");
			if (pcontent) {
				pcontent.style.display = "";
				mainbody.style.display = "none";
			}
			company_id=0;
		});
		if (nWaiting) {
			nWaiting.close();
		}
	} else {
		gotourl("/index.html", "newindex");
		netstatus = 1;
	}
	var shortcutfirst = plus.storage.getItem("shortcutfirst");
	if (appsystem=="Android" && !shortcutfirst){
		createShortcut();
		plus.storage.setItem("shortcutfirst","1");
	}
}

function regloadbody(cid) {
		company_id = cid;
		nowurl = nowurl.replace("company_id=0", "company_id=" + cid.toString());

		gotourl(nowurl, nowwintype);
		netstatus = 1
}
	//重新加载

function reloadapp(obj) {
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	obj.innerHTML = "刷新重试...";
	loadappbody();
}


// 百度地图API功能
//var myGeo = new BMap.Geocoder();
//function geocodeSearch(latitude,longitude){
//	var pt=new BMap.Point(longitude,latitude)
//	myGeo.getLocation(pt, function(rs){
//		var addComp = rs.addressComponents;
//		nowprovice=addComp.province;
//		nowcity=addComp.city;
//	});
//}
//function geocodeSearch(latitude,longitude){
//	alert(latitude)
//	mui.get("http://api.map.baidu.com/geocoder/v2/?ak=32TStPTKRZApGLPVI20DSo68&location="+longitude.toString()+","+latitude.toString()+"&output=json", function(data) {
//		alert(data)
//		var addComp=JSON.parse(data);
//		nowprovice=addComp.result.addressComponent.province;
//		nowcity=addComp.result.addressComponent.city;
//		alert(nowprovice);
//	},function(){
//		alert(2);
//	});
//}

function getPos() {
	//	plus.geolocation.getCurrentPosition( function(p){
	//		//longitude=p.coords.longitude;
	//		//altitude=p.coords.altitude;
	//	}, function ( e ) {
	//		//alert( "获取位置信息失败："+e.message );
	//	});
	//	plus.geolocation.clearWatch(null);
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

