Ext.ns("ast.ast1949.bbs.bbsCategory");

var BBSCATEGORY={
	TREE:"bbscategorytree",
}

Ext.define("BbsPostCategoryModel",{
	extend:"Ext.data.Model",
	fields:["id","parentId","name"],
	idProperty:"idcode",
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/bbs/bbscategory/querySimpleCategoryById.htm"
		}
	}
});

Ext.define("ast.ast1949.bbs.bbsCategory.CategoryAction",{
	extend:"Ext.menu.Menu",
	initComponent:function(){
		
		var c={
			items:[{
				iconCls:"add16",
				text:"增加类别",
				scope:this,
				handler:function(btn,e){
					
					var form=Ext.create("ast.ast1949.bbs.bbsCategory.CategoryForm",{
						saveUrl:Context.ROOT+"/zz91/bbs/bbscategory/addCategory.htm",
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
					
					var form=Ext.create("ast.ast1949.bbs.bbsCategory.CategoryForm",{
						saveUrl:Context.ROOT+"/zz91/bbs/bbscategory/update.htm",
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
							url: Context.ROOT+"/zz91/bbs/bbscategory/delete.htm",
							params:{"id":node.getId()},
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

Ext.define("ast.ast1949.bbs.bbsCategory.CategoryForm",{
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
				name:"parentId",
				id:"parentId"
			},{
				fieldLabel : '父类别',
				name : 'parentName',
				id : 'parentName',
				readOnly:true
			},{
				fieldLabel : '名称',
				name : 'name',
				id : 'name',
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
							node.set("text",form.child("#name").getValue());
						}else{
							if(node.isLoaded()){
								node.appendChild({
									id:action.result.data,
									leaf:true,
									text:form.child("#name").getValue()
								});
								node.expand();
							}else{
								node.set("leaf",false);
								Ext.getCmp(BBSCATEGORY.TREE).getStore().load({
									node:node,
									callback: function(records, operation, successful){
										operation.node.expand();
									}
								});
							}
						}
						
						win.close();
					}
					win.close();
				},
				failure: function(f, action) {
					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，类别没有更新！");
				}
			});
		
		}
	},
	initParentForAdd:function(nodeInterface){
		
		this.getChildByElement("parentId").setValue(nodeInterface.getId());
		this.getChildByElement("parentName").setValue(nodeInterface.data["text"]);
	},
	initParentForUpdate:function(nodeInterface){
		var _this=this;
		
		Ext.ModelMgr.getModel('BbsPostCategoryModel').load(nodeInterface.getId(), {
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

