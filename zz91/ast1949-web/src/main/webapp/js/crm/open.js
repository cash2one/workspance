Ext.namespace("ast.ast1949.crm.open");

var OPEN=new function(){
	this.APPLYSVRGRID="applysvrgrid";
}

ast.ast1949.crm.open.Category=["付费","赠送"];
ast.ast1949.crm.open.ApplyStatus={"0":"申请中","1":"已开通","2":"拒绝开通"};

ast.ast1949.crm.open.step1=function(){
	var companyGrid=new ast.ast1949.crm.open.Step1CompanyGrid({
		id:"step1Company",
//		columnWidth:1,
		height:200
	});
	
//	var companySvrGrid=new ast.ast1949.crm.open.Step1CompanySvrGrid({
//		title:"开通的服务",
////		columnWidth:1,
//		height:200
//	});
	
//	companyGrid.on("rowclick",function(g,rowIndex,e){
//		var B=companySvrGrid.getStore().baseParams;
//		B["companyId"]=g.getStore().getAt(rowIndex).get("id");
//		companySvrGrid.getStore().baseParams=B;
//		companySvrGrid.getStore().reload({});
//	});
	
	var win = new Ext.Window({
		id:"step1win",
		title:"申请服务 step1",
		width:680,
		modal:true,
		layout:"column",
		items:[companyGrid],
		buttons:[{
			text:"下一步",
			listeners:{
				"click":function(button){
					// TODO 单击
					var row= companyGrid.getSelectionModel().getSelected();
					if(typeof(row)=="object"){
						Ext.getCmp("step1win").hide();
						ast.ast1949.crm.open.step2();
					}else{
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "请先选择公司再进入下一步",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				}
			}
		}]
	});
	
	win.show();
};

ast.ast1949.crm.open.step2=function(){
	var win = Ext.getCmp("step2win");
	if(typeof(win)!="undefined"){
		win.show();
		return ;
	}
	
	var svrGrid= new ast.ast1949.crm.svr.Grid({
		id:"step2svr",
		queryUrl:Context.ROOT +Context.PATH+  "/crm/open/applyQuerySvr.htm",
		height:200,
		autoload:false,
		tbar:[{
			xtype:"combo",
			lazyInit:true,
			mode:"local",
			triggerAction:"all",
			lazyRender:true,
			store:new Ext.data.SimpleStore({
				fields:['k','v'],
				data:[["vap","VAP"],["zst",'再生通'],["zst_ppt0",'银牌品牌通'],["zst_ppt1",'金牌品牌通'],["zst_ppt2",'钻石品牌通'],["ldb",'来电宝']]
			}),
			valueField:"k",
			displayField:"v",
			id:"svrgroup",
			listeners:{
				"blur":function(field){
					if(Ext.get("svrgroup").dom.value==""){
						field.setValue("");
					}
					svrGrid.getStore().baseParams["svrgroup"]=field.getValue();
					svrGrid.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}]
	});
	
	win = new Ext.Window({
		id:"step2win",
		title:"申请服务 step2",
		width:680,
		modal:true,
		items:[svrGrid],
		buttons:[{
			text:"上一步",
			listeners:{
				"click":function(button){
					Ext.getCmp("step2win").hide();
					Ext.getCmp("step1win").show();
				}
			}
		},{
			text:"下一步",
			listeners:{
				"click":function(button){
					var row= svrGrid.getSelectionModel().getSelected();
					if(typeof(row)=="object"){
						Ext.getCmp("step2win").hide();
						ast.ast1949.crm.open.step3();
					}else{
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "请先选择要开通的服务再进入下一步",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
					
				}
			}
		}]
	});
	
	win.show();
};

ast.ast1949.crm.open.step3=function(){
	
	var win=Ext.getCmp("step3win"); 
	if(typeof(win)!="undefined"){
		win.show();
		return ;
	}
	
	var form = new ast.ast1949.crm.open.Step3ApplyForm({
		height:200
	});
	
	win = new Ext.Window({
		id:"step3win",
		title:"申请服务 step3",
		width:680,
		modal:true,
		items:[form],
		buttons:[{
			text:"上一步",
			listeners:{
				"click":function(button){
					Ext.getCmp("step3win").hide();
					Ext.getCmp("step2win").show();
				}
			}
		},{
			text:"提交申请",
			listeners:{
				"click":function(button){
					var companyRow= Ext.getCmp("step1Company").getSelectionModel().getSelected();
					var svrRows = Ext.getCmp("step2svr").getSelectionModel().getSelections();
					var svrCodeArr=new Array();
					for(var i = 0;i<svrRows.length;i++){
						svrCodeArr.push(svrRows[i].get("code"));
					}
					var svrgroup=Ext.getCmp("svrgroup").getValue();
					form.applySubmit(companyRow.get("id"), svrCodeArr, svrgroup);
				}
			}
		}]
	});
	
	win.show();
};

ast.ast1949.crm.open.Step1CompanyGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT+Context.PATH+"/crm/open/applyQueryCompany.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm=new Ext.grid.ColumnModel([sm,{
			header:"编号",
			hidden:true,
			dataIndex:"id"
		},{
			width:380,
			header:"公司名称",
			dataIndex:"companyName"
		},{
			header:"email",
			width:250,
			dataIndex:"email"
		}
		]);
		
		var _fields=this.fields;
		
		var _url=this.queryUrl;
		var _store=new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totalRecords',
				remoteSort:true,
				fields:_fields,
				url:_url,
				autoLoad:false
		});
		
		var _toolbar = this.toolbar;
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store,
			tbar : _toolbar,
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
		ast.ast1949.crm.open.Step1CompanyGrid.superclass.constructor.call(this,c);
	},
	fields:[{name:"id",mapping:"company.id"},
		{name:"companyName",mapping:"company.name"},
		{name:"email",mapping:"account.email"}
	],
	toolbar:["用户email",":",{
		xtype:"textfield",
		width:590,
		listeners:{
			"blur":function(textfield){
				var _store = Ext.getCmp("step1Company").getStore();
				_store.baseParams["email"]=textfield.getValue();
				_store.reload();
			}
		}
	}]
});

