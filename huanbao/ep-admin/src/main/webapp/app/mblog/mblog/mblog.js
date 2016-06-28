Ext.namespace("com.zz91.ep.mblog");

var MBLOG=new function(){
	this.GRID="mbloggrid";
	this.COMMENT_List_GRID="commentlistGrid";
}
com.zz91.ep.mblog.Field=[
	{name:"id",mapping:"mBlog.id"},
	{name:"infoId",mapping:"mBlog.infoId"},
	{name:"content",mapping:"mBlog.content"},
	{name:"type",mapping:"mBlog.type"},
	{name:"isDelete",mapping:"mBlog.isDelete"},
	{name:"discussCount",mapping:"mBlog.discussCount"},
	{name:"sentCount",mapping:"mBlog.sentCount"},
	{name:"gmtCreated",mapping:"mBlog.gmtCreated"},
	{name:"photos",mapping:"sList"},
	{name:"name",mapping:"info.name"}
];

com.zz91.ep.mblog.CommentField=[
	{name:"id",mapping:"comment.id"},
	{name:"infoId",mapping:"comment.infoId"},
	{name:"mblogId",mapping:"comment.mblogId"},
	{name:"content",mapping:"comment.content"},
	{name:"isDelete",mapping:"comment.isDelete"},
	{name:"gmtCreated",mapping:"comment.gmtCreated"},
	{name:"name",mapping:"info.name"}
];

com.zz91.ep.mblog.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totals',
			remoteSort:true,
			fields: com.zz91.ep.mblog.Field,
			url:Context.ROOT + "/mblog/mblog/queryAllMBlog.htm",
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
				header:"简略显示内容",
				sortable:true,
				width:250,
				dataIndex:"content",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					var isDelete= record.get("isDelete");
					var returnvalue=value;
					if(isDelete=='1'){
						returnvalue=value+"(已删除)";
					}
					return returnvalue;
				}
			},{
				header:"发布人",
				sortable:true,
				dataIndex:"name",
				width:150
			},{
				header:"发布时间",
				sortable:true,
				dataIndex:"gmtCreated",
				width:70,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"发布状态",
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
			},{
				header:"转发量",
				sortable:true,
				dataIndex:"sentCount",
				width:80
				
				
			},{
				header:"评论量",
				sortable:true,
				dataIndex:"discussCount",
				width:80
				
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
		
		com.zz91.ep.mblog.Grid.superclass.constructor.call(this,c);
		
	},
	
	mytoolbar:[{
		text:"编辑",
		iconCls:"edit16",
		menu:[{
			iconCls:"delete16",
			text:"删除",
			handler:function(btn){
				com.zz91.ep.mblog.doDeleteMBlog();
			}
		},{
			iconCls:"play16",
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
				com.zz91.ep.mblog.queryMblog(MBLOG.GRID,{"isDelete":1})
			}
		},{
			text:"全部",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("全部");
				com.zz91.ep.mblog.queryMblog(MBLOG.GRID);
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



com.zz91.ep.mblog.doDeleteMBlog=function(){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '删除之后不能恢复，你确定要这么做吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		           
		var grid = Ext.getCmp(MBLOG.GRID);
		var row = grid.getSelectionModel().getSelections();
		
		for (var i=0,len = row.length;i<len;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/mblog/mblog/delete.htm",
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
				url:Context.ROOT+"/mblog/mblog/updateDeleteStatus.htm",
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



com.zz91.ep.mblog.queryMblog = function(gridid,param){
	var _store = Ext.getCmp(MBLOG.GRID).getStore();
	_store.baseParams = param;
	_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
}

com.zz91.ep.mblog.ReplyForm=Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+"/mblog/mblog/updateMBlog.htm",
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _edittype=this.editType||"";
		
		var form=this;
		
		var c={
			labelAlign : "right",
			labelWidth : 80,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"99%"
			},
			items:[{
				xtype:"hidden",
				id:_edittype+"id",
				name:"id"
			},{
				xtype:"hidden",
				id:_edittype+"infoId",
				name:"infoId"
			},{
				xtype:"textarea",
				fieldLabel:"正文",
				id:_edittype+"content",
				name:"content",
				height:240
			},{
				xtype:"htmleditor",
				fieldLabel:"图片",
				height:600,
				id:_edittype+"photo"
			}],
			buttonAlign:"right",
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:form.saveUrl,
							method:"post",
							type:"json",
							success:function(){
								//刷新日志表格
							    com.zz91.utils.Msg("","操作成功");
								Ext.getCmp(MBLOG.GRID).getStore().reload();
								if(typeof form.ownerCt  != "undefined"){
									form.ownerCt.close();
								}
							},
							failure:function(){
									Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : "请仔细查看红色的项是否都填写完了",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		com.zz91.ep.mblog.ReplyForm.superclass.constructor.call(this,c);
	},
	loadreply:function(g){
		var row = g.getSelectionModel().getSelected();
		var edittype=this.editType||"";
		this.findById(edittype+"photo").setValue("");
		this.findById(edittype+"id").setValue(row.get("id"));
		this.findById(edittype+"content").setValue(row.get("content"));
		var path=row.get("photos").toString();
		if(path.length>0){
			var pathString='';
			for ( var i = 0; i < path.length; i++) {
				var pathChar=path.charAt(i);
				if(i==path.length-1){
					pathString+=pathChar;
					this.findById(edittype+"photo").setValue(this.findById(edittype+"photo").getValue()+"<img src="+Context.RESOURCE+pathString+" width='100' height='100' />");
					break;
				}
				if(pathChar==','){
					this.findById(edittype+"photo").setValue(this.findById(edittype+"photo").getValue()+"<img src="+Context.RESOURCE+pathString+" width='100' height='100' />");	
					pathString='';
					continue;
				}else{
					pathString+=pathChar;
				}
				
			}
		}
	}
});


com.zz91.ep.mblog.CommentListGrid=Ext.extend(Ext.grid.GridPanel,{
	
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		var _url = this.listUrl;
		var _store=new Ext.data.JsonStore({
			url:_url,
			autoLoad:false,
			remoteSort:false,
			fields:com.zz91.ep.mblog.CommentField,
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号", width:80, dataIndex:"id",sortable:true,hidden:true
			},{
				header:"内容", width:400,dataIndex:"content",sortable:true
			},{
				header:"评论回复人", width:200, dataIndex:"name",sortable:true
			},{
				header:"创建时间", width:200, dataIndex:"gmtCreated",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"评论状态", width:100, dataIndex:"isDelete",sortable:true,
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
		
		var cgrid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			
		};
		com.zz91.ep.mblog.CommentListGrid.superclass.constructor.call(this,c);
	},
	listUrl:Context.ROOT+"/mblog/mblog/queryCommentByMblogId.htm",
	load:function(g){
		var cgrid=this;
		var row = g.getSelectionModel().getSelected();
		var mblogId=row.get("id");
		cgrid.getStore().reload({params:{"mblogId":mblogId}});
	},
});





