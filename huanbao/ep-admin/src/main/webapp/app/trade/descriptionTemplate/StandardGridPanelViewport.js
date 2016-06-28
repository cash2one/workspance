Ext.namespace("com.zz91");

com.zz91.StandardGridPanelViewport = Ext.extend(Ext.Viewport,{
	constructor : function (_cfg){
		if(_cfg ==null)
			_cfg = {};
			
		Ext.apply(this,_cfg);
		
		var _grid = this["grid"] ==null ? '' : this["grid"];
		
		var _panel = {
			region : "center",
			layout : "fit",
			items : [_grid]
		} ;
		
		com.zz91.StandardGridPanelViewport.superclass.constructor.call(this,{
			layout : "border",
			border : false,
			items : [_panel]
		});
	}
} );