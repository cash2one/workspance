Ext.namespace("ast.ast1949.admin.products");

var EDITPRODUCTS = new function(){
	this.TEMPLATE_WIN = "templatewin";
	this.FORM_TAB="formtabpanel";
}

ast.ast1949.admin.products.productsForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		var form = this;
		var c = {
			labelAlign : "right",
			labelWidth : 80,
			frame:true,
			items:[{
				id:"editproducts",
				layout:"column",
				frame:true,
				autoScroll:true,
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
					},{
						xtype:"hidden",
						id:"membershipCode",
						name:"membershipCode"
					},{
						id : "title",
						name : "title",
						fieldLabel : "信息标题",
						allowBlank : false,
						itemCls:"required"
					}]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"combotree",
						fieldLabel:"供求类别",
						allowBlank:false,
						itemCls:"required",
						id : "categoryProductsMainLabel",
						name : "categoryProductsMainLabel",
						hiddenName : "categoryProductsMainCode",
						hiddenId : "categoryProductsMainCode",
						tree:new ast.ast1949.admin.categoryproducts.treePanel({
							rootData:"",
				    		isAssist:'0'
						}),
						listeners:{
							"change":function(field,newvalue,oldvalue){
								//TODO 需要制作一个联动效果,允许选择相应的辅助类别
//								alert(Ext.getCmp("categoryProductsMainLabel").getValue());
								if(newvalue!=""){
									var assist = Ext.getCmp("categoryProductsAssistLabel");
									assist.getStore().reload({
										params:{
											"parentCode":Ext.get("categoryProductsMainCode").dom.value,
											"isAssist":1
										}
									});
									assist.setValue("");
								}
							}
						}
					},{
						xtype:"combo",
						id:"productsTypeCode_combo",
						hiddenName:"productsTypeCode",
						triggerAction : "all",
						forceSelection : true,
						fieldLabel:"供求类型",
						displayField : "label",
						valueField : "code",
						store:new Ext.data.JsonStore( {
							root : "records",
							fields : [ "label", "code" ],
							autoLoad:true,
							url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["productsTypeCode"]
						})
					},{
						id : "tags",
						name : "tags",
						fieldLabel : "客户标签"
					},{
						xtype : "checkbox",
						id : "provideStatus",
						name : "provideStatus",
						boxLabel:"长期供货",
//						fieldLabel : "长期供货?",
						inputValue :1
					},{
						xtype : "numberfield",
						fieldLabel : "数量",
						id : "quantity",
						name : "quantity"
					},{
						fieldLabel : "外观",
						id : "appearance",
						name : "appearance"
					},{
						fieldLabel : "货源地",
						id : "source",
						name : "source"
					},{
						fieldLabel : "杂质(物)含量",
						id : "impurity",
						name : "impurity"
					},{
						xtype:"combo",
						id:"manufacture_combo",
						hiddenName:"manufacture",
						triggerAction : "all",
						forceSelection : true,
						fieldLabel:"加工说明",
						displayField : "label",
						valueField : "code",
						store:new Ext.data.JsonStore( {
							root : "records",
							fields : [ "label", "code" ],
							autoLoad:true,
							url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["manufactureCode"]
						})
					}]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"combo",
						id:"categoryProductsAssistLabel",
						hiddenId:"categoryProductsAssistCode",
						hiddenName:"categoryProductsAssistCode",
						triggerAction : "all",
						forceSelection : true,
						fieldLabel:"辅助类别",
						displayField : "text",
						valueField : "data",
						mode:'local',
						store:new Ext.data.JsonStore( {
							root : "records",
							fields : [ "text", "data" ],
							autoLoad:false,
							url : Context.ROOT+Context.PATH + "/admin/products/assistCombo.htm"
						})
					},{
						id : "account",
						name : "account",
						fieldLabel : "发布者(账户)",
						readOnly : true
					},{
						id : "tagsAdmin",
						name : "tagsAdmin",
						fieldLabel : "后台标签"
					},{
						fieldLabel : "供货/求购总量",
						id : "totalQuantity",
						name : "totalQuantity"
					},{
						fieldLabel:"计量单位",
						id:"quantityUnit",
						name:"quantityUnit"
					},{
						fieldLabel : "产品规格",
						id : "specification",
						name : "specification"
					},{
						fieldLabel : "颜色",
						id : "color",
						name : "color"
					},{
						fieldLabel : "来源产品",
						id : "origin",
						name : "origin"
					},{
						fieldLabel : "此废料可用于",
						id : "useful",
						name : "useful"
					}
