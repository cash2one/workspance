Ext.namespace("com.zz91.ep.news");

var NEWS=new function(){
	this.COLLECTION_GRID="collectiongrid";
	this.FILE_GRID="filegrid";
}

com.zz91.ep.news.GRIDFIELD=[
	{name:"id",mapping:"news.id"},
	{name:"pause_status",mapping:"news.pauseStatus"},
	{name:"category_code",mapping:"news.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"title",mapping:"news.title"},
	// {name:"recommend_type",mapping:"recommend.type"},
	{name:"admin_name",mapping:"news.adminName"},
	{name:"tags",mapping:"news.tags"},
	{name:"gmt_publish",mapping:"news.gmtPublish"},
	{name:"news_source_url",mapping:"news.newsSourceUrl"},
	{name:"news_source",mapping:"news.newsSource"},
	{name:"rid",mapping:"rid"},
	{name:"view_count",mapping:"news.viewCount"}
];

com.zz91.ep.news.FORMFIELD=[
	{name:"id",mapping:"news.id"},
	{name:"pauseStatus",mapping:"news.pauseStatus"},
	{name:"categoryCode",mapping:"news.categoryCode"},
	{name:"title",mapping:"news.title"},
	{name:"titleIndex",mapping:"news.titleIndex"},
	{name:"adminAccount",mapping:"news.adminAccount"},
	{name:"adminName",mapping:"news.adminName"},
	{name:"tags",mapping:"news.tags"},
	{name:"viewCount",mapping:"news.viewCount"},
	{name:"gmtPublish",mapping:"news.gmtPublish"},
	{name:"gmtCreated",mapping:"news.gmtCreated"},
	{name:"newsSourceUrl",mapping:"news.newsSourceUrl"},
	{name:"newsSource",mapping:"news.newsSource"},
	{name:"description",mapping:"news.description"},
	{name:"details",mapping:"news.details"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"videoUrl",mapping:"videoUrl"},
	{name:"photoCover",mapping:"photoCover"}
];

com.zz91.ep.news.FILTER_RECOMMEND=[];

com.zz91.ep.news.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/news/news/queryNews.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.ep.news.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,
			{
				header:"rid",dataIndex:"rid",sortable:true,hidden:true
			},{
				header:"状态", width:60, dataIndex:"pause_status",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var returnValue = value;				
				if(returnValue==0){
					returnValue="未发布";
				}
				if(returnValue==1){
					returnValue="已发布";
				}
				return returnValue;
				}
			},{
				header:"类别", width:100, dataIndex:"category_name",sortable:true
			},{
				header:"标题", width:260, dataIndex:"title",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='http://www.huanbao.com/news/details"+record.get("id")+".htm' target='_blank'>"+value+"</a>";
				}
			},{
				header:"编辑人", width:80, dataIndex:"admin_name",sortable:true
			},{
				header:"标签", width:200, dataIndex:"tags",sortable:true
			},{
				header:"发布时间", width:150,dataIndex:"gmt_publish",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"来源", width:100, dataIndex:"news_source",sortable:true
			},{
				header:"访问数(PV)", width:100, dataIndex:"view_count",sortable:true
			}
		]);
		
		var grid=this;
		
		var _bbar=com.zz91.utils.pageNav(_store);
		
		var recommendMenu=grid.recommendMenu;
		if(recommendMenu==null){
			recommendMenu=new Array();
		}
		
		recommendMenu.push({
			text:"取消推荐",
			handler:function(){
				// 得到表格对象
				var grid=Ext.getCmp(NEWS.COLLECTION_GRID);
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT+"/news/news/cancelRecommendNews.htm",
						params:{"id":rows[i].get("id"),"rid":rows[i].get("rid")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								com.zz91.utils.Msg("","取消推荐成功");
								grid.getStore().reload();
							}else{
								com.zz91.utils.Msg("","取消推荐失败");
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
			}
		});
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"添加",
					iconCls:"add16",
					handler:function(btn){
					    var code=grid.getStore().baseParams["categoryCode"];
					    window.open(Context.ROOT+"/news/news/details.htm?code="+code);
				  }  
				},"-",{
					text:"编辑",
					iconCls:"edit16",
					menu:[
					{
						id:"editButton",
						iconCls:"edit16",
						text:"修改",
						handler:function(btn){
							var rows=grid.getSelectionModel().getSelections();
							com.zz91.ep.news.edit(rows);
						}
					},
					{
						id:"copyButton",
						iconCls:"floppy16",
						text:"复制",
						handler:function(btn){
							var rows=grid.getSelectionModel().getSelections();
							if(rows.length!=1){
								com.zz91.utils.Msg("","请选择选择一条记录进行复制！");
							} else {
								com.zz91.ep.news.copy(rows);
							}
						}
					},{
						text:"取消发布",
						iconCls:"pause16",
						handler:function(btn){
							grid.pause(0,_store);
						}
					},{
						text:"恢复发布",
						iconCls:"play16",
						handler:function(btn){
							grid.pause(1,_store);
						}
					},{
						text:"隐藏资讯",
						iconCls:"pause16",
						handler:function(btn){
						     grid.hide(id,_store);
					    }
					},{
						text:"显示资讯",
						iconCls:"play16",
						handler:function(btn){
						     grid.showLine(id,_store);
					    }
					}]
				},"-",{
					text:"推荐到",
					iconCls:"fav16",
					menu:recommendMenu,
				},"导数据始于：",{
			    	id:"targetFrom",
			    	xtype:"datefield",
			    	width:90,
			    	format:"Y-m-d"
			    },"到",{
			    	id:"targetTo",
			    	xtype:"datefield",
			    	width:90,
			    	format:"Y-m-d"
			    },{
			    	iconCls:"add16",
					text:"导出",
					handler:function(btn){
			    		grid.exportPrice(_store);
					}
				},"->",{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
					}
				},"->",{
// text:"选择搜索条件",
// //iconCls:"websearch16",
// menu:[{}]
						xtype:"textfield",
						width:120,
						emptyText:"搜索标题",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["title"]=newValue;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype:"combo",
						width:100,
						emptyText:"发布状态搜寻",
						name:"pauseStatusStr",
						hiddenName:"pauseStatus",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","未发布"],["1","已发布"],["7","隐藏资讯"],["","全部"]]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["pauseStatus"]=newValue;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype:"combo",
						width:100,
						emptyText:"推荐类型搜索",
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.ep.news.FILTER_RECOMMEND
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["recommendCode"]=newValue;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}
				]
			}),
			bbar:_bbar
		};
		
		com.zz91.ep.news.grid.superclass.constructor.call(this,c);
	},
	listeners:{
		"rowdblclick":function(grid,rowIndex,e){
			var rows=grid.getSelectionModel().getSelections();
			com.zz91.ep.news.edit(rows);
		}
	},
	
	hide:function(id,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(id==rows[i].get("id")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/news/news/inserthideInfo.htm",
				params:{"id":rows[i].get("id"),"targetType":"0"},
				success:function(response,opt){ 
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","隐藏资讯成功");
						  store.reload();
					}else{
						com.zz91.utils.Msg("","隐藏资讯失败");
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
	},
	exportPrice:function(store){
		var B = store.baseParams;
		var from=Ext.get("targetFrom").dom.value;
		var to=Ext.get("targetTo").dom.value;
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
			if(btn!="yes"){
				return ;
			}else{
				Ext.Ajax.request({
					url: window.open(Context.ROOT+"/news/news/exportData.htm?" + "from="+from+"&to="+to),
				});
			}
		});
	},
	showLine:function(id,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(id==rows[i].get("id")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/news/news/deleteHideInfo.htm",
				params:{"id":rows[i].get("id"),"targetType":"0"},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","恢复显示资讯成功");
						  store.reload();
					}else{
						com.zz91.utils.Msg("","恢复显示资讯失败");
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
	},
	pause:function(pauseStatus,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(pauseStatus==rows[i].get("pause_status")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/news/news/updatePauseStatus.htm",
				params:{"id":rows[i].get("id"),"pauseStatus":pauseStatus},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
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
	},
	recommendMenu:null
});

