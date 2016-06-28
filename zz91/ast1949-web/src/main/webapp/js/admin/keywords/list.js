/*
 * 供求关键字管理
 */
Ext.namespace("ast.ast1949.admin.keywords")

// 定义变量
var _C = new function() {
	this.RESULT_GRID = "resultgrid";
	this.KEYWORDS_FORM = "keywordsform";
	this.KEYWORDS_INFO_WIN = "keywordsinfowin";
}

Ext.onReady(function() {

			// 加载关键字排名列表
			var resultgrid = new ast.ast1949.admin.keywords.ResultGrid({
						title : "供求关键字管理",
						id : _C.RESULT_GRID,
						listUrl : Context.ROOT + Context.PATH+ "/admin/keywords/query.htm",
						region : 'center',
						autoScroll : true
					});

			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [resultgrid]
					});

		});

// 关键字排名列表
ast.ast1949.admin.keywords.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);

		var _fields = this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
					root : "records",
					totalProperty : 'totalRecords',
					remoteSort : true,
					fields : _fields,
					url : _url,
					autoLoad : true
				});
		//操作按钮(选中记录或未选中记录时的显示情况)
		var _sm = new Ext.grid.CheckboxSelectionModel({
					listeners : {
						selectionchange : function(sm) {
							if (sm.getCount()) {
								Ext.getCmp("deleteKeywordsButton").enable();
								Ext.getCmp("updateKeywordsButton").enable();
								Ext.getCmp("checkButton").enable();
								Ext.getCmp("unCheckButton").enable();
							} else {
								Ext.getCmp("deleteKeywordsButton").disable();
								Ext.getCmp("updateKeywordsButton").disable();
								Ext.getCmp("checkButton").disable();
								Ext.getCmp("unCheckButton").disable();
							}
						}
					}
				});

	//显示关键字排名列表的列
		var _cm = new Ext.grid.ColumnModel([_sm, {
					header : "编号",
					sortable : false,
					dataIndex : "id",
					hidden : false
				}, {
					header : "供求信息",
					sortable : false,
					width : 150,
					dataIndex : "productsTitle"
				}, {
					header : "公司名称",
					sortable : false,
					width : 100,
					dataIndex : "companyName"
				}, {
					header : "关键字",
					sortable : false,
					width : 100,
					dataIndex : "name"
				}, {
					header : "标王类型",
					width : 100,
					sortable : false,
					dataIndex : "label"
//					renderer : function(value, metadata, record, rowIndex,
//							colIndex, store) {
//						if (value == "10431000") {
//							return "金牌标王";
//						} else if (value == "10431001") {
//							return "银牌标王";
//						} else if(value=="10431002"){
//							return "铜牌标王";
//						}else {
//							return value;
//						}
//					}
				}, {
					header : "开始时间",
					width : 100,
					sortable : true,
					dataIndex : "start_time",
					renderer : function(value, metadata, record, rowIndex,colIndex, store) {
						if (value != null) {
							return Ext.util.Format.date(new Date(value.time),'Y-m-d');
						} else {
							return "";
						}
					}
				}, {
					header : "结束时间",
					id:"endTime1",
					width : 100,
					sortable : true,
					dataIndex : "end_time",
					renderer : function(value, metadata, record, rowIndex,colIndex, store) {
						if (value != null) {
							return Ext.util.Format.date(new Date(value.time),'Y-m-d');
						} else {
							return "";
						}
					}
				}, {
					header :"购买时间",
					width : 100,
					sortable : false,
					dataIndex : "buyTime",
					renderer : function(value, metadata, record, rowIndex,colIndex, store) {
						if (value != null) {
							return Ext.util.Format.date(new Date(value.time),'Y-m-d');
						} else {
							return "";
						}
					}
				},{
					header : "审核情况",
					width : 100,
					sortable : false,
					dataIndex : "isChecked",
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if(value==1){
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
						}else if(value==0){
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
						}else{
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
						}
					}
				}, {
					header : "是否过期",
					width : 100,
					sortable : false,
					dataIndex : "expire",
					renderer : function(value, metadata, record, rowIndex,colIndex, store) {
						if (value ==true) {
							return "未过期";
						} else if (value == false) {
							return "已过期";
						} else {
							return "";
						}
					}
				}]);
				
		//分页工具条
		var con = {
			iconCls : "icon-grid",
			store : _store,
			sm : _sm,
			cm : _cm,
			autoExpandColumn:10,
			tbar : this.mytoolbar,
			bbar : new Ext.PagingToolbar({
						pageSize : Context.PAGE_SIZE,
						store : _store,
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条记录,共 {2} 条',
						emptyMsg : '没有可显示的记录',
						beforePageText : '第',
						afterPageText : '页,共{0}页',
						paramNames : {
							start : "startIndex",
							limit : "pageSize"
						}
					}),
			listeners : {
				"render" : this.buttonQuery
			}
		};
		ast.ast1949.admin.keywords.ResultGrid.superclass.constructor.call(this,con);
	},
	//选择审核状态是否未审核函数
	searchByIsChecked:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("isCheckedBtn").getValue()){
			ary.push(1);
		}else{
			ary.push(0);
		}
		B["isChecked"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	//选择信息状态是否过期
	searchByExpire:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("isExpireBtn").getValue()){
			ary.push(true);
		}else{
			ary.push(false);
		}
		B["expire"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	//映射字段
	listRecord : Ext.data.Record.create([
		{name : "id",mapping : "productsKeywordsRank.id"}, 
		{name : "productsTitle",mapping : "productsTitle"},
		{name : "companyName",mapping : "companyName"}, 
		{name:"name",mapping:"productsKeywordsRank.name"},
		{name : "type",mapping : "productsKeywordsRank.type"},
		{name : "start_time",mapping : "productsKeywordsRank.startTime"}, 
		{name : "end_time",mapping : "productsKeywordsRank.endTime"}, 
		{name: "buyTime",mapping :"productsKeywordsRank.buyTime"},
		{name : "isChecked",mapping : "productsKeywordsRank.isChecked"}, 
		{name : "expire",mapping : "expire"},
		{name : "label",mapping : "label"}
	]),
	//搜索菜单条
	mytoolbar : ["关键字：", {
				xtype : "textfield",
				id : "searchName",
				name : "name",
				width : 80
			}, "公司名：", {
				xtype : "textfield",
				id : "searchCompanyName",
				name : "companyName",
				width : 80
			}, "标王类别", {
				xtype : "combo",
				id : "searchType",
				name : "type",
				triggerAction : "all",
				forceSelection : true,
				displayField : "label",
				valueField : "code",
				width : 80,
				store : new Ext.data.JsonStore({
					root : "records",
					fields : ["label", "code"],
					autoLoad : false,
					url : Context.ROOT
							+ Context.PATH
							+ "/admin/category/selectCategoiesByCode.htm?preCode="
							+ Context.CATEGORY.typeCode,
					listeners : {
						load : function() {
						}
					}
				}),
				listeners : {
					"blur" : function(field) {
						var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
						var B = _store.baseParams;
						B = B || {};
						if (Ext.get(field.getId()).dom.value != "") {
							B["type"] = field.getValue();
						} else {
							B["type"] = undefined;
						}
						_store.baseParams = B;
						_store.reload({
									params : {
										"startIndex" : 0,
										"pageSize" : Context.PAGE_SIZE
									}
								});
					}
				}
			}, "-", "开始时间>=", {
				xtype : "datefield",
				format : 'Y-m-d',
				id : "searchStartTime",
				name:"startTime",
				width : 90
			}, "-", "结束时间<=", {
				xtype : "datefield",
				format : 'Y-m-d',
				id : "searchEndTime",
				name:"endTime",
				width : 90
			},{
				text : "查询",
				iconCls : "query",
				handler :function(){
			//动态查询
				var result_grid = Ext.getCmp(_C.RESULT_GRID);
//				var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
//				var B = _store.baseParams;
				 if(Ext.get("searchStartTime").dom.value==""&&Ext.get("searchEndTime").dom.value==""){
			           result_grid.store.baseParams = {
								"name":Ext.get("searchName").dom.value,
			            		"companyName":Ext.get("searchCompanyName").dom.value,
								"type":Ext.get("searchType").dom.value
								};
			        }
				 else if(Ext.get("searchEndTime").dom.value!=""&&Ext.get("searchStartTime").dom.value==""){
			         result_grid.store.baseParams = {
								"name":Ext.get("searchName").dom.value,
			            		"companyName":Ext.get("searchCompanyName").dom.value,
								"type":Ext.get("searchType").dom.value,
								"endTimeStr":Ext.get("searchEndTime").dom.value
								};
			        }
				 else if(Ext.get("searchStartTime").dom.value!=""&&Ext.get("searchEndTime").dom.value ==""){
			           result_grid.store.baseParams = {
								"name":Ext.get("searchName").dom.value,
			            		"companyName":Ext.get("searchCompanyName").dom.value,
								"type":Ext.get("searchType").dom.value,
								"startTimeStr":Ext.get("searchStartTime").dom.value
								};
			        }
			        else if(Ext.get("searchStartTime").dom.value!=""&&Ext.get("searchEndTime").dom.value !=""){
			        		result_grid.store.baseParams = {
								"name":Ext.get("searchName").dom.value,
			            		"companyName":Ext.get("searchCompanyName").dom.value,
								"type":Ext.get("searchType").dom.value,
								"startTimeStr":Ext.get("searchStartTime").dom.value,
								"endTimeStr":Ext.get("searchEndTime").dom.value
								};
			        }
				 	var grid = Ext.getCmp(_C.RESULT_GRID);
						grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
			}],
	buttonQuery : function() {
		var tbar2 = new Ext.Toolbar({
					items : [ {
								iconCls : "delete",
								id : "deleteKeywordsButton",
								text : "删除",
								disabled : true,
								handler:function(btn){
									Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
								}
							}, "-", {
								iconCls : "edit",
								id : "updateKeywordsButton",
								text : "修改",
								disabled : true,
								handler : function(btn) {
									var grid = Ext.getCmp(_C.RESULT_GRID);

									var row = grid.getSelections();
									if (row.length > 1) {
										Ext.MessageBox.show({
													title : Context.MSG_TITLE,
													msg : "最多只能选择一条记录！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
									} else {
										var _pid = row[0].get("id");
										ast.ast1949.admin.keywords.editFormWin(_pid);
									}
								}
							}, "-", {
								iconCls : "add",
								id : "checkButton",
								text : "审核",
								disabled : true,
								handler : function(btn) {
									// 加个判断
									checkButton(1);
								}
							}, "-", {
								iconCls : "edit",
								id : "unCheckButton",
								text : "取消审核",
								disabled : true,
								handler : function(btn) {
									// Ext.MessageBox.confirm(Context.MSG_TITLE,
									// "确定要取消审核吗？",function(){
									checkButton(0);
									// });
								}
							},"->",{
							xtype:"checkbox",
							boxLabel:"显示已审核的信息",
							id:"isCheckedBtn",
							checked:false,
							listeners:{
								"check":function(field,newvalue,oldvalue){
									var resultgrid=Ext.getCmp(_C.RESULT_GRID);
									resultgrid.searchByIsChecked();
								}
							}
						},"->",{
							xtype:"checkbox",
							boxLabel:"显示未过期的信息",
							id:"isExpireBtn",
							checked:false,
							listeners:{
									"check":function(field,newvalue,oldvalue){
										var resultgrid=Ext.getCmp(_C.RESULT_GRID);
										resultgrid.searchByExpire();
									}
								}
					}]
				});
		tbar2.render(this.tbar);
	}
});

//删除功能
function doDelete(_btn){
	if(_btn != "yes")
			return ;
			
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/keywords/delete.htm?random="+Math.random()+"&ids="+_ids.join(','),
		method : "get",
		scope : this,
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
				Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
			}
		}
	});
}
//修改页面
ast.ast1949.admin.keywords.editFormWin = function(id) {
	var form = new ast.ast1949.admin.keywords.infoForm({
				id : _C.KEYWORDS_FORM,
				region : "center",
				saveUrl : Context.ROOT + Context.PATH + "/admin/keywords/update.htm?id="+id
			});

	var win = new Ext.Window({
				id : _C.KEYWORDS_INFO_WIN,
				title : "修改关键字排名信息",
				width : "80%",
				modal : true,
				items : [form]
			});
	form.loadRecords(id);
	win.show();
};

