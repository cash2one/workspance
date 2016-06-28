Ext.namespace("ast.ast1949.trust.analysis");


ast.ast1949.trust.analysis.CSMAP={};

ast.ast1949.trust.analysis.Fields=[
	"account",
	"star1B",
	"star1C",
	"star2B",
	"star2C",
	"star3B",
	"star3C",
	"star4B",
	"star4C",
	"star5B",
	"star5C"
];

ast.ast1949.trust.analysis.dayLogFields=[
	"account",
	"num5",
	"num4",
	"num3",
	"num2",
	"num1",
	"numAll",
	"numNew",
	"numLost",
	"numTomorrow",
	"numToday"
];

ast.ast1949.trust.analysis.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/crm/analysislog/queryAnalysisTrustLog.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
//			root:"records",
//			totalProperty:'totalRecords',
//			remoteSort:true,
			fields:ast.ast1949.trust.analysis.Fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "客服",
			dataIndex : "account",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.trust.analysis.CSMAP[value] != "undefined"){
						return ast.ast1949.trust.analysis.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		},{
			
			sortable: true,
			width:60,
			header : "5星采购",
			dataIndex : "star5B"
		},{
			
			sortable: true,
			width:60,
			header : "5星公司",
			dataIndex : "star5C"
		},{
			
			sortable: true,
			width:60,
			header : "4星采购",
			dataIndex : "star4B"
		},{
//			
			sortable: true,
			width:60,
			header : "4星公司",
			dataIndex : "star4C"
		},{
//			
			sortable: true,
			width:60,
			header : "3星采购",
			dataIndex : "star3B"
		},{
//			
			sortable: true,
			width:60,
			header : "3星公司",
			dataIndex : "star3C"
		},{
//			
			sortable: true,
			width:60,
			header : "2星采购",
			dataIndex : "star2B"
		},{
//			
			sortable: true,
			width:60,
			header : "2星公司",
			dataIndex : "star2C"
		},{
//			
			sortable: true,
			width:60,
			header : "1星采购",
			dataIndex : "star1B"
		},{
//			
			sortable: true,
			width:60,
			header : "1星公司",
			dataIndex : "star1C"
		}
//		,{
//			sortable: true,
//			width:60,
//			header : "未赋星级Y",
//			dataIndex : "star0Y"
//		},{
//			sortable: true,
//			width:60,
//			header : "未赋星级N",
//			dataIndex : "star0N"
//		}
		,{
			header : "合计(采购)",
			dataIndex : "star0B",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var y=0;
				var n=0;
				y=parseInt(record.get("star1B"))+parseInt(record.get("star2B"))+parseInt(record.get("star3B"))+parseInt(record.get("star4B"))+parseInt(record.get("star5B"));
				return "采购："+y;
			}
		},{
			header : "合计(公司)",
			dataIndex : "star0C",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var n=0;
				n=parseInt(record.get("star1C"))+parseInt(record.get("star2C"))+parseInt(record.get("star3C"))+parseInt(record.get("star4C"))+parseInt(record.get("star5C"));
				return "公司："+n;
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
            		newwin=window.open("http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=anslysis_trust_log&start="+new Date().add(Date.DAY, 1).format("Y-m-d")+" 00:00:00")
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
            },{
            	text:"每日联系量统计表",
            	iconCls:"query",
            	handler:function(btn){
            		window.open(Context.ROOT +Context.PATH+  "/trust/crm/analysis_month_log.htm")
            	}
            }]
		};
		
		ast.ast1949.trust.analysis.Grid.superclass.constructor.call(this,c);
	},
	initDate:function(from, to,account){
		if(from==null || to==null){
			from=new Date();
			to=new Date();
		}
		Ext.getCmp("targetFrom").setValue(from);
		Ext.getCmp("targetTo").setValue(to);
		var store = Ext.getCmp("analysisTrustGrid").getStore();
		store.baseParams["from"]=from;
		store.baseParams["to"]=to;
		store.baseParams["account"]=account;
		store.reload();
	}
});

ast.ast1949.trust.analysis.dayLogGrid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/crm/analysislog/queryDayLog.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
			fields:ast.ast1949.trust.analysis.dayLogFields,
			url:_url,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "客服",
			dataIndex : "account",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.trust.analysis.CSMAP[value] != "undefined"){
						return ast.ast1949.trust.analysis.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		},{
			width:60,
			header : "5星采购",
			dataIndex : "num5",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_all.htm";
					return "<a href='"+url+"?account="+account+"&star=5"+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:60,
			header : "4星采购",
			dataIndex : "num4",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_all.htm";
					return "<a href='"+url+"?account="+account+"&star=4"+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:60,
			header : "3星采购",
			dataIndex : "num3",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_all.htm";
					return "<a href='"+url+"?account="+account+"&star=3"+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:60,
			header : "2星采购",
			dataIndex : "num2",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_all.htm";
					return "<a href='"+url+"?account="+account+"&star=2"+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:60,
			header : "1星采购",
			dataIndex : "num1",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_all.htm";
					return "<a href='"+url+"?account="+account+"&star=1"+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:60,
			header : "客户总数",
			dataIndex : "numAll",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_all.htm";
					return "<a href='"+url+"?account="+account+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:90,
			header : "新分配未联系",
			dataIndex : "numNew",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_new.htm";
					return "<a href='"+url+"?account="+account+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:60,
			header : "	跟丢客户",
			dataIndex : "numLost",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_lost.htm";
					return "<a href='"+url+"?account="+account+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			width:90,
			header : "明日安排联系",
			dataIndex : "numTomorrow"
		},{
			width:90,
			header : "今日安排联系",
			dataIndex : "numToday",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("account");
					var url = Context.ROOT +Context.PATH+  "/trust/crm/cs_today.htm";
					return "<a href='"+url+"?account="+account+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		}]);
		
		var grid=this;
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[]
		};
		
		ast.ast1949.trust.analysis.dayLogGrid.superclass.constructor.call(this,c);
	}
});