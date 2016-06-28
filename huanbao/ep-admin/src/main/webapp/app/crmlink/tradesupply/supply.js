Ext.namespace("com.zz91.comp.linkSupply");

var COMP=new function(){
	this.SUPPLY_GRID="supplygrid";
}

com.zz91.comp.linkSupply.GRIDFIELD=[
	{name:"id",mapping:"supply.id"},
	{name:"category_code",mapping:"supply.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"compName",mapping:"compName"},
	{name:"check_status",mapping:"supply.checkStatus"},
	{name:"property_query",mapping:"supply.propertyQuery"},// 专业属性名字
	{name:"title",mapping:"supply.title"},
	{name:"tags",mapping:"supply.tags"},// 简要信息
	{name:"gmt_publish",mapping:"supply.gmtPublish"}
];

com.zz91.comp.linkSupply.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totals',
			remoteSort:true,
			url:Context.ROOT+"/crmlink/crmlinktradebuy/queryCompSupply.htm",
			fields:com.zz91.comp.linkSupply.GRIDFIELD,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"审核状态", 
				width:150, 
				dataIndex:"check_status",
				sortable:true,
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
				hidden:true,
				header:"编号",
				dataIndex:"id"
				
			},{
				header:"标题", width:360, dataIndex:"title",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var id=record.get("id");
					var v1="<a href='"+Context.ROOT+"/crmlink/crmlinktradesupply/edit.htm?id="+id+"' target='_blank'>"+value+"</a>";
					var v2="<a href='"+Context.ESITE+"/detail"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v2+v1;
				}
			},{
				header:"类别", 
				width:150, 
				dataIndex:"category_name",
				sortable:false
			},{
				header:"公司", 
				width:250, 
				dataIndex:"compName",
				sortable:false
			},{
				header:"标签",
				width:100,
				dataIndex:"tags",
				sortable:false
			},{
				header:"专业属性", 
				width:200,
				dataIndex:"property_query",
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
				items:['->',{
					xtype:"checkbox",
					boxLabel:"推荐信息",
					inputValue:"",
					handler:function(btn){
							var B=_store.baseParams||{};
							if(btn.getValue()){
								B["type"]="2";
							}else{
								B["type"]=undefined;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
				},"->",{
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
		
		com.zz91.comp.linkSupply.grid.superclass.constructor.call(this,c);
	}
});