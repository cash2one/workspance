Ext.namespace("ast.ast1949.admin.crm.opens")

var _C = new function() {
	this.REGENER_FORM="regenerform"
	this.BRAND_FORM="brandform";
	this.AD_FORM="adform";
	this.SMALL_AD_FORM="smalladform";
	this.OTHER_FORM="otherform";
}

Ext.onReady(function() {
	var tags = new ast.ast1949.admin.crm.opens.ApplyFormTab({
		activeTab: 0	//默认显示第一个Tab
	});
	
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[tags]
	});
	
})

ast.ast1949.admin.crm.opens.ApplyFormTab = Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var c={
//			activeTab: 1,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			defaults:{
				closable: true
			},	
			items:[{
				title:'再生通开通单',
				layout:"border",
				items:[
					new ast.ast1949.admin.crm.opens.RegenerForm({
						id:_C.REGENER_FORM,
						region:"center"
					})
				]
			},{
				title:"品牌通",
				layout:"border",
				items:[
						new ast.ast1949.admin.crm.opens.BrandForm({
						id:_C.BRAND_FORM,
						region:"center"
					})
				]
			},{
				title:"广告",
				layout:"border",
				items:[
						new ast.ast1949.admin.crm.opens.AdForm({
						id:_C.AD_FORM,
						region:"center"
					})
				]
			
			},{
				title:"小产品",
				layout:"border",
				items:[
						new ast.ast1949.admin.crm.opens.SmallAdForm({
						id:_C.SMALL_AD_FORM,
						region:"center"
					})
				]
			},{
				title:'新产品及其他',
				layout:"border",
				items:[
					new ast.ast1949.admin.crm.opens.OtherForm({
						id:_C.OTHER_FORM,
						region:"center"
					})
				]
			}]
		};

		ast.ast1949.admin.crm.opens.ApplyFormTab.superclass.constructor.call(this,c);

	}
});

//再生通表单
ast.ast1949.admin.crm.opens.RegenerForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 120,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
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
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"companyId",
					name:"companyId",
					value:PAGE_CONST.COMPANY_ID
				},{
					xtype:"hidden",
					id:"serveId",
					name:"serveId",
					value:PAGE_CONST.SERVE_ID
				},{
					xtype:"hidden",
					id:"membershipTypeCode",
					name:"membershipTypeCode",
					value:"10051001"
				}, {
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					displayField : "realName",
					valueField : "id",
					value:PAGE_CONST.SERVE_NAME,
					fieldLabel:"服务人员:",
//					allowBlank:false,
//					blankText:"服务人员不能为空",
//					itemCls:"required",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "realName", "id" ],
						autoLoad:false,
						url : Context.ROOT + Context.PATH+ "/admin/crm/getUserList.htm"
					}),
					listeners:{
						"blur":function(field){
							if(field.getValue()!=null&&field.getValue()>0) {
								Ext.get("serveId").dom.value=field.getValue();
							}
						}
					}	
				},
