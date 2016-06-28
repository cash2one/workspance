Ext.namespace("ast.ast1949.admin.price")

var _C = new function(){
	this.INFO_FORM="INFO_FORM";
}

ast.ast1949.admin.price.InfoForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var typeCombo = ast.ast1949.admin.price.priceCategoryComboTree({
			fieldLabel	: "主类别",
			id			: "combo-typeId",
			name		: "combo-typeId",
			hiddenName	: "typeId",
			hiddenId	: "typeId",
			emptyText	: "请选择...",
			readOnly	:true,
			allowBlank	:true,
//			itemCls 	:"required",
			width		:"100",
			el:"typeId-combo",
			
			rootData:Context.NEWS_CATEGORY.base
		});
		
		typeCombo.on("blur", function(field){
			
			var node=Ext.getCmp("priceCategory-tree").getSelectionModel().getSelectedNode();
			var pNode = node.parentNode;     
			var text = pNode.text;
			if(Ext.get("tags").dom.value==""||Ext.get("tags").dom.value==null){
				if(pNode.text=="所有类别"){
					Ext.get("tags").dom.value=Ext.get("combo-typeId").dom.value;
				}else{
					Ext.get("tags").dom.value=text+","+Ext.get("combo-typeId").dom.value;
				} 
				
			}
			
		});
		
		var assistTypeCombo = ast.ast1949.admin.price.priceCategoryComboTree({
			fieldLabel	: "辅助类别",
			id			: "combo-assistTypeId",
			name		: "combo-assistTypeId",
			hiddenName	: "assistTypeId",
			hiddenId	: "assistTypeId",
			emptyText	: "请选择...",

			//readOnly	:true,
			allowBlank	:true,
			width		:"100",
			el:"assistTypeId-combo",
			
			rootData:Context.NEWS_CATEGORY.assist
		});
		
		var btn = {
			id:"addBtn",
			text:"保存为上下版",
			iconCls:"add",
			handler:function(btn){
				this.saveUrl = Context.ROOT+Context.PATH + "/admin/price/addAndTemplate.htm";
				this.saveForm();
			},
			scope:this
		}
		
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
				items:[typeCombo,{
						xtype : "datefield",
						fieldLabel : "排序",
						name : "gmtOrder",
						allowBlank:false,
						format : 'Y-m-d H:i:s',
						value:new Date()
					 },{
					 	xtype:"checkbox",
						fieldLabel:"是否审核",
						boxLabel:"是",
						name:"isChecked",
						inputValue:"1",
						checked:true
					 }]
			},{
					columnWidth: 0.5,
					layout: "form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[assistTypeCombo,{
						xtype:"checkbox",
						id:"checkbox",
						fieldLabel:"辅类别取消",
						listeners:{
							"check":function(checkbox, checked){
								Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要取消此类别', function(_btn) {
									if(_btn != "no") {
										Ext.getCmp('combo-assistTypeId').clearValue();
									}
								});
							}
						}
					},{
							xtype : "datefield",
							fieldLabel : "发布时间",
							name : "gmtCreated",
							allowBlank:false,
							format : 'Y-m-d H:i:s',
							value:new Date()
						}
					]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"98%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
							xtype:"textfield",
							fieldLabel:"标签",
							allowBlank:true,
							name:"tags",
							id:"tags",
							value:Ext.get("tags").dom.value,
							//anchor:"98%"
					}]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"button",
						iconCls:'copy',
						text:"导出为excel表格",
							handler : function(form,action) {
							Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
								if(btn!="yes"){
									return ;
								}else{
									Ext.Ajax.request({
										url: window.open(Context.ROOT+Context.PATH+ "/admin/price/exportTable.htm?id="+Ext.get("priceId").dom.value)
									});
								}
							});
						}
					}]
				},{
					columnWidth: 1,
					layout: "fit",
					defaults:{
						anchor:"98%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
//					{
//						xtype:"textarea",
//						id:"ckeditor",
//						name:"content",
//						fieldLabel:"详细内容"
//					}
//					{
//						height:"600px",
//						fieldLabel:"详细内容",
//						xtype:"htmleditor"
//						,
//						plugins:[
//							new Ext.ux.form.HtmlEditor.Table(),
//							new Ext.ux.form.HtmlEditor.RemoveFormat()
//						]
//					}
						{
							xtype: "ckeditor",
							fieldLabel: "详细内容：",
							id: "content",
							name: "content",
							CKConfig: { //CKEditor的基本配置，详情配置请结合实际需要。
							    toolbar:"Full",
							    height : 300,
							    width: "97%"
							}					
						},{
							xtype:"button",
							text:"上传内容图片",
								handler : function(form,action) {
								var oEditor = CKEDITOR.instances.content;
								if ( CKEDITOR.currentInstance ){
									com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/doUpload.htm"
									var win = new com.zz91.sms.gateway.UploadWin({
										title:"上传内容图片",
									});
									com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
										Ext.Msg.alert(Context.MSG_TITLE,"图片上传成功！");
		    							oEditor.insertHtml( "<img src='"+Context.RESOURCE_URL+action.result.data+"' />" );
										win.close();
										}
									win.show();
								}else{
									Ext.Msg.alert(Context.MSG_TITLE,"请先将焦点放在要插入的位置");
								}
							}
						}
//						,{
//							xtype:"button",
//							iconCls:'copy',
//							text:"导出为excel表格",
//								handler : function(form,action) {
//								Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
//									if(btn!="yes"){
//										return ;
//									}else{
//										Ext.Ajax.request({
//											url: window.open(Context.ROOT+Context.PATH+ "/admin/price/exportTable.htm?id="+Ext.get("priceId").dom.value)
//										});
//									}
//								});
//							}
//						}
					]
				}],
			buttons:_buttons
		};

		ast.ast1949.admin.price.InfoForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/price/add.htm",
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
	addToPriceTemplate:function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:Context.ROOT+Context.PATH + "/admin/price/addToTemplate.htm",
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
	loadPrice:function(id){
		var _fields = [{name:"id",mapping:"price.id"},
		        {name:"title",mapping:"price.title"},
				{name:"indexTitle",mapping:"price.indexTitle"},
				{name:"goUrl",mapping:"price.goUrl"},
				{name:"gmtCreated",mapping:"price.gmtCreated",
					convert : function(value) {
						if(value!=null){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
						} else {
							return Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
						}
					}
				},
				{name:"gmtOrder",mapping:"price.gmtOrder",
					convert : function(value) {
						if(value!=null){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
						} else {
							return Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
						}
					}
				},
				{name:"assistTypeId",mapping:"assistTypeName"},
				{name:"typeId",mapping:"typeName"},
				{name:"baseTypeId",mapping:"baseTypeName"},
				{name:"typeId1",mapping:"price.typeId"},
				{name:"baseTypeId1",mapping:"price.baseTypeId"},
				{name:"assistTypeId1",mapping:"price.assistTypeId"},
				{name:"tags",mapping:"price.tags"},
				{name:"isIssue",mapping:"price.isIssue"},
				{name:"isChecked",mapping:"price.isChecked"},
				{name:"content",mapping:"price.content"}];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/price/getSingleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						ast.ast1949.utils.Msg("","数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						
						Ext.get("typeId").dom.value=record.get("typeId1");
						Ext.get("assistTypeId").dom.value=record.get("assistTypeId1");
					}
				}
			}
		})
	},
	loadPrices:function(pid){
		var _fields = [{name:"id",mapping:"price.id"},
		        {name:"title",mapping:"price.title"},
				{name:"indexTitle",mapping:"price.indexTitle"},
				{name:"goUrl",mapping:"price.goUrl"},
				{name:"gmtCreated",mapping:"price.gmtCreated",
					convert : function(value) {
						if(value!=null){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
						} else {
							return Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
						}
					}
				},
				{name:"gmtOrder",mapping:"price.gmtOrder",
					convert : function(value) {
						if(value!=null){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
						} else {
							return Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
						}
					}
				},
				{name:"assistTypeId",mapping:"assistTypeName"},
				{name:"typeId",mapping:"typeName"},
				{name:"baseTypeId",mapping:"baseTypeName"},
				{name:"typeId1",mapping:"price.typeId"},
				{name:"baseTypeId1",mapping:"price.baseTypeId"},
				{name:"assistTypeId1",mapping:"price.assistTypeId"},
				{name:"tags",mapping:"price.tags"},
				{name:"isIssue",mapping:"price.isIssue"},
				{name:"isChecked",mapping:"price.isChecked"},
				{name:"content",mapping:"price.content"}];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/price/getSingleRecords.htm",
			baseParams:{"id":pid},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						ast.ast1949.utils.Msg("","数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("typeId").dom.value=record.get("typeId1");
						Ext.get("assistTypeId").dom.value=record.get("assistTypeId1");
					}
				}
			}
		})
	}
	/*,
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","保存成功！");
	}
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","保存失败！");
	}*/
});