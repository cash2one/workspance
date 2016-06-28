Ext.namespace("ast.ast1949.admin.bbscategorytree");

ast.ast1949.admin.bbscategorytree.treePanel = function(_cfg){

	if(_cfg==null){
		_cfg = {};
	}

	var _el = _cfg["el"] || "";
	var _rootData = _cfg["rootData"] || "";
//	var _title = _cfg["title"] || "";

	var root = new Ext.tree.AsyncTreeNode({
		text:"所有类别",
		draggable:false,
		id:"node-0",
		data:_rootData
	});

	var data = new Ext.tree.TreeLoader({url: Context.ROOT+Context.PATH+"/zz91/bbs/bbscategory/categoryTreeNode.htm"});
	var tree = new Ext.tree.TreePanel({
		id:"bbsCategory-tree",
		el:_el,   //"category-tree",
		root:root,
		autoHight:true,
		height:"100%",
		border:false,
		autoScroll:true,
		loader:data
	});

	data.on("beforeload",function(treeLoader,node){
//		alert(node.attributes["data"])
		this.baseParams["id"] = node.attributes["data"];//点击节点时的链接
	},data);

	tree.setRootNode(root);
	tree.render();
	root.expand();

//	拖拽事件处理
    tree.on("nodedrop",function(e){

    	if(e.point == "append"){
    		alert("拖拽功能尚未完成,拖拽信息未能保存到数据库!");
//    		alert('当前"【'+e.dropNode.text+'】"被放到目录"【'+e.target.text+'】"下！');
    		//服务器处理对应事件
    	}else if(e.point == "above"){
    		alert("拖拽功能尚未完成,拖拽信息未能保存到数据库!");
//    		alert('当前"'+e.dropNode.text+'"放在了"'+e.target.text+'"上面！');
    		//服务器对应事件处理
    	}else if(e.point == "below"){
    		alert("拖拽功能尚未完成,拖拽信息未能保存到数据库!");
//    		alert('当前"'+e.dropNode.text+'"放在了"'+e.target.text+'"下面！');
    		//服务器对应事件处理
    	}
    });

	return tree;

}
