/*
 * 帖子管理
 */
Ext.namespace("ast.ast1949.admin.bbs.posts")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
	this.POSTS_INFO_FORM="postinfoform";
	this.POSTS_WIN="postwin";
	this.POSTS_CANCEL_AUDIT_FORM="postscancelauditform";
	this.POSTS_CANCEL_AUDIT_WIN="postscancelauditwin";
}


//帖子列表
ast.ast1949.admin.bbs.posts.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
	                    Ext.getCmp("removeButton").enable();
	                    Ext.getCmp("editButton").enable();
	                    Ext.getCmp("recommend").enable();
	                    Ext.getCmp("checkedButton").enable();
	                    Ext.getCmp("cancelCheckdeButton").enable();
	                    Ext.getCmp("deleteButton").enable();
	                } else {
	                    Ext.getCmp("removeButton").disable();
	                    Ext.getCmp("editButton").disable();
	                    Ext.getCmp("recommend").disable();
	                    
	                    Ext.getCmp("checkedButton").disable();
	                    Ext.getCmp("cancelCheckdeButton").disable();
	                    Ext.getCmp("deleteButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"审核",
				dataIndex : "checkStatus",
				width:30,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1 || value==2){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
			},{
				header:"标题",
				sortable:false,
				dataIndex:"title",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(record!=null){
							var _id=record.get("id");
							var  _url="";
							if(record.get("checkStatus")!="0"){
								_url="<a href='"+CONST.BBS_SERVER+"/bbs/viewReply.htm?postId="+_id+"' target='_blank'>"+value+"</a>";
							}else{
								_url=value;
							}
							return _url;
					} else {
						return "";
					}
				}
			},{
				header:"公司名称",
				sortable:false,
				dataIndex:"companyName",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(record!=null){
						var _id=record.get("companyId");
						if(_id=="0"){
							return "<span style='color:Green'>ZZ91平台</span>";
						} else {
							return value;						
						}
					} else {
						return "未知";					
					}
				}
				
			},{
				header:"会员类型",
				sortable:false,
				dataIndex:"membershipName",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(record!=null){
						var _id=record.get("companyId");
						if(_id=="0"){
							return "<span style='color:Green'>ZZ91管理员</span>";
						} else {
							return "<span style='color:Red'>"+value+"</span>";						
						}
					} else {
						return "未知";					
					}
				}
			},{
				header:"查看量",
				sortable:false,
				dataIndex:"visitedCount"
			},{
				header:"回复量",
				sortable:false,
				dataIndex:"replyCount"
			},{
				header:"更新时间",
				sortable:false,
				dataIndex:"gmtModified",
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
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
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
				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.bbs.posts.ResultGrid.superclass.constructor.call(this,c);
		
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"bbsPost.id"},
		{name:"companyId",mapping:"bbsPost.companyId"},
		{name:"title",mapping:"bbsPost.title"},
		{name:"account",mapping:"bbsPost.account"},
		{name:"companyName",mapping:"companyName"},
		{name:"visitedCount",mapping:"bbsPost.visitedCount"},
		{name:"replyCount",mapping:"bbsPost.replyCount"},
		{name:"checkStatus",mapping:"bbsPost.checkStatus"},
		{name:"gmtModified",mapping:"bbsPost.gmtModified"},
		{name:"isDel",mapping:"bbsPost.isDel"},
		"membershipName"
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/posts/list.htm",
	mytoolbar:[{
		xtype:"label",
		text:"标题："
	},{
		xtype:"textfield",
		id:"title",
		name:"title",
		width:160
	},"-",{
		xtype:"label",
		text:"公司名称："
	},{
		xtype:"textfield",
		id:"companyName",
		name:"companyName",
		width:160
	},"-",{
		xtype:"label",
		text:"发贴者："
	},{
		xtype:"textfield",
		id:"account",
		name:"account",
		width:160
	},{
		iconCls:"query",
		text:"搜索",
		handler:function(btn){
			var resultgrid = Ext.getCmp(_C.RESULT_GRID);

//			resultgrid.store.baseParams={};
			//以下是原始参数
			var isDel="0";
			var isQueryAdmin="";
			var checkStatus="";
//			alert(resultgrid.store.baseParams.isDel);
//			alert(resultgrid.store.baseParams.isQueryAdmin);
//			alert(resultgrid.store.baseParams.checkStatus);
			if(resultgrid.store.baseParams.isDel!="undefined"){
				isDel=resultgrid.store.baseParams.isDel
			}
			if(resultgrid.store.baseParams.isQueryAdmin!="undefined"){
				isQueryAdmin=resultgrid.store.baseParams.isQueryAdmin
			}
			if(resultgrid.store.baseParams.checkStatus!="undefined"){
				checkStatus=resultgrid.store.baseParams.checkStatus
			}
//			alert(isDel);
//			alert(isQueryAdmin);
//			alert(checkStatus);
			//新参数
			resultgrid.store.baseParams = {"isDel":isDel,"isQueryAdmin":isQueryAdmin,"checkStatus":checkStatus,"title":Ext.get("title").dom.value,"companyName":Ext.get("companyName").dom.value,"account":Ext.get("account").dom.value};
			//定位到第一页
			resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		}
	}],
	buttonQuery:function(){
		var tbar2 =new Ext.Toolbar({
			items:[
//			{
//				iconCls:"add",
//				text:"Ext CKEditor",
//				handler:function(btn){
//					var url=Context.ROOT+Context.PATH+"/admin/bbs/posts/demo.htm?ts="+Math.random();
//					window.open(url);
////					ast.ast1949.admin.bbs.posts.AddFormWin();
//				}
//			}
			{
				iconCls:"add",
				text:"添加",
				handler:function(btn){
					var url=Context.ROOT+Context.PATH+"/admin/bbs/posts/edit.htm?ts="+Math.random();
					window.open(url);
//					ast.ast1949.admin.bbs.posts.AddFormWin();
				}
			},{
				iconCls:"edit",
				id:"editButton",
				text:"修改",
				disabled:true,
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
			
					var row = grid.getSelections();
					if(row.length>1){
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "最多只能选择一条记录！",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					} else {
						var _pid=row[0].get("id");
						var _cid=row[0].get("companyId");
						var url=Context.ROOT+Context.PATH+"/admin/bbs/posts/edit.htm?cid="+_cid+"&id="+_pid+"&ts="+Math.random();
						window.open(url);
//						ast.ast1949.admin.bbs.posts.EditFormWin(_pid);
					}
				}
			},"-",{
				iconCls:"delete",
				id:"removeButton",
				text:"删除",
				disabled:true,
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要将选中的记录设置为删除吗?', updateDelete);
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
						var grid = Ext.getCmp(_C.RESULT_GRID);
			
						var row = grid.getSelections();
						var _ids = new Array();
						var _accounts = new Array();
						var _compabyIds = new Array();
						var _currentStates = new Array();
						
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							var _account=row[i].get("account");
							var _companyId=row[i].get("companyId");
							var _currentState=row[i].get("checkStatus");

							//更新
							var conn = new Ext.data.Connection();
							conn.request({
								url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/updateCheckStatus.htm?random="+Math.random()
								+"&companyId="+_companyId+"&currentState="+_currentState+"&checkStatus=1&account="+_account+"&id="+_id,
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
								url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/updateCheckStatus.htm?random="+Math.random()
								+"&companyId="+_companyId+"&currentState="+_currentState+"&checkStatus=0&account="+_account+"&id="+_id,
								method : "get",
								scope : this,
								callback : function(options,success,response){
								var a=Ext.decode(response.responseText);
									if(success){
										ast.ast1949.utils.Msg("","信息已成功更新")
										grid.getStore().reload();
									}else{
										ast.ast1949.utils.Msg("","操作失败")
									}
								}
							});
						}
					});
					
				}
			},{
				id:"recommend",
				iconCls:"add",
				text:"推荐",
				disabled:true,
				handler:function(btn){
				
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelections();
					
					ast.ast1949.admin.dataIndex.SendIndex({
						title:row[0].get("title"),
						link:"http://huzhu.zz91.com/viewReply"+row[0].get("id")+".htm"
					});
				}
			},"-",{
				iconCls:"delete",
				id:"deleteButton",
				text:"直接删除",
				disabled:true,
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
				}
			}]
		});
		
		tbar2.render(this.tbar);
	}
});

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
		url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/updateCheckStatus.htm?random="+Math.random()+"&checkStatus="+checkStatus+"&ids="+_ids,
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

