Ext.namespace("ast.ast1949.admin.companySearch");
ast.ast1949.admin.companySearch.SEX=["男","女"];

ast.ast1949.admin.companySearch.CSMAP={};

var ACCOUNT = new function(){
	this.ACCOUNT_GRID = "accountgrid";
}
ast.ast1949.admin.companySearch.COMPANYFIELD=[
		{name:"id",mapping:"company.id"},
		{name:"name",mapping:"company.name"},
		{name:"business",mapping:"company.business"},
		{name:"serviceCode",mapping:"company.serviceCode"},
		{name:"areaCode",mapping:"company.areaCode"},
		{name:"industryCode",mapping:"company.industryCode"},
		{name:"foreignCity",mapping:"company.foreignCity"},
		{name:"categoryGardenId",mapping:"company.categoryGardenId"},
		{name:"membershipCode",mapping:"company.membershipCode"},
		{name:"starSys",mapping:"company.starSys"},
		{name:"star",mapping:"company.star"},
		{name:"domainZz91",mapping:"company.domainZz91"},
		{name:"domain",mapping:"company.domain"},
		{name:"website",mapping:"company.website"},
		{name:"classifiedCode",mapping:"company.classifiedCode"},
		{name:"address",mapping:"company.address"},
		{name:"addressZip",mapping:"company.addressZip"},
		{name:"businessType",mapping:"company.businessType"},
		{name:"saleDetails",mapping:"company.saleDetails"},
		{name:"buyDetails",mapping:"company.buyDetails"},
		{name:"tags",mapping:"company.tags"},
		{name:"introduction",mapping:"company.introduction"},
		{name:"regfromCode",mapping:"company.regfromCode"},
		{name:"regtime",mapping:"company.regtime"},
		{name:"areaLabel", mpping:"areaLabel"},
		{name:"categoryGarden",mapping:"categoryGardenName"}                               
];
ast.ast1949.admin.companySearch.ACCOUNTFIELD=[
        {name:"id",mapping:"id"},
    	{name:"account",mapping:"account"},
    	{name:"companyId",mapping:"companyId"},
    	{name:"contact",mapping:"contact"},
    	{name:"isAdmin",mapping:"isAdmin"},
    	{name:"telCountryCode",mapping:"telCountryCode"},
    	{name:"telAreaCode",mapping:"telAreaCode"},
    	{name:"tel",mapping:"tel"},
    	{name:"mobile",mapping:"mobile"},
    	{name:"faxCountryCode",mapping:"faxCountryCode"},
    	{name:"faxAreaCode",mapping:"faxAreaCode"},
    	{name:"fax",mapping:"fax"},
    	{name:"email",mapping:"email"},
    	{name:"sex",mapping:"sex"},
    	{name:"position",mapping:"position"},
    	{name:"qq",mapping:"qq"},
    	{name:"msn",mapping:"msn"},
    	{name:"backEmail",mapping:"backEmail"},
    	{name:"isUseBackEmail",mapping:"isUseBackEmail"},
    	{name:"password",mapping:"password"},
    	{name:"numLogin",mapping:"numLogin"}
];
var regfromMap={
			"10031011":"查看报价注册",
			"10031002":"发布供求信息",
			"10031019":"查看供求信息而注册",
			"10031010":"为了查看报价而注册",
			"10031000":"申请普通会员",	
			"10031003":"对普通会员询盘",
			"10031022":"查看企业报价注册",
			"10031023":"后台手动注册",
			"10031024":"手机注册",
			"10031025":"注册机(可能)",
			"10031026":"QQ登录注册",
			"10031027":"现货商城注册",
			"10031028":"QQ帐号绑定",
			"10031031":"QQ查看报价而注册",
			"10031032":"QQ对普通会员询盘而注册",
			"10031035":"QQ发布供求而注册",
			"10031033":"QQ对查看报价而绑定",
			"10031034":"QQ询盘而绑定",
			"10031036":"QQ发布供求而绑定",
			"10031029":"再生互助操作注册"
		};
