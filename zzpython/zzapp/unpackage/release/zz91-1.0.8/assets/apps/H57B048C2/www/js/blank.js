//商机定制
function orderclick(id) {
	var obj = $("#orderm" + id);
	var str = obj.find(".item-div-icon").css("display");
	if (str === "none") {
		obj.removeClass("item-div-style");
		obj.addClass("item-div-click");
		obj.find(".item-div-icon").css("display", "block");
		obj.find(".item_div_i").attr("checked", 'true');
	} else {
		obj.removeClass("item-div-click");
		obj.addClass("item-div-style");
		obj.find(".item-div-icon").css("display", "none");
		obj.find(".item_div_i").removeAttr("checked");
	}
	submitfrm(document.getElementById("formorder"), "http://app.zz91.com/order/save_collect.html")
}

//加载新数据
function loaddata(url) {
		if (window.plus) {
			var nWaiting = plus.nativeUI.showWaiting();
		}
		if (isback == 1) {
			winarr.push(url);
		}
		//		
		zzajax("get",url, '', function(data) {
			lendata = data.indexOf("id=\"appnavname\"");
			if (lendata > 0) {
				document.getElementById("dcontent").innerHTML = data;
				iOSclosecontent();
				page = 2;
				if (nWaiting) {
					nWaiting.close();
				}
				var navname = document.getElementById("appnavname")
				if (navname) {
					document.getElementById("apptopname").innerHTML = navname.value;
				} else {
					document.getElementById("apptopname").innerHTML = "ZZ91再生网";
				}
				if (minwindow == null) {
					mui.scrollTo(0, 10);
				}
				//读取缓存数据
				if (window.plus) {
					plus.cache.calculate(function(size) {
						cachesize = parseInt(size / 1024);
						var cachediv = document.getElementById("cachesize");
						if (cachediv) {
							cachediv.innerHTML = cachesize + "KB";
						}
					});
					var visiton = document.getElementById("visiton");
					if (visiton) {
						visiton.innerHTML = "" + plus.runtime.version + "";
						visitoncode = plus.runtime.version;
					}
					//高版本兼容问题
					var newsnav = document.getElementById("newsnav");
					if (newsnav) {
						newsnav.style.display = "block";
					}
					var rightbutton = document.getElementById("rightbutton");
					if (rightbutton) {
						if (url.indexOf("/huzhuview/")>0){
							rightbutton.style.display = "block";
						}
					}
					
				}
				reloadnum = 0;
			} else {
				document.getElementById("apptopname").innerHTML = "系统错误";
				document.getElementById("dcontent").innerHTML = "<br /><br /><center>系统错误...<br /><br /><button onclick='closewindow();' class='mui-btn mui-btn-danger'>关闭</button></center>";
				if (nWaiting) {
					nWaiting.close();
				}
				//winarr.pop();
			}
		}, function() {
			if (reloadnum <= 3) {
				var btnArray = ['重试加载', '取消'];
				mui.confirm('哎呀,网络不给力，点击重试加载试试！', '提示', btnArray, function(e) {
					if (e.index == 0) {
						loaddata(url);
						if (nWaiting) {
							nWaiting.close();
						}
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
				reloadnum += 1;
			} else {
				plus.ui.alert("系统问题请稍后再试！");
				if (nWaiting) {
					nWaiting.close();
				}
			}
			winarr.pop();
		});
		nowherf = url;
}
	//打开新窗口
function gotourl(url, wintype) {
		url = url.replace("/companyinfo/?company_id=", "/companyinfo/?forcid=");
		if (wintype == "blank") {
			url = url.replace("&", "TandT").replace("?", "wenhao");
			mui.openWindow({
				id: url,
				url: "/blank.html?url2=" + url + "&wintype=" + wintype + "&company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&visitoncode=" + visitoncode,
				preload: false //TODO 等show，hide事件，动画都完成后放开预加载
			});
		} else {
			if (url.indexOf("http://app.zz91.com") < 0) {
				if (url.indexOf("?") < 0) {
					url = "http://app.zz91.com/" + url + "?t=" + (new Date()).getTime().toString() + "&company_id=" + company_id.toString() + "&win=" + win.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&visitoncode=" + visitoncode;
				} else {
					url = "http://app.zz91.com/" + url + "&t=" + (new Date()).getTime().toString() + "&company_id=" + company_id.toString() + "&win=" + win.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&visitoncode=" + visitoncode;
				}
			}
			loaddata(url)
		}
	}
	//翻页
var page = 2;

function loadmorepp(obj, url) {
		if (url.indexOf("?") < 0) {
			url = url + "?page=" + page.toString() + "&company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&t=" + (new Date()).getTime().toString() + "&visitoncode=" + visitoncode;
		} else {
			url = url + "&page=" + page.toString() + "&company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem + "&t=" + (new Date()).getTime().toString() + "&visitoncode=" + visitoncode;
		}
		obj.style.display = "";
		obj.innerHTML = "正在加载中...";

		if (url != "" && url != null) {
			mui.get("http://app.zz91.com/" + url, '', function(data) {
				if (data != "") {
					var newNode = document.createElement("div");
					newNode.innerHTML = data;
					obj.parentNode.insertBefore(newNode, obj);
					if (obj) {
						page += 1;
						obj.innerHTML = "<span class='mui-icon mui-icon-down'></span><span>点此加载更多</span>";
					}
					mui.init();
				} else {
					obj.style.display = "none";
				}
			});
		}
	}
	//互助回复

function huzhureplay(replayid, tocompany_id) {
		var bbs_post_reply_id = document.getElementById("bbs_post_reply_id");
		if (bbs_post_reply_id) {
			document.getElementById("bbs_post_reply_id").value = replayid;
			document.getElementById("tocompany_id").value = tocompany_id;
			if (company_id != 0) {
				openoverlay('', '回复', 0, 180, '.d-reply');
			} else {
				loaddata("http://app.zz91.com/login/?tourl=" + rt);
			}
		}
	}
	//供求留言

function tradeleavewords(pid, tocompany_id) {
		if (company_id == 0) {
			loaddata("http://app.zz91.com/login/?tourl=" + rt);
		} else {
			openoverlay('', '回复', 0, 180, '.d-leavewords');
		}

	}
	//收藏

function favorite(querylist) {
		if (company_id == 0) {
			loaddata("http://app.zz91.com/login/?tourl=" + rt);
		} else {
			var furl = "http://app.zz91.com/favorite/";
			requestquery(furl, querylist);
		}
	}
	//来电宝支付

function selectpay(obj) {
	$(".tag-bg").css("backgroundColor", "#e0e0e0");
	$(".tag-bg").css("color", "#9b9b9b");
	obj.style.backgroundColor = "#409be4";
	obj.style.color = "#fff";

	//document.getElementById("total_feepay").value=obj.title;
}

function pay(formobj) {
		var total_fee = document.getElementById("total_feepay").value;
		document.getElementById("total_fee").value = total_fee
		if (isNaN(total_fee)) {
			plus.ui.alert("金额必须是数字！")
			return false;
		}
		if (parseInt(total_fee) < 1000 || total_fee == "") {
			plus.ui.alert("金额必须大于1000元以上");
			return false;
		}
		startpay(formobj)
			//form1.submit();
	}
	//来电宝查看

function lookfirst(cid, id) {
	openoverlay('', '查看未接来电', '<br /><font color=#f00>提醒</font><br />查看该未接来电将扣除10元费用.<br />确定要查看吗？<br /><input type=button value=\'确定查看\' id=\'mobilebutton\' class=\'mui-btn mui-btn-danger\' onclick=lookcontact(' + cid + ',' + id.toString() + ') /></form><br /><br /></div>', 170, '');
}

function lookcontact(cid, id) {
	mui.get("http://app.zz91.com/ldb_weixin/lookcontact.html?id=" + id.toString() + "&company_id=" + cid.toString(), '',
		function(data) {
			if (data != "err\n" && data != "" && data != "err") {
				$("#lst-phone" + id.toString()).html(data);
				closeoverlay();
			} else {
				$(".mainlist").html("<br />您的帐户余额不足10元，请充值后查看！<br /><br /><button class='mui-btn mui-btn-green' onclick='gotourl(\"/ldb_weixin/balance.html\",\"blank\");closeoverlay();'>点此立即充值！</button>")
			}
		});
}

function tabView(tblId) {
	var children = document.getElementById("tabUL").getElementsByTagName("li");
	for (var i = 0; i < children.length; i++) {
		children[i].setAttribute("class", "m-li");
		document.getElementById("tab" + (i + 1)).style.display = "none";
	}
	children[parseInt(tblId.replace("tab", "")) - 1].setAttribute("class", "m-li_");
	document.getElementById(tblId).style.display = "table";

}

function offCanvasHide() {

	}
	//登陆

function zz91login(frm) {
	var username = frm.username.value;
	var passwd = frm.passwd.value;
	var tourl = frm.tourl.value;
	if (username == "" || passwd == "") {
		return false;
	}
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	var arg = "username=" + username;
	arg += "&passwd=" + passwd;
	arg += "&appid=" + clientid;
	arg += "&loginflag=1";
	arg += "&appsystem=" + appsystem;
	mui.post("http://app.zz91.com/loginof.html", function(data) {
		var j = JSON.parse(data);
		if (j && j.err) {
			if (j.err == "false") {
				company_id = j.result;
				var wobj = plus.webview.currentWebview();
				if (wobj) {
					var opener = wobj.opener();
					if (opener) {
						opener.evalJS('regloadbody(' + company_id.toString() + ');');
					}
					//					var parent = wobj.parent();
					//					if (parent) {
					//						parent.evalJS('loadappbody();');
					//					}
				}
				if (tourl != "") {
					if (tourl.indexOf("?") < 0) {
						loaddata("http://app.zz91.com/" + tourl + "?t=" + (new Date()).getTime().toString() + "&company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem) + "&visitoncode=" + visitoncode;
					} else {
						loaddata("http://app.zz91.com/" + tourl + "&t=" + (new Date()).getTime().toString() + "&company_id=" + company_id.toString() + "&clientid=" + clientid + "&appsystem=" + appsystem) + "&visitoncode=" + visitoncode;
					}
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
	}, function(data) {
		//alert(data)
	}, arg);
	return false;
}

