Ext.namespace("com.zz91.ep.crm");

com.zz91.ep.crm.StarCompField=[
	{name:"compId",mapping:"compId"},
	{name:"cname",mapping:"cname"},
	{name:"email",mapping:"email"},
	{name:"star",mapping:"star"},
	{name:"gmtLogin",mapping:"gmtLogin"},
	{name:"loginCount",mapping:"loginCount"},
	{name:"gmtNextContact",mapping:"gmtNextContact"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtRegister",mapping:"gmtRegister"},
	{name:"saleName",mapping:"saleName"}
];

com.zz91.ep.crm.StarCompGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			fields:com.zz91.ep.crm.StarCompField,
			url:Context.ROOT + "/sale/data/queryCompByStar.htm",
			autoLoad:true
		});
		
		var grid = this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				hidden:true,
				header : "公司ID",
				dataIndex : "compId"
			},{
				header:"公司名称",
				dataIndex:"cname",
				sortable:true,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var cid=record.get("compId");
					var v=value;
					if (v==""){
						return "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?id="+cid+"' target='_blank'>公司名称暂无</a>";
					}else{
						return "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?id="+cid+"' target='_blank'>"+v+"</a>";
					}
				}
			},{
				header:"Email",
				dataIndex:"email",
				width:200,
				sortable:true
			},{
				header:"星级",
				dataIndex:"star",
				sortable:true
			},{
				header :"最近登录",
				dataIndex:"gmtLogin",
				width:130,
				sortable : true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d h:i:s');
					}
				}
			},{
				header :"登陆次数",
				dataIndex:"loginCount",
				sortable : true
			},{
				header :"下次联系时间",
				dataIndex:"gmtNextContact",
				width:130,
				sortable : true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d h:i:s');
					}
				}
			},{
				header :"最后联系时间",
				dataIndex:"gmtCreated",
				width:130,
				sortable : true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d h:i:s');
					}
				}
			},{
				header :"注册时间",
				dataIndex:"gmtRegister",
				width:130,
				sortable : true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d h:i:s');
					}
				}
			},{
				header : "拥有者",
				width:200,
				dataIndex : "saleName"
			}
		]);
	
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar: com.zz91.utils.pageNav(_store)
		}
		com.zz91.ep.crm.StarCompGrid.superclass.constructor.call(this,c);
	},
	loadStarGrid:function(star,tDate,account){
		this.getStore().baseParams["star"]=star;
		this.getStore().baseParams["tDate"]=tDate;
		this.getStore().baseParams["account"]=account;
	}
});

