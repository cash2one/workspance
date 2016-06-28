Ext.namespace("ast.ast1949.admin.adminCompany");

// 定义一个添加,编辑的父窗体类,继承自Window
ast.ast1949.admin.adminCompany.InfoCollectFormPanel = Ext.extend(Ext.Panel,{
	_form:null,
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}
		Ext.apply(this,_cfg);
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";
		
//		var tree = new ast.ast1949.admin.category.categoryTreePanel({
//			root:{
//				nodeType:'async',
//				text:'所有类别',
//				data:'1003'
//			}
//		});
//		
//		tree.on("click", function(node, event) {
//			Ext.get("search-categoryProductsCode").dom.value = node.attributes["data"];
//			grid.getStore().baseParams = {
//				"categoryProductsCode" : node.attributes["data"]
//			};
//			grid.getStore().reload({
//				params : {
//					"startIndex" : 0,
//					"pageSize" : Context.PAGE_SIZE
//				}
//			});
//		});
		
		var categoryProvinceCombo = new Ext.form.ComboBox({
			fieldLabel : "<span style='color:red'>* </span>省市",
			hiddenName : "areaCode",
			hiddenId : "areaCode",
			mode : "local",
			xtype : "combo",
			readOnly : true,
			selectOnFocus : true,
			triggerAction : "all",
			emptyText : "请选择",
			anchor : "95%",
			tabIndex : 1,
			allowBlank : false,
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			tpl : "<tpl for='.'><div style='height:280px' id='category-province-combo'></div></tpl>",
			onSelect : Ext.emptyFn
		});

		var tree;
		categoryProvinceCombo.on("expand", function() {
					if (tree == null) {
						tree = ast.ast1949.admin.category.provincesTreePanel({
									el : "category-province-combo",
									rootData : ""
								});
						tree.getRootNode().disable();
						tree.on('click', function(node) {
							categoryProvinceCombo.setValue(node.text);
							categoryProvinceCombo.collapse();
							Ext.get("areaCode").dom.value = node.attributes["data"];
							Ext.getCmp("categoryGardenId2")
								.store.reload({
									params:{
									"areaCode":node.attributes["data"],
									"gardenTypeCode":Ext.get("gardenTypeCode").dom.value
									}
								});
							Ext.getCmp("categoryGardenId2").setValue("");
						});
					}
			});

		this._form = new Ext.form.FormPanel( {
			region : "center",
			frame : true,
			bodyStyle : "padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
//			width : "100%",
			autoScroll:true,
			items : [ 
//			          {
//				xtype : "fieldset",
//				layout : "column",
//				autoHeight : true,
//				anchor : "95%",
//				title : "搜集查询(<span style='color:red'>请最好只选择手机号或Email中的一个进行查询</span>)",
//				items : [ {
//					columnWidth : .5,
//					layout : "form",
//					defaults : {
//						disabled : false
//					},
//					items : [ {
//						xtype : "textfield",
//						fieldLabel : "<span style='color:red'>* </span>手机号",
//						allowBlank : false,
//						id : "mobile",
//						name : "mobile",
//						anchor : "95%",
//						blankText : "手机号不能为空"
//					}]
//				},{
//					columnWidth : .5,
//					layout : "form",
//					defaults : {
//						disabled : false
//					},
//					items : [{
//						xtype : "textfield",
//						fieldLabel : "<span style='color:red'>* </span>Email",
//						allowBlank : false,
//						id : "email",
//						name : "email",
//						anchor : "95%",
//						blankText : "Email不能为空"
//					}]
////					,new Ext.Button({
////						id : "searchByMobileAndMobile",
//////						menuAlign : "center",
////						text : "查询",
////						hidden : _notView
////					})]
//				}]
//			},
			{
				xtype : "fieldset",
				layout : "column",
				autoHeight : true,
				anchor : "95%",
				title : "公司基本信息",
				items : [ {
					columnWidth : .5,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ {
						xtype : "hidden",
						id : "id",
						dataIndex : "id"
					},{
						xtype:"hidden",
						name:"infoSourceCode",
						value: '10311001'
					},{
						xtype : "textfield",
						fieldLabel : "<span style='color:red'>* </span>公司名",
						id : "name",
						name : "name",
						allowBlank:false,
						tabIndex : 1,
						anchor : "95%",
						blankText : " 客户名称不能为空"
					},{
						xtype : "textfield",
						fieldLabel : "<span style='color:red'>* </span>手机号",
						allowBlank : false,
						id : "mobile",
						name : "mobile",
						anchor : "95%",
						blankText : "手机号不能为空"
					}, {
						xtype:"combotree",
						fieldLabel:"<span style='color:red'>* </span>注册来源",
						id : "regfromCodeName",
//						displayField:"regfromCodeName",
						hiddenName:"regfromCode",
//						hiddenId:"regfromCode",
//						valueField : "regfromCode",
						anchor : "95%",
						allowBlank:false,
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
					},
					new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["serviceCode"],
						fieldLabel : "<span style='color:red'>* </span>服务类型",
						id : "serviceCode",
						name : "serviceCode"
					})]
				}, {
					columnWidth : .5,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [{
						xtype : "textfield",
						fieldLabel : "<span style='color:red'>* </span>Email",
						allowBlank : false,
						id : "email",
						name : "email",
						anchor : "95%",
						blankText : "Email不能为空"
					}, new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["membership"],
						fieldLabel : "<span style='color:red'>* </span>会员类型",
						id : "membershipCode",
						name : "membershipCode"
					}), new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["levelCode"],
						fieldLabel : "<span style='color:red'>* </span>客户级别",
						id : "levelCode",
						name : "levelCode"
					}), new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["service"],
						fieldLabel : "<span style='color:red'>* </span>主营行业",
						id : "industryCode",
						name : "industryCode"
					})]
				} , {
					columnWidth : 1,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ {
						xtype : "textarea",
						fieldLabel : "主营业务",
						id : "business",
						name : "business",
						tabIndex : 2,
						anchor : "95%"
					}]
				} ]
			}, {
				xtype : "fieldset",
				layout : "column",
				anchor : "95%",
				autoHeight : true,
				title : "公司联系方式信息",
				items : [ {
					columnWidth : .5,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ {
						xtype : "textfield",
						fieldLabel : "<span style='color:red'>* </span>登录帐号",
						allowBlank : false,
						id : "account",
						name : "account",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "电话国家代码",
						allowBlank : true,
						id : "telCountryCode",
						name : "telCountryCode",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "电话区号",
						allowBlank : true,
						id : "telAreaCode",
						name : "telAreaCode",
						anchor:"95%"
					},{
						xtype : "textfield",
						fieldLabel : "电话号码",
						allowBlank : true,
						id : "tel",
						name : "tel",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "传真国家代码",
						allowBlank : true,
						id : "faxCountryCode",
						name : "faxCountryCode",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "传真区号",
						allowBlank : true,
						id : "faxAreaCode",
						name : "faxAreaCode",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "传真",
						allowBlank : true,
						id : "fax",
						name : "fax",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "网址",
						allowBlank : true,
						id : "website",
						name : "website",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "邮编",
						allowBlank : true,
						id : "zip",
						name : "zip",
						anchor:"95%"
					}]
				}, {
					columnWidth : .5,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ 
					categoryProvinceCombo,
					{
						xtype : "combo",
						id : "gardenTypeCode2",
						displayField:"gardenTypeCode2",
						hiddenName:"gardenTypeCode",
						hiddenId:"gardenTypeCode",
						valueField : "gardenTypeCode",
						fieldLabel : "所属园区",
						tabIndex : 1,
						mode: 'local',
						anchor : "95%",
						forceSelection : true,
						editable :false,
						triggerAction: 'all',
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad:true,
							//获得园区集散地信息
							url:Context.ROOT+Context.PATH + "/admin/category/child.htm?parentCode=1002",
							fields:[
							{name:"gardenTypeCode2",mapping:"text"},
							{name:"gardenTypeCode",mapping:"data"}]
						}),
						mode:'local',
						triggerAction:'all',
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
					},
					{
						xtype : "combo",
						id : "categoryGardenId2",
						displayField:"categoryGardenId2",
						hiddenName:"categoryGardenId",
						hiddenId:"categoryGardenId",
						valueField : "categoryGardenId",
						fieldLabel : "园区集散地",
						tabIndex : 1,
						mode: 'local',
						anchor : "95%",
						forceSelection : true,
						editable : false,
						triggerAction: 'all',
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad:false,
							url:Context.ROOT+Context.PATH + "/admin/categorygarden/queryBySomeCode.htm",
							fields:[
							{name:"categoryGardenId2",mapping:"name"},
							{name:"categoryGardenId",mapping:"id"}]
						}),
						mode:'local',
						triggerAction:'all'
					},
					{
						xtype : "textfield",
						fieldLabel : "联系人",
						allowBlank : true,
						id : "contact",
						name : "contact",
						anchor:"95%"
					},{
				        xtype: 'radiogroup',
						fieldLabel : "性别",
						horizontal:false,
						anchor : "95%",
						items :[
						        {inputValue:"1",boxLabel:"男",name:"sex"},
						        {inputValue:"0",boxLabel:"女",name:"sex"}
							]
					},{
						xtype : "textfield",
						fieldLabel : "职位",
						allowBlank : true,
						id : "position",
						name : "position",
						anchor:"95%"
					},  {
						xtype : "textfield",
						fieldLabel : "联系地址",
						allowBlank : true,
						id : "address",
						name : "address",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "QQ",
						allowBlank : true,
						id : "qq",
						name : "qq",
						anchor:"95%"
					}, {
						xtype : "textfield",
						fieldLabel : "MSN",
						allowBlank : true,
						id : "msn",
						name : "msn",
						anchor:"95%"
					}]
				} , {
					columnWidth : 1,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ {
						xtype : "textarea",
						fieldLabel : "公司简介",
						id : "introduction",
						name : "introduction",
						tabIndex : 2,
						anchor : "95%"
					}]
				} ]
			} ],
			buttons : [ {
				id : "saveCollect",
				text : "添加",
				hidden : _notView
			}, {
				id : "cancel",
				text : "取消",
				hidden : _isView
			}, {
				id : "close",
				text : "关闭",
				hidden : _isView
			} ]
		});

		ast.ast1949.admin.adminCompany.InfoCollectFormPanel.superclass.constructor.call(this,{
			closeable:true,
			width:700,
			autoHeight:false,
			modal:true,
			border:false,
			plain:true,
			autoScroll:true,
			layout:"border",
			items:[this._form]
		});
		this.addEvents("saveSuccess","saveFailure","submitFailure");
	},
	submit:function(_url){
		if(this._form.getForm().isValid()){
			this._form.getForm().submit({
				url:_url,
				method:"post",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "带红色<span style='color:red'>*</span>项不能为空",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	loadRecord:function(_record){
		this._form.getForm().loadRecord(_record);
		Ext.get("areaCode").dom.value=_record.get("areaCode1");
		Ext.get("categoryGardenId").dom.value=_record.get("categoryGardenId1");
		Ext.get("gardenTypeCode").dom.value=_record.get("gardenTypeCode1");
		Ext.get("regfromCodeName").dom.value = _record.get("regfromCodeName");
		Ext.get("regfromCode").dom.value = _record.get("regfromCode");
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
//		alert(_form.getValues());
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	},
	initFocus:function(){
		var f = this._form.getForm().findField("label");
		f.focus(true,true);
	}
});