ast.ast1949.crm.open.Step1CompanySvrGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm=new Ext.grid.ColumnModel([sm,{
			header:"已开通服务",
			dataIndex:"nameLabel",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return value;
			}
		},{
			header:"开始时间",
			dataIndex:"gmt_start",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"结束时间",
			dataIndex:"gmt_end",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"服务开通状态",
			dataIndex:"status",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value=="0"){
					return "申请中";
				}
				if(value=="1"){
					return "已开通";
				}
				if(value=="2"){
					return "拒绝开通";
				}
				return "";
			}
		},{
			header:"备注",
			dataIndex:"remark"
		},{
			header:"关键字",
			dataIndex:"keywords"
		},{
			header:"付款金额",
			dataIndex:"amount",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return value+"元";
			}
		}]);
		
		var reader=[
			{name:"id",mapping:"crmCompanySvr.id"},
			{name:"companyId",mapping:"crmCompanySvr.companyId"},
			{name:"crmServiceCode",mapping:"crmCompanySvr.crmServiceCode"},
			{name:"nameLabel",mapping:"svrName"},
			{name:"gmt_start",mapping:"crmCompanySvr.gmtStart"},
			{name:"gmt_end",mapping:"crmCompanySvr.gmtEnd"},
			{name:"status",mapping:"crmCompanySvr.applyStatus"},
			{name:"remark",mapping:"crmCompanySvr.remark"},
			{name:"keywords",mapping:"keywords"},
			{name:"amount",mapping:"amount"}
		];
		
		var _store=new Ext.data.JsonStore({
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + Context.PATH + "/crm/open/applyQuerySvrHistory.htm",
			autoLoad:false
		});
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store
		};
		
		ast.ast1949.crm.open.Step1CompanySvrGrid.superclass.constructor.call(this,c);
	},
	loadByCompany:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
	}
});

