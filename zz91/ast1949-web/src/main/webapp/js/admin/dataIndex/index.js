Ext.namespace("ast.ast1949.admin.dataIndex");

var DATAINDEX = new function(){
	this.grid="dataindexgrid";
	this.categorytree="categorytree";
}

ast.ast1949.admin.dataIndex.CategoryTree=Ext.extend(Ext.tree.TreePanel,{
	rootData:"",
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
	
		var _rootData = this.rootData||"";
	
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/admin/dataindex/categoryChild.htm",
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
				nodeType:'async',
				text:'所有类别',
				id:"node-0",
				data:_rootData
			},
			loader:treeLoad,
			tbar:[
				{
	        		text:"展开",
	        		scope:this,
	        		handler:function(btn){
	        			this.expandAll();
	        		}
	        	},{
	        		text:"全部折叠",
	        		scope:this,
	        		handler:function(){
	        			this.collapseAll();
	        		}
	        	}
	        ],
	        contextMenu:this.contextmenu,
	        listeners:{
	        	contextmenu:function(node,e){
	        		node.select();
	        		var c = node.getOwnerTree().contextMenu;
		            c.contextNode = node;
		            c.showAt(e.getXY());
	        	},
	        	"click":this.treeclick
	        }
		};
	
		ast.ast1949.admin.dataIndex.CategoryTree.superclass.constructor.call(this,c);
	
	},
	treeclick:function(node,e){
	},
	contextmenu:new Ext.menu.Menu({
		items: [{
	        id: 'cm-add',
	        cls:'item-add',
	        text: '增加类别',
	        handler:function(btn){
				ast.ast1949.admin.dataIndex.addCategoryWin();
	        }
	    },{
	        id: 'cm-edit',
	        cls:'item-edit',
	        text: '修改类别',
	        handler:function(btn){
	    		ast.ast1949.admin.dataIndex.editCategoryWin();
	        }
	    },{
	        id: 'cm-delete',
	        cls:'item-del',
	        text: '删除类别',
	        handler:function(btn){
		    	if(btn.parentMenu.contextNode.attributes.data!=""){
	                // TODO 删除类别
	                var tree = Ext.getCmp(DATAINDEX.categorytree);
                    var node = tree.getSelectionModel().getSelectedNode();
                    Ext.MessageBox.confirm(Context.MSG_TITLE,"删除索引节点可能会导致页面上无法显示数据，请谨慎操作，\n您确定还要删除吗？", function(btn){
                        if(btn != "yes"){
                                return false;
                        }
                        //从服务端删除权限节点
                        Ext.Ajax.request({
                            url:Context.ROOT+Context.PATH+"/admin/dataindex/deleteCategory.htm",
                            params:{"code":node.attributes.data},
                            success:function(response,opt){
                                var obj = Ext.decode(response.responseText);
                                if(obj.success){
                                    node.remove();
                                }else{
                                	Ext.MessageBox.show({
    									title:Context.MSG_TITLE,
    									msg : "发生错误,信息没有被删除",
    									buttons:Ext.MessageBox.OK,
    									icon:Ext.MessageBox.ERROR
    								});
                                }
                            },
                            failure:function(response,opt){
                            	Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "发生错误,信息没有被删除",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
                            }
                        });
                    });
	            }
	        }
	    }]
	})
});

