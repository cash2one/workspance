Ext.namespace("ast.ast1949.trust.purchase");

var POST=new function(){
	this.GRID="postgrid";
}

ast.ast1949.trust.purchase.CHECKSTATUS=["未审核","通过","","退回"];

ast.ast1949.trust.purchase.FIELDS=[
	 {name:"id",mapping:"trustBuy.id"},
    {name:"buyNo",mapping:"trustBuy.buyNo"},
    {name:"detail",mapping:"trustBuy.detail"},
    {name:"title",mapping:"trustBuy.title"},
    {name:"categoryName",mapping:"categoryName"},
    {name:"isPause",mapping:"trustBuy.isPause"},
    {name:"company_name",mapping:"company.name"},
    {name:"company_id",mapping:"company.id"},
    {name:"contact",mapping:"account.contact"},
    {name:"tel",mapping:"account.mobile"},
    {name:"status",mapping:"trustBuy.status"},
    {name:"name",mapping:"trustDealer.name"},
    {name:"tb.gmt_created",mapping:"trustBuy.gmtCreated"},
    {name:"tb.gmt_modified",mapping:"trustBuy.gmtModified"},
    {name:"tb.gmt_refresh",mapping:"trustBuy.gmtRefresh"}
    
];

ast.ast1949.trust.purchase.PUBSUPPLYFIELD=[
	{name:"buyId",mapping:"trustBuy.id"},
	{name:"title",mapping:"trustBuy.title"},
//	{name:"detail",mapping:"trustBuy.detail"},
//	{name:"code",mapping:"trustBuy.code"},
//	{name:"color",mapping:"trustBuy.color"},
//	{name:"level",mapping:"trustBuy.level"},
//	{name:"status",mapping:"trustBuy.status"},
//	{name:"dealerId",mapping:"dealerId"},
	{name:"buyNo",mapping:"trustBuy.buyNo"}
//	,
//	{name:"quantity",mapping:"trustBuy.quantity"},
//	{name:"useful",mapping:"trustBuy.useful"},
//	{name:"areaCode",mapping:"trustBuy.areaCode"},
//	{name:"price",mapping:"trustBuy.price"},
//	{name:"areaLabel",mapping:"areaLabel"},
//	{name:"mobile",mapping:"trustBuy.mobile"},
//	{name:"companyContact",mapping:"trustBuy.companyContact"},
//	{name:"companyName",mapping:"trustBuy.companyName"}
];

