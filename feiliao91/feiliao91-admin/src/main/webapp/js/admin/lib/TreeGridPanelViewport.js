Ext.namespace("ast.ast1949");

ast.ast1949.TreeGridPanelViewport = Ext.extend(Ext.Viewport,{
	constructor : function (_cfg){
		if(_cfg ==null)
			_cfg = {};
			
		Ext.apply(this,_cfg);
		
		var _grid = this["grid"] == null ? '' : this["grid"];
		var _tree = this["tree"] == null ? '' : this["tree"];
		
		var _panel = {
			region : "center",
			layout : "fit",
			items : [_grid]
		} ;
		
		var _treePanel = {
			region	: "east",
			layout	: "fit",
			width	: 300,
			items	: _tree 
		}
		
		ast.ast1949.StandardGridPanelViewport.superclass.constructor.call(this,{
			layout : "border",
			border : false,
			items : [_tree,_panel]
		});
	}
} );