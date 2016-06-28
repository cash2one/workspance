Ext.namespace("ast.ast1949.admin.categoryGarden");
//定义一个添加,编辑的父窗体类,继承自Window
ast.ast1949.admin.categoryGarden.InfoFormWin = Ext.extend(Ext.Window, {
	_form : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.apply(this, _cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";

		var categoryCombo = new Ext.form.ComboBox({
			fieldLabel	: "选择省市",
			id			: "combo-areaCode",
			name		: "combo-areaCode",
			hiddenName	: "areaCode",
			hiddenId	: "areaCode",
			mode		: "local",
			xtype		: "combo",
			readOnly	: true,
			selectOnFocus:true,
			triggerAction: "all",
			emptyText	: "省市父类别",
			anchor		: "95%",
			tabIndex	: 1,
			allowBlank	: true,
			store		: new Ext.data.SimpleStore({fields:[],data:[[]]}),
			tpl			: "<tpl for='.'><div style='height:280px' id='category-combo'></div></tpl>",
			onSelect	: Ext.emptyFn
		});

		var tree;
      	categoryCombo.on("expand",function(){
      		if(tree==null){
	      		tree =  ast.ast1949.admin.category.treePanel({el:"category-combo",rootData:Context.CATEGORY.areaCode});
	      		tree.getRootNode().disable();
				tree.on('click',function(node){
		          	categoryCombo.setValue(node.text);
		          	categoryCombo.collapse();
		          	Ext.get("areaCode").dom.value= node.attributes["data"];
	//	          	tree.destroy();
		      	});
      		}
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
				title : "园区类别",
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
						fieldLabel : "名称",
						allowBlank : false,
						name : "name",
						tabIndex : 1,
						anchor : "95%",
						blankText : "名称不能为空"
					}, {
						xtype : "textfield",
						fieldLabel : "园区简称",
						allowBlank : false,
						name : "shorterName",
						tabIndex : 2,
						anchor : "95%",
						blankText : "园区简称不能为空"
					}, new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["industryCode"],
						fieldLabel : "行业类别",
						name : "industryCode"
					}), new ast.ast1949.CategoryCombo( {
						categoryCode : Context.CATEGORY["gardenTypeCode"],
						fieldLabel : "园区",
						name : "gardenTypeCode"
					}), categoryCombo]
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

		ast.ast1949.admin.categoryGarden.InfoFormWin.superclass.constructor
				.call(this, {
					title : _title,
					closeable : true,
					width : 700,
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
		//初始化区域列表框
//		Ext.get("combo-areaCode").dom.value=_record.get("");
	},
	onSaveSuccess : function(_form, _action) {
		this.fireEvent("saveSuccess", _form, _action, _form.getValues());
	},
	onSaveFailure : function(_form, _action) {
		// alert(_form.getValues());
	this.fireEvent("saveFailure", _form, _action, _form.getValues());
},
initFocus : function() {
	var f = this._form.getForm().findField("name");
	f.focus(true, true);
}
});