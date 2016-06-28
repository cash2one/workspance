Ext.namespace("com.zz91.zzmail.template");

var TEMPLATE = new function(){
	this.TEMPLATE_FORM="templateform";
	this.TEMPLATE_WIN = "templatewindows";
	this.TEMPLATE_GRID = "templategrid";
}

com.zz91.zzmail.template.Field=[
	{name:"id",mapping:"id"},
	{name:"code",mapping:"code"},
	{name:"name",mapping:"name"},
	{name:"tContent",mapping:"tContent"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.zzmail.template.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.zzmail.template.Field,
				url:Context.ROOT +  "/template/template/queryTemplates.htm",
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
			header :"模板名称",
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
			header : "模板内容",
			sortable : false,
			dataIndex : "tContent"
		},{
			header : "创建时间",
			sortable : false,
			dataIndex : "gmtCreated",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d h:i:s');
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
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d h:i:s');
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
		
		com.zz91.zzmail.template.Grid.superclass.constructor.call(this,c);
	},
	mytoolbar:[{
		text : '添加',
		tooltip : '添加',
		iconCls : 'add16',
		handler : function(btn){
			com.zz91.zzmail.template.addtemplateWin();
		}
	}, {
		text : '编辑',
		tooltip : '编辑',
		iconCls : 'edit16',
		handler : function(btn){
//			// 实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
			var row = Ext.getCmp(TEMPLATE.TEMPLATE_GRID).getSelectionModel().getSelected();
			if(row!=null){
				com.zz91.zzmail.template.edittemplateWin(row.get("id"));
			}
		}

	}, {
		text : '删除',
		tooltip : '删除记录',
		iconCls : 'delete16',
		handler : function(btn){
			var row = Ext.getCmp(TEMPLATE.TEMPLATE_GRID).getSelectionModel().getSelections();
			if (row.length > 0) {
				Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + row.length + '条记录?', function(_btn){
					if (_btn != "yes")
						return;
					com.zz91.zzmail.template.DeleteProject(row);
				});
			}
		}
	},"->",{
		xtype : "textfield",
		id : "search-name",
		emptyText:"搜索模板名称",
		listeners:{
			"change":function(field){
				var _store = Ext.getCmp(TEMPLATE.TEMPLATE_GRID).getStore();
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

com.zz91.zzmail.template.Form = Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel : "模板名称",
					allowBlank : false,
					itemCls :"required",
					name : "name"
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
					xtype : "hidden",
					name : "id",
					dataIndex : "id"
				},{
					xtype:"textarea",
					anchor:"99%",
					fieldLabel:"模板内容",
					name:"tContent",
					itemCls:"required",
					allowBlank:false
				}]
			}],
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
								Ext.getCmp(TEMPLATE.TEMPLATE_GRID).getStore().reload();
								Ext.getCmp(TEMPLATE.TEMPLATE_WIN).close();
								var cp = _action.result.data.split("|");
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
					Ext.getCmp(TEMPLATE.TEMPLATE_WIN).close();
				}
			}]
		};
		
		com.zz91.zzmail.template.Form.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+ "/template/template/createTemplate.htm",
	loadOneRecord:function(id){
		var reader=[
		        	{name:"id",mapping:"id"},
		        	{name:"code",mapping:"code"},
		        	{name:"name",mapping:"name"},
		        	{name:"tContent",mapping:"tContent"},
		        	{name:"gmtCreated",mapping:"gmtCreated"},
		        	{name:"gmtModified",mapping:"gmtModified"},
		        ];
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields : reader,
			url : Context.ROOT+ "/template/template/queryOneTemplate.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record != null) {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	},
});

com.zz91.zzmail.template.addtemplateWin = function(){
	var form = new com.zz91.zzmail.template.Form({
		id:TEMPLATE.TEMPLATE_FORM,
		saveUrl:Context.ROOT+ "/template/template/createTemplate.htm",
		region:"center"
	});
	
	
	var win = new Ext.Window({
			id:TEMPLATE.TEMPLATE_WIN ,
			title:"添加模板信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.zzmail.template.edittemplateWin = function(id){
	var form = new com.zz91.zzmail.template.Form({
		id:TEMPLATE.TEMPLATE_FORM,
		saveUrl:Context.ROOT+ "/template/template/updateTemplate.htm",
		region:"center"
	});
	
	form.loadOneRecord(id);
	
	var win = new Ext.Window({
			id:TEMPLATE.TEMPLATE_WIN,
			title:"修改模板信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.zzmail.template.DeleteProject = function(rows){
	
	for (var i = 0, len = rows.length; i < len; i++) {
		Ext.Ajax.request({
	        url:Context.ROOT+"/template/template/deleteTemplate.htm",
	        params:{
	            "id":rows[i].get("id")
	        },
	        success:function(response,opt){
	            var obj = Ext.decode(response.responseText);
	            if(obj.success){
	            	com.zz91.utils.Msg("","删除记录成功");
	                Ext.getCmp(TEMPLATE.TEMPLATE_GRID).getStore().reload();
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


