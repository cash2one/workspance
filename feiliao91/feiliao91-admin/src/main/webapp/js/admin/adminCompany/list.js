Ext.namespace("ast.ast1949.admin.adminCompany");

var COMPANY = new function() {
	this.RESULT_GRID="resultgrid";
}

//列表
ast.ast1949.admin.adminCompany.resultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{
				header : "公司名",
				width : 200,
				sortable : false,
				dataIndex : "name"
			},{
				header : "登录帐号",
				width : 130,
				sortable : false,
				dataIndex : "account"
			},{
				header : "会员类型",
				width : 100,
				sortable : false,
				dataIndex : "membershipLabel",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")!="10051000"){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					}
					val= val + value;
					return val;
				}
			},{
				header : "电话",
				width : 100,
				sortable : false,
				dataIndex : "tel"
			},{
				header : "手机",
				width : 100,
				sortable : false,
				dataIndex : "mobile"
			},{
				header : "地区",
				width : 90,
				sortable : false,
				dataIndex : "areaName"
			},{
				header : "注册时间",
				width : 100,
				sortable : false,
				dataIndex : "regtime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}]);

		// 字段信息
		var reader = [ {name:"id",mapping:"company.id"},
		   {name:"name",mapping:"company.name"},
		   {name:"areaCode",mapping:"company.areaCode"},
		   {name:"regtime",mapping:"company.regtime"},
		   {name:"mobile",mapping:"account.mobile"},	               
		   {name:"account",mapping:"account.account"},
		   {name:"tel",mapping:"account.tel"},
		   {name:"areaName",mapping:"areaName"},
		   {name:"membershipLabel",mapping:"membershipLabel"},
		   {name:"membershipCode",mapping:"company.membershipCode"}];

		var storeUrl = Context.ROOT + Context.PATH + "/admin/admincompany/query.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:true
		});
		
	
		var tbar = [
//		            {
//			text:"添加",
//			iconCls:"add",
//			handler:function(){
//					var url=Context.ROOT+Context.PATH+"/admin/admincompany/edit.htm?ts="+Math.random();
//					window.open(url);
//			}
//		},
		{
			text:"修改",
			iconCls:"edit",
			handler:function(){
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
				else
					var row = grid.getSelections();
					var _cid=row[0].get("id");
					var _acc=row[0].get("account")
					//	var url=Context.ROOT+Context.PATH+"/admin/bbs/posts/edit.htm?id="+_pid+"&ts="+Math.random();
					var url=Context.ROOT+Context.PATH+"/admin/admincompany/edit.htm?companyId="+_cid+"&account="+_acc+"&ts="+Math.random();
					window.open(url);
			}
		},
//		{
//			text:"删除",		
//			iconCls:"delete",
//			handler:function(){
//				if (sm.getCount() == 0)
//					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
//				else
//					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的' + sm.getCount()+ '条记录?', 
//					function(btn){
//						if(btn=="yes"){
//								var row = grid.getSelections();
//								var _ids = new Array();
//								for (var i=0,len = row.length;i<len;i++){
//									var _id=row[i].get("id");
//									_ids.push(_id);
//								}
//								var conn = new Ext.data.Connection();
//								conn.request({
//									url: Context.ROOT+Context.PATH + "/admin/admincompany/delete.htm?ids="+_ids.join(','),
//									method : "get",
//									scope : this,
//									callback : function(options,success,response){
//									var res= Ext.decode(response.responseText);
//										if(res.success){
//											grid.getStore().reload();
//										}else{
//											Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
//										}
//									}
//								});
//						}
//				});
//			}
//		},
//		{
//			text:"更改状态",
//			iconCls : 'edit',
//			menu:[{
//				text:"加入黑名单",
//				handler:function(btn){
//					updatState("10191001");
//				}
//			},{
//				text:"冻结",
//				handler:function(btn){
//					updatState("10191002");
//				}
//			},{
//				text:"恢复正常",
//				handler:function(btn){
//					updatState("10191000");
//				}
//			}]
//		},
		
//		,{
//			xtype:"checkbox",
//			boxLabel:"再生通",
//			id:"uncheckBtn",
//			checked:false,
//			listeners:{
//				"check":function(field,newvalue,oldvalue){
//					grid.searchByCheckStatus();
//				}
//			}
//		},{
//			xtype:"checkbox",
//			boxLabel:"品牌通",
//			id:"checkedBtn",
//			listeners:{
//				"check":function(field,newvalue,oldvalue){
//					grid.searchByCheckStatus();
//				}
//			}
//		},{
//		xtype:"checkbox",
//		boxLabel:"再生通",
//		id:"uncheckBtn",
//		checked:false,
//		listeners:{
//			"check":function(field,newvalue,oldvalue){
//				grid.searchByCheckStatus();
//			}
//		}
//	},{
//		xtype:"checkbox",
//		boxLabel:"品牌通",
//		id:"checkedBtn",
//		listeners:{
//			"check":function(field,newvalue,oldvalue){
//				grid.searchByCheckStatus();
//			}
//		}
//	}
		"->","按类型",{
			xtype:"combo",
			mode:"local",
			width:100,
			triggerAction:"all",
			store:[
			    ["10051000","普通会员"],
			    ["10051001","再生通会员"],
			    ["10051002","品牌通会员"]
			],
			listeners:{
			"change":function(field,newvalue,oldvalule){
				var B=_store.baseParams||{};
				if(newvalue==""){
					B["membershipCode"]=undefined;
				}else{
					B["membershipCode"]=newvalue;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
		}];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
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
			tbar : tbar
		};
		ast.ast1949.admin.adminCompany.resultGrid.superclass.constructor.call(this,c);
	}
//,
//	searchByCheckStatus:function(){
//		
//		var B=this.getStore().baseParams||{};
//			var ary = new Array();
//			if(Ext.getCmp("uncheckBtn").getValue()){
//				ary.push(10051001);
//			}
//			if(Ext.getCmp("checkedBtn").getValue()){
//				ary.push(10051002);
//			}
//
//		B["membershipCode"] = ary.join(",");
//		this.getStore().baseParams = B;
//		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//	}
});

