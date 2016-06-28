Ext.BLANK_IMAGE_URL = "../../resources/images/default/s.gif"

// shorthand
var Tree = Ext.tree;

var tree = new Tree.TreePanel({
    el:"tree-div",
    useArrows:true,
    autoScroll:true,
    animate:true,
    enableDD:true,
    containerScroll: true, 
    loader: new Tree.TreeLoader({
        dataUrl:Context.ROOT + Context.PATH + "/admin/news/newsModule/children.htm?parentId=" + 0
    })
});

tree.on("checkchange", function(node, checked) {   
	node.expand();   
	node.attributes.checked = checked;   
	node.eachChild(function(child) {   
		child.ui.toggleCheck(checked);   
		child.attributes.checked = checked;   
		child.fireEvent("checkchange", child, checked);   
	});   
}, tree);  

// set the root node
var root = new Tree.AsyncTreeNode({
    text: "Ext JS",
    draggable:false,
    id:"source"
});
tree.setRootNode(root);

// render the tree
tree.render();
root.expand();