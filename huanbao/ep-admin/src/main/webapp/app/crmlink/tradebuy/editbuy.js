Ext.namespace("com.zz91.trade.editbuy");

var EDITBUY = new function(){
	this.TEMPLATE_WIN = "templatewin";
	this.BUY_FORM="buyform";
	this.EDIT_FORM="editform";
	this.BUY_GRID="buygrid";
	this.PROPERTY_FORM="propertyform";
	this.PROPERTY_WIN="propertywin";
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
						readOnly:true,
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
						readOnly:true,
						id:"quantity",
						name:"quantity"
					},{
						xtype : "numberfield",
						fieldLabel:"预计年采购量",
						readOnly:true,
						id:"quantityYear",
						name:"quantityYear"
					},{
						fieldLabel:"标签信息",
						readOnly:true,
						name:"tagsSys"
					},{
						fieldLabel:"查看次数",
						name:"viewCount",
						readOnly:true
					},{
						fieldLabel:"+1次数",
						name:"plusCount",
						readOnly:true
					},{
						fieldLabel:"暂停发布",
						xtype:"combo",
						mode:"local",
						readOnly:true,
						value:"0",
						triggerAction:"all",
						hiddenName:"pauseStatus",
						hiddenId:"pauseStatus",
						store:[
						["0","正常"],
						["1","暂停"]
						]
					},{
						fieldLabel:"审核状态",
						xtype:"cdombo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"checkStatus",
						hiddenId:"checkStatus",
						readOnly:true,
						store:[
						["0","未审核"],
						["1","审核通过"],
						["2","审核不通过"]
						]
					},{
						fieldLabel:"审核人账号",
						name:"checkAdmin",
						readOnly:true
					},{
						xtype:"datefield",
						fieldLabel:"确认意向时间",
						readOnly:true,
						name:"gmtConfirmStr",
						id:"gmtConfirmStr",
						format:"Y-m-d H:i:s",
						value:new Date()
					},{
						xtype:"datefield",
						fieldLabel:"确认收货时间",
						readOnly:true,
						name:"gmtReceiveStr",
						id:"gmtReceiveStr",
						format:"Y-m-d H:i:s",
						value:new Date()
					},{
						xtype:"datefield",
						fieldLabel:"发布时间",
						readOnly:true,
						name:"gmtPublishStr",
						id:"gmtPublishStr",
						format:"Y-m-d H:i:s",
						value:new Date()
					},{
						xtype:"datefield",
						fieldLabel:"刷新时间",
						readOnly:true,
						name:"gmtRefreshStr",
						id:"gmtRefreshStr",
						format:"Y-m-d H:i:s",
						value:new Date()
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
								com.zz91.trade.editbuy.choiceCategory(initValue,function(node,e){
									Ext.getCmp("categoryName").setValue(node.text);
									Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
									node.getOwnerTree().ownerCt.close();
								})
							}
						}
					},{
						fieldLabel:"采购数量的单位",
						readOnly:true,
						name:"quantityUntis"
					},{
						xtype:"combo",
						hiddenId:"provinceCode",
						hiddenName:"provinceCode",
						readOnly:true,
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
							collapse :function(){
								Ext.getCmp("areaName").store.reload({
									params:{"parentCode":Ext.get("provinceCode").dom.value}
								});
								Ext.getCmp("areaName").setValue("");
							}
						}
					},{
						xtype:"combo",
						id : "areaName",
						hiddenId:"areaCode",
						hiddenName:"areaCode",
						readOnly:true,
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
						fieldLabel:"采购类别",
						readOnly:true,
						name:"buyType"
					},{
						fieldLabel:"用途",
						readOnly:true,
						name:"useTo"
					},{
						fieldLabel:"留言次数",
						name:"messageCount",
						readOnly:true
					},{
						fieldLabel:"收藏次数",
						name:"favoriteCount",
						readOnly:true
					},{
						fieldLabel:"静态页面地址",
						readOnly:true,
						name:"htmlPath"
					},{
						fieldLabel:"删除标记",
						readOnly:true,
						xtype:"combo",
						mode:"local",
						value:"0",
						triggerAction:"all",
						hiddenName:"delStatus",
						hiddenId:"delStatus",
						store:[
						["0","未删除"],
						["1","被删除"]
						]
					},{
						fieldLabel:"有效天数",
						readOnly:true,
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
							anchor:"99%",
							fieldLabel:"详细信息(纯文本)",
							readOnly:true,
							name:"detailsQuery"
						},{
							xtype:"htmleditor",
							anchor:"99%",
							readOnly:true,
							fieldLabel:"产品描述(富文本)",
							name:"details"
						},{
							xtype:"hidden",
							readOnly:true,
							fieldLabel:"未通过原因",
							name:"checkRefuse",
							id:"checkRefuse"
						}]
				}]
			}],
		};

		com.zz91.trade.editbuy.buyForm.superclass.constructor.call(this,c);
	},
	newProductFlag:false,
	saveUrl:Context.ROOT+"/crmlink/crmlinktradebuy/updateTradeBuy.htm",
	saveForm:function(){
		var _url = this.saveUrl;
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : _url,
				method : "post",
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
	loadRecord:function(id){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.trade.FORMFIELD,
			url:Context.ROOT+"/crmlink/crmlinktradebuy/queryOneBuy.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						form.findById("gmtPublishStr").setValue(Ext.util.Format.date(new Date(record.get("gmtPublish").time), 'Y-m-d H:i:s'));
						form.findById("gmtRefreshStr").setValue(Ext.util.Format.date(new Date(record.get("gmtRefresh").time), 'Y-m-d H:i:s'));
						form.findById("gmtConfirmStr").setValue(Ext.util.Format.date(new Date(record.get("gmtConfirm").time), 'Y-m-d H:i:s'));
						form.findById("gmtReceiveStr").setValue(Ext.util.Format.date(new Date(record.get("gmtReceive").time), 'Y-m-d H:i:s'));
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
		
		var _url = Context.ROOT+"/crmlink/crmlinktradebuy/queryTradePropertyValue.htm";
		
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
		url:Context.ROOT+"/crmlink/crmlinktradebuy/updateCheckStatus.htm",
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

com.zz91.trade.editbuy.unpasswin=function(checkStatus,checkRefuse,id){
	var form = new com.zz91.trade.editbuy.UnpassForm({
		id:EDITBUY.EDIT_FORM,
		saveUrl:Context.ROOT+"/crmlink/crmlinktradebuy/unPassCheckStatus.htm?id="+id+"&checkRefuse="+checkRefuse+"&checkStatus="+checkStatus,
		region:"center"
	});
	
	var win = new Ext.Window({
		id:EDITBUY.TEMPLATE_WIN,
		title:"不通过原因",
		width:450,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.trade.editbuy.UnpassForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var c={
			frame:true,
			layout:"form",
			defaults:{
				xtype:"textarea",
				anchor:"99%"
			},
			items:[{
				name:"checkRefuse",
				id:"checkRefuse",
				itemCls:"required",
				allowBlank:false
			},{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				xtype:"hidden",
				name:"checkStatus",
				id:"checkStatus"
			}],
			buttons:[{
				text:"确定",
				scope:this,
				handler:function(){
					var form=this;
					if(this.getForm().isValid()){
						var _url=this.saveUrl;
						this.getForm().submit({
							url:_url,
							method:"post",
							type:"json",
							success:function(){
								Ext.getCmp(EDITBUY.TEMPLATE_WIN).close()
								com.zz91.utils.Msg("","操作成功,刷新查看审核状态!");
							},
							failure:function(){
								com.zz91.utils.Msg("","保存失败");
							}
						});
					}
				}
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp(EDITBUY.TEMPLATE_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.editbuy.UnpassForm.superclass.constructor.call(this,c);
	},
	saveUrl:""
});


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
					
					var url=Context.ROOT+"/crmlink/crmlinktradebuy/createPropertyValue.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/crmlink/crmlinktradebuy/updatePropertyValue.htm";
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
			url : Context.ROOT + "/crmlink/crmlinktradebuy/queryProperty.htm",
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