ast.ast1949.admin.companySearch.CompanyForm=Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel : "公司名称",
					allowBlank : false,
					itemCls:"required",
					name : "name",
					id:"name"
				},{
					xtype:"combo",
					mode:"local",
					readOnly:true,
					triggerAction:"all",
					hiddenName:"membershipCode",
					hiddenId:"membershipCode",
					fieldLabel:"会员类型",
					store:[
					["10051000","普通会员"],
					["10051001","再生通会员"],
					["10051003","来电宝"],
					["100510021000","银牌品牌通会员"],
					["100510021001","金牌品牌通会员"],
					["100510021002","钻石品牌通会员"]
					]
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"主营行业",
					displayField : "label",
					valueField : "code",
					hiddenId:"industryCode",
					hiddenName:"industryCode",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
					})
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"服务类型",
					displayField : "label",
					valueField : "code",
					hiddenId:"serviceCode",
					hiddenName:"serviceCode",
					allowBlank:false,
					itemCls:"required",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["serviceCode"]
					})
				},{
					xtype:"combotree",
					fieldLabel:"地区",
					id : "areaLabel",
					name:"areaLabel",
					hiddenName:"areaCode",
					hiddenId:"areaCode",
					allowBlank:false,
					itemCls:"required",
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
					}),
					listeners:{
						"collapse":function(combo){
							Ext.getCmp("categoryGarden").store.reload({
								params:{
									"areaCode":Ext.get("areaCode").dom.value
								}
							});
							Ext.getCmp("categoryGarden").setValue("");
						}
					}
				},{
					name:"foreignCity",
					fieldLabel:"国外城市"
				},{
					xtype : "combo",
					id : "categoryGarden",
					name:"categoryGarden",
					hiddenName:"categoryGardenId",
					hiddenId:"categoryGardenId",
					displayField:"name",
					valueField : "id",
					fieldLabel : "园区集散地",
					triggerAction: 'all',
					forceSelection : true,
					editable :false,
					store:new Ext.data.JsonStore({
						autoLoad:true,
						url:Context.ROOT+Context.PATH + "/admin/categorygarden/queryBySomeCode.htm",
						fields:["id","name"]
					}),
					listeners:{
						"expand":function(combo){
						}
					}
				},{
					name:"domain",
					fieldLabel:"绑定域名"
				},{
					name:"domainZz91",
					fieldLabel:"二级域名",
					readOnly:true
				},{
					name:"website",
					fieldLabel:"公司网站"
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
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"公司归类",
					displayField : "label",
					valueField : "code",
					hiddenId:"classifiedCode",
					hiddenName:"classifiedCode",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["classifiedCode"]
					})
				},{
					name:"address",
					fieldLabel:"公司地址"
				},{
					name:"addressZip",
					fieldLabel:"邮编"
				},{
					fieldLabel : "主营业务",
					name : "business"
				},{
					xtype:"combo",
					fieldLabel : "主营方向",
					name:"businessTypeName",
					hiddenName:"businessType",
					mode:"local",
					triggerAction : "all",
					store:[[0,"全部"],[1,"供应"],[2,"求购"]]
//					items:[{boxLabel:"供应",id:"businessType1",anchor:"100"},
//					       {boxLabel:"求购",id:"businessType2",anchor:"100"}]
				},{
					fieldLabel : "主营方向(供应)",
					name : "saleDetails"
				},{
					fieldLabel : "主营方向(求购)",
					name : "buyDetails"
				},{
					xtype:"radiogroup",
					fieldLabel:"星级",
					columns:5,
					items:[{boxLabel:"1星",name:"star",anchor:"100",inputValue:1},
					       {boxLabel:"2星",name:"star",anchor:"100",inputValue:2},
					       {boxLabel:"3星",name:"star",anchor:"100",inputValue:3},
					       {boxLabel:"4星",name:"star",anchor:"100",inputValue:4},
					       {boxLabel:"5星",name:"star",anchor:"100",inputValue:5}]
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"注册来源",
					displayField : "label",
					valueField : "code",
					hiddenName:"regfromCode",
					hiddenId:"regfromCode",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["regfromCode"]
					})
				},{
					name:"tags",
					fieldLabel:"公司标签"
				}
