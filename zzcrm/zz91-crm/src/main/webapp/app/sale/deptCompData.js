Ext.namespace("com.zz91.ep.crm");

com.zz91.ep.crm.DeptCompDataField=[
	{name:"saleAccount",mapping:"saleAccount"},
	{name:"saleName",mapping:"saleName"},
	{name:"saleDept",mapping:"saleDept"},
	{name:"start1",mapping:"start1"},
	{name:"start2",mapping:"start2"},
	{name:"start3",mapping:"start3"},
	{name:"start4",mapping:"start4"},
	{name:"start5",mapping:"start5"},
	{name:"uncontact",mapping:"uncontact"},
	{name:"tomContact",mapping:"tomContact"},
	{name:"todContact",mapping:"todContact"},
	{name:"totals",mapping:"totalsComp"},
	{name:"newUnContact",mapping:"newUnContact"},
	{name:"seaUnContact",mapping:"seaUnContact"},
	{name:"lostComp",mapping:"lostComp"},
	{name:"dragDestroy",mapping:"dragDestroy"}
];
com.zz91.ep.crm.DeptCompDataGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				fields:com.zz91.ep.crm.DeptCompDataField,
				url:Context.ROOT +  "/sale/data/deptCompData.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,
		    {
				header : "销售人",
				width:65,
				dataIndex : "saleName"
			},{
				hidden:true,
				header:"销售部门",
				dataIndex:"saleDept",
				sortable:true
			},{
				header:"1星客户数",
				width:65,
				dataIndex:"start1",
				sortable:true
			},{
				header:"2星客户数",
				width:65,
				dataIndex:"start2",
				sortable:true
			},{
				header:"3星客户数",
				width:65,
				dataIndex:"start3",
				sortable:true
			},{
				header:"4星客户数",
				width:65,
				dataIndex:"start4",
				sortable:true
			},{
				header :"5星客户数",
				width:65,
				dataIndex:"start5",
				sortable : true
			},{
				header : "0星客户数",
				width:65,
				dataIndex : "uncontact",
				sortable : true
			},{
				header : "新分配未联系",
				width:85,
				dataIndex : "newUnContact",
				sortable : true
			},{
				header : "公海挑入未联系",
				width:95,
				dataIndex : "seaUnContact",
				sortable : true
			},{
				header : "明天安排客户",
				width:85,
				dataIndex : "tomContact",
				sortable : true
			},{
				header : "今天安排客户",
				width:85,
				dataIndex : "todContact",
				sortable : true
			},{
				header :"跟丢客户",
				width:60,
				dataIndex:"lostComp",
				sortable : true
			},{
				header :"拖/毁单客户",
				width:80,
				dataIndex:"dragDestroy",
				sortable : true
			},{
				header :"客户总数",
				width:80,
				dataIndex:"totals",
				sortable : true
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar: com.zz91.utils.pageNav(_store),
		};
		
		com.zz91.ep.crm.DeptCompDataGrid.superclass.constructor.call(this,c);
	}
});