ast.ast1949.trust.purchase.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.trust.purchase.FIELDS,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm
		                                  ,{
			 header:"编号",
	            hidden:true,
	            dataIndex:"id"
	        },{
	            header:"采购流水号",
	            sortable:false,
	            width:85,
	            dataIndex:"buyNo",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	var _id=record.get("id");
	            	var _url="";
	                if(value!=null){ 
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_id+"&companyId="+record.get("company_id")
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"采购内容",
	            sortable:false,
	            width:250,
	            dataIndex:"detail",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	var _id=record.get("id");
	            	var _url="";
	                if(value!=null){ 
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_id+"&companyId="+record.get("company_id")
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"产品",
	            sortable:false,
	            width:100,
	            dataIndex:"categoryName",
            	renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	        		var str = "";
	        		if(record.get("isPause")==1){
	        			str = " <span style='color:red'>[暂不发布]</span>";
	        		}
	        		return value + str;
	            }
	        },{
	        	header:"公司名称",
	        	sortable:false,
	        	width:100,
	        	dataIndex:"company_name",
	        	renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	        		var _url="";
	        		if(value!=null){
	                	if (record.get("company_id")>0) {
	                		_url=Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+record.get("company_id");
	                		return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
						}else{
							_url=Context.ROOT+Context.PATH+"/trust/crm/relate.htm?id="+record.get("id");
							return "<a href=\'" +_url+ "\' target='_blank'>[关联帐号]</a> " + value ;
						}
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"联系人",
	            sortable:false,
	            width:70,
	            dataIndex:"contact"
	        },{
	            header:"手机",
	            sortable:false,
	            width:100,
	            dataIndex:"tel"
	        },{
	            header:"状态",
	            sortable:true,
	            width:90,
	            dataIndex:"status",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value=="00"){
	                    return "未审核";
	                }else if(value=="01"){
	                    return "正在报价";
	                }else if(value=="02"){
	                    return "已有报价";
	                }else if(value=="03"){
	                    return "正在洽谈";
	                }else if(value=="04"){
	                    return "等待打款";
	                }else if(value=="05"){
	                    return "交易完成";
	                }else if(value=="06"){
	                    return "交易终止";
	                }else if(value=="99"){
	                    return "审核不通过";
	                }
	            }
	        },{
	            header:"交易员",
	            sortable:false,
	            width:70,
	            dataIndex:"name"
	        },{
	            header:"发布时间",
	            sortable:true,
	            dataIndex:"tb.gmt_created",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"更新时间",
	            sortable:true,
	            dataIndex:"tb.gmt_modified",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"刷新时间",
	            sortable:true,
	            dataIndex:"tb.gmt_refresh",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
	        }
	        ]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			autoExpandColumn:4,
			tbar:this.mytoolbar,
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
		
		ast.ast1949.trust.purchase.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/trust/queryChase.htm",
	
	mytoolbar:[{
		text:"新增",
		iconCls:"add",
		handler:function(){
			var url=Context.ROOT+Context.PATH+"/trust/add.htm";
			window.open(url);
		}
	},{
		text:"编辑",
		iconCls:"edit",
		handler:function(){
			var grid = Ext.getCmp(POST.GRID);
			var row = grid.getSelections();
			if (row.length>1){
				Ext.Msg.alert(Context.MSG_TITLE, "最多只能选择一条记录!");
			}else{
				var _pid=row[0].get("id");
				var url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_pid+"&companyId="+row[0].get("company_id");
				window.open(url);
			}
		}
	},{
		text:"删除",
		iconCls:"delete",
		handler:function(){
			var grid = Ext.getCmp(POST.GRID);
			var rows = grid.getSelections();
			Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要删除该采购吗？",function(btn){
				if(btn != "yes"){
					return false;
				}
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url: Context.ROOT+Context.PATH+ "/trust/deleteById.htm",
						params:{
							"id":rows[i].get("id")
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","删除成功");
								var _store = Ext.getCmp(POST.GRID).getStore();
								_store.reload();
							}else{
								Ext.Msg.alert(Context.MSG_TITLE, "删除失败");
							}
						},
						failure:function(response,opt){
							Ext.Msg.alert(Context.MSG_TITLE, "删除失败");
						}
					});
				}
			})
		}
	},"-",{
		text:"刷新",
		iconCls:"refresh",
		handler:function(){
			var grid = Ext.getCmp(POST.GRID);
			var row = grid.getSelections();
			if (row.length<=0){
				Ext.Msg.alert(Context.MSG_TITLE, "请至少选择一条记录!");
			}else{
				var arrayids="";
				for(var i=0;i<row.length;i++){
					arrayids=arrayids + row[i].get("id") +",";
				}
				Ext.Ajax.request({
					url: Context.ROOT+Context.PATH+"/trust/batchRefresh.htm?ids="+arrayids,
					method: 'GET',
					success: function (response, options) {
						var _store = Ext.getCmp(POST.GRID).getStore();
						_store.reload();
					},
					failure: function (response, options) {
					}
				});
			}
		}
	},"-",{
		text:"导入crm库",
		iconCls:"add",
		handler:function(){
			var grid = Ext.getCmp(POST.GRID);
			var row = grid.getSelections();
			if (row.length<=0){
				Ext.Msg.alert(Context.MSG_TITLE, "请选择一条记录!");
			}else{
				Ext.MessageBox.confirm(Context.MSG_TITLE,"是否将该公司导入 CRM 库?",function(btn){
					if(btn != "yes"){
						return false;
					}
					for(var i=0;i<row.length;i++){
						Ext.Ajax.request({
							url: Context.ROOT+Context.PATH+"/trust/crm/importToCrm.htm?companyId="+row[i].get("company_id"),
							method: 'GET',
							success: function (response, options) {
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","该公司已成功导入 CRM 库");
								var _store = Ext.getCmp(POST.GRID).getStore();
								_store.reload();
							}else{
								Ext.Msg.alert(Context.MSG_TITLE, "CRM 库已有该公司,无需重复操作(该公司已经在 CRM 库里)");
							}
						},
							failure: function (response, options) {
							Ext.Msg.alert(Context.MSG_TITLE, "CRM 库已有该公司,无需重复操作(该公司已经在 CRM 库里)");
						}
						});
					}
				})
			}
		}
	},"-",{
		text:"我要供货",
		iconCls:"item-key",
		handler:function(){
			var grid = Ext.getCmp(POST.GRID);
			var row = grid.getSelections();
			if (row.length<=0||row.length>1){
				Ext.Msg.alert(Context.MSG_TITLE, "请选择一条记录!");
			}else{
				for(var i=0;i<row.length;i++){
					
					ast.ast1949.trust.purchase.pubSupplyInfo(row[i].get("id"));
				}
			}
		}
	},"-",{
		text:"暂不发布",
		iconCls:"lock",
		handler:function(){
			Ext.MessageBox.confirm(Context.MSG_TITLE,"是否暂不发布已选择的采购信息？",function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var row = grid.getSelections();
				if (row.length<=0){
					Ext.Msg.alert(Context.MSG_TITLE, "请选择一条或一条以上记录!");
				}else{
					var ids="";
					for(var i=0;i<row.length;i++){
						ids = ids + row[i].get("id");
					}
					Ext.Ajax.request({
						url: Context.ROOT+Context.PATH+"/trust/pauseBuy.htm?ids="+ids,
						method: 'GET',
						success: function (response, options) {
						var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","操作成功，选中数据已经进入暂不发布");
								var _store = Ext.getCmp(POST.GRID).getStore();
								_store.reload();
							}
						},
						failure: function (response, options) {
							Ext.Msg.alert(Context.MSG_TITLE, "错误，请重试！");
						}
					});
				}
			})
		}
	},{
		text:"重新发布",
		iconCls:"unlock",
		handler:function(){
			Ext.MessageBox.confirm(Context.MSG_TITLE,"是否重新发布已选择的采购信息？",function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var row = grid.getSelections();
				if (row.length<=0){
					Ext.Msg.alert(Context.MSG_TITLE, "请选择一条或一条以上记录!");
				}else{
					var ids="";
					for(var i=0;i<row.length;i++){
						ids = ids + row[i].get("id");
					}
					Ext.Ajax.request({
						url: Context.ROOT+Context.PATH+"/trust/pubBuy.htm?ids="+ids,
						method: 'GET',
						success: function (response, options) {
						var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","操作成功，选中数据已经重新发布");
								var _store = Ext.getCmp(POST.GRID).getStore();
								_store.reload();
							}
						},
						failure: function (response, options) {
							Ext.Msg.alert(Context.MSG_TITLE, "错误，请重试！");
						}
					});
				}
			})
		}
	},"->",{
		xtype:"checkbox",
		boxLabel:"暂不发布",
		checked:false,
		id:"isPause",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp(POST.GRID).getStore();
				var B=_store.baseParams||{};
				var ary = new Array();
				if(Ext.getCmp("isPause").getValue()){
					B["isPause"] = 1;
				}else{
					B["isPause"] = undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"-",{
		xtype:"combo",
		mode:"local",
		emptyText:"是否登录",
		triggerAction:"all",
		displayField:'name',
		width:80,
		valueField:'value',
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'全部',value:2},
				{name:'是',value:1},
				{name:'否',value:0}
			]
		}),
		listeners:{
			"blur":function(field,newvalue,oldvalue){
				var store=Ext.getCmp(POST.GRID).getStore();
				var B=store.baseParams;
				B=B||{};
				if(field.getValue()==1){
					B["loginFlag"] = 1;
					B["noLoginFlag"] = undefined;
				}else if(field.getValue()==0){
					B["loginFlag"] = undefined;
					B["noLoginFlag"] = 1;
				}else{
					B["loginFlag"] = undefined;
					B["noLoginFlag"] = undefined;
				}
				store.baseParams = B;
				store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"-",{
		xtype:"checkbox",
		boxLabel:"今日第一次发布",
		checked:false,
		id:"isFirst",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp(POST.GRID).getStore();
				var B=_store.baseParams||{};
				var ary = new Array();
				if(Ext.getCmp("isFirst").getValue()){
					B["isFirst"] = 1;
				}else{
					B["isFirst"] = undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}
//	,{
//		text:"查询",
//		iconCls:"query",
//		handler:function(btn){
//			//TODO 查找数据
//			var _store = Ext.getCmp(POST.GRID).getStore();
//			var targetFrom=Ext.getCmp("targetFrom").getValue();
//			var targetTo=Ext.getCmp("targetTo").getValue();
//			var statusGroup=Ext.getCmp("statusGroup").getValue();
//			var buyNum=Ext.getCmp("search-buyNo").getValue();
//			var title=Ext.getCmp("search-content").getValue();
//			_store.baseParams["from"]=targetFrom;
//			_store.baseParams["to"]=targetTo;
//			_store.baseParams["status"]=statusGroup;
//			_store.baseParams["buyNo"]=buyNum;
//			_store.baseParams["title"]=title;
//			_store.baseParams["mobile"]=Ext.getCmp("search-mobile").getValue();
//			_store.baseParams["companyName"]=Ext.getCmp("search-companyName").getValue();
//			if(Ext.getCmp("isLogin").getValue()){
//				_store.baseParams["isLogin"] = 1;
//			}else{
//				_store.baseParams["isLogin"] = undefined;
//			}
//			_store.reload();
//		}
//	}
	]
});

