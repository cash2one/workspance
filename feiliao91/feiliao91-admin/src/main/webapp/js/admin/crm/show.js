Ext.namespace("ast.ast1949.admin.crm")

Ext.onReady(function(){
	var editForm = new ast.ast1949.admin.crm.EditForm();
	
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[editForm]
	});
	
	editForm.myLoadRecord();
});


ast.ast1949.admin.crm.EditForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var c={
			title:"编辑信息",
			id:"myform",
			layout:"column",
			autoScroll:true,
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeperator:""
				},
				items:[{
					xtype:"checkboxgroup",
					fieldLabel:"客户生意规模",
					//columns:[100,100],
					items:[{
						name:"businessSizeCode",
						inputValue:"1",
						boxLabel:"推荐为优质大客户（规模比较大的客户）"
					},{
						name:"businessSizeCode",
						inputValue:"2",
						boxLabel:"货源来自国外的客户"
					}]
				},{
					xtype:"checkboxgroup",
					fieldLabel:"与本站关系归类",
					items:[{
						name:"relationCode",
						inputValue:"1",
						boxLabel:"有做成过生意的"
					},{
						name:"relationCode",
						inputValue:"2",
						boxLabel:"有见面过的"
					},{
						name:"relationCode",
						inputValue:"3",
						boxLabel:"认同感比较强的"
					},{
						name:"relationCode",
						inputValue:"4",
						boxLabel:"可发展的客户"
					},{
						name:"relationCode",
						inputValue:"5",
						boxLabel:"来过公司的"
					},{
						name:"relationCode",
						inputValue:"6",
						boxLabel:"合作过的客户"
					},{
						name:"relationCode",
						inputValue:"7",
						boxLabel:"大客户"
					}]
				},new Ext.form.RadioGroup({
					fieldLabel:"高质客户归类",
					items:[{
						name:"highCompanyCode",
						inputValue:"1",
						boxLabel:"外网高级会员"
					},{
						name:"highCompanyCode",
						inputValue:"2",
						boxLabel:"外网广告会员"
					},{
						name:"highCompanyCode",
						inputValue:"3",
						boxLabel:"IN CALL"
					}]
				
				}),{
					xtype:"textarea",
					name:"hignCompanyRemark",
					fieldLabel:"本/外网活动明细"
				}]
			}],
			buttons:[{
				text:"确定",
				scope:this,
				handler:this.submitForm
			}]
		};
		
		ast.ast1949.admin.crm.EditForm.superclass.constructor.call(this,c);
	},
	submitUrl:Context.ROOT+Context.PATH + "/admin/crm/editSingleRecord.htm?companyId="+paramId,
	submitForm:function(btn){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.submitUrl,
				method:"post",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:MESSAGE.title,
				msg : MESSAGE.unValidate,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "更新成功",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "更新失败",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
	},
	myLoadRecord:function(){
		var _fields=["highCompanyCode","hignCompanyRemark","relationCode","businessSizeCode"];
		var form = this;
		var store = new Ext.data.JsonStore({
			root:"records",
			fields:_fields,
			url:Context.ROOT+Context.PATH+"/admin/crm/getSingleRecord.htm?id="+paramId,
			baseParams:{},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record =s.getAt(0);
					if(record!=null){
						//Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});
