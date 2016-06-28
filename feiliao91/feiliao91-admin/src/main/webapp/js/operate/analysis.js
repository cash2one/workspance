Ext.namespace("ast.ast1949.operate.analysis");


ast.ast1949.operate.analysis.CSMAP={};

ast.ast1949.operate.analysis.Fields=[
	"operator",
	"bbsAdminPost",
	"bbsClientPostFailure",
	"bbsClientPostSuccess",
	"bbsReplyFailure",
	"bbsReplySuccess",
	"checkComppriceFailure",
	"checkComppriceSuccess",
	"checkProductsFailure",
	"checkProductsSuccess",
	"importToProducts",
	"postPrice",
	"postPriceText",
	"gmtCreated"
];

FormatTime=function(value,format){
    var date = new Date();
    date.setYear((value.year) + 1900);
    date.setMonth(value.month);
    date.setDate(value.date);
    date.setHours(value.hours);
    date.setMinutes(value.minutes);
    date.setSeconds(value.seconds);
    return date.format(format);
}

ast.ast1949.operate.analysis.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/analysis/analysisoperate/queryAnalysisData.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
//			root:"records",
//			totalProperty:'totalRecords',
//			remoteSort:true,
			fields:ast.ast1949.operate.analysis.Fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "运营",
			width:80,
			dataIndex : "operator",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.operate.analysis.CSMAP[value] != "undefined"){
						return ast.ast1949.operate.analysis.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "系统";
				}
			}
		},{
			sortable: true,
			width:80,
			header : "供求 通过",
			dataIndex : "checkProductsSuccess",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					var account = record.get("operator");
					var url = Context.ROOT +Context.PATH+  "/admin/products/list.htm";
					var from =new Date().format('Y-m-d');
					if(Ext.getCmp("targetFrom").getValue()!=""){
						from = new Date(Ext.getCmp("targetFrom").getValue()).format('Y-m-d');
					}
					var to = new Date().format('Y-m-d');
					if(Ext.getCmp("targetTo").getValue()!=""){
						to = new Date(Ext.getCmp("targetTo").getValue()).format('Y-m-d');
					}
					return "<a href='"+url+"?status=1&account="+account+"&from="+from+"&to="+to+"' target='_blank'>"+value+"</a>";
				}else{
					return 0;
				}
			}
		},{
			sortable: true,
			width:80,
			header : "供求 退回",
			dataIndex : "checkProductsFailure",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
			if(value>0){
				var account = record.get("operator");
				var url = Context.ROOT +Context.PATH+  "/admin/products/list.htm";
				var from =new Date().format('Y-m-d');
				if(Ext.getCmp("targetFrom").getValue()!=""){
					from = new Date(Ext.getCmp("targetFrom").getValue()).format('Y-m-d');
				}
				var to = new Date().format('Y-m-d');
				if(Ext.getCmp("targetTo").getValue()!=""){
					to = new Date(Ext.getCmp("targetTo").getValue()).format('Y-m-d');
				}
				return "<a href='"+url+"?status=2&account="+account+"&from="+from+"&to="+to+"' target='_blank'>"+value+"</a>";
			}else{
				return 0;
			}
		}
		},{
			header : "总量",
			width:60,
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var y=0;
				y=parseInt(record.get("checkProductsSuccess"))+parseInt(record.get("checkProductsFailure"));
				if(y>0){
					var account = record.get("operator");
					var url = Context.ROOT +Context.PATH+  "/admin/products/list.htm";
					var from =new Date().format('Y-m-d');
					if(Ext.getCmp("targetFrom").getValue()!=""){
						from = new Date(Ext.getCmp("targetFrom").getValue()).format('Y-m-d');
					}
					var to = new Date().format('Y-m-d');
					if(Ext.getCmp("targetTo").getValue()!=""){
						to = new Date(Ext.getCmp("targetTo").getValue()).format('Y-m-d');
					}
					return "<a href='"+url+"?status=1,2&account="+account+"&from="+from+"&to="+to+"' target='_blank'>"+y+"</a>";
				}else{
					return 0;
				}
			}
		},{
			header : "通过率",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var y=0;
				y=parseInt(record.get("checkProductsSuccess"))+parseInt(record.get("checkProductsFailure"));
				x=parseInt(record.get("checkProductsSuccess"));
				if(y==0){
					return 0;
				}
				var z = x/y*100+"";
				if(z.length>5){
					return z.substring(0,5)+"%"
				}else if(z.length==3){
					return z.substring(0,3)+"%"
				}
				return x/y;
			}
		},{
			sortable: true,
			width:90,
			header : "企业报价通过",
			dataIndex : "checkComppriceSuccess"
		},{
			sortable: true,
			width:90,
			header : "企业报价退回",
			dataIndex : "checkComppriceFailure"
		},{
			header : "总量",
			width:60,
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var y=0;
				y=parseInt(record.get("checkComppriceSuccess"))+parseInt(record.get("checkComppriceFailure"));
				return y;
			}
		},{
			sortable: true,
			width:60,
			header : "询盘导出",
			dataIndex : "importToProducts"
		},{
			sortable: true,
			width:60,
			header : "发布报价",
			dataIndex : "postPrice"
		},{
			sortable: true,
			width:90,
			header : "发布报价(文本)",
			dataIndex : "postPriceText"
		},{
			
			sortable: true,
			width:90,
			header : "审核帖子通过",
			dataIndex : "bbsClientPostSuccess"
		},{
			
			sortable: true,
			width:90,
			header : "审核帖子退回",
			dataIndex : "bbsClientPostFailure"
		},{
			sortable: true,
			width:60,
			header : "互助发贴",
			dataIndex : "bbsAdminPost"
		},{
			sortable: true,
			width:90,
			header : "审核回帖通过",
			dataIndex : "bbsReplySuccess"
		},{
//			
			sortable: true,
			width:90,
			header : "审核回帖退回",
			dataIndex : "bbsReplyFailure"
		}
//		,{
//			
//			sortable: true,
//			width:50,
//			header : "未赋星级N",
//			dataIndex : "star0N"
//		},{
//			
//			sortable: true,
//			width:50,
//			header : "服务电话",
//			dataIndex : "serviceCall"
//		},{
//			
//			sortable: true,
//			width:50,
//			header : "销售电话",
//			dataIndex : "saleCall"
//		},{
//			header : "合计",
//			dataIndex : "star0Y",
//			renderer:function(value,metadata,record,rowIndex,colIndex,store){
//				var y=0;
//				var n=0;
//				y=parseInt(record.get("star0Y"))+parseInt(record.get("star1Y"))+parseInt(record.get("star2Y"))+parseInt(record.get("star3Y"))+parseInt(record.get("star4Y"))+parseInt(record.get("star5Y"));
//				n=parseInt(record.get("star0N"))+parseInt(record.get("star1N"))+parseInt(record.get("star2N"))+parseInt(record.get("star3N"))+parseInt(record.get("star4N"))+parseInt(record.get("star5N"));
//				return "有效："+y+"<br />无效："+n;
//			}
//		}
		]);
		
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
            		newwin=window.open("http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=analysis_operation&start="+new Date().add(Date.DAY, 1).format("Y-m-d")+" 00:00:00")
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
		
		ast.ast1949.operate.analysis.Grid.superclass.constructor.call(this,c);
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