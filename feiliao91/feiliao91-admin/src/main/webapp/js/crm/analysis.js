Ext.namespace("ast.ast1949.crm.analysis");


ast.ast1949.crm.analysis.CSMAP={};

ast.ast1949.crm.analysis.Fields=[
	"csAccount",
	"star0Y",
	"star0N",
	"star1Y",
	"star1N",
	"star2Y",
	"star2N",
	"star3Y",
	"star3N",
	"star4Y",
	"star4N",
	"star5Y",
	"star5N",
	"saleCall",
	"serviceCall",
	"analysisDate"
];

ast.ast1949.crm.analysis.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/crm/analysislog/queryAnalysisData.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
//			root:"records",
//			totalProperty:'totalRecords',
//			remoteSort:true,
			fields:ast.ast1949.crm.analysis.Fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "客服",
			dataIndex : "csAccount",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.crm.analysis.CSMAP[value] != "undefined"){
						return ast.ast1949.crm.analysis.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		},{
			
			sortable: true,
			width:50,
			header : "5星Y",
			dataIndex : "star5Y"
		},{
			
			sortable: true,
			width:50,
			header : "5星N",
			dataIndex : "star5N"
		},{
			
			sortable: true,
			width:50,
			header : "4星Y",
			dataIndex : "star4Y"
		},{
//			
			sortable: true,
			width:50,
			header : "4星N",
			dataIndex : "star4N"
		},{
//			
			sortable: true,
			width:50,
			header : "3星Y",
			dataIndex : "star3Y"
		},{
//			
			sortable: true,
			width:50,
			header : "3星N",
			dataIndex : "star3N"
		},{
//			
			sortable: true,
			width:50,
			header : "2星Y",
			dataIndex : "star2Y"
		},{
//			
			sortable: true,
			width:50,
			header : "2星N",
			dataIndex : "star2N"
		},{
//			
			sortable: true,
			width:50,
			header : "1星Y",
			dataIndex : "star1Y"
		},{
//			
			sortable: true,
			width:50,
			header : "1星N",
			dataIndex : "star1N"
		},{
//			
			sortable: true,
			width:50,
			header : "未赋星级Y",
			dataIndex : "star0Y"
		},{
//			
			sortable: true,
			width:50,
			header : "未赋星级N",
			dataIndex : "star0N"
		},{
//			
			sortable: true,
			width:50,
			header : "服务电话",
			dataIndex : "serviceCall"
		},{
//			
			sortable: true,
			width:50,
			header : "销售电话",
			dataIndex : "saleCall"
		},{
			header : "合计(有效)",
			dataIndex : "star0Y",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var y=0;
				var n=0;
				y=parseInt(record.get("star0Y"))+parseInt(record.get("star1Y"))+parseInt(record.get("star2Y"))+parseInt(record.get("star3Y"))+parseInt(record.get("star4Y"))+parseInt(record.get("star5Y"));
				return "有效："+y;
			}
		},{
			header : "合计(无效)",
			dataIndex : "star0N",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var n=0;
				n=parseInt(record.get("star0N"))+parseInt(record.get("star1N"))+parseInt(record.get("star2N"))+parseInt(record.get("star3N"))+parseInt(record.get("star4N"))+parseInt(record.get("star5N"));
				return "无效："+n;
			}
		}]);
		
		var grid=this;
//		var summary = new Ext.grid.GroupSummary(); 
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
//			plugins: summary,
			tbar:["开始时间：",{
            	id:"targetFrom",
            	xtype:"datefield",
            	format:"Y-m-d"
            },"结束时间",{
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
            		if(targetFrom!="" && targetTo!=""){
            			_store.baseParams["from"]=targetFrom;
            			_store.baseParams["to"]=targetTo;
            			_store.reload();
            		}
            	}
            },{
            	text:"统计今天小记",
            	iconCls:"query",
            	handler:function(btn){
            		newwin=window.open("http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=analysis_cs_log&start="+new Date().add(Date.DAY, 1).format("Y-m-d")+" 00:00:00")
            		setTimeout(function(){
            			newwin.close();
            		},3000);
            		var nowDate = new Date().format("Y-m-d")
            		Ext.getCmp("targetFrom").setValue(nowDate);
            		Ext.getCmp("targetTo").setValue(nowDate);
            		_store.baseParams["from"]=nowDate;
        			_store.baseParams["to"]=nowDate;
        			_store.reload();
            	}
            }]
		};
		
		ast.ast1949.crm.analysis.Grid.superclass.constructor.call(this,c);
	},
	initDate:function(from, to){
		if(from==null || to==null){
			from=new Date();
			to=new Date();
		}
		Ext.getCmp("targetFrom").setValue(from);
		Ext.getCmp("targetTo").setValue(to);
	}
});