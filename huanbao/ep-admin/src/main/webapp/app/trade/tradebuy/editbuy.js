Ext.namespace("com.zz91.trade.editbuy");

var EDITBUY = new function(){
	this.TEMPLATE_WIN = "templatewin";
	this.BUY_FORM="buyform";
	this.EDIT_FORM="editform";
	this.BUY_GRID="buygrid";
	this.PROPERTY_FORM="propertyform";
	this.PROPERTY_WIN="propertywin";
	this.FORM_TAB="formtabpanel";
}
com.zz91.trade.FORMFIELD=[
	{name:"id",mapping:"tradeBuy.id"},
	{name:"uid",mapping:"tradeBuy.uid"},
	{name:"cid",mapping:"tradeBuy.cid"},
	{name:"title",mapping:"tradeBuy.title"},
	{name:"details",mapping:"tradeBuy.details"},
	{name:"categoryCode",mapping:"tradeBuy.categoryCode"},
	{name:"quantity",mapping:"tradeBuy.quantity"},
	{name:"quantityYear",mapping:"tradeBuy.quantityYear"},
	{name:"buyType",mapping:"tradeBuy.buyType"},
	{name:"quantityUntis",mapping:"tradeBuy.quantityUntis"},
	{name:"useTo",mapping:"tradeBuy.useTo"},
	{name:"usedProduct",mapping:"tradeBuy.usedProduct"},
	{name:"tagsSys",mapping:"tradeBuy.tagsSys"},
	{name:"detailsQuery",mapping:"tradeBuy.detailsQuery"},
	{name:"propertyQuery",mapping:"tradeBuy.propertyQuery"},
	{name:"messageCount",mapping:"tradeBuy.messageCount"},
	{name:"viewCount",mapping:"tradeBuy.viewCount"},
	{name:"favoriteCount",mapping:"tradeBuy.favoriteCount"},
	{name:"plusCount",mapping:"tradeBuy.plusCount"},
	{name:"htmlPath",mapping:"tradeBuy.htmlPath"},
	{name:"gmtPublish",mapping:"tradeBuy.gmtPublish"},
	{name:"gmtRefresh",mapping:"tradeBuy.gmtRefresh"},
	{name:"gmtReceive",mapping:"tradeBuy.gmtReceive"},
	{name:"gmtConfirm",mapping:"tradeBuy.gmtConfirm"},
	{name:"validDays",mapping:"tradeBuy.validDays"},
	{name:"delStatus",mapping:"tradeBuy.delStatus"},
	{name:"pauseStatus",mapping:"tradeBuy.pauseStatus"},
	{name:"checkStatus",mapping:"tradeBuy.checkStatus"},
	{name:"checkAdmin",mapping:"tradeBuy.checkAdmin"},
	{name:"checkRefuse",mapping:"tradeBuy.checkRefuse"},
	{name:"gmtCheck",mapping:"tradeBuy.gmtCheck"},
	{name:"gmtCreated",mapping:"tradeBuy.gmtCreated"},
	{name:"gmtModified",mapping:"tradeBuy.gmtModified"},
	{name:"gmtExpired",mapping:"tradeBuy.gmtExpired"},
	{name:"areaCode",mapping:"tradeBuy.areaCode"},
	{name:"provinceCode",mapping:"tradeBuy.provinceCode"},
	{name:"provinceName",mapping:"provinceName"},
	{name:"areaName",mapping:"areaName"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"supplyAreaCode",mapping:"tradeBuy.supplyAreaCode"}
	];

com.zz91.trade.PROPERTYFIELD=[
	{name:"propertyId",mapping:"tradeProperty.id"},
	{name:"name",mapping:"tradeProperty.name"},
	{name:"propertyValue",mapping:"propertyValue"},
	{name:"buyId",mapping:"tradeBuy.id"},
	{name:"id",mapping:"vId"}
	];

