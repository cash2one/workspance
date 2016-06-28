Ext.namespace("ast.ast1949.admin.products");

var parentCombo = new Ext.form.ComboBox({
	fieldLabel : "主类别",
	hiddenName : "categoryProductsMainCode",
	hiddenId : "categoryProductsMainCode",
	mode : "local",
	xtype : "combo",
	readOnly : true,
	selectOnFocus : true,
	triggerAction : "all",
	emptyText : "不选为根节点",
	anchor : "95%",
	tabIndex : 1,
	allowBlank : true,
	store : new Ext.data.SimpleStore({
				fields : [],
				data : [[]]
			}),
	tpl : "<tpl for='.'><div style='height:280px' id='adminmenu-combo'></div></tpl>",
	onSelect : Ext.emptyFn
});

var tree;
parentCombo.on("expand", function() {
			if (tree == null) {
				tree = ast.ast1949.admin.categoryproducts.treePanel({
							el : "adminmenu-combo",
							rootData : ""
						});
				tree.getRootNode().disable();
				tree.on('click', function(node) {
					parentCombo.setValue(node.text);
					parentCombo.collapse();
					Ext.get("categoryProductsMainCode").dom.value = node.attributes["data"];
					// tree.destroy();
					Ext.getCmp("categoryProductsAssistCode2")
						.store.reload({params:{"parentCode":node.attributes["data"]}});
					Ext.getCmp("categoryProductsAssistCode2").setValue("");
				});
			}
	});

