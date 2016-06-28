Ext.namespace("com.zz91.zhuanti.category");

var CATEGORY=new function(){
	this.TREE="categorytree";
	this.EDIT_WIN="editwin";
}

com.zz91.zhuanti.category.TreePanel = Ext.extend(Ext.tree.TreePanel,{
	rootVisible:true,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT + "/news/zhuanti/zhuantiCategory.htm",
		});
		var _rootVisible=this.rootVisible;
		var c={
			rootVisible:_rootVisible,
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				expanded:true,
				nodeType:"async",
				text:"所有类别",
				data:""
			},
			loader:treeLoad,
			tbar:[{
				text:"全部展开",
				scope:this,
				handler:function(btn){
					this.expandAll();
				}
			},{
				text:"全部折叠",
				scope:this,
				handler:function(btn){
					this.collapseAll();
				}
			}]
		};
		com.zz91.zhuanti.category.TreePanel.superclass.constructor.call(this,c);
	},
	initSelect:function(initValue){
	}
});
