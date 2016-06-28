Ext.namespace("ast.ast1949.admin.category");

ast.ast1949.admin.category.treePanel = function(_cfg){

	if(_cfg==null){
		_cfg = {};
	}

	var _el = _cfg["el"] || "";
	var _rootData = _cfg["rootData"] || "";

	var root = new Ext.tree.AsyncTreeNode({
		text:'所有类别',
		draggable:false,
		id:"node-0",
		data:_rootData
	});

	var data = new Ext.tree.TreeLoader({url: Context.ROOT+Context.PATH+"/admin/category/child.htm"});
	var tree = new Ext.tree.TreePanel({
		el : _el, // "adsplaces-tress",
		id:"aTree",
		root : root,
//		autoHight : true,
		height : "100%",
		border : false,
//		autoScroll : true,
		loader : data,
		tbar :[
		      {
		    	  text:"全部折叠",
	        		scope:this,
	        		handler:function(){
	        			this.collapseAll();
	        		}
		      }
		       ]
	});

	data.on("beforeload",function(treeLoader,node){

		this.baseParams["parentCode"] = node.attributes["data"];
	},data);

	tree.setRootNode(root);
	root.expand();
	return tree;

}