//				new Ext.form.RadioGroup({
//		//			id:"myGroup",
//					fieldLabel:"会员类型：",
//					allowBlank:false,
//					blankText:"请选会员类型",
//					itemCls:"required",
//					items:[
//						{boxLabel:"注册",name:"membershipTypeCode",inputValue:"0",checked:true},
//						{boxLabel:"付费",name:"membershipTypeCode",inputValue:"1"}
//					]
//				}),
				{
					name:"url",
					fieldLabel:"二级域名："
				},new Ext.form.RadioGroup({
		//			id:"myGroup",
					fieldLabel:"服务状态：",
					allowBlank:false,
					blankText:"请选服务状态",
					itemCls:"required",
					items:[
						{boxLabel:"开通",name:"serviceStatus",inputValue:"1"},
						{boxLabel:"关闭",name:"serviceStatus",inputValue:"0"},
						{boxLabel:"暂停（仍在服务期内）",name:"serviceStatus",inputValue:"2"}
					]
				}),{
					xtype:"numberfield",
					name:"amount",
					fieldLabel:"金额：",
					allowBlank:false,
					itemCls:"required",
					blankText:"金额不能为空"
				},new Ext.form.CheckboxGroup({
		//			id:"myGroup",
					fieldLabel:"提示方式：",
					items:[
						{boxLabel:"是否发送开通成功的邮件",name:"isSendEmail",inputValue:"1"},
						{boxLabel:"是否发送开通成功的短信",name:"isSendMsm",inputValue:"1"}
					]
				}),{
					xtype:"hidden",
					id:"contractType",
					name:"contractType",
					value:"0"
				},
				{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注"
				},
				new Ext.TabPanel({
//					region:"south",
					activeTab:0,
					split:true,
					height:300,
					items:[{
						title:"新签",
						layout:"fit",
						items:[{
							labelAlign : "right",
							labelWidth : 120,
							layout:"form",
							bodyStyle:"padding:5px 0 0",
							frame:true,
							items:[{
								xtype:"datefield",
								fieldLabel:"开通时间",
								name:"gmtStart",
								allowBlank:false,
								itemCls:"required",
								blankText:"请选择开通时间",
								format:"Y-m-d",
								value:new Date(),
								readOnly:true
							},{
								xtype:"datefield",
								fieldLabel:"结束时间",
								name:"gmtEnd",
								allowBlank:false,
								itemCls:"required",
								blankText:"请选择结束时间",
								format:"Y-m-d",
//								value:new Date(),
								readOnly:true
							}]
						}]
					},{
						title:"续签",
						layout:"fit",
						items:[{
							labelAlign : "right",
							labelWidth : 120,
							layout:"form",
							bodyStyle:"padding:5px 0 0",
							frame:true,
							items:[{
								xtype:"datefield",
								fieldLabel:"上次到期时间",
								name:"lastEnd",
								allowBlank:true,
//								itemCls:"required",
//								blankText:"请选择上次到期时间",
								format:"Y-m-d",
//								value:new Date(),
								readOnly:true
							},{
								xtype:"datefield",
								fieldLabel:"实际续签时间",
								name:"gmtStart",
								allowBlank:false,
								itemCls:"required",
								blankText:"请选择实际续签时间",
								format:"Y-m-d",
								value:new Date(),
								readOnly:true
							},{
								xtype:"datefield",
								fieldLabel:"结束时间",
								name:"gmtEndt",
								allowBlank:false,
								itemCls:"required",
								blankText:"请选择结束时间",
								format:"Y-m-d",
								readOnly:true
							}]
						}]
					}]
				})
				]
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.OTHER_FORM).getForm().reset();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.crm.opens.OtherForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/crm/opens/saveRegener.htm",
	save:function() {
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:onSaveSuccess,
				failure:onSaveFailure,
				scope:this
			});
		} else {
			Ext.MessageBox.show({
				title : Context.MSF_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR 
			});
		}
	}
});

//品牌通表单
ast.ast1949.admin.crm.opens.BrandForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		var brandType = new Ext.form.RadioGroup({
//			id:"myGroup",
			allowBlank:false,
			itemCls:"required",
			fieldLabel:"类型：",
			items:PAGE_CONST.BRAND_CATEGORY
		});
		
		var c={
			labelAlign : "right",
			labelWidth : 120,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
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
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"companyId",
					name:"companyId",
					value:PAGE_CONST.COMPANY_ID
				},{
					xtype:"hidden",
					id:"serveId",
					name:"serveId",
					value:PAGE_CONST.SERVE_ID
				},brandType,{
					xtype:"datefield",
					fieldLabel:"开通时间：",
					name:"gmtStart",
					allowBlank:false,
					itemCls:"required",
					blankText:"请选择开通时间",
					format:"Y-m-d",
					value:new Date(),
					readOnly:true
				},{
					xtype:"datefield",
					fieldLabel:"结束时间：",
					name:"gmtEnd",
					allowBlank:false,
					itemCls:"required",
					blankText:"请选择结束时间",
					format:"Y-m-d",
					value:new Date(),
					readOnly:true
				},{
					xtype:"numberfield",
					name:"amount",
					fieldLabel:"金额：",
					allowBlank:false,
					itemCls:"required",
					blankText:"金额不能为空"
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注"
				}]
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.BRAND_FORM).getForm().reset();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.crm.opens.BrandForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/crm/opens/saveVabusiness.htm",
	save:function() {
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:onSaveSuccess,
				failure:onSaveFailure,
				scope:this
			});
		} else {
			Ext.MessageBox.show({
				title : Context.MSF_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR 
			});
		}
	}
});

