Ext.namespace("ast.ast1949.admin.category");

ast.ast1949.admin.category.editPanel = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);


	var _editPanel = this["editPanel"] || "editPanel";


	var title = "编辑菜单信息";
//	var _url = Context.ROOT + "/zz91/admin/category/update.htm";
	var isView = true;
	var notView = false;
	var panel = new ast.ast1949.admin.category.InfoFormPanel({
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
		url : Context.ROOT + Context.PATH + "/admin/category/init.htm",
		autoLoad:true,
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

		var _tree=Ext.getCmp("aTree");
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