com.zz91.ep.news.edit=function(rows){
	for(var i=0;i<rows.length;i++){
		window.open(Context.ROOT+"/news/news/details.htm?id="+rows[i].get("id"));
	}
}

com.zz91.ep.news.copy=function(rows){
	for(var i=0;i<rows.length;i++){
		window.open(Context.ROOT+"/news/news/details.htm?copy=1&id="+rows[i].get("id"));
	}
}

// @Deprecated
com.zz91.ep.news.deleteCollection=function(idArr, store){
	
	Ext.Msg.confirm("确认","你确定要删除吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
		for(var i=0;i<idArr.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/news/news/deleteNews.htm",
				params:{"id":idArr[i]},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
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
	});
}

com.zz91.ep.news.Form=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		var c={
			layout:"column",
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					name:"id",
					id:"id"
				},{
					fieldLabel:"标题",
					name:"title",
					id:"title",
					itemCls:"required",
					allowBlank:false,
					listeners:{
						"blur":function(field){
							var title=Ext.getCmp("title").getValue();
								Ext.getCmp("titleIndex").setValue(title);
						}
					}
				},{
					xtype:"hidden",
					id:"categoryCode",
					name:"categoryCode"
				},{
					xtype:"hidden",
					id:"adminAccount",
					name:"adminAccount"
				},{
					xtype:"hidden",
					id:"adminName",
					name:"adminName"
				},{
					fieldLabel:"类别",
					name:"categoryName",
					id:"categoryName",
					itemCls:"required",
					allowBlank:false,
					readOnly:true,
					listeners:{
						"focus":function(field){
							var initValue=Ext.getCmp("categoryCode").getValue();
							com.zz91.ep.news.choiceCategory(initValue,function(node,e){
								Ext.getCmp("categoryName").setValue(node.text);
								Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							})
						}
					}
				},{
					fieldLabel:"来源",
					name:"newsSource"
				},{
					fieldLabel:"标签",
					name:"tags",
				},{
                	xtype:"textfield",
                	id:"filePath",
                	fieldLabel: '文件上传(20M)',
                	name:"filePath",
                	listeners:{
                		"focus":function(c){
//                			if(Ext.get("id").dom.value==""){
//                				Ext.MessageBox.show({
//									title:MESSAGE.title,
//									msg : "请先保存，然后打开编辑页面，进行上传操作！",
//									buttons:Ext.MessageBox.OK,
//									icon:Ext.MessageBox.ERROR
//								});
//                				return ;
//                			}else{
                				
								com.zz91.ep.photo.UploadConfig.uploadURL=Context.ROOT+"/news/news/uploadFile.htm?id="+Ext.get("id").dom.value;
                				var win = new com.zz91.ep.photo.UploadWin({
                					title:"允许格式(doc,docx,xls,pdf,jpg,jpeg,gif,bmp,png)"
                				});
                				com.zz91.ep.photo.UploadConfig.uploadSuccess=function(f,o){
                					if(o.result.success){
                						win.close();
                						com.zz91.utils.Msg(MESSAGE.title, "文件已上传!");
                						Ext.getCmp(NEWS.FILE_GRID).getStore().reload();
                					}else{
                						com.zz91.utils.Msg(MESSAGE.title, o.result.data);
                					}
                				}
                				win.show();
                				
//                			}
                		}
                	}
                }]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"首页显示标题",
					name:"titleIndex",
					id:"titleIndex",
					itemCls:"required",
					allowBlank:false
				},{
					xtype:"checkbox",
					boxLabel:'正常发布', 
					name:'pauseStatus',
					inputValue:"1",
					checked:true
				},{
					fieldLabel:"来源URL",
					name:"newsSourceUrl"
				},{
					xtype:"numberfield",
					fieldLabel:"查看次数",
					name:"viewCount",
					value:"0"
				},{
					fieldLabel:"视频地址",
					name:"videoUrl"
				},{
					id:"coverurl",
					fieldLabel:"视频封面图片",
					name:"photoCover",
					listeners:{
                		"focus":function(c){
							com.zz91.ep.photo.UploadConfig.uploadURL=Context.ROOT+"/news/news/uploadFile.htm?id="+Ext.get("id").dom.value;
            				var win = new com.zz91.ep.photo.UploadWin({
            					title:"允许格式(jpg,jpeg,gif,bmp,png)"
            				});
            				com.zz91.ep.photo.UploadConfig.uploadSuccess=function(f,o){
            					if(o.result.success){
            						win.close();
            						com.zz91.utils.Msg(MESSAGE.title, "文件已上传!");
            						Ext.getCmp(NEWS.FILE_GRID).getStore().reload();
            						Ext.getCmp("coverurl").setValue(o.result.data);
            					}else{
            						com.zz91.utils.Msg(MESSAGE.title, o.result.data);
            					}
            				}
            				win.show();
                		}
                	}
				}]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					xtype:"textarea",
					anchor:"99%",
					fieldLabel:"文章导读",
					name:"description",
					itemCls:"required",
					allowBlank:false
				},{
					id:"html",
					xtype:"htmleditor",
					anchor:"99%",
					height:185,
					fieldLabel:"文章正文",
					name:"details",
					itemCls:"required",
					allowBlank:false
				},{
                	xtype:"textfield",
                	anchor:"50%",
                	fieldLabel: '添加图片',
                	listeners:{
                		"focus":function(c){
							com.zz91.ep.photo.UploadConfig.uploadURL=Context.ROOT+"/news/news/uploadFile.htm?id="+Ext.get("id").dom.value;
            				var win = new com.zz91.ep.photo.UploadWin({
            					title:"允许格式(jpg,jpeg,gif,bmp,png)"
            				});
            				com.zz91.ep.photo.UploadConfig.uploadSuccess=function(f,o){
            					if(o.result.success){
            						win.close();
            						com.zz91.utils.Msg(MESSAGE.title, "文件已上传!");
            						Ext.getCmp(NEWS.FILE_GRID).getStore().reload();
            						Ext.getCmp("html").insertAtCursor("<img src='"+"http://image01.huanbao.com/images.php?picurl=http://img1.huanbao.com"+o.result.data+"&width=400&height=400"+"'></img>");
            					}else{
            						com.zz91.utils.Msg(MESSAGE.title, o.result.data);
            					}
            				}
            				win.show();
                		}
                	}
                }]
			}],
			buttons:[{
				iconCls:"saveas16",
				text:"保存",
				handler:function(){
					var url=Context.ROOT+"/news/news/createNews.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/news/news/updateNews.htm";
					}
					if(form.getForm().isValid()){
				    	form.getForm().submit({
			                url:url,
			                method:"post",
			                type:"json",
			                waitMsg: '提交中，请稍后......',
			                success:function(_form,_action){
				    		    com.zz91.utils.Msg(MESSAGE.title, MESSAGE.saveSuccess);
				    		    var res = _action.result.data;
				    		    form.findById("id").setValue(res);
								// form.reset();
								
							},
							failure:function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailureError,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
			            });
				    }else{
			            Ext.MessageBox.show({
			                title:MESSAGE.title,
			                msg : MESSAGE.unValidate,
			                buttons:Ext.MessageBox.OK,
			                icon:Ext.MessageBox.ERROR
			            });
				    }
				}
			},{
				text:"关闭",
				iconCls:"close16",
// scope:this,baseParams:{"id":id},
				handler:function(btn){
					window.close();
				}
			}]
		};
		
		com.zz91.ep.news.Form.superclass.constructor.call(this,c);
	},initCategoryCode:function(){
		// var tree = Ext.getCmp(CATEGORY.TREE);
		var tree = new com.zz91.ep.category.TreePanel({
			id:"newscategorytree",
			region:"center",
			layout:"fit",
			rootVisible:false
		});
		var node = tree.getSelectionModel().getSelectedNode();

		if(node!=null && node.attributes["data"]!=""){
			// this.findById("categoryCode").setValue(node.attributes["data"]);
			Ext.getCmp("categoryName").setValue(node.text);
			Ext.getCmp("categoryCode").setValue(node.attributes["data"]);
		}
	},
	

		
	
	loadRecord:function(id,code,copy){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.ep.news.FORMFIELD,
			url:Context.ROOT+"/news/news/queryOneNews.htm",
			baseParams:{"id":id,"code":code,"copy":copy},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						// form.findById("gmtEntryDate").setValue(Ext.util.Format.date(new
						// Date(record.get("gmtEntry").time), 'Y-m-d'));
					}
				}
			}
		});
	}
});

