Ext.namespace("ast.ast1949.admin.crm")

Ext.onReady(function(){
	var navigateTab = new ast.ast1949.admin.crm.NavigateTab();
//	var remarkForm=new ast.ast1949.admin.crm.RemarkForm({id:"myform"});
	
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[navigateTab]
	});
	
	
	Ext.getCmp("myform").loadOneRecord();
});

ast.ast1949.admin.crm.TabContent = function(url){
	return '<iframe src="' + Context.ROOT + Context.PATH + url + '"' + '" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe> ';
}

ast.ast1949.admin.crm.NavigateTab = Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var c={
			activeTab: 1,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			defaults:{
				closable: true
			},
			items:[{
				title:'客户信息',
				html: ast.ast1949.admin.crm.TabContent("/admin/admincompany/edit.htm?companyId="+PAGE_CONST.COMPANY_ID+"&st="+Math.random())
				
			},{
				title:'电话小记',
				layout:"border",
				items:[
					new ast.ast1949.admin.crm.RemarkForm({
						id:"myform",
						region:"center"
					}),
					new Ext.TabPanel({
						region:"south",
						activeTab:0,
						split:true,
						height:300,
						items:[
							{
								title:"服务联系记录",
								layout:"fit",
								items:[
									new ast.ast1949.admin.crm.FeedbackGrid({})
								]
							},{
								title:"电话回访小记",
								layout:"fit",
								items:[
									new ast.ast1949.admin.crm.InterviewForm()
								]
							},{
								title:"客户来电小记",
								layout:"fit",
								items:[
									new ast.ast1949.admin.crm.FeedbackForm()
								]
							}
						]
					})
				]
			},{
				title:'供求信息',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'发送的询盘',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'收到的询盘',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'定制情况',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'查看邮件记录',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'关键字定制',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'再生通开通记录',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'品牌通服务登记',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			},{
				title:'报价信息',
				html:ast.ast1949.admin.crm.TabContent("/admin/index.htm")
			}]
		};

		ast.ast1949.admin.crm.NavigateTab.superclass.constructor.call(this,c);

	}
});

