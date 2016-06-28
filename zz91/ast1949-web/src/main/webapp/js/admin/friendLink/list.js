Ext.namespace("ast.ast1949.admin.friendLink");

Ext.onReady(function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
//	var expander = new Ext.grid.RowExpander({
//				tpl : new Ext.Template('<p><b>广告图片:</b> {url}</p><br>',
//						'<p><img src="'+Friendlink.uploadurl+'/'+Friendlink.model+'/'+Friendlink.filetype+'/'+'{picAddress}" /></p>')
//			});
	var cm = new Ext.grid.ColumnModel([sm, {
				header : "序号",
				width : 40,
				sortable : false,
				dataIndex : "showIndex"
			}, {
				header : "网站名称",
				width : 150,
				sortable : false,
				dataIndex : "linkName"
			}, {
				header : "logo",
				width : 200,
				sortable : false,
				dataIndex : "picAddress",
				renderer:function(value, metadata, record, rowIndex,
						colIndex, store){
					return '<img src="'+resourceUrl+"/"+value+'" />';
				}
			}, {
				header : "网站类别",
				width : 150,
				sortable : false,
				dataIndex : "linkCategoryName"
			}, {
				header : "链接地址",
				width : 200,
				sortable : false,
				dataIndex : "link"
			}, {
				header : "添加时间",
				width : 150,
				sortable : false,
				dataIndex : "addTime",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d');
					}
				}
			},{
				header : "审核",
				width :80,
				sortable : false,
				dataIndex : "isChecked",
				renderer:function (value,metadata,record,rowIndex,colIndex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
			}]);

	var tbar = [{
				text : '添加',
				tooltip : '添加一条记录',
				iconCls : 'add',
				handler : onAdd,
				scope : this
			}, {
				text : '编辑',
				tooltip : '编辑一条记录',
				iconCls : 'edit',
				handler : onEdit,
				scope : this
			}, {
				text : '删除',
				tooltip : '删除记录',
				iconCls : 'delete',
				handler : onDelete,
				scope : this
			},{
				text : '审核',
				tooltip : '审核友情链接',
				iconCls : 'edit',
				handler : onChecked,
				scope : this
			},{
				text : '取消审核',
				tooltip : '删除审核',
				iconCls : 'edit',
				handler : onCancelChecked,
				scope : this
			}]

	var reader = [{
				name : "id",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.id;
				}
			}, {
				name : "linkName",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.linkName;
				}
			}, {
				name : "height",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.height;
				}
			}, {
				name : "width",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.width;
				}
			}, {
				name : "picAddress",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.picAddress;
				}
			}, {
				name : "addTime",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.addTime;
				}
			}, {
				name : "textColor",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.textColor;
				}
			}, {
				name : "gmtCreated",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.gmtCreated;
				}
			}, {
				name : "gmtModified",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.gmtModified;
				}
			}, {
				name : "link",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.link;
				}
			}, {
				name : "showIndex",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.showIndex;
				}
			}, {
				name : "isChecked",
				mapping : "friendLinkDO",
				convert : function(v) {
					return v.isChecked;
				}
			}, "linkCategoryName"];

	var storeUrl = Context.ROOT + Context.PATH + "/admin/friendlink/query.htm";

	var title = "友情链接管理";

	// 页面上的简单查询工具条
	function simpleQueryBar() {
		var tbar = new Ext.Toolbar({
			items : ["网站名称:", {
						xtype : "textfield",
						id : "keyname",
						width : 150
					}, " ", new ast.ast1949.CategoryCombo({
								categoryCode : Context.CATEGORY["linkCategoryCode"],
								fieldLabel : "类别",
								width : 120,
								id : "linkCategory",
								name : "linkCategory"
							}), " -", {
						text : "查询",
						iconCls : "query",
						handler : function() {
							var lc=Ext.get("linkCategory").dom.value;
							if(""==Ext.get("linkCategory_combo").getValue()){
								lc="";
							}
							grid.store.baseParams = {
								"linkName" : Ext.get("keyname").dom.value,
								"linkCategoryName" : lc
							};

							grid.store.reload();
						}
					}]
		});
		tbar.render(grid.tbar);
	}
     var grid = new ast.ast1949.PluginsGridPanel( {
		sm : sm,
		cm : cm,
		autoExpandColumn:7,
//		plugins :expander,
		reader : reader,
		storeUrl : storeUrl,
		baseParams : {"dir" : "DESC","sort" : "id"},
		title : title,
		tbar : tbar,
		listeners : {
			"render" : simpleQueryBar
		}
	});
