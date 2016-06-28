Ext.namespace("com.zz91.trade.editsupply");

var EDITSUPPLY = new function(){
	this.TEMPLATE_WIN = "templatewin";
	this.SUPPLY_FORM="supplyform";
	this.EDIT_FORM="editform";
	this.SUPPLY_GRID="supplygrid";
	this.PROPERTY_FORM="propertyform";
	this.PROPERTY_WIN="propertywin";
	this.FORM_TAB="formtabpanel";
}
com.zz91.trade.FORMFIELD=[
	{name:"id",mapping:"supply.id"},
	{name:"uid",mapping:"supply.uid"},
	{name:"cid",mapping:"supply.cid"},
	{name:"title",mapping:"supply.title"},
	{name:"details",mapping:"supply.details"},
	{name:"categoryCode",mapping:"supply.categoryCode"},
	{name:"totalNum",mapping:"supply.totalNum"},
	{name:"totalUnits",mapping:"supply.totalUnits"},
	{name:"priceNum",mapping:"supply.priceNum"},
	{name:"priceUnits",mapping:"supply.priceUnits"},
	{name:"priceFrom",mapping:"supply.priceFrom"},
	{name:"priceTo",mapping:"supply.priceTo"},
	{name:"useTo",mapping:"supply.useTo"},
	{name:"usedProduct",mapping:"supply.usedProduct"},
	{name:"tags",mapping:"supply.tags"},
	{name:"tagsSys",mapping:"supply.tagsSys"},
	{name:"detailsQuery",mapping:"supply.detailsQuery"},
	{name:"propertyQuery",mapping:"supply.propertyQuery"},
	{name:"messageCount",mapping:"supply.messageCount"},
	{name:"viewCount",mapping:"supply.viewCount"},
	{name:"favoriteCount",mapping:"supply.favoriteCount"},
	{name:"plusCount",mapping:"supply.plusCount"},
	{name:"htmlPath",mapping:"supply.htmlPath"},
	{name:"integrity",mapping:"supply.integrity"},
	{name:"gmtPublish",mapping:"supply.gmtPublish"},
	{name:"gmtRefresh",mapping:"supply.gmtRefresh"},
	{name:"validDays",mapping:"supply.validDays"},
	{name:"delStatus",mapping:"supply.delStatus"},
	{name:"pauseStatus",mapping:"supply.pauseStatus"},
	{name:"checkStatus",mapping:"supply.checkStatus"},
	{name:"checkAdmin",mapping:"supply.checkAdmin"},
	{name:"checkRefuse",mapping:"supply.checkRefuse"},
	{name:"gmtCheck",mapping:"supply.gmtCheck"},
	{name:"gmtCreated",mapping:"supply.gmtCreated"},
	{name:"gmtModified",mapping:"supply.gmtModified"},
	{name:"gmtExpired",mapping:"supply.gmtExpired"},
	{name:"areaCode",mapping:"supply.areaCode"},
	{name:"provinceCode",mapping:"supply.provinceCode"},
	{name:"provinceName",mapping:"provinceName"},
	{name:"areaName",mapping:"areaName"},
	{name:"infoComeFrom",mapping:"supply.infoComeFrom"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"operator",mapping:"logInfo.operator"},
	{name:"operation",mapping:"logInfo.operation"},
	{name:"gmtLogTime",mapping:"logInfo.time"}
	];

com.zz91.trade.PROPERTYFIELD=[
	{name:"propertyId",mapping:"propertyId"},
	{name:"name",mapping:"propertyName"},
	{name:"propertyValue",mapping:"propertyValue"},
	{name:"supplyId",mapping:"supplyId"},
	{name:"id",mapping:"vId"}
];

