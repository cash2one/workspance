var jsstr;
var loadnum=1;
var messagenum=0;
var shopfun = {
	opencontact: function() {
		var qianbaoblance = $('#qianbaoblance').val();
		var money = 5;
		if (parseInt(qianbaoblance) >= money) {
			openfloatdiv(event, 'showphone1');
		} else {
			openfloatdiv(event, 'bal');
		}
	},
	chongzhi:function(furl) {
		if (appsystem=="iOS"){
			//requestquery("http://app.zz91.com/chongzhi.html","");
			plus.ui.alert("我们已将支付方式以短信和邮件的方式发送给您，请注意查收！")
		}else{
			gotourl(furl,'blank');
		}
	},
	name:1
};
var myrc = {
	favoritedel: function(id, obj) {
		var btnArray = ['确定', '取消'];
		mui.confirm('确认要删除吗？', '提示', btnArray, function(e) {
			if (e.index == 0) {
				var arg = "company_id=" + company_id;
				arg += "&id=" + id;
				zzajax("post","http://app.zz91.com/myrc/favoritedel.html",arg, function(data) {
					var j = JSON.parse(data);
					if (j && j.err) {
						if (j.err == "false") {
							var favor = obj.parentNode
							if (favor) {
								favor.style.display = "none";
							}
						} else {
							plus.ui.alert(j.errkey);
						}
						if (nWaiting) {
							nWaiting.close();
						}
					} else {
						if (nWaiting) {
							nWaiting.close();
						}
					}
				}, function(data) {});
			} else if (e.index == 1) {
				if (nWaiting) {
					nWaiting.close();
				}
			} else {
				if (nWaiting) {
					nWaiting.close();
				}
			}
		});
	},
	forgetpasswd: function(frm, url) {
		var arg = "";
		if (window.plus) {
			nWaiting = plus.nativeUI.showWaiting();
			arg += "company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&t=" + Math.ceil(new Date / 3600000);
			var checkflag = 0;
			for (var i = 0; i < frm.length; i++) {
				var objinput = frm[i];
				var objtype = objinput.type;
				var objvalue = objinput.value;
				var objname = objinput.name;
				if (objname != "") {
					if (objinput.type == "radio" || objinput.type == "checkbox") {
						if (objinput.checked == true) {
							if (arg.indexOf(objname) < 0) {
								checkflag = 0;
							}
							if (checkflag == 1) {
								arg += "," + objvalue;
							} else {
								arg += "&" + objname + "=" + objvalue;
							}
						}
						checkflag = 1;
					} else {
						checkflag = 0;
						arg += "&" + objname + "=" + objvalue;
					}
				}
				if (objinput.title != "" && objinput.title) {
					if (objinput.title.substring(0, 1) == "*") {
						if (objinput.value == "") {
							plus.ui.alert(objinput.title.substring(1));
							objinput.focus();
							if (nWaiting) {
								nWaiting.close();
							}
							return false
						}
					}
				}
			}
		}

		zzajax("post",url,arg, function(data) {
			var j = JSON.parse(data);
			if (j) {
				var err = j.err;
				var errkey = j.errkey;
				if (err == "true") {
					if (j.type == "forgetpasswd") {
						$(".c-red").html(errkey);
					}
				} else {
					if (j.type == "forgetpasswd") {
						$(".c-red").html("");
						var result = j.result;
						var step = j.step;
						var mobile = result.mobile;
						var account = result.account;
						loaddata("http://app.zz91.com/forgetpasswdpage.html?mobile=" + mobile + "&account=" + account + "&clientid=" + clientid + "&step=" + step);
					}
				}
			}

			if (nWaiting) {
				nWaiting.close();
			}
		}, function() {
			if (nWaiting) {
				nWaiting.close();
			}
		});
		return false;
	},
	//重置密码成功后自动登陆
	forgetpasswdload: function(cid) {
		company_id = cid;
		if (window.plus) {
			var wobj = plus.webview.currentWebview();
			if (wobj) {
				var opener = wobj.opener();
				if (opener) {
					opener.evalJS('regloadbody(' + company_id + ');');
				}
				var mainwin=plus.webview.getWebviewById( "HBuilder" );
				if (mainwin){
					mainwin.evalJS('regloadbody(' + company_id + ');');
				}
			}
		}
		closewindow();
	}
};
var messages = {
	views: function(urlp, obj, isview) {
		var messagescount = document.getElementById("messagescount");
		var count = $("#messagescount").html();
		messagenum=document.getElementById("mtype");
		if (messagenum){
			messagenum=messagenum.value;
		}else{
			messagenum=0
		}
		if (isview != "1" && obj.style.color == "") {
			if (count != "" && count != "0") {
				$("#messagescount").html((count - 1).toString())
			}
			if (count - 1 == 0) {
				messagescount.style.display = "none";
			}
			obj.style.color = "#C4C4C4";
			var mainwin=plus.webview.getWebviewById( "HBuilder" );
			if (mainwin){
				mainwin.evalJS('messages.showviewnum('+messagenum.toString()+');');
			}
			var wobj = plus.webview.currentWebview();
			if (wobj) {
				var opener = wobj.opener();
				if (opener) {
					opener.evalJS('messages.showviewnum('+messagenum.toString()+');');
				}
			}
		}
		if (count == "" || count == "0") {
			messagescount.style.display = "none";
		}
		gotourl(urlp, "blank");
	},
	showviewnum:function(num){
		var messagescount = document.getElementById("messagescount");
		if (messagescount){
			var count = $("#messagescount").html();
			if (count != "" && count != "0") {
				$("#messagescount").html((count - 1).toString())
			}
			if (count - 1 == 0) {
				messagescount.style.display = "none";
			}
		}
		var messagescount = document.getElementById("messagescount"+num.toString());
		if (messagescount){
			var count = $("#messagescount"+num.toString()).html();
			if (count != "" && count != "0") {
				$("#messagescount"+num.toString()).html((count - 1).toString())
			}
			if (count - 1 == 0) {
				messagescount.style.display = "none";
			}
		}
	},
	openlist:function(num){
		messagenum=num;
		gotourl("messagelistmongo.html?mtype="+num.toString(), "blank");
	}
};
var downloader = {
	down: function(durl, srvVer) {
		var dtask = null;
		if (dtask) {
			return;
		}
		var options = {
			method: "GET"
		};
		dtask = plus.downloader.createDownload(durl, options);
		dtask.addEventListener("statechanged", function(task, status) {
			switch (task.state) {
				case 1: // 开始
					break;
				case 2: // 已连接到服务器
					break;
				case 3: // 已接收到数据

					var downloadwindowjindu = document.getElementById("downloadwindowjindu");
					downloadwindowjindu.innerHTML = '下载数据: <span id="dsize"></span>/' + parseInt(task.totalSize / 1024) / 1000 + "M";
					var ds = document.getElementById("dsize");
					ds.innerText = parseInt(task.downloadedSize / 1024) / 1000 + "M";

					break;
				case 4: // 下载完成
					closeoverlay();
					plus.runtime.openFile("_downloads/zz91-" + srvVer + ".apk", {}, function(e) {
						plus.nativeUI.alert("升级失败：" + e.emssage);
					});
					break;
			}
		});
		dtask.start();
	},
	htmldown:function(html){
		var dtask = null;
		if (dtask) {
			return;
		}
		var durl = "http://app.zz91.com/app/html/index.html";
		var options = {
			method: "GET"
		};
		dtask = plus.downloader.createDownload(durl, options);
		dtask.addEventListener("statechanged", function(task, status) {
			switch (task.state) {
				case 1: // 开始
					break;
				case 2: // 已连接到服务器
					break;
				case 3: // 已接收到数据
					break;
				case 4: // 下载完成
					//plus.io.FileEntry.moveTo("_www/","index.html",'','');
					break;
			}
		});
		dtask.start();
	}
};
var msgchickurl="";
// 监听plusready事件  
document.addEventListener("plusready", function() {
	// 监听点击消息事件
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
	
	
}, false);


//链接通用函数
(function($) {
	$.ready(function() {
		$('body').on('tap', 'a', function(e) {
			var id = this.getAttribute('href');
			var wintarget = "blank";
			if (nowurl){
				if (nowurl.indexOf("/priceindex/") >= 0) {
					//wintarget = "price"
				}
				if (nowurl.indexOf("/category/") >= 0) {
					wintarget = "trade"
				}
			}
			if (id && id.substring(0, 1) != "#") {
				if (window.plus) {
					if (id.indexOf("javascript:") < 0) {
						if (id.indexOf("tel:")>=0){
							eval("dialtel("+id.replace("tel:","")+")")
						}else{
							if (appsystem=="iOS"){
								gotourl(id, wintarget);
							}else{
								gotourl(id, wintarget);
							}
						}
					} else {
						if (id.indexOf("tel:")>0){
							eval("dialtel("+id.replace("tel:","")+")")
						}else{
							if (appsystem=="iOS"){
								eval(id);
							}else{
								eval(id);
							}
						}
					}
				}
			}
		});
	});
})(mui);