ast.ast1949.crm.open.Step3ApplyForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelWidth:80,
			labelAlign:"right",
			frame:true,
			layout:"column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"numberfield",
					fieldLabel:"到款金额",
					allowBlank:false,
					itemCls:"required",
					name:"amountFloat",
					id:"amountFloat"
				},{
					xtype:"hidden",
					name:"amount",
					id:"amount"
				},{
					fieldLabel:"销售人员",
					allowBlank:false,
					itemCls:"required",
					name:"saleStaff"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					fieldLabel:"到款时间",
					allowBlank:false,
					format:"Y-m-d",
					itemCls:"required",
					name:"gmtIncomeDate"
				}]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					fieldLabel:"备注",
					xtype:"textarea",
					name:"remark",
					anchor:"99%"
				}]
			}]
		};
		
		ast.ast1949.crm.open.Step3ApplyForm.superclass.constructor.call(this,c);
	},
	applySubmit:function(companyId, svrCodeArr, svrgroup){
		if (this.getForm().isValid()) {
			this.findById("amount").setValue(this.findById("amountFloat").getValue()*100);
			this.getForm().submit({
				url : Context.ROOT+Context.PATH+"/crm/open/applySubmit.htm",
				method : "post",
				params:{"companyId":companyId,"svrCodeArr":svrCodeArr.join(","),"svrgroup":svrgroup},
				waitMsg:Context.SAVEMASK.msg,
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "您的申请已经提交了，请等待客服部审核",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
						Ext.getCmp("step3win").hide();
					} else {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "发生错误,您的申请没有提交",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					}
				},
				failure : function(_form,_action){
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "发生错误,信息没有被保存",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	}
});

