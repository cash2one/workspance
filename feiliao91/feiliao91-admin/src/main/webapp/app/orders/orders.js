/*
 * 提现记录
 */
Ext.namespace("ast.feiliao91.admin.orders")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//订单记录列表
ast.feiliao91.admin.orders.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header:"订单号",
	            width:150,
	            dataIndex:"orderNo",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	var _url="";
	            	if(value!=null){ 
	                	
	                    return "<a href='javascript:void(0);' class='orderDetailBtn'>"+value+"</a>";
	                }else{
	                    return "";
	                }
				}
			},{
				header:"产品",
	            width:150,
	            dataIndex:"title",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	var _id=record.get("goodsId");
	            	var _url="";
	                if(value!=null){
	                	_url=Context.ROOT+Context.PATH+"/admin/good/edit.htm?goodsId="+_id+"&companyId="+record.get("companyId")
	                    return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
	                }else{
	                    return "";
	                }
				}
			},{
				header:"供应商",
	            width:150,
	            dataIndex:"sellCompanyName",
	            renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						_url=Context.ROOT+Context.PATH+"/admin/company/detail.htm?companyId="+record.get("companyId");
						return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
					}
					return "";
				}
			},{
				header:"采购商",
	            width:150,
	            dataIndex:"buyCompanyName",
	            renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						_url=Context.ROOT+Context.PATH+"/admin/company/detail.htm?companyId="+record.get("buyCompanyId");
						return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
					}
					return "";
				}
			},{
				header:"采购量",
	            dataIndex:"buyQuantity",
	            renderer:function(value,m,record,ridx,cidx,store,view){
					return value+"吨";
				}
			},{
				header:"单价",
	            dataIndex:"buyPricePay",
	            renderer:function(value,m,record,ridx,cidx,store,view){
					return value+"元/吨";
				}
			},{
				header:"运费",
	            dataIndex:"buyPriceLogistics",
	            renderer:function(value,m,record,ridx,cidx,store,view){
					return value+"元";
				}
			},{
				header:"实付",
	            dataIndex:"totalPay",
	            renderer:function(value,m,record,ridx,cidx,store,view){
					return value+"元";
				}
			},{
				header:"订单状态",
	            dataIndex:"status",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value=="0"){
	                    return "买家已下单";
	                }else if(value=="1"){
	                    return "等待买家付款";
	                }else if(value=="2"){
	                    return "等待发货";
	                }else if(value=="3"){
	                    return "物流运输中";
	                }else if(value=="4"){
	                	return "货物已揽收";
	                }else if(value=="50"){
	                	return "退款中";
	                }else if(value=="66"){
	                    return "交易成功";
	                }else if(value=="99"){
	                    return "交易关闭";
	                }
				}
			},{
				header:"下单时间",
	            sortable:true,
	            width:130,
	            dataIndex:"gmt_created",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
			},{
				header:"最后操作时间",
	            sortable:true,
	            width:130,
	            dataIndex:"gmt_modified",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
			}
		]);
		
		var con={
				iconCls:"icon-grid",
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
//				tbar:this.mytoolbar,
				bbar:new Ext.PagingToolbar({
					pageSize : Context.PAGE_SIZE,
					store : _store,
					displayInfo: true,
					displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
					emptyMsg : '没有可显示的记录',
					beforePageText : '第',
					afterPageText : '页,共{0}页',
					paramNames : {start:"startIndex",limit:"pageSize"}
				}),
			};
		ast.feiliao91.admin.orders.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"orders.id"},
		{name:"orderNo",mapping:"orders.orderNo"},
		{name:"title",mapping:"goods.title"},
		{name:"sellCompanyName",mapping:"sellCompany.name"},
		{name:"goodsId",mapping:"orders.goodsId"},//货物id
		{name:"companyId",mapping:"orders.sellCompanyId"},//商家公司id
		{name:"buyCompanyId",mapping:"orders.buyCompanyId"},//买家公司id
		{name:"buyCompanyName",mapping:"info.name"},
		{name:"buyQuantity",mapping:"orders.buyQuantity"},
		{name:"buyPricePay",mapping:"orders.buyPricePay"},//单价
		{name:"buyPriceLogistics",mapping:"orders.buyPriceLogistics"},//运费
		{name:"totalPay",mapping:"detailJson.orderTotalPay"},//实付
		{name:"status",mapping:"orders.status"},//订单状态
		{name:"gmt_created",mapping:"orders.gmtCreated"},//下单时间
		{name:"gmt_modified",mapping:"orders.gmtModified"},//最后操作时间
		{name:"unit",mapping:"goods.unit"},//单位
		{name:"picAddress",mapping:"detailJson.piclist"},
		{name:"getAddress",mapping:"detailJson.Address.address"},//收货地址
		{name:"mobile",mapping:"detailJson.Address.mobile"},//手机
		{name:"name",mapping:"detailJson.Address.name"},//收件人
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/orders/queryList.htm",
	
	//根据companyId搜索卖出的产品订单
	searchByCompanySell:function(companyId){
		var B=this.getStore().baseParams||{};
		B["sellCompanyId"] = companyId;//供应商
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	//根据companyId搜索买到的产品订单
	searchByCompanyBuy:function(companyId){
		var B=this.getStore().baseParams||{};
		B["buyCompanyId"] = companyId;//供应商
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
//	showdetail:function(result){
//		var html="<span>订单编号："+result.data.orderNo+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>供应商："+result.data.sellCompanyName+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>采购商："+result.data.buyCompanyName+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>下单时间："+Ext.util.Format.date(new Date(result.data.gmt_created.time), 'Y-m-d H:i:s')+"</span></p>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>产品名："+result.data.title+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>采购量："+result.data.buyQuantity+result.data.unit+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>单价："+result.data.buyPricePay+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>运费："+result.data.buyPriceLogistics+"元</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>实付："+result.data.totalPay+"元</span>";
//		var statusStr="";
//		if(result.data.status=="0"){
//			statusStr = "买家已下单";
//        }else if(result.data.status=="1"){
//        	statusStr = "等待买家付款";
//        }else if(result.data.status=="2"){
//        	statusStr = "等待发货";
//        }else if(result.data.status=="3"){
//        	statusStr = "物流运输中";
//        }else if(result.data.status=="4"){
//        	statusStr = "货物已揽收";
//        }else if(result.data.status=="50"){
//        	statusStr = "退款中";
//        }else if(result.data.status=="66"){
//        	statusStr = "交易成功";
//        }else if(result.data.status=="99"){
//        	statusStr = "交易关闭";
//        }
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>订单状态："+statusStr+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>采购商收货地址："+result.data.totalPay+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>收件人："+"三方让"+"</span>";
//		html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<span>手机："+"1357446624"+"</span></br>";
//		if(result.data.piclist.length>0 ){
//			html = html + "&nbsp;&nbsp;&nbsp;&nbsp;" +
//			"<span><img height='150' width='150' src='http://img1.taozaisheng.com"+result.data.piclist[0].picAddress+"'></span>";
//		}
//		Ext.MessageBox.show({
//			title:"订单详情",
//			width: 1500,
//			msg : html,
//			buttons:Ext.MessageBox.OK,
//		});
//	}
});

