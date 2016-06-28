Ext.namespace("ast.ast1949.admin.productsPic");

Ext.onReady(function() {
	var queryWin = null;
	var sm = new Ext.grid.CheckboxSelectionModel();

	var cm = new Ext.grid.ColumnModel([sm, {
		header : "",
		width : 200,
		sortable : false,
		dataIndex : "picAddress",
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			//alert(value);
			return '<img src="' + ProductsPic.upload_url + '/'
					+ ProductsPic.model + '/' + ProductsPic.filetype + '/'
					+ value + '" class="thumb-img">';
		}
	}, {
		header : "产品名称",
		width : 200,
		sortable : false,
		dataIndex : "productTitle"
	}, {
		header : "相册类别",
		width : 200,
		sortable : false,
		dataIndex : "albumName"
	}, {
		header : "是否为封面",
		width : 100,
		sortable : false,
		dataIndex : "isCover",
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			if (value == 1) {
				return "是";
			} else {
				return "否";
			}
		}
	}, {
		header : "是否为客户默认显示的图片",
		width : 100,
		sortable : false,
		dataIndex : "isDefault",
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			if (value == 1) {
				return "是";
			} else {
				return "否";
			}
		}
	}]);
	/*
	 * function setcz(value) { var str = "<a href=\"">ygy</a>";
	 * //str+="&nbsp;&nbsp;<a href='#'><img
	 * src='/ext/images/icons/fam/tabs.gif' border='0' alt='预览'></a>" return
	 * str; }
	 */
	var tbar = [{
				text : '添加',
				tooltip : '添加图片信息',
				iconCls : 'edit',
				handler : onAdd,
				scope : this
			}, {
				text : '编辑 ',
				tooltip : '编辑图片信息 ',
				iconCls : 'edit',
				handler : onEdit,
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
				mapping : "productsPicDO",
				convert : function(v) {
					return v.id;
				}
			}, {
				name : "productId",
				mapping : "productsPicDO",
				convert : function(v) {
					return v.productId;
				}
			}, {
				name : "isCover",
				mapping : "productsPicDO",
				convert : function(v) {
					return v.isCover;
				}
			}, {
				name : "isDefault",
				mapping : "productsPicDO",
				convert : function(v) {
					return v.isDefault;
				}
			}, {
				name : "picAddress",
				mapping : "productsPicDO",
				convert : function(v) {
					return v.picAddress;
				}
			}, {
				name : "gmtCreated",
				mapping : "productsPicDO",
				convert : function(v) {
					return v.gmtCreated;
				}
			}, {
				name : "gmtModified",
				mapping : "productsPicDO",
				convert : function(v) {
					return v.gmtModified;
				}
			}, "albumName", "productTitle"];
	// alert(ProductsPic.productId);
	var storeUrl = Context.ROOT + Context.PATH
			+ "/admin/productspic/queryByProductId.htm?productId="
			+ ProductsPic.productId;

	var title = "图片管理";

	// 页面上的简单查询工具条
	function simpleQueryBar() {
		var tbar = new Ext.Toolbar({
			items : ["产品名称:", {
						xtype : "textfield",
						id : "productTitle",
						width : 150
					}, " ",
					new ast.ast1949.admin.productsPic.productsAlbumsTree({
								fieldLabel : "相册类别",
								id : "search-combo-assistTypeId",
								name : "search-combo-assistTypeId",
								hiddenName : "albumName",
								hiddenId : "albumName",
								emptyText : "相册类别",
								readOnly : true,
								allowBlank : true,
								width : "50",
								el : "search-assistTypeId-combo",
								rootData : 0
							}), " -", {
						text : "查询",
						iconCls : "query",
						handler : function() {
							grid.store.baseParams = {
								"albumName" : Ext.get("albumName").dom.value,
								"productTitle" : Ext.get("productTitle").dom.value
							};

							grid.store.reload();
						}
					}]
		});
		tbar.render(grid.tbar);
	}
	var grid = new ast.ast1949.PluginsGridPanel({
				sm : sm,
				cm : cm,
				// plugins : expander,
				reader : reader,
				storeUrl : storeUrl,
				baseParams : {
					"dir" : "DESC",
					"sort" : "id"
				},
				title : title,
				tbar : tbar,
				listeners : {
					"render" : simpleQueryBar
				}
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
		new ast.ast1949.admin.productsPic.AddFormWin(grid);
		upload_img();
	}
	function onEdit() {

		if (sm.getCount() == 0) {
			Ext.Msg.alert(Context.MST_TITLE, "请选定一条记录编辑");
		} else if (sm.getCount() > 1) {
			Ext.Msg.alert(Context.MST_TITLE, "只能选定一条记录编辑");
		} else {
			var row = grid.getSelections();
			var _id = row[0].get("id");
			new ast.ast1949.admin.productsPic.EditFormWin({
						id : _id,
						grid : grid
					});
			upload_img();
		}

	}
	function onView() {

		new ast.ast1949.admin.productsPic.ViewFormWin({
					id : 0
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
					url : Context.ROOT + Context.PATH
							+ "/admin/productspic/delete.htm?ids="
							+ _ids.join(','),
					method : "get",
					scope : this,
					callback : function(options, success, response) {
						if (success) {
							Ext.MessageBox.alert(Context.MSG_TITLE,
									"选定的记录已被删除!");
							grid.getStore().reload();
						} else {
							Ext.MessageBox
									.alert(Context.MSG_TITLE, "所选记录删除失败!");
						}
					}
				});

	}
	function upload_img() {
		Ext.get("preview_img").on("click", function() {
			ast.ast1949.Upload({
						model : ProductsPic.model,
						filetype : ProductsPic.filetype
					}, function(_form, _action) {
						var res = Ext.decode(_action.response.responseText);
						if (res.success) {

							var uploaded = "";
							for (var i = 0, len = res.data.length; i < len; i++) {
								if (uploaded == "") {
									uploaded = res.data[i];
								} else {
									uploaded = uploaded + "," + res.data[i];
								}
							}
							Ext.get("picAddress").dom.value = uploaded;
							Ext.get("preview_img").dom.src = ProductsPic.upload_url
									+ "/"
									+ ProductsPic.model
									+ "/"
									+ ProductsPic.filetype + "/" + uploaded;
							Ext.get("preview_img_a").dom.href = ProductsPic.upload_url
									+ "/"
									+ ProductsPic.model
									+ "/"
									+ ProductsPic.filetype + "/" + uploaded;
							Ext.getCmp("upload-file-window").close();
						} else {
							Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "发生错误,请联系管理员",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
					}, function(_form, _action) {

					});

		});
	}

});