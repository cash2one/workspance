Ext.namespace("ast.ast1949.admin.priceCategory");

//改进
//
//类别树没有滚动条
//类别树不能清楚地显示类别编号，对开发来说不是很方便
//右侧显示辅助类别，修改和添加用弹出窗口的方式实现
//树节点没有按照排序字段排序
//类别树增加一个leaf字段，用来判断是否有叶子节点
//＊类别树，增加备份功能，可以备份和恢复
//＊增加导出excel功能，将树节点以表格的形式导出（也可以用于代替备份功能）

//定义一些可能用到的常量
var PRICECATEGORY = new function(){
	this.CATEGORY_TREE="categorytree";
	this.CATEGORY_WIN="category_win";
	this.ASSIT_CATEGORY_GRID="assit_category_grid";
	this.ASSIT_CATEGORY_GRID_LIST="assit_category_grid_list";
	this.PRICE_CATEGORY_TREE_WIN="price_category_tree_win";
}

/**
 * <p>报价信息类别树，示例：</p>
 * <code>
 * var tree = new ast.ast1949.admin.priceCategory.CategoryTree({
 * 	   id="demotree"
 * });
 * </code>
 * */
ast.ast1949.admin.priceCategory.CategoryTreePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/admin/pricecategory/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["id"] = node.attributes["data"];
				}
			}
		});
		
		var c={
			rootVisible:true,
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				nodeType:"async",
				text:"所有类别",
				data:0
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
					c.contextNode = node;
					c.showAt(e.getXY());
				}
			}
		};
		
		ast.ast1949.admin.priceCategory.CategoryTreePanel.superclass.constructor.call(this,c);
		
	},
	contextmenu:new Ext.menu.Menu({
		items:[{
			id:"cm-add",
			cls:"item-add",
			text:"增加类别",
			handler:function(btn){
				var _id=btn.parentMenu.contextNode.attributes.data;
				var _text=btn.parentMenu.contextNode.attributes.text;
//				alert(_id+_text);
				
				ast.ast1949.admin.priceCategory.AddCategoryWin(_id,_text);
			}
		},{
            id: 'cm-edit',
            cls:'item-edit',
            text: '修改类别',
            handler:function(btn){
            	if(btn.parentMenu.contextNode.attributes.data>0){
            		ast.ast1949.admin.priceCategory.EditCategoryWin(
            			btn.parentMenu.contextNode.attributes.data
            		);
            	} else {
            		ast.ast1949.utils.Msg("","此节点不可编辑！");
            	}
            }
        },{
        	id: 'cm-del',
            cls:'item-del',
            text: '删除类别',
            handler:function(btn){
        		if(btn.parentMenu.contextNode.attributes.data>0){
        			// 删除指定类别信息
        			var tree = Ext.getCmp(PRICECATEGORY.CATEGORY_TREE);
        			var node = tree.getSelectionModel().getSelectedNode();
        			Ext.MessageBox.confirm("系统提示","您确定要删除该分类及其子分类吗?", function(btn){
						if(btn != "yes"){
							return false;
						}
						Ext.Ajax.request({
							url:Context.ROOT + Context.PATH + "/admin/pricecategory/delete.htm",
							params:{"id":node.attributes.data},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									node.remove();
								}else{
									ast.ast1949.utils.Msg("","类别没有被删除，可能服务器发生了一些错误！");
								}
							},
							failure:function(response,opt){
								ast.ast1949.utils.Msg("","类别没有被删除，可能网络发生了一些错误！");
							}
						});
        			});
        		}
        	}
        }
//        ,{
//        	id: 'cm-add2',
//            cls:'item-add',
//            text: '添加关联',
//            handler:function(btn){
//            	
//            	if(btn.parentMenu.contextNode.attributes.data>0){
//            		
//            		$.ajax({
//            			type: "GET",
//            			url: Context.ROOT+Context.PATH+'/admin/pricecategory/queryAssistCategoryId.htm',
//            			data: "id="+btn.parentMenu.contextNode.attributes.data,
//            			async: true,
//            			dataType:'json',
//            			success: function(msg){
//            				var _ids = new Array();
//							if(msg!=null&&msg.records.length>0){
//							  
//							   for (var i = 0, len = msg.records.length; i < len; i++) {
//									var _id = msg.records[i];
//									_ids.push(_id);
//								}
//							}
//							
//							ast.ast1949.admin.priceCategory.PriceCategoryCheckBoxTreeWin(
//		            			btn.parentMenu.contextNode.attributes.data,
//		            			_ids.join(',')
//		            		);
//						}
//					});
//					
//            		
//            	} else {
//            		ast.ast1949.utils.Msg("","此节点不可编辑！");
//            	}
//            }
//        }
        ]
	})
});