com.zz91.trade.editbuy.buyForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		var form = this;
		var c = {
			labelAlign : "right",
			labelWidth : 100,
			frame:true,
			items:[{
				id:"editbuy",
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
						fieldLabel:"采购数量",
						id:"quantity",
						name:"quantity"
					},{
						fieldLabel:"标签信息",
						name:"tagsSys"
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
                        xtype:"datefield",
                        fieldLabel:"过期时间",
                        id:"gmtExpiredStr",
                        name:"gmtExpiredStr",
                        format:"Y-m-d H:i:s"
                    }]
//					{
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
//					},
					
				},{
					columnWidth:.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
//					{
//						fieldLabel:"类别",
//						name:"categoryName",
//						id:"categoryName",
//						itemCls:"required",
//						allowBlank:false,
//						readOnly:true,
//						listeners:{
//							"focus":function(field){
//								var initValue=Ext.getCmp("categoryCode").getValue();
//								com.zz91.trade.editbuy.choiceCategory(initValue,function(node,e){
//									Ext.getCmp("categoryName").setValue(node.text);
//									Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
//									node.getOwnerTree().ownerCt.close();
//								})
//							}
//						}
//					},
					{
						fieldLabel:"数量单位",
						name:"quantityUntis"
					},{
						xtype:"combo",
						id:"provinceName",
						hiddenId:"provinceCode",
						hiddenName:"provinceCode",
						fieldLabel:"收货地省份",
						valueField:"code1",
						displayField:"name1",
						anchor : "95%",
						forceSelection : true,
						editable :false,
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url :Context.ROOT+"/combo/getAreaCode.htm?parentCode="+COMBO.areaCode["parentCode"],
							fields:[{name:"name1",mapping:"name"},
									{name:"code1",mapping:"code"}]
						}),
						mode:"local",
						triggerAction:"all",
						listeners :{
							"select" :function(){
								Ext.getCmp("areaName").setValue("");
								Ext.getCmp("areaName").store.reload({
									params:{"parentCode":Ext.get("provinceCode").dom.value}
								});
							}
						}
					},{
						xtype:"combo",
						id : "areaName",
						hiddenId:"areaCode",
						hiddenName:"areaCode",
						fieldLabel:"收货地地区",
						valueField:"code",
						displayField:"name",
						anchor : "95%",
						forceSelection : true,
						editable :false,
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/combo/getAreaCode.htm",
							fields:["name","code"]
						}),
						mode:'local',
						triggerAction:'all'
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
							xtype:"textarea",
							hidden : true,
							hideLabel: true,
							anchor:"99%",
							fieldLabel:"详细信息(纯文本)",
							id:"detailsQuery",
							name:"detailsQuery"
						}
//						,{
//							xtype:"htmleditor",
//							anchor:"99%",
//							fieldLabel:"产品描述(富文本)",
//							name:"details"
//						}
						,{
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
							xtype:"tabpanel",
							id:EDITBUY.FORM_TAB,
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
											   com.zz91.trade.editbuy.unpasswinBuy(1,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
											}
										},{
											text:"不通过",
											iconCls:"delete16",
											handler:function(btn){
											   com.zz91.trade.editbuy.unpasswinBuy(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
											}
										},{
											text:"审核模板",
											iconCls:"foldermove16",
											handler:function(btn){
											   com.zz91.trade.editbuy.unpasswin(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
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
                    if(Ext.getCmp('details').getValue().length<=1000){
                        com.zz91.trade.editbuy.unpasswinBuy(1,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
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
//					com.zz91.trade.editbuy.unpasswinBuy(2,Ext.getCmp("checkRefuse").getValue(),Ext.getCmp("id").getValue());
//				}
//			}
			]
		};

		com.zz91.trade.editbuy.buyForm.superclass.constructor.call(this,c);
	},
	newProductFlag:false,
	saveUrl:Context.ROOT+"/trade/tradebuy/updateTradeBuy.htm",
	saveForm:function(){
		if (Ext.getCmp('details').getValue().length>1000){
			alert("字符不得超过500");
			return null;
		}
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
	saveBuyForm:function(){
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
							icon : Ext.MessageBox.INFO,
							fn : function(){
							    window.location.reload();
						    }
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
	},
	loadRecord:function(id){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.trade.FORMFIELD,
			url:Context.ROOT+"/trade/tradebuy/queryOneBuy.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						var checktab=Ext.getCmp(EDITBUY.FORM_TAB).getComponent(0);
						var checkstatus=record.get("checkStatus");
						if(checkstatus=="1"){
							checktab.setIconClass("next16");
						}
						if(checkstatus=="2"){
							checktab.setIconClass("delete16");
						}
						Ext.get("areaCode").dom.value=record.get("areaCode");
						Ext.get("provinceCode").dom.value=record.get("provinceCode");
						Ext.getCmp("areaName").store.reload({
							params:{"parentCode":Ext.get("provinceCode").dom.value}
						});
						if (record.get("gmtExpired") != null) {
                            form.findById("gmtExpiredStr").setValue(Ext.util.Format.date(new Date(record.get("gmtExpired").time), 'Y-m-d H:i:s'));
                        }
						/*form.findById("gmtPublishStr").setValue(Ext.util.Format.date(new Date(record.get("gmtPublish").time), 'Y-m-d H:i:s'));
						form.findById("gmtRefreshStr").setValue(Ext.util.Format.date(new Date(record.get("gmtRefresh").time), 'Y-m-d H:i:s'));
						if (record.get("gmtConfirm")!=null){
							form.findById("gmtConfirmStr").setValue(Ext.util.Format.date(new Date(record.get("gmtConfirm").time), 'Y-m-d H:i:s'));
						}
						if (record.get("gmtReceive")!=null){
							form.findById("gmtReceiveStr").setValue(Ext.util.Format.date(new Date(record.get("gmtReceive").time), 'Y-m-d H:i:s'));
						}*/
						
					}
				}
			}
		});
	}
});

