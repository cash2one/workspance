Ext.namespace("com.zz91.trade.buy.category");

var CATEGORY=new function(){
	this.TREE="categorytree";
	this.EDIT_WIN="editwin";
	this.COLLECTION_GRID="collectiongrid";
}

com.zz91.trade.buy.category.TreePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT + "/trade/tradebuy/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["parentCode"] = node.attributes["data"];
				}
			}
		});
		
		var c={
			rootVisible:false,
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				expanded:true,
				nodeType:"async",
				text:"所有类别",
				data:""
			},
			loader:treeLoad,
			tbar:[{
				text:"全部展开",
				scope:this,
				handler:function(btn){
					this.expandAll();
				}
			},{
				text:"全部折叠",
				scope:this,
				handler:function(btn){
					this.collapseAll();
				}
			}],
			contextMenu:this.contextmenu,
			listeners:{
				contextmenu:function(node,e){
					node.select();
					var c = node.getOwnerTree().contextMenu;
					if(c!=null){
						c.contextNode = node;
						c.showAt(e.getXY());
					}
				}
			}
		};
		com.zz91.trade.buy.category.TreePanel.superclass.constructor.call(this,c);
	},
	contextmenu:new Ext.menu.Menu({
		items:[{
			id:"cm-add",
			cls:"add16",
			text:"增加类别",
			handler:function(btn){
				var code=btn.parentMenu.contextNode.attributes.data;
				var text=btn.parentMenu.contextNode.attributes.text;
				com.zz91.trade.buy.category.AddWin(code,text);
			}
		},{
			id: "cm-edit",
			cls:"edit16",
			text: "修改类别",
			handler:function(btn){
				if(btn.parentMenu.contextNode.attributes.data>0){
					com.zz91.trade.buy.category.EditWin(
						btn.parentMenu.contextNode.attributes.data
					);
				} else {
					com.zz91.ads.board.utils.Msg("","此类别不可编辑！");
				}
			}
		},{
			id: "cm-del",
			cls:"delete16",
			text: "删除类别",
			handler:function(btn){
				if(btn.parentMenu.contextNode.attributes.data>0){
					// 删除指定类别信息
					var tree = Ext.getCmp(CATEGORY.TREE);
					var node = tree.getSelectionModel().getSelectedNode();
					Ext.MessageBox.confirm("系统提示","您确定要删除该分类及其子分类吗?", function(btn){
						if(btn != "yes"){
							return false;
						}
						Ext.Ajax.request({
							url:Context.ROOT + "/trade/tradecategory/deleteCategory.htm",
							params:{"code":node.attributes.data},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									node.remove();
								}else{
									com.zz91.utils.Msg("","类别没有被删除，可能服务器发生了一些错误！");
								}
							},
							failure:function(response,opt){
								com.zz91.utils.Msg("","类别没有被删除，可能网络发生了一些错误！");
							}
						});
					});
				}
			}
		}]
	})
});

com.zz91.trade.buy.category.EditForm=Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+"/trade/tradecategory/createCategory.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign:"right",
			labelWidth:80,
			frame:true,
			layout:"form",
			defaults:{
				xtype:"textfield",
				anchor:"99%"
			},
			items:[{
				fieldLabel:"ID",
				name:"id",
				id:"id",
				readOnly:true
			},{
				fieldLabel:"CODE",
				name:"code",
				readOnly:true,
				id:"code"
			},{
				xtype:"hidden",
				name:"parentCode",
				id:"parentCode"
			},{
				fieldLabel:"父类别",
				id:"parentName",
				readOnly:true
			},{
				fieldLabel:"类别名称",
				allowBlank:false,
				itemCls:"required",
				name:"name",
				id:"name"
			},{
				fieldLabel:"排序",
				name:"sort",
				value:"0"
			},{
				fieldLabel:"搜索标签",
				name:"tags"
			},{
				xtype:"checkbox",
				boxLabel:'在页面上显示', 
				name:'showIndex',
				inputValue:"1",
			}]
			,
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(){
					var form=this;
					if(this.getForm().isValid()){
						var _url=this.saveUrl;
						this.getForm().submit({
							url:_url,
							method:"post",
							type:"json",
							success:function(){
								var tree = Ext.getCmp(CATEGORY.TREE);
								var node = tree.getSelectionModel().getSelectedNode();
								if(form.findById("id").getValue() > 0){
									node.setText(form.findById("name").getValue()); 
								}else{
									node.leaf= false;
									tree.getLoader().load(node,function(){
										node.expand();
									});
								}
								Ext.getCmp(CATEGORY.EDIT_WIN).close()
								com.zz91.utils.Msg("",MESSAGE.saveSuccess);
							},
							failure:function(){
								com.zz91.utils.Msg("","保存失败");
							}
						});
					}
				}
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp(CATEGORY.EDIT_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.buy.category.EditForm.superclass.constructor.call(this,c);
	},
	initParent:function(code,text){
		this.findById("parentCode").setValue(code);
		this.findById("parentName").setValue(text);
	},
	loadRecord:function(code){
		var _fields=["id","code","name","tags","sort","showIndex"];
		var form = this;
		var store=new Ext.data.JsonStore({
			fields : _fields,
			url : Context.ROOT + "/trade/tradecategory/queryCategoryByCode.htm",
			baseParams:{"code":code},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,MESSAGE.loadError);
					} else {
						form.getForm().loadRecord(record);
						//设置父类别
						var tree = Ext.getCmp(CATEGORY.TREE);
						var node = tree.getSelectionModel().getSelectedNode().parentNode;
						if(form.findById("id").getValue() > 0){
							form.findById("parentName").setValue(node.text);
						}else{
							form.findById("parentName").setValue("所有类别");
						}
					}
				}
			}
		});
	}
});

com.zz91.trade.buy.category.EditWin = function(code){
	var form = new com.zz91.trade.buy.category.EditForm({});
	
	form.saveUrl = Context.ROOT + "/trade/tradecategory/updateCategory.htm";
	form.loadRecord(code);
	
	var win = new Ext.Window({
		id:CATEGORY.EDIT_WIN,
		title:"修改类别",
		width:450,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.trade.buy.category.AddWin = function(code,text){
	var form = new com.zz91.trade.buy.category.EditForm();
	
	form.saveUrl = Context.ROOT + "/trade/tradecategory/createCategory.htm";
	form.initParent(code,text);
	
	var win = new Ext.Window({
		id:CATEGORY.EDIT_WIN,
		title:"增加类别",
		width:450,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}


