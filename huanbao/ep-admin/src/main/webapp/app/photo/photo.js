Ext.namespace("com.zz91.ep.tradePic");

com.zz91.ep.tradePic.productPhoto = Ext.extend(Ext.grid.GridPanel,{
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		
		var _store = new Ext.data.JsonStore({
			url : Context.ROOT+'/trade/tradesupply/listPhoto.htm?targetType=supply&targetId='+Ext.getDom('sid').value,
			autoLoad : true,
			remoteSort : false,
			fields : [ "id", "targetId", "isDel","title","path", "gmtCreated","checkStatus" ]
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm = new Ext.grid.ColumnModel([_sm,
				{
					header : "编号",
					width : 80,
					dataIndex : "id",
					sortable : true,
					hidden : true
				},{
					header : "TargetID",
					width : 80,
					dataIndex : "targetId",
					sortable : true,
					hidden : true
				}
				//,{
                //    header : "删除状态",
                //    dataIndex : "isDel",
               //     width : 100,
                //    sortable : true,
               //     renderer:function(value, metadata, record, rowIndex, colIndex, store){
    	       //         var returnValue = value;
    	        //        if(value==0){
    	       //             returnValue="未删除";
    	         //       }
    	        //        if(value==1){
    	         //           returnValue="已删除";
    	         //      }
    	        //        return returnValue;
    	       // 
    	        //    }
              //  }
              
              	,{
                    header : "审核状态",
                    dataIndex : "checkStatus",
                    width : 100,
                    sortable : true,
                    renderer:function(value, metadata, record, rowIndex, colIndex, store){
    	                var returnValue = value;
    	                if(value==0){
    	                    returnValue="未审核";
    	                }
    	                if(value==1){
    	                    returnValue="通过";
    	               }
    	                if(value==2){
    	                    returnValue="退回";
    	               }
    	                return returnValue;
    	            }
                }
               ,
                {
					header : "预览图",
					dataIndex : "title",
					width : 350,
					sortable : true,
					renderer : function(v, m, record,ridx, cidx, store, view) {
						if (record.get("path").length > 0) {
//							 v = "<img src="+Context.RESOURCE+record.get("path")+" width='100' height='100' /><br />";
							 
							 v = "<a href='"+Context.RESOURCE+record.get("path")+"' target='_blank'><img src="+Context.PIC+Context.RESOURCE+record.get("path")+" width='100' height='100' /></a><br />";
						}
						return v;
					}
					
				},{
					header : "图片路径",
					width : 400,
					dataIndex : "path",
					sortable : true
				},{
					header : "创建时间",
					width : 200,
					dataIndex : "gmtCreated",
					sortable : true,
					renderer : function(v) {
					 	return Ext.util.Format.date(new Date(v.time), 'Y-m-d H:i:s');
					}
				} 
		]);

		var grid = this;
		var c = {
			loadMask : Context.LOADMASK,
			store : _store,
			sm : _sm,
			cm : _cm,
			tbar : new Ext.Toolbar({
				items : [ {
					text : "设置为封面图片",
					iconCls : "refresh16",
					handler : function(btn) {
						grid.installPhoto(_store);
					}
				},{
					text : "上传图片",
					iconCls : "add16",
					handler : function(btn) {
						grid.insertImg(_store);
					}
				}
				
				//,{
				//	iconCls : "delete16",
				//	text : "删除图片",
				//	handler : function(btn) {
				//		grid.deleteRecord(_store);
				//	}
				//},"-",{
                //    text : "取消删除",
               //     handler : function(btn) {
               //         grid.cancelDeleteRecord(_store);
               //     }
                //}
                
                ,{
				iconCls:"accept16",
				text:"通过选中图片",
				scope:this,
				handler:function(btn){
				 this.getSelectionModel().getSelections();
				
				var selectedrecords = this.getSelectionModel().getSelections();
				if(selectedrecords.length==0){
						return ;
					}
					var _ids = new Array();
					for (var i=0,len = selectedrecords.length;i<len;i++){
						var _id=selectedrecords[i].get("id");
						_ids.push(_id);
					}
						
					Ext.Ajax.request({
						url:Context.ROOT +  "/trade/tradesupply/passPic.htm", 
						params:{"idArrayStr":_ids.join(","),"unpassReason":Ext.get("batchReason").dom.value},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								_store.reload(); 
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
				iconCls:"edit16",
				text:"退回选中图片",
				scope:this,
				handler:function(btn){
					var selectedrecords = this.getSelectionModel().getSelections();
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
						url:Context.ROOT +  "/trade/tradesupply/refusePic.htm",
						params:{"idArrayStr":_ids.join(","),"unpassReason":unpassReason},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								_store.reload(); 
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
			},"-",{
				iconCls:"backward16",
				text:"退回所有图片",
				scope:this,
				handler:function(btn){

					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要退回所有图片吗？",function(btn){
						if(btn!="yes"){
							return ;
						}
						
						var unpassReason = Ext.get("batchReason").dom.value;
						
						if(unpassReason==null||unpassReason==""||unpassReason=="审核不过原因"){
							alert("请选择不通过原因")
							return ;
						}
						
						var targetId = Ext.getDom('sid').value;
						Ext.Ajax.request({
							url:Context.ROOT +  "/trade/tradesupply/refuseAllPic.htm",
							params:{"targetId":targetId,"unpassReason":unpassReason},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									_store.reload(); 
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
					url : Context.ROOT +  "/trade/descriptiontemplate/queryList.htm?templateCode=10341003"
				})
			}
                
                ]
			})
		};

		com.zz91.ep.tradePic.productPhoto.superclass.constructor.call(this, c);
	},
	loadPhotoRecord : function(id) {
		if (id > 0) {
			this.getStore().reload({
				params : {"id" : id}
			});
		}
	},
	deleteRecord : function(store) {
		var rows = this.getSelectionModel().getSelections();
		for ( var i = 0; i < rows.length; i++) {
			Ext.Ajax.request({
				url : Context.ROOT+ "/trade/tradesupply/deleteTradePic.htm",
				params : {
					"id" : rows[i].get("id")
//					"path" : rows[i].get("path")
				},
				success : function(response, opt) {
					var obj = Ext.decode(response.responseText);
					if (obj.success) {
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
					} else {
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
					}
				},
				failure : function(response, opt) {
					Ext.MessageBox.show({
						title : MESSAGE.title,
						msg : MESSAGE.submitFailure,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	},
	cancelDeleteRecord : function(store) {
        var rows = this.getSelectionModel().getSelections();
        for ( var i = 0; i < rows.length; i++) {
            Ext.Ajax.request({
                url : Context.ROOT+ "/trade/tradesupply/cancelDeleteTradePic.htm",
                params : {
                    "id" : rows[i].get("id")
//                  "path" : rows[i].get("path")
                },
                success : function(response, opt) {
                    var obj = Ext.decode(response.responseText);
                    if (obj.success) {
                        store.reload();
                        com.zz91.utils.Msg("",MESSAGE.operateSuccess);
                    } else {
                        com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
                    }
                },
                failure : function(response, opt) {
                    Ext.MessageBox.show({
                        title : MESSAGE.title,
                        msg : MESSAGE.submitFailure,
                        buttons : Ext.MessageBox.OK,
                        icon : Ext.MessageBox.ERROR
                    });
                }
            });
        }
    },
	installPhoto : function(store) {
		var sr = this.getSelectionModel().getSelections();
		if(sr.length!=1){
			com.zz91.utils.Msg("操作提示","请选择一张图片");
			return ;
		}
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定要设置该图片为封面图片吗？",function(btn){
			if(btn!="yes"){
				return ;
			}
			var path=sr[0].get("path");
			var sId=sr[0].get("targetId");
			Ext.Ajax.request({
				url:Context.ROOT +"/trade/tradesupply/setFrontPic.htm",
				params:{"path":path,"sId":sId},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("操作提示","设置成功!");
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
	},
	insertImg : function(store){
		com.zz91.ep.photo.UploadConfig.uploadURL=Context.ROOT+"/trade/tradesupply/uploadFile.htm?id="+Ext.get("sid").dom.value;
		var win = new com.zz91.ep.photo.UploadWin({
			title:"允许格式(jpg,jpeg,gif,png)"
		});
		com.zz91.ep.photo.UploadConfig.uploadSuccess=function(f,o){
			if(o.result.success){
				win.close();
				com.zz91.utils.Msg(MESSAGE.title, "文件已上传!");
				store.reload();
			}else{
				com.zz91.utils.Msg(MESSAGE.title, o.result.data);
			}
		}
		win.show();				
	}
});
