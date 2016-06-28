Ext.namespace("ast.ast1949.admin.productsPic");

ast.ast1949.admin.productsPic.imageView = Ext.extend(Ext.Panel,{
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
				store:_imgStore,
				tpl:_xtpl,
				multiSelect:true,
				overClass:'x-view-over',
				itemSelector:'div.thumb-wrap',
				emptyText:"没有图片"
			})
			,
			tbar:[
//			      {
//				iconCls:"item-add",
//				text:"添加新图片",
//				ref:"../uploadImageButton",
//				handler:function(btn){
//					caiban.easyadmin.UploadConfig.uploadURL = Context.ROOT+"/product/product!uploadImage.act?product.id="+Ext.get("id").dom.value;
//					var win = new caiban.easyadmin.UploadWin({
//						title:"上传产品图片"
////						closeAction:"hide"
//					});
//					win.show();
//					
//					caiban.easyadmin.UploadConfig.uploadSuccess = function(f,o){
//						// TODO 上传成功后的操作
//						if(o.result.success){
//							//获取返回的文件名和路径
//							var pid=Ext.get("id").dom.value;
//							if(pid>0){
//							//如果是修改操作,重新载入store即可
//								_imgStore.reload();
//							}else{
//							//如果是添加操作,则需要往store里添加一条record
//								var imgnewrecord = new _imgRecord({
//									filename:o.result.data.filename,
//									filepath:o.result.data.filepath,
//									filetype:o.result.data.filetype,
//									remark:o.result.data.remark
//								});
//								
//								_imgStore.add(imgnewrecord);
//							}
//							win.close();
//						}else{
//							Ext.MessageBox.show({
//								title:MESSAGE.title,
//								msg : getMsg(MESSAGE.uploadFailure, [o.result.data]),
//								buttons:Ext.MessageBox.OK,
//								icon:Ext.MessageBox.ERROR
//							});
//						}
//					}
//					
//				}
//			},
//			{
//				iconCls:"item-del",
//				text:"删除选中图片",
//				scope:this,
//				handler:function(btn){
//					var imgview = Ext.getCmp("images-view").getComponent(0);
//					var selectedrecords = imgview.getSelectedRecords()
//					if(selectedrecords.length==0){
//						return ;
//					}
//					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要删除这些图片吗？",function(btn){
//						if(btn!="yes"){
//							return ;
//						}
//						var _ids = new Array();
//						for (var i=0,len = selectedrecords.length;i<len;i++){
//							var _id=selectedrecords[i].get("id");
//							_ids.push(_id);
//						}
//						
//						Ext.Ajax.request({
//							url:Context.ROOT + Context.PATH + "/admin/products/deleteProductImage.htm",
//							params:{"imageArrays":_ids.join(",")},
//							success:function(response,opt){
//								var obj = Ext.decode(response.responseText);
//								if(obj.success){
//									imgview.getStore().reload();
//								}else{
//									Ext.MessageBox.show({
//										title : Context.MSG_TITLE,
//										msg : "发生错误,没有读取图片信息",
//										buttons : Ext.MessageBox.OK,
//										icon : Ext.MessageBox.INFO
//									});
//								}
//							},
//							failure:function(response,opt){
//								Ext.MessageBox.show({
//									title : Context.MSG_TITLE,
//									msg : "发生错误,没有读取图片信息",
//									buttons : Ext.MessageBox.OK,
//									icon : Ext.MessageBox.INFO
//								});
//							}
//						});
//						
//					});
//					
//				}
//			},
			" 注意： Ctrl+左键 可以复选哟！ ",{
				iconCls:"item-true",
				text:"通过选中图片",
				scope:this,
				handler:function(btn){
					var imgview = Ext.getCmp("images-view").getComponent(0);
					var selectedrecords = imgview.getSelectedRecords()
					if(selectedrecords.length==0){
						return ;
					}
					var _ids = new Array();
					for (var i=0,len = selectedrecords.length;i<len;i++){
						var _id=selectedrecords[i].get("id");
						_ids.push(_id);
					}
						
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/admin/products/passPic.htm",
						params:{"idArrayStr":_ids.join(","),"unpassReason":Ext.get("batchReason").dom.value},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								imgview.getStore().reload();
							}else{
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,没有读取图片信息",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title : Context.MSG_TITLE,
								msg : "发生错误,没有读取图片信息",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
						}
					});
				}
			},{
				iconCls:"item-add",
				text:"<span color='red'>置顶</span>选中图片",
				scope:this,
				handler:function(btn){
					var imgview = Ext.getCmp("images-view").getComponent(0);
					var selectedrecords = imgview.getSelectedRecords()
					if(selectedrecords.length==0||selectedrecords.length>1){
						return ;
					}
					var _ids = new Array();
					for (var i=0,len = selectedrecords.length;i<len;i++){
						var _id=selectedrecords[i].get("id");
						_ids.push(_id);
					}
						
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/admin/products/defaultPic.htm",
						params:{"idArrayStr":_ids.join(","),"productId":Ext.getCmp("id").value},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								
								imgview.getStore().reload();
							}else{
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,没有读取图片信息",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title : Context.MSG_TITLE,
								msg : "发生错误,没有读取图片信息",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
						}
					});
				}
			},{
				iconCls:"item-edit",
				text:"退回选中图片",
				scope:this,
				handler:function(btn){
					var imgview = Ext.getCmp("images-view").getComponent(0);
					var selectedrecords = imgview.getSelectedRecords()
					if(selectedrecords.length==0){
						return ;
					}
					var _ids = new Array();
					for (var i=0,len = selectedrecords.length;i<len;i++){
						var _id=selectedrecords[i].get("id");
						_ids.push(_id);
					}
					
					var unpassReason = Ext.get("batchReason").dom.value;
					
					if(unpassReason==null||unpassReason==""||unpassReason=="审核不过原因"){
						alert("请选择不通过原因")
						return ;
					}
					
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/admin/products/refusePic.htm",
						params:{"idArrayStr":_ids.join(","),"unpassReason":unpassReason,"productId":Ext.getCmp("id").value},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								imgview.getStore().reload();
							}else{
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,没有读取图片信息",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title : Context.MSG_TITLE,
								msg : "发生错误,没有读取图片信息",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
						}
					});
				}
			},{
				iconCls:"item-false",
				text:"退回所有图片",
				scope:this,
				handler:function(btn){
					var imgview = Ext.getCmp("images-view").getComponent(0);
//					var selectedrecords = imgview.getSelectedRecords()
//					if(selectedrecords.length==0){
//						return ;
//					}
					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要退回所有图片吗？",function(btn){
						if(btn!="yes"){
							return ;
						}
						
						var unpassReason = Ext.get("batchReason").dom.value;
						
						if(unpassReason==null||unpassReason==""||unpassReason=="审核不过原因"){
							alert("请选择不通过原因")
							return ;
						}
						
						var productId = Ext.getCmp("id").value;
						
						Ext.Ajax.request({
							url:Context.ROOT + Context.PATH + "/admin/products/refuseAllPic.htm",
							params:{"productId":productId,"unpassReason":unpassReason},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									imgview.getStore().reload();
								}else{
									Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "发生错误,没有读取图片信息",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
								}
							},
							failure:function(response,opt){
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,没有读取图片信息",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						});
						
					});
				}
			},"->",{	
				xtype:"combo",
				width : 600,
				emptyText: '审核不过原因',
				triggerAction : "all",
				forceSelection : true,
				displayField : "content",
				valueField : "id",
				id : "batchReason",
				name:"reason",
				allowBlank:true,
				itemCls:"required",
				store:new Ext.data.JsonStore( {
					fields : [ "content", "id" ],
					autoLoad:true,
					url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341003"
				})
			}]
		};
		
		ast.ast1949.admin.productsPic.imageView.superclass.constructor.call(this,c);

