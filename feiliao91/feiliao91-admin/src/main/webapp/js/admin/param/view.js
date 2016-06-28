Ext.namespace("ast.ast1949.admin.param");

var Const = new function(){
	this.PARAM_WIN = "paramwin";
	this.PARAM_GRID = "paramgrid";
	this.PARAMTYPE_WIN = "paramtypewin";
	this.PARAMTYPE_GRID = "paramtypegrid";
}

Ext.onReady(function(){

	var listParam = new ast.ast1949.admin.param.listParamGrid({
//		layout:"fit",
		id:Const.PARAM_GRID,
		region:"center"
	});

	var listParamType = new ast.ast1949.admin.param.listParamTypeGrid({
		id:Const.PARAMTYPE_GRID,
		region:"west",
		width:260,
		split:true,
		listeners:{
			"rowclick":function(grid,rowindex,e){
				listParam.getStore().reload({
					params:{types:grid.getStore().getAt(rowindex).get("key")}
				});
			}
		}
	});

	var viewport = new Ext.Viewport({
		layout : "border",
//		border : true,
		items:[listParam,listParamType]
	});


});

ast.ast1949.admin.param.listParamGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+Context.PATH+"/admin/param/listParamByTypes.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var _reader = ["id","name","types","key","value","sort","isuse","gmtCreated"];
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			remoteSort:false,
			fields:_reader,
			url: _url,
			autoLoad:false
		});

		var _sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect:true
		});

		var cm = new Ext.grid.ColumnModel([
			_sm,{
				id:"edit-id",
				header:"编号",
				dataIndex:"id",
				sortable:true,
				hidden:true
			},{id:"edit-name",
				header:"名称",
				dataIndex:"name",
				sortable:true
			},{id:"edit-key",
				header:"名(key)",
				dataIndex:"key",
				sortable:true
			},{id:"edit-value",
				header:"值(value)",
				dataIndex:"value",
				sortable:true
			},{id:"edit-sort",
				header:"排序",
				dataIndex:"sort",
				sortable:true
			},{id:"edit-isuse",
				header:"状态",
				dataIndex:"isuse",
				sortable:true,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
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
//				text:"新增配置",
				iconCls : "add",
				handler :function(btn){
					// TODO 添加参数配置
					var type = Ext.getCmp(Const.PARAMTYPE_GRID);
					var select=type.getSelectionModel().getSelections();
					if(select.length > 0){
						ast.ast1949.admin.param.addParamWin(select[0]);
					}
				}
			},{
//				text:"修改配置",
				iconCls : "edit",
				handler :function(btn){
					// TODO 修改参数配置
					var paramtypegrid = Ext.getCmp(Const.PARAM_GRID);
					var selectedRecord = paramtypegrid.getSelectionModel().getSelected();
					if(typeof(selectedRecord) == "undefined"){
						Ext.MessageBox.show({
							title: Context.MSG_TITLE,
							msg : "请至少选定一条记录",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.WARNING
						});
						return ;
					}
					var type = Ext.getCmp(Const.PARAMTYPE_GRID);
					var select=type.getSelectionModel().getSelections();
					if(select.length > 0){
						ast.ast1949.admin.param.editParamWin(selectedRecord.get("id"),select[0]);
					}
//					ast.ast1949.admin.param.editParamWin(0);
				}
			},{
				iconCls:"delete",
				handler:function(b){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"删除参数可能会影响系统稳定,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(Const.PARAM_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+Context.PATH+"/admin/param/deleteParam.htm",
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
			},"->",{
				text:"备份",
				iconCls:"save",
				tooltip:"如果不确定当前操作会带来什么样的后果，请先备份配置！",
				handler:function(btn){
					Ext.Ajax.request({
						url:Context.ROOT+Context.PATH+"/admin/param/backup.htm",
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
//								Ext.MessageBox.show({
//									title:Context.MSG_TITLE,
//									msg : "配置已备份",
//									buttons:Ext.MessageBox.OK,
//									icon:Ext.MessageBox.INFO
//								});
								var win = new Ext.Window({
					                id:Context.MSG_TITLE,
					                title:"备份内容，可以按Ctrl+A复制后，保存到文档中",
					                width:600,
					                autoHeight:true,
					                modal:true,
					                items:[{
					                	xtype:"textarea",
					                	height:450,
					                	value:obj.data,
					                	width:"100%"
					                }]
								});
								win.show();
							}else{
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "发生错误,配置没有备份",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
									msg : "发生错误,配置没有备份",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});
				}
			},{
				text:"重新加载",
				iconCls : "refresh",
				tooltip:"重新加载参数配置到缓存中，请谨慎操作！对配置的变更，需要重新加载才能生效",
				handler :function(){
//					Ext.getCmp(Const.PARAM_GRID).getStore().reload();
					//重新载入缓存

					Ext.Ajax.request({
						url:Context.ROOT+Context.PATH+"/admin/param/refreshcache.htm",
//						params:{"id":row[0].get("id")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "配置已重新载入",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							}else{
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "发生错误,配置没有重新载入",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
									msg : "发生错误,配置没有重新载入",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});

				}
			}]
		};

		ast.ast1949.admin.param.listParamGrid.superclass.constructor.call(this,c);
	}
});