com.zz91.trade.editbuy.choiceAreaCategory=function(init,callback){
	
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
com.zz91.trade.editbuy.listTradePropertyValueGird = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		var grid = this;
		config=config||{};
		Ext.apply(this,config);
		
		var _url = Context.ROOT+"/trade/tradebuy/queryTradePropertyValue.htm";
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			remoteSort:false,
			fields:com.zz91.trade.PROPERTYFIELD,
			url: _url,
			autoLoad:true
		});

		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});

		var _cm = new Ext.grid.ColumnModel([
			_sm,{
				id:"edit-name",
				header:"属性名称",
				dataIndex:"name",
				sortable:true
			},{
				id:"edit-value",
				header:"属性值",
				dataIndex:"propertyValue",
				sortable:true,
				editor:new Ext.form.TextField({  
				allowBlank:false  
				}) 
			},{
				hidden:true,
				header:"buyId",
				dataIndex:"buyId",
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
							var row = Ext.getCmp(EDITBUY.BUY_GRID).getSelectionModel().getSelected();
							if(row!=null){
								com.zz91.trade.editbuy.editPropertyWin(row.get("propertyId"),row.get("buyId"));
							}
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
		com.zz91.trade.editbuy.listTradePropertyValueGird.superclass.constructor.call(this,c);
	}

});

