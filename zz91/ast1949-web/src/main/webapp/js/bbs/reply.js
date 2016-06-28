Ext.namespace("ast.ast1949.bbs.reply");

var REPLY=new function(){
	this.GRID="replygrid";
}

ast.ast1949.bbs.reply.CHECKSTATUS=["未审核","通过","","退回"];
ast.ast1949.bbs.reply.TARGETPOST=0;
ast.ast1949.bbs.reply.TARGETPOSTTITLE="";

ast.ast1949.bbs.reply.FIELDS=["id","title","account","content",
	{name:"check_status", mapping:"checkStatus"},
	{name:"is_del", mapping:"isDel"},
	{name:"bbs_post_id", mapping:"bbsPostId"},
	{name:"company_id", mapping:"companyId"},
	{name:"gmt_created", mapping:"gmtCreated"},
	{name:"check_person", mapping:"checkPerson"},
	{name:"unpass_reason", mapping:"unpassReason"},
	{name:"nickname", mapping:"nickname"},
	{name:"check_time", mapping:"checkTime"}]

ast.ast1949.bbs.reply.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.bbs.reply.FIELDS,
			url:_url,
			autoLoad:false
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",
				hidden:true,
				dataIndex:"id"
			},{
				header:"审核",
				dataIndex : "check_status",
				width:35,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1 || value==2){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" alt="审核通过"/>';
					}else if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" alt="审核退回"/>';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" alt="未审核"/>';
					}
				}
			},{
				header:"删除",
				dataIndex : "is_del",
				width:35,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" alt="未删除"/>';
					} else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" alt="已删除"/>';
					}
				}
			},{
				header:"回复的贴子",
				sortable:false,
				width:250,
				dataIndex:"title",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(config.goHuzhu){
						return "<a href='http://huzhu.zz91.com/viewReply"+record.get("bbs_post_id")+".htm' target='_blank'>"+value;
					}
					return "<a href='"+Context.ROOT+Context.PATH+"/bbs/post/edit.htm?id="+record.get("bbs_post_id")+"' target='_blank'>"+value;
				}
			},{
				header:"回复内容",
				sortable:false,
				width:350,
				dataIndex:"content",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					return "<div style='width:99%;white-space:normal;'>"+value+"</div>";
				}
			},{
				header:"回复人",
				sortable:false,
				dataIndex:"account",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(record.get("company_id")>0){
						return "<a href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a>"
					}else{
						
					}
					return "zz91管理员："+value;
				}
			},{
				header:"昵称",
				sortable:true,
				dataIndex:"nickname"
			},{
				header:"回复时间",
				sortable:true,
				dataIndex:"gmt_created",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}else{
						return "";
					}
				}
			},{
				header:"审核人",
				sortable:true,
				dataIndex:"check_person"
			},{
				header:"审核时间",
				sortable:true,
				dataIndex:"check_time",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}else{
						return "";
					}
				}
			},{
				header:"审核不通过原因",
				sortable:true,
				dataIndex:"unpass_reason"
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			autoExpandColumn:5,
			tbar:this.mytoolbar,
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
		
		ast.ast1949.bbs.reply.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/bbs/reply/queryReply.htm",
	mytoolbar:[{
		text:"编辑",
		iconCls:"edit",
		menu:[{
			text:"添加回复",
			iconCls:"add",
			handler:function(btn){
				
				if(ast.ast1949.bbs.reply.TARGETPOST<=0){
					Ext.MessageBox.alert(Context.MSG_TITLE,"只能在贴子详细信息页面里可以添加更多回复");
					return false;
				}
				
				var form=new ast.ast1949.bbs.reply.ReplyForm({
					height:400,
					editType:"more",
					region:"center",
					saveUrl:Context.ROOT+Context.PATH+"/bbs/reply/createReply.htm"
				});
				
				form.loadpostid(ast.ast1949.bbs.reply.TARGETPOST,ast.ast1949.bbs.reply.TARGETPOSTTITLE);
				
				var win = new Ext.Window({
					id:"replywin",
					title:"添加更多回复",
					width:680,
					modal:true,
					autoHeight:true,
					items:[form]
				});
				
				win.show();
				
			}
		},{
			iconCls:"delete",
			text:"删除",
			handler:function(btn){
				ast.ast1949.bbs.reply.doDelete();
			}
		}]
	},{
		text:"审核",
		menu:[{
			text:"通过",
			handler:function(btn){
				ast.ast1949.bbs.reply.updateCheckStatus(1);
			}
		},{
			text:"退回",
			handler:function(btn){
				ast.ast1949.bbs.reply.updateCheckStatus(3);
			}
		}]
	},{
		text:"筛选",
		id:"filter-config",
		menu:[{
			text:"未审核",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("未审核");
				ast.ast1949.bbs.reply.queryReply(REPLY.GRID,{"checkStatus":0})
			}
		},{
			text:"审核通过",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("审核通过");
				ast.ast1949.bbs.reply.queryReply(REPLY.GRID,{"checkStatus":1})
			}
		},{
			text:"审核退回",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("审核退回");
				ast.ast1949.bbs.reply.queryReply(REPLY.GRID,{"checkStatus":3})
			}
		},"-",{
			text:"全部回贴",
			handler:function(btn){
				Ext.getCmp("filter-config").setText("筛选");
				ast.ast1949.bbs.reply.queryReply(REPLY.GRID,{})
			}
		}]
	},"->","标题:",{
		xtype:"textfield",
		width:120,
		id:"search-title",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp(REPLY.GRID).getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["title"] = field.getValue();
				}else{
					B["title"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"发贴人",{
		xtype:"textfield",
		width:120,
		id:"search-account",
		emptyText:"请输入发贴人账号",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp(REPLY.GRID).getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["account"] = field.getValue();
				}else{
					B["account"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}]
});

