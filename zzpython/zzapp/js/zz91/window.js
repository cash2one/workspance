// 百度地图API功能
var nowaddress;
var dwflag = true;
var oldaddress;
var redw = false;
var nowprovice;
var nowcity;
if (window.plus) {
	var netstatst = getnetstats();
//	if (netstatst != "未连接网络") {
//		if (BMap) {
//			var myGeo = new BMap.Geocoder();
//		}
//	}
}
var dwtype = 1;
var nWaiting;

function login() {
		gotourl("/login/", "login");
	}
	//通用加载页面
var loadnum = 0;
//加载新数据
function loaddata(url) {

}



	//地理位置定位
var searchname = "";

function geocodeSearch(latitude, longitude) {
		var i = 0;
		if (dwflag == true) {
			var pt = new BMap.Point(longitude, latitude)
			myGeo.getLocation(pt, function(rs) {
				var addComp = rs.addressComponents;
				nowprovice = addComp.province;
				nowcity = addComp.city;
				district = addComp.district;
				nowaddress = nowprovice.replace("省", "");
				nowaddress += " " + nowcity.replace("市", "");
				var oldaddress = plus.storage.getItem("nowaddress");
				if (oldaddress != nowaddress) {
					redw = true;
					plus.storage.setItem("nowaddress", nowaddress);
				}
				dwflag = false;
			});
		}
		//alert(i)
		var mylocation_status = document.getElementById("mylocation_status")
		if (mylocation_status) {
			//mylocation_status.innerHTML="GPS定位中1...";
			//mylocation_status.innerHTML=nowaddress;
			//alert(nowaddress)
		} else {
			if (i <= 8) {
				geocodeSearch();
			}
			i += 1;
		}
	}

function getPushInfo11() {
	var info = plus.push.getClientInfo();
	//outSet( "获取客户端推送标识信息：" );
	//outLine( "token: "+info.token );
	//outLine( "clientid: "+info.clientid );
	//outLine( "appid: "+info.appid );
	//outLine( "appkey: "+info.appkey );
	createLocalPushMsg();
}

function getPushInfo() {
		var info = plus.push.getClientInfo();
		//alert(info.clientid);
		//outSet( "获取客户端推送标识信息：" );
		//outLine( "token: "+info.token );
		//outLine( "clientid: "+info.clientid );
		//outLine( "appid: "+info.appid );
		//outLine( "appkey: "+info.appkey );
	}
	/**
	 * 本地创建一条推动消息
	 */

function createLocalPushMsg() {
		var options = {
			cover: false
		};
		var str = dateToStr(new Date());
		str += ": 欢迎使用Html5 Plus创建本地消息！";
		plus.push.createMessage(str, "LocalMSG", options);
		//outSet( "创建本地消息成功！" );
		//outLine( "请到系统消息中心查看！" );
		if (plus.os.name == "iOS") {
			//outLine('*如果无法创建消息，请到"设置"->"通知"中配置应用在通知中心显示!');
		}
	}
	/**
	 * 请求‘简单通知’推送消息
	 */

function requireNotiMsg() {
	var url = pushServer + 'notiPush.php?appid=' + encodeURIComponent(plus.runtime.appid);
	url += ('&cid=' + encodeURIComponent(plus.push.getClientInfo().clientid));
	url += ('&title=' + encodeURIComponent('Hello H5+'));
	url += ('&content=' + encodeURIComponent('欢迎回来体验HTML5 plus应用！'));
	plus.runtime.openURL(url);
}

window.HTMLElement.prototype.slideDown = function(speed, height) {
	var o = this;
	//clearInterval(o.slideFun);
	var h = height;
	var i = 0;
	o.style.height = h + 'px';
	//o.style.width = '150px';
	o.style.display = 'block';
	o.style.overflow = 'hidden';
	o.style.removeProperty('overflow');


	//	o.slideFun = setInterval(function(){
	//		
	//		i = i + 5;
	//		if(i>h) i=h;
	//		//o.style.height = i+'px';
	//		if(i>=h)
	//		{
	//			o.style.removeProperty('overflow');
	//			clearInterval(o.slideFun);
	//		}	
	//	},speed);
}

window.HTMLElement.prototype.slideUp = function(speed, height) {
	var o = this;
	//clearInterval(o.slideFun);
	var h = height;
	var i = h;
	//o.style.overflow = 'hidden';
	o.style.display = 'none';
	//o.style.removeProperty('overflow');

	//	o.slideFun = setInterval(function(){
	//		i -= 5;
	//		if(i<0) i=0;
	//		o.style.height = i+'px';
	//		if(i<=0)
	//		{
	//			o.style.display = 'none';
	//			o.style.removeProperty('overflow');
	//			//more.className = more.className.replace(' moreclick','');
	//			clearInterval(o.slideFun);
	//		}	
	//	
	//	},speed);
};

function openfile(url) {
	mui.openWindow({
		id: url,
		url: url,
		preload: false //TODO 等show，hide事件，动画都完成后放开预加载
	});
}

function htmldown(html) {
	var dtask = null;
	if (dtask) {
		return;
	}
	var durl = zzweburl+"/app/html/index.html";
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
	//dtask.start();
	plus.io.resolveLocalFileSystemURL("_www/index1.html", function(entry) {
		// 可通过entry对象操作test.html文件 
		// remove this file
		// Get the parent DirectoryEntry
		// Request the metadata object for this entry
		entry.getMetadata(function(metadata) {
			alert(metadata.modificationTime)
			plus.console.log("Last Modified: " + metadata.modificationTime);
		}, function() {
			alert(e.message);
		});
		// remove this file
		//		entry.remove(function(entry) {
		//			plus.console.log("Remove succeeded");
		//			alert(2)
		//		}, function(e) {
		//			alert(e.message);
		//		});
		// Get the parent DirectoryEntry
		entry.getParent(function(entry) {
			plus.console.log("Parent Name: " + entry.name);
			alert(entry.name)
		}, function(e) {
			alert(e.message);
		});
		// create a FileWriter to write to the file
		entry.createWriter(function(writer) {
			// Write data to file.
			writer.write("Data ");
			alert(3)
		}, function(e) {
			alert(e.message);
		});

		//		var fileURL = entry.toRemoteURL();
		//		entry.copyTo(fileURL, "newfile.txt", function(entry) {
		//			alert(1);
		//			plus.console.log("New Path: " + entry.fullPath);
		//		}, function(e) {
		//			alert(e.message);
		//		});
		entry.file(function(file) {
			var fileReader = new plus.io.FileReader();
			//alert("getFile:" + JSON.stringify(file));
			fileReader.readAsText(file, 'utf-8');
			fileReader.onloadend = function(evt) {
				//alert("11" + evt);
				//alert("evt.target" + evt.target);
				//alert(evt.target.result);
			}
			alert(file.size + '--' + file.name);
		});
	}, function(e) {
		alert("Resolve file URL failed: " + e.message);
	});
}