//					,{
//						id : "checkTime",
//						name : "checkTimeStr",
//						fieldLabel : "审核时间",
//						readOnly : true
//					}
					]
				},{
					xtype:"tabpanel",
					id:EDITPRODUCTS.FORM_TAB,
					columnWidth:1,
					activeTab:0,
					border:true,
					defaults:{
						height:180
					},
					items:[
						
						new Ext.Panel({
							layout:"fit",
							title:"产品描述",
//							tbar:["->","如果用户没有填写产品描述，您可以从这里选择描述的内容帮助用户补全",'-',{
//								text:"供求模板",
//								iconCls:"view",
//								handler:function(btn){
//									ast.ast1949.admin.products.DescriptionWin("10341000","details");
//								}
//							}],
							items:[{
								xtype:"htmleditor",
								name : "details",
								id : "details",
								height:154,
								width:"100%",
								listeners:{
									"render":function(cmp){
										cmp.getToolbar().add('->',{
											tooltip:"如果用户没有填写产品描述，您可以从这里选择描述的内容帮助用户补全",
											iconCls:"item-open",
											cls : "x-btn-icon",
//											text:"供求模板",
											handler:function(btn){
												ast.ast1949.admin.products.DescriptionWin("10341000","details");
											}
										});
									}
								}
							}]
						}),
						new Ext.Panel({
							title:"产品报价",
							layout:"column",
							autoScroll:true,
							tbar:[{
								text:"发布成企业报价",
								iconCls:"item-add",
								handler:function(btn){
									Ext.Ajax.request({
										url:Context.ROOT+Context.PATH+"/admin/products/saveCompanyPrice.htm",
										params:{
											"productId":productId
										},
										success:function(response,opt){
											var obj = Ext.decode(response.responseText);
											if(obj.success){
												btn.setText("查看企业报价");
												btn.setIconClass("view");
												btn.setHandler(function(){
													window.open("http://price.zz91.com/companyprice/priceDetails.htm?productId="+productId);
												});
												Ext.MessageBox.show({
													title : Context.MSG_TITLE,
													msg : "已被发布成企业报价",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO
												});
											}else{
												Ext.MessageBox.show({
													title : Context.MSG_TITLE,
													msg : "发生错误,信息没有被审核(公司没有填写地区或该供求已经推荐过等等)",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO
												});
											}
										},
										failure:function(response,opt){
											Ext.MessageBox.show({
												title : Context.MSG_TITLE,
												msg : "发生错误,信息没有被审核",
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.INFO
											});
										}
									});
								}
							}],
							items:[{
								layout:"form",
								columnWidth:.5,
								height:"auto",
								defaults:{
									anchor:"95%",
									xtype:"textfield",
									labelSeparator:""
								},
								items:[{
									xtype : "numberfield",
									fieldLabel : "产品价格",
									id : "price",
									name : "price"
								},{
									xtype : "textfield",
									fieldLabel : "单位",
									id : "priceUnit",
									name : "priceUnit"
								},{
									xtype:"numberfield",
									fieldLabel:"价格范围从",
									id:"minPrice",
									name:"minPrice"
								},{
									xtype:"numberfield",
									fieldLabel:"到",
									id:"maxPrice",
									name:"maxPrice"
								}]
							}]
						}),
						new Ext.Panel({
							title:"审核",
							iconCls:"item-info",
							layout:"fit",
							tbar:[{
								text:"通过",
								iconCls:"item-true",
								handler:function(btn){
									ast.ast1949.admin.products.auditCheckStatus("1");
								}
							},{
								text:"不通过",
								iconCls:"item-false",
								handler:function(btn){
									ast.ast1949.admin.products.auditCheckStatus("2");
								}
							},{
								text:"不通过并显示在sb频道",
								scope:this,
								iconCls:"item-false",
								handler:function(btn){
									this.saveFormForRub();
								}
							},"->","如果审核不通过，您可以从这里选择退回原因","-",{
								text:"审核模板",
								iconCls:"item-open",
								handler:function(btn){
									ast.ast1949.admin.products.DescriptionWin("10341001","unpassReason");
								}
							}],
							items:[{
								xtype:"textarea",
								name : "unpassReason",
								id : "unpassReason",
								height:154,
								width:"100%"
							}]
						}),
						new ast.ast1949.admin.productsPic.imageView({
							layout:"fit",
							title:"相关图片"
						}),
						new Ext.Panel({title:"询盘"})
					]
				}]
			}],
			buttons:[{
				id : "onlysave",
				text : "保存违规信息",
				scope:this,
				hidden:this.newProductFlag,
				handler:this.saveForm
			}
			,{
				id : "saveandrub",
				iconCls:"item-false",
				text : "移出违规页面",
				scope:this,
				hidden:this.newProductFlag,
				handler:function(btn){
					ast.ast1949.admin.products.updateForRub(1);
					window.close();
				}
			}
			]
		};

		ast.ast1949.admin.products.productsForm.superclass.constructor.call(this,c);
	},
	newProductFlag:false,
	saveUrl:Context.ROOT+Context.PATH+"/admin/products/updateProductRub.htm",
	saveNewProduct:function(btn){
//		alert(this.inquiryData.id)
		
		var _url = this.saveUrl;
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : _url,
				method : "post",
				params:{"inquiryId":this.inquiryData.id},
				waitMsg:Context.SAVEMASK.msg,
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "信息已被保存,您可以关闭窗口了",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
						
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
	saveForm:function(){
		var _url = this.saveUrl;
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : _url,
				method : "post",
				waitMsg:Context.SAVEMASK.msg,
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "信息已被保存,您可以关闭窗口了",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
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
	loadOneRecord:function(id){
		var reader = [{
			name : "id",
			mapping : "products.id"
		}, {
			name : "account",
			mapping : "products.account"
		},// 发布帐号名
		{
			name : "productsTypeCode",
			mapping : "products.productsTypeCode"
		},// 供求类型
		{
			name : "categoryProductsMainCode",
			mapping : "products.categoryProductsMainCode"
		},// 主类别
		{
			name : "categoryProductsMainLabel",
			mapping : "categoryProductsMainLabel"
		},// 主类别名称
		{
			name : "categoryProductsAssistCode",
			mapping : "products.categoryProductsAssistCode"
		},// 辅助类别
		{
			name : "categoryProductsAssistLabel",
			mapping : "categoryProductsAssistLabel"
		},// 辅助类别名称
		{
			name : "sourceTypeCode",
			mapping : "products.sourceTypeCode"
		},// 信息来源
		{
			name : "title",
			mapping : "products.title"
		},// 标题
		{
			name : "details",
			mapping : "products.details"
		},// 详细描述
		{
			name : "checkStatus",
			mapping : "products.checkStatus"
		},// 审核
		// 审核未通过原因
		{
			name : "unpassReason",
			mapping : "products.unpassReason"
		}, {
			name : "uncheckedCheckStatus",
			mapping : "products.uncheckedCheckStatus"
		},// 审核未审核
		{
			name : "provideStatus",
			mapping : "products.provideStatus"
		},// 长期供货
		{
			name : "totalQuantity",
			mapping : "products.totalQuantity"
		},// 供货总量
		{
			name : "isShowInPrice",
			mapping : "products.isShowInPrice"
		},// 是否显示在企业报价
		{
			name : "price",
			mapping : "products.price"
		},// 产品价格
		{
			name:"priceUnit",
			mapping:"products.priceUnit"
		},//价格单位
		{
			name : "quantityUnit",
			mapping : "products.quantityUnit"
		},// 计量单位
		{
			name : "quantity",
			mapping : "products.quantity"
		},// 数量
		{
			name : "specification",
			mapping : "products.specification"
		},// 产品规格
		{
			name : "source",
			mapping : "products.source"
		},// 货源地
		{
			name : "appearance",
			mapping : "products.appearance"
		},// 外观
		// {name:"",mapping:""},//
		{
			name : "origin",
			mapping : "products.origin"
		},// 来源产品
		{
			name : "impurity",
			mapping : "products.impurity"
		},// 杂质含量
		{
			name : "color",
			mapping : "products.color"
		},// 颜色
		{
			name : "useful",
			mapping : "products.useful"
		},// 用途
		{
			name : "isPause",
			mapping : "products.isPause"
		},// 暂停发布
		{
			name : "manufacture",
			mapping : "products.manufacture"
		},// 加工说明
		{
		  	name: "tagsAdmin",
		  	mapping :"products.tagsAdmin"
		}, //客户标签
		{
		  	name: "tags",
			mapping :"products.tags"
		},//后台标签
		{
			name:"checkTime",
			mapping:"products.checkTime"
		},{
			name:"realTime",
			mapping:"products.realTime"
		},{
			name:"minPrice",
			mapping:"products.minPrice"
		},{
			name:"maxPrice",
			mapping:"products.maxPrice"
		},{
			name:"companyId",
			mapping:"products.companyId"
		},{
			name:"membershipCode",
			mapping:"company.membershipCode"
		}];
		
		Ext.MessageBox.show({
			title : Context.MSG_TITLE,
			msg : Context.LOADMASK.msg,
			icon : Ext.MessageBox.INFO,
			closable:false,
			width:350,
			wait:true,
			waitConfig: {interval:300}
		});
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields : reader,
			url : Context.ROOT + Context.PATH + "/admin/products/initForRubEdit.htm?id=" + id,
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
						form.getForm().loadRecord(record);
						
						// 初始化主类别
						form.findById("categoryProductsMainLabel").setValue({
							text:record.get("categoryProductsMainLabel"),
							attributes:{data:record.get("categoryProductsMainCode")}
						});
						
						// 初始化审核状态
						var checktab=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(2);
						var checkstatus="0";
						if(record.get("membershipCode")!="10051000" && record.get("checkStatus")=="1"){
							checkstatus=record.get("uncheckedCheckStatus");
						}else{
							checkstatus=record.get("checkStatus");
						}
						
						if(checkstatus=="1"){
							checktab.setIconClass("item-true");
						}
						
						if(checkstatus=="2"){
							checktab.setIconClass("item-false");
						}
						
						if(record.get("isShowInPrice")!="0"){
							var tbar=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(1).getTopToolbar()[0];
							tbar.text="查看企业报价";
							tbar.iconCls="view";
							tbar.handler=function(btn){
								window.open("http://price.zz91.com/companyprice/priceDetails.htm?productId="+productId);
							}
						}
						
						Ext.MessageBox.hide();
						
					}
					
				}
			}
		});
	},
	loadImage:function(id){
		// TODO 载入图片信息
		var imageview = this.findById("images-view");
		if(id>0){
			imageview.imageStore.load({
				params:{"productId":id}
			});
//			imageview.deleteImageButton.enable();
		}
	}
});