ast.ast1949.crm.open.CompanySvrGrid=Ext.extend(Ext.grid.EditorGridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm=new Ext.grid.ColumnModel([sm,{
			header:"服务申请组",
			hidden:true,
			dataIndex:"apply_group"
		},{
			header:"申请服务",
			dataIndex:"svrName",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				var st='<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
				if(record.get("applyStatus")=="1"){
					st='<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
				}
				if(record.get("applyStatus")=="2"){
					st='<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
				}
				return st+value;
			}
		},{
			header:"单价",
			dataIndex:"svrPrice"
		},{
			header:"描述",
			dataIndex:"svrRemark"
		},{
			header:"上次服务开始时间",
			dataIndex:"gmt_pre_start",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header:"上次服务结束时间",
			dataIndex:"gmt_pre_end",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header:"服务签定时间",
			dataIndex:"gmt_signed",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header:"本次服务开始时间",
			dataIndex:"gmt_start",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header:"本次服务结束时间",
			dataIndex:"gmt_end",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header:"类型",
			dataIndex:"category",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return ast.ast1949.crm.open.Category[value];
			}
		},{
			header:"备注",
			dataIndex:"remark"
		}
		]);
		
		var reader=[{name:"apply_group",mapping:"crmCompanySvr.applyGroup"},
			{name:"svrName",mapping:"svrName"},
			{name:"svrPrice",mapping:"svrPrice"},
			{name:"svrRemark",mapping:"svrRemark"},
			{name:"gmt_pre_start",mapping:"crmCompanySvr.gmtPreStart"},
			{name:"gmt_pre_end",mapping:"crmCompanySvr.gmtPreEnd"},
			{name:"gmt_signed",mapping:"crmCompanySvr.gmtSigned"},
			{name:"gmt_start",mapping:"crmCompanySvr.gmtStart"},
			{name:"gmt_end",mapping:"crmCompanySvr.gmtEnd"},
			{name:"category",mapping:"crmCompanySvr.category"},
			{name:"remark",mapping:"crmCompanySvr.remark"},
			{name:"crmServiceCode",mapping:"crmCompanySvr.crmServiceCode"},
			{name:"companyId",mapping:"crmCompanySvr.companyId"},
			{name:"id",mapping:"crmCompanySvr.id"},
			{name:"applyStatus",mapping:"crmCompanySvr.applyStatus"}
		];
		
		var _store=new Ext.data.JsonStore({
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + Context.PATH + "/crm/open/queryApplySvr.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var c={
				loadMask:Context.LOADMASK,
				sm:sm,
				cm:cm,
				store:_store,
				tbar:[{
					iconCls:"unlock",
					text:"开通",
					handler:function(btn){
						var row=grid.getSelectionModel().getSelected();
						if(row!=null && typeof row !="undefined"){
							if(row.get("applyStatus")=="1"){
								ast.ast1949.utils.Msg("","服务已开通，请不要重复开通！");
								return false;
							}
							
							ast.ast1949.api.callback=function(){
								ast.ast1949.api.closeWin();
								_store.reload();
							}
							ast.ast1949.api.showOpenWin(
								row.get("crmServiceCode"),
								{"companyId":row.get("companyId"),
								"companySvrId":row.get("id")}
							);
						}else{
							ast.ast1949.utils.Msg("","请选择要开通的服务！");
						}
					}
				},{
					iconCls:"lock",
					text:"关闭",
					handler:function(btn){
						var row=grid.getSelectionModel().getSelected();
						if(row!=null && typeof row !="undefined"){
							
							if(row.get("applyStatus")!="1"){
								ast.ast1949.utils.Msg("","服务没有开通，不能关闭！");
								return false;
							}
							
							ast.ast1949.api.callback=function(){
								ast.ast1949.api.closeWin();
								_store.reload();
							}
							ast.ast1949.api.showCloseWin(
								row.get("crmServiceCode"),
								{"companyId":row.get("companyId"),"companySvrId":row.get("id")}
							);
						}else{
							ast.ast1949.utils.Msg("","请选择要关闭的服务！");
						}
					}
				}]
		};
		
		ast.ast1949.crm.open.CompanySvrGrid.superclass.constructor.call(this,c);
	},
	loadApply:function(applyGroup){
		if(applyGroup==null || applyGroup.length<=0){
			return ;
		}
		this.getStore().baseParams["applyGroup"]=applyGroup;
		this.getStore().reload();
	}
});

ast.ast1949.crm.open.SvrDetail=[
	{name:"apply_group",mapping:"applyGroup"},
	{name:"gmt_signed",mapping:"gmtSigned"},
	{name:"gmt_start",mapping:"gmtStart"},
	{name:"gmt_end",mapping:"gmtEnd"},
	{name:"gmt_pre_start",mapping:"gmtPreStart"},
	{name:"gmt_pre_end",mapping:"gmtPreEnd"},
	{name:"apply_status",mapping:"applyStatus"},
	{name:"category",mapping:"category"},
	{name:"remark",mapping:"remark"},
	{name:"signed_type",mapping:"signedType"},
	{name:"remark",mapping:"remark"},
	{name:"companyId",mapping:"companyId"},
	{name:"crmServiceCode",mapping:"crmServiceCode"},
	{name:"id",mapping:"id"}
];

