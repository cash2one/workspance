Ext.namespace("ast.ast1949.admin.crm.opens")

var _C = new function() {
	this.PAY_SURE_FORM = "pay_sure_form";
}

Ext.onReady(function() {
	
	var form = new ast.ast1949.admin.crm.opens.PaySureForm({
		title:"付费会员服务到款确认单",
		id:_C.PAY_SURE_FORM,
		region:"center"
	});

	var viewport = new Ext.Viewport({
		layout:"border",
		items:[form]
	});
})

//表单
ast.ast1949.admin.crm.opens.PaySureForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var myCheckboxGroup = new Ext.form.RadioGroup({
			id:"myGroup",
			allowBlank:false,
			itemCls:"required",
			fieldLabel:"客户类型：",
			items:[
				{boxLabel:"新客户",name:"customerType",inputValue:"1",checked:true},
				{boxLabel:"续签客户",name:"customerType",inputValue:"2"},
				{boxLabel:"VAP客户",name:"customerType",inputValue:"3"}
			]
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
					value:CONST.COMPANYID
				},myCheckboxGroup,{
					xtype:"datefield",
					fieldLabel:"到款时间：",
					name:"gmtPaid",
					allowBlank:false,
					itemCls:"required",
					blankText:"请选择到款时间",
					format:"Y-m-d",
					value:new Date(),
					readOnly:true
				},{
					name:"originalEmail",
					fieldLabel:"原客户邮箱：",
					allowBlank:false,
					itemCls:"required",
					blankText:"原客户邮箱不能为空"
				},{
					name:"openedEmail",
					fieldLabel:"要开通的客户邮箱：",
					allowBlank:false,
					itemCls:"required",
					blankText:"要开通的客户邮箱不能为空"
				},{
					xtype:"numberfield",
					name:"amount",
					fieldLabel:"到款金额(销售金额)：",
					allowBlank:false,
					itemCls:"required",
					blankText:"到款金额不能为空"
				},{
					name:"saleName",
					value:CONST.SALENAME,
					fieldLabel:"销售人员：",
					allowBlank:false,
					itemCls:"required",
					blankText:"销售人员名称不能为空"
									
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注："
				}
//				,{
//					xtype:"label",
//					html:"<span style='color:#FF0000;font-size: 13px; '>说明</span>：<span style='font-size: 14px; '><strong>若有广告业务请同时</strong></span><a href='#'>填写广告开通单</a><strong><span style='color:#FF0000;font-size: 13px; '>（赠送广告也必须填写申请单）</span></strong>"
//				}
				]
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.PAY_SURE_FORM).getForm().reset();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.crm.opens.PaySureForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/crm/opens/savePaySure.htm",
	save:function() {
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
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
	},
	onSaveSuccess:function(_form, _action) {
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "提交成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	},
	onSaveFailure:function(_form, _action) {
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "提交失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});