Ext.namespace("ast.ast1949.admin.category");

Ext.onReady(function() {

	// alert(Ext.get("node-selected").dom.value)
		var _url = Context.ROOT + "/zz91/admin/category/edit.htm";

		var _editPanel = ast.ast1949.admin.category.editPanel( {
			id : Ext.get("node-selected").dom.value
		});
		var _center = {
			region : "center",
			layout : "fit",
			items : [ _editPanel.panel ]
		};
   var _left = new Ext.Panel({
		title:  '类别管理<span style="color:red">【点击右键更多操作】</span>',
		region: 'west',
		collapsible:true,
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		split: true,
		width: 210,
		maxSize: 240,
		minSize:120,
		autoScroll:true,
//		layout: 'fit',
		items:[{html:"<div id='category-tree'></div>"}]  //item,{title:"添加类别",load:{url:"http://www.g.cn"}}
	});

//		var _left = new Ext.Panel( {
//			title : '类别管理',
//			region : 'west',
//			collapsible : true,
//			margins : "0 0 2 2",
//			cmargins : "0 2 2 2",
//			split : true,
//			width : 210,
//			maxSize : 240,
//			minSize : 120,
////			layout : 'fit',
//			items : [_tree]
//		// item,{title:"添加类别",load:{url:"http://www.g.cn"}}
//				});

		var viewport = new Ext.Viewport( {
			layout : "border",
			border : true,
			items : [ _left, _center ]
		});

		var _tree = ast.ast1949.admin.category.treePanel( {
			el : "category-tree",
			rootData : ""
		});
		_editPanel._tree = _tree;

		// 左侧树点击后加载右边表单数据
		_tree.on("click", function(node, event) {
			var pnode = node.parentNode;
			var _id = node.id;
			_id = _id.split("-")[1];
			_editPanel.store.load( {
				params : {
					"id" : _id
				}
			});

			if (pnode != null) {
				var f = _editPanel.panel._form;
				f.findById("combo-preCode").setValue(pnode.text);
				Ext.get("preCode").dom.value = pnode.attributes["data"];
			} else {
				var f = _editPanel.panel._form;
				f.findById("combo-preCode").setValue("请选择父节点");
				Ext.get("preCode").dom.value = "";
			}

		});

		// alert(Ext.get("save"))

		// 右健菜单定义
		var contextmenu = new Ext.menu.Menu( {
			id : "rightMenu",
			items : [
					{
						id : "menu1",
						text : "添加类别",
						cls : "item-add",
						handler : function(menu) {
							var f = _editPanel.panel._form;
							f.findById("combo-preCode").setValue(
									Ext.get("node-selected-text").dom.value);

							f.findById("categoryId").setValue("");
							f.findById("label").setValue("");
							f.findById("showIndex").setValue(0);
							Ext.get("preCode").dom.value = Ext
									.get("node-selected").dom.value;

							_editPanel.store.load();

						}
					},
					{
						id : "menu2",
						text : "修改类别",
						cls : "item-edit",
						handler : function() {
							var _id = Ext.get("node-selected-id").dom.value;
							_id = _id.split("-")[1];
							_editPanel.store.load( {
								params : {
									"id" : _id
								}
							});
						}
					},
					{
						id : "menu3",
						text : "删除分类",
						cls : "item-del",
						handler : function() {
							Ext.MessageBox.confirm(Context.MSG_TITLE,
									'您确定要删除该分类及其子分类吗?', doDelete);
						}
					} ]
		});

		// 右健菜单
		_tree.on("contextmenu", function(node, event) {
			// alert("context click")
				event.preventDefault();
				contextmenu.showAt(event.getXY());
				node.select();
				Ext.get("node-selected").dom.value = node.attributes["data"];
				Ext.get("node-selected-id").dom.value = node.id;
				Ext.get("node-selected-text").dom.value = node.text;

			});

		function doDelete(_btn) {
			if (_btn != "yes")
				return;
			var code = Ext.get("node-selected").dom.value;
			// Ext.MessageBox.alert(Context.MSG_TITLE," WARN:功能还未实现!");
			/* 提交删除 */
			var conn = new Ext.data.Connection();
			conn.request( {
				url : Context.ROOT + Context.PATH
						+ "/admin/category/delete.htm?code=" + code,
				method : "get",
				scope : this,
				callback : function(options, success, response) {
					if (success) {

						var node = _tree.getNodeById(Ext
								.get("node-selected-id").dom.value);

						node.remove();
					} else {
						Ext.MessageBox.alert(Context.MSG_TITLE, "所选记录删除失败!");
					}
				}
			});
		}

		function doDeleteProduct(_btn) {
		}

		// 更新菜单
		Ext.get("save").on("click", function() {
			_editPanel.panel.submit(_url);
		});

	});