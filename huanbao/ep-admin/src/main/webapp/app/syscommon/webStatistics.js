Ext.namespace("com.zz91.ep.web");
 
com.zz91.ep.web.Field=[
	{name:"id",mapping:"id"},
	{name:"gmt_data",mapping:"gmtData"},
	{name:"register_stp1",mapping:"registerStp1"},
	{name:"register_stp2",mapping:"registerStp2"},
	{name:"message_supply",mapping:"messageSupply"},
	{name:"message_buy",mapping:"messageBuy"},
	{name:"message_company",mapping:"messageCompany"},
	{name:"message_admin",mapping:"messageAdmin"},
	{name:"company_news",mapping:"companyNews"},
	{name:"supply",mapping:"supply"},
	{name:"buy",mapping:"buy"},
	{name:"publish_company",mapping:"publishCompany"},
	{name:"publish_news",mapping:"publishNews"},
	{name:"login_count",mapping:"loginCount"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"gmt_modified",mapping:"gmtModified"}
];

com.zz91.ep.web.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.web.Field,
				url:Context.ROOT +  "/syscommon/queryWebStatistics.htm",
				autoLoad:true
		});
		
		var grid = this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				hidden:true,
				header : "编号",
				sortable: true,
				width:50,
				dataIndex : "id"
			},{
				header : "统计时间",
				sortable: true,
				width : 80,
				dataIndex : "gmt_data"
			},{
				header : "注册量(完成第一步)",
				sortable: true,
				dataIndex : "register_stp1"
			},{
				header : "注册量(完成第二步)",
				sortable: true,
				dataIndex : "register_stp2"
			},{
				header : "供应留言(客户发)",
				sortable: true,
				dataIndex : "message_supply"
			},{
				header : "求购信息",
				sortable: true,
				width:60,
				dataIndex : "message_buy"
			},{
				header : "公司留言",
				sortable: true,
				width:60,
				dataIndex : "message_company"
			},{
				header : "后台代发留言数",
				sortable: true,
				dataIndex : "message_admin"
			},{
				header : "公司发布公司动态，技术文章，成功案例数",
				sortable: true,
				dataIndex : "company_news"
			},{
				header : "发布供应信息数",
				sortable: true,
				dataIndex : "supply"
			},{
				header : "发布求购信息数",
				sortable: true,
				dataIndex : "buy"
			},{
				header : "有发布供求的企业数",
				sortable: true,
				dataIndex : "publish_company"
			},{
				header : "发布企业文章的企业数",
				sortable: true,
				dataIndex : "publish_news"
			},{
				header : "当天登录总数",
				sortable: true,
				dataIndex : "login_count"
			},{
				hidden:true,
				header : "创建时间",
				sortable: true,
				width : 130,
				dataIndex : "gmt_created",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:m:s');
					}
				}
			},{
				hidden:true,
				header : "最后更改时间",
				sortable: true,
				width : 130,
				dataIndex : "gmt_modified",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:m:s');
					}
				}
			}
		]);
	
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar: com.zz91.utils.pageNav(_store)
		}
		com.zz91.ep.web.Grid.superclass.constructor.call(this,c);
	}
});