com.zz91.trade.editsupply.supplyForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		var form = this;
		var c = {
			labelAlign : "right",
			labelWidth : 100,
			frame:true,
			items:[{
				id:"editsupply",
				layout:"column",
				frame:true,
				autoScroll:true,
				items:[{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"id",
						name:"id"
					},{
						xtype:"hidden",
						id:"cid",
						name:"cid"
					},{
						xtype:"hidden",
						id:"uid",
						name:"uid"
					},{
						xtype:"hidden",
						name:"areaCode",
						id:"areaCode"
					},{
						xtype:"hidden",
						name:"categoryCode",
						id:"categoryCode"
					},{
						id : "title",
						name : "title",
						fieldLabel : "信息标题",
						allowBlank : false,
						itemCls:"required"
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
						xtype : "numberfield",
						fieldLabel:"供货总量",
						id:"totalNum",
						name:"totalNum"
					},{
						fieldLabel:"信息标签",
						name:"tags"
					},{
						fieldLabel:"标签信息",
						name:"tagsSys"
					},{
						fieldLabel:"供货地区",
						name:"areaName",
						id:"areaName",
						readOnly:true,
						listeners:{
							"focus":function(field){
								var initValue=Ext.getCmp("areaCode").getValue();
								com.zz91.trade.editsupply.choiceAreaCategory(initValue,function(node,e){
									Ext.getCmp("areaName").setValue(node.text);
									Ext.getCmp("areaCode").setValue(node.attributes["data"]);
									node.getOwnerTree().ownerCt.close();
								})
							}
						}
					},{
						xtype : "numberfield",
						fieldLabel:"产品价格",
						name:"priceNum"
					},{
						xtype : "numberfield",
						fieldLabel:"报价范围(小)",
						name:"priceFrom"
					},{
						xtype : "numberfield",
						fieldLabel:"报价范围(大)",
						name:"priceTo"
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
						fieldLabel:"类别",
						name:"categoryName",
						id:"categoryName",
						itemCls:"required",
						allowBlank:false,
						readOnly:true,
						listeners:{
							"focus":function(field){
								var initValue=Ext.getCmp("categoryCode").getValue();
								com.zz91.trade.editsupply.choiceCategory(initValue,function(node,e){
									Ext.getCmp("categoryName").setValue(node.text);
									Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
									node.getOwnerTree().ownerCt.close();
								})
							}
						}
					},{
						fieldLabel:"数量单位",
						name:"totalUnits"
					},{
						fieldLabel:"用途",
						name:"useTo"
					}
//					,{
//						fieldLabel:"审核状态",
//						xtype:"combo",
//						mode:"local",
//						triggerAction:"all",
//						hiddenName:"checkStatus",
//						hiddenId:"checkStatus",
//						readOnly:true,
//						store:[
//						["0","未审核"],
//						["1","审核通过"],
//						["2","审核不通过"]
//						]
//					}
					,{
                        fieldLabel:"新旧",
                        xtype:"combo",
                        mode:"local",
                        value:"0",
                        triggerAction:"all",
                        hiddenName:"usedProduct",
                        hiddenId:"usedProduct",
                        store:[
                        ["0","全新"],
                        ["1","二手"]
                        ]
                    }
					,{
						fieldLabel:"报价单位",
						name:"priceUnits"
					},{
						fieldLabel:"有效天数",
						xtype:"combo",
						value:"0",
						hiddenName:"validDays",
						hiddenId:"validDays",
						mode:"local",
						itemCls:"required",
						allowBlank:false,
						triggerAction:"all",
						store:[
							["10","10天"],
							["20","20天"],
							["30","一个月"],
							["90","三个月"],
							["180","六个月"],
							["0","长期有效"]
						]
					},{
                        fieldLabel:"信息完整度评分",
                        value:"8",
                        name:"integrity",
                        allowBlank : false,
                        itemCls:"required"
                    },{
                        xtype : "hidden",
                        fieldLabel:"审核账号",
                        id:"checkAdmin",
                        name:"checkAdmin"
                    }]
				},{	
						columnWidth:1,
						layout:"form",
						defaults:{
							anchor:"99%",
							labelSeparator:""
						},
						items:[{
							xtype: "ckeditor",
							fieldLabel: "产品描述(富文本)",
							id: "details",
							name: "details",
							CKConfig: { //CKEditor的基本配置，详情配置请结合实际需要。
								toolbar:"Full",
								width: "99%"
							}
						},{
							xtype:"hidden",
							fieldLabel:"状态",
							name:"isStatus",
							id:"isStatus"
						},{
							xtype: "hidden",
							name: "detailS",
							id : "detailS"
						},{
							xtype:"tabpanel",
							id:EDITSUPPLY.FORM_TAB,
							columnWidth:1,
							activeTab:0,
							border:true,
							defaults:{
								height:180,
							},
							items:[
									new Ext.Panel({
										title:"审核",
										iconCls:"pause16",
										layout:"fit",
										tbar:[{
											text:"通过",
											iconCls:"next16",
											handler:function(btn){
											    com.zz91.trade.editsupply.unpasswinSupply(1,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
											}
										},{
											text:"不通过",
											iconCls:"delete16",
											handler:function(btn){
											   com.zz91.trade.editsupply.unpasswinSupply(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
											}
										},{
											text:"审核模板",
											iconCls:"foldermove16",
											handler:function(btn){
										 	      com.zz91.trade.editsupply.unpasswin(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
											}
										}],
										items:[{
											xtype:"textarea",
											name : "checkRefuse",
											id : "checkRefuse",
											height:154,
											width:"100%"
										}]
									}),
								]
						}]
				}]
			}],
			buttons:[{
				id : "save",
				text : "保存",
				iconCls:"saveas16",
				scope:this,
				hidden:this.newProductFlag,
				handler:this.saveForm
			},{
                id : "saveandcheck",
                iconCls:"play16",
                text : "保存并审核通过",
                scope:this,
                hidden:this.newProductFlag,
                handler:function(btn){
                    this.saveForm();
                    if(Ext.getCmp('details').getValue().length<=5000){
                      com.zz91.trade.editsupply.unpasswinSupply(1,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
                    }
                }
            },{
					id : "payrecommend",
					text : "付费推荐",
					iconCls:"fav16"
			}
//			,{
//				id : "nopass",
//				iconCls:"pause16",
//				text : "不通过",
//				scope:this,
//				hidden:this.newProductFlag,
//				handler:function(btn){
//				   com.zz91.trade.editsupply.unpasswinSupply(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
//			   }
//			}
			]
		};
		com.zz91.trade.editsupply.supplyForm.superclass.constructor.call(this,c);
	},
	newProductFlag:false,
	saveUrl:Context.ROOT+"/trade/tradesupply/updateTradeSupply.htm",
	saveForm:function(){
		if (Ext.getCmp('details').getValue().length>5000){
			alert("字符不得超过5000");
			return null;
		}"1","二手"
		var _url = this.saveUrl;
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : _url,
				method : "post",
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						/*Ext.getCmp('detailsQuery').setValue(res.data);*/
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
	loadRecord:function(id){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.trade.FORMFIELD,
			url:Context.ROOT+"/trade/tradesupply/queryOneSupply.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						var checktab=Ext.getCmp(EDITSUPPLY.FORM_TAB).getComponent(0);
						var checkstatus=record.get("checkStatus");
						if(checkstatus=="1"){
							checktab.setIconClass("next16");
						}
						
						if(checkstatus=="2"){
							checktab.setIconClass("delete16");
						}
						
						if(record.get("gmtLogTime")){
							
							form.findById("gmtLogTime").setValue(Ext.util.Format.date(new Date(record.get("gmtLogTime").time), 'Y-m-d H:i:s'));
						}
//						form.findById("gmtPublishStr").setValue(Ext.util.Format.date(new Date(record.get("gmtPublish").time), 'Y-m-d H:i:s'));
//						form.findById("gmtRefreshStr").setValue(Ext.util.Format.date(new Date(record.get("gmtRefresh").time), 'Y-m-d H:i:s'));
//						form.findById("gmtExpiredStr").setValue(Ext.util.Format.date(new Date(record.get("gmtExpired").time), 'Y-m-d H:i:s'));
					}
				}
			}
		});
	},
	saveSupplyForm:function(){
		if (Ext.getCmp('details').getValue().length>5000){
			alert("字符不得超过5000");
			return null;
		}"1","二手"
		var _url = this.saveUrl;
		Ext.getCmp("isStatus").setValue(1);
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : _url,
				method : "post",
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "审核信息已被保存,您可以关闭窗口了",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					} else {
						Ext.MessageBox.show({
							title : Context.MSG_TITLE,
							msg : "发生错误,审核信息没有被保存",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					}
				},
				failure : function(_form,_action){
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "发生错误,审核信息没有被保存",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
		com.zz91.trade.editsupply.unpasswinSupply(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
	},
	

});

