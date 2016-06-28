Ext.namespace("ast.ast1949.admin.categoryCompanyPrice");

ast.ast1949.admin.categoryCompanyPrice.treePanel = function(_cfg){

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

	var data = new Ext.tree.TreeLoader({url: Context.ROOT+Context.PATH+"/admin/categorycompanyprice/child.htm"});
	var tree = new Ext.tree.TreePanel({
		el : _el, // "adsplaces-tress",
		id:"adsplaces-tree",
			layout:"fit",
		region:"west",
		root : root,
//		autoHight : true,
		border:false,
		height : "100%",
		border : false,
		autoScroll : true,
		loader : data
	});

	data.on("beforeload",function(treeLoader,node){

		this.baseParams["parentCode"] = node.attributes["data"];
	},data);

	tree.setRootNode(root);
tree.render();
	root.expand();
	return tree;

}