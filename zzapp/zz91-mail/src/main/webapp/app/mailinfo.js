Ext.namespace("com.zz91.zzmail.mailinfo");

var MAILINFO = new function(){
	this.MAILINFO_GRID = "mailinfogrid";
	this.MAILINFO_WIN = "mailinfowindows";
	this.MAILINFO_FORM="mailinfoform";
}

com.zz91.zzmail.mailinfo.Field=[
	{name:"id",mapping:"id"},
	{name:"template",mapping:"templateId"},
	{name:"title",mapping:"emailTitle"},
	{name:"gmt_post",mapping:"gmtPost"},
	{name:"priority",mapping:"priority"},
	{name:"receiver",mapping:"receiver"},
	{name:"sender",mapping:"sender"},
	{name:"sendStatus",mapping:"sendStatus"},
	{name:"content",mapping:"content"}
];

com.zz91.zzmail.mailinfo.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totals',
			remoteSort:true,
			fields:com.zz91.zzmail.mailinfo.Field,
			url:Context.ROOT +  "/mailinfo/query.htm",
			autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				header :"邮件模板",
				sortable : false,
				dataIndex:"template"
			},{
				header :"邮件标题",
				sortable : false,
				width : 300,
				dataIndex:"title"
			},{
				header : "计划发送时间",
				sortable : true,
				dataIndex :"gmt_post",
				width:150,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}else{
						return "";
					}
				}
			},{
				header:"发送状态",
				sortable:false,
				dataIndex:"sendStatus",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value==0){
						return "等待发送";
					}
					if(value==1){
						return "发送成功";
					}
					if(value==2){
						return "发送失败";
					}
					if(value==3){
						return "发送中...";
					}
				}
				
			},{
				header :"优先级",
				sortable : false,
				dataIndex:"priority"
				
			},{
				header :"收件人",
				sortable : false,
				width:200,
				dataIndex:"receiver"
			},{
				header :"发送地址",
				sortable : false,
				width:200,
				dataIndex:"sender"
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: MESSAGE.paging.displayMsg,
				emptyMsg : MESSAGE.paging.emptyMsg,
				beforePageText : MESSAGE.paging.beforePageText,
				afterPageText : MESSAGE.paging.afterPageText,
				paramNames : MESSAGE.paging.paramNames
			})
		};
		
		com.zz91.zzmail.mailinfo.Grid.superclass.constructor.call(this,c);
		
	},
	mytoolbar:[{
		text : '删除',
		tooltip : '删除记录',
		iconCls : 'delete16',
		handler : function(btn){
			
			var row = Ext.getCmp(MAILINFO.MAILINFO_GRID).getSelectionModel().getSelections();
			
			if (row.length > 0) {
				Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + row.length + '条记录?', function(_btn){
					if (_btn != "yes")
						return;
					for (var i = 0, len = row.length; i < len; i++) {
						Ext.Ajax.request({
							url:Context.ROOT +  "/mailinfo/delete.htm",
							params:{"id":row[i].get("id")},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									com.zz91.utils.Msg("","删除记录成功");
									Ext.getCmp(MAILINFO.MAILINFO_GRID).getStore().reload();
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
		text : '重发',
		tooltip : '重发',
		iconCls : 'add16',
		handler : function(btn){
			var row = Ext.getCmp(MAILINFO.MAILINFO_GRID).getSelectionModel().getSelected();
				if(row!=null){
					com.zz91.zzmail.mailinfo.reMail(row.get("id"));
			}
		}
	}, {
		text : '正文',
		tooltip : '正文',
		iconCls : 'edit16',
		handler : function(btn){
			var row = Ext.getCmp(MAILINFO.MAILINFO_GRID).getSelectionModel().getSelected();
			if(row!=null){
				var win = new Ext.Window({
					id:MAILINFO.MAILINFO_WIN,
					title:"查看邮件内容",
					width:700,
					autoHeight:true,
					modal:true,
					items:[{
						region:"center",
						height:500,
						autoLoad:Context.ROOT+"/mailinfo/details.htm?id="+row.get("id")
					},{
						buttons:[
							{
							text:"关闭",
							handler:function(btn){
							Ext.getCmp(MAILINFO.MAILINFO_WIN).close();
							}
						}]
					}]
				});
				win.show();
			}
		}
	},{
		text : '发送邮件',
		iconCls : 'mail16',
		handler:function(btn){
			com.zz91.zzmail.mailinfo.sendMail();
	}
	},"->",
	{
		xtype : "datefield",
		format:"Y-m-d",
		name : "from",
		emptyText:"计划开始",
		listeners:{
			"blur":function(field){
				var _store=Ext.getCmp(MAILINFO.MAILINFO_GRID).getStore();
				if(field.getValue()!=""){
					_store.baseParams["from"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:i:s');
				}else{
					_store.baseParams["from"]=null;
				}
				_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
			}
		}
	},{
		xtype : "datefield",
		format:"Y-m-d",
		name:"to",
		emptyText:"计划结束",
		listeners:{
			"blur":function(field){
				var _store=Ext.getCmp(MAILINFO.MAILINFO_GRID).getStore();
				if(field.getValue()!=""){
					_store.baseParams["to"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:i:s');
				}else{
					_store.baseParams["to"]=null;
				}
				_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
			}
		}
	}]
});
// 重发表单
com.zz91.zzmail.mailinfo.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
				region:"center",
				layout:"form",
				bodyStyle:'padding:5px 0 0',
				frame:true,
				labelAlign : "right",
				labelWidth : 80,
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
			},
			items:[
			{
				xtype : "hidden",
				name : "id",
				dataIndex : "id"
			},{
				fieldLabel : "邮件标题",
				itemCls :"required",
				name : "title",
				allowBlank : false
			},{
				fieldLabel : "接收人",
				itemCls :"required",
				name:"receiver",
				allowBlank : false
			},{
				xtype:'htmleditor',
				anchor:"99%",
				height:380,
				allowBlank : false,
				fieldLabel:"邮件内容",
				name:"content",
				itemCls:"required"
			}],
			buttons:[{
				text:"发送",
				scope:this,
				handler:function(btn){
					var url=this.resendUrl;
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","邮件正在发送");
								Ext.getCmp(MAILINFO.MAILINFO_GRID).getStore().reload();
								Ext.getCmp(MAILINFO.MAILINFO_WIN).close();
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.submitFailure,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp(MAILINFO.MAILINFO_WIN).close();
				}
			}]
		};
		
		com.zz91.zzmail.mailinfo.Form.superclass.constructor.call(this,c);
	},
	loadOneRecord:function(id){
		var reader=[
			{name:"id",mapping:"id"},			
			{name:"title",mapping:"emailTitle"},			
			{name:"receiver",mapping:"receiver"},	
			{name:"content",mapping:"content"}
			];
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			url : Context.ROOT+ "/mailinfo/queryOne.htm",
			root : "records",
			fields : reader,
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
	},
});

com.zz91.zzmail.mailinfo.reMail=function(id){
	var form = new com.zz91.zzmail.mailinfo.Form({
		id:MAILINFO.MAILINFO_FORM,
		resendUrl:Context.ROOT +  "/mailinfo/resend.htm",
		region:"center"
	});
	
	form.loadOneRecord(id);
	
	var win = new Ext.Window({
			id:MAILINFO.MAILINFO_WIN,
			title:"邮件信息",
			width:700,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}
//发送表单
com.zz91.zzmail.mailinfo.Form1 = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
				region:"center",
				layout:"form",
				bodyStyle:'padding:5px 0 0',
				frame:true,
				labelAlign : "right",
				labelWidth : 80,
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
			},
			items:[
			{
				xtype : "hidden",
				name : "id",
				dataIndex : "id"
			},{
				fieldLabel : "邮件标题",
				itemCls :"required",
				name : "title",
				allowBlank : false
			},{
				fieldLabel : "发送地址",
				itemCls :"required",
				name : "sender",
				allowBlank : false
			},{
				fieldLabel : "接收人",
				itemCls :"required",
				name:"receiver",
				allowBlank : false
			},{
				xtype:'htmleditor',
				anchor:"99%",
				height:380,
				allowBlank : false,
				fieldLabel:"邮件内容",
				name:"content",
				itemCls:"required"
			}],
			buttons:[{
				text:"确定",
				scope:this,
				handler:function(btn){
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : Context.ROOT +  "/mailinfo/sendEmail.htm",
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","邮件正在发送");
								Ext.getCmp(MAILINFO.MAILINFO_GRID).getStore().reload();
								Ext.getCmp(MAILINFO.MAILINFO_WIN).close();
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.submitFailure,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp(MAILINFO.MAILINFO_WIN).close();
				}
			}]
		};
		
		com.zz91.zzmail.mailinfo.Form1.superclass.constructor.call(this,c);
	},
});
	
com.zz91.zzmail.mailinfo.sendMail=function(){
	var form = new com.zz91.zzmail.mailinfo.Form1({
		id:MAILINFO.MAILINFO_FORM,
		sendUrl:Context.ROOT +  "/mailinfo/send.htm",
		region:"center"
	});
	var win = new Ext.Window({
			id:MAILINFO.MAILINFO_WIN,
			title:"邮件群发",
			width:700,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}
