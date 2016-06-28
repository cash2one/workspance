Ext.namespace("ast.ast1949.admin.categoryproducts");

var CATEGORYPRODUCTS = new function(){
	this.CATEGORY_WIN = "categorywin";
	this.CATEGORY_TREE = "categorytree";
}

Ext.override(Ext.Element,{
      contains : function(el){
        try {
        	return !el ? false : Ext.lib.Dom.isAncestor(this.dom, el.dom ? el.dom : el);
        } catch(e) {
			return false;
    	}
	}
});

Ext.onReady(function() {

		var _url = Context.ROOT + "/zz91/admin/categoryproducts/edit.htm";

		var _mainCategoryGrid=ast.ast1949.admin.categoryproducts.subGridPanel({
			title:"主类别"
		});
		
		var _assistCategoryGrid=ast.ast1949.admin.categoryproducts.subGridPanel({
			title:"辅助类别"
		});

		var _main={
			region:"north",
			height:250,
			layout:"fit",
			items:[_mainCategoryGrid]
		}
		
		var _south ={
			region:"center",
			layout:"fit",
			items:[_assistCategoryGrid]
		};
		
		var _center = {
			region : "center",
			layout : "border",
			items : [_main,_south]
		};

		var _tree = new ast.ast1949.admin.categoryproducts.treePanel({
			id:CATEGORYPRODUCTS.CATEGORY_TREE,
			title : "类别管理",
			region : "west",
			collapsible:true,
			margins : "0 0 0 0",
			cmargins : "0 0 0 0",
			split : true,
			width : 210,
			maxSize : 240,
			minSize : 120,
			rootData:"",
			isAssist:'0',
			contextmenu: new Ext.menu.Menu( {
				id : "rightMenu",
				items : [{
					id : "menu1",
					text : "添加子类别",
					cls : "item-add",
					handler : function(menu) {
						ast.ast1949.admin.categoryproducts.createCategoryProductsWin();
					}
				},{
					id : "menu4",
					text : "添加关联",
					cls : "item-add",
					handler : function(menu) {
						var _code=Ext.get("node-selected").dom.value;
						var g=ast.ast1949.admin.categoryproducts.associateGridPanel({
							title:"关联关键字",
							code:_code
						});
						var win = new ast.ast1949.admin.categoryproducts.associateWin( {
							grid : g,
							title:"添加关联关键字"
						});
						win.show();
					}
				},{
					id : "menu2",
					text : "修改类别",
					cls : "item-edit",
					handler : function() {
						ast.ast1949.admin.categoryproducts.updateCategoryProductsWin();
					}
				},{
					id : "menu3",
					text : "删除分类",
					cls : "item-del",
					handler : function() {
						Ext.MessageBox.confirm(Context.MSG_TITLE,
								'您确定要删除该分类及其子分类吗?', doDelete);
					}
				},{
					id : "menu4",
					text : "推荐",
					cls : "item-edit",
					handler : function() {
					
						var code=Ext.get("node-selected").dom.value;
//						var _id = id.split("-")[1];
						var _title=Ext.get("node-selected-text").dom.value;
//						var _url="trade/offerlist_m"+_id+".htm";
						
//						alert(id+"    "+_title)
						ast.ast1949.admin.dataIndex.SendIndex({
							title:_title,
							link:"http://trade.zz91.com/offerlist--mc"+code+".htm"
						});
						
					}
				}]
			})
		});

		var viewport = new Ext.Viewport( {
			layout : "border",
			border : true,
			items : [  _tree,_center ]
		});


//		左侧树点击后加载右边表单数据
		_tree.on("click", function(node, event) {
			var pnode = node.parentNode;
			var _code = node.attributes["data"];

			_mainCategoryGrid.getStore().load( {
				params : {
					"code" : _code,
					"isAssist":0
				}
			});
			_assistCategoryGrid.getStore().load( {
				params : {
					"code" : _code,
					"isAssist":1
				}
			});
		});


//		// 右健菜单
		_tree.on("contextmenu", function(node, event) {
			event.preventDefault();
			node.select();
			Ext.get("node-selected").dom.value = node.attributes["data"];
			Ext.get("node-selected-id").dom.value = node.id;
			Ext.get("node-selected-text").dom.value = node.text;
			var pnode = node.parentNode;
			if(pnode!=null){
				Ext.get("parentnode-selected-edit").dom.value = pnode.attributes["data"];
				Ext.get("parentnode-selected-text-edit").dom.value = pnode.text;
			}

		});

		function doDelete(_btn) {
			if (_btn != "yes")
				return;
			var _id = Ext.get("node-selected-id").dom.value;
			_id = _id.split("-")[1];
			/* 提交删除 */
			var conn = new Ext.data.Connection();
			conn.request( {
				url : Context.ROOT + Context.PATH
						+ "/admin/categoryproducts/delete.htm?id=" + _id,
				method : "get",
				scope : this,
				callback : function(options, success, response) {
					if (success) {

						var node = _tree.getNodeById(Ext
								.get("node-selected-id").dom.value);

						node.remove();
					} else {
						Ext.MessageBox.alert(Context.MSG_TITLE, "所选记录删除失败!");
					}
				}
			});
		}

		function doDeleteProduct(_btn) {
		}
	// 更新菜单
});

