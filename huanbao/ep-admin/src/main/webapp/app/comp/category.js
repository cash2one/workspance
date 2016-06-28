Ext.namespace("com.zz91.ep.admin.comp");

var Const = new function(){
	this.COMPCATEGORY_WIN = "compCategorywin";
	this.COMPCATEGORY_GRID = "compCategorygrid";
}


com.zz91.ep.admin.comp.listCompCategoryGrid = Ext.extend(Ext.grid.GridPanel,{
	listUrl:Context.ROOT+"/comp/compcategory/listCompCategory.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var _reader = ["id","code","name","gmtCreated"];
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
				id:"edit-id",
				header:"编号",
				dataIndex:"id",
				sortable:true,
				hidden:true
			},{id:"edit-name",
				header:"名称",
				dataIndex:"name",
				sortable:true
			},{id:"edit-code",
				header:"代码(code)",
				dataIndex:"code",
				sortable:true
			},{id:"edit-value",
				header:"创建时间",
				dataIndex:"gmtCreated",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
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
				text: "添加",
				iconCls : "add16",
				handler :function(btn){
					// TODO 添加参数配置
						com.zz91.ep.admin.comp.addCompCategoryWin();
					}
				},{
					text: "修改",
					iconCls : "edit16",
					handler :function(btn){
						// TODO 修改参数配置
						var compCategoryGrid = Ext.getCmp(Const.COMPCATEGORY_GRID);
						var selectedRecord = compCategoryGrid.getSelectionModel().getSelected();
						if(typeof(selectedRecord) == "undefined"){
							Ext.MessageBox.show({
								title: Context.MSG_TITLE,
								msg : "请至少选定一条记录",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.WARNING
							});
							return ;
						}
						com.zz91.ep.admin.comp.editCompCategoryWin(selectedRecord.get("id"));
					}
				},{
				text: "删除",
				iconCls:"delete16",
				handler:function(b){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"删除参数可能会影响系统稳定,你确定要这么做吗?",function(btn){
						if(btn!="yes"){
							return ;
						}

						var grid = Ext.getCmp(Const.COMPCATEGORY_GRID);
						var row = grid.getSelectionModel().getSelections();

						Ext.Ajax.request({
							url:Context.ROOT+"/comp/compcategory/deleteCompCategory.htm",
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
			}]
		};

		com.zz91.ep.admin.comp.listCompCategoryGrid.superclass.constructor.call(this,c);
	}
});


com.zz91.ep.admin.comp.compCategoryForm = Ext.extend(Ext.form.FormPanel,{
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
				id:"code",
				name:"code",
				fieldLabel:"代码",
				allowBlank:false
			},{
				id:"name",
				name:"name",
				fieldLabel:"名称",
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
					//关闭窗口
					Ext.getCmp(Const.COMPCATEGORY_WIN).close();
				}
			}]
		};

		com.zz91.ep.admin.comp.compCategoryForm.superclass.constructor.call(this,c);

	},
	saveUrl:Context.ROOT+"/comp/compcategory/createCompCategory.htm",
	loadCompCategory:function(id){
		// TODO 初始化参数表单
		//设置表单项不可用
		var _fields = ["id","name","code"];

		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT  + "/comp/compcategory/listOneCompCategory.htm",
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
			Ext.getCmp(Const.COMPCATEGORY_GRID).getStore().reload();
			Ext.getCmp(Const.COMPCATEGORY_WIN).close();
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

com.zz91.ep.admin.comp.addCompCategoryWin = function (){
	var form = new com.zz91.ep.admin.comp.compCategoryForm({
		region:"center"
	});
	var win = new Ext.Window({
		id:Const.COMPCATEGORY_WIN,
		title:"添加公司类型",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

com.zz91.ep.admin.comp.editCompCategoryWin = function(id){
	var form = new com.zz91.ep.admin.comp.compCategoryForm({
		region:"center",
		saveUrl:Context.ROOT+"/comp/compcategory/updateCompCategory.htm"
	});

	form.loadCompCategory(id);

	var win = new Ext.Window({
		id:Const.COMPCATEGORY_WIN,
		title:"修改公司类型",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}
