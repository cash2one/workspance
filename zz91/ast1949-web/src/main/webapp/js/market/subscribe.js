Ext.namespace("ast.ast1949.admin.market.subscribe");

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

ast.ast1949.admin.market.subscribe.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header : "公司名称",
				width : 180,
				dataIndex : "companyName",
			},{
				header : "登录帐号",
				width : 180,
				dataIndex : "companyAccount",
			},{
				header : "关键词定制",
				width : 180,
				dataIndex : "keywordsTags",
			},{
				header : "市场定制",
				width : 180,
				dataIndex : "marketTags",
			},{
				header : "定制时间",
				sortable : true,
				width:135,
				dataIndex : "gmt_created",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"最后修改时间",
				sortable:true,
				width:135,
				dataIndex:"gmt_modified",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			}
		]);
		
		var con={
				iconCls:"icon-grid",
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
//				tbar:[{
//					text:"审核",
//					menu:[{
//						text:"审核通过",
//						handler:function(btn,e){
//							ast.feiliao91.admin.goods.updateStatus(1);
//						}
//					},{
//						text:"审核不通过",
//						handler:function(btn,e){
//							ast.feiliao91.admin.goods.updateStatus(2);
//						}
//					}]
//				}],
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
			};
		ast.ast1949.admin.market.subscribe.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"subscribe.id"},
		{name:"companyName",mapping:"company.name"},
		{name:"companyAccount",mapping:"account.account"},
		{name:"companyId",mapping:"subscribe.companyId"},
		{name:"keywordsTags",mapping:"keywordsTags"},
		{name:"marketTags",mapping:"marketTags"},
		{name:"gmt_created",mapping:"subscribe.gmtCreated"},
		{name:"gmt_modified",mapping:"subscribe.gmtModified"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/market/querySubscribe.htm",
});


//右下部搜索框
ast.ast1949.admin.market.subscribe.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
						fieldLabel : "公司名：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["name"] = undefined;
								}else{
									B["name"] = newvalue;
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
					},{
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
						fieldLabel : "手机号码：",
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
		
		ast.ast1949.admin.market.subscribe.SearchForm.superclass.constructor.call(this,c);
	}
});