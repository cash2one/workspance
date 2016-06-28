Ext.namespace("com.zz91.ads.board.ad.renew");
Ext.define("AdRenewFormModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"ad_id",mapping:"adId"},
		{name:"gmt_start",mapping:"gmtStart"},
		{name:"gmt_end",mapping:"gmtEnd"},
		{name:"applicant",mapping:"applicant"},
		{name:"gmt_apply",mapping:"gmtApply"},
		{name:"dealer",mapping:"dealer"},
		{name:"gmt_deal",mapping:"gmtDeal"},
		{name:"gmt_created",mapping:"gmtCreated"},
		{name:"gmt_modified",mapping:"gmtModified"},
		{name:"detail",mapping:"detail"}
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/ad/booking/queryRenewById.htm"
		}
	}
});

Ext.define("RenewGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"ad_id",mapping:"adId"},
		{name:"gmt_start",mapping:"gmtStart"},
		{name:"gmt_end",mapping:"gmtEnd"},
		{name:"applicant",mapping:"applicant"},
		{name:"gmt_apply",mapping:"gmtApply"},
		{name:"dealer",mapping:"dealer"},
		{name:"gmt_deal",mapping:"gmtDeal"},
		{name:"gmt_created",mapping:"gmtCreated"},
		{name:"gmt_modified",mapping:"gmtModified"},
		{name:"detail",mapping:"detail"}
	]
});

Ext.define("com.zz91.ads.board.ad.renew.BaseGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"RenewGridModel",
			remoteSort:true,
			pageSize:Context.PAGE_SIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/ad/booking/queryRenewByAdId.htm",
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
			text:"编号",
			dataIndex:"id",
			width:50,
			hidden:true
		},{
			header:"开始时间",
			dataIndex:"gmt_start",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}else{
					return "";
				}
			}
		},{
			header:"结束时间",
			dataIndex:"gmt_end",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}else{
					return "";
				}
			}
		},{
			header:"申请人",
			dataIndex:"applicant"
		},{
			header:"申请时间",
			dataIndex:"gmt_apply",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}else{
					return "";
				}
			}
		},{
			header:"操作人",
			dataIndex:"dealer"
		}
		,{
			header:"操作时间",
			dataIndex:"gmt_deal",
			width:150,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}else{
					return "";
				}
			}
		}
		]
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			}],
			tbar:[
			      {
				iconCls:"add16",
				text:"续费",
				handler:function(btn){
					var tree=Ext.getCmp("mainadgrid");
					var record=tree.getSelectionModel().getLastSelected();
					if(record==null || record.get("id")<=0){
						Ext.Msg.alert(MESSAGE.title, "请选择上方您要续费的广告！");
						return false;
					}
					var form = Ext.create("com.zz91.ads.board.ad.renew.MainForm",{
						region:"center"
					});
					var win=Ext.create("Ext.window.Window",{
						layout:"border",
						title:"续订广告",
						autoHeight:true,
						width:450,
						modal:true,
						items:[form]
					});
					
					win.show();
					Ext.getCmp("gmtStart").setValue(new Date(record.get("a.gmt_plan_end").time));
					
				}
			},{
				iconCls:"edit16",
				text:"修改续费信息",
				handler:function(btn){
					var tree=Ext.getCmp("renewinggrid");
					var record=tree.getSelectionModel().getLastSelected();
					if(record==null || record.get("id")<=0){
						Ext.Msg.alert(MESSAGE.title, "请选择上方您要续费的广告！");
						return false;
					}
					var form = Ext.create("com.zz91.ads.board.ad.renew.MainForm",{
						region:"center"
					});
					var win=Ext.create("Ext.window.Window",{
						layout:"border",
						title:"续订广告",
						autoHeight:true,
						width:450,
						modal:true,
						items:[form]
					});
					
					win.show();
					form.loadModel(record.get("id"));
					
				}
			},"->",{
				iconCls:"configure16",
				text:"续费成功",
				handler:function(btn){
					var tree=Ext.getCmp("renewinggrid");
					var record=tree.getSelectionModel().getLastSelected();
					if(record==null || record.get("id")<=0){
						Ext.Msg.alert(MESSAGE.title, "请选择续费详情！");
						return false;
					}else{
						Ext.Ajax.request({
							url:Context.ROOT+"/ad/booking/updateDeal.htm",
							params:{"id":record.get("id")},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								Ext.getCmp("renewinggrid").getStore().load();
								Ext.Msg.alert(MESSAGE.title, obj.data);
							},
							failure:function(response,opt){
								Ext.Msg.alert(MESSAGE.title, obj.data);
							}
						});
					}
				}
			}
			]
		}
		Ext.applyIf(this,c);
		this.callParent();
	},
	loadByAdId:function(adId){
		this.getStore().setExtraParam("adId",adId);
		this.getStore().load();
	}
});

Ext.define("com.zz91.ads.board.ad.renew.MainForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		
		var c={
			bodyPadding: 5,
			fieldDefaults: {
				labelAlign: 'right',
				labelWidth: 60,
				labelSeparator:""
			},
			layout:"anchor",
			defaults:{
				anchor:'100%',
				xtype : 'textfield'
			},
			items:[{
				name:"id",
				hidden:"true"
			},{
				xtype:"datefield",
				name:"gmtStart",
				id:"gmtStart",
				format:"Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				formItemCls:"x-form-item required",
				fieldLabel:"开始时间"
			},{
				xtype:"datefield",
				name:"gmtEnd",
				id:"gmtEnd",
				format:"Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				formItemCls:"x-form-item required",
				fieldLabel:"结束时间"
			},{
				xtype:"textarea",
				name:"detail",
				fieldLabel:"备注"
			}],
			buttons:[{
				iconCls:"saveas16",
				text:"保存",
				handler:this.saveModel
			},{
				iconCls:"close16",
				text:"关闭",
				handler:function(){
					this.up("window").close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	saveModel:function(){
		var form=this.up("form");
		var tree=Ext.getCmp("mainadgrid");
		var record=tree.getSelectionModel().getLastSelected();
		var adId = record.get("id");
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:Context.ROOT+"/ad/booking/createRenew.htm?adId="+adId,
				success: function(f, action) {
					Ext.getCmp("renewinggrid").getStore().load();
					form.up("window").close();
				},
				failure: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "对不起，广告位已满，暂不接受预订！");
				}
			});
		}
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('AdRenewFormModel').load(id, {
			success: function(model) {
				_this.loadRecord(model);
				Ext.getCmp("gmtStart").setValue(new Date(model.data.gmt_start.time));
				Ext.getCmp("gmtEnd").setValue(new Date(model.data.gmt_end.time));
			}
		});
	}
});
