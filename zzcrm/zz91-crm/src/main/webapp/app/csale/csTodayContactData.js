Ext.namespace("com.zz91.ep.cscrm");

com.zz91.ep.cscrm.DataField=[
	{name:"id",mapping:"statistics.id"},
	{name:"star1_able",mapping:"statistics.star1Able"},
	{name:"star1_disable",mapping:"statistics.star1Disable"},
	{name:"star2_able",mapping:"statistics.star2Able"},
	{name:"star2_disable",mapping:"statistics.star2Disable"},
	{name:"star3_able",mapping:"statistics.star3Able"},
	{name:"star3_disable",mapping:"statistics.star3Disable"},
	{name:"star4_able",mapping:"statistics.star4Able"},
	{name:"star4_disable",mapping:"statistics.star4Disable"},
	{name:"star5_able",mapping:"statistics.star5Able"},
	{name:"star5_disable",mapping:"statistics.star5Disable"},
	{name:"drag_order_count",mapping:"statistics.dragOrderCount"},
	{name:"destroy_order_count",mapping:"statistics.destroyOrderCount"},
	{name:"sale_account",mapping:"statistics.saleAccount"},
	{name:"sale_dept",mapping:"statistics.saleDept"},
	{name:"sale_name",mapping:"statistics.saleName"},
	{name:"gmt_target",mapping:"statistics.gmtTarget"},
	{name:"gmt_created",mapping:"statistics.gmtCreated"},
	{name:"gmt_modified",mapping:"statistics.gmtModified"},
	{name:"add_renew_count",mapping:"statistics.addRenewCount"},
	{name:"able_total",mapping:"ableTotal"},
	{name:"disable_total",mapping:"disableTotal"},
	{name:"able_rate",mapping:"ableRate"},
	{name:"dateStartEnd",mapping:"dateStartEnd"},
	{name:"tomContactCount",mapping:"tomContactCount"},
	{name:"total",mapping:"total"}
];
com.zz91.ep.cscrm.DataGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				fields:com.zz91.ep.cscrm.DataField,
				url:Context.ROOT + "/csale/csdata/queryDataByToday.htm",
				autoLoad:true
		});
		
		var grid = this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				hidden:true,
				header : "编号",
				dataIndex : "id"
			},{
				header:"销售",
				width:60,
				dataIndex:"sale_name",
				sortable:true
			},{
				hidden:true,
				header:"销售账号",
				width:60,
				dataIndex:"sale_account",
				sortable:true
			},{
				header:"日期",
				width:80,
				dataIndex:"dateStartEnd",
				sortable:true
			},{
				header:"五星有效",
				width:60,
				dataIndex:"star5_able",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=5' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header:"五星无效",
				width:60,
				dataIndex:"star5_disable",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=5' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header:"四星有效",
				width:60,
				dataIndex:"star4_able",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=4' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header :"四星无效",
				width:60,
				dataIndex:"star4_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=4' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "三星有效",
				width:60,
				dataIndex : "star3_able",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=3' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "三星无效",
				width:60,
				dataIndex : "star3_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=3' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "二星有效",
				width:60,
				dataIndex : "star2_able",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=2' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "二星无效",
				width:60,
				dataIndex : "star2_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=2' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "一星有效",
				width:60,
				dataIndex : "star1_able",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=1' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "一星无效",
				width:60,
				dataIndex : "star1_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=1' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "增值/续签",
				width:70,
				dataIndex : "add_renew_count",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&type=3' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "有效合计",
				width:60,
				dataIndex : "able_total",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "无效合计",
				width:60,
				dataIndex : "disable_total",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "总联系次数",
				width:80,
				dataIndex : "total",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var url = "<a href='"+Context.ROOT + "/csale/cscompany/todayCrmLog.htm?account="+account+"&tdate="+Ext.util.Format.date(new Date(), 'Y-m-d')+"' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "有效联系率(%)",
				width:100,
				dataIndex : "able_rate",
				sortable : true
			},{
				header : "明天安排",
				width:60,
				dataIndex : "tomContactCount",
				sortable : true
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm
		};
		com.zz91.ep.cscrm.DataGrid.superclass.constructor.call(this,c);
	},
	initAccount:function(account,deptCode){
		if (account!=null){
			this.getStore().baseParams["account"]=account;
		}
		if (deptCode!=null){
			this.getStore().baseParams["deptCode"]=deptCode;
		}
	}
});