ast.feiliao91.admin.orders.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};
		var form=this;
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
						fieldLabel : "订单号：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["orderNo"] = null;
								}else{
									B["orderNo"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						fieldLabel : "产品名：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["title"] = null;
								}else{
									B["title"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						id:"gslx",
						fieldLabel:"公司类型：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'供应商',value:"0"},
								{name:'采购商',value:"1"}
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=undefined){
									B["companyType"] = field.getValue();
								}else{
									B["companyType"] = undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						fieldLabel : "公司名：",
						emptyText:"请先选择公司类型",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["companyName"] = null;
								}else{
									B["companyName"] = newvalue;
								}
								if(form.findById("gslx").getValue()==""){
									alert("请选择公司类型！");
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						fieldLabel:"订单状态：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'全部',value:""},
								{name:'买家已下单',value:"0"},
								{name:'等待买家付款',value:"1"},
								{name:'等待发货',value:"2"},
								{name:'物流运输中',value:"3"},
								{name:'货物已揽收',value:"4"},
								{name:'退款中',value:"50"},
								{name:'交易成功',value:"66"},
								{name:'交易关闭',value:"99"},
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=null){
									B["status"] = field.getValue();
								}else{
									B["status"] = null;
								}
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
								{name:'下单时间',value:"o.gmt_created"},
								{name:'最后操作时间',value:"o.gmt_modified"}
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=undefined){
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
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}]
		};
		
		ast.feiliao91.admin.orders.SearchForm.superclass.constructor.call(this,c);
	}
});
//订单详细弹出框
ast.feiliao91.admin.orders.OrdersInfoForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
				labelAlign : "right",
	            labelWidth : 90,
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
	                    items:[{
	                        id : "orderNo",
	                        name : "orderNo",
	                        fieldLabel :"订单编号",
	                        readOnly:"true"
	                    
	                    },{
							id : "sellCompanyName",
							name : "sellCompanyName",
							fieldLabel : "供应商",
							readOnly:"true"
	                    },{
							id:"title",
							name:"title",
							fieldLabel : "产品名",
							readOnly:"true"
	                    },{
							id:"buyQuantity",
							name:"buyQuantity",
							fieldLabel : "采购量",
							readOnly:"true"
	                    },{
	                    	id:"buyPricePay",
							name:"buyPricePay",
							fieldLabel : "单价",
							readOnly:"true"
	                    },
//	                    {
//	                    	xtype:'panel',
//	                    	html:'<img id="checkpic" src="http://img3.zz91.com/500x500/zz91admin/2015/10/23/f56194c3-3154-4fae-b5ce-0e66ce1441eb.jpg" style="width:200px;height:200px;"  onclick="showBigPic()" />'
//	                    }
	                    {
	                    	id : "pic",
							name : "pic",
							fieldLabel : "图片",
							xtype:"htmleditor",
							height:150,
							readOnly:"true"
	                    }
	                    ]
	                },{
	                	columnWidth:0.5,
	                    layout:"form",
	                    defaults:{
	                        anchor:"95%",
	                        xtype:"textfield",
	                        labelSeparator:""
	                    },
	                    items:[{
	                        id : "gmtCreated",
	                        name : "gmtCreated",
	                        fieldLabel : "下单时间",
	                        readOnly:"true"
	                    },{
	                    	id : "buyCompanyName",
							name : "buyCompanyName",
							fieldLabel : "采购商",
							readOnly:"true"
	                    },{
	                    	id : "buyPriceLogistics",
							name : "buyPriceLogistics",
							fieldLabel : "运费",
							readOnly:"true"
	                    },{
	                    	id : "totalPay",
							name : "totalPay",
							fieldLabel : "实付",
							readOnly:"true"
	                    },{
	                    	id : "status",
							name : "status",
							fieldLabel : "订单状态",
							readOnly:"true"
	                    },{
	                    	id : "address",
							name : "address",
							fieldLabel : "收货地址",
							xtype:"textarea",
							height:100,
							readOnly:"true"
	                    },{
	                    	id : "name",
							name : "name",
							fieldLabel : "收件人",
							readOnly:"true"
	                    },{
	                    	id : "mobile",
							name : "mobile",
							fieldLabel : "手机",
							readOnly:"true"
	                    }]
	                }]
	            }]
		};
		ast.feiliao91.admin.orders.OrdersInfoForm.superclass.constructor.call(this,c);
	},
	loadInit:function(row){
		Ext.getCmp("orderNo").setValue(row.get("orderNo"));
		Ext.getCmp("sellCompanyName").setValue(row.get("sellCompanyName"));
		Ext.getCmp("buyCompanyName").setValue(row.get("buyCompanyName"));
		Ext.getCmp("gmtCreated").setValue(new Date(row.data.gmt_created.time).format("Y-m-d"));
		Ext.getCmp("title").setValue(row.get("title"));
		Ext.getCmp("buyQuantity").setValue(row.get("buyQuantity")+"吨");
		Ext.getCmp("buyPricePay").setValue(row.get("buyPricePay"));
		Ext.getCmp("buyPriceLogistics").setValue(row.get("buyPriceLogistics"));
		Ext.getCmp("totalPay").setValue(row.get("totalPay"));
		Ext.getCmp("address").setValue(row.get("getAddress"));
		Ext.getCmp("mobile").setValue(row.get("mobile"));
		Ext.getCmp("name").setValue(row.get("name"));
		var picList = row.get("picAddress");
		if(picList.length>0){
			var picUrl = "<img src='http://img3.taozaisheng.com/180x180"+picList[0].picAddress+"'>";
			Ext.getCmp("pic").setValue(picUrl);
		}
		var st = row.get("status");
		if(st=="0"){
			return Ext.getCmp("status").setValue("买家已下单");
        }else if(st=="1"){
        	return Ext.getCmp("status").setValue("等待买家付款");
        }else if(st=="2"){
        	return Ext.getCmp("status").setValue("等待发货");
        }else if(st=="3"){
        	return Ext.getCmp("status").setValue("物流运输中");
        }else if(st=="4"){
        	return Ext.getCmp("status").setValue("货物已揽收");
        }else if(st=="50"){
        	return Ext.getCmp("status").setValue("退款中");
        }else if(st=="66"){
        	return Ext.getCmp("status").setValue("交易成功");
        }else if(st=="99"){
        	return Ext.getCmp("status").setValue("交易关闭");
        }
	}
});




