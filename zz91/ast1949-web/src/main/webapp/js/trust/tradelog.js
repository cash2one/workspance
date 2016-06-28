Ext.namespace("ast.ast1949.trust.buy.trade");

ast.ast1949.trust.buy.trade.COMPANYFIELD=[
{name:"id",mapping:"id"},
{name:"buyId",mapping:"buyId"},
{name:"unit",mapping:"unit"},
{name:"quantity",mapping:"quantity"},
{name:"companyId",mapping:"companyId"},
{name:"companyName",mapping:"companyName"},
{name:"contact",mapping:"contact"},
{name:"price",mapping:"price"},
{name:"source",mapping:"source"},
{name:"detail",mapping:"detail"},
{name:"picAddress",mapping:"picAddress"},
{name:"gmtTrade",mapping:"gmtTrade"},
];


ast.ast1949.trust.buy.trade.BuyDetailForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
				autoScroll:true,
				frame : true,
				labelAlign : "right",
				labelWidth : 90,
				layout : "column",
				items:[{
					columnWidth:.5,
					layout : "form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype : "hidden",
						name : "buyId",
						id : "buyId"
					},{
						xtype : "hidden",
						name : "id",
						id : "id"
					},{
						xtype : "hidden",
						name : "companyId",
						id : "company_id"
					},{
						xtype:"datefield",
						id : "gmtTrade",
		                name : "gmtTrade",
		                fieldLabel : "交易时间",
		                format: 'Y-m-d H:i:s', 
		                readOnly:"true",
		                allowBlank : false,
		                itemCls:"required"
					},{
						fieldLabel : '成交单价',
						name : 'unit',
						allowBlank : false,
		                itemCls:"required"
					},{
						fieldLabel : '货源地',
						name : 'source',
						allowBlank : false,
		                itemCls:"required"
					},{
						fieldLabel : '供货商',
						name : 'contact',
						allowBlank : false,
		                itemCls:"required"
					}]
				},{
					columnWidth:.5,
					layout : "form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel : '交易凭证',
						name:"picAddress",
						allowBlank : false,
		                itemCls:"required",
						listeners:{
							"focus":function(field,e){
								com.zz91.trust.upload.UploadConfig.uploadURL=Context.ROOT+"/zz91/trust/doUpload.htm"
								var win = new com.zz91.trust.upload.UploadWin({
									title:"上传内容图片",
								});
								com.zz91.trust.upload.UploadConfig.uploadSuccess=function(form,action){
									Ext.Msg.alert(Context.MSG_TITLE,"图片上传成功！");
									field.setValue(action.result.data);
									win.close();
								}
							win.show();
						}
						}
					},{
						fieldLabel : '成交量',
						name : 'quantity',
						id : 'quantity',
						allowBlank : false,
		                itemCls:"required"
					},{
						fieldLabel : '成交金额',
						name : 'price',
						id : 'price',
						allowBlank : false,
		                itemCls:"required"
					},{
						xtype : "combo",
						id : "companyId",
						name:"companyId",
						hiddenName:"companyName",
						hiddenId:"companyName",
						displayField:"name",
						valueField : "id",
						fieldLabel : "供货公司",
						triggerAction: 'all',
						forceSelection : true,
						editable :false,
						allowBlank : false,
		                itemCls:"required",
						store:new Ext.data.JsonStore({
							autoLoad:true,
							url:Context.ROOT+Context.PATH + "/trust/queryCompanyByBuyId.htm?buyId="+form.buyId,
							fields:["id","name"]
						}),
						listeners:{
							"expand":function(combo){
							}
						}
					}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"textarea",
						fieldLabel : "备注",
						name : "detail",
						labelSeparator:"",
						height:65,
						anchor : "98%"
					}]
				}],
			buttonAlign:"center",
			buttons:[{
				text:"保存",
				iconCls:"item-true",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/trust/addTrustTrade.htm",
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
		
		ast.ast1949.trust.buy.trade.BuyDetailForm.superclass.constructor.call(this,c);
		
	},
	fields:ast.ast1949.trust.buy.trade.COMPANYFIELD,
	loadTrustTrade:function(buyId){
		//alert(buyId);
		Ext.getCmp("buyId").setValue(buyId);
		//alert(Ext.getCmp("buyId").getValue());
//		var _store = Ext.getCmp("companyd").store;
//		var B = _store.baseParams;
//		B = B || {};
//		B["buyId"] = Ext.getCmp("buyId").getValue();
//		 _store.baseParams = B
		 
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+"/zz91/trust/queryTradeById.htm?buyId="+buyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.getCmp("gmtTrade").setValue(new Date(record.data.gmtTrade.time).format("Y-m-d"));
						Ext.getCmp("id").setValue(record.data.id);
					}
				}
			}
		});
	}
});