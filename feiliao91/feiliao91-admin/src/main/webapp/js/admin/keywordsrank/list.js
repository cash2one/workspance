Ext.namespace("ast.ast1949.admin.keywordsrank");
Ext.onReady(function(){
	var queryWin = null;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "名称",
		width : 30,
		sortable : false,
		dataIndex : "title"
	},{
		header : "关键字",
		width : 35,
		sortable : false,
		dataIndex : "name"
	},{
		header : "开始时间",
		width : 35,
		sortable : false,
		dataIndex : "startTime",
		renderer : function (value,metadata,record,rowIndex,colIndex,store){
			return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
		}
	},{
		header : "结束时间",
		width : 35,
		sortable : false,
		dataIndex : "endTime",
		renderer : function (value,metadata,record,rowIndex,colIndex,store){
			return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
		}
	}
	]);
	var tbar = [{
				text :  '修改',
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
			}];

	var reader = ["id","title","name","startTime","endTime"];

	var storeUrl = Context.ROOT+Context.PATH + "/admin/keywordsrank/queryKeywordsRank.htm";

	var title = "关键字排名管理";
	var grid = new ast.ast1949.StandardEditorGridPanel({
		clicksToEdit:1,
		sm: sm,
		cm: cm,
		reader : reader,
		storeUrl : storeUrl,
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
			items:["公司名称:",{
				xtype:"textfield",
				id:"searchCompanyName",
				width:120
			},"-","关键字:",{
				xtype:"textfield",
				id:"searchName",
				width:80
			},"-",
			"所在类别:",
			new ast.ast1949.CategoryCombo( {
				categoryCode : Context.CATEGORY["876"],
				name : "675"
			}),"-",
			"<span style='color:red'>信息状态: </span>",
			new ast.ast1949.CategoryCombo( {
				categoryCode : Context.CATEGORY["infoStatus"],
				name : "searrchInfoStatus"
			})]
		});
		var tbar2 = new Ext.Toolbar({
			items:[
			"开始时间>=",{
				xtype:"datefield",
				format : 'Y-m-d H:i:s',
				id:"searchStartTime",
				width:90
			},"-",
			"结束时间<=",{
				xtype:"datefield",
				format : 'Y-m-d H:i:s',
				id:"searchEndTime",
				width:90
			},"-",{
				text:"查询",
				iconCls : "query",
				handler :
					function(){
				 if(Ext.get("searchStartTime").dom.value==""&&Ext.get("searchEndTime").dom.value==""){
			            grid.store.baseParams = {
			            	    "companyName":Ext.get("searchCompanyName").dom.value,
								"name":Ext.get("searchName").dom.value,
								"isChecked":Ext.get("searrchInfoStatus").dom.value};
			        }
				 else if(Ext.get("searchStartTime").dom.value!=""&&Ext.get("searchEndTime").dom.value==""){
			            grid.store.baseParams = {"companyName":Ext.get("searchCompanyName").dom.value,
								"name":Ext.get("searchName").dom.value,
								"startTime":Ext.get("searchStartTime").dom.value,
								"isChecked":Ext.get("searrchInfoStatus").dom.value};
			        }
				 else if(Ext.get("searchEndTime").dom.value!=""&&Ext.get("searchStartTime").dom.value ==""){
			            grid.store.baseParams = {"companyName":Ext.get("searchCompanyName").dom.value,
								"name":Ext.get("searchName").dom.value,
								"endTime":Ext.get("searchEndTime").dom.value,
								"isChecked":Ext.get("searrchInfoStatus").dom.value};
			        }
				 else{
			            grid.store.baseParams = {"companyName":Ext.get("searchCompanyName").dom.value,
								"name":Ext.get("searchName").dom.value,
								"startTime":Ext.get("searchStartTime").dom.value,
								"endTime":Ext.get("searchEndTime").dom.value,
								"isChecked":Ext.get("searrchInfoStatus").dom.value};
				 }
						grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
			}]
		});
		tbar.render(grid.tbar);
		tbar2.render(grid.tbar);
	}

	function onEdit(){
		/* 实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
		if(sm.getCount() == 0){
			Ext.Msg.alert(Context.MST_TITLE,"请选定一条记录编辑");
		}else if(sm.getCount()>1){
			Ext.Msg.alert(Context.MST_TITLE,"只能选定一条记录编辑");
		}else{
	*/
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else if(sm.getCount()>1)
			Ext.Msg.alert(Context.MSG_TITLE,"最多只能编辑一条记录")
		else{
			var row = grid.getSelections();
			var _ids = new Array();
			for (var i=0,len = row.length;i<len;i++){
				var _id=row[i].get("id");
				_ids.push(_id);
			}
			new ast.ast1949.admin.keywordsrank.EditFormWin({id:_ids,grid:grid});
		}
//		new ast.ast1949.admin.keywordsrank.EditFormWin({});

	}

	function onDelete(){
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else

			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的' + sm.getCount()
									+ '条记录?', function(btn){
				if(btn=="yes")
					doDelete();
			});
	}
	function doDelete(){
		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			_ids.push(_id);
		}

		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH + "/admin/keywordsrank/deleteKeywordsRank.htm?ids="+_ids.join(','),
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var res= Ext.decode(response.responseText);
				if(res.success){
					grid.getStore().reload();
				}else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
				}
			}
		});
	}
	
});