ast.ast1949.admin.products.productsForm = function(_cfg) {
	if (_cfg == null) {
		_cfg = {};
	}
	
	Ext.apply(this, _cfg);

	var _id = this["id"] || "0";
	var _cid = this["cid"] || "";
	alert(_id)
	// var _grid = this["grid"] || "";
	var form = new Ext.form.FormPanel({
		id : "productsFormPanel",
		title : "供求基本信息",
		layout : "column",
		region : "center",
		frame : true,
		height : 150,
		// width:"50%",
		bodyStyle : "padding:2px 2px 0",
		labelAlign : "right",
		labelWidth : 80,
		autoScroll : true,
		collapsed : false,
		items : [{
			columnWidth : 0.5,
			layout : "form",
			items : [{
						xtype : "hidden",
						name : "id",
						dataIndex : "id"
					},
					{
						xtype : "hidden",
						name : "companyId",
						value : _cid
					},new ast.ast1949.CategoryCombo({
								categoryCode : Context.CATEGORY["productsTypeCode"],
								fieldLabel : "<span style='color:red'>* </span>供求类型",
								name : "productsTypeCode"
							}), parentCombo, {
						xtype : "textfield",
						name : "realTime",
						fieldLabel : "真正时间",
						tabIndex : 1,
						format : 'Y-m-d',
						anchor : "95%",
						disabled : true
					}, {
						xtype : "textfield",
						name : "checkTime",
						fieldLabel : "审核时间",
						tabIndex : 1,
						format : 'Y-m-d',
						anchor : "95%",
						disabled : true
					}]
		}, {
			columnWidth : 0.5,
			layout : "form",
			items : [
			{
				xtype : "checkbox", // 添加到某个分类,复制到某分类
				name : "isRecommendToMagazine",
				fieldLabel : "<span style='color:red' >推荐到杂志?</span>",
				tabIndex : 1
			},
			{
			xtype : "combo",
			id : "categoryProductsAssistCode2",
			displayField:"categoryProductsAssistCode2",
			hiddenName:"categoryProductsAssistCode",
			hiddenId:"categoryProductsAssistCode",
			valueField : "categoryProductsAssistCode",
			fieldLabel : "辅助类别",
			tabIndex : 1,
			mode: 'local',
			anchor : "95%",
			forceSelection : true,
			editable :false,
			triggerAction: 'all',
			emptyText :'请选择...',
			store:new Ext.data.JsonStore({
				autoLoad:false,
				url:Context.ROOT+Context.PATH + "/admin/categoryproducts/child.htm",
				fields:[{name:"categoryProductsAssistCode2",mapping:"text"},
				{name:"categoryProductsAssistCode",mapping:"data"}]
			}),
			mode:'local',
			triggerAction:'all'
			},
			{
				xtype : "textfield",
				id : "account",// add by rolyer 2010.03.05
				name : "account",
				fieldLabel : "发布者(Email)",
				tabIndex : 1,
				anchor : "95%",
				disabled : true
			}]
		}, {
			columnWidth : 1,
			layout : "form",
			items : [{
						xtype : "textfield",
						id : "title", // add by rolyer 2010.03.05
						name : "title",
						fieldLabel : "<span style='color:red'>* </span>信息标题",
						tabIndex : 1,
						allowBlank : false,
						anchor : "97%",
						blankText : "信息标题不能为空"
					}, {
						layout : "fit",
						xtype : "panel",
						anchor : "97%",
						tbar : ["<b>供求详情</b>", {
							text : "从模板选择",
							handler : function() {
								var grid = ast.ast1949.admin.products
										.descriptionGrid();
								var tmpwin = new Ext.Window({
											id : "descriptionTemplateWin",
											title : "选择模板",
											layout : "border",
											modal : true,
											maximizable : true,
											// layout:"fit",
											width : 450,
											height : 300,
											items : [{
														layout : "fit",
														region : "center",
														items : [grid]
													}]
										});
								tmpwin.show();
							}
						}],
						bbar : [],
						items : [{
									xtype : "textarea",
									name : "details",
									id : "details",
									height : 120,
									anchor : "100%"
								}]
					}, new Ext.form.ComboBox({
								store : new Ext.data.JsonStore({
									root : "checkStatus",
									fields : ['k', 'v'],
									data :ast.ast1949.statesdata.products
								}),

								displayField : 'v',
								valueField : "k",
								typeAhead : true,
								mode : 'local',
								forceSelection : true,
								triggerAction : 'all',
								selectOnFocus : true,

								fieldLabel : "审核状态",
								hiddenId : "checkStatus",
								hiddenName : "checkStatus",
								emptyText : "请选择审核状态",
								anchor : "97%",
								listeners : {
									"change" : function(formfield, newValue,
											oldValue) {
										if ("2" == newValue) {
											Ext.getCmp("checkStatusReson")
													.setVisible(true);
										} else {
											Ext.getCmp("checkStatusReson")
													.setVisible(false);
										}
									}
								}

							}), {
						xtype : "textarea",
						name : "unpassReason",
						id : "unpassReason",
						fieldLabel : "未通过原因",
						tabIndex : 1,
						height : 40,
						anchor : "97%"
					}, {
						xtype : 'radiogroup',
						fieldLabel : "免审核发布审核",
						horizontal : false,
						items : [new Ext.form.Radio({
											name : "uncheckedCheckStatus",
											inputValue : "1",
											boxLabel : "审核"
										}), new Ext.form.Radio({
											name : "uncheckedCheckStatus",
											inputValue : "0",
											boxLabel : "未审核"
										})]
					}, {
						xtype : "textfield",
						name : "adminTags",
						fieldLabel : "后台标签",
						tabIndex : 1,
						anchor : "97%"
					}, {
						xtype : "textfield",
						name : "companyTags",
						fieldLabel : "客户标签",
						tabIndex : 1,
						anchor : "97%"
					}]
		}, {
			columnWidth : 0.5,
			layout : "form",
			items : [
					// {
					// xtype : "textfield",
					// fieldLabel : "所在地",
					// allowBlank : false,
					// name : "title",
					// tabIndex : 1,
					// anchor : "95%"
					// },
					{
				xtype : "checkbox",
				name : "provideStatus",
				fieldLabel : "长期供货?",
				inputValue :1
			}
					// ,{
					// xtype : "combo",
					// fieldLabel : "信息有效期",
					// // allowBlank : false,
					// name : "enddate", //有效期计算出enddate
					// tabIndex : 1,
					// anchor : "95%"
					// }
			, {
				xtype : "numberfield",
				fieldLabel : "产品价格",
				// allowBlank : false,
				name : "price",
				tabIndex : 1,
				anchor : "95%"
			}, {
				xtype : "numberfield",
				fieldLabel : "数量",
				// allowBlank : false,
				name : "quantity",
				tabIndex : 1,
				anchor : "95%"
			}, {
				xtype : "textfield",
				fieldLabel : "货源地",
				// allowBlank : false,
				name : "source",
				tabIndex : 1,
				anchor : "95%"
			}, {
				xtype : "textfield",
				fieldLabel : "来源产品",
				// allowBlank : false,
				name : "origin",
				tabIndex : 1,
				anchor : "95%"
			}, {
				xtype : "textfield",
				fieldLabel : "颜色",
				// allowBlank : false,
				name : "color",
				tabIndex : 1,
				anchor : "95%"
			}, {
				xtype : "checkbox",
				name : "isPause",
				fieldLabel : "暂停发布?",
				inputValue :1
			}
//			, {
//				xtype : "textfield",
//				fieldLabel : "现货物所在地",
//				name : "location",
//				tabIndex : 1,
//				anchor : "95%"
//			}, {
//				xtype : "textfield",
//				fieldLabel : "信息有效期",
//				name : "totalQuantity",
//				tabIndex : 1,
//				anchor : "95%"
//			}
			]
		}, {
			columnWidth : 0.5,
			layout : "form",
			items : [{
						xtype : "textfield",
						fieldLabel : "供货总量",
						name : "totalQuantity",
						tabIndex : 1,
						anchor : "95%"
					}, {
						xtype : "checkbox",
						name : "isShowInPrice",
						fieldLabel : "显示成报价?",
						inputValue: 1
					}, {
						xtype : "textfield",
						fieldLabel : "计量单位",
						name : "quantityUnit",
						tabIndex : 1,
						anchor : "95%"
					}, {
						xtype : "textfield",
						fieldLabel : "外观",
						name : "appearance",
						tabIndex : 1,
						anchor : "95%"
					}, {
						xtype : "textfield",
						fieldLabel : "产品规格",
						name : "specification",
						tabIndex : 1,
						anchor : "95%"
					}, {
						xtype : "textfield",
						fieldLabel : "杂质(物)含量",
						name : "impurity",
						tabIndex : 1,
						anchor : "95%"
					}, {
						xtype : "textfield",
						fieldLabel : "此废料可用于",
						name : "useful",
						tabIndex : 1,
						anchor : "95%"
					}, new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["manufactureCode"],
						fieldLabel : "<span style='color:red'>* </span>加工说明",
						name : "manufacture"
					})]
		}],
		buttons : [{
					id : "save",
					text : "修改"
				}, {
					id : "close",
					text : "关闭"
				}]
	});
	var reader = [{
				name : "id",
				mapping : "productsDO.id"
			}, {
				name : "account",
				mapping : "productsDO.account"
			},// 发布帐号名
			{
				name : "productsTypeCode",
				mapping : "productsDO.productsTypeCode"
			},// 供求类型
			
			
			{
				name : "categoryProductsMainCode1",
				mapping : "productsDO.categoryProductsMainCode"
			},// 主类别
			{
				name : "categoryProductsMainCode",
				mapping : "categoryProductsMainCodeName"
			},// 主类别名称
			{
				name : "categoryProductsAssistCode1",
				mapping : "productsDO.categoryProductsAssistCode"
			},// 辅助类别
			{
				name : "categoryProductsAssistCode",
				mapping : "categoryProductsAssistCodeName"
			},// 辅助类别名称
			
			
			{
				name : "sourceTypeCode",
				mapping : "productsDO.sourceTypeCode"
			},// 信息来源
			{
				name : "title",
				mapping : "productsDO.title"
			},// 标题
			{
				name : "details",
				mapping : "productsDO.details"
			},// 详细描述
			{
				name : "checkStatus",
				mapping : "productsDO.checkStatus"
			},// 审核
			// 审核未通过原因
			{
				name : "unpassReason",
				mapping : "productsDO.unpassReason"
			}, {
				name : "uncheckedCheckStatus",
				mapping : "productsDO.uncheckedCheckStatus"
			},// 审核未审核

			{
				name : "provideStatus",
				mapping : "productsDO.provideStatus"
			},// 长期供货
			{
				name : "totalQuantity",
				mapping : "productsDO.totalQuantity"
			},// 供货总量
			{
				name : "",
				mapping : ""
			},// 信息有效期 有效期减去刷新时间
			{
				name : "isShowInPrice",
				mapping : "productsDO.isShowInPrice"
			},// 是否显示在企业报价
			{
				name : "price",
				mapping : "productsDO.price"
			},// 产品价格
			{
				name : "quantityUnit",
				mapping : "productsDO.quantityUnit"
			},// 计量单位
			{
				name : "quantity",
				mapping : "productsDO.quantity"
			},// 数量
			{
				name : "specification",
				mapping : "productsDO.specification"
			},// 产品规格
			{
				name : "source",
				mapping : "productsDO.source"
			},// 货源地
			{
				name : "appearance",
				mapping : "productsDO.appearance"
			},// 外观
			// {name:"",mapping:""},//
			{
				name : "origin",
				mapping : "productsDO.origin"
			},// 来源产品
			{
				name : "impurity",
				mapping : "productsDO.impurity"
			},// 杂质含量
			{
				name : "color",
				mapping : "productsDO.color"
			},// 颜色
			{
				name : "useful",
				mapping : "productsDO.useful"
			},// 用途
			{
				name : "isPause",
				mapping : "productsDO.isPause"
			},// 暂停发布
			{
				name : "manufacture",
				mapping : "productsDO.manufacture"
			},// 加工说明
			{
			  name: "adminTags",
			  mapping :"adminTags"
			}, //客户标签
			{ 
			  name: "companyTags",
			  mapping :"companyTags"
			}//后台标签
	];
	
	var _store = new Ext.data.JsonStore({
				
				root : "records",
				fields : reader,
				url : Context.ROOT + Context.PATH
						+ "/admin/products/init.htm?id=" + _id,
				autoLoad : true,
				listeners : {
					"datachanged" : function() {

						var record = _store.getAt(0);

						if (record == null) {
							Ext.MessageBox.alert(Context.MSG_TITLE,
									"数据加载错误,请联系管理员!");
							// win.close();
						} else {
							form.getForm().loadRecord(record);
							// 处理类别选项
							Ext.get("categoryProductsMainCode").dom.value=record.get("categoryProductsMainCode1");
							Ext.get("categoryProductsAssistCode").dom.value=record.get("categoryProductsAssistCode1");
						}
					}
				}
			});
	return form;
}

