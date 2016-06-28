Ext.namespace("com.zz91.ep.crm");

var LOG = new function(){
	this.LOG_GRID = "loggrid";
}

com.zz91.ep.crm.LogField=[
	{name:"id",mapping:"id"},
	{name:"operation",mapping:"operation"},
	{name:"target_id",mapping:"targetId"},
	{name:"sale_account",mapping:"saleAccount"},
	{name:"sale_dept",mapping:"saleDept"},
	{name:"sale_name",mapping:"saleName"},
	{name:"sale_ip",mapping:"saleIp"},
	{name:"details",mapping:"details"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"gmt_modified",mapping:"gmtModified"},
	{name:"cname",mapping:"cname"}
];

com.zz91.ep.crm.LogGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				fields:com.zz91.ep.crm.LogField,
				url:Context.ROOT + "/system/log/querySysLog.htm",
				autoLoad:true
		});
		
		var grid = this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			hidden:true,
			header:"编号",
			dataIndex:"id"
		},{
			hidden:true,
			header:"目标ID",
			dataIndex:"target_id",
		},{
			header:"公司名称",
			dataIndex:"cname",
			sortable:true,
			width:280,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var id=record.get("target_id");
				var returnValue=value;
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				var url = "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?visbile=5&id="+id+"' target='_blank'>"+returnValue+"</a>";
				return url ;
			}
		},{
			header:"操作者账号",
			dataIndex:"sale_account",
			sortable:true
		},{
			header:"操作者姓名",
			dataIndex:"sale_name",
			sortable:true
		},{
			hidden:true,
			header:"操作者部门",
			dataIndex:"sale_dept",
		},{
			header:"操作者IP",
			dataIndex:"sale_ip",
			sortable:true
		},{
			header:"详细信息",
			dataIndex:"details",
			width:300,
			sortable:true
		},{
			header : "操作时间",
			sortable : true,
			width:200,
			dataIndex : "gmt_created",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
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
			tbar:new Ext.Toolbar({
				items:["->",{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
					}
				},"->",{
					xtype : "textfield",
					id : "search-name",
					emptyText:"按销售人员搜索",
					listeners:{
						"change":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["saleName"] = field.getValue();
							}else{
								B["saleName"]=undefined;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"",{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenId:"operation",
					hiddenName:"operation",
					emptyText:"按操作规则查询",
					lazyRender:true,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[
						      ["0","放入公海"],["1","申请放入废品池"],["2","修改公司信息"],
						      ["3","手动录入客户信息"],["4","放入个人库"],["5","重新分配客户"],
						      ["6","放入废品池审核通过"],["7","放入废品池审核不通过"],["8","手动添加客户审核通过"],
						      ["9","自动掉公海"]
						]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["operation"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}]
			}),
			bbar:com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.ep.crm.LogGrid.superclass.constructor.call(this,c);
	},
	loadLog:function(id){
		if (id>0){
			this.getStore().baseParams["targetId"]=id;
		}
	}
});

