Ext.namespace("com.zz91.ads.board.ad.check");

var AdCheck =new function(){
	this.WinUploadMaterialId = "WinUploadMaterial";
	this.FormUploadMaterialId = "FormUploadMaterial";
	this.startAdFormId = "startAdForm";
	this.startAdWinId = "startAdWin";
	this.viewAdvertiserFormId ="viewAdvertiserForm";
	this.viewAdvertiserWinId ="viewAdvertiserWin";
	this.viewMaterialFormId ="viewMaterialForm";
	this.viewMaterialWinId ="viewMaterialWin";
	this.checkFormId ="checkForm";
	this.checkWinId ="checkWin";
}

com.zz91.ads.board.ad.check.Grid = Ext.extend(Ext.grid.EditorGridPanel,{
	constructor:function(config){
		this.config = config || {};
		Ext.apply(this, config);
		var _fields=Ext.data.Record.create([
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
			{name:"id",mapping:"ad.id"},
			{name:"positionName",mapping:"positionName"},
			{name:"hasExactAd",mapping:"hasExactAd"},
			{name:"advertiserName",mapping:"advertiserName"},
			{name:"requestUrl",mapping:"requestUrl"},
			{name:"width",mapping:"width"},
			{name:"height",mapping:"height"},
			{name:"a.sequence",mapping:"ad.sequence"},
			{name:"searchExact",mapping:"ad.searchExact"}
		]);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			fields:_fields,
			url:Context.ROOT+"/ad/check/queryAd.htm",
			remoteSort:true,
			autoLoad:true
		});
		
		var grid		= this;
		
		var _sm			= new Ext.grid.CheckboxSelectionModel();
		
		var _cm			= new Ext.grid.ColumnModel([_sm,{
				header:"id",
				hidden:true,
				dataIndex:"id"
			},{
				header:"状态",
				width:60,
				dataIndex:"a.review_status",
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var cst="<img src='"+Context.ROOT+"/themes/icons/review_"+value+".png' title='"+Context.REVIEWSTATUS_CATEGORY[value]+"' />";
					var ost="<img src='"+Context.ROOT+"/themes/icons/online_"+record.get("a.online_status")+".png' title='"+Context.ONLINE_STATUS[record.get("a.online_status")]+"' />";
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
				editor:{
					xtype:"numberfield",
					decimalPrecision:6,
					name:"sequence",
					id:"sequence"
				}
			},{
				header:"广告位",
				dataIndex:"positionName",
				sortable:false,
				width:300,
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
					if(record.get("adContent")!="" && record.get("adContent")!=null){
						return ad+"<br /><a href='"+record.get("adContent")+"' target='_blank' ><img src='"+Context.ROOT+"/themes/boomy/pictures16.png'></a><img src='"+record.get("adContent")+"' width='150' height='50'/>";
					}
					return ad;
				}
			},{
				header:"搜索条件",
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
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
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
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			}
		]);
		
		var c = {
			loadMask:Context.LOADMASK,
			store:_store,
			cm:_cm,
			sm:_sm,
			clicksToEdit:1,
			tbar:[{
				iconCls:"configure16",
				text:"操作",
				menu:[{
					iconCls:"file16",
					text:"审核",
					handler:function(btn){
						var row=grid.getSelectionModel().getSelected();
						if(row!=null){
							com.zz91.ads.board.ad.check.checkWin(row.get("id"));
						}
					}
				},{
					text:"通过",
					iconCls:"accept16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/check/checkAd.htm",
								params:{"id":rows[i].get("id"),"reviewStatus":"Y"},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										_store.reload();
									}else{
										com.zz91.ads.board.utils.Msg("","发生错误，广告没审核！");
									}
								},
								failure:function(response,opt){
									com.zz91.ads.board.utils.Msg("","发生错误，广告没审核！");
								}
							});
						}
					}
				},{
					text:"退回",
					iconCls:"undo16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						for(var i=0;i<rows.length;i++){
							if(rows[i].get("reviewStatus")=="N"){
								continue;
							}
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/check/checkAd.htm",
								params:{"id":rows[i].get("id"),"reviewStatus":"N"},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										_store.reload();
									}else{
										com.zz91.ads.board.utils.Msg("","发生错误，广告没审核！");
									}
								},
								failure:function(response,opt){
									com.zz91.ads.board.utils.Msg("","发生错误，广告没审核！");
								}
							});
						}
					}
				},"-",{
					text:"移动广告",
					iconCls:"foldermove16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						var adArr=new Array();
						for(var i=0;i<rows.length;i++){
							adArr.push(rows[i].get("id"))
						}
						com.zz91.ads.board.ad.check.moveAdWin(adArr,function(){
							_store.reload();
						});
					}
				},"-",{
					text:"提交设计",
					iconCls:"paint16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/check/designAd.htm",
								params:{"id":rows[i].get("id")},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										_store.reload();
									}else{
										com.zz91.ads.board.utils.Msg("","发生错误，广告没提交到设计！");
									}
								},
								failure:function(response,opt){
									com.zz91.ads.board.utils.Msg("","发生错误，广告没提交到设计！");
								}
							});
						}
					}
				},"-",{
					text:"上线",
					iconCls:"play16",
					handler:function(btn){
						var row=grid.getSelectionModel().getSelected();
						if(row!=null){
							com.zz91.ads.board.ad.check.startAdWin(row.get("id"), row.get("positionId"), row.get("hasExactAd"));
						}	
					}
				},{
					text:"下线",
					iconCls:"stop16",
					handler:function(btn){
						//得到选中广告
						var rows=grid.getSelectionModel().getSelections();
						for(var i=0;i<rows.length;i++){
							if(rows[i].get("onlineStatus")=="N"){
								continue;
							}
							com.zz91.ads.board.ad.check.publish(rows[i].get("id"),"N",function(response,opt){
								com.zz91.ads.board.utils.Msg("","广告已下线！");
								Ext.getCmp("checkadgrid").getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
							});
						}
					}
				},"-",{
					text:"删除",
					iconCls:"trashb16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url:Context.ROOT+"/ad/check/deleteAd.htm",
								params:{"id":rows[i].get("id")},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										_store.reload();
									}else{
										com.zz91.ads.board.utils.Msg("","发生错误，广告没删除！");
									}
								},
								failure:function(response,opt){
									com.zz91.ads.board.utils.Msg("","发生错误，广告没删除！");
								}
							});
						}
					}
				}]
			},{
				text:"缓存管理",
				handler:function(btn){
					window.open("http://gg.zz91.com/cacheManager");
				}
			},{
				text:"日志跟踪",
				handler:function(btn){
					window.open("http://gg.zz91.com/tracking");
				}
			}
