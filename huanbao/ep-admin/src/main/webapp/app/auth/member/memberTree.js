Ext.namespace("com.zz91.ep.member");

com.zz91.ep.member.MemberSelector = function(dbclickfn){
	var memberTree = new com.zz91.ep.member.Tree({
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	memberTree.on("dblclick",dbclickfn);
	
	var win = new Ext.Window({
		id:"memberselector",
		title:"选择会员类型",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[memberTree]
	});
	
	win.show();
}

/**
 * 会员类型树
 */
com.zz91.ep.member.Tree = Ext.extend(Ext.tree.TreePanel,{
	nodeUrl:Context.ROOT+"/auth/crmmember/crmMemberChild.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var root=new Ext.tree.AsyncTreeNode({
			text:"所有会员类型",
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
		
		com.zz91.ep.member.Tree.superclass.constructor.call(this,c);
		
	}
})