ast.ast1949.crm.open.CompanySvrHistoryGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var cm=new Ext.grid.ColumnModel([{
			header:"开通申请组",
			dataIndex:"apply_group",
			hidden:true
		},{
			header:"服务签约时间",
			dataIndex:"gmt_signed",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"服务开通时间",
			dataIndex:"gmt_start",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"服务结束时间",
			dataIndex:"gmt_end",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"状态",
			dataIndex:"apply_status",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return ast.ast1949.crm.open.ApplyStatus[value];
				}
			}
		},{
			header:"类别",
			dataIndex:"category",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return ast.ast1949.crm.open.Category[value];
			}
		},{
			header:"备注",
			dataIndex:"remark"
		}]);
		
		var reader=ast.ast1949.crm.open.SvrDetail;
		
		var _store=new Ext.data.JsonStore({
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + Context.PATH + "/crm/open/querySvrHistory.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			cm:cm,
			store:_store,
			tbar:[{
				text:"开通申请单",
				iconCls:"item-open",
				handler:function(btn){
					//打开历史开通单
					var row=grid.getSelectionModel().getSelected();
					window.open(Context.ROOT+Context.PATH+"/crm/open/detail.htm?applyGroup="+row.get("apply_group")+"&companyId="+row.get("companyId"))
				}
			}]
		};
		
		ast.ast1949.crm.open.CompanySvrHistoryGrid.superclass.constructor.call(this,c);
	},
	loadHistory:function(companyId, svrCode){
		this.getStore().baseParams["companyId"]=companyId;
		this.getStore().baseParams["svrCode"]=svrCode;
		this.getStore().reload();
	}
});

ast.ast1949.crm.open.SvrApplyForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var form=this;
		var c={
			labelWidth:80,
			labelAlign:"right",
			frame:true,
			layout:"column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					name:"id"
				},{
					fieldLabel:"销售人员",
					allowBlank:false,
					itemCls:"required",
					name:"saleStaff"
				}
//				,{
//					xtype:"combo",
//					triggerAction : "all",
//					forceSelection : true,
//					fieldLabel:"指定客服",
//					lazyInit:true,
//					displayField : "name",
//					valueField : "account",
//					hiddenId:"csAccount",
//					hiddenName:"csAccount",
//					id:"cs",
//					store:new Ext.data.JsonStore( {
////						root : "records",
//						fields : [ "account", "name" ],
//						autoLoad:true,
//						url : Context.ROOT + Context.PATH+ "/crm/open/applyQueryCs.htm"
//					}),
//					listeners:{
//						"blur":function(field){
//							if(Ext.get("cs").dom.value==""){
//								field.setValue("");
//							}
//						}
//					}
//				}
//				,{
//					xtype:"combo",
//					mode:"local",
//					triggerAction:"all",
//					name:"membership",
//					id:"membership",
//					hiddenName:"membershipCode",
//					hiddenId:"membershipCode",
//					fieldLabel:"会员类型",
//					store:[
//					    ["10051000","普通会员"],
//					    ["10051001","再生通会员"],
//					    ["100510021000","银牌品牌通会员"],
//					    ["100510021001","金牌品牌通会员"],
//					    ["100510021002","钻石品牌通会员"]
//					],
//					listeners:{
//						"blur":function(field){
//							if(Ext.get("membership").dom.value==""){
//								field.setValue("");
//							}
//						}
//					}
//				}
				]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					xtype:"textfield",
					anchor:"100%",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"amount",
					name:"amount"
				},{
					xtype:"numberfield",
					fieldLabel:"到款金额",
					allowBlank:false,
					itemCls:"required",
					name:"amountFloat",
					id:"amountFloat",
					listeners:{
						"blur":function(field){
							Ext.get("amount").dom.value=field.getValue()*100;
						}
					}
				},{
					xtype:"datefield",
					fieldLabel:"到款时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					id:"gmtIncomeDate",
					name:"gmtIncomeDate"
				}]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					fieldLabel:"备注",
					xtype:"textarea",
					anchor:"99%",
					height:150,
					name:"remark"
				}]
			}
