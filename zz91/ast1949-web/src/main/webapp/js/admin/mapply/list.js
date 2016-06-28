
Ext.namespace("ast.ast1949.admin.mapply")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
	this.POSTS_CANCEL_AUDIT_FORM="postscancelauditform";
	this.POSTS_CANCEL_AUDIT_WIN="postscancelauditwin";

	this.POSTS_EDIT_FORM="postseditform";
	this.POSTS_EDIT_WIN="postseditwin";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.mapply.ResultGrid({
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/mapply/list.htm",
		region:'center',
		autoScroll:true
	});

	var quicksearchtab = new ast.ast1949.admin.mapply.QuickSearchTab({
		region:'north'
	});

	var viewport = new Ext.Viewport({
		layout:'border',
		items:[quicksearchtab,resultgrid]
	});
});

//列表
ast.ast1949.admin.mapply.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
	                    Ext.getCmp("cancelCheckdeButton").enable();
	                } else {
	                    Ext.getCmp("checkedButton").disable();
	                    Ext.getCmp("cancelCheckdeButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id"
			},{
				header:"用户名",
				sortable:false,
				dataIndex:"username"
			},{
				header:"申请类型",
				sortable:false,
				dataIndex:"membershipName"
			},{
				header:"电话",
				sortable:false,
				dataIndex:"tel"
			},{
				header:"移动电话",
				sortable:false,
				dataIndex:"mobile"
			},{
				header:"邮箱地址",
				sortable:false,
				dataIndex:"email"
			},{
				header:"申请时间",
				sortable:false,
				dataIndex:"gmtCreated",
				renderer : function (value,metadata,record,rowIndex,colIndex,store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time),'Y-m-d');
					} else {
						return "";
					}
       			}
			}
		]);

		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			sm:_sm,
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
		};

		ast.ast1949.admin.mapply.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"memberApply.id"},
		{name:"username",mapping:"memberApply.username"},
		{name:"membershipName",mapping:"membershipName"},
		{name:"tel",mapping:"memberApply.tel"},
		{name:"mobile",mapping:"memberApply.mobile"},
		{name:"email",mapping:"memberApply.email"},
		{name:"gmtCreated",mapping:"memberApply.gmtCreated"}

	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/mapply/list.htm",
	mytoolbar:[{
		id:"checkedButton",
		text:"已处理",
		disabled:true,
		handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要更新所选记录吗?', function(_btn){
				if(_btn != "yes")
				return ;
				//更新
//				updateChecked(1);
				var grid = Ext.getCmp(_C.RESULT_GRID);

				var row = grid.getSelections();
				var _ids = new Array();
				for (var i=0,len = row.length;i<len;i++){
					var _id=row[i].get("id");
					_ids.push(_id);
				}
				//更新
				ast.ast1949.admin.mapply.AuditWin(_ids);
			});
		}
	},"-",{
		id:"cancelCheckdeButton",
		text:"未处理",
		disabled:true,
		handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要更新所选记录吗?', function(_btn){
				if(_btn != "yes")
				return ;

				var grid = Ext.getCmp(_C.RESULT_GRID);

				var row = grid.getSelections();
				var _ids = new Array();
				for (var i=0,len = row.length;i<len;i++){
					var _id=row[i].get("id");
					_ids.push(_id);
				}
				//更新
				ast.ast1949.admin.mapply.CancelAuditWin(_ids);
			});
		}
	}]
});

/**
 * 更新处理状态
 * @param {} checkStatus 1为通过处理；0为不通过处理。
 */
function updateChecked(checkStatus){
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
		url: Context.ROOT+Context.PATH+ "/admin/mapply/updateCheckStatus.htm?random="+Math.random()+"&processStatus="+checkStatus+"&ids="+_ids,
		method : "get",
		scope : this,
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录已更新!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
			}
		}
	});
}

//导航Tab
ast.ast1949.admin.mapply.QuickSearchTab = Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config:config||{};
		Ext.apply(this,config);

		var resultgrid = Ext.getCmp(_C.RESULT_GRID);

		var c={
			activeTab: 0,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			items:[{
				title:'所有',
				html:'<i>所有申请信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'未处理',
				html:'<i>所有未处理申请信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"processStatus":"0"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'已处理',
				html:'<i>所有已处理申请信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"processStatus":"1"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			}]
		};

		ast.ast1949.admin.mapply.QuickSearchTab.superclass.constructor.call(this,c);
	}
});

ast.ast1949.admin.mapply.CancelAuditForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"ids",
					name:"ids"
				},{
					xtype:"hidden",
					id:"processStatus",
					name:"processStatus",
					value:"0"
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注",
					allowBlank:false,
					tabIndex:1
				}]
			}],
			buttons:[{
				text:"确定",
				handler:this.cancelChecked,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					//关闭编辑产品的窗口
					Ext.getCmp(_C.POSTS_CANCEL_AUDIT_WIN).close();
				}
			}]
		};

		ast.ast1949.admin.mapply.CancelAuditForm.superclass.constructor.call(this,c);
	},
	cancelUrl:Context.ROOT+Context.PATH + "/admin/mapply/updateCheckStatus.htm",
	cancelChecked:function(){
		var _url = this.cancelUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onCancelCheckedSuccess,
				failure:this.onCancelCheckedFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onCancelCheckedSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});

		ast.ast1949.admin.mapply.resultGridReload();
		Ext.getCmp(_C.POSTS_CANCEL_AUDIT_WIN).close();
	},
	onCancelCheckedFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

ast.ast1949.admin.mapply.AuditWin=function(ids){
	var form = new ast.ast1949.admin.mapply.CancelAuditForm({
		id:_C.POSTS_CANCEL_AUDIT_FORM,
		region:"center"
	});

		var win = new Ext.Window({
		id:_C.POSTS_CANCEL_AUDIT_WIN,
		title:"处理请求",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});

	win.show();
	Ext.get("ids").dom.value=ids;//record.get("typeId1");
	Ext.get("processStatus").dom.value="1";
};

ast.ast1949.admin.mapply.CancelAuditWin=function(ids){
	var form = new ast.ast1949.admin.mapply.CancelAuditForm({
		id:_C.POSTS_CANCEL_AUDIT_FORM,
		region:"center"
	});

		var win = new Ext.Window({
		id:_C.POSTS_CANCEL_AUDIT_WIN,
		title:"取消处理",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});

	win.show();
	Ext.get("ids").dom.value=ids;//record.get("typeId1");
};

//重新绑定Grid数据
ast.ast1949.admin.mapply.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}
