Ext.namespace("ast.ast1949.trust.trade");

var POST=new function(){
	this.GRID="postgrid";
}

ast.ast1949.trust.trade.FIELDS=[
	{name:"id",mapping:"trade.id"},
	{name:"buy_id",mapping:"buy.id"},
	{name:"title",mapping:"buy.title"},
    {name:"buyNo",mapping:"buy.buyNo"},
    {name:"detail",mapping:"buy.detail"},
    {name:"toCompanyContact",mapping:"buy.companyContact"},
    {name:"toMobile",mapping:"buy.mobile"},
    {name:"companyId",mapping:"company.id"},
    {name:"companyName",mapping:"company.name"},
    {name:"to_companyId",mapping:"toCompany.id"},
    {name:"to_companyName",mapping:"toCompany.name"},
    {name:"unit",mapping:"trade.unit"},
    {name:"quantity",mapping:"trade.quantity"},
    {name:"price",mapping:"trade.price"},
    {name:"source",mapping:"trade.source"},
    {name:"details",mapping:"trade.detail"},
    {name:"gmtTrade",mapping:"trade.gmtTrade"},
    {name:"dealer",mapping:"dealer.name"},
    {name:"hasPic",mapping:"hasPic"} 
];

ast.ast1949.trust.trade.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.trust.trade.FIELDS,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			 header:"编号",
	            hidden:true,
	            dataIndex:"id"
	        },{
	            header:"采购流水号",
	            sortable:false,
	            width:85,
	            dataIndex:"buyNo",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	var _id=record.get("buy_id");
	            	var _url="";
	                if(value!=null){ 
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_id+"&companyId="+record.get("to_companyId")
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
	            	var _id=record.get("buy_id");
	            	var _url="";
	                if(value!=null){ 
	                	_url=Context.ROOT+Context.PATH+"/trust/edit.htm?id="+_id+"&companyId="+record.get("to_companyId")
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"供货公司",
	            sortable:false,
	            width:100,
	            dataIndex:"companyName",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	        		var _url="";
	        		if(value!=null&&value!=""){
	                	if (record.get("companyId")>0) {
	                		_url=Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+record.get("companyId");
	                		return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
						}else{
							return value;
						}
	                }else{
	                    return "供货公司不存在";
	                }
	            }
	        },{
	        	header:"采购公司",
	        	sortable:false,
	        	width:100,
	        	dataIndex:"to_companyName",
	        	renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	        		var _url="";
	        		if(value!=null){
	                	if (record.get("to_companyId")>0) {
	                		_url=Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+record.get("to_companyId");
	                		return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
						}else{
							return value;
						}
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"成交单价",
	            sortable:false,
	            width:70,
	            dataIndex:"unit"
	        },{
	            header:"成交量",
	            sortable:false,
	            width:100,
	            dataIndex:"quantity"
	        },{
	            header:"成交总额",
	            sortable:true,
	            width:90,
	            dataIndex:"price"  
	        },{
	            header:"货源地",
	            sortable:false,
	            width:70,
	            dataIndex:"source"
	        },{
	            header:"交易时间",
	            sortable:true,
	            dataIndex:"gmtTrade",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"交易凭证",
	            sortable:true,
	            dataIndex:"hasPic",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	            	if(value > 0){
	            		return "<button class='controlBtn'>" + "查看" + "</button>"; 
	            	}else{
	            		return "无";
	            	}
	            }
	        },{
	            header:"交易员",
	            sortable:true,
	            dataIndex:"dealer"
	        },{
	            header:"查看",
	            sortable:true,
                renderer : function(value, metadata, record, rowIndex,colIndex, store) {
                	return "<button class='deatilBtn'>" + "订单详情" + "</button>";
	            }
	        }
	        ]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
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
		
		ast.ast1949.trust.trade.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/trust/queryTrade.htm",
	
	mytoolbar:[
	        "-","开始时间:",{
			id:"targetFrom",
			xtype:"datefield",
			format:"Y-m-d"
		},"-","结束时间:",{
			id:"targetTo",
			xtype:"datefield",
			format:"Y-m-d"
		},"-","采购流水号",{
			xtype:"textfield",
			width:70,
			id:"search-buyNo"
		},"-","产品关键字:",{
			xtype:"textfield",
			width:70,
			id:"search-content"
		},"-","公司名称:",{
			xtype:"textfield",
			width:70,
			id:"search-companyName"
		},"-","交易员:",{
			xtype:"combo",
			triggerAction : "all",
			forceSelection : true,
			fieldLabel:"交易员：",
			displayField : "name",
			valueField : "id",
			id:"dealerId",
			width:90,
			store:new Ext.data.JsonStore( {
				root : "records",
				fields : [ "name", "id" ],
				autoLoad:false,
				url : Context.ROOT+Context.PATH+"/trust/queryAllDealer.htm"
			}),
			listeners:{
				"change":function(field){
					var _store = Ext.getCmp(POST.GRID).getStore();
					var B=_store.baseParams||{};
					B["dealerId"] =  field.getValue();
					_store.baseParams = B;
				}
			}
		},"-",{
			xtype:"checkbox",
			boxLabel:"交易凭证",
			id:"isLogin",
			listeners:{
			"check":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp(POST.GRID).getStore();
				var B=_store.baseParams||{};
				var ary = new Array();
				if(Ext.getCmp("isLogin").getValue()){
					B["isLogin"] = 1;
				}else{
					B["isLogin"] = undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},{
		text:"查询",
		iconCls:"query",
		handler:function(btn){
			//TODO 查找数据
			var _store = Ext.getCmp(POST.GRID).getStore();
			var targetFrom=Ext.getCmp("targetFrom").getValue();
			var targetTo=Ext.getCmp("targetTo").getValue();
			var buyNum=Ext.getCmp("search-buyNo").getValue();
			var title=Ext.getCmp("search-content").getValue();
			var companyName = Ext.getCmp("search-companyName").getValue();
			_store.baseParams["from"]=targetFrom;
			_store.baseParams["to"]=targetTo;
			_store.baseParams["buyNo"]=buyNum;
			_store.baseParams["keyword"]=title;
			_store.baseParams["companyName"]=Ext.getCmp("search-companyName").getValue();
			if(Ext.getCmp("isLogin").getValue()){
				_store.baseParams["isLogin"] = 1;
			}else{
				_store.baseParams["isLogin"] = undefined;
			}
			_store.reload();
		}
	}]
});
ast.ast1949.trust.trade.TradeInfoForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
				labelAlign : "right",
	            labelWidth : 70,
	            frame:true,
	            items:[{
	                id:"editproducts",
	                layout:"column",
	                frame:true,
	                autoScroll:true,
	                
	                items:[{
	                    columnWidth:1,
	                    layout:"form",
	                    defaults:{
	                        anchor:"95%",
	                        //xtype:"textfield",
	                        labelSeparator:""
	                    },
	                    items:[{
	                        xtype:"hidden",
	                        id:"id",
	                        name:"id"
	                    }]
	                },{
	                    columnWidth:0.5,
	                    layout:"form",
	                    defaults:{
	                        anchor:"95%",
	                        xtype:"textfield",
	                        labelSeparator:""
	                    },
	                    items:[ {
	                        id : "buyNo",
	                        name : "buyNo",
	                        fieldLabel : "采购流水号",
	                        readOnly:"true"
	                    
	                    },{
	                        id : "title",
	                        name : "title",
	                        fieldLabel : "产品",
	                        readOnly:"true"
	                    },{
	                        id : "unit",
	                        name : "unit",
	                        fieldLabel : "成交单价",
	                        readOnly:"true"
	                    },{
	                        id : "toCompanyName",
	                        name : "toCompanyName",
	                        fieldLabel : "采购公司",
	                        readOnly:"true"
	                    },{
	                        id : "toMobile",
	                        name : "toMbile",
	                        fieldLabel : "采购商手机",
	                        readOnly:"true"
	                    },{
	                        id : "source",
	                        name : "source",
	                        fieldLabel : "货源地",
	                        readOnly:"true"
	                    }]
	                },{
	                    columnWidth:0.5,
	                    layout:"form",
	                    defaults:{
	                        anchor:"95%",
	                        xtype:"textfield",
	                        labelSeparator:""
	                    },
	                    items:[{
	                        id : "gmtTrade",
	                        name : "gmtTrade",
	                        fieldLabel : "交易成功时间",
	                        readOnly:"true"
	                    },{
	                        id : "quantity",
	                        name : "quantity",
	                        fieldLabel : "成交量",
	                        readOnly:"true"
	                     
	                    },{
	                        id : "price",
	                        name : "price",
	                        fieldLabel : "成交总额",
	                        readOnly:"true"
	                    },{
	                        id : "toCompanyContact",
	                        name : "toCompanyContact",
	                        fieldLabel : "采购商",
	                        readOnly:"true"
	                    },{
	                        id : "companyName",
	                        name : "companyName",
	                        fieldLabel : "供货公司",
	                        readOnly:"true"
	                    }]
	                },{
	                	 columnWidth:1,
		                 layout:"form",
		                 defaults:{
		                      anchor:"95%",
		                      xtype:"textfield",
		                      labelSeparator:""
		                  },
		                 items:[{
		                	 	xtype:"textarea",
		                        id:"detail",
		                        name:"detail",
		                        fieldLabel : "采购详情",
		                        readOnly:"true"
		                    },{
		                    	 xtype:"textarea",
		                    	 id:"details",
			                     name:"details",
			                     fieldLabel : "备注",
			                     readOnly:"true"
		                    }]
	                }]
	            }]
		};
		
		ast.ast1949.trust.trade.TradeInfoForm.superclass.constructor.call(this,c);
	},
	loadInit:function(row){
		Ext.getCmp("buyNo").setValue(row.get("buyNo"));
		Ext.getCmp("buyNo").fieldLabel = "<a href='"+Context.ROOT+Context.PATH+"/trust/edit.htm?id="+row.get("buy_id")+"&companyId="+row.get("to_companyId")+"' target='_blank' >"+"采购流水号"+"</a>";
		Ext.getCmp("gmtTrade").setValue(new Date(row.data.gmtTrade.time).format("Y-m-d"));
		Ext.getCmp("title").setValue(row.get("title"));
		Ext.getCmp("unit").setValue(row.get("unit"));
		Ext.getCmp("quantity").setValue(row.get("quantity"));
		Ext.getCmp("price").setValue(row.get("price"));
		Ext.getCmp("toCompanyName").setValue(row.get("to_companyName"));
		Ext.getCmp("source").setValue(row.get("source"));
		Ext.getCmp("detail").setValue(row.get("detail"));
		Ext.getCmp("detail").fieldLabel = "<a href='"+Context.ROOT+Context.PATH+"/trust/edit.htm?id="+row.get("buy_id")+"&companyId="+row.get("to_companyId")+"' target='_blank' >"+"采购详情"+"</a>";
		Ext.getCmp("details").setValue(row.get("details"));
		Ext.getCmp("companyName").setValue(row.get("companyName"));
		if(row.get("companyId")&&row.get("companyId")>0){
			Ext.getCmp("companyName").fieldLabel="<a href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+row.get("companyId")+"' target='_blank' >"+"供应商"+"</a>";
		}
		Ext.getCmp("toCompanyContact").setValue(row.get("toCompanyContact"));
		Ext.getCmp("toMobile").setValue(row.get("toMobile"));
		if(row.get("to_companyId")&&row.get("to_companyId")>0){
			Ext.getCmp("toCompanyContact").fieldLabel="<a href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+row.get("to_companyId")+"' target='_blank' >"+"采购商"+"</a>";
			Ext.getCmp("toMobile").fieldLabel="<a href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+row.get("to_companyId")+"' target='_blank' >"+"采购商手机"+"</a>";
		}
	}
});

