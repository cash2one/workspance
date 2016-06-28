Ext.namespace("ast.ast1949.admin.descriptionTemplate");
// 定义一个添加,继承自父窗体类

ast.ast1949.admin.descriptionTemplate.AddFormWin = function(_cfg) {
	
	if (_cfg == null) {
		_cfg = {};
	}
	Ext.apply(this,_cfg);
	//var code = this["_code"] || "";
    var _grid = this["_grid"] || "";
	var title = "添加描述模板";
	var url = Context.ROOT + Context.PATH
			+ "/admin/descriptiontemplate/add.htm";
	var isView = true;
	var notView = false;
	var win = new ast.ast1949.admin.descriptionTemplate.InfoFormWin({
				title : title,
				view : isView,
				nView : notView,
				listeners : {
					"saveSuccess" : onSaveSuccess,
					"saveFailure" : onSaveFailure,
					"submitFailure" : onSubmitFailure
				}
			});

	win.show();
	win.initFocus();

	Ext.get("save").on("click", function() {
				win.submit(url);
			});

	Ext.get("cancel").on("click", function() {
				win.close();
			});

	function onSaveSuccess() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "描述模板已保存",
					buttons : Ext.MessageBox.OK,
					fn : closeMe,
					icon : Ext.MessageBox.INFO
				});
		closeMe();
	}

	function onSaveFailure() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "描述模板保存失败",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}

	function onSubmitFailure() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "验证失败",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}

	function closeMe() {
		_grid.getStore().reload();
		win.close();
	}

}