//设置删除
function updateDelete(_btn){
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
		var _currentIsDel=row[i].get("isDel");
		
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/updateIsDelete.htm?random="+Math.random()
			+"&companyId="+_companyId+"&currentState="+_currentState+"&currentIsDel="+_currentIsDel+"&account="+_account+"&isDel=1&id="+_id,
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
				if(success){
					ast.ast1949.utils.Msg("","记录已成功更新，您可以在已删除中查看记录!");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","操作失败!");
				}
			}
		});
	}
}

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
		var _currentIsDel=row[i].get("isDel");
		
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/delete.htm?random="+Math.random()
			+"&companyId="+_companyId+"&currentState="+_currentState+"&currentIsDel="+_currentIsDel+"&account="+_account+"&id="+_id,
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
				if(success){
					ast.ast1949.utils.Msg("","记录已成功更新，您可以在已删除中查看记录!");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","操作失败!");
				}
			}
		});
		
	}
//	/*提交*/
//	var conn = new Ext.data.Connection();
//	conn.request({
//		url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/delete.htm?random="+Math.random()+"&ids="+_ids.join(','),
//		method : "get",
//		scope : this,
//		callback : function(options,success,response){
//		var a=Ext.decode(response.responseText);
//			if(success){
//				Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
//				grid.getStore().reload();
//			}else{
//				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
//			}
//		}
//	});
}

