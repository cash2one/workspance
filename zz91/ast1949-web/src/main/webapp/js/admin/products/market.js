Ext.namespace("ast.ast1949.admin.products.market");
// 定义变量
var _C = new function() {
	this.MARKET_GRID = "marketGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.admin.products.market.MARKETFIELD=[
  	{name:"id",mapping:"id"},
	{name:"name",mapping:"name"},
	{name:"area",mapping:"area"},
	{name:"industry",mapping:"industry"},
	{name:"category",mapping:"category"},
	{name:"business",mapping:"business"},
	{name:"companyId",mapping:"companyId"},
	{name:"words",mapping:"words"},
	{name:"checkStatus",mapping:"checkStatus"},
	{name:"companyAccount",mapping:"companyAccount"},
	{name:"gmtCreated",mapping:"gmtCreated"}
];

ast.ast1949.admin.products.market.marketGrid=Ext.extend(Ext.grid.GridPanel,{
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
				header:"审核",
				id : "checkStatus",
				dataIndex:"checkStatus",
				width:35,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" alt="审核通过"/>';
					}else if(value==2){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" alt="审核退回"/>';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" alt="未审核"/>';
					}
				}
			},{    	
				header:"市场名称",
				width:150,
				id:"name",
				dataIndex : "name",
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var webstr="<a href='http://y.zz91.com/"+record.get("words")+"/' target='_blank' >";
					webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
					var title=value;
					return webstr+title;
				}	
			},{
				header:"市场所在地",
				width:100,
				dataIndex : "area"
				
			},{
				header:"所属行业",
				width:100,
				dataIndex : "industry"
			},{
				header:"行业类别",
				width:100,
				dataIndex : "category"
				
			}, {
				header : "主营业务",
				width : 300,
				dataIndex : "business"
			}, {
				header : "登陆账号",
				width:100,
				dataIndex : "companyAccount"
			}, {
				header : "添加时间",
				width : 150,
				dataIndex : "gmtCreated",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/products/listMarket.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.products.market.MARKETFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
				iconCls:"edit",
				text:"编辑市场",
				handler:function(btn){
					var grid = Ext.getCmp("marketGrid");
					var row = grid.getSelections();
					if(row.length==1){
						window.open(Context.ROOT+Context.PATH+"/admin/products/editMarket.htm?marketId="+row[0].get("id"));
					}else{
						Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
					}
					
				}
			},{
				iconCls:"add",
				text:"添加市场",
				handler:function(btn){
					window.open(Context.ROOT+Context.PATH+"/admin/products/addMarket.htm");
				}
			},{
				iconCls:"add",
				text:"推荐",
				handler:function(btn){
					var grid = Ext.getCmp("marketGrid");
					var row = grid.getSelections();
					if(row.length==1){
						ast.ast1949.admin.dataIndex.SendIndex({
							title:row[0].get("name"),
							link:"http://y.zz91.com/marketIndex.htm?id="+row[0].get("id")
						});
					}else{
						Ext.Msg.alert(Context.MSG_TITLE, "请选定一条记录");	
					}	
				
				}
		     },"-",{
				xtype:"checkbox",
				boxLabel:"有图",
				id:"uncheckBtn",
				listeners:{
					"check":function(field,newvalue,oldvalue){
						var _store = Ext.getCmp("marketGrid").getStore();
						var B = _store.baseParams;
						if(field.getValue()){
							B["hasPic"] = 1;
						}else{
							B["hasPic"] = undefined;
						}
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
	    	  },"-",{
		   		xtype:"checkbox",
				boxLabel:"无图",
				id:"checkedBtn",
				listeners:{
					"check":function(field,newvalue,oldvalue){
						var _store = Ext.getCmp("marketGrid").getStore();
						var B = _store.baseParams;
						if(field.getValue()){
							B["hasPic"] = 0;
						}else{
							B["hasPic"] = undefined;
						}
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
	    	}];
		var c={
			id:"marketGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
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
			})
			
		};
		ast.ast1949.admin.products.market.marketGrid.superclass.constructor.call(this,c);
	},
	

});
ast.ast1949.admin.products.market.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel : "市场名称：",
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
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"所属行业",
					displayField : "label",
					valueField : "code",
					hiddenId:"industryCode",
					hiddenName:"industryCode",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["industry"] = field.getRawValue();
								}else{
									B["industry"]=undefined;
								}
								_store.baseParams = B;
								


							}
						}
				},{
					fieldLabel : "行业类别：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["category"] = undefined;
							}else{
								B["category"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combotree",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"市场所在地",
					displayField : "label",
					valueField : "code",
					hiddenId:"search-areaCode",
					hiddenName:"search-areaCode",
					editable:true,
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							fields : [ "label", "code" ],
							autoLoad: false,
							url:Context.ROOT + Context.PATH+ "/admin/category/childSearch.htm",
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
				},{
					xtype:"combo",
					mode:"local",
					fieldLabel:"添加来源",
					hiddenName:'companyId',
					triggerAction:"all",
					displayField:'name',
					valueField:'value',
			   		store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'客户添加',value:'1'},
						{name:'后台添加',value:'0'}
					]
					}),
					listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["companyId"] = field.getValue();
								}else{
									B["companyId"]=undefined;
								}
								_store.baseParams = B;
							}
						}	
				},{
					fieldLabel : "添加账号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["companyAccount"] = undefined;
							}else{
								B["companyAccount"] = newvalue;
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
		
		ast.ast1949.admin.products.market.SearchForm.superclass.constructor.call(this,c);

	}
});