//				,new Ext.Slider({
//					id:"starSys",
//					columnWidth:0.25,
//					increment:1,
//					maxValue:5,
//					minValue:0,
//					plugins:tip
//				})
				]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					xtype:"textarea",
					fieldLabel : "公司简介",
					name : "introduction",
					labelSeparator:"",
					height:200,
					anchor : "99%"
				}]
			}],
			buttonAlign:"right",
			buttons:[{
				text:"刷新",
				iconCls:"refresh",
				handler:function(btn){
					form.loadCompany();
				}
			},{
				text:"保存",
				iconCls:"item-true",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/company/saveCompany.htm",
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
			},{
				text:"加入黑名单",
				handler:function(btn){
					ast.ast1949.admin.companySearch.block(form.companyId)
				}
			},{
				text:"取消黑名单",
				handler:function(){
					form.blockUser(form.companyId, 0);
				}
			}]
		};
		
		ast.ast1949.admin.companySearch.CompanyForm.superclass.constructor.call(this,c);
		
	},
	fields:ast.ast1949.admin.companySearch.COMPANYFIELD,
	companyId:0,
	loadCompany:function(){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+Context.PATH+"/crm/company/queryCompanyInfo.htm?companyId="+form.companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("categoryGardenId").dom.value=record.get("categoryGardenId");
						Ext.get("categoryGarden").dom.value=record.get("categoryGarden");
						Ext.get("areaCode").dom.value=record.get("areaCode");
						Ext.get("areaLabel").dom.value=record.get("areaLabel");
					}
				}
			}
		});
	},
	blockUser:function(companyId, blockFlag,reason){
		var str = "";
		if(blockFlag==1){
			str = "已成功拉入黑名单";
		}else{
			str = "已成功取消黑名单";
		}
		Ext.Ajax.request({
			url: Context.ROOT+Context.PATH+ "/crm/company/updateIsBlock.htm",
			params:{
				"companyId":companyId,
				"isBlock":blockFlag,
				"reason":reason
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("",str);
					_store.reload();
				}else{
					ast.ast1949.utils.Msg("","操作失败");
				}
			},
			failure:function(response,opt){
				ast.ast1949.utils.Msg("","操作失败");
			}
		});
	}
});

ast.ast1949.admin.companySearch.CompanyAccountGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			fields : ast.ast1949.admin.companySearch.ACCOUNTFIELD,
			url:Context.ROOT +Context.PATH+  "/crm/cs/queryCompanyAccount.htm",
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			width : 50,
			sortable : true,
			dataIndex : "id",
			hidden:true
		},{
			header : "账号",
			dataIndex:"account"
		},{
			header : "明文密码",
			dataIndex:"password"
		},{
			header : "联系人",
			dataIndex:"contact",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(record.get("isAdmin")=="1"){
					return value+"(主账号)";
				}
				return value;
			}
		},{
			header : "email",
			dataIndex:"email"
		},{
			header : "手机",
			dataIndex:"mobile"
		},{
			header : "电话号码",
			dataIndex:"tel"
		},{
			header : "传真",
			dataIndex:"fax"
		},{
			header : "性别",
			dataIndex:"sex",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return ast.ast1949.admin.companySearch.SEX[value];
			}
		},{
			header : "职位",
			dataIndex:"position"
		},{
			header : "qq",
			dataIndex:"qq"
		},{
			header : "msn",
			dataIndex:"msn"
		}]);
		var _this=this;
		
		var _tbar=config.tbar||[{
			text:"编辑",
			iconCls:"edit",
			handler:function(btn){
				var rows=_this.getSelectionModel().getSelected();
				ast.ast1949.admin.companySearch.updateCompanyAccount(rows.get("companyId"));
			}
		},{
			text:"重置密码",
			iconCls:"item-key",
			handler:function(btn){
				var row=_this.getSelectionModel().getSelected();
				if(row!=null && typeof row != "undefined"){
					ast.ast1949.admin.companySearch.resetPassword(row.get("account"),row.get("password"),row.get("mobile"),row.get("email"));
				}
			}
		}]
			
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:_tbar
		};
		
		ast.ast1949.admin.companySearch.CompanyAccountGrid.superclass.constructor.call(this,c);
	},
	loadAccount:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
	}
});

