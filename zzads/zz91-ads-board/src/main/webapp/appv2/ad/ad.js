Ext.namespace("com.zz91.ads.board.ad.ad");

/**
 * ad model
 * */
Ext.define("AdFormModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"positionId",mapping:"positionId"},
		{name:"adTitle",mapping:"adTitle"},
		{name:"advertiserId",mapping:"advertiserId"},
		{name:"advertiser",mapping:"advertiserName"},
		{name:"adDescription",mapping:"adDescription"},
		{name:"adContent",mapping:"adContent"},
		{name:"adTargetUrl",mapping:"adTargetUrl"},
		{name:"gmtStart",mapping:"gmtStart"},
		{name:"gmtPlanEnd",mapping:"gmtPlanEnd"},
		{name:"remark",mapping:"remark"},
		{name:"applicant",mapping:"applicant"},
		{name:"sequence",mapping:"sequence"},
		{name:"searchExact",mapping:"searchExact"},
		{name:"expiredRent",mapping:"expiredRent"},
		{name:"phone",mapping:"advertiserPhone"},
		{name:"contact",mapping:"advertiserContact"},
		{name:"email",mapping:"advertiserEmail"},
		{name:"positionName",mapping:"positionName"}
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/ad/ad/queryAdById.htm"
		}
	}
});

Ext.define("AdGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"ad.id"},
		{name:"a.review_status",mapping:"ad.reviewStatus"},
		{name:"positionId",mapping:"ad.positionId"},
		{name:"adTitle",mapping:"ad.adTitle"},
		{name:"adTargetUrl",mapping:"ad.adTargetUrl"},
		{name:"adContent",mapping:"ad.adContent"},
		{name:"applicant",mapping:"ad.applicant"},
		{name:"a.online_status",mapping:"ad.onlineStatus"},
		{name:"designStatus",mapping:"ad.designStatus"},
		{name:"designer",mapping:"ad.designer"},
		{name:"advertiserId",mapping:"ad.advertiserId"},
		{name:"a.gmt_created",mapping:"ad.gmtCreated"},
		{name:"a.gmt_start",mapping:"ad.gmtStart"},
		{name:"a.gmt_plan_end",mapping:"ad.gmtPlanEnd"},
		{name:"positionName",mapping:"positionName"},
		{name:"hasExactAd",mapping:"hasExactAd"},
		{name:"advertiserName",mapping:"advertiserName"},
		{name:"requestUrl",mapping:"requestUrl"},
		{name:"a.sequence",mapping:"ad.sequence"},
		{name:"searchExact",mapping:"ad.searchExact"},
		{name:"expiredRent",mapping:"ad.expiredRent"},
		{name:"width",mapping:"width"},
		{name:"height",mapping:"height"}
	]
});

Ext.define("AdExactGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"exactName",mapping:"exact.exactName"},
		{name:"exactRemark",mapping:"exact.remark"},
		{name:"anchorPoint",mapping:"adExact.anchorPoint"},
		{name:"id",mapping:"adExact.id"}
	]
});

Ext.define("PositionTreeModel",{
	extend: 'Ext.data.Model',
	fields:[{name:"id",mapping:"id"},{name:"text",mapping:"text"},{name:"leaf",mapping:"leaf"}]
});

Ext.define("ExactTypeComboModel",{
	extend: 'Ext.data.Model',
	fields:["id",{name:"code",mapping:"id"},{name:"label",mapping:"remark"}]
});

