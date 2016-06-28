Ext.namespace("com.zz91.ep.crm");

com.zz91.ep.crm.DataField=[
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
	{name:"seo_count",mapping:"statistics.seoCount"},
	{name:"able_total",mapping:"ableTotal"},
	{name:"disable_total",mapping:"disableTotal"},
	{name:"able_rate",mapping:"ableRate"},
	{name:"dateStartEnd",mapping:"dateStartEnd"}
];
com.zz91.ep.crm.DataGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				fields:com.zz91.ep.crm.DataField,
				url:Context.ROOT +  "/sale/data/queryData.htm",
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
				width:70,
				dataIndex:"sale_name",
				sortable:true
			},{
				header:"日期",
				width:90,
				dataIndex:"dateStartEnd",
				sortable:true
			},{
				header:"五星有效",
				width:70,
				dataIndex:"star5_able",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=5' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header:"五星无效",
				width:70,
				dataIndex:"star5_disable",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=5' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header:"四星有效",
				width:70,
				dataIndex:"star4_able",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=4' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header :"四星无效",
				width:70,
				dataIndex:"star4_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=4' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "三星有效",
				width:70,
				dataIndex : "star3_able",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=3' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "三星无效",
				width:70,
				dataIndex : "star3_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=3' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "二星有效",
				width:70,
				dataIndex : "star2_able",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=2' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "二星无效",
				width:70,
				dataIndex : "star2_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=2' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "一星有效",
				width:70,
				dataIndex : "star1_able",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0&star=1' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "一星无效",
				width:70,
				dataIndex : "star1_disable",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1&star=1' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "SEO服务",
				width:60,
				dataIndex : "seo_count",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&type=2' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "有效合计",
				width:70,
				dataIndex : "able_total",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=0' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "无效合计",
				width:70,
				dataIndex : "disable_total",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var account=record.get("sale_account");
					var date=record.get("dateStartEnd");
					var url = "<a href='"+Context.ROOT + "/sale/mycompany/todayCrmLog.htm?account="+account+"&tdate="+date+"&disable=1' target='_blank'>"+value+"</a>";
					return url ;
				}
			},{
				header : "有效联系率(%)",
				width:90,
				dataIndex : "able_rate",
				sortable : true
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:["->",{
					xtype:"datefield",
					width:200,
					format:"Y-m-d",
					emptyText:"开始时间",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["start"]=newValue;
							grid.getStore().reload();
						}
					}
				},"到",{
					xtype:"datefield",
					width:200,
					format:"Y-m-d",
					emptyText:"结束时间",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["end"]=newValue;
							grid.getStore().reload();
						}
					}
				},"",{
					xtype:"combo",
					triggerAction : "all",
					displayField : "name",
					valueField : "account",
					hiddenId:"csAccount",
					hiddenName:"csAccount",
					emptyText:"环保销售",
					id:"saleCombo",
					width:80,
					store:new Ext.data.JsonStore( {
						fields : [ "account", "name" ],
						url : Context.ROOT + "/sale/data/queryPerson.htm",
						autoLoad:true
					}),
					listeners:{
						"blur":function(field){
							if(Ext.get("saleCombo").dom.value==""){
								field.setValue("");
							}
							var B=_store.baseParams;
							if(B["account"]==field.getValue()){
								return false;
							}
							B["account"]=field.getValue();
							_store.reload();
						}
					}
				},"",{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
					}
				},"",{
					xtype:"checkbox",
					boxLabel:"求和",
					inputValue:1,
					handler:function(btn){
						var B=_store.baseParams||{};
						if(btn.getValue()){
							B["group"]="1";
						}else{
							B["group"]=undefined;
						}
						_store.baseParams = B;
						_store.reload();
						}
				}]
			}),
//			bbar: com.zz91.utils.pageNav(_store)
		};
		com.zz91.ep.crm.DataGrid.superclass.constructor.call(this,c);
	},
	initAccount:function(account){
		if (account!=null){
			this.getStore().baseParams["account"]=account;
			Ext.getCmp("saleCombo").setVisible(false);
		}
	}
});
