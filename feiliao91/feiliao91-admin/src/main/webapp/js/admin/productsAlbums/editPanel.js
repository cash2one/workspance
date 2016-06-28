Ext.namespace("ast.ast1949.admin.productsAlbums");

ast.ast1949.admin.productsAlbums.editPanel = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);
	
	var _editPanel = this["editPanel"] || "editPanel";

	var title = "编辑相册类别";
	var isView = true;
	var notView = false;
	var panel = new ast.ast1949.admin.productsAlbums.InfoFormPanel({
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
			{name:"name",mapping:"name"}
			],
		url : Context.ROOT + Context.PATH + "/admin/productsalbums/init.htm",
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
		
		var _msg="相册类别信息保存成功"
		var form=Ext.getCmp("productsAlbums-form");
		if(form.findById("productsAlbumsId").getValue()==""||form.findById("productsAlbumsId").getValue()=="0"){
			_msg="新增了一个相册类别："+form.findById("name").getValue();
		}
		
		var _tree=Ext.getCmp("productsAlbums-tree");
		if(_tree!=null){
			var node=_tree.getSelectionModel().getSelectedNode();

			if(form.findById("productsAlbumsId").getValue()==""||form.findById("productsAlbumsId").getValue()=="0"){
				if(node==null){
					node=_tree.getRootNode();
				}else{
					if(form.findById("productsAlbumsId").getValue()==""){
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
		Ext.getCmp("productsAlbums-form").getForm().reset();
		
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
			msg : "信息类别保存失败，请检查您输入的组名是否已存在！",
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