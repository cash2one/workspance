Ext.namespace("ast.ast1949.admin.score.conversion")

/**
 * 数据列表
 * @class ast.ast1949.admin.score.conversion.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.score.conversion.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
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
	                } else {
	                	Ext.getCmp("editButton").disable();
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
			},{
				header:"兑换服务",
				dataIndex:"name",
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					var _id=record.get("scoreGoodsId");
					if(_id!=null){
						return "<a style='cursor:pointer' onclick='javascript:ast.ast1949.admin.score.conversion.viewFormWin("+_id+")' target='_blank'>"+value+"</a>";
					} else {
						return value;
					}
				}
			},{
				header:"公司名称",
				dataIndex:"companyName",
				sortable:false
			},{
				header:"兑换方式",
				dataIndex:"conversionCategory",
				width:100,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null&&value=="0"){
						return "实物";
					} else {
						return "服务";
					}
				}
			},{
				header:"总积分",
				dataIndex:"totalScore",
				sortable:false
			},{
				header:"联系人",
				dataIndex:"contactName",
				sortable:false
			},{
				header:"联系电话",
				dataIndex:"phone",
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					var val="";
					if(value!=null&&value.length>0){
						val=value;
					} else {
						val=record.get("tel");
					}
					return val;
				}
			},{
				header:"状态",
				dataIndex:"status",
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					var val="";
					if(value!=null){
						//0 申请中；1 成功；2 失败。
						if(value=="1") {
							val="成功 "+record.get("remark");;
						} else if(value=="2") {
							val="失败 "+record.get("remark");
						} else {
							val="申请中";
						}
					}
					return val;
				}
			},{
				header:"申请时间",
				dataIndex:"gmtCreated",
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
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
					id:"editButton",
					iconCls:"item-edit",
					text:"申请单",
					disabled:true,
					handler:function(btn){
						
//						var grid = Ext.getCmp(Const.PRICE_GRID);
						var row = grid.getSelections();
						var selectedRecord = grid.getSelectionModel().getSelected();
						if(row.length>1){
							ast.ast1949.utils.Msg("","最多只能选择一条记录！");
						} else {
							var row = grid.getSelections();
							var _id=row[0].get("id");
							ast.ast1949.admin.score.conversion.goodsApplyFormWin(_id);
						}
					}
				},"->",{
					xtype:"checkbox",
					boxLabel:"未完成",
					id:"isStatus",
//					checked:true,
					listeners:{
						"check":function(field,newvalue,oldvalue){
							grid.doSearch();
						}
					}
				}
			],
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
		
		ast.ast1949.admin.score.conversion.ResultGrid.superclass.constructor.call(this,c);
	},
	doSearch:function(){
		var B=this.getStore().baseParams||{};
		if(Ext.getCmp("isStatus").getValue()){
			B["status"] = "0";
		} else {
			B["status"] = null;
		}
		//B["membershipCode"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"history.id"},
		{name:"scoreGoodsId",mapping:"history.scoreGoodsId"},
		{name:"name",mapping:"goods.name"},
		{name:"detailUrl",mapping:"goods.detailUrl"},
		{name:"conversionCategory",mapping:"history.conversionCategory"},
		{name:"companyName",mapping:"history.companyName"},
		{name:"contactName",mapping:"history.contactName"},
		{name:"tel",mapping:"history.tel"},
		{name:"phone",mapping:"history.phone"},
		{name:"status",mapping:"history.status"},
		{name:"remark",mapping:"history.remark"},
		{name:"gmtCreated",mapping:"history.gmtCreated"},
		{name:"totalScore",mapping:"totalScore"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/score/conversion/query.htm"
})

/**
 * 申请表单
 * @class ast.ast1949.admin.score.conversion.editForm
 * @extends Ext.form.FormPanel
 */
