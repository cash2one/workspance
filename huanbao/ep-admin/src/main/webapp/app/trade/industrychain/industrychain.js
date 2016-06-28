Ext.namespace("com.zz91.ep.industrychain");

com.zz91.ep.industrychain.GRIDFIELD=[
	{name:"id",mapping:"id"},
	{name:"delStatus",mapping:"delStatus"},
	{name:"categoryCode",mapping:"categoryCode"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"areaCode",mapping:"areaCode"},
	{name:"areaName",mapping:"areaName"},
	{name:"showIndex",mapping:"showIndex"},
	{name:"orderby",mapping:"orderby"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.ep.industrychain.baseGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/trade/industrychain/querychain.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.ep.industrychain.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({
			singleSelect:true
		});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",dataIndex:"id",sortable:true
			},{
				header:"删除状态",width:70,dataIndex:"delStatus",
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value==0) {
						returnvalue="未删除";
					}
					if(value==1) {
						returnvalue="已删除";
					}
					return returnvalue;
				}
			},{
				header:"类别编号",dataIndex:"categoryCode",hidden:true
			},{
				header:"名称",dataIndex:"categoryName"
			},{
				header:"地区编号",dataIndex:"areaCode",hidden:true
			},{
				header:"地区名称",dataIndex:"areaName"
			},{
				header:"首页展示",dataIndex:"showIndex",
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(returnvalue==0) {
						returnvalue="否";
					}
					if(returnvalue==1) {
						returnvalue="是";
					}
					return returnvalue;
				}
			},{
				header:"排序",dataIndex:"orderby"
			},{
				header:"创建时间", width:130, dataIndex:"gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"修改时间", width:130, dataIndex:"gmtModified",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			}
		]);
		
//		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar:com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.ep.industrychain.baseGrid.superclass.constructor.call(this,c);
	}

});

com.zz91.ep.industrychain.addWin = function(code){
	
	var form = new com.zz91.ep.industrychain.Form();
	
	var win = new Ext.Window({
		id:"editWin",
		title:"添加产业链信息",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.ep.industrychain.editWin = function(record){
	
	var form = new com.zz91.ep.industrychain.Form({
		region:"center"
	});

	form.loadRecords(record);
	
	var win = new Ext.Window({
		id:"editWin",
		title:"修改产业链信息",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.ep.industrychain.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:"",
				msgTarget :"under"
			},
			items:[{
					xtype:"hidden",
					id:"id",
					name:"id",
					fieldLabel:"编号"
			},{
				fieldLabel:"产业链标题",
				name:"categoryName",
				allowBlank:false,
				itemCls:"required"
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
						com.zz91.ep.industrychain.choiceArea(initValue,function(node,e){
							Ext.getCmp("areaName").setValue(node.text);
							Ext.getCmp("areaCode").setValue(node.attributes["data"]);
							node.getOwnerTree().ownerCt.close();
						})
					}
				}
			},{
				xtype:"numberfield",
				fieldLabel:"排序",
				name:"orderby",
				value:"0",
				allowBlank:false,
				itemCls:"required"
			},{
				xtype:"checkbox",
				boxLabel:'首页显示', 
				name:'showIndex',
				inputValue:"1",
				checked:true
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					var form =this;
					var _url=Context.ROOT+"/trade/industrychain/addIndustryChain.htm";
					if(form.findById("id").getValue()>0){
						_url=Context.ROOT+"/trade/industrychain/updateIndustryChain.htm";
					}
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:_url,
							method:"post",
							type:"json",
							success:this.onSaveSuccess,
							failure:this.onSaveFailure,
							scope:this
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "请检查表单是否正确填写!",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp("editWin").close();
				}
			}]
		};

		com.zz91.ep.industrychain.Form.superclass.constructor.call(this,c);

	},
	onSaveSuccess:function(_form,_action){
		com.zz91.utils.Msg(MESSAGE.title, "数据已保存!");
		Ext.getCmp("chainMainGrid").getStore().reload();
		Ext.getCmp("editWin").close();
	},
	onSaveFailure:function(_form,_action){
		com.zz91.utils.Msg(MESSAGE.title, "发生错误,数据未保存!");
	},
	loadRecords:function(record){
		this.getForm().loadRecord(record);
	}
});

