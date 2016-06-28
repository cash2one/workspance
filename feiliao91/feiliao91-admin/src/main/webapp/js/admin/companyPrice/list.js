Ext.namespace("ast.ast1949.admin.companyPrice");

var _C = new function() {
	this.RESULT_GRID="resultgrid";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.companyPrice.ResultGrid({
		title:"企业报价管理",
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT + Context.PATH + "/admin/companyprice/query.htm",
		region:'center',
		autoScroll:true
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[resultgrid]
	});
});

ast.ast1949.admin.companyPrice.ResultGrid =Ext.extend(Ext.grid.GridPanel, {
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
	                    Ext.getCmp("checked").enable();
	                    Ext.getCmp("unchecked").enable();
	                } else {
            	       	Ext.getCmp("edit").disable();
	                    Ext.getCmp("delete").disable();
	                    Ext.getCmp("checked").disable();
	                    Ext.getCmp("unchecked").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
            {
				header : "类别",
				sortable : false,
				dataIndex : "categoryName"
			}, {
				header : "来源",
				sortable : false,
				dataIndex : "productId",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value == 0) {
						return "供求推荐";
					} else {
						return "myrc发布";
					}
				}
			}, {
				header : "产品名称",
				width : 150,
				sortable : false,
				dataIndex : "title"
			}, {
				header : "产品价格",
				sortable : false,
				dataIndex : "price"
			}, {
				header : "单位",
				sortable : false,
				dataIndex : "priceUnit"
			}, {
				header : "时间",
				sortable : false,
				dataIndex : "refreshTime",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(
								new Date(value.time), 'Y-m-d');
					}
				}
			}, {
				header : "审核状态",
				sortable : false,
				dataIndex : "isChecked",
				renderer:function (value,metadata,record,rowIndex,colIndex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			sm:_sm,
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
			}),
			listeners:{
				"render":this.buttonQuery
			}
		};
		
		ast.ast1949.admin.companyPrice.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
       {
			name : "id",
			mapping : "companyPriceDO.id"
		}, {
			name : "productId",
			mapping : "companyPriceDO.productId"
		}, {
			name : 'title',
			mapping : 'companyPriceDO.title'
		}, {
			name : 'price',
			mapping : 'companyPriceDO.price'
		}, {
			name : 'priceUnit',
			mapping : 'companyPriceDO.priceUnit'
		}, {
			name : 'refreshTime',
			mapping : 'companyPriceDO.refreshTime'
		}, {
			name : 'isChecked',
			mapping : 'companyPriceDO.isChecked'
		}, "categoryName"
    ]),
    listUrl:Context.ROOT + Context.PATH + "/admin/companyprice/query.htm",
    mytoolbar:[{
    	id:"edit",
		text : '修改',
		tooltip : '修改一条记录',
		iconCls : 'edit',
		handler : function(btn){
			var grid = Ext.getCmp(_C.RESULT_GRID);
			
			var row = grid.getSelections();
			if(row.length>1){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "最多只能选择一条记录！",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			} else {
				var _id=row[0].get("id");
				
				new ast.ast1949.admin.companyPrice.EditFormWin({id : _id, grid : grid});
			}
		},
		disabled:true,
		scope : this
	}, {
		id:"delete",
		text : '删除',
		tooltip : '删除一条记录',
		iconCls : 'delete',
		handler : function(btn){
			var grid = Ext.getCmp(_C.RESULT_GRID);
			var row = grid.getSelections();
			
			if (row.length == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
			else {
					Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要将选中的记录设置为删除吗?', function doDelete(_btn) {
						if (_btn != "yes")
							return;
						var _ids = new Array();
						for (var i = 0, len = row.length; i < len; i++) {
							var _id = row[i].get("id");
							_ids.push(_id);
						}
		
						var conn = new Ext.data.Connection();
						conn.request({
									url : Context.ROOT + Context.PATH
											+ "/admin/companyprice/delete.htm?ids="
											+ _ids.join(','),
									method : "get",
									scope : this,
									callback : function(options, success, response) {
										if (success) {
											Ext.MessageBox.alert(Context.MSG_TITLE,
													"所选记录删除成功!");
											ast.ast1949.admin.companyPrice.resultGridReload();
										} else {
											Ext.MessageBox.alert(Context.MSG_TITLE,
													"所选记录删除失败!");
										}
									}
								});
					});
			}
		},
		disabled:true,
		scope : this
	}, {
		id:"checked",
		text : '审核',
		tolltip : '审核',
		iconCls : 'edit',
		handler : function(){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要审核选中的记录?', function doChecked(_btn) {
				if (_btn != "yes")
					return;
				var grid = Ext.getCmp(_C.RESULT_GRID);
				var row = grid.getSelections();
				var _ids = new Array();
				for (var i = 0, len = row.length; i < len; i++) {
					var _id = row[i].get("id");
					_ids.push(_id);
				}
				var conn = new Ext.data.Connection();
				conn.request({
							url : Context.ROOT + Context.PATH
									+ "/admin/companyprice/check.htm?ids="
									+ _ids.join(',') + "&isChecked=" + 1,
							method : "get",
							scope : this,
							callback : function(options, success, response) {
								if (success) {
									Ext.MessageBox.alert(Context.MSG_TITLE,
											"选定的记录已审核!");
									ast.ast1949.admin.companyPrice.resultGridReload();
								} else {
									Ext.MessageBox.alert(Context.MSG_TITLE,
											"所选记录审核失败!");
								}
							}
						});

			});
		},
		disabled:true,
		scope : this
	}, {
		id:"unchecked",
		text : '取消审核',
		tolltip : '取消审核',
		iconCls : 'edit',
		handler : function(){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要取消审核选中的记录?', function doCancelChecked(_btn) {
				if (_btn != "yes")
					return;
					
				var grid = Ext.getCmp(_C.RESULT_GRID);
				var row = grid.getSelections();
				var _ids = new Array();
				for (var i = 0, len = row.length; i < len; i++) {
					var _id = row[i].get("id");
					_ids.push(_id);
				}

				var conn = new Ext.data.Connection();
				conn.request({
							url : Context.ROOT + Context.PATH
									+ "/admin/companyprice/check.htm?ids="
									+ _ids.join(',') + "&isChecked=" + 0,
							method : "get",
							scope : this,
							callback : function(options, success, response) {
								if (success) {
									Ext.MessageBox.alert(Context.MSG_TITLE,
											"选定的记录已被取消审核!");
									ast.ast1949.admin.companyPrice.resultGridReload();
								} else {
									Ext.MessageBox.alert(Context.MSG_TITLE,
											"所选记录取消审核失败!");
								}
							}
						});

			});
		},
		disabled:true,
		scope : this
	}],
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:["名称", {
				xtype : "textfield",
				id : "keyname",
				width : 150
			}, " ",new ast.ast1949.admin.categoryCompanyPrice.companyPriceTree({
				fieldLabel : "报价类别",
				id : "search-combo-assistTypeId",
				name : "search-combo-assistTypeId",
				hiddenName : "categoryCompanyPriceCode",
				hiddenId : "categoryCompanyPriceCode",
				emptyText : "报价类别",
				readOnly : true,
				allowBlank : true,
				width : "50",
				el : "search-assistTypeId-combo",
				rootData : 0
			}), " -", {
				text : "查询",
				iconCls : "query",
				handler : function() {
				
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.store.baseParams = {
						"title" : Ext.get("keyname").dom.value,
						"categoryCompanyPriceCode" :Ext.get("categoryCompanyPriceCode").dom.value
					};
					grid.store.reload();
				}
			}]
		});
		
		tbar2.render(this.tbar);
	}
})

//重新绑定Grid数据
ast.ast1949.admin.companyPrice.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}