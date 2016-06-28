Ext.namespace("com.zz91.ads.board.ad.advertiser")

/**
 * 广告主列表
 * @class com.zz91.ads.board.ad.advertiser.Grid
 * @extends Ext.grid.GridPanel
 */
com.zz91.ads.board.ad.advertiser.Grid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({
			listeners: {
				selectionchange: function(sm) {
	                if (sm.getCount()) {
						Ext.getCmp("editButton").enable();
						Ext.getCmp("deleteButton").enable();
	                } else {
	                	Ext.getCmp("editButton").disable();
	                	Ext.getCmp("deleteButton").disable();
					}
				} 
			}
		});
		
		var _cm=new Ext.grid.ColumnModel([_sm, 
			{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				sortable:false
			}, 
			{
				header:"类别",
				dataIndex:"category",
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store){
					if(value!=null) {
						return Context.ADVERTISER_CATEGORY[value];
					} else {
						return "普通会员";
					}
				}
			},{
				header:"广告主",
				dataIndex:"name",
				sortable:false
			},{
				header:"联系人",
				dataIndex:"contact",
				sortable:false
			},{
				header:"联系电话",
				dataIndex:"phone",
				sortable:false
			},{
				header:"邮箱",
				dataIndex:"email",
				sortable:false
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[
				{
					id:"addButton",
					iconCls:"add16",
					text:"添加",
					handler:function(btn){
						com.zz91.ads.board.ad.advertiser.addFormWin();
					}
				},
				{
					id:"editButton",
					iconCls:"edit16",
					text:"修改",
					disabled:true,
					handler:function(btn){
						var sm = grid.getSelectionModel();
						var row = sm.getSelections();
						var selectedRecord = grid.getSelectionModel().getSelected();
						if(row.length>1){
							com.zz91.ads.board.utils.Msg("","最多只能选择一条记录！");
						} else {
							var row = sm.getSelections();
							var _id=row[0].get("id");
							com.zz91.ads.board.ad.advertiser.editFormWin(_id);
						}
					}
				},{
					iconCls:"delete16",
					id:"deleteButton",
					text:"删除",
					disabled:true,
	            	handler:function(btn){
		            	var sm=grid.getSelectionModel();
		                var submitIds=sm.getCount();
		                if ( submitIds== 0){
	                        Ext.MessageBox.show({
	                            title:MESSAGE.title,
	                            msg : MESSAGE.needOneRecord,
	                            buttons:Ext.MessageBox.OK,
	                            icon:Ext.MessageBox.WARNING
	                        });
		                } else{
	                        Ext.MessageBox.confirm(MESSAGE.title, MESSAGE.confirmDelete, function(btn){
	                            if(btn != "yes"){
	                                    return false;
	                            }
	                            
	                            var row = sm.getSelections();
	                            var _ids = new Array();
	                            for (var i=0,len = row.length;i<len;i++){
	                                var _id=row[i].get("id");
	                                _ids.push(_id);
	                            }
	                            //提交删除
	                           Ext.Ajax.request({
									url: Context.ROOT + "/ad/advertiser/delete.htm?st="+timestamp(),
									params:{"items":_ids.join(",")},
									method: "post",
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											grid.getStore().reload();
										}else{
											com.zz91.ads.board.utils.Msg("","删除失败！");
										}
									},
									failure:function(response,opt){
										com.zz91.ads.board.utils.Msg("","删除失败！");
									}
								});
	                        });
		                }
	            	}
				},"->",
				{
					xtype:"textfield",
					id:"email",
					name:"email",
					emptyText:"搜索广告主邮箱",
					width:150,
					listeners:{
						//失去焦点
						"blur":function(c){
							var val=Ext.get("email").dom.value;
//							var grid = Ext.getCmp(Const.PRICE_GRID);
							var B = grid.store.baseParams;
							B = B||{};
							if(val!=""){
								B["email"] = c.getValue();
							}else{
								B["email"]=null;
							}
							grid.store.baseParams = B;
							grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						},
						//回车事件
						specialkey:function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
//								var grid = Ext.getCmp(Const.PRICE_GRID);
								var B = grid.store.baseParams;
								B = B||{};
								if(Ext.get(field.getId()).dom.value!=""){
									B["email"] = field.getValue();
								}else{
									B["email"]=null;
								}
								grid.store.baseParams = B;
								grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}
				},{
					xtype:"textfield",
					id:"name",
					name:"name",
					emptyText:"搜索广告主名字",
					width:160,
					listeners:{
						//失去焦点
						"blur":function(c){
							var val=Ext.get("name").dom.value;
//							var grid = Ext.getCmp(Const.PRICE_GRID);
							var B = grid.store.baseParams;
							B = B||{};
							if(val!=""){
								B["name"] = c.getValue();
							}else{
								B["name"]=null;
							}
							grid.store.baseParams = B;
							grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						},
						//回车事件
						specialkey:function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
//								var grid = Ext.getCmp(Const.PRICE_GRID);
								var B = grid.store.baseParams;
								B = B||{};
								if(Ext.get(field.getId()).dom.value!=""){
									B["name"] = field.getValue();
								}else{
									B["name"]=null;
								}
								grid.store.baseParams = B;
								grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}
				}
			],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: "显示第 {0} - {1} 条记录,共 {2} 条",
				emptyMsg : "没有可显示的记录",
				beforePageText : "第",
				afterPageText : "页,共{0}页",
				paramNames : {start:"start",limit:"limit"}
			})
		};
		com.zz91.ads.board.ad.advertiser.Grid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"category",mapping:"category"},
		{name:"name",mapping:"name"},
		{name:"contact",mapping:"contact"},
		{name:"phone",mapping:"phone"},
		{name:"email",mapping:"email"}
	]),
	listUrl:Context.ROOT+"/ad/advertiser/query.htm?st="+timestamp()
})