com.zz91.ep.news.choiceCategory=function(init,callback){
	
	var tree = new com.zz91.ep.category.TreePanel({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
	
	tree.initSelect(init);
}

com.zz91.ep.news.recommend=function(type){
	// 得到表格对象
	var grid=Ext.getCmp(NEWS.COLLECTION_GRID);
	// 获取选中行的ID
	var rows=grid.getSelectionModel().getSelections();
	for(var i=0;i<rows.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/news/news/recommendNews.htm",
			params:{"id":rows[i].get("id"),"type":type},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					com.zz91.utils.Msg(MESSAGE.title, "资讯推荐成功");
					store.reload();
				}else{
					com.zz91.utils.Msg(MESSAGE.title, "资讯推荐失败");
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
}

com.zz91.ep.news.photoListGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/news/news/queryPhotoList.htm",
			autoLoad:false,
			remoteSort:false,
			fields:["id","targetId","targetType","path","gmtCreated"]
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号", width:80, dataIndex:"id",sortable:true,hidden:true
			},{
				header:"对应资讯",width:300, dataIndex:"targetId",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==0){
						return "新发布图片 或 之前上传没有成功匹配资讯的图片";
					}
					return value;
				}
			},{
				header:"类型", dataIndex:"targetType",sortable:true
			},{
				header:"文件路径", width:400, dataIndex:"path",sortable:true
			},{
				header:"创建时间", width:200, dataIndex:"gmtCreated",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			}
		]);
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"插入下载链接",
					iconCls:"add16",
					handler:function(btn){
						grid.insertLink(_store);
					}
				},{
					text:"插入图片",
					iconCls:"add16",
					handler:function(btn){
						grid.insertImg(_store);
					}
				},{
					text:"设置视频封面",
					iconCls:"add16",
					handler:function(btn){
						grid.insertVideoCover(_store);
					}
				},{
					text:"删除",
					iconCls:"edit16",
					handler:function(btn){
						grid.deleteRecord(_store);
					} 
				}]
			})
		};
		
		com.zz91.ep.news.photoListGrid.superclass.constructor.call(this,c);
	},
	loadPhotoRecord:function(id){
//		if (id>0){
			this.getStore().reload({
				params:{"id":id}
			});
//		}
	},
	deleteRecord:function(store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/news/news/deleteUploadFile.htm",
				params:{"id":rows[i].get("id"),"path":rows[i].get("path")},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
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
	},
	insertLink:function(store){
		var rows=this.getSelectionModel().getSelections();
		if(rows.length==0){
			com.zz91.utils.Msg("","请至少选择一项！");
			return ;
		}
		Ext.MessageBox.confirm(Context.MSG_TITLE, "下载格式(excel,doc,pdf),确定选择正确并插入下载链接吗？", function(btn){
			if(btn != "yes"){
				return false;
			}else{
				for(var i=0;i<rows.length;i++){
					Ext.getCmp("html").insertAtCursor("<a href='"+Context.UPLOAD_FILE+rows[i].get("path")+"'>下载标题</a>");
				}
			}
		});
		
	},
	insertImg:function(store){
		var rows=this.getSelectionModel().getSelections();
		if(rows.length==0){
			com.zz91.utils.Msg("","请至少选择一项！");
			return ;
		}
		Ext.MessageBox.confirm(Context.MSG_TITLE, "图片显示格式(jpg,jpeg,gif,bmp,png),确定选择正确并插入图片吗？", function(btn){
			if(btn != "yes"){
				return false;
			}else{
				for(var i=0;i<rows.length;i++){
					Ext.getCmp("html").insertAtCursor("<img src='"+"http://image01.huanbao.com/images.php?picurl="+Context.UPLOAD_FILE+rows[i].get("path")+"&width=400&height=400"+"'></img>");
				}
			}
		});
	},
	insertVideoCover:function(store){
		var rows=this.getSelectionModel().getSelections();
		if(rows.length==0){
			com.zz91.utils.Msg("","请选择一项设置视频图片！");
			return false;
		}else if(rows.length>1){
			com.zz91.utils.Msg("","只能选一张设置视频图片！");
			return false;
		}
		Ext.MessageBox.confirm(Context.MSG_TITLE, "图片显示格式(jpg,jpeg,gif,bmp,png),确定选择正确并插入图片吗？", function(btn){
			if(btn != "yes"){
				return false;
			}else{
				for(var i=0;i<rows.length;i++){
					Ext.getCmp("coverurl").setValue(rows[i].get("path"));
				}
			}
		});
	},
	
});

