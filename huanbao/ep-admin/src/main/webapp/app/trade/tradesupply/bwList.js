Ext.namespace("com.zz91.trade.bw");

var BW=new function(){
	this.BWID="bwid";
}
com.zz91.trade.bw.GRIDFIELD=[
      {name:"id",mapping:"id"},
      {name:"cid",mapping:"cid"},
      {name:"targetId",mapping:"targetId"},
      {name:"start",mapping:"start"},
      {name:"end",mapping:"end"},
      {name:"keywords",mapping:"keywords"},
      {name:"type",mapping:"type"},
      {name:"status",mapping:"status"},
      {name:"cname",mapping:"cname"},
      {name:"title",mapping:"title"},
      {name:"photoCover",mapping:"photoCover"},
      {name:"areaCode",mapping:"areaCode"},
      {name:"provinceCode",mapping:"provinceCode"},
      {name:"priceNum",mapping:"priceNum"},
      {name:"priceUnits",mapping:"priceUnits"},
      {name:"memberCode",mapping:"memberCode"},
      {name:"gmtRegister",mapping:"gmtRegister"},
      {name:"creditFile",mapping:"creditFile"},
      {name:"domainTwo",mapping:"domainTwo"},
      {name:"gmtCreated",mapping:"gmtCreated"},
      {name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.trade.bw.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
	config=config||{};
	Ext.apply(this,config);
	

	var _store=new Ext.data.JsonStore({
		root:"records",
		totalProperty:"totals",
		fields:com.zz91.trade.bw.GRIDFIELD,
		url:Context.ROOT+"/trade/tradesupply/queryBw.htm",
		autoLoad:true
	});
	
	var _sm=new Ext.grid.CheckboxSelectionModel({});
	var _cm=new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),_sm,
	     {
  			header:"编号",dataIndex:"id",sortable:true,hidden:true
         },{
			header:"公司编号",dataIndex:"cid",sortable:true,hidden:true
	     },{
			header:"信息编号",dataIndex:"targetId",sortable:true,hidden:true
	     },{
			header:"图片路径",dataIndex:"photoCover",sortable:true,hidden:true
	     },{
			header:"注册时间",dataIndex:"gmtRegister",sortable:true,hidden:true
	     },{
			header:"证书数量",dataIndex:"creditFile",sortable:true,hidden:true
	     },{
			header:"公司名称",dataIndex:"cname",width:180,sortable:true
		 },{
			header:"信息标题",dataIndex:"title",width:150,sortable:true
		 },{
			header:"标王类型",dataIndex:"type",sortable:true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var returnValue = value;
				if(value==0){
					returnValue="供应";
				}
				if(value==1){
					returnValue="求购";
				}
				return returnValue;
			}
		 },{
			header:"状态", width:60, dataIndex:"status",sortable:true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var returnValue = value;
				if(value==0){
					returnValue="暂停";
				}
				if(value==1){
					returnValue="正常";
				}
				return returnValue;
			}
		 },{
			header:"省份", width:60, dataIndex:"provinceCode",sortable:true
		 },{
			header:"地区", width:60, dataIndex:"areaCode",sortable:true
		 },{
			header:"会员类型", width:60, dataIndex:"memberCode",sortable:true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==10011001) {
					returnvalue="高级会员";
				}
				else {
					returnvalue="普通客户";
				}
				return returnvalue;
			}
		 },{
	 		header:"二级域名", width:60, dataIndex:"domainTwo",sortable:true
		 },{
			header:"单价", width:60, dataIndex:"priceNum",sortable:true
		 },{
			header:"单位", width:60, dataIndex:"priceUnits",sortable:true
		 },{
			header:"关键字", width:60, dataIndex:"keywords",sortable:true
		 },{
			header:"开始时间", width:120, dataIndex:"start",sortable:true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		 },{
			header:"结束时间", width:120, dataIndex:"end",sortable:true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		 }
	]);
	
	var grid=this;

	var c={
		store:_store,
		sm:_sm,
		cm:_cm,
		tbar:new Ext.Toolbar({
			items:[{
					text:"编辑",
					iconCls:"edit16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
							com.zz91.trade.bw.edit(rows);
						} 
					},"-",{
						text:"标记暂停",
						iconCls:"pause16",
						handler:function(btn){
							grid.pause(0,_store);
						}
					},"-",{
						text:"取消暂停",
						iconCls:"play16",
						handler:function(btn){
							grid.pause(1,_store);
						}
					},"->",{
						xtype:"datefield",
						width:100,
						format:"Y-m-d",
						emptyText:"标王开始时间",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["startStr"]=newValue;
								grid.getStore().reload();
							}
						}
					},"-",{
						xtype:"datefield",
						width:100,
						format:"Y-m-d",
						emptyText:"标王结束时间",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["endStr"]=newValue;
								grid.getStore().reload();
							}
						}
					},"-",{
						xtype:"combo",
						width:100,
						emptyText:"暂停/未暂停",
						name:"statusStr",
						hiddenName:"status",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","暂停"],["1","正常"],["","全部"]]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["status"]=newValue;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
							xtype:"textfield",
							width:120,
							emptyText:"关键字",
							listeners:{
								"change":function(field,newValue,oldValue){
									grid.getStore().baseParams["keyword"]=newValue;
									grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
								}
							}
						},{
							text:"搜索",
							iconCls:"websearch16",
							handler:function(btn){
							}
						}
		        ]
	       }),
	       bbar:com.zz91.utils.pageNav(_store)
      };
		com.zz91.trade.bw.grid.superclass.constructor.call(this,c);
	},
	pause:function(status,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(status==rows[i].get("status")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradesupply/updateStatus.htm",
				params:{"id":rows[i].get("id"),"status":status},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
					}else{
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
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
	}

});

com.zz91.trade.bw.edit=function(rows){
	for(var i=0;i<rows.length;i++){
		window.open(Context.ROOT+"/trade/tradesupply/bwDetails.htm?id="+rows[i].get("id"));
	}
}