//导航Tab
ast.ast1949.admin.bbs.posts.QuickSearchTab = Ext.extend(Ext.TabPanel,{
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
//				html:'<i>所有未审核帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"0","checkStatus":"0"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'免审核',
//				html:'<i>所有未审核帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"0","uncheckedCheckStatus":"1"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'已审核',
//				html:'<i>所有已审核帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"0","checkStatus":"1"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'会员发布',
//				html:'<i>所有前台用户发布的帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"0","isQueryAdmin":"0"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'本站信息',
//				html:'<i>所有后台管理发布的帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"0","isQueryAdmin":"1"};
						
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'已删除',
//				html:'<i>所有已删除帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"1"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'所有',
//				html:'<i>所有帖子信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {};
						resultgrid.store.baseParams = {"isDel":"0"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			}]
		};
		
		ast.ast1949.admin.bbs.posts.QuickSearchTab.superclass.constructor.call(this,c);
	}
});

//发帖表单
ast.ast1949.admin.bbs.posts.PostInfoForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _hiddenRelease = this["hiddenRelease"] || "";
		var _hiddenSave = this["hiddenSave"] || "";
		var _hiddenUpdate = this["hiddenUpdate"] || "";

		var postCategoryCombo = ast.ast1949.admin.bbs.psotCategory.comboTree({
			fieldLabel	: "<span style='color:red'>所属模块</span>",
			id			: "combo-bbsPostCategoryId",
			name		: "combo-bbsPostCategoryId",
			hiddenName	: "bbsPostCategoryId",
			hiddenId	: "bbsPostCategoryId",
			emptyText	: "请选择...",

			readOnly	:true,
			allowBlank	:true,
			width		:"100",

			el:"bbsPostCategoryId-combo",
			rootData:Context.BBS_PSOT_CATEGORY.all
		});
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:0.5,
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
				},postCategoryCombo
