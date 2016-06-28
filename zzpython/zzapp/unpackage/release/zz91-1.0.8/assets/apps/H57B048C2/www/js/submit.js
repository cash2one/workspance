//通用表单提交
function submitfrm(frm, url) {
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting("数据上传中......");
	}
	if (company_id==0 && url!="http://app.zz91.com/regsave.html" && url!="http://app.zz91.com/feedbacksave.html"){
		if (rt){
			if (nWaiting) {
				nWaiting.close();
			}
			loaddata("http://app.zz91.com/login/?tourl="+rt);
		}else{
			if (nWaiting) {
				nWaiting.close();
			}
			gotourl(nowurl, nowwintype);
		}
		return false;
	}
	var arg = {
		company_id:company_id,
		clientid:clientid,
		appsystem:appsystem,
		t:Math.ceil(new Date/3600000)
	}
	var checkflag=0;
	var checklist=""
	for (var i = 0; i < frm.length; i++) {
		var objinput = frm[i];
		var objtype = objinput.type;
		var objvalue = objinput.value;
		var objname = objinput.name;
		if(objname!=""){
			if (objinput.type == "radio" || objinput.type == "checkbox") {
				if (objinput.checked == true) {
					if (checklist.indexOf(objname)<0){
						checkflag=0;
					}
					if (checkflag==1){
						checklist += "," + objvalue;
					}else{
						checklist += "&" + objname + "=" + objvalue;
					}
				}
				checkflag=1;
			} else {
				checkflag=0;
				//arg += "&" + objname + "=" + objvalue;
				arg[objname]=objvalue
			}
		}
		
		var arrchecklist=checklist.split("&");
		if (arrchecklist.length>1){
			for (var a=1;a<arrchecklist.length;a++){
				if (arrchecklist[a]){
					var arrchecklist1=arrchecklist[a].split("=");
					var checkname=arrchecklist1[0];
					var checkvalue=arrchecklist1[1];
					arg[checkname]=checkvalue;
				}
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
	
	mui.post(url, arg,function(data) {
		var j=JSON.parse(data);
		var err=j.err;
		var errkey=j.errkey;
		if (err == "true") {
			if (j.type=="regerr"){
				var errflag=j.errflag;
				var errname=j.errname;
				$(".c-red").html("");
				$("#regerr"+errflag).html(errkey);
				document.getElementById(errname).focus();
			}else{
				if (j.type=="forgetpasswd"){
					$(".c-red").html(errkey);
				}else{
					plus.ui.alert(errkey);
				}
			}
		} else {
			if (j.type){
				//发布供求成功提醒
				if (j.type=="tradesave" || j.type=="questionback" || j.type=="leavewords" || j.type=="posthuzhu"){
					openoverlay('', '提示', 0, 250, '.postsuc');
				}else{
					if(j.type=="chongzhi"){
						var payload=j.content;
						var furl="http://app.zz91.com/zz91paysubmit.html?"+payload
						//plus.runtime.openURL(furl);
					}else{
						if (j.type=="regsuc"){
							plus.ui.alert('注册成功！');
							company_id=j.company_id;
							plus.storage.setItem("company_id",company_id);
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
								closewindow();
							}
						}else{
							
							if (j.type=="forgetpasswd"){
								var result=j.result;
								var step=j.step;
								var mobile=result.mobile;
								var account=result.account;
								loaddata("http://app.zz91.com/forgetpasswdpage.html?mobile="+mobile+"&account="+account+"&clientid="+clientid+"&step="+step);
							}else{
								if (j.type=="infosave"){
									plus.ui.alert(j.errkey);
									if (nWaiting) {
										nWaiting.close();
									}
								}else{
									if (j.type=="savecollect"){
										
									}else{
										closeoverlay();
										loaddata(nowherf);
									}
									
								}
							}
							
						}
					}
				}
			}else{
				
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
}
//重置密码成功后自动登陆
function forgetpasswdload(cid){
	company_id=cid;
	if (window.plus) {
		var wobj = plus.webview.currentWebview();
		if (wobj) {
			var opener = wobj.opener();
			if (opener) {
				opener.evalJS('regloadbody('+company_id.toString()+');');
			}
			var parent = wobj.parent();
			if (parent) { //又得evalJS
				parent.evalJS('loadappbody();');
			}
		}
	}
	closewindow();
}
function requestquery(furl,querylist){
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	var arg = {
		company_id:company_id,
		clientid:clientid,
		appsystem:appsystem
	};
	//var arg="company_id="+company_id.toString()+"&clientid="+clientid+"&appsystem="+appsystem+querylist
	var arrchecklist=querylist.split("&");
	if (arrchecklist.length>1){
		for (var a=1;a<arrchecklist.length;a++){
			if (arrchecklist[a]){
				var arrchecklist1=arrchecklist[a].split("=");
				var checkname=arrchecklist1[0];
				var checkvalue=arrchecklist1[1];
				arg[checkname]=checkvalue;
			}
		}
	}
	mui.post(furl, arg,function(data) {
		var j=JSON.parse(data);
		var err=j.err;
		var errkey=j.errkey;
		if (err == "true") {
			if (j.type=="viewcontact"){
				closeoverlay();
				openfloatdiv(event,'bal');
				return false;
			}else{
				plus.ui.alert(errkey);
			}
			
		} else {
			if (j.type){
				switch(j.type){
					//发布供求成功提醒
					case "favorite":
						openoverlay('', '提示', 0, 200, '.favorite');
						break;
					case "viewcontact":
						loaddata(nowherf);
						closeoverlay();
						break;
					case "pro_report":
						closeoverlay();
						openfloatdiv(event,'tippOff');
						break;
					default:
						closeoverlay();
				}
			}
		}
		if (nWaiting) {
			nWaiting.close();
		}
	});
	return false;
}
function chongzhi(furl){
	if (appsystem=="iOS"){
		requestquery("http://app.zz91.com/chongzhi.html","");
		plus.ui.alert("我们已将支付方式以短信和邮件的方式发送给您，请注意查收！")
	}else{
		gotourl(furl,'blank');
	}
}
function dialtel(telphone) {
	plus.device.dial( telphone, false );
	//telclick("http://m.zz91.com/trade/telclicklog.html?tel="+telphone+"&pagefrom=apptrade");
}
function jubao(furl,querylist){
	var chk_value =[];
	$('input[name="report"]:checked').each(function(){    
		chk_value.push($(this).val());    
	});
	if (chk_value==[]){
		plus.ui.alert("请选择你要举报的类目！");
		return false;
	}
	requestquery(furl,querylist+"&chk_value="+chk_value);
}