/**
 * 编辑表单
 * @class com.zz91.ads.board.ad.advertiser.editForm
 * @extends Ext.form.FormPanel
 */
com.zz91.ads.board.ad.advertiser.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[
				{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"id",
						name:"id"
					},{
						name:"name",
						fieldLabel:"广告主",
						itemCls :"required",
						blankText : "广告主不能为空",
						allowBlank : false
					},{
						name:"contact",
						fieldLabel:"联系人"
					},{
						name:"phone",
						fieldLabel:"电话"
					}]
				}
				,{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:          "combo",
	                    mode:           "local",
	                    triggerAction:  "all",
	                    forceSelection: true,
	                    editable:       false,
	                    fieldLabel:     "广告类型",
	                    value:"",
	                    hiddenName:     "category",
	                    displayField:   "name",
	                    valueField:     "value",
	                    width:100,
	                    store:          new Ext.data.JsonStore({
	                        fields : ["name", "value"],
	                        data   : [
	                            {name : Context.ADVERTISER_CATEGORY[0],   value: "0"},
	                            {name : Context.ADVERTISER_CATEGORY[1],  value: "1"},
	                            {name : Context.ADVERTISER_CATEGORY[2],  value: "2"},
//	                            {name : "其他",  value: "2"}
	                        ]
	                    })
					},{
						name:"email",
						fieldLabel:"邮箱",
						allowBlank : true
					}]
				}
				,{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textarea",
						name:"remark",
						fieldLabel:"备注",
						allowBlank : true
					}]
				}
			],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(ADVERTISER.EDIT_WIN).close();
				},
				scope:this
			}
			]
		};
		
		com.zz91.ads.board.ad.advertiser.editForm.superclass.constructor.call(this,c);
	},
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"category",mapping:"category"},
			{name:"name",mapping:"name"},
			{name:"contact",mapping:"contact"},
			{name:"phone",mapping:"phone"},
			{name:"email",mapping:"email"},
			{name:"remark",mapping:"remark"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + "/ad/advertiser/queryById.htm?st="+timestamp(),
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
//						Ext.get("image-preview").dom.src=resourceUrl+"/"+ record.get("showPicture");
//						
//						if(record.get("isHot")!=null&&record.get("isHot")=="Y") {
//							Ext.getCmp("checkbox-isHot").setValue(true);
//						}
//						if(record.get("isHome")!=null&&record.get("isHome")=="Y") {
//							Ext.getCmp("checkbox-isHome").setValue(true);
////							form.getForm().findField("checkbox-isHome").setValue(true);
//						}
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT + "/ad/advertiser/add.htm?st="+timestamp(),
	save:function(){
		var _url = this.saveUrl;
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
			com.zz91.ads.board.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		com.zz91.ads.board.utils.Msg("","保存成功！");
		var grid = Ext.getCmp(ADVERTISER.GRID);
		var B=grid.getStore().baseParams||{};
		grid.getStore().baseParams = B;
		grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
		
		Ext.getCmp(ADVERTISER.EDIT_WIN).close();
	},
	onSaveFailure:function (){
		com.zz91.ads.board.utils.Msg("","保存失败！");
	}
});

