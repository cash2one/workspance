Ext.namespace("ast.ast1949.admin.admincompany");

ast.ast1949.admin.admincompany.companyForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var c={
			region : "center",
			frame : true,
			bodyStyle : "padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
//			width : "90%", 
			items : [{
			layout : "column",
			items : [{
				columnWidth : .5,
				layout : "form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items : [{
					xtype : "hidden",
					name : "id",
					id : "id"
				},{
					fieldLabel : "客户名称",
					allowBlank : false,
					itemCls:"required",
					name : "name",
					id:"name"
				}, {
					xtype:"combo",
					mode:"local",
					width:100,
					triggerAction:"all",
					hiddenName:"membershipCode",
					hiddenId:"membershipCode",
					fieldLabel:"会员类型",
					store:[
					    ["10051000","普通会员"],
					    ["10051001","再生通会员"],
					    ["100510021000","银牌品牌通会员"],
					    ["100510021001","金牌品牌通会员"],
					    ["100510021002","钻石品牌通会员"]
					     ]
				}, {
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"客户级别",
					displayField : "label",
					valueField : "code",
					hiddenId:"levelCode",
					hiddenName:"levelCode",
					allowBlank:false,
					itemCls:"required",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["levelCode"]
					})
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"主营行业",
					displayField : "label",
					valueField : "code",
					hiddenId:"industryCode",
					hiddenName:"industryCode",
					allowBlank:false,
					itemCls:"required",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
					})
				},{
					fieldLabel : "手机号码",
					allowBlank : false,
					itemCls:"required",
					name : "mobile",
					id : "mobile"
				}, {
					fieldLabel : "邮箱",
					readOnly:true,
					name : "email",
					id : "email"
				}, {
					fieldLabel : "登录帐号",
					readOnly:true,
					name : "account",
					id : "account"
				}, {
					fieldLabel : "联系人",
					name : "contact",
					id : "contact"
				}, {
					fieldLabel : "电话国家代码",
					name : "telCountryCode"
				}, {
					fieldLabel : "电话区号",
					name : "telAreaCode"
				}, {
					fieldLabel : "电话号码",
					name : "tel"
				}, {
					fieldLabel : "传真国家代码",
					name : "faxCountryCode"
				}, {
					fieldLabel : "传真区号",
					name : "faxAreaCode"
				}, {
					fieldLabel : "传真",
					name : "fax"
				}]
			},{
				columnWidth : .5,
				layout : "form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items : [{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"公司归类",
					displayField : "label",
					valueField : "code",
					hiddenId:"classifiedCode",
					hiddenName:"classifiedCode",
					allowBlank:false,
					itemCls:"required",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["classifiedCode"]
					})
				},{
					fieldLabel : "主营业务信息",
					name : "business"
				}, {
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
					fieldLabel:"注册来源",
					id : "regfromLabel",
					hiddenName:"regfromCode",
					hiddenId:"regfromCode",
					allowBlank:false,
					itemCls:"required",
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							autoLoad: false,
							url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
							listeners:{
								beforeload:function(treeload,node){
									this.baseParams["parentCode"] = node.attributes["data"];
								}
							}
						}),
				   	 	root : new Ext.tree.AsyncTreeNode({text:'全部区域',data:Context.CATEGORY.regfromCode})
					})
				},{
					xtype:"combotree",
					fieldLabel:"地区",
					id : "areaLabel",
					hiddenName:"areaCode",
					hiddenId:"areaCode",
					allowBlank:false,
					itemCls:"required",
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							autoLoad: false,
							url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
							listeners:{
								beforeload:function(treeload,node){
									this.baseParams["parentCode"] = node.attributes["data"];
								}
							}
						}),
				   	 	root : new Ext.tree.AsyncTreeNode({text:'全部省市',data:Context.CATEGORY.areaCode})
					})
				},
				{
					xtype : "combo",
					id : "gardenTypeCode2",
					displayField:"gardenTypeCode2",
					hiddenName:"gardenTypeCode",
					hiddenId:"gardenTypeCode",
					valueField : "gardenTypeCode",
					fieldLabel : "所属园区",
					mode: 'local',
					forceSelection : true,
					editable :false,
					triggerAction: 'all',
					store:new Ext.data.JsonStore({
						autoLoad:true,
						//获得园区集散地信息
						url:Context.ROOT+Context.PATH + "/admin/category/child.htm?parentCode=1002",
						fields:[
						{name:"gardenTypeCode2",mapping:"text"},
						{name:"gardenTypeCode",mapping:"data"}]
					}),
					listeners :{
						collapse :function(){
							Ext.getCmp("categoryGardenId2")
							.store.reload({
								params:{
								"areaCode":Ext.get("areaCode").dom.value,
								"gardenTypeCode":Ext.get("gardenTypeCode").dom.value
								}
							});
							Ext.getCmp("categoryGardenId2").setValue("");
						}
					}
				},{
					xtype : "combo",
					id : "categoryGardenId2",
					displayField:"categoryGardenId2",
					hiddenName:"categoryGardenId",
					hiddenId:"categoryGardenId",
					valueField : "categoryGardenId",
					fieldLabel : "园区集散地",
					mode: 'local',
					triggerAction: 'all',
					forceSelection : true,
					editable :false,
					store:new Ext.data.JsonStore({
						autoLoad:false,
						url:Context.ROOT+Context.PATH + "/admin/categorygarden/queryBySomeCode.htm",
						fields:[
						{name:"categoryGardenId2",mapping:"name"},
						{name:"categoryGardenId",mapping:"id"}]
					})
				},{
					xtype : 'radiogroup',
					fieldLabel : "性别",
					horizontal : false,
					anchor : "95%",
					items : [{
						inputValue : "1",
						boxLabel : "男",
						name : "sex"
					}, {
						inputValue : "0",
						boxLabel : "女",
						name : "sex"
					}]
				}, {
					fieldLabel : "职位",
					name : "position"
				}, {
					fieldLabel : "联系地址",
					name : "address"
				}, {
					fieldLabel : "邮编",
					name : "zip"
				}, {
					fieldLabel : "QQ",
					name : "qq"
				}, {
					fieldLabel : "MSN",
					name : "msn"
				}, {
					fieldLabel : "网址",
					name : "website"
				}]
				}]
			}, {
				columnWidth : 1,
				layout : "form",
				items : [{
					xtype : "textarea",
					fieldLabel : "公司简介",
					name : "introduction",
					tabIndex : 5,
					height : 120,
					anchor : "97%"
				}]
			}],
			buttons : [{
				id : "save",
				text : "添加",
				handler:this.save,
				scope:this,
				hidden : true
			},{
				id:"update",
				text:"修改",
				handler:this.update,
				scope:this,
				hidden : false
			},{
				id : "close",
				text : "关闭",
				handler:function(btn){
					window.close();
				}
			}]
		}
		ast.ast1949.admin.admincompany.companyForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadOneRecord:function(id,account){
		var _fields=[
				{name:"id",mapping:"company.id"},
		        {name:"name",mapping:"company.name"},
		        {name:"business",mapping:"company.business"},
		        
		        {name:"classifiedCode",mapping:"company.classifiedCode"},
		        {name:"levelCode",mapping:"company.levelCode"},
		        {name:"serviceCode",mapping:"company.serviceCode"},
		        {name:"membershipCode",mapping:"company.membershipCode"},
		        {name:"industryCode",mapping:"company.industryCode"},
		        {name:"introduction",mapping:"company.introduction"},
		        {name:"compInfoBackup",mapping:"company.compInfoBackup"},
		        
		        {name:"regfromCode",mapping:"company.regfromCode"},
		        {name:"regfromLabel",mapping:"regfromLabel"},
		        {name:"areaLabel",mapping:"areaLabel"},
		        {name:"areaCode",mapping:"company.areaCode"},
		        {name:"categoryGardenId",mapping:"categoryGardenName"},
		        {name:"categoryGardenId1",mapping:"company.categoryGardenId"},
//		        {name:"gardenTypeCode",mapping:"categoryLabel"},
//		        {name:"gardenTypeCode1",mapping:"gardenTypeCode"},
		        
		        {name:"account",mapping:"account.account"},
		        {name:"companyId",mapping:"account.companyId"},
		        {name:"defaultContact",mapping:"account.defaultContact"},
		        {name:"telCountryCode",mapping:"account.telCountryCode"},
		        {name:"telAreaCode",mapping:"account.telAreaCode"},
		        {name:"tel",mapping:" account.tel"},
		        {name:"mobile",mapping:"account.mobile"},
		        {name:"faxCountryCode",mapping:"account.faxCountryCode"},
		        {name:"faxAreaCode",mapping:"account.faxAreaCode"},
		        {name:"fax",mapping:"account.fax"},
		        {name:"email",mapping:"account.email"},
		        {name:"website",mapping:"account.website"},
		        {name:"contact",mapping:"account.contact"},
		        {name:"sex",mapping:"account.sex"},
		        {name:"position",mapping:"account.position"},
		        {name:"address",mapping:"account.address"},
		        {name:"zip",mapping:"account.zip"},
		        {name:"qq",mapping:"account.qq"},
		        {name:"msn",mapping:"account.msn"}
		];
		var form = this;
		Ext.MessageBox.show({
			title : Context.MSG_TITLE,
			msg : Context.LOADMASK.msg,
			icon : Ext.MessageBox.INFO,
			closable:false,
			width:350,
			wait:true,
			waitConfig: {interval:300}
		});
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/admincompany/queryById.htm",
			baseParams:{"id":id,"account":account},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("areaCode").dom.value = record.get("areaCode");
						Ext.get("areaLabel").dom.value =record.get("areaLabel");
						Ext.get("categoryGardenId").dom.value=record.get("categoryGardenId1");
						Ext.get("gardenTypeCode").dom.value=record.get("gardenTypeCode1");
						Ext.get("regfromCode").dom.value = record.get("regfromCode");
						Ext.get("regfromLabel").dom.value =record.get("regfromLabel");
						Ext.MessageBox.hide();
					}
				}
			}
		});
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/admincompany/add.htm",
	//保存
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		//Ext.getCmp(_C.INFO_FORM).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	},
	updateUrl:Context.ROOT+Context.PATH + "/admin/admincompany/update.htm",
	//修改
	update:function(){
		var _url = this.updateUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onUpdateSuccess,
				failure:this.onUpdateFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onUpdateSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		//Ext.getCmp(_C.INFO_FORM).close();
	},
	onUpdateFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

});