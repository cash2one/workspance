Ext.namespace("ast.ast1949.admin.exhibit");

// 定义变量
var _C = new function() {
	this.RESULT_GRID = "resultGrid";
	this.EDIT_FORM = "exhibitEditForm";
	this.EDIT_WIN = "exhibitEditWin";
	this.ADD_FORM = "exhibitAddForm";
	
}

// 列表
ast.ast1949.admin.exhibit.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);

		var _fields = this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
					root : "records",
					totalProperty : 'totalRecords',
					remoteSort : true,
					fields : _fields,
					url : _url,
					autoLoad : true
				});

		var _sm = new Ext.grid.CheckboxSelectionModel({
					listeners : {
						selectionchange : function(sm) {
							if (sm.getCount()) {
								Ext.getCmp("editButton").enable();
								Ext.getCmp("deleteButton").enable();
							} else {
								Ext.getCmp("editButton").disable();
								Ext.getCmp("deleteButton").disable();
							}
						}
					}
				});
		var _cm = new Ext.grid.ColumnModel([_sm, {
					header : "会展名称",
					sortable : false,
					width : 350,
					dataIndex : "name"
				}, {
					header : "所属类别",
					width : 200,
					sortable : false,
					dataIndex : "exhibitCategoryName"
				}, {
					header : "所在板块",
					width:200,
					sortable : false,
					dataIndex : "plateCategoryName"
				}, {
					header : "开展日期",
					width:200,
					sortable : false,
					dataIndex : "startTime",
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (value != null) {
							return Ext.util.Format.date(new Date(value.time),
									'Y-m-d');
						}
					}
				}, {
					header : "结束日期",
					sortable : false,
					dataIndex : "endTime",
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (value != null) {
							return Ext.util.Format.date(new Date(value.time),
									'Y-m-d');
						}
					}
				}]);

		var c = {
			iconCls : "icon-grid",
			viewConfig : {
				autoFill : true
			},
			store : _store,
			autoExpandColumn:5,
			sm : _sm,
			cm : _cm,
			tbar : this.mytoolbar,
			bbar : new Ext.PagingToolbar({
						pageSize : Context.PAGE_SIZE,
						store : _store,
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条记录,共 {2} 条',
						emptyMsg : '没有可显示的记录',
						beforePageText : '第',
						afterPageText : '页,共{0}页',
						paramNames : {
							start : "startIndex",
							limit : "pageSize"
						}
					})
		};

		ast.ast1949.admin.exhibit.ResultGrid.superclass.constructor.call(this,
				c);
	},
	listRecord : Ext.data.Record.create([{
				name : "id",
				mapping : "exhibitDO.id"
			}, {
				name : "name",
				mapping : "exhibitDO.name"
			}, {
				name : 'startTime',
				mapping : 'exhibitDO.startTime'
			}, {
				name : 'endTime',
				mapping : 'exhibitDO.endTime'
			}, {
				name : 'priceUnit',
				mapping : 'exhibitDO.priceUnit'
			}, "exhibitCategoryName", "plateCategoryName"]),
		mytoolbar : [
		     {
					iconCls : "add",
					text : "添加",
					handler : function(btn) {
						ast.ast1949.admin.exhibit.addFormWin();
					}
			 }, "-", {
					iconCls : "edit",
					id : "editButton",
					text : "修改",
					disabled : true,
					handler : function(btn) {
						var grid = Ext.getCmp(_C.RESULT_GRID);
						var row = grid.getSelections();
						if (row.length > 1) {
							Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "最多只能选择一条记录！",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						} else {
							var _id = row[0].get("id");
							ast.ast1949.admin.exhibit
									.editFormWin(_id)
						}
					}
			 }, "-", {
					iconCls : "delete",
					id : "deleteButton",
					text : "删除",
					disabled : true,
					handler : function(btn) {
						Ext.MessageBox.confirm(Context.MSG_TITLE,
								'您确定要删除所选记录吗?', doDelete);
					}
			},
			"->",
			{
				xtype : "textfield",
				id : "name",
				name : "name",
				width : 160,
				listeners:{
					"blur":function(btn) {
						var resultgrid = Ext.getCmp(_C.RESULT_GRID);
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {
							"name" : Ext.get("name").dom.value
						};
						// 定位到第一页
						resultgrid.store.reload({
									params : {
										"startIndex" : 0,
										"pageSize" : Context.PAGE_SIZE
									}
								});
					}
				}
			},
			{iconCls:"query"}
		]
});

