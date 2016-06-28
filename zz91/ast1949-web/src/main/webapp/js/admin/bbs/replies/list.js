/*
 * 回帖管理
 */
Ext.namespace("ast.ast1949.admin.bbs.replies")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
	this.REPLIES_CANCEL_AUDIT_FORM="repliescancelauditform";
	this.REPLIES_CANCEL_AUDIT_WIN="repliescancelauditwin";
	
	this.REPLIES_EDIT_FORM="replieseditform";
	this.REPLIES_EDIT_WIN="replieseditwin";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.bbs.replies.ResultGrid({
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/bbs/replies/list.htm",
		region:'center',
		autoScroll:true
	});
	
	var quicksearchtab = new ast.ast1949.admin.bbs.replies.QuickSearchTab({
		region:'north'
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[quicksearchtab,resultgrid]
	});
	
	resultgrid.on("afteredit",function(e) {
		var _id=e.record.get("id");
		var _content=e.record.get("content");
		
		Ext.Ajax.request({
			url: Context.ROOT+Context.PATH + "/admin/bbs/replies/updateSingle.htm",
			params:{
				"id":_id,
				"content":_content
			},
			method : "post",
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","修改成功！");
//					ast.ast1949.admin.bbs.replies.resultGridReload();
				}else{
					ast.ast1949.utils.Msg("","修改失败！");
				}
			},
			failure:function(response,opt){
				ast.ast1949.utils.Msg("","修改失败！");
			}
		});
	});
});

