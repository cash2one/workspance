Ext.namespace("ast.ast1949.trust.buy.detail");

ast.ast1949.trust.buy.detail.SEX=["男","女"];

ast.ast1949.trust.buy.detail.CSMAP={};

var ACCOUNT = new function(){
	this.ACCOUNT_GRID = "accountgrid";
}
ast.ast1949.trust.buy.detail.COMPANYFIELD=[
{name:"id",mapping:"trustBuy.id"},
{name:"title",mapping:"trustBuy.title"},
{name:"detail",mapping:"trustBuy.detail"},
{name:"code",mapping:"trustBuy.code"},
{name:"color",mapping:"trustBuy.color"},
{name:"level",mapping:"trustBuy.level"},
{name:"status",mapping:"trustBuy.status"},
{name:"dealerId",mapping:"dealerId"},
{name:"buyNo",mapping:"trustBuy.buyNo"},
{name:"quantity",mapping:"trustBuy.quantity"},
{name:"useful",mapping:"trustBuy.useful"},
{name:"areaCode",mapping:"trustBuy.areaCode"},
{name:"price",mapping:"trustBuy.price"},
{name:"areaLabel",mapping:"areaLabel"},
{name:"mobile",mapping:"trustBuy.mobile"},
{name:"companyContact",mapping:"trustBuy.companyContact"},
{name:"companyName",mapping:"trustBuy.companyName"}
];


ast.ast1949.trust.buy.detail.BuyDetailForm=Ext.extend(Ext.form.FormPanel,{
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
						anchor:"100%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype : "hidden",
						name : "id",
						id : "id"
					},{
						fieldLabel : '产品',
						name : 'title'
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"code",
						hiddenId:"code",
						fieldLabel:"类别",
						store:[
						       	["10011004","再生颗粒"],
						       	["100110041000","PP再生颗粒"],
						       	["100110041001","PE再生颗粒"],
						    	["100110041002","PVC再生颗粒"],
						    	["100110041003","ABS再生颗粒"],
						    	["100110041004","PS再生颗粒"],
						    	["100110041005","EVA再生颗粒"],
						    	["100110041006","PA再生颗粒"],
						    	["100110041007","PET再生颗粒"],
						    	["100110041008","PPO再生颗粒"],
						    	["100110041009","PC再生颗粒"],
						    	["100110041010","其他再生颗粒"]
						      ]
					},{
						fieldLabel : '颜色',
						name : 'color'
					},{
						fieldLabel : '级别',
						name : 'level'
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"status",
						hiddenId:"status",
						fieldLabel:"状态",
						store:[
					       		["00","未审核"],
							    ["01","正在报价"],
							    ["02","已有报价"],
							    ["03","正在洽谈"],
							    ["04","等待打款"],
							    ["05","交易完成"],
							    ["06","交易终止"],
							    ["99","审核不通过"]
					       	]
					},{
							xtype : "combo",
							id : "dealId",
							name:"dealId",
							hiddenName:"dealerId",
							hiddenId:"dealerId",
							displayField:"name",
							valueField : "id",
							fieldLabel : "交易员",
							triggerAction: 'all',
							forceSelection : true,
							editable :false,
							store:new Ext.data.JsonStore({
								autoLoad:true,
								url:Context.ROOT+Context.PATH + "/trust/queryDealer.htm",
								fields:["id","name"]
							}),
							listeners:{
								"expand":function(combo){
								}
							}
					},{
						fieldLabel : '公司名',
						id:"hiddenName",
						name : 'companyName'
					}]
				},{
					columnWidth:.5,
					layout : "form",
					defaults:{
						anchor:"100%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel : '采购流水号',
						name : 'buyNo',
						readOnly:true
					},{
						fieldLabel : '采购量',
						name : 'quantity'
					},{
						fieldLabel : '用途',
						name : 'useful'
					},{
						xtype:"combotree",
						fieldLabel:"地区",
						id : "areaLabel",
						name:"areaLabel",
						hiddenName:"areaCode",
						hiddenId:"areaCode",
						tree:new Ext.tree.TreePanel({
							loader: new Ext.tree.TreeLoader({
								root : "records",
								autoLoad: true,
								url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
								listeners:{
									beforeload:function(treeload,node){
										this.baseParams["parentCode"] = node.attributes["data"];
									}
								}
							}),
							root : new Ext.tree.AsyncTreeNode({text:'全部地区',data:"1001"})
						})
					},{
						fieldLabel : '价格',
						name : 'price'
					},{
						fieldLabel : '电话号码',
						id:"hiddenMobile",
						name : 'mobile'
					},{
						fieldLabel : '联系人',
						id:"hiddenContact",
						name : 'companyContact'
					}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"textarea",
						fieldLabel : "委托采购详情",
						name : "detail",
						labelSeparator:"",
						height:200,
						anchor : "99%"
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
							url:Context.ROOT+Context.PATH+"/trust/saveEditOfBuy.htm",
							method:"post",
							type:"json",
							success:function(form, action){
								if(action.result.success){
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "保存成功！",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.INFO
									});
								}else{
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "保存失败！"+action.result.data,
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.INFO
									});
								}
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
		
		ast.ast1949.trust.buy.detail.BuyDetailForm.superclass.constructor.call(this,c);
		
	},
	fields:ast.ast1949.trust.buy.detail.COMPANYFIELD,
	buyId:0,
	loadTrustBuy:function(id){
		Ext.get("id").dom.value=id;
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+"/zz91/trust/queryDetail.htm?id="+id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("areaLabel").dom.value=record.get("areaLabel");
					}
					if(form.noLoginPub){
						Ext.getCmp('hiddenMobile').getEl().up('.x-form-item').setDisplayed(false);
						Ext.getCmp('hiddenContact').getEl().up('.x-form-item').setDisplayed(false);
						Ext.getCmp('hiddenName').getEl().up('.x-form-item').setDisplayed(false);
					}
				}
			}
		});
	}
});

