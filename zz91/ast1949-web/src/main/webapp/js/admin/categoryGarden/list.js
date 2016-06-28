Ext.namespace("ast.ast1949.admin.categoryGarden");

Ext.onReady(function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel( [ sm, {
		header : "名称",
		width : 30,
		sortable : false,
		dataIndex : "name"
	}, {
		header : "园区",
		width : 15,
		sortable : false,
		dataIndex : "gardenTypeName"
	}, {
		header : "省市",
		width : 15,
		sortable : false,
		dataIndex : "areaName"
	}, {
		header : "行业",
		width : 15,
		sortable : false,
		dataIndex : "industryName"
	}, {
		header : "简称",
		width : 25,
		sortable : false,
		dataIndex : "shorterName"
	} ]);

	var tbar = [ {
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
	} ]

	var reader = [
	    {name:"id",mapping:"categoryGardenDO",convert:function(v){
			return v.id;
		}},{name:"name",mapping:"categoryGardenDO",convert:function(v){
			return v.name;
		}}, {name:"shorterName",mapping:"categoryGardenDO",convert:function(v){
			return v.shorterName;
		}}, "areaName", "industryName","gardenTypeName" ];

	var storeUrl = Context.ROOT + Context.PATH + "/admin/categorygarden/query.htm";

	var title = "园区类别管理";

	var areaCombo = new Ext.form.ComboBox({
		fieldLabel	: "区域",
		id			: "combo-area",
		name		: "combo-area",
		hiddenName	: "area",
		hiddenId	: "area",
		mode		: "local",
		xtype		: "combo",
		readOnly	: true,
		selectOnFocus:true,
		triggerAction: "all",
		emptyText	: "区域",
//			anchor		: "95%",
		tabIndex	: 1,
		width		: "100",
		allowBlank	: true,
		store		: new Ext.data.SimpleStore({fields:[],data:[[]]}),
		tpl			: "<tpl for='.'><div style='height:280px' id='search-combo'></div></tpl>",
		onSelect	: Ext.emptyFn
	});

	var areaTree;
  	areaCombo.on("expand",function(){
  		if(areaTree==null){
      		areaTree =  ast.ast1949.admin.category.treePanel({el:"search-combo",rootData:Context.CATEGORY.areaCode});
      		areaTree.getRootNode().disable();
			areaTree.on('click',function(node){
	          	areaCombo.setValue(node.text);
	          	areaCombo.collapse();
	          	Ext.get("area").dom.value= node.attributes["data"];
//	          	tree.destroy();
	      	});
  		}
  	});

// 页面上的简单查询工具条
	function simpleQueryBar() {
		var tbar = new Ext.Toolbar( {
			items : [ "名称:", {
				xtype : "textfield",
				id : "keyname",
				width : 100
			}," ",
			new ast.ast1949.CategoryCombo( {
				categoryCode : Context.CATEGORY["industryCode"],
				fieldLabel : "行业类别",
				width : 100,
				name : "industry",
				emptyText:"行业类别"
			})," ", new ast.ast1949.CategoryCombo( {
				categoryCode : Context.CATEGORY["gardenTypeCode"],
				fieldLabel : "园区",
				width : 100,
				name : "garden",
				emptyText:"园区"
			}), " ",areaCombo, " -",
			{
				text : "查询",
				iconCls : "query",
				handler : function() {
					grid.store.baseParams = {
						"searchName" : Ext.get("keyname").dom.value,
						"industryName" : Ext.get("industry").dom.value,
						"gardenTypeName" : Ext.get("garden").dom.value,
						"areaName" : Ext.get("area").dom.value
					};

					grid.store.reload();
				}
			} ]
		});
		tbar.render(grid.tbar);
	}

	var grid = new ast.ast1949.StandardGridPanel( {
		sm : sm,
		cm : cm,
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
	var view = new ast.ast1949.StandardGridPanelViewport( {
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
		_store.reload( {
			params : {
				"startIndex" : 0,
				"pageSize" : Context.PAGE_SIZE
			}
		});
	}

	function onAdd() {
		new ast.ast1949.admin.categoryGarden.AddFormWin(grid);
	}

	function onEdit() {


//			  实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
		if(sm.getCount() ==0){
			Ext.Msg.alert(Context.MST_TITLE,"请选定一条记录编辑");
		}else if(sm.getCount()>1){
			Ext.Msg.alert(Context.MST_TITLE,"只能选定一条记录编辑");
		}else{
			var row = grid.getSelections();
			var _id = row[0].get("id");
			new ast.ast1949.admin.categoryGarden.EditFormWin( {
				id : _id,
				grid : grid
			});

		}

		// }
	}

	function onDelete() {
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else

			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的' + sm
					.getCount() + '条记录?', doDelete);
	}

	function doDelete(_btn) {
		if (_btn != "yes")
			return;

		var row = grid.getSelections();
		var _ids = new Array();
		for ( var i = 0, len = row.length; i < len; i++) {
			var _id = row[i].get("id");
			_ids.push(_id);
		}

		var conn = new Ext.data.Connection();
		conn.request( {
			url: Context.ROOT+Context.PATH+ "/admin/categorygarden/delete.htm?ids="+_ids.join(','),
			method : "get",
			scope : this,
			callback : function(options, success, response) {
				if (success) {
					Ext.MessageBox.alert(Context.MSG_TITLE, "选定的记录已被删除!");
					grid.getStore().reload();
				} else {
					Ext.MessageBox.alert(Context.MSG_TITLE, "所选记录删除失败!");
				}
			}
		});

	}

	function onView() {
		// Ext.MessageBox.alert("debug","view event");
		/*
		 * 实际情况需要判断选定的记录数,一般情况下一次只能查看一条记录,也可以按照需求做一次查看多条记录的功能 if
		 * (sm.getCount() != 1) Ext.Msg.alert(Context.MSG_TITLE,
		 * "请选择一条记录查看"); else
		 */
		new ast.ast1949.admin.categoryGarden.ViewFormWin( {
			id : 0
		}); // id:sm.getSelected().get("id")
	}

	});