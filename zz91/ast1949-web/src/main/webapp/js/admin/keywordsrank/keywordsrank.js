Ext.namespace("ast.ast1949.admin.keywordsrank");

var KEYWORDSRANK = new function(){
	this.GRID = "keywordsrankgrid";
}

ast.ast1949.admin.keywordsrank.SearchForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 80,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype : "hidden",
				id : "id",
				name : "id"
			}, {
				xtype : "hidden",
				id : "productId",
				name : "productId"
			},{
				fieldLabel : "搜索关键字",
				allowBlank : false,
				itemCls:"required",
				id:"name",
				name : "name"
			},{
				xtype:"checkbox",
				id:"isChecked",
				name:"isChecked",
				fieldLabel:"审核？",
				inputValue:1
			},{
				xtype:"datefield",
				id:"startTime",
				name:"startTime",
				format: "Y-m-d",
				allowBlank : false,
				itemCls:"required",
				fieldLabel:"开始时间",
				value:new Date()
			},{
				xtype:"datefield",
				id:"endTime",
				name:"endTime",
				format: "Y-m-d",
				allowBlank : false,
				itemCls:"required",
				fieldLabel:"结束时间"
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					this.saveForm();
				}
			}]
		};

		ast.ast1949.admin.keywordsrank.SearchForm.superclass.constructor.call(this,c);
	},
	initForm:function(obj){
		this.findById("productId").setValue(obj.productId);
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/keywordsrank/addKeywordsRank.htm",
	saveForm:function(){
		var _url = this.saveUrl;
		this.getForm().submit({
			url:_url,
			method:"post",
			success:function(){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "您已给供求信息指定了关键字!",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.INFO
				});
				Ext.getCmp(KEYWORDSRANK.GRID).getStore().reload();
			},
			failure:function(){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "发生错误,关键字没有被添加!",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			}
		});
	}
});

ast.ast1949.admin.keywordsrank.KeywordsGrid = Ext.extend(Ext.grid.EditorGridPanel,{
	queryUrl: Context.ROOT + Context.PATH+ "/admin/keywordsrank/selectKeywordsRankByProductId.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var _fields = this.fieldsRecord;
		var _url = this.queryUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
//		var grid = this;

		var _sm=new Ext.grid.CheckboxSelectionModel({
//			listeners: {
//	            // On selection change, set enabled state of the removeButton
//	            // which was placed into the GridPanel using the ref config
//	            selectionchange: function(sm) {
//	            	if (sm.getCount()) {
//	            		grid.editButton.enable();
//	            		grid.deleteButton.enable();
//	            	} else {
//	                    grid.editButton.disable();
//	                    grid.deleteButton.disable();
//	                }
//	            }
//	        }
		});

		var _cm =  new Ext.grid.ColumnModel([_sm,{
			header : "搜索关键字",
			sortable : false,
			dataIndex : "name"
		},{
			header : "开始时间",
			width:130,
			sortable : false,
			dataIndex : "startTime",
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
				return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
			}
		},{
			header : "结束时间",
			width:130,
			sortable : false,
			dataIndex : "endTime",
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
				return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
			}
		},{
			header : "审核",
			width : 50,
			sortable : false,
			dataIndex : "isChecked",
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
				if(value==1){
					return "审核";
				}else{
					return "未审核"
				}
			}
//			,editor:new Ext.form.Checkbox({
//				inputValue:1
//			})
		}]);

		var c={
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			frame:true,
			tbar:[{
				text:"删除",
				handler:function(){
				}
			}]
		};

		ast.ast1949.admin.keywordsrank.KeywordsGrid.superclass.constructor.call(this,c);
	},
	fieldsRecord:["id","productId","name","startTime","endTime","isChecked"],
	loadRecordByProductsId:function(id){
		//TODO 这里载入希望的数据
		var B=this.getStore().baseParams;
		B["id"] = id;
		this.getStore().reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	}
});