Ext.namespace("ast.ast1949.admin.categoryproducts");

ast.ast1949.admin.categoryproducts.treePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _rootData = this.rootData;
		var _isAssist = this.isAssist;

		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/admin/good/goodsChild.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["parentCode"] = node.attributes["data"];
					this.baseParams["isAssist"] = _isAssist;
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

		ast.ast1949.admin.categoryproducts.treePanel.superclass.constructor.call(this,c);

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