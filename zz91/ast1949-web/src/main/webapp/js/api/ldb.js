Ext.namespace("ast.ast1949.api.ldb");

ast.ast1949.api.ldb.SvrDetail=[
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

ast.ast1949.api.ldb.SvrApplyDetailForm=Ext.extend(Ext.form.FormPanel,{
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
					name:"companyId",
					id:"companyId"
				},{
					xtype:"hidden",
					name:"applyStatus",
					id:"applyStatus"
				},{
					xtype:"hidden",
					name:"oldCsAccount",
					id:"oldCsAccount"
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
					xtype:"combo",
					allowBlank:false,
					itemCls:"required",
					mode:"local",
					triggerAction:"all",
					name:"membership",
					id:"membership",
					hiddenName:"membershipCode",
					hiddenId:"membershipCode",
					fieldLabel:"会员类型",
					store:[
						["10051000","普通会员"],
						["10051003","来电宝会员"]
					],
					listeners:{
						"blur":function(field){
							if(Ext.get("membership").dom.value==""){
								field.setValue("");
							}
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"通话费用(元)",
//					allowBlank:false,
//					itemCls:"required",
					hiddenName:"telFee",
					id:"phoneFee",
					name:"phoneFee",
					store:[
						//["2.8","两块八"],
						["4.9","四块九"]
					]
				},{
					xtype:"textfield",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"短信费用(元)",
//					allowBlank:false,
//					itemCls:"required",
					id:"smsFee",
					name:"smsFee"
				},{
					xtype:"textfield",
					forceSelection : true,
					fieldLabel:"消费金额",
//					allowBlank:false,
//					itemCls:"required",
					name:"fee",
					id:"fee"
				}
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
					id:"categoryName",
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
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"指定客服",
					allowBlank:false,
					itemCls:"required",
					displayField : "name",
					valueField : "account",
					hiddenId:"csAccount",
					hiddenName:"csAccount",
					id:"cs",
					store:new Ext.data.JsonStore( {
						fields : [ "account", "name" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/crm/open/applyQueryCs.htm"
					}),
					listeners:{
						"blur":function(field){
							if(Ext.get("cs").dom.value==""){
								field.setValue("");
							}
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"点击费用(元)",
//					allowBlank:false,
//					itemCls:"required",
					id:"clickfee",
					name:"clickfee",
					hiddenName:"clickFee",
					store:[
						//["1","一元"],
						["5","五元"]
					]
				}
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
						ast.ast1949.utils.Msg("","请先填写必要的信息");
						return false;
					}
						
					var form=this;
					Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定信息都填写无误，立即开通服务吗？", function(btn){
						if(btn != "yes"){
							return false;
						}
						form.getForm().submit({
							url : Context.ROOT+Context.PATH+"/api/ldb/doOpen.htm",
							method : "post",
							waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									ast.ast1949.utils.Msg("","服务已开通");
									parent.ast.ast1949.api.callback();
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
		
		ast.ast1949.api.ldb.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadOpenDetail:function(id, companyId,data){
		this.findById("companyId").setValue(companyId);
		this.findById("oldCsAccount").setValue(data["csAccount"]);
		var fields=ast.ast1949.api.ldb.SvrDetail;
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + Context.PATH + "/api/ldb/querySvrById.htm?id="+id,
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
	},
	smsShow:function(){
		var form = this;
//		form.findById("smsFee").getEl().up('.x-form-item').setDisplayed(true);
		form.findById("clickfee").getEl().up('.x-form-item').setDisplayed(false);
		form.findById("phoneFee").getEl().up('.x-form-item').setDisplayed(false);
		form.findById("fee").getEl().up('.x-form-item').setDisplayed(false);
//		form.findById("membership").getEl().up('.x-form-item').setDisplayed(false);
	},
	smsHide:function(){
		var form = this;
		form.findById("smsFee").getEl().up('.x-form-item').setDisplayed(false);
	},
	feeTong:function(){
		var form = this;
		form.findById("clickfee").getEl().up('.x-form-item').setDisplayed(false);
		form.findById("phoneFee").getEl().up('.x-form-item').setDisplayed(false);
		form.findById("fee").getEl().up('.x-form-item').setDisplayed(false);
	}
	
});

// 修改表单
ast.ast1949.api.ldb.SvrApplyDetailEditForm=Ext.extend(Ext.form.FormPanel,{
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
					name:"companyId",
					id:"companyId"
				},{
					xtype:"hidden",
					name:"applyStatus",
					id:"applyStatus"
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
				}
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
					id:"categoryName",
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
				}
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
					id:"remark",
					name:"remark"
				}]
			}],
			buttons:[{
				text:"变更",
				scope:this,
				handler:function(btn){
					if (!this.getForm().isValid()) {
						ast.ast1949.utils.Msg("","请先填写必要的信息");
						return false;
					}
						
					var form=this;
					Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定信息都填写无误，立即开通服务吗？", function(btn){
						if(btn != "yes"){
							return false;
						}
						form.getForm().submit({
							url : Context.ROOT+Context.PATH+"/api/ldb/doChange.htm",
							method : "post",
							waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									ast.ast1949.utils.Msg("","服务已开通");
									parent.ast.ast1949.api.callback();
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
		
		ast.ast1949.api.ldb.SvrApplyDetailEditForm.superclass.constructor.call(this,c);
	},
	loadChangeDetail:function(id, companyId){
		this.findById("companyId").setValue(companyId);
		var fields=ast.ast1949.api.ldb.SvrDetail;
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + Context.PATH + "/api/ldb/querySvrById.htm?id="+id,
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
						form.getForm().loadRecord(record);
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
