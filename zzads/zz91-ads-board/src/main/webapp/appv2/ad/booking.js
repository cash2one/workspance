Ext.namespace("com.zz91.ads.board.ad.booking");

Ext.define("BookingGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"position_id",mapping:"positionId"},
		{name:"crmId",mapping:"crmId"},
		{name:"account",mapping:"account"},
		{name:"keywords",mapping:"keywords"},
		{name:"email",mapping:"email"},
		{name:"gmt_booking",mapping:"gmtBooking"},
		{name:"remark",mapping:"remark"}
	]
});

Ext.define("com.zz91.ads.board.ad.booking.BaseGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"BookingGridModel",
			remoteSort:true,
			pageSize:Context.PAGE_SIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/ad/booking/query.htm",
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totals"
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
			header:"Email",
			dataIndex:"email"
		},{
			header:"关键词",
			dataIndex:"keywords"
		},{
			header:"预订时间",
			dataIndex:"gmt_booking",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					
					var now=new Date();
					if((now.getTime()-86400*2*1000)>value.time){
						return Ext.String.format("<img src='{0}/lib/extjs/themes/boomy/clock16.png' title='预订已过期'/>{1}",
							Context.IMG0,
							Ext.util.Format.date(new Date(value.time), 'Y-m-d')
						);
					}
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}else{
					return "";
				}
			}
		},{
			header:"备注",
			dataIndex:"remark"
		},{
			header:"操作账户",
			dataIndex:"account"
		},{
			header:"CRM_KEY",
			dataIndex:"crmId"
		}]
		
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
			tbar:[{
				iconCls:"add16",
				text:"预订广告",
				handler:function(btn){
					
					//检测是否选中了广告位
					var tree=Ext.getCmp("positiontree");
					var record=tree.getSelectionModel().getLastSelected();
					if(record==null || record.get("id")<=0){
						Ext.Msg.alert(MESSAGE.title, "请先从左边选择要预订的广告位！");
						return false;
					}
					
					//检测可否预订
					var form = Ext.create("com.zz91.ads.board.ad.booking.MainForm",{
						region:"center"
					});
					
					form.initForm(record.get("id"), this.up("grid").getCrmId());
					
					var win=Ext.create("Ext.window.Window",{
						layout:"border",
						title:"新增预订",
						autoHeight:true,
						width:450,
						modal:true,
						items:[form]
					});
					
					win.show();
					
				}
			},{
				iconCls:"delete16",
				text:"删除预订",
				scope:this,
				handler:function(btn){
					var _this=this;
					var selections=_this.getSelectionModel().getSelection();
					Ext.Msg.confirm(MESSAGE.title,"预订删除后将无法恢复，您确定要删除这些预订信息吗？",function(o){
						if(o!="yes"){
							return ;
						}
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/booking/deleteBooking.htm",
								params:{"id":obj.get("id")},
								success:function(response,opt){
									_this.getStore().load();
								},
								failure:function(response,opt){
								}
							});
						});
					});
				}
			},"->",{
				xtype:"checkbox",
				boxLabel:"包含过期",
				checked:true,
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("gmtBooking", null);
					}else{
						this.up("grid").getStore().setExtraParam("gmtBooking", Ext.Date.format(Ext.Date.add(new Date(), Ext.Date.DAY, -2),"Y-m-d H:i:s"));
					}
					this.up("grid").getStore().load();
				}
			}]
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	crmId:0,
	config:{
		crmId:null
	}
});

Ext.define("com.zz91.ads.board.ad.booking.MainForm",{
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
				xtype:"hidden",
				name:"positionId"
			},{
				xtype:"hidden",
				name:"crmId"
			},{
				name:"keywords",
				fieldLabel:"预订关键字"
			},{
				name:"email",
				fieldLabel:"Email"
			},{
				xtype:"datefield",
				name:"gmtBooking",
				format:"Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				fieldLabel:"预订日期",
				formItemCls:"x-form-item required",
				allowBlank : false,
				value:new Date()
			},{
				xtype:"textarea",
				name:"remark",
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
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:Context.ROOT+"/ad/booking/createBooking.htm",
				success: function(f, action) {
					Ext.getCmp("bookinggrid").getStore().load();
					form.up("window").close();
				},
				failure: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "对不起，广告位已满，暂不接受预订！");
				}
			});
		}
	},
	initForm:function(pid,crmId){
		this.getForm().findField("positionId").setValue(pid);
		this.getForm().findField("keywords").setValue(Ext.getCmp("searchKeywords").getValue());
		if(crmId==""){
			crmId=0;
		}
		this.getForm().findField("crmId").setValue(crmId);
	}
});
