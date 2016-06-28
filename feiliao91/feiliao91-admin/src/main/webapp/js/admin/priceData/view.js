Ext.namespace("ast.ast1949.admin.price")

/**
 * 常量
 */
var _C = new function(){
	this.COMPANY_PRICE_GRID="COMPANY_PRICE_GRID";
	this.PRICE_DATA_FRID="PRICE_DATA_FRID";
}

Ext.onReady(function(){
	//CompanyPrice
	var companyPriceGrid = new ast.ast1949.admin.price.CompanyPriceResultGrid({
//		title:"企业报价管理",
//		ddGroup : "priceData",
//		enableDragDrop : true,
//		stripeRows : true,
		
		id:_C.COMPANY_PRICE_GRID,
		listUrl:Context.ROOT + Context.PATH + "/admin/companyprice/query.htm",
//		region:'center',
		autoScroll:true
	});
	
	var priceDataGrid = new ast.ast1949.admin.price.PriceDataResultGrid({
//		ddGroup : "companyPrice",
//		enableDragDrop : true,
//		stripeRows : true,
		
		id:_C.PRICE_DATA_FRID,
		listUrl:Context.ROOT + Context.PATH + "/admin/pricedata/query.htm?id="+_CONST.PRICE_ID,
		autoScroll:true
	});
	
	//手动添加
	priceDataGrid.on("afteredit",function(e) {
		
		var _id=e.record.get("id");
		var _productName=e.record.get("productName");
		var _quote=e.record.get("quote");
		var _area=e.record.get("area");
		var _companyName=e.record.get("companyName");
		
		Ext.Ajax.request({
			url: Context.ROOT+Context.PATH+ "/admin/pricedata/insert.htm",
			params:{
				"id":_id,
				"productName":_productName,
				"quote":_quote,
				"area":_area,
				"companyName":_companyName,
				"priceId":_CONST.PRICE_ID,
				"companyId":0,
				"companyPriceId":0,
				"showIndex":0
			},
			method : "post",
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.admin.price.PriceDataResultGridReload();
					if(_id>0){
						ast.ast1949.utils.Msg("","修改成功！");
					} else {
						ast.ast1949.utils.Msg("","添加成功！");
						ast.ast1949.admin.price.PriceDataResultGridReload();
					}
				}else{
					ast.ast1949.utils.Msg("","添加失败！");
				}
			},
			failure:function(response,opt){
				ast.ast1949.utils.Msg("","添加失败！");
			}
		});
	});
	
	//form
	var _left = {
		title : "企业报价列表",
		region : "west",
		layout : "fit",
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		collapsible:true,
		split: true,
		width: "50%",
		maxSize: 380,
		minSize:280,
		items : [companyPriceGrid]
	}
	
	var _center = {
//		title: "nothing",
		region : "center",
		layout : "fit",
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		collapsible:true,
		split: true,
//		width: "50%",
		maxSize: 380,
		minSize:280,
		items : [{html:"Oh,no!If you nothing to do?You can drag the data to the right to left."}]
	}
	
	var _right = {
		title : "报价数据",
		region : "east",
		layout : "fit",
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		collapsible:true,
		split: true,
		width: "50%",
		maxSize: 380,
		minSize:280,
		items : [priceDataGrid]
	}
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[_left, _center, _right]
	});
	
});


