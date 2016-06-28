Ext.namespace("ast.ast1949.admin.adminUser");
//定义一个添加,编辑的父窗体类,继承自Window

ast.ast1949.admin.adminUser.InfoFormWin = Ext.extend(Ext.Window,{
	_form:null,
	_role:null,
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}

		Ext.apply(this,_cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";

		this._form = new Ext.form.FormPanel({
			id:"adminUserForm",
			region:"center",
			frame:true,
			bodyStyle:"padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 90,
			width:"100%",
			items:[{
//				xtype:"fieldset",
				layout:"form",
				autoHeight:true,
//				title:"用户基本信息",
				items:[{
						xtype:"hidden",
						name:"id",
						id:"id",
						dataIndex:"id"
					},{
						xtype:"hidden",
						name:"roleArray",
						id:"roleArray"
					},{
						xtype:"textfield",
						itemCls :"required",
						fieldLabel:"管理员账户",
						allowBlank:false,
						name:"username",
						tabIndex:1,
						anchor:"95%",
						blankText : "用户名称不能为空",
						vtype:"account"
					},{
						xtype:"textfield",
//						inputType:"password",
						itemCls :"required",
						fieldLabel:"密码",
						allowBlank:false,
						name:"password",
						tabIndex:3,
						anchor:"95%",
						blankText:"密码不能为空",
						vtype:"password"
					},{
						xtype:"textfield",
						itemCls :"required",
						fieldLabel:"邮箱地址",
						allowBlank:false,
						name:"email",
						tabIndex:3,
						anchor:"95%",
						blankText:"邮箱不能为空",
						vtype:"email"
					},{
						xtype:"textfield",
//						inputType:"password",
						itemCls :"required",
						fieldLabel:"邮箱密码",
						allowBlank:false,
						name:"emailPwd",
						tabIndex:3,
						anchor:"95%",
						blankText:"邮箱密码不能为空,邮箱将作为管理员的默认发送邮箱"
					},{
						xtype:"textfield",
						fieldLabel:"真实姓名",
						allowBlank:true,
						name:"realName",
						tabIndex:3,
						anchor:"95%"
					},{
						xtype:"checkbox",
						fieldLabel:"是否主管",
						boxLabel:"是",
						name:"isManager",
						inputValue:"1"
					},{
						xtype:"checkbox",
						fieldLabel:"是否为编辑",
						boxLabel:"是",
						name:"isEditor",
						inputValue:"1"
					},{
						xtype:"checkbox",
						fieldLabel:"可查看密码",
						boxLabel:"是",
						name:"canSeePwd",
						inputValue:"1"
					}]
			}],
			buttons:[{
				id:"save",
				text:"保存",
				hidden:_notView
			},{
				id:"cancel",
				text:"取消",
				hidden:_notView
			},{
				id:"close",
				text:"关闭",
				hidden:_isView
			}]
		});

		this._role=ast.ast1949.admin.adminUser.userRoleGrid();

		ast.ast1949.admin.adminUser.InfoFormWin.superclass.constructor.call(this,{
			title:_title,
			closeable:true,
			width:600,
			height:300,
//			autoHeight:true,
			modal:true,
			border:false,
			plain:true,
			layout:"border",
			items:[this._form,
				{region:"east",
				layout:"fit",
				width:220,
				items:this._role}
			]
		});
		this.addEvents("saveSuccess","saveFailure","submitFailure");
	},
	submit:function(_url){
		if(this._form.getForm().isValid()){
			var grid=Ext.getCmp("myrolegrid");
			var _ids=grid.getStore().collect("id");
//			alert(_ids.join(","));
			this._form.findById("roleArray").setValue(_ids.join(","));

			this._form.getForm().submit({
				url:_url,
				method:"post",
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
	loadRecord:function(_record){
		this._form.getForm().loadRecord(_record);
		this._role.getStore().load({params:{username:_record.get("username")}});
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
//		alert(_form.getValues());
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	},
	initFocus:function(){
		var f = this._form.getForm().findField("username");
		f.focus("",true);
	}
});

ast.ast1949.admin.adminUser.userRoleGrid=function(){
	var _store = new Ext.data.JsonStore({
		root:"records",
		remoteSort:false,
		fields:["id","name","remark"],
		url: Context.ROOT+Context.PATH+"/admin/adminuser/listUserRole.htm",
		autoLoad:false
	});

	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.CheckboxSelectionModel(),
		{id:"edit-id",
			header:"编号",
			dataIndex:"id",
			sortable:true,
			hidden:true,
			width:20
		},{id:"edit-name",
			header:"角色",
			dataIndex:"name",
			sortable:true,
			width:50
		},{id:"edit-desc",
			header:"备注",
			dataIndex:"remark"
		}
	]);

	var role = Ext.data.Record.create([
           {name: 'id', type: 'int'},
           {name: 'name', type: 'string'},
           {name: 'remark', type: 'string'}
      ]);

	var grid = new Ext.grid.GridPanel({
		id:"myrolegrid",
		cm:cm,
		store: _store,
		clicksToEdit:2,
		selModel:new Ext.grid.CheckboxSelectionModel(),
		viewConfig:{
			autoFill :true
		},
		tbar: new Ext.Toolbar({
			items:[{
				text:"添加角色",
				iconCls : "add",
				handler :function(){
					ast.ast1949.admin.adminUser.selectRoleWin();
				}
			},{
				text:"删除角色",
				iconCls : "delete",
				handler :function(){
					var row = grid.getSelections();
					if(row.length==0){
						return false;
					}
					var form=Ext.getCmp("adminUserForm");
//					if(form.findById("id").getValue()>0){
//						var _ids = new Array();
//						for (var i=0,len = row.length;i<len;i++){
//							var _id=row[i].get("id");
//							_ids.push(_id);
//						}
//						Ext.Ajax.request({
//							url: Context.ROOT + Context.PATH + "/admin/adminuser/deleteUserRole.htm",
//							params:{roleArray:_ids.join(","), username:form.findById("username").getValue()},
//							method: "get",
//							success:function(response){
//								var res= Ext.decode(response.responseText);
//								if(!res.success){
//									Ext.MessageBox.show({
//										title:Context.MSG_TITLE,
//										msg : res.data,
//										buttons:Ext.MessageBox.OK,
//										icon:Ext.MessageBox.ERROR
//									});
//								}else{
//									grid.getStore().reload();
//								}
//							},
//							failure:function(response){
//								Ext.MessageBox.show({
//									title:Context.MSG_TITLE,
//									msg : "发生错误,请联系管理员!",
//									buttons:Ext.MessageBox.OK,
//									icon:Ext.MessageBox.ERROR
//								});
//							}
//						});
//					}else{
						for(var i=0;i<row.length;i++){
							grid.getStore().remove(row[i]);
						}
//					}

				}
			},{
				text:"刷新",
				iconCls : "refresh",
				handler :function(){
					grid.getStore().reload();
				}
			}]
		})
	});

	return grid;
}

ast.ast1949.admin.adminUser.selectRoleWin=function(){
	var win=new Ext.Window({
		id:"selectRoleWin",
		title:"为用户选择角色信息",
		height:300,
		width:400,
		layout:"fit",
		modal:true,
		items:ast.ast1949.admin.adminUser.roleGrid()
	});
	win.show();
}

ast.ast1949.admin.adminUser.roleGrid=function(){
	var _store = new Ext.data.JsonStore({
		root:"records",
		remoteSort:false,
		fields:["id","name","remark"],
		url: Context.ROOT+Context.PATH+"/admin/adminuser/listRole.htm",
		autoLoad:true
	});

	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.CheckboxSelectionModel(),
		{id:"edit-id",
			header:"编号",
			dataIndex:"id",
			sortable:true,
			hidden:true,
			width:20
		},{id:"edit-name",
			header:"角色",
			dataIndex:"name",
			sortable:true,
			width:50
		},{id:"edit-desc",
			header:"备注",
			dataIndex:"remark"
		}
	]);

	var grid = new Ext.grid.GridPanel({
		cm:cm,
		store: _store,
		clicksToEdit:2,
		selModel:new Ext.grid.CheckboxSelectionModel(),
		viewConfig:{
			autoFill :true
		},
		tbar: new Ext.Toolbar({
			items:[{
				text:"选择",
				iconCls : "add",
				handler :function(){
					var row = grid.getSelections();
					if(row.length==0){
						return false;
					}
					var myrolegrid=Ext.getCmp("myrolegrid");
					var myroleArray=myrolegrid.getStore().collect("id");
					var isexist=false;
					for(var i=0;i<row.length;i++){
						for(var j=0;j<myroleArray.length;j++){
							if(myroleArray[j]==row[i].get("id")){
								isexist=true;
							}
						}
						if(isexist){
							isexist=false;
							continue;
						}
						myrolegrid.getStore().insert(myrolegrid.getStore().getCount(),row[i]);
					}
					Ext.getCmp("selectRoleWin").close();
				}
			}]
		})
	});

	return grid;
}