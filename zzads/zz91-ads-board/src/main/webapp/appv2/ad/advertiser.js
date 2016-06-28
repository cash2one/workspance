Ext.namespace("com.zz91.ads.board.ad.advertiser")

Ext.define("GridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"name",mapping:"name"},
		{name:"contact",mapping:"contact"},
		{name:"phone",mapping:"phone"},
		{name:"email",mapping:"email"},
		{name:"remark",mapping:"remark"},
		{name:"category",mapping:"category"},
		{name:"deleted",mapping:"deleted"},
		{name:"gmtCreated",mapping:"gmtCreated"},
		{name:"gmtCreated",mapping:"gmtCreated"}
	]
});

Ext.define("com.zz91.ads.board.ad.advertiser.MainGrid",{
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"GridModel",
			remoteSort:true,
			pageSize:Context.PAGE_SIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/ad/advertiser/query.htm",
				simpleSortMode:true,
				reader: {
					type: 'json',
					root: 'records',
					totalProperty:"totals"
				},
				actionMethods:{
					reader:"post"
				}
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[
			{text:"编号",dataIndex:"id",width:30,hidden:true},
			{header:"类别",dataIndex:"category",renderer:function(v,m,record,ridx,cidx,store,view){
				if(v!=null) {
					return Context.ADVERTISER_CATEGORY[v];
				} else {
					return "";
				}
			}},
			{text:"广告主",dataIndex:"name",width:200,sortable:false},
			{text:"联系人",dataIndex:"contact",width:60,sortable:false},
			{text:"联系电话",dataIndex:"phone",sortable:false},
			{text:"邮箱",dataIndex:"email", sortable:false}
		];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			},{
				xtype: 'toolbar',
				dock:"top",
				items:[{
					xtype:"textfield",
					emptyText:"广告主",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("name",nv);
							this.up("grid").getStore().load();
						}
					}
				},{
					xtype:"textfield",
					emptyText:"email",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("email",nv);
							this.up("grid").getStore().load();
						}
					}
				}, Ext.create("com.zz91.util.LocalCombo",{
					emptyText:"选择类别",
					editable:false,
					localData:[
                        {name : Context.ADVERTISER_CATEGORY[0],   value: "0"},
                        {name : Context.ADVERTISER_CATEGORY[1],  value: "1"},
                        {name : Context.ADVERTISER_CATEGORY[2],  value: "2"}
                    ],
					listeners:{
						"change":function(field,nv,ov,e){
							if(Context.ADVERTISER_CATEGORY[nv]){
								this.up("grid").getStore().setExtraParam("category",nv);
								this.up("grid").getStore().load();
							}
						}
					}
				})]
			}]
		};
		
		Ext.applyIf(this,c);
		
		this.callParent();
	}
});