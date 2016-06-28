Ext.namespace("ast.ast1949.phonecallclick");

ast.ast1949.phonecallclick.CALLCLICKLOGFIELD=[
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"clickFee",mapping:"clickFee"},
	{name:"callerTel",mapping:"callerTel"}
 ];

ast.ast1949.phonecallclick.callClickLogGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header : "点击时间",
				width:150,
				sortable:false,
				dataIndex:"gmtCreated",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "总计:";
					}
				}

			},{
				header : "点击费用",
				width:100,
				sortable:false,
				dataIndex:"clickFee",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return value + "元";
				}
			},{
				header : "被查看的电话号码",
				width : 200,
				sortable : false,
				dataIndex : "callerTel",
			}			
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryCallClickLog.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phonecallclick.CALLCLICKLOGFIELD,
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
            			_store.baseParams["targetFrom"]=targetFrom;
            			_store.baseParams["targetTo"]=targetTo;
            			_store.reload();
            	
            	}
            }      
		];

		var c={
			id:"callClickLogGrid",	
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
		ast.ast1949.phonecallclick.callClickLogGrid.superclass.constructor.call(this,c);
	},
	loadCallClickLogRecord:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
		var B=this.getStore().baseParams;
		B=B||{};
		B["companyId"]=companyId;
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