Ext.define("com.zz91.ads.board.ad.ad.ApplyForm",{
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
		    items:[{
		    	xtype:"container",
		    	layout:"column",
		    	anchor:"100%",
		    	items:[{
		    		xtype: 'container',
					columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						name:"adTitle",
						fieldLabel:"广告名称",
						formItemCls:"x-form-item required",
						allowBlank : false
					},{
						xtype:"datefield",
						name:"gmtStart",
						fieldLabel:"开始时间",
						format : "Y-m-d H:i:s",
						submitFormat:"Y-m-d H:i:s",
						value:new Date(),
						allowBlank : false,
						formItemCls:"x-form-item required"
					}]
		    	},{
		    		xtype:"container",
		    		columnWidth:.5,
		    		layout:"anchor",
		    		defaults:{
		    			anchor:'100%',
						xtype : 'textfield'
		    		},
		    		items:[{
		    			name:"adTargetUrl",
						fieldLabel:"目标地址",
						formItemCls:"x-form-item required",
						allowBlank : false
		    		},{
						xtype:"datefield",
						name:"gmtPlanEnd",
						fieldLabel:"结束时间",
						format : "Y-m-d",
						submitFormat:"Y-m-d H:i:s",
						value:new Date()
					}]
		    	}]
		    },{
				xtype:"hidden",
				name:"positionId",
				id:"positionId"
			},{
		    	xtype:"textfield",
				name:"position",
				id:"position",
				anchor:"100%",
				fieldLabel:"投放位置",
				formItemCls:"x-form-item required",
				allowBlank : false,
				listeners:{
					'focus':function(){
						var win=Ext.create("com.zz91.util.TreeSelectorWin",{
							title:"选择投放位置",
							width : 250,
							height:400,
							modal : true,
							treeModel:"PositionTreeModel",
							queryUrl:Context.ROOT+"/ad/position/child.htm",
							nodeParam:"id",
							rootCode:"0",
							callbackFn:function(nodeInterface){
								Ext.getCmp("positionId").setValue(nodeInterface.data.id);
								Ext.getCmp("position").setValue(nodeInterface.data.text);
								this.close();
							}
						});
						win.show();
						
						win.child("treepanel").getRootNode().expand();
					}
				}
		    },{
		    	xtype:"textfield",
		    	anchor:"100%",
		    	name:"adDescription",
		    	fieldLabel:"广告描述"
		    },{
		    	xtype:"container",
		    	layout:"column",
		    	anchor:"100%",
		    	items:[{
		    		xtype:"container",
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						xtype:"hidden",
						id:"advertiserId",
						name:"advertiserId"
					},{
						id:"advertiser",
						name:"advertiser",
						fieldLabel:"广告主",
						formItemCls:"x-form-item required",
						allowBlank : false,
						listeners:{
							'focus':function(){
								var grid=Ext.create("com.zz91.ads.board.ad.advertiser.MainGrid",{
									height:250,
									region:"center"
								});
								
								grid.on("itemdblclick",function(e){
									var row = this.getSelectionModel().getLastSelected(); 
									Ext.getCmp("advertiser").setValue(row.get("name"));
									Ext.getCmp("advertiserId").setValue(row.get("id"));
									Ext.getCmp("contact").setValue(row.get("contact"));
									Ext.getCmp("phone").setValue(row.get("phone"));
									Ext.getCmp("email").setValue(row.get("email"));
									
									this.up("window").close();
								});
								
								var win= Ext.create("Ext.Window",{
									layout : 'border',
									iconCls : "add16",
									width : 600,
									autoHeight:true,
									title : "选择广告主",
									modal : true,
									items : [ grid ],
									buttons:[{
										iconCls:"saveas16",
										text:"选择",
										handler:function(){
											var row = grid.getSelectionModel().getLastSelected(); 
											Ext.getCmp("advertiser").setValue(row.get("name"));
											Ext.getCmp("advertiserId").setValue(row.get("id"));
											Ext.getCmp("contact").setValue(row.get("contact"));
											Ext.getCmp("phone").setValue(row.get("phone"));
											Ext.getCmp("email").setValue(row.get("email"));
											this.up("window").close();
										}
									},{
										iconCls:"close16",
										text:"取消",
										handler:function(){
											this.up("window").close();
										}
									}]
								});
						
								win.show();
							}
						}
					},{
						name:"phone",
						id:"phone",
						readOnly:true,
						fieldLabel:"联系电话"
					}]
		    	},{
		    		xtype:"container",
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						name:"contact",
						id:"contact",
						readOnly:true,
						fieldLabel:"联系人"
					},{
						name:"email",
						id:"email",
						readOnly:true,
						fieldLabel:"邮箱"
					}]
		    	}]
		    },{
		    	xtype:"textarea",
		    	anchor:"100%",
				name:"remark",
				height:250,
				fieldLabel:"备注信息"
		    }],
		    buttons:[{
				scale:"large",
				xtype:"button",
				text:"现在申请",
				iconCls:"saveas32",
				handler:this.saveModel
			}]
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	saveModel:function(){
		var form=this.up("form");
		var _url=form.getSaveUrl();
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "您的申请已经提交！");
					form.getForm().reset();
				},
				failure: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "发生错误，信息没有更新！");
				}
			});
		}
	},
	saveUrl:Context.ROOT+"/ad/ad/applyAd.htm",
	config:{
		saveUrl:null
	}
});

