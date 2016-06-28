Ext.ns("com.zz91.ep.subnetLink");

Ext.define("CategoryGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"parentId",mapping:"parentId"},
		{name:"name",mapping:"name"},
		{name:"link",mapping:"link"},
		{name:"sort",mapping:"sort"},
		{name:"gmtCreated",mapping:"gmtCreated"},
		{name:"gmtModified",mapping:"gmtModified"}
	]
});

Ext.define("com.zz91.ep.subnetLink.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CategoryGridModel",
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/trade/subnetlink/queryAllParent.htm",
				simpleSortMode:true,
				reader: {
		            type: 'json'
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
		    	text:"编号",dataIndex:"id",width:60,hidden:true
			},{
		    	text:"父类编号",dataIndex:"parentId",hidden:true
			},{
				text:"名称",dataIndex:"name",width:70
			},{
				text:"链接网站",dataIndex:"link"
			},{
				text:"排序",dataIndex:"sort",hidden:true
			},{
				text:"最后修改时间",dataIndex:"gmtModified",
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}];
		
		var grid=this;
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype: 'toolbar',
				dock:"top",
				items:[{
					xtype:"button",
					iconCls:"add16",
					text:"添加",
					scope:this,
					handler:function(btn,e){
					com.zz91.ep.subnetLink.addWin(null);
					}
				},{
					xtype:"button",
					iconCls:"edit16",
					text:"修改",
					scope:this,
					handler:function(btn,e){
					com.zz91.ep.subnetLink.editWin(grid.getSelectionModel().getLastSelected());
					}
				}]
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	}
});

Ext.define("com.zz91.ep.subnetLink.Form",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		
		var c={
			bodyPadding: 5,
			fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 60,
		        labelSeparator:""
		    },
		    autoScroll:true,
		    layout:"anchor",
		    defaults:{
		    	anchor:"100%",
		    	xtype: "textfield",
		    },
		    items:[{
				name:"id",
				fieldLabel:"编号",
				hidden:true
			},{
				name:"parentId",
				fieldLabel:"父类ID",
				readOnly:true
			},{
				name:"name",
				fieldLabel:"链接名称",
				formItemCls:"x-form-item required",
				allowBlank : false
			},{
				name:"link",
				fieldLabel:"链接网址",
				formItemCls:"x-form-item required",
				allowBlank : false,
				value:"http://www."
			},{
				name:"sort",
				fieldLabel:"排序",
				value:"0"
			}],
			buttons:[{
				text:"保存",
				iconCls:"saveas16",
				handler:function(){
					var form=this.up("form");
					var url="";
					if (form.getForm().findField("id").getValue()>0){
						url=Context.ROOT+"/trade/subnetlink/updateSubnetLink.htm";
					}else{
						url=Context.ROOT+"/trade/subnetlink/addSubnetLink.htm";
					}
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:url,
							success: function(f, action) {
								form.up("window").close();
								Ext.getCmp("mainGrid").getStore().load();
							},
							failure: function(f, action) {
								Ext.Msg.alert(MESSAGE.title, "发生错误，信息没有更新！");
							}
						});
					}
				}
			},{
				text:"关闭",
				iconCls:"close16",
				handler:function(){
					this.up("window").close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	initParentId:function(parentId){
		this.getForm().findField("parentId").setValue(parentId);
	},
	loadForm:function(record){
		this.getForm().loadRecord(record);
	}
});

com.zz91.ep.subnetLink.addWin=function(record){
	var form=Ext.create("com.zz91.ep.subnetLink.Form",{
		region:"center"
	});
	
	var win=Ext.create("Ext.window.Window",{
		title:"添加新类别",
		layout:"border",
		width : 600,
		autoHeight:true,
		modal : true,
		items:[form]
	});
	
	if (record!=null){
		form.initParentId(record.get("id"));
	}
	win.show();
};

com.zz91.ep.subnetLink.editWin=function(record){
	var form=Ext.create("com.zz91.ep.subnetLink.Form",{
		region:"center"
	});
	
	var win=Ext.create("Ext.window.Window",{
		title:"添加新类别",
		layout:"border",
		width : 600,
		autoHeight:true,
		modal : true,
		items:[form]
	});
	win.show();
	
	form.loadForm(record);
};


Ext.define("com.zz91.ep.subnetLink.ChildGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CategoryGridModel",
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/trade/subnetlink/queryLinkByParentId.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totals"
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:false
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
		    	text:"编号",dataIndex:"id",width:60
			},{
		    	text:"父类编号",dataIndex:"parentId",hidden:true
			},{
				header:"链接名称",dataIndex:"name"
			},{
				text:"链接网址",dataIndex:"link"
			},{
				text:"排序",dataIndex:"sort"
			},{
				text:"创建时间",dataIndex:"gmtCreated",
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			},{
				text:"最后修改时间",dataIndex:"gmtModified",
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			},{
				xtype: 'toolbar',
				dock:"top",
				items:[{
					xtype:"button",
					iconCls:"add16",
					text:"添加",
					scope:this,
					handler:function(btn,e){
						var select=Ext.getCmp("mainGrid").getSelectionModel().getSelection();
						if (select.length>0){
							com.zz91.ep.subnetLink.addWin(select[0]);
						}
					}
				},{
					xtype:"button",
					iconCls:"edit16",
					text:"修改",
					scope:this,
					handler:function(btn,e){
						var select=Ext.getCmp("childGrid").getSelectionModel().getSelection();
						if (select.length>0){
						com.zz91.ep.subnetLink.editWin(this.getSelectionModel().getLastSelected());
						}
					}
				},{
					xtype:"button",
					iconCls:"delete16",
					text:"删除",
					scope:this,
					handler:function(btn,e){
						this.deleteModel(this.getSelectionModel().getSelection());
					}
				}]
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	deleteModel:function(selections){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Msg.confirm(Context.MSG_TITLE,"您确定要删除选中记录？",function(o){
			if(o!="yes"){
				return ;
			}
			
			Ext.Array.each(selections, function(obj, idx, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/trade/subnetlink/deleteChildLink.htm",
					params: {
						id: obj.get("id")
					},
					success: function(response){
						Ext.Msg.alert("提示","删除成功!");
						_this.getStore().load({
							params:{parentId:obj.get("parentId")}
						});
					}
				});
			});
			
		});
	}
});