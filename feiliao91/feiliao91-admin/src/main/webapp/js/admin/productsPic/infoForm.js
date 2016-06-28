Ext.namespace("ast.ast1949.admin.productsPic");
// 定义一个添加,编辑的父窗体类,继承自Window
ast.ast1949.admin.productsPic.InfoFormWin = Ext.extend(Ext.Window, {
	_form : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.apply(this, _cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";

		var ablumsCombo = new Ext.form.ComboBox({
			fieldLabel : "相册类别",
			id : "combo-albumId",
			name : "combo-albumId",
			hiddenName : "albumId",
			hiddenId : "albumId",
			mode : "local",
			xtype : "combo",
			readOnly : true,
			selectOnFocus : true,
			triggerAction : "all",
			emptyText : "选择相册类别",
			anchor : "95%",
			tabIndex : 1,
			allowBlank : false,
			blankText : "",
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			tpl : "<tpl for='.'><div style='height:280px' id='place-combo'></div></tpl>",
			onSelect : Ext.emptyFn
		});

		var tree;
		ablumsCombo.on("expand", function() {
			if (tree == null) {
				tree = ast.ast1949.admin.productsAlbums.treePanel({
							el : "place-combo",
							rootData : ""
						});
				tree.getRootNode().disable();
				tree.on('click', function(node) {
							ablumsCombo.setValue(node.text);
							ablumsCombo.collapse();
							Ext.get("albumId").dom.value = node.attributes["data"];
						});
			}
		});
		this._form = new Ext.form.FormPanel({
			region : "center",
			frame : true,
			bodyStyle : "padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 120,
			width : "100%",
			items : [{
				xtype : "fieldset",
				layout : "column",
				autoHeight : true,
				title : "图片管理",
				items : [{
					columnWidth : 1,
					layout : "form",
					defaults : {
						disabled : _notView
					},
					items : [{
								xtype : "hidden",
								name : "id",
								dataIndex : "id"
							}, ablumsCombo, {
								xtype : 'checkbox',
								fieldLabel : "是否为封面",
								name:'isCover',
								inputValue:"1"
							},{
								xtype : 'checkbox',
								fieldLabel : "是否为客户默认显示",
								name:'isDefault',
								inputValue:"1"
							} , {
								xtype : "hidden",
								name : "picAddress",
								dataIndex : "picAddress"
							},  {
								xtype : "panel",
								items : [{
									html : "<div id='file_upload' style='border:1px #999 solid;padding:5px;'>点击上传图片 "
											+ "<a id='preview_img_a' href='#' target='_blank' >查看原图</a><br />"
											+ "<img id='preview_img' src='#' alt='点击上传图片' height='50' width='100%'/></div>"
								}]
							}]
				}]
			}],
			buttons : [{
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
					}]
		});

		ast.ast1949.admin.productsPic.InfoFormWin.superclass.constructor.call(
				this, {
					title : _title,
					closeable : true,
					width : 600,
					autoHeight : true,
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
		Ext.get("combo-albumId").dom.value=_record.get("albumName");
		Ext.get("preview_img_a").dom.href = ProductsPic.upload_url + "/"
				+ ProductsPic.model + "/" + ProductsPic.filetype + "/"
				+ _record.get("picAddress");
		Ext.get("preview_img").dom.src = ProductsPic.upload_url + "/"
				+ ProductsPic.model + "/" + ProductsPic.filetype + "/"
				+ _record.get("picAddress");
	},
	onSaveSuccess : function(_form, _action) {
		this.fireEvent("saveSuccess", _form, _action, _form.getValues());
	},
	onSaveFailure : function(_form, _action) {
		this.fireEvent("saveFailure", _form, _action, _form.getValues());
	},
	initFocus : function() {
		var f = this._form.getForm().findField("picAddress");
		f.focus(true, true);
	}
});