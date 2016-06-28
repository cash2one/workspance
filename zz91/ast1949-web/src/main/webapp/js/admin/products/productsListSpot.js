Ext.namespace("ast.ast1949.admin.products");

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
				header:"供/求",
				dataIndex : "productsTypeLabel",
				width:50,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					return value;
				}
			},{
				header:"产品类目",
				dataIndex : "categoryProductsMainLabel",
				width:100,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					return value;
				}
			}, {
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
				header : "最小价格(元/吨)",
				sortable : true,
				width:80,
				dataIndex : "minPrice"
			},{
				header : "最大价格(元/吨)",
				sortable : true,
				width:80,
				dataIndex : "maxPrice"
			},{
				header : "数量(吨)",
				sortable : true,
				width:80,
				dataIndex : "quantity"
			},{
				header : "地区",
				sortable : true,
				width:100,
				dataIndex : "location"
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
				header : "联系人",
				sortable : true,
				width:80,
				dataIndex : "contact"
			},{
				header : "电话",
				sortable : true,
				width:120,
				dataIndex : "mobile"
			},{
				header:"刷新时间",
				sortable:true,
				width:80,
				dataIndex:"refresh_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:m:s');
					}
				}
			},{
				header : "特",
				sortable : true,
				width:50,
				dataIndex : "isTe",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return "yes";
					}
				}
			},{
				header : "优",
				width:50,
				sortable : true,
				dataIndex : "isYou",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return "yes";
					}
				}
			},{
				header : "热",
				width:50,
				sortable : true,
				dataIndex : "isHot",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return "yes";
					}
				}
			},{
				header : "保",
				width:50,
				sortable : true,
				dataIndex : "isBail",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return "yes";
					}
				}
			}]
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
			  {name:"checkTime",mapping:"products.checkTime"},
			  {name:"minPrice",mapping:"products.minPrice"},
			  {name:"maxPrice",mapping:"products.maxPrice"},
			  {name:"quantity",mapping:"products.quantity"},
			  {name:"location",mapping:"products.location"},
			  {name:"uncheckedCheckStatus",mapping:"products.uncheckedCheckStatus"},
			  {name:"categoryProductsMainCode",mapping:"products.categoryProductsMainCode"},
			  {name:"categoryProductsAssistCode",mapping:"products.categoryProductsAssistCode"},
			  {name:"categoryProductsMainLabel",mapping:"categoryProductsMainLabel"},
			  {name:"categoryProductsAssistLabel",mapping:"categoryProductsAssistLabel"},
			  {name:"productsTypeLabel",mapping:"productsTypeLabel"},
			  {name:"companyName",mapping:"company.name"},
			  {name:"mobile",mapping:"companyContacts.mobile"},
			  {name:"contact",mapping:"companyContacts.contact"},
			  {name:"isHot",mapping:"productsSpot.isHot"},
			  {name:"isTe",mapping:"productsSpot.isTe"},
			  {name:"isYou",mapping:"productsSpot.isYou"},
			  {name:"isBail",mapping:"productsSpot.isBail"},
			  {name:"spotId",mapping:"productsSpot.id"},
			  {name:"zstYear",mapping:"company.zstYear"},
			  {name:"membershipCode",mapping:"company.membershipCode"}];
