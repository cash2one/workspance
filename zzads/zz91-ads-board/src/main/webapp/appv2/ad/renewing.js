Ext.namespace("com.zz91.ads.board.ad.ad");

Ext.define("AdGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"renew.id"},
		{name:"gmtStart",mapping:"renew.gmtStart"},
		{name:"gmtEnd",mapping:"renew.gmtEnd"},
		{name:"dealer",mapping:"renew.dealer"},
		{name:"detail",mapping:"renew.detail"},
		{name:"gmtDeal",mapping:"renew.gmtDeal"},
		{name:"applicant",mapping:"renew.applicant"},
		{name:"positionName",mapping:"positionName"},
		{name:"advertiserName",mapping:"advertiserName"},
		{name:"requestUrl",mapping:"requestUrl"},
		{name:"searchExact",mapping:"ad.searchExact"},
		{name:"width",mapping:"width"},
		{name:"height",mapping:"height"},
		{name:"email",mapping:"email"}
	]
});

Ext.define("com.zz91.ads.board.ad.ad.BaseGrid",{
	extend:"Ext.grid.Panel",
        initComponent:function(){
		var _store=Ext.create("Ext.data.Store",{
			model:"AdGridModel",
			remoteSort:true,
			pageSize:Context.PAGE_SIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/ad/booking/queryRenewInfo.htm",
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
			autoLoad:false
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1
			});
		
		var _cm=[{
				text:"编号",
				dataIndex:"id",
				width:50,
				hidden:true
			},{
				header:"状态",
				width:60,
				dataIndex:"dealer",
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					if(value!=null&&value!=''){
						return "<img src='"+Context.ROOT+"/themes/icons/review_Y.png'/>";
					}else{
						return "<img src='"+Context.ROOT+"/themes/icons/review_U.png'/>";
					}
				}
			},{
				header:"广告主",
				width:150,
				dataIndex:"advertiserName",
				sortable:true,
				editor: {
					xtype:"textfield",
	                allowBlank: false
	            },
	            renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var str=value;
					if(record.get("email")!=""){
						str="<a href='"+Context.ROOT+"/ad/ad/index.htm?email="+record.get("email")+"' target='_blank'>"+value+"</a>";
					}
					return str+"<br />规格：宽"+record.get("width")+"px;高"+record.get("height")+"px";
				}
			},{
				header:"广告位",
				dataIndex:"positionName",
				sortable:false,
				width:150,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					var str=value;
					if(record.get("requestUrl")!=""){
						str="<a href='"+record.get("requestUrl")+"' target='_blank'>"+value+"</a>";
					}
					return str+"<br />规格：宽"+record.get("width")+"px;高"+record.get("height")+"px";
				}
			},{
				header:"搜索条件（关键词）",
				dataIndex:"searchExact",
				sortable:false,
				width:100
			},{
				header:"申请人",
				sortable:false,
				dataIndex:"applicant"
			},{
				header:"开始时间",
				dataIndex:"gmtStart",
				sortable:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				header:"结束时间",
				dataIndex:"gmtEnd",
				sortable:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			},{
				header:"备注",
				dataIndex:"detail",
				sortable:true
			},{
				header:"操作人",
				dataIndex:"dealer",
				sortable:true
			},{
				header:"操作时间",
				dataIndex:"gmtDeal",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			}];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			}],
			plugins:[cellEditing]
		};
		
		Ext.applyIf(this,c);
		
		this.callParent();
	},
            loadByEmail:function(checkStatus){
                 this.getStore().setExtraParam("checkStatus", checkStatus);
		this.getStore().load();
	}
});

Ext.define("com.zz91.ads.board.ad.ad.SimpleGrid", {
	extend:"com.zz91.ads.board.ad.ad.BaseGrid",
	initComponent:function(){
		
		var c={
			tbar:["->",{
				xtype:"textfield",
				emptyText:"请输入Email",
				listeners:{
					"change":function(field,nv,ov){
						this.up("grid").getStore().setExtraParam("email", nv);
						this.up("grid").getStore().load();
					}
				}
			},{
				xtype:"textfield",
				emptyText:"请输入要搜索的关键字",
				id:"searchKeywords",
				listeners:{
					"change":function(field,nv,ov){
						this.up("grid").getStore().setExtraParam("anchorPoint", nv);
						this.up("grid").getStore().load();
					}
				}
			},{
				xtype:"checkboxfield",
				boxLabel:"申请中",
				checked:true,
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("checkStatus", 0);
					}else{
						this.up("grid").getStore().setExtraParam("checkStatus", null);
					}
					this.up("grid").getStore().load();
				}
			},{
				xtype:"checkboxfield",
				boxLabel:"已续费",
				handler:function(field){
					if(field.getValue()){
						this.up("grid").getStore().setExtraParam("checkStatus", 1);
					}else{
						this.up("grid").getStore().setExtraParam("checkStatus", null);
					}
					this.up("grid").getStore().load();
				}
			}],
		}
		
		Ext.applyIf(this,c);
		
		this.callParent();
	}
});
