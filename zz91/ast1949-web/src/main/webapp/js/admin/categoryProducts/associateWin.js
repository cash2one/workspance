Ext.namespace("ast.ast1949.admin.categoryproducts");
// 定义一个添加,继承自父窗体类

ast.ast1949.admin.categoryproducts.associateWin = Ext.extend(Ext.Window, {
	_grid : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}

		Ext.apply(this, _cfg);

		var _title = this["title"] || "";
		this._grid = this["grid"] || null;

		ast.ast1949.admin.categoryproducts.associateWin.superclass.constructor.call(
				this, {
					id:"chooseTemplateWin",
					title : _title,
					closeable : true,
					width : 680,
					autoHeight : false,
					height : 480,
					modal : true,
					border : false,
					plain : true,
					layout : "fit",
					items : [ this._grid ]
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
}
});
//窗体里包含的grid
ast.ast1949.admin.categoryproducts.associateGridPanel=function(_cfg){
	if(_cfg==null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);
	var _title = this["title"] ||'';
	var _code=this["code"]||'';
	var sm=new Ext.grid.CheckboxSelectionModel();
	var cm=new Ext.grid.ColumnModel([sm,
		new Ext.grid.RowNumberer(),
		{
			header:"编号",
			width:10,
			hidden:true,
			sortable:false,
			dataIndex:"id"
		},{
			xtype:"hidden",
			hidden:true,
			dataIndex:"categoryProductsCode"
		},{
			header:"关键字",
			width:50,
			sortable:true,
			dataIndex:"keyword",
			editor:new Ext.form.TextField({
				allowBlank:false,
				blankText :"关键字",
				lazyRender:true
			})
		}
	]);

	var reader=["id","productsCategoryCode","keyword"];
	var storeUrl=Context.ROOT+Context.PATH+"/admin/productssearchassociatekeywords/query.htm?code="+_code;
	var keyword = Ext.data.Record.create([
           {name: 'keyword', type: 'string'
           }
   ]);
	var grid=new ast.ast1949.NoPagerEditorGridPanel({
		id:"associateGrid",
		sm:sm,
		cm:cm,
		reader:reader,
		storeUrl:storeUrl,
		title:_title,
		baseParams : {"dir" : "DESC","sort" : "id"},
		tbar : new Ext.Toolbar({
			items:[{
				text : '添加关联',
				tooltip : '添加关联,搜索时用',
				handler:function(){
					var r = new keyword({
						keyword:""
			        });
			        var row=grid.getStore().getCount();
			        grid.stopEditing();
			        grid.getStore().insert(row, r);
			        grid.startEditing(row, 4);
				},
				iconCls : 'add',
				scope : this
			}]
		})
	});
	grid.on("afteredit",function(event){
		var _id=event.record .get("id");
		var _keyword = event.value;
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH + "/admin/productssearchassociatekeywords/edit.htm",
			params:{keyword:_keyword,id:_id,categoryProductsCode:_code},
			method : "post",
			scope : this,
			callback : function(options,success,response){
			var res= Ext.decode(response.responseText);
			if(res.success){
				Ext.MessageBox.alert(Context.MSG_TITLE,"记录修改成功!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录修改失败!");
			}
		}
		});
	})
	return grid;
}