//		              "productId", "realTime", "refreshTime", "productTitle",
//			"productTypeName", "infoSourceName", "companyId","account", "companyName",
//			"checkPerson", "companyName","checkStatus"]

		var storeUrl = Context.ROOT + Context.PATH + "/admin/products/listProductsSpot.htm";

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
		},"-",{
			text:"推荐为现货供求",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkToSpot();
			}
		},{
			text:"取消现货供求",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkOutSpot();
			}
		},"-",{
			text:"推荐至首页模块",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.admin.dataIndex.SendIndex({
						title:["{"+"'contact':'"+row.get("contact")+"'",
						       "'title':'"+row.get("title")+"'",
						       "'location':'"+row.get("location")+"'",
						       "'isBail':'"+row.get("isBail")+"'",
						       "'mobile':'"+row.get("mobile")+"'",
						       "'zstYear':'"+row.get("zstYear")+"'",
						       "'productsTypeLabel':'"+row.get("productsTypeLabel")+"'",
						       "'membershipCode':'"+row.get("membershipCode")+"'"+"}"],
						link:"http://xianhuo.zz91.com/detail"+row.get("spotId")+".htm"
					});
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		},"-",{
			text:"推荐至促销区",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.spot.SendPromotion({
						spotId:row.get("spotId"),
						productId:row.get("id")
					});
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		},"-",{
			text:"推荐至竞拍区",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelectionModel().getSelected();
				if(row){
					ast.ast1949.spot.SendAuction({
						spotId:row.get("spotId"),
						productId:row.get("id")
					});
				}else{
					Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
				}	
				
			}
		}]
	},"-",{
		text:"特",
		menu:[{
			text:"设置特",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息设置为特?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateTe(1);
					});
				}
			}
		},{
			text:"取消特",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息取消特?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateTe(0);
					});
				}
			}
		}]
	},"-",{
		text:"优",
		menu:[{
			text:"设置优",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息设置优?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateYou(1);
					});
				}
			}
		},{
			text:"取消优",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息取消优?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateYou(0);
					});
				}
			}
		}]
	},"-",{
		text:"热",
		menu:[{
			text:"设置热",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息设置热?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateRe(1);
					});
				}
			}
		},{
			text:"取消热",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息取消热?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateRe(0);
					});
				}
			}
		}]
	},{
		text:"保证金",
		menu:[{
			text:"已交保证金",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息设置为已交保证金?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateBail(1);
					});
				}
			}
		},{
			text:"未交取消抵押",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要将选中的 ' + row.length + '条记录信息设置为未交保证金?', function(_btn){
						if (_btn != "yes")
							return;
						var grid = Ext.getCmp("productsresultgrid");
						grid.updateBail(0);
					});
				}
			}
		}]
	},"->","搜索",{
		xtype:"textfield",
		width:"60",
		id:"seachTitle",
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
	},"-","特/热/优/保",{
		xtype:"combo",
		mode:"local",
		triggerAction:"all",
		id:"isStatus",
		displayField:'name',
		valueField:'value',
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'优',value:"is_you"},
				{name:'特',value:"is_te"},
				{name:'热',value:"is_hot"},
				{name:'保',value:"is_bail"}
			]
		}),
		listeners:{
			"blur":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isStatus"] = field.getValue();
				}else{
					B["isStatus"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"->","ID搜索",{
		xtype:"textfield",
		width:"60",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["id"] = field.getValue();
				}else{
					B["id"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"-","现货数量",{
		xtype:"textfield",
		width:"60",
		id:"min",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["min"] = field.getValue();
				}else{
					B["min"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"--",{
		xtype:"textfield",
		width:"60",
		id:"max",
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["max"] = field.getValue();
				}else{
					B["max"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"吨","-",{
		text:"信息导出",
		iconCls:'add',
		handler:function(btn){
		var grid = Ext.getCmp("productsresultgrid");
			grid.exportComp();
		}
	},{
		xtype:"hidden",
		boxLabel:"未审核",
		id:"uncheckBtn",
		checked:true,
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("productsresultgrid");
				grid.searchByCheckStatus();
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
						com.zz91.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","操作失败");
				}
			});
		}
	},
	exportComp:function(){
		var _store = Ext.getCmp("productsresultgrid").getStore();
		var grid = Ext.getCmp("productsresultgrid");
		var B = _store.baseParams;
		var viewd=Ext.getCmp("isStatus").getValue();
		var title=Ext.getCmp("seachTitle").getValue();
		var min=Ext.getCmp("min").getValue();
		var max=Ext.getCmp("max").getValue();
		var typeCode=Ext.getCmp("search-productsTypeCode_combo").getValue();
		var proCode=B["categoryProductsMainCode"];
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
			if(btn!="yes"){
				return ;
			}else{
				Ext.Ajax.request({
					url: window.open(Context.ROOT+Context.PATH+ "/admin/products/exportData.htm?" +
							"isStatus="+viewd+"&title="+title+"&productsTypeCode="+typeCode+"&min="+min+"" +
							"&max="+max+"&categoryProductsMainCode="+proCode+""),
				});
			}
		});
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
					com.zz91.utils.Msg("","操作失败");
				}
			});
		}
	},
	checkOutSpot:function(){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateOutSpot.htm",
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
					com.zz91.utils.Msg("","操作失败");
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
						com.zz91.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","操作失败");
				}
			});
		}
	},
	updateTe:function(isTe,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateTe.htm",
				params:{
					"productId":rows[i].get("id"),
					"isTe":isTe
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
	},
	updateRe:function(isHot,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateRe.htm",
				params:{
					"productId":rows[i].get("id"),
					"isHot":isHot
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
	},
	updateYou:function(isYou,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateYou.htm",
				params:{
					"productId":rows[i].get("id"),
					"isYou":isYou
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
	},
	updateBail:function(isBail,msg){
		var grid = Ext.getCmp("productsresultgrid");
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/products/updateBail.htm",
				params:{
					"productId":rows[i].get("id"),
					"isBail":isBail
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