Ext.define("com.zz91.ads.board.ad.ad.EditAdvertiserForm",{
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
		    items:[{
		    	xtype:"container",
		    	layout:"column",
		    	anchor:"100%",
		    	items:[{
		    		xtype: 'container',
					columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						name:"id",
						fieldLabel:"id",
						formItemCls:"x-form-item",
						hidden : true
					},{
						name:"adTitle",
						fieldLabel:"广告名称",
						formItemCls:"x-form-item",
						allowBlank : false
					},{
						xtype:"datefield",
						name:"gmtStart",
						fieldLabel:"开始时间",
						format : "Y-m-d H:i:s",
						submitFormat:"Y-m-d H:i:s",
						value:new Date(),
						allowBlank : false,
						formItemCls:"x-form-item"
					}]
		    	},{
		    		xtype:"container",
		    		columnWidth:.5,
		    		layout:"anchor",
		    		defaults:{
		    			anchor:'100%',
						xtype : 'textfield'
		    		},
		    		items:[{
		    			name:"adTargetUrl",
						fieldLabel:"目标地址",
						formItemCls:"x-form-item",
						allowBlank : false
		    		},{
						xtype:"datefield",
						name:"gmtPlanEnd",
						fieldLabel:"结束时间",
						format : "Y-m-d",
						submitFormat:"Y-m-d H:i:s",
						value:new Date()
					}]
		    	}]
		    },{
				xtype:"hidden",
				name:"positionId",
				id:"positionId"
			}
//		    ,{
//		    	xtype:"textfield",
//				name:"positionName",
//				id:"positionName",
//				anchor:"100%",
//				fieldLabel:"投放位置",
//				formItemCls:"x-form-item"
//		    }
		    ,{
		    	xtype:"textfield",
		    	anchor:"100%",
		    	name:"adDescription",
		    	fieldLabel:"广告描述"
		    },{
		    	xtype:"container",
		    	layout:"column",
		    	anchor:"100%",
		    	items:[{
		    		xtype:"container",
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						xtype:"hidden",
						id:"advertiserId",
						name:"advertiserId"
					},{
						id:"advertiser",
						name:"advertiser",
						fieldLabel:"广告主",
						formItemCls:"x-form-item required",
						allowBlank : false,
						listeners:{
							'focus':function(){
								var grid=Ext.create("com.zz91.ads.board.ad.advertiser.MainGrid",{
									height:250,
									region:"center"
								});
								
								grid.on("itemdblclick",function(e){
									var row = this.getSelectionModel().getLastSelected(); 
									Ext.getCmp("advertiser").setValue(row.get("name"));
									Ext.getCmp("advertiserId").setValue(row.get("id"));
									Ext.getCmp("contact").setValue(row.get("contact"));
									Ext.getCmp("phone").setValue(row.get("phone"));
									Ext.getCmp("email").setValue(row.get("email"));
									
									this.up("window").close();
								});
								
								var win= Ext.create("Ext.Window",{
									layout : 'border',
									iconCls : "add16",
									width : 600,
									autoHeight:true,
									title : "选择广告主",
									modal : true,
									items : [ grid ],
									buttons:[{
										iconCls:"saveas16",
										text:"选择",
										handler:function(){
											var row = grid.getSelectionModel().getLastSelected(); 
											Ext.getCmp("advertiser").setValue(row.get("name"));
											Ext.getCmp("advertiserId").setValue(row.get("id"));
											Ext.getCmp("contact").setValue(row.get("contact"));
											Ext.getCmp("phone").setValue(row.get("phone"));
											Ext.getCmp("email").setValue(row.get("email"));
											this.up("window").close();
										}
									},{
										iconCls:"close16",
										text:"取消",
										handler:function(){
											this.up("window").close();
										}
									}]
								});
						
								win.show();
							}
						}
					},{
						name:"phone",
						id:"phone",
						readOnly:true,
						fieldLabel:"联系电话"
					}]
		    	},{
		    		xtype:"container",
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						name:"contact",
						id:"contact",
						readOnly:true,
						fieldLabel:"联系人"
					},{
						name:"email",
						id:"email",
						readOnly:true,
						fieldLabel:"邮箱"
					}]
		    	}]
		    },{
		    	xtype:"textarea",
		    	anchor:"100%",
				name:"remark",
				height:250,
				fieldLabel:"备注信息"
		    }],
		    buttons:[{
				scale:"large",
				xtype:"button",
				text:"修改",
				iconCls:"saveas32",
				handler:this.saveModel
			}]
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	saveModel:function(){
		var form=this.up("form");
		var _url=form.getSaveUrl();
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "修改已经提交！");
//					form.getForm().reset();
				},
				failure: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "发生错误，信息没有更新！");
				}
			});
		}
	},
	saveUrl:Context.ROOT+"/ad/ad/updateOnly.htm",
	config:{
		saveUrl:null
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('AdFormModel').load(id, {
			success: function(model) {
				_this.loadRecord(model);
				_this.getForm().findField("gmtStart").setValue(new Date(model.data.gmtStart.time));
				if(model.data.gmtPlanEnd!=null){
					_this.getForm().findField("gmtPlanEnd").setValue(new Date(model.data.gmtPlanEnd.time));
				}
			}
		});
	}
});

