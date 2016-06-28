Ext.namespace("com.zz91.trade.category");

var CATEGORY=new function(){
	this.TREE="categorytree";
	this.EDIT_WIN="editwin";
	this.COLLECTION_GRID="collectiongrid";
	this.RESULT_GRID = "resultGrid";
	this.EDIT_FORM = "tradeEditForm";
}

com.zz91.trade.category.FIELD=[
	{name:"id",mapping:"tradeProperty.id"},
	{name:"category_code",mapping:"tradeProperty.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"name",mapping:"tradeProperty.name"},
	{name:"search_value",mapping:"tradeProperty.searchValue"},
	{name:"vtype",mapping:"tradeProperty.vtype"},
	{name:"vtype_reg",mapping:"tradeProperty.vtypeReg"},
	{name:"vtype_value",mapping:"tradeProperty.vtypeValue"}
];

com.zz91.trade.category.FORMFIELD=[
	{name:"id",mapping:"tradeProperty.id"},
	{name:"categoryCode",mapping:"tradeProperty.categoryCode"},
	{name:"name",mapping:"tradeProperty.name"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"searchValue",mapping:"tradeProperty.searchValue"},
	{name:"vtype",mapping:"tradeProperty.vtype"},
	{name:"vtypeReg",mapping:"tradeProperty.vtypeReg"},
	{name:"vtypeValue",mapping:"tradeProperty.vtypeValue"},
	{name:"inputRequired",mapping:"tradeProperty.inputRequired"},
	{name:"searchable",mapping:"tradeProperty.searchable"}
];

com.zz91.trade.category.TreePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT + "/trade/tradecategory/child.htm",
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
		com.zz91.trade.category.TreePanel.superclass.constructor.call(this,c);
	},
	contextmenu:new Ext.menu.Menu({
		items:[{
			id:"cm-add",
			cls:"add16",
			text:"增加类别",
			handler:function(btn){
				var code=btn.parentMenu.contextNode.attributes.data;
				var text=btn.parentMenu.contextNode.attributes.text;
				com.zz91.trade.category.AddWin(code,text);
			}
		},{
			id: "cm-edit",
			cls:"edit16",
			text: "修改类别",
			handler:function(btn){
				if(btn.parentMenu.contextNode.attributes.data>0){
					com.zz91.trade.category.EditWin(
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

com.zz91.trade.category.EditForm=Ext.extend(Ext.form.FormPanel,{
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
							success:function(form, action){
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
							failure:function(form, action){
								alert(action.result.data);
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
		
		com.zz91.trade.category.EditForm.superclass.constructor.call(this,c);
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

com.zz91.trade.category.EditWin = function(code){
	var form = new com.zz91.trade.category.EditForm({});
	
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

com.zz91.trade.category.AddWin = function(code,text){
	var form = new com.zz91.trade.category.EditForm();
	
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


com.zz91.trade.category.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/trade/tradecategory/queryTradeProperty.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.trade.category.FIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,
			{header:"类别", width:120, dataIndex:"category_name",sortable:true},
			{header:"名称", width:150, dataIndex:"name",sortable:true},
			{header:"搜索可选值", width:100, dataIndex:"search_value",sortable:true},
			{header:"输入类型", width:150, dataIndex:"vtype",sortable:true},
			{header:"验证正则", width:200, dataIndex:"vtype_reg",sortable:true},
			{header:"输入候选项", width:200, dataIndex:"vtype_value",sortable:true}
		]);
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"添加",
					iconCls:"add16",
					handler:function(){
						com.zz91.trade.category.createCollectionWin();
					}
				},{
					text:"编辑",
					iconCls:"edit16",
					handler : function(btn) {
						var row = grid.getSelectionModel().getSelections();
						if (row.length > 1) {
							Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "最多只能选择一条记录！",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						} else {
							var _id = row[0].get("id");
							com.zz91.trade.category
									.editFormWin(_id)
						}
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
						com.zz91.trade.category.deleteCollection(idArr, _store);
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
		
		com.zz91.trade.category.grid.superclass.constructor.call(this,c);
	},
	listeners:{
		"rowdblclick":function(grid,rowIndex,e){
			var row=grid.getSelectionModel().getSelections();
			var _id = row[0].get("id");
			com.zz91.trade.category
					.editFormWin(_id)
		}
	},
});


com.zz91.trade.category.deleteCollection=function(idArr, store){
	
	Ext.Msg.confirm("确认","你确定要删除吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
		for(var i=0;i<idArr.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradecategory/deleteTradeProperty.htm",
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

com.zz91.trade.category.CollectionForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		var form=this;
		var c={
				layout:"column",
				frame:true,
				labelAlign : "right",
				labelWidth : 80,
				items:[{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						name:"id",
						id:"id"
					},{
						fieldLabel:"名称",
						name:"name",
						itemCls:"required",
						allowBlank:false
					}]
				},{
					columnWidth:.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"categoryCode",
						name:"categoryCode"
					},{
						fieldLabel:"类别",
						name:"categoryName",
						id:"categoryName",
						itemCls:"required",
						allowBlank:false,
						readOnly:true,
						listeners:{
							"focus":function(field){
								var initValue=Ext.getCmp("categoryCode").getValue();
								com.zz91.trade.choiceCategory(initValue,function(node,e){
									Ext.getCmp("categoryName").setValue(node.text);
									Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
									node.getOwnerTree().ownerCt.close();
								})
							}
						}
					},{
						xtype:"checkbox",
						boxLabel:'作为搜索项', 
						name:'searchable',
						inputValue:"1",
					},{
						xtype:"combo",
						fieldLabel:"输入类型",
						name:"vtypeStr",
						hiddenName:"vtype",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["text","文本框"],["combo","选择框"],["radio","单选"],["checkbox","多选"]]
						}),
						valueField:"k",
						displayField:"v"
					},{
						xtype:"combo",
						fieldLabel:"验证正则",
						name:"vtypeRegStr",
						hiddenName:"vtypeReg",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["/\D/g","纯数字"],["/^(?:\w+\.?)*\w+@(?:\w+\.?)*\w+$/","邮件"],[" /^(([1-9]\d*)|0)(\.\d{2})?$/","金钱"],["/[^_a-zA-Z]/g","纯英文"]
							      ,["/[^\u4E00-\u9FA5]/g","纯中文"],["/(?:0[1-9]|[12][0-9]|3[01]\/(?:)[1-9]|1[0-2]\/(?:19|20\d{2}))/","日期"]]
						}),
						valueField:"k",
						displayField:"v"
					}]
				},{
					columnWidth:.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"checkbox",
						boxLabel:'作为必填项', 
						name:'inputRequired',
						inputValue:"1"
					},{
						fieldLabel:"搜索可选的值",
						name:"searchValue"
					},{
						fieldLabel:"候选项",
						name:"vtypeValue",
					}]
				}],
				buttons:[{
					iconCls:"saveas16",
					text:"保存",
					handler:function(){
						var url=Context.ROOT+"/trade/tradecategory/createTradeProperty.htm";
						if(form.findById("id").getValue()>0){
							url=Context.ROOT+"/trade/tradecategory/updateTradeProperty.htm";
						}
						
						if(form.getForm().isValid()){
					    	form.getForm().submit({
				                url:url,
				                method:"post",
				                type:"json",
				                success:function(_form,_action){
									com.zz91.utils.Msg(MESSAGE.title, MESSAGE.saveSuccess);
									form.reset();
								},
								failure:function(_form,_action){
									Ext.MessageBox.show({
										title:MESSAGE.title,
										msg : MESSAGE.saveFailure,
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
									});
								}
				            });
					    }else{
				            Ext.MessageBox.show({
				                title:MESSAGE.title,
				                msg : MESSAGE.unValidate,
				                buttons:Ext.MessageBox.OK,
				                icon:Ext.MessageBox.ERROR
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
		com.zz91.trade.category.CollectionForm.superclass.constructor.call(this,c);
	},
	initCategoryCode:function(){
		var tree = Ext.getCmp(CATEGORY.TREE);
		var node = tree.getSelectionModel().getSelectedNode();

		if(node!=null && node.attributes["data"]!=""){
			//this.findById("categoryCode").setValue(node.attributes["data"]);
			Ext.getCmp("categoryName").setValue(node.text);
			Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
		}
	},
	loadRecord:function(id){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.trade.category.FORMFIELD,
			url:Context.ROOT+"/trade/tradecategory/queryOneTradeProperty.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						//form.findById("gmtEntryDate").setValue(Ext.util.Format.date(new Date(record.get("gmtEntry").time), 'Y-m-d'));
					}
				}
			}
		});
	}
});

com.zz91.trade.category.createCollectionWin = function(code){
	var form = new com.zz91.trade.category.CollectionForm({});
	form.initCategoryCode();
	
	var win = new Ext.Window({
		id:CATEGORY.EDIT_WIN,
		title:"添加属性",
		width:850,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.trade.choiceCategory=function(init,callback){
	
	var tree = new com.zz91.trade.category.TreePanel({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
	
}
//修改类别属性窗口
com.zz91.trade.category.editFormWin = function(id) {

	var form = new com.zz91.trade.category.CollectionForm({
				id : CATEGORY.EDIT_FORM,
				region : "center",
				saveUrl : Context.ROOT + Context.PATH
						+ "/trade/tradecategory/updateTradeProperty.htm"
			});

	var win = new Ext.Window({
				id : CATEGORY.EDIT_WIN,
				title : "类别属性修改",
				width : 850,
				modal : true,
				// autoHeight:true,
				// maximizable:true,
				items : [form]
			});
	form.loadRecord(id);
	win.show();
};