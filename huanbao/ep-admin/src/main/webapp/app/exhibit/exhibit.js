Ext.namespace("com.zz91.ep.exhibit");

var EXHIBIT=new function(){
	this.COLLECTION_GRID="collectiongrid";
	this.FILE_GRID="filegrid";
}

com.zz91.ep.exhibit.GRIDFIELD=[
	{name:"id",mapping:"exhibit.id"},
	{name:"pause_status",mapping:"exhibit.pauseStatus"},
	{name:"plate_category_code",mapping:"exhibit.plateCategoryCode"},
	{name:"plate_category_name",mapping:"plateCategoryName"},
	{name:"industry_code",mapping:"exhibit.industryCode"},
	{name:"industry_category_name",mapping:"industryCategoryName"},
	{name:"name",mapping:"exhibit.name"},
	{name:"admin_name",mapping:"exhibit.adminName"},
	{name:"province_name",mapping:"proviceName"},
	{name:"area_name",mapping:"areaName"},
	{name:"gmt_start",mapping:"exhibit.gmtStart"},
	{name:"gmt_end",mapping:"exhibit.gmtEnd"},
	{name:"gmt_publish",mapping:"exhibit.gmtPublish"},
	{name:"organizers",mapping:"exhibit.organizers"},
	{name:"rid",mapping:"rid"}
];

com.zz91.ep.exhibit.FORMFIELD=[
	{name:"id",mapping:"exhibit.id"},
	{name:"name",mapping:"exhibit.name"},
	{name:"provinceCode",mapping:"exhibit.provinceCode"},
	{name:"areaCode",mapping:"exhibit.areaCode"},
	{name:"gmtStart",mapping:"exhibit.gmtStart"},
	{name:"gmtEnd",mapping:"exhibit.gmtEnd"},
	{name:"plateCategoryCode",mapping:"exhibit.plateCategoryCode"},
	{name:"industryCode",mapping:"exhibit.industryCode"},
	{name:"showName",mapping:"exhibit.showName"},
	{name:"details",mapping:"exhibit.details"},
	{name:"organizers",mapping:"exhibit.organizers"},
	{name:"gmtPublish",mapping:"exhibit.gmtPublish"},
	{name:"gmtSort",mapping:"exhibit.gmtSort"},
	{name:"pauseStatus",mapping:"exhibit.pauseStatus"},
	{name:"gmtModified",mapping:"exhibit.gmtModified"},
	{name:"adminAccount",mapping:"exhibit.adminAccount"},
	{name:"adminName",mapping:"exhibit.adminName"},
	{name:"plateCategoryName",mapping:"plateCategoryName"},
	{name:"industryCategoryName",mapping:"industryCategoryName"},
	{name:"proviceName",mapping:"proviceName"},
	{name:"areaName",mapping:"areaName"},
];

com.zz91.ep.exhibit.FILTER_RECOMMEND=[];