// ast.ast1949.admin.products.loadProductsRecord=function(_cfg){
// if(_cfg == null){
// _cfg = {};
// }
//
//
//
// }

ast.ast1949.admin.products.descriptionGrid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm, {
				header : "描述模板内容",
				width : 50,
				sortable : false,
				dataIndex : "content"
			}, {
				header : "模板类别",
				width : 50,
				sortable : false,
				dataIndex : "templateName"
			}]);

	var tbar = [{
		text : '选择',
		tooltip : '选择一个描述模板',
		iconCls : 'add',
		handler : function() {
			var g = Ext.getCmp("descriptionTemplateGrid");
			var sels = g.getSelections();
			if (sels.length != 1) {
				Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
			} else {
				// alert(sels[0].get("content"))
				Ext.get("details").dom.value = Ext.get("details").dom.value
						+ sels[0].get("content");
				Ext.getCmp("descriptionTemplateWin").close();
			}
		}
	}]

	var reader = [{
				name : "id",
				mapping : "descriptionTemplateDO",
				convert : function(v) {
					return v.id;
				}
			}, {
				name : "content",
				mapping : "descriptionTemplateDO",
				convert : function(v) {
					return v.content;
				}
			}, {
				name : "gmtCreated",
				mapping : "descriptionTemplateDO",
				convert : function(v) {
					return v.gmtCreated;
				}
			}, {
				name : "gmtModified",
				mapping : "descriptionTemplateDO",
				convert : function(v) {
					return v.gmtModified;
				}
			}, "templateName"];
	var storeUrl = Context.ROOT + Context.PATH
			+ "/admin/descriptiontemplate/query.htm";

	var title = "供求详细信息模板管理";

	var grid = new ast.ast1949.StandardGridPanel({
				id : "descriptionTemplateGrid",
				sm : sm,
				cm : cm,
				reader : reader,
				storeUrl : storeUrl,
				baseParams : {
					"dir" : "DESC",
					"sort" : "id"
				},
				title : title,
				tbar : tbar
			});

	return grid;
}
