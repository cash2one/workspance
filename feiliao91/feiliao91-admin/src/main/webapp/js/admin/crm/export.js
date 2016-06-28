Ext.namespace("ast.ast1949.admin.crm.exports")

var _C=new function(){
	this.RESULT_GRID = "resultgrid";
	this.QUERIED="";
}

Ext.onReady(function(){
	
	var resultgrid = new ast.ast1949.admin.crm.exports.ResultGrid({
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/crm/getExportList.htm",
		region:'center',
		autoScroll:true
	});
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[resultgrid]
//		items:[{region:"center",html:"html"}]
	});
});

ast.ast1949.admin.crm.exports.ResultGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config =config || {};
		Ext.apply(this,config);
		
		var _fields = this.crmRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
		
//		var sm = new Ext.grid.CheckboxSelectionModel();
		
		 var _cm=new Ext.grid.ColumnModel([{
			header:'邮箱',
			sortable:false,
			dataIndex:'email'
		},{
			header:'手机',
			sortable:false,
			dataIndex:'mobile'
		},{
			header:'电话',
			sortable:false,
			dataIndex:'tel'
		},{
			header:'联系人',
			sortable:false,
			dataIndex:'contactName'
		},{
			header:'公司',
			sortable:false,
			dataIndex:'companyName'
		}]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:false
			},
			store:_store,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
//			,
//			listeners : {
//				"render" : this.buttonQuery
//			}
		};
		
		ast.ast1949.admin.crm.exports.ResultGrid.superclass.constructor.call(this,c);
	},
	crmRecord:Ext.data.Record.create([{
		name: 'email',
		mapping:'email'
	},{
		name: 'mobile',
		mapping:'mobile'
	},{
		name: 'tel',
		mapping:'tel'
	},{
		name: 'contactName',
		mapping:'contactName'
	},{
		name: 'companyName',
		mapping:'companyName'
	}]),
	listUrl:Context.ROOT+Context.PATH+"/admin/crm/getExportList.htm",
//	baseParams : {start:"startIndex",limit:"pageSize",pageSize : Context.PAGE_SIZE},
	mytoolbar:["行业：",
				new ast.ast1949.CategoryCombo({
					categoryCode : Context.CATEGORY["service"],
					id:"industryCode",
					name : "industryCode"
				}),"会员类型：",
				new ast.ast1949.CategoryCombo({
					categoryCode : Context.CATEGORY["membership"],
					id:"membershipCode",
					name : "membershipCode"
				}),"主营业务：",{
					xtype:"textfield",
					id:"business",
					name:"business",
					width:160	
				},{
					text:"搜索",
					iconCls : "query",
					handler:function(btn){
						var resultgrid = Ext.getCmp(_C.RESULT_GRID);
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"industryCode":Ext.get("industryCode").dom.value,"membershipCode":Ext.get("membershipCode").dom.value,"business":Ext.get("business").dom.value};
						//定位到第一页
						resultgrid.getStore().load({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
						});
					}
				},"-",{
					iconCls:"add",
					text:"导出",
					handler:function(btn){
						var params="&industryCode="+Ext.get("industryCode").dom.value+"&membershipCode="+Ext.get("membershipCode").dom.value+"&business="+Ext.get("business").dom.value;
						var page=Context.ROOT+Context.PATH+"/admin/crm/exportExcel.htm?startIndex=0&pageSize=2000"+params;
						window.open(page);
					}
				}]
//				,
//	buttonQuery:function(){
//	var tbar2 = new Ext.Toolbar({
//			items : [{
//				text:"分配客户",
//				handler:function(btn){
//					
//				}
//			},"-",{
//				text:"客户归类",
//				handler:function(btn){
//					
//				}
//			}]
//		});
//		tbar2.render(this.tbar);
//	}
});