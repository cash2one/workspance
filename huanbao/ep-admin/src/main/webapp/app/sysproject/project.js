Ext.namespace("com.zz91.ep.project");

var PROJECT = new function(){
	this.PROJECT_FORM="projectform";
	this.PROJECT_WIN = "projectwindows";
	this.PROJECT_GRID = "projectgrid";
}

com.zz91.ep.project.Field=[
	{name:"id",mapping:"id"},
	{name:"code",mapping:"code"},
	{name:"password",mapping:"password"},
	{name:"name",mapping:"name"},
	{name:"rightCode",mapping:"rightCode"},
	{name:"url",mapping:"url"},
	{name:"avatar",mapping:"avatar"},
	{name:"version",mapping:"version"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.ep.project.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.project.Field,
				url:Context.ROOT +  "/sysproject/querySysProject.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "代号",
			width : 250,
			sortable : false,
			dataIndex : "code"
		},{
			header : "密码",
			sortable : false,
			dataIndex : "password" 
		},{
			header :"项目名称",
			sortable : false,
			width : 200,
			dataIndex:"name",
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var url=record.get("url");
				if(url!=null && typeof(url)!="undefined" && url.length>0){
					return "<a href='"+url+"' target='_blank' >"+value+"</a>" ;
				}
				return value;
			}
		},{
			header : "版本",
			sortable : false,
			dataIndex : "version"
		},{
			header : "创建时间",
			sortable : false,
			dataIndex : "gmtCreated",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "最后修改时间",
			sortable : false,
			dataIndex : "gmtModified",
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
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: MESSAGE.paging.displayMsg,
				emptyMsg : MESSAGE.paging.emptyMsg,
				beforePageText : MESSAGE.paging.beforePageText,
				afterPageText : MESSAGE.paging.afterPageText,
				paramNames : MESSAGE.paging.paramNames
			})
		};
		
		com.zz91.ep.project.Grid.superclass.constructor.call(this,c);
	},
	mytoolbar:[{
		text : '添加',
		tooltip : '添加',
		iconCls : 'add16',
		handler : function(btn){
			com.zz91.ep.project.addprojectWin();
		}
	}, {
		text : '编辑',
		tooltip : '编辑',
		iconCls : 'edit16',
		handler : function(btn){
//			// 实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
			var row = Ext.getCmp(PROJECT.PROJECT_GRID).getSelectionModel().getSelected();
			if(row!=null){
				com.zz91.ep.project.editprojectWin(row.get("id"));
			}
		}

	}, {
		text : '删除',
		tooltip : '删除记录',
		iconCls : 'delete16',
		handler : function(btn){
			var row = Ext.getCmp(PROJECT.PROJECT_GRID).getSelectionModel().getSelections();
			if (row.length > 0) {
				Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + row.length + '条记录?', function(_btn){
					if (_btn != "yes")
						return;
					com.zz91.ep.project.DeleteProject(row);
				});
			}
		}
	},"->",{
		xtype : "textfield",
		id : "search-name",
		emptyText:"搜索软件名称",
		listeners:{
			"change":function(field){
				var _store = Ext.getCmp(PROJECT.PROJECT_GRID).getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["name"] = field.getValue();
				}else{
					B["name"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
			}
		}
	}]
});

