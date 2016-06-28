Ext.namespace("ast.ast1949.crm.company");

ast.ast1949.crm.company.SEX=["男","女"];

ast.ast1949.crm.company.CSMAP={};

var ACCOUNT = new function(){
	this.ACCOUNT_GRID = "accountgrid";
}
ast.ast1949.crm.company.ACCOUNTFIELD=[
        {name:"id",mapping:"id"},
    	{name:"account",mapping:"account"},
    	{name:"companyId",mapping:"companyId"},
    	{name:"mobile",mapping:"mobile"},
    	{name:"email",mapping:"email"},
    	{name:"password",mapping:"password"}
];
ast.ast1949.crm.company.COMPANYFIELD=[
		{name:"id",mapping:"companyInfo.id"},
		{name:"name",mapping:"companyInfo.name"},
		{name:"companyCode",mapping:"companyInfo.companyCode"},//公司类型
		{name:"industry",mapping:"industry"},//主营行业（默认为废塑料）
		{name:"business",mapping:"companyInfo.business"},//主营产品
		{name:"area",mapping:"companyInfo.area"},//地区
		{name:"areaLabel",mapping:"areaLabel"},//地区label
		{name:"address",mapping:"address"},//公司地址
		{name:"introduce",mapping:"companyInfo.introduce"},//公司简介
];


ast.ast1949.crm.company.CompanyForm=Ext.extend(Ext.form.FormPanel,{
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
					hiddenName:"industry",
					hiddenId:"industry",
					fieldLabel:"主营行业",
					store:[
					       ["20162016","废塑料"],//自定义的code
					]
				},{
					xtype:"combotree",
					fieldLabel:"地区",
					id : "areaLabel",
					name:"areaLabel",
					hiddenName:"area",
					hiddenId:"area",
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
					})
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
					mode:"local",
					readOnly:true,
					triggerAction:"all",
					hiddenName:"companyCode",
					hiddenId:"companyCode",
					fieldLabel:"公司类型",
					store:[
					["10091000","废塑料加工厂"],
					["10091001","塑料造粒厂"],
					["10091002","塑料贸易商"],
					["10091003","塑料改性"],
					["10091004","塑料制品加工厂"]
					]
				},{
					fieldLabel : "主营产品",
					name : "business"
				},{
					name:"address",
					fieldLabel:"公司地址"
				}
				]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					xtype:"textarea",
					fieldLabel : "公司简介",
					name : "introduce",
					labelSeparator:"",
					height:200,
					anchor : "99%"
				}]
			}],
			buttonAlign:"right",
			buttons:[
			{
				id:"refresh",
				text:"刷新",
				iconCls:"refresh",
				handler:function(btn){
					form.loadCompany();
				}
			},{
				id:"save",
				text:"保存",
				iconCls:"item-true",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/admin/company/saveCompany.htm",
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
		
		ast.ast1949.crm.company.CompanyForm.superclass.constructor.call(this,c);
		
	},
	fields:ast.ast1949.crm.company.COMPANYFIELD,
	companyId:0,
	loadCompany:function(){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
//			url : Context.ROOT+Context.PATH+"/crm/company/queryCompanyInfo.htm?companyId="+form.companyId, 
			url : Context.ROOT+Context.PATH+"/admin/company/queryCompanyInfo.htm?companyId="+form.companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("area").dom.value=record.get("area");
						Ext.get("areaLabel").dom.value=record.get("areaLabel");
					}
				}
			}
		});
	},
});

ast.ast1949.crm.company.CompanyAccountGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			fields : ast.ast1949.crm.company.ACCOUNTFIELD,
			url:Context.ROOT +Context.PATH+  "/admin/company/queryCompanyAccount.htm",
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
			dataIndex:"password",
			hidden: true,
			hideLabel:true 
		},{
			header : "email",
			dataIndex:"email"
		},{
			header : "手机",
			dataIndex:"mobile"
		}]);
		var _this=this;
		
		var _tbar=config.tbar||[{
			text:"编辑",
			iconCls:"edit",
			handler:function(btn){
				var rows=_this.getSelectionModel().getSelected();
				ast.ast1949.crm.company.updateCompanyAccount(rows.get("companyId"));
			}
		}]
			
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:_tbar
		};
		
		ast.ast1949.crm.company.CompanyAccountGrid.superclass.constructor.call(this,c);
	},
	loadAccount:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
	}
});

