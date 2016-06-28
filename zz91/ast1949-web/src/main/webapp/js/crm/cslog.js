Ext.namespace("ast.ast1949.crm.cslog");

ast.ast1949.crm.cslog.CALLTYPE=["服务回访","销售电话","客户来电","调整日期"];
ast.ast1949.crm.cslog.SITUATION=["无效联系","有效联系","客户不存在"];
ast.ast1949.crm.cslog.OPERATION=["没有操作过","不积极","积极","一般"];
ast.ast1949.crm.cslog.TRANSACTION=["有反馈，有成交","有反馈，无成交","无反馈，无成交","不了解"];
ast.ast1949.crm.cslog.ISSUE=["未解决","正在解决","已解决"];

ast.ast1949.crm.cslog.LogForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var form=this;
		
		var c={
			frame : true,
			labelAlign : "right",
			labelWidth : 80,
			layout : "column",
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					name:"companyId",
					id:"companyId"
				},{
					xtype:"datefield",
					name:"gmtNextVisitPhoneDate",
					format:"Y-m-d",
					fieldLabel:"下次电话时间",
					value:new Date
				},{
					fieldLabel:"登录次数",
					name:"loginCount",
					id:"numLogin"
				},{
					fieldLabel:"最近登录时间",
					name:"gmtLastLoginTime",
					id:"gmtLastLogin"
				},{
					xtype:"radiogroup",
					fieldLabel:"通话类型",
					columns:4,
					items:[{boxLabel:"服务回访",name:"callType",inputValue:0,anchor:"100",checked:true},
					       {boxLabel:"销售电话",name:"callType",inputValue:1,anchor:"100"},
					       {boxLabel:"客户来电",name:"callType",inputValue:2,anchor:"100"},
					       {boxLabel:"调整日期",name:"callType",inputValue:3,anchor:"100"}
					]
				},{
					xtype:"radiogroup",
					fieldLabel:"联系情况",
					column:3,
					items:[{boxLabel:"无效联系",name:"situation",inputValue:0,anchor:"100"},
					       {boxLabel:"有效联系",name:"situation",inputValue:1,anchor:"100",checked:true},
					       {boxLabel:"客户不存在",name:"situation",inputValue:2,anchor:"100"}]
				},{
					xtype:"radiogroup",
					fieldLabel:"操作情况",
					columns:4,
					items:[{boxLabel:"没有操作过",name:"operation",inputValue:0,anchor:"100"},
					       {boxLabel:"不积极",name:"operation",inputValue:1,anchor:"100"},
					       {boxLabel:"积极",name:"operation",inputValue:2,anchor:"100",checked:true},
					       {boxLabel:"一般",name:"operation",inputValue:3,anchor:"100"}]
				},{
					xtype:"textarea",
					name:"operationDetails",
					fieldLabel:"操作详情"
				},{
					xtype:"radiogroup",
					id:"issueStatus",
					fieldLabel:"解决情况",
					columns:3,
					items:[{boxLabel:"未解决",name:"issueStatus",anchor:"100",inputValue:0, checked:true},
					       {boxLabel:"正在解决",name:"issueStatus",anchor:"100",inputValue:1},
					       {boxLabel:"已解决",name:"issueStatus",anchor:"100",inputValue:2}
					       ]
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					fieldLabel : "下次邮件时间",
					format:"Y-m-d",
					name : "gmtNextVisitEmailDate",
					value:new Date
				},{
					fieldLabel:"pv",
					name:"pv",
					id:"pv"
				},{
					xtype:"checkboxgroup",
					fieldLabel:"回访类型",
					columns:3,
					items:[
					       {boxLabel:"电话",name:"isVisitPhone",anchor:"100",inputValue:1},
					       {boxLabel:"邮件",name:"isVisitEmail",anchor:"100",inputValue:1},
					       {boxLabel:"短信",name:"isVisitSms",anchor:"100",inputValue:1}
					       ]
				},{
					xtype:"radiogroup",
					fieldLabel:"交易情况",
					columns:2,
					items:[{boxLabel:"有反馈，有成交",name:"transaction",anchor:"100",inputValue:0,checked:true},
					       {boxLabel:"有反馈，无成交",name:"transaction",anchor:"100",inputValue:1},
					       {boxLabel:"无反馈，无成交",name:"transaction",anchor:"100",inputValue:2},
					       {boxLabel:"不了解",name:"transaction",anchor:"100",inputValue:3}
					]
				},{
					xtype:"textarea",
					name:"transactionDetails",
					fieldLabel:"交易详情"
				},{
					xtype:"radiogroup",
					id:"newStar",
					fieldLabel:"星级",
					columns:5,
					items:[{boxLabel:"1星",name:"newStar",anchor:"100",inputValue:1},
					       {boxLabel:"2星",name:"newStar",anchor:"100",inputValue:2},
					       {boxLabel:"3星",name:"newStar",anchor:"100",inputValue:3},
					       {boxLabel:"4星",name:"newStar",anchor:"100",inputValue:4},
					       {boxLabel:"5星",name:"newStar",anchor:"100",inputValue:5}]
				},{
					xtype:"hidden",
					name:"star",
					id:"oldStar"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textarea",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"解决情况",
					name:"issueDetails"
				},{
					name:"feedback",
					fieldLabel:"客户反馈及需求"
				},{
					name:"suggestion",
					fieldLabel:"给建议"
				},{
					name:"visitTarget",
					fieldLabel:"下次回访点"
				},{
					name:"remark",
					fieldLabel:"其他说明"
				}]
			}],
			buttonAlign:"right",
			buttons:[{
				text:"保存",
				iconCls:"item-true",
				handler:function(btn){
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/cs/saveLog.htm",
							method:"post",
							type:"json",
							success:function(){
								form.findById("oldStar").setValue(form.findById("newStar").getValue());
								//刷新日志表格
								if(config.loggrid!=null && config.loggrid!=""){
									Ext.getCmp(config.loggrid).getStore().reload();
								}
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存成功！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							},
							failure:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		}
		
		ast.ast1949.crm.cslog.LogForm.superclass.constructor.call(this,c);
	},
	initLogForm:function(companyId, star, numLogin, gmtLastLogin, pv){
		this.findById("companyId").setValue(companyId);
		this.findById("oldStar").setValue(star);
		this.findById("newStar").setValue(star);
		this.findById("numLogin").setValue(numLogin);
		this.findById("gmtLastLogin").setValue(gmtLastLogin);
		this.findById("pv").setValue(pv);
	}
});