ast.ast1949.admin.crm.RemarkForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			autoScroll:true,
			frame:true,
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"checkbox",
					boxLabel:"是否公开邮箱"
				},{
					name:"operateName",
					id:"operateName",
					fieldLabel:"网上操作员姓名"
				},{
					name:"operateTel",
					fieldLabel:"网上操作员电话"
				},{
					xtype: 'checkboxgroup',
	            	fieldLabel: '诚信档案',
	            	columns:[80,80,80],
	            	items:[{
	            		name:"haveIdcard",
	            		inputValue:"1",
	            		boxLabel:"身份证"
	            	},{
	            		name:"haveLicence",
	            		inputValue:"1",
	            		boxLabel:"营业执照"
	            	},{
	            		name:"haveCertificate",
	            		inputValue:"1",
	            		boxLabel:"税务登记"
	            	}]
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"checkbox",
					name:"foreignCustomer",
					inputValue:"1",
					boxLabel:"国外客户"
				},{
					fieldLabel:"网上操作员手机",
					name:"operateMobile"
				},new Ext.form.RadioGroup({
					fieldLabel:"客户等级",
					columns:[60,60,60,60,60],
					items:[{
						name:"salesRank",
						inputValue:"1",
						boxLabel:"1星"
					},{
						name:"salesRank",
						inputValue:"2",
						boxLabel:"2星"
					},{
						name:"salesRank",
						inputValue:"3",
						boxLabel:"3星"
					},{
						name:"salesRank",
						inputValue:"4",
						boxLabel:"4星"
					},{
						name:"salesRank",
						inputValue:"5",
						boxLabel:"5星"
					}]
				}),{
					name:"customerIdcard",
					fieldLabel:"身体证号"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					name:"postAddress",
					fieldLabel:"邮寄地址"
				},{
					xtype:"checkbox",
					id:"isMustSee",
					name:"isMustSee",
					boxLabel:"开通买家必看 <a href='#' onclick='javascript:ast.ast1949.admin.crm.editFormWin(0)'>设置</a>"
				},{
					xtype:"textarea",
					name:"remark",
					fieldLabel:"备注"
				},{
					xtype:"panel",
					html:""//"有效电话0次,短信回访0次,邮件回访0次,未接通0次"
				}]
			}],
			buttons:[{
				text:"更新",
				scope:this,
				handler:this.saveForm
			}]
		};

		ast.ast1949.admin.crm.RemarkForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/crm/update.htm?id="+PAGE_CONST.COMPANY_ID,
	saveForm:function(btn){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:MESSAGE.title,
				msg : MESSAGE.unValidate,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "更新成功",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "更新失败",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
	},
	loadOneRecord:function(){
		
//		{name:"operateTel",mapping:"operateTel"},
//				{name:"haveIdcard",mapping:"haveIdcard"},
//				{name:"foreignCustomer",mapping:"foreignCustomer"},
//				{name:"operateMobile",mapping:"operateMobile"},
//				{name:"salesRank",mapping:"salesRank"},
//				{name:"customerIdcard",mapping:"customerIdcard"},
//				{name:"postAddress",mapping:"postAddress"},
//				{name:"remark",mapping:"remark"}
		
		var _fields = ["operateName","operateTel","haveIdcard","foreignCustomer","operateMobile",
				"salesRank","customerIdcard","postAddress","remark","isMustSee"]
				
				
                var form = this;
                var store=new Ext.data.JsonStore({
                        root : "records",
                        fields : _fields,
                        url : Context.ROOT + Context.PATH + "/admin/crm/getSingleContactPlanRecord.htm?id="+PAGE_CONST.COMPANY_ID,
                        baseParams:{"product.id":id},
                        autoLoad : true,
                        listeners : {
                                "datachanged" : function(s) {
                                        var record = s.getAt(0);
                                        if (record == null) {
                                           	Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
                                        } else {
                                            form.getForm().loadRecord(record);
                                        }
                                }
                        }
                })
                
//		var form=this;
//		
//		var reader = [
//				{name:"operateName",mapping:"operateName"},
//				{name:"operateTel",mapping:"operateTel"},
//				{name:"haveIdcard",mapping:"haveIdcard"},
//				{name:"foreignCustomer",mapping:"foreignCustomer"},
//				{name:"operateMobile",mapping:"operateMobile"},
//				{name:"salesRank",mapping:"salesRank"},
//				{name:"customerIdcard",mapping:"customerIdcard"},
//				{name:"postAddress",mapping:"postAddress"},
//				{name:"remark",mapping:"remark"}
//				];
//		var store = new Ext.data.JsonStore({
//				root : "records",
//				fields : ["operateName"],
//				url : Context.ROOT + Context.PATH
//						+ "/admin/crm/getSingleContactPlanRecord.htm?id="+PAGE_CONST.COMPANY_ID,
//				autoLoad : true,
//				listeners : {
//					"datachanged" : function() {
//						
//						var record = store.getAt(0);
//						if (record == null) {
//							Ext.MessageBox.alert(Context.MSG_TITLE,
//									"数据加载错误,请联系管理员!");
//						} else {
//							form.getForm().loadRecord(record);
//						}
//					}
//				}
//			});
	}
});