ast.ast1949.admin.score.conversion.editForm=Ext.extend(Ext.form.FormPanel, {
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
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"id",
						name:"id"
					},{
						xtype:"hidden",
						id:"companyId",
						name:"companyId"
					},{
						name:"companyName",
						fieldLabel:"公司名称",
						allowBlank:true
					},{
						name:"phone",
						fieldLabel:"手机",
						readonly:true,
						allowBlank:true
					},{
						name:"email",
						fieldLabel:"邮箱",
						allowBlank:true
					}]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
					{
						name:"contactName",
						fieldLabel:"联系人",
						allowBlank:true
					},{
						name:"tel",
						fieldLabel:"电话",
						allowBlank:true
					},{
						name:"keywords",
						fieldLabel:"关键字",
						allowBlank:true
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
						{
							xtype:"textarea",
							name:"remark",
							fieldLabel:"备注信息",
							allowBlank:true
						}
					]
				}
			],
			buttons:[
			{
				id:"btnSuccess",
				text:"兑换成功",
				handler:this.updateSuccess,
				scope:this
			},{
				id:"btnFailure",
				text:"兑换失败",
				handler:this.updateFailure,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(PAGE_CONST.APPLY_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.score.conversion.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"companyId",mapping:"companyId"},
			{name:"companyName",mapping:"companyName"},
			{name:"contactName",mapping:"contactName"},
			{name:"phone",mapping:"phone"},
			{name:"tel",mapping:"tel"},
			{name:"email",mapping:"email"},
			{name:"keywords",mapping:"keywords"},
			{name:"status",mapping:"status"},
			{name:"remark",mapping:"remark"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/score/conversion/getSingleById.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						var _status=record.get("status");
						if(_status!=null){
							if(_status=="1"||_status=="2") {
								Ext.getCmp("btnSuccess").hide();
								Ext.getCmp("btnFailure").hide();
							}
						}
					}
				}
			}
		})
	},
	
	updateSuccessUrl:Context.ROOT+Context.PATH + "/admin/score/conversion/updateSuccess.htm",
	updateSuccess:function(){
		var _url = this.updateSuccessUrl;
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	updateFailureUrl:Context.ROOT+Context.PATH + "/admin/score/conversion/updateFailure.htm",
	updateFailure:function(){
		var _url = this.updateFailureUrl;
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","保存成功！");
		
		Ext.getCmp(PAGE_CONST.APPLY_WIN).close();
		
		var grid=Ext.getCmp(PAGE_CONST.RESULT_GRID);
		var B=grid.getStore().baseParams||{};
    	grid.getStore().baseParams = B;
    	grid.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","保存失败！");
	}
});

/**
 * 申请窗口
 * @param {} id
 */
