Ext.namespace("ast.ast1949.admin.weekly");

var _b = new function(){
this.weeklyGrid="weeklyGrid";
	
}
ast.ast1949.admin.weekly.listWeekly=function(_cfg){
	if(_cfg==null){
		_cfg={};
	}
	var _list=_cfg["list"]||null;

	var queryWin = null;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "id",
		sortable : true,
		dataIndex : 'id',
		hidden:true
	},{
		header : "pageId",
		id:"pageId",	
		sortable : true,
		dataIndex : 'pageId',
		hidden:true
	},{
		header : "文章标题",
		sortable : true,
		dataIndex : "title"
	},{
		header : "文章URL",
		sortable : false,
		dataIndex : "url"
	}]);

	var reader = [
		{name:"id",mapping:"weeklyArticleDO.id"},
		{name:"pageId",mapping:"weeklyArticleDO.pageId"},
		 "title","url"];

	var storeUrl = Context.ROOT +Context.PATH+ "/admin/weekly/listWeekly.htm";

	var grid = new ast.ast1949.StandardGridPanelNoPage({
		sm: sm,
		cm: cm,
		reader : reader,
		storeUrl : storeUrl,
		id :_b.weeklyGrid,
		tbar : new Ext.Toolbar({
			items:[{
				text:"添加",
				iconCls : "add",
				handler :function(){
					if(_list==null || typeof(_list) == "undefined"){
						return false;
					}else{
						var row = _list.getSelections();
						var win=ast.ast1949.admin.weekly.listRightWin({
							pageId:row[0].get("id"),
							RightList:grid
						});
					}
				}
			},{
				text:"删除",
				iconCls : "delete",
				handler :function(){
					
					if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		 			else
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的'
							+ sm.getCount() + '条记录?', doDeleteWeekly);
				}
			}]
		})
	});

	return grid;
}

function doDeleteWeekly(_btn){
		if(_btn != "yes")
			return ;
		var grid = Ext.getCmp("weeklyGrid");
		var row = grid.getSelections();
		var _ids = new Array();
		
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			_ids.push(_id);
		}
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/weekly/deleteArticle.htm?ids="+_ids.join(','),
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