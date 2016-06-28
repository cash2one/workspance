Ext.namespace("ast.ast1949.admin.categoryproducts");

ast.ast1949.admin.categoryproducts.editWin = Ext.extend(Ext.Window, {
	_panel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.apply(this, _cfg);

		var _title = this["title"] || "";
		this._panel = this["panel"] || null;

		ast.ast1949.admin.categoryproducts.editWin.superclass.constructor.call(
				this, {
					id:"editWin",
					title : _title,
					closeable : true,
					width : 680,
					autoHeight : true,
					//height : 480,
					modal : true,
					border : false,
					plain : true,
					layout : "fit",
					items : [ this._panel ]
				});

		this.addEvents("saveSuccess", "saveFailure", "submitFailure");
	},
	submit : function(_url) {
		if (this._form.getForm().isValid()) {
			this._form.getForm().submit( {
				url : _url,
				method : "post",
				success : this.onSaveSuccess,
				failure : this.onSaveFailure,
				scope : this
			});
		} else {
			Ext.MessageBox.show( {
				title : Context.MSG_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
		}
	},
	loadRecord : function(_record) {
		this._form.getForm().loadRecord(_record);
	},
	onSaveSuccess : function(_form, _action) {
		this.fireEvent("saveSuccess", _form, _action, _form.getValues());
	},
	onSaveFailure : function(_form, _action) {

	this.fireEvent("saveFailure", _form, _action, _form.getValues());
}
});
ast.ast1949.admin.categoryproducts.editPanel = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);


	var _editPanel = this["editPanel"] || "editPanel";


	var title = "编辑关联信息";
//	var _url = Context.ROOT + "/zz91/admin/category/update.htm";
	var isView = true;
	var notView = false;
	var panel = new ast.ast1949.admin.categoryproducts.InfoFormPanel({
		title:title,
		view:isView,
		nView:notView,
		editPanel: _editPanel,
//		url : _url,
		listeners:{
			"saveSuccess" : onSaveSuccess,
			"saveFailure" : onSaveFailure,
			"submitFailure" : onSubmitFailure
		}
	});

//	panel.show();
//	panel.initFocus();

	var _store = new Ext.data.JsonStore({
		root:"records",
		fields:[{name:"id",mapping:"id"},
			{name:"label",mapping:"label"},
			{name:"showIndex",mapping:"showIndex"}
			],
		url : Context.ROOT + Context.PATH + "/admin/categoryproducts/init.htm",
//		autoLoad:true,
		listeners:{
			"datachanged": function(){
				var record = _store.getAt(0);
//				alert(record)
				if(record == null){
					Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载错误,请联系管理员!");
//					win.close();
				}else{

					panel.loadRecord(record);
				}
			}
		}
	});

	function onSaveSuccess(){
		var _msg="菜单信息修改成功";
		var form=Ext.getCmp("category-form");//
		if(form.findById("categoryId").getValue() == ""){
			_msg="新增了一个菜单:"+form.findById("label").getValue();
		}

		var _tree=Ext.getCmp("category-tree");
		if(_tree!=null){
			var node = _tree.getSelectionModel().getSelectedNode();
			if(form.findById("categoryId").getValue() == ""){
				  if(node==null){
                    node=_tree.getRootNode();
                }
				node.leaf = false;
				_tree.getLoader().load(node,function(){
					node.expand();
				});
			}else{
				node.setText(Ext.get("label").dom.value);
			}
		}
		Ext.getCmp("category-form").getForm().reset();

		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : _msg,
			buttons:Ext.MessageBox.OK,
			fn:function(){
			},
			icon:Ext.MessageBox.INFO
		});
		Ext.getCmp("editWin").close();

	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "后台菜单信息保存失败",
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
//		_grid.getStore().reload();
//		win.close();
	}

	return {panel:panel,store:_store};
}