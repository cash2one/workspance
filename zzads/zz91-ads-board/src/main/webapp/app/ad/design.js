Ext.namespace("com.zz91.ads.board.ad.design");

var WinConfig =new function(){
	this.FormViewAdvertiseId= "FormViewAdvertise";
	this.WinViewAdvertiseId	= "WinViewAdvertise"
}
/*
 *  设计广告
 *  param com.zz91.ads.board.ad.design.Grid
 */

com.zz91.ads.board.ad.design.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields = this.listRecord;
		var _url=this.listUrl;
			
		var _store	= new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			fields:_fields,
			url:_url,
			autoLoad:true,
			remoteSort:true
		});
		
		var _sm		= new Ext.grid.CheckboxSelectionModel({});
		
		var _cm		= new Ext.grid.ColumnModel([_sm,
			{
				header:"广告位",
				dataIndex:"positionName",
				width:300,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var str=value;
					if(record.get("requestUrl")!=""){
						str="<a href='"+record.get("requestUrl")+"' target='_blank'>"+value+"</a>";
					}
					return str+"<br />规格：宽"+record.get("width")+"px;高"+record.get("height")+"px";
				}
			},{
				header:"广告名",
				width:200,
				dataIndex:"ad_title"
			},{
				header:"设计者",
				dataIndex:"designer"
			},{
				sortable:true,
				header:"申请时间",
				dataIndex:"gmt_created",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				sortable:true,
				header:"上线时间",
				dataIndex:"gmt_start",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				header:"下线时间",
				sortable:true,
				dataIndex:"gmt_plan_end",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			}
		]);
		
		var c		= {
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			fields:_fields,
			tbar:[{
				iconCls:"filesearch16",
				xtype:"button",
				text:"查看广告",
				handler:function(btn){
					var row=_sm.getSelected();
					if(row!=null){
						com.zz91.ads.board.ad.design.designWin(row.get("id"));
					}
				}
			}
//			,"-",{
//				xtype:"checkbox",
//				fieldLabel:"只显示我的",
//				boxLabel:"只显示我的",
//				listeners:{
//					"focus":function(e){
//						//todo 只显示我的设计
//					},
//					"blur":function(e){
//						//todo 显示所有设计
//					}
//				}
//			},"->",{
//				xtype:"textfield",
//				emptyText:"选择广告主",
//				fieldLabel:"选择广告主",
//				name:"srhAdvertiserId",
//			},"-",{
//				xtype:"textfield",
//				emptyText:"选择审核人",
//				fieldLabel:"选择审核人",
//				name:"srhReviewer",
//			},"-",{
//				xtype:"datefield",
//				fieldLabel:"开始时间",
//				value:new Date(),
//				name:"srhGmtStart"
//			},"-",{
//				xtype:"datefield",
//				fieldLabel:"结束时间",
//				value:new Date(),
//				name:"srhGmtPlanEnd"
//			},"-",{
//				xtype:"button",
//				text:"搜索",
//				cls:"x-btn-over",
//				itemCls:"x-btn-over"
//			}
			],
			bbar:new Ext.PagingToolbar({
				pageSize:Context.PAGE_SIZE,
				store:_store,
				displayInfo:true,
				displayMsg:"显示第{0}--{1}跳记录, 共{2}条记录",
				emptyMsg:"没有记录",
				beforePageText:"第",
				afterPageText:"页，共{0}页",
				paramNames:{start:"start",limit:"limit"}
			})
		};
		com.zz91.ads.board.ad.design.Grid.superclass.constructor.call(this,c);
	},
	listRecord:new Ext.data.Record.create([
			{name:"id",mapping:"ad.id"},
			{name:"ad_title",mapping:"ad.adTitle"},
			{name:"applicant",mapping:"ad.applicant"},
			{name:"designer",mapping:"ad.designer"},
			{name:"gmt_created",mapping:"ad.gmtCreated"},
			{name:"gmt_start",mapping:"ad.gmtStart"},
			{name:"gmt_plan_end",mapping:"ad.gmtPlanEnd"},
			{name:"positionName",mapping:"positionName"},
			{name:"width",mapping:"width"},
			{name:"height",mapping:"height"},
			{name:"requestUrl",mapping:"requestUrl"}
	]),
	listUrl:Context.ROOT + "/ad/design/query.htm"
});

com.zz91.ads.board.ad.design.designWin=function(id){
	var form = new com.zz91.ads.board.ad.design.DesignForm({
	});
	
	var win = new Ext.Window({
		id:"designadwin",
		width:600,
		modal:true, 
		autoHeight:true,
		title:"设计广告",
		items:[form]
	});
	
	form.loadRecords(id);
	
	win.show();
}