/**
 * <p>辅助信息表格，默认不显示消息，可以按照主类别查找关联的辅助信息</p>
 * */
//ast.ast1949.admin.priceCategory.AssitTypeGrid = Ext.extend(Ext.grid.GridPanel,{
//	listUrl:Context.ROOT+Context.PATH +"/admin/pricecategory/query.htm",
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var _fields = this.assitTypeRecord;
//		var _url = this.listUrl; 
//		var _store = new Ext.data.JsonStore({
//			root:"records",
//			totalProperty:'totals',
//			remoteSort:true,
//			fields:_fields,
//			url:_url,
//			autoLoad:false
//		});
//		
//		var grid = this;
//		
//		var _sm=new Ext.grid.CheckboxSelectionModel({
//			listeners: {
//	            selectionchange: function(sm) {
//	                if (sm.getCount()) {
//	                	Ext.getCmp("deleteButton").enable();
//	                } else {
//	                    Ext.getCmp("deleteButton").disable();
//	                }
//	            }
//	        }	
//		});
//		
//		var _cm=new Ext.grid.ColumnModel([_sm,{
//			header:"记录编号",
//			hidden:true,
//			dataIndex:"id"
//		},{
//			header:"辅助类别",
//			width:150,
//			dataIndex:"name"
//		}]);
//		
//		var c={
//			iconCls:"icon-grid",
//			loadMask:Context.LOADMASK,
//			store:_store,
//			sm:_sm,
//			cm:_cm,
//			tbar:[
//				{
//					iconCls:"delete",
//					id:"deleteButton",
//					text:"删除",
//					disabled:true,
//					handler:function(btn){
//						Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
//					}
//				}
//			]
//		};
//		
//		ast.ast1949.admin.priceCategory.AssitTypeGrid.superclass.constructor.call(this,c);
//	},
//	assitTypeRecord:Ext.data.Record.create([{
//		name: 'id',
//		mapping:'id',
//		type: 'int'
//	},{
//		name: 'name',
//		mapping:'name'
//	},{
//		name: 'sort',
//		mapping: 'sort'
//	}])
//});

/**
 * 删除关联类别
 * @param {} _btn
 */
//function doDelete(_btn){
//	if(_btn != "yes")
//			return ;
//			
//	var grid = Ext.getCmp(PRICECATEGORY.ASSIT_CATEGORY_GRID);
//	
//	var row = grid.getSelections();
//	var _ids = new Array();
//	for (var i=0,len = row.length;i<len;i++){
//		var _id=row[i].get("id");
//		_ids.push(_id);
//	}
//	/*提交*/
//	var conn = new Ext.data.Connection();
//	conn.request({
//		url: Context.ROOT+Context.PATH+ "/admin/pricecategory/deleteRelated.htm?random="+Math.random()+"&ids="+_ids.join(','),
//		method : "get",
//		scope : this,
//		callback : function(options,success,response){
//		var a=Ext.decode(response.responseText);
//			if(success){
//				ast.ast1949.utils.Msg(Context.MSG_TITLE,"选定的记录已被删除!");
//				
//				var grid = Ext.getCmp(PRICECATEGORY.ASSIT_CATEGORY_GRID);
//				var B = grid.store.baseParams;
//				B = B||{};
//				
//				grid.store.baseParams = B;
//				grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//			}else{
//				ast.ast1949.utils.Msg(Context.MSG_TITLE,"所选记录删除失败!");
//			}
//		}
//	});
//}

