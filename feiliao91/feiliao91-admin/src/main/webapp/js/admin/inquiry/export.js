
/**
 * Modify By Rolyer 2011.01.18
 * TODO:
 * 1、询盘导出为供求
 */
Ext.namespace("ast.ast1949.admin.inquiry");

/**
 * 询盘信息列表
 * @class ast.ast1949.admin.inquiry.product.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.inquiry.ProductResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel();
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header : "编号",
				sortable : true,
				dataIndex : "id",
				hidden:true
			},{
				header:"供/求",
				dataIndex : "productsTypeCode",
				width:20,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value=="10331000"){
						return "供应";
					}else{
						return "求购";
					}
				}
			},{
				xtype : "hidden",
				header : "供求标题",
				width:80,
				sortable : false,
				dataIndex : "fromTitle"
			},{
				header : "群发时间",
				width : 80,
				sortable : true,
				dataIndex : "gmtInquiryStr"
			},{
				header : "被询盘供求",
				width : 80,
				sortable : true,
				dataIndex : "title",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='"+Context.ROOT + Context.PATH + "/admin/products/edit.htm?productid="+record.get("id")+"&companyid="+record.get("companyId")+"' target='_blank'>"+value+"</a>"
				}
			},{
				header : "收件人",
				width : 80,
				sortable : true,
				dataIndex : "name",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='"+Context.ROOT + Context.PATH + "/crm/company/detail.htm?companyId="+record.get("companyId")+"' target='_blank'>"+value+"</a>"
				}
			},{
				header : "内容",
				width : 80,
				sortable : true,
				dataIndex : "details"
			}
		]);
		
		var mybar=this.toolbar;
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:mybar,
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
		
		ast.ast1949.admin.inquiry.ProductResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"products.id"},
		{name:"title",mapping:"products.title"},
		{name:"fromTitle",mapping:"fromTitle"},
		{name:"companyId",mapping:"company.id"},
		{name:"name",mapping:"company.name"},
		{name:"details",mapping:"products.details"},
		{name:"productsTypeCode",mapping:"products.productsTypeCode"},
		{name:"gmtInquiryStr",mapping:"gmtInquiryStr"}
	]),
	listUrl:Context.ROOT + Context.PATH + "/admin/inquiry/queryInquiry.htm",
	loadBySender:function(account){
		var B=this.getStore().baseParams||{};
		B["receiverAccount"]=null;
		B["senderAccount"]=account;
		B["isRubbish"]=null;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	loadByReceiver:function(account){
		var B=this.getStore().baseParams||{};
		B["receiverAccount"]=account;
		B["senderAccount"]=null;
		B["isRubbish"]="0";
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	showdetail:function(details){
		Ext.MessageBox.show({
			title:"询盘详细信息",
			msg : details,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	}
});

// 该公司询盘信息
ast.ast1949.admin.inquiry.CompanyResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel();
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header : "编号",
				width : 40,
				sortable : true,
				dataIndex : "id",
				hidden:true
			},{
				header:"供/求",
				dataIndex : "productsTypeCode",
				width:50,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value=="10331000"){
						return "供应";
					}else{
						return "求购";
					}
				}
			},{
				xtype : "hidden",
				header : "供求标题",
				width:120,
				sortable : false,
				dataIndex : "title",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='"+Context.ROOT + Context.PATH + "/admin/products/edit.htm?productid="+record.get("id")+"&companyid="+record.get("companyId")+"' target='_blank'>"+value+"</a>"
				}
			},{
				header : "发布时间",
				width : 80,
				sortable : true,
				dataIndex : "real_time",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d H:i:s');
					}
				}
			},{
				header : "刷新时间",
				width : 80,
				sortable : true,
				dataIndex : "refresh_time",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d H:i:s');
					}
				}
			},{
				header : "审核时间",
				width : 80,
				sortable : true,
				dataIndex : "check_time",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d H:i:s');
					}
				}
			},{
				header:"是否转为询盘",
				width:180,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex, colIndex, store){
					if(record.get("countInquiry")>0){
						return "<a href='"+Context.ROOT + Context.PATH + "/admin/inquiry/listOfProductExport.htm?productId="+record.get("id")+"' target='_blank'>已转为询盘</a>";
					}else{
						return "未转为询盘";
					}
				}
			},{
				header : "次数",
				width : 80,
				sortable : true,
				dataIndex : "countInquiry",
					renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value>0){
					return "<a href='"+Context.ROOT + Context.PATH + "/admin/inquiry/listOfProductExport.htm?productId="+record.get("id")+"' target='_blank'>"+value+"</a>";
				}else{
					return value;
				}
			}
			},{
				header : "转发时间",
				width : 80,
				sortable : true,
				dataIndex : "gmtInquiryStr"
			}
		]);
		
		var mybar=this.toolbar;
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:mybar,
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
		
		ast.ast1949.admin.inquiry.CompanyResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"products.id"},
		{name:"title",mapping:"products.title"},
		{name:"companyId",mapping:"company.id"},
		{name:"details",mapping:"products.details"},
		{name:"productsTypeCode",mapping:"products.productsTypeCode"},
		{name:"real_time",mapping:"products.realTime"},
		{name:"refresh_time",mapping:"products.refreshTime"},
		{name:"check_time",mapping:"products.checkTime"},
		{name:"send_time",mapping:"sendTime"},
		{name:"countInquiry",mapping:"countInquiry"},
		{name:"gmtInquiryStr",mapping:"gmtInquiryStr"}
	]),
	listUrl:Context.ROOT + Context.PATH + "/admin/inquiry/queryInquiry.htm",
	loadBySender:function(account){
		var B=this.getStore().baseParams||{};
		B["receiverAccount"]=null;
		B["senderAccount"]=account;
		B["isRubbish"]=null;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	loadByReceiver:function(account){
		var B=this.getStore().baseParams||{};
		B["receiverAccount"]=account;
		B["senderAccount"]=null;
		B["isRubbish"]="0";
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	showdetail:function(details){
		Ext.MessageBox.show({
			title:"询盘详细信息",
			msg : details,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	}
});

