Ext.namespace("ast.ast1949.admin.productsAlbums")



ast.ast1949.admin.productsAlbums.treePanel = function(_cfg) {

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
	var data = new Ext.tree.TreeLoader({url: Context.ROOT+Context.PATH+"/admin/productsalbums/child.htm"});
	var tree = new Ext.tree.TreePanel({
		el:_el,  
        id:"productsAlbums-tree",
		root:root,
		autoHight:true,
		height:"100%",
		border:false,
		autoScroll:false,
		loader:data
	});
  // alert(node.attributes["data"]);
	data.on("beforeload",function(treeLoader,node){
	
	//	alert(node.attributes["data"])
		this.baseParams["id"] = node.attributes["data"];//点击节点时的链接
	},data);

	tree.setRootNode(root);
	tree.render();
	root.expand();


	return tree;

}
