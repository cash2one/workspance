Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.CLICKLOGFIELD=[
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"clickFee",mapping:"clickFee"},
	{name:"targetId",mapping:"targetId"},
	{name:"name",mapping:"name"}
 ];

ast.ast1949.phone.clickLogGrid = Ext.extend(Ext.grid.GridPanel,{
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
				header : "被查看的用户",
				width : 200,
				sortable : false,
				dataIndex : "name",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				   if(record.get("targetId")!=0){
					   var v1="<a href='http://company.zz91.com/compinfo"+encodeURIComponent(record.get("targetId"))+".htm' target='_blank'>"+value+"</a>"; 
				   }else{
					   var v1=value;
				   }
				   return v1;
				}
			}			
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryClickLog.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.CLICKLOGFIELD,
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
			id:"clickLogGrid",	
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
		ast.ast1949.phone.clickLogGrid.superclass.constructor.call(this,c);
	},
	loadClickLogRecord:function(companyId){
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


