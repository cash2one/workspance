Ext.namespace("com.zz91.ep.crm.open");

var OPEN=new function(){
	this.APPLYSVRGRID="applysvrgrid";
	this.ASSIGNCSWIN="assigncswin";
	this.HISTORY_GRID="historygrid";
}

com.zz91.ep.crm.open.Category=["付费","赠送"];
com.zz91.ep.crm.open.ApplyStatus={"0":"申请中","1":"已开通","2":"拒绝开通","3":"服务关闭(手动)"};

com.zz91.ep.crm.open.step1=function(){
	var companyGrid=new com.zz91.ep.crm.open.Step1CompanyGrid({
		id:"step1Company",
		height:200
	});
	
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
						com.zz91.ep.crm.open.step2();
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

com.zz91.ep.crm.open.step2=function(){
	var win = Ext.getCmp("step2win");
	if(typeof(win)!="undefined"){
		win.show();
		return ;
	}
	
	var svrGrid= new com.zz91.ep.crm.svr.Grid({
		id:"step2svr",
		height:200,
		autoload:true,
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
						com.zz91.ep.crm.open.step3();
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

com.zz91.ep.crm.open.step3=function(){
	
	var win=Ext.getCmp("step3win"); 
	if(typeof(win)!="undefined"){
		win.show();
		return ;
	}
	
	var form = new com.zz91.ep.crm.open.Step3ApplyForm({
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
					form.applySubmit(companyRow.get("id"), svrCodeArr);
				}
			}
		}]
	});
	
	win.show();
};

com.zz91.ep.crm.open.Step1CompanyGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT+"/crm/open/applyQueryCompany.htm",
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
		com.zz91.ep.crm.open.Step1CompanyGrid.superclass.constructor.call(this,c);
	},
	fields:[{name:"id",mapping:"compProfile.id"},
		{name:"companyName",mapping:"compProfile.name"},
		{name:"email",mapping:"compAccount.email"}
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

com.zz91.ep.crm.open.Step1CompanySvrGrid=Ext.extend(Ext.grid.GridPanel,{
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
				if(value=="3"){
					return "手动关闭";
				}
				return "";
			}
		},{
			header:"备注",
			dataIndex:"remark"
		}]);
		
		var reader=[
			{name:"nameLabel",mapping:"svrName"},
			{name:"gmt_start",mapping:"crmCompSvr.gmtStart"},
			{name:"gmt_end",mapping:"crmCompSvr.gmtEnd"},
			{name:"status",mapping:"crmCompSvr.applyStatus"},
			{name:"remark",mapping:"crmCompSvr.remark"}
		];
		
		var _store=new Ext.data.JsonStore({
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + "/crm/open/queryApplySvrHistory.htm",
			autoLoad:false
		});
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store
		};
		
		com.zz91.ep.crm.open.Step1CompanySvrGrid.superclass.constructor.call(this,c);
	},
	loadByCompany:function(cid){
		this.getStore().reload({params:{"cid":cid}});
	}
});

