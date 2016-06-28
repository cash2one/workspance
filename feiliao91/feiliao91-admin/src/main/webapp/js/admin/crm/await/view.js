 Ext.namespace("ast.ast1949.admin.crm.await")

//表单
ast.ast1949.admin.crm.await.PaySureForm = Ext.extend(Ext.form.FormPanel, {
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
					name:"companyId"
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
			buttons:[/*{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"重填",
				handler:function(){
					Ext.getCmp(_C.PAY_SURE_FORM).getForm().reset();
				},
				scope:this
			}*/]
		};
		
		ast.ast1949.admin.crm.await.PaySureForm.superclass.constructor.call(this,c);
	},
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"companyId",mapping:"companyId"},
			{name:"customerType",mapping:"customerType"},
			{name:"gmtPaid",mapping:"gmtPaid",convert:function(value){
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d');
				}
			}},
			{name:"originalEmail",mapping:"originalEmail"},
			{name:"openedEmail",mapping:"openedEmail"},
			{name:"amount",mapping:"amount"},
			{name:"saleName",mapping:"saleName"},
			{name:"gmtModified",mapping:"gmtModified",convert:function(value){
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d');
				}
			}},
			{name:"gmtCreated",mapping:"gmtCreated",convert:function(value){
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d');
				}
			}},
			{name:"remark",mapping:"remark"},
			{name:"status",mapping:"status"}
			
//			{name:"gmtDate",mapping:"gmtDate", convert:function(value){
//				if(value!=null) {
//					return Ext.util.Format.date(new Date(value.time),
//					'Y-m-d');
//				} else {
//					return Ext.util.Format.date(new Date(),'Y-m-d');
//				}
//			}}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/crm/await/querySimpleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	}
	/*
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
	}*/
});