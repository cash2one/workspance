Ext.namespace("ast.ast1949.admin.periodical");

var _C = new function(){
	this.IMAGE_TAB="image_tabpanel";
	this.PERIODICAL_GRID="periodical_grid";
	this.PERIODICAL_LIST="periodical_list";
	this.PERIODICAL_LIST_PREVIEW="periodical_list_preview";
}

Ext.onReady(function(){

	var panel=new Ext.TabPanel({
		activeTab:0,
		region:"center",
		border:false,
		items:[
			new Ext.Panel({
				title:"子页预览",
				layout:"border",
				items:[
//					new Ext.Panel({
//						title:"这里是表单和上传",
//						height:100,
//						region:"north"
//					}),
					new ast.ast1949.admin.periodical.periodicalDetailsPreview({
						id:_C.IMAGE_TAB,
						region:"center"
//						periodicalId:periodicalId
					})
				]
			}),
			new Ext.Panel({
				title:"编辑子页",
				layout:"border",
				items:[
					new ast.ast1949.admin.periodical.periodicalDetailsGrid({
						id:_C.PERIODICAL_GRID,
						region:"center",
						listeners :{
							"afteredit":function(e){
								if(e.originalValue==e.value){
									return ;
								}
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+"/admin/periodical/updatePeriodicalDetails.htm",
									params:{
										"id":e.record.get("id"),
										"name":e.record.get("name"),
										"orders":e.record.get("orders")
									},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											Ext.getCmp(_C.PERIODICAL_GRID).getStore().reload();
										}else{
											Ext.MessageBox.show({
												title:Context.MSG_TITLE,
												msg : "发生错误,期刊子页没有被更新",
												buttons:Ext.MessageBox.OK,
												icon:Ext.MessageBox.ERROR
											});
										}
									},
									failure:function(response,opt){
										Ext.MessageBox.show({
											title:Context.MSG_TITLE,
												msg : "发生错误,期刊子页没有被更新",
											buttons:Ext.MessageBox.OK,
											icon:Ext.MessageBox.ERROR
										});
									}
								});
							}
						}
					})
				]
			})
		]
	});

	var viewport = new Ext.Viewport({
		layout : "border",
		items:[panel]
	});

});


