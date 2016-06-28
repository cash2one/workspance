Ext.namespace("ast.ast1949.admin.crm.stat")

var _C=new function(){
	this.RESULT_GRID = "resultgrid";
	this.QUERIED="";
}

Ext.onReady(function(){
	var resultgrid = new ast.ast1949.admin.crm.stat.ResultGrid({
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/crm/stat/getStatContactList.htm",
		region:'center',
		autoScroll:true
	});
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[resultgrid]
	});
});


ast.ast1949.admin.crm.stat.ResultGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields = this.gdRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		
		var _cm=new Ext.grid.ColumnModel([{
			header:"客服",
			width:70,
			sortable:false,
			dataIndex:'adminName'
		},{
			header:"无效",
			width:50,
			sortable:false,
			dataIndex:'oneStarInvalid'
		},{
			header:"有效",
			width:50,
			sortable:false,
			dataIndex:'oneStarValid'
		},{
			header:"无效",
			width:50,
			sortable:false,
			dataIndex:'twoStarInvalid'
		},{
			header:"有效",
			width:50,
			sortable:false,
			dataIndex:'twoStarValid'
		},{
			header:"无效",
			width:50,
			sortable:false,
			dataIndex:'threeStarInvalid'
		},{
			header:"有效",
			width:50,
			sortable:false,
			dataIndex:'threeStarValid'
		},{
			header:"无效",
			width:50,
			sortable:false,
			dataIndex:'fourStarInvalid'
		},{
			header:"有效",
			width:50,
			sortable:false,
			dataIndex:'fourStarValid'
		},{
			header:"无效",
			width:50,
			sortable:false,
			dataIndex:'fiveStarInvalid'
		},{
			header:"有效",
			width:50,
			sortable:false,
			dataIndex:'fiveStarValid'
		},{
			header:"服务电话",
			width:50,
			sortable:false,
			dataIndex:'serviceCall'
		},{
			header:"销售电话",
			width:50,
			sortable:false,
			dataIndex:'sellCall'
		},{
			header:"无效",
			width:50,
			sortable:false,
			dataIndex:'totalInvalid'
		},{
			header:"有效",
			width:50,
			sortable:false,
			dataIndex:'totalValid'
		}]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:false
			},
			store:_store,
			cm:_cm,
			tbar:this.mytoolbar
//			,
//			listeners : {
//				"render" : this.mytoolbar
//			}
		};
		
		ast.ast1949.admin.crm.stat.ResultGrid.superclass.constructor.call(this,c);
	},
	gdRecord:Ext.data.Record.create([
		{name: 'adminName',mapping:'adminName'},
		
		{name: 'oneStarInvalid',mapping:'statContact.oneStarInvalid'},
		{name: 'oneStarValid',mapping:'statContact.oneStarValid'},
		
		{name: 'twoStarInvalid',mapping:'statContact.twoStarInvalid'},
		{name: 'twoStarValid',mapping:'statContact.twoStarValid'},
		
		{name: 'threeStarInvalid',mapping:'statContact.threeStarInvalid'},
		{name: 'threeStarValid',mapping:'statContact.threeStarValid'},
		
		{name: 'fourStarInvalid',mapping:'statContact.fourStarInvalid'},
		{name: 'fourStarValid',mapping:'statContact.fourStarValid'},
		
		{name: 'fiveStarInvalid',mapping:'statContact.fiveStarInvalid'},
		{name: 'fiveStarValid',mapping:'statContact.fiveStarValid'},
		
		{name: 'serviceCall',mapping:'statContact.serviceCall'},
		{name: 'sellCall',mapping:'statContact.sellCall'},
		
		{name:"totalInvalid",mapping:"statContact.totalInvalid"},
		{name:"totalValid",mapping:"statContact.totalValid"}]),
	listUrl:Context.ROOT+Context.PATH+"/admin/crm/stat/getStatContactList.htm",
	mytoolbar:["筛选：从",{
					xtype:"datefield",
					id:"satrtDate",
					name:"satrtDate",
					width: 110,      
			        format: 'Y-m-d',      
			      	emptyText: '请选择日期 ...'
				},"到",{
					xtype:"datefield",
					id:"endDate",
					name:"endDate",
					width: 110,      
			        format: 'Y-m-d',      
			      	emptyText: '请选择日期 ...'
				},"-",{
					text:"查询",
					iconCls : "query",
					handler :
					function(){
						var grid=Ext.getCmp(_C.RESULT_GRID);
						grid.store.baseParams = {
								"satrtDate":Ext.get("satrtDate").dom.value,
								"endDate":Ext.get("endDate").dom.value
								};
						//定位到第一页
						grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE,"dir":"desc","sort":"id"}});
					}
				}]
});