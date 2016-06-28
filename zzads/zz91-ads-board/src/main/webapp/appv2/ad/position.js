Ext.namespace("com.zz91.ads.board.ad.position");

Ext.define("PositionTreeModel",{
	extend: 'Ext.data.Model',
	fields:[{name:"id",mapping:"id"},{name:"text",mapping:"text"},{name:"leaf",mapping:"leaf"}]
});