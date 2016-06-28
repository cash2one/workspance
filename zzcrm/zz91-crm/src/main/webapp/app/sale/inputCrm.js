Ext.namespace("com.zz91.ep.crm");

var COMPINPUT=new function(){
	this.COMP_GRID="compgrid";
	this.INPUT_WIN="inputwin";
	this.INPUT_FORM="inputform";
};

com.zz91.ep.crm.CRMCOMPFIELD=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"uid",mapping:"uid"},
	{name:"account",mapping:"account"},
	{name:"email",mapping:"email"},
	{name:"cname",mapping:"cname"},
	{name:"mobile",mapping:"mobile"},
	{name:"phoneCountry",mapping:"phoneCountry"},
	{name:"phoneArea",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"gmtRegister",mapping:"gmtRegister"},
	{name:"input_account",mapping:"inputAccount"},
	{name:"fax",mapping:"fax"}
]

com.zz91.ep.crm.inputCrmForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var form=this;
		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			frame:true,
			items:[{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"label",
						labelSeparator:""
					},
					items:[{
						name:"text",
						html:"<span style='color: rgb(255, 0, 0); line-height: 20px; font-family: 'AR PL UKai CN'; '><strong>【责任明确】本着有发现规定期" +
								"内客户接到电话会告知其它同事，我对此申请已确保查询过“公司名称”“座机”“手机”这3要素重复，如在申请时有撞单的，我对此撞单负责。</strong></span>" +
								"<span style='line-height: 20px; font-family: 'AR PL UKai CN'; '><p><strong>【撞单制度.录入事项规范】</strong><br><strong>" +
								"&nbsp;6.1.2、录入规范：</strong><br>&nbsp;&nbsp;6.1.2.1、公司、手机、座机3要素录入者必须第一先查明是否有重复。如无重复，需要录入信息详细" +
								"真实填写。<br>&nbsp;&nbsp;6.1.2.2、录入如有发现公司、手机、座机已有重复的不用再录入，有在别人库中的需要判单处理，提交客户申请邮件。<br>&nbsp;" +
								"&nbsp;6.1.2.3、发现有重复号码需要帮客户录入一个新的邮箱的，需要确定其它几个已有账号没有撞单冲突，且需要在每个原来账号里面写上“关联小记”。</p><p>" +
								"<strong>&nbsp;6.1.3、审核标准：</strong><br>&nbsp;&nbsp;6.1.3.1、资料完善，发现公司名称、手机、座机、邮箱乱填和不真实的不予通过审核。<br>" +
								"&nbsp;&nbsp;6.1.3.2、录入公司、手机、座机均无重复可方通过录入审核。如有号码需要重复录入的确保没有和其他人发生此客户的撞单，再发客户申请邮件并且需要" +
								"注明具体情况。让审核人可以了解具体情况。</p><p><strong>&nbsp;6.1.4、录入违规，审核违规处罚：</strong><br>如发现违规情况，审核人与录入者各受50-100" +
								"元罚款。违规并造成不良后果的，根据情节严重处以行政处罚。&nbsp;&nbsp;&nbsp;&nbsp;<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
								"&nbsp;&nbsp;<br>有故意引导客户或“刻意”录入不同公司/电话号码用来“避免撞单”的，属于价值观问题的，依照公司制度，严惩！</strong></p></span>",
						readOnly:true
					}]
			}],
			buttons:[{
				id:"on-add",
				iconCls:"add16",
				text:"添加客户",
				handler:function(btn){
					com.zz91.ep.crm.addCrmWin();
				}
			}]
		};
		
		com.zz91.ep.crm.inputCrmForm.superclass.constructor.call(this,c);
	}
});

