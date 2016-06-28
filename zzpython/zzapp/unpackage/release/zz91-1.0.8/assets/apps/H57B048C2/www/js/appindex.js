var zzajax1 = function(method, url, arg, successCallback, errorCallback) {
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
				zzajax1("post","http://app.zz91.com/myrc/favoritedel.html",arg, function(data) {
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

		zzajax1("post",url,arg, function(data) {
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
					opener.evalJS('loadappbody();');
				}
				var parent = wobj.parent();
				if (parent) { //又得evalJS
					parent.evalJS('loadappbody();');
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
		if (isview != "1" && obj.style.color == "") {
			if (count != "" && count != "0") {
				$("#messagescount").html((count - 1).toString())
			}
			if (count - 1 == 0) {
				messagescount.style.display = "none";
			}

			obj.style.color = "#C4C4C4";
		}
		if (count == "" || count == "0") {
			messagescount.style.display = "none";
		}
		gotourl(urlp, "blank");
	}
};
var downloader = {
	down: function(durl, srvVer) {
		var dtask = null;
		if (dtask) {
			return;
		}
		//var durl = "http://app.zz91.com/app/zz91-1.0.2.apk";
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
					downloadwindowjindu.innerHTML = '正在下载数据: <span id="dsize"></span>/' + parseInt(task.totalSize / 1024) / 1000 + "M";
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