//				,{
//					xtype:"combo",
//					name:"bbsPostCategoryId",
//					fieldLabel:"所属模块:"
//				}
				,{
					xtype:"textfield",
					fieldLabel:"访问量:",
					name:"visitedCount"
				},new Ext.form.RadioGroup({
					fieldLabel:"是否显示:",
					items:[{
						name:"isShow",
						inputValue:"1",
						boxLabel:"是"					
					},{
						name:"isShow",
						inputValue:"0",
						boxLabel:"否"
					}]
				})]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"textfield",
					fieldLabel:"标题:",
					name:"title",
					allowBlank:false
				},new ast.ast1949.BbsPostCategoryCombo( {
					fieldLabel : "标签",
					emptyText: "请选择标签",
					anchor :"95%",
					name : "bbsTagsId"
				})
//				,{
//					xtype:"combo",
//					name:"tagId",
//					fieldLabel:"标签:"
//				}
				,new Ext.form.RadioGroup({
					fieldLabel:"是否热帖:",
					items:[{
						name:"isHotPost",
						inputValue:"1",
						boxLabel:"是"					
					},{
						name:"isHotPost",
						inputValue:"0",
						boxLabel:"否"
					}]
				})]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[new Ext.form.RadioGroup({
					//0:差贴,1:好贴;2:牛贴;3:头条;4:最新动态;5:热门话题
					fieldLabel:"类型:",
					items:[{
						name:"postType",
						inputValue:"0",
						boxLabel:"差贴"
					},{
						name:"postType",
						inputValue:"1",
						boxLabel:"好帖"
					},{
						name:"postType",
						inputValue:"2",
						boxLabel:"牛贴"
					},{
						name:"postType",
						inputValue:"3",
						boxLabel:"头条"
					},{
						name:"postType",
						inputValue:"4",
						boxLabel:"最新动态"
					},{
						name:"postType",
						inputValue:"5",
						boxLabel:"热门话题"
					}]
				
				}),{
					xtype:"hidden",
					name : "content",
					id:"content"
				},{
					xtype:"panel",
					id:"contentpanel",
					html:'<iframe id="contentiframe" src="'+CONST.WEB_JS_SERVER+'/edit/editor.html?id=content&cHeight=100&ReadCookie=0" frameBorder="0" marginHeight="0" marginWidth="0" scrolling="No" width="700" height="260"></iframe>'
				}
//				,{
//					xtype : "htmleditor",
//					name : "content",
//					id:"content",
//					fieldLabel : "详细内容:",
//					abIndex:9,
//					height : 250,
//					allowBlank:false
//				}
				]
			}],
			buttons:[{
				text:"发布",
				handler:this.release,
				scope:this,
				hidden:_hiddenRelease
			},{
				text:"保存",
				handler:this.save,
				scope:this,
				hidden:_hiddenSave
			},{
				text:"修改",
				handler:this.update,
				hidden:_hiddenUpdate,
				scope:this
			}]
		};
		
		ast.ast1949.admin.bbs.posts.PostInfoForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadBbsPost:function(id){
		var _fields=[
			{name:"id",mapping:"bbsPost.id"},
			{name:"title",mapping:"bbsPost.title"},
			{name:"bbsPostCategoryId1",mapping:"bbsPost.bbsPostCategoryId"},
			{name:"visitedCount",mapping:"bbsPost.visitedCount"},
			{name:"tagsId",mapping:"bbsPost.tagsId"},
			{name:"isShow",mapping:"bbsPost.isShow"},
			{name:"isHotPost",mapping:"bbsPost.isHotPost"},
			{name:"postType",mapping:"bbsPost.postType"},
			{name:"content",mapping:"bbsPost.content"},
			{name:"bbsPostCategoryId",mapping:"bbsPostCategoryName"},
			{name:"bbsTagsName",mapping:"bbsTagsName"},
			{name:"bbsTagsId",mapping:"bbsTagsId"}
			
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/bbs/posts/getSingleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("bbsPostCategoryId").dom.value=record.get("bbsPostCategoryId1");
						
						document.getElementById("contentiframe").src=CONST.WEB_JS_SERVER+'/edit/editor.html?id=content&cHeight=100&ReadCookie=0';
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/save.htm",
	//保存
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
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
	onSaveSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.posts.resultGridReload();
		Ext.getCmp(_C.POSTS_WIN).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	},
	//发布
	releaseUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/release.htm",
	release:function(){
		var _url = this.releaseUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onReleaseSuccess,
				failure:this.onReleaseFailure,
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
	onReleaseSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : " 发布成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.posts.resultGridReload();
		Ext.getCmp(_C.POSTS_WIN).close();
	},
	onReleaseFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "发布失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	},
	updateUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/update.htm",
	//修改
	update:function(){
		var _url = this.updateUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onUpdateSuccess,
				failure:this.onUpdateFailure,
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
	onUpdateSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.posts.resultGridReload();
		Ext.getCmp(_C.POSTS_WIN).close();
	},
	onUpdateFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});


