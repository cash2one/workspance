Ext.namespace("ast.ast1949.admin.keywordsrank");
// 定义一个添加,编辑的父窗体类,继承自Window

ast.ast1949.admin.keywordsrank.infoKeywordSrankForm = Ext.extend(Ext.Window, {
	_form : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.apply(this, _cfg);

		var _productId = this["productId"] || "";
		var _productTitle = this["productTitle"] || "";
		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";
		var listKeywordSrank = new ast.ast1949.admin.keywordsrank.listKeywordSrank({
				productId:_productId,
				productTitle:_productTitle
			});
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
				items : [ {
					columnWidth : .5,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [ {
						xtype : "hidden",
						name : "id",
						dataIndex : "id"
					}, {
						xtype : "hidden",
						name : "productId",
						value:_productId
					},{
						xtype : "textfield",
						fieldLabel : "<span style='color:red'>* </span>搜索关键字",
						allowBlank : false,
						name : "name",
						tabIndex : 1,
						anchor : "95%",
						blankText : "搜索关键字不能为空"
					},{
						xtype:"datefield",
						format : 'Y-m-d', //H:i:s
						fieldLabel : "<span style='color:red'>* </span>开始时间",
						allowBlank : false,
						name : "startTime",
						tabIndex : 1,
						anchor : "95%",
						value:new Date(),
						blankText : "开始时间不能为空",
						renderer : function (value,metadata,record,rowIndex,colIndex,store){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
						}
					}]
				}, {
					columnWidth : .5,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [{
				        xtype: 'radiogroup',
				        fieldLabel : "<span style='color:red'>* </span>审核",
						horizontal:false,
						anchor : "95%",
						items :[
								{inputValue:"1",boxLabel:"是",name:"isChecked",checked:true},
								{inputValue:"0",boxLabel:"否",name:"isChecked"}
						       ]
					},{
						xtype:"datefield",
						format : 'Y-m-d', // H:i:s
						fieldLabel : "<span style='color:red'>* </span>结束时间",
						allowBlank : false,
						name : "endTime",
						tabIndex : 1,
						anchor : "95%",
						blankText : "结束时间不能为空",
						renderer : function (value,metadata,record,rowIndex,colIndex,store){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
						}
					}]
				} , {
					columnWidth : 1,
					layout : "form",
					items : [listKeywordSrank]
				} ]
			}],
			buttons : [ {
				id : "savekeywordsrank",
				text : "提交",
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

		ast.ast1949.admin.keywordsrank.infoKeywordSrankForm.superclass.constructor.call(
				this, {
					title : _title,
					closeable : true,
					width : 700,
					autoHeight : false,
					height:300,
					autoScroll : true,
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
				msg : "带红色<span style='color:red'>*</span>项不能为空",
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
		// alert(_form.getValues());
	this.fireEvent("saveFailure", _form, _action, _form.getValues());
},
initFocus : function() {
}
});