//账户信息修改
ast.ast1949.crm.company.updateCompanyAccount=function(companyId){
	
	var form=new ast.ast1949.crm.company.UpdateCompanyAccountForm({
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
//
//ast.ast1949.crm.company.UpdateCompanyAccountForm = Ext.extend(Ext.form.FormPanel,{
//	targetGrid:null,
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var form=this;
//		
//		var c={
//			labelAlign : "right",
//			layout : "form",
//			frame:true,
//			defaults:{
//				anchor:"95%",
//				xtype:"textfield",
//				labelSeparator:""
//			},
//			items:[{
//				fieldLabel:"账户",
//				name:"account",
//				id:"account",
//				readOnly:true
//			},{
//				xtype:"hidden",
//				name:"companyId",
//				id:"companyId"
//			},{
//				xtype:"hidden",
//				name:"isAdmin",
//				id:"isAdmin"
//			},{
//				xtype:"hidden",
//				name:"id",
//				id:"id"
//			},{
//				fieldLabel:"联系人",
//				name:"contact",
//				id:"contact",
//				itemCls:"required",
//				allowBlank:false,
//				renderer:function(value,metadata,record,rowindex,colindex,store){
//					if(record.get("isAdmin")=="1"){
//						return value+"(主账号)";
//					}
//					return value;
//				}
//			},{
//				fieldLabel:"邮箱",
//				name:"email",
//				id:	"email",
//				readOnly:true
//			},{
//				fieldLabel:"备用邮箱",
//				name:"backEmail",
//				id:	"backEmail",
//				readOnly:true
//			},{
//				fieldLabel:"手机",
//				name:"mobile",
//				id:"mobile",
//				itemCls:"required",
//				allowBlank:false	
//			},{
//				fieldLabel:"电话区号",
//				name:"telAreaCode",
//				id:"telAreaCode"
//			},{
//				fieldLabel:"电话",
//				name:"tel",
//				id:"tel"
//			},{
//				fieldLabel:"传真",
//				name:"fax",
//				id:"fax"
//			},{	
//				xtype:"combo",
//				mode:"local",
//				triggerAction:"all",
//				name:"sex",
//				id:"sex",
//				fieldLabel:"性别",
//				store:[
//						["0","男"],
//						["1","女"]
//				      ]
//			},{
//				fieldLabel:"职位",
//				name:"position",
//				id:"position"
//			},{
//				fieldLabel:"qq",
//				name:"qq",
//				id:"qq"
//			},{
//				fieldLabel:"微信ID",
//				name:"weixin",
//				id:"weixin"
//			},{
//				fieldLabel:"msn",
//				name:"msn",
//				id:"msn"
//			}],
//			buttons:[{
//				text:"保存",
//				handler:function(btn){
//					if(form.getForm().isValid()){
//						var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
//						form.getForm().submit({
//							url:Context.ROOT+Context.PATH+"/crm/cs/updateCompanyAccount.htm",
//							method:"post",
//							type:"json",
//							success:function(){
//								ast.ast1949.utils.Msg("","保存成功");
//								Ext.getCmp("updateaccountwin").close();
//								grid.getStore().reload();	
//							},
//							failure:function(){
//								ast.ast1949.utils.Msg("","保存失败");
//							}
//						});
//					}else{
//						Ext.MessageBox.show({
//							title:Context.MSG_TITLE,
//							msg : "验证未通过",
//							buttons:Ext.MessageBox.OK,
//							icon:Ext.MessageBox.ERROR
//						});
//					}
//				}
//			}]
//		};
//		
//		ast.ast1949.crm.company.UpdateCompanyAccountForm.superclass.constructor.call(this,c);
//	},
//	loadCompanyAccount:function(companyId){
//		var form=this;
//		if(form.store!=null){
//			form.store.reload();
//			return ;
//		}
//		form.store = new Ext.data.JsonStore({
//			fields : ast.ast1949.crm.company.ACCOUNTFIELD,
//			url : Context.ROOT+Context.PATH+"/crm/cs/queryCompanyAccount.htm?companyId="+companyId, 
//			autoLoad : true,
//			listeners : {
//				"datachanged" : function(s) {
//					var record = s.getAt(0);
//					if (record == null) {
//						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
//					} else {
//						form.getForm().loadRecord(record);
//						
//					}
//				}
//			}
//		});
//	}
//});
//
////密码重置
//ast.ast1949.crm.company.resetPassword=function(account,password,mobile,email){
//	
//	var form=new ast.ast1949.crm.company.ResetPasswordForm({
//	});
//	
//	form.initFormValue(account,password,mobile,email);
//	
//	var win = new Ext.Window({
//		id:"resetpasswordwin",
//		title:"密码重置",
//		width:300,
//		modal:true,
//		autoHeight:true,
//		items:[form]
//	});
//	
//	win.show();
//}
//
//ast.ast1949.crm.company.ResetPasswordForm = Ext.extend(Ext.form.FormPanel,{
//	targetGrid:null,
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var form=this;
//		
//		var c={
//			labelAlign : "right",
//			layout : "form",
//			frame:true,
//			defaults:{
//				anchor:"95%",
//				xtype:"textfield",
//				labelSeparator:""
//			},
//			items:[{
//				fieldLabel : "将账户",
//				name:"account",
//				id:"account",
//				readOnly:true
//			}
////			,{
////				fieldLabel:"密码重置为",
////				name:"password",
////				id:"password",
////				required:"true",
////				allowBlank:false,
////				hidden: true,
////				hideLabel:true 
////			}
//			,{
//				xtype:"checkbox",
//				inputValue:true,
//				name:"isSendMobile",
//				boxLabel:"短信通知",
//				anchor:"100",
//				hidden: true,
//				hideLabel:true 
//			},{
//				name:"mobile",
//				id:"mobile",
//				fieldLabel:"并短信通知"
//			}],
//			buttons:[{
//				text:"确定重置",
//				handler:function(btn){
//					if(form.getForm().isValid()){
//						//提交前的初始化工作
//						form.getForm().submit({
//							url:Context.ROOT+Context.PATH+"/crm/company/resetPassword.htm",
//							method:"post",
//							type:"json",
//							success:function(){
//								ast.ast1949.utils.Msg("","密码已重置");
//								Ext.getCmp("resetpasswordwin").close();
//							},
//							failure:function(){
//								ast.ast1949.utils.Msg("","发生错误，密码没有被重置");
//							}
//						});
//					}else{
//						Ext.MessageBox.show({
//							title:Context.MSG_TITLE,
//							msg : "验证未通过",
//							buttons:Ext.MessageBox.OK,
//							icon:Ext.MessageBox.ERROR
//						});
//					}
//				}
//			}]
//		};
//		
//		ast.ast1949.crm.company.ResetPasswordForm.superclass.constructor.call(this,c);
//	},
//	initFormValue:function(account,password,mobile,email){
//		this.findById("account").setValue(account);
////		this.findById("password").setValue(password);
//		this.findById("mobile").setValue(mobile);
//	}
//});
//
//ast.ast1949.crm.company.CompanyAccountContactGrid=Ext.extend(Ext.grid.GridPanel,{
//	constructor:function(config){
//		config=config||{};
//		Ext.apply(this,config);
//		
//		var _store = new Ext.data.JsonStore({
////			root:"records",
////			totalProperty:'totalRecords',
//			remoteSort:true,
//			fields:["id","account","name","sex","tel","mobile"],
//			url:Context.ROOT +Context.PATH+  "/crm/cs/queryAccountContact.htm",
//			autoLoad:false
//		});
//		
//		var _sm=new Ext.grid.CheckboxSelectionModel({});
//		
//		var _cm=new Ext.grid.ColumnModel( [_sm,{
//			header : "编号",
//			width : 50,
//			sortable : true,
//			dataIndex : "id",
//			hidden:true
//		},{
//			header:"姓名",
//			dataIndex:"name"
//		},{
//			header:"性别",
//			dataIndex:"sex",
//			renderer:function(value,metadata,record,rowindex,colindex,store){
//				return ast.ast1949.crm.company.SEX[value];
//			}
//		},{
//			header:"手机",
//			dataIndex:"mobile"
//		},{
//			header:"电话",
//			dataIndex:"tel"
//		}]);
//		
//		var c={
//			loadMask:Context.LOADMASK,
//			store:_store,
//			sm:_sm,
//			cm:_cm
//		};
//		
//		ast.ast1949.crm.company.CompanyAccountContactGrid.superclass.constructor.call(this,c);
//	}
//});
//
//ast.ast1949.crm.company.Grid = Ext.extend(Ext.grid.GridPanel,{
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//
//		var grid=this;
//
//		var sm = new Ext.grid.CheckboxSelectionModel();
//		var cm = new Ext.grid.ColumnModel([sm,{
//				header : "公司名称",
//				width : 200,
//				sortable : false,
//				dataIndex : "name",
//				renderer:function(value,metadata,record,rowIndex,colIndex,store){
//					return "<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>登录</a> "+value
//				}
//			},{
//				header : "密码",
//				sortable : false,
//				dataIndex : "password",
//				hidden: true,
//				hideLabel:true 
//			},{
//				header : "登录帐号",
//				width : 130,
//				sortable : false,
//				dataIndex : "account"
//			},{
//				header : "会员类型",
//				width : 100,
//				sortable : false,
//				dataIndex : "membershipLabel",
//				renderer : function(value, metadata, record, rowIndex, colndex, store) {
//					var val="";
//					if(record.get("membershipCode")=="10051003"){
//						val="<img src='"+Context.ROOT+"/images/ldblogo.jpg'width='20px'height='20px' />";
//					}else if(record.get("membershipCode")!="10051000"){
//						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
//					} 
//					val= val + value;
//					return val;
//				}
//			},{
//				header : "电话",
//				width : 100,
//				sortable : false,
//				dataIndex : "tel"
//			},{
//				header : "手机",
//				width : 100,
//				sortable : false,
//				dataIndex : "mobile"
//			},{
//				header : "地区",
//				width : 90,
//				sortable : false,
//				dataIndex : "areaName"
//			},{
//				header : "注册时间",
//				width : 100,
//				sortable : false,
//				dataIndex : "c.regtime",
//				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
//					if(value!=null){
//						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
//					}
//					else{
//						return "";
//					}
//				}
//			},{
//				header : "最后登陆时间",
//				width : 100,
//				sortable : false,
//				dataIndex : "gmtLastLogin",
//				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
//					if(value!=null){
//						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
//					}
//					else{
//						return "";
//					}
//				}
//			}]);
//
//		// 字段信息
//		var reader = [ {name:"id",mapping:"company.id"},
//		   {name:"name",mapping:"company.name"},
//		   {name:"areaCode",mapping:"company.areaCode"},
//		   {name:"c.regtime",mapping:"company.regtime"},
//		   {name:"star",mapping:"company.star"},
//		   {name:"mobile",mapping:"account.mobile"},	               
//		   {name:"account",mapping:"account.account"},
//		   {name:"tel",mapping:"account.tel"},
//		   {name:"password",mapping:"account.password"},
//		   {name:"areaName",mapping:"areaName"},
//		   {name:"membershipLabel",mapping:"membershipLabel"},
//		   {name:"membershipCode",mapping:"company.membershipCode"},
//		   {name:"gmtLastLogin",mapping:"account.gmtLastLogin"}];
//
//		var storeUrl = Context.ROOT + Context.PATH + "/crm/companyaccount/queryAccount.htm";
//		
//		var _store= new Ext.data.JsonStore({
//			root:"records",
//			totalProperty: 'totalRecords',
//			remoteSort:true,
//			fields:reader,
//			url: storeUrl,
//			autoLoad:true
//		});
//		
//		var tbar = [{
//			text:"修改",
//			iconCls:"edit",
//			handler:function(){
//				if (sm.getCount() == 0)
//					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
//				else
//					var row = grid.getSelections();
//					window.open(Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+row[0].get("id"));
//			}
//		},"-",{
//			text:"客服小记",
//			handler:function(){
//				var rows=grid.getSelectionModel().getSelections();
//				if(rows.length>1){
//					//询问是否要一次打开全部客户信息
//					Ext.MessageBox.confirm(Context.MSG_TITLE, "同时打开多个客户信息可能会造成浏览器假死<br />您确定要打开"+rows.length+"个客户信息吗？", function(btn){
//				        if(btn != "yes"){
//				                return false;
//				        }
//				        for(var i=0;i<rows.length;i++){
//				        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("id")+"&star="+rows[i].get("star")+"&companyName="+encodeURI(rows[i].get("name")));
//				        }
//				    });
//				}else{
//					for(var i=0;i<rows.length;i++){
//			        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("id")+"&star="+rows[i].get("star")+"&companyName="+encodeURI(rows[i].get("name")));
//			        }
//				}
//			}
//		},"-",{	
//			xtype:"combo",
//			width : 240,
//			emptyText: '拉黑原因',  
//			triggerAction : "all",
//			forceSelection : true,
//			displayField : "content",
//			valueField : "id",
//			id : "batchReason",
//			name:"reason",
//			allowBlank:false,
//			itemCls:"required",
//			store:new Ext.data.JsonStore( {
//				fields : [ "content", "id" ],
//				autoLoad:true,
//				url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
//			})
//		},{
//			text:"加入黑名单",
//			handler:function(){
//				var rows=grid.getSelectionModel().getSelections();
//				if(rows.length>0){
//					var reason = Ext.get("batchReason").dom.value;
//					if(reason==null||reason.length<1||reason=="拉黑原因"){
//						alert("请选择拉黑理由！")
//						return false;
//					}
//					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要将选中的客户加入黑名单吗？",function(btn){
//						if(btn != "yes"){
//							return false;
//						}
//						for(var i=0;i<rows.length;i++){
//							Ext.Ajax.request({
//								url: Context.ROOT+Context.PATH+ "/crm/company/updateIsBlock.htm?isBlock=1",
//								params:{
//									"companyId":rows[i].get("id"),
//									"reason":reason
//								},
//								success:function(response,opt){
//									var obj = Ext.decode(response.responseText);
//									if(obj.success){
//										ast.ast1949.utils.Msg("","已成功拉入黑名单");
//										_store.reload();
//									}else{
//										ast.ast1949.utils.Msg("","操作失败");
//									}
//								},
//								failure:function(response,opt){
//									ast.ast1949.utils.Msg("","操作失败");
//								}
//							});
//						}
//					})
//				}
//			}
//		},{
//			text:"取消黑名单",
//			handler:function(){
//				var rows=grid.getSelectionModel().getSelections();
//				if(rows.length>0){
//					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要将选中的客户取消黑名单吗？",function(btn){
//						if(btn != "yes"){
//							return false;
//						}
//						for(var i=0;i<rows.length;i++){
//							Ext.Ajax.request({
//								url: Context.ROOT+Context.PATH+ "/crm/company/updateIsBlock.htm?isBlock=0",
//								params:{
//									"companyId":rows[i].get("id")
//								},
//								success:function(response,opt){
//									var obj = Ext.decode(response.responseText);
//									if(obj.success){
//										ast.ast1949.utils.Msg("","已成功取消黑名单");
//										_store.reload();
//									}else{
//										ast.ast1949.utils.Msg("","操作失败");
//									}
//								},
//								failure:function(response,opt){
//									ast.ast1949.utils.Msg("","操作失败");
//								}
//							});
//						}
//					})
//				}
//			}	
//		},{
//			xtype:"checkbox",
//			boxLabel:"个人",
//			listeners:{
//				"check":function(field,newvalue,oldvalue){
//					var B = _store.baseParams;
//					if(field.getValue()){
//						B["isPerson"] = "1";
//					}else{
//						B["isPerson"] = null;
//					}
//					_store.baseParams = B;
//					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//				}
//			}
//		},{
//			xtype:"checkbox",
//			boxLabel:"公司",
//			listeners:{
//				"check":function(field,newvalue,oldvalue){
//					var B = _store.baseParams;
//					if(field.getValue()){
//						B["isPerson"] = "0";
//					}else{
//						B["isPerson"] = null;
//					}
//					_store.baseParams = B;
//					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//				}
//			}
//		}
////		,"->","按类型",{
////			xtype:"combo",
////			mode:"local",
////			width:100,
////			triggerAction:"all",
////			store:[
////			    ["10051000","普通会员"],
////			    ["10051001","再生通会员"],
////			    ["10051002","品牌通会员"]
////			],
////			listeners:{
////			"change":function(field,newvalue,oldvalule){
////				var B=_store.baseParams||{};
////				if(newvalue==""){
////					B["membershipCode"]=undefined;
////				}else{
////					B["membershipCode"]=newvalue;
////				}
////				_store.baseParams = B;
////				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
////			}
////		}
////		}
//		];
//
//		var c={
//			loadMask:Context.LOADMASK,
//			sm : sm,
//			autoExpandColumn:7,
//			cm : cm,
//			iconCls : "icon-grid",
//			store:_store,
//			tbar : tbar,
//			bbar: new Ext.PagingToolbar({
//				pageSize : Context.PAGE_SIZE,
//				store : _store,
//				displayInfo: true,
//				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
//				emptyMsg : '没有可显示的记录',
//				beforePageText : '第',
//				afterPageText : '页,共{0}页',
//				paramNames : {start:"startIndex",limit:"pageSize"}
//			}),
//			listeners:{
//				"rowcontextmenu":function(g,rowIndex,e){
//					if(!g.getSelectionModel().isSelected(rowIndex)){
//						g.getSelectionModel().clearSelections();
//						g.getSelectionModel().selectRow(rowIndex);
//					}
//					e.preventDefault();
//					if(g.contextmenu!=null){
//						g.contextmenu.showAt(e.getXY());
//					}
//				}
//			}
//		};
//		ast.ast1949.crm.company.Grid.superclass.constructor.call(this,c);
//	},
//	loadDefault:null,
//	contextmenu:null
//});
//
///**
// * 分配客户
// * */
//ast.ast1949.crm.company.reassign=function(grid,oldassignArr,cs, csname){
//	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
//	if(oldassignArr.length<=0){
//		return false;
//	}
//	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这 <b>"+oldassignArr.length+"</b> 个客户分配给 <b>"+csname+"</b> 吗？", function(btn){
//        if(btn != "yes"){
//                return false;
//        }
//        for(var i=0;i<oldassignArr.length;i++){
//        	//TODO 分配客户
//        	Ext.Ajax.request({
//				url:Context.ROOT+Context.PATH+"/crm/companyaccount/reassign.htm",
//				params:{
//					"companyId":oldassignArr[i].companyId,
//					"oldCsAccount":oldassignArr[i].csAccount,
//					"csAccount":cs
//				},
//				success:function(response,opt){
//					var obj = Ext.decode(response.responseText);
//					if(obj.success){
//						ast.ast1949.utils.Msg("","已重新分配");
//						grid.getStore().reload();
//					}else{
//						
//					}
//				},
//				failure:function(response,opt){
//					ast.ast1949.utils.Msg("","发生错误，客户没有被分配");
//				}
//			});
//        }
//    });
//};
//
//ast.ast1949.crm.company.SearchForm = Ext.extend(Ext.form.FormPanel,{
//	targetGrid:null,
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		var _store = this.targetGrid.getStore();
//		var B = _store.baseParams;
//		B = B||{};
//
//		var c={
//			bodyStyle : "padding:2px 2px 0",
//			labelAlign : "right",
//			labelWidth : 80,
//			autoScroll:true,
//			layout : "column",
//			items:[{
//				columnWidth:1,
//				layout:"form",
//				defaults:{
//					anchor:"95%",
//					xtype:"textfield",
//					labelSeparator:""
//				},
//				items:[{
//					fieldLabel : "公司名：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["name"] = undefined;
//							}else{
//								B["name"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "帐号：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["account"] = undefined;
//							}else{
//								B["account"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "邮箱：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["email"] = undefined;
//							}else{
//								B["email"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "手机号码：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["mobile"] = undefined;
//							}else{
//								B["mobile"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "地址：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							B["address"] = newvalue;
//							_store.baseParams = B;
//						}
//					}
//				},{
//					xtype:"combo",
//					triggerAction : "all",
//					forceSelection : true,
//					fieldLabel:"主营行业：",
//					displayField : "label",
//					valueField : "code",
//					store:new Ext.data.JsonStore( {
//						root : "records",
//						fields : [ "label", "code" ],
//						autoLoad:false,
//						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
//					}),
//					listeners:{
//						"change":function(field){
//							B["industryCode"] =  field.getValue();
//							_store.baseParams = B;
//						}
//					}
//				},{
//					xtype:"combotree",
//					fieldLabel:"地区：",
//					hiddenName : "search-areaCode",
//					hiddenId : "search-areaCode",
//					editable:true,
//					tree:new Ext.tree.TreePanel({
//						loader: new Ext.tree.TreeLoader({
//							root : "records",
//							fields : [ "label", "code" ],
//							autoLoad: false,
//							url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
//							listeners:{
//								beforeload:function(treeload,node){
//									this.baseParams["parentCode"] = node.attributes["data"];
//								}
//							}
//						}),
//				   	 	root : new Ext.tree.AsyncTreeNode({text:'地区',data:Context.CATEGORY.areaCode})
//					}),
//					listeners:{
//						"blur":function(field){
//							if(Ext.get("search-areaCode").dom.value!=""){
//								B["areaCode"] =  Ext.get("search-areaCode").dom.value;
//							}else{
//								B["areaCode"] = undefined;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					xtype:"datefield",
//					id : "regFrom",
//					name:"regFrom",
//					format:"Y-m-d",
//					fieldLabel : "注册时间始：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["regFrom"] = undefined;
//							}else{
//								B["regFrom"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					xtype:"datefield",
//					id : "regTo",
//					name:"regTo",
//					format:"Y-m-d",
//					fieldLabel : "注册时间终：",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["regTo"] = undefined;
//							}else{
//								B["regTo"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				}]
//			}],
//			buttons:[{
//				text:"按条件搜索",
//				handler:function(btn){
//					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//				}
//			}]
//		};
//
//		ast.ast1949.crm.company.SearchForm.superclass.constructor.call(this,c);
//
//	}
//});
//
//// 拉黑表单
//ast.ast1949.crm.company.block=function(companyId){
//	
//	var form=new ast.ast1949.crm.company.blockForm({
//	});
//	form.loadCompanyAccount(companyId);
//	var win = new Ext.Window({
//		id:"blockwin",
//		title:"拉黑原因",
//		width:400,
//		modal:true,
//		autoHeight:true,
//		items:[form]
//	});
//	
//	win.show();
//}
//
//ast.ast1949.crm.company.blockForm = Ext.extend(Ext.form.FormPanel,{
//	targetGrid:null,
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var form=this;
//		
//		var c={
//			labelAlign : "right",
//			layout : "form",
//			frame:true,
//			defaults:{
//				anchor:"95%",
//				xtype:"textfield",
//				labelSeparator:""
//			},
//			items:[{
//				header : "编号",
//				width : 50,
//				sortable : true,
//				dataIndex : "id",
//				name:"companyId",
//				hidden:true
//			},{
//				fieldLabel:"公司",
//				name:"name",
//				readOnly:true
//			},{	
//				xtype:"combo",
//				triggerAction : "all",
//				forceSelection : true,
//				fieldLabel:"拉黑原因",
//				displayField : "content",
//				valueField : "id",
//				name:"reason",
//				allowBlank:false,
//				itemCls:"required",
//				store:new Ext.data.JsonStore( {
//					fields : [ "content", "id" ],
//					autoLoad:true,
//					url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
//				})
//			}],
//			buttons:[{
//				text:"保存",
//				handler:function(btn){
//					if(form.getForm().isValid()){
//						var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
//						form.getForm().submit({
//							url:Context.ROOT+Context.PATH+"/crm/company/updateIsBlock.htm?isBlock=1",
//							method:"post",
//							type:"json",
//							success:function(){
//								ast.ast1949.utils.Msg("","加入黑名单成功");
//								Ext.getCmp("blockwin").close();
//								grid.getStore().reload();	
//							},
//							failure:function(){
//								ast.ast1949.utils.Msg("","保存失败");
//							}
//						});
//					}else{
//						Ext.MessageBox.show({
//							title:Context.MSG_TITLE,
//							msg : "验证未通过",
//							buttons:Ext.MessageBox.OK,
//							icon:Ext.MessageBox.ERROR
//						});
//					}
//				}
//			}]
//		};
//		
//		ast.ast1949.crm.company.blockForm.superclass.constructor.call(this,c);
//	},
//	loadCompanyAccount:function(companyId){
//		var form=this;
//		if(form.store!=null){
//			form.store.reload();
//			return ;
//		}
//		form.store = new Ext.data.JsonStore({
//			fields : ast.ast1949.crm.company.COMPANYFIELD,
//			url : Context.ROOT+Context.PATH+"/crm/company/queryCompanyInfo.htm?companyId="+companyId, 
//			autoLoad : true,
//			listeners : {
//				"datachanged" : function(s) {
//					var record = s.getAt(0);
//					if (record == null) {
//						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
//					} else {
//						form.getForm().loadRecord(record);
//						
//					}
//				}
//			}
//		});
//	}
//});
