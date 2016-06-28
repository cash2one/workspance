Ext.namespace('com.zz91.sms.smslog');

var SMSLOG=new function(){
	this.SMSLOG_GRID="smsloggrid";
}
com.zz91.sms.smslog.Field=[
	{name:"id",mapping:"id"},
	{name:"templateCode",mappling:"templateCode"},
	{name:"content",mapping:"content"},
	{name:"receiver",mapping:"receiver"},
	{name:"sendStatus",mapping:"sendStatus"},
	{name:"gmtSend",mapping:"gmtSend"},
	{name:"gatewayCode",mapping:"gatewayCode"},
	{name:"priority",mapping:"priority"}
];

com.zz91.sms.smslog.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.sms.smslog.Field,
				url:Context.ROOT +  "/smslog/querySms.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( 
				[ _sm,{
			header : "短信内容",
			sortable : false,
			dataIndex : "content"
		},{
			header :"接收者电话",
			sortable : false,
			dataIndex:"receiver"
		},{
			header:"发送状态",
			sortable:false,
			dataIndex:"sendStatus",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var send = ["待发送","发送中","发送成功","发送失败"];
				if(value>3){
					return value;
				}
				return send[value];
			}
		},{
			header : "发送时间",
			sortable : false,
			dataIndex : "gmtSend",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "网关",
			sortable : false,
			dataIndex : "gatewayCode"
		},{
			header:"优先级",
			sortable:false,
			dataIndex:"priority"
		}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[
				{
					text : '重发',
					iconCls : 'stats16',
					handler : function(btn){		
						var row = Ext.getCmp(SMSLOG.SMSLOG_GRID).getSelectionModel().getSelections();
						if (row.length > 0) {
							Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要发送选中的信息?', function(_btn){
								if (_btn != "yes")
									return;
								for (var i = 0, len = row.length; i < len; i++) {
									Ext.Ajax.request({
										url:Context.ROOT +  "/smslog/resendSms.htm",
										params:{"id":row[i].get("id")},
										success:function(response,opt){
											var obj = Ext.decode(response.responseText);
											if(obj.success){
												com.zz91.utils.Msg("","发送成功");
												Ext.getCmp(SMSLOG.SMSLOG_GRID).getStore().reload();
											}else{
												Ext.MessageBox.show({
													title:MESSAGE.title,
													msg : MESSAGE.saveFailure,
													buttons:Ext.MessageBox.OK,
													icon:Ext.MessageBox.ERROR
												});
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
							});
						}
					}
				},{
					text : '删除',
					iconCls : 'delete16',
					handler : function(btn){		
					var row = Ext.getCmp(SMSLOG.SMSLOG_GRID).getSelectionModel().getSelections();		
						if (row.length > 0) {
							Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + row.length + '条记录?', function(_btn){
								if (_btn != "yes")
									return;
								for (var i = 0, len = row.length; i < len; i++) {
									Ext.Ajax.request({
										url:Context.ROOT +  "/smslog/deleteSms.htm",
										params:{"id":row[i].get("id")},
										success:function(response,opt){
											var obj = Ext.decode(response.responseText);
											if(obj.success){
												com.zz91.utils.Msg("","删除记录成功");
												Ext.getCmp(SMSLOG.SMSLOG_GRID).getStore().reload();
											}else{
												Ext.MessageBox.show({
													title:MESSAGE.title,
													msg : MESSAGE.saveFailure,
													buttons:Ext.MessageBox.OK,
													icon:Ext.MessageBox.ERROR
												});
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
							});
						}
					}
				},"->",{
					xtype:"combo",
					itemCls:"required",
					width:100,
					name:"categoryCombo",
					mode:"local",
					emptyText:"选择发送状态",
					triggerAction:"all",
					forceSelection: true,
					displayField:'name',
					valueField:'value',
					autoSelect:true,
					store:new Ext.data.JsonStore({
						fields : ['name', 'value'],
						data   : [
						    {name:'所有',value:null},
							{name:'待发送',value:'0'},
							{name:'发送中',value:'1'},
							{name:'发送成功',value:'2'},
							{name:'发送失败',value:'3'}
						]
					}),
					listeners:{
					"change":function(field,newValue,oldValue){
						var grid=Ext.getCmp(SMSLOG.SMSLOG_GRID);
						grid.getStore().baseParams["sendStatus"]=newValue;
						grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
					}
				}

				},"->",{
					xtype : "datefield",
					format:"Y-m-d",
					name:"to",
					emptyText:"发送时间(末)",
					listeners:{
						"blur":function(field){
							if(field.getValue()!=""){
								_store.baseParams["to"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:i:s');
							}else{
								_store.baseParams["to"]=null;
							}
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype : "datefield",
					format:"Y-m-d",
					name : "from",
					emptyText:"发送时间(始)",
					listeners:{
						"blur":function(field){
							if(field.getValue()!=""){
								_store.baseParams["from"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:i:s');
							}else{
								_store.baseParams["from"]=null;
							}
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"textfield",
					id:"_receiver",
					width:100,
					emptyText:"请输入接收号码",
					listeners:{
						//失去焦点
						"blur":function(c){
							var val = Ext.get("_receiver").dom.value;
							if(val!=""){
								_store.baseParams["receiver"]= val;
							}else{
								_store.baseParams["receiver"]=null;
							}
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"textfield",
					id:"_gatewayCode",
					width:100,
					emptyText:"请输入网关名称",
					listeners:{
						//失去焦点
						"blur":function(c){
							var val = Ext.get("_gatewayCode").dom.value;
							if(val!=""){
								_store.baseParams["gatewayCode"]= val;
							}else{
								_store.baseParams["gatewayCode"]=null;
							}
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"textfield",
					id:"_content",
					width:100,
					emptyText:"请输入短信内容",
					listeners:{
						//失去焦点
						"blur":function(c){
							var val = Ext.get("_content").dom.value;
							if(val!=""){
								_store.baseParams["content"]= val;
							}else{
								_store.baseParams["content"]=null;
							}
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"textfield",
					id:"_templateCode",
					width:100,
					emptyText:"请输入模板code",
					listeners:{
						//失去焦点
						"blur":function(c){
							var val = Ext.get("_templateCode").dom.value;
							if(val!=""){
								_store.baseParams["templateCode"]= val;
							}else{
								_store.baseParams["templateCode"]=null;
							}
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				}],
			bbar: com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.sms.smslog.Grid.superclass.constructor.call(this,c);
	}
});