//		this.get(0).on("dblclick",function(view,index,node,e){
////			var viewstore=view.getStore();
////			alert(Ext.get(node).child(".thumb img").dom.src)
//			window.open(Ext.get(node).child(".thumb img").dom.src)
//		});
	},
//	porductId:0,
	//xtemplate,图片展现模板
	xtpl:new Ext.XTemplate('<tpl for=".">',
        '<div class="thumb-wrap" id="img-{id}">',
        '<div class="thumb"><img src="'+Context.RESOURCE_URL+'/{picAddress}" title="{name}" /></div>',
        '<tpl if="checkStatus == 0"><span style="color:grey">未审核</span></tpl>',
        '<tpl if="checkStatus == 1"><span style="color:green">通过</span></tpl>',
        '<tpl if="checkStatus == 2"><span style="color:red" title="{unpassReason}">退回</span></tpl>',
        '</div>',
	    '</tpl>',
	    '<div class="x-clear"></div>'
	),
	imageStore:new Ext.data.JsonStore({
	    url: Context.ROOT+Context.PATH+'/admin/products/listProductsPic.htm',
//	    scope:this,
//	    params:{"productId":this.productId},
	    root: 'records',
	    fields: [
	        'id','productId','picAddress','name','checkStatus','unpassReason'
	    ]
	})
//	,
//	imageRecord:Ext.data.Record.create(['id','productId','picAddress','name'])
});

