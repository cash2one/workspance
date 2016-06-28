Ext.namespace("ast.ast1949.bbs.post");

var POST=new function(){
	this.GRID="postgrid";
}

ast.ast1949.bbs.post.CHECKSTATUS=["未审核","通过","","退回"];

ast.ast1949.bbs.post.FIELDS=[
	{name:"id",mapping:"post.id"},
	{name:"company_id",mapping:"post.companyId"},
	{name:"title",mapping:"post.title"},
	{name:"content",mapping:"post.content"},
	{name:"bbsPostCategoryId",mapping:"post.bbsPostCategoryId"},
	{name:"bbsPostAssistId",mapping:"post.bbsPostAssistId"},
	{name:"account",mapping:"post.account"},
	{name:"visited_count",mapping:"post.visitedCount"},
	{name:"reply_count",mapping:"post.replyCount"},
	{name:"check_status",mapping:"post.checkStatus"},
	{name:"check_person",mapping:"post.checkPerson"},
	{name:"reply_time",mapping:"post.replyTime"},
	{name:"post_time",mapping:"post.postTime"},
	{name:"check_time",mapping:"post.checkTime"},
	{name:"is_del",mapping:"post.isDel"},
	{name:"nickname",mapping:"post.nickname"},
	{name:"unpass_reason",mapping:"post.unpassReason"},
	{name:"companyName",mapping:"company.name"},
	"membershipLabel",
	"contentIntro"
];

