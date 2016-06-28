Ext.namespace("ast.ast1949.admin.keywordsrank");
//定义一个添加,继承自父窗体类
ast.ast1949.admin.keywordsrank.addKeywordSrankFormWin = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

//	Ext.apply(this,_cfg);

	var _productId = this["productId"] || "";
	var _productTitle = this["productTitle"] || "";
	var _grid = this["grid"] || "";

	var title = "添加关键字";
	var url = Context.ROOT+Context.PATH + "/admin/keywordsrank/addKeywordsRank.htm";
	var isView = true;
	var notView = false;
	var keywordsrankWin = new ast.ast1949.admin.keywordsrank.infoKeywordSrankForm({
		title:title,
		productId:_productId,
		productTitle:_productTitle,
		view:isView,
		nView:notView,
		listeners:{
			"saveSuccess" : onSaveSuccess,
			"saveFailure" : onSaveFailure,
			"submitFailure" : onSubmitFailure
		}
	});

	keywordsrankWin.show();
	keywordsrankWin.maximize();


	Ext.get("savekeywordsrank").on("click",function(){
		keywordsrankWin.submit(url);
	});

	Ext.get("cancel").on("click",function(){
		keywordsrankWin.close();
	});

	function onSaveSuccess(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "信息添加成功",
			buttons:Ext.MessageBox.OK,
			fn:closeMe,
			icon:Ext.MessageBox.INFO
		});
		closeMe();
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "仓库信息保存失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

	function onSubmitFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "验证失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

	function closeMe(){
		Ext.getCmp("listKeywordSrank").getStore().load({});
	}

}