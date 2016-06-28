Ext.namespace("com.zz91.tags.category");

var CATEGORY=new function(){
	this.TREE="categorytree";
	this.EDIT_WIN="editwin";
	this.COLLECTION_GRID="collectiongrid";
}

com.zz91.tags.category.FIELD=[
	{name:"id",mapping:"id"},
	{name:"category_code",mapping:"categoryCode"},
	{name:"category_index_key",mapping:"categoryIndexKey"},
	{name:"tags",mapping:"tags"},
	{name:"tags_encode",mapping:"tagsEncode"}
];


com.zz91.tags.category.TreePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT + "/tags/category/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["parentCode"] = node.attributes["data"];
				}
			}
		});
		
		var c={
			rootVisible:true,
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
		com.zz91.tags.category.TreePanel.superclass.constructor.call(this,c);
	},
	contextmenu:new Ext.menu.Menu({
		items:[{
			id:"cm-add",
			cls:"add16",
			text:"增加类别",
			handler:function(btn){
				var code=btn.parentMenu.contextNode.attributes.data;
				var text=btn.parentMenu.contextNode.attributes.text;
				com.zz91.tags.category.AddWin(code,text);
			}
		},{
			id: "cm-edit",
			cls:"edit16",
			text: "修改类别",
			handler:function(btn){
				if(btn.parentMenu.contextNode.attributes.data>0){
					com.zz91.tags.category.EditWin(
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
							url:Context.ROOT + "/tags/category/deleteCategory.htm",
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

com.zz91.tags.category.EditForm=Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+"/tags/category/createCategory.htm",
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
				fieldLabel:"索引 key",
				name:"indexKey"
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
		
		com.zz91.tags.category.EditForm.superclass.constructor.call(this,c);
	},
	initParent:function(code,text){
		this.findById("parentCode").setValue(code);
		this.findById("parentName").setValue(text);
	},
	loadRecord:function(code){
		var _fields=["id","code","name","indexKey"];
		var form = this;
		var store=new Ext.data.JsonStore({
			fields : _fields,
			url : Context.ROOT + "/tags/category/queryCategoryByCode.htm",
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

com.zz91.tags.category.EditWin = function(code){
	var form = new com.zz91.tags.category.EditForm({});
	
	form.saveUrl = Context.ROOT + "/tags/category/updateCategory.htm";
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

com.zz91.tags.category.AddWin = function(code,text){
	var form = new com.zz91.tags.category.EditForm();
	
	form.saveUrl = Context.ROOT + "/tags/category/createCategory.htm";
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

//com.zz91.tags.category.tree=Ext.extend(Ext.tree.TreePanel,{
//	nodeUrl:Context.ROOT+"/tags/category/queryCategory.htm",
// 	constructor:function(config){
//		config=config||{};
//		Ext.apply(this.config);
//		var root=new Ext.tree.AsyncTreeNode({
//			text:"所有类别",
//			draggable:false,
//			id:"0",
//			data:"",
//			expanded:true
//		});
//		var _nodeUrl=this.nodeUrl;
//		var dataLoader = new Ext.tree.TreeLoader({
//			url: _nodeUrl,
//			listeners:{
//				"beforeload":function(treeLoader,node){
//					this.baseParams["parentCode"]=node.attributes["data"];
//				}
//			}
//		});
//		
//		var tree=this;
//		var c={
//			boreder:false,
//			useArrows:true,
//			root:root,
//			autoScroll:true,
//			loader:dataLoader,
//			tbar:[{
//				text:"全部展开",
//				scope:this,
//				handler:function(btn){
//					this.getRootNode().expand(true);
//				}
//			},{
//				text:"全部折叠",
//				scope:this,
//				handler:function(btn){
//					this.getRootNode().collapse(true);
//				}
//			}],
//			contextmenu:new Ext.menu.Menu({
//				items:[
//					{
//						text:"添加",
//						scope:this,
//						handler:function(){
//							var win3=new Ext.Window({
//								title:"类别添加",
//								width:280,
//								maximizable:true,
//								height:200,
//								modal:true,
//								layout:"fit",
//								items:{
//									id:"win1_form",
//									xtype:"form",
//									layout:"form",
//									frame:true,
//									labelAlign:"right",
//									labelWidth:70,
//									buttonAlign:"center",
//									defaults:{xtype:"textfield",anchor:"90%",allowBlank:false},
//									items:[
//										{fieldLabel:"父类别",name:"parentCode"},
//										{fieldLabel:"类别名",name:"name"},
//										{fieldLabel:"index_key",name:"indexKey"}	
//									],
//									buttons:[{
//										text:"保存",
//										handler:function(){
//											if(Ext.getCmp("win1_form").getForm().isValid()){
//												Ext.getCmp("win1_form").getForm().submit({
//													url:Context.ROOT+"/tags/category/createCategory.htm",
//													method:"post",
//													type:"json",
//													success:function(){
//														com.zz91.utils.Msg("","保存成功");
//														this.getCmp().reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
//														win3.close();
//													},
//													failure:function(){
//														com.zz91.utils.Msg("","保存失败");
//													}
//												})
//											}
////											Ext.getCmp("win1_form").getForm().submit({});
////											win1.close(); 	
//										}
//									},{
//										text:"取消",
//										handler:function(){
//											win3.close();
//										}
//									}]		
//								}	
//							});
//							win3.show();
//						}
//					},{
//						text:"修改",
//						scope:this,
//						handler:function(){
//							var win1=new Ext.Window({
//								title:"类别编辑",
//								width:280,
//								maximizable:true,
//								height:200,
//								modal:true,
//								layout:"fit",
//								items:{
//									id:"win1_form",
//									xtype:"form",
//									layout:"form",
//									frame:true,
//									labelAlign:"right",
//									labelWidth:70,
//									buttonAlign:"center",
//									defaults:{xtype:"textfield",anchor:"90%",allowBlank:false},
//									items:[
//										{fieldLabel:"父类别",name:"parentCode"},
//										{fieldLabel:"类别名",name:"name"},
//										{fieldLabel:"index_key",name:"indexKey"}	
//									],
//									buttons:[{
//										text:"保存",
//										handler:function(){
//											if(Ext.getCmp("win1_form").getForm().isValid()){
//												Ext.getCmp("win1_form").getForm().submit({
//													url:Context.ROOT+"/tags/category/updateCategory.htm",
//													method:"post",
//													type:"json",
//													success:function(){
//														com.zz91.utils.Msg("","保存成功");
//														this.getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
//														win1.close();
//													},
//													failure:function(){
//														com.zz91.utils.Msg("","保存失败");
//													}
//												})
//											}
////											Ext.getCmp("win1_form").getForm().submit({});
////											win1.close(); 	
//										}
//									},{
//										text:"取消",
//										handler:function(){
//											win1.close();
//										}
//									}]		
//								}	
//							});
//							win1.show();
//						}
//					},{
//						text:"删除",
//						scope:this,
//						handler:function(){
//							this.getChecked();
//						}
//					}	
//				]	
//			}),
//			listeners:{
//				scope:this,
//				"contextmenu":function(node,e){
//					this.contextmenu.showAt(e.getXY());
//				}
//			} 
//		};
//		com.zz91.tags.category.tree.superclass.constructor.call(this,c);	
//	}
//});

com.zz91.tags.category.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/tags/category/queryCollection.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.tags.category.FIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,
			{header:"类别", width:150, dataIndex:"category_code",sortable:true},
			{header:"index", width:150, dataIndex:"category_index_key",sortable:true},
			{header:"标签", width:100, dataIndex:"tags",sortable:true},
			{header:"url标签", width:200, dataIndex:"tags_encode",sortable:true}
		]);
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"添加关联",
					iconCls:"add16",
					handler:function(){
						com.zz91.tags.category.createCollectionWin();
					}
				},{
					text:"删除",
					iconCls:"delete16",
					handler:function(){
						var row=grid.getSelectionModel().getSelections();
						var idArr=new Array();
						for(var i=0;i<row.length;i++){
							idArr.push(row[i].get("id"));
						}
						com.zz91.tags.category.deleteCollection(idArr, _store);
					}
				}]
			}),
			bbar:new Ext.PagingToolbar({
				pageSize:Context.PAGE_SIZE,
				store:_store,
				displayInfo:true,
				displayMsg:"显示第{0}--{1}跳记录, 共{2}条记录",
				emptyMsg:"没有记录",
				beforePageText:"第",
				afterPageText:"页，共{0}页",
				pramaNames:{start:"start",limit:"limit"}
			})	
		};
		
		com.zz91.tags.category.grid.superclass.constructor.call(this,c);
	}		
});

com.zz91.tags.category.deleteCollection=function(idArr, store){
	
	Ext.Msg.confirm("确认","你确定要删除吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
		for(var i=0;i<idArr.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/tags/category/deleteCollection.htm",
				params:{"id":idArr[i]},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
					}
				},
				failure:function(response,opt){
					Ext.MessageBox.show({
						title:MESSAGE.title,
						msg : MESSAGE.submitFailure,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			});	
		}
	});
}

