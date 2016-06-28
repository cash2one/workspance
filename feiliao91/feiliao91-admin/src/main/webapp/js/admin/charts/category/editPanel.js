Ext.namespace("ast.ast1949.admin.charts.category");

ast.ast1949.admin.charts.category.editPanel = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);
	
	var _editPanel = this["editPanel"] || "editPanel";

	var title = "编辑类别";
	var isView = true;
	var notView = false;
	var panel = new ast.ast1949.admin.charts.category.InfoFormPanel({
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
		fields:[{name:"id",mapping:"chartCategory.id"},
			{name:"name",mapping:"chartCategory.name"},
			{name:"setting",mapping:"chartCategory.setting"},
			{name:"showIndex",mapping:"chartCategory.showIndex"},
			{name:"showInHome",mapping:"chartCategory.showInHome"},
			{name:"relevanceId",mapping:"relevanceId",convert:function(value){
				if(value=="0") {
					return ""
				} else {
					return value;
				}
			}},
			{name:"relevanceId1",mapping:"chartCategory.relevanceId"},
			{name:"relevanceName",mapping:"relevanceName"}
			],
		url : Context.ROOT + Context.PATH + "/admin/charts/getSimpleChartsCategory.htm",
		autoLoad:true,
		listeners:{
			"datachanged": function(){
				var record = _store.getAt(0);
				if(record != null){
					panel.loadRecord(record);
					
					var f=panel._form;
					f.findById("combo-relevanceId").setValue(record.get("relevanceName"));
					if(record.get("relevanceId1")!="0"){
						Ext.get("relevanceId").dom.value = record.get("relevanceId1");
					} else {
						Ext.get("relevanceId").dom.value="";
					}
				}
				else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载错误,请联系管理员!");
				}
			}
		}
	});

	function onSaveSuccess(){
		
		var _msg="信息保存成功"
		var form=Ext.getCmp("charts-category-form");
		if(form.findById("id").getValue()==""||form.findById("id").getValue()=="0"){
			_msg="新增了一个类别："+form.findById("name").getValue();
		}
		
		var _tree=Ext.getCmp("charts-category-tree");
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
		Ext.getCmp("charts-category-form").getForm().reset();
		Ext.get("showIndex").dom.value="0";
//		alert(Ext.get("showIndex").dom.value);
		
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
			msg : "信息类别保存失败，请检查您输入的类别是否已存在！",
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