// 添加修改展会表单
ast.ast1949.admin.exhibit.editForm = Ext.extend(Ext.form.FormPanel, {
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		
		var categoryProvinceCombo1 = new Ext.form.ComboBox({
			fieldLabel : "<span style='color:red'>* </span>省市",
			hiddenName : "areaCode",
			hiddenId : "areaCode",
			id:"comboProvince",
			name:"comboProvince",
			mode : "local",
			xtype : "combo",
			readOnly : true,
			selectOnFocus : true,
			allowBlank : false,
			triggerAction : "all",
//			emptyText : "不选为根节点",
			emptyText : "请选择",
			anchor : "95%",
			tabIndex : 1,
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			tpl : "<tpl for='.'><div style='height:280px' id='category-province-combo1'></div></tpl>",
			onSelect : Ext.emptyFn
		});
		
		var tree;
		categoryProvinceCombo1.on("expand", function() {
			if (tree == null) {
				tree = ast.ast1949.admin.category.provincesTreePanel({
							el : "category-province-combo1",
							rootData : ""
						});
				tree.getRootNode().disable();
				tree.on('click', function(node) {
					categoryProvinceCombo1.setValue(node.text);
					categoryProvinceCombo1.collapse();
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

		var c = {
			height:600,
			labelAlign : "right",
			labelWidth : 60,
			layout : "column",
			bodyStyle : "padding:5px 0 0",
			frame : true,
			items : [{
				columnWidth : 0.5,
				layout : "form",
				defaults : {
					anchor : "90%",
					xtype : "textfield",
					labelSeparator : ""
				},
				items : [{
							xtype : "hidden",
							id : "id",
							name : "id"
						}, {
							xtype : "textfield",
							fieldLabel : "展会名称:",
							name : "name"
						}, {
							xtype : "datefield",
							fieldLabel : "开展日期:",
							name : "startTime",
							format : 'Y-m-d'
						}, new ast.ast1949.CategoryCombo({
							categoryCode : Context.CATEGORY["plateCategoryCode"],
							fieldLabel : "模块类别",
							emptyText : "请选择模块类别",
							anchor : "95%",
							itemCls : "required",
							name : "plateCategoryCode"
						})]
			}, {
				columnWidth : 0.5,
				layout : "form",
				defaults : {
					anchor : "95%",
					xtype : "textfield",
					labelSeparator : ""
				},
				items : [categoryProvinceCombo1, {
							xtype : "datefield",
							fieldLabel : "结束日期:",
							format : 'Y-m-d',
							name : "endTime"
						}, new ast.ast1949.CategoryCombo({
							categoryCode : Context.CATEGORY["exhibitCategoryCode"],
							fieldLabel : "展会类别",
							emptyText : "请选择展会类别",
							anchor : "95%",
							itemCls : "required",
							name : "exhibitCategoryCode"
						})]
			},{
				columnWidth : 1,
				layout : "form",
				defaults : {
					anchor : "99%",
					xtype : "textfield",
					labelSeparator : ""
				},
				items : [
//						{
//							xtype : "hidden",
//							name : "content",
//							id : "content"
//						}, 
							{
							xtype: "ckeditor",
							fieldLabel: "详细内容：",
							id: "content",
							name: "content",
							CKConfig: { //CKEditor的基本配置，详情配置请结合实际需要。
							    toolbar:"Full",
							    height : 320,
							    width: "98%"
							}
							
//							xtype : "panel",
//							id : "contentpanel",
//							html : '<iframe id="contentiframe" src="'
//									+ CONST.WEB_JS_SERVER
//									+ '/edit/editor.html?id=content&cHeight=100&ReadCookie=0" frameBorder="0" marginHeight="0" marginWidth="0" scrolling="No" width="700" height="260"></iframe>'
						}
						]
			}],
			buttons : [{
						text : "确定",
						handler : this.save,
						scope : this
					}, {
						text : "关闭",
						handler : function() {
							Ext.getCmp(_C.EDIT_WIN).close();
						},
						scope : this
					}]
		};


		ast.ast1949.admin.exhibit.editForm.superclass.constructor.call(this, c);
	},
	mystore : null,
	loadRecords : function(id) {
		var _fields = [{
					name : "id",
					mapping : "exhibitDO.id"
				}, {
					name : "name",
					mapping : "exhibitDO.name"
				}, {
					name : "startTime",
					mapping : "exhibitDO.startTime",
					convert : function(value) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d');
					}
				}, {
					name : "endTime",
					mapping : "exhibitDO.endTime",
					convert : function(value) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d');
					}
				}, {
					name : "plateCategoryCode",
					mapping : "exhibitDO.plateCategoryCode"
				}, {
					name : "exhibitCategoryCode",
					mapping : "exhibitDO.exhibitCategoryCode"
				}, {
					name : "areaCode",
					mapping : "exhibitDO.areaCode"
				}, {
					name : "content",
					mapping : "exhibitDO.content"
				}, {
					name : "areaName",
					mapping : "areaName"
				}, {
					name : "exhibitCategoryName",
					mapping : "exhibitCategoryName"
				}, {
					name : "plateCategoryName",
					mapping : "plateCategoryName"
				}];
		var form = this;
		var store = new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + Context.PATH + "/admin/exhibit/init.htm",
			baseParams : {
				"id" : id
			},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					//	alert(record.get("areaName"));
						 Ext.get("comboProvince").dom.value =record.get("areaName");
						document.getElementById("contentiframe").src = CONST.WEB_JS_SERVER
								+ '/edit/editor.html?id=content&cHeight=100&ReadCookie=0';

					}
				}
			}
		})
	},

	saveUrl : Context.ROOT + Context.PATH + "/admin/exhibit/add.htm",
	save : function() {
		var _url = this.saveUrl;
		if (this.getForm().isValid()) {
			this.getForm().submit({
						url : _url,
						method : "post",
						type : "json",
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
	onSaveSuccess : function() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "操作成功！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
		ast.ast1949.admin.exhibit.resultGridReload();
		Ext.getCmp(_C.EDIT_WIN).close();
	},
	onSaveFailure : function() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "操作失败！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}

});

// 直接删除
function doDelete(_btn) {
	if (_btn != "yes")
		return;

	var grid = Ext.getCmp(_C.RESULT_GRID);

	var row = grid.getSelections();
	var _ids = new Array();
	for (var i = 0, len = row.length; i < len; i++) {
		var _id = row[i].get("id");
		_ids.push(_id);
	}
	/* 提交 */

	var conn = new Ext.data.Connection();
	conn.request({
				url : Context.ROOT + Context.PATH
						+ "/admin/exhibit/delete.htm?random=" + Math.random()
						+ "&ids=" + _ids.join(','),
				method : "get",
				scope : this,
				callback : function(options, success, response) {
					var a = Ext.decode(response.responseText);
					if (success) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "选定的记录已被删除!");
						grid.getStore().reload();
					} else {
						Ext.MessageBox.alert(Context.MSG_TITLE, "所选记录删除失败!");
					}
				}
			});
}

