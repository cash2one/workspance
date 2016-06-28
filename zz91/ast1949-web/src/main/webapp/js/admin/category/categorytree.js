Ext.namespace("ast.ast1949.admin.category");
//var data = new Ext.tree.TreeLoader({url: Context.ROOT+Context.PATH+"/admin/category/child.htm"});

//TreePanel Start
ast.ast1949.admin.category.categoryTreePanel = Ext.extend(Ext.tree.TreePanel,{

	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/admin/category/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["parentCode"] = node.attributes.data;
				}
			}
		});
		
		var c = {
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
	        listeners:{}
		};
		
		ast.ast1949.admin.category.categoryTreePanel.superclass.constructor.call(this,c);
	}
});
//TreePanel End