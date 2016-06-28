Ext.namespace("ast.ast1949.admin.category");

ast.ast1949.admin.category.provincesTreePanel = function(_cfg){

	if(_cfg==null){
		_cfg = {};
	}

	var _el = _cfg["el"] || "";
	var _rootData = _cfg["rootData"] || "";

	var root = new Ext.tree.AsyncTreeNode({
		text:"所有类别",
		draggable:false,
		id:"node-0",
		data:_rootData
	});

	var data = new Ext.tree.TreeLoader({url:Context.ROOT+Context.PATH+"/admin/category/childProvinces.htm"});
	var tree = new Ext.tree.TreePanel({
		el:_el,   //"category-tree",
//		title:_title,
		id:"category-tree",
		root : root,
		autoHight : true,
		height : "100%",
		border : false,
		autoScroll : false,
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