//			      {
//				text:"操作",
//				iconCls:"app16",
//				menu:[{
//					cls:"paint16",
//					text:"添加素材",
//					handler:function(btn){
//						var row=grid.getSelectionModel().getSelected();
//						
//						var form = new com.zz91.ads.board.ad.material.AdMaterialEditForm({
//							aid:row.get("id"),
//							height:150,
//							onSaveSuccess:function(_f,_a){
//								this.ownerCt.close();
//							}
//						});
//						var win = new Ext.Window({
//							id:AdCheck.WinUploadMaterialId,
//							title:"添加素材",
//							width:560,
//							autoHeight:true,
//							modal:true,
//							items:[form]
//						});
//						win.show();
//					}
//				},{
//					text:"广告上线",
//					handler:function(btn){
//						com.zz91.ads.board.ad.check.startAdWin();
//					}
//				},{
//					text:"广告下线",
//					handler:function(btn){
//						//todo 广告下线
//					}
//				}]
//			},{
//				text:"查看",
//				iconCls:"filesearch16",
//				menu:[
////				,{
////					text:"查看广告主",
////					handler:function(btn){
////						com.zz91.ads.board.ad.check.viewAdvertiserWin();
////					}
////				}
//				]
//			},{
//				text:"审核",
//				iconCls:"userid16",
//				menu:[{
//					cls:"",
//					text:"进行审核",
//					handler:function(btn){
//						var sm = grid.getSelectionModel();
//						var row = sm.getSelections();
//						if(row.length>1){
//							com.zz91.ads.board.utils.Msg("","最多只能选择一条记录！");
//						}else{
//							var _id = row[0].get["id"];
// 							com.zz91.ads.board.ad.check.checkWin(_id);
//						}
//					}
//				},{
//					cls:"accept16",
//					text:"直接通过",
//					handler:function(btn){
//						//todo 直接通过
//					}
//				},{
//					cls:"",
//					text:"直接退回",
//					handler:function(){
//						//todo 直接退回
//					}
//				}]
//			}
			,"->","搜索：",{
				xtype:"textfield",
				id:"search-email",
				width:120,
				emptyText:"搜索广告主email",
				listeners:{
					//失去焦点
					"blur":function(field){
						var val = field.getValue();
						var B	= _store.baseParams;
						B	= B ||{};
						if(val!=""){
							B["email"]= val;
						}else{
							B["email"]=null;
						}
						_store.baseParams = B;
						_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
					}
				}
			}," ",{
				xtype:"textfield",
				id:"search-title",
				width:160,
				emptyText:"搜索广告标题",
				listeners:{
					//失去焦点
					"blur":function(c){
						var val = Ext.get("search-title").dom.value;
						var B	= _store.baseParams;
						B	= B ||{};
						if(val!=""){
							B["adTitle"]= val;
						}else{
							B["adTitle"]=null;
						}
						_store.baseParams = B;
						_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
					}
				}
			},"-",{
				xtype:"checkbox",
				boxLabel:"仅未审核",
				handler:function(btn){
					var B=_store.baseParams||{};
					if(btn.getValue()){
						B["reviewStatus"]="U";
					}else{
						B["reviewStatus"]=null;
					}
					_store.baseParam=B;
					_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
				}
			},{
				xtype:"checkbox",
				boxLabel:"仅未上线",
				handler:function(btn){
					var B=_store.baseParams||{};
					if(btn.getValue()){
						B["onlineStatus"]="N";
					}else{
						B["onlineStatus"]=null;
					}
					_store.baseParam=B;
					_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
				}
			}],
			bbar:new Ext.PagingToolbar({
				pageSize:Context.PAGE_SIZE,
				store:_store,
				displayInfo:true,
				displayMsg:"显示第{0}--{1}跳记录, 共{2}条记录",
				emptyMsg:"没有记录",
				beforePageText:"第",
				afterPageText:"页，共{0}页",
				pramaNames:{start:"start",limit:"limit"}
			}),
			listeners:{
				"afteredit":function(e){
					e.grid.updateSequence(e.record.get("a.sequence"),e.record.get("id"));
				}
			}
		};
		com.zz91.ads.board.ad.check.Grid.superclass.constructor.call(this, c);
	},
	updateSequence:function(sequence, id){
		var grid=this;
		Ext.Ajax.request({
			url:Context.ROOT+"/ad/check/updateSequence.htm",
			params:{"sequence":sequence,"id":id},
			success:function(response,opt){
//				var obj = Ext.decode(response.responseText);
				grid.getStore().reload();
			},
			failure:function(response,opt){
			}
		});
	}
});

