Ext.namespace("ast.ast1949.admin.xiazai");

ast.ast1949.admin.xiazai.treePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _rootData = this.rootData;
		var _isAssist = this.isAssist;

		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/admin/xiazai/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["code"] = node.attributes["data"];
				}
			}
		});

//		var tree = this;

		var c={
//			width:220,
			rootVisible:true,
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				nodeType:'async',
				text:'所有类别',
				id:"node-0",
				data:_rootData,
				isAssist:_isAssist
			},
			loader:treeLoad,
			tbar:[
//				{
//	        		text:"展开",
//	        		scope:this,
//	        		handler:function(btn){
//	        			this.expandAll();
//	        		}
//	        	},
	        	{
	        		text:"全部折叠",
	        		scope:this,
	        		handler:function(){
	        			this.collapseAll();
	        		}
	        	}
	        ],
	        contextMenu:this.contextmenu,
	        listeners:{
	        	contextmenu:function(node,e){
	        		node.select();
	        		var c = node.getOwnerTree().contextMenu;
		            c.contextNode = node;
		            c.showAt(e.getXY());
	        	}
	        }
		};

		ast.ast1949.admin.xiazai.treePanel.superclass.constructor.call(this,c);

	},
	contextmenu:new Ext.menu.Menu({
		items: [{
            id: 'cm-add',
            cls:'item-add',
            text: '增加类别',
            handler:function(btn){

            }
        }]
	})
});

//ast.ast1949.admin.xiazai.treePanel = function(_cfg){
//
//	if(_cfg==null){
//		_cfg = {};
//	}
//
//	var _el = _cfg["el"] || "";
//	var _rootData = _cfg["rootData"] || "";
//	var _isAssist=_cfg["isAssist"]||"";
//
//	var root = new Ext.tree.AsyncTreeNode({
//		text:"所有类别",
//		draggable:false,
//		id:"node-0",
//		data:_rootData,
//		isAssist:_isAssist
//	});
//
//	var data = new Ext.tree.TreeLoader({url: Context.ROOT+Context.PATH+"/admin/categoryproducts/child.htm"});
//	var tree = new Ext.tree.TreePanel({
//		el:_el,   //"category-tree",
////		title:_title,
//		id:"category-tree",
//		root : root,
//		autoHight : true,
//		height : "100%",
//		border : false,
//		autoScroll : true,
//		loader : data
//	});
//
//	data.on("beforeload",function(treeLoader,node){
//		this.baseParams["parentCode"] = node.attributes["data"];
//		this.baseParams["isAssist"] = root.attributes["isAssist"];
//	},data);
//
//	tree.setRootNode(root);
//	tree.render();
//	root.expand();
//
//	return tree;
//
//}