ast.ast1949.admin.periodical.periodicalDetailsPreview=Ext.extend(Ext.Panel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var _imgStore = this.imageStore;
		var _xtpl = this.xtpl;
//		var _imgRecord = this.imageRecord;
		var c={
			id:'images-view',
			autoScroll:true,
			items:new Ext.DataView({
				id:"image-data-view",
				store:_imgStore,
				tpl:_xtpl,
				multiSelect:true,
				overClass:'x-view-over',
				itemSelector:'div.thumb-wrap',
				emptyText:"没有子页,您可以用上面的按钮上传新的子页"
			}),
			tbar:[{
				iconCls:"item-add",
				text:"上传子页",
//				disabled:true,
				ref:"../uploadImageButton",
				handler:function(btn){
					//TODO 上传新的期刊子页,并且解压出来
					ast.ast1949.UploadConfig.uploadURL=Context.ROOT+Context.PATH+"/admin/upload?model=periodical&filetype=zip";

					var win = new ast.ast1949.UploadWin({
						title:"上传期刊子页(.zip包)"
//						closeAction:"hide"
					});
					win.show();

					ast.ast1949.UploadConfig.uploadSuccess = function(f,o){
						if(o.result.success){
							//TODO 通知后台解压上传的zip文件
							Ext.MessageBox.progress(Context.MSG_TITLE,"","正在解压生成缩略图...");
							Ext.MessageBox.updateProgress(0.5,"压缩包已解压,并生成缩略图.","");
							Ext.Ajax.request({
								url:Context.ROOT+Context.PATH+"/admin/periodical/unzipPeriodicalDetails.htm",
								params:{"periodicalId":periodicalId,"zipfile":o.result.data[0].path+o.result.data[0].uploadedFilename},
								timeout:360000,
								success:function(response,opt){
									Ext.MessageBox.updateProgress(1,"压缩包已解压,并生成缩略图.","")
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										Ext.getCmp("image-data-view").getStore().reload();
										Ext.getCmp(_C.PERIODICAL_GRID).getStore().reload();
										win.close();
									}else{
										Ext.MessageBox.show({
											title:Context.MSG_TITLE,
											msg : "发生错误,文件没有被解压,请重新上传!",
											buttons:Ext.MessageBox.OK,
											icon:Ext.MessageBox.ERROR
										});
									}
									Ext.MessageBox.hide();
								},
								failure:function(response,opt){
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "发生错误,文件没有被解压,请重新上传!",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
									});
								}
							});
//							win.close();
						}else{
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "文件没有被上传",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					}
				}
			},{
				iconCls:"item-del",
				text:"删除选中子页",
				ref:"../deleteImageButton",
				scope:this,
				handler:function(btn){
					var imgview=this.getComponent("image-data-view");
//
					Ext.MessageBox.confirm(Context.MSG_TITLE,"您确定要删除选中的期刊子页吗?",function(btn){
						if(btn!="yes"){
							return ;
						}
						var selectedrecords = imgview.getSelectedRecords()
						var _ids = new Array();
						for (var i=0,len = selectedrecords.length;i<len;i++){
							var _id=selectedrecords[i].get("id");
							_ids.push(_id);
						}
						//TODO delete periodical details
						if(_ids.length>0){
							deletePeriodicalDetails({idArray:_ids.join(","),
								onSuccess:function(){
									imgview.getStore().reload();
								}
							});
						}
					});
				}
			},"->",{
				xtype:"combo",
				id:_C.PERIODICAL_LIST_PREVIEW,
				triggerAction:"all",
				typeAhead: true,
				mode: "remote",
				value:periodicalName,
				forceSelection :true,
				store:new Ext.data.JsonStore({
					autoLoad	: false,
					root		: "records",
					url			: Context.ROOT+Context.PATH+"/admin/periodical/periodicalCombo.htm",
					fields		: ["id","name"]
				}),
				valueField:"id",
				displayField:"name",
				listeners :{
					"change":function(field,newValue,oldValue){
//						var store= Ext.getCmp(_C.PERIODICAL_GRID).getStore();
						_imgStore.baseParams={"id":newValue};
						_imgStore.reload();
					}
				}
			}
//			periodicalName
			]
		};

		ast.ast1949.admin.periodical.periodicalDetailsPreview.superclass.constructor.call(this,c);

//		this.get(0).on("dblclick",function(view,index,node,e){
////			var viewstore=view.getStore();
////			alert(Ext.get(node).child(".thumb img").dom.src)
//			window.open(Ext.get(node).child(".thumb img").dom.src)
//		});
	},
//	periodicalId:0,
	//xtemplate,图片展现模板
	xtpl:new Ext.XTemplate('<tpl for=".">',
        '<div class="thumb-wrap" id="img-{id}">',
        '<div class="thumb"><img src="'+resourceUrl+'/{previewUrl}{periodicalId}s/{imageName}"></div>',
        '<span>{name}</span></div>',
	    '</tpl>',
	    '<div class="x-clear"></div>'
	),
	imageStore:new Ext.data.JsonStore({
	    url: Context.ROOT+Context.PATH+"/admin/periodical/previewDetails.htm",
	    scope:this,
	    baseParams:{"id":periodicalId},
	    root: 'records',
	    autoLoad:true,
	    fields: [
	        'id','periodicalId','name','imageName','previewUrl','orders','pageType'
	    ]
	})
//	,
//	imageRecord:Ext.data.Record.create([{
//		name:"id",mapping:"id"
//	},{
//		name:"name",mapping:"name"
//	},{
//		name:"periodicalId",mapping:"periodicalId"
//	},{
//		name:"imageName",mapping:"imageName"
//	},{
//		name:"previewUrl",mapping:"previewUrl"
//	},{
//		name:"orders",mapping:"orders"
//	},{
//		name:"pageType",mapping:"pageType"
//	}])
});