//帖子列表
ast.ast1949.admin.bbs.replies.ResultGrid = Ext.extend(Ext.grid.EditorGridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            // On selection change, set enabled state of the removeButton
	            // which was placed into the GridPanel using the ref config
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("editButton").enable();
	                    Ext.getCmp("checkedButton").enable();
	                    Ext.getCmp("recommend").enable();
	                    Ext.getCmp("cancelCheckdeButton").enable();
	                    Ext.getCmp("deleteButton").enable();
	                } else {
	                    Ext.getCmp("editButton").disable();
	                    Ext.getCmp("recommend").disable();
	                    Ext.getCmp("checkedButton").disable();
	                    Ext.getCmp("cancelCheckdeButton").disable();
	                    Ext.getCmp("deleteButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"审核",
				dataIndex : "checkStatus",
				width:5,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"所属帖子标题",
				sortable:false,
				dataIndex:"postTitle",
				width:25,
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(record!=null){
						var _url="";
						var _id=record.get("bbsPostId");
						if(value!=null&&value!="") {
							//http://localhost:880/bbs/viewReply390320.htm
							_url="<a href='"+CONST.BBS_SERVER+"/bbs/viewReply"+_id+".htm' target='_blank' alt="+value+">"+value+"</a>";
						} else {
							var _title=record.get("title");
							if(_title!=null&&_title!=""){
								_url="<a href='"+CONST.BBS_SERVER+"/bbs/viewReply"+_id+".htm' target='_blank' alt="+_title+">"+_title+"</a>";
							} else {
								_url="无标题";
							}
						}
						return _url;
					} else {
						return "无标题";
					}
				}
			},{
				header:"发帖者(Email)",
				sortable:false,
				width:20,
				dataIndex:"email"
			},{
				header:"会员类型",
				sortable:false,
				width:20,
				dataIndex:"membershipName"
			},{
				header:"内容",
				sortable:false,
				dataIndex:"content",
				editor:new Ext.form.TextArea({
					allowBlank:false
				})
			},{
				header:"回复时间",
				sortable:false,
				width:15,
				dataIndex:"gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var c={
			clicksToEdit:1,
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			loadMask:Context.LOADMASK,
			sm:_sm,
			cm:_cm,
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
			}),
			listeners:{
				"render":this.buttonQuery
			}
		};
		
		ast.ast1949.admin.bbs.replies.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"bbsPostReply.id"},
		{name:"title",mapping:"bbsPostReply.title"},
		{name:"bbsPostId",mapping:"bbsPostReply.bbsPostId"},
		{name:"checkStatus",mapping:"bbsPostReply.checkStatus"},
		{name:"content",mapping:"bbsPostReply.content"},
		{name:"account",mapping:"bbsPostReply.account"},
		{name:"gmtCreated",mapping:"bbsPostReply.gmtCreated"},
		{name:"companyId",mapping:"bbsPostReply.companyId"},
		{name:"postTitle",mapping:"postTitle"},
		{name:"email",mapping:"email"},
		{name:"membershipNamec",mapping:"membershipName"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/posts/list.htm",
	mytoolbar:[{
		xtype:"label",
		text:"标题 ："
	},{
		xtype:"textfield",
		id:"title",
		name:"title",
		width:160
	},"-",{
		xtype:"label",
		text:"发帖者 ："
	},{
		xtype:"textfield",
		id:"email",
		name:"email",
		width:160
	},{
		iconCls:"query",
		text:"搜索",
		handler:function(btn){
			var resultgrid = Ext.getCmp(_C.RESULT_GRID);

			resultgrid.store.baseParams={};
			resultgrid.store.baseParams = {"title":Ext.get("title").dom.value,"email":Ext.get("email").dom.value};
			//定位到第一页
			resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		}
	}],
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:[{
		iconCls:"edit",
		id:"editButton",
		text:"修改内容",
		disabled:true,
		handler:function(btn){
			var productGrid = Ext.getCmp(_C.RESULT_GRID);
			var selectedRecord = productGrid.getSelectionModel().getSelected();
			if(typeof(selectedRecord) == "undefined"){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "请加载数据...",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.WARNING
				});
				return ;
			}
			var _pid=selectedRecord.get("id");
		
			ast.ast1949.admin.bbs.replies.editFormWin(_pid)
		}
	},"-",{
		iconCls:"delete",
		id:"deleteButton",
		text:"删除",
		disabled:true,
		handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
		}
	},{
		id:"recommend",
		iconCls:"add",
		text:"推荐",
		disabled:true,
		handler:function(btn){
			
			var grid = Ext.getCmp(_C.RESULT_GRID);
			var row = grid.getSelections();
			var _cid=row[0].get("id");
			
			var _url="bbs/viewReply"+_cid+".htm" ;
			var _title=row[0].get("title");
			var url=Context.ROOT+Context.PATH+"/admin/dataindex/index.htm?url="+_url+"&title="+row[0].get("title");
			window.open(url);
		}
	},"-",{
		id:"checkedButton",
		text:"审核",
		disabled:true,
		handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要审核所选记录吗?', function(_btn){
				if(_btn != "yes")
				return ;
				//更新
//				updateChecked(1);
				
				var grid = Ext.getCmp(_C.RESULT_GRID);
	
				var row = grid.getSelections();
				var _ids = new Array();
				var _accounts = new Array();
				for (var i=0,len = row.length;i<len;i++){
					var _id=row[i].get("id");
					var _account=row[i].get("account");
					var _companyId=row[i].get("companyId");
					var _currentState=row[i].get("checkStatus");
					var _bbsPostId=row[i].get("bbsPostId");

					//更新
					var conn = new Ext.data.Connection();
					conn.request({
						//account, id, checkStatus,unpassReason, companyId, currentState
						url: Context.ROOT+Context.PATH+ "/admin/bbs/replies/updateCheckStatus.htm?random="+Math.random()
						+"&checkStatus=1&companyId="+_companyId+"&currentState="+_currentState+"&account="+_account+"&id="+_id+"&bbsPostId="+_bbsPostId,
						method : "get",
						scope : this,
						callback : function(options,success,response){
						var a=Ext.decode(response.responseText);
							if(success){
								ast.ast1949.utils.Msg("","审核成功")
								grid.getStore().reload();
							}else{
								ast.ast1949.utils.Msg("","审核失败")
							}
						}
					});
				}
			});
		}
	},"-",{
		id:"cancelCheckdeButton",
		text:"取消审核",
		disabled:true,
		handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要取消审核所选记录吗?', function(_btn){
				if(_btn != "yes")
				return ;
				
				var grid = Ext.getCmp(_C.RESULT_GRID);
	
				var row = grid.getSelections();
				var _ids = new Array();
				var _accounts = new Array();
				for (var i=0,len = row.length;i<len;i++){
					var _id=row[i].get("id");
					var _account=row[i].get("account");
					var _companyId=row[i].get("companyId");
					var _currentState=row[i].get("checkStatus");

					//更新
					var conn = new Ext.data.Connection();
					conn.request({
						//account, id, checkStatus,unpassReason, companyId, currentState
						url: Context.ROOT+Context.PATH+ "/admin/bbs/replies/updateCheckStatus.htm?random="+Math.random()
						+"&checkStatus=0&companyId="+_companyId+"&currentState="+_currentState+"&account="+_account+"&id="+_id,
						method : "get",
						scope : this,
						callback : function(options,success,response){
						var a=Ext.decode(response.responseText);
							if(success){
								ast.ast1949.utils.Msg("","信息更新成功！")
								grid.getStore().reload();
							}else{
								ast.ast1949.utils.Msg("","审核失败！")
							}
						}
					});
				}
			});
		}
	}]
		});
		
		tbar2.render(this.tbar);
	}
});

