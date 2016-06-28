Ext.namespace("ast.ast1949.admin.yuanliao");

ast.ast1949.admin.yuanliao.treePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _rootData = this.rootData;
		var _isAssist = this.isAssist;

		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/yuanliao/categoryTreeNode.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["parentCode"] = node.attributes["data"];
				}
			}
		});

		var c={
			rootVisible:true,
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				nodeType:'async',
				text:'所有类别',
				id:"node-0",
				data:_rootData
			},
			loader:treeLoad,
			tbar:[
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

		ast.ast1949.admin.yuanliao.treePanel.superclass.constructor.call(this,c);

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