//取消审核表单
ast.ast1949.admin.bbs.posts.CancelAuditForm=Ext.extend(Ext.form.FormPanel, {
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
					id:"integral",
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
					allowBlank:true,
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
					Ext.getCmp(_C.POSTS_CANCEL_AUDIT_WIN).close();
				}
			}]
		};
		
		ast.ast1949.admin.bbs.posts.CancelAuditForm.superclass.constructor.call(this,c);
	},
	cancelUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/updateCheckStatus.htm",
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
			msg : "操作成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		
		ast.ast1949.admin.bbs.posts.resultGridReload();
		Ext.getCmp(_C.POSTS_CANCEL_AUDIT_WIN).close();
	},
	onCancelCheckedFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

//添加帖子窗口
ast.ast1949.admin.bbs.posts.AddFormWin=function(){
	var form = new ast.ast1949.admin.bbs.posts.PostInfoForm({
		id:_C.POSTS_INFO_FORM,
		hiddenUpdate:"true",
		region:"center"
	});
	
	var win = new Ext.Window({
		id:_C.POSTS_WIN,
		title:"添加帖子",
		width:655,
		modal:true,
//		autoHeight:true,
		maximizable:true,
		items:[form]
	});
	win.show();
};

//编辑帖子窗口
ast.ast1949.admin.bbs.posts.EditFormWin=function(id){
	var form = new ast.ast1949.admin.bbs.posts.PostInfoForm({
		id:_C.POSTS_INFO_FORM,
		region:"center",
		hiddenRelease:"true",
		hiddenSave:"true"
	});
	
		var win = new Ext.Window({
		id:_C.POSTS_WIN,
		title:"修改帖子",
		width:655,
		modal:true,
//		autoHeight:true,
		maximizable:true,
		items:[form]
	});
	form.loadBbsPost(id);
	win.show();
};

//审核窗口
ast.ast1949.admin.bbs.posts.AuditWin=function(ids,_accounts){
	var form = new ast.ast1949.admin.bbs.posts.CancelAuditForm({
		id:_C.POSTS_CANCEL_AUDIT_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.POSTS_CANCEL_AUDIT_WIN,
		title:"审核帖子",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	
	win.show();
	Ext.get("ids").dom.value=ids;
	Ext.get("integral").dom.value="5";
	Ext.get("checkStatus").dom.value="1";
	Ext.get("accounts").dom.value=_accounts;
};

//取消审核窗口
ast.ast1949.admin.bbs.posts.CancelAuditWin=function(ids,accounts){
	var form = new ast.ast1949.admin.bbs.posts.CancelAuditForm({
		id:_C.POSTS_CANCEL_AUDIT_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.POSTS_CANCEL_AUDIT_WIN,
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
ast.ast1949.admin.bbs.posts.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}