Ext.namespace("ast.ast1949.admin.adminUser");

Ext.onReady(function(){
	var queryWin = null;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "编号",
		width : 30,
		sortable : false,
		dataIndex : "id"
	},{
		header : "用户名称",
		width : 20,
		sortable : false,
		dataIndex : "username"
	},{
		header : "邮箱地址",
		width : 30,
		sortable : false,
		dataIndex : "email"
	},{
		header : "创建时间",
		width : 30,
		sortable : false,
		dataIndex : "gmtCreated",
		renderer : function (value,metadata,record,rowIndex,colIndex,store){
		
		return Ext.util.Format.date(new Date(value.time),'Y-m-d');
//			if(value == null || value == 0){
//            	return '';
//            }else{
//       	    	return Ext.util.Format.date(new Date(value*1000),'Y-m-d');
//            }
       }
	}
	]);
	var tbar = [{
				text :  '添加',
				tooltip : '添加一条记录',
				iconCls : 'add',
				handler : onAdd,
				scope : this
			},{
				text :  '编辑',
				tooltip : '编辑一条记录',
				iconCls : 'edit',
				handler : onEdit,
				scope : this
			},{
				text :  '删除',
				tooltip : '删除记录',
				iconCls : 'delete',
				handler : onDelete,
				scope : this
			},{
				text :  '查看',
				tooltip : '查看一条记录',
				iconCls : 'view',
				handler : onView,
				scope : this
			}]

	var reader = ["id","username","email","gmtCreated"];

	var storeUrl = Context.ROOT+Context.PATH+ "/admin/adminuser/query.htm";

	var title = "用户查询";
	var grid = new ast.ast1949.StandardGridPanel({
		sm: sm,
		cm: cm,
		reader : reader,
		storeUrl : storeUrl,
		//baseParams : {"page.dir":"DESC","page.sortField":"u.id"},
		baseParams : {"dir":"DESC","sort":"id"},
		title : title,
		tbar : tbar,
		listeners : {
			"render" : simpleQueryBar
		}
	});
	var view = new ast.ast1949.StandardGridPanelViewport({
		grid : grid
	});

	//页面上的简单查询工具条
	function simpleQueryBar(){
		var tbar = new Ext.Toolbar({
			items:["用户名称:",{
				xtype:"textfield",
				id:"keyusername",
				width:300
			},"-",{
				text:"查询",
				iconCls : "query",
				handler :
				function(){
					grid.store.baseParams = {"searchName":Ext.get("keyusername").dom.value};
					//定位到第一页
					grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
				}
			}]
		});
		tbar.render(grid.tbar);
	}

	//查询
	function onQuery(){
		//查询方式,不要把分页变量放到baseParams上,baseParams的参数会跟在分页导航生成的各种分页参数后面
		var B = {};
		B["form.ischeck"] = 1;
		var _store = grid.getStore();
		_store.baseParams = B;
		//reload的时候与autoLoad一样,可以重新设置分页变量,对pagingToolBar的更新也需要此配置
		_store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	}

	function onAdd(){
		new ast.ast1949.admin.adminUser.AddFormWin(grid);
	}

	function onEdit(){
		//实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
		if(sm.getCount() == 0){
			Ext.Msg.alert(Context.MST_TITLE,"请选定一条记录编辑");
		}else if(sm.getCount()>1){
			Ext.Msg.alert(Context.MST_TITLE,"只能选定一条记录编辑");
		}else{
			//取得选中Id
			var row = grid.getSelections();
			var _id=row[0].get("id");
			
			new ast.ast1949.admin.adminUser.EditFormWin({id:_id,grid:grid});
		}
	}

	function onDelete(){
		//实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else
			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + sm.getCount()
									+ '条记录?', doDelete);
	}

	function doDelete(_btn){
		if(_btn != "yes")
			return ;

		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			_ids.push(_id);
		}
		//Ext.MessageBox.alert(Context.MSG_TITLE," WARN:功能还未实现!");
		/*提交删除*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/adminuser/delete.htm?ids="+_ids.join(','),
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
//			alert(a.error);
//				if(a.error=="true"){
//			alert(success);
				if(success){
					Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
					grid.getStore().reload();
				}else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
				}
			}
		});
	}

	function onView(){
//		Ext.MessageBox.alert("debug","view event");
		//实际情况需要判断选定的记录数,一般情况下一次只能查看一条记录,也可以按照需求做一次查看多条记录的功能
		if (sm.getCount() != 1)
			Ext.Msg.alert(Context.MSG_TITLE, "请选择一条记录查看");
		else
			new ast.ast1949.admin.adminUser.ViewFormWin({id:sm.getSelected().get("id")});  //id:sm.getSelected().get("id")
	}

});