//广告表单
ast.ast1949.admin.crm.opens.AdForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var adType = new Ext.form.CheckboxGroup({
//			id:"myGroup",
			fieldLabel:"类型：",
			allowBlank:false,
			itemCls:"required",
			items:PAGE_CONST.AD_CATEGORY
		});
		
		var c={
			labelAlign : "right",
			labelWidth : 120,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
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
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"companyId",
					name:"companyId",
					value:PAGE_CONST.COMPANY_ID
				},{
					xtype:"hidden",
					id:"serveId",
					name:"serveId",
					value:PAGE_CONST.SERVE_ID
				},adType,{
					xtype:"datefield",
					fieldLabel:"开通时间：",
					name:"gmtStart",
					allowBlank:false,
					itemCls:"required",
					blankText:"请选择开通时间",
					format:"Y-m-d",
					value:new Date(),
					readOnly:true
				},{
					xtype:"datefield",
					fieldLabel:"结束时间：",
					name:"gmtEnd",
					allowBlank:false,
					itemCls:"required",
					blankText:"请选择结束时间",
					format:"Y-m-d",
					value:new Date(),
					readOnly:true
				},{
					xtype:"numberfield",
					name:"amount",
					fieldLabel:"金额：",
					allowBlank:false,
					itemCls:"required",
					blankText:"金额不能为空"
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注"
				}]
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.AD_FORM).getForm().reset();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.crm.opens.AdForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/crm/opens/saveVabusiness.htm",
	save:function() {
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:onSaveSuccess,
				failure:onSaveFailure,
				scope:this
			});
		} else {
			Ext.MessageBox.show({
				title : Context.MSF_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR 
			});
		}
	}
});

//小广告表单
ast.ast1949.admin.crm.opens.SmallAdForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var smallAdType = new Ext.form.CheckboxGroup({
//			id:"myGroup",
			allowBlank:false,
			itemCls:"required",
			fieldLabel:"类型：",
			items:PAGE_CONST.SAD_CATEGORY
//			[
//				{boxLabel:"黄页",name:"codes",inputValue:"1"},
//				{boxLabel:"报价",name:"codes",inputValue:"5"}
//			]
		});
		
		var c={
			labelAlign : "right",
			labelWidth : 120,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
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
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"companyId",
					name:"companyId",
					value:PAGE_CONST.COMPANY_ID
				},{
					xtype:"hidden",
					id:"serveId",
					name:"serveId",
					value:PAGE_CONST.SERVE_ID
				},smallAdType,{
					xtype:"numberfield",
					name:"amount",
					fieldLabel:"金额：",
					allowBlank:false,
					itemCls:"required",
					blankText:"金额不能为空"
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注"
				}]
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.SMALL_AD_FORM).getForm().reset();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.crm.opens.SmallAdForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/crm/opens/saveVabusiness.htm",
	save:function() {
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:onSaveSuccess,
				failure:onSaveFailure,
				scope:this
			});
		} else {
			Ext.MessageBox.show({
				title : Context.MSF_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR 
			});
		}
	}
});

//其他表单
ast.ast1949.admin.crm.opens.OtherForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 120,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
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
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"companyId",
					name:"companyId",
					value:PAGE_CONST.COMPANY_ID
				},{
					xtype:"hidden",
					id:"serveId",
					name:"serveId",
					value:PAGE_CONST.SERVE_ID
				},{
					name:"proName",
					fieldLabel:"产品内容：",
					allowBlank:false,
					itemCls:"required",
					blankText:"产品内容不能为空"				
				},{
					name:"amount",
					fieldLabel:"金额：",
					allowBlank:false,
					itemCls:"required",
					blankText:"金额不能为空"
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注"
				}]
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.OTHER_FORM).getForm().reset();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.crm.opens.OtherForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/crm/opens/saveVabusiness.htm",
	save:function() {
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:onSaveSuccess,
				failure:onSaveFailure,
				scope:this
			});
		} else {
			Ext.MessageBox.show({
				title : Context.MSF_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR 
			});
		}
	}
});

/**
 * 提交成功
 * @param {} _form
 * @param {} _action
 */
function onSaveSuccess(_form, _action) {
	if(PAGE_CONST.ORDER_ID!=null&&PAGE_CONST.ORDER_ID>0) {
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/crm/await/deal.htm?random="+Math.random()+"&status=1&ids="+PAGE_CONST.ORDER_ID,
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
				if(success){
					ast.ast1949.utils.Msg("","提交成功！");//Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
				}else{
					ast.ast1949.utils.Msg("","提交成功,但开通单处理状态更新失败！");
				}
			}
		});
	} else {
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "提交成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	}
}
/**
 * 提价失败
 * @param {} _form
 * @param {} _action
 */
function onSaveFailure(_form, _action) {
	Ext.MessageBox.show({
		title:Context.MSG_TITLE,
		msg : "提交失败！",
		buttons:Ext.MessageBox.OK,
		icon:Ext.MessageBox.ERROR
	});
}