// 修改订制页面表单
ast.ast1949.admin.keywords.infoForm = Ext.extend(Ext.form.FormPanel, {
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);

		var c = {
			labelAlign : "right",
			labelWidth : 100,
			bodyStyle : "padding:5px 0 0",
			frame : true,
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : "textfield",
								fieldLabel : "关键字",
								itemCls:"required",
								allowBlank : true,
								name : "name",
								tabIndex : 3,
								anchor : "95%"
							}, {
								xtype : "datefield",
								fieldLabel:"开始时间",
								format : 'Y-m-d 00:00:00',
								id : "startTime",
								name:"startTime",
								width : 180
							}, {
								xtype : "datefield",
								fieldLabel : "购买时间",
								format : 'Y-m-d 00:00:00',
								id:"buyTime",
								name : "buyTime",
								value:new Date(),
								width:180
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : "combo",
						id : "type1",
						triggerAction : "all",
						forceSelection : true,
						displayField : "label",
						valueField : "code",
						width : 50,
						fieldLabel : "标王类型",
						allowBlank : true,
						hiddenName:"type",
                        displayField : "label",
                        valueField : "code",
						tabIndex : 3,
						anchor : "95%",
						store : new Ext.data.JsonStore({
							root : "records",
							fields : ["label", "code"],
							autoLoad : false,
							url : Context.ROOT
									+ Context.PATH
									+ "/admin/category/selectCategoiesByCode.htm?preCode="
									+ Context.CATEGORY.typeCode,
							listeners : {
								load : function() {
								}
							}
						})
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : "datefield",
								fieldLabel : "结束时间",
								format : 'Y-m-d 00:00:00',
								id:"endTime1",
								name : "endTime",
								value:new Date(),
								width:180
							}, {
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : "99%",
									xtype : "textfield",
									labelSeparator : ""
								},
								items : [new Ext.form.RadioGroup({
									// 0:未审核,1:审核
									fieldLabel : "是否审核",
									itemCls:"required",
									items : [{
												name : "isChecked",
												inputValue : "0",
												boxLabel : "否       "
											}, {
												name : "isChecked",
												inputValue : "1",
												boxLabel : "是       "
											}]
								})]
							}]
				}]
			}],
			buttons : [{
						text : "确定",
						handler : this.save,
						scope : this
					}, {
						text : "关闭",
						handler : function() {
							Ext.getCmp(_C.KEYWORDS_INFO_WIN).close();
						},
						scope : this
					}]
		};
		ast.ast1949.admin.keywords.infoForm.superclass.constructor.call(this,c);
	},
	mystore : null,
	loadRecords : function(id) {
		var _fields = [
				{name : "id",mapping : "id"}, 
				{name : "name",mapping : "name"}, 
				{name : "startTime",mapping : "startTime",convert:function(value) {
				return Ext.util.Format.date(new Date(value.time), 'Y-m-d');}}, 
				{name : "buyTime",mapping : "buyTime",convert:function(value) {
				return Ext.util.Format.date(new Date(value.time), 'Y-m-d');}},
				{name : "type",mapping : "type"},
				{name : "endTime",mapping : "endTime",convert:function(value) {
				return Ext.util.Format.date(new Date(value.time), 'Y-m-d');}},
				{name:"isChecked",mapping:"isChecked"}
			];
		var form = this;
		var store = new Ext.data.JsonStore({
					root : "records",
					fields : _fields,
					url : Context.ROOT + Context.PATH
							+ "/admin/keywords/queryById.htm",
					baseParams : {
						"id" : id
					},
					autoLoad : true,
					listeners : {
						"datachanged" : function(s) {
							var record = s.getAt(0);
							if (record == null) {
								Ext.MessageBox.alert(Context.MSG_TITLE,
										"数据加载失败...");
							} else {
								form.getForm().loadRecord(record);
							}
						}
					}
				})

	},
	saveUrl:Context.ROOT+Context.PATH + "",
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
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		ast.ast1949.admin.keywords.resultGridReload();
		Ext.getCmp(_C.KEYWORDS_INFO_WIN).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

});


// 审核
function checkButton(_btn) {
	var grid = Ext.getCmp(_C.RESULT_GRID);

	var row = grid.getSelections();
	var _ids = new Array();
	for (var i = 0, len = row.length; i < len; i++) {
		var _id = row[i].get("id");
		_ids.push(_id);
	}

	/* 提交 */
	var conn = new Ext.data.Connection();
	conn.request({
				url : Context.ROOT + Context.PATH + "/admin/keywords/updateChecked.htm?random="+Math.random()+"&ids="+_ids+"&isChecked="+_btn,
				method : "get",
				scope : this,
				callback : function(options, success, response) {
					var res = Ext.decode(response.responseText);
					if (res.success) {
						if (_btn == 1) {
							ast.ast1949.utils.Msg("", "所选记录已审核通过！");
						} else {
							ast.ast1949.utils.Msg("", "所选已取消审核！");
						}
						grid.getStore().reload();
					} else {
						Ext.MessageBox.alert(Context.MSG_TITLE, "所选操作失败!");
					}
				}
			});
};
//重新绑定Grid数据
ast.ast1949.admin.keywords.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}