ast.ast1949.crm.cslog.LogGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:[
				{name:"id",mapping:"log.id"},
				{name:"csAccount",mapping:"log.csAccount"},
				{name:"companyId",mapping:"log.companyId"},
				{name:"gmtNextVisitPhone",mapping:"log.gmtNextVisitPhone"},
				{name:"gmtNextVisitEmail",mapping:"log.gmtNextVisitEmail"},
				{name:"isVisitPhone",mapping:"log.isVisitPhone"},
				{name:"isVisitEmail",mapping:"log.isVisitEmail"},
				{name:"isVisitSms",mapping:"log.isVisitSms"},
				{name:"callType",mapping:"log.callType"},
				{name:"situation",mapping:"log.situation"},
				{name:"operation",mapping:"log.operation"},
				{name:"operationDetails",mapping:"log.operationDetails"},
				{name:"transaction",mapping:"log.transaction"},
				{name:"transactionDetails",mapping:"log.transactionDetails"},
				{name:"feedback",mapping:"log.feedback"},
				{name:"suggestion",mapping:"log.suggestion"},
				{name:"issueStatus",mapping:"log.issueStatus"},
				{name:"issueDetails",mapping:"log.issueDetails"},
				{name:"visitTarget",mapping:"log.visitTarget"},
				{name:"gmtCreated",mapping:"log.gmtCreated"},
				{name:"star",mapping:"log.star"},
				{name:"csName",mapping:"csName"},
				{name:"addedList",mapping:"addedList"},
				{name:"companyName",mapping:"company.name"},
				{name:"pv",mapping:"log.pv"},
				{name:"numLogin",mapping:"log.loginCount"},
				{name:"gmtLastLogin",mapping:"log.gmtLastLoginTime"}
			],
			url:Context.ROOT +Context.PATH+  "/crm/cs/queryCsLogOfCompany.htm",
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var grid = this;
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			width : 50,
			sortable : true,
			dataIndex : "id",
			hidden:true
		},{//显示时间和联系的公司名称
			header:"联系时间",
			dataIndex:"gmtCreated",
			width:125,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var timeStr = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				var companyName = record.get("companyName");
				return timeStr+"<br />"+"<a href='"+Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+record.get("companyId")+"&star=0&companyName="+companyName+"' target='_blank'>"+companyName+"</a>";
			}
		},{//销售电话，电话回访/短信回访
			header:"通话类型",
			dataIndex:"callType",
			width:65,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var ct=ast.ast1949.crm.cslog.CALLTYPE[value];
				var s="";
				if(record.get("isVisitPhone")=="1"){
					s=s+"电话回访 ";
				}
				if(record.get("isVisitEmail")=="1"){
					s=s+"邮件回访 ";
				}
				if(record.get("isVisitSms")=="1"){
					s=s+"短信回访";
				}
				if(s!=null){
					return ct+"<br />"+s;
				}
				return ct;
			}
		},{//有效联系，无效联系
			header:"联系情况",
			dataIndex:"situation",
			width:65,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return ast.ast1949.crm.cslog.SITUATION[value];
			}
		},{//客服姓名
			header:"联系人",
			dataIndex:"csName",
			width:50,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return record.get("csName");
			}
		},{//客户等级，联系客户时的客户等级
			header:"等级",
			dataIndex:"star",
			width:50,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return value+"星";
			}
		},{//pv
			header:"pv",
			dataIndex:"pv",
			width:50
		},{//登录次数
			header:"登录次数",
			dataIndex:"numLogin",
			width:65
		},{//最近登录时间
			header:"最近登录时间",
			dataIndex:"gmtLastLogin",
			width:125,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
			    if(value!=null){
					var timeStr = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}else{
					var timeStr='';
				}
				return timeStr;
			}
			
		},{//联系内容
			header:"联系内容",
			dataIndex:"issueStatus",
			width:500,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var table="<table border='0' cellpadding='3' cellspacing='1' bgcolor='#7386a5' style='width:99%;white-space:normal;'>";
				if(typeof(record.get("feedback"))!="undefined" && record.get("feedback")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>问题反馈</td>";
					table=table+"<td bgcolor='#ffffff'>"+record.get("feedback")+"</td></tr>";
				}
				if(typeof(record.get("operationDetails"))!="undefined" && record.get("operationDetails")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>操作情况</td>";
					table=table+"<td bgcolor='#ffffff'><b >("+ast.ast1949.crm.cslog.OPERATION[record.get("operation")]+")</b>"+record.get("operationDetails")+"</td></tr>";
				}
				if(typeof(record.get("transactionDetails"))!="undefined" && record.get("transactionDetails")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>交易情况</td>";
					table=table+"<td bgcolor='#ffffff'><b >("+ast.ast1949.crm.cslog.TRANSACTION[record.get("transaction")]+")</b>"+record.get("transactionDetails")+"</td></tr>";
				}
				if(typeof(record.get("visitTarget"))!="undefined" && record.get("visitTarget")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>下次回访点</td>";
					table=table+"<td bgcolor='#ffffff'>"+record.get("visitTarget")+"</td></tr>";
				}
				if(typeof(record.get("suggestion"))!="undefined" && record.get("suggestion")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>给建议</td>";
					table=table+"<td bgcolor='#ffffff'>"+record.get("suggestion")+"</td></tr>";
				}
				if(typeof(record.get("remark"))!="undefined" && record.get("remark")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>其他</td>";
					table=table+"<td bgcolor='#ffffff'>"+record.get("remark")+"</td></tr>";
				}
				if(typeof(record.get("issueDetails"))!="undefined" && record.get("issueDetails")!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>解决情况</td>";
					table=table+"<td bgcolor='#ffffff'><b >("+ast.ast1949.crm.cslog.ISSUE[record.get("issueStatus")]+")</b>"+record.get("issueDetails")+"</td></tr>";
				}
				//alert(record.get("addedList"))
				if(record.get("addedList").length>0){
					var ct="";
					for(var i=0;i<record.get("addedList").length;i++){
						if(ct!=""){
							ct=ct+"<br />";
						}
						ct=ct+record.get("addedList")[i].csAccount+":"+record.get("addedList")[i].content;
					}
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>补充说明</td>";
					table=table+"<td bgcolor='#ffffff'>"+ct+"</td></tr>";
				}
				
				table=table+"</table>";
				return table;
			}
		}]);
		
		var _tbar=config.tbar||["补充说明 ",{
				xtype:"textfield",
				width:450,
				id:"addedcontent"
			},{
				iconCls:"add",
				handler:function(btn){
					if(Ext.getCmp("addedcontent").getValue()==""){
						return false;
					}
					Ext.Ajax.request({
						url:Context.ROOT+Context.PATH+"/crm/cs/addedLog.htm",
						params:{
							"content":Ext.getCmp("addedcontent").getValue(),
							"companyId":config.companyId
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","补充说明已添加");
								grid.getStore().reload();
							}else{
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,补充没有添加",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title : Context.MSG_TITLE,
								msg : "发生错误,补充没有添加",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
						}
					});
				}
			},"->",{
				text:"查看交易小计(打勾代表有，其他代表无)",
				iconCls:"item-info",
				id:"haveTrustLog",
				handler:function(btn){
					window.open(Context.ROOT+Context.PATH+"/trust/myLog.htm?companyId="+config.companyId);
				}
			},"-",{ 
				text:"查看本地CRM",
				handler:function(btn){
					window.open("http://192.168.2.2/admin1/crmlocal/crm_tel_comp.asp?com_id="+config.companyId);
				}
			}]
			
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			tbar:_tbar
		};
		
		ast.ast1949.crm.cslog.LogGrid.superclass.constructor.call(this,c);
	},
	companyName:"",
	loadLog:function(companyId){
		this.getStore().baseParams["companyId"]=companyId;
		this.getStore().load({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}})
	}
});