/**
 * 公司搜索表单
 * */
ast.ast1949.admin.adminCompany.searchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 80,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "公司名：",
//					name : "name",
//					id : "search-name",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["name"] = undefined;
							}else{
								B["name"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "帐号：",
//					name : "account",
//					id : "search-account",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["account"] = undefined;
							}else{
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}
//				,{
//					fieldLabel : "电话：",
//					name : "tel",
//					id : "search-tel",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["tel"] = undefined;
//							}else{
//								B["tel"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				}
				,{
					fieldLabel : "邮箱：",
//					name : "email",
//					id : "search-email",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["email"] = undefined;
							}else{
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "手机号码：",
//					name : "mobile",
//					id : "search-title",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["mobile"] = undefined;
							}else{
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "地址：",
//					name : "address",
//					id : "search-address",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							B["address"] = newvalue;
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
//					id:"search-infoSourceCode_combo",
//					name:"search-infoSourceCode",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"主营行业：",
					displayField : "label",
					valueField : "code",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:false,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
					}),
					listeners:{
						"change":function(field){
							B["industryCode"] =  field.getValue();
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combotree",
					fieldLabel:"地区：",
//					id : "combo-area",
//					name : "combo-area",
					hiddenName : "search-areaCode",
					hiddenId : "search-areaCode",
					editable:true,
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							fields : [ "label", "code" ],
							autoLoad: false,
							url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
							listeners:{
								beforeload:function(treeload,node){
									this.baseParams["parentCode"] = node.attributes["data"];
								}
							}
						}),
				   	 	root : new Ext.tree.AsyncTreeNode({text:'地区',data:Context.CATEGORY.areaCode})
					}),
					listeners:{
						"blur":function(field){
							if(Ext.get("search-areaCode").dom.value!=""){
								B["areaCode"] =  Ext.get("search-areaCode").dom.value;
							}else{
								B["areaCode"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				}]
//				,{
//					xtype:"combo",
//					id:"search-gardenCode_combo",
//					name:"search-gardenCode",
////					hiddenId:"search-gardenCode",
//					triggerAction : "all",
//					forceSelection : true,
//					fieldLabel:"园区：",
//					displayField : "label",
//					valueField : "code",
//					store:new Ext.data.JsonStore( {
//						root : "records",
//						fields : [ "label", "code" ],
//						autoLoad:false,
//						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["gardenTypeCode"],
//						listeners :{
//						  load:function(){
//						  }
//						}
//					}),
//					listeners:{
//						"blur":function(field){
//							if(Ext.get(field.getId()).dom.value!=""){
//								B["gardenCode"] =  field.getValue();
//							}else{
//								B["gardenCode"] = undefined;
//							}
//							_store.baseParams = B;
//						}
//					}
//				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};

		ast.ast1949.admin.adminCompany.searchForm.superclass.constructor.call(this,c);

	}
});

//function updatState(value){
//	
//		var grid=Ext.getCmp(COMPANY.RESULT_GRID)
//		var row = grid.getSelections();
//		if(row==""){
//			Ext.Msg.alert(Context.MST_TITLE, "请选定一条记录更改");
//		}
//		else
//			var id=row[0].get("id");
//			var account = row[0].get("account");
//			var val =value;
//			
//			var conn = new Ext.data.Connection();
//			conn.request({
//				url: Context.ROOT+Context.PATH + "/admin/admincompany/gradeaccess.htm?ids="+id+"&val="+val+"&account="+account,
//				method : "get",
//				scope : this,
//				callback : function(options,success,response){
//				var res= Ext.decode(response.responseText);
//				if(res.success){
//					Ext.MessageBox.alert(Context.MSG_TITLE,"修改成功!");
//					grid.getStore().reload();
//				}else{
//					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录修改失败!");
//				}
//			}
//			});
//}