/**
 * 添加窗口
 */
com.zz91.ads.board.ad.advertiser.addFormWin = function(){
	var grid = new com.zz91.ads.board.ad.advertiser.editForm({
		id:ADVERTISER.ADD_FORM,
		saveUrl:Context.ROOT + "/ad/advertiser/add.htm?st="+timestamp(),
		region:"center"
	});
	
	var win = new Ext.Window({
		id:ADVERTISER.EDIT_WIN,
		title:"添加广告主",
		width:"75%",
		modal:true,
		items:[grid]
	});
	win.show();
};

/**
 * 编辑窗口
 */
com.zz91.ads.board.ad.advertiser.editFormWin = function(id){
	var form = new com.zz91.ads.board.ad.advertiser.editForm({
		id:ADVERTISER.EDIT_FORM,
		saveUrl:Context.ROOT + "/ad/advertiser/update.htm?st="+timestamp(),
		region:"center"
	});
	
	var win = new Ext.Window({
		id:ADVERTISER.EDIT_WIN,
		title:"编辑广告主",
		width:"75%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};

/**
 * 广告主列表
 * @class com.zz91.ads.board.ad.ad.advertiserGrid
 * @extends Ext.grid.GridPanel
 */
com.zz91.ads.board.ad.advertiser.AdvertiserGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;
				
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel({
			columns:[_sm,{
					header:"编号",
					dataIndex:"id",
					hidden:true,
					sortable:false
				},{
					header:"类别",
					dataIndex:"category",
					sortable:false,
					renderer:function(value, metadata, record, rowIndex,colIndex, store){
						if(value!=null) {
							return Context.ADVERTISER_CATEGORY[value];
						} else {
							return "其他";
						}
					}
				},{
					id:"name",
					name:"name",
					header:"广告主",
					dataIndex:"name",
					sortable:false
				},{
					header:"联系人",
					dataIndex:"contact",
					sortable:false
				},{
					header:"联系电话",
					dataIndex:"phone",
					sortable:false
				},{
					header:"邮箱",
					dataIndex:"email",
					sortable:false
				}
			]
		});
		
		var c = {
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[
				"广告主：",
				{
					xtype:"textfield",
					id:"name",
					name:"name",
					width:160,
					listeners:{
						//失去焦点
						"blur":function(c){
							var val=Ext.get("name").dom.value;
							var B = grid.store.baseParams;
							B = B||{};
							if(val!=""){
								B["name"] = c.getValue();
							}else{
								B["name"]=null;
							}
							grid.store.baseParams = B;
							grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						},
						//回车事件
						specialkey:function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								var B = grid.store.baseParams;
								B = B||{};
								if(Ext.get(field.getId()).dom.value!=""){
									B["name"] = field.getValue();
								}else{
									B["name"]=null;
								}
								grid.store.baseParams = B;
								grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}
				},"-","类型：",{
					xtype:          "combo",
                    mode:           "local",
                    triggerAction:  "all",
                    forceSelection: true,
                    editable:       false,
                    fieldLabel:     "广告类型",
                    value:"",
                    hiddenName:     "category",
                    displayField:   "name",
                    valueField:     "value",
                    width:100,
                    store:          new Ext.data.JsonStore({
                        fields : ["name", "value"],
                        data   : [
                            {name : Context.ADVERTISER_CATEGORY[0],   value: "0"},
                            {name : Context.ADVERTISER_CATEGORY[1],  value: "1"},
                            {name : Context.ADVERTISER_CATEGORY[2],  value: "2"}
                        ]
                    }),
                    listeners:{
						//失去焦点
						"blur":function(c){
							var val=Ext.get("category").dom.value;
							var B = grid.store.baseParams;
							B = B||{};
							if(val!=""){
								B["category"] = c.getValue();
							}else{
								B["category"]=null;
							}
							grid.store.baseParams = B;
							grid.store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}
			],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: "显示第 {0} - {1} 条记录,共 {2} 条",
				emptyMsg : "没有可显示的记录",
				beforePageText : "第",
				afterPageText : "页,共{0}页",
				paramNames : {start:"start",limit:"limit"}
			})
		};
		com.zz91.ads.board.ad.advertiser.AdvertiserGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"category",mapping:"category"},
		{name:"name",mapping:"name"},
		{name:"contact",mapping:"contact"},
		{name:"phone",mapping:"phone"},
		{name:"email",mapping:"email"}
	]),
	listUrl:Context.ROOT+"/ad/advertiser/query.htm"
});