com.zz91.ep.exhibit.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/exhibit/exhibit/queryExhibit.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.ep.exhibit.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,
			{
				header:"rid", width:60, dataIndex:"rid",hidden:true
			},{
				header:"状态", width:60, dataIndex:"pause_status",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var returnValue = value;
				if(value==0){
					returnValue="未发布";
				}
				if(value==1){
					returnValue="已发布";
				}
				return returnValue;
				}
			},{
				header:"类别", width:80, dataIndex:"plate_category_name",sortable:true
			},{
				header:"行业", width:100, dataIndex:"industry_category_name",sortable:true
			},{
				header:"标题", width:150, dataIndex:"name",sortable:true
			},{
				header:"主办方", width:200, dataIndex:"organizers",sortable:true
			},{
				header:"发布时间", width:130, dataIndex:"gmt_publish",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"开始时间", width:130, dataIndex:"gmt_start",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"结束时间", width:130, dataIndex:"gmt_end",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"地区", width:80, dataIndex:"province_name",sortable:true
			},{
				header:"编辑", width:80, dataIndex:"admin_name",sortable:true
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
			//得到表格对象
			var grid=Ext.getCmp(EXHIBIT.COLLECTION_GRID);
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
						window.open(Context.ROOT+"/exhibit/exhibit/details.htm");
					}
				},{
					text:"编辑",
					iconCls:"edit16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						com.zz91.ep.exhibit.edit(rows);
					} 
				},"-",{
					text:"暂停",
					iconCls:"pause16",
					handler:function(btn){
						grid.pause(0,_store);
					}
				},{
					text:"恢复",
					iconCls:"play16",
					handler:function(btn){
						grid.pause(1,_store);
					}
				},{
					text:"推荐到",
					iconCls:"fav16",
					menu:recommendMenu,
				},"->",{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
					}
				},"->",{
					xtype:"textfield",
					width:120,
					emptyText:"搜索标题",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["name"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},{
					xtype:"combo",
					width:100,
					emptyText:"发布状态",
					name:"pauseStatusStr",
					hiddenName:"pauseStatus",
					mode:"local",
					triggerAction:"all",
					lazyRender:true,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["0","未发布"],["1","已发布"],["","全部"]]
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
                       data : com.zz91.ep.exhibit.FILTER_RECOMMEND
                   }),
                   listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["recommendType"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}]
			}),
			bbar:_bbar
		};
		
		com.zz91.ep.exhibit.grid.superclass.constructor.call(this,c);
	},
	listeners:{
		"rowdblclick":function(grid,rowIndex,e){
			var rows=grid.getSelectionModel().getSelections();
			com.zz91.ep.exhibit.edit(rows);
		}
	},
	pause:function(pauseStatus,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(pauseStatus==rows[i].get("pause_status")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/exhibit/exhibit/updatePauseStatus.htm",
				params:{"id":rows[i].get("id"),"pauseStatus":pauseStatus},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.saveSuccess);
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

com.zz91.ep.exhibit.edit=function(rows){
	for(var i=0;i<rows.length;i++){
		window.open(Context.ROOT+"/exhibit/exhibit/details.htm?id="+rows[i].get("id"));
	}
}

com.zz91.ep.exhibit.recommend=function(type){
	//得到表格对象
	var grid=Ext.getCmp(EXHIBIT.COLLECTION_GRID);
	//获取选中行的ID
	var rows=grid.getSelectionModel().getSelections();
	for(var i=0;i<rows.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/news/news/recommendNews.htm",
			params:{"id":rows[i].get("id"),"type":type},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					com.zz91.utils.Msg(MESSAGE.title, "展会推荐成功");
					store.reload();
				}else{
					com.zz91.utils.Msg(MESSAGE.title, "展会推荐失败");
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

com.zz91.ep.exhibit.deleteCollection=function(idArr, store){
	
	Ext.Msg.confirm("确认","你确定要删除吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
		for(var i=0;i<idArr.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/exhibit/exhibit/deleteExhibit.htm",
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

com.zz91.ep.exhibit.Form=Ext.extend(Ext.form.FormPanel,{
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
				columnWidth:1,
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
					fieldLabel:"展会标题",
					name:"name",
					itemCls:"required",
					allowBlank:false
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
					xtype:"hidden",
					id:"adminAccount",
					name:"adminAccount"
				},{
					xtype:"hidden",
					id:"adminName",
					name:"adminName"
				},{
					xtype:"hidden",
					id:"plateCategoryCode",
					name:"plateCategoryCode"
				},{
					fieldLabel:"类别",
					name:"plateCategoryName",
					id:"plateCategoryName",
					itemCls:"required",
					allowBlank:false,
					readOnly:true,
					listeners:{
						"focus":function(field){
							var initValue=Ext.getCmp("plateCategoryCode").getValue();
							com.zz91.ep.exhibit.choiceCategory(initValue,function(node,e){
								Ext.getCmp("plateCategoryName").setValue(node.text);
								Ext.getCmp("plateCategoryCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							})
						}
					}
				},{
					xtype:"hidden",
					id:"areaCode",
					name:"areaCode"
				},{
					fieldLabel:"地区",
					name:"areaName",
					id:"areaName",
					itemCls:"required",
					allowBlank:false,
					readOnly:true,
					listeners:{
						"focus":function(field){
							var initValue=Ext.getCmp("areaCode").getValue();
							com.zz91.ep.exhibit.choiceAreaCategory(initValue,function(node,e){
								Ext.getCmp("areaName").setValue(node.text);
								Ext.getCmp("areaCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							})
						}
					}
				},{
					fieldLabel:"展馆名称",
					name:"showName"
				},{
					xtype:"datefield",
					fieldLabel:"展会开始时间",
					name:"gmtStartStr",
					id:"gmtStartStr",
					allowBlank:false,
					value:new Date(),
					format:"Y-m-d H:i:s"
				},{
					xtype:"datefield",
					fieldLabel:"发布时间(当前)",
					name:"gmtPublishStr",
					id:"gmtPublishStr",
					value:new Date(),
					allowBlank:false,
					format:"Y-m-d H:i:s"
				},{
                	xtype:"textfield",
                	id:"filePath",
                	fieldLabel: '文件上传(20M)',
                	name:"filePath",
                	listeners:{
                		"focus":function(c){
                			if(Ext.get("id").dom.value==""){
                				Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : "请先保存，然后打开编辑页面，进行上传操作！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
                				return ;
                			}else{
                				com.zz91.ep.photo.UploadConfig.uploadURL=Context.ROOT+"/exhibit/exhibit/uploadFile.htm?id="+Ext.get("id").dom.value;
                				var win = new com.zz91.ep.photo.UploadWin({
                					title:"允许格式(doc,docx,xls,pdf,jpg,jpeg,gif,bmp,png)"
                				});
                				com.zz91.ep.photo.UploadConfig.uploadSuccess=function(f,o){
                					if(o.result.success){
                						win.close();
                						com.zz91.utils.Msg(MESSAGE.title, "文件已上传!");
                						Ext.getCmp(EXHIBIT.FILE_GRID).getStore().reload();
                					}else{
                						com.zz91.utils.Msg(MESSAGE.title, o.result.data);
                					}
                				}
                				win.show();
                			}
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
					xtype:"checkbox",
					boxLabel:'正常发布', 
					name:'pauseStatus',
					inputValue:"1",
				},{
					xtype:"hidden",
					fieldLabel:'行业code', 
					id:'ic',
					name:'industryCode',
				},{
				 	xtype: "combo",
	                fieldLabel:"选择行业",
	                name:"industryCategoryName",
	                editable: false,
	                triggerAction: "all",
	                anchor: '99%',
	                store: new Ext.data.Store({
	                    autoLoad: true,
	                    url: Context.ROOT+"/exhibit/exhibit/queryInsdustry.htm",
	                    reader: new Ext.data.JsonReader({
	                        fields: ["code","name"]
	                    }),
	                }),
	                listeners:{
						"change":function(field,newValue,oldValue){
								Ext.getCmp("ic").setValue(newValue);
						}
					},
					displayField: "name",
					valueField: "code"
				},{
					fieldLabel:"主办方",
					name:"organizers",
				},{
					xtype:"datefield",
					fieldLabel:"展会结束时间",
					name:"gmtEndStr",
					id:"gmtEndStr",
					value:new Date(),
					allowBlank:false,
					format:"Y-m-d H:i:s"
				},{
					xtype:"datefield",
					fieldLabel:"排序时间(排序)",
					name:"gmtSortStr",
					id:"gmtSortStr",
					allowBlank:false,
					value:new Date(),
					format:"Y-m-d H:i:s"
				}]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					id:"html",
					xtype:"htmleditor",
					anchor:"99%",
					fieldLabel:"展会正文",
					name:"details",
					itemCls:"required",
					allowBlank:false
				}]
			}],
			buttons:[{
				iconCls:"saveas16",
				text:"保存",
				handler:function(){
					var url=Context.ROOT+"/exhibit/exhibit/createExhibit.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/exhibit/exhibit/updateExhibit.htm";
					}
					
					if(form.getForm().isValid()){
				    	form.getForm().submit({
			                url:url,
			                method:"post",
			                type:"json",
			                success:function(_form,_action){
								com.zz91.utils.Msg(MESSAGE.title, MESSAGE.saveSuccess);
								form.reset();
							},
							failure:function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
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
//				scope:this,
				handler:function(btn){
					window.close();
				}
			}]
		};
		
		com.zz91.ep.exhibit.Form.superclass.constructor.call(this,c);
	},
	loadRecord:function(id){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.ep.exhibit.FORMFIELD,
			url:Context.ROOT+"/exhibit/exhibit/queryOneExhibit.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						form.findById("gmtEndStr").setValue(Ext.util.Format.date(new Date(record.get("gmtEnd").time), 'Y-m-d H:i:s'));
						form.findById("gmtStartStr").setValue(Ext.util.Format.date(new Date(record.get("gmtStart").time), 'Y-m-d H:i:s'));
						form.findById("gmtPublishStr").setValue(Ext.util.Format.date(new Date(record.get("gmtPublish").time), 'Y-m-d H:i:s'));
						form.findById("gmtSortStr").setValue(Ext.util.Format.date(new Date(record.get("gmtSort").time), 'Y-m-d H:i:s'));
						//form.findById("gmtEntryDate").setValue(Ext.util.Format.date(new Date(record.get("gmtEntry").time), 'Y-m-d'));
					}
				}
			}
		});
	}
});

com.zz91.ep.exhibit.choiceCategory=function(init,callback){
	
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

com.zz91.ep.exhibit.choiceAreaCategory=function(init,callback){
	
	var tree = new com.zz91.ep.common.area.Tree({
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
	
	//tree.initSelect(init);
}

com.zz91.ep.exhibit.photoListGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/exhibit/exhibit/queryPhotoList.htm",
			autoLoad:false,
			remoteSort:false,
			fields:["id","targetType","path","gmtCreated"]
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号", width:80, dataIndex:"id",sortable:true,hidden:true
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
					text:"删除",
					iconCls:"edit16",
					handler:function(btn){
						grid.deleteRecord(_store);
					} 
				}]
			})
		};
		
		com.zz91.ep.exhibit.photoListGrid.superclass.constructor.call(this,c);
	},
	loadPhotoRecord:function(id){
		if (id>0){
			this.getStore().reload({
				params:{"id":id}
			});
		}
	},
	deleteRecord:function(store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/exhibit/exhibit/deleteUploadFile.htm",
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
	}
});