ast.ast1949.admin.dataIndex.CategoryForm = Ext.extend(Ext.form.FormPanel, {

	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"parentCode",
					name:"parentCode"
				},{
					fieldLabel:"父类别",
					id:"parentName",
					name:"parentName",
					readOnly:true
				},{
					name:"code",
					id:"code",
					fieldLabel:"类别code"
				},{
					name:"label",
					id:"label",
					fieldLabel:"类别名称",
					itemCls:"required",
					allowBlank : false
				}]
			}],
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp("categoryformwin").close();
				}
			}]
		};
		
		ast.ast1949.admin.dataIndex.CategoryForm.superclass.constructor.call(this,c);
	},
	initParentForAdd:function(){
        var tree = Ext.getCmp(DATAINDEX.categorytree);
        var node = tree.getSelectionModel().getSelectedNode();
        this.findById("parentCode").setValue(node.attributes.data||"");
        this.findById("parentName").setValue(node.text);
	},
	initParentForUpdate:function(){
        var tree = Ext.getCmp(DATAINDEX.categorytree);
        var node = tree.getSelectionModel().getSelectedNode();
        this.findById("parentName").setValue(node.parentNode.text);
	},
	loadRecords : function() {
		var tree = Ext.getCmp(DATAINDEX.categorytree);
        var node = tree.getSelectionModel().getSelectedNode();
        
        var _fields = ["id","label","code"];
        
        var form = this;
        var _store = new Ext.data.JsonStore({
            root : "records",
            fields : _fields,
            url : Context.ROOT + Context.PATH + "/admin/dataindex/initCategory.htm?code="+node.attributes.data,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    if (record == null) {
                    	Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载失败...");
                        Ext.getCmp("categoryformwin").close();
                    } else {
                        form.getForm().loadRecord(record);
                    }
                }
            }
        });
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/dataindex/addCategory.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过！");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","操作成功！");
		var tree = Ext.getCmp(DATAINDEX.categorytree);
        var node = tree.getSelectionModel().getSelectedNode();
        if(this.findById("id").getValue() > 0){
        	node.setText(this.findById("label").getValue()); 
        }else{
	        node.leaf= false;
	        tree.getLoader().load(node,function(){
	                node.expand();
	        });
        }
		Ext.getCmp("categoryformwin").close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","操作失败！");
	}
});

/**
 * <p>编辑表单</p>
 * <p>示例：</p>
 * <code>
 	var _editForm=new ast.ast1949.admin.dataIndex.editForm({
		saveUrl:Context.ROOT+Context.PATH+"/admin/dataindex/add.htm"
	});
   </code>
 * @class ast.ast1949.admin.dataIndex.editForm
 * @extends Ext.form.FormPanel
 */
ast.ast1949.admin.dataIndex.InfoForm=Ext.extend(Ext.form.FormPanel, {

	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					xtype:"combotree",
					fieldLabel:"模块类别",
					id:"category",
					name:"category",
					hiddenName : "categoryCode",
					hiddenId : "categoryCode",
					tree:new ast.ast1949.admin.dataIndex.CategoryTree({
						contextmenu:null
					})
				},{
					xtype:"textarea",
					name:"title",
					id:"title",
					fieldLabel:"标题名称",
					allowBlank : false,
					itemCls :"required"
				},{
					name:"link",
					id:"link",
					fieldLabel:"链接地址",
					itemCls:"required",
					allowBlank : false
				},{
					name:"style",
					id:"style",
					fieldLabel:"标题样式",
					itemCls:"required"
				},{
					xtype:"numberfield",
					name:"sort",
					id:"sort",
					fieldLabel:"排序"
				}]
			}
//			,{
//				columnWidth:0.5,
//				layout:"fit",
//				items:[new ast.ast1949.admin.dataIndex.TitleStyleGrid({
//					target:"style"
//				})]
//			}
			],
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp("editformwin").close();
				}
			}]
		};
		
		ast.ast1949.admin.dataIndex.InfoForm.superclass.constructor.call(this,c);
	},
	loadRecords : function(id) {
		
		var _fields = [	{name:"id",mapping:"dataIndexDO.id"},
    		{name:"title",mapping:"dataIndexDO.title"},
    		{name:"categoryCode",mapping:"dataIndexDO.categoryCode"},
    		{name:"link",mapping:"dataIndexDO.link"},
    		{name:"style",mapping:"dataIndexDO.style"},
    		{name:"pic",mapping:"dataIndexDO.pic"},
    		{name:"sort",mapping:"dataIndexDO.sort"},
    		{name:"category",mapping:"categoryName"},
		];
		var form = this;
		var store = new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + Context.PATH + "/admin/dataindex/initDataIndex.htm",
			baseParams : {
				"id" : id
			},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						form.findById("category").setValue({text:record.get("category"),attributes:{data:record.get("categoryCode")}});
					}
				}
			}
		});
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/dataindex/add.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过！");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","操作成功！");
		var resultgrid = Ext.getCmp(DATAINDEX.grid);
		resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		Ext.getCmp("editformwin").close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","操作失败！");
	}
});