com.zz91.trade.editbuy.auditCheckStatus = function(checkStatus){
	Ext.Ajax.request({
		url:Context.ROOT+"/trade/tradebuy/updateCheckStatus.htm",
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

com.zz91.trade.editbuy.unpasswinBuy = function(checkStatus,checkRefuse,id){
	Ext.Ajax.request({
		url:Context.ROOT+"/trade/tradebuy/unPassCheckStatus.htm",
		params:{
			"id":id,
			"checkRefuse":checkRefuse,
			"checkStatus":checkStatus
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				var checktab=Ext.getCmp(EDITBUY.FORM_TAB).getComponent(0);
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

com.zz91.trade.editbuy.unpasswin=function(checkStatus,checkRefuse,id){
	var grid = new com.zz91.trade.editbuy.descriptionGrid({
		queryUrl:Context.ROOT+"/trade/descriptiontemplate/query.htm",
		height:350,
		region:"center",
		setSelectedTemplate:function(btn){
		    var sels = grid.getSelectionModel().getSelections();
		    if (sels.length > 0) {
				Ext.getCmp("checkRefuse").setValue(Ext.getCmp("checkRefuse").getValue()+sels[0].get("content"));
				Ext.getCmp("isStatus").setValue(checkStatus);
				Ext.getCmp(EDITBUY.TEMPLATE_WIN).close();
			}
	    },
	    listeners:{
			"rowdblclick":function(g,rowindex,e){
				var sels = grid.getSelectionModel().getSelections();
				Ext.getCmp("checkRefuse").setValue(Ext.getCmp("checkRefuse").getValue() + sels[0].get("content"));
				Ext.getCmp(EDITBUY.TEMPLATE_WIN).close();
			}
		}
	});
	
	var win = new Ext.Window({
		id:EDITBUY.TEMPLATE_WIN,
		title:"审核不通过原因",
		width:500,
		height:500,
		autoHeight:true,
		modal:true,
		items:[grid]
	});
	
	win.show();
}

com.zz91.trade.editbuy.descriptionGrid=Ext.extend(Ext.grid.GridPanel,{
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
					Ext.getCmp(EDITBUY.TEMPLATE_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.editbuy.descriptionGrid.superclass.constructor.call(this,c);
	},
	fieldsRecord:[
	     {name : "content", mapping : "descriptionTemplateDO.content"}
	]

});

//com.zz91.trade.editbuy.unpasswin=function(checkStatus,checkRefuse,id){
//	var form = new com.zz91.trade.editbuy.UnpassForm({
//		id:EDITBUY.EDIT_FORM,
//		saveUrl:Context.ROOT+"/trade/tradebuy/unPassCheckStatus.htm?id="+id+"&checkRefuse="+checkRefuse+"&checkStatus="+checkStatus,
//		region:"center"
//	});
//	
//	var win = new Ext.Window({
//		id:EDITBUY.TEMPLATE_WIN,
//		title:"不通过原因",
//		width:450,
//		autoHeight:true,
//		modal:true,
//		items:[form]
//	});
//	
//	win.show();
//}
//
//
//
//
//com.zz91.trade.editbuy.UnpassForm=Ext.extend(Ext.form.FormPanel,{
//	constructor:function(config){
//		config=config||{};
//		Ext.apply(this,config);
//		
//		var c={
//			frame:true,
//			layout:"form",
//			defaults:{
//				xtype:"textarea",
//				anchor:"99%"
//			},
//			items:[{
//				name:"checkRefuse",
//				id:"checkRefuse",
//				itemCls:"required",
//				allowBlank:false
//			},{
//				xtype:"hidden",
//				name:"id",
//				id:"id"
//			},{
//				xtype:"hidden",
//				name:"checkStatus",
//				id:"checkStatus"
//			}],
//			buttons:[{
//				text:"确定",
//				scope:this,
//				handler:function(){
//					var form=this;
//					if(this.getForm().isValid()){
//						var _url=this.saveUrl;
//						this.getForm().submit({
//							url:_url,
//							method:"post",
//							type:"json",
//							success:function(){
//								Ext.getCmp(EDITBUY.TEMPLATE_WIN).close()
//								com.zz91.utils.Msg("","操作成功,刷新查看审核状态!");
//							},
//							failure:function(){
//								com.zz91.utils.Msg("","保存失败");
//							}
//						});
//					}
//				}
//			},{
//				text:"取消",
//				handler:function(){
//					Ext.getCmp(EDITBUY.TEMPLATE_WIN).close();
//				}
//			}]
//		};
//		
//		com.zz91.trade.editbuy.UnpassForm.superclass.constructor.call(this,c);
//	},
//	saveUrl:""
//});


com.zz91.trade.editbuy.editPropertyForm = Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel : "buyId",
					name : "buyId",
					id:"buyId"
				},{
					xtype:"hidden",
					fieldLabel : "id",
					name : "id",
					id:"id"
				}]
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					
					var url=Context.ROOT+"/trade/tradebuy/createPropertyValue.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/trade/tradebuy/updatePropertyValue.htm";
					}
					
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","信息已保存")
								Ext.getCmp(EDITBUY.BUY_GRID).getStore().reload();
								Ext.getCmp(EDITBUY.PROPERTY_WIN).close();
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
					Ext.getCmp(EDITBUY.PROPERTY_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.editbuy.editPropertyForm.superclass.constructor.call(this,c);
	},
	loadOneRecord:function(propertyId,buyId){
		var form = this;
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields : com.zz91.trade.PROPERTYFIELD,
			url : Context.ROOT + "/trade/tradebuy/queryProperty.htm",
			baseParams:{"propertyId":propertyId,"buyId":buyId},
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record != null) {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});

com.zz91.trade.editbuy.editPropertyWin = function(propertyId,buyId){
	var form = new com.zz91.trade.editbuy.editPropertyForm({
		id:EDITBUY.PROPERTY_FORM,
		region:"center"
	});
	
	form.loadOneRecord(propertyId,buyId);
	
	var win = new Ext.Window({
			id:EDITBUY.PROPERTY_WIN,
			title:"修改属性信息",
			width:300,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.trade.editbuy.choiceCategory=function(init,callback){
	
	var tree = new com.zz91.trade.buy.category.TreePanel({
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