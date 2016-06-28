Ext.ns("ast.ast1949.admin.exhibit");

Ext.define("ExhibitGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"exhibitDO.id"},
		{name:"name",mapping:"exhibitDO.name"},
		{name:"photo_cover",mapping:"exhibitDO.photoCover"},
		{name:"area_code",mapping:"exhibitDO.areaCode"},
		{name:"plate_category_code",mapping:"exhibitDO.plateCategoryCode"},
		{name:"exhibit_category_code",mapping:"exhibitDO.exhibitCategoryCode"},
		{name:"start_time",mapping:"exhibitDO.startTime"},
		{name:"end_time",mapping:"exhibitDO.endTime"},
		{name:"gmt_created",mapping:"exhibitDO.gmtCreated"},
		{name:"exhibitCategoryName",mapping:"exhibitCategoryName"},
		{name:"plateCategoryName",mapping:"plateCategoryName"},
		{name:"areaName",mapping:"areaName"}
	]
});

Ext.define("ExhibitFormdModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"exhibitDO.id"},
		{name:"name",mapping:"exhibitDO.name"},
		{name:"photoCover",mapping:"exhibitDO.photoCover"},
		{name:"areaCode",mapping:"exhibitDO.areaCode"},
		{name:"plateCategoryCode",mapping:"exhibitDO.plateCategoryCode"},
		{name:"exhibitCategoryCode",mapping:"exhibitDO.exhibitCategoryCode"},
		{name:"startTime",mapping:"exhibitDO.startTime"},
		{name:"endTime",mapping:"exhibitDO.endTime"},
		{name:"gmtCreated",mapping:"exhibitDO.gmtCreated"},
		{name:"exhibitCategoryName",mapping:"exhibitCategoryName"},
		{name:"plateCategoryName",mapping:"plateCategoryName"},
		{name:"areaName",mapping:"areaName"}
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/admin/exhibit/queryOneExhibit.htm"
		}
	}
});

Ext.define("ast.ast1949.admin.exhibit.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"ExhibitGridModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/zz91/admin/exhibit/query.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[
			{text:"编号",dataIndex:"id",width:30,hidden:true},
			{header:"展会名称",dataIndex:"name",width:250,renderer:function(v,m,record,ridx,cidx,store,view){
				if(record.get("photo_cover").length>0){
					return Ext.String.format("<img src='{0}' width='80' height='50' /><br />{1}",Context.RESOURCES+record.get("photo_cover"),v);
				}
				return v;
			}},
			{text:"地区",dataIndex:"areaName",sortable:false},
			{text:"页面模块",dataIndex:"plateCategoryName",sortable:false},
			{text:"行业类别",dataIndex:"exhibitCategoryName",sortable:false},
			{text:"开始时间",dataIndex:"start_time", renderer:function(v){
				if(v!=null){
					return Ext.Date.format(new Date(v.time),"Y-m-d");
				}
				return "";
			}},
			{text:"结束时间",dataIndex:"end_time", renderer:function(v){
				if(v!=null){
					return Ext.Date.format(new Date(v.time),"Y-m-d");
				}
				return "";
			}},
			{text:"创建时间",dataIndex:"gmt_created", renderer:function(v){
				if(v!=null){
					return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
				}
				return "";
			}}
		];
		
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
					text:"添加展会",
					handler:this.createModel
				},{
					xtype:"button",
					iconCls:"edit16",
					text:"修改",
					scope:this,
					handler:this.editModel
				},{
					xtype:"button",
					iconCls:"delete16",
					text:"删除展会",
					scope:this,
					handler:function(btn,e){
						this.deleteModel(this.getSelectionModel().getSelection());
					}
				},"->",{
					xtype:"textfield",
					emptyText:"输入展会标题搜索展会",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("name",nv);
							this.up("grid").getStore().load();
						}
					}
				},Ext.create("ast.ast1949.util.CategoryCombo",{
					rootCode:"1037",
					emptyText:"选择板块类别",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("plateCategoryCode",nv);
							this.up("grid").getStore().load();
						}
					}
				}),Ext.create("ast.ast1949.util.CategoryCombo",{
					rootCode:"1038",
					emptyText:"选择行业类别",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("exhibitCategoryCode",nv);
							this.up("grid").getStore().load();
						}
					}
				})]
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
		Ext.Msg.confirm(Context.MSG_TITLE,"展会信息删除后将无法恢复，您确定要删除这些展会信息吗？",function(o){
			if(o!="yes"){
				return ;
			}
			Ext.Array.each(selections, function(obj, index, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/zz91/admin/exhibit/delete.htm",
					params: {
						id: obj.getId()
					},
					success: function(response){
						_this.getStore().load();
//						var text = response.responseText;
					}
				});
			});
		});
