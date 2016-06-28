Ext.namespace("ast.ast1949.admin.categoryproducts")

ast.ast1949.admin.categoryproducts.subGridPanel=function(_cfg){
	if(_cfg==null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);
	var _title = this["title"] ||'';
	var sm=new Ext.grid.CheckboxSelectionModel();
	var cm=new Ext.grid.ColumnModel([sm,{
			header:"序号",
			width:11,
			sortable:false,
			dataIndex:"id"
		},{
			xtype:"hidden",
			hidden:true,
			dataIndex:"code"
		},{
			header:"类别名",
			width:50,
			sortable:true,
			dataIndex:"label",
			editor:new Ext.form.TextField({
				lazyRender:true
			})
		},{
			header:"关联关键字",
			width:50,
			dataIndex:"keywords"

		}
	]);

	var reader=[
	            {name:"id",mapping:"categoryProductsDO.id"},
	            {name:"code",mapping:"categoryProductsDO.code"},
	            {name:"label",mapping:"categoryProductsDO.label"},
	            {name:"keywords",mapping:"keywords"}
	            ];
	var storeUrl=Context.ROOT+Context.PATH+"/admin/categoryproducts/query.htm";
	var tbar = [ _title,"-",{
		text : '添加关联',
		tooltip : '添加关联,搜索时用',
		handler:onShowAssociate,
		iconCls : 'add',
		scope : this
	}];
	var grid=new ast.ast1949.NoPagerEditorGridPanel({
		id:"subList",
		sm:sm,
		cm:cm,
		reader:reader,
		storeUrl:storeUrl,
		loadMask:Context.LOADMASK,
//		title:_title,
		baseParams : {"dir" : "DESC","sort" : "id"},
		tbar : tbar
	});
	grid.on("afteredit",function(event){
		var id=event.record .get("id");
		var code=event.record.get("code");
		var label = event.value;
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH + "/admin/categoryproducts/edit.htm?id="+id+"&label="+label+"&code="+code,
			method : "get",
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
	function onShowAssociate(){
		// 实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
		if (sm.getCount() == 0) {
			Ext.Msg.alert(Context.MST_TITLE, "请选定一条记录编辑");
		} else if (sm.getCount() > 1) {
			Ext.Msg.alert(Context.MST_TITLE, "只能选定一条记录编辑");
		} else {
			var row = grid.getSelections();
			var _code = row[0].get("code");
			var g=ast.ast1949.admin.categoryproducts.associateGridPanel({
				title:"关联关键字",
				code:_code
			});
			var win = new ast.ast1949.admin.categoryproducts.associateWin( {
				grid : g,
				title:"添加关联关键字"
			});
			win.show();
		}

	}
	return grid;
}