//反馈表单
ast.ast1949.admin.crm.FeedbackForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			autoScroll:true,
			frame:true,
				anchor:"95%",
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					name:"telNextTime",
					format : 'Y-m-d',
					fieldLabel:"下次联系时间"
				},new Ext.form.RadioGroup({
					fieldLabel:"解决情况",
					columns:[60,60,60],
					items:[{
						name:"solutionState",
						inputValue:"3",
						boxLabel:"未解决"
					},{
						name:"solutionState",
						inputValue:"1",
						boxLabel:"解决中"
					},{
						name:"solutionState",
						inputValue:"2",
						boxLabel:"已解决"
					}]
				}),{
					xtype:"textarea",
					name:"operationDetails",
					fieldLabel:"解决情况",
					itemCls :"required"
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					name:"mailNextTime",
					format : 'Y-m-d',
					fieldLabel:"下次邮件时间"
				},{
					xtype:"textarea",
					name:"proposal",
					fieldLabel:"建议与意见"
				},{
					xtype:"textarea",
					name:"nextTimePoin",
					fieldLabel:"下次回访点"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"98%",
					xtype:"textarea",
					labelSeparator:""
				},
				items:[{
					name:"otherExplain",
					fieldLabel:"其他说明"
				}]
			}],
			buttons:[{
				scope:this,
				handler:this.addCallNote,
				text:"保存"
			}]
		};

		ast.ast1949.admin.crm.FeedbackForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/crm/callSmallNote.htm?id="+PAGE_CONST.COMPANY_ID,
	addCallNote:function(btn){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:MESSAGE.title,
				msg : MESSAGE.unValidate,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "操作成功",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "操作失败",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
	}
});

ast.ast1949.admin.crm.InterviewForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			autoScroll:true,
			frame:true,
				anchor:"95%",
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					name:"telNextTime",
					format : 'Y-m-d',
					fieldLabel:"下次联系时间"
				},{
					xtype:"textarea",
					name:"operationDetails",
					fieldLabel:"操作详细情况"
				},{
					xtype:"textarea",
					name:"proposal",
					fieldLabel:"给建议",
					itemCls :"required"
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					name:"mailNextTime",
					format : 'Y-m-d',
					fieldLabel:"下次邮件时间"
				},{
					xtype:"textarea",
					name:"clientFeedback",
					fieldLabel:"客户反馈/需求"
				},{
					xtype:"textarea",
					name:"nextTimePoint",
					fieldLabel:"下次回访点"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"98%",
					xtype:"textarea",
					labelSeparator:""
				},
				items:[{
					name:"otherExplain",
					fieldLabel:"其他说明"
				}]
			}],
			buttons:[{
				scope:this,
				handler:this.addNote,
				text:"添加小记"
			}]
		};
		
		ast.ast1949.admin.crm.FeedbackForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/crm/reSmallNote.htm?id="+PAGE_CONST.COMPANY_ID,
	addNote:function(btn){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:MESSAGE.title,
				msg : MESSAGE.unValidate,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "操作成功",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "操作失败",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
	}
});

//电话反馈记录
ast.ast1949.admin.crm.FeedbackGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var _fields = this.crmRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalsRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});

		//var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([/*_sm,*/{
			header:'编号',
			sortable:false,
			dataIndex:'id',
			hidden:true
		},{
			header:'联系时间',
			sortable:false,
			dataIndex:'telNextTime',
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
				return Ext.util.Format.date(new Date(value.time),'Y-m-d');
       		}
		},{
			header:'通话类型',
			sortable:false,
			dataIndex:'callType',
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
				if(value!=null){
					if(value=="1"){
						return " 销售电话";
					} else if(value=="2"){
						return "服务回访";
					} else if(value=="3"){
						return "客户来电";
					} else if(value=="4"){
						return "调整日期 ";
					} else {
						return "/";
					}
				} else{
					return "/";
				}
       		}
		},{
			header:'联系情况',
			sortable:false,
			dataIndex:'contactType',
			renderer:function (value,metadata,record,rowIndex,colIndex,store){
				if(value!=null){
					if(value=="1"){
						return "无效联系";
					} else if(value=="2"){
						return "有效联系";
					} else if(value=="3"){
						return "该客户不存在";
					} else {
						return "/";
					}
				} else{
					return "/";
				}
       		}
		},{
			header:'联系人',
			sortable:false,
			dataIndex:'userName'
		},{
			header:'客户等级',
			sortable:false,
			dataIndex:'rank',
			renderer:function (value,metadata,record,rowIndex,colIndex,store){
				if(value!=null){
					return value+"星";
				} else{
					return "/";
				}
			}
		},{
			header:'联系内容 ',
			sortable:false,
			dataIndex:'operationDetails'
		}]);

		var c={
			store:_store,
//			sm:_sm,
			cm:_cm,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};

		ast.ast1949.admin.crm.FeedbackGrid.superclass.constructor.call(this,c);
	},
	listUrl:Context.ROOT+Context.PATH+"/admin/crm/getContactFeedbackList.htm?id="+PAGE_CONST.COMPANY_ID,
	crmRecord:[{name:"id",mapping:"contactFeedback.id"},
				{name:"telNextTime",mapping:"contactFeedback.telNextTime"},
				{name:"callType",mapping:"contactFeedback.callType"},
				{name:"contactType",mapping:"contactFeedback.contactType"},
				{name:"operationDetails",mapping:"contactFeedback.operationDetails"},
				{name:"userName",mapping:"userName"},
				{name:"rank",mapping:"rank"}
//				{name:"",mapping:"contactFeedback."},
//				{name:"",mapping:"contactFeedback."}
				]
});

