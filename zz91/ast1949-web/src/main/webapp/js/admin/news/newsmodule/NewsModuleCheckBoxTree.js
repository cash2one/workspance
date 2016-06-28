Ext.namespace("ast.ast1949.admin.news.newsmodule");

ast.ast1949.admin.news.newsmodule.checkBoxTreePanel = Ext.extend(Ext.tree.TreePanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _ids = this["ids"] || '';//被选中的节点的编号，如1,2,3,5,
		
//		var treeLoad = new Ext.tree.TreeLoader({
//			dataUrl:Context.ROOT + Context.PATH + "/admin/news/newsmodule/children.htm?parentId=0"
//		});
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT + Context.PATH + "/admin/news/newsmodule/children.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["parentId"] = node.attributes.data;
					this.baseParams["ids"] = _ids;
				}
			}
		});
		
		var c={
//			width:220,
			enableDD:true,
	        containerScroll: true,
	        
			rootVisible:true,
			autoScroll:true,
			animate:true,
			split:true,
			
//			root:{
//				nodeType:'async',
//				text:'所有类别',
//				id:"node-0",
//				data:_rootData,
//				isAssist:_isAssist
//			},
			loader:treeLoad,
			tbar:[
	        	{
	        		text:"全部展开",
	        		scope:this,
	        		handler:function(){
	        			this.expandAll();
	        		}
	        	},{
	        		text:"全部折叠",
	        		scope:this,
	        		handler:function(){
	        			this.collapseAll();
	        		}
	        	}
	        ],
	        contextMenu:this.contextmenu,
	        listeners:{
//	        	contextmenu:function(node,e){
//	        		node.select();
//	        		var c = node.getOwnerTree().contextMenu;
//		            c.contextNode = node;
//		            c.showAt(e.getXY());
//	        	}
	        }
		};
		
		ast.ast1949.admin.news.newsmodule.checkBoxTreePanel.superclass.constructor.call(this,c);
	},
	//右键菜单
	contextmenu:new Ext.menu.Menu({
		items:[{
//			id:"cm-add",
//			cls:"item-add",
//			text:"添加类别",
//			hendler:function(btn){
////				ast.ast1949.admin.price.category.AddCategoryWin();
//			}
		}]
	})
});