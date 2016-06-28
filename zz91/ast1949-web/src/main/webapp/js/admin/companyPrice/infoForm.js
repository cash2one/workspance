Ext.namespace("ast.ast1949.admin.companyPrice")

ast.ast1949.admin.companyPrice.InfoFormWin = Ext.extend(Ext.Window, {
	_form : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";
		var parentCombo = new Ext.form.ComboBox({
			fieldLabel : "所属类别",
			id : "combo-a",
			name : "combo-a",
			hiddenName : "categoryCompanyPriceCode",
			hiddenId : "categoryCompanyPriceCode",
			mode : "local",
			xtype : "combo",
			readOnly : true,
			selectOnFocus : true,
			triggerAction : "all",
			emptyText : "选择父类别",
			anchor : "85%",
			tabIndex : 1,
			allowBlank : true,
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			tpl : "<tpl for='.'><div style='height:280px' id='category-combo'></div></tpl>",
			onSelect : Ext.emptyFn
		});

		var tree;
		parentCombo.on("expand", function() {

					if (tree == null) {

						tree = ast.ast1949.admin.categoryCompanyPrice
								.treePanel({
											el : "category-combo",
											rootData : ""
										});
						tree.getRootNode().disable();
						tree.on('click', function(node) {
							parentCombo.setValue(node.text);
							parentCombo.collapse();
							Ext.get("preCode").dom.value = node.attributes["data"];
								// tree.destroy();
						});
					}
				});

		var categoryProvinceCombo = new Ext.form.ComboBox({
			fieldLabel : "省市",
			id : "combo-b",
			name : "combo-b",
			hiddenName : "areaCode",
			hiddenId : "areaCode",
			mode : "local",
			xtype : "combo",
			readOnly : true,
			selectOnFocus : true,
			triggerAction : "all",
			emptyText : "请选择",
			anchor : "85%",
			tabIndex : 1,
			allowBlank : true,
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			tpl : "<tpl for='.'><div style='height:280px' id='category-province-combo'></div></tpl>",
			onSelect : Ext.emptyFn
		});

		var tree1;
		categoryProvinceCombo.on("expand", function() {
			if (tree1 == null) {
				tree1 = ast.ast1949.admin.category.provincesTreePanel({
							el : "category-province-combo",
							rootData : ""
						});
				tree1.getRootNode().disable();
				tree1.on('click', function(node) {
					categoryProvinceCombo.setValue(node.text);
					categoryProvinceCombo.collapse();
					Ext.get("areaCode").dom.value = node.attributes["data"];
					Ext.getCmp("categoryGardenId2").store.reload({
						params : {
							"areaCode" : node.attributes["data"],
							"gardenTypeCode" : Ext.get("gardenTypeCode").dom.value
						}
					});
					Ext.getCmp("categoryGardenId2").setValue("");
				});
			}
		});

		this._form = new Ext.form.FormPanel({
			labelAlign : 'right',
			fileUpload : true,
			frame : true,
			layout : 'form',
			labelWidth : 80,
			items : [{

				layout : 'column',
				items : [{
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : "hidden",
								name : "id",
								dataIndex : "id"
							}, {
								xtype : "hidden",
								allowBlank : false,
								itemCls : "required",
								format : 'Y-m-d',
								name : "refreshTime"
							}, {
								xtype : "textfield",
								fieldLabel : "公司名称",
								name : "companyName",
								tabIndex : 1,
								anchor : "85%",
								readOnly : true
							}, {
								xtype : "textfield",
								fieldLabel : "产品价格",
								//allowBlank : false,
								name : "price",
								id : "price",
								tabIndex : 1,
								anchor : "85%"
							}, {
								xtype : "textfield",
								fieldLabel : "价格最小值",
								name : "minPrice",
								id : "minPrice",
								tabIndex : 1,
								anchor : "85%",
								emptyText : "0"
							}, parentCombo, new Ext.form.ComboBox({
								store : new Ext.data.JsonStore({
											root : "validTime",
											fields : ['k', 'v'],
											data : ast.ast1949.statesdata.companyPrice
										}),

								displayField : 'v',
								valueField : "k",
								typeAhead : true,
								mode : 'local',
								forceSelection : true,
								triggerAction : 'all',
								selectOnFocus : true,
								fieldLabel : "有效期",
								hiddenId : "validTime",
								hiddenName : "validTime",
								emptyText : "请选择",
								anchor : "90%",
								listeners : {
									"change" : function(formfield, newValue,
											oldValue) {
										if ("10" == newValue) {
											Ext.getCmp("validTime")
													.setVisible(true);
										} else if ("20" == newValue) {
											Ext.getCmp("validTime")
													.setVisible(true);
										} else if ("30" == newValue) {
											Ext.getCmp("validTime")
													.setVisible(true);
										} else if ("90" == newValue) {
											Ext.getCmp("validTime")
													.setVisible(true);
										}else if ("180" == newValue) {
											Ext.getCmp("validTime")
													.setVisible(true);
										}else if ("-1" == newValue) {
											Ext.getCmp("validTime")
													.setVisible(true);
										}else
										{
											Ext.getCmp("validTime")
													.setVisible(false);
										}
									}
								}

							})]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : "textfield",
								fieldLabel : "产品名称",
								name : "title",
								tabIndex : 1,
								anchor : "85%"
							}, {
								xtype : "textfield",
								fieldLabel : "价格单位",
								allowBlank : false,
								name : "priceUnit",
								tabIndex : 1,
								anchor : "85%"
							}, {
								xtype : "textfield",
								fieldLabel : "价格最大值",
								name : "maxPrice",
								tabIndex : 1,
								anchor : "85%",
								emptyText : "0"
							}, categoryProvinceCombo]
				}]

			}, {
				xtype : 'textarea',
				id : 'details',
				fieldLabel : '产品描述',
				height : 160,
				anchor : '95%'
			}],
			buttons : [{
						id : "save",
						text : "修改",
						hidden : _notView
					}, {
						id : "cancel",
						text : "关闭",
						hidden : _isView
					}]
		});

		ast.ast1949.admin.companyPrice.InfoFormWin.superclass.constructor.call(
				this, {
					title : _title,
					closeable : true,
					width : 630,
					autoHeight : false,
					height : 375,
					modal : true,
					border : false,
					plain : true,
					layout : "form",
					items : [this._form]
				});
		this.addEvents("saveSuccess", "saveFailure", "submitFailure");
	},
	submit : function(_url) {
		if (this._form.getForm().isValid()) {
			this._form.getForm().submit({
						url : _url,
						method : "post",
						success : this.onSaveSuccess,
						failure : this.onSaveFailure,
						scope : this
					});
		} else {
			Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "验证未通过",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
		}
	},
	loadRecord : function(_record) {
		this._form.getForm().loadRecord(_record);
		// 初始化区域列表框
		Ext.get("combo-a").dom.value = _record.get("categoryName");
		Ext.get("combo-b").dom.value = _record.get("areaName");
	},
	onSaveSuccess : function(_form, _action) {
		this.fireEvent("saveSuccess", _form, _action, _form.getValues());
	},
	onSaveFailure : function(_form, _action) {
		this.fireEvent("saveFailure", _form, _action, _form.getValues());
	},
	initFocus : function() {
		// var f = this._form.getForm().findField("title");
		// f.focus(true, true);
	}
});