ast.ast1949.admin.products.DescriptionWin = function(code,name){
	var grid = new ast.ast1949.admin.products.descriptionGrid({
		queryUrl:Context.ROOT + Context.PATH + "/admin/descriptiontemplate/query.htm?templateCode="+code,
		region:"center",
		height:300,
		setSelectedTemplate:function(btn){
			var sels = grid.getSelections();
			if (sels.length > 0) {
				Ext.getCmp(name).setValue(Ext.getCmp(name).getValue() + sels[0].get("content"));
				Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN).close();
			}
		},
		listeners:{
			"rowdblclick":function(g,rowindex,e){
				var sels = grid.getSelections();
				Ext.getCmp(name).setValue(Ext.getCmp(name).getValue() + sels[0].get("content"));
				Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN).close();
			}
		}
	});
//
//	grid.on("rowdblclick",function(g,rowindex,e){
//
//	});

//	var win = Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN+code);
//	if(typeof(win)=="undefined"){
	win = new Ext.Window({
		id:EDITPRODUCTS.TEMPLATE_WIN,
		title:"选择模板",
		width:600,
		autoHeight:true,
		modal:true,
		items:[grid]
	});
//	}
	win.show();
}

/**
 * 描述模板使用的grid
 * templateCode:模板类别
 * */
ast.ast1949.admin.products.descriptionGrid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT + Context.PATH + "/admin/descriptiontemplate/query.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var _fields = this.fieldsRecord;
		var _url = this.queryUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});

		var _sm=new Ext.grid.CheckboxSelectionModel();
		var _cm = new Ext.grid.ColumnModel([_sm, {
			header : "描述模板内容",
			width : 300,
			sortable : false,
			dataIndex : "content"
		}, {
			header : "模板类别",
			width : 100,
			sortable : false,
			dataIndex : "templateName"
		}]);

		var tbar = [{
			text : '选择',
			tooltip : '选择一个描述模板',
			iconCls : 'add',
			handler : function(btn) {
//				var g = Ext.getCmp("descriptionTemplateGrid");
//				var sels = g.getSelections();
//				if (sels.length != 1) {
//					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
//				} else {
//					Ext.get("details").dom.value = Ext.get("details").dom.value + sels[0].get("content");
//					Ext.getCmp("descriptionTemplateWin").close();
//				}
			}
		}]

		var c={
//			viewConfig:{
//				autoFill:true
//			},
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				text : '选择',
				tooltip : '选择一个描述模板',
				iconCls : 'add',
				handler:this.setSelectedTemplate
			}]
		};

		ast.ast1949.admin.products.descriptionGrid.superclass.constructor.call(this,c);
	},
	fieldsRecord:[{
		name : "id",
		mapping : "descriptionTemplateDO",
		convert : function(v) {
			return v.id;
		}
	}, {
		name : "content",
		mapping : "descriptionTemplateDO",
		convert : function(v) {
			return v.content;
		}
	}, {
		name : "gmtCreated",
		mapping : "descriptionTemplateDO",
		convert : function(v) {
			return v.gmtCreated;
		}
	}, {
		name : "gmtModified",
		mapping : "descriptionTemplateDO",
		convert : function(v) {
			return v.gmtModified;
		}
	}, "templateName"]
});