//账户信息修改
ast.ast1949.admin.companySearch.updateCompanyAccount=function(companyId){
	
	var form=new ast.ast1949.admin.companySearch.UpdateCompanyAccountForm({
	});
	
	form.loadCompanyAccount(companyId);
	
	var win = new Ext.Window({
		id:"updateaccountwin",
		title:"公司账户信息",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.admin.companySearch.UpdateCompanyAccountForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				fieldLabel:"账户",
				name:"account",
				id:"account",
				readOnly:true
			},{
				xtype:"hidden",
				name:"companyId",
				id:"companyId"
			},{
				xtype:"hidden",
				name:"isAdmin",
				id:"isAdmin"
			},{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"联系人",
				name:"contact",
				id:"contact",
				itemCls:"required",
				allowBlank:false,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(record.get("isAdmin")=="1"){
						return value+"(主账号)";
					}
					return value;
				}
			},{
				fieldLabel:"邮箱",
				name:"email",
				id:	"email",
				readOnly:true
			},{
				fieldLabel:"备用邮箱",
				name:"backEmail",
				id:	"backEmail",
				readOnly:true
			},{
				fieldLabel:"手机",
				name:"mobile",
				id:"mobile",
				itemCls:"required",
				allowBlank:false	
			},{
				fieldLabel:"电话区号",
				name:"telAreaCode",
				id:"telAreaCode"
			},{
				fieldLabel:"电话",
				name:"tel",
				id:"tel"
			},{
				fieldLabel:"传真",
				name:"fax",
				id:"fax"
			},{	
				xtype:"combo",
				mode:"local",
				triggerAction:"all",
				name:"sex",
				id:"sex",
				fieldLabel:"性别",
				store:[
						["0","男"],
						["1","女"]
				      ]
			},{
				fieldLabel:"职位",
				name:"position",
				id:"position"
			},{
				fieldLabel:"qq",
				name:"qq",
				id:"qq"
			},{
				fieldLabel:"msn",
				name:"msn",
				id:"msn"
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/cs/updateCompanyAccount.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updateaccountwin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
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
		
		ast.ast1949.admin.companySearch.UpdateCompanyAccountForm.superclass.constructor.call(this,c);
	},
	loadCompanyAccount:function(companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.admin.companySearch.ACCOUNTFIELD,
			url : Context.ROOT+Context.PATH+"/crm/cs/queryCompanyAccount.htm?companyId="+companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						
					}
				}
			}
		});
	}
});

//密码重置
ast.ast1949.admin.companySearch.resetPassword=function(account,password,mobile,email){
	
	var form=new ast.ast1949.admin.companySearch.ResetPasswordForm({
	});
	
	form.initFormValue(account,password,mobile,email);
	
	var win = new Ext.Window({
		id:"resetpasswordwin",
		title:"密码重置",
		width:300,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.admin.companySearch.ResetPasswordForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				fieldLabel : "将账户",
				name:"account",
				id:"account",
				readOnly:true
			},{
				fieldLabel:"密码重置为",
				name:"password",
				id:"password",
				required:"true",
				allowBlank:false
			},{
				xtype:"checkbox",
				inputValue:true,
				name:"isSendMobile",
				boxLabel:"短信通知",
				anchor:"100"
			},{
				name:"mobile",
				id:"mobile",
				fieldLabel:"并短信通知"
			}],
			buttons:[{
				text:"确定重置",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/company/resetPassword.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","密码已重置");
								Ext.getCmp("resetpasswordwin").close();
							},
							failure:function(){
								ast.ast1949.utils.Msg("","发生错误，密码没有被重置");
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
		
		ast.ast1949.admin.companySearch.ResetPasswordForm.superclass.constructor.call(this,c);
	},
	initFormValue:function(account,password,mobile,email){
		this.findById("account").setValue(account);
		this.findById("password").setValue(password);
		this.findById("mobile").setValue(mobile);
	}
});

