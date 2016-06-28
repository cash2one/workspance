Ext.namespace("ast.ast1949.lib");

ast.ast1949.lib.ComboTreeField= Ext.extend(Ext.form.ComboBox,{
	constructor : function (_cfg){
		if(_cfg ==null)
			_cfg = {};
			
		Ext.apply(this,_cfg);
		
		var _fieldLabel = this["fieldLabel"] || "";
//		alert(_fieldLabel);
		var _url = this["url"] || "";
		var _valueField = this["valueField"] || "";
		var _name = this["name"] || "";
		var _id = this["id"] || "";
		var _anchor = this["anchor"] || "100";
		
		var _treePanel=new Ext.tree.TreePanel({
			loader:new Ext.tree.TreeLoader({
				dataUrl	: _url
			}),
			root : new Ext.tree.AsyncTreeNode({
				text	: "载入中...",
				id		: "0"
			}),
			listeners : {
				"click"	: {
					fn 	: function(node){
						_valueField.setValue(node.id);
						var combo = Ext.getCmp(_id);
						combo.setValue(node.text);
						combo.collapse();
					}
				}
			}
		});
		
		ast.ast1949.lib.ComboTreeField.superclass.constructor.call(this,{
			id		: _id,
			name	: _name,
			fieldLabel	: _fieldLabel,
			store	: new Ext.data.SimpleStore({
				fields	: [],
				data	: [[]]
			}),
			editable : false,
			model	: "local",
			triggerAction	: "all",
			anchor			: _anchor,
			autoHeight 		: true,
			tpl				: "<tpl for='.' ><div style='height:200px'><div id='tree'></div></div></tpl>",
			selectedClass	: "",
			onSelect 		: Ext.emptyFn,
			listeners		: {
				"expand"	: {
					fn : function(){
						_treePannel.render("tree");
						_treePanel.expandAll();
					},
					scope : this
				}
			}
		});
	}
} );