Ext.define("com.zz91.ads.board.ad.ad.BaseGrid",{
	extend:"Ext.grid.Panel",
        initComponent:function(){
	//constructor:function(config){
		//config = config||{};
		//Ext.apply(this,config);
		
		var _store=Ext.create("Ext.data.Store",{
			model:"AdGridModel",
			remoteSort:true,
			pageSize:Context.PAGE_SIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/ad/ad/query.htm",
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
		
		var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1
			});
		
		var _cm=[Ext.create('Ext.grid.RowNumberer'),{
				text:"编号",
				dataIndex:"id",
				width:50,
				hidden:true
			},{
				header:"状态",
				width:60,
				dataIndex:"a.review_status",
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var cst="<img src='"+Context.ROOT+"/themes/icons/review_"+value+".png' title='"+Context.REVIEWSTATUS_CATEGORY[value]+"' />";
					var ost="<img src='"+Context.ROOT+"/themes/icons/online_"+record.get("a.online_status")+".png' title='"+Context.ONLINE_STATUS[record.get("a.online_status")]+"' />";
					//过期状态
					if(record.get("designStatus")!=null && record.get("designStatus")!=""){
						var dst="<img src='"+Context.ROOT+"/themes/icons/design_"+record.get("designStatus")+".png' title='"+Context.DESIGN_STATUS[record.get("designStatus")]+"' />";
						return cst+ost+dst;
					}
					
					return cst+ost;
				}
			},{
				header:"排序",
				width:50,
				dataIndex:"a.sequence",
				sortable:true,
				editor: {
					xtype:"textfield",
	                allowBlank: false
	            }
			},{
				header:"广告位",
				dataIndex:"positionName",
				sortable:false,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var str=value;
					if(record.get("requestUrl")!=""){
						str="<a href='"+record.get("requestUrl")+"' target='_blank'>"+value+"</a>";
					}
					return str+"<br />规格：宽"+record.get("width")+"px;高"+record.get("height")+"px";
				}
			},{
				header:"广告",
				dataIndex:"adTitle",
				sortable:false,
				width:200,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					var ad= value;
					if(record.get("adTargetUrl")!="" && record.get("adTargetUrl")!=null){
						ad="<a href='"+record.get("adTargetUrl")+"' target='_blank' >"+value+"</a>";
					}
					if(record.get("expiredRent")!=""){
						ad=ad+"<br /><a href='"+record.get("expiredRent")+"' target='_blank' >含过期招租广告</a>";
					}
					if(record.get("adContent")!="" && record.get("adContent")!=null){
						return ad+"<br /><a href='"+record.get("adContent")+"' target='_blank' ><img src='"+Context.ROOT+"/themes/boomy/pictures16.png'></a><img src='"+record.get("adContent")+"' width='150' height='50'/>";
					}
					return ad;
				}
			},{
				header:"搜索条件",
				sortable:false,
				dataIndex:"searchExact"
			},{
				header:"申请人",
				dataIndex:"applicant",
				sortable:false
			},{
				header:"广告主",
				dataIndex:"advertiserName",
				sortable:false
			},{
				header:"申请时间",
				dataIndex:"a.gmt_created",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				header:"计划开始时间",
				dataIndex:"a.gmt_start",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						var now=new Date();
						if(now.getTime()>value.time){
							return Ext.String.format("<img src='{0}/lib/extjs/themes/boomy/clock16.png' title='广告已开始'/>{1}",Context.IMG0,Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s'));
						}else{
							return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
						}
					}else{
						return "";
					}
				}
			},{
				header:"计划下线时间",
				dataIndex:"a.gmt_plan_end",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						var now=new Date();
						if(now.getTime()>value.time){
							return Ext.String.format("<img src='{0}/lib/extjs/themes/boomy/clock16.png' title='广告已过期'/>{1}",Context.IMG0,Ext.util.Format.date(new Date(value.time), 'Y-m-d'));
						}else{
							return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
						}
					}else{
						return "";
					}
				}
			}];
		
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
			plugins:[cellEditing]
		};
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
            loadByEmail:function(email){
                 this.getStore().setExtraParam("email", email);
		this.getStore().load();
	}
});

