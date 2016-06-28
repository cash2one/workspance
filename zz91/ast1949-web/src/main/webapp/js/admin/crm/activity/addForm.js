Ext.namespace("ast.ast1949.admin.crm.activity");
//定义一个添加,继承自父窗体类

ast.ast1949.admin.crm.activity.AddFormWin = function(_grid){
	var title = "添加信息";
	var url = Context.ROOT+Context.PATH + "/admin/crm/activity/add.htm";
	var isView = true;
	var notView = false;
//	alert(Math.random())
	var win = new ast.ast1949.admin.crm.activity.InfoFormWin({
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
	win.maximize();//窗口最大化，先要show();
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
			msg : "信息已保存",
			buttons:Ext.MessageBox.OK,
			fn:closeMe,
			icon:Ext.MessageBox.INFO
		});
		closeMe();
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "信息保存失败",
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
		_grid.getStore().reload();
		win.close();
	}

}