ast.ast1949.admin.periodical.periodicalDetailsGrid=Ext.extend(Ext.grid.EditorGridPanel,{
	listUrl:Context.ROOT+Context.PATH+"/admin/periodical/listPeriodicalDetails.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var _reader = ["id","name","periodicalId","imageName","previewUrl","orders","pageType"
				,"gmtModified","gmtCreated"];
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:false,
			fields:_reader,
			url: _url,
			baseParams:{"periodicalId":periodicalId},
			autoLoad:true
		});

		var _sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel([
			_sm,{
				id:"edit-id",
				header:"编号",
				dataIndex:"id",
				sortable:true,
				hidden:true
			},{
				id:"edit-name",
				header:"页面名称",
				dataIndex:"name",
				sortable:false,
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				id:"edit-orders",
				header:"排序",
				dataIndex:"orders",
				sortable:false,
				editor:new Ext.form.NumberField({
					allowBlank:false
				})
			},{
				header:"预览",
				dataIndex:"previewUrl",
				sortable:false,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					return '<img src="'+resourceUrl+"/"+record.get("previewUrl")+record.get("periodicalId")+'s/'+record.get("imageName")+'" />';
//					if(record.get("previewUrl")!=null && record.get("previewUrl").length>0){
//					}
//					return "";
				}
			},{
				header:"子页类型",
				dataIndex:"pageType",
				sortable:true,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==0){
						return "封面";
					}else if(value==1){
						return "目录"
					}else if(value==3){
						return "封底"
					}

					return "页面";
				}
			},{
				header:"图片名称",
				dataIndex:"imageName",
				sortable:true
			}
		]);

		var grid = this;

		var c={
			cm:cm,
			store: _store,
			sm:_sm,
			clicksToEdit:1,
			viewConfig:{
				autoFill :true
			},
			tbar: [{
				text:"设成封面",
				handler :function(btn){
//					ast.ast1949.admin.periodical.addPeriodicalWin();
					setPageType(grid,0);
				}
			},{
				text:"设成目录",
				handler :function(btn){
					setPageType(grid,1);
				}
			},{
				text:"设成页面",
				handler:function(btn){
					setPageType(grid,2);
				}
			},{
				text:"设成封底",
				handler:function(b){
					setPageType(grid,3);

				}
			},'-',{
				text:"删除选中页面",
				iconCls : "delete",
				handler :function(){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"您确定要删除选中的期刊子页吗?",function(btn){
						if(btn!="yes"){
							return ;
						}
						var row = grid.getSelections();
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
						if(_ids.length>0){
							deletePeriodicalDetails({
								idArray:_ids.join(","),
								onSuccess:function(){
									grid.getStore().reload();
								}
							});
						}
					});
				}
			},'->',{
				xtype:"combo",
				id:_C.PERIODICAL_LIST,
				triggerAction:"all",
				typeAhead: true,
				mode: "remote",
				value:periodicalName,
				forceSelection :true,
				store:new Ext.data.JsonStore({
					autoLoad	: false,
					root		: "records",
					url			: Context.ROOT+Context.PATH+"/admin/periodical/periodicalCombo.htm",
					fields		: ["id","name"]
				}),
				valueField:"id",
				displayField:"name",
				listeners :{
					"change":function(field,newValue,oldValue){
						var store= Ext.getCmp(_C.PERIODICAL_GRID).getStore();
						store.baseParams={"periodicalId":newValue};
						store.reload();
					}
				}
//				onSelect:function(value){
////					alert(value)
//				}
			}],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};

		ast.ast1949.admin.periodical.periodicalDetailsGrid.superclass.constructor.call(this,c);
	}
});


/**
 * 设置页面类型
 * grid:期刊子页列表
 * pagetype:待设置的页面类型
 * 		0:封面
 * 		1:目录
 * 		2:页面
 * 		3:封底
 * */
function setPageType(grid,pagetype){

	var row = grid.getSelectionModel().getSelections();

	Ext.Ajax.request({
		url:Context.ROOT+Context.PATH+"/admin/periodical/changePeriodicalDetailsPagetype.htm",
		params:{"id":row[0].get("id"),"pagetype":pagetype},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				grid.getStore().reload();
			}else{
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "发生错误,页面类型没有设置",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			}
		},
		failure:function(response,opt){
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "发生错误,页面类型没有设置",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	});
}

/**
 * 删除选中的子页
 * 参数e:
 * idArrays:选中子页的ID字符串用","隔开,例: "1,2,3,4,5"
 * onSuccess:删除成功之后的操作
 * */
function deletePeriodicalDetails(e){
		Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/admin/periodical/deletePeriodicalDetails.htm",
			params:{"idArray":e.idArray},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					new e.onSuccess
				}else{
					Ext.MessageBox.show({
						title:Context.MSG_TITLE,
						msg : "发生错误,期刊子页没有被删除",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			},
			failure:function(response,opt){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "发生错误,期刊子页没有被删除",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			}
		});
}