/**
 * <p>报价类别信息表单</p>
 * */
ast.ast1949.admin.priceCategory.CategoryForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,c);
		
		var c={
			abelAlign : "right",
			labelWidth : 80,
			region:"center",
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
				id:"id",
				name:"id"
			},{
				xtype:"combotree",
				fieldLabel:"父类别",
				id : "combo-parent",
				name : "combo-parent",
				hiddenName : "parentId",
				hiddenId : "parentId",
				editable:false,
				emptyText:"父节点，不选表示根节点...",
				tree:new ast.ast1949.admin.priceCategory.CategoryTreePanel({
					contextmenu:null
				})
			},{
				id:"name",
				name:"name",
				allowBlank:false,
				fieldLabel:"类别名称"
			},{
				fieldLabel:"链接地址",
				name:"goUrl",
				id:"goUrl"
			},{
				xtype:"numberfield",
				fieldLabel:"排序",
				name:"showIndex",
				id:"showIndex",
				value:100
			}],
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(PRICECATEGORY.CATEGORY_WIN).close();
				}
			}]
		};
		
		ast.ast1949.admin.priceCategory.CategoryForm.superclass.constructor.call(this,c);
	},
	reload:function(id){
		var _fields=[
			{name:"id",mapping:"priceCategoryDO.id"},
		    {name:"parentId",mapping:"priceCategoryDO.parentId"},
			{name:"name",mapping:"priceCategoryDO.name"},
			{name:"goUrl",mapping:"priceCategoryDO.goUrl"},
			{name:"showIndex",mapping:"priceCategoryDO.showIndex"},
			{name:"parentName",mapping:"parentName"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + Context.PATH + "/admin/pricecategory/init.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						//设置父类别
						var _pid=record.get("parentId");
						var _pname=record.get("parentName");
						
						if(_pid!=null){
							if(_pid>0){
								Ext.get("combo-parent").dom.value=_pname;
							}
							Ext.get("parentId").dom.value=_pid;
							
						}
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/save.htm",
	//保存
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
			ast.ast1949.utils.Msg(Context.MSG_TITLE,"验证未通过！");
		}
	},
	onSaveSuccess:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存成功！");
		Ext.getCmp(PRICECATEGORY.CATEGORY_WIN).close();
	},
	onSaveFailure:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存失败！");
	}
});

/**
 * <p>添加关联</p>
 * 
 * @class ast.ast1949.admin.priceCategory.AssitCategoryForm
 * @extends Ext.form.FormPanel
 */
//ast.ast1949.admin.priceCategory.AssitCategoryForm = Ext.extend(Ext.form.FormPanel, {
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,c);
//		
//		var c={
//			abelAlign : "right",
//			labelWidth : 80,
//			region:"center",
//			layout:"form",
//			bodyStyle:'padding:5px 0 0',
//			frame:true,
//			defaults:{
//				anchor:"95%",
//				xtype:"textfield",
//				labelSeparator:""
//			},
//			items:[{
//				xtype:"hidden",
//				id:"hidden-id",
//				name:"priceTypeId"
//			},{
//				xtype:"hidden",
//				id:"hidden-checked-id",
//				name:"ids",
//				allowBlank:false
//			}],
//			buttons:[{
//				text:"保存",
//				handler:this.save,
//				scope:this
//			},{
//				text:"关闭",
//				handler:function(){
//					Ext.getCmp(PRICECATEGORY.PRICE_CATEGORY_TREE_WIN).close();
//				}
//			}]
//		};
//		
//		ast.ast1949.admin.priceCategory.AssitCategoryForm.superclass.constructor.call(this,c);
//	},
//	saveUrl:Context.ROOT+Context.PATH + "/admin/pricecategory/addRelated.htm",
//	//保存
//	save:function(){
//		var _url = this.saveUrl;
//		if(this.getForm().isValid()){
//			this.getForm().submit({
//				url:_url,
//				method:"post",
//				type:"json",
//				success:this.onSaveSuccess,
//				failure:this.onSaveFailure,
//				scope:this
//			});
//		}else{
//			ast.ast1949.utils.Msg(Context.MSG_TITLE,"验证未通过！");
//		}
//	},
//	onSaveSuccess:function (form, action){
//		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存成功！");
//		var grid = Ext.getCmp(PRICECATEGORY.ASSIT_CATEGORY_GRID);
//		var B = grid.store.baseParams;
//		B = B||{};
//		var _id=Ext.get("hidden-id").dom.value;
//		if(_id!=null && _id!=""){
//			B["id"] = _id;
//		}else{
//			B["id"] = null;
//		}
//		grid.store.baseParams = B;
//		grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//		Ext.getCmp(PRICECATEGORY.PRICE_CATEGORY_TREE_WIN).close();
//	},
//	onSaveFailure:function (form, action){
//		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存失败！");
//	}
//});

