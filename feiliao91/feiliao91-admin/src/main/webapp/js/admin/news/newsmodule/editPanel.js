Ext.namespace("ast.ast1949.admin.news.newsmodule");

ast.ast1949.admin.news.newsmodule.editPanel = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);
	
	var _editPanel = this["editPanel"] || "editPanel";

	var title = "编辑模块";
	var isView = true;
	var notView = false;
	var panel = new ast.ast1949.admin.news.newsmodule.InfoFormPanel({
		title:title,
		view:isView,
		nView:notView,
		editPanel: _editPanel,
		listeners:{
			"saveSuccess" : onSaveSuccess,
			"saveFailure" : onSaveFailure,
			"submitFailure" : onSubmitFailure
		}
	});

	var _store = new Ext.data.JsonStore({
		root:"records",
		fields:[{name:"id",mapping:"id"},
			{name:"name",mapping:"name"},
			{name:"url",mapping:"url"},
			{name:"checked",mapping:"checked"}
			],
		url : Context.ROOT + Context.PATH + "/admin/news/newsmodule/getSimpleNewsModule.htm",
		autoLoad:true,
		listeners:{
			"datachanged": function(){
				var record = _store.getAt(0);
				if(record != null){
					panel.loadRecord(record);
				}
				else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载错误,请联系管理员!");
				}
			}
		}
	});

	function onSaveSuccess(){
		
		var _msg="信息保存成功"
		var form=Ext.getCmp("news-module-form");
		if(form.findById("id").getValue()==""||form.findById("id").getValue()=="0"){
			_msg="新增了一个模块："+form.findById("name").getValue();
		}
		
		var _tree=Ext.getCmp("news-module-tree");
		if(_tree!=null){
			var node=_tree.getSelectionModel().getSelectedNode();

			if(form.findById("id").getValue()==""||form.findById("id").getValue()=="0"){
				if(node==null){
					node=_tree.getRootNode();
				}else{
					if(form.findById("id").getValue()==""){
						node=_tree.getRootNode();
					}
				}
				
				node.leaf = false;
				_tree.getLoader().load(node,function(){
					node.expand();
				});
			} else {
				//重新载入节点
				node.setText(Ext.get("name").dom.value);
			}
		}
		Ext.getCmp("news-module-form").getForm().reset();
		
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : _msg,
			buttons:Ext.MessageBox.OK,
			fn:closeMe,
			icon:Ext.MessageBox.INFO
		});
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "信息模块保存失败，请检查您输入的模块是否已存在！",
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
	}

	return {panel:panel,store:_store};
}