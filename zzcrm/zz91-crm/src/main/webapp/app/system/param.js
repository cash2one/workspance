Ext.namespace("com.zz91.crm.param");

var Const = new function(){
	this.PARAM_WIN = "paramwin";
	this.PARAM_GRID = "paramgrid";
	this.PARAMTYPE_WIN = "paramtypewin";
	this.PARAMTYPE_GRID = "paramtypegrid";
}

com.zz91.crm.param.listParamGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+"/system/param/listParamByTypes.htm",
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
			},{
				id:"edit-name",
				header:"名称",
				dataIndex:"name",
				sortable:true
			},{
				id:"edit-key",
				header:"名(key)",
				dataIndex:"key",
				sortable:true
			},{
				id:"edit-value",
				header:"值(value)",
				dataIndex:"value",
				sortable:true
			},{
				id:"edit-sort",
				header:"排序",
				dataIndex:"sort",
				sortable:true
			},{
				id:"edit-isuse",
				header:"状态",
				dataIndex:"isuse",
				sortable:true,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/themes/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/themes/icons/Item.False.gif" />';
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
				text: "添加参数",
				iconCls : "add16",
				handler :function(btn){
					// TODO 添加参数配置
					var type = Ext.getCmp(Const.PARAMTYPE_GRID);
					var select=type.getSelectionModel().getSelections();
					if(select.length > 0){
						com.zz91.crm.param.addParamWin(select[0]);
					}
				}
			},{
				text: "编辑参数",
				iconCls : "edit16",
				handler :function(btn){
					// TODO 修改参数配置
					var paramtypegrid = Ext.getCmp(Const.PARAM_GRID);
					var selectedRecord = paramtypegrid.getSelectionModel().getSelected();
					if(typeof(selectedRecord) == "undefined"){
						Ext.MessageBox.show({
							title: Context.MSG_TITLE,
							msg : "请至少选定一条记录(且选中左边所属类型)!",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.WARNING
						});
						return ;
					}
					var type = Ext.getCmp(Const.PARAMTYPE_GRID);
					var select=type.getSelectionModel().getSelections();
					if(select.length > 0){
						com.zz91.crm.param.editParamWin(selectedRecord,select[0]);
					}
				}
			},{
				text: "删除参数",
				iconCls:"delete16",
				handler:function(b){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"删除参数可能会影响系统稳定,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(Const.PARAM_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+"/system/param/deleteParam.htm",
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
									title:MESSAGE.title,
									msg : MESSAGE.submitFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					});
				}
			},{
				text: "与外网同步",
				iconCls : "add16",
				handler :function(btn){
					var grid = Ext.getCmp(Const.PARAM_GRID);
					var type = Ext.getCmp(Const.PARAMTYPE_GRID);
					var row = type.getSelectionModel().getSelections();
					Ext.Ajax.request({
						url:Context.ROOT+"/system/param/updateWWWParam.htm",
						params:{"types":row[0].get("key")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								grid.getStore().reload();
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "同步成功!共同步"+obj.data+"条数据",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.NONE
								});
							}else{
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "发生错误,信息没有被同步",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title:MESSAGE.title,
								msg : MESSAGE.submitFailure,
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});
				}
			}]
		};

		com.zz91.crm.param.listParamGrid.superclass.constructor.call(this,c);
	}
});