com.zz91.trade.editsupply.choiceAreaCategory=function(init,callback){
	
	var tree = new com.zz91.ep.common.area.Tree({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择地区类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
}
//供应信息对应的专业属性表格
com.zz91.trade.editsupply.listTradePropertyValueGird = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		var grid = this;
		config=config||{};
		Ext.apply(this,config);
		
		var _url = Context.ROOT+"/trade/tradesupply/queryTradePropertyValue.htm";
		
		var _store = new Ext.data.JsonStore({
			fields:com.zz91.trade.PROPERTYFIELD,
			url: _url,
			autoLoad:true
		});

		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect:true
		});

		var _cm = new Ext.grid.ColumnModel([
			_sm,{
				id:"edit-name",
				header:"属性名称",
				dataIndex:"name",
				width:200,
				sortable:true
			},{
				id:"edit-value",
				header:"属性值",
				dataIndex:"propertyValue",
				width:200,
				sortable:true,
				editor:new Ext.form.TextField({  
				allowBlank:false  
				}) 
			},{
				hidden:true,
				header:"supplyId",
				dataIndex:"supplyId",
				sortable:true
			},{
				hidden:true,
				header:"propertyId",
				dataIndex:"propertyId",
				sortable:true
			},{
				hidden:true,
				header:"id",
				dataIndex:"id"
			}
		]);
		
		var c={
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
				tbar: [{	
						text: "编辑",
						iconCls: "edit16",
						handler: function(){
							var row = Ext.getCmp(EDITSUPPLY.SUPPLY_GRID);
							var record=row.getSelectionModel().getSelected();
							if(typeof(record) == "undefined"){
								Ext.MessageBox.show({
									title: Context.MSG_TITLE,
									msg : "请至少选定一条记录",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.WARNING
								});
								return ;
							}
							com.zz91.trade.editsupply.editPropertyWin(record);
						}
					},{
						text: "刷新",
						iconCls: "refresh16",
						handler: function(){
							grid.store.reload();
						}
					}
				]
			};
		com.zz91.trade.editsupply.listTradePropertyValueGird.superclass.constructor.call(this,c);
	}

});



