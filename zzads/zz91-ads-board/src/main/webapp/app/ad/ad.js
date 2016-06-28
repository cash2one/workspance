Ext.namespace("com.zz91.ads.board.ad.ad");

/**
 * 广告列表
 * @class com.zz91.ads.board.ad.ad.Grid
 * @extends Ext.grid.GridPanel
 */
com.zz91.ads.board.ad.ad.Grid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:_fields,
			url:_url
//			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				sortable:false
			}, {
				header:"审核状态",
				dataIndex:"check",
				sortable:false
			}, {
				header:"广告位",
				dataIndex:"name",
				sortable:false
			},{
				header:"广告",
				dataIndex:"remark",
				sortable:false
			},{
				header:"申请人",
				dataIndex:"",
				sortable:false
			},{
				header:"广告主",
				dataIndex:"",
				sortable:false
			},{
				header:"申请时间",
				dataIndex:"gmtCreated",
				sortable:false
			},{
				header:"开始时间",
				dataIndex:"gmtStart",
				sortable:false
			},{
				header:"结束时间",
				dataIndex:"gmtPlanEnd",
				sortable:false
			}
		]);
		
		var c = {
			iconCls:"add16",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				iconCls:"add16",
				text:"广告申请",
				handler:function(btn){
					com.zz91.ads.board.ad.ad.AdEditWin();
				}
			}],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: "显示第 {0} - {1} 条记录,共 {2} 条",
				emptyMsg : "没有可显示的记录",
				beforePageText : "第",
				afterPageText : "页,共{0}页",
				paramNames : {start:"start",limit:"limit"}
			})
		};
		com.zz91.ads.board.ad.ad.Grid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"name",mapping:"name"},
		{name:"remark",mapping:"remark"},
		{name:"gmtCreated",mapping:"gmtCreated"}
	]),
	listUrl:Context.ROOT+"/ad/admaterial/query.htm"
});

/**
 * 添加广告步骤一
 * @class com.zz91.ads.board.ad.ad.AdEditForm
 * @extends Ext.form.FormPanel
 */
com.zz91.ads.board.ad.ad.AdEditForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[
				{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"adId",
						name:"id"
					},{
						name:"adTargetUrl",
						fieldLabel:"目标地址",
						blankText : "目标地址不能为空",
						itemCls :"required",
						allowBlank : false
					},{
						name:"adTitle",
						fieldLabel:"广告名称",
						itemCls :"required",
						allowBlank : false
					},{
						xtype:"hidden",
						name:"positionId",
						id:"positionId"
					},{
						name:"position",
						id:"position",
						fieldLabel:"投放位置",
						itemCls :"required",
						allowBlank : false,
						listeners:{
							'focus':{
								fn:function(){
									com.zz91.ads.board.ad.ad.SelectPositionWin();
								}
							}
						}
					},{
						xtype:"datefield",
						name:"gmtStartStr",
						fieldLabel:"开始时间",
						format : "Y-m-d H:i:s",
						value:new Date(),
						allowBlank : false,
						itemCls:"required"
					},{
						xtype:"datefield",
						name:"gmtPlanEndStr",
						fieldLabel:"结束时间",
						format : "Y-m-d",
//						value:new Date(),
						allowBlank:true
					}]
				},