//直接删除
function doDelete(_btn){
	if(_btn != "yes")
			return ;
			
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		var _account=row[i].get("account");
		var _companyId=row[i].get("companyId");
		var _currentState=row[i].get("checkStatus");
		
		//Integer id,Integer companyId,String account,String currentState
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/bbs/replies/delete.htm?random="+Math.random()
			+"&companyId="+_companyId+"&currentState="+_currentState+"&account="+_account+"&id="+_id,
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
				if(success){
					ast.ast1949.utils.Msg("","选定的记录已被删除!");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","所选记录删除失败!");
				}
			}
		});
	}
}

/**
 * 更新审核状态
 * @param {} checkStatus 1为通过审核；0为不通过审核。
 */
function updateChecked(checkStatus){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}

	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/bbs/replies/updateCheckStatus.htm?random="+Math.random()+"&checkStatus="+checkStatus+"&ids="+_ids,
		method : "get",
		scope : this,
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录已更新为已审核!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
			}
		}
	});
}

//导航Tab
ast.ast1949.admin.bbs.replies.QuickSearchTab = Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config:config||{};
		Ext.apply(this,config);
		
		var resultgrid = Ext.getCmp(_C.RESULT_GRID);
		
		var c={
			activeTab: 0,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			items:[{
				title:'未审核',
				html:'<i>所有未审核帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"checkStatus":"0"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'已审核',
				html:'<i>所有已审核帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"checkStatus":"1"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'所有',
				html:'<i>所有帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			}]
		};
		
		ast.ast1949.admin.bbs.replies.QuickSearchTab.superclass.constructor.call(this,c);
	}
});