//			,{
//				columnWidth:0.5,
//				layout:"form",
//				defaults:{
//					anchor:"100%",
//					labelSeparator:""
//				},
//				items:[{
//					xtype:"checkbox",
//					boxLabel:"邮件通知",
//					iconCls:"required"
//				}]
//			},{
//				columnWidth:0.5,
//				layout:"form",
//				defaults:{
//					anchor:"100%",
//					labelSeparator:""
//				},
//				items:[{
//					xtype:"checkbox",
//					boxLabel:"短信通知",
//					iconCls:"required"
//				}]
//			}
			],
			buttons:[{
				iconCls:"save",
				text:"保存开通单",
				handler:form.saveApply
			}]
		};
		
		ast.ast1949.crm.open.SvrApplyForm.superclass.constructor.call(this,c);
	},
	saveApply:function(){
		var form =this;
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : Context.ROOT+Context.PATH+"/crm/open/saveApply.htm",
				method : "post",
				waitMsg:Context.SAVEMASK.msg,
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "服务已按照指定时间开通。",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO,
							fn:function(btnId, text){
								window.close();
							}
						});
						
						//关闭窗口
					} else {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "发生错误,信息没有被保存",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					}
				},
				failure : function(_form,_action){
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "发生错误,信息没有被保存",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	},
	loadApply:function(applyGroup, companyId){
		var fields=["id","applyGroup","orderNo","gmtIncome","email","amount","amountDetails",
		            "saleStaff","remark","membershipCode"];
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + Context.PATH + "/crm/open/queryApplyForm.htm?applyGroup="+applyGroup,
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
						form.getForm().loadRecord(record);
						form.findById("amountFloat").setValue(record.get("amount")/100);
						if(record.get("gmtIncome")!=null){
							form.findById("gmtIncomeDate").setValue(Ext.util.Format.date(new Date(record.get("gmtIncome").time),'Y-m-d'));
						}
					}
				}
			}
		});
	},
	openApply:function(applyGroup, companyId){
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : Context.ROOT+Context.PATH+"/crm/open/openSvr.htm?applyGroup="+applyGroup+"&companyId="+companyId,
				method : "post",
				waitMsg:Context.SAVEMASK.msg,
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "服务已按照指定时间开通。",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO,
							fn:function(btnId, text){
								window.close();
							}
						});
						
						//关闭窗口
					} else {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "发生错误,信息没有被保存",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					}
				},
				failure : function(_form,_action){
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "发生错误,信息没有被保存",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	},
	refusedApply:function(applyGroup){
		Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/crm/open/refusedApply.htm?applyGroup="+applyGroup,
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "服务申请已被拒绝。",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO,
						fn:function(btnId, text){
							window.close();
						}
					});
				}else{
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "发生错误,操作没有成功。",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO
					});
				}
			},
			failure:function(response,opt){
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "发生错误,操作没有成功",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			}
		});
	}
});

