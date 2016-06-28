Ext.namespace("com.zz91.ep.crm");

com.zz91.ep.crm.DeptCompDataField=[
	{name:"saleAccount",mapping:"saleAccount"},
	{name:"saleName",mapping:"saleName"},
	{name:"saleDept",mapping:"saleDept"},
	{name:"star4",mapping:"star4"},
	{name:"star5",mapping:"star5"},
	{name:"gmtTarget",mapping:"gmtTarget"}
];

com.zz91.ep.crm.DataGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.crm.DeptCompDataField,
				url:Context.ROOT +  "/sale/data/queryFourOrFiveStar.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				header :"日期",
				width:200,
				dataIndex:"gmtTarget",
				sortable : true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			},{
				header : "销售人",
				width:200,
				dataIndex : "saleName"
			},{
				hidden:true,
				header:"销售部门",
				dataIndex:"saleDept",
				sortable:true
			},{
				hidden:true,
				header:"销售账户",
				dataIndex:"saleAccount",
				sortable:true
			},{
				header:"转4星",
				width:200,
				dataIndex:"star4",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var v=value;
					var account=record.get("saleAccount");
					var td=Ext.util.Format.date(new Date(record.get("gmtTarget").time), 'Y-m-d')
					return "<a href='"+Context.ROOT+"/sale/data/turnStarComp.htm?star=4&account="+account+"&tDate="+td+"' target='_blank'>"+v+"</a>";;
				}
			},{
				header :"转5星",
				width:200,
				dataIndex:"star5",
				sortable : true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var v=value;
					var account=record.get("saleAccount");
					var td=Ext.util.Format.date(new Date(record.get("gmtTarget").time), 'Y-m-d')
					return "<a href='"+Context.ROOT+"/sale/data/turnStarComp.htm?star=5&account="+account+"&tDate="+td+"' target='_blank'>"+v+"</a>";;
				}
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					xtype:"datefield",
					width:200,
					format:"Y-m-d",
					emptyText:"开始时间",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["startTime"]=newValue;
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
							grid.getStore().baseParams["endTime"]=newValue;
							grid.getStore().reload();
						}
					}
				}]
			}),
			bbar: com.zz91.utils.pageNav(_store),
		};
		
		com.zz91.ep.crm.DataGrid.superclass.constructor.call(this,c);
	}
});