com.zz91.ep.project.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			region:"center",
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "代号",
					allowBlank : false,
					itemCls :"required",
					name : "code",
					id : "code"
				}, {
					fieldLabel : "项目名称",
					allowBlank : false,
					itemCls :"required",
					name : "name"
				}, {
					readOnly:true,
					fieldLabel:"权限根结点",
					name:"rightCode",
					id:"rightCode",
					listeners:{
						"focus":function(field){
							//选择权限根，弹出树选择框
							var initValue=Ext.getCmp("rightCode").getValue();
							com.zz91.ep.project.choiceRightCategory(initValue, function(node,e){
								Ext.getCmp("rightCode").setValue(node.text);
								Ext.getCmp("rightCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							})
						}
					}
				}, {
					fieldLabel:"图标",
					readOnly:true,
					id:"avatarName",
					name:"avatarName",
					listeners:{
						"focus":function(field){
							//选择权限根，弹出树选择框
							com.zz91.ep.project.AvatarSelector();
						}
					}
				}, {
					xtype:"hidden",
					name:"avatar",
					id:"avatar"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype : "hidden",
					name : "id",
					dataIndex : "id"
				}, {
					fieldLabel : "密码",
					allowBlank : false,
					itemCls :"required",
					name : "password",
					id : "password"
				},{
					fieldLabel : "版本",
					name : "version"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "链接地址",
					name : "url"
				}]
			}
			],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					var url=this.saveUrl;
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","信息已保存");
								Ext.getCmp(PROJECT.PROJECT_GRID).getStore().reload();
								Ext.getCmp(PROJECT.PROJECT_WIN).close();
								var cp = _action.result.data.split("|");
								Ext.get("initCode").dom.value=cp[0];
								Ext.get("initPassword").dom.value=cp[1];
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.submitFailure,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp(PROJECT.PROJECT_WIN).close();
				}
			}]
		};
		
		com.zz91.ep.project.Form.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+ "/sysproject/createSysProject.htm",
	loadOneRecord:function(id){
		var reader=[
		        	{name:"id",mapping:"id"},
		        	{name:"code",mapping:"code"},
		        	{name:"password",mapping:"password"},
		        	{name:"name",mapping:"name"},
		        	{name:"rightCode",mapping:"rightCode"},
		        	{name:"url",mapping:"url"},
		        	{name:"avatar",mapping:"avatar"},
		        	{name:"version",mapping:"version"},
		        	{name:"gmtCreated",mapping:"gmtCreated"},
		        	{name:"gmtModified",mapping:"gmtModified"},
		        ];
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields : reader,
			url : Context.ROOT+ "/sysproject/queryOneProject.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record != null) {
						form.getForm().loadRecord(record);
						Ext.getCmp("avatarName").setValue(record.get("avatar"));
					}
				}
			}
		});
	},
	initCodePassword:function(code, password){
		this.findById("code").setValue(code);
		this.findById("password").setValue(password);
	}
});

com.zz91.ep.project.addprojectWin = function(){
	var form = new com.zz91.ep.project.Form({
		id:PROJECT.PROJECT_FORM,
		saveUrl:Context.ROOT+ "/sysproject/createSysProject.htm",
		region:"center"
	});
	
	form.initCodePassword(Ext.get("initCode").dom.value,Ext.get("initPassword").dom.value);
	
	var win = new Ext.Window({
			id:PROJECT.PROJECT_WIN ,
			title:"添加项目信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.ep.project.editprojectWin = function(id){
	var form = new com.zz91.ep.project.Form({
		id:PROJECT.PROJECT_FORM,
		saveUrl:Context.ROOT+ "/sysproject/updateSysProject.htm",
		region:"center"
	});
	
	form.loadOneRecord(id);
	
	var win = new Ext.Window({
			id:PROJECT.PROJECT_WIN,
			title:"修改项目信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.ep.project.DeleteProject = function(rows){
	
	for (var i = 0, len = rows.length; i < len; i++) {
		Ext.Ajax.request({
	        url:Context.ROOT+"/sysproject/deleteSysProject.htm",
	        params:{
	            "id":rows[i].get("id")
	        },
	        success:function(response,opt){
	            var obj = Ext.decode(response.responseText);
	            if(obj.success){
	            	com.zz91.utils.Msg("","删除记录成功");
	                Ext.getCmp(PROJECT.PROJECT_GRID).getStore().reload();
	            }else{
	                Ext.MessageBox.show({
	                    title:MESSAGE.title,
	                    msg : MESSAGE.saveFailure,
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
}

com.zz91.ep.project.choiceRightCategory=function(init,callback){
	
	var tree = new com.zz91.ep.auth.right.Tree({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
}

com.zz91.ep.project.AvatarSelector=function(fn){
	
	var _myStore = new Ext.data.JsonStore({
	    url: Context.ROOT+"/sysproject/avatar.htm",
	    autoLoad:true,
	    fields: ["url","avatar"]
	});
	var _xtpl = new Ext.XTemplate('<tpl for=".">',
        '<div class="thumb-wrap">',
        '<div class="thumb"><img src="'+Context.ROOT+'{url}" title="{avatar}"></div>',
        '</div>',
	    '</tpl>',
	    '<div class="x-clear"></div>'
	);
	
	var win = new Ext.Window({
		id:"avatarselector" ,
		title:"选择图标",
		width:650,
		height:300,
		modal:true,
		bodyCssClass:"avatar-view",
		autoScroll:true,
		items:new Ext.DataView({
			height:300,
			store:_myStore,
			tpl:_xtpl,
			multiSelect:false,
			overClass:'x-view-over',
			itemSelector:'div.thumb-wrap',
			listeners:{
				"dblclick":function(dv,idx,node,e){
					Ext.getCmp("avatarName").setValue(_myStore.getAt(idx).get("url"));
					Ext.getCmp("avatar").setValue(_myStore.getAt(idx).get("url"));
					dv.ownerCt.close();
				}
			}
		})
	});
	win.show();
}