//编辑回帖
ast.ast1949.admin.bbs.replies.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					xtype:"panel",
					html:"<div id='details'></div>"
				},{
					xtype:"textarea",
					name:"content",
					fieldLabel:"内容:",
					allowBlank:false
				}]
			}],
			buttons:[{
				text:"确定",
				handler:this.edit,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(_C.REPLIES_EDIT_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.bbs.replies.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"content",mapping:"content"},
			{name:"checkStatus",mapping:"checkStatus"},
			{name:"title",mapping:"title"},
			{name:"account",mapping:"account"},
			{name:"gmtCreated",mapping:"gmtCreated"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/bbs/replies/getSingleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						var _html = "";
						var _title="";
						var _checkStatus="未审核";
						var _date="";
						var _account="";
						
						if(record.get("title")!=null){
							_title=record.get("title");
						}
						
						if(record.get("checkStatus")!=null){
							if(record.get("checkStatus")=="1"){
								_checkStatus="已审核";
							}
						}
						
						if(record.get("gmtCreated")!=null){
							var value=eval(record.get("gmtCreated"));
							_date = Ext.util.Format.date(new Date(value.time), 'Y-m-d');
						}
						
						if(record.get("account")!=null){
							_account=record.get("account");
						}

						_html += "<p>所属帖子："+_title+"</p>";
						_html += "<p>"+_checkStatus+" 回帖时间："+_date+" 回帖者："+_account+"</p>";
						jQuery("#details").html(_html);
					}
				}
			}
		})
	},
	editUrl:Context.ROOT+Context.PATH + "/admin/bbs/replies/updateSingle.htm",
	edit:function(){
		var _url = this.editUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onEditSuccess,
				failure:this.onEditFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onEditSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.replies.resultGridReload();
		Ext.getCmp(_C.REPLIES_EDIT_WIN).close();
	},
	onEditFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

//编辑回帖窗口
ast.ast1949.admin.bbs.replies.editFormWin=function(id){
		
	var form = new ast.ast1949.admin.bbs.replies.editForm({
		id:_C.REPLIES_EDIT_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.REPLIES_EDIT_WIN,
		title:"修改回帖",
		width:"50%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
//	Ext.get("id").dom.value=id;//record.get("typeId1");
};

//取消审核表单
ast.ast1949.admin.bbs.replies.CancelAuditForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"ids",
					name:"ids"
				},{
					xtype:"hidden",
					id:"accounts",
					name:"accounts"
				},{
					xtype:"hidden",
					id:"checkStatus",
					name:"checkStatus",
					value:"0"
				},{
					name:"integral",
					fieldLabel:"加/减积分",
					allowBlank:false,
					value:"0",
					tabIndex:1
				},{
					xtype:"label",
					html :"正数为加分，负数为减分"
				},{
					xtype:"textarea",
					name:"unpassReason",
					fieldLabel:"原因",
					allowBlank:false,
					tabIndex:2
				}]
			}],
			buttons:[{
				text:"确定",
				handler:this.cancelChecked,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					//关闭编辑产品的窗口
					Ext.getCmp(_C.REPLIES_CANCEL_AUDIT_WIN).close();
				}
			}]
		};
		
		ast.ast1949.admin.bbs.replies.CancelAuditForm.superclass.constructor.call(this,c);
	},
	cancelUrl:Context.ROOT+Context.PATH + "/admin/bbs/replies/updateCheckStatus.htm",
	cancelChecked:function(){
		var _url = this.cancelUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onCancelCheckedSuccess,
				failure:this.onCancelCheckedFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onCancelCheckedSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.replies.resultGridReload();
		Ext.getCmp(_C.REPLIES_CANCEL_AUDIT_WIN).close();
	},
	onCancelCheckedFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

//审核窗口
ast.ast1949.admin.bbs.replies.AuditWin=function(ids,accounts){
	var form = new ast.ast1949.admin.bbs.replies.CancelAuditForm({
		id:_C.REPLIES_CANCEL_AUDIT_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.REPLIES_CANCEL_AUDIT_WIN,
		title:"审核回帖",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	
	win.show();
	Ext.get("ids").dom.value=ids;//record.get("typeId1");
	Ext.get("checkStatus").dom.value="1";
	Ext.get("accounts").dom.value=accounts;
	
};

//取消审核窗口
ast.ast1949.admin.bbs.replies.CancelAuditWin=function(ids,accounts){
	var form = new ast.ast1949.admin.bbs.replies.CancelAuditForm({
		id:_C.REPLIES_CANCEL_AUDIT_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.REPLIES_CANCEL_AUDIT_WIN,
		title:"取消审核",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	
	win.show();
	Ext.get("ids").dom.value=ids;//record.get("typeId1");
	Ext.get("accounts").dom.value=accounts;
};

//重新绑定Grid数据
ast.ast1949.admin.bbs.replies.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}