//ast.ast1949.admin.dataIndex.TitleStyleGrid=Ext.extend(Ext.grid.GridPanel,{
//	constructor:function(config){
//		config = config || {};
//		Ext.apply(this,config);
//		
//		var targetId=this.target||"";
//		
//		var myData = [
//		    ['红色12号字体','color:red;font-size:12px;']
//		];
//		 var _store = new Ext.data.Store({
//		        reader: new Ext.data.XmlReader({
//		            record: 'order',
//		            id: 'order_code'
//		        }, [
//		            {name: 'orderCode', mapping: 'order_code'},
//		            {name: 'notes', mapping: 'notes'}
//		        ])
//		    });
//		 
////		var _store = new Ext.data.Store({
////			reader:[{name: 'title'},
////			       {name: 'style'}
////			],
////			data:[
////				['红色12号字体','color:red;font-size:12px;']
////			]
////		});
//		
//		//_store.loadData(myData);
//		
//		var c={
//			iconCls:"icon-grid",
//			store:_store,
////			columns:[{
////				id:"title",
////				header:"示例文字",
////				dataIndex:"order"
////			}],
//			columns: [
//	            {header: "Local Order Code", width: 120, dataIndex: 'orderCode'},
//	            {header: "Notes", width: 120, dataIndex: 'notes'}
//	        ],
//	        autoHeight:true,
//	        autoWidth:true,
//			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
//		};
//		
//		ast.ast1949.admin.dataIndex.TitleStyleGrid.superclass.constructor.call(this,c);
//	}
//});

ast.ast1949.admin.dataIndex.editFormWin = function(id) {

	var form = new ast.ast1949.admin.dataIndex.InfoForm({
		id : "editform",
		region : "center",
		saveUrl : Context.ROOT + Context.PATH
				+ "/admin/dataindex/update.htm"
	});
	form.loadRecords(id);

	var win = new Ext.Window({
		id : "editformwin",
		title : "修改推荐信息",
		width : 500,
		modal : true,
		items : [form]
	});
	win.show();
};

ast.ast1949.admin.dataIndex.addCategoryWin = function() {

	var form = new ast.ast1949.admin.dataIndex.CategoryForm({
		id : "editform",
		region : "center",
		saveUrl : Context.ROOT + Context.PATH
				+ "/admin/dataindex/addCategory.htm"
	});
	//TODO parentNode初始化
	form.initParentForAdd();

	var win = new Ext.Window({
		id : "categoryformwin",
		title : "修改",
		width : 350,
		modal : true,
		items : [form]
	});
	win.show();
};

ast.ast1949.admin.dataIndex.editCategoryWin = function() {
	var tree = Ext.getCmp(DATAINDEX.categorytree);
    var node = tree.getSelectionModel().getSelectedNode();
    if(tree.getRootNode() == node){
    	return false;
    }
    
	var form = new ast.ast1949.admin.dataIndex.CategoryForm({
		id : "editform",
		region : "center",
		saveUrl : Context.ROOT + Context.PATH
				+ "/admin/dataindex/updateCategory.htm"
	});
	form.initParentForUpdate();
	form.loadRecords();

	var win = new Ext.Window({
		id : "categoryformwin",
		title : "修改",
		width : 350,
		modal : true,
		items : [form]
	});
	win.show();
};


