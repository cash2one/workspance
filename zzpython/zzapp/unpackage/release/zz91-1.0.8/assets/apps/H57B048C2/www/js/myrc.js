//生意管家供求列表
function myrcproducts(frm,url){
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	proid="0"
	for (var i = 0; i < frm.length; i++) {
		var objinput = frm[i];
		var objtype = objinput.type;
		var objvalue = objinput.value;
		var objname = objinput.name;
		
		if(objname!=""){
			if (objinput.type == "checkbox") {
				if (objinput.checked == true) {
					proid += "," + objvalue;
				}
			} 
		}
	}
	var arg={
		company_id:company_id,
		proid:proid
	};
	mui.post(url, arg,function(data) {
		var j=JSON.parse(data);
		var err=j.err;
		var errkey=j.errkey;
		if (err == "true") {
			plus.ui.alert(errkey);
		} else {
			if (j.type){
				//供求刷新
				//if (j.type=="proreflush"){
					loaddata(nowherf);
				//}
			}
		}
		if (nWaiting) {
			nWaiting.close();
		}
	});
	return false;
}
function editpro(frm,url){
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting();
	}
	var arg="";
	arg += "proid="
	for (var i = 0; i < frm.length; i++) {
		var objinput = frm[i];
		var objtype = objinput.type;
		var objvalue = objinput.value;
		var objname = objinput.name;
		
		if(objname!=""){
			if (objinput.type == "checkbox") {
				if (objinput.checked == true) {
					arg +=  objvalue;
					gotourl('/products_update/?'+arg,'blank');
					if (nWaiting) {
						nWaiting.close();
					}
					break
				}
			} 
		}
		
	}
	if (nWaiting) {
		nWaiting.close();
	}
}

//删除邀请回复
function mycommunitydel(post_id,ptype,pid){
	document.getElementById("pid").value=pid;
	document.getElementById("ptype").value=ptype;
	document.getElementById("post_id").value=post_id;
	openoverlay('', '确实要删除吗？', 0, 200, '.mycommunitydel');
}