com.zz91.ep.crm.compGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			fields:com.zz91.ep.crm.CRMCOMPFIELD,
			url:Context.ROOT + "/sale/mycompany/queryCrmCompany.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			dataIndex : "id",
			hidden:true
		},{
			header : "公司Id",
			dataIndex : "cid",
			hidden:true
		},{
			header : "公司名称",
			dataIndex:"cname",
			width:200,
			sortable : true
		},{
			header : "手机",
			sortable : true,
			width:130,
			dataIndex:"mobile"
		},{
			header : "座机所在国家",
			sortable : true,
			dataIndex:"phoneCountry"
		},{
			header : "座机所在地区",
			width:150,
			sortable : true,
			dataIndex:"phoneArea"
		},{
			header : "座机",
			sortable : true,
			dataIndex:"phone"
		},{
			header : "传真或qq",
			sortable : true,
			width:130,
			dataIndex:"fax"
		},{
			header : "邮箱",
			sortable : true,
			width:130,
			dataIndex:"email"
		},{
			header : "注册时间",
			sortable : false,
			width:130,
			dataIndex : "gmtRegister",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "信息录入者",
			sortable : true,
			dataIndex:"input_account"
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:["->",{
						text:"确认查询",
						iconCls:"websearch16",
						handler:function(btn){
						}
					},"->",{
						xtype : "textfield",
						emptyText:"公司名称搜索",
						listeners:{
							"change":function(field){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["cname"] = field.getValue();
								}else{
									B["cname"]=undefined;
								}
								_store.baseParams = B;
								_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype:"textfield",
						emptyText:"手机搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["mobile"] = field.getValue();
								}else{
									B["mobile"]=undefined;
								}
								_store.baseParams = B;
								_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype:"textfield",
						emptyText:"座机搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["phone"] = field.getValue();
								}else{
									B["phone"]=undefined;
								}
								_store.baseParams = B;
								_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype : "textfield",
						id : "search-email",
						emptyText:"邮箱搜索",
						listeners:{
							"change":function(field){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["email"] = field.getValue();
								}else{
									B["email"]=undefined;
								}
								_store.baseParams = B;
								_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype : "textfield",
						emptyText:"qq或传真搜索",
						listeners:{
							"change":function(field){
								var B = _store.baseParams;
								B = B||{};
								if(field.getValue()!=""){
									B["fax"] = field.getValue();
								}else{
									B["fax"]=undefined;
								}
								_store.baseParams = B;
								_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}]
			}),
		};
		
		com.zz91.ep.crm.compGrid.superclass.constructor.call(this,c);
	}
});