com.zz91.tags.category.CollectionForm=Ext.extend(Ext.form.FormPanel,{
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
				fieldLabel:"类别Code",
				name:"categoryCode",
				id:"categoryCode",
				itemCls:"required",
				allowBlank:false
			},{
				fieldLabel:"标签",
				allowBlank:false,
				itemCls:"required",
				name:"tags",
				id:"tags"
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(){
					var form=this;
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:Context.ROOT+"/tags/category/createCollection.htm",
							method:"post",
							type:"json",
							success:function(){
								Ext.getCmp(CATEGORY.COLLECTION_GRID).getStore().reload();
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
		
		com.zz91.tags.category.CollectionForm.superclass.constructor.call(this,c);
	},
	initCategoryCode:function(){
		var tree = Ext.getCmp(CATEGORY.TREE);
		var node = tree.getSelectionModel().getSelectedNode();

		if(node!=null && node.attributes["data"]!=""){
			this.findById("categoryCode").setValue(node.attributes["data"]);
		}
	}
});

com.zz91.tags.category.createCollectionWin = function(code){
	var form = new com.zz91.tags.category.CollectionForm({});
	form.initCategoryCode();
	var win = new Ext.Window({
		id:CATEGORY.EDIT_WIN,
		title:"添加关联",
		width:450,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};