com.zz91.ads.board.ad.check.moveAdWin=function(adArr, callback){
	var tree = new com.zz91.ads.board.ad.position.TreePanel({
		height:400,
		autoScroll:true,
		layout:"fit",
		region:"center",
		contextmenu:null
	});
	
	var win = new Ext.Window({
		title:"选择要移动到的位置",
		modal:true,
		width:300,
		items:[tree]
	});
	win.show();
	
	tree.on('dblclick',function(node,e){
		//node.attributes["data"];
		var pid=node.attributes["data"];
		var success=0;
		var failure=0;
		for(var i=0;i<adArr.length;i++){
			Ext.Ajax.request({
		        url:Context.ROOT+"/ad/check/moveAd.htm",
		        params:{"id":adArr[i],"positionId":pid},
		        success:function(response,opt){
		            var obj = Ext.decode(response.responseText);
		            if(obj.success){
		            	success++;
		            	
		            }else{
		            	failure++;
		            }
		            if((success+failure) == adArr.length){
		            	com.zz91.ads.board.utils.Msg(Context.MSG_TITLE,"共移动广告"+adArr.length+"个，其中成功移动"+success+"个广告，失败"+failure+"个。");
		            	win.close();
		            	callback();
		            }
		        },
		        failure:function(response,opt){
					failure++;
		        }
		    });
		}
	});
}

