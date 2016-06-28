Ext.namespace("ast.ast1949.admin.category");

ast.ast1949.admin.category.provincesTreePanel=Ext.extend(Ext.tree.TreePanel,{
	rootData:"",
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
	
		var _rootData = this.rootData||"";
	
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT+Context.PATH+"/admin/category/childProvinces.htm",
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
			root:{
				nodeType:'async',
				text:'所有类别',
				id:"node-0",
				data:_rootData
			},
			loader:treeLoad,
			tbar:[
				{
	        		text:"全部展开",
	        		scope:this,
	        		handler:function(btn){
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
	        listeners:{
				"contextmenu":function(node,event){
					if(config.contextmenu!="undefiend" || config.contextmenu!=null){
						event.preventDefault();
						config.contextmenu.showAt(event.getXY());
						node.select();
					}
				}
			}
		};
		ast.ast1949.admin.category.provincesTreePanel.superclass.constructor.call(this,c);
	}
});