ast.ast1949.admin.param.listParamTypeGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+Context.PATH+"/admin/param/listParamType.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var _reader = ["key","name","gmtCreated"];
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			remoteSort:false,
			fields:_reader,
			url: _url,
			autoLoad:true
		});
		var _sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect :true
		});
		var cm = new Ext.grid.ColumnModel([
			_sm,{
				header:"健",
				dataIndex:"key",
				sortable:true
			},{
				header:"名称",
				dataIndex:"name"
			}
		]);


		var c={
			cm:cm,
			store: _store,
			sm:_sm,
			viewConfig:{
				autoFill :true
			},
			tbar: [{
				iconCls : "add",
				handler :function(btn){
					// TODO 添加参数配置
					ast.ast1949.admin.param.addParamTypeWin();
				}
			},{
				iconCls : "edit",
				handler :function(btn){
					// TODO 修改参数配置
					var paramtypegrid = Ext.getCmp(Const.PARAMTYPE_GRID);
					var selectedRecord = paramtypegrid.getSelectionModel().getSelected();
					if(typeof(selectedRecord) == "undefined"){
						Ext.MessageBox.show({
							title: Context.MSG_TITLE,
							msg : "请至少选定一条记录",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.WARNING
						});
						return ;
					}
					ast.ast1949.admin.param.editParamTypeWin(selectedRecord.get("key"));
				}
			},{
				iconCls:"delete",
				handler:function(b){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"删除参数类型可能会影响系统稳定,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(Const.PARAMTYPE_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+Context.PATH+"/admin/param/deleteParamType.htm",
							params:{"key":row[0].get("key")},
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
			}]
		};

		ast.ast1949.admin.param.listParamTypeGrid.superclass.constructor.call(this,c);
	}
});