//				{
//					columnWidth:0.5,
//					layout:"form",
//					defaults:{
//						anchor:"95%",
////						xtype:"textfield",
//						labelSeparator:""
//					},
//					items:[{html:"<div style='width:100%;height:200px;background:White'>效果预览</div>"}]
//				},
				{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"advertiserId"
					},{
						id:"advertiser",
						name:"advertiser",
						fieldLabel:"广告主",
						itemCls :"required",
						allowBlank : false,
						listeners:{
							'focus':{
								fn:function(){
									com.zz91.ads.board.ad.ad.SelectAdvertiserWin();
								}
							}
						}
					},{
						name:"contact",
						id:"contact",
						readOnly:true,
						fieldLabel:"联系人",
						allowBlank : true
					},{
						name:"phone",
						id:"phone",
						readOnly:true,
						fieldLabel:"联系电话",
						allowBlank : true
					},{
						name:"email",
						id:"email",
						readOnly:true,
						fieldLabel:"邮箱",
						allowBlank : true
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textarea",
						name:"adDescription",
						fieldLabel:"广告描述",
						alloeBlank:true
					},{
							xtype:"textarea",
							name:"remark",
							fieldLabel:"备注信息",
							alloeBlank:true
						}
					]
				}
			],
			buttons:[{
					text:"仅提交申请",
					iconCls:"floppy16",
					handler:this.saveOnly,
					scope:this
				},{
					text:"申请并继续",
					iconCls:"addfile16",
					handler:this.save,
					scope:this
				}
			]
		};
		
		com.zz91.ads.board.ad.ad.AdEditForm.superclass.constructor.call(this,c);
	},
	loadRecords:function(id){
		var _fields=[
			{name:"id",mapping:"id"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + "/ad/ad/queryById.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		})
	},
	saveOnly:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccessOnly,
				failure:this.onSaveFailure,
				scope:this
			});
		}
	},
	saveUrl:Context.ROOT + "/ad/ad/applyAd.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}
	},
	onSaveSuccessOnly:function (_form,_action){
		Ext.getCmp(AD.STEP_ONE_WIN).close();
	},
	onSaveSuccess:function (_form,_action){
		Ext.getCmp(AD.STEP_ONE_WIN).close();
		com.zz91.ads.board.ad.ad.AdExactTypeFormEditWin(_action.result.data);
	},
	onSaveFailure:function (_form,_action){
		com.zz91.ads.board.utils.Msg("","保存失败！");
	}
});

/**
 * 步骤一(窗口)
 * @param {} id
 */
com.zz91.ads.board.ad.ad.AdEditWin= function(id){
	var form = new com.zz91.ads.board.ad.ad.AdEditForm({
		id:"AD_FORM"
	});
	
	var win = new Ext.Window({
		id:AD.STEP_ONE_WIN,
		title:"广告投放申请-Step 1",
		width:650,
		autoHeight:true,
		modal:true,
		maximizable:true,
		items:[form]
	});
	win.show();
}

/**
 * 精确投放
 * @class com.zz91.ads.board.ad.ad.AdExactTypeFormEditForm
 * @extends Ext.form.FormPanel
 */
com.zz91.ads.board.ad.ad.AdExactTypeFormEditForm = Ext.extend(Ext.form.FormPanel, {
	aid:0,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var adid=config.aid;
		
		var c={
			labelAlign : "right",
			labelWidth : 80,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"99%",
				labelSeparator:""
			},
			items:[{
				xtype:"combo",
				triggerAction : "all",
				forceSelection : true,
				fieldLabel:"key",
				displayField : "remark",
				valueField : "id",
				hiddenName:"exactTypeId",
				allowBlank:false,
				itemCls:"required",
				store:new Ext.data.JsonStore( {
					fields : [ "remark", "id" ],
					autoLoad:true,
					url : Context.ROOT + "/ad/ad/queryExactTypeOfAd.htm?aid="+adid
				})
			},{
				xtype:"textfield",
				name:"anchorPoint",
				fieldLabel:"value",
				itemCls :"required",
				allowBlank : false
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:this.save
			}]
		};
		
		com.zz91.ads.board.ad.ad.AdExactTypeFormEditForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT + "/ad/ad/createAdExact.htm",
	save:function(){
		var _url = this.saveUrl+"?adId="+this.aid;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}
	},
	onSaveSuccess:function (_form,_action){
		
	},
	onSaveFailure:function (){
		com.zz91.ads.board.utils.Msg("","保存失败！");
	}
});