com.zz91.ep.crm.open.Step3ApplyForm=Ext.extend(Ext.form.FormPanel,{
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
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"销售人员",
					allowBlank:false,
					itemCls:"required",
					displayField : "name",
					valueField : "account",
					hiddenId:"saleStaff",
					hiddenName:"saleStaff",
					id:"sale",
					store:new Ext.data.JsonStore( {
						fields : [ "account", "name" ],
						autoLoad:true,
						url : Context.ROOT + "/crm/open/applyQueryCs.htm"
					}),
					listeners:{
						"blur":function(field){
							if(Ext.get("sale").dom.value==""){
								field.setValue("");
							}
						}
					}
				},{
					xtype:"datefield",
					fieldLabel:"开始时间",
					allowBlank:false,
					format:"Y-m-d",
					itemCls:"required",
					name:"gmtStartDate"
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
				},{
					xtype:"datefield",
					fieldLabel:"签约时间",
					allowBlank:false,
					format:"Y-m-d",
					itemCls:"required",
					name:"gmtSignedDate"
				},{
					xtype:"datefield",
					fieldLabel:"结束时间",
					allowBlank:false,
					format:"Y-m-d",
					itemCls:"required",
					name:"gmtEndDate"
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
		
		com.zz91.ep.crm.open.Step3ApplyForm.superclass.constructor.call(this,c);
	},
	applySubmit:function(cid, svrCodeArr){
		if (this.getForm().isValid()) {
			this.findById("amount").setValue(this.findById("amountFloat").getValue()*100);
			this.getForm().submit({
				url : Context.ROOT+"/crm/open/applySubmit.htm",
				method : "post",
				params:{"cid":cid,"svrCodeArr":svrCodeArr.join(",")},
				//waitMsg:Context.SAVEMASK.msg,
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

com.zz91.ep.crm.open.CompanySvrGrid=Ext.extend(Ext.grid.EditorGridPanel,{
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
//				var st='<img src ="'+Context.ROOT+'/src/main/webapp/themes/icons/Item.True.gif" />';
//				if(record.get("applyStatus")=="1"){
//					st='<img src ="'+Context.ROOT+'/src/main/webapp/themes/icons/Item.True.gif" />';
//				}
//				if(record.get("applyStatus")=="2"){
//					st='<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
//				}
				return value;
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
				return com.zz91.ep.crm.open.Category[value];
			}
		},{
			header:"备注",
			dataIndex:"remark"
		}
		]);
		
		var reader=[{name:"apply_group",mapping:"crmCompSvr.applyGroup"},
			{name:"svrName",mapping:"svrName"},
			{name:"svrPrice",mapping:"svrPrice"},
			{name:"svrRemark",mapping:"svrRemark"},
			{name:"gmt_pre_start",mapping:"crmCompSvr.gmtPreStart"},
			{name:"gmt_pre_end",mapping:"crmCompSvr.gmtPreEnd"},
			{name:"gmt_signed",mapping:"crmCompSvr.gmtSigned"},
			{name:"gmt_start",mapping:"crmCompSvr.gmtStart"},
			{name:"gmt_end",mapping:"crmCompSvr.gmtEnd"},
			{name:"category",mapping:"crmCompSvr.saleCategory"},
			{name:"remark",mapping:"crmCompSvr.remark"},
			{name:"cid",mapping:"crmCompSvr.cid"},
			{name:"id",mapping:"crmCompSvr.id"},
			{name:"applyStatus",mapping:"crmCompSvr.applyStatus"},
			{name:"crmSvrId",mapping:"crmCompSvr.crmSvrId"}
		];
		
		var _store=new Ext.data.JsonStore({
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + "/crm/open/queryApplySvr.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var c={
				loadMask:Context.LOADMASK,
				sm:sm,
				cm:cm,
				store:_store,
				tbar:[{
					text:"开通",
					iconCls:"play16",
					handler:function(btn){
						var row=grid.getSelectionModel().getSelected();
						if(row!=null && typeof row !="undefined"){
							if(row.get("applyStatus")=="1"){
								com.zz91.utils.Msg("","服务已开通，请不要重复开通！");
								return false;
							}
							
							com.zz91.ep.api.callback=function(){
								com.zz91.ep.api.closeWin();
								_store.reload();
							}
							com.zz91.ep.api.showOpenWin(
								row.get("crmSvrId"),
								{"cid":row.get("cid"),
								"companySvrId":row.get("id")}
							);
						}else{
							com.zz91.utils.Msg("","请选择要开通的服务！");
						}
					}
				},"-",{
					text:"关闭",
					iconCls:"close16",
					handler:function(btn){
						var row=grid.getSelectionModel().getSelected();
						if(row!=null && typeof row !="undefined"){
							
							if(row.get("applyStatus")!="1"){
								com.zz91.utils.Msg("","服务没有开通，不能关闭！");
								return false;
							}
							
							com.zz91.ep.callback=function(){
								com.zz91.ep.api.closeWin();
								_store.reload();
							}
							com.zz91.ep.api.showCloseWin(
								row.get("crmSvrId"),
								{"cid":row.get("cid"),
								"companySvrId":row.get("id")}
							);
						}else{
							com.zz91.utils.Msg("","请选择要关闭的服务！");
						}
					}
				}
//				,{
//					text:"续签",
//					hidden:true,
//					handler:function(btn){
//					}
//				},"-",{
//					text:"变更",
//					handler:function(btn){
//						
//					}
//				}
		]
		};
		
		com.zz91.ep.crm.open.CompanySvrGrid.superclass.constructor.call(this,c);
	},
	loadApply:function(applyGroup){
		if(applyGroup==null || applyGroup.length<=0){
			return ;
		}
		this.getStore().baseParams["applyGroup"]=applyGroup;
		this.getStore().reload();
	}
});

com.zz91.ep.crm.open.SvrDetail=[
	{name:"apply_group",mapping:"applyGroup"},
	{name:"gmt_signed",mapping:"gmtSigned"},
	{name:"gmt_start",mapping:"gmtStart"},
	{name:"gmt_end",mapping:"gmtEnd"},
	{name:"gmt_pre_start",mapping:"gmtPreStart"},
	{name:"gmt_pre_end",mapping:"gmtPreEnd"},
	{name:"apply_status",mapping:"applyStatus"},
	{name:"category",mapping:"saleCategory"},
	{name:"remark",mapping:"remark"},
	{name:"signed_type",mapping:"signedStatus"},
	{name:"remark",mapping:"remark"},
	{name:"cid",mapping:"cid"},
	{name:"crmSvrId",mapping:"crmSvrId"},
	{name:"id",mapping:"id"}
];

com.zz91.ep.crm.open.CompanySvrHistoryGrid=Ext.extend(Ext.grid.GridPanel,{
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
					return com.zz91.ep.crm.open.ApplyStatus[value];
				}
			}
		},{
			header:"类别",
			dataIndex:"category",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return com.zz91.ep.crm.open.Category[value];
			}
		},{
			header:"备注",
			width:200,
			dataIndex:"remark"
		}]);
		
		var reader=com.zz91.ep.crm.open.SvrDetail;
		
		var _store=new Ext.data.JsonStore({
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + "/crm/open/querySvrHistory.htm",
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
					window.open(Context.ROOT + "/crm/open/detail.htm?applyGroup="+row.get("apply_group")+"&cid="+row.get("cid"))
				}
			}]
		};
		
		com.zz91.ep.crm.open.CompanySvrHistoryGrid.superclass.constructor.call(this,c);
	},
	loadHistory:function(cid, crmSvrId){
		this.getStore().baseParams["cid"]=cid;
		this.getStore().baseParams["crmSvrId"]=crmSvrId;
		this.getStore().reload();
	}
});

