Ext.namespace("ast.ast1949.admin.periodical");

var _C = new function(){
	this.PERIODICAL_WIN="periodicalwin";
	this.PERIODICAL_GRID="periodicalgrid";
}

Ext.onReady(function(){

	var listPeriodical = new ast.ast1949.admin.periodical.listPeriodicalGrid({
		id:_C.PERIODICAL_GRID,
		region:"center"
	});

	var viewport = new Ext.Viewport({
		layout : "border",
		items:[listPeriodical]
	});

});

ast.ast1949.admin.periodical.listPeriodicalGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+Context.PATH+"/admin/periodical/listPeriodical.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var _reader = ["id","name","cycle","numView","numUp","pdfDownload","size"
				,"gmtRelease","numRelease","releaseNo","gmtModified","gmtCreated"];
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			remoteSort:false,
			fields:_reader,
			url: _url,
			autoLoad:true
		});

		var _sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel([
			_sm,{
				header:"编号",
				dataIndex:"id",
				sortable:true,
				hidden:true
			},{
				header:"期刊名称",
				dataIndex:"name",
				sortable:false,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					//TODO 期刊下载地址
					if(record.get("pdfDownload")!=null && record.get("pdfDownload").length>0){
						return '<a href ="'+record.get("pdfDownload")+'" title="期刊大小:'+record.get("size")+'" target="_blank">'+value+'</a>';
					}
					return value;
				}
			},{
				header:"发刊周期",
				dataIndex:"cycle",
				sortable:false
			},{
				header:"浏览次数",
				dataIndex:"numView",
				sortable:true
			},{
				header:"支持数",
				dataIndex:"numUp",
				sortable:true
			},{
				header:"发行时间",
				dataIndex:"gmtRelease",
				sortable:true,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				header:"发行量",
				dataIndex:"numRelease",
				sortable:true
			}
		]);

		var grid = this;

		var c={
			cm:cm,
			store: _store,
			sm:_sm,
			viewConfig:{
				autoFill :true
			},
			tbar: [{
				text:"发布",
				iconCls : "add",
				handler :function(btn){
					// TODO 添加期刊信息
					ast.ast1949.admin.periodical.addPeriodicalWin();
				}
			},{
				text:"修改期刊",
				iconCls : "edit",
				handler :function(btn){
					// TODO 修改期刊信息

					var type = Ext.getCmp(_C.PERIODICAL_GRID);
					var select=type.getSelectionModel().getSelections();
					if(select.length > 0){
						ast.ast1949.admin.periodical.editPeriodicalWin(select[0].get("id"));
					}
				}
			},{
				text:"删除期刊",
				tooltip:"会删除期刊信息以及期刊子页信息",
				iconCls:"delete",
				handler:function(b){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"删除期刊后无法从前台看到期刊信息,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(_C.PERIODICAL_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+Context.PATH+"/admin/periodical/deletePeriodical.htm",
							params:{"id":row[0].get("id")},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									grid.getStore().reload();
								}else{
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "发生错误,信息没有被删除",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
									});
								}
							},
							failure:function(response,opt){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
										msg : "发生错误,信息没有被删除",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					});
				}
			},{
				text:"清空期刊页",
				iconCls : "delete",
				tooltip:"仅仅清空属于期刊的子页信息",
				handler :function(){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"清空期刊页后无法从前台看到期刊信息,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(_C.PERIODICAL_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+Context.PATH+"/admin/periodical/deletePeriodicalOnlyDetails.htm",
							params:{"id":row[0].get("id")},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									grid.getStore().reload();
								}else{
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "发生错误,信息没有被删除",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
									});
								}
							},
							failure:function(response,opt){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
										msg : "发生错误,信息没有被删除",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					});
				}
			},'->',{
				text:"期刊预览",
				iconCls : "view",
				handler :function(){
					//TODO 预览所有子页信息,如果没有子页则可以在这个页面里上传期刊子页信息(zip包)
//					var grid = Ext.getCmp(_C.PERIODICAL_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length>0){
						window.open(Context.ROOT+Context.PATH+"/admin/periodical/preview.htm?id="+row[0].get("id"));
					}
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

		ast.ast1949.admin.periodical.listPeriodicalGrid.superclass.constructor.call(this,c);
	}
});

