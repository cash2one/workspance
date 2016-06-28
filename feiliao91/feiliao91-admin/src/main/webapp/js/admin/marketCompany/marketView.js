Ext.namespace("ast.ast1949.admin.products.market");
// 定义变量
var _C = new function() {
	this.PHONESEOKEYWORD_GRID = "marketGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.admin.products.market.MARKETFIELD=[
  	{name:"id",mapping:"market.id"},
	{name:"area",mapping:"market.area"},
	{name:"marketName",mapping:"market.name"},
	{name:"category",mapping:"market.category"},
	{name:"industry",mapping:"market.industry"},
	{name:"name",mapping:"company.name"},
	{name:"companyId",mapping:"company.id"},
	{name:"account",mapping:"account"},
	{name:"regTime",mapping:"regTime"}
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
				header:"公司名字",
				width:100,
				dataIndex : "name"	
			},{    	
				header:"登录帐号",
				width:100,
				dataIndex : "account"	
			},{    	
				header:"所属类别",
				width:100,
				dataIndex : "category"	
			},{    	
				header:"所属行业",
				width:100,
				dataIndex : "industry"	
			},{    	
				header:"市场入驻时间",
				width:200,
				dataIndex : "regTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{    	
				header:"所属市场",
				width:100,
				dataIndex : "marketName"	
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/marketcompany/queryListMarketByadmin.htm";;
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.products.market.MARKETFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
				iconCls:"edit",
				text:"入驻市场",
				handler:function(btn){
					var grid = Ext.getCmp("marketGrid");
					var rows = grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else if(rows.length==0){
						// 没有选择的情况下，选择 store本身的 companyId作为添加id
						var B = grid.store.baseParams;
						if(B["companyId"]!=""){
							window.open(Context.ROOT+Context.PATH+"/admin/marketcompany/addMarketCompany.htm?companyId="+B["companyId"]);
						}else{
							ast.ast1949.utils.Msg("","请选择一条记录");
						}
					}else{
						window.open(Context.ROOT+Context.PATH+"/admin/marketcompany/addMarketCompany.htm?companyId="+rows[0].get("companyId"));
					}
					
				}
			},{
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
	initFormByCid:function(companyId){
		var B = this.store.baseParams;
		if(companyId==""){
			B["companyId"] = undefined;
		}else{
			B["companyId"] = companyId;
		}
		this.baseParams = B;
	}

});


ast.ast1949.admin.products.market.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 80,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "公司名称：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["companyName"] = undefined;
							}else{
								B["companyName"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "帐号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["account"] = undefined;
							}else{
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}
				,{
					fieldLabel : "邮箱：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["email"] = undefined;
							}else{
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "手机号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["mobile"] = undefined;
							}else{
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};
		
		ast.ast1949.admin.products.market.SearchForm.superclass.constructor.call(this,c);

	}
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