com.zz91.ep.crm.open.SvrApplyForm=Ext.extend(Ext.form.FormPanel,{
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
			],
			buttons:[{
				iconCls:"save",
				text:"保存开通单",
				handler:form.saveApply
			}]
		};
		
		com.zz91.ep.crm.open.SvrApplyForm.superclass.constructor.call(this,c);
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
	loadApply:function(applyGroup, cid){
		var fields=["id","applyGroup","gmtIncome","amount","targetEmail","targetAmount","amountDetails",
		            "saleStaff","remark"];
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + "/crm/open/queryApplyForm.htm?applyGroup="+applyGroup,
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
	openApply:function(applyGroup, cid){
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : Context.ROOT+Context.PATH+"/crm/open/openSvr.htm?applyGroup="+applyGroup+"&="+cid,
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
			url:Context.ROOT+"/crm/open/refusedApply.htm?applyGroup="+applyGroup,
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

com.zz91.ep.crm.open.SvrApplyDetailForm=Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+"/trade/tradecategory/createCategory.htm",
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
					name:"cid"
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
						com.zz91.ep.utils.Msg("","请先从左侧表格选择要编辑的服务申请");
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
									com.zz91.ep.utils.Msg("","信息已保存");
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
		
		com.zz91.ep.crm.open.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadSvrDetail:function(id){
		var fields=com.zz91.ep.crm.open.SvrDetail;
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

com.zz91.ep.crm.open.OpenHistoryGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT+"/crm/open/queryApplyCompany.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var sm = new Ext.grid.CheckboxSelectionModel({});
		var cm=new Ext.grid.ColumnModel([sm,{
			header:"编号",
			dataIndex:"id",
			width:80
		},{
			header:"已分配帐号",
			dataIndex:"sale_staff",
			sortable : false,
			renderer:function(value,metadata,record,rowindex,colindex,store){
			var url;
			if (value!=""){
				url = "<front style='color:red'>"+value+"</front>";
			}
			return url ;
			}
		},{
			width:245,
			header:"公司名称",
			dataIndex:"companyName"
		},{
			header:"email",
			width:200,
			dataIndex:"email"
		},{
			header:"签约时间",
			dataIndex:"ccs.gmt_signed",
			sortable : true,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
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
				totalProperty:'totals',
				remoteSort:true,
				fields:_fields,
				url:_url,
				autoLoad:true
		});
		
		var _toolbar = this.toolbar;
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store,
			tbar : _toolbar,
			bbar: com.zz91.utils.pageNav(_store)
		};
		com.zz91.ep.crm.open.OpenHistoryGrid.superclass.constructor.call(this,c);
	},
	fields:[{name:"id",mapping:"compProfile.id"},
		{name:"companyName",mapping:"compProfile.name"},
		{name:"email",mapping:"compAccount.email"},
		{name:"applyGroup",mapping:"crmCompSvr.applyGroup"},
		{name:"ccs.gmt_signed",mapping:"crmCompSvr.gmtSigned"},
		{name:"ccs.gmt_start",mapping:"crmCompSvr.gmtStart"}, 
		{name:"ccs.gmt_end",mapping:"crmCompSvr.gmtEnd"},
		{name:"sale_staff",mapping:"saleStaff"}
	]
});