ast.ast1949.admin.param.paramForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:"",
				msgTarget :"under"
			},
			items:[{
				xtype:"hidden",
				id:"id",
				name:"id"
			},{
				xtype:"hidden",
				id:"types",
				name:"types"
			},{
				id:"typename",
				name:"typename",
				readOnly:true,
				fieldLabel:"参数类型"
			},{
				id:"name",
				name:"name",
				fieldLabel:"参数名称"
			},{
				id:"key",
				name:"key",
				fieldLabel:"健(key)",
				itemCls:"required",
				allowBlank:false
			},{
				id:"value",
				key:"value",
				fieldLabel:"值(value)"
			},{
				xtype:"numberfield",
				id:"sort",
				name:"sort",
				fieldLabel:"排序"
			},{
				xtype:"checkbox",
				id:"isuse",
				name:"isuse",
				fieldLabel:"正常",
				inputValue:1
			}],
			buttons:[{
				text:"保存",
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
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"取消",
				handler:function(btn){
					//关闭窗口
					Ext.getCmp(Const.PARAM_WIN).close();
				}
			}]
		};

		ast.ast1949.admin.param.paramForm.superclass.constructor.call(this,c);

	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/param/createParam.htm",
	loadParam:function(id){
		// TODO 初始化参数表单
		//设置表单项不可用
		var _fields = ["id","name","type","key","value","sort","isuse"];

		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT +Context.PATH + "/admin/param/listOneParam.htm",
			baseParams:{"id":id},
			autoLoad : true
			,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据载入错误...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	},
	onSaveSuccess:function(_form,_action){
//		if(_action.result.success){
			Ext.getCmp(Const.PARAM_GRID).getStore().reload();
			Ext.getCmp(Const.PARAM_WIN).close();
//		}else{
//			Ext.MessageBox.show({
//				title:Context.MSG_TITLE,
//				msg : _action.result.data,
//				buttons:Ext.MessageBox.OK,
//				icon:Ext.MessageBox.ERROR
//			});
//		}
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

ast.ast1949.admin.param.addParamWin = function (typerecord){
	var form = new ast.ast1949.admin.param.paramForm({
//		id:Const.PRODUCT_INFO_FORM,
		region:"center",
		saveUrl:Context.ROOT+Context.PATH+"/admin/param/createParam.htm"
	});

	form.findById("typename").setValue(typerecord.get("name"));
	form.findById("types").setValue(typerecord.get("key"));

	var win = new Ext.Window({
		id:Const.PARAM_WIN ,
		title:"添加参数信息",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

ast.ast1949.admin.param.editParamWin = function(id,typerecord){
	var form = new ast.ast1949.admin.param.paramForm({
//		id:Const.PRODUCT_INFO_FORM,
		region:"center",
		saveUrl:Context.ROOT+Context.PATH+"/admin/param/updateParam.htm"
	});

	form.loadParam(id);

	form.findById("typename").setValue(typerecord.get("name"));

	var win = new Ext.Window({
		id:Const.PARAM_WIN ,
		title:"修改参数信息",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

ast.ast1949.admin.param.paramTypeForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:"",
				msgTarget :"under"
			},
			items:[{
				id:"name",
				fieldLabel:"参数名称"
			},{
				id:"key",
				fieldLabel:"健(key)",
				itemCls:"required",
				allowBlank:false
			}],
			buttons:[{
				text:"保存",
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
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp(Const.PARAMTYPE_WIN).close();
				}
			}]
		};

		ast.ast1949.admin.param.paramTypeForm.superclass.constructor.call(this,c);

	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/param/createParamType.htm",
	onSaveSuccess:function(_form,_action){
		Ext.getCmp(Const.PARAMTYPE_GRID).getStore().reload();
		Ext.getCmp(Const.PARAMTYPE_WIN).close();
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : _action.result.data,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	},
	loadParamType:function(key){

		var _fields = ["name","key"];

		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + Context.PATH + "/admin/param/listOneParamType.htm",
			baseParams:{"key":key},
			autoLoad : true
			,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据载入错误...");
					} else {
						form.getForm().loadRecord(record);
						form.findById("key").disable();
					}
				}
			}
		})
	}
});

ast.ast1949.admin.param.addParamTypeWin = function (){
	var form = new ast.ast1949.admin.param.paramTypeForm({
		region:"center"
	});
	var win = new Ext.Window({
		id:Const.PARAMTYPE_WIN,
		title:"添加参数信息",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

ast.ast1949.admin.param.editParamTypeWin = function(key){
	var form = new ast.ast1949.admin.param.paramTypeForm({
		region:"center",
		saveUrl:Context.ROOT+Context.PATH+"/admin/param/updateParamType.htm?key="+key
	});

	form.loadParamType(key);

	var win = new Ext.Window({
		id:Const.PARAMTYPE_WIN,
		title:"修改参数信息",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