Ext.define("com.zz91.ads.board.ad.ad.SimpleGrid", {
	extend:"com.zz91.ads.board.ad.ad.BaseGrid",
	initComponent:function(){
		
		var c={
			tbar:[{
				iconCls:"configure16",
				text:"操作",
				menu:[{
					iconCls:"add16",
					text:"申请广告",
					handler:function(){
						window.open(Context.ROOT+"/ad/ad/apply.htm");
					}
				},"-",{
					iconCls:"edit16",
					text:"审核/修改",
					scope:this,
					handler:function(){
						var grid=this;
						com.zz91.ads.board.ad.ad.editWin(
							grid.getSelectionModel().getLastSelected()
							);
					}
				},{
					iconCls:"edit16",
					text:"修改广告主",
					scope:this,
					handler:function(){
						var grid=this;
						var record=grid.getSelectionModel().getLastSelected();
						window.open(Context.ROOT+"/ad/ad/apply.htm?id="+record.get("id"));
					}
				},{
					iconCls:"accept16",
					text:"审核通过",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/ad/checkAd.htm",
								params:{"id":obj.get("id"),"reviewStatus":"Y"},
								success:function(response,opt){
									_this.getStore().load();
								},
								failure:function(response,opt){
								}
							});
						});
					}
				},{
					iconCls:"stop16",
					text:"退回",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/ad/checkAd.htm",
								params:{"id":obj.get("id"),"reviewStatus":"N"},
								success:function(response,opt){
									_this.getStore().load();
								},
								failure:function(response,opt){
								}
							});
						});
					}
				},"-",{
					iconCls:"movefile16",
					text:"移动广告",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						
						var win=Ext.create("com.zz91.util.TreeSelectorWin",{
							title:"选择要移动到的位置",
							width : 250,
							height:400,
							modal : true,
							treeModel:"PositionTreeModel",
							queryUrl:Context.ROOT+"/ad/position/child.htm",
							nodeParam:"id",
							rootCode:"0",
							callbackFn:function(nodeInterface){
								var pid=nodeInterface.data.id;
								var success=0;
								var failure=0;
								
								Ext.Array.each(selections, function(obj, index, countriesItSelf){
									Ext.Ajax.request({
										url:Context.ROOT+"/ad/ad/moveAd.htm",
										params:{"id":obj.get("id"),"positionId":pid},
										success:function(response,opt){
											success++;
											if((success+failure) == selections.length){
												Ext.Msg.alert(MESSAGE.title, Ext.String.format("共移动广告{0}个，其中成功移动{1}个广告，失败{2}个。",selections.length,success,failure));
												_this.getStore().load();
											}
										},
										failure:function(response,opt){
											failure++;
										}
									});
								});
								
								this.close();
							}
						});
						win.show();
						
						win.child("treepanel").getRootNode().expand();
					}
				},{
					iconCls:"add16",
					text:"复制",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/ad/copy.htm",
								params:{"id":obj.get("id")},
								success:function(response,opt){
									_this.getStore().load();
								},
								failure:function(response,opt){
								}
							});
						});
					}
				},"-",{
					iconCls:"weboptions16",
					text:"广告上线",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/ad/publishAd.htm",
								params:{"id":obj.get("id"),"onlineStatus":"Y"},
								success:function(response,opt){
									_this.getStore().load();
								},
								failure:function(response,opt){
								}
							});
						});
					}
				},{
					iconCls:"pause16",
					text:"广告下线",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/ad/publishAd.htm",
								params:{"id":obj.get("id"),"onlineStatus":"N"},
								success:function(response,opt){
									_this.getStore().load();
								},
								failure:function(response,opt){
								}
							});
						});
					}
				},"-",{
					iconCls:"trash16",
					text:"删除广告",
					scope:this,
					handler:function(){
						var _this=this;
						var selections=_this.getSelectionModel().getSelection();
						Ext.Msg.confirm(MESSAGE.title,"广告删除后将无法恢复，您确定要删除这些广告吗？",function(o){
							if(o!="yes"){
								return ;
							}
							Ext.Array.each(selections, function(obj, index, countriesItSelf){
								Ext.Ajax.request({
									url:Context.ROOT+"/ad/ad/deleteAd.htm",
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
				}]
			},{
				iconCls:"find16",
				text:"快速搜索",
				menu:[{
					iconCls:"clock16",
					text:"今天到期",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.add(now,Ext.Date.DAY,1);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},{
					iconCls:"clock16",
					text:"即将到期(7d)",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.add(now,Ext.Date.DAY,7);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},{
					iconCls:"clock16",
					text:"30天到期(30d)",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.add(now,Ext.Date.DAY,30);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},"-",{
					iconCls:"clock16",
					text:"本月到期",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.getLastDateOfMonth(now);
						to=Ext.Date.add(to,Ext.Date.DAY,1);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},"-",{
					iconCls:"refresh16",
					text:"重置搜索",
					scope:this,
					handler:function(){
						this.getStore().setExtraParam("planEndFrom",null);
						this.getStore().setExtraParam("planEndTo",null);
						
						this.getStore().load();
					}
				}]
			},{
				iconCls:"find16",
				text:"高级搜索",
				id:"searchBtn",
				handler:function(){
					
					var win=Ext.getCmp("searchWindow");
					var store=this.up("grid").getStore();
					
					if(typeof win=="undefined"){
						var searchForm=Ext.create("com.zz91.ads.board.ad.ad.searchForm",{
							region:"center"
						});
						
						win=Ext.create("Ext.window.Window",{
							id:"searchWindow",
							closeAction:"hide",
							animateTarget:"searchBtn",
							layout : 'border',
							iconCls : "find16",
							width : 350,
							autoHeight:true,
							title : "高级搜索",
							collapsible:true,
							titleCollapse:true,
//							modal : true,
							items : [ searchForm ],
							buttons:[{
								iconCls:"textfile16",
								text:"清空",
								handler:function(){
									searchForm.getForm().reset();
								}
							},{
								iconCls:"find16",
								text:"搜索",
								handler:function(){
									searchForm.buildSearch(store);
									store.load();
								}
							},{
								iconCls:"close16",
								text:"关闭",
								handler:function(){
									this.up("window").close();
								}
							}]
						});
					}
					
					win.show();
				}
			},{
				text:"缓存",
				menu:[{
					text:"缓存管理",
					handler:function(){
						window.open("http://gg.zz91.com/cacheManager");
					}
				},{
					text:"广告跟踪",
					handler:function(){
						window.open("http://gg.zz91.com/tracking");
					}
				}]
			},"->",{
				xtype:"textfield",
				emptyText:"请输入Email",
				listeners:{
					"change":function(field,nv,ov){
						this.up("grid").getStore().setExtraParam("email", nv);
						this.up("grid").getStore().load();
					}
				}
			},{
				xtype:"textfield",
				emptyText:"请输入要搜索的关键字",
				id:"searchKeywords",
				listeners:{
					"change":function(field,nv,ov){
						this.up("grid").getStore().setExtraParam("anchorPoint", nv);
						this.up("grid").getStore().load();
						
						Ext.getCmp("bookinggrid").getStore().setExtraParam("keywords",nv);
						Ext.getCmp("bookinggrid").getStore().load();
					}
				}
			},{
				xtype:"checkboxfield",
				boxLabel:"仅未审核",
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("reviewStatus", "U");
					}else{
						this.up("grid").getStore().setExtraParam("reviewStatus", null);
					}
					this.up("grid").getStore().load();
				}
			},{
				xtype:"checkboxfield",
				boxLabel:"仅未上线",
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("onlineStatus", "N");
					}else{
						this.up("grid").getStore().setExtraParam("onlineStatus", null);
					}
					this.up("grid").getStore().load();
				}
			}],
			listeners:{
				"itemdblclick":function(view,record,item,index,e){
					com.zz91.ads.board.ad.ad.editWin(record);
				},
				"edit":function(edit,e,o){
					Ext.Ajax.request({
						url:Context.ROOT+"/ad/check/updateSequence.htm",
						params:{"sequence":e.record.get("a.sequence"),"id":e.record.get("id")},
						success:function(response,opt){
							Ext.getCmp("mainadgrid").getStore().load();
						},
						failure:function(response,opt){
						}
					});
				}
			}
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	initDefault:function(planEndFrom){
		this.getStore().setExtraParam("planEndFrom",Ext.Date.format(new Date(parseInt(planEndFrom)),"Y-m-d H:i:s"));
	}
});