ast.ast1949.trust.buy.detail.AddForm=Ext.extend(Ext.form.FormPanel,{
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
						anchor:"100%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype : "hidden",
						name : "id",
						id : "id"
					},{
						fieldLabel : '产品',
						name : 'title'
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"code",
						hiddenId:"code",
						fieldLabel:"类别",
						store:[
						       	["10011004","再生颗粒"],
						       	["100110041000","PP再生颗粒"],
						       	["100110041001","PE再生颗粒"],
						    	["100110041002","PVC再生颗粒"],
						    	["100110041003","ABS再生颗粒"],
						    	["100110041004","PS再生颗粒"],
						    	["100110041005","EVA再生颗粒"],
						    	["100110041006","PA再生颗粒"],
						    	["100110041007","PET再生颗粒"],
						    	["100110041008","PPO再生颗粒"],
						    	["100110041009","PC再生颗粒"],
						    	["100110041010","其他再生颗粒"]
						      ]
					},{
						fieldLabel : '颜色',
						name : 'color'
					},{
						fieldLabel : '级别',
						name : 'level'
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"status",
						hiddenId:"status",
						fieldLabel:"状态",
						store:[
					       		["00","未审核"],
							    ["01","正在报价"],
							    ["02","已有报价"],
							    ["03","正在洽谈"],
							    ["04","等待打款"],
							    ["05","交易完成"],
							    ["06","交易终止"],
							    ["99","审核不通过"]
					       	]
					},{
							xtype : "combo",
							id : "dealId",
							name:"dealId",
							hiddenName:"dealerId",
							hiddenId:"dealerId",
							displayField:"name",
							valueField : "id",
							fieldLabel : "交易员",
							triggerAction: 'all',
							forceSelection : true,
							editable :false,
							store:new Ext.data.JsonStore({
								autoLoad:true,
								url:Context.ROOT+Context.PATH + "/trust/queryDealer.htm",
								fields:["id","name"]
							}),
							listeners:{
								"expand":function(combo){
								}
							}
					},{
						fieldLabel : '公司名',
						name : 'companyName'
					}]
				},{
					columnWidth:.5,
					layout : "form",
					defaults:{
						anchor:"100%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
//					       {
//						fieldLabel : '采购流水号',
//						name : 'buyNo',
//						readOnly:true
//					},
					{
						fieldLabel : '采购量',
						name : 'quantity'
					},{
						fieldLabel : '用途',
						name : 'useful'
					},{
						xtype:"combotree",
						fieldLabel:"地区",
						id : "areaLabel",
						name:"areaLabel",
						hiddenName:"areaCode",
						hiddenId:"areaCode",
						tree:new Ext.tree.TreePanel({
							loader: new Ext.tree.TreeLoader({
								root : "records",
								autoLoad: true,
								url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
								listeners:{
									beforeload:function(treeload,node){
										this.baseParams["parentCode"] = node.attributes["data"];
									}
								}
							}),
							root : new Ext.tree.AsyncTreeNode({text:'全部地区',data:"1001"})
						})
					},{
						fieldLabel : '价格',
						name : 'price'
					},{
						fieldLabel : '电话号码',
						name : 'mobile'
					},{
						fieldLabel : '联系人',
						name : 'companyContact'
					}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"textarea",
						fieldLabel : "委托采购详情",
						name : "detail",
						labelSeparator:"",
						height:200,
						anchor : "99%"
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
							url:Context.ROOT+Context.PATH+"/trust/doAdd.htm",
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
		
		ast.ast1949.trust.buy.detail.AddForm.superclass.constructor.call(this,c);
	}
});
