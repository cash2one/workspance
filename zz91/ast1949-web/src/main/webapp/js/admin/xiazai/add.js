Ext.namespace("ast.ast1949.admin.xiazai")

var _C = new function(){
	this.INFO_FORM="INFO_FORM";
}

ast.ast1949.admin.xiazai.InfoForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var _buttons = [{
			text:"保存",
			iconCls:"add",
			handler:this.saveForm,
			scope:this
		},{
			text:"关闭",
			iconCls:"delete",
			handler:function(){
				//关闭编辑产品的窗口
				window.close();
			}
		}];
			
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			autoScroll:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"98%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					xtype:"textfield",
					fieldLabel:"信息标题",
					itemCls:"required",
					allowBlank:false,
					value:Ext.get("title").dom.value,
					name:"title",
					id:"title",
					blankText : "信息标题不能为空"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[
					{
						xtype:"combotree",
						fieldLabel:"类别",
						allowBlank:false,
						itemCls:"required",
						emptyText: "请选择...",
						id : "label",
						name : "label",
						hiddenName : "code",
						hiddenId : "code",
						tree:new ast.ast1949.admin.xiazai.treePanel({
							rootData:"20051000"
						}),
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue!=""){
									var assist = Ext.getCmp("label");
									assist.getStore().reload({
										params:{
											"code":Ext.get("code").dom.value,
										}
									});
									assist.setValue("");
								}
							}
						}
					},{
						xtype:"combo",
						id:"type",
						name:"type",
						triggerAction : "all",
						forceSelection : true,
						fieldLabel:"文件类型",
						emptyText: "请选择文件类型(例如：pdf)...",
						displayField : "label",
						valueField : "code",
						store:new Ext.data.JsonStore( {
							root : "records",
							fields : [ "label", "code" ],
							autoLoad:true,
							url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode=20051001"
						})
					},{
						xtype:"combo",
						id:"language",
						name:"language",
						triggerAction : "all",
						forceSelection : true,
						fieldLabel:"语言",
						emptyText: "请选择语言(例如：简体中文)...",
						displayField : "label",
						valueField : "code",
						store:new Ext.data.JsonStore( {
							root : "records",
							fields : [ "label", "code" ],
							autoLoad:true,
							url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode=20051002"
						})
					}
//					,{
//					 	xtype:"checkbox",
//						fieldLabel:"是否审核",
//						boxLabel:"是",
//						name:"isChecked",
//						inputValue:"1",
//						checked:true
//					}
					]
			},{
					columnWidth: 0.5,
					layout: "form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
					{
						fieldLabel : '<b style="color:red">文件</b>',
						id:'fileUrl',
						name:'fileUrl',
						listeners:{
							"focus":function(field,e){
								com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/doXiazaiDocUpload.htm"
								var win = new com.zz91.sms.gateway.UploadWin({
									title:"上传文件",
								});
								com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
									Ext.Msg.alert(Context.MSG_TITLE,"文件上传成功！");
									var words = action.result.data.split("|")
									field.setValue(words[0]);
									Ext.get("ckeditor").dom.value= words[1];
									win.close();
								}
								win.show();
							}
						}
					},{
						fieldLabel : '封面图片',
						id:'picCover',
						name:'picCover',
						listeners:{
							"focus":function(field,e){
								com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/doXiazaiPicUpload.htm"
								var win = new com.zz91.sms.gateway.UploadWin({
									title:"上传图片",
								});
								com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
									Ext.Msg.alert(Context.MSG_TITLE,"图片上传成功！");
									field.setValue(action.result.data);
									win.close();
								}
								win.show();
							}
						}
					},{
						fieldLabel : '缩略图',
						id:'picThumb',
						name:'picThumb',
						listeners:{
							"focus":function(field,e){
								com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/doXiazaiPicUpload.htm"
								var win = new com.zz91.sms.gateway.UploadWin({
									title:"上传缩略图",
								});
								com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
									Ext.Msg.alert(Context.MSG_TITLE,"缩略图上传成功！");
									field.setValue(action.result.data);
									win.close();
								}
								win.show();
							}
						}
					}
					]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						xtype:"textfield",
						labelSeparator:""
					}
				},{
					columnWidth: 1,
					layout: "fit",
					defaults:{
						anchor:"98%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
					{
						xtype:"textarea",
						id:"ckeditor",
						name:"detail",
						emptyText: "请输入文档简介...",
						fieldLabel:"详细内容"
					}
					]
				}],
			buttons:_buttons
		};

		ast.ast1949.admin.xiazai.InfoForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/xiazai/doAdd.htm",
	saveForm:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过！");
		}
	},
	onSaveSuccess:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存成功！");
		Ext.MessageBox.confirm(Context.MSG_TITLE, '保存成功！是否关闭本页面?', function(_btn) {
			if(_btn != "no") {
				window.close();
			}
		});
	},
	onSaveFailure:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存失败！");
	},
	mystore:null,
	init:function(id){
		var _fields = [
				{name:"id",mapping:"id"},
				{name:"title",mapping:"title"},
				{name:"detail",mapping:"detail"},
				{name:"type",mapping:"type"},
				{name:"language",mapping:"language"},
				{name:"fileUrl",mapping:"fileUrl"},
				{name:"picCover",mapping:"picCover"},
				{name:"language",mapping:"language"},
				{name:"picThumb",mapping:"picThumb"}
			];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/xiazai/init.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						ast.ast1949.utils.Msg("","数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	},
	initCopy:function(id){
		var _fields = [
				{name:"id",mapping:"id"},
				{name:"title",mapping:"title"},
				{name:"detail",mapping:"detail"},
				{name:"type",mapping:"type"},
				{name:"language",mapping:"language"},
				{name:"fileUrl",mapping:"fileUrl"},
				{name:"picCover",mapping:"picCover"},
				{name:"language",mapping:"language"},
				{name:"picThumb",mapping:"picThumb"}
			];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/xiazai/init.htm",
			baseParams:{"id":id,"copy":1},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						ast.ast1949.utils.Msg("","数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	}
});