com.zz91.ep.industrychain.choiceArea = function(init,callback){
	
	var tree = new com.zz91.ep.common.area.Tree({
		id:"areatree",
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
};

//特色产业链管理
com.zz91.ep.industrychain.allChainGrid=Ext.extend(com.zz91.ep.industrychain.baseGrid,{
	constructor:function(config){
	config=config||{};
	Ext.apply(this,config);
		
		var c={
				tbar:new Ext.Toolbar({
					items:[{
						text:"新增",
						iconCls:"next16",
						handler:function(btn){
							com.zz91.ep.industrychain.addWin();
						}
					},{
						text:"修改",
						iconCls:"edit16",
						handler:function(btn){
							var mainGrid = Ext.getCmp("chainMainGrid");
							var record = mainGrid.getSelectionModel().getSelected();
							if(typeof(record) == "undefined"){
								com.zz91.utils.Msg("","请选择一条记录!");
							}
							com.zz91.ep.industrychain.editWin(record);
						
						}
					},{
						text:"标记删除",
						iconCls:"delete16",
						handler:function(btn){
							var grid = Ext.getCmp("chainMainGrid");
							grid.delStatus(1,grid.getStore());
						}
					},{
						text:"取消删除",
						iconCls:"accept16",
						handler:function(btn){
							var grid = Ext.getCmp("chainMainGrid");
							grid.delStatus(0,grid.getStore());
						}
					},{
						text:"首页显示",
						iconCls:"play16",
						handler:function(btn){
							var grid = Ext.getCmp("chainMainGrid");
							grid.showIndex(1,grid.getStore());
						}
					},{
						text:"首页不显示",
						iconCls:"pause16",
						handler:function(btn){
							var grid = Ext.getCmp("chainMainGrid");
							grid.showIndex(0,grid.getStore());
						}
					}]
				}),
		};
		com.zz91.ep.industrychain.allChainGrid.superclass.constructor.call(this,c);
	},
	showIndex:function(showIndex,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			
			if(showIndex==rows[i].get("showIndex")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/industrychain/updateShowIndex.htm",
				params:{"id":rows[i].get("id"),"showIndex":showIndex},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
					}else{
						store.reload();
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
	delStatus:function(delStatus,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			
			if(delStatus==rows[i].get("delStatus")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/industrychain/updateDelStatus.htm",
				params:{"id":rows[i].get("id"),"delStatus":delStatus},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
					}else{
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
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

//公司产业链管理
com.zz91.ep.industrychain.compChainGrid=Ext.extend(com.zz91.ep.industrychain.baseGrid,{
	constructor:function(config){
	config=config||{};
	Ext.apply(this,config);
		
		var c={
				tbar:new Ext.Toolbar({
					items:[{
						text:"新增",
						iconCls:"next16",
						handler:function(btn){
							var mainGrid = Ext.getCmp("compChainGrid");
							if (mainGrid.getStore().getCount()==3){
								com.zz91.utils.Msg("","超出限制条数,无法继续添加!");
								return "";
							}
							com.zz91.ep.industrychain.compChainAddWin(Ext.get("compId").dom.value);
						}
					},{
						text:"修改",
						iconCls:"edit16",
						handler:function(btn){
							var mainGrid = Ext.getCmp("compChainGrid");
							var record = mainGrid.getSelectionModel().getSelected();
							if(typeof(record) == "undefined"){
								com.zz91.utils.Msg("","请选择一条记录!");
							}
							com.zz91.ep.industrychain.compChainEditWin(record);
						}
					},{
						text:"删除",
						iconCls:"delete16",
						handler:function(btn){
							var grid = Ext.getCmp("compChainGrid");
							var record = grid.getSelectionModel().getSelected();
							if(typeof(record) == "undefined"){
								com.zz91.utils.Msg("","请选择一条记录!");
							}else{
								Ext.Ajax.request({
									url:Context.ROOT+"/trade/industrychain/updateCompChainDelStatus.htm",
									params:{"chainId":record.get("id"),"delStatus":1,"cid":Ext.get("compId").dom.value},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											grid.getStore().reload();
											com.zz91.utils.Msg("",MESSAGE.operateSuccess);
										}else{
											grid.getStore().reload();
											com.zz91.utils.Msg("",MESSAGE.operateSuccess);
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
					}]
				}),
		};
		com.zz91.ep.industrychain.compChainGrid.superclass.constructor.call(this,c);
	},
	showIndex:function(showIndex,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			
			if(showIndex==rows[i].get("showIndex")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/industrychain/updateShowIndex.htm",
				params:{"id":rows[i].get("id"),"showIndex":showIndex},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
					}else{
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
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
	loadCompId:function(cid){
		this.getStore().baseParams["cid"]=cid;
	}
});

com.zz91.ep.industrychain.compChainAddWin = function(cid){
	
	var form = new com.zz91.ep.industrychain.ChainForm();
	
	form.findById("cid").setValue(cid);
	
	var win = new Ext.Window({
		id:"compChainEditWin",
		title:"添加产业链",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.ep.industrychain.compChainEditWin = function(record){
	
	var form = new com.zz91.ep.industrychain.ChainForm({
		region:"center"
	});

	form.loadRecords(record);
	
	var win = new Ext.Window({
		id:"compChainEditWin",
		title:"修改产业链",
		width:400,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.ep.industrychain.ChainForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:"",
				msgTarget :"under"
			},
			items:[{
				xtype:"hidden",
				fieldLabel:"ID编号",
				id:"id",
				name:"id"
			},{
				xtype:"hidden",
				fieldLabel:"公司编号",
				id:"cid",
				name:"cid"
			},{
				xtype:"combo",
				id:"area",
				fieldLabel:"所属地区",
				anchor : "95%",
				name:"areaName",
				forceSelection : true,
				editable :false,
				emptyText :'请选择...',
				valueField:"areaCode",
				displayField:"areaName",
				store:new Ext.data.JsonStore({
					autoLoad : true,
					url : Context.ROOT+"/combo/getChainAreaName.htm",
					fields: ["areaCode","areaName"]
				}),
				mode:"local",
				triggerAction:"all",
				listeners :{
					"change" :function(field,newValue,oldValue){
						Ext.getCmp("chainName").setValue("");
						Ext.getCmp("chainName").store.reload({
							params:{"areaCode":newValue}
						});
					}
				}
			},{
				xtype:"hidden",
				fieldLabel:"产业链编号",
				id:"chainId",
				name:"chainId"
			},{
				xtype:"combo",
				mode:'local',
				triggerAction:'all',
				id : "chainName",
				fieldLabel:"产业链",
				valueField:"id",
				displayField:"categoryName",
				anchor : "95%",
				forceSelection : true,
				editable :false,
				emptyText :'请选择...',
				store:new Ext.data.JsonStore({
					autoLoad : false,
					url : Context.ROOT+"/combo/getCategoryName.htm",
					fields:["id","categoryName"]
				}),
				listeners:{
					"change":function(field,newValue,oldValue){
							Ext.getCmp("chainId").setValue(newValue);
					}
				}
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					var form =this;
					var _url=Context.ROOT+"/trade/industrychain/AddCompChain.htm";
					if(form.findById("id").getValue()>0){
						_url=Context.ROOT+"/trade/industrychain/updateCompChain.htm";
					}
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:_url,
							method:"post",
							type:"json",
							success:this.onSaveSuccess,
							failure:this.onSaveFailure,
							scope:this
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "请检查表单是否正确填写!",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp("compChainEditWin").close();
				}
			}]
		};

		com.zz91.ep.industrychain.ChainForm.superclass.constructor.call(this,c);

	},
	onSaveSuccess:function(_form,_action){
		com.zz91.utils.Msg(MESSAGE.title, "数据已保存!");
		Ext.getCmp("compChainGrid").getStore().reload();
		Ext.getCmp("compChainEditWin").close();
	},
	onSaveFailure:function(_form,_action){
		com.zz91.utils.Msg(MESSAGE.title, "发生错误或已存在相应记录,数据未保存!");
	},
	loadRecords:function(record){
		this.findById("cid").setValue(Ext.get("compId").dom.value);
		this.findById("area").setValue(record.get("areaCode"));
		this.findById("chainName").setValue(record.get("categoryName"));
		this.getForm().loadRecord(record);
	}
});