//	var grid = new ast.ast1949.StandardGridPanel({
//				sm : sm,
//				cm : cm,
//				reader : reader,
//				storeUrl : storeUrl,
//				baseParams : {
//					"dir" : "asc",
//					"sort" : "id"
//				},
//				title : title,
//				tbar : tbar,
//				listeners : {
//					"render" : simpleQueryBar
//				}
//			});
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
		new ast.ast1949.admin.friendLink.AddFormWin(grid);
		upload_img();
	}

	function onEdit() {

		// 实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
		if (sm.getCount() == 0) {
			Ext.Msg.alert(Context.MST_TITLE, "请选定一条记录编辑");
		} else if (sm.getCount() > 1) {
			Ext.Msg.alert(Context.MST_TITLE, "只能选定一条记录编辑");
		} else {
			var row = grid.getSelections();
			var _id = row[0].get("id");
			new ast.ast1949.admin.friendLink.EditFormWin({
						id : _id,
						grid : grid
					});

		}
		upload_img();
	}

	function onDelete() {
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else

			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的'
							+ sm.getCount() + '条记录?', doDelete);
	}

	function onChecked() {
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else

			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要审核选中的'
							+ sm.getCount() + '条记录?', doChecked);
	}

	function onCancelChecked() {
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else

			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要取消审核选中的'
							+ sm.getCount() + '条记录?', doCancelChecked);
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
							+ "/admin/friendlink/delete.htm?ids="
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
	function doChecked(_btn) {
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
							+ "/admin/friendlink/checked.htm?ids="
							+ _ids.join(','),
					method : "get",
					scope : this,
					callback : function(options, success, response) {
						if (success) {
							Ext.MessageBox.alert(Context.MSG_TITLE,
									"选定的记录已审核!");
							grid.getStore().reload();
						} else {
							Ext.MessageBox
									.alert(Context.MSG_TITLE, "所选记录审核失败!");
						}
					}
				});

	}
	function doCancelChecked(_btn) {
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
							+ "/admin/friendlink/cancelChecked.htm?ids="
							+ _ids.join(','),
					method : "get",
					scope : this,
					callback : function(options, success, response) {
						if (success) {
							Ext.MessageBox.alert(Context.MSG_TITLE,
									"选定的记录已被取消审核!");
							grid.getStore().reload();
						} else {
							Ext.MessageBox
									.alert(Context.MSG_TITLE, "所选记录取消审核失败!");
						}
					}
				});

	}
	function onView() {

		new ast.ast1949.admin.friendLink.ViewFormWin({
					id : 0
				}); // id:sm.getSelected().get("id")
	}

	function upload_img(){		
		Ext.get("file_upload").on("click", function() {			
			ast.ast1949.UploadConfig.uploadURL=Context.ROOT+Context.PATH+"/admin/upload?model=friendLink&filetype=img";
			var win = new ast.ast1949.UploadWin({
				title:"上传图片"
			});
			win.show();
			ast.ast1949.UploadConfig.uploadSuccess = function(f,o){
				if(o.result.success){
						var uploadUrl=o.result.data[0].path+o.result.data[0].uploadedFilename;
						Ext.get("picAddress").dom.value=uploadUrl;
						Ext.get("preview_img").dom.src=resourceUrl+"/"+uploadUrl;
						win.close();
				}else{
					Ext.MessageBox.show({
						title:Context.MSG_TITLE,
						msg : "文件没有被上传",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			}
		});
	}

});