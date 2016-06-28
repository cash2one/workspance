Ext.ns("ast.ast1949.analysis.analysis");

Ext.define("RegisterModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"regfromCode",mapping:"regfromCode"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"}
	]
});

Ext.define("InquiryModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"inquiryType",mapping:"inquiryType"},
		{name:"inquiryTarget",mapping:"inquiryTarget"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"}
	]
});

Ext.define("ProductModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"typeCode",mapping:"typeCode"},
		{name:"categoryCode",mapping:"categoryCode"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"}
	]
});

Ext.define("CompPriceModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"categoryCode",mapping:"categoryCode"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"}
	]
});

Ext.define("KeywordsModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"kw",mapping:"kw"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"}
	]
});

Ext.define("XiaZaiKeywordsModel",{
    extend:"Ext.data.Model",
    fields:[
        {name:"id",mapping:"id"},
        {name:"kw",mapping:"kw"},
        {name:"num",mapping:"num"},
        {name:"gmtTarget",mapping:"gmtTarget"},
        {name:"targetDate",mapping:"targetDate"}
    ]
});

// QQ登录详细
Ext.define("QQModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"operator",mapping:"operator"},
		{name:"operation",mapping:"operation"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"}
	]
});

Ext.define("SpotModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"analysisid",mapping:"id"},
		{name:"operator",mapping:"operator"},
		{name:"operation",mapping:"operation"},
		{name:"num",mapping:"num"},
		{name:"gmtTarget",mapping:"gmtTarget"},
		{name:"targetDate",mapping:"targetDate"},
		{name:"title",mapping:"title"}
	]
});

Ext.define("ast.ast1949.analysis.analysis.MainPageGrid", {
	extend:"Ext.grid.Panel",
	storeModel:"",
	queryUrl:"",
	cm:null,
//	gfield:"targetDate",
	config:{
		storeModel:"",
		queryUrl:"",
		cm:""
//		gfield:""
	},
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:this.getStoreModel(),
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:this.getQueryUrl(),
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=this.getCm();
		
		var c={
			features: [{
//	            id: 'group',
	            ftype: 'groupingsummary',
	            groupHeaderTpl: '{name}',
	            hideGroupedHeader: false,
	            remoteRoot: 'summaryData'
	        }],
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	}
});

Ext.define("ast.ast1949.analysis.analysis.MainGrid", {
	extend:"Ext.grid.Panel",
	storeModel:"",
	queryUrl:"",
	cm:null,
	gfield:"targetDate",
	config:{
		storeModel:"",
		queryUrl:"",
		cm:"",
		gfield:""
	},
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:this.getStoreModel(),
			remoteSort:false,
			proxy:{
				type:"ajax",
				url:this.getQueryUrl(),
				simpleSortMode:true,
				reader: {
		            type: 'json'
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:true,
			groupField:this.getGfield()
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var c={
			features: [{
//	            id: 'group',
	            ftype: 'groupingsummary',
	            groupHeaderTpl: '{name}',
	            hideGroupedHeader: false,
	            enableGroupingMenu: false
	        }],
			store:_store,
			columns:this.getCm(),
			selModel:_sm
//			sortableColumns:false
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	}
});

Ext.define("ast.ast1949.analysis.analysis.ChartView", {
	extend:"Ext.panel.Panel",
	storeModel:"",
	queryUrl:"",
	chartStore:null,
	config:{
		queryUrl:"",
		storeModel:"",
		chartStore:null
	},
	initComponent:function(){
		
		this.chartStore=Ext.create("Ext.data.Store",{
			model:this.getStoreModel(),
			remoteSort:true,
			proxy:{
				type:"ajax",
				url:this.getQueryUrl(),
				simpleSortMode:true,
				reader: {
		            type: 'json'
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			listeners:{
				"datachanged":function(st,e){
					if(st.getCount()>0){
						var sumNum=0;
						for(var i=0;i< st.getCount(); i++){
							sumNum=sumNum + st.getAt(i).get("num");
						}
						Ext.getCmp("summeryField").setValue("总数:"+sumNum)
					}
				}
			},
			autoLoad:false
		});
		
		var _this=this;
		
		var now=new Date();
		var end=new Date(now.getTime()-86400000);
		var start=new Date(end.getTime()-(86400000*7));
		
		this.getChartStore().setExtraParam("from",Ext.Date.format(start,"Y-m-d H:i:s"));
		this.getChartStore().setExtraParam("to",Ext.Date.format(end,"Y-m-d H:i:s"));
		
		var c={
			animate:true,
			layout:"fit",
			items:[{
				store:this.getChartStore(),
				xtype:"chart",
				axes:[{
					title:"数量",
					type:"Numeric",
					position:"left",
					fields:["num"],
					minimum:0
				},{
					title:"时间",
					type:"Category",
					position:"bottom",
					fields:["targetDate"]
				}],
				series:[{
					type:"line",
					xField:"targetDate",
					yField:"num",
					tips: {
						trackMouse: true,
						width:120,
						renderer: function(storeItem, item) {
							var title=Ext.String.format("数量：{0}<br />日期：{1}",storeItem.get('num'),storeItem.get('targetDate'))
							this.setTitle(title);
						}
					}
				}]
			}],
			tbar:[{
				width:300,
				id:"summeryField",
				xtype:"displayfield",
				value:"数量："
			},"->",{
				xtype:"datefield",
				format:"Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				emptyText:"开始时间",
				id:"start",
				value:start,
				listeners:{
					"change":function(field, nv, ov, e){
						_this.getChartStore().setExtraParam("from",field.getSubmitValue());
						_this.getChartStore().load();
					}
				}
			},{
				xtype:"datefield",
				format:"Y-m-d",
				submitFormat:"Y-m-d H:i:s",
				emptyText:"结束时间",
				id:"end",
				value:end,
				listeners:{
					"change":function(field, nv, ov, e){
						_this.getChartStore().setExtraParam("to",field.getSubmitValue());
						_this.getChartStore().load();
					}
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	}
});
