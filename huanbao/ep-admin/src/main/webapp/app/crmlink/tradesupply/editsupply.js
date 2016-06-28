Ext.namespace("com.zz91.trade.editsupply");

var EDITSUPPLY = new function(){
	this.TEMPLATE_WIN = "templatewin";
	this.SUPPLY_FORM="supplyform";
	this.EDIT_FORM="editform";
	this.SUPPLY_GRID="supplygrid";
	this.PROPERTY_FORM="propertyform";
	this.PROPERTY_WIN="propertywin";
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
	{name:"categoryName",mapping:"categoryName"}
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
						fieldLabel:"供货总量",
						id:"totalNum",
						readOnly:true,
						name:"totalNum"
							
					},{
						fieldLabel:"信息标签",
						readOnly:true,
						name:"tags"
					},{
						fieldLabel:"标签信息",
						readOnly:true,
						name:"tagsSys"
					},{
						fieldLabel:"新旧",
						xtype:"combo",
						mode:"local",
						readOnly:true,
						value:"0",
						triggerAction:"all",
						hiddenName:"usedProduct",
						hiddenId:"usedProduct",
						store:[
						["0","全新"],
						["1","二手"]
						]
					},{
						fieldLabel:"查看次数",
						name:"viewCount",
						readOnly:true
					},{
						fieldLabel:"+1次数",
						name:"plusCount",
						readOnly:true
					},{
						fieldLabel:"信息完整度评分",
						value:"8",
						name:"integrity",
						allowBlank : false,
						itemCls:"required"
					},{
						fieldLabel:"暂停发布",
						xtype:"combo",
						mode:"local",
						value:"0",
						readOnly:true,
						triggerAction:"all",
						hiddenName:"pauseStatus",
						hiddenId:"pauseStatus",
						store:[
						["0","正常"],
						["1","暂停"]
						]
					},{
						fieldLabel:"审核人账号",
						name:"checkAdmin",
						readOnly:true
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
						readOnly:true,
						name:"priceNum"
					},{
						xtype : "numberfield",
						fieldLabel:"报价范围(小)",
						readOnly:true,
						name:"priceFrom"
					},{
						xtype : "numberfield",
						fieldLabel:"报价范围(大)",
						readOnly:true,
						name:"priceTo"
					},{
						xtype:"datefield",
						fieldLabel:"发布时间",
						name:"gmtPublishStr",
						id:"gmtPublishStr",
						format:"Y-m-d H:i:s",
						readOnly:true,
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
								com.zz91.trade.editsupply.choiceCategory(initValue,function(node,e){
									Ext.getCmp("categoryName").setValue(node.text);
									Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
									node.getOwnerTree().ownerCt.close();
								})
							}
						}
					},{
						fieldLabel:"供货总量单位",
						readOnly:true,
						name:"totalUnits"
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
						xtype:"combo",
						mode:"local",
						readOnly:true,
						value:"0",
						triggerAction:"all",
						hiddenName:"delStatus",
						hiddenId:"delStatus",
						store:[
						["0","未删除"],
						["1","被删除"]
						]
					},{
						fieldLabel:"审核状态",
						xtype:"combo",
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
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenId:"infoComeFrom",
						hiddenName:"infoComeFrom",
						fieldLabel:"信息来源",
						valueField:"value",
						displayField:"name",
						readOnly:true,
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["register_type"],
							fields:["value","name"],
						})
					}
					,{
						fieldLabel:"特有属性查询文本",
						readOnly:true,
						name:"propertyQuery"
					},{
						fieldLabel:"报价单位",
						readOnly:true,
						name:"priceUnits"
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
						columnWidth:1,
						layout:"form",
						defaults:{
							anchor:"99%",
							labelSeparator:""
						},
						items:[{
							xtype:"textarea",
							anchor:"99%",
							readOnly:true,
							fieldLabel:"详细信息(纯文本)",
							name:"detailsQuery"
						},{
							xtype:"htmleditor",
							anchor:"99%",
							readOnly:true,
							fieldLabel:"产品描述(富文本)",
							name:"details"
						},{
							xtype:"hidden",
							fieldLabel:"未通过原因",
							readOnly:true,
							name:"checkRefuse",
							id:"checkRefuse"
						}]
				}]
			}],
		};

		com.zz91.trade.editsupply.supplyForm.superclass.constructor.call(this,c);
	},
	newProductFlag:false,
	saveUrl:Context.ROOT+"/crmlink/crmlinktradesupply/updateTradeSupply.htm",
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
			url:Context.ROOT+"/crmlink/crmlinktradesupply/queryOneSupply.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						form.findById("gmtPublishStr").setValue(Ext.util.Format.date(new Date(record.get("gmtPublish").time), 'Y-m-d H:i:s'));
						form.findById("gmtRefreshStr").setValue(Ext.util.Format.date(new Date(record.get("gmtRefresh").time), 'Y-m-d H:i:s'));
					}
				}
			}
		});
	}
	

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
		
		var _url = Context.ROOT+"/crmlink/crmlinktradesupply/queryTradePropertyValue.htm";
		
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
		url:Context.ROOT+"/crmlink/crmlinktradesupply/updateCheckStatus.htm",
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

com.zz91.trade.editsupply.unpasswin=function(checkStatus,checkRefuse,id){
	var form = new com.zz91.trade.editsupply.UnpassForm({
		id:EDITSUPPLY.EDIT_FORM,
		saveUrl:Context.ROOT+"/crmlink/crmlinktradesupply/unPassCheckStatus.htm?id="+id+"&checkRefuse="+checkRefuse+"&checkStatus="+checkStatus,
		region:"center"
	});
	
	var win = new Ext.Window({
		id:EDITSUPPLY.TEMPLATE_WIN,
		title:"不通过原因",
		width:450,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.trade.editsupply.UnpassForm=Ext.extend(Ext.form.FormPanel,{
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
								Ext.getCmp(EDITSUPPLY.TEMPLATE_WIN).close()
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
					Ext.getCmp(EDITSUPPLY.TEMPLATE_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.editsupply.UnpassForm.superclass.constructor.call(this,c);
	},
	saveUrl:""
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
					fieldLabel : "id",
					name : "id",
					id:"id"
				}]
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					
					var url=Context.ROOT+"/crmlink/crmlinktradesupply/createPropertyValue.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/crmlink/crmlinktradesupply/updatePropertyValue.htm";
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