// 添加展会窗口
ast.ast1949.admin.exhibit.addFormWin = function() {

	var form = new ast.ast1949.admin.exhibit.editForm({
				id : _C.ADD_FORM,
				region : "center"
			});

	var win = new Ext.Window({
				id : _C.EDIT_WIN,
				title : "添加展会",
				width : "90%",
				modal : true,
				// autoHeight:true,
				// maximizable:true,
				items : [form]
			});
	win.show();
};

// 修改展会窗口
ast.ast1949.admin.exhibit.editFormWin = function(id) {

	var form = new ast.ast1949.admin.exhibit.editForm({
				id : _C.EDIT_FORM,
				region : "center",
				saveUrl : Context.ROOT + Context.PATH
						+ "/admin/exhibit/update.htm"
			});

	var win = new Ext.Window({
				id : _C.EDIT_WIN,
				title : "展会修改",
				width : "90%",
				modal : true,
				// autoHeight:true,
				// maximizable:true,
				items : [form]
			});
	form.loadRecords(id);
	win.show();
};

// 重新绑定Grid数据
ast.ast1949.admin.exhibit.resultGridReload = function() {
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	// 定位到第一页
	resultgrid.store.reload({
				params : {
					"startIndex" : 0,
					"pageSize" : Context.PAGE_SIZE
				}
			});
}