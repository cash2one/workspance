Ext.namespace("ast.ast1949.admin.score.rules")

/**
 * 积分规则类列表
 * @class ast.ast1949.admin.score.summary.RulesGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.score.rules.RulesGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				sortable:false
			},{
				header:"积分变更项",
				dataIndex:"name",
				sortable:false
			},{
				header:"key",
				dataIndex:"rulesCode",
				sortable:false
			},{
				header:"分数",
				dataIndex:"score",
				sortable:false
			},{
				header:"最大值",
				dataIndex:"scoreMax",
				sortable:false
			},{
				header:"周期",
				dataIndex:"cycleDay",
				sortable:false
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
		ast.ast1949.admin.score.rules.RulesGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"name",mapping:"name"},
		{name:"rulesCode",mapping:"rulesCode"},
		{name:"score",mapping:"score"},
		{name:"scoreMax",mapping:"scoreMax"},
		{name:"cycleDay",mapping:"cycleDay"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/score/rules/query.htm"
})