ast.ast1949.admin.products.auditCheckStatus = function(checkStatus){
	var membershipCode=Ext.getCmp("membershipCode").getValue();
//	alert(membershipCode);
//	return false;
	Ext.Ajax.request({
		url:Context.ROOT+Context.PATH+"/admin/products/updateCheckStatus.htm",
		params:{
			"checkStatus":checkStatus,
			"productId":productId,
			"unpassReason":Ext.getCmp("unpassReason").getValue(),
			"membershipCode":membershipCode,
			"companyId":Ext.getCmp("companyId").getValue()
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				var checktab=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(2);
				if(checkStatus=="1"){
					checktab.setIconClass("item-true");
				}
				
				if(checkStatus=="2"){
					checktab.setIconClass("item-false");
				}
				
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "信息已审核",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			}else{
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "发生错误,信息没有被审核",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			}
		},
		failure:function(response,opt){
			Ext.MessageBox.show({
				title : Context.MSG_TITLE,
				msg : "发生错误,信息没有被审核",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	});
}

ast.ast1949.admin.products.updateForRub = function(status){
	if(status==1){
		url = "/admin/products/removeProductToRub.htm";
	}
	Ext.Ajax.request({
		url:Context.ROOT+Context.PATH+url,
		params:{
			"productId":productId
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			ast.ast1949.utils.Msg("",obj.data)
		},
		failure:function(response,opt){
			var obj = Ext.decode(response.responseText);
			ast.ast1949.utils.Msg("","操作失败")
		}
	});
}

ast.ast1949.admin.products.updateBtnToViewCompanyPrice = function(){
	var tbar=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(1).getTopToolbar();
	alert(tbar)
//	var tbar=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(1).getTopToolbar()[0];
//	alert(tbar)
//	tbar.text="查看企业报价";
//	tbar.iconCls="view";
//	tbar.handler=function(btn){
//		window.open(Context.FRONT_SERVER+"/companyprice/priceDetails.htm?productId="+productId);
//	}
}