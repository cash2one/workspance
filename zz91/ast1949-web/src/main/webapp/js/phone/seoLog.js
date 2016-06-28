Ext.namespace("ast.ast1949.phone.seoLog");
// 定义变量
var _C = new function() {
	this.PHONEseoLog_GRID = "phoneseoLogGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.phone.seoLog.PHONEseoLogSFIELD=[
  	{name:"id",mapping:"id"},
	{name:"startTime",mapping:"phonelog.startTime"},
	{name:"endTime",mapping:"phonelog.endTime"},
	{name:"callerId",mapping:"phonelog.callerId"},
	{name:"tel",mapping:"phonelog.tel"},
	{name:"callFee",mapping:"phonelog.callFee"},
	{name:"province",mapping:"phonelog.province"},
	{name:"city",mapping:"phonelog.city"},
	{name:"ip",mapping:"analysisPhoneOptimiza.ip"},
	{name:"utmSource",mapping:"analysisPhoneOptimiza.utmSource"},
	{name:"utmTerm",mapping:"analysisPhoneOptimiza.utmTerm"},
	{name:"utmContent",mapping:"analysisPhoneOptimiza.utmContent"},
	{name:"utmCampaign",mapping:"analysisPhoneOptimiza.utmCampaign"},
	{name:"isValid",mapping:"analysisPhoneOptimiza.isValid"},
	{name:"isFirst",mapping:"analysisPhoneOptimiza.isFirst"},
	{name:"pageFirst",mapping:"analysisPhoneOptimiza.pageFirst"},
	{name:"pageLast",mapping:"analysisPhoneOptimiza.pageLast"},
	{name:"gmtCreated",mapping:"analysisPhoneOptimiza.gmtCreated"},
	{name:"area",mapping:"analysisPhoneOptimiza.area"}
];

ast.ast1949.phone.seoLog.phoneseoLogGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header:"点击时间",
				width:125,
				dataIndex : "gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header:"开始通话时间",
				width:125,
				dataIndex : "startTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header:"结束通话时间",
				width:125,
				dataIndex : "endTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}else{
						return "";
					}
				}
			},{
				header:"通话时长",
				width:100,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(record.get("endTime")!=null&&record.get("endTime")!=null){
						return record.get("endTime").time - record.get("startTime").time
					}else{
						return ;
					}
				}
			},{
				header:"来电号码",
				width:100,
				dataIndex : "calledId"
			},{
				header:"被拨打400号码",
				width:100,
				dataIndex : "tel"
			},{
				header:"通话费用",
				width:100,
				dataIndex : "callFee"
			},{
				header:"访客地区",
				width:100,
				dataIndex : "area"
			},{
				header:"访客ip",
				width:100,
				dataIndex : "ip"
			},{
				header:"百度搜索词",
				width:100,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "研究中";
				}
			},{
				header:"搜索词",
				width:100
				,
				dataIndex : "utmContent"
			},{
				header:"来源",
				width:100,
				dataIndex : "utmSource"
			},{
				header:"推广单元",
				width:100,
				dataIndex : "utmCampaign"
			},{
				header:"推广计划",
				width:100,
				dataIndex : "utmTerm"
			},{
				header:"是否有效果",
				width:100,
				dataIndex : "isValid",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==0){
						return "否";
					}else{
						return "是";
					}
				}
			},{
				header:"是否初次访问",
				width:100,
				dataIndex : "isFirst",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==0){
						return "否";
					}else{
						return "是";
					}
				}
			},{
				header:"受访页面",
				width:400,
				dataIndex : "pageFirst"
			},{
				header:"最后停留的页面",
				width:400,
				dataIndex : "pageLast"
			},{
				header:"拨打时停留的页面",
				width:400,
				dataIndex : "pageCalling"
			}
//			,{
//				header:"修改时间",
//				width:200,
//				dataIndex : "gmtModified",
//				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
//					if(value!=null){
//						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
//					}
//					else{
//						return "";
//					}
//				}
//			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/phone/seoLogData.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.seoLog.PHONEseoLogSFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [];
		var c={
			id:"phoneseoLogGrid",	
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
			})
			
		};
		ast.ast1949.phone.seoLog.phoneseoLogGrid.superclass.constructor.call(this,c);
	},
	

});