Ext.define("com.zz91.ads.board.ad.ad.SaleGrid", {
	extend:"com.zz91.ads.board.ad.ad.BaseGrid",
	initComponent:function(){
		
		var c={
			tbar:[{
					iconCls:"add16",
					text:"申请广告",
					handler:function(){
						window.open(Context.ROOT+"/ad/ad/apply.htm");
					}
				},{
				iconCls:"find16",
				text:"快速搜索",
				menu:[{
					iconCls:"clock16",
					text:"今天到期",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.add(now,Ext.Date.DAY,1);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},{
					iconCls:"clock16",
					text:"即将到期(7d)",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.add(now,Ext.Date.DAY,7);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},{
					iconCls:"clock16",
					text:"30天到期(30d)",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.add(now,Ext.Date.DAY,30);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},"-",{
					iconCls:"clock16",
					text:"本月到期",
					scope:this,
					handler:function(){
						var now=Ext.Date.clearTime(new Date());
						var to=Ext.Date.getLastDateOfMonth(now);
						to=Ext.Date.add(to,Ext.Date.DAY,1);
						this.getStore().setExtraParam("planEndFrom",Ext.Date.format(now,"Y-m-d H:i:s"));
						this.getStore().setExtraParam("planEndTo",Ext.Date.format(to,"Y-m-d H:i:s"));
						
						this.getStore().load();
					}
				},"-",{
					iconCls:"refresh16",
					text:"重置搜索",
					scope:this,
					handler:function(){
						this.getStore().setExtraParam("planEndFrom",null);
						this.getStore().setExtraParam("planEndTo",null);
						
						this.getStore().load();
					}
				}]
			},{
				iconCls:"find16",
				text:"高级搜索",
				id:"searchBtn",
				handler:function(){
					
					var win=Ext.getCmp("searchWindow");
					var store=this.up("grid").getStore();
					
					if(typeof win=="undefined"){
						var searchForm=Ext.create("com.zz91.ads.board.ad.ad.searchForm",{
							region:"center"
						});
						
						win=Ext.create("Ext.window.Window",{
							id:"searchWindow",
							closeAction:"hide",
							animateTarget:"searchBtn",
							layout : 'border',
							iconCls : "find16",
							width : 350,
							autoHeight:true,
							title : "高级搜索",
							collapsible:true,
							titleCollapse:true,
//							modal : true,
							items : [ searchForm ],
							buttons:[{
								iconCls:"textfile16",
								text:"清空",
								handler:function(){
									searchForm.getForm().reset();
								}
							},{
								iconCls:"find16",
								text:"搜索",
								handler:function(){
									searchForm.buildSearch(store);
									store.load();
								}
							},{
								iconCls:"close16",
								text:"关闭",
								handler:function(){
									this.up("window").close();
								}
							}]
						});
					}
					
					win.show();
				}
			},"->",{
				xtype:"textfield",
				emptyText:"请输入Email",
				listeners:{
					"change":function(field,nv,ov){
						this.up("grid").getStore().setExtraParam("email", nv);
						this.up("grid").getStore().load();
					}
				}
			},{
				xtype:"textfield",
				emptyText:"请输入要搜索的关键字",
				id:"searchKeywords",
				listeners:{
					"change":function(field,nv,ov){
						this.up("grid").getStore().setExtraParam("anchorPoint", nv);
						this.up("grid").getStore().load();
						
						Ext.getCmp("bookinggrid").getStore().setExtraParam("keywords",nv);
						Ext.getCmp("bookinggrid").getStore().load();
					}
				}
			},{
				xtype:"checkboxfield",
				boxLabel:"仅未审核",
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("reviewStatus", "U");
					}else{
						this.up("grid").getStore().setExtraParam("reviewStatus", null);
					}
					this.up("grid").getStore().load();
				}
			},{
				xtype:"checkboxfield",
				boxLabel:"仅未上线",
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("onlineStatus", "N");
					}else{
						this.up("grid").getStore().setExtraParam("onlineStatus", null);
					}
					this.up("grid").getStore().load();
				}
			}]
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	}
});