/**
 * 信息列表
 * <p>示例：</p>
 * <code>
 	var _grid=new ast.ast1949.admin.dataIndex.ResultGrid({
		id:DATAINDEX.grid,//这项配置很重要。必需这么配置。
		listUrl:Context.ROOT+Context.PATH+"/admin/dataindex/query.htm",
		autoScroll:true
	});
   </code>
 * @class ast.ast1949.admin.dataIndex.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.dataIndex.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+Context.PATH+"/admin/dataindex/queryDataIndex.htm",
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                	Ext.getCmp("edit").enable();
	                    Ext.getCmp("delete").enable();
	                } else {
	                	Ext.getCmp("edit").disable();
	                    Ext.getCmp("delete").disable();
	                }
	            }
	        }
		});

		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"code",
			sortable:true,
			width:150,
			dataIndex:"categoryCode"
		},{
				header:"标题",
				sortable:false,
				width:250,
				dataIndex:"title",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					return "<span style='"+record.get("style")+"' >"+value+"</span>";
				}
			},{
				header:"Link地址",
				sortable:false,
				width:300,
				dataIndex:"link",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					return "<a href='"+value+"' target='_blank'>"+value+"</a>";
				}
			},{
				header:"排序号",
				width:80,
				dataIndex:"sort"
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			autoExpandColumn:3,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
		ast.ast1949.admin.dataIndex.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create(["id","title","categoryCode","style","link","pic","sort"]),
	mytoolbar:[{
		iconCls:"add",
		text:"添加",
		handler:function(btn){
			ast.ast1949.admin.dataIndex.SendIndex({
				title:"",
				link:"#"
			})
		}
	},{
		iconCls:"edit",
		id:"edit",
		text:"修改",	
		disabled:true,
		handler:function(){
			var grid = Ext.getCmp(DATAINDEX.grid);
			var row = grid.getSelections();
			var id=row[0].get("id");
			ast.ast1949.admin.dataIndex.editFormWin(id);
		}
	},{
			iconCls:"delete",
			id:"delete",
			text:"删除",
			disabled:true,
			handler:function(btn){
				Ext.MessageBox.confirm(Context.MSG_TITLE, '你真的要删除所选记录?',function(btn){
					if(btn!="yes"){
						return ;
					}
					var grid = Ext.getCmp(DATAINDEX.grid);
					var row = grid.getSelections();
					Ext.Ajax.request({
	                    url:Context.ROOT+Context.PATH+ "/admin/dataindex/deleteIndex.htm",
	                    params:{"id":row[0].get("id")},
	                    success:function(response,opt){
	                    	var obj = Ext.decode(response.responseText);
							if(obj.success){
								//消息提示
								ast.ast1949.utils.Msg("","信息已删除！");
								grid.getStore().reload();
							}else{
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "发生错误,信息没有被删除",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
	                    },
	                    failure:function(response,opt){
	                    	Ext.MessageBox.show({
								title:Context.MSG_TITLE,
									msg : "发生错误,信息没有被删除",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
	                    }
	                });
				});
			}
		},"->",{
			xtype:"label",
			text:"标题："
		},{
			xtype:"textfield",
			id:"title",
			name:"title",
			width:150,
			listeners:{
				"blur":function(field){
					var grid=Ext.getCmp(DATAINDEX.grid);
					var B=grid.getStore().baseParams||{};
					B["title"]=field.getValue();
					grid.getStore().baseParams=B;
					grid.getStore().reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					
				}
			}
		}
	]
});


ast.ast1949.admin.dataIndex.SendIndex=function(cfg){
	var form = new ast.ast1949.admin.dataIndex.InfoFormForAdd({
		region : "center",
		saveUrl : Context.ROOT + Context.PATH + "/admin/dataindex/add.htm"
	});
	//初始化
	form.findById("title").setValue(cfg.title||"");
	form.findById("link").setValue(cfg.link||"");
	form.findById("style").setValue(cfg.style||"color:black;font-size:12px");
	form.findById("sort").setValue(cfg.sort||"0");

	var win = new Ext.Window({
		id : "dataindexaddformwin",
		title : "推荐到",
		width : 500,
		modal : true,
		items : [form]
	});
	win.show();
}

ast.ast1949.admin.dataIndex.InfoFormForAdd=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				id:"id",
				name:"id"
			},{
				xtype:"combotree",
				fieldLabel:"模块类别",
				id:"category",
				name:"category",
				hiddenName : "categoryCode",
				hiddenId : "categoryCode",
				tree:new ast.ast1949.admin.dataIndex.CategoryTree({
					contextmenu:null
				})
			},{
				xtype:"textarea",
				name:"title",
				id:"title",
				fieldLabel:"标题名称",
				allowBlank : false,
				itemCls :"required"
			},{
				name:"link",
				id:"link",
				fieldLabel:"链接地址",
				itemCls:"required",
				allowBlank : false
			},{
				name:"style",
				id:"style",
				fieldLabel:"标题样式",
				itemCls:"required"
			},{
				xtype:"numberfield",
				name:"sort",
				id:"sort",
				fieldLabel:"排序"
			}],
			
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp("dataindexaddformwin").close();
				}
			}]
		};
		
		ast.ast1949.admin.dataIndex.InfoFormForAdd.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/dataindex/add.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过！");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","操作成功！");
		Ext.getCmp("dataindexaddformwin").close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","添加失败！");
	}
});


