Ext.namespace("ast.ast1949.admin.descriptionTemplate");

Ext.onReady(function() {
			var queryWin = null;
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm, {
						header : "描述模板内容",
						width : 50,
						sortable : false,
						dataIndex : "content"
					}, {
						header : "模板类别",
						width : 50,
						sortable : false,
						dataIndex : "templateName"
					}]);

			var tbar = [{
						text : '添加',
						tooltip : '添加一条记录',
						iconCls : 'add',
						handler : onAdd,
						scope : this
					}, {
						text : '删除',
						tooltip : '删除记录',
						iconCls : 'delete',
						handler : onDelete,
						scope : this
					}]

			var reader = [{
						name : "id",
						mapping : "descriptionTemplateDO",
						convert : function(v) {
							return v.id;
						}
					}, {
						name : "content",
						mapping : "descriptionTemplateDO",
						convert : function(v) {
							return v.content;
						}
					}, {
						name : "gmtCreated",
						mapping : "descriptionTemplateDO",
						convert : function(v) {
							return v.gmtCreated;
						}
					}, {
						name : "gmtModified",
						mapping : "descriptionTemplateDO",
						convert : function(v) {
							return v.gmtModified;
						}
					}, "templateName"];
			var storeUrl = Context.ROOT + Context.PATH
					+ "/admin/descriptiontemplate/query.htm";

			var title = "供求详细信息模板管理";

			var grid = new ast.ast1949.StandardGridPanel({
						sm : sm,
						cm : cm,
						reader : reader,
						storeUrl : storeUrl,
						baseParams : {
							"dir" : "DESC",
							"sort" : "id"
						},
						title : title,
						tbar : tbar

					});
			var view = new ast.ast1949.StandardGridPanelViewport({
						grid : grid
					});

			// 查询
			function onQuery() {
				// 查询方式,不要把分页变量放到baseParams上,baseParams的参数会跟在分页导航生成的各种分页参数后面
				var B = {
					"dir" : "DESC",
					"sort" : "id"
				};
				var _store = grid.getStore();
				_store.baseParams = B;
				// reload的时候与autoLoad一样,可以重新设置分页变量,对pagingToolBar的更新也需要此配置
				_store.reload({
							params : {
								"startIndex" : 0,
								"pageSize" : Context.PAGE_SIZE
							}
						});
			}

			function onAdd() {
				// var code="10260001";
				new ast.ast1949.admin.descriptionTemplate.AddFormWin({
							_grid : grid
						});
			}

			function onDelete() {
				// 实际情况需要判断选定记录
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
				else

					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的'
									+ sm.getCount() + '条记录?', doDelete);
			}
			function doDelete(_btn) {
				if (_btn != "yes")
					return;

				var row = grid.getSelections();
				var _ids = new Array();
				for (var i = 0, len = row.length; i < len; i++) {

					var _id = row[i].get("id");
					_ids.push(_id);
				}

				var conn = new Ext.data.Connection();
				conn.request({
							url : Context.ROOT
									+ Context.PATH
									+ "/admin/descriptiontemplate/delete.htm?ids="
									+ _ids.join(','),
							method : "get",
							scope : this,
							callback : function(options, success, response) {
								if (success) {
									Ext.MessageBox.alert(Context.MSG_TITLE,
											"选定的记录已被删除!");
									grid.getStore().reload();
								} else {
									Ext.MessageBox.alert(Context.MSG_TITLE,
											"所选记录删除失败!");
								}
							}
						});

			}

		});