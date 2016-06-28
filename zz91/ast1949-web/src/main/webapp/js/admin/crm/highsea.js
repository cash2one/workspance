Ext.namespace("ast.ast1949.crm.highsea");

ast.ast1949.crm.highsea.FIELD = [{
	name: "id",
	mapping: "crmOutLog.id"
},
{
	name: "companyId",
	mapping: "crmOutLog.companyId"
},
{
	name: "oldCsAccount",
	mapping: "crmOutLog.oldCsAccount"
},
{
	name: "operator",
	mapping: "crmOutLog.operator"
},
{
	name: "status",
	mapping: "crmOutLog.status"
},
{
	name: "gmtCreated",
	mapping: "crmOutLog.gmtCreated"
},
{
	name: "mobile",
	mapping: "mobile"
},
{
	name: "email",
	mapping: "email"
},
{
	name: "account",
	mapping: "account"
}];

ast.ast1949.crm.highsea.Grid = Ext.extend(Ext.grid.GridPanel, {
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);

		var _store = new Ext.data.JsonStore({
			root: "records",
			totalProperty: "totalRecords",
			remoteSort: true,
			fields: ast.ast1949.crm.highsea.FIELD,
			url: Context.ROOT + Context.PATH + "/crm/highsea/query.htm",
			autoLoad: false
		});

		var _sm = new Ext.grid.CheckboxSelectionModel({});
		var _cm = new Ext.grid.ColumnModel([_sm, {
			header: "登录帐号",
			width: 130,
			sortable: false,
			dataIndex: "account"
		},
		{
			header: "公海类型",
			width: 100,
			sortable: false,
			dataIndex: "status",
			renderer: function(value, metadata, record, rowindex, colindex, store) {
				if (value == "1") {
					return "捞入";
				}else if(value == "2"){
					return "分配";
				} else {
					return "丢公海";
				}
			}
		},
		{
			header: "手机",
			width: 100,
			sortable: false,
			dataIndex: "mobile"
		},
		{
			header: "邮件",
			width: 300,
			sortable: false,
			dataIndex: "email"
		},{
			header: "丢入者",
			width: 100,
			sortable: false,
			dataIndex: "oldCsAccount"
		},
		{
			header: "操作人帐号",
			width: 100,
			sortable: false,
			dataIndex: "operator"
		},
		{
			header: "操作时间时间",
			width: 150,
			sortable: false,
			dataIndex: "gmtCreated",
			renderer: function(value, metadata, record, rowIndex, colIndex, store) {
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else {
					return "";
				}
			}
		}]);

		var c = {
			iconCls: "icon-grid",
			loadMask: Context.LOADMASK,
			store: _store,
			sm: _sm,
			cm: _cm,
			bbar: new Ext.PagingToolbar({
				pageSize: Context.PAGE_SIZE,
				store: _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg: '没有可显示的记录',
				beforePageText: '第',
				afterPageText: '页,共{0}页',
				paramNames: {
					start: "startIndex",
					limit: "pageSize"
				}
			}),
			tbar: [{
				text: "修改",
				iconCls: "edit",
				handler: function() {
					if (_sm.getCount() == 0) Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
					else var row = grid.getSelections();
					window.open(Context.ROOT + Context.PATH + "/crm/company/detail.htm?companyId=" + row[0].get("id"));
				}
			},
			"-", {
				text: "客服小记",
				handler: function() {
					var rows = grid.getSelectionModel().getSelections();
					if (rows.length > 1) {
						//询问是否要一次打开全部客户信息
						Ext.MessageBox.confirm(Context.MSG_TITLE, "同时打开多个客户信息可能会造成浏览器假死<br />您确定要打开" + rows.length + "个客户信息吗？",
						function(btn) {
							if (btn != "yes") {
								return false;
							}
							for (var i = 0; i < rows.length; i++) {
								window.open(Context.ROOT + Context.PATH + "/crm/cs/detail.htm?companyId=" + rows[i].get("id") + "&star=" + rows[i].get("star") + "&companyName=" + encodeURI(rows[i].get("name")));
							}
						});
					} else {
						for (var i = 0; i < rows.length; i++) {
							window.open(Context.ROOT + Context.PATH + "/crm/cs/detail.htm?companyId=" + rows[i].get("id") + "&star=" + rows[i].get("star") + "&companyName=" + encodeURI(rows[i].get("name")));
						}
					}
				}
			}]
		};
		var grid = this;
		ast.ast1949.crm.highsea.Grid.superclass.constructor.call(this, c);
	},
	load: function(targetDate) {
		this.getStore().baseParams["gmtStatDate"] = targetDate;
		this.getStore().reload();
	}
});

ast.ast1949.crm.highsea.SearchForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid: null,
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B || {};

		var c = {
			bodyStyle: "padding:2px 2px 0",
			labelAlign: "right",
			labelWidth: 80,
			autoScroll: true,
			layout: "column",
			items: [{
				columnWidth: 1,
				layout: "form",
				defaults: {
					anchor: "95%",
					xtype: "textfield",
					labelSeparator: ""
				},
				items: [{
					fieldLabel: "帐号：",
					listeners: {
						"change": function(field, newvalue, oldvalue) {
							if (newvalue == "") {
								B["account"] = undefined;
							} else {
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},
				{
					fieldLabel: "邮箱：",
					listeners: {
						"change": function(field, newvalue, oldvalue) {
							if (newvalue == "") {
								B["email"] = undefined;
							} else {
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},
				{
					fieldLabel: "手机号码：",
					listeners: {
						"change": function(field, newvalue, oldvalue) {
							if (newvalue == "") {
								B["mobile"] = undefined;
							} else {
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons: [{
				text: "按条件搜索",
				handler: function(btn) {
					_store.reload({
						params: {
							"startIndex": 0,
							"pageSize": Context.PAGE_SIZE
						}
					});
				}
			}]
		};

		ast.ast1949.crm.highsea.SearchForm.superclass.constructor.call(this, c);

	}
});
