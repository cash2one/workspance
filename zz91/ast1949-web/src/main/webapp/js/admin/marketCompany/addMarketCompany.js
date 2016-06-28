Ext.namespace("ast.ast1949.admin.products.market");
// 定义变量
var _C = new function() {
	this.MARKET_GRID = "marketGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.admin.products.market.MARKETFIELD=[
  	{name:"id",mapping:"id"},
	{name:"name",mapping:"name"},
	{name:"area",mapping:"area"},
	{name:"industry",mapping:"industry"},
	{name:"category",mapping:"category"},
	{name:"companyId",mapping:"companyId"},
	{name:"business",mapping:"business"}
];

ast.ast1949.admin.products.market.marketGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header:"公司ID",
				sortable : true,
				hidden:true,
				name : "companyId",
				id : "companyId",
				dataIndex:"companyId"
			},{    	
				header:"市场名称",
				width:100,
				id:"name",
				dataIndex : "name"	
			},{
				header:"市场所在地",
				width:100,
				dataIndex : "area"
				
			},{
				header:"所属行业",
				width:100,
				dataIndex : "industry"
			},{
				header:"行业类别",
				width:100,
				dataIndex : "category"
				
			}, {
				header : "主营业务",
				width : 100,
				dataIndex : "business"
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/marketcompany/queryListMarketBycompanyId.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.products.market.MARKETFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
				iconCls:"delete",
				text:"退出该市场",
				handler:function(btn){
					var grid= Ext.getCmp("marketGrid");
					var rows=grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						
						Ext.MessageBox.confirm(Context.MSG_TITLE,"确定退出该市场",function(btn){
							if(btn!="yes"){
								return ;
							}else{
								ast.ast1949.admin.products.market.updateIsQuitByMarketId(rows[0].get("id"),rows[0].get("companyId"));
								_store.reload();
							}
						});	
					}
					
				}
			
	    	}];
		var c={
			id:"marketGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
			bbar: new Ext.PagingToolbar({
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
		ast.ast1949.admin.products.market.marketGrid.superclass.constructor.call(this,c);
	},
	insertMarketCopany:function(marketId,companyId){
       	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+ "/admin/marketcompany/insertMarketCopany.htm",
		params:{
			"marketId":marketId,
			"companyId":companyId
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				ast.ast1949.utils.Msg("","添加已成功")
				var grid= Ext.getCmp("marketGrid");
				grid.getStore().reload();
				
			}else{
				ast.ast1949.utils.Msg("","操作失败");
			}
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
    }

});
ast.ast1949.admin.products.market.marketResultGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{    	
				header:"市场名称",
				width:100,
				id:"name",
				dataIndex : "name"	
			},{
				header:"市场所在地",
				width:100,
				dataIndex : "area"
				
			},{
				header:"所属行业",
				width:100,
				dataIndex : "industry"
			},{
				header:"行业类别",
				width:100,
				dataIndex : "category"
				
			}, {
				header : "主营业务",
				width : 100,
				dataIndex : "business"
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/products/listMarket.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.products.market.MARKETFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = ["市场名称:",{
					xtype:"textfield",
					width:200,
					id:"search-company-name",
					listeners:{
					"blur":function(field){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["name"] = field.getValue();
								}else{
									B["name"]=undefined;
								}
								_store.baseParams = B;
								_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				    	 }
				    }
			
	    	}];
		var c={
			id:"marketResultGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			tbar : tbar,
			iconCls : "icon-grid",
			store:_store,
			bbar: new Ext.PagingToolbar({
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
		ast.ast1949.admin.products.market.marketResultGrid.superclass.constructor.call(this,c);
	},
	

});
//退出改市场
ast.ast1949.admin.products.market.updateIsQuitByMarketId=function(MarketId,companyId){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/admin/marketcompany/updateIsQuitByMarketId.htm",
		params:{
		"marketId":MarketId,
		"companyId":companyId
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			ast.ast1949.utils.Msg("",obj.data)

		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
}
