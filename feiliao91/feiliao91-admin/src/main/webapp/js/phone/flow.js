Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.PHONEFLOWFIELD=[
	{name:"pv",mapping:"pv"},
	{name:"uv",mapping:"uv"}
 ];

ast.ast1949.phone.phoneFlowGrid = Ext.extend(Ext.grid.GridPanel,{
	
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header : "pv",
				name : "pv",
				id : "pv",
				width:150,
				sortable:false,
				dataIndex:"pv",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
						return value;
				}
			},{
				header : "uv",
				name : "uv",
				id : "uv",
				width:150,
				sortable:false,
				dataIndex:"uv",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
						return value;
				}
				
			}		
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryPhoneFlow.htm";
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.PHONEFLOWFIELD,
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
			id:"phoneFlowGrid",
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
		ast.ast1949.phone.phoneFlowGrid.superclass.constructor.call(this,c);
	},
	loadPhoneFlowRecord:function(companyId){
		this.getStore().reload({params:{"cid":companyId}});
		var B=this.getStore().baseParams;
		B=B||{};
		B["cid"]=companyId;
		this.getStore().baseParams=B;
		this.getStore().reload({});
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