ast.ast1949.bbs.reply.doDelete=function(){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '删除之后不能恢复，你确定要这么做吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		
		var grid = Ext.getCmp(REPLY.GRID);
		
		var row = grid.getSelections();
		for (var i=0,len = row.length;i<len;i++){
			Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/bbs/reply/delete.htm",
				params:{
					"id":row[i].get("id"),
					"account":row[i].get("account")
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","回贴已删除");
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("","发生错误,操作被取消");
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","发生错误,操作被取消");
				}
			});
		}
	});
}
	
ast.ast1949.bbs.reply.updateCheckStatus=function(checkstatus){
	var grid = Ext.getCmp(REPLY.GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/bbs/reply/updateCheckStatus.htm",
			params:{
				"id":row[i].get("id"),
				"checkStatus":checkstatus,
				"companyId":row[i].get("company_id"),
				"account":row[i].get("account")
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","回帖已处理");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","发生错误,操作被取消");
				}
			},
			failure:function(response,opt){
				ast.ast1949.utils.Msg("","发生错误,操作被取消");
			}
		});
	}
}

ast.ast1949.bbs.reply.queryReply = function(gridid,param){
	
	Ext.getCmp("search-title").setValue("");
	Ext.getCmp("search-account").setValue("");
	
	
	var _store = Ext.getCmp(REPLY.GRID).getStore();
	if(ast.ast1949.bbs.reply.TARGETPOST>0){
		param["bbsPostId"]=ast.ast1949.bbs.reply.TARGETPOST;
	}
	_store.baseParams = param;
	_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
}

ast.ast1949.bbs.reply.ReplyForm=Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+Context.PATH+"/bbs/reply/updateReply.htm",
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
				id:_edittype+"bbsPostId",
				name:"bbsPostId"
			},{
				xtype:"textfield",
				fieldLabel:"回复的帖子",
				id:_edittype+"title",
				name:"title",
				allowBlank:false,
				itemCls:"required"
			},{
				xtype:"textarea",
				fieldLabel:"回复内容",
				id:_edittype+"content",
				name:"content",
				height:300
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
								ast.ast1949.utils.Msg("","贴子已保存，您可以继续添加或关闭页面！");
								Ext.getCmp(REPLY.GRID).getStore().reload();
								if(typeof form.ownerCt  != "undefined"){
									form.ownerCt.close();
								}
							},
							failure:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "请仔细查看红色的项是否都填写完了",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		ast.ast1949.bbs.reply.ReplyForm.superclass.constructor.call(this,c);
	},
	loadpostid:function(postid,title){
		this.findById(this.editType+"bbsPostId").setValue(postid);
		this.findById(this.editType+"title").setValue(title);
	},
	loadreply:function(g){
		var row = g.getSelectionModel().getSelected();
		var edittype=this.editType||"";
		this.findById(edittype+"id").setValue(row.get("id"));
		this.findById(edittype+"title").setValue(row.get("title"));
		this.findById(edittype+"content").setValue(row.get("content"));
	}
});