ast.ast1949.bbs.post.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.bbs.post.FIELDS,
			url:_url,
			autoLoad:false
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",
				hidden:true,
				dataIndex:"id"
			},{
				header:"内容",
				hidden:true,
				dataIndex:"content"
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
				width:25,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" alt="未删除"/>';
					} else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" alt="已删除"/>';
					}
				}
			},{
				header:"标题",
				sortable:false,
				width:250,
				dataIndex:"title",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					var _id=record.get("id");
					var  _url="";
					if(record.get("check_status")!="0"){
						_url="<a href='http://huzhu.zz91.com/detail/"+_id+".html' target='_blank'>"+value+"</a>";
					}else{
						_url=value;
					}
					return _url;
				}
			},{
				header:"内容",
				sortable:false,
				width:250,
				dataIndex:"contentIntro",
//				renderer:function(value,metadata,record,rowIndex,colndex,store){
//					console.log(value);
//				}
			},{
				header:"公司名称",
				sortable:false,
				dataIndex:"companyName",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					var _id=record.get("company_id");
					if(_id=="0"){
						return "<span style='color:Green'>管理员："+record.get("account")+"</span>";
					} else {
						return "<a href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+_id+"' target='_blank'>"+value+"</a>";
					}
				}
			},{
				header:"昵称",
				sortable:true,
				width:50,
				dataIndex:"nickname"
			},{
				header:"会员类型",
				sortable:false,
				width:100,
				dataIndex:"membershipLabel",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					var _id=record.get("company_id");
					if(_id=="0"){
						return "";
					} else {
						return "<span style='color:Red'>"+value+"</span>";
					}
				}
			},{
				header:"帖子类型",
				sortable:false,
				width:100,
				dataIndex:"bbsPostCategoryId",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value=="1"){
						return "废料问答";
					} else if(value=="2"){
						return "互助社区";
					}else if(value=="3"){
						return "废料学院";
					}else if(value=="106"){
						return "商圈";
					}
				}
			},{
				header:"查看量",
				sortable:true,
				width:50,
				dataIndex:"visited_count"
			},{
				header:"回复量",
				sortable:true,
				width:50,
				dataIndex:"reply_count"
			},{
				header:"最后回复时间",
				sortable:true,
				dataIndex:"reply_time",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}else{
						return "";
					}
				}
			},{
				header:"发布时间",
				sortable:true,
				dataIndex:"post_time",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}else{
						return "";
					}
				}
			},{
				header:"审核人",
				sortable:false,
				width:60,
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
				header:"审核未通过原因",
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
			autoExpandColumn:4,
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
		
		ast.ast1949.bbs.post.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/bbs/post/queryPost.htm",
	mytoolbar:[{
		text:"编辑",
		iconCls:"edit",
		menu:[{
			text:"添加",
			iconCls:"add",
			handler:function(btn){
				var url=Context.ROOT+Context.PATH+"/bbs/post/edit.htm";
				window.open(url);
			}
		},{
			iconCls:"edit",
			text:"修改",
			handler:function(btn){
				var grid = Ext.getCmp(POST.GRID);
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
					var url=Context.ROOT+Context.PATH+"/bbs/post/edit.htm?id="+_pid+"&companyId="+row[0].get("company_id");
					window.open(url);
				}
			}
		},"-",{
			text:"推荐",
			handler:function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.admin.dataIndex.SendIndex({
						title:row.get("title"),
						link:"http://huzhu.zz91.com/detail/"+row.get("id")+".html"
					});
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		},"-",{
			text:"一键转移至商圈",
			handler:function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.bbs.post.tosq();
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		},"-",{
			text:"置顶",
			handler:function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.admin.dataIndex.SendIndex({
						title:["{"+"'bid':'"+row.get("id")+"'","'title':'"+row.get("title")+"'","'account':'"+row.get("account")+"'",
						       "'postTime':'"+Ext.util.Format.date(new Date(row.get("post_time").time), 'Y-m-d H:i:s')+"'"+"}"],
						link:"http://huzhu.zz91.com/detail/"+row.get("id")+".html"
					});
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		},"-",{
			text:"推荐到头条",
			handler:function(btn){
				ast.ast1949.bbs.post.recommendPost();
			}
		},{
			text:"明星企业",
			handler:function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.admin.dataIndex.SendIndex({
						title:["{"+"'bid':'"+row.get("id")+"'","'title':'"+row.get("title")+"'","'content':''"+"}"],
						link:"http://mingxing.zz91.com/starDetail"+row.get("id")+".htm"
					});
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		},"-",{
			iconCls:"delete",
			text:"删除",
			handler:function(btn){
				ast.ast1949.bbs.post.updateDelete(1);
			}
		},{
			text:"恢复",
			handler:function(btn){
				ast.ast1949.bbs.post.updateDelete(0);
			}
		}
//		,"-",{
//			iconCls:"delete",
//			text:"彻底删除",
//			handler:function(btn){
//				ast.ast1949.bbs.post.doDelete();
//			}
//		}
		]
	},{
		text:"审核",
		menu:[{
			text:"通过",
			handler:function(btn){
				ast.ast1949.bbs.post.updateCheckStatus(1);
			}
		},{
			text:"退回",
			handler:function(btn){
				ast.ast1949.bbs.post.updateCheckStatus(3);
			}
		}]
	},"->","标题:",{
		xtype:"textfield",
		width:120,
		id:"search-title",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp(POST.GRID).getStore();
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
	},"公司名:",{
		xtype:"textfield",
		width:120,
		id:"search-company-name",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp(POST.GRID).getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["companyName"] = field.getValue();
				}else{
					B["companyName"]=undefined;
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
				var _store = Ext.getCmp(POST.GRID).getStore();
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
	
ast.ast1949.bbs.post.updateDelete = function(isdel){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '你确定要这么做吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		
		var grid = Ext.getCmp(POST.GRID);
		
		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			
			Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/bbs/post/updateDelete.htm",
				params:{
					"isDel":isdel,
					"id":_id
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","帖子已处理");
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

ast.ast1949.bbs.post.doDelete=function(){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '删除之后不能恢复，你确定要这么做吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		
		var grid = Ext.getCmp(POST.GRID);
		
		var row = grid.getSelections();
		for (var i=0,len = row.length;i<len;i++){
			Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/bbs/post/delete.htm",
				params:{
					"id":row[i].get("id")
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","帖子已删除");
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

ast.ast1949.bbs.post.updateCheckStatus=function(checkstatus){
	var grid = Ext.getCmp(POST.GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/bbs/post/updateCheckStatus.htm",
			params:{
				"id":row[i].get("id"),
				"checkStatus":checkstatus,
				"companyId":row[i].get("company_id"),
				"account":row[i].get("account")
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","帖子已处理");
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

ast.ast1949.bbs.post.recommendPost = function(){
	Ext.MessageBox.confirm(Context.MSG_TITLE, '你确定要推荐到头条吗?',function(_btn){
		
		if(_btn != "yes")
			return ;
		
		var grid = Ext.getCmp(POST.GRID);
		
		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			
			Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/bbs/score/recommendPost.htm",
				params:{
					"postId":_id
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","帖子已推荐");
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

ast.ast1949.bbs.post.queryPost = function(gridid,param){
	
	Ext.getCmp("search-title").setValue("");
	Ext.getCmp("search-company-name").setValue("");
	Ext.getCmp("search-account").setValue("");
	
	var _store = Ext.getCmp(POST.GRID).getStore();
	_store.baseParams = param;
	_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
}

ast.ast1949.bbs.post.tosq = function(){
	var grid=Ext.getCmp(POST.GRID);
	var row = grid.getSelections();
	console.log(row)
	for(var i=0;i<row.length;i++){
		var _id=row[i].data.id;
	Ext.Ajax.request({
		url:Context.ROOT+Context.PATH+"/bbs/post/changSq.htm",
		params:{
			"id":_id
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				ast.ast1949.utils.Msg("","转移商圈成功");
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
ast.ast1949.bbs.post.Form = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			buttonAlign:"right",
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
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
					name:"id",
					id:"id"
				},{
					fieldLabel:"帖子标题",
					name:"title",
					allowBlank:false,
					itemCls:"required"
				},{
					xtype:"combo",
					fieldLabel:"帖子类型",
					name:"postTypeCombo",
					id:"postTypeCombo",
					mode:"local",
					triggerAction:"all",
					forceSelection: true,
					hiddenName:'postType',
					displayField:   'name',
					valueField:     'value',
					autoSelect:true,
					store:new Ext.data.JsonStore({
						fields : ['name', 'value'],
						data   : [
							{name:'普通帖子',value:'0'},
//							{name : '差贴',  value: '0'},
//							{name : '好帖',  value: '1'},
//							{name : '牛贴',  value: '2'},
							{name : '头条',  value: '3'},
							{name : '最新动态',  value: '4'},
							{name : '热门话题',  value: '5'},
							{name : '互助月刊',  value: '6'},
							{name : '互助周报',  value: '7'},
							{name : '实用商讯',  value: '8'}
						]
					})
				},{
					xtype:"numberfield",
					fieldLabel:"查看次数",
					name:"visitedCount"
				},{
					xtype:"datefield",
					fieldLabel:"发布时间", //发帖时默认当前时间
					format : 'Y-m-d H:i',
					name:"postTimeStr",
					id:"postTimeStr",
					allowBlank:false,
					itemCls:"required"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"combo",
					fieldLabel:"版块",
					allowBlank:false,
					itemCls:"required",
					name:"categoryCombo",
					mode:"local",
					triggerAction:"all",
					forceSelection: true,
					hiddenName:'bbsPostCategoryId',
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					store:new Ext.data.JsonStore({
						fields : ['name', 'value'],
						data   : [
							{name:'废料动态',value:'1'},
							{name:'行业知识',value:'2'},
							{name:'江湖风云',value:'3'},
							{name:'zz91动态',value:'4'},
							{name:'废料问答',value:'11'},
							{name:'纠纷处理',value:'13'},
							{name:'意见反馈',value:'14'},
							{name:'新手报道',value:'15'}
						]
					})
				},{
					xtype:"numberfield",
					fieldLabel:"查看积分",
					name:"integral"
				},{
					xtype:"numberfield",
					fieldLabel:"回复数",
					name:"replyCount"
				},{
					xtype:"datefield",
					fieldLabel:"回复时间",
					name:"replyTimeStr",
					format : 'Y-m-d H:i',
					id:"replyTimeStr"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"标签",
					name:"tags"
				},{
					xtype: "ckeditor",
					fieldLabel: "详细内容",
					id: "content",
					name: "content",
					CKConfig: { //CKEditor的基本配置，详情配置请结合实际需要。
						/* Enter your CKEditor config paramaters here or define a custom CKEditor config file. 
						 * e.g: Defualt Full
						 * toolbar:'Basic' //Full
						 * toolbar: [
						 *		[ 'Styles' , 'Format' ],
						 *		[ 'Bold' , 'Italic' , '-' , 'NumberedList' , 'BulletedList' , '-' , 'Link' , '-' , 'About' ]
						 *	]
						 *	 
						 * */
						//customConfig : '/ckeditor/config.js',
						toolbar:"Full",
						height : 500,
						width: "96%"
					}
				}]
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var _saveurl=Context.ROOT+Context.PATH+"/bbs/post/createPost.htm";
						if(form.findById("id").value>0){
							_saveurl=Context.ROOT+Context.PATH+"/bbs/post/updatePost.htm";
						}
						
						form.getForm().submit({
							url:_saveurl,
							method:"post",
							type:"json",
							success:function(){
								//刷新日志表格
								ast.ast1949.utils.Msg("","贴子已保存，您可以继续添加或关闭页面！");
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
			},{
				text:"关闭",
				handler:function(btn){
					window.close();
				}
			}]
		};
		
		ast.ast1949.bbs.post.Form.superclass.constructor.call(this,c);
		
	},
	initFormField:function(){
		this.findById("postTimeStr").setValue(new Date());
		this.findById("postTypeCombo").setValue(0);
	},
	fields:["id","companyId","account","bbsUserProfilerId","bbsPostCategoryId",
		"title","content","isDel","checkPerson","checkStatus","checkTime",
		"unpassReason","postType","visitedCount","replyCount","postTime",
		"replyTime","integral","tags","gmtCreated","gmtModified"],
	loadPost:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+Context.PATH+"/bbs/post/queryOnePost.htm?id="+id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						ast.ast1949.bbs.reply.TARGETPOSTTITLE=record.get("title");
						form.findById("postTimeStr").setValue(new Date(record.get("postTime").time));
						form.findById("replyTimeStr").setValue(new Date(record.get("replyTime").time));
					}
				}
			}
		});
	}
});
ast.ast1949.bbs.post.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 80,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"combo",
                    name:"checkStatusCombo",
					id:"checkStatusCombo",
					mode:"local",
					fieldLabel:"审核状态",
					hiddenName:'checkStatus',
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
			   		store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'未审核',value:'0'},
						{name:'审核通过',value:'1'},
						{name:'审核不通过',value:'3'}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["checkStatus"] = field.getValue();
								}else{
									B["checkStatus"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
				},{
					xtype:"combotree",
					fieldLabel:"帖子类别",
					id : "categoryLabel",
					name:"categoryLabel",
					hiddenName:"bbsPostAssistId",
					hiddenId:"bbsPostAssistId",
					allowBlank:false,
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							autoLoad: true,
							url:Context.ROOT + Context.PATH+ "/bbs/bbscategory/categoryTreeNode.htm",
							listeners:{
								beforeload:function(treeload,node){
									this.baseParams["parentCode"] = node.attributes["data"];
								}
							}
						}),
						root : new Ext.tree.AsyncTreeNode({text:'所有类别',data:"0"})
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["bbsPostAssistId"] = field.hiddenField.defaultValue;
								}else{
									B["bbsPostAssistId"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
				},{
					xtype:"combo",
                    name:"isDelCombo",
					id:"isDelCombo",
					mode:"local",
					fieldLabel:"删除状态",
					hiddenName:'isDel',
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
			   		store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'已删除',value:'1'},
						{name:'未删除',value:'0'}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["isDel"] = field.getValue();
								}else{
									B["isDel"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
					
				},{
					xtype:"combo",
                    name:"companyIdCombo",
					id:"companyIdCombo",
					mode:"local",
					fieldLabel:"发布",
					hiddenName:'companyId',
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
			   		store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'会员发布',value:'-1'},
						{name:'zz91发布',value:'0'}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["companyId"] = field.getValue();
								}else{
									B["companyId"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
				},{
					xtype:"combo",
                    name:"postTypeCombo",
					id:"postTypeCombo",
					mode:"local",
					fieldLabel:"其他",
					hiddenName:'postType',
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
			   		store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'头条',value:'3'},
						{name:'最新动态',value:'4'},
						{name:'热门话题',value:'5'}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["postType"] = field.getValue();
								}else{
									B["postType"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
				},{
					xtype:"combo",
                    name:"selectTimeCombo",
					id:"selectTimeCombo",
					mode:"local",
					fieldLabel:"时间标准",
					hiddenName:'selectTime',
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					autoSelect:true,
			   		store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'发布时间',value:'post_time'},
						{name:'审核时间',value:'check_time'}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["selectTime"] = field.getValue();
								}else{
									B["selectTime"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
				},{
					xtype : "datefield",
						format:"Y-m-d",
						name:"from",
						fieldLabel:"时间(始)",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									_store.baseParams["from"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:m:s');
								}else{
									_store.baseParams["from"]=null;
								}
							}
						}
				},{
					xtype : "datefield",
						format:"Y-m-d",
						name : "to",
						fieldLabel:"时间(终)",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									_store.baseParams["to"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:m:s');
								}else{
									_store.baseParams["to"]=null;
								}
							}
						}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};
		
		ast.ast1949.bbs.post.SearchForm.superclass.constructor.call(this,c);

	}
});

