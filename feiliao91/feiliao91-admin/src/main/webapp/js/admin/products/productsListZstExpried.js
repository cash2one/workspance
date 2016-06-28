Ext.namespace("ast.ast1949.admin.products");

/**
 * 用于提供搜索输入框的表单
 * */
ast.ast1949.admin.products.searchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
//			layout : "column",
//			region : "north",
//			height : 185,
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
					fieldLabel : "供需",
					name : "title",
					id : "search-title",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["title"] = undefined;
							}else{
								B["title"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}
//				,{
//					xtype : "hidden",
//					name : "categoryProductsCode",
//					id : "search-categoryProductsCode"
//				}
				,{
					fieldLabel : "公司",
					name : "name",
					id : "search-name",
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
					fieldLabel : "发布人(Email)",
					name : "email",
					id : "search-email",
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
					fieldLabel : "供需简介",
					id : "search-details",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["details"] = undefined;
							}else{
								B["details"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					id:"search-infoSourceCode_combo",
					name:"search-infoSourceCode",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"信息来源",
					displayField : "label",
					valueField : "code",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:false,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["infoSourceCode"]
					}),
					listeners:{
						"blur":function(field,newvalue,oldvalue){
							if(newvalue!=""){
								B["infoSourceCode"] = newvalue;
							} else {
								B["infoSourceCode"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combotree",
					fieldLabel:"区域",
					id : "combo-area",
					name : "combo-area",
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
				   	 	root : new Ext.tree.AsyncTreeNode({text:'全部区域',data:Context.CATEGORY.areaCode})
					}),
					listeners:{
						"blur":function(field){
//							alert(Ext.get(field.getId()).dom.value)
							if(Ext.get("search-areaCode").dom.value!=""){
								B["areaCode"] =  Ext.get("search-areaCode").dom.value;
							}else{
								B["areaCode"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype : "datefield",
					fieldLabel : "刷新时间开始",
					name : "startTimeStr",
					id : "search-startTime",
					format : 'Y-m-d',
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["startTimeStr"] = undefined;
							}else{
								B["startTimeStr"] = newvalue.format("Y-m-d");
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					fieldLabel : "刷新时间结束",
					name : "endTimeStr",
//					id : "search-endTime",
					format : 'Y-m-d',
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
//								alert(B["endTime"])
								B["endTimeStr"] = undefined;
							}else{
								B["endTimeStr"] = newvalue.format("Y-m-d");
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					id:"search-gardenCode_combo",
					name:"search-gardenCode",
//					hiddenId:"search-gardenCode",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"园区",
					displayField : "label",
					valueField : "code",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:false,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["gardenTypeCode"],
						listeners :{
						  load:function(){
						  }
						}
					}),
					listeners:{
						"blur":function(field){
							if(Ext.get(field.getId()).dom.value!=""){
								B["gardenCode"] =  field.getValue();
							}else{
								B["gardenCode"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};

		ast.ast1949.admin.products.searchForm.superclass.constructor.call(this,c);

	}
});

/**
 * 用于显示各种条件查询后的供求信息列表
 * */
ast.ast1949.admin.products.resultGrid = Ext.extend(Ext.grid.GridPanel,{
	readOnly:false,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _this=this;

		var sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel([sm,{
				header : "编号",
				width : 60,
				hidden:true,
				dataIndex : "id"
			},{
				header:"审核状态",
				dataIndex : "checkStatus",
				width:50,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var status=record.get("checkStatus");
					
					if(status=="1"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="2"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"高",
				dataIndex : "uncheckedCheckStatus",
				width:50,
				hidden:false,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(value==2){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"供/求",
				dataIndex : "productsTypeLabel",
				width:80,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					return value;
				}
			},{
				header:"类别",
				dataIndex : "categoryProductsMainLabel",
				width:80
			}, {
				header : "供求标题",
				width : 220,
				dataIndex : "title",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var webstr="<a href='http://trade.zz91.com/productdetails"+record.get("id")+".htm' target='_blank' >";
					webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
					var title=value;
					if(!_this.readOnly){
						title=" <a href='"+
							Context.ROOT+Context.PATH+
							"/admin/products/edit.htm?productid="+record.get("id")+
							"&companyid="+record.get("companyId")+
							"&account="+record.get("account")+
							"' target='_blank'>"+value+"</a>";
					}
					return webstr+title;
				}
			}, {
				header : "公司名称",
				width : 180,
				dataIndex : "companyName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")!="10051000"){
						val="<img src='"+Context.ROOT+"/images/recycle"+record.get("membershipCode")+".gif' />";
					}
					
					if(_this.readOnly){
						val= val + value;
					}else{
						val= val + "<a href='" + Context.ROOT + Context.PATH + 
							"/crm/company/detail.htm?companyId=" + 
							record.get("companyId") + "' target='_blank'>" + 
							value + "</a>";
					}
					return val;
				}
			}
//			, {
//				header : "审核发布时间",
//				sortable : false,
//				dataIndex : "refreshTime",
//				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
//					if (value != null) {
//						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
//					}
//				}
//			}
			, {
				header : "发布时间",
				sortable : true,
				width:80,
				dataIndex : "real_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			}, {
				header:"刷新时间",
				sortable:true,
				width:80,
				dataIndex:"refresh_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			}, {
				header : "审核人",
				sortable : true,
				width:80,
				dataIndex : "check_person"
			},{
				header : "审核时间",
				width:130,
				sortable : false,
				dataIndex : "checkTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}]
		);
				// 字段信息
		var reader = [{name:"id",mapping:"products.id"},
			  {name:"real_time",mapping:"products.realTime"},
			  {name:"refresh_time",mapping:"products.refreshTime"},
			  {name:"title",mapping:"products.title"},
			  {name:"companyId",mapping:"products.companyId"},
			  {name:"account",mapping:"products.account"},
			  {name:"check_person",mapping:"products.checkPerson"},
			  {name:"checkStatus",mapping:"products.checkStatus"},
			  {name:"checkTime",mapping:"products.checkTime"},
			  {name:"uncheckedCheckStatus",mapping:"products.uncheckedCheckStatus"},
			  {name:"categoryProductsMainCode",mapping:"products.categoryProductsMainCode"},
			  {name:"categoryProductsAssistCode",mapping:"products.categoryProductsAssistCode"},
			  {name:"categoryProductsMainLabel",mapping:"categoryProductsMainLabel"},
			  {name:"categoryProductsAssistLabel",mapping:"categoryProductsAssistLabel"},
			  {name:"productsTypeLabel",mapping:"productsTypeLabel"},
			  {name:"companyName",mapping:"company.name"},
			  {name:"membershipCode",mapping:"company.membershipCode"}];
//		              "productId", "realTime", "refreshTime", "productTitle",
//			"productTypeName", "infoSourceName", "companyId","account", "companyName",
//			"checkPerson", "companyName","checkStatus"]

		var storeUrl = Context.ROOT + Context.PATH + "/admin/products/listProductsZstExpried.htm";

		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:false
		});
		var tbar = this.toolbar;
		var c={
			id:"productsresultgrid",
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:10,
			cm : cm,
//			plugins : expander,
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

		ast.ast1949.admin.products.resultGrid.superclass.constructor.call(this,c);
	},
	searchByCheckStatus:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("uncheckBtn").getValue()){
			ary.push(0);
		}
		if(Ext.getCmp("checkedBtn").getValue()){
			ary.push(1);
		}
		if(Ext.getCmp("rejectedBtn").getValue()){
			ary.push(2);
		}
		if(ary.length>0){
			B["statusArray"] = ary.join(",");
		}else{
			B["statusArray"] = undefined;
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByCompany:function(companyId){
		var B=this.getStore().baseParams||{};
		B["companyId"] = companyId;
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	toolbar:[{
		text:"编辑",
		iconCls : 'edit',
		menu:[{
			text:"编辑选中供求",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					window.open(Context.ROOT+Context.PATH
						+"/admin/products/edit.htm?productid="+row[i].get("id")
						+"&companyid="+row[i].get("companyId")
						+"&account="+row[i].get("account"));
				}
			}
		},{
			text:"导出为询盘",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				if (row.length > 0){
					for (var i=0,len = row.length;i<len;i++){
						var _id=row[i].get("id");
						window.open(Context.ROOT+Context.PATH+"/admin/products/createInquiry.htm?id="+_id);
					}
				}
			}
		},{
			text:"设为稀缺产品",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				if (row.length == 0){
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");
				}else if (row.length  > 1){
					Ext.Msg.alert(Context.MSG_TITLE, "最多只能编辑一条记录")
				}else {
					//设为稀缺
					window.open(Context.ROOT + Context.PATH + "/admin/products/createProductsrare.htm?id=" + row[0].get("id"));
				}
			}
		},{
			text:"购买关键字",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				if (row.length == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");
				else if (row.length  > 1)
					Ext.Msg.alert(Context.MSG_TITLE, "最多只能选择一条记录")
				else {
					window.open(Context.ROOT+Context.PATH+"/admin/keywords/addnew.htm?id="+row[0].get("id")+"&companyId="+row[0].get("companyId")+"&account="+row[0].get("account"));
				}
			}
		},"-",{
			text:"推荐",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				if (row.length == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");
				else if (row.length  > 1)
					Ext.Msg.alert(Context.MSG_TITLE, "最多只能选择一条记录")
				else {
					ast.ast1949.admin.dataIndex.SendIndex({
						title:row[0].get("title"),
						link:"http://china.zz91.com/trade/productdetails"+row[0].get("id")+".htm"
					});
				}
			}
		},{
			text:"推荐(供求专用)",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				if (row.length == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条以上记录");
				else {
					var tree=new ast.ast1949.admin.dataIndex.CategoryTree({
						id:"categoryTree",
						contextmenu:null
					});
					
					var win=new Ext.Window({
						layout:"fit",
						height:400,
						width:250,
						items:[tree],
						buttons:[{
							text:"推荐到这里",
							scope:this,
							handler:function(){
								var node = Ext.getCmp("categoryTree").getSelectionModel().getSelectedNode();
								node.attributes["data"]
								for(var i=0;i<row.length;i++){
									Ext.Ajax.request({
										url: Context.ROOT+Context.PATH+ "/admin/productsindex/productsIndex.htm",
										params:{
											"productId":row[i].get("id"),
											"categoryCode":node.attributes["data"]
										},
										success:function(response,opt){
											var obj = Ext.decode(response.responseText);
											if(obj.success){
												ast.ast1949.utils.Msg("","信息已推荐，如需更改，请到交易中心信息推荐修改")
												win.close();
											}else{
												ast.ast1949.utils.Msg("","操作失败");
											}
										},
										failure:function(response,opt){
											ast.ast1949.utils.Msg("","操作失败");
										}
									});
								}
							}
						},{
							iconCls:"item-exit",
							text:"关闭",
							handler:function(){
								win.close();
							}
						}]
					});
					
					win.show();
//					for(var i=0;i<row.length;i++){
//						
//					}
					
				}
			}
		}]
	},"-",
	{
		text:"审核",
		menu:[{
			text:"通过",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.check(1);
			}
		},
		{
			text:"不通过",
			handler:function(btn){
				Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
					if (btn == 'ok'){
						var grid = Ext.getCmp("productsresultgrid");
						grid.check(2,text);
					}
				}, this,true,"供求信息产品名称填写不明确：建议您一条信息只填写一种类型的发布与废料相关的产品、设备、服务等具体产品，并且只须填写产品名称或服务即可，如PP颗粒（多产品或标题过长都将非常不利于您的信息在ZZ91或百度谷歌等各大搜索引擎被搜索到），这样可以大大提升您信息在ZZ91或百度、谷歌等各大搜索引擎被查看到的机率。");
			}
		}]
	},{
		text:"快速查询",
		iconCls:"query",
		menu:[{
			text:"查看全部",
			handler:function(btn){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				_store.baseParams = {};
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		},{
			text:"前台发布的",
			handler:function(btn){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				B["postType"] = 0;
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		},{
			text:"后台发布的",
			handler:function(btn){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				B["postType"] = 1;
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		},{
			text:"询盘导出的",
			handler:function(btn){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				B["isPostFromInquiry"] = 1;
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		},{
			text:"受限时发布的",
			handler:function(btn){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				B["isPostWhenViewLimit"]=1;
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		},{
			text:"无标签产品",
			hidden:true,
			handler:function(btn){
				//TODO 暂时不做
			}
		}]
//		,{
//			text:"稀缺产品",
//			handler:function(btn){
//				var _store = Ext.getCmp("productsresultgrid").getStore();
//				var B={};
//				B["isRare"]=1;
//				_store.baseParams = B;
//				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//			}
//		}]
	},"->","搜索",{
		xtype:"textfield",
		width:"120",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["title"] = field.getValue();
				}else{
					B["title"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"供/求 ",{
		xtype:"combo",
		id:"search-productsTypeCode_combo",
		name:"search-productsTypeCode",
		triggerAction : "all",
		forceSelection : true,
		displayField : "label",
		valueField : "code",
		width:80,
		store:new Ext.data.JsonStore( {
			root : "records",
			fields : [ "label", "code" ],
			autoLoad:false,
			url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["productsTypeCode"],
			listeners :{
			  load:function(){
			  }
			}
		}),
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["productsTypeCode"] = field.getValue();
				}else{
					B["productsTypeCode"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}," ",{
		xtype:"checkbox",
		boxLabel:"高级用户",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				if(field.getValue()){
					B["membershipCode"] = "10051001";
				}else{
					B["membershipCode"] = "10051000";
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},{
		xtype:"checkbox",
		boxLabel:"未审核",
		id:"uncheckBtn",
		checked:true,
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
			}
		}
	}
	," ",{
		xtype:"checkbox",
		boxLabel:"通过",
		id:"checkedBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
			}
		}
	}
	," ",{
		xtype:"checkbox",
		boxLabel:"不通过",
		id:"rejectedBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
			}
		}	
	}
	],
	check:function(checkStatus,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateCheckStatus.htm",
				params:{
					"productId":rows[i].get("id"),
					"checkStatus":checkStatus,
					"companyId":rows[i].get("companyId"),
					"unpassReason":msg
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						grid.getStore().reload();
					}else{
						com.zz91.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","操作失败");
				}
			});
		}
		
	}
});

