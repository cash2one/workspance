Ext.namespace("ast.ast1949.spot.order");

ast.ast1949.spot.order.INFOFIELD=[
	{name:"id",mapping:"spotOrder.id"},
	{name:"company_id",mapping:"spotOrder.company_id"},
	{name:"products_typeCode",mapping:"productsTypeCode"},
	{name:"products_category",mapping:"productsCategory"},
	{name:"title",mapping:"spotOrder.orderProductTitle"},
	{name:"order_company_name",mapping:"spotOrder.orderCompanyName"},
	{name:"price",mapping:"spotOrder.price"},
	{name:"price_unit",mapping:"spotOrder.priceUnit"},
	{name:"quantity",mapping:"spotOrder.quantity"},
	{name:"quantity_unit",mapping:"spotOrder.quantityUnit"},
	{name:"total",mapping:"spotOrder.total"},
	{name:"mobile",mapping:"mobile"},
	{name:"order_mobile",mapping:"orderMobile"},
	{name:"order_status",mapping:"spotOrder.orderStatus"},
	{name:"gmt_created",mapping:"spotOrder.gmtCreated"}
];

ast.ast1949.spot.order.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header : "供/求",
				name : "products_typeCode",
				id : "products_typeCode",
				width:50,
				sortable:false,
				dataIndex:"products_typeCode"
			},{
				header : "产品类目",
				name : "products_category",
				id : "products_category",
				width:100,
				sortable:false,
				dataIndex:"products_category"
			},{
				header : "标题",
				width : 200,
				sortable : false,
				dataIndex : "title"
			},{
				header : "下单公司名",
				width : 200,
				sortable : false,
				dataIndex : "order_company_name"
			},{
				header : "价格",
				width : 140,
				sortable : false,
				dataIndex : "price",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					var priceUnit = record.get("price_unit");
					return  value + priceUnit
				}
			},{
				header : "数量",
				width : 80,
				sortable : false,
				dataIndex : "quantity",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					var quantityUnit = record.get("quantity_unit");
					return value + quantityUnit;
				}
			},{
				header : "总价",
				width : 140,
				sortable : false,
				dataIndex : "total",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					var priceUnit = record.get("price_unit");
					return  value + priceUnit
				}
			},{
				header : "联系方式",
				width : 150,
				sortable : false,
				dataIndex : "mobile"
			},{
				header : "下单用户联系方式",
				width : 150,
				sortable : false,
				dataIndex : "order_mobile",
			},{
				header : "审核状态",
				width : 55,
				sortable : false,
				dataIndex : "order_status",
				renderer:function(value,metadata,record,rowIndex,colIndex,store){
					var str = "";
					if(value=="0"){
						str = "购物车";
					}else if(value=="1"){
						str = "待确认";
					}else if(value=="2"){
						str = "已下单";
					}
					return str
				}
			},{
				header : "申请时间",
				width : 128,
				sortable : false,
				dataIndex : "gmt_created",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/spot/queryOrder.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.spot.order.INFOFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [
		{
			text:"审核通过",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/spot/updateStatusForAuction.htm",
						params:{
							"id":rows[i].get("id"),
							"status":1
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","审核成功");
								_store.reload();
							}else{
								ast.ast1949.utils.Msg("","操作失败");
							}
						}
					})
				}
			}
		},"-",{
			text:"退回",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/spot/updateStatus.htm",
						params:{
							"id":rows[i].get("id"),
							"status":2
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","退回成功");
								_store.reload();
							}else{
								ast.ast1949.utils.Msg("","操作失败");
							}
						}
					})
				}
			}
		},"->",{
			xtype:"combo",
			id:"selectTime",
			mode:"local",
			emptyText:"订单状态",
			fieldLabel:"订单状态：",
			triggerAction:"all",
			displayField:'name',
			valueField:'value',
			autoSelect:true,
			store:new Ext.data.JsonStore({
				fields : ['name', 'value'],
				data   : [
					{name:'购物车',value:'0'},
					{name:'待确认',value:'1'},
					{name:'已下单',value:'2'}
				]
			}),
			listeners:{
				"blur":function(field){
					var B = _store.baseParams;
					if(Ext.get(field.getId()).dom.value!=""){
						B["orderStatus"] = field.getValue();
					}else{
						B["orderStatus"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
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
		ast.ast1949.spot.order.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null
});

ast.ast1949.spot.order.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
				},{
					fieldLabel : "手机号码：",
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
					fieldLabel : "邮箱：",
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
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"黑名单原因：",
					displayField : "content",
					valueField : "id",
					id : "reason",
					store:new Ext.data.JsonStore( {
						fields : [ "content", "id" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
					}),
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue!="") {
								B["reason"] =  Ext.get("reason").dom.value;;
							}else{
								B["reason"] = null;
							}
							alert(1);
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

		ast.ast1949.spot.order.SearchForm.superclass.constructor.call(this,c);

	}
});