ast.ast1949.admin.crm.editFormWin=function(id){
	var form = new ast.ast1949.admin.crm.SubscribeForm({
		id:PAGE_CONST.EDIT_SUB_FORM,
		region:"center",
		saveUrl:Context.ROOT+Context.PATH + "/admin/subscribe/insert.htm"
	});
	
	var win = new Ext.Window({
		id:PAGE_CONST.EDIT_SUB_WIN,
		title:"关键定制",
		width:"50%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};

//设置买家必看
ast.ast1949.admin.crm.SubscribeForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
	config = config||{};
	Ext.apply(this,config);

	var c={
		labelAlign : "right",
		labelWidth : 100,
		bodyStyle:"padding:5px 0 0",
		frame:true,
		items : [{
		layout : 'column',
		items : [{
			columnWidth : 1,
			layout : 'form',
			items :[
				//公司编号 帐号 关键字 是否买家必看
				{
				xtype:"hidden",
				name:"isMustSee",
				value:"1"
			},{
				xtype:"hidden",
				name:"companyId",
				value:PAGE_CONST.COMPANY_ID
			},{
				xtype:"hidden",
				name:"account",
				value:""
			},{		
				xtype:"textfield",
				fieldLabel:"关键字",
				name:"keywords",
				allowBlank:false,
				itemCls :"required",
				anchor : "95%"
			}]
			}]
		}],
		buttons:[{
			text:"添加",
			handler:this.save,
			scope:this
		}]
	};
	ast.ast1949.admin.crm.SubscribeForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
		var _fields=[
		{name:"id",mapping:"id"},
		{name:"chineseName",mapping:"chineseName"},
		{name:"companyName",mapping:"companyName"},
		{name:"email",mapping:"email"},
		{name:"keywords",mapping:"keywords"},
		{name:"isMustSee",mapping:"isMustSee"},
		{name:"supplydemandoffer",mapping:"supplydemandoffer"}
	];
	var form = this;
	var store = new Ext.data.JsonStore({
		root : "records",
		fields : _fields,
		url : Context.ROOT + Context.PATH + "/admin/subscribe/queryByIdSubscribe.htm",
		baseParams : {
			"id" : id
		},
		autoLoad : true,
		listeners : {
			"datachanged" : function(s) {
				var record = s.getAt(0);
				if (record == null) {
					Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载失败...");
				} else {
					form.getForm().loadRecord(record);
				}
			}
		}
	})
		
	},
	updateUrl:Context.ROOT+Context.PATH + "/admin/subscribe/insert.htm",
	//修改
	save:function(){
		var _url = this.updateUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "添加成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		ast.ast1949.admin.subscribe.resultGridReload();
		Ext.getCmp(_C.SUBSCRIBE_WIN).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "添加失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});
