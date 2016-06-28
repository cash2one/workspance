Ext.ns("ast.ast1949.admin.productsIndex");

var DATAINDEX={
	TREE:"dataindextree",
	GRID:"dataindexgrid"
}

Ext.define("ProductsIndexModel",{
	extend:"Ext.data.Model",
	fields:[
		"id","dataIndexCode","productsId","companyId","productsType",
		"title","minPrice","maxPrice","priceUnit","quantity","quantityUnit",
		"tags","tagsAdmin","refreshTime","realTime","pic","orderby",{name:"gmt_created",mapping:"gmtCreated"}
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/admin/productsindex/queryOne.htm"
		}
	}
});

Ext.define("ast.ast1949.admin.productsIndex.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"ProductsIndexModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/zz91/admin/productsindex/queryIndex.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var productType={"10331000":"供应","10331001":"求购","10331002":"合作"};
		
		var _cm=[
			{text:"编号",dataIndex:"id",width:50,hidden:true},
			{text:"code",dataIndex:"dataIndexCode",width:150},
			{text:"排序",dataIndex:"orderby", editor: {
                xtype: 'numberfield',
                decimalPrecision:9,
                allowBlank: false
            }},
			{header:"标题",dataIndex:"title",width:250,renderer:function(v,m,record,ridx,cidx,store,view){
				return Ext.String.format("[{0}] <a href='"+Context.ROOT+"/zz91/admin/products/edit.htm?productid={2}&companyid={3}&account={4}' target='_blank'>查看</a> {1}",
					productType[record.get("productsType")],v,
					record.get("productsId"),
					record.get("companyId"),
					record.get("account")
					);
			}, editor: {
                xtype: 'textfield',
                allowBlank: false
            }},
			{text:"图片",dataIndex:"pic",renderer:function(v,m,record,ridx,cidx,store,view){
				if(v==null || v==""){
					return "无图片";
				}
				return Ext.String.format("<a href='http://img1.zz91.com/{0}' target='_blank'><img src='http://img1.zz91.com/{0}' /></a>", v)
			}},
			{text:"数量",dataIndex:"quantity",renderer:function(v,m,record,ridx,cidx,store,view){
				return Ext.String.format("{0} {1}", v,record.get("quantityUnit"));
			}},
			{text:"报价",dataIndex:"minPrice",width:150,renderer:function(v,m,record,ridx,cidx,store,view){
				var maxp="";
				if(record.get("maxPrice")!=null && record.get("maxPrice")!=""){
					maxp="-"+record.get("maxPrice");
				}
				return Ext.String.format("{0}{1} {2}/{3}", v,maxp,record.get("priceUnit"),record.get("quantityUnit"));
			}},
			{text:"创建时间",dataIndex:"gmt_created",width:150,renderer:function(v,m,record,ridx,cidx,store,view){
				if(v!=null){
					return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
				}
				return "";
			}}
		];
		
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	        clicksToMoveEditor: 1,
	        autoCancel: false
	    });
		
		var c={
			plugins:[rowEditing],
			store:_store,
			columns:_cm,
			selModel:_sm,
			tbar:[
//			{
//				xtype:"button",
//				iconCls:"add16",
//				text:"添加",
//				handler:this.createModel
//			},
//			{
//				xtype:"button",
//				iconCls:"edit16",
//				text:"修改",
//				scope:this,
//				handler:this.editModel
//			},
			{
				xtype:"button",
				iconCls:"delete16",
				text:"删除",
				scope:this,
				handler:function(btn,e){
					this.deleteModel(this.getSelectionModel().getSelection());
				}
			}],
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			}],
			listeners:{
				"edit":function(edit,e){
					var record=edit.record;
					var param=recrod.data;
					param["realTime"]=Ext.Date.format(new Date(record.data["refreshTime"].time),"Y-m-d H:i:s");
					param["refreshTime"]=Ext.Date.format(new Date(record.data["refreshTime"].time),"Y-m-d H:i:s");
					
					
					Ext.Ajax.request({
						url:Context.ROOT+"/zz91/admin/productsindex/doUpdate.htm",
						params:param,
						success:function(response,opt){
						},
						failure:function(response,opt){
						}
						
					});
				}
			}
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	deleteModel:function(selections){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Msg.confirm(Context.MSG_TITLE,"信息删除后将无法恢复，您确定要删除这些信息吗？",function(o){
			if(o!="yes"){
				return ;
			}
			Ext.Array.each(selections, function(obj, index, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/zz91/admin/productsindex/delete.htm",
					params: {
						id: obj.getId()
					},
					success: function(response){
						_this.getStore().load();
					}
				});
			});
		});
	},
	editModel:function(btn,e){
//		var rowModel=this.getSelectionModel().getLastSelected();
//		
//		var form=Ext.create("ast.ast1949.admin.dataIndex.MainForm",{
//			saveUrl:Context.ROOT+"/zz91/admin/dataindex/update.htm",
//			region:"center"
//		});
//		
//		var win= Ext.create("Ext.Window",{
//			layout : 'border',
//			iconCls : "edit16",
//			width : 450,
//			autoHeight:true,
//			title : "修改信息",
//			modal : true,
//			items : [ form ]
//		});
//
//		win.show();
//		
//		form.loadModel(rowModel.getId());
	}
});


