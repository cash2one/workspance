Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.PHONECOSTFIELD=[
	{name:"stringFellName",mapping:"stringFellName"},
	{name:"allFee",mapping:"allFee"}
];

ast.ast1949.phone.phoneCostGrid = Ext.extend(Ext.grid.GridPanel,{
	
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				name : "stringFellName",
				id : "stringFellName",
				width:150,
				sortable:false,
				dataIndex:"stringFellName",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
						return value;
				}
			},{
				header : "",
				name : "allFee",
				id : "allFee",
				width:150,
				sortable:false,
				dataIndex:"allFee",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
						return value;
				}
				
			}			
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryPhoneCost.htm";
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.PHONECOSTFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [
		    "查看时间：",{
            	id:"targetFrom",
            	xtype:"datefield",
            	format:"Y-m-d"
            },"至",{
            	id:"targetTo",
            	xtype:"datefield",
            	format:"Y-m-d"
            },{
            	text:"查询",
            	iconCls:"query",
            	handler:function(btn){
            		//TODO 查找数据
            		var targetFrom=Ext.getCmp("targetFrom").getValue();
            		var targetTo=Ext.getCmp("targetTo").getValue();
            			_store.baseParams["from"]=targetFrom;
            			_store.baseParams["to"]=targetTo;
            			_store.reload();
            	
            	}
            }
		];

		var c={
			id:"phoneCostGrid",
			features:[{
				ftype: "groupingsummary",
				groupHeaderTpl: '{name}',
				hideGroupedHeader: false,
				enableGroupingMenu: false
			}],
			
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			
		};
		ast.ast1949.phone.phoneCostGrid.superclass.constructor.call(this,c);
	},

	initDate:function(from, to){
		if(from==null || to==null){
			from=new Date();
			to=new Date();
		}
		Ext.getCmp("targetFrom").setValue(from);
		Ext.getCmp("targetTo").setValue(to);
	},
	
});