ast.ast1949.admin.companySearch.CompanyAccountContactGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
//			root:"records",
//			totalProperty:'totalRecords',
			remoteSort:true,
			fields:["id","account","name","sex","tel","mobile"],
			url:Context.ROOT +Context.PATH+  "/crm/cs/queryAccountContact.htm",
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			width : 50,
			sortable : true,
			dataIndex : "id",
			hidden:true
		},{
			header:"姓名",
			dataIndex:"name"
		},{
			header:"性别",
			dataIndex:"sex",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return ast.ast1949.admin.companySearch.SEX[value];
			}
		},{
			header:"手机",
			dataIndex:"mobile"
		},{
			header:"电话",
			dataIndex:"tel"
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm
		};
		
		ast.ast1949.admin.companySearch.CompanyAccountContactGrid.superclass.constructor.call(this,c);
	}
});
ast.ast1949.admin.companySearch.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{
				header : "公司名称",
				width : 200,
				sortable : false,
				dataIndex : "name",
				renderer:function(value,metadata,record,rowIndex,colIndex,store){
					return "<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>登录</a> "+value
				}
			},{
				header : "密码",
				sortable : false,
				dataIndex : "password"
			},{
				header : "登录帐号",
				width : 130,
				sortable : false,
				dataIndex : "account"
			},{
				header : "会员类型",
				width : 100,
				sortable : false,
				dataIndex : "membershipLabel",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")=="10051003"){
						val="<img src='"+Context.ROOT+"/images/ldblogo.jpg'width='20px'height='20px' />";
					}else if(record.get("membershipCode")!="10051000"){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					} 
					val= val + value;
					return val;
				}
			},{
				header : "电话",
				width : 100,
				sortable : false,
				dataIndex : "tel"
			},{
				header : "手机",
				width : 100,
				sortable : false,
				dataIndex : "mobile"
			},{
				header : "地区",
				width : 90,
				sortable : false,
				dataIndex : "areaName"
			},{
				header : "注册时间",
				width : 100,
				dataIndex : "c.regtime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header : "最后登陆时间",
				width : 100,
				dataIndex : "gmtLastLogin",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
                              header : "登录次数",
      			      width :50,
			      dataIndex :"numLogin"
			},{
                              header : "供求数量",
      			      width :50,
			      dataIndex :"numProducts"
			},{
			      header :"注册来源",
			 	width :70,
				dataIndex: "regfromCode",
				renderer:function(value, metadata, record, rowIndex,colIndex, store){
    					return regfromMap[value];
    				}
			}]);

		// 字段信息
		var reader = [ {name:"id",mapping:"company.id"},
		   {name:"name",mapping:"company.name"},
		   {name:"areaCode",mapping:"company.areaCode"},
		   {name:"c.regtime",mapping:"company.regtime"},
		   {name:"star",mapping:"company.star"},
		   {name:"mobile",mapping:"account.mobile"},	               
		   {name:"account",mapping:"account.account"},
		   {name:"tel",mapping:"account.tel"},
		   {name:"password",mapping:"account.password"},
		   {name:"areaName",mapping:"areaName"},
		   {name:"membershipLabel",mapping:"membershipLabel"},
		   {name:"membershipCode",mapping:"company.membershipCode"},
		   {name:"gmtLastLogin",mapping:"account.gmtLastLogin"},
                   {name:"numProducts",mapping:"numProducts"},
                   {name:"numLogin",mapping:"account.numLogin"},
                   {name:"regfromCode",mapping:"company.regfromCode"},
                  
                  ];
                   

		var storeUrl = Context.ROOT + Context.PATH + "/crm/companyaccount/queryAccountBySearch.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [{
			text:"修改",
			iconCls:"edit",
			handler:function(){
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
				else
					var row = grid.getSelections();
					window.open(Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+row[0].get("id"));
			}
		},"-",{
			text:"客服小记",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				if(rows.length>1){
					//询问是否要一次打开全部客户信息
					Ext.MessageBox.confirm(Context.MSG_TITLE, "同时打开多个客户信息可能会造成浏览器假死<br />您确定要打开"+rows.length+"个客户信息吗？", function(btn){
				        if(btn != "yes"){
				                return false;
				        }
				        for(var i=0;i<rows.length;i++){
				        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("id")+"&star="+rows[i].get("star")+"&companyName="+encodeURI(rows[i].get("name")));

				        }
				    });
				}else{
					for(var i=0;i<rows.length;i++){
			        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("id")+"&star="+rows[i].get("star")+"&companyName="+encodeURI(rows[i].get("name")));
			        }
				}
			}
		},"-",{	
			xtype:"combo",
			width : 240,
			emptyText: '拉黑原因',  
			triggerAction : "all",
			forceSelection : true,
			displayField : "content",
			valueField : "id",
			id : "batchReason",
			name:"reason",
			allowBlank:false,
			itemCls:"required",
			store:new Ext.data.JsonStore( {
				fields : [ "content", "id" ],
				autoLoad:true,
				url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
			})
		},{
			text:"加入黑名单",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				if(rows.length>0){
					var reason = Ext.get("batchReason").dom.value;
					if(reason==null||reason.length<1||reason=="拉黑原因"){
						alert("请选择拉黑理由！")
						return false;
					}
					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要将选中的客户加入黑名单吗？",function(btn){
						if(btn != "yes"){
							return false;
						}
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url: Context.ROOT+Context.PATH+ "/crm/company/updateIsBlock.htm?isBlock=1",
								params:{
									"companyId":rows[i].get("id"),
									"reason":reason
								},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										ast.ast1949.utils.Msg("","已成功拉入黑名单");
										_store.reload();
									}else{
										ast.ast1949.utils.Msg("","操作失败");
									}
								},
								failure:function(response,opt){
									ast.ast1949.utils.Msg("","操作失败");
								}
							});
						}
					})
				}
			}
		},{
			text:"取消黑名单",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				if(rows.length>0){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要将选中的客户取消黑名单吗？",function(btn){
						if(btn != "yes"){
							return false;
						}
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url: Context.ROOT+Context.PATH+ "/crm/company/updateIsBlock.htm?isBlock=0",
								params:{
									"companyId":rows[i].get("id")
								},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										ast.ast1949.utils.Msg("","已成功取消黑名单");
										_store.reload();
									}else{
										ast.ast1949.utils.Msg("","操作失败");
									}
								},
								failure:function(response,opt){
									ast.ast1949.utils.Msg("","操作失败");
								}
							});
						}
					})
				}
			}	
		},{
			xtype:"checkbox",
			boxLabel:"个人",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var B = _store.baseParams;
					if(field.getValue()){
						B["isPerson"] = "1";
					}else{
						B["isPerson"] = null;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"公司",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var B = _store.baseParams;
					if(field.getValue()){
						B["isPerson"] = "0";
					}else{
						B["isPerson"] = null;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}
//		,"->","按类型",{
//			xtype:"combo",
//			mode:"local",
//			width:100,
//			triggerAction:"all",
//			store:[
//			    ["10051000","普通会员"],
//			    ["10051001","再生通会员"],
//			    ["10051002","品牌通会员"]
//			],
//			listeners:{
//			"change":function(field,newvalue,oldvalule){
//				var B=_store.baseParams||{};
//				if(newvalue==""){
//					B["membershipCode"]=undefined;
//				}else{
//					B["membershipCode"]=newvalue;
//				}
//				_store.baseParams = B;
//				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//			}
//		}
//		}
		];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:7,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			listeners:{
				"rowcontextmenu":function(g,rowIndex,e){
					if(!g.getSelectionModel().isSelected(rowIndex)){
						g.getSelectionModel().clearSelections();
						g.getSelectionModel().selectRow(rowIndex);
					}
					e.preventDefault();
					if(g.contextmenu!=null){
						g.contextmenu.showAt(e.getXY());
					}
				}
			}
		};
		ast.ast1949.admin.companySearch.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null
});
/**
 * 分配客户
 * */