com.zz91.crm.param.listParamTypeGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+"/system/param/listParamType.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var _reader = [
			{name:"id",mapping:"id"},
			{name:"key",mapping:"key"},
			{name:"name",mapping:"name"},
			{name:"gmtCreated",mapping:"gmtCreated"}
			];
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			remoteSort:false,
			fields:_reader,
			url: _url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect:true
		});
		
		var cm = new Ext.grid.ColumnModel([
			_sm,{
				header:"健",
				dataIndex:"key",
				sortable:true
			},{
				header:"名称",
				dataIndex:"name",
				sortable:true
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
				text: "添加参数类型",
				iconCls : "add16",
				handler :function(btn){
					// TODO 添加参数配置
					com.zz91.crm.param.addParamTypeWin();
				}
			},{
				text: "修改参数类型",
				iconCls : "edit16",
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
					com.zz91.crm.param.editParamTypeWin(selectedRecord);
				}
			},{
				text: "删除参数类型(慎重)",
				iconCls:"delete16",
				handler:function(b){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"删除参数类型可能会影响系统稳定,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(Const.PARAMTYPE_GRID);
						var paramGrid=Ext.getCmp(Const.PARAM_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+"/system/param/deleteParamType.htm",
							params:{"key":row[0].get("key")},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									grid.getStore().reload();
									paramGrid.getStore().reload();
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
									title:MESSAGE.title,
									msg : MESSAGE.submitFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					});
				}
			}]
		};

		com.zz91.crm.param.listParamTypeGrid.superclass.constructor.call(this,c);
	}
});

com.zz91.crm.param.paramForm = Ext.extend(Ext.form.FormPanel,{
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
				fieldLabel:"参数类型",
				id:"typename",
				name:"typename",
				readOnly:true
			},{
				id:"name",
				name:"name",
				fieldLabel:"参数名称"
			},{
				fieldLabel:"健(key)",
				id:"key",
				name:"key",
				allowBlank:false,
				itemCls:"required"
			},{
				fieldLabel:"值(value)",
				id:"value",
				name:"value"
			},{
				xtype:"numberfield",
				fieldLabel:"排序",
				id:"sort",
				name:"sort",
				value:0
			},{
				xtype:"checkbox",
				fieldLabel:"正常",
				id:"isuse",
				name:"isuse",
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

		com.zz91.crm.param.paramForm.superclass.constructor.call(this,c);

	},
	saveUrl:Context.ROOT+"/system/param/createParam.htm",
	loadParam:function(record){
		var form = this;
		// TODO 初始化参数表单
		form.getForm().loadRecord(record);
	},
	onSaveSuccess:function(_form,_action){
			Ext.getCmp(Const.PARAM_GRID).getStore().reload();
			Ext.getCmp(Const.PARAM_WIN).close();
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存失败(不能存在相同的Key)",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

com.zz91.crm.param.addParamWin = function (typerecord){
	var form = new com.zz91.crm.param.paramForm({
		region:"center",
		saveUrl:Context.ROOT+"/system/param/createParam.htm"
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

com.zz91.crm.param.editParamWin = function(record,typerecord){
	var form = new com.zz91.crm.param.paramForm({
		region:"center",
		saveUrl:Context.ROOT+"/system/param/updateParam.htm"
	});

	form.loadParam(record);

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

com.zz91.crm.param.paramTypeForm = Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel:"编号"
			},{
				id:"name",
				fieldLabel:"参数名称",
				allowBlank:false,
				itemCls:"required"
			},{
				fieldLabel:"健(key)",
				id:"key",
				allowBlank:false,
				itemCls:"required"
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
							msg : "请检查表单是否正确填写!",
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

		com.zz91.crm.param.paramTypeForm.superclass.constructor.call(this,c);

	},
	saveUrl:Context.ROOT+"/system/param/createParamType.htm",
	onSaveSuccess:function(_form,_action){
		Ext.getCmp(Const.PARAMTYPE_GRID).getStore().reload();
		Ext.getCmp(Const.PARAMTYPE_WIN).close();
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "保存失败(不能存在相同Key)!",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	},
	loadParamType:function(record){
		this.getForm().loadRecord(record);
	}
});

com.zz91.crm.param.addParamTypeWin = function (){
	var form = new com.zz91.crm.param.paramTypeForm({
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

com.zz91.crm.param.editParamTypeWin = function(record){
	var form = new com.zz91.crm.param.paramTypeForm({
		region:"center",
		saveUrl:Context.ROOT+"/system/param/updateParamType.htm"
	});

	form.loadParamType(record);
	
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

