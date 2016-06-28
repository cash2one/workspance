Ext.namespace("com.zz91.trade.buyrecycle");

var NEWS=new function(){
	
	this.COLLECTION_GRID="collectiongrid";
}

com.zz91.trade.buyrecycle.GRIDFIELD=[
	{name:"id",mapping:"buy.id"},
	{name:"cid",mapping:"buy.cid"},
	{name:"uid",mapping:"buy.uid"},
	{name:"category_code",mapping:"buy.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"compName",mapping:"compName"},
	{name:"check_status",mapping:"buy.checkStatus"},
	{name:"property_query",mapping:"buy.propertyQuery"},// 专业属性名字
	{name:"title",mapping:"buy.title"},
	{name:"details",mapping:"buy.details"},// 简要信息
	{name:"details_query",mapping:"buy.detailsQuery"},// 简要信息
	{name:"gmt_publish",mapping:"buy.gmtPublish"},
	{name:"gmt_refresh",mapping:"buy.gmtRefresh"},
	{name:"infoComeFrom",mapping:"buy.infoComeFrom"},
	{name:"del_status",mapping:"buy.delStatus"}
];

com.zz91.trade.buyrecycle.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/trade/tradebuy/queryDeleteTradeBuy.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.trade.buyrecycle.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,
			{
				hidden:true,
				header:"编号",
				dataIndex:"id"
				
			},{header:"公司", width:200, dataIndex:"compName",sortable:true},
			{header:"标题", width:360, dataIndex:"title",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var id=record.get("id");
					var v1="<a href='"+Context.ROOT+"/trade/tradebuy/edit.htm?id="+id+"' target='_blank'>"+value+"</a>";
					var v2="<a href='"+Context.ESITE+"/detail"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v2+v1;
				}
			},
			{header:"类别", width:120, dataIndex:"category_name",sortable:true},
			{header:"简要信息", width:100, dataIndex:"details_query",sortable:true},
			{header:"专业属性", width:100, dataIndex:"property_query",sortable:true},
			{
				header:"发布时间", width:130, dataIndex:"gmt_publish",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},
			{
				header:"刷新时间", width:130, dataIndex:"gmt_refresh",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var grid=this;
		
		var _bbar=com.zz91.utils.pageNav(_store);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"永久删除",
					iconCls:"delete16",
					handler:function(btn){
						grid.delStatus(1,_store);
					}
				},{
					text:"取消删除",
					iconCls:"play16",
					handler:function(btn){
						grid.delStatus(0,_store);
					}
				}]
			}),
			bbar:_bbar
		};
		
		com.zz91.trade.buyrecycle.grid.superclass.constructor.call(this,c);
	},
	delStatus:function(delStatus,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(delStatus==rows[i].get("del_status")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradebuy/updateDelStatus.htm",
				params:{"id":rows[i].get("id"),"delStatus":delStatus},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
					}
				},
				failure:function(response,opt){
					Ext.MessageBox.show({
						title:MESSAGE.title,
						msg : MESSAGE.submitFailure,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			});
		}
	},
});