ast.ast1949.crm.open.SvrApplyDetailForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelWidth:100,
			labelAlign:"right",
			frame:true,
			layout:"column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"datefield",
					labelSeparator:""
				},items:[{
					xtype:"hidden",
					name:"id",
					id:"id"
				},{
					xtype:"hidden",
					name:"companyId"
				},{
					xtype:"hidden",
					name:"crmServiceCode"
				},{
					fieldLabel:"上次服务开始时间",
					format:"Y-m-d",
					name:"gmtPreStartDate",
					id:"gmtPreStart"
				},{
					fieldLabel:"本次服务开始时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtStartDate",
					id:"gmtStart"
				},{
					fieldLabel:"服务签定时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtSignedDate",
					id:"gmtSigned"
				},{
					xtype:"numberfield",
					fieldLabel:"再生通年限",
					id:"zstYear",
					name:"zstYear"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"datefield",
					labelSeparator:""
				},items:[{
					fieldLabel:"上次服务结束时间",
					format:"Y-m-d",
					name:"gmtPreEndDate",
					id:"gmtPreEnd"
				},{
					fieldLabel:"本次服务结束时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtEndDate",
					id:"gmtEnd"
				},{
					xtype:"combo",
					fieldLabel:"类别",
					itemCls:"required",
					allowBlank:false,
					name:"categoryName",
					lazyInit:true,
					mode:"local",
					triggerAction:"all",
					lazyRender:true,
					store:new Ext.data.SimpleStore({
						fields:['k','v'],
						data:[[0,'付费'],[1,'赠送']]
					}),
					valueField:"k",
					displayField:"v",
					id:"categoryName",
					hiddenName:"category",
					listeners:{
						"blur":function(field){
							if(Ext.get("categoryName").dom.value==""){
								field.setValue("");
							}
						}
					}
				}]
			},{
				columnWidth:1,
				xtype:"textarea",
				anchor:"99%",
				fieldLabel:"备注",
				name:"remark"
			}],
			buttons:[{
				text:"保存服务申请",
				scope:this,
				handler:function(btn){
					if(this.findById("id").getValue()<=0){
						ast.ast1949.utils.Msg("","请先从左侧表格选择要编辑的服务申请");
						return ;
					}
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : Context.ROOT+Context.PATH+"/crm/open/updateApplySvr.htm",
							method : "post",
							waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									ast.ast1949.utils.Msg("","信息已保存");
									Ext.getCmp(OPEN.APPLYSVRGRID).getStore().reload();
								} else {
									Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "发生错误,信息没有被保存",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
								}
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,信息没有被保存",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
							}
						});
					}
				}
			}]
		};
		
		ast.ast1949.crm.open.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadSvrDetail:function(id){
		var fields=ast.ast1949.crm.open.SvrDetail;
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + Context.PATH + "/crm/open/querySvrById.htm?id="+id,
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
						form.getForm().loadRecord(record);
						//Ext.util.Format.date(new Date(value.time),'Y-m-d')
						if(record.get("gmt_pre_start")!=null){
							form.findById("gmtPreStart").setValue(Ext.util.Format.date(new Date(record.get("gmt_pre_start").time),'Y-m-d'));
						}
						if(record.get("gmt_pre_end")!=null){
							form.findById("gmtPreEnd").setValue(Ext.util.Format.date(new Date(record.get("gmt_pre_end").time),'Y-m-d'));
						}
						if(record.get("gmt_start")!=null){
							form.findById("gmtStart").setValue(Ext.util.Format.date(new Date(record.get("gmt_start").time),'Y-m-d'));
						}
						if(record.get("gmt_end")!=null){
							form.findById("gmtEnd").setValue(Ext.util.Format.date(new Date(record.get("gmt_end").time),'Y-m-d'));
						}
						if(record.get("gmt_signed")!=null){
							form.findById("gmtSigned").setValue(Ext.util.Format.date(new Date(record.get("gmt_signed").time),'Y-m-d'));
						}
					}
				}
			}
		});
	}
});

ast.ast1949.crm.open.OpenHistoryGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT+Context.PATH+"/crm/open/applyQueryCompany.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm=new Ext.grid.ColumnModel([sm,{
			header:"编号",
			hidden:true,
			dataIndex:"id"
		}
		,{
			width:380,
			header:"公司名称",
			dataIndex:"companyName"
		}
		,{
			header:"email",
			width:250,
			dataIndex:"email"
		}
		,{
			header:"签约时间",
			dataIndex:"ccs.gmt_signed",
			sortable : true,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		}
		,{
			header:"开始时间",
			dataIndex:"ccs.gmt_start",
			sortable : true,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"结束时间",
			dataIndex:"ccs.gmt_end",
			sortable : true,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		}
		]);
		
		var _fields=this.fields;
		
		var _url=this.queryUrl;
		var _store=new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totalRecords',
				remoteSort:true,
				fields:_fields,
				url:_url,
				autoLoad:false
		});
		
		var _toolbar = this.toolbar;
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store,
			tbar : _toolbar,
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
		ast.ast1949.crm.open.OpenHistoryGrid.superclass.constructor.call(this,c);
	},
	fields:[{name:"id",mapping:"company.id"},
		{name:"companyName",mapping:"company.name"},
		{name:"email",mapping:"account.email"},
		{name:"applyGroup",mapping:"crmCompanySvr.applyGroup"},
		{name:"ccs.gmt_signed",mapping:"crmCompanySvr.gmtSigned"},
		{name:"ccs.gmt_start",mapping:"crmCompanySvr.gmtStart"},
		{name:"ccs.gmt_end",mapping:"crmCompanySvr.gmtEnd"}
	]
});
