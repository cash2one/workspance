Ext.namespace("ast.ast1949");

ast.ast1949.GridPanelNoPage = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(_cfg){
		if(_cfg == null)
			_cfg = {};
		Ext.apply(this, _cfg);
		
		var _sm = this["sm"] == null ? '' : this["sm"];
		var _cm = this["cm"] == null? '' : this["cm"];
		var _reader = this["reader"] == null ? '' : this["reader"];
		var _storeUrl = this["storeUrl"] == null ? '' : this["storeUrl"];
		var _title = this["title"] == null ? '' : this["title"];
		var _params = this["baseParams"] == null ? {} : this["baseParams"];
		var _tbar = this["tbar"] == null ? [] : this["tbar"];
		var _height = this["height"] == null ? 200 : this["height"];
		var _width = this["width"] == null ? 500 : this["width"];
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:_reader,
			url: _storeUrl,
			autoLoad:true, //自动加载第一页
			baseParams:_params //加载后的基本参数
		});
		
		_store.on("beforeload", function(myds,options) {
			this.paramNames ={start:"page.startIndex",limit:"page.pageSize",sort:"page.sort",dir:"page.dir"};
		});
		
		ast.ast1949.GridPanelNoPage.superclass.constructor.call(this,{
			height:_height,
			width:_width,
			enableClumnMove:false,
			iconCls : "icon-grid",
			frame:true,
			viewConfig:{
				froceFit:true
			},
			title:_title,
			colModel:_cm,
			store : _store,
			selModel: _sm,
			paramNames : {sort:"page.sort",dir:"page.dir",start:"page.startIndex",limit:"page.pageSize"} ,
//			bbar : new Ext.PagingToolbar({
//				pageSize : Context.PAGE_SIZE,
//				store : _store,
//				displayInfo: true,
//				displayMsg: '显示第 {0} - {1} 第记录,共 {2} 条',
//				emptyMsg : '没有可显示的记录',
//				beforePageText : '第',
//				afterPageText : '页,共{0}页',
//				paramNames : {start:"page.startIndex",limit:"page.pageSize"}
//			}),
			tbar: _tbar
		});
		this.addEvents("query","add","edit","delete","view");
	},
	onQueryButtonClick : function(){
		this.fireEvent("query",this);
	},
	onAddButtonClick : function(){
		this.fireEvent("add",this);
	},
	onEditButtonClick : function(){
		this.fireEvent("edit",this);
	},
	onDeleteButtonClick : function(){
		this.fireEvent("delete",this);
	},
	onViewButtonClick : function(){
		this.fireEvent("view",this);
	}
	
});