ast.ast1949.admin.score.conversion.applyFormWin=function(id){
	var form = new ast.ast1949.admin.score.conversion.editForm({
		id:PAGE_CONST.APPLY_FORM,
		region:"center"
	});
	
	var win = new Ext.Window({
		id:PAGE_CONST.APPLY_WIN,
		title:"申请单",
		width:"60%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};

/**
 * 商品编辑表单
 * @class ast.ast1949.admin.score.conversion.editForm
 * @extends Ext.form.FormPanel
 */
ast.ast1949.admin.score.conversion.editForm=Ext.extend(Ext.form.FormPanel, {
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
					columnWidth:0.33,
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
						fieldLabel:"商品名称",
						itemCls :"required",
						blankText : "商品名称不能为空",
						allowBlank:false
					},{
						xtype : "datefield",
						fieldLabel : "开始时间",
						allowBlank : false,
						itemCls :"required",
						format : 'Y-m-d',
						name : "gmtStart",
						value:new Date(),
						blankText : "开始时间不能为空"
					},{
						xtype:"numberfield",
						name:"priceBuy",
						fieldLabel:"原价购买",
						value:0,
						allowBlank : false,
						itemCls :"required",
						allowBlank:true
					},{
						xtype:"numberfield",
						name:"scoreBuy",
						fieldLabel:"积分购买",
						value:0,
						allowBlank : false,
						itemCls :"required",
						allowBlank:true
					},{
						name:"freight",
						fieldLabel:"运费",
						value:0,
						allowBlank : false,
						itemCls :"required",
						allowBlank:true
					},{
						name:"keywords",
						fieldLabel:"关键字",
						allowBlank:true
					},{
						xtype:"checkbox",
						boxLabel:"热门商品",
						id:"checkbox-isHot",
						name:"isHot",
						inputValue:"Y"
					}]
				},{
					columnWidth:0.33,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:          'combo',
	                    mode:           'local',
	                    triggerAction:  'all',
	                    forceSelection: true,
	                    editable:       false,
	                    fieldLabel:     '商品类型',
	                    value:"",
	                    hiddenName:     'category',
	                    displayField:   'name',
	                    valueField:     'value',
	                    width:100,
	                    itemCls :"required",
						blankText : "商品类型不能为空",
						allowBlank:false,
	                    store:          new Ext.data.JsonStore({
	                        fields : ['name', 'value'],
	                        data   : [
	                            {name : '实物',   value: '1'},
	                            {name : '服务',  value: '0'}
	                        ]
	                    })
					},{
						xtype : "datefield",
						fieldLabel : "结束时间",
						allowBlank : false,
						itemCls :"required",
						format : 'Y-m-d',
						name : "gmtEnd",
						value:new Date(),
						blankText : "结束时间不能为空"
					},{
						xtype:"numberfield",
						name:"priceDay",
						fieldLabel:"(原价)服务时间",
						value:0,
						allowBlank : false,
						itemCls :"required",
						allowBlank:true
					},{
						xtype:"numberfield",
						name:"scoreDay",
						fieldLabel:"(积分)服务时间",
						value:0,
						allowBlank : false,
						itemCls :"required",
						allowBlank:true
					},{
						name:"detailUrl",
						fieldLabel:"详情URL",
						allowBlank:true
					},{
						xtype:"numberfield",
						name:"numConversion",
						fieldLabel:"兑换数量",
						value:0,
						allowBlank : false,
						itemCls :"required",
						allowBlank:true
					},{
						xtype:"checkbox",
						boxLabel:"首页显示",
						id:"checkbox-isHome",
						name:"isHome",
						inputValue:"Y"
					}]
				},{
					columnWidth:0.33,
					layout:"fit",
            		height:"100%",
					buttons:[
						{
							text:"上传图片",
	            			handler:function(btn){
			            		ast.ast1949.UploadConfig.uploadURL=Context.ROOT+Context.PATH+"/admin/upload?model=goods&filetype=img";
			
								var win = new ast.ast1949.UploadWin({
									title:"上传广告图片"
								});
								win.show();
			
								ast.ast1949.UploadConfig.uploadSuccess = function(f,o){
									if(o.result.success){
										ast.ast1949.utils.Msg("","图片已成功上传");
										var uploadUrl=o.result.data[0].path+o.result.data[0].uploadedFilename;
										Ext.getCmp("showPicture").setValue(uploadUrl);
										Ext.get("image-preview").dom.src=resourceUrl+"/"+uploadUrl;
										win.close();
									}else{
										Ext.MessageBox.show({
											title:Context.MSG_TITLE,
											msg : "出现错误，文件没有被上传",
											buttons:Ext.MessageBox.OK,
											icon:Ext.MessageBox.ERROR
										});
									}
								}
		            		}
						}
					],
					html:'<div class="thumb-wrap" id="img-wrap">'+
                	'<div class="thumb" style="cursor:pointer"><img onclick="javascript:window.open(this.src)" id="image-preview" src="http://img1.zz91.com/ads/no_image.gif" title="点击查看原图" style="height:150px;width:350px"></div>'+
                	'</div>'+
                    '<div class="x-clear"></div>'
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
						{
							xtype : "hidden",
							name : "showPicture",
							id:"showPicture"
						},{
							xtype:"textarea",
							name:"details",
							fieldLabel:"服务描述",
							allowBlank:true
						},{
							xtype:"textarea",
							name:"remark",
							fieldLabel:"备注信息",
							allowBlank:true
						}
					]
				}
			],
			buttons:[{
				text:"确定",
				handler:this.save,
				hidden:true,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(PAGE_CONST.GOODS_EDIT_WIN).close();
				},
				scope:this
			}
			]
		};
		
		ast.ast1949.admin.score.conversion.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"name",mapping:"name"},
			{name:"category",mapping:"category"},
			{name:"gmtStart",mapping:"gmtStart",convert:function(value){
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d');
				}
			}},
			{name:"gmtEnd",mapping:"gmtEnd",convert:function(value){
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d');
				}
			}},
			{name:"priceBuy",mapping:"priceBuy"},
			{name:"priceDay",mapping:"priceDay"},
			{name:"scoreBuy",mapping:"scoreBuy"},
			{name:"scoreDay",mapping:"scoreDay"},
			{name:"freight",mapping:"freight"},
			{name:"remark",mapping:"remark"},
			{name:"keywords",mapping:"keywords"},
			{name:"detailUrl",mapping:"detailUrl"},
			{name:"isHot",mapping:"isHot"},
			{name:"isHome",mapping:"isHome"},
			{name:"details",mapping:"details"},
			{name:"showPicture",mapping:"showPicture"},
			{name:"numConversion",mapping:"numConversion"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/score/goods/getSingleGoodsById.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("image-preview").dom.src=resourceUrl+"/"+ record.get("showPicture");
						
						if(record.get("isHot")!=null&&record.get("isHot")=="Y") {
							Ext.getCmp("checkbox-isHot").setValue(true);
						}
						if(record.get("isHome")!=null&&record.get("isHome")=="Y") {
							Ext.getCmp("checkbox-isHome").setValue(true);
//							form.getForm().findField("checkbox-isHome").setValue(true);
						}
					}
				}
			}
		})
	},
	
	saveUrl:Context.ROOT+Context.PATH + "/admin/score/goods/save.htm",
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","保存成功！");
		doSearchGoodsByCategory(null);
		Ext.getCmp(PAGE_CONST.GOODS_EDIT_WIN).close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","保存失败！");
	}
});