//企业报价
ast.ast1949.admin.price.CompanyPriceResultGrid = Ext.extend(Ext.grid.GridPanel, {
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
			Ext.getCom()
	                if (sm.getCount()) {
	             	
                    Ext.getCmp("edit").enable();
	                } else {
          	       	Ext.getCmp("edit").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
            {
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
				dataIndex : "title",
				renderer: function(value,metadate,record,rowIndex,colIndex,store){
					 var  val="";
					if(!this.readOnly){
						val=" <a href='"+
							Context.ROOT+Context.PATH+
							"/admin/products/edit.htm?productid="+record.get("productId")+
							"&companyid="+record.get("companyId")+
							"&account="+record.get("account")+
							"' target='_blank'>"+value+"</a>";
					}
					return val;
				}
				
			}, {
				header : "产品价格",
				sortable : false,
				dataIndex : "price",
				renderer:function (value,metadata,record,rowIndex,colIndex,store){
					if(record.get("maxPrice")!=""&& record.get("maxPrice") !="0.0"){
						return record.get("minPrice") +" ~ "+record.get("maxPrice");
					}
					return record.get("minPrice");
				}
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
				"rowdblclick":function(g, rowIndex, e){
					        var grid = Ext.getCmp(_C.COMPANY_PRICE_GRID);
						var row = grid.getSelections();
						var selectedRecord = grid.getSelectionModel().getSelected();
						if(row.length>1){
//							ast.ast1949.utils.Msg("","最多只能选择一条记录");
//							Ext.MessageBox.show({
//								title:Context.MSG_TITLE,
//								msg : "最多只能选择一条记录！",
//								buttons:Ext.MessageBox.OK,
//								icon:Ext.MessageBox.ERROR
//							});
						} else {
							//获取数据
							var row = grid.getSelections();
							var _priceId=_CONST.PRICE_ID
							var _companyPriceId=row[0].get("id");
							var _productName=row[0].get("title");
							var _quote="";
							
							var minPrice=row[0].get("minPrice");
							var maxPrice=row[0].get("maxPrice");
							
							if(maxPrice != ""&& maxPrice !="0.0"){
								_quote=minPrice+"-"+maxPrice+" "+row[0].get("priceUnit");
							}else{
								_quote=minPrice+" "+row[0].get("priceUnit");
							}
							
							var _companyId=row[0].get("companyId");
							var _showIndex="0";
							
							Ext.Ajax.request({
								url: Context.ROOT+Context.PATH+ "/admin/pricedata/insert.htm",
								params:{
									"priceId":_priceId, 
									"companyPriceId":_companyPriceId, 
									"productName":_productName,
									"quote":_quote,
									"companyId":_companyId,
									"showIndex":_showIndex
								},
								method : "post",
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										ast.ast1949.admin.price.PriceDataResultGridReload();
										ast.ast1949.utils.Msg("","数据添加成功！")
									}else{
										ast.ast1949.utils.Msg("","数据添加失败！")
									}
								},
								failure:function(response,opt){
									ast.ast1949.utils.Msg("","数据添加失败！")
								}
							});
							
						}
				}
//				"render":this.buttonQuery
			}
		};
		
		ast.ast1949.admin.price.CompanyPriceResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
       {
			name : "id",
			mapping : "companyPriceDO.id"
		}, {
			name : "companyId",
			mapping : "companyPriceDO.companyId"
		}, {
			name : "productId",
			mapping : "companyPriceDO.productId"
		}, {
			name : "account",
			mapping : "companyPriceDO.account"
		}, {
			name : 'title',
			mapping : 'companyPriceDO.title'
		}, {
			name : 'minPrice',
			mapping : 'companyPriceDO.minPrice'
		}, {
			name : 'maxPrice',
			mapping : 'companyPriceDO.maxPrice'
		}, {
			name : 'priceUnit',
			mapping : 'companyPriceDO.priceUnit'
		}, {
			name : 'refreshTime',
			mapping : 'companyPriceDO.refreshTime'
		}, {
			name : 'isChecked',
			mapping : 'companyPriceDO.isChecked'
		}
    ]),
    listUrl:Context.ROOT + Context.PATH + "/admin/companyprice/query.htm",
    mytoolbar:["名称", {
				xtype : "textfield",
				id : "keyname",
				width : 150
			}, " ",new ast.ast1949.admin.categoryCompanyPrice.companyPriceTree({
				fieldLabel : "报价类别",
				id : "search-combo-assistTypeId",
				name : "search-combo-assistTypeId",
				hiddenName : "categoryCode",
				hiddenId : "categoryCode",
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
				
					var grid = Ext.getCmp(_C.COMPANY_PRICE_GRID);
					grid.store.baseParams = {
						"title" : Ext.get("keyname").dom.value,
						"categoryCode" :Ext.get("categoryCode").dom.value
					};
					grid.store.reload();
				}
			},{
				text : '添加',
				iconCls : 'add',
				handler : function(btn){		
				var row = Ext.getCmp(_C.COMPANY_PRICE_GRID).getSelectionModel().getSelections();
					if (row.length > 0) {
						Ext.MessageBox.confirm(Context.MSG_TITLE, '否要将选中的 ' + row.length + '条记录信息通过?', function(_btn){
							if (_btn != "yes"){
								return;
							}
							for (var i = 0; i < row.length; i++) {
								var _priceId=_CONST.PRICE_ID
								var _companyPriceId=row[i].get("id");
								var _productName=row[i].get("title");
								var _quote="";
								
								var minPrice=row[i].get("minPrice");
								var maxPrice=row[i].get("maxPrice");
								
								if(maxPrice != "" && maxPrice !="0.0"){
									_quote=minPrice+"-"+maxPrice+" "+row[i].get("priceUnit");
								}else{
									_quote=minPrice+" "+row[i].get("priceUnit");
								}
								
								var _companyId=row[i].get("companyId");
								var _showIndex="0";
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+ "/admin/pricedata/insert.htm",
									params:{"priceId":_priceId, 
									"companyPriceId":_companyPriceId, 
									"productName":_productName,
									"quote":_quote,
									"companyId":_companyId,
									"showIndex":_showIndex},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											ast.ast1949.admin.price.PriceDataResultGridReload();
											ast.ast1949.utils.Msg("","数据添加成功！")
										}else{
											ast.ast1949.utils.Msg("","数据添加失败！")
										}
									},
									failure:function(response,opt){
										ast.ast1949.utils.Msg("","数据添加失败！")
									}
								});
							}
						});
					}
				}
		    }]
})

