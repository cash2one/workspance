Ext.namespace("com.zz91.ep.message");

var MESS=new function(){
	this.MESSAGE_GRID="messagegrid";
	this.MESSAGE_FORM="messageform";
}

com.zz91.ep.message.gridField=[
	{name:"id",mapping:"message.id"},
	{name:"uid",mapping:"message.uid"},
	{name:"target_cid",mapping:"message.targetCid"},
	{name:"target_id",mapping:"message.targetId"},
	{name:"target_type",mapping:"message.targetType"},
	{name:"title",mapping:"message.title"},
	{name:"details",mapping:"message.details"},
	{name:"read_status",mapping:"message.readStatus"},
	{name:"reply_status",mapping:"message.replyStatus"},
	{name:"reply_details",mapping:"message.replyDetails"},
	{name:"gmt_reply",mapping:"message.gmtReply"},
	{name:"gmt_created",mapping:"message.gmtCreated"},
	{name:"gmt_modified",mapping:"message.gmtModified"},
	{name:"del_receive_status",mapping:"message.delReceiveStatus"},
	{name:"del_send_status",mapping:"message.delSendStatus"},
	{name:"titleName",mapping:"tradeTitle"},
	{name:"receive",mapping:"receive"},
	{name:"send",mapping:"send"}
];

com.zz91.ep.message.Grid=Ext.extend(Ext.grid.GridPanel,{
	targetType:"",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _targetType=this.targetType;
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:com.zz91.ep.message.gridField,
			url:Context.ROOT+"/message/queryMessage.htm?targetType="+_targetType,
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				hidden:true,
				header:"供应Id", 
				dataIndex:"target_id",
				sortable:true
			},{
				header:"供求标题或公司名称", 
				width:150, 
				dataIndex:"target_type",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==0){
						return "<a href='"+Context.ROOT+'/trade/tradesupply/supplyDetails.htm?id='+record.get("target_id")+"'>"+record.get("titleName")+"</a>";
					}
					
					if(value==1){
						return "<a href='"+Context.ROOT+'/trade/tradebuy/edit.htm?id='+record.get("target_id")+"'>"+record.get("titleName")+"</a>";
					}
					
					if(value==2){
						return "<a href='"+Context.ROOT+'/comp/comp/profileDetails.htm?id='+record.get("target_cid")+"'>"+record.get("titleName")+"</a>";
					}
				}
			},{
				header:"留言标题", 
				width:200, 
				dataIndex:"title",
				sortable:true
			},{
				header:"发送标识", 
				dataIndex:"uid",
				width:60, 
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value=="0"){
						return "后台代发";
					}else{
						return "客户自发";
					}
				}
			},{
				header:"发送方", 
				dataIndex:"send",
				width:120,
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==""){
						return "<front style='color:green'>公司名称未填写</front>";
					}
					return value;
				}
			},{
				header:"接收方", 
				dataIndex:"receive",
				width:120,
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==""){
						return "<front style='color:green'>公司名称未填写</front>";
					}
					return value;
				}
			},{
				header:"留言时间", 
				width:130, 
				dataIndex:"gmt_created",
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
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
						id:"tbar",
						hidden:true,
						xtype:"combo",
						mode:"local",
						editable:false,
						triggerAction:"all",
						emptyText:"留言类型搜索",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","供应信息留言"],["1","求购信息留言"],["2","公司留言"]]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["targetType"]=newValue;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					}]
				}),
				bbar:_bbar
			};
		
		com.zz91.ep.message.Grid.superclass.constructor.call(this,c);
	}
});

com.zz91.ep.message.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			region:"center",
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "留言标题",
					name : "title",
					id:"title",
					readOnly:true
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype : "hidden",
					name : "id",
					dataIndex : "id"
				}, {
					xtype:"textarea",
					height:300,
					fieldLabel : "留言内容",
					itemCls :"required",
					name : "details",
					id : "details",
					readOnly:true
				},{
					xtype:"textarea",
					height:300,
					fieldLabel : "回复内容",
					itemCls :"required",
					name : "reply_details",
					id : "reply_details",
					readOnly:true
				}]
			}
			]
		};
		
		com.zz91.ep.message.Form.superclass.constructor.call(this,c);
	},
	loadOneRecord:function(id){
		var reader=[
        	{name:"id",mapping:"id"},
        	{name:"title",mapping:"title"},
        	{name:"details",mapping:"details"},
        	{name:"reply_details",mapping:"replyDetails"}
		];
		
		var form = this;
		
		var _store = new Ext.data.JsonStore({
			fields : reader,
			url : Context.ROOT+ "/message/queryOneMessage.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record != null) {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});