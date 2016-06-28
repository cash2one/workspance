Ext.namespace("com.zz91.ep.mblog");

var MBLOG=new function(){
	this.GRID="commentgrid";
}
com.zz91.ep.mblog.Field=[
	{name:"id",mapping:"comment.id"},
	{name:"infoId",mapping:"comment.infoId"},
	{name:"mblogId",mapping:"comment.mblogId"},
	{name:"targetType",mapping:"comment.targetType"},
	{name:"targetIdPic",mapping:"comment.targetId"},
	{name:"content",mapping:"comment.content"},
	{name:"isDelete",mapping:"comment.isDelete"},
	{name:"gmtCreated",mapping:"comment.gmtCreated"},
	{name:"mBlogContent",mapping:"mBlog.content"},
	{name:"name",mapping:"info.name"}
];


com.zz91.ep.mblog.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totals',
			remoteSort:true,
			fields: com.zz91.ep.mblog.Field,   
			url:Context.ROOT + "/mblog/comment/queryAllMblogComment.htm",
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",
				hidden:true,
				dataIndex:"id"
			},{
				header:"info编号",
				hidden:true,
				dataIndex:"infoId"
			},{
				header:"info编号",
				hidden:true,
				dataIndex:"photos"
			},{
				header:"删除",
				sortable:true,
				dataIndex:"isDelete",
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value==0) {
						returnvalue = "未删除";
					}else{
						returnvalue = "已删除";
					}
					return returnvalue;
				}
				
			},{
				header:"内容",
				sortable:true,
				width:200,
				dataIndex:"content",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					
					return value;
				}
			},{
				header:"原微博",
				sortable:true,
				width:250,
				dataIndex:"mBlogContent",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					return value;
				}
			},{
				header:"回复/评论人",
				sortable:true,
				dataIndex:"name",
				width:100
			},{
				header:"回复/评论时间",
				sortable:true,
				dataIndex:"gmtCreated",
				width:150,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"回复/评论状态",
				width:60,
				dataIndex:"isDelete",
				sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value==0) {
						returnvalue = "发布成功";
					}else{
						returnvalue = "发布失败";
					}
					return returnvalue;
				}
			}
		]);
		var grid = this;
		var c={
			iconCls:"icon-grid",
			loadMask:MESSAGE.loadmask,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			autoExpandColumn:5,
			bbar:com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.ep.mblog.grid.superclass.constructor.call(this,c);
		
	},
	
	mytoolbar:[{
		text:"审核",
		menu:[{
			text:"删除",
			handler:function(btn){
				com.zz91.ep.mblog.doDeleteComment();
			}
		},{
			text:"恢复",
			handler:function(btn){
				com.zz91.ep.mblog.doUpdateDeleteStatus();
			}
		}]
	},{
		text:"筛选",
		id:"filter-config",
		menu:[{
			text:"已删除",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("已删除");
				com.zz91.ep.mblog.queryComment(MBLOG.GRID,{"isDelete":1})
			}
		},{
			text:"全部",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("全部");
				com.zz91.ep.mblog.queryComment(MBLOG.GRID);
			}
		}]
	},"->","内容:",{
		xtype:"textfield",
		width:120,
		id:"search-content",
		listeners:{
			"blur":function(field){
			 	var _store = Ext.getCmp(MBLOG.GRID).getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["content"] = field.getValue();
				}else{
					B["content"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"发布人",{
		xtype:"textfield",
		width:120,
		id:"search-name",
		emptyText:"请输入发布人名字",
		listeners:{
			"blur":function(field){
		        var _store = Ext.getCmp(MBLOG.GRID).getStore();
		        var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["name"] = field.getValue();
				}else{
					B["name"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}]
});


com.zz91.ep.mblog.queryComment = function(gridid,param){
	var _store = Ext.getCmp(MBLOG.GRID).getStore();
	_store.baseParams = param;
	_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
}


com.zz91.ep.mblog.doDeleteComment=function(){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '删除之后不能恢复，你确定要这么做吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		           
		var grid = Ext.getCmp(MBLOG.GRID);
		var row = grid.getSelectionModel().getSelections();
		
		for (var i=0,len = row.length;i<len;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/mblog/comment/delete.htm",
				params:{
					"id":row[i].get("id"),
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","操作成功");
						grid.getStore().reload();
					}else{
						com.zz91.utils.Msg("","发生错误,操作被取消");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","发生错误,操作被取消");
				}
			});
		}
	});
}

com.zz91.ep.mblog.doUpdateDeleteStatus=function(){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '执行之后数据将恢复，你确定要这么做吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		           
		var grid = Ext.getCmp(MBLOG.GRID);
		var row = grid.getSelectionModel().getSelections();
		
		for (var i=0,len = row.length;i<len;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/mblog/comment/updateDeleteStatus.htm",
				params:{
					"id":row[i].get("id"),
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","操作成功");
						grid.getStore().reload();
					}else{
						com.zz91.utils.Msg("","发生错误,操作被取消");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","发生错误,操作被取消");
				}
			});
		}
	});
}


