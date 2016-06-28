Ext.namespace("ast.ast1949.weekly");

var _C = new function(){
	this.POSTS_INFO_FORM="POSTS_INFO_FORM";
	this.GRID="grid";
	this.MY_WEEKLY_GRID="mygrid";
	this.WEEKLY_GRID_WIN="weekly_GRID_WIN";
	
}

//添加关联
ast.ast1949.admin.weekly.listRightWin = function(_cfg) {
	
		var _pageId= _cfg["pageId"] || 0;
		var _rightList = _cfg["RightList"] || null;
		var weeklyGrid = new ast.ast1949.admin.weekly.listRightNot({
			id:_C.GRID,
			listUrl:Context.ROOT+Context.PATH+"/admin/weekly/listBbs.htm",
			region:'center',
			autoScroll:true,
			_pageid:_pageId,
			rightListArticle:_rightList
		});
		
		var win=new Ext.Window({
			id:"listRightWin",
			title:"文章信息",
			closeable:true,
			width:700,
			height:450,
			modal:true,
			border:false,
			plain:true,
			maximizable:true,
			layout:"fit",
			items:[weeklyGrid]
		});
		win.show();
}

//列表
ast.ast1949.admin.weekly.listRightNot= Ext.extend(Ext.grid.GridPanel,{
	_pageid:0,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}   
		
		Ext.apply(this, _cfg);
	   	var _rightListArticle = _cfg["rightListArticle"] || null;
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
  		var _sm = new Ext.grid.CheckboxSelectionModel();
		var _cm = new Ext.grid.ColumnModel([_sm,{
			header : "id",
			sortable : true,
			dataIndex : 'id',
			hidden:false
		},{
			header : "文章标题",
			sortable : true,
			dataIndex : "title"
		},{
			header : "文章URL",
			sortable : false,
			dataIndex : "url"
		}]);
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store, 	
			sm:_sm,
			cm:_cm,
			tbar:[{
					text:"确定选择",
			//		iconCls:"option",
					scope:this,
					handler:function(btn){ 			
						var grid = Ext.getCmp(_C.GRID);
						var row = grid.getSelections();
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
						Ext.Ajax.request({
						url: Context.ROOT + Context.PATH + "/admin/weekly/addArticle.htm",
						params:{"pageId":this._pageid,"bbsPostIds":_ids.join(',')},
						method: "post",
						success:function(response){
							var res= Ext.decode(response.responseText);
							if(!res.success){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : res.data,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}else{
								Ext.getCmp("weeklyGrid").getStore().reload();	
							}
						},
						failure:function(response){
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "发生错误,请联系管理员!",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});
		
				}
			},{
			   text:"关闭",
		//	   iconCls:"close",
			   handler:function(){
			  	 Ext.getCmp("listRightWin").close();
			   }
			},"->",{
				xtype:"label",
				text:"标题 ："
			},{
				xtype:"textfield",
				id:"title",
				name:"title",
				width:160
			},{
				iconCls:"query",
				text:"搜索",
				handler:function(btn){
					var grid=Ext.getCmp(_C.GRID)
					grid.store.baseParams = {
						"title" : Ext.get("title").dom.value
					};
					grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
				}
			}],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
		ast.ast1949.admin.weekly.listRightNot.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"title",mapping:"title"},
		{name:"url",mapping:"url"}
	]),
	listUrl:Context.ROOT +Context.PATH+ "/admin/weekly/listBbs.htm"
});


