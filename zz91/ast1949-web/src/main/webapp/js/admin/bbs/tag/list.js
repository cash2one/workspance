/*
 * 标签管理
 */
Ext.namespace("ast.ast1949.admin.bbs.tag")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
	this.TAG_EDIT_FORM="tageditform";
	this.TAG_EDIT_WIN="tageditwin";
	this.TAG_ADD_FORM="tagaddform";
	this.TAG_ADD_WIN="tagaddwin";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.bbs.tag.ResultGrid({
		title:"标签列表",
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/bbs/tag/list.htm",
		region:'center',
		autoScroll:true
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[resultgrid]
	});

	//	resultgrid.searchByIsAdmin();
});

//标签列表
ast.ast1949.admin.bbs.tag.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("editButton").enable();
	                    Ext.getCmp("deleteButton").enable();
	                } else {
	                    Ext.getCmp("editButton").disable();
	                    Ext.getCmp("deleteButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"名称",
				sortable:false,
				dataIndex:"name"
			},{
				header:"排序",
				sortable:false,
				dataIndex:"sort"
			},{
				header:"属于管理员",
				sortable:false,
				dataIndex:"isAdmin",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value.length>0&&value=="1"){
						return "是";
					} else {
						return "否";
					}
				}
			}
		]);
		
		var c={
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
				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.bbs.tag.ResultGrid.superclass.constructor.call(this,c);
	},
	searchByIsAdmin:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("isAdminBtn").getValue()){
			ary.push(1);
		}else{
			ary.push(0);
		}
		B["isAdmin"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"bbsTags.id"},
		{name:"name",mapping:"bbsTags.name"},
		{name:"sort",mapping:"bbsTags.sort"},
		{name:"isAdmin",mapping:"bbsTags.isAdmin"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/posts/list.htm",
	mytoolbar:[{
		xtype:"label",
		text:"名称 ："
	},{
		xtype:"textfield",
		id:"name",
		name:"name",
		width:160
	},{
		iconCls:"query",
		text:"搜索",
		handler:function(btn){
			var resultgrid = Ext.getCmp(_C.RESULT_GRID);

			resultgrid.store.baseParams={};
			resultgrid.store.baseParams = {"name":Ext.get("name").dom.value};
			//定位到第一页
			resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		}
	}],
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:[{
						iconCls:"add",
						text:"添加",
						handler:function(btn){
							ast.ast1949.admin.bbs.tag.addFormWin();
						}
					},"-",{
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
								var _id=row[0].get("id");
								ast.ast1949.admin.bbs.tag.editFormWin(_id)
							}
						}
					},"-",{
						iconCls:"delete",
						id:"deleteButton",
						text:"删除",
						disabled:true,
						handler:function(btn){
							Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
						}
					},"->",{
						xtype:"checkbox",
						boxLabel:"只显示管理员",
						id:"isAdminBtn",
						checked:false,
						listeners:{
							"check":function(field,newvalue,oldvalue){
								var resultgrid=Ext.getCmp(_C.RESULT_GRID);
								resultgrid.searchByIsAdmin();
							}
						}
					}]
		});
		
		tbar2.render(this.tbar);
	}
});

//标签表单
ast.ast1949.admin.bbs.tag.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
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
					id:"id",
					name:"id"
				},{
//					xtype:"textarea",
					name:"name",
					fieldLabel:"名称:",
					allowBlank:false
				},{
					name:"signType",
					fieldLabel:"标签分类:"		
				},{
					name:"sort",
					fieldLabel:"排序:"
				},new Ext.form.RadioGroup({
					fieldLabel:"属于管理员:",
					items:[{
						name:"isAdmin",
						inputValue:"1",
						boxLabel:"是"					
					},{
						name:"isAdmin",
						inputValue:"0",
						boxLabel:"否"
					}]
				}),{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注:"
				}]
			}],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(_C.TAG_EDIT_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.bbs.tag.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"name",mapping:"name"},
			{name:"signType",mapping:"signType"},
			{name:"sort",mapping:"sort"},
			{name:"isAdmin",mapping:"isAdmin"},
			{name:"remark",mapping:"remark"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/bbs/tag/getSingleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	},
	
	saveUrl:Context.ROOT+Context.PATH + "/admin/bbs/tag/add.htm",
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
			msg : "操作成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		ast.ast1949.admin.bbs.tag.resultGridReload();
		Ext.getCmp(_C.TAG_EDIT_WIN).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
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
		_ids.push(_id);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/bbs/tag/delete.htm?random="+Math.random()+"&ids="+_ids.join(','),
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


//编辑标签窗口
ast.ast1949.admin.bbs.tag.addFormWin=function(){
		
	var form = new ast.ast1949.admin.bbs.tag.editForm({
		id:_C.TAG_ADD_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.TAG_EDIT_WIN,
		title:"添加标签",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	win.show();
};

//编辑标签窗口
ast.ast1949.admin.bbs.tag.editFormWin=function(id){
		
	var form = new ast.ast1949.admin.bbs.tag.editForm({
		id:_C.TAG_EDIT_FORM,
		region:"center",
		saveUrl:Context.ROOT+Context.PATH + "/admin/bbs/tag/update.htm"
	});
	
		var win = new Ext.Window({
		id:_C.TAG_EDIT_WIN,
		title:"修改标签",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};

//重新绑定Grid数据
ast.ast1949.admin.bbs.tag.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}