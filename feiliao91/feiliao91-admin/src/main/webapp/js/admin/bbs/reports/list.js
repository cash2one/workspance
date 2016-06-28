/*
 * 举报信息管理
 */
Ext.namespace("ast.ast1949.admin.bbs.reports")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
}

//举报信息列表
ast.ast1949.admin.bbs.reports.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});

		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("checkedButton").enable();
	                    Ext.getCmp("deleteButton").enable();
	                    Ext.getCmp("cancelCheckdeButton").enable();
	                } else {
	                    Ext.getCmp("checkedButton").disable();
	                    Ext.getCmp("deleteButton").disable();
	                    Ext.getCmp("cancelCheckdeButton").disable();
	                }
	            }
	        }
		});
		
		//举报列表的列字段
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				hidden : true,
				dataIndex:"id"
			},{
				header:"被举报信息ID",
				sortable:false,
				hidden : true,
				dataIndex:"reportId"
			},{
				header:"举报类型",
				sortable:false,
				dataIndex:"reportType",
				renderer :function(value, metadata, record, rowIndex, colIndex, store){
					if(value!=null){
						if(value==1){
							return "举报留言";
						}else if(value==0){
							return "举报帖子";
						}
					} else {
						return "未知"
					}
				}
			},{
				header:"举报理由",
				sortable:false,
				dataIndex:"reportReason"
			},{
				header:"举报人帐号",
				sortable:false,
				dataIndex:"reportAccount"
			},{
				header:"举报人姓名",
				sortable:false,
				dataIndex:"reportName"
			},{
				header:"举报时间",
				sortable:false,
				dataIndex:"gmtReportTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				header:"举报人IP",
				sortable:false,
				dataIndex:"ip"
			},{
				header:"处理状态",
				sortable:false,
				dataIndex:"checkstate",
				renderer :function(value, metadata, record, rowIndex, colIndex, store){
					if(value!=null){
						if(value==1){
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
						}else if(value==0){
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
						}
					} else {
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			}
		]);
		
		//分页工具栏显示
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this,
			//一个特殊的工具条，特工自动分页控制
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			listeners:{
				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.bbs.reports.ResultGrid.superclass.constructor.call(this,c);
	},
	//这里的字段要跟列字段相匹配
	listRecord:Ext.data.Record.create([              
		{name:"id",mapping:"id"},
		{name:"reportId",mapping:"reportId"},
		{name:"reportType",mapping:"reportType"},
		{name:"reportReason",mapping:"reportReason"},
		{name:"reportAccount",mapping:"reportAccount"},
		{name:"reportName",mapping:"reportName"},
		{name:"gmtReportTime",mapping:"gmtReportTime"},
		{name:"ip",mapping:"ip"},
		{name:"checkstate",mapping:"checkstate"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/reports/query.htm",
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:[{
						iconCls:"delete",
						id:"deleteButton",
						text:"删除",
						disabled:true,
						handler:function(btn){
							Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
						}
					},"-",{
						id : "checkedButton",
						text : "处理",
						disabled : true,
						handler:function(btn){
						checkButton(1);
						}
					},
					"-",
					{
						id : "cancelCheckdeButton",
						text : "取消处理",
						disabled : true,
						handler:function(btn){
						checkButton(0);
						}
					}]
		});
		
		tbar2.render(this.tbar);
	}
});

//直接删除
function doDelete(_btn){
	if(_btn != "yes")
			return ;
			
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/bbs/reports/delete.htm?random="+Math.random()+"&ids="+_ids.join(','),
		method : "get",
		scope : this,
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
				Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
			}
		}
	});
}

//审核
function checkButton(_btn){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/bbs/reports/update.htm?random="+Math.random()+"&ids="+_ids+"&checkstate="+_btn,
		method : "get",
		scope : this,
		callback : function(options,success,response){
		//decode方法将json字符串转换成对象
			var res= Ext.decode(response.responseText);
			if(res.success){
				if(_btn==1){
					ast.ast1949.utils.Msg("","选定记录已处理!");
				}else{
					ast.ast1949.utils.Msg("","选定记录已取消处理!");
				}
				grid.getStore().reload();//另一种自动加载方法ast.ast1949.admin.bbs.reports.resultGridReload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选操作失败!");
			}
		}
	});
};

//重新绑定Grid数据
ast.ast1949.admin.bbs.reports.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}