//		for(var i=0;i<selections.length;i++){
//			alert(selections[i].getId())
//		}
	},
	createModel:function(btn,e){
		window.open(Context.ROOT+"/zz91/admin/exhibit/details.htm")
	},
	editModel:function(btn,e){
		var rowModel=this.getSelectionModel().getLastSelected();
		window.open(Context.ROOT+"/zz91/admin/exhibit/details.htm?id="+rowModel.getId())
	}
});

Ext.define("ast.ast1949.admin.exhibit.MainForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			bodyPadding: 5,
			fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 60,
//		        msgTarget:"under",
		        labelSeparator:""
		    },
		    layout:"anchor",
			items:[{
				xtype:"hidden",
				id:"id",
				name:"id"
			},{
				xtype:"textfield",
				name:"name",
				anchor:"100%",
				allowBlank:false,
				formItemCls:"x-form-item required",
				fieldLabel:"展会名称"
			},{
				xtype: 'container',
				anchor:"100%",
				layout:"column",
				border:0,
				items:[{
					xtype: 'container',
					columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
				    items:[{
				    	xtype:"datefield",
				    	format:"Y-m-d H:i:s",
						fieldLabel : '开始时间',
						name : 'startTime',
						id : 'startTime',
						allowBlank:false,
						formItemCls:"x-form-item required"
					},Ext.create("ast.ast1949.util.CategoryCombo",{
						fieldLabel:"模块类别",
						rootCode:"1037",
						name:"plateCategoryCode",
//						hiddenName:"plateCategoryCode",
						allowBlank:false,
						formItemCls:"x-form-item required"
					}),
//					{
//						xtype:"combo",
//						fieldLabel : '模块类别',
//						name : 'plateCategoryCode',
//						allowBlank:false,
//						formItemCls:"x-form-item required"
//					},
					{
						xtype:"hidden",
						id:"areaCode",
						name:"areaCode"
					},{
						fieldLabel : '所在地区',
						name : 'areaName',
						id : 'areaName',
						allowBlank:false,
						formItemCls:"x-form-item required",
						listeners:{
							"focus":function(field,e){
								var initCode=Ext.getCmp("areaCode").getValue();
								var win=Ext.create("ast.ast1949.util.TreeSelectorWin",{
									height:500,
									width:300,
									modal:true,
									rootCode:"1001",
									initCode:initCode,
									callbackFn:function(nodeInterface){
										Ext.getCmp("areaCode").setValue(nodeInterface.data.id);
										Ext.getCmp("areaName").setValue(nodeInterface.data.text);
										this.close();
									}
								});
								win.show();
								win.initTree(4);
							}
						}
					}
					]
			    },{
			    	xtype: 'container',
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
					items:[{
						xtype:"datefield",
						format:"Y-m-d H:i:s",
						fieldLabel : '结束时间',
						name : 'endTime',
						id : 'endTime',
						allowBlank:false,
						formItemCls:"x-form-item required"
					},Ext.create("ast.ast1949.util.CategoryCombo",{
						fieldLabel : '行业类别',
						rootCode:"1038",
						name:"exhibitCategoryCode",
//						hiddenName:"exhibitCategoryCode",
						allowBlank:true,
						//formItemCls:"x-form-item required"
					}),{
						fieldLabel : '图片',
						name:"photoCover",
						listeners:{
							"focus":function(field,e){
								var win=Ext.create("ast.ast1949.util.UploadWin",{
									uploadUrl:Context.ROOT+"/zz91/common/doUpload.htm",
									callbackFn:function(form,action){
										field.setValue(action.result.data);
										win.close();
									}
								});
								win.show();
							}
						}
					}]
				}]
			}],
			buttons:[{
				scale:"large",
				xtype:"button",
				text:"马上保存",
				iconCls:"saveas32",
				handler:this.saveModel
			},{
				scale:"large",
				xtype:"button",
				text:"关闭",
				iconCls:"close32",
				handler:function(){
					window.close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('ExhibitFormdModel').load(id, { // load user with ID of "1"
			success: function(model) {
				_this.loadRecord(model);
				Ext.getCmp("startTime").setValue(Ext.Date.format(new Date(model.data.startTime.time),"Y-m-d"));
				Ext.getCmp("endTime").setValue(Ext.Date.format(new Date(model.data.endTime.time),"Y-m-d"));
			}
		});
	},
	saveModel:function(btn,e){
	}
});