ast.ast1949.admin.categoryproducts.createCategoryProductsWin = function(){
	var form = new ast.ast1949.admin.categoryproducts.categoryProductsForm();
	form.initParentForAdd();

	var win = new Ext.Window({
		id:CATEGORYPRODUCTS.CATEGORY_WIN,
		title:"添加产品类别",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});

	win.show();
}

ast.ast1949.admin.categoryproducts.updateCategoryProductsWin = function(){
	var form = new ast.ast1949.admin.categoryproducts.categoryProductsForm({
		saveUrl:Context.ROOT+Context.PATH+"/admin/categoryproducts/updateCategoryProducts.htm"
	});

	var tree = Ext.getCmp(CATEGORYPRODUCTS.CATEGORY_TREE);
	var node = tree.getSelectionModel().getSelectedNode();
	var _id = node.attributes["id"];
	_id=_id.split("-")[1];

	form.loadOneRecord(_id);
	form.initParentForUpdate();

	var win = new Ext.Window({
		id:CATEGORYPRODUCTS.CATEGORY_WIN,
		title:"修改产品类别",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});

	win.show();
}

ast.ast1949.admin.categoryproducts.categoryProductsForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config || {};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 80,
//			region:"center",
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				id:"categoryId",
				name:"id"
			},{
				xtype:"hidden",
				id:"preCode",
				name:"preCode"
			},{
				xtype:"textfield",
				id:"parent",
				readOnly:true,
				fieldLabel:"父类别"
			},{
				id:"label",
				name:"label",
				allowBlank:false,
				itemCls:"required",
				fieldLabel:"类别名称"
			},{
				id:"cnspell",
				name:"cnspell",
				vtype:"alphanum",
				fieldLabel:"拼音首字母"
			},{
				xtype:"checkbox",
				id:"isAssist",
				name:"isAssist",
				fieldLabel:"辅助类别",
				inputValue:1
			}],
			buttons:[{
				text:"保存",
				handler:this.saveForm,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(CATEGORYPRODUCTS.CATEGORY_WIN).close();
				}
			}]
		};

		ast.ast1949.admin.categoryproducts.categoryProductsForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/categoryproducts/createCategoryProducts.htm",
	saveForm:function(btn){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "表单填写有错误，请仔细检查一下表单",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	loadOneRecord:function(id){
		// TODO 初始化参数表单
		var _fields = ["id","label","isAssist","cnspell"];

		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT +Context.PATH + "/admin/categoryproducts/init.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据载入错误...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	},
	onSaveSuccess:function(_form,_action){
//			Ext.getCmp(_C.PERIODICAL_GRID).getStore().reload();
			Ext.getCmp(CATEGORYPRODUCTS.CATEGORY_WIN).close();
			//TODO 树结点重新加载
			var tree = Ext.getCmp(CATEGORYPRODUCTS.CATEGORY_TREE);
			var node = tree.getSelectionModel().getSelectedNode();
			if(this.findById("categoryId").getValue() > 0){
				node.setText(this.findById("label").getValue());
			}else{
				node.leaf= false;
				tree.getLoader().load(node,function(){
					node.expand();
				});
			}
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	},
	initParentForAdd:function(){
		var tree = Ext.getCmp(CATEGORYPRODUCTS.CATEGORY_TREE);
		var node = tree.getSelectionModel().getSelectedNode();
		this.findById("preCode").setValue(node.attributes.data);
		this.findById("parent").setValue(node.text);
	},
	initParentForUpdate:function(){
		var tree = Ext.getCmp(CATEGORYPRODUCTS.CATEGORY_TREE);
		var node = tree.getSelectionModel().getSelectedNode();
		this.findById("parent").setValue(node.parentNode.text);
	}
})