Ext.namespace("com.zz91.comp.linkBuy");

var COMP=new function(){
	this.BUY_GRID="buygrid";
}

com.zz91.comp.linkBuy.GRIDFIELD=[
	{name:"id",mapping:"tradeBuy.id"},
	{name:"category_code",mapping:"tradeBuy.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"compName",mapping:"compName"},
	{name:"check_status",mapping:"tradeBuy.checkStatus"},
	{name:"title",mapping:"tradeBuy.title"},
	{name:"details_query",mapping:"tradeBuy.detailsQuery"},// 简要信息
	{name:"gmt_publish",mapping:"tradeBuy.gmtPublish"},
	{name:"quantity",mapping:"tradeBuy.quantity"},
	{name:"quantity_year",mapping:"tradeBuy.quantityYear"},
	{name:"quantity_untis",mapping:"tradeBuy.quantityUntis"},
	{name:"use_to",mapping:"tradeBuy.useTo"},
	{name:"valid_days",mapping:"tradeBuy.validDays"}
];

com.zz91.comp.linkBuy.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totals',
			remoteSort:true,
			url:Context.ROOT+"/crmlink/crmlinktradebuy/queryCompBuy.htm",
			fields:com.zz91.comp.linkBuy.GRIDFIELD,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"审核状态", width:100, dataIndex:"check_status",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value==1) {
						returnvalue="通过";
					}
					if(value==0) {
						returnvalue="未审核";
					}
					if(value==2) {
						returnvalue="不通过";
					}
					return returnvalue;
				}
			},{
				header:"标题", width:200, dataIndex:"title",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var id=record.get("id");
					var v1="<a href='"+Context.ROOT+"/crmlink/crmlinktradebuy/edit.htm?id="+id+"' target='_blank'>"+value+"</a>";
					var v2="<a href='"+Context.TRADE+"/buy/detail"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v2+v1;
				}
			},{
				header:"类别", 
				width:150, 
				dataIndex:"category_name",
				sortable:false
			},{
				header:"公司", 
				width:220, 
				dataIndex:"compName",
				sortable:false
			},{
				header:"采购数量",
				width:100,
				dataIndex:"quantity",
				sortable:false
			},{
				header:"预计年采购量",
				width:100,
				dataIndex:"quantity_year",
				sortable:true
			},{
				header:"单位", 
				width:100,
				dataIndex:"quantity_untis",
				sortable:true
			},{
				header:"用途", 
				width:150,
				dataIndex:"use_to",
				sortable:true
			},{
				header:"有效期天数", 
				width:100,
				dataIndex:"valid_days",
				sortable:true
			},{
				header:"发布时间",
				width:200,
				dataIndex:"gmt_publish",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:m:s');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var grid=this;
		
		var _bbar=com.zz91.utils.pageNav(_store);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:["->",{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
					}
				},"->",{
					xtype:"combo",
					emptyText:"审核状态搜寻",
					name:"checkStatusStr",
					hiddenName:"check_status",
					mode:"local",
					triggerAction:"all",
					lazyRender:true,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["0","未审核"],["1","审核通过"],["2","审核不通过"]]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["checkStatus"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"textfield",
					width:200,
					emptyText:"搜索标题",
					listeners:{
						"change":function(field,newValue,oldValue){
							var B=grid.getStore().baseParams;
							B=B||{};
							B["title"]=newValue;
							grid.getStore().baseParams=B;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
			}]
			}),
			bbar:_bbar
		};
		
		com.zz91.comp.linkBuy.grid.superclass.constructor.call(this,c);
	}
});