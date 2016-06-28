Ext.namespace("ast.ast1949.admin.crm.activity");

Ext.onReady(function(){
	var queryWin=null;
	var sm=new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "编号",
		width : 10,
		sortable : false,
		dataIndex : "activityApply.id"
	},{
		header : "活动名称",
		width : 60,
		sortable : false,
		dataIndex : "label"
	},{
		header : "公司",
		width : 60,
		sortable : false,
		dataIndex : "activityApply.companyName"
	},{
		header : "联系人",
		width : 60,
		sortable : false,
		dataIndex : "activityApply.contact"
	},{
		header : "电话",
		width : 60,
		sortable : false,
		dataIndex : "activityApply.tel"
	},{
		header : "邮箱",
		width : 60,
		sortable : false,
		dataIndex : "activityApply.email"
	}]);
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
	}]
	var reader = [
					"activityApply.id",
					"activityApply.catrgoryCode",
					"activityApply.email",
					"activityApply.companyName",
					"activityApply.contact",
					"activityApply.tel",
					"activityApply.mobile",
					"activityApply.address",
					"activityApply.gmtModified",
					"label"
				];

	var storeUrl = Context.ROOT+Context.PATH+ "/admin/crm/activity/query.htm";

	var title = "信息查询";
	var grid = new ast.ast1949.StandardGridPanel({
		sm: sm,
		cm: cm,
		reader : reader,
		storeUrl : storeUrl,
		baseParams : {"dir":"desc","sort":"id"},
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
			items:["信息标题:",{
				xtype:"textfield",
				id:"name",
				width:100
			},"邮箱:",{
				xtype:"textfield",
				id:"mail",
				width:100
			},"活动类别:",new ast.ast1949.CategoryCombo({
				categoryCode : Context.CATEGORY["activityCategoryCode"],
				id:"code",
				name : "code"
			}),"-",{
				text:"查询",
				iconCls : "query",
				handler :
				function(){
					grid.store.baseParams = {
							"name":Ext.get("name").dom.value,
							"mail":Ext.get("mail").dom.value,
							"code":Ext.get("code").dom.value
							};
					//定位到第一页
					grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE,"dir":"desc","sort":"id"}});
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
		_store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	}

	//添加
	function onAdd(){
		new ast.ast1949.admin.crm.activity.AddFormWin(grid);
	}

	//编辑
	function onEdit(){
		//实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
		if(sm.getCount() == 0){
			Ext.Msg.alert(Context.MST_TITLE,"请选定一条记录编辑");
		}else if(sm.getCount()>1){
			Ext.Msg.alert(Context.MST_TITLE,"只能选定一条记录编辑");
		}else{
			var row = grid.getSelections();
			var _id=row[0].get("activityApply.id");
			new ast.ast1949.admin.crm.activity.EditFormWin({id:_id,grid:grid});
		}
	}

	//删除
	function onDelete(){
		//实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else
			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + sm.getCount()
									+ '条记录?', doDelete);
	}

	//提交删除
	function doDelete(_btn){
		if(_btn != "yes")
			return ;

		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("activityApply.id");
			_ids.push(_id);
		}
		/*提交删除*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/crm/activity/delete.htm?ids="+_ids.join(','),
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
				if(success){
					Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
					grid.getStore().reload();
				}else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
				}
			}
		});
	}
});