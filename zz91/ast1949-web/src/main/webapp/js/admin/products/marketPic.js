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
				emptyText:"上传成功"
			})
			,
			tbar:[

			" 注意： Ctrl+左键 可以复选哟！ ",{
				iconCls:"add",
				text:"上传图片",
				id:'fileUrl',
				name:'fileUrl',
				listeners:{
					"click":function(field,e){
						com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/doMarketUpload.htm?marketId="+Ext.getCmp("id").value;
						var win = new com.zz91.sms.gateway.UploadWin({
							title:"上传文件",
						});
						com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
							Ext.Msg.alert(Context.MSG_TITLE,"导入成功");
							win.close();
							var imgview = Ext.getCmp("images-view").getComponent(0);
							imgview.getStore().reload();
						}
						win.show();
					}
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
						url:Context.ROOT + Context.PATH + "/admin/products/defaultMarketPic.htm",
						params:{"idArrayStr":_ids.join(","),"marketId":Ext.getCmp("id").value},
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
						url:Context.ROOT + Context.PATH + "/admin/products/checkMarketPic.htm",
						params:{"idArrayStr":_ids.join(","),"marketId":Ext.getCmp("id").value},
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
					
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/admin/products/delMarketPic.htm",
						params:{"idArrayStr":_ids.join(","),"marketId":Ext.getCmp("id").value},
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
			}]
		};
		
		ast.ast1949.admin.productsPic.imageView.superclass.constructor.call(this,c);


	},
	xtpl:new Ext.XTemplate('<tpl for=".">',
	        '<div class="thumb-wrap" id="img-{id}">',
	        '<div class="thumb"><img src="'+Context.RESOURCE_URL+'/{picAddress}" title="{name}" /></div>',
	        '<tpl if="checkStatus == 0"><span style="color:grey">未审核</span></tpl>',
	        '<tpl if="checkStatus == 1"><span style="color:green">通过</span></tpl>',
	        '<tpl if="checkStatus == 2"><span style="color:red">退回</span></tpl>',
	        '</div>',
		    '</tpl>',
		    '<div class="x-clear"></div>'
		),
	imageStore:new Ext.data.JsonStore({
	    url: Context.ROOT+Context.PATH+'/admin/products/listMarketPic.htm',
	    root: 'records',
	    fields: [
	        'id','productId','picAddress','checkStatus'
	    ]
	})

});