//服务开通窗口
com.zz91.ep.crm.open.openFormWin = function(id) {

	var form = new com.zz91.ep.crm.open.SvrApplyDetailForm({
				region : "center",
				saveUrl : Context.ROOT + Context.PATH
						+ "/trade/tradecategory/updateTradeProperty.htm"
			});

	var win = new Ext.Window({
				id : CATEGORY.EDIT_WIN,
				title : "类别属性修改",
				width : 850,
				modal : true,
				// autoHeight:true,
				// maximizable:true,
				items : [form]
			});
	form.loadRecord(id);
	win.show();
};

com.zz91.ep.crm.open.assignCsWin=function(adArr, callback){
	
	var win = new Ext.Window({
		id: OPEN.ASSIGNCSWIN,
		title:"选择要分配的客服",
		modal:true,
		width:216,
		items:[{
			xtype:"combo",
			triggerAction : "all",
			forceSelection : true,
			fieldLabel:"销售人员",
			allowBlank:false,
			itemCls:"required",
			displayField : "name",
			valueField : "account",
			hiddenName:"account",
			id:"sale",
			store:new Ext.data.JsonStore( {
				fields : [ "account", "name" ],
				autoLoad:true,
				url : Context.ROOT + "/crm/open/applyQueryCs.htm"
			}),
			listeners:{
				"blur":function(field){
					if(Ext.get("sale").dom.value==""){
						field.setValue("");
					}
				}
			}
		}],
		buttons:[{
			text:"取消",
			listeners:{
				"click":function(button){
					Ext.getCmp(OPEN.ASSIGNCSWIN).close();
				}
			}
		},{
			text:"分配",
			listeners:{
				"click":function(button){
					var account=Ext.getCmp("sale").getValue();
					if (account==""){
						return ;
					}
					var success=0;
					var failure=0;
					Ext.Msg.confirm("确认","如果客户申请多个服务,此操作将统一分配客户给同一个客服!<br/>你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}else{
							for(var i=0;i<adArr.length;i++){
								Ext.Ajax.request({
							        url:Context.ROOT+"/crm/open/assignApplyToCs.htm",
							        params:{"id":adArr[i],"account":account},
							        success:function(response,opt){
							            var obj = Ext.decode(response.responseText);
							            if(obj.success){
							            	var grid=Ext.getCmp(OPEN.HISTORY_GRID);
							            	success++;
							            	grid.getStore().reload();
							            }else{
							            	failure++;
							            }
							            if((success+failure) == adArr.length){
							            	com.zz91.utils.Msg(Context.MSG_TITLE,"共分配服务申请"+adArr.length+"个，其中成功"+success+"个，失败"+failure+"个。");
							            	win.close();
							            	callback();
							            }
							        },
							        failure:function(response,opt){
										failure++;
							        }
							    });
							}
						}
					});
				}
			}
		}]
	});
	win.show();
}