com.zz91.ads.board.ad.design.DesignForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config ||{};
		Ext.apply(this,config);
		
		var c = {
			labelAlign:"right",
			labelWidth:80,
			frame:true,
			layout:"column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					xtype:"textfield",
					anchor:"99%"
				},
				items:[{
					xtype:"compositefield",
					fieldLabel:"投放目标",
					allowBlank : false,
					itemCls:"required",
					items:[{
						xtype:"textfield",
						id:"adTargetUrl",
						name:"adTargetUrl",
//						emptyText:"http://",
						width:390
					},{
						xtype:"button",
						text:"预览",
						width:80,
						listeners:{
							"click":function(btn){
								var viewUrl = Ext.getCmp("adTargetUrl").getValue();
								if(viewUrl.length>0 && viewUrl!="http://"){
									window.open(viewUrl);
								}else{
									com.zz91.ads.board.utils.Msg("","没有预览地址！");
								}
							}
						}
					}]	
				},{
					xtype:"hidden",
					name:"id"
				},{
					fieldLabel:"广告名称",
					allowBlank : false,
					itemCls:"required",
					name:"adTitle"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					xtype:"datefield",
					anchor:"98%"
				},
				items:[{
					xtype:"datefield",
					id:"gmtStartStr",
					name:"gmtStartStr",
					fieldLabel:"开始时间",
					format : "Y-m-d H:i:s",
					allowBlank : false,
					itemCls:"required"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					xtype:"datefield",
					anchor:"98%"
				},
				items:[{
					xtype:"datefield",
					id:"gmtPlanEndStr",
					name:"gmtPlanEndStr",
					fieldLabel:"结束时间",
					format : "Y-m-d",
					allowBlank:true
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					xtype:"textarea",
					anchor:"99%"
				},
				items:[{
					xtype:"textfield",
					fieldLabel:"广告媒体",
					name:"adContent",
					id:"adContent",
					listeners:{
						"focus":function(field){
							com.zz91.ads.board.UploadConfig.uploadURL=Context.ROOT+"/ad/ad/upload.htm";
		        			
							var win = new com.zz91.ads.board.UploadWin({
		        				title:"上传广告媒体（图片或者flash）"
		        			});
							
							com.zz91.ads.board.UploadConfig.uploadSuccess=function(f,o){
		        				if(o.result.success){
		        					win.close();
		        					Ext.getCmp("adContent").setValue(Context.ROOT_IMAGE+o.result.data);
		        				}
		        			}
		                    win.show();
						}
					}
				},{
					fieldLabel:"广告描述",
					name:"adDescription"
				}]
			}],
			buttons:[{
				text:"仅保存",
				scope:this,
				handler:function(){
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:Context.ROOT+"/ad/design/updateOnly.htm",
							method:"post",
							type:"json",
							success:function(){
								com.zz91.ads.board.utils.Msg("","保存成功");
								Ext.getCmp("designadwin").close();
								var grid=Ext.getCmp("designadgrid");
								grid.getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
							},
							failure:function(){
								com.zz91.ads.board.utils.Msg("","保存失败");
							}
						})
					}
				}
			},{
				text:"设计完了",
				scope:this,
				handler:function(){
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:Context.ROOT+"/ad/design/saveAndDesign.htm",
							method:"post",
							type:"json",
							success:function(){
								com.zz91.ads.board.utils.Msg("","保存成功并已完成设计");
								Ext.getCmp("designadwin").close();
								var grid=Ext.getCmp("designadgrid");
								grid.getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
							},
							failure:function(){
								com.zz91.ads.board.utils.Msg("","保存失败");
							}
						})
					}
				}
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp("designadwin").close();
				}
			}]
		};
		com.zz91.ads.board.ad.design.DesignForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+"/ad/design/saveOnly.htm",
	loadRecords:function(id){
		var _fields=[
			{name:"id",mapping:"id"},
			{name:"adTitle",mapping:"adTitle"},
			{name:"adTargetUrl",mapping:"adTargetUrl"},
			{name:"gmtStart",mapping:"gmtStart"},
			{name:"gmtPlanEnd",mapping:"gmtPlanEnd"},
			{name:"adContent",mapping:"adContent"},
			{name:"adDescription",mapping:"adDescription"}
		];
		
		var _form = this;
		var _store = new Ext.data.JsonStore({
			fields:_fields,
			url:Context.ROOT+"/ad/design/queryAdById.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"dataChanged":function(s){
					var record = s.getAt(0);
					if(record==null){
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					}else{
						_form.getForm().loadRecord(record);
						//时间初始化
						Ext.getCmp("adTargetUrl").setValue(record.get("adTargetUrl"));
						Ext.getCmp("gmtStartStr").setValue(Ext.util.Format.date(new Date(record.get("gmtStart").time), 'Y-m-d'));
						if(record.get("gmtPlanEnd")!=null){
							Ext.getCmp("gmtPlanEndStr").setValue( Ext.util.Format.date(new Date(record.get("gmtPlanEnd").time), 'Y-m-d'));
						}
					}
				}
			}
		})
	}
});

