mui.plusReady(function() {

	function UserInfo() {};
	var username="";
	var password;
	var pwd_hash;
	UserInfo.web_query = function(func_url, params, onSuccess, onError, retry){
	    var retry = arguments[4]?arguments[4]:3;
	    func_url = zzweburl + func_url;
	    mui.ajax(func_url, {
	        data:params,
	        dataType:'json',
	        type:'get',
	        timeout:3000,
	        success:function(data){
	            if(data.err === 'false'){
	                UserInfo.onSuccess(data);
	            }
	            else{
	                UserInfo.onError(data.errkey);
	            }
	        },
	        error:function(xhr,type,errorThrown){
	            retry--;
	            if(retry > 0) return UserInfo.web_query(func_url, params, onSuccess, onError, retry);
	            UserInfo.onError('FAILED_NETWORK');
	        }
	    })
	};
	//清除登录信息
	UserInfo.clear = function() {
		plus.storage.removeItem('username');
		plus.storage.removeItem('password');
		plus.storage.removeItem('token');
	};

	//检查是否包含自动登录的信息
	UserInfo.auto_login = function() {
		var username = UserInfo.username();
		var pwd = UserInfo.password();
		if (!username || !pwd) {
			return false;
		}
		return true;
	};

	//检查是否已登录
	UserInfo.has_login = function() {
		var username = UserInfo.username();
		var pwd = UserInfo.password();
		var token = UserInfo.token();
		if (!username || !pwd || !token) {
			return false;
		}
		return true;
	};

	UserInfo.username = function() {
		if (arguments.length == 0) {
			return plus.storage.getItem('username');
		}
		if (arguments[0] === '') {
			plus.storage.removeItem('username');
			return;
		}
		plus.storage.setItem('username', arguments[0]);
	};

	UserInfo.password = function() {
		if (arguments.length == 0) {
			return plus.storage.getItem('password');
		}
		if (arguments[0] === '') {
			plus.storage.removeItem('password');
			return;
		}
		plus.storage.setItem('password', arguments[0]);
	};

	UserInfo.token = function() {
		if (arguments.length == 0) {
			return plus.storage.getItem('token');
		}
		if (arguments[0] === '') {
			plus.storage.removeItem('token');
			return;
		}
		plus.storage.setItem('token', arguments[0]);
	};

	

	UserInfo.get_pwd_hash=function(pwd) {
		return $.md5(pwd);
		//var salt = 'hbuilder'; //此处的salt是为了避免黑客撞库，而在md5之前对原文做一定的变形，可以设为自己喜欢的，只要和服务器验证时的salt一致即可。
		//return $.md5(salt + pwd); //此处假设你已经引用了md5相关的库，比如github上的JavaScript-MD5
	};

	

	UserInfo.onSuccess = function(data,username,pwd_hash) {
		UserInfo.username(username);
		UserInfo.password(pwd_hash);
		UserInfo.token(data.token); //把获取到的token保存到storage中
		//var wc = plus.webview.currentWebview();
		//wc.hide('slide-out-bottom');    //此处假设是隐藏登录页回到之前的页面，实际你也可以干点儿别的
	};

	UserInfo.onError = function(errcode) {
		mui.toast(errcode);
//		switch (errcode) {
//			case 'INCORRECT_PASSWORD':
//				mui.toast('密码不正确');
//				break;
//			case 'USER_NOT_EXISTS':
//				mui.toast('用户尚未注册');
//				break;
//		}
	};
	//这里假设你已经通过DOM操作获取到了用户名和密码，分别保存在username和password变量中。
	if (UserInfo.has_login()) {
		//打开需要展示给用户的页面，或者是调用远端接口
		alert(UserInfo.token())
	} else {
		//alert(2)
		//wv_login.show('slide-in-up');   //从底部向上滑出登录页面
	}
//	username = "xufangyue";
//	password = "zj88friend";
//	pwd_hash = UserInfo.get_pwd_hash(password);
//
//	var info = plus.push.getClientInfo();
//	if (info.token) {
//		clientid = info.token;
//	} else {
//		if (info.clientid) {
//			clientid = info.clientid;
//		}
//	}
//	var userimei = plus.device.imei;
//	appsystem=plus.os.name;
//	visitoncode=plus.runtime.version;
//	
//	UserInfo.web_query('/loginof.html', {'username':username,'passwd':password,'hash':1,'loginflag':1,'appid':clientid}, UserInfo.onSuccess, UserInfo.onError, 3);
});