com.zz91.ep.crm.inputForm=Ext.extend(Ext.form.FormPanel,{
		constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var form=this;
		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			frame:true,
			items:[{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel:"公司名称",
						name:"cname",
						itemCls:"required",
						allowBlank:false
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
						xtype:"hidden",
						name:"id",
						id:"id"
					},{
						xtype:"hidden",
						fieldLabel:"销售信息id",
						name:"saleCompId",
						id:"saleCompId"
					},{
						fieldLabel:"联系人",
						name:"name",
						itemCls:"required",
						allowBlank:false
					},{
						xtype: "combo",
						fieldLabel:"行业",
						hiddenName: "industryCode",
						editable: false,
						triggerAction: "all",
						anchor: '99%',
						store: new Ext.data.Store({
							autoLoad: true,
							url: Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["comp_industry"],
							reader: new Ext.data.JsonReader({
								fields: [
									{name:"code",mapping:"value"},
									{name:"name",mapping:"name"}
								]
							}),
						}),
						displayField: "name",
						valueField: "code"
					},{
						fieldLabel:"手机",
						name:"mobile",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"电话",
						name:"phone",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"电话所在国家",
						name:"phoneCountry"
					},{
						fieldLabel:"电话所在地区",
						name:"phoneArea"
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"mainBuy",
						hiddenId:"mainBuy",
						editable: false,
						itemCls:"required",
						allowBlank:false,
						fieldLabel:"采购商",
						store:[
						["1","采购商"],
						["0","非采购商"]
						]
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"mainSupply",
						hiddenId:"mainSupply",
						itemCls:"required",
						editable: false,
						allowBlank:false,
						fieldLabel:"供应商",
						store:[
						["1","供应商"],
						["0","非供应商"]
						]
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenId:"provinceCode",
						hiddenName:"provinceCode",
						fieldLabel:"省份",
						valueField:"code1",
						displayField:"name1",
						anchor : "99%",
						forceSelection : true,
						editable :false,
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url :Context.ROOT+"/combox/queryAreaCode.htm?parentCode="+COMBO.areaCode["parentCode"],
							fields:[{name:"name1",mapping:"name"},
									{name:"code1",mapping:"code"}]
						}),
						listeners :{
							"select" :function(){
								Ext.getCmp("areaName").setValue("");
								Ext.getCmp("areaName").store.reload({
									params:{"parentCode":Ext.get("provinceCode").dom.value}
								});
							}
						}
					},{
						fieldLabel:"邮编",
						name:"addressZip"
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
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"sex",
						hiddenId:"sex",
						editable: false,
						itemCls:"required",
						allowBlank:false,
						fieldLabel:"性别",
						store:[
						["0","男"],
						["1","女"]
						]
					},{
						xtype: "combo",
						fieldLabel:"业务类型",
						hiddenName: "businessCode",
						editable: false,
						itemCls:"required",
						allowBlank:false,
						forceSelection: true,
						triggerAction: "all",
						anchor: '99%',
						store: new Ext.data.Store({
							autoLoad: true,
							url : Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["company_industry_code"],
							reader: new Ext.data.JsonReader({
								fields: [{name:"name",mapping:"name"},
										{name:"code",mapping:"value"}]
							}),
						}),
						displayField: "name",
						valueField: "code"
					},{
						fieldLabel:"email",
						name:"email",
						allowBlank:false,
						itemCls:"required"
					},{
						fieldLabel:"传真",
						name:"fax"
					},{
						fieldLabel:"传真所在国家",
						name:"faxCountry"
					},{
						fieldLabel:"传真所在地区",
						name:"faxArea"
					},{
						fieldLabel:"主营产品(采购)",
						name:"mainProductBuy"
					},{
						fieldLabel:"主营产品(供应)",
						name:"mainProductSupply"
					},{
						xtype:"combo",
						mode:'local',
						triggerAction:'all',
						id : "areaName",
						hiddenId:"areaCode",
						hiddenName:"areaCode",
						fieldLabel:"城市",
						valueField:"code2",
						displayField:"name2",
						anchor : "99%",
						forceSelection : true,
						editable :false,
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad : false,
							url :Context.ROOT+"/combox/queryAreaCode.htm",
							fields:[{name:"name2",mapping:"name"},
									{name:"code2",mapping:"code"}]
						})
					},{
						xtype:"combo",
						fieldLabel:"会员类型",
						mode:"local",
						triggerAction:"all",
						hiddenName:"memberCode",
						hiddenId:"memberCode",
						editable: false,
						itemCls:"required",
						allowBlank:false,
						store:[
						["10011001","付费会员"],
						["10011000","普通会员"]
						]
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel:"地址",
						name:"address",
						allowBlank:false,
						itemCls:"required"
					},{
						xtype:"htmleditor",
						fieldLabel:"公司介绍",
						name:"details",
						allowBlank:false,
						itemCls:"required",
						height:100
					}]
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					var url=this.saveUrl;
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","信息已保存");
								Ext.getCmp(COMPINPUT.INPUT_WIN).close();
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : "email或手机已存在，请重新填写!",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.unValidate,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp(COMPINPUT.INPUT_WIN).close();
				}
			}]
		};
		com.zz91.ep.crm.inputForm.superclass.constructor.call(this,c);
	},
	saveUrl:""

});

com.zz91.ep.crm.addCrmWin = function(){
	var form=new com.zz91.ep.crm.inputForm({
		id:COMPINPUT.INPUT_FORM,
		saveUrl:Context.ROOT+ "/sale/mycompany/createCrmCompany.htm",
		region:"center"
	});
	
	var win = new Ext.Window({
		id:COMPINPUT.INPUT_WIN,
		title:"填写录入客户信息",
		width:650,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
};