ast.ast1949.trust.purchase.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
					xtype:"checkbox",
					fieldLabel:"是否登录",
					boxLabel:"登陆发布",
					checked:true,
					id:"isLogin",
					listeners:{
						"check":function(field,newvalue,oldvalue){
							if(Ext.getCmp("isLogin").getValue()){
								B["isLogin"] = 1;
							}else{
								B["isLogin"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				}
//				,{
//					xtype:"combo",
//					mode:"local",
//					fieldLabel:"发布类型",
//					triggerAction:"all",
//					displayField:'name',
//					valueField:'value',
//					store:new Ext.data.JsonStore({
//						fields : ['name', 'value'],
//						data   : [
//							{name:'登录发布',value:1},
//							{name:'免登录发布',value:2}
//						]
//					}),
//					listeners:{
//						"blur":function(field,newvalue,oldvalue){
//							if(field.getValue() == 2){
//								B["isLogin"] = undefined;
//							}else{
//								B["isLogin"] = "1";
//							}
//							_store.baseParams = B;
//						}
//					}
//				}
				,{
					fieldLabel : "公司名：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["companyName"] = undefined;
							}else{
								B["companyName"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "手机：",
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
					fieldLabel : "产品关键字：",
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
				},{
					fieldLabel : "采购流水号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["buyNo"] = undefined;
							}else{
								B["buyNo"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					mode:"local",
					fieldLabel:"状态：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					store:new Ext.data.JsonStore({
						fields : ['name', 'value'],
						data   : [
							{name:'全部',value:""},
							{name:'未审核',value:"00"},
							{name:'正在报价',value:"01"},
							{name:'已有报价',value:"02"},
							{name:'正在洽谈',value:"03"},
							{name:'等待打款',value:"04"},
							{name:'交易完成',value:"05"},
							{name:'交易终止',value:"06"},
							{name:'审核不通过',value:"99"},
						]
					}),
					listeners:{
						"blur":function(field,newvalue,oldvalue){
							if(field.getValue()!=undefined){
								B["status"] = field.getValue();
							}else{
								B["status"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"交易员：",
					displayField : "name",
					valueField : "id",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "name", "id" ],
						autoLoad:false,
						url : Context.ROOT+Context.PATH+"/trust/queryAllDealer.htm"
					}),
					listeners:{
						"change":function(field){
							B["dealerId"] =  field.getValue();
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					mode:"local",
					fieldLabel:"时间类型：",
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
					store:new Ext.data.JsonStore({
						fields : ['name', 'value'],
						data   : [
							{name:'发布时间',value:"tb.gmt_created"},
							{name:'更新时间',value:"tb.gmt_modified"},
							{name:'刷新时间',value:"tb.gmt_refresh"}
						]
					}),
					listeners:{
						"blur":function(field,newvalue,oldvalue){
							if(field.getValue()==undefined){
								B["dateType"] = field.getValue();
							}else{
								B["dateType"] = undefined;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					id : "regFrom",
					name:"regFrom",
					format:"Y-m-d",
					fieldLabel : "时间(始)：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["from"] = undefined;
							}else{
								B["from"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					id : "regTo",
					name:"regTo",
					format:"Y-m-d",
					fieldLabel : "时间(终)：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["to"] = undefined;
							}else{
								B["to"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					if(Ext.getCmp("isLogin").getValue()){
						B["isLogin"] = 1;
					}else{
						B["isLogin"] = undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};

		ast.ast1949.trust.purchase.SearchForm.superclass.constructor.call(this,c);

	}
});

// 我发供货
ast.ast1949.trust.purchase.pubSupplyInfo=function(buyId){
	var form=new ast.ast1949.trust.purchase.pubSupplyInfoForm({});
	
	form.loadInit(buyId);
	
	var win = new Ext.Window({
		id:"publishSupply",
		title:"我要供货",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.trust.purchase.pubSupplyInfoForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
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
				fieldLabel:"采购流水号",
				name:"buyNo",
				id:"buyNo",
				readOnly:true
			},{
				xtype:"hidden",
				name:"buyId",
				id:"buyId"
			},{
				fieldLabel:"供货商帐号",
				name:"account",
				id:"account",
				allowBlank : false,
				itemCls:"required"
			},{
				xtype:"textarea",
				fieldLabel : "供货单详情",
				name : "content"
			}
			],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("phoneGrid");
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/trust/pubSupplyByBuyId.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("publishSupply").close();
								grid.getStore().reload();
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败,帐号不存在或供货信息重复");
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
				}
			}]
		};
		
		ast.ast1949.trust.purchase.pubSupplyInfoForm.superclass.constructor.call(this,c);
	},
	loadInit:function(buyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.trust.purchase.PUBSUPPLYFIELD,
			url : Context.ROOT+Context.PATH+"/trust/queryDetail.htm?id="+buyId, 
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
		});
	}
});