ast.ast1949.admin.score.conversion.viewFormWin=function(id){
	var form = new ast.ast1949.admin.score.conversion.editForm({
		id:PAGE_CONST.GOODS_EDIT_FORM,
		region:"center"
	});
	
	var win = new Ext.Window({
		id:PAGE_CONST.GOODS_EDIT_WIN,
		title:"修改商品",
		width:"85%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
	
};



/**
 * 申请表单
 * @class ast.ast1949.admin.score.conversion.goodsApplyForm
 * @extends Ext.form.FormPanel
 */
ast.ast1949.admin.score.conversion.goodsApplyForm=Ext.extend(Ext.form.FormPanel, {
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
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"id",
						name:"id"
					},{
						xtype:"hidden",
						id:"companyId",
						name:"companyId"
					},{
						name:"companyName",
						fieldLabel:"公司名称",
						allowBlank:true
					},{
						name:"phone",
						fieldLabel:"手机",
						readonly:true,
						allowBlank:true
					},{
						name:"email",
						fieldLabel:"邮箱",
						allowBlank:true
					}]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
					{
						name:"contactName",
						fieldLabel:"联系人",
						allowBlank:true
					},{
						name:"tel",
						fieldLabel:"电话",
						allowBlank:true
					},{
						name:"keywords",
						fieldLabel:"关键字",
						allowBlank:true
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
						{
							xtype:"textarea",
							name:"remark",
							fieldLabel:"备注信息",
							allowBlank:true
						}
					]
				}
			],
			buttons:[
			{
				id:"btnSuccess",
				text:"兑换成功",
				handler:this.updateSuccess,
				scope:this
			},{
				id:"btnFailure",
				text:"兑换失败",
				handler:this.updateFailure,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(PAGE_CONST.GOODS_APPLY_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.score.conversion.goodsApplyForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"companyId",mapping:"companyId"},
			{name:"companyName",mapping:"companyName"},
			{name:"contactName",mapping:"contactName"},
			{name:"phone",mapping:"phone"},
			{name:"tel",mapping:"tel"},
			{name:"email",mapping:"email"},
			{name:"keywords",mapping:"keywords"},
			{name:"status",mapping:"status"},
			{name:"remark",mapping:"remark"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/score/conversion/getSingleById.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						var _status=record.get("status");
						if(_status!=null){
							if(_status=="1"||_status=="2") {
								Ext.getCmp("btnSuccess").hide();
								Ext.getCmp("btnFailure").hide();
							}
						}
					}
				}
			}
		})
	},
	updateSuccessUrl:Context.ROOT+Context.PATH + "/admin/score/conversion/updateSuccess.htm",
	updateSuccess:function(){
		var _url = this.updateSuccessUrl;
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	updateFailureUrl:Context.ROOT+Context.PATH + "/admin/score/conversion/updateFailure.htm",
	updateFailure:function(){
		var _url = this.updateFailureUrl;
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","保存成功！");
		
		Ext.getCmp(PAGE_CONST.GOODS_APPLY_WIN).close();
		
		var grid=Ext.getCmp(PAGE_CONST.RESULT_GRID);
		var B=grid.getStore().baseParams||{};
    	grid.getStore().baseParams = B;
    	grid.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});		
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","保存失败！");
	}
});

ast.ast1949.admin.score.conversion.goodsApplyFormWin=function(id){
	var form = new ast.ast1949.admin.score.conversion.goodsApplyForm({
		id:PAGE_CONST.GOODS_APPLY_FORM,
		region:"center"
	});
	
	var win = new Ext.Window({
		id:PAGE_CONST.GOODS_APPLY_WIN,
		title:"申请单",
		width:"60%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};