//*
//	提交素材表单
//	param com.zz91.ads.board.ad.check.subMaterialFrom
//*/
//com.zz91.ads.board.ad.check.subMaterialForm = Ext.extend(Ext.form.FormPanel,{
//	constructor:function(config){		
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var _form= this;
//		var c = {
//			labelAlign:"right",
//			labelWidth:80,
//			layout:"form",
//			frame:true,
//			defaults:{
//				xtype:"textfield",
//				anchor:"99%"
//			},
//			items:[{
//				xtype:"hidden",
//				name:"id",
//				id:"id"
//			},{
//				fieldLabel:"素材名称",
//				name:"materialName",
//				allowBlank:false,
//				itemsCls:"required"
//			},{
//				xtype:"textarea",
//				fieldLabel:"素材描述",
//				name:"materialDiscript"
//			},{
//				name:"materialPath",
//				fieldLabel:"上传素材",
//				listeners:{
//					"focus":function(c){
//						com.zz91.ads.board.UploadConfig.uploadURL = Context.ROOT + "/ad/check/upload.htm";
//						var win = new com.zz91.ads.board.UploadWin({
//							title:"上传素材"
//						});
//						com.zz91.ads.board.UploadConfig.uploadSuccess = function(f,o){
//							if(o.result.success){
//								win.close();
//								Ext.get("materialPath").setValue(o.result.data); 
//							}
//						};
//						win.show();
//					}
//				}
//			}],
//			buttons:[{
//				text:"提交",
//				handler:new function(){
//					var saveURL = _form.saveURL;
//					if(_form.getForm().isvalidate()){
//						this.getForm().submit({
//							url:saveURL,
//							method:"post",
//							type:"json",
//							success:function(_form,_action){
//								com.zz91.ads.board.utils.Msg("","保存成功！");
//							},
//							failure:function(_form,_action){
//								com.zz91.ads.board.utils.Msg("","保存失败！");
//							}
//						});
//					}
//				},
//				scope:this
//			},{
//				text:"关闭",
//				handler:function(){
//					Ext.getCmp(AdCheck.subMaterialWinId).close();
//				}
//			}]
//		};
//		com.zz91.ads.board.ad.check.subMaterialForm.superclass.constructor.call(this,c);
//	},
//	loadRecords:function(id){
//		var _field = [
//			{name:	"id",mapping:"id"}
//		];
//		
//		var _form = this;
//		var _store= new Ext.data.JsonStore({
//			root:	"records",
//			fields:	_field,
//			url:	Context.ROOT + "/ad/check/queryById.htm",
//			baseParams:{"id":id},
//			autoLoad:true,
//			listeners:{
//				"datachanged":function(){
//					var record = s.getAt(0);
//					if (record == null) {
//						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
//					} else {
//						form.getForm().loadRecord(record);
//					}
//				}
//			}
//		}); 
//	},
//	saveUrl:Context.ROOT+"/ad/check/add.htm"
//});

///*
//	提交素材弹出窗口
//	zz91.ads.board.ad.check.subMaterialWin
//*/
//com.zz91.ads.board.ad.check.subMaterialWin = function(id){
//	var form = new com.zz91.ads.board.ad.check.subMaterialForm({
//		id:AdCheck.subMaterialFormId,
//		saveURL:Context.ROOT + "/ad/check/add.htm",
//		height:200
//	});
//	var win = new Ext.Window({
//		id:AdCheck.subMaterialWinId,
//		title:"提交素材",
//		width:480,
//		autoHeight:true,
//		modal:true, 
//		//maximizable:true,
//		items:[form]
//	});
//	win.show();
//}

/*
	广告上线编辑表单
	zz91.ads.board.ad.check.statAdForm();
*/

