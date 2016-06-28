Ext.namespace("ast.ast1949.admin.descriptionTemplate");
// 定义一个添加,编辑的父窗体类,继承自Window
ast.ast1949.admin.descriptionTemplate.InfoFormWin = Ext.extend(Ext.Window, {
	_form : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.apply(this, _cfg);


		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";

		this._form = new Ext.form.FormPanel( {
			region : "center",
			frame : true,
			bodyStyle : "padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
			width : "100%",
			items : [ {
				xtype : "fieldset",
				layout : "column",
				autoHeight : true,
				title : "后台供求详细描述模板",
				items : [ {
					columnWidth : 1,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ {
						xtype : "hidden",
						name : "id",
						dataIndex : "id"
					},{
						xtype : "textarea",
						fieldLabel : "描述模板内容",
						allowBlank : false,
						name : "content",
						tabIndex : 1,
						anchor : "95%",
						blankText : "描述模板内容"
					}, new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["templateCode"],
						fieldLabel : "描述模板类别",
						emptyText: "请选择描述模板类别",
						anchor :"95%",
						name : "templateCode"
					})
					]
				} ]
			} ],
			buttons : [ {
				id : "save",
				text : "保存",
				hidden : _notView
			}, {
				id : "cancel",
				text : "取消",
				hidden : _notView
			}, {
				id : "close",
				text : "关闭",
				hidden : _isView
			} ]
		});

		ast.ast1949.admin.descriptionTemplate.InfoFormWin.superclass.constructor
				.call(this, {
					title : _title,
					closeable : true,
					width : 500,
					autoHeight : true,
					modal : true,
					border : false,
					plain : true,
					layout : "form",
					items : [ this._form ]
				});
		this.addEvents("saveSuccess", "saveFailure", "submitFailure");
	},
	submit : function(_url) {
		if (this._form.getForm().isValid()) {
			this._form.getForm().submit( {
				url : _url,
				method : "post",
				success : this.onSaveSuccess,
				failure : this.onSaveFailure,
				scope : this
			});
		} else {
			Ext.MessageBox.show( {
				title : Context.MSG_TITLE,
				msg : "验证未通过",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
		}
	},
	loadRecord : function(_record) {
		this._form.getForm().loadRecord(_record);

	},
	onSaveSuccess : function(_form, _action) {
		this.fireEvent("saveSuccess", _form, _action, _form.getValues());
	},
	onSaveFailure : function(_form, _action) {
	this.fireEvent("saveFailure", _form, _action, _form.getValues());
},
initFocus : function() {
	var f = this._form.getForm().findField("content");
	f.focus(true, true);
}
});