Ext.define("ast.ast1949.admin.productsIndex.MainForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			border: 0,
			bodyPadding: 5,
			fieldDefaults: {
				labelAlign: 'right',
				labelWidth: 80
			},
			layout:{
				type:"vbox",
				align:"stretch"
			},
			defaults:{
				anchor:'99%',
				xtype : 'textfield'
			},
		    items : [ {
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				xtype:"hidden",
				name:"categoryCode",
				id:"categoryCode"
			},{
				fieldLabel : '选择类别',
				name : 'categoryName',
				id : 'categoryName',
				formItemCls:"x-form-item required",
				listeners:{
					"focus":function(field,e){
						var initCode=Ext.getCmp("categoryCode").getValue();
						var win=Ext.create("ast.ast1949.util.TreeSelectorWin",{
							height:500,
							width:300,
							modal:true,
							initCode:initCode,
							queryUrl:Context.ROOT+"/zz91/admin/dataindex/categoryChild.htm",
							callbackFn:function(nodeInterface){
								Ext.getCmp("categoryCode").setValue(nodeInterface.data.id);
								Ext.getCmp("categoryName").setValue(nodeInterface.data.text);
								this.close();
							}
						});
						win.show();
						win.initTree(4);
					}
				}
			}, {
				xtype:"textarea",
				fieldLabel : '标题名称',
				name : 'title',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				fieldLabel : '连接地址',
				name : 'link',
				id : 'link',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				fieldLabel : '标题样式',
				name : 'style',
				id : 'style',
				allowBlank : false,
				formItemCls:"x-form-item required"
			}, {
				xtype:"numberfield",
				fieldLabel : '排序(大>小)',
				name : 'sort'
			}, {
				fieldLabel : '图片',
				name : 'pic',
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
			} ],
			buttons:[{
				xtype:"button",
				text:"马上保存",
				iconCls:"saveas16",
				handler:this.saveModel
			},{
				iconCls:"close16",
				text:"关闭",
				handler:function(btn,e){
					this.up("window").close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('DataIndexFormModel').load(id, { // load user with ID of "1"
			success: function(model) {
				_this.loadRecord(model);
			}
		});
	},
	saveModel:function(btn,e){
		var form=this.up("form");
		var _url=form.getSaveUrl();
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					form.up("window").close();
					Ext.getCmp(DATAINDEX.GRID).getStore().load();
				},
				failure: function(f, action) {
					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，信息没有更新！");
				}
			});
		}
	},
	initField:function(){
		this.child("#style").setValue("color:black;font-size:12px");
		this.child("#link").setValue("#");
	},
	saveUrl:Context.ROOT+"/zz91/admin/dataindex/add.htm",
	config:{
		saveUrl:null
	}
});