ast.ast1949.admin.companySearch.reassign=function(grid,oldassignArr,cs, csname){
	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
	if(oldassignArr.length<=0){
		return false;
	}
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这 <b>"+oldassignArr.length+"</b> 个客户分配给 <b>"+csname+"</b> 吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<oldassignArr.length;i++){
        	//TODO 分配客户
        	Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/crm/companyaccount/reassign.htm",
				params:{
					"companyId":oldassignArr[i].companyId,
					"oldCsAccount":oldassignArr[i].csAccount,
					"csAccount":cs
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","已重新分配");
						grid.getStore().reload();
					}else{
						
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","发生错误，客户没有被分配");
				}
			});
        }
    });
};
ast.ast1949.admin.companySearch.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 80,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "公司名：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["name"] = undefined;
							}else{
								B["name"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "帐号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["account"] = undefined;
							}else{
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "邮箱：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["email"] = undefined;
							}else{
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "手机号码：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["mobile"] = undefined;
							}else{
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "地址：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							B["address"] = newvalue;
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"主营行业",
					displayField : "label",
					valueField : "code",
					hiddenId:"industryCode",
					hiddenName:"industryCode",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["industryCode"] = field.getValue();
								}else{
									B["industryCode"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}
				},{
					xtype:"combotree",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"地区",
					displayField : "label",
					valueField : "code",
					hiddenId:"search-areaCode",
					hiddenName:"search-areaCode",
					editable:true,
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							fields : [ "label", "code" ],
							autoLoad: false,
							url:Context.ROOT + Context.PATH+ "/admin/category/childSearch.htm",
							listeners:{

								beforeload:function(treeload,node){
									this.baseParams["parentCode"] = node.attributes["data"];
								}
							}
						}),
				   	 	root : new Ext.tree.AsyncTreeNode({text:'地区',data:Context.CATEGORY.areaCode})
					}),
					listeners:{

						"blur":function(field){
							if(Ext.get("search-areaCode").dom.value!=""){
								B["areaCode"] =  Ext.get("search-areaCode").dom.value;
							}else{
								B["areaCode"] = undefined;
							}
								
							_store.baseParams = B;
						}
					}
					
				},{
					xtype:"datefield",
					id : "regFrom",
					name:"regFrom",
					format:"Y-m-d",
					fieldLabel : "注册时间始：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["regFrom"] = undefined;
							}else{
								B["regFrom"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					id : "regTo",
					name:"regTo",
					format:"Y-m-d",
					fieldLabel : "注册时间终：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["regTo"] = undefined;
							}else{
								B["regTo"] = newvalue;
							}
							_store.baseParams = B;
						}
					}				                                                                                             		
            			},{
                                       fieldLabel : "登录次数始：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["numLoginFrom"] = undefined;
							}else{
								B["numLoginFrom"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
                                },{
					fieldLabel : "登录次数终：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["numLoginTo"] = undefined;
							}else{
								B["numLoginTo"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
			},{
					xtype:"combo",
					id:"companyInfo",
					mode:"local",
					emptyText:"",
					fieldLabel:"公司信息完整：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'不全',value:0},
						{name:'全',value:1}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["companyInfo"] = field.getValue();
								}else{
									B["companyInfo"]=undefined;
								}
								_store.baseParams = B;



							}
						}	
			},{
					fieldLabel : "公司名关键字：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["keyword"] = undefined;
							}else{
								B["keyword"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}]},
			{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
                            items:[{					
					xtype:"combo",
					id:"yangPublish",
					mode:"local",
					emptyText:"",
					fieldLabel:"样品发布：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						 {name:'无样品发布',value:0},
						 {name:'有样品发布',value:1}
						
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["yangPublish"] = field.getValue();
								}else{
									B["yangPublish"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}
			},{
					xtype:"combo",
					id:"order",
					mode:"local",
					emptyText:"",
					fieldLabel:"样品成交：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						 {name:'无订单',value:0},
						 {name:'有订单',value:1}
						
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["order"] = field.getValue();
								}else{
									B["order"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}
			},{
                        		xtype:"combo",
					id:"zhifubao",
					mode:"local",
					emptyText:"",
					fieldLabel:"支付宝绑定：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'已绑定',value:1},
						{name:'未绑定',value:0}
						
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["zhifubao"] = field.getValue();
								}else{
									B["zhifubao"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}	
			},{
					xtype:"combo",
					id:"weixin",
					mode:"local",
					emptyText:"",
					fieldLabel:"微信绑定：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'已绑定',value:1}
						
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["weixin"] = field.getValue();
								}else{
									B["weixin"]=undefined;
								}
								_store.baseParams = B;
							


							}
						}		
			},{
					xtype:"combo",
					id:"crmCode",
					mode:"local",
					emptyText:"",
					fieldLabel:"会员类型：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'过期高会',value:0},
						{name:'银牌品牌通',value:1},
						{name:'再生通',value:2},
						{name:'简版再生通',value:3},
						{name:'来电宝',value:4},
						{name:'百度优化',value:5},
						{name:'普通会员',value:6},
						{name:'金牌品牌通',value:7},
						{name:'钻石品牌通',value:8}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["crmCode"] = field.getValue();
								}else{
									B["crmCode"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}		
			},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"服务类型",
					displayField : "label",
					valueField : "code",
					hiddenId:"serviceCode",
					hiddenName:"serviceCode",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["serviceCode"]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["serviceCode"] = field.getValue();
								}else{
									B["serviceCode"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}
			},{
					xtype:"combo",
					id:"regfromCode",
					mode:"local",
					emptyText:"",
					fieldLabel:"注册来源：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						 {name:'查看报价注册',value:10031011},
						 {name:'发布供求信息',value:10031002},
						 {name:'为了查看报价而注册',value:10031010},
						 {name:'申请普通会员',value:10031000},
						 {name:'对普通会员询盘',value:10031003},
						 {name:'查看企业报价注册',value:10031022},
						 {name:'后台手动注册',value:10031023},
						 {name:'手机注册',value:10031024},
                                                 {name:'注册机(可能)',value:10031025},
						 {name:'QQ登录注册',value:10031026},
                                                 {name:'现货商城注册',value:10031027},
                                                 {name:'QQ查看报价而注册',value:10031031},
                                                 {name:'QQ对普通会员询盘而注册',value:100310032},
                                                 {name:'QQ发布供求而注册',value:10031035},
                                                 {name:'再生互助操作注册',value:10031029},
						 {name:'查看供求信息而注册',value:10031019},
						 {name:'QQ帐号绑定',value:10031028},
						 {name:'QQ对查看报价而绑定',value:10031033},
						 {name:'QQ询盘而绑定',value:10031034},
						 {name:'QQ发布供求而绑定',value:10031036},

					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["regfromCode"] = field.getValue();
								}else{
									B["regfromCode"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}
				
			},{
					xtype:"combo",
					id:"isBlock",
					mode:"local",
					emptyText:"",
					fieldLabel:"拉黑情况：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'已拉黑',value:1},
						{name:'未拉黑',value:0}
						
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["isBlock"] = field.getValue();
								}else{
									B["isBlock"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}				
			},{
					xtype:"combo",
					id:"publish",
					mode:"local",
					emptyText:"",
					fieldLabel:"发布情况：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'有发布供求',value:1},
						{name:'无发布供求',value:2},
						{name:'有发布报价',value:3},
						{name:'无发布报价',value:8},
						{name:'有发布询价',value:6},
						{name:'无发布询价',value:7},
						{name:'有回贴',value:5},
						{name:'有发布贴子',value:4}
						
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["publish"] = field.getValue();
								}else{
									B["publish"]=undefined;
								}
								_store.baseParams = B;


							}
						}	
			},{
					xtype:"combo",
					id:"receive",
					mode:"local",
					emptyText:"",
					fieldLabel:"接收情况：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'有收到回贴',value:1}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["receive"] = field.getValue();
								}else{
									B["receive"]=undefined;
								}
								_store.baseParams = B;


							}
						}	
			},{
					xtype:"datefield",
					id : "loginFrom",
					name:"loginFrom",
					format:"Y-m-d",
					fieldLabel : "登录时间始：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["loginFrom"] = undefined;
							}else{
								B["loginFrom"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					id : "loginTo",
					name:"loginTo",
					format:"Y-m-d",
					fieldLabel : "登录时间终：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["loginTo"] = undefined;
							}else{
								B["loginTo"] = newvalue;
							}
							_store.baseParams = B;
						}
					}				                                                                                             		
            			},{
					xtype:"combo",
					id:"categoryGarden",
					mode:"local",
					emptyText:"",
					fieldLabel:"园区集散地：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					width:80,
					store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'有',value:1}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["categoryGarden"] = field.getValue();
								}else{
									B["categoryGarden"]=undefined;
								}
								_store.baseParams = B;


							}
						}	
				}]
			}],
			
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};
              
		ast.ast1949.admin.companySearch.SearchForm.superclass.constructor.call(this,c);

	}
});