/**
 * <p>
 * 弹出一个窗口，用于添加报价类别信息到指定的类别中
 * </p>
 * */
ast.ast1949.admin.priceCategory.AddCategoryWin = function(id,text){
	var form = new ast.ast1949.admin.priceCategory.CategoryForm();
	form.saveUrl = Context.ROOT+Context.PATH+"/admin/pricecategory/edit.htm";
	
	var win = new Ext.Window({
		id:PRICECATEGORY.CATEGORY_WIN,
		title:"添加报价类别",
		width:380,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
	Ext.get("combo-parent").dom.value=text;
	Ext.get("parentId").dom.value=id;
}

/**
 * <p>
 * 弹出一个窗口，用于编辑一个报价类别信息到指定的类别中
 * </p>
 * */
ast.ast1949.admin.priceCategory.EditCategoryWin = function(id){
	var form = new ast.ast1949.admin.priceCategory.CategoryForm();
	
	form.saveUrl = Context.ROOT+Context.PATH+"/admin/pricecategory/edit.htm";
	form.reload(id);
	var win = new Ext.Window({
		id:PRICECATEGORY.CATEGORY_WIN,
		title:"修改报价类别",
		width:380,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

/**
 * <p>弹出一个窗口，用于关联类别</p>
 * @param {} id 主类别编号
 * @param {} ids 已关联的类别编号，如：1,2,3,4
 */
//ast.ast1949.admin.priceCategory.PriceCategoryCheckBoxTreeWin = function(id,ids) {
//	var tree =new ast.ast1949.admin.priceCategory.checkBoxTreePanel({
//		layour:"fit",
//		region:"center",
//		width:200,
//		
//		ids:ids,
//		root:{
//			nodeType:'async',
//			text:"所有辅助类别",
//			data:0
//		}
//	});
//	
//	tree.on('checkchange', function(node, checked) {   
//		node.expand();   
//		node.attributes.checked = checked;
//		
//		//选中的叶子节点
////		node.eachChild(function(child) {   
////			child.ui.toggleCheck(checked);   
////			child.attributes.checked = checked;
//////			alert(child);
////			child.fireEvent('checkchange', child, checked);   
////		});
//		
//		//显示被选中的节点
//		Ext.get("hidden-checked-id").dom.value = tree.getChecked("id");
//	}, tree);
//	tree.expandAll();
//	
//	var _from = new ast.ast1949.admin.priceCategory.AssitCategoryForm({
//		title:"编辑报价信息",
//		height:57,
////		height:200,
//		layour:"fit",
//		region:"south"
//	});
//	
//	var form ={
//		height:57,
////		height:200,
//		layour:"fit",
//		region:"south",
//		autoScroll:true,
//		items:[_from]
//	};
//	
//	var win = new Ext.Window({
//		id:PRICECATEGORY.PRICE_CATEGORY_TREE_WIN,
//		title:"辅助类别",
//		width:450,
//		height:450,
//		modal:true,
//		layout:'border',
//		items:[form,tree]
//		
//	});
//	
//	win.show();
//	Ext.get("hidden-id").dom.value=id;
//	Ext.get("hidden-checked-id").dom.value=ids;
//}
