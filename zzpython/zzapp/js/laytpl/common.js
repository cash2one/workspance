//加载内容
var zzweburl = "http://app.zz91.com";
var page = 1;
//设备唯一标示
var clientid = "";
var company_id = 0;
//app系统
var appsystem;
var visitoncode;
var jsstr;
var defaultqueryString={};
var url;

mui.plusReady(function() {
	var info = plus.push.getClientInfo();
	if (info.token) {
		clientid = info.token;
	} else {
		if (info.clientid) {
			clientid = info.clientid;
		}
	}
	var userimei = plus.device.imei;
	appsystem=plus.os.name;
	visitoncode=plus.runtime.version;
	//默认参数
	defaultqueryString={
		company_id:company_id,
		clientid:clientid,
		appsystem:appsystem,
		t:Math.ceil(new Date/3600000)
	}
	
});

function telclick(url) {
	mui.get(url, '', function(data) {
	}, '')
}

function dialtel(telphone) {
	plus.device.dial(telphone, false);
	telclick("http://m.zz91.com/trade/telclicklog.html?tel=" + telphone + "&pagefrom=apptrade&company_id="+company_id.toString());
}
//链接通用函数
(function($) {
	$.ready(function() {
		$('body').on('tap', 'a', function(e) {
			var id = this.getAttribute('href');
			if (window.plus && id) {
				if (id.indexOf("javascript:") < 0) {
					if (id.indexOf("tel:") >= 0) {
						//eval("dialtel(" + id.replace("tel:", "") + ")")
					} else {
						mui.openWindow({
							id: id,
							url: id,
							preload: false //TODO 等show，hide事件，动画都完成后放开预加载
						});
						return false;
					}
				} else {
					if (id.indexOf("tel:") > 0) {
						//eval("dialtel(" + id.replace("tel:", "") + ")")
					} else {
						eval(id);
					}
					return false;
				}
			}
			return false;
		});
	});
})(mui);
function getFormQueryString(frmID) {
	//var frmID = document.getElementById(frmID);
	var arg = defaultqueryString;
	var i, queryString = "",
		and = "";
	var item; // for each form's object
	var itemValue; // store each form object's value

	for (i = 0; i < frmID.length; i++) {
		item = frmID[i]; // get form's each object
		if (item.name != '') {
			if (item.type == 'select-one') {
				itemValue = item.options[item.selectedIndex].value;
			} else if (item.type == 'checkbox' || item.type == 'radio') {
				if (item.checked == false) {
					continue;
				}
				itemValue = item.value;
			} else if (item.type == 'button' || item.type == 'submit' || item.type == 'reset' || item.type == 'image') { // ignore this type
				continue;
			} else {
				itemValue = item.value;
			}
			//itemValue = encodeURIComponent(itemValue);
			arg[item.name]=itemValue
			//queryString += and + item.name + '=' + itemValue;
			//and = "&";
		}
	}
	return arg;
}
function loadcompanyid(cid) {
	company_id = cid;
	defaultqueryString['company_id']=cid;
}
function gotourl(url,wintype){
	url = url.replace("/companyinfo/?company_id=", "/companyinfo/?forcid=");
	url = url.replace("&", "TandT").replace("?", "wenhao")
	mui.openWindow({
		id: url,
		url: "/blank.html?url2=" + url + "&wintype=" + wintype + "&company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&visitoncode=" + visitoncode,
		preload: false //TODO 等show，hide事件，动画都完成后放开预加载
	});
	return;
}
function openwindow(url,extras){
	mui.openWindow({
		id: url,
		url: url,
		extras:extras,
		preload: false //TODO 等show，hide事件，动画都完成后放开预加载
	});
}
function closewindow() {
	var ws = plus.webview.currentWebview();
	ws.close();
}