// 拉黑表单
ast.ast1949.admin.companySearch.block=function(companyId){
	
	var form=new ast.ast1949.admin.companySearch.blockForm({
	});
	form.loadCompanyAccount(companyId);
	var win = new Ext.Window({
		id:"blockwin",
		title:"拉黑原因",
		width:400,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.admin.companySearch.blockForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				header : "编号",
				width : 50,
				sortable : true,
				dataIndex : "id",
				name:"companyId",
				hidden:true
			},{
				fieldLabel:"公司",
				name:"name",
				readOnly:true
			},{	
				xtype:"combo",
				triggerAction : "all",
				forceSelection : true,
				fieldLabel:"拉黑原因",
				displayField : "content",
				valueField : "id",
				name:"reason",
				allowBlank:false,
				itemCls:"required",
				store:new Ext.data.JsonStore( {
					fields : [ "content", "id" ],
					autoLoad:true,
					url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
				})
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/company/updateIsBlock.htm?isBlock=1",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","加入黑名单成功");
								Ext.getCmp("blockwin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
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
		
		ast.ast1949.admin.companySearch.blockForm.superclass.constructor.call(this,c);
	},

	loadCompanyAccount:function(companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.admin.companySearch.COMPANYFIELD,
			url : Context.ROOT+Context.PATH+"/crm/company/queryCompanyInfo.htm?companyId="+companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						
					}
				}
			}
		});
	}
});