com.zz91.trade.editsupply.auditCheckStatus = function(checkStatus){
	Ext.Ajax.request({
		url:Context.ROOT+"/trade/tradesupply/updateCheckStatus.htm",
		params:{
			"checkStatus":checkStatus,
			"id":Ext.getCmp("id").getValue()
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "信息已审核通过,刷新后查看审核状态",
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


com.zz91.trade.editsupply.unpasswinSupply = function(checkStatus,checkRefuse,id){
	Ext.Ajax.request({
		url:Context.ROOT+"/trade/tradesupply/unPassCheckStatus.htm",
		params:{
			"checkStatus":checkStatus,
			"id":id,
			"checkRefuse":checkRefuse
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				var checktab=Ext.getCmp(EDITSUPPLY.FORM_TAB).getComponent(0);
				if(checkStatus=="2"){
					checktab.setIconClass("delete16");
				}
				if(checkStatus=="1"){
					checktab.setIconClass("next16");
				}
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "操作成功,审核信息状态被修改",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			}else{
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "操作失败,审核信息状态没有被修改",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			}
		},
		failure:function(response,opt){
			Ext.MessageBox.show({
				title : Context.MSG_TITLE,
				msg : "操作失败,审核信息状态没有被修改",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	});
}



com.zz91.trade.editsupply.unpasswin=function(checkStatus,checkRefuse,id){
	var grid = new com.zz91.trade.editsupply.descriptionGrid({
		//id:EDITSUPPLY.EDIT_FORM,
		queryUrl:Context.ROOT+"/trade/descriptiontemplate/query.htm",
		height:350,
		region:"center",
		setSelectedTemplate:function(btn){
		    var sels = grid.getSelectionModel().getSelections();
		    if (sels.length > 0) {
				Ext.getCmp("checkRefuse").setValue(Ext.getCmp("checkRefuse").getValue()+sels[0].get("content"));
				Ext.getCmp("isStatus").setValue(checkStatus);
				Ext.getCmp(EDITSUPPLY.TEMPLATE_WIN).close();
			}
	    },
	    listeners:{
			"rowdblclick":function(g,rowindex,e){
				var sels = grid.getSelectionModel().getSelections();
				Ext.getCmp("checkRefuse").setValue(Ext.getCmp("checkRefuse").getValue() + sels[0].get("content"));
				Ext.getCmp(EDITSUPPLY.TEMPLATE_WIN).close();
			}
		}
	});
	
	var win = new Ext.Window({
		id:EDITSUPPLY.TEMPLATE_WIN,
		title:"审核不通过原因",
		width:500,
		height:500,
		autoHeight:true,
		modal:true,
		items:[grid]
	});
	
	win.show();
}

com.zz91.trade.editsupply.descriptionGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT+"/trade/descriptiontemplate/query.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _fields = this.fieldsRecord;
		var _url = this.queryUrl;
		var _store = new Ext.data.JsonStore({
		    root:"records",
			//totalProperty:"tradeDtoList",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel();
		var _cm = new Ext.grid.ColumnModel([_sm, {
			header : "审核不通过原因",
			width : 480,
			sortable : false,
			dataIndex : "content"
		}]);
		
		var c={
			store:_store,
			sm:_sm,
			cm:_cm,
			buttons:[{
				text:"确定",
				scope:this,
				handler:this.setSelectedTemplate
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp(EDITSUPPLY.TEMPLATE_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.editsupply.descriptionGrid.superclass.constructor.call(this,c);
	},
	fieldsRecord:[
	              {name : "content", mapping : "descriptionTemplateDO.content"}
	]

});






com.zz91.trade.editsupply.editPropertyForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			region:"center",
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			labelAlign : "right",
			labelWidth : 90,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "属性名称",
					name : "name",
					id : "name",
					readOnly:true
				},{
					fieldLabel : "属性值",
					name : "propertyValue",
					id:"propertyValue"
				},{
					xtype:"hidden",
					fieldLabel : "propertyId",
					name : "propertyId",
					id:"propertyId"
				},{
					xtype:"hidden",
					fieldLabel : "supplyId",
					name : "supplyId",
					id:"supplyId"
				},{
					xtype:"hidden",
					fieldLabel : "编号",
					name : "id",
					id:"id"
				}]
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					
					var url=Context.ROOT+"/trade/tradesupply/createPropertyValue.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/trade/tradesupply/updatePropertyValue.htm";
					}
					
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","信息已保存")
								Ext.getCmp(EDITSUPPLY.SUPPLY_GRID).getStore().reload();
								Ext.getCmp(EDITSUPPLY.PROPERTY_WIN).close();
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.submitFailure,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp(EDITSUPPLY.PROPERTY_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.editsupply.editPropertyForm.superclass.constructor.call(this,c);
	},
	loadOneRecord:function(record){
		this.getForm().loadRecord(record);
	}
});

com.zz91.trade.editsupply.editPropertyWin = function(record){
	var form = new com.zz91.trade.editsupply.editPropertyForm({
		id:EDITSUPPLY.PROPERTY_FORM,
		region:"center"
	});
	
	form.loadOneRecord(record);
	
	var win = new Ext.Window({
			id:EDITSUPPLY.PROPERTY_WIN,
			title:"修改属性信息",
			width:300,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.trade.editsupply.choiceCategory=function(init,callback){
	
	var tree = new com.zz91.trade.supply.category.TreePanel({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
	
	tree.initSelect(init);
}