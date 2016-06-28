Ext.namespace("ast.ast1949.admin.friendLink");
// 定义一个添加,编辑的父窗体类,继承自Window
ast.ast1949.admin.friendLink.InfoFormWin = Ext.extend(Ext.Window, {
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
			id:"form1",
			labelWidth : 80,
			width : "100%",
			items : [ {
				xtype : "fieldset",
				layout : "column",
				autoHeight : true,
				title : "友情链接",
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
					}, {
						xtype : "textfield",
						fieldLabel : "网站名称",
						allowBlank : false,
						itemCls :"required",
						name : "linkName",
						tabIndex : 1,
						anchor : "95%",
						blankText : "网站名称不能为空"
					},  new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["linkCategoryCode"],
						fieldLabel : "网站类别",
						emptyText: "请选择网站类别",
						anchor :"95%",
						itemCls :"required",
						name : "linkCategoryCode"
					}), {
						xtype : "numberfield",
						fieldLabel : "长度",
						allowBlank : false,
						itemCls :"required",
						name : "width",
						tabIndex : 1,
						anchor : "95%",
						blankText : "长度不能为空"
					}, {
						xtype : "numberfield",
						fieldLabel : "高度",
						allowBlank : false,
						itemCls :"required",
						name : "height",
						tabIndex : 1,
						anchor : "95%",
						blankText : "高度不能为空"
					},{
						xtype : "textfield",
						fieldLabel : "排序",
						allowBlank : false,
						itemCls :"required",
						name : "showIndex",
						tabIndex : 2,
						anchor : "95%",
						blankText : "排序不能为空"
					},{
						xtype : "textfield",
						fieldLabel : "链接地址",
						allowBlank : false,
						itemCls :"required",
						emptyText:"(格式：http://www.zz91.com)",
						name : "link",
						tabIndex : 2,
						anchor : "95%",
						blankText : "链接地址不能为空"
					},{
						xtype : "hidden",
						fieldLabel : "文字颜色",
						allowBlank : false,
						itemCls :"required",
						name : "textColor",
						tabIndex : 2,
						anchor : "45%"
					},{
						xtype : "hidden",
						id:"picAddress",
						name : "picAddress",
						dataIndex : "picAddress"
					},{
						xtype:"tabpanel",
						id:"tabpanel1",
						columnWidth:1,
						activeTab:0,
						border:false,
						defaults:{
							height:100
						},
						items:[{
								title : "上传图片",
								xtype:"panel",
								html:"<div id='file_upload' style='border:0px #999 solid;padding:5px;'>点击上传图片 <br />" +
									"<img id='preview_img' src='' alt='' /></div>"
						}]
					}]
				}]
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
	
		ast.ast1949.admin.friendLink.InfoFormWin.superclass.constructor
				.call(this, {
					title : _title,
					closeable : true,
					width : 600,
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
		// 初始化区域列表框
		
		//图片显示的路径
		Ext.get("preview_img").dom.src=resourceUrl+"/"+_record.get("picAddress");

	},
	onSaveSuccess : function(_form, _action) {
		this.fireEvent("saveSuccess", _form, _action, _form.getValues());
	},
	onSaveFailure : function(_form, _action) {
	this.fireEvent("saveFailure", _form, _action, _form.getValues());
},
initFocus : function() {
	var f = this._form.getForm().findField("linkName");
	f.focus(true, true);
}
});