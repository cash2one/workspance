Ext.namespace("ast.ast1949.analysis.product");


ast.ast1949.analysis.product.CSMAP={};

ast.ast1949.analysis.product.Fields=[
	"analysisType",
	"boli",
	"dianzidianqi",
	"ershoushebei",
	"fangzhipin",
	"fuwu",
	"jinshu",
	"xiangjiao",
	"qitafeiliao",
	"suliao",
	"zhi",
	"pige",
	"luntai"
];

ast.ast1949.analysis.product.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/analysis/analysisoperate/queryAnalysisProductData.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
//			root:"records",
//			totalProperty:'totalRecords',
//			remoteSort:true,
			fields:ast.ast1949.analysis.product.Fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "",
			width:80,
			dataIndex : "analysisType",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.analysis.product.CSMAP[value] != "undefined"){
						return ast.ast1949.analysis.product.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "系统";
				}
			}
		},{
			sortable: true,
			width:60,
			header : "废金属",
			dataIndex : "jinshu"
		},{
			sortable: true,
			width:60,
			header : "废塑料",
			dataIndex : "suliao"
		},{
			sortable: true,
			width:80,
			header : "橡胶",
			dataIndex : "xiangjiao"
		},{
			sortable: true,
			width:90,
			header : "纺织品",
			dataIndex : "fangzhipin"
		},{
			sortable: true,
			width:60,
			header : "废纸",
			dataIndex : "zhi"
		},{
			sortable: true,
			width:75,
			header : "废电子电器",
			dataIndex : "dianzidianqi"
		},{
			sortable: true,
			width:65,
			header : "废玻璃",
			dataIndex : "boli"
		},{
			sortable: true,
			width:70,
			header : "二手设备",
			dataIndex : "ershoushebei"
		},{
			sortable: true,
			width:70,
			header : "其他废料",
			dataIndex : "qitafeiliao"
		},{
			sortable: true,
			width:70,
			header : "服务",
			dataIndex : "fuwu"
		},{
			sortable: true,
			width:70,
			header : "废皮革",
			dataIndex : "pige"
		},{
			sortable: true,
			width:70,
			header : "废轮胎",
			dataIndex : "luntai"
		}
		]);
		
		var grid=this;
//		var summary = new Ext.grid.GroupSummary(); 
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm
//			plugins: summary,
//			tbar:["开始时间：",{
//            	id:"targetFrom",
//            	xtype:"datefield",
//            	format:"Y-m-d"
//            },"结束时间",{
//            	id:"targetTo",
//            	xtype:"datefield",
//            	format:"Y-m-d"
//            },{
//            	text:"查询",
//            	iconCls:"query",
//            	handler:function(btn){
//            		//TODO 查找数据
//            		var targetFrom=Ext.getCmp("targetFrom").getValue();
//            		var targetTo=Ext.getCmp("targetTo").getValue();
//            		if(targetFrom!="" && targetTo!=""){
//            			_store.baseParams["from"]=targetFrom;
//            			_store.baseParams["to"]=targetTo;
//            			_store.reload();
//            		}
//            	}
//            }
//            ,{
//            	text:"统计今天小记",
//            	iconCls:"query",
//            	handler:function(btn){
//            		newwin=window.open("http://admin1949.zz91.com/task/job/definition/doTask.htm?jobName=analysis_operation&start="+new Date().add(Date.DAY, 1).format("Y-m-d")+" 00:00:00")
//            		newwin.close();
//            		var nowDate = new Date().format("Y-m-d")
//            		Ext.getCmp("targetFrom").setValue(nowDate);
//            		Ext.getCmp("targetTo").setValue(nowDate);
//            		_store.baseParams["from"]=nowDate;
//        			_store.baseParams["to"]=nowDate;
//        			_store.reload();
//            	}
//            }
//            ]
		};
		
		ast.ast1949.analysis.product.Grid.superclass.constructor.call(this,c);
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