Ext.namespace("ast.ast1949.phone");
// 定义变量
var _C = new function() {
	this.LIBRARY_GRID = "libraryGrid";
	this.UPDATE_NUMBER_FORM = "updatePhoneNumberForm";
	this.UPDATE_NUMBER_WIN = "updateNumberwin";
}
ast.ast1949.phone.libraryFIELD=[
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"},
	{name:"id",mapping:"id"},
	{name:"tel",mapping:"tel"},
	{name:"called",mapping:"called"},
	{name:"status",mapping:"status"}
];

ast.ast1949.phone.libraryGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,
			{
				header : "id",
				hidden: true,
				dataIndex:"id"
			},{
				header : "400号码",
				width:200,
				sortable:false,
				dataIndex:"tel"
			},{
				header : "内网号",
				width:200,
				sortable:false,
				dataIndex:"called"
			},{
				header : "状态",
				sortable:false,
				dataIndex:"status",
				renderer:function(value,metadata,record,rowIndex,colIndex,store){
					if(value==1){
						return "<span style='color:green'>未选可用</span>";
					}else{
						return "<b style='color:red'>不可用</b>";
					}
				}
			},{
				header : "导入时间",
				width:150,
				sortable:false,
				dataIndex:"gmtCreated",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}

			},{
				header : "最后分配时间",
				width:150,
				sortable:false,
				dataIndex:"gmtModified",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}

			}			
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryPhoneLibary.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.libraryFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [{
				iconCls:"add",
				text:"导入400号码",
				id:'fileUrl',
				name:'fileUrl',
				listeners:{
					"click":function(field,e){
						com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/importPhoneByExcel.htm"
						var win = new com.zz91.sms.gateway.UploadWin({
							title:"上传文件",
						});
						com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
							Ext.Msg.alert(Context.MSG_TITLE,"导入成功");
							win.close();
							_store.reload();
						}
						win.show();
					}
				}
			},{
				iconCls:"delete",
				text:"回收号码",
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"确定回收此号码?如果号码使用者未过期，擅自操作，一切后果自负-_-!",function(btn){
						if(btn!="yes"){
							return ;
						}else{
							ast.ast1949.phone.delOne();
							_store.reload();
						}
					});
				}
			},{
				iconCls:"edit",
				text:"修改",
				handler:function(btn){
					var grid= Ext.getCmp("libraryGrid");
					var rows=grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
							ast.ast1949.phone.updatePhoneNumber(rows[0].get("id"));
							_store.reload();
					}

				}
			},{
				iconCls:"query",
				text:"未使用号码",
				handler:function(btn){
				_store.proxy=new Ext.data.HttpProxy({url:Context.ROOT+Context.PATH+ "/phone/queryPhoneLibary.htm?status=1"});
				_store.load();
//					Ext.Ajax.request({
//						url: Context.ROOT+Context.PATH+ "/phone/queryPhoneLibary.htm",
//				        params:{
//							"status":1,
//						},
//					});
				}
			},{
				iconCls:"delete",
				text:"删除号码",
				handler:function(btn){
					var grid= Ext.getCmp("libraryGrid");
					var rows=grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						if(rows[0].get("status")==1){
							Ext.MessageBox.confirm(Context.MSG_TITLE,"确定删除号码",function(btn){
								if(btn!="yes"){
									return ;
								}else{
									ast.ast1949.phone.delNumber(rows[0].get("id"));
									_store.reload();
								}
							});
						}else{
							ast.ast1949.utils.Msg("","该号码已使用,不能删除");	
						}
							
					}
					
				}
			},"->","-","查找号码：",{
            	id:"tel",
            	xtype:"textfield",
            	listeners:{
					"change":function(field,newValue,oldValue){
						var phone=Ext.getCmp("tel").getValue();
	            		_store.baseParams["tel"]=phone;
					}
				}
            },{
				xtype:"combo",
				itemCls:"required",
				width:100,
				name:"categoryCombo",
				mode:"local",
				emptyText:"选择发送状态",
				triggerAction:"all",
				forceSelection: true,
				displayField:'name',
				valueField:'value',
				autoSelect:true,
				store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'全部',value:null},
						{name:'不可用',value:0},
						{name:'可用',value:1}
					]
				}),
				listeners:{
					"change":function(field,newValue,oldValue){
						_store.baseParams["status"]=newValue;
						_store.reload();
					}
				}
			},{
            	text:"查询",
            	iconCls:"query",
            	handler:function(btn){
            		_store.reload();
            	}
            }
		];

		var c={
			id:"libraryGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
			bbar: new Ext.PagingToolbar({
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
		ast.ast1949.phone.libraryGrid.superclass.constructor.call(this,c);
	}
});

