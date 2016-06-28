Ext.namespace("com.zz91.ep.mblog");

//var COMP = new function(){
//	this.COMP_GRID = "compgrid";
//	this.WEEKLY_WIN = "weeklywin";
//}
 
com.zz91.ep.mblog.Field=[
	{name:"id",mapping:"mBlogInfo.id"},
	{name:"cid",mapping:"mBlogInfo.cid"},
	{name:"account",mapping:"mBlogInfo.account"},
	{name:"realName",mapping:"mBlogInfo.realName"},
	{name:"headPic",mapping:"mBlogInfo.headPic"},
	{name:"name",mapping:"mBlogInfo.name"},
	{name:"gmtCreated",mapping:"mBlogInfo.gmtCreated"},
	{name:"isDelete",mapping:"mBlogInfo.isDelete"},
	{name:"compName",mapping:"compProfile.name"},
	{name:"areaName",mapping:"areaName"},
	{name:"address",mapping:"address"},
	{name:"provinceName",mapping:"provinceName"},
	{name:"gmtLogin",mapping:"gmtLogin"},
];

//com.zz91.ep.mblog.FILTER_RECOMMEND=[];

//com.zz91.ep.mblog.FILTER_SUBREC=[];

com.zz91.ep.mblog.grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.mblog.Field,
				url:Context.ROOT +  "/news/mbloginfo/queryAllMblogInfo.htm",
				autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			hidden:true,
			header : "编号",
			dataIndex : "id"
		},{
			hidden:true,
			header : "公司号",
			dataIndex : "cid"
		},{
			header:"昵称",
			width:120,
			dataIndex:"name",
			sortable:true
		},{
			header:"环保帐号",
			width:120,
			dataIndex:"account",
			sortable:true
			
		},{
			header:"删除状态",
			width:60,
			dataIndex:"isDelete",
			sortable:true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==1) {
					returnvalue = "已冻结";
				}else{
					returnvalue = "未冻结";
				}
				return returnvalue;
			}
		},{
			header:"地区",
			width:120,
			dataIndex:"address",
			sortable:true
		},{
			header:"性别",
			width:60,
			dataIndex:"sex",
			sortable:true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==0) {
					returnvalue = "男";
				}else{
					returnvalue = "女";
				}
				return returnvalue;
			}
		},{
			header : "公司名",
			sortable : true,
			width : 210,
			dataIndex : "compName"
		},{
			header :"注册时间",
			sortable : true,
			width : 150,
			dataIndex:"gmtCreated",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}	
		},{
			header : "最后登入时间",
			sortable : true,
			width : 150,
			dataIndex : "gmtLogin",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
			
		}
		]);
		var grid = this;
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"冻结帐号",
					iconCls:"delete16",
					handler:function(btn){
						grid.unCompCode("100010001005",_store);
					}
				},{
					text:"拉入黑名单",
					iconCls:"play16",
					handler:function(btn){
						grid.unCompCode("100010001005",_store);
					}
				},"->",{
						text:"确认搜索",
						iconCls:"websearch16",
						handler:function(btn){
						}
					},"->",{
					text:"选择搜索条件",
					menu:[{
						xtype:"textfield",
						width:200,
						emptyText:"按昵称搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var C=grid.getStore().baseParams;
								C=C||{};
								C["name"]=newValue;
								grid.getStore().baseParams=C;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},{
						xtype:"textfield",
						width:200,
						emptyText:"按环保帐号搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var C=grid.getStore().baseParams;
								C=C||{};
								C["account"]=newValue;
								grid.getStore().baseParams=C;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},{
						xtype:"textfield",
						width:200,
						emptyText:"按地区搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var C=grid.getStore().baseParams;
								C=C||{};
								C["address"]=newValue;
								grid.getStore().baseParams=C;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},{
						xtype : "textfield",
						width:200,
						emptyText:"按公司名称搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var C=grid.getStore().baseParams;
								C=C||{};
								C["compName"]=newValue;
								grid.getStore().baseParams=C;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}
				  ]
			  }]
			}),
			bbar: com.zz91.utils.pageNav(_store)
		}
		com.zz91.ep.mblog.grid.superclass.constructor.call(this,c);
	},
	unCompCode:function(code,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			alert(rows[i].get("id"));
			Ext.Ajax.request({
				url:Context.ROOT+"/news/mbloginfo/updateCompCodeAndInfoCode.htm",
				params:{"infoId":rows[i].get("id"),"codeBlock":code,"id":rows[i].get("cid")},
				success:function(response,opt){ 
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","冻结帐号成功");
						  store.reload();
					}else{
						com.zz91.utils.Msg("","冻结帐号失败");
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