//重新绑定Grid数据
ast.ast1949.admin.price.CompanyPriceResultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.COMPANY_PRICE_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}


//报价数据
ast.ast1949.admin.price.PriceDataResultGrid = Ext.extend(Ext.grid.EditorGridPanel, {
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
	                    Ext.getCmp("delete").enable();
	                } else {
            	       	Ext.getCmp("delete").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
            {
				header : "产品名称",
				sortable : false,
				dataIndex : "productName",
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			}, {
				header : "报价",
				sortable : false,
				dataIndex : "quote",
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			}, {
				header : "地区",
				sortable : false,
				dataIndex : "area",
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			}, {
				header : "企业名称",
				sortable : true,
				dataIndex : "company_name",
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			clicksToEdit:2,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			selModel: new Ext.grid.RowSelectionModel({
				moveEditorOnEnter :false,
				singleSelect:true   //配置是否单选模式
			}),
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
//				"render":this.buttonQuery
			}
		};
		
		ast.ast1949.admin.price.PriceDataResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
	   {name:"id",mapping:"id"},
       {name : "productName", mapping : "productName"},
       {name : "quote", mapping : "quote"},
       {name : "area", mapping : "area"},
       {name : "company_name", mapping : "companyName"}
    ]),
    listUrl:Context.ROOT + Context.PATH + "/admin/pricedata/query.htm?id="+_CONST.PRICE_ID,
    mytoolbar:[{
    	text : "添加数据",
		iconCls : "add",
		handler : function() {
			var grid=Ext.getCmp(_C.PRICE_DATA_FRID)
			
			var priceData = Ext.data.Record.create([
			 	{name:"id",mapping:"id"},
				{name: 'productName', type: 'string'},
				{name: 'quote', type: 'string'},
				{name: 'area', type: 'string'},
				{name: 'companyName', type: 'string'}
			]);
			
			var r = new priceData({
				id:"0",
				productName:"",
				quote:"",
				area:"",
				companyName:""
            });
            var row=grid.getStore().getCount();
            grid.stopEditing();
            grid.getStore().insert(row, r);
            grid.startEditing(row, 1);
		}
    },{
    	id:"delete",
    	disabled:true,
    	text : "删除数据",
		iconCls : "delete",
		handler : function() {
			Ext.MessageBox.confirm(Context.MSG_TITLE, '你真的要删除所选记录?',function(btn){
				if(btn!="yes"){
					return ;
				}

				var grid = Ext.getCmp(_C.PRICE_DATA_FRID);
				var row = grid.getSelectionModel().getSelections();
				var _ids = new Array();
				for (var i=0,len = row.length;i<len;i++){
					var _id=row[i].get("id");
					_ids.push(_id);
				}

				Ext.Ajax.request({
					url: Context.ROOT+Context.PATH+ "/admin/pricedata/delete.htm",
					params:{"ids":_ids.join(",")},
					method : "post",
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							ast.ast1949.admin.price.PriceDataResultGridReload();
							ast.ast1949.utils.Msg("","删除成功！");
						}else{
							ast.ast1949.utils.Msg("","删除失败！");
						}
					},
					failure:function(response,opt){
						ast.ast1949.utils.Msg("","删除失败！");
					}
				});
			});
		}
    },{
    	text : "刷新",
		iconCls : "refresh",
		handler : function() {
			ast.ast1949.admin.price.PriceDataResultGridReload();
		}
    }]
});

ast.ast1949.admin.price.PriceDataResultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.PRICE_DATA_FRID);
	//定位到第一页
//	grid.store.baseParams = {"id" : Ext.get("keyname").dom.value};
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}