//添加新黑名单
ast.ast1949.phone.addOne=function(){
	var form=new ast.ast1949.phone.addOneForm({
	});
	
	var win = new Ext.Window({
		id:"addOnewin",
		title:"添加黑名单",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}
//修改页面
ast.ast1949.phone.updatePhoneNumber = function(id) {
	var form = new ast.ast1949.phone.updateNumberForm({
			});

	var win = new Ext.Window({
				id :"_C.UPDATE_NUMBER_WIN",
				title : "修改的内容",
				width : "400",
				modal : true,
				items : [form]
			});
	form.loadRecords(id);
	win.show();
};
// 修改订制页面表单
ast.ast1949.phone.updateNumberForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid:null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var form=this;
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"400号码",
				name:"tel",
				id:"tel"
			},{
				fieldLabel:"内网号",
				name:"called",
				id:"called"
			}],
			buttons : [{
						text : "确定",
						handler:function(btn){
					         if(form.getForm().isValid()){
							var grid = Ext.getCmp("libraryGrid");
							form.getForm().submit({
								url:Context.ROOT+Context.PATH+"/phone/updatePhoneLibraryById.htm",
								method:"post",
								type:"json",
								success:function(){
									ast.ast1949.utils.Msg("","保存成功");
									Ext.getCmp("_C.UPDATE_NUMBER_WIN").close();
									grid.getStore().reload();	
								},
								failure:function(){
									ast.ast1949.utils.Msg("","保存失败");
								}
							});
						}else{
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "验证未通过",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
				
					}}, {
						text : "关闭",
						handler : function() {
							Ext.getCmp("_C.UPDATE_NUMBER_WIN").close();
						},
						scope : this
					}]
		};
		ast.ast1949.phone.updateNumberForm.superclass.constructor.call(this,c);
	},
	mystore : null,
	loadRecords : function(id) {
		var form = this;
		var store = new Ext.data.JsonStore({
					root : "records",
					fields :ast.ast1949.phone.libraryFIELD,
					url : Context.ROOT + Context.PATH
							+ "/phone/queryPhoneLibaryById.htm",
					baseParams : {
						"id" : id
					},
					autoLoad : true,
					listeners : {
						"datachanged" : function(s) {
							var record = s.getAt(0);
							if (record == null) {
								Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
							} else {
								form.getForm().loadRecord(record);
							}
						}
					}
				})

	},
	
});

//回收号码
ast.ast1949.phone.delOne=function(){
	var grid = Ext.getCmp("libraryGrid");
	var rows = grid.getSelections();
	var id = "";
	for(var i=0;i<rows.length;i++){
		id = rows[i].get("id");
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/phone/delLibrary.htm",
		params:{
		"id":id
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			ast.ast1949.utils.Msg("",obj.data)
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
}
//删除号码
ast.ast1949.phone.delNumber=function(id){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/phone/delNumber.htm",
		params:{
		"id":id
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			ast.ast1949.utils.Msg("",obj.data)
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
}

// 400号码检索结果 grid 匹配所有 该 400号码的客户
ast.ast1949.phone.phoneLibraryGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				dataIndex:"id"
			},{
				header : "公司Id",
				sortable : false,
				hidden:true,
				dataIndex : "companyId"
			},{
				header : "客户名称",
				width : 100,
				sortable : false,
				dataIndex : "contact"
			},{
				header : "邮箱",
				width : 100,
				sortable : false,
				dataIndex : "email"
			},{
				header : "开通日期",
				width : 200,
				sortable : false,
				dataIndex : "gmtOpen",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
					}
				}
			},{
				header : "充值总金额",
				width : 130,
				sortable : false,
				dataIndex : "amount"
			},{
				header : "余额",
				width : 100,
				sortable : false,
				dataIndex : "balance"
			},{
				header : "号码绑定状态",
				width : 90,
				sortable : false,
				dataIndex : "expireFlag",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(value!="0"){
						val="<b style='color:red'>解除绑定</b>";
					}else{
						val="<span style='color:green'>绑定</span>";
					}
					return val;
				}
			}]);

		// 字段信息
		var reader = [{name:"id",mapping:"id"},
		   {name:"companyId",mapping:"companyId"},
		   {name:"contact",mapping:"contact"},
		   {name:"email",mapping:"email"},
		   {name:"gmtOpen",mapping:"gmtOpen"},
		   {name:"amount",mapping:"amount"},
		   {name:"balance",mapping:"balance"},
		   {name:"expireFlag",mapping:"expireFlag"}];

		var storeUrl = Context.ROOT + Context.PATH + "/api/ldb/queryPhoneLibraryByTel.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [{
			text:"解除绑定",
			iconCls:"delete",
			handler:function(){
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
				else
					var row = grid.getSelections();
//					ast.ast1949.crm.phoneCostSvr.UpdateSvr(row[0].get("companyId"));
					Ext.Ajax.request({
						url: Context.ROOT+Context.PATH+ "/api/ldb/closePhone.htm",
						params:{
							"companyId":row[0].get("companyId")
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							var grid = Ext.getCmp("phoneLibraryGrid");
							if(obj.success){
								ast.ast1949.utils.Msg("","解除绑定，关闭"+obj.data+"该号码成功")
							}else{
								ast.ast1949.utils.Msg("","操作失败")
							}
							grid.getStore().reload();
						}
					});
			}
		}];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:7,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			listeners:{
				"rowcontextmenu":function(g,rowIndex,e){
					if(!g.getSelectionModel().isSelected(rowIndex)){
						g.getSelectionModel().clearSelections();
						g.getSelectionModel().selectRow(rowIndex);
					}
					e.preventDefault();
					if(g.contextmenu!=null){
						g.contextmenu.showAt(e.getXY());
					}
				}
			}
		};
		ast.ast1949.phone.phoneLibraryGrid.superclass.constructor.call(this,c);
	},
	loadByCompany:function(tel){
		var B=this.getStore().baseParams;
		B=B||{};
		B["tel"]=tel;
		this.getStore().baseParams=B;
		this.getStore().reload({});
	}
});
