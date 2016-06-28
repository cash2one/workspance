Ext.namespace("ast.ast1949.analysis.kwsearch.kwsearch")

ast.ast1949.analysis.kwsearch.KEYWORDFIELD=[
	{name:"id",mapping:"id"},
	{name:"companyId",mapping:"companyId"},
	{name:"companyName",mapping:"companyName"},
	{name:"title",mapping:"title"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"productsTypeCode",mapping:"productsTypeCode"},
	{name:"mobile",mapping:"mobile"},
	{name:"area",mapping:"area"},
	{name:"vip",mapping:"vip"},
	{name:"productId",mapping:"productId"},
	{name:"numLogin",mapping:"numLogin"}
];
var typeMap={
			"10331000":"供应",
			"10331001":"求购",
			"10331002":"合作"			
		};
ast.ast1949.analysis.kwsearch.keywordSearchGrid=Ext.extend(Ext.grid.GridPanel,{
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
				header:"公司ID",
				hidden:true,
				width:100,
				dataIndex : "companyId"	
			},{    	
				header:"产品ID",
				hidden:true,
				width:100,
				dataIndex : "productId"	
			},{    	
				header:"客户信息",
				width:100,
				dataIndex : "companyName",
			},{    	
				header:"供求标题",
				width:100,
				dataIndex : "title"	
			},{    	
				header:"电话号码",
				width:100,
				dataIndex : "mobile"	
			},{    	
				header :"供/求",
			 	width :70,
				dataIndex: "productsTypeCode",
				renderer:function(value, metadata, record, rowIndex,colIndex, store){
    					return typeMap[value];
    				}
			},{    	
				header:"发布时间",
				width:200,
				dataIndex : "gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{    	
				header:"是否高会",
				width:100,
				dataIndex : "vip"	
			},{    	
				header:"登录次数",
				width:100,
				dataIndex : "numLogin"	
			},{    	
				header:"地区",
				width:100,
				dataIndex : "area"	
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/analysis/analysis/querykeyWordsearch.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.analysis.kwsearch.KEYWORDFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = ["-","关键字：",{
            			id:"keyword",
            			xtype:"textfield",
            			format:"Y-m-d"
           		},{
            			text:"搜索",
            			iconCls:"query",
            			handler:function(btn){
            				var keyword=Ext.getCmp("keyword").getValue();
            				_store.baseParams["keyword"]=keyword;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}})
            			}
			},{
					iconCls:"edit",
					text:"导出",
					handler:function(btn){
					
						Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
							if(btn!="yes"){
								return ;
							}else{
								var keyword=Ext.getCmp("keyword").getValue();
								Ext.Ajax.request({
								url: window.open(Context.ROOT+Context.PATH+ "/analysis/analysis/downLoadData.htm?keyword="+encodeURI(encodeURI(keyword))),
							});}
						});
					}
			}];
		var c={
			id:"keywordSearchGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			tbar:tbar,
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
			})
			
		};
		ast.ast1949.analysis.kwsearch.keywordSearchGrid.superclass.constructor.call(this,c);
	},
});
