Ext.namespace("ast.ast1949.admin.price.category")

//TreePanel Start
ast.ast1949.admin.price.category.TreePanel = Ext.extend(Ext.tree.TreePanel,{

	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+ "/admin/pricecategory/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["id"] = node.attributes.data;
				}
			}
		});
		
		var c = {
			width:220,
			rootVisible:true,
			autoScroll:true,
			animate:true,
//			root:{
//				nodeType:'async',
//				text:'所有类别',
//				data:0
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
	        contextMenu:this.contextmenu,//右键菜单
	        listeners:{
//	        	contextmenu:function(node,e){
//	        		node.select();
//	        		var c = node.getOwnerTree().contextMenu;
//		            c.contextNode = node;
//		            c.showAt(e.getXY());
//	        	}
	        }
		};
		ast.ast1949.admin.price.category.TreePanel.superclass.constructor.call(this,c);
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
//TreePanel End