ast.ast1949.admin.periodical.periodicalForm = Ext.extend(Ext.form.FormPanel,{
	isEdit:false,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

//		var form=this;

		var c={
			labelAlign : "right",
			labelWidth : 80,
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"98%",
					xtype:"textfield",
					labelSeparator:"",
					msgTarget :"under"
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					id:"name",
					name:"name",
					itemCls:"required",
					allowBlank:false,
					fieldLabel:"周刊标题"
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:"",
					msgTarget :"under"
				},
				items:[{
					id:"releaseNo",
					name:"releaseNo",
					fieldLabel:"刊号"
				},{
					xtype:"datefield",
					id:"gmtRelease",
					name:"gmtRelease",
					format:"Y-m-d",
					fieldLabel:"发行时间",
					value:new Date()
				},{
					xtype:"numberfield",
					id:"numView",
					name:"numView",
					fieldLabel:"浏览次数",
					value:0,
					readOnly:this.isEdit
				},{
					id:"pdfDownload",
					name:"pdfDownload",
					fieldLabel:"pdf下载地址"
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:"",
					msgTarget :"under"
				},
				items:[{
					id:"cycle",
					name:"cycle",
					fieldLabel:"发刊周期"
				},{
					xtype:"numberfield",
					id:"numRelease",
					name:"numRelease",
					fieldLabel:"发行量",
					value:0
				},{
					xtype:"numberfield",
					id:"numUp",
					name:"numUp",
					fieldLabel:"支持次数",
					value:0,
					readOnly:this.isEdit
				},{
					xtype:"numberfield",
					id:"size",
					name:"size",
					fieldLabel:"期刊大小(M)",
					value:0.0
				}]
			}
//			,{
//				xtype:"tabpanel",
//				columnWidth:1,
//				id:_C.IMAGE_TAB,
//				activeTab:0,
//				border:false,
////				defaults:{
////					height:200
////				},
//				items:[
//					new ast.ast1949.admin.periodical.periodicalDetailsPreview({
//						title:"子页预览"
//					})
//				]
//			}
			],
			buttons:[{
				text:"发布",
				scope:this,
				handler:function(btn){
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
							msg : "表单填写有错误，请仔细检查一下表单",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"取消",
				handler:function(btn){
					//关闭窗口
					Ext.getCmp(_C.PERIODICAL_WIN).close();
				}
			}]
		};

		ast.ast1949.admin.periodical.periodicalForm.superclass.constructor.call(this,c);

	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/periodical/createPeriodical.htm",
	loadOneRecord:function(id){
		// TODO 初始化参数表单
		//设置表单项不可用
		var _fields = ["id","name","cycle","numView","numUp","pdfDownload","size"
				,"gmtRelease","numRelease","releaseNo","gmtModified","gmtCreated"];

		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT +Context.PATH + "/admin/periodical/listOnePeriodical.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据载入错误...");
					} else {
						form.getForm().loadRecord(record);
						if(record.get("gmtRelease")!=null){
							form.findById("gmtRelease").setValue(Ext.util.Format.date(new Date(record.get("gmtRelease").time), 'Y-m-d'))
						}
					}
				}
			}
		})
	},
	onSaveSuccess:function(_form,_action){
			Ext.getCmp(_C.PERIODICAL_GRID).getStore().reload();
			Ext.getCmp(_C.PERIODICAL_WIN).close();
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

ast.ast1949.admin.periodical.addPeriodicalWin =function(){
	var form = new ast.ast1949.admin.periodical.periodicalForm({
//		id:_C.PRODUCT_INFO_FORM,
		region:"center",
		saveUrl:Context.ROOT+Context.PATH+"/admin/periodical/createPeriodical.htm"
	});

	var win = new Ext.Window({
		id:_C.PERIODICAL_WIN ,
		title:"发布新期刊",
		width:600,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

ast.ast1949.admin.periodical.editPeriodicalWin = function(id){
	var form = new ast.ast1949.admin.periodical.periodicalForm({
		region:"center",
		isEdit:true,
		saveUrl:Context.ROOT+Context.PATH+"/admin/periodical/updatePeriodical.htm"
	});

	form.loadOneRecord(id);

	var win = new Ext.Window({
		id:_C.PERIODICAL_WIN ,
		title:"更改期刊信息",
		width:600,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}


