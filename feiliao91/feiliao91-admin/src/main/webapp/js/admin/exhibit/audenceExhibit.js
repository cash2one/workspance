Ext.namespace("ast.ast1949.admin.exhibit.audenceExhibit");
// 定义变量
var _C = new function() {
	this.JOINEXHIBIT_GRID = "audenceExhibit";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.admin.exhibit.audenceExhibit.EXHIBITORSFIELD=[
  	{name:"id",mapping:"id"},
	{name:"companyName",mapping:"companyName"},
	{name:"name",mapping:"name"},
	{name:"mobile",mapping:"mobile"},
	{name:"exhibitNum",mapping:"exhibitNum"},
	{name:"website",mapping:"website"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

ast.ast1949.admin.exhibit.audenceExhibit.audenceExhibit=Ext.extend(Ext.grid.GridPanel,{
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
				header:"申请时间",
				width:200,
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
				header:"联系方式",
				width:100,
				dataIndex : "mobile"	
			},{    	
				header:"企业名称",
				width:100,
				dataIndex : "companyName"	
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/exhibit/queryAllExhibitors.htm?type=1";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.exhibit.audenceExhibit.EXHIBITORSFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
			iconCls:"add",
			text:"导出excel",
			handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
					if(btn!="yes"){
						return ;
					}else{
						Ext.Ajax.request({
							url: window.open(Context.ROOT+Context.PATH+ "/admin/exhibit/exportData.htm?type=1"),
						});
					}
				});
			}
		}];
		var c={
			id:"audenceExhibit",	
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
		ast.ast1949.admin.exhibit.audenceExhibit.audenceExhibit.superclass.constructor.call(this,c);
	},
	

});