com.zz91.ads.board.ad.check.startAdForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this, config);
		
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
//							emptyText:"http://",
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
					format : "Y-m-d",
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
					listeners:{
						"focus":function(field){
							if(field.getValue().length>0){
								window.open(field.getValue());
							}
						}
					}
				},{
					fieldLabel:"广告描述",
					name:"adDescription"
				},{
					fieldLabel:"代码",
					id:"onlineCode"
				}]
			}],
			buttons:[{
				text:		"上线",
				handler:	this.save,
				scrop:		this
			},{
				text:		"取消",
				handler: function(){
					Ext.getCmp(AdCheck.startAdWinId).close();
				}
			}]
		};
		com.zz91.ads.board.ad.check.startAdForm.superclass.constructor.call(this,c);
	},
	loadRecords:function(id){
		var _fields=[
 			{name:"id",mapping:"id"},
 			{name:"adTitle",mapping:"adTitle"},
 			{name:"adTargetUrl",mapping:"adTargetUrl"},
 			{name:"adContent",mapping:"adContent"},
 			{name:"gmtStart",mapping:"gmtStart"},
 			{name:"gmtPlanEnd",mapping:"gmtPlanEnd"},
 			{name:"adContent",mapping:"adContent"},
 			{name:"adDescription",mapping:"adDescription"}
 		];
 		
 		var _form = this;
 		var _store = new Ext.data.JsonStore({
 			fields:_fields,
 			url:Context.ROOT+"/ad/check/queryAdById.htm",
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
	},
	initOnlineCode:function(aid,pid, hasexactid){
		Ext.Ajax.request({
			url:Context.ROOT+"/ad/check/buildOnlineCode.htm",
			params:{"adId":aid,"pid":pid, "hasexactid":hasexactid},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				Ext.getCmp("onlineCode").setValue(obj.data);
			},
			failure:function(response,opt){
			}
		});
	},
	save:function(btn){
		
	}
});

com.zz91.ads.board.ad.check.publish=function(id, status, fn){
	Ext.Ajax.request({
		url:Context.ROOT+"/ad/check/publishAd.htm",
		params:{"id":id,"onlineStatus":status},
		success:fn,
		failure:function(response,opt){
		}
	});
}

/*
	广告上线弹出窗
	param com.zz91.ads.board.ad.check.statAdWin
*/

com.zz91.ads.board.ad.check.startAdWin = function(id,pid,hasexactid){
	var form =new com.zz91.ads.board.ad.check.startAdForm({
		id:AdCheck.startAdFormId,
		height:300,
		save:function(btn){
			com.zz91.ads.board.ad.check.publish(id,"Y",function(response,opt){
				com.zz91.ads.board.utils.Msg("","广告已上线！");
				Ext.getCmp("checkadgrid").getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
				Ext.getCmp(AdCheck.startAdWinId).close();
			});
		}
	});
	
	form.loadRecords(id);
	form.initOnlineCode(id, pid, hasexactid);
	
	var win =new Ext.Window({
		title:		"广告上线",
		modal:		true,
		width:		600,
		autoHeight:	true,
		items:[form],
		id:AdCheck.startAdWinId
	});
	win.show();
};

/*
	查看广告主信息
	aderId 		广告主ID
	aderName	广告主名称
	aderContact	联系地址
	aderPhone	联系电话
	aderEmail	邮箱
	aderRemark	备注
*/

