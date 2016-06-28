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
			layout : "column",
			items:[{
//				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"combo",
					mode:"local",
					fieldLabel:"快速查询",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					store:new Ext.data.JsonStore({
						fields : ['name', 'value'],
						data   : [
							{name:'查看全部',value:0},
							{name:'前台发布',value:1},
							{name:'后台发布',value:2},
							{name:'询盘导出',value:3},
							{name:'受限时发布',value:4},
							{name:'已转询盘的供求',value:5},
							{name:'未转询盘的供求',value:6}
						]
					}),
					listeners:{
						"blur":function(field,newvalue,oldvalue){
							if(field.getValue() == 0){
								B["postType"] = "";
								B["isPostFromInquiry"] = "";
								B["isPostWhenViewLimit"] = "";
							}
							if(field.getValue() == 1){
								B["postType"] = 0;
							}
							if(field.getValue() == 2){
								B["postType"] = 1;
							}
							if(field.getValue() == 3){
								B["isPostFromInquiry"] = 1;
							}
							if(field.getValue() == 4){
								B["isPostWhenViewLimit"] = 1;
							}
							if(field.getValue() == 5){
								B["isExportInquiry"]=1;
								B["isNotExportInquiry"]=undefined;
							}
							if(field.getValue() == 6){
								B["isExportInquiry"]=undefined;
								B["isNotExportInquiry"]=1;
								
							}
							_store.baseParams = B;
						}
					}
				},{
						xtype:"combo",
						id:"selectTime",
						mode:"local",
						emptyText:"请选择...",
						fieldLabel:"审核状态：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						autoSelect:true,
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'审核时间',value:'check_time'},
								{name:'发布时间',value:'real_time'},
								{name:'刷新时间',value:'refresh_time'}
							]
						}),
						listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["selectTime"] = field.getValue();
								}else{
									B["selectTime"]=undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype : "datefield",
						format:"Y-m-d",
						name:"from",
						fieldLabel:"时间(始)",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									_store.baseParams["from"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:m:s');
								}else{
									_store.baseParams["from"]=null;
								}
							}
						}
					},{
						xtype : "datefield",
						format:"Y-m-d",
						name : "to",
						fieldLabel:"时间(尾)",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									_store.baseParams["to"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:m:s');
								}else{
									_store.baseParams["to"]=null;
								}
							}
						}
					},{
						xtype:"textfield",
						fieldLabel:"关键字搜索",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									B["title"] = field.getValue();
								}else{
									B["title"]=undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						fieldLabel:"供/求",
						id:"search-productsTypeCode_combo",
						name:"search-productsTypeCode",
						triggerAction : "all",
						forceSelection : true,
						displayField : "label",
						valueField : "code",
						width:50,
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
								if(Ext.get(field.getId()).dom.value!=""){
									B["productsTypeCode"] = field.getValue();
								}else{
									B["productsTypeCode"]=undefined;
								}
								_store.baseParams = B;
							}
						}
					}
				]
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
				hidden:true,
				dataIndex : "id"
			},{
				header:"供/求",
				dataIndex : "productsTypeLabel",
				width:50,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					return value;
				}
			},{
				header : "供求标题",
				width : 300,
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
			},{
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
			},{
				header : "发布时间",
				sortable : true,
				width:80,
				dataIndex : "real_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			},{
				header:"刷新时间",
				sortable:true,
				width:80,
				dataIndex:"refresh_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			},{
				header : "审核时间",
				width:100,
				sortable : true,
				dataIndex : "check_time",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header : "操作",
				sortable : true,
				width:80,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='"+Context.ROOT+Context.PATH+"/admin/products/createInquiry.htm?id="+record.get("id")+"' target='blank'>转询盘</a>";
				}
			},{
				header : "次数",
				sortable : true,
				width:80,
				dataIndex : "countInquiry",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='"+Context.ROOT+Context.PATH+"/admin/inquiry/listOfProductExport.htm?productId="+record.get("id")+"' target='blank'>"+value+"</a>";
				}
			},{
				header : "群发时间",
				sortable : true,
				width:80,
				dataIndex : "gmtInquiryStr"
			}
			]
		);
				// 字段信息
		var reader = [{name:"id",mapping:"products.id"},
			  {name:"real_time",mapping:"products.realTime"},
			  {name:"isDel",mapping:"products.isDel"},
			  {name:"refresh_time",mapping:"products.refreshTime"},
			  {name:"title",mapping:"products.title"},
			  {name:"companyId",mapping:"products.companyId"},
			  {name:"account",mapping:"products.account"},
			  {name:"check_person",mapping:"products.checkPerson"},
			  {name:"checkStatus",mapping:"products.checkStatus"},
			  {name:"check_time",mapping:"products.checkTime"},
			  {name:"uncheckedCheckStatus",mapping:"products.uncheckedCheckStatus"},
			  {name:"categoryProductsMainCode",mapping:"products.categoryProductsMainCode"},
			  {name:"categoryProductsAssistCode",mapping:"products.categoryProductsAssistCode"},
			  {name:"categoryProductsMainLabel",mapping:"categoryProductsMainLabel"},
			  {name:"categoryProductsAssistLabel",mapping:"categoryProductsAssistLabel"},
			  {name:"productsTypeLabel",mapping:"productsTypeLabel"},
			  {name:"companyName",mapping:"company.name"},
			  {name:"membershipCode",mapping:"company.membershipCode"},
			  {name:"countInquiry",mapping:"countInquiry"},
			  {name:"gmtInquiryStr",mapping:"gmtInquiryStr"}
			  ];

		var storeUrl = Context.ROOT + Context.PATH + "/admin/products/listProductsExportInquiry.htm";

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
	searchByAutoCheck:function(){
		var B=this.getStore().baseParams||{};
		if(Ext.getCmp("autoPassBtn").getValue()){
			B["checkPerson"] = "zz91-auto-check";
		}else{
			B["checkPerson"] = "";
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
						link:"http://trade.zz91.com/productdetails"+row[0].get("id")+".htm"
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
		},"-",{
			text:"推荐为现货供求",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkToSpot();
			}
		},{
			text:"(删除信息)恢复操作",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkIsDel(0);
			}
		}
		]
	},"-",{
		text:"审核",
		menu:[{
			text:"通过",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.check(1);
				grid.updateForRub(1);
			}
		},{
			text:"不通过",
			handler:function(btn){
				Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
					if (btn == 'ok'){
						var grid = Ext.getCmp("productsresultgrid");
						grid.check(2,text);
					}
				}, this,true,"供求信息产品名称填写不明确：建议您一条信息只填写一种类型的发布与废料相关的产品、设备、服务等具体产品，并且只须填写产品名称或服务即可，如PP颗粒（多产品或标题过长都将非常不利于您的信息在ZZ91或百度谷歌等各大搜索引擎被搜索到），这样可以大大提升您信息在ZZ91或百度、谷歌等各大搜索引擎被查看到的机率。");
			}
		},{
			text:"不通过并显示在sb频道",
			handler:function(btn){
				Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
					if (btn == 'ok'){
						var grid = Ext.getCmp("productsresultgrid");
						grid.check(2,text);
						grid.updateForRub();
					}
				}, this,true,"供求信息产品名称填写不明确：建议您一条信息只填写一种类型的发布与废料相关的产品、设备、服务等具体产品，并且只须填写产品名称或服务即可，如PP颗粒（多产品或标题过长都将非常不利于您的信息在ZZ91或百度谷歌等各大搜索引擎被搜索到），这样可以大大提升您信息在ZZ91或百度、谷歌等各大搜索引擎被查看到的机率。");
			}
		}]
	},"-",{
		text:"高审核",
		menu:[{
			text:"通过",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkGaohui(1);
			}
		},{
			text:"不通过",
			handler:function(btn){
				Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
					if (btn == 'ok'){
						var grid = Ext.getCmp("productsresultgrid");
						grid.checkGaohui(2,text);
					}
				}, this,true,"供求信息产品名称填写不明确：建议您一条信息只填写一种类型的发布与废料相关的产品、设备、服务等具体产品，并且只须填写产品名称或服务即可，如PP颗粒（多产品或标题过长都将非常不利于您的信息在ZZ91或百度谷歌等各大搜索引擎被搜索到），这样可以大大提升您信息在ZZ91或百度、谷歌等各大搜索引擎被查看到的机率。");
			}
		}]
	},"-",{
		id:"exportDate",
		xtype : "datefield",
		format:"Y-m-d",
		name:"from",
		emptyText:"日期"
	},{
    	iconCls:"add",
		text:"导出",
		handler:function(btn){
			var grid = Ext.getCmp("productsresultgrid");
    		grid.exportPrice();
		}
	},"->",{
		xtype:"checkbox",
		boxLabel:"<span style='color:red'>高会</span>",
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
	},"-",{
		xtype:"checkbox",
		boxLabel:"未审核",
		id:"uncheckBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
			}
		}
	},"-",{
		xtype:"checkbox",
		boxLabel:"通过",
		id:"checkedBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
			}
		}
	}," ",{
		xtype:"checkbox",
		boxLabel:"退回",
		id:"rejectedBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
			}
		}	
	},"-",{
		xtype:"checkbox",
		boxLabel:"自动过滤",
		id:"autoPassBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByAutoCheck();
			}
		}	
	},"-",{
		xtype:"combo",
		id:"isDel",
		mode:"local",
		emptyText:"删除状态",
		fieldLabel:"删除状态：",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		autoSelect:true,
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'已删除',value:1},
				{name:'未删除',value:0}
			]
		}),
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isDel"] = field.getValue();
				}else{
					B["isDel"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"-",{
		xtype:"combo",
		id:"isPause",
		mode:"local",
		emptyText:"发布状态",
		fieldLabel:"发布状态：",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		autoSelect:true,
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'暂不发布',value:1},
				{name:'发布',value:0}
			]
		}),
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isPause"] = field.getValue();
				}else{
					B["isPause"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}],
	checkIsDel:function(checkStatus,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateIsDel.htm",
				params:{
					"productId":rows[i].get("id"),
					"status":checkStatus
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","操作失败");
				}
			});
		}
	},
	checkToSpot:function(){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateToSpot.htm",
				params:{
					"productId":rows[i].get("id")
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("","操作失败(已推荐)");
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","操作失败");
				}
			});
		}
	},
	checkGaohui:function(checkStatus,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateGaoCheckStatus.htm",
				params:{
					"productId":rows[i].get("id"),
					//"membershipCode":rows[i].get("membershipCode"),
					"checkStatus":checkStatus,
					//"isDel":rows[i].get("isDel"),
					//"companyId":rows[i].get("companyId"),
					"unpassReason":msg
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","操作失败");
				}
			});
		}
	},
	check:function(checkStatus,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateCheckStatus.htm",
				params:{
					"productId":rows[i].get("id"),
					"membershipCode":rows[i].get("membershipCode"),
					"checkStatus":checkStatus,
					"isDel":rows[i].get("isDel"),
					"companyId":rows[i].get("companyId"),
					"unpassReason":msg
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("",obj.data);
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","操作失败");
				}
			});
		}
	},
	updateForRub:function(checkStatus){
		var grid = Ext.getCmp("productsresultgrid");
		var url = "/admin/products/addProductToRub.htm";
		if(checkStatus==1){
			url = "/admin/products/removeProductToRub.htm";
		}
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+url,
				params:{
					"productId":rows[i].get("id")
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
	},
	exportPrice:function(){
		var date = Ext.get("exportDate").dom.value;
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
			if(btn!="yes"){
				return ;
			}else{
				Ext.Ajax.request({
					url: window.open(Context.ROOT+Context.PATH+ "/admin/products/exportPassData.htm?" + "date="+date)
				});
			}
		});
	}
});

