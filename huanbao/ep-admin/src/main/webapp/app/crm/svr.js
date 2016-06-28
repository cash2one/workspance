Ext.namespace("com.zz91.ep.crm.svr");

com.zz91.ep.crm.svr.Fields=["id","code","name","details","unitPrice","units"];

com.zz91.ep.crm.svr.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT + "/crm/svr/querySvr.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		var _store = new Ext.data.JsonStore({
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:com.zz91.ep.crm.svr.Fields,
			url:_url,
			autoLoad:true
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
			header : "详细说明",
			width:400,
			dataIndex : "details"
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm
		};
		
		com.zz91.ep.crm.svr.Grid.superclass.constructor.call(this,c);
	}
});
