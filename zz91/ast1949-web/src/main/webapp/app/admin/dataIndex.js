Ext.ns("ast.ast1949.admin.dataIndex");

var DATAINDEX={
	TREE:"dataindextree",
	GRID:"dataindexgrid"
}

Ext.define("DataIndexModel",{
	extend:"Ext.data.Model",
	fields:["id","title","categoryCode","link","style","pic","sort","categoryName"]
});

Ext.define("DataIndexFormModel",{
	extend:"Ext.data.Model",
	fields:[{name:"id",mapping:"dataIndexDO.id"},
		{name:"title",mapping:"dataIndexDO.title"},
		{name:"categoryCode",mapping:"dataIndexDO.categoryCode"},
		{name:"link",mapping:"dataIndexDO.link"},
		{name:"style",mapping:"dataIndexDO.style"},
		{name:"pic",mapping:"dataIndexDO.pic"},
		{name:"sort",mapping:"dataIndexDO.sort"},
		{name:"categoryName",mapping:"categoryName"}
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/admin/dataindex/initDataIndex.htm"
		}
	}
});

Ext.define("DataIndexCategoryModel",{
	extend:"Ext.data.Model",
	fields:["id","label","code"],
	idProperty:"idcode",
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/admin/dataindex/initCategory.htm"
		}
	}
});

Ext.define("ast.ast1949.admin.dataIndex.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"DataIndexModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/zz91/admin/dataindex/queryDataIndex.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				sortParam:"pagesort",
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[
			{text:"编号",dataIndex:"id",width:50,hidden:true},
			{text:"code",dataIndex:"categoryCode",width:150},
			{header:"标题",dataIndex:"title",width:250,renderer:function(v,m,record,ridx,cidx,store,view){
				if(record.get("pic").length>0){
					return Ext.String.format("<img src='{0}' width='80' height='50' /><br />{1}",Context.RESOURCES+record.get("pic"),v);
				}
				return v;
			}},
			{text:"连接地址",dataIndex:"link",width:350,renderer:function(v){
				if(v!="#" && v!=""){
					return Ext.String.format("<a href='{0}' target='_blank'>{0}</a>",v);
				}
				return v;
			}},
			{text:"排序号",dataIndex:"sort"}
		];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			tbar:[{
				xtype:"button",
				iconCls:"add16",
				text:"添加",
				handler:this.createModel
			},{
				xtype:"button",
				iconCls:"edit16",
				text:"修改",
				scope:this,
				handler:this.editModel
			},{
				xtype:"button",
				iconCls:"delete16",
				text:"删除",
				scope:this,
				handler:function(btn,e){
					this.deleteModel(this.getSelectionModel().getSelection());
				}
			}],
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	deleteModel:function(selections){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Msg.confirm(Context.MSG_TITLE,"信息删除后将无法恢复，您确定要删除这些信息吗？",function(o){
			if(o!="yes"){
				return ;
			}
			Ext.Array.each(selections, function(obj, index, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/zz91/admin/dataindex/deleteIndex.htm",
					params: {
						id: obj.getId()
					},
					success: function(response){
						_this.getStore().load();
					}
				});
			});
		});
	},
	createModel:function(btn,e){
		var form=Ext.create("ast.ast1949.admin.dataIndex.MainForm",{
			region:"center"
		});
		
		form.initCategoryCode();
		
		var win= Ext.create("Ext.Window",{
			layout : 'border',
			iconCls : "add16",
			width : 450,
			autoHeight:true,
			title : "增加信息",
			modal : true,
			items : [ form ]
		});

		win.show();
		
		form.initField();
	},
	editModel:function(btn,e){
		var rowModel=this.getSelectionModel().getLastSelected();
		
		var form=Ext.create("ast.ast1949.admin.dataIndex.MainForm",{
			saveUrl:Context.ROOT+"/zz91/admin/dataindex/update.htm",
			region:"center"
		});
		
		var win= Ext.create("Ext.Window",{
			layout : 'border',
			iconCls : "edit16",
			width : 450,
			autoHeight:true,
			title : "修改信息",
			modal : true,
			items : [ form ]
		});

		win.show();
		
		form.loadModel(rowModel.getId());
	}
});