Ext.define("com.zz91.ads.board.ad.ad.searchForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		
		var c={
			bodyPadding: 5,
			fieldDefaults: {
				labelAlign: 'right',
				labelSeparator:""
			},
			autoScroll:true,
			layout:"anchor",
			defaults:{
				anchor:'100%',
				xtype : 'textfield'
			},
			items:[{
				name:"adTitle",
				fieldLabel:"广告标题"
			},{
				xtype:"datefield",
				name:"planEndFrom",
//				id:"planEndFrom",
				format : "Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				fieldLabel:"到期范围（始）"
			},{
				xtype:"datefield",
				name:"planEndTo",
				format : "Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				fieldLabel:"到期范围（末）"
			},{
				xtype:"datefield",
				name:"startFrom",
				format : "Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				fieldLabel:"开始范围（始）"
			},{
				xtype:"datefield",
				name:"startTo",
				format : "Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				fieldLabel:"开始范围（末）"
			}]
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	buildSearch:function(store){
		var baseForm=this.getForm();
//		alert(baseForm.findField("planEndFrom").getSubmitValue())
		store.setExtraParam("adTitle",baseForm.findField("adTitle").getValue());
		store.setExtraParam("planEndFrom",baseForm.findField("planEndFrom").getSubmitValue());
		store.setExtraParam("planEndTo",baseForm.findField("planEndTo").getSubmitValue());
		store.setExtraParam("startFrom",baseForm.findField("startFrom").getSubmitValue());
		store.setExtraParam("startTo",baseForm.findField("startTo").getSubmitValue());
	}
});

Ext.define("com.zz91.ads.board.ad.ad.EditForm",{
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
		    items:[{
		    	xtype:"container",
		    	layout:"column",
		    	anchor:"100%",
		    	items:[{
		    		xtype: 'container',
					columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						name:"adTitle",
						fieldLabel:"广告名称",
						formItemCls:"x-form-item required",
						allowBlank : false
					},{
						xtype:"datefield",
						name:"gmtStart",
						fieldLabel:"开始时间",
						format : "Y-m-d H:i:s",
						submitFormat:"Y-m-d H:i:s",
						value:new Date(),
						allowBlank : false,
						formItemCls:"x-form-item required"
					}]
		    	},{
		    		xtype:"container",
		    		columnWidth:.5,
		    		layout:"anchor",
		    		defaults:{
		    			anchor:'100%',
						xtype : 'textfield'
		    		},
		    		items:[{
		    			name:"adTargetUrl",
						fieldLabel:"目标地址",
						formItemCls:"x-form-item required",
						allowBlank : false
		    		},{
						xtype:"datefield",
						name:"gmtPlanEnd",
						fieldLabel:"结束时间",
						format : "Y-m-d",
						submitFormat:"Y-m-d H:i:s",
						value:new Date()
					}]
		    	}]
		    },{
		    	xtype:"textfield",
				name:"adContent",
				id:"adContent",
				anchor:"100%",
				fieldLabel:"广告媒体",
				listeners:{
					'focus':function(field){
						var win=Ext.create("com.zz91.util.UploadWin",{
							uploadUrl:Context.ROOT+"/ad/ad/upload.htm",
							callbackFn:function(form,action){
								field.setValue(Context.ROOT_IMAGE+action.result.data);
								win.close();
							}
						});
						win.show();
					}
				}
		    },{
		    	xtype:"textfield",
				name:"expiredRent",
				id:"expiredRent",
				anchor:"100%",
				fieldLabel:"招租图片",
				listeners:{
					'focus':function(field){
						var win=Ext.create("com.zz91.util.UploadWin",{
							uploadUrl:Context.ROOT+"/ad/ad/upload.htm",
							callbackFn:function(form,action){
								field.setValue(Context.ROOT_IMAGE+action.result.data);
								win.close();
							}
						});
						win.show();
					}
				}
		    },{
		    	xtype:"textfield",
		    	anchor:"100%",
		    	name:"adDescription",
		    	fieldLabel:"广告描述"
		    },{
		    	xtype:"textarea",
		    	anchor:"100%",
				name:"remark",
				height:100,
				fieldLabel:"备注信息"
		    },{
		    	xtype:"hidden",
				name:"id",
				id:"id"
		    }],
		    buttons:[{
				xtype:"button",
				text:"取消招租",
				iconCls:"delete16",
				handler:this.cancelRent
			},{
				xtype:"button",
				text:"仅修改",
				iconCls:"saveas16",
				handler:this.saveModel
			},{
				xtype:"button",
				text:"保存并通过",
				iconCls:"accept16",
				handler:this.saveAndCheckModel
			},{
				xtype:"button",
				text:"关闭",
				iconCls:"close16",
				handler:function(){
					this.up("window").close();
				}
			}]
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
	saveModel:function(){
		var form=this.up("form");
		var _url=Context.ROOT+"/ad/ad/updateOnly.htm";
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					form.up("window").close();
					Ext.getCmp("mainadgrid").getStore().load();
				},
				failure: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "发生错误，信息没有更新！");
				}
			});
		}
	},
	saveAndCheckModel:function(){
		var form=this.up("form");
		var _url=Context.ROOT+"/ad/ad/updateAndCheck.htm";
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:_url,
				success: function(f, action) {
					form.up("window").close();
					Ext.getCmp("mainadgrid").getStore().load();
				},
				failure: function(f, action) {
					Ext.Msg.alert(MESSAGE.title, "发生错误，信息没有更新！");
				}
			});
		}
	},
	cancelRent:function(){
		var form=this.up("form");
		Ext.Ajax.request({
			url: Context.ROOT+"/ad/ad/removeRent.htm",
			params: {
				id: form.child("#id").getValue()
			},
			success: function(response){
				form.child("#expiredRent").setValue("招租广告已移除，但您仍然可以点击这里重新设置招租广告！");
			}
		});
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('AdFormModel').load(id, {
			success: function(model) {
				_this.loadRecord(model);
				_this.getForm().findField("gmtStart").setValue(new Date(model.data.gmtStart.time));
				if(model.data.gmtPlanEnd!=null){
					_this.getForm().findField("gmtPlanEnd").setValue(new Date(model.data.gmtPlanEnd.time));
				}
			}
		});
	}
});


