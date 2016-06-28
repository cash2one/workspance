Ext.namespace("com.zz91.ep.common.area");

com.zz91.ep.common.area.RightSelector = function(dbclickfn){
	var areaTree = new com.zz91.ep.common.area.Tree({
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	areaTree.on("dblclick",dbclickfn);
	
	var win = new Ext.Window({
		title:"选择权限",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[areaTree]
	});
	
	win.show();
}

/**
 * 权限树
 */

com.zz91.ep.common.area.Tree = Ext.extend(Ext.tree.TreePanel,{
	nodeUrl:Context.ROOT+"/syscommon/queryAreaChild.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var root=new Ext.tree.AsyncTreeNode({
			text:"全部地区",
			draggable:false,
			id:"0",
			data:""
		});
		var _nodeUrl=this.nodeUrl;
		var dataLoader = new Ext.tree.TreeLoader({
			url: _nodeUrl,
			listeners:{
				"beforeload":function(treeLoader,node){
					this.baseParams["parentCode"]=node.attributes["data"];
				}
			}
		});
		
		var c={
			root:root,
			autoScroll:true,
			loader:dataLoader,
			tbar:[{
				text:"全部展开",
				scope:this,
				handler:function(btn){
					this.getRootNode().expand(true);
				}
			},{
				text:"全部折叠",
				scope:this,
				handler:function(btn){
					this.getRootNode().collapse(true);
				}
			}],
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
		
		com.zz91.ep.common.area.Tree.superclass.constructor.call(this,c);
		
	}
})