Ext.define("ast.ast1949.admin.dataIndex.CategoryAction",{
	extend:"Ext.menu.Menu",
	initComponent:function(){
		
		var c={
			items:[{
				iconCls:"add16",
				text:"增加类别",
				scope:this,
				handler:function(btn,e){
					
					var form=Ext.create("ast.ast1949.admin.dataIndex.CategoryForm",{
						region:"center",
						treeNode:this.getTreeNode()
					});
					
					var win= Ext.create("Ext.Window",{
						layout : 'border',
						iconCls : "add16",
						width : 350,
						autoHeight:true,
						title : "增加类别",
						modal : true,
						items : [ form ]
					});

					win.show();
					
					form.initParentForAdd(this.getTreeNode());
				}
			},{
				iconCls:"edit16",
				text:"修改类别",
				scope:this,
				handler:function(btn,e){
					if(this.getTreeNode().isRoot()){
						return false;
					}
					
					var form=Ext.create("ast.ast1949.admin.dataIndex.CategoryForm",{
						saveUrl:Context.ROOT+"/zz91/admin/dataindex/updateCategory.htm",
						region:"center",
						treeNode:this.getTreeNode()
					});
					
					var win= Ext.create("Ext.Window",{
						layout : 'border',
						iconCls : "add16",
						width : 350,
						autoHeight:true,
						title : "修改类别",
						modal : true,
						items : [ form ]
					});

					win.show();
					
					form.initParentForUpdate(this.getTreeNode());
				}
			},{
				iconCls:"delete16",
				text:"删除类别",
				scope:this,
				handler:function(btn,e){
					var node=this.getTreeNode();
					Ext.Msg.confirm(Context.MSG_TITLE,"类别删除后将无法恢复（包括子类别），您确定要删除这些类别吗？",function(o){
						if(o!="yes"){
							return ;
						}
						Ext.Ajax.request({
							url: Context.ROOT+"/zz91/admin/dataindex/deleteCategory.htm",
							params:{"code":node.getId()},
							success: function(response){
								node.remove(true);
							}
						});
					});
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	config:{
		treeNode:null
	}
});

Ext.define("ast.ast1949.admin.dataIndex.CategoryForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		
		var c={
			border: 0,
			bodyPadding: 5,
			fieldDefaults: {
				labelAlign: 'right',
				labelWidth: 60
			},
			layout:{
				type:"vbox",
				align:"stretch"
			},
			defaults:{
				anchor:'99%',
				xtype : 'textfield'
			},
			items : [ {
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				xtype:"hidden",
				name:"parentCode",
				id:"parentCode"
			},{
				fieldLabel : '父类别',
				name : 'parentName',
				id : 'parentName',
				readOnly:true
			}, {
				fieldLabel : 'code',
				readOnly:true,
				name : 'code',
				id : 'code'
			}, {
				fieldLabel : '名称',
				name : 'label',
				id : 'label',
				allowBlank : false,
				formItemCls:"x-form-item required"
			} ],
			buttons:[{
				iconCls:"saveas16",
				text:"保存",
				handler:this.saveModel
			},{
				iconCls:"close16",
				text:"关闭",
				handler:function(btn,e){
					this.up("window").close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	saveUrl:Context.ROOT+"/zz91/admin/dataindex/addCategory.htm",
	saveModel:function(btn, e){
		
		var win=this.up("window");
		var form=this.up("form");
		var node=form.getTreeNode();
		
		if(form.getForm().isValid()){
			//保存
			form.getForm().submit({
				url:form.getSaveUrl(),
				success: function(f, action) {
					if(action.result.success){
						if(form.child("#id").getValue()>0){
//							node.updateInfo({
//								text:form.child("#label").getValue()
//							});
							node.set("text",form.child("#label").getValue());
						}else{
							if(node.isLoaded()){
								node.appendChild({
									id:action.result.data,
									leaf:true,
									text:form.child("#label").getValue()
								});
								node.expand();
							}else{
								node.set("leaf",false);
								Ext.getCmp(DATAINDEX.TREE).getStore().load({
									node:node,
									callback: function(records, operation, successful){
										operation.node.expand();
									}
								});
							}
						}
						win.close();
					}
				},
				failure: function(f, action) {
					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，类别没有更新！");
				}
			});
		
		}
	},
	initParentForAdd:function(nodeInterface){
		this.getChildByElement("parentCode").setValue(nodeInterface.getId());
		this.getChildByElement("parentName").setValue(nodeInterface.data["text"]);
	},
	initParentForUpdate:function(nodeInterface){
		var _this=this;
		
		Ext.ModelMgr.getModel('DataIndexCategoryModel').load(nodeInterface.getId(), {
			success: function(model) {
				_this.loadRecord(model);
			}
		});
		
		this.getChildByElement("parentName").setValue(nodeInterface.parentNode.data["text"]);
		
	},
	config:{
		saveUrl:null,
		treeNode:null
	}
});

Ext.define("ast.ast1949.admin.dataIndex.MainForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			border: 0,
			bodyPadding: 5,
			fieldDefaults: {
				labelAlign: 'right',
				labelWidth: 80
			},
			layout:{
				type:"vbox",
				align:"stretch"
			},
			defaults:{
				anchor:'99%',
				xtype : 'textfield'
			},
		    items : [ {
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				xtype:"hidden",
				name:"categoryCode",
				id:"categoryCode"
			},{
				fieldLabel : '选择类别',
				name : 'categoryName',
				id : 'categoryName',
				formItemCls:"x-form-item required",
				listeners:{
					"focus":function(field,e){
						var initCode=Ext.getCmp("categoryCode").getValue();
						var win=Ext.create("ast.ast1949.util.TreeSelectorWin",{
							height:500,
							width:300,
							modal:true,
							initCode:initCode,
							queryUrl:Context.ROOT+"/zz91/admin/dataindex/categoryChild.htm",
							callbackFn:function(nodeInterface){
								Ext.getCmp("categoryCode").setValue(nodeInterface.data.id);
								Ext.getCmp("categoryName").setValue(nodeInterface.data.text);
								this.close();
							}
						});
						win.show();
						win.initTree(4);
					}
				}
			}, {
				xtype:"textarea",
				fieldLabel : '标题名称',
				name : 'title',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				fieldLabel : '连接地址',
				name : 'link',
				id : 'link',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				fieldLabel : '标题样式',
				name : 'style',
				id : 'style',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				xtype:"numberfield",
				fieldLabel : '排序(大>小)',
				name : 'sort'
			}, {
				fieldLabel : '图片',
				name : 'pic',
				listeners:{
					"focus":function(field,e){
						var win=Ext.create("ast.ast1949.util.UploadWin",{
							uploadUrl:Context.ROOT+"/zz91/common/doUpload.htm",
							callbackFn:function(form,action){
								field.setValue(action.result.data);
								win.close();
							}
						});
						win.show();
					}
				}
			} ],
			buttons:[{
				xtype:"button",
				text:"马上保存",
				iconCls:"saveas16",
				handler:this.saveModel
			},{
				iconCls:"close16",
				text:"关闭",
				handler:function(btn,e){
					this.up("window").close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('DataIndexFormModel').load(id, { // load user with ID of "1"
			success: function(model) {
				_this.loadRecord(model);
			}
		});
	},
	saveModel:function(btn,e){
		var form=this.up("form");
		var _url=form.getSaveUrl();
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					form.up("window").close();
					Ext.getCmp(DATAINDEX.GRID).getStore().load();
				},
				failure: function(f, action) {
					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，信息没有更新！");
				}
			});
		}
	},
	initField:function(){
		this.child("#style").setValue("color:black;font-size:12px");
		this.child("#link").setValue("#");
	},
	initCategoryCode:function(){
		var tree = Ext.getCmp(DATAINDEX.TREE);
		var node = tree.getSelectionModel();
		var code = node.lastSelected.data.id;
		var name = node.lastSelected.data.text;
		if(node!=null && code!=""){
			Ext.getCmp("categoryCode").setValue(code);
			Ext.getCmp("categoryName").setValue(name);
		}
	},
	saveUrl:Context.ROOT+"/zz91/admin/dataindex/add.htm",
	config:{
		saveUrl:null
	}
});
