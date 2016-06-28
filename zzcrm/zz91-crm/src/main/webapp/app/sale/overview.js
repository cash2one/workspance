Ext.namespace("com.zz91.ep.crm.overview");

com.zz91.ep.crm.overview.Field=[
	{name:"id",mapping:"id"},
	{name:"totals",mapping:"totals"},
	{name:"sea_count",mapping:"seaCount"},
	{name:"no_active_count",mapping:"noActiveCount"},
	{name:"self_count",mapping:"selfCount"},
	{name:"waste_count",mapping:"wasteCount"},
	{name:"repeat_count",mapping:"repeatCount"},
	{name:"today_assign_count",mapping:"todayAssignCount"},
	{name:"today_choose_count",mapping:"todayChooseCount"},
	{name:"today_put_count",mapping:"todayPutCount"},
	{name:"gmt_target",mapping:"gmtTarget"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"gmt_modified",mapping:"gmtModified"}
];
com.zz91.ep.crm.overview.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.crm.overview.Field,
				url:Context.ROOT +  "/sale/data/queryCrmStatisticsData.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				header : "编号",
				dataIndex : "id",
				hidden:true
			},{
				header : "统计日期",
				dataIndex : "gmt_target",
				width:120,
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				hidden:true,
				header : "创建时间",
				dataIndex : "gmt_created",
				width:120,
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header : "总客户数",
				dataIndex : "totals",
				sortable:true
			},{
				header:"公海客户数",
				dataIndex:"sea_count",
				sortable:true
			},{
				header:"未激活客户数",
				dataIndex:"no_active_count",
				sortable:true
			},{
				header:"个人库客户总数",
				dataIndex:"self_count",
				sortable:true
			},{
				header:"废品池客户",
				dataIndex:"waste_count",
				sortable:true
			},{
				header:"重复客户库",
				dataIndex:"repeat_count",
				sortable:true
			},{
				header :"今日新分配客户总数",
				width:130,
				dataIndex:"today_assign_count",
				sortable : true
			},{
				header : "今天公海挑入总数",
				width:120,
				dataIndex : "today_choose_count",
				sortable : true
			},{
				header : "今日丢入公海数",
				dataIndex : "today_put_count",
				sortable : true
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar: com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.ep.crm.overview.Grid.superclass.constructor.call(this,c);
	}
});