com.zz91.ads.board.ad.ad.AdExactTypeGrid=Ext.extend(Ext.grid.GridPanel,{
	aid:0,
	getAid:function(){
		return this.aid;
	},
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var grid=this;
		
		var _field=[{name:"exactName",mapping:"exact.exactName"},
		           {name:"exactRemark",mapping:"exact.remark"},
		           {name:"anchorPoint",mapping:"adExact.anchorPoint"},
		           {name:"id",mapping:"adExact.id"}];
		
		var _store=new Ext.data.JsonStore({
			remoteSort:true,
			fields:_field,
			url:Context.ROOT+"/ad/ad/queryAdExact.htm",
			params:{aid:grid.getAid()},
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel();
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"key",
			dataIndex:"exactName"
		},{
			header:"value",
			dataIndex:"anchorPoint"
		},{
			header:"说明",
			dataIndex:"exactRemark"
		}]);
		
		
		
		var c={
			store:_store,
			sm:_sm,
			cm:_cm,
			loadMask:Context.LOADMASK,
			tbar:[{
				iconCls:"add16",
				text:"添加投放条件",
				handler:function(btn){
					var aid=grid.getAid();
					var form=new com.zz91.ads.board.ad.ad.AdExactTypeFormEditForm({
						aid:aid,
						height:100,
						onSaveSuccess:function(_form,_action){
							_store.baseParams["aid"]=aid;
							_store.reload();
							this.ownerCt.close();
						}
					});
					
					var win = new Ext.Window({
						title:"添加投放条件",
						width:350,
						autoHeight:true,
						modal:true,
						items:[form]
					});
					
					win.show();
				}
			},{
				iconCls:"delete16",
				text:"删除",
				handler:function(btn){
					var row=grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
				        url:Context.ROOT+"/ad/ad/deleteAdExact.htm",
				        params:{"id":row.get("id")},
				        success:function(response,opt){
				            var obj = Ext.decode(response.responseText);
				            if(obj.success){
				            	_store.reload();
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
			},{
				iconCls:"refresh16",
				text:"刷新",
				handler:function(btn){
					_store.reload();
				}
			}]
		};
		
		com.zz91.ads.board.ad.ad.AdExactTypeGrid.superclass.constructor.call(this,c);
	}
});

/**
 * 步骤二（窗口）
 * @param {} id
 */
com.zz91.ads.board.ad.ad.AdExactTypeFormEditWin = function(id){

	var grid=new com.zz91.ads.board.ad.ad.AdExactTypeGrid({
		aid:id,
		height:300
	});
	
	var win = new Ext.Window({
		id:AD.STEP_TWO_WIN,
		title:"广告投放申请 Step 2  (注：非精确投放请跳过)",
		width:650,
		autoHeight:true,
		modal:true,
		items:[grid],
		buttons:[{
			text:"继续",
			handler:function(){
				win.close();
				com.zz91.ads.board.ad.ad.AdMaterialGridWin(id);
			}
		},{
			text:"跳过",
			handler:function(){
				win.close();
				com.zz91.ads.board.ad.ad.AdMaterialGridWin(id);
			}
		}]
	});
	win.show();
}

/**
 * 步骤三（窗口）
 * @param {} id
 */
com.zz91.ads.board.ad.ad.AdMaterialGridWin = function(id){
	var form = new com.zz91.ads.board.ad.material.AdMaterialGrid({
		aid:id,
		height:300
	});
	
	var win = new Ext.Window({
		title:"广告投放申请 Step 3",
		width:650,
		autoHeight:true,
		modal:true,
		items:[form],
		buttons:[{
			text:"完成申请",
			handler:function(btn){
				win.close();
			}
		}]
	});
	
	win.show();
}


/**
 * 广告主选择框
 * @param {} id
 */
com.zz91.ads.board.ad.ad.SelectAdvertiserWin = function(id){
	var grid = new com.zz91.ads.board.ad.advertiser.AdvertiserGrid({
		height:300
	});
	
	grid.on("dblclick",function(e){
		var row = this.getSelectionModel().getSelected(); 
		Ext.getCmp("advertiser").setValue(row.get("name"));
		Ext.getCmp("advertiserId").setValue(row.get("id"));
		Ext.getCmp("contact").setValue(row.get("contact"));
		Ext.getCmp("phone").setValue(row.get("phone"));
		Ext.getCmp("email").setValue(row.get("email"));
		
		this.ownerCt.close();
	});
	
	var win = new Ext.Window({
		title:"选择广告主",
		width:600,
		autoHeight:true,
		modal:true,
		items:[grid]
	});
	win.show();
}

/**
 * 广告位选择框
 * @param {} id
 */
com.zz91.ads.board.ad.ad.SelectPositionWin = function(id){
	var tree = new com.zz91.ads.board.ad.position.TreePanel({
		height:380,
		autoScroll:true,
		layout:"fit",
		region:"center",
		contextmenu:null
	});
	
	var win = new Ext.Window({
		title:"选择广告位",
		modal:true,
		width:300,
		items:[tree]
	});
	win.show();
	
	tree.on('dblclick',function(node,e){
		Ext.get("positionId").dom.value=node.attributes["data"];
		Ext.get("position").dom.value=node.text;
		win.close();
	});
}
