Ext.ns("ast.ast1949.admin.category");

var CATEGORY={
	TREE:"categorytree"
}

Ext.define("CategoryFormModel",{
	extend:"Ext.data.Model",
	fields:[
		"id","label","showIndex"
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/admin/category/queryOne.htm"
		}
	}
});
		
Ext.define("ast.ast1949.admin.category.Action",{
	extend:"Ext.menu.Menu",
	initComponent:function(){
		
		var c={
			items:[{
				iconCls:"add16",
				text:"增加类别",
				scope:this,
				handler:function(btn,e){
					
					var form=Ext.create("ast.ast1949.admin.category.MainForm",{
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
//
					win.show();
//					
					form.initParent(this.getTreeNode());
				}
			},{
				iconCls:"edit16",
				text:"修改类别",
				scope:this,
				handler:function(btn,e){
					if(this.getTreeNode().isRoot()){
						return false;
					}
					
					var form=Ext.create("ast.ast1949.admin.category.MainForm",{
						saveUrl:Context.ROOT+"/zz91/admin/category/update.htm",
						region:"center",
						treeNode:this.getTreeNode()
					});
//					
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
					
					form.initParent(this.getTreeNode().parentNode);
					form.loadModel(this.getTreeNode().getId());
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
							url: Context.ROOT+"/zz91/admin/category/delete.htm",
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

Ext.define("ast.ast1949.admin.category.MainForm",{
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
				anchor:'100%',
				xtype : 'textfield'
			},
		    items : [ {
				xtype:"hidden",
				name:"id",
				id:"id"
			}, {
				xtype:"hidden",
				name:"code",
				id:"code"
			}, {
				xtype:"hidden",
				name:"parentCode",
				id:"parentCode"
			}, {
				fieldLabel:"父类别",
				name:"parentName",
				id:"parentName"
			}, {
				fieldLabel : '类别名称',
				name : 'label',
				id : 'label',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				xtype:"numberfield",
				fieldLabel : '排序',
				name : 'showIndex'
			}
//			, {
//				fieldLabel:"拼音缩写",
//				name:"abbreviation"
//			}
			],
			buttons:[{
				xtype:"button",
				text:"保存",
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
	loadModel:function(code){
		var _this=this;
		Ext.ModelMgr.getModel('CategoryFormModel').load(code, { // load user with ID of "1"
			success: function(model) {
				_this.loadRecord(model);
			}
		});
	},
	saveModel:function(btn,e){
		var form=this.up("form");
		var _url=form.getSaveUrl();
		var node=form.getTreeNode();
		
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					if(form.child("#id").getValue()>0){
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
							Ext.getCmp(CATEGORY.TREE).getStore().load({
								node:node,
								callback: function(records, operation, successful){
									operation.node.expand();
								}
							});
						}
					}
					
					form.up("window").close();
				},
				failure: function(f, action) {
					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，信息没有更新！");
				}
			});
		}
	},
	initParent:function(nodeInterface){
		this.getChildByElement("parentCode").setValue(nodeInterface.getId());
		this.getChildByElement("parentName").setValue(nodeInterface.data["text"]);
	},
	saveUrl:Context.ROOT+"/zz91/admin/category/create.htm",
	config:{
		saveUrl:null,
		treeNode:null
	}
});