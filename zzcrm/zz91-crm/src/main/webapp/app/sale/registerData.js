Ext.namespace("com.zz91.ep.crm");

com.zz91.ep.crm.RegDataField=[
	{name:"id",mapping:"crmSaleStatistics.id"},
	{name:"zhejiang",mapping:"crmSaleStatistics.zhejiang"},
	{name:"jiangsu",mapping:"crmSaleStatistics.jiangsu"},
	{name:"shanghai",mapping:"crmSaleStatistics.shanghai"},
	{name:"guangdong",mapping:"crmSaleStatistics.guangdong"},
	{name:"shandong",mapping:"crmSaleStatistics.shandong"},
	{name:"beijing",mapping:"crmSaleStatistics.beijing"},
	{name:"hebei",mapping:"crmSaleStatistics.hebei"},
	{name:"other",mapping:"crmSaleStatistics.other"},
	{name:"gmt_target",mapping:"crmSaleStatistics.gmtTarget"},
	{name:"gmt_created",mapping:"crmSaleStatistics.gmtCreated"},
	{name:"gmt_modified",mapping:"crmSaleStatistics.gmtModified"},
	{name:"dateStartEnd",mapping:"dateStartEnd"},
	{name:"totals",mapping:"totals"}
	
];

com.zz91.ep.crm.RegDataGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				fields:com.zz91.ep.crm.RegDataField,
				url:Context.ROOT + "/sale/data/registerData.htm",
				autoLoad:true
		});
		
		var grid = this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				header : "编号",
				dataIndex : "id",
				hidden:true
			},{
				header:"日期",
				dataIndex:"dateStartEnd",
				width:150,
				sortable:true
			},{
				header:"浙江",
				dataIndex:"zhejiang",
				sortable:true
			},{
				header:"江苏",
				dataIndex:"jiangsu",
				sortable:true
			},{
				header:"上海",
				dataIndex:"shanghai",
				sortable:true
			},{
				header :"广东",
				dataIndex:"guangdong",
				sortable : true
				
			},{
				header : "山东",
				dataIndex : "shandong",
				sortable : true
			},{
				header : "北京",
				dataIndex : "beijing",
				sortable : true
			},{
				header : "河北",
				dataIndex : "hebei",
				sortable : true
			},{
				header : "其他",
				dataIndex : "other",
				sortable : true
			},{
				header : "合计",
				dataIndex : "totals",
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
		
		com.zz91.ep.crm.RegDataGrid.superclass.constructor.call(this,c);
	}
});
