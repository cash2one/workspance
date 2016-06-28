Ext.namespace("ast.ast1949.admin.productsPic");
//定义一个添加,继承自父窗体类

ast.ast1949.admin.productsPic.AddFormWin = function(_grid){
	//alert(ProductsPic.productId);
//	alert(ProductsPic.albumId);
	var _productId=ProductsPic.productId
	var title = "添加图片信息";
	var url = Context.ROOT+Context.PATH + "/admin/productspic/add.htm?productId="+_productId;
	var isView = true;
	var notView = false;
	var win = new ast.ast1949.admin.productsPic.InfoFormWin({
		title:title,
		view:isView,
		nView:notView,
		listeners:{
			"saveSuccess" : onSaveSuccess,
			"saveFailure" : onSaveFailure,
			"submitFailure" : onSubmitFailure
		}
	});

	win.show();
	win.initFocus();

	Ext.get("save").on("click",function(){
	
		win.submit(url);
	});

	Ext.get("cancel").on("click",function(){
	
		win.close();
	});

	function onSaveSuccess(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "图片信息已保存",
			buttons:Ext.MessageBox.OK,
			fn:closeMe,
			icon:Ext.MessageBox.INFO
		});
		closeMe();
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "图片信息保存失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

	function onSubmitFailure(){
//		alert("system");
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "验证失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

	function closeMe(){
		_grid.getStore().reload();
		win.close();
	}

}