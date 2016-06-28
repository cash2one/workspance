Ext.namespace("com.zz91.ep.api.svr");

com.zz91.ep.api.svr.SvrDetail=[
	{name:"apply_group",mapping:"applyGroup"},
	{name:"gmt_signed",mapping:"gmtSigned"},
	{name:"gmt_start",mapping:"gmtStart"},
	{name:"gmt_end",mapping:"gmtEnd"},
	{name:"gmt_pre_start",mapping:"gmtPreStart"},
	{name:"gmt_pre_end",mapping:"gmtPreEnd"},
	{name:"apply_status",mapping:"applyStatus"},
	{name:"saleCategory",mapping:"saleCategory"},
	{name:"remark",mapping:"remark"},
	{name:"signed_type",mapping:"signedType"},
	{name:"remark",mapping:"remark"},
	{name:"cid",mapping:"cid"},
	{name:"crmServiceCode",mapping:"crmServiceCode"},
	{name:"id",mapping:"id"}
];

com.zz91.ep.api.svr.SvrApplyDetailForm=Ext.extend(Ext.form.FormPanel,{
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
					anchor:"99%",
					xtype:"datefield",
					labelSeparator:""
				},items:[{
					xtype:"hidden",
					name:"id",
					id:"id"
				},{
					xtype:"hidden",
					name:"cid",
					id:"cid"
				},{
					xtype:"hidden",
					name:"applyStatus",
					id:"applyStatus"
				},{
					fieldLabel:"上次服务开始时间",
					format:"Y-m-d",
					name:"gmtPreStartDate",
					id:"gmtPreStart",
					readOnly:true
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
				}
//				,{
//					xtype:"numberfield",
//					name:"integral",
//					id:"integral",
//					fieldLabel:"增加积分"
//				}
				]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"datefield",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"上次服务结束时间",
					format:"Y-m-d",
					name:"gmtPreEndDate",
					id:"gmtPreEnd",
					readOnly:true
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
					name:"saleCategoryName",
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
					id:"saleCategoryName",
					hiddenName:"saleCategory",
					listeners:{
						"blur":function(field){
							if(Ext.get("saleCategoryName").dom.value==""){
								field.setValue("");
							}
						}
					}
				}
//				,{
//					xtype:"checkbox",
//					inputValue:true,
//					boxLabel:"邮件通知",
//					name:"noticeEmail",
//					id:"noticeEmail"
//				},{
//					xtype:"checkbox",
//					inputValue:true,
//					boxLabel:"短信通知",
//					name:"noticeSms",
//					id:"noticeSms"
//				}
				]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					labelSeparator:""
				},
				items:[{
					xtype:"textarea",
					anchor:"99%",
					fieldLabel:"备注",
					name:"remark"
				}]
			}],
			buttons:[{
				text:"现在开通",
				scope:this,
				handler:function(btn){
					if (!this.getForm().isValid()) {
						com.zz91.utils.Msg("","请先填写必要的信息");
						return false;
					}
						
					var form=this;
					Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定信息都填写无误，立即开通服务吗？", function(btn){
						if(btn != "yes"){
							return false;
						}
						form.getForm().submit({
							url : Context.ROOT+"/api/svrcommon/doOpen.htm",
							method : "post",
							//waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									com.zz91.utils.Msg("","服务已开通");
									parent.com.zz91.ep.api.callback();
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
					});
					
				}
			}]
		};
		
		com.zz91.ep.api.svr.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadOpenDetail:function(id, cid){
		this.findById("cid").setValue(cid);
		var fields=com.zz91.ep.api.svr.SvrDetail;
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + "/api/svrcommon/querySvrById.htm?id="+id+"&cid="+cid,
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
						
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
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});

com.zz91.ep.svr.CompanySvrHistoryGrid=Ext.extend(Ext.grid.GridPanel,{
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
					return "服务关闭(手动)";
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
			url:Context.ROOT + "/crm/open/queryApplySvrHistory.htm?cid=" + cid,
			autoLoad:false
		});
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store
		};
		
		com.zz91.ep.svr.CompanySvrHistoryGrid.superclass.constructor.call(this,c);
	},
	loadByCompany:function(cid){
		this.getStore().reload({params:{"cid":cid}});
	}
});