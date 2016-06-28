/*
 * 客户管理 
 */
Ext.namespace("ast.ast1949.admin.bbs.clients")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
	this.CLIENTS_ADD_INTEGRAL_WIN="clientsaddintegralwin";
	this.CLIENTS_ADD_INTEGRAL_FORM="clientsaddintegralform";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.bbs.clients.ResultGrid({
		title:"客户列表",
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/bbs/clients/list.htm",
		region:'center',
		autoScroll:true
	});
	
	resultgrid.getColumnModel().setHidden(2, true);//隐藏公司编号 
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[resultgrid]
	});
});

//帖子列表
ast.ast1949.admin.bbs.clients.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
	                    Ext.getCmp("addIntegralButton").enable();
	                } else {
	                    Ext.getCmp("addIntegralButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header:"公司编号",
				sortable:false,
				dataIndex:"companyId"
				
			},{
				header:"公司名",
				sortable:false,
				dataIndex:"companyName",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					var _companyId=record.get("companyId");
					var _account=record.get("account");
					var _url=Context.ROOT+Context.PATH+"/admin/admincompany/edit.htm?companyId="+_companyId+"&account="+_account+"&ts="+Math.random();
					_name="<a href='"+_url+"' target='_blank'>"+value+"</a>";
					
					return _name;
				}
			},{
				header:"昵称",
				sortable:false,
				dataIndex:"nickname"
			},{
				header:"积分",
				sortable:false,
				dataIndex:"integral"
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			loadMask:Context.LOADMASK,
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
		ast.ast1949.admin.bbs.clients.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"bbsUserProfiler.id"},
		{name:"companyId",mapping:"bbsUserProfiler.companyId"},
		{name:"companyName",mapping:"companyName"},
		{name:"account",mapping:"bbsUserProfiler.account"},
		{name:"nickname",mapping:"bbsUserProfiler.nickname"},
		{name:"integral",mapping:"bbsUserProfiler.integral"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/clients/list.htm",
	mytoolbar:["公司名称：",{
		xtype:"textfield",
		id:"companyName",
		name:"companyName",
		width:100
	},"邮箱：",{
		xtype:"textfield",
		id:"email",
		name:"email",
		width:100
	},{
		text:"查询",
				iconCls : "query",
				handler :
				function(){
//					alert(Ext.get("companyName").dom.value);
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.store.baseParams = {
							"companyName":Ext.get("companyName").dom.value,
							"serEmail":Ext.get("email").dom.value
							};
					//定位到第一页
					grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE/*,"dir":"desc","sort":"id"*/}});
				}
	},"-",{
		id:"addIntegralButton",
		text:"加分",
		disabled:true,
		handler:function(){
			var grid = Ext.getCmp(_C.RESULT_GRID);
	
			var row = grid.getSelections();
			if(row.length>1){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "最多只能选择一条记录！",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			} else {
				var _id=row[0].get("id");
				var _account=row[0].get("account");
				var _companyId=row[0].get("companyId");
		
				ast.ast1949.admin.bbs.clients.AddIntegralWin(_id,_account,_companyId);
			}
		}
	}]
});

//加分表单
ast.ast1949.admin.bbs.clients.AddIntegralForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			items:[{//id,account,companyId
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"bbsUserProfilerId",
					name:"bbsUserProfilerId"
				},{
					xtype:"hidden",
					id:"account",
					name:"account"
				},{
					xtype:"hidden",
					id:"companyId",
					name:"companyId"
				},{
					name:"profiler",
					fieldLabel:"积分变更：",
					allowBlank:false
				},{
					xtype:"textarea",
					name:"reason",
					fieldLabel:"变更原因：",
					allowBlank:false
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
					Ext.getCmp(_C.CLIENTS_ADD_INTEGRAL_WIN).close();
				}
			}]
		};
		
		ast.ast1949.admin.bbs.clients.AddIntegralForm.superclass.constructor.call(this,c);
	},
	cancelUrl:Context.ROOT+Context.PATH + "/admin/bbs/clients/addIntegral.htm",
	cancelChecked:function(){
		var _url = this.cancelUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSuccess,
				failure:this.onFailure,
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
	onSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.clients.resultGridReload();
		Ext.getCmp(_C.CLIENTS_ADD_INTEGRAL_WIN).close();
	},
	onFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

/**
 * 加分
 * @param {} bbsUserProfilerId 个人基本信息ID
 * @param {} account 账号名
 * @param {} companyId 公司编号
 */
ast.ast1949.admin.bbs.clients.AddIntegralWin=function(bbsUserProfilerId,account,companyId){
	var form = new ast.ast1949.admin.bbs.clients.AddIntegralForm({
		id:_C.CLIENTS_ADD_INTEGRAL_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.CLIENTS_ADD_INTEGRAL_WIN,
		title:"取消审核",
		width:"80%",
		modal:true,
		items:[form]
	});
	
	win.show();
	Ext.get("bbsUserProfilerId").dom.value=bbsUserProfilerId;
	Ext.get("account").dom.value=account;
	Ext.get("companyId").dom.value=companyId;
};

ast.ast1949.admin.bbs.clients.resultGridReload = function() {
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}
