Ext.namespace("ast.ast1949.trust.purchase.supply");

var POST=new function(){
	this.GRID="postgrid";
}

ast.ast1949.trust.purchase.supply.CHECKSTATUS=["未审核","通过","","退回"];

ast.ast1949.trust.purchase.supply.FIELDS=[
	{name:"id",mapping:"sell.id"},
    {name:"buyNo",mapping:"buy.buyNo"},
    {name:"buyId",mapping:"buy.id"},
    {name:"detail",mapping:"buy.detail"},
    {name:"title",mapping:"buy.title"},
    {name:"buyStatus",mapping:"buy.status"},
    {name:"company_name",mapping:"company.name"},
    {name:"company_id",mapping:"company.id"},
    {name:"to_company_id",mapping:"toCompany.id"},
    {name:"to_company_mobile",mapping:"toCompanyMobile"},
    {name:"to_company_contact",mapping:"toCompanyContact"},
    {name:"categoryName",mapping:"categoryName"},
    {name:"content",mapping:"sell.content"},
    {name:"status",mapping:"sell.status"},
    {name:"name",mapping:"dealer.name"},
    {name:"ts.gmt_created",mapping:"sell.gmtCreated"},
    {name:"ts.gmt_modified",mapping:"sell.gmtModified"}
];

ast.ast1949.trust.purchase.supply.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.trust.purchase.supply.FIELDS,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			 header:"编号",
	            hidden:true,
	            dataIndex:"id"
		 	},{
	            header:"审核状态",
	            sortable:false,
	            width:85,
	            dataIndex:"status",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value=="01"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" alt="审核通过"/>';
					}else if(value=="99"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" alt="审核退回"/>';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" alt="未审核"/>';
					}
	            }
	        },{
	            header:"采购状态",
	            sortable:false,
	            width:85,
	            dataIndex:"buyStatus",
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
	            header:"采购流水号",
	            sortable:false,
	            width:85,
	            dataIndex:"buyNo",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	var _id=record.get("buyId");
	            	var _url="";
	                if(value!=null){ 
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_id+"&companyId="+record.get("to_company_id")
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"供货内容",
	            sortable:false,
	            width:85,
	            dataIndex:"content",
	        },{
	            header:"采购内容",
	            sortable:false,
	            width:250,
	            dataIndex:"detail",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	var _id=record.get("buyId");
	            	var _url="";
	                if(value!=null){ 
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_id+"&companyId="+record.get("to_company_id")
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"采购联系人",
	            sortable:false,
	            width:250,
	            dataIndex:"to_company_contact"
	        },{
	            header:"采购手机号",
	            sortable:false,
	            width:250,
	            dataIndex:"to_company_mobile"
	        },{
	            header:"产品",
	            sortable:false,
	            width:70,
	            dataIndex:"categoryName"
	        },{
	            header:"供货公司",
	            sortable:false,
	            width:100,
	            dataIndex:"company_name",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	var _url="";
	                if(value!=null){
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+record.get("company_id");
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
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
	            dataIndex:"ts.gmt_created",
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
	            dataIndex:"ts.gmt_modified",
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
		
		ast.ast1949.trust.purchase.supply.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/trust/querySupply.htm",
	
	mytoolbar:[{
		text:"审核",
		menu:[{
			text:"通过",
			handler:function(btn){
				ast.ast1949.trust.purchase.supply.updateCheckStatus("01");
			}
		},{
			text:"退回",
			handler:function(btn){
				ast.ast1949.trust.purchase.supply.updateCheckStatus("99");
			}
		}]
	},{
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
					var arrayids="";
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
	},"->",{
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
//	,"-","开始时间:",{
//		id:"targetFrom",
//    	xtype:"datefield",
//    	format:"Y-m-d"
//	},"-","结束时间:",{
//		id:"targetTo",
//    	xtype:"datefield",
//    	format:"Y-m-d"
//	},"-","状态:",{
//		xtype:"combo",
//		mode:"local",
//		triggerAction:"all",
//		id:"statusGroup",
//		emptyText:"全部",
//		itemCls:"required",
//		sortable:false,
//        width:85,
//		store:[
//		    [" ","全部"],
//			["00","已报价"],
//			["01","报价被采纳"],
//			["99","报价被否决"]
//		]
//	},"-","采购流水号",{
//		xtype:"textfield",
//		width:70,
//		id:"search-buyNo"
//	},"-","产品关键字:",{
//		xtype:"textfield",
//		width:70,
//		id:"search-content"
//	},"手机:",{
//		xtype:"textfield",
//		width:70,
//		id:"search-mobile"
//	},"公司名称:",{
//		xtype:"textfield",
//		width:70,
//		id:"search-companyName"
//	},"-",{
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
//			_store.reload();
//		}
//	}
	]
});
ast.ast1949.trust.purchase.supply.updateCheckStatus=function(status){
	var grid = Ext.getCmp(POST.GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/trust/updateStatus.htm",
			params:{
				"id":row[i].get("id"),
				"status":status,
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","供货已处理");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","发生错误,操作被取消");
				}
			},
			failure:function(response,opt){
				ast.ast1949.utils.Msg("","发生错误,操作被取消");
			}
		});
	}
}

ast.ast1949.trust.purchase.supply.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel:"是否登录：",
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
							{name:'已报价',value:"00"},
							{name:'报价被采纳',value:"01"},
							{name:'报价被否决',value:"99"}
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
							{name:'发布时间',value:"ts.gmt_created"},
							{name:'更新时间',value:"ts.gmt_modified"}
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

		ast.ast1949.trust.purchase.supply.SearchForm.superclass.constructor.call(this,c);

	}
});