//com.zz91.ads.board.ad.check.viewAdvertiserForm = Ext.extend(Ext.form.FormPanel,{
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var c = {
//			labelAlign:"right",
//			labelWidth:50,
//			layout:"column",
//			bodyStyle:"padding:12px 0 0",
//			frame:true,
//			items:[{
//				columnWidth:0.5,
//				layout:"form",
//				defaults:{
//					anchor:"96%",
//					xtype:"textfield"
//				},
//				items:[{
//					fieldLabel:"广告主",
//					name:"aderName"
//				},{
//					fieldLabel:"联系人",
//					name:"aderContact"
//				}]
//			},{
//				columnWidth:0.5,
//				layout:"form",
//				defaults:{
//					anchor:"96%",
//					xtype:"textfield"
//				},
//				items:[{
//					fieldLabel:"联系电话",
//					name:"aderPhone"
//				},{
//					fieldLabel:"E-mail",
//					name:"aderEmail"
//				}]
//			},{
//				columnWidth:1,
//				layout:"form",
//				defaults:{
//					xtype:"textarea",
//					anchor:"98%",
//				},
//				items:[{
//					xtype:"textarea",
//					fieldLabel:"备注",
//					name:"aderRemark"
//				}]
//			}],
//			buttons:[{
//				text:"关闭",
//				handler:function(){
//					Ext.getCmp(AdCheck.viewAdvertiserWinId).close();
//				}
//			}]
//		};
//		com.zz91.ads.board.ad.check.viewAdvertiserForm.superclass.constructor.call(this,c);
//	},
//	loadRecords:function(id){
//		var _field = [
//			{name:"id",mapping:"id"},
//			{name:"aderName",mapping:"aderName"},
//			{name:"aderContact",mapping:"aderContact"},
//			{name:"aderPhone",mapping:"aderPhone"},
//			{name:"aderEmail",mapping:"aderEmail"},
//			{name:"aderRemark",mapping:"aderRemark"}
//		];
//		
//		var _form = this;
//		var _store = new Ext.data.JsonStore({
//			url:Context.ROOT+"/ad/check/queryById.htm",
//			root:"records",
//			fields:_field,
//			autoLoad:true,
//			baseParams:{"id":id},
//			listeners:{
//				"dataChanged":function(s){
//					var record = s.getAt(0);
//					if(record == null){
//						Ext.MassageBox.alert(Context.MSG_TITLE,"数据加载失败")
//					}else{
//						form.getForm().loadRecord(record);
//					}
//				}
//			}
//		});
//	}
//});

/*
	查看广告主信息窗口
	param	com.zz91.ads.board.ad.check.viewAdvertiserWin
*/

//com.zz91.ads.board.ad.check.viewAdvertiserWin = function(id){
//	var form = new com.zz91.ads.board.ad.check.viewAdvertiserForm({
//		id:AdCheck.viewAdvertiserFormId,
//		//region:"center",
//		height:180
//	});
//	var win = new Ext.Window({
//		id:AdCheck.viewAdvertiserWinId,
//		//layout:"form",
//		width:480,
//		autoHeight:true,
//		modal:true,
//		title:"查看广告主",
//		items:[form]
//	});
//	win.show();
//};

///*
//	查看素材
//	com.zz91.ads.board.ad.check.viewMaterialForm
//*/
//
//com.zz91.ads.board.ad.check.viewMaterialForm = Ext.extend(Ext.form.FormPanel,{
//	constructor:function(config){
//		config=config||{};
//		Ext.apply(this,config);
//		
//		var c = {
//			labelAlign:"right",
//			labelWidth:60,
//			layout:"column",
//			bodyStyle:"padding:12px 0 0",
//			frame:true,
//			items:[{
//				columnWidth:0.5,
//				layout:"form",
//				defaults:{
//					xtype:"textfield",
//					anchor:"96%",
//					labelSeparetor:":"
//				},
//				items:[{
//					fieldLabel:"素材名称",
//					name:"matarialName"
//				},{
//					fieldLabel:"素材类型",
//					name:"materialType"
//				}]
//			},{
//				columnWidth:0.5,
//				layout:"form",
//				defaults:{
//					anchor:"96%",
//					xtype:"textfield",
//					disabled:false
//				},
//				items:[{
//					fieldLabel:"关联广告",
//					name:"adId"
//				},{
//					fieldLabel:"素材路径",
//					name:"materialPath"
//				}]
//			},{
//				columnWidth:1,
//				layout:"form",
//				defaults: {
//					anchor:"98%",
//					xtype:"textarea",
//					disabled:false
//				},
//				items:[{
//					fieldLabel:"素材描述",
//					name:"materialRemark"
//				}]
//			}],
//			buttons:[{
//				text:"关闭",
//				handler:function(){
//					Ext.getCmp(AdCheck.viewMaterialWinId).close();
//				}
//			}]
//		};
//		com.zz91.ads.board.ad.check.viewMaterialForm.superclass.constructor.call(this,c);
//	},
//	loadRecords:function(id){
//		var _field = [
//			{name:	"id",mapping:"id"},
//			{name:	"adId",mapping:"adId"},
//			{name:	"materialType",mapping:"materialType"},
//			{name:	"materialName",mapping:"materialName"},
//			{name:	"materialRemark",mapping:"materialRemark"},
//			{name:	"materialPath",mapping:"materialPath"}
//			];
//		
//		var _form = this;
//		var _store= new Ext.data.JsonStore({
//			root:	"records",
//			fields:	_field,
//			url:	Context.ROOT + "/ad/check/queryById.htm?st="+timeStamp(),
//			baseParams:{"id":id},
//			autoLoad:true,
//			listeners:{
//				"dataChanged":function(){
//					var record = s.getAt(0);
//					if (record == null) {
//						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
//					} else {
//						form.getForm().loadRecord(record);
//					}
//				}
//			}
//		}); 
//	}
//});


/*
 * 查看素材信息 弹出窗口
 */

com.zz91.ads.board.ad.check.viewMaterialWin = function(id){
	
	var grid=new com.zz91.ads.board.ad.material.AdMaterialGrid({
		aid:id,
		height:200
	});
	
	var win = new Ext.Window({
		modal:true,
		autoHeight:true,
		width:480,
		items:[grid],
		title:"查看素材信息",
		buttons:[{
			text:"关闭",
			handler:function(btn){
				win.close();
			}
		}]
	});
	
	win.show();
};

/*
* 修改审核
*/


com.zz91.ads.board.ad.check.checkForm = Ext.extend(Ext.form.FormPanel,{
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
				},{
					xtype:"numberfield",
					id:"sequence",
					name:"sequence",
//					allowDecimals:false,
					decimalPrecision:6,
					fieldLabel:"排序"
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
				},{
					fieldLabel:"备注",
					name:"remark"
				}]
			}],
			buttons:[{
				text:"仅修改",
				scope:this,
				handler:function(){
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:Context.ROOT+"/ad/check/updateOnly.htm",
							method:"post",
							type:"json",
							success:function(){
								com.zz91.ads.board.utils.Msg("","保存成功");
								Ext.getCmp(AdCheck.checkWinId).close();
								var grid=Ext.getCmp("checkadgrid");
								grid.getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
							},
							failure:function(){
								com.zz91.ads.board.utils.Msg("","保存失败");
							}
						})
					}
				}
			},{
				text:"保存并通过",
				scope:this,
				handler:function(){
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:Context.ROOT+"/ad/check/updateAndCheck.htm",
							method:"post",
							type:"json",
							success:function(){
								com.zz91.ads.board.utils.Msg("","保存成功并已审核");
								Ext.getCmp(AdCheck.checkWinId).close();
								var grid=Ext.getCmp("checkadgrid");
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
					Ext.getCmp(AdCheck.checkWinId).close();
				}
			}]
		};
		com.zz91.ads.board.ad.check.checkForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+"/ad/check/updateAd.htm",
	loadRecords:function(id){
		var _fields=[
			{name:"id",mapping:"id"},
			{name:"adTitle",mapping:"adTitle"},
			{name:"adTargetUrl",mapping:"adTargetUrl"},
			{name:"gmtStart",mapping:"gmtStart"},
			{name:"gmtPlanEnd",mapping:"gmtPlanEnd"},
			{name:"adContent",mapping:"adContent"},
			{name:"adDescription",mapping:"adDescription"},
			{name:"remark",mapping:"remark"},
			{name:"sequence",mapping:"sequence"},
		];
		
		var _form = this;
		var _store = new Ext.data.JsonStore({
			fields:_fields,
			url:Context.ROOT+"/ad/check/queryAdById.htm",
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


/*
	审核修改弹出窗口
	param com.zz91.ads.board.ad.check.checkWin()
*/

com.zz91.ads.board.ad.check.checkWin = function(_id){
	var form = new com.zz91.ads.board.ad.check.checkForm({
	});
	
	var win = new Ext.Window({
		id:AdCheck.checkWinId,
		width:600,
		modal:true, 
		autoHeight:true,
		title:"修改/审核",
		items:[form]
	});
	
	form.loadRecords(_id);
	
	win.show();
}


/*
	广告树目录
	param  com.zz91.ads.board.ad.check.treePanel
*/

com.zz91.ads.board.ad.check.treePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+"/ad/check/child.htm",
			listeners:{
				beforeLoad:function(treeLoad,node){
					this.baseParams["id"] = node.attributes["data"];
				}
			}
		});
		
		var c = {
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				nodeType:"async",
				text:"所有分类",
				data:0
			},
			loader:treeLoad,
			tbar:[{
				text:"全部展开",
				scrop:this,
				handler:function(){
					this.expendAll();
				}
			},{
				text:"全部收起",
				scrop:this,
				handler:function(){
					this.collapseAll();
				}
			}],
			contextMenu:this.contextmenu,
			listeners:{
				contextmenu:function(node,e){
					node.select();
					var c = node.getOwnerTree().contextMenu;
					c.contextNode = node;
					c.show(e.getXY());
				}
			}
		};
		
		com.zz91.ads.board.ad.check.treePanel.superclass.constructor.call(this, c)
	}
});
