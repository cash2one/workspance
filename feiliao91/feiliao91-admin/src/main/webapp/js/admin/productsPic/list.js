Ext.namespace("ast.ast1949.admin.productsPic");

Ext.onReady(function() {
	var queryWin = null;
	var sm = new Ext.grid.CheckboxSelectionModel();
	// var expander = new Ext.grid.RowExpander({
	// tpl : new Ext.Template('<p><b>图片:</b> {url}</p><br>',
	// '<p><img
	// src="'+ProductsPic.uploadurl+'/'+ProductsPic.model+'/'+ProductsPic.filetype+'/'+'{picAddress}"
	// /></p>')
	// });
	Ext.override(Ext.Element, {
				contains : function(el) {
					try {
						return !el ? false : Ext.lib.Dom.isAncestor(this.dom,
								el.dom ? el.dom : el);
					} catch (e) {
						return false;
					}
				}
			})
	var cm = new Ext.grid.ColumnModel([sm, {
		header : "图片",
		width:350,
		
		sortable : false,
		dataIndex : "picAddress",
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<img src="' + ProductsPic.upload_url + '/'
					+ ProductsPic.model +  '/'
					+ value + '"width="100" class="thumb-img" >';
		}
	}, {
		header : "供求信息",
		width : 300,
		sortable : false,
		dataIndex : "productTitle",
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var productId = record.get("productId");
			var storeUrl = Context.ROOT + Context.PATH
					+ "/admin/productspic/view.htm?productId=" + productId;
			var html = "<a href='#'>"+value+"</a>";
			html += "<br><a href='" + storeUrl + "' >查看该供求所有图片</a>"
			return html;
		}
	},{
		header : "相册类别",
		sortable : false,
		width:200,
		dataIndex : "albumName"
	}, {
		header : "是否推荐",
		width:200,
		sortable : false,
		dataIndex : "isCover",
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			if (value == 1) {
				return "已推荐";
			} else {
				return "未推荐";
			}
		}
	}]);
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

	var storeUrl = Context.ROOT + Context.PATH + "/admin/productspic/query.htm";

	var title = "图片管理";

	// 页面上的简单查询工具条
	function simpleQueryBar() {
		tbar = new Ext.Toolbar({
			items : ["网站名称:", {
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
							}), {
						text : "查询",
						iconCls : "query",
						handler : function() {
							grid.store.baseParams = {
								"productTitle" : Ext.get("productTitle").dom.value,
								"albumName" : Ext.get("albumName").dom.value
							}, grid.store.reload();
						}
					}, {
						text : '查看该产品信息',
						tooltip : '查看该产品信息',
						iconCls : 'view',
						handler : onView,
						scope : this
					}]
		});
		tbar.render(grid.tbar);
	}
	var grid = new ast.ast1949.PluginsGridPanel({
				sm : sm,
				cm : cm,
				// plugins :expander,
				reader : reader,
				storeUrl : storeUrl,
				baseParams : {
					"dir" : "DESC",
					"sort" : "id"
				},
				title : title,
				// tbar : tbar,
				listeners : {
					"render" : simpleQueryBar
				}
			});
	function onView() {
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else if (sm.getCount() > 1)
			Ext.Msg.alert(Context.MSG_TITLE, "最多只能编辑一条记录")
		else {
			var row = grid.getSelections();
			var _ids = new Array();
			for (var i = 0, len = row.length; i < len; i++) {
				var _id = row[i].get("id");
				_ids.push(_id);
			}
			new ast.ast1949.admin.admincompany.EditFormWin({
						id : _ids,
						grid : grid
					});
		}

	}
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