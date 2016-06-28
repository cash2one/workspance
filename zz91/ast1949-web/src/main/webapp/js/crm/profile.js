Ext.namespace("ast.ast1949.crm.profile");

ast.ast1949.crm.profile.ProfileForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var form = this;
		var c={
			frame : true,
			labelAlign : "right",
			labelWidth : 100,
			layout : "column",
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					name:"id"
				},{
					xtype:"hidden",
					name:"companyId",
					id:"profileCompanyId"
				},{
					name:"saleDetails",
					fieldLabel:"主营产品(供应)"
				},{
					name:"operatorName",
					fieldLabel:"网上操作员姓名"
				},{
					name:"operatorTel",
					fieldLabel:"电话"
				},{
					xtype:"textarea",
					name:"introduction",
					fieldLabel:"基本情况描述"
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					name:"buyDetails",
					fieldLabel:"主营产品(求购)"
				},{
					name:"operatorPhone",
					fieldLabel:"手机"
				},{
					name:"address",
					fieldLabel:"邮寄地址"
				},{
					xtype:"checkboxgroup",
					fieldLabel:"诚信档案资料",
					columns:3,
					items:[
					       {boxLabel:"身分证",name:"creditCard",inputValue:1,anchor:"100"},
					       {boxLabel:"营业执照",name:"creditLicense",inputValue:1,anchor:"100"},
					       {boxLabel:"税务登记证",name:"creditTax",inputValue:1,anchor:"100"}
					       ]
				}]
			},{
				columnWidth:1,
				xtype:"checkboxgroup",
				columns:4,
				items:[{boxLabel:"推荐为优质大客户(规模比较大的客户)",name:"tag1",anchor:"100",inputValue:1},
				       {boxLabel:"货源来自国外的客户",name:"tag2",anchor:"100",inputValue:1},
				       {boxLabel:"成长见证人 (更新为成长见证人时，请同时去更新供求信息)",name:"tag3",anchor:"100",inputValue:1},
				       {boxLabel:"有做成过生意的",name:"tag4",anchor:"100",inputValue:1},
				       {boxLabel:"有见面过的",name:"tag5",anchor:"100",inputValue:1},
				       {boxLabel:"认同感比较强的",name:"tag6",anchor:"100",inputValue:1},
				       {boxLabel:"可发展的客户",name:"tag7",anchor:"100",inputValue:1},
				       {boxLabel:"来过公司的",name:"tag8",anchor:"100",inputValue:1},
				       {boxLabel:"合作过的客户",name:"tag9",anchor:"100",inputValue:1},
				       {boxLabel:"大客户",name:"tag10",anchor:"100",inputValue:1}]
			}],
			buttonAlign:"right",
			buttons:[{
				text:"保存",
				iconCls:"item-true",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/cs/saveProfile.htm",
							method:"post",
							type:"json",
							success:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存成功！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							},
							failure:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		ast.ast1949.crm.profile.ProfileForm.superclass.constructor.call(this,c);
	},
	fields:["id","companyId","saleDetails","buyDetails","introduction","operatorName","operatorPhone",
	        "operatorTel","address","creditCard","creditLicense","creditTax",
	        "tag1","tag2","tag3","tag4","tag5","tag6","tag7","tag8","tag9","tag10"],
	loadProfile:function(companyId){
		var form=this;
		var store=new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+Context.PATH+"/crm/cs/queryProfile.htm?companyId="+companyId,
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						form.findById("profileCompanyId").setValue(companyId);
					}
				}
			}
		});
	}
});
