Ext.namespace("ast.ast1949.yuanliao.yuanLiaoPic");

ast.ast1949.yuanliao.yuanLiaoPic.imageView = Ext.extend(Ext.Panel,{
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
						url:Context.ROOT + Context.PATH + "/yuanliao/passPic.htm",
						params:{"ids":_ids},
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
					var _ids = selectedrecords[0].get("id");
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/yuanliao/defaultPic.htm",
						params:{"id":_ids,"yuanliaoId":Ext.getCmp("id").value},
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
						url:Context.ROOT + Context.PATH + "/yuanliao/refusePic.htm",
						params:{"ids":_ids,"unpassReason":unpassReason},
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
					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要退回所有图片吗？",function(btn){
						if(btn!="yes"){
							return ;
						}
						
						var unpassReason = Ext.get("batchReason").dom.value;
						
						if(unpassReason==null||unpassReason==""||unpassReason=="审核不过原因"){
							alert("请选择不通过原因")
							return ;
						}
						
						var yuanliaoId = Ext.getCmp("id").value;
						
						Ext.Ajax.request({
							url:Context.ROOT + Context.PATH + "/yuanliao/refuseAllPic.htm",
							params:{"yuanliaoId":yuanliaoId,"unpassReason":unpassReason},
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
		
		ast.ast1949.yuanliao.yuanLiaoPic.imageView.superclass.constructor.call(this,c);
	},
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
	    url: Context.ROOT+Context.PATH+'/yuanliao/listYuanLiaoPic.htm',
	    root: 'records',
	    fields: [
	        'id','yuanliaoId','picAddress','name','checkStatus','unpassReason'
	    ]
	})
});