Ext.define("com.zz91.ads.board.ad.ad.AdExactTypeForm",{
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
		    	anchor:"100%"
		    },
		    items:[{
		    	xtype:"hidden",
		    	name:"adId"
		    },Ext.create("com.zz91.util.CategoryCombo",{
		    	name:"exactTypeId",
		    	queryUrl:Context.ROOT+"/ad/ad/queryExactTypeOfAd.htm?aid=",
		    	al:false,
		    	categoryModel:"ExactTypeComboModel",
		    	rootCode:this.getAid(),
		    	fieldLabel:"Key",
				formItemCls:"x-form-item required",
				allowBlank : false
		    }),{
				xtype:"textfield",
				name:"anchorPoint",
				fieldLabel:"Value",
				formItemCls:"x-form-item required",
				allowBlank : false
			}],
			buttons:[{
				text:"保存",
				iconCls:"saveas16",
				handler:function(){
					var form=this.up("form");
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:Context.ROOT+"/ad/ad/createAdExact.htm",
							success: function(f, action) {
								form.getGridStore().load();
								form.up("window").close();
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
	aid:0,
	gridStore:null,
	config:{
		aid:0,
		gridStore:null
	},
	initAdId:function(adid){
		this.getForm().findField("adId").setValue(adid);
	}
});

Ext.define("com.zz91.ads.board.ad.ad.AdExactTypeGrid",{
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"AdExactGridModel",
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/ad/ad/queryAdExact.htm",
//				simpleSortMode:true,
				reader: {
					type: 'json'
				}
			},
			autoLoad:true
		});
		
		_store.setExtraParam("aid",this.getAid());
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
				header:"编号",
				dataIndex:"id",
				width:50,
				hidden:true
			},{
				header:"Key",
				dataIndex:"exactName"
			},{
				header:"Value",
				dataIndex:"anchorPoint"
			},{
				header:"备注",
				dataIndex:"exactRemark"
			}];
			
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			tbar:[{
				iconCls:"add16",
				text:"添加",
				handler:function(){
					var grid=this.up("grid");
					var form=Ext.create("com.zz91.ads.board.ad.ad.AdExactTypeForm",{
						aid:grid.getAid(),
						gridStore:grid.getStore(),
						region:"center"
					});
					
					form.initAdId(grid.getAid());
					
					var win=Ext.create("Ext.window.Window",{
						layout:"border",
						title:"添加投放条件",
						autoHeight:true,
						width:300,
						modal:true,
						items:[form]
					});
					
					win.show();
				}
			},{
				iconCls:"delete16",
				text:"删除",
				handler:function(){
					var selections=this.up("grid").getSelectionModel().getSelection();
					if(selections.length<=0){
						return ;
					}
					var _this=this.up("grid");
					Ext.Msg.confirm(MESSAGE.title,"您确定要删除这些信息吗？",function(o){
						if(o!="yes"){
							return ;
						}
						Ext.Array.each(selections, function(obj, index, countriesItSelf){
							Ext.Ajax.request({
								url: Context.ROOT+"/ad/ad/deleteAdExact.htm",
								params: {
									id: obj.getId()
								},
								success: function(response){
									_this.getStore().load();
								}
							});
						});
					});
				}
			}]
		};
		
		Ext.applyIf(this,c);
		this.callParent();
	},
	aid:0,
	config:{
		aid:0
	}
});

com.zz91.ads.board.ad.ad.editWin=function(record){
	var form=Ext.create("com.zz91.ads.board.ad.ad.EditForm",{
		region:"center"
	});
	
	var grid=Ext.create("com.zz91.ads.board.ad.ad.AdExactTypeGrid",{
		aid:record.get("id"),
		region:"south",
		height:150
	});
	
	var win=Ext.create("Ext.window.Window",{
		title:"修改/审核广告",
		layout:"border",
		width : 600,
		height:500,
		modal : true,
		items:[form,grid]
	});
	win.show();
	form.loadModel(record.get("id"));
}