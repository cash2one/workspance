Ext.namespace("com.zz91.ep.svr");

com.zz91.ep.svr.SvrSelector = function(dbclickfn){
	var svrTree = new com.zz91.ep.svr.Tree({
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	svrTree.on("dblclick",dbclickfn);
	
	var win = new Ext.Window({
		id:"svrselector",
		title:"选择服务类型",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[svrTree]
	});
	
	win.show();
}

/**
 * 服务类型树
 */
com.zz91.ep.svr.Tree = Ext.extend(Ext.tree.TreePanel,{
	nodeUrl:Context.ROOT+"/auth/crmsvr/crmSvrChild.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var root=new Ext.tree.AsyncTreeNode({
			text:"所有服务类型",
			draggable:false,
			id:"0",
			data:"",
			expanded:true
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
		
		com.zz91.ep.svr.Tree.superclass.constructor.call(this,c);
		
	}
})