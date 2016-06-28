Ext.namespace("ast.ast1949.phonecrm.ldbSvr");

ast.ast1949.phonecrm.ldbSvr.Fields=["code","name","remark","unitPrice","units"];

ast.ast1949.phonecrm.ldbSvr.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/crm/svr/queryLdbSvr.htm",
	autoload:true,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _autoLoad=this.autoload;
		var _url=this.queryUrl;
		var _store = new Ext.data.JsonStore({
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phonecrm.ldbSvr.Fields,
			url:_url,
			autoLoad:_autoLoad
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			sortable : true,
			dataIndex : "code",
			hidden:true
		},{
			header : "服务名",
			dataIndex : "name"
		},{
			header : "价格",
			dataIndex : "unitPrice",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value==0){
					return "";
				}
				return record.get("unitPrice")+" "+record.get("units");
			}
		},{
			header : "备注",
			dataIndex : "remark"
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm
		};
		
		ast.ast1949.phonecrm.ldbSvr.Grid.superclass.constructor.call(this,c);
	}
});
