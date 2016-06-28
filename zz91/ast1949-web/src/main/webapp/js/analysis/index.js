Ext.namespace("ast.ast1949.analysis")

var ANALYSIS =function(){
	this.ANALYSIS.GRID="analysisgrid";
}

ast.ast1949.analysis.FILED=[
	{name:"id",mapping:"id"},
	{name:"statCate",mapping:"statCate"},
	{name:"statCateName",mapping:"statCateName"},
	{name:"statCount",mapping:"statCount"},
	{name:"gmtStatDate",mapping:"gmtStatDate"},
	{name:"gmtCreated",mapping:"gmtCreated"} 
];

/**
 * 统计信息列表
 */
ast.ast1949.analysis.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty: "totalRecords",
			remoteSort:true,
			fields:ast.ast1949.analysis.FILED,
			url:Context.ROOT+Context.PATH+"/analysis/webbasedatastat/query.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				dataIndex:"id",
				width:300,
				sortable:false
			},{
				header:"统计类型",
				dataIndex:"statCate",
				width:280,
				sortable:false
			},{
				header:"统计结果",
				dataIndex:"statCount",
				width:300,
				sortable:false
			},{
				header:"统计时间",
				dataIndex:"gmtStatDate",
				width:280,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"创建时间",
				dataIndex:"gmtCreated",
				width:280,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
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
			tbar:["->","统计类型：",{
				xtype:"combo",
				mode:"local",
				triggerAction:"all",
				store:[
						["register","注册量"],
						["publish_company_price","企业报价数量"],
						["publish_bbs_post","发帖量"],
						["publish_bbs_reply","回帖量"],
						["publish_products","供求数量"],
						["publish_inquiry","询盘数量"]
				      ],
				      listeners:{
							"change":function(field){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["statCate"] = field.getValue();
								}else{
									B["statCate"]=null;
								}
								_store.baseParams = B;
								_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
							}
						}
			},"->","统计时间：",{
            	xtype:"datefield",
            	format:"Y-m-d",
            	listeners:{
					"change":function(field){
						var B = _store.baseParams;
						B = B||{};
						if(field.getValue()!=""){
							B["gmtStatDate"] = field.getValue();
						}
						else{
							B["gmtStatDate"] = undefined;
						}
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
            }]
		};
		ast.ast1949.analysis.ResultGrid.superclass.constructor.call(this,c);
	},
	load:function(targetDate){
		this.getStore().baseParams["gmtStatDate"]=targetDate;
		this.getStore().reload();
	}
});
