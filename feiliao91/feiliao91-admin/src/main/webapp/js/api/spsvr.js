Ext.namespace("ast.ast1949.api.spsvr");

ast.ast1949.api.spsvr.SvrApplyDetailForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelWidth:100,
			labelAlign:"right",
			frame:true,
			layout:"column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"datefield",
					labelSeparator:""
				},items:[{
					xtype:"hidden",
					name:"id",
					id:"id"
				},{
					xtype:"hidden",
					name:"companyId",
					id:"companyId"
				},{
					xtype:"hidden",
					name:"oldCsAccount",
					id:"oldCsAccount"
				},{
					xtype:"hidden",
					name:"applyStatus",
					id:"applyStatus"
				},{
					fieldLabel:"上次服务开始时间",
					format:"Y-m-d",
					name:"gmtPreStartDate",
					id:"gmtPreStart"
				},{
					fieldLabel:"本次服务开始时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtStartDate",
					id:"gmtStart"
				},{
					fieldLabel:"服务签定时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtSignedDate",
					id:"gmtSigned"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"datefield",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"上次服务结束时间",
					format:"Y-m-d",
					name:"gmtPreEndDate",
					id:"gmtPreEnd"
				},{
					fieldLabel:"本次服务结束时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtEndDate",
					id:"gmtEnd"
				},{
					xtype:"textfield",
					fieldLabel:"二级域名",
					allowBlank:false,
					itemCls:"required",
					id:"domainZz91",
					name:"domainZz91",
					listeners:{
						"blur":function(field){
							var companyId = Ext.get("companyId").dom.value;
							if(field.getValue()!=""){
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+"/api/zst/validateDomain.htm",
									params:{"companyId":companyId, "domainZz91":field.getValue()},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(!obj.success){
											Ext.MessageBox.show({
												title : Context.MSG_TITLE,
												msg : "二级域名 "+field.getValue()+" 已经被占用，请更换其他二级域名",
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
											field.setValue("");
										}
									},
									failure:function(response,opt){
										Ext.MessageBox.show({
											title : Context.MSG_TITLE,
											msg : "发生错误，暂时无法判断二级域名是否已被使用",
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.INFO
										});
									}
								});
							}
						}
					}
				}
//				,{
//					xtype:"checkbox",
//					inputValue:true,
//					boxLabel:"邮件通知",
//					name:"noticeEmail",
//					id:"noticeEmail"
//				},{
//					xtype:"checkbox",
//					inputValue:true,
//					boxLabel:"短信通知",
//					name:"noticeSms",
//					id:"noticeSms"
//				}
				]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					labelSeparator:""
				},
				items:[{
					xtype:"textarea",
					anchor:"99%",
					fieldLabel:"备注",
					name:"remark"
				}]
			}],
			buttons:[{
				text:"现在开通",
				scope:this,
				handler:function(btn){
					if (!this.getForm().isValid()) {
						ast.ast1949.utils.Msg("","请先填写必要的信息");
						return false;
					}
//					var info="";
//					if(this.findById("membership").getValue()=="10051000"){
//						info="<span style='color:red;' >仍然保持普通会员身份</span>，";
//					}
						
					var form=this;
					Ext.MessageBox.confirm(Context.MSG_TITLE, "商铺服务关系会员的各种功能使用，请务必谨慎操作！<br />现在您确定信息都填写无误，开通商铺服务吗？", function(btn){
						if(btn != "yes"){
							return false;
						}
						form.getForm().submit({
							url : Context.ROOT+Context.PATH+"/api/spsvr/doOpen.htm",
							method : "post",
							waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									ast.ast1949.utils.Msg("","服务已开通");
									//TODO 开通成功后要做的事
									parent.ast.ast1949.api.callback();
								} else {
									Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "发生错误,信息没有被保存",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
								}
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,信息没有被保存",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
							}
						});
					});
					
				}
			}]
		};
		
		ast.ast1949.api.spsvr.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadOpenDetail:function(data){
		this.findById("id").setValue(data["companySvrId"]);
		this.findById("companyId").setValue(data["companyId"]);
//		this.findById("membership").setValue(data["membershipCode"]);
		this.findById("domainZz91").setValue(data["domainZz91"]);
//		this.findById("categoryName").setValue(0);
		if(data["gmtPreStart"]!=null){
			this.findById("gmtPreStart").setValue(Ext.util.Format.date(new Date(data["gmtPreStart"]),'Y-m-d'));
		}
		if(data["gmtPreEnd"]!=null){
			this.findById("gmtPreEnd").setValue(Ext.util.Format.date(new Date(data["gmtPreEnd"]),'Y-m-d'));
		}
//		this.findById("integral").setValue(data["integral"]);
		if(typeof data["svr"]!="undefined"){
			if(data.svr.gmtStart!=null){
				this.findById("gmtStart").setValue(Ext.util.Format.date(new Date(data.svr.gmtStart.time),'Y-m-d'));
			}
			if(data.svr.gmtEnd!=null){
				this.findById("gmtEnd").setValue(Ext.util.Format.date(new Date(data.svr.gmtEnd.time),'Y-m-d'));
			}
			if(data.svr.gmtSigned!=null){
				this.findById("gmtSigned").setValue(Ext.util.Format.date(new Date(data.svr.gmtSigned.time),'Y-m-d'));
			}
			this.findById("applyStatus").setValue(data.svr.applyStatus);
//			this.findById("zstYear").setValue(data.svr.zstYear);
		}
//		if(typeof data["csAccount"]!="undefined"){
//			this.findById("cs").setValue(data["csAccount"]);
//		}
//		if(typeof data["csName"] !="undefined"){
//			Ext.get("cs").dom.value=data["csName"];
//		}
//		this.findById("oldCsAccount").setValue(data["csAccount"]);
	},
	loadSvrDetail:function(id){
		var fields=ast.ast1949.crm.open.SvrDetail;
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : fields,
			url : Context.ROOT + Context.PATH + "/crm/open/querySvrById.htm?id="+id,
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
						form.getForm().loadRecord(record);
						//Ext.util.Format.date(new Date(value.time),'Y-m-d')
						if(record.get("gmt_pre_start")!=null){
							form.findById("gmtPreStart").setValue(Ext.util.Format.date(new Date(record.get("gmt_pre_start").time),'Y-m-d'));
						}
						if(record.get("gmt_pre_end")!=null){
							form.findById("gmtPreEnd").setValue(Ext.util.Format.date(new Date(record.get("gmt_pre_end").time),'Y-m-d'));
						}
						if(record.get("gmt_start")!=null){
							form.findById("gmtStart").setValue(Ext.util.Format.date(new Date(record.get("gmt_start").time),'Y-m-d'));
						}
						if(record.get("gmt_end")!=null){
							form.findById("gmtEnd").setValue(Ext.util.Format.date(new Date(record.get("gmt_end").time),'Y-m-d'));
						}
						if(record.get("gmt_signed")!=null){
							form.findById("gmtSigned").setValue(Ext.util.Format.date(new Date(record.get("gmt_signed").time),'Y-m-d'));
						}
					}
				}
			}
		});
	}
});

ast.ast1949.api.spsvr.SvrApplyDetailEditorForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelWidth:100,
			labelAlign:"right",
			frame:true,
			layout:"column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"datefield",
					labelSeparator:""
				},items:[{
					xtype:"hidden",
					name:"id",
					id:"id"
				},{
					xtype:"hidden",
					name:"companyId",
					id:"companyId"
				}
//				,{
//					xtype:"hidden",
//					name:"oldCsAccount",
//					id:"oldCsAccount"
//				}
				,{
					xtype:"hidden",
					name:"applyStatus",
					id:"applyStatus"
				},{
					fieldLabel:"上次服务开始时间",
					format:"Y-m-d",
					name:"gmtPreStartDate",
					id:"gmtPreStart"
				},{
					fieldLabel:"本次服务开始时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtStartDate",
					id:"gmtStart"
				},{
					fieldLabel:"服务签定时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtSignedDate",
					id:"gmtSigned"
				},{
					xtype:"numberfield",
					fieldLabel:"再生通年限",
					allowBlank:false,
					itemCls:"required",
					id:"zstYear",
					name:"zstYear"
				}
//				,{
//					xtype:"combo",
//					allowBlank:false,
//					itemCls:"required",
//					mode:"local",
//					triggerAction:"all",
//					name:"membership",
//					id:"membership",
//					hiddenName:"membershipCode",
//					hiddenId:"membershipCode",
//					fieldLabel:"会员类型",
//					store:[
//						["10051000","普通会员"],
//						["10051001","再生通会员"],
//						["100510021000","银牌品牌通会员"],
//						["100510021001","金牌品牌通会员"],
//						["100510021002","钻石品牌通会员"]
//					],
//					listeners:{
//						"blur":function(field){
//							if(Ext.get("membership").dom.value==""){
//								field.setValue("");
//							}
//						}
//					}
//				}
				]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"datefield",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"上次服务结束时间",
					format:"Y-m-d",
					name:"gmtPreEndDate",
					id:"gmtPreEnd"
				},{
					fieldLabel:"本次服务结束时间",
					allowBlank:false,
					itemCls:"required",
					format:"Y-m-d",
					name:"gmtEndDate",
					id:"gmtEnd"
				},{
					xtype:"combo",
					fieldLabel:"类别",
					itemCls:"required",
					allowBlank:false,
					name:"categoryName",
					id:"categoryName",
					lazyInit:true,
					mode:"local",
					triggerAction:"all",
					lazyRender:true,
					store:new Ext.data.SimpleStore({
						fields:['k','v'],
						data:[[0,'付费'],[1,'赠送']]
					}),
					valueField:"k",
					displayField:"v",
					id:"categoryName",
					hiddenName:"category",
					listeners:{
						"blur":function(field){
							if(Ext.get("categoryName").dom.value==""){
								field.setValue("");
							}
						}
					}
				}
				,{
					xtype:"textfield",
					fieldLabel:"二级域名",
					allowBlank:false,
					itemCls:"required",
					id:"domainZz91",
					name:"domainZz91",
					listeners:{
						"blur":function(field){
							var companyId = Ext.get("companyId").dom.value;
							if(field.getValue()!=""){
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+"/api/zst/validateDomain.htm",
									params:{"companyId":companyId, "domainZz91":field.getValue()},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(!obj.success){
											Ext.MessageBox.show({
												title : Context.MSG_TITLE,
												msg : "二级域名 "+field.getValue()+" 已经被占用，请更换其他二级域名",
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
											field.setValue("");
										}
									},
									failure:function(response,opt){
										Ext.MessageBox.show({
											title : Context.MSG_TITLE,
											msg : "发生错误，暂时无法判断二级域名是否已被使用",
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.INFO
										});
									}
								});
							}
						}
					}
				}
//				,{
//					xtype:"checkbox",
//					inputValue:true,
//					boxLabel:"邮件通知",
//					name:"noticeEmail",
//					id:"noticeEmail"
//				},{
//					xtype:"checkbox",
//					inputValue:true,
//					boxLabel:"短信通知",
//					name:"noticeSms",
//					id:"noticeSms"
//				}
				]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					labelSeparator:""
				},
				items:[{
					xtype:"textarea",
					anchor:"99%",
					fieldLabel:"备注",
					id:"remark",
					name:"remark"
				}]
			}],
			buttons:[{
				text:"变更",
				scope:this,
				handler:function(btn){
					if (!this.getForm().isValid()) {
						ast.ast1949.utils.Msg("","请先填写必要的信息");
						return false;
					}
					var info="";
					if(this.findById("membership")!=null&&this.findById("membership").getValue()=="10051000"){
						info="<span style='color:red;' >变更为普通会员身份</span>，";
					}
						
					var form=this;
					Ext.MessageBox.confirm(Context.MSG_TITLE, "再生通服务变更关系会员的各种功能使用，请务必谨慎操作！<br />现在您确定信息都填写无误，"+info+"开通再生通服务吗？", function(btn){
						if(btn != "yes"){
							return false;
						}
						form.getForm().submit({
							url : Context.ROOT+Context.PATH+"/api/zst/doChange.htm",
							method : "post",
							waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									ast.ast1949.utils.Msg("","服务已变更");
									//TODO 开通成功后要做的事
									parent.ast.ast1949.api.callback();
								} else {
									Ext.MessageBox.show({
										title : Context.MSG_TITLE,
										msg : "发生错误,信息没有被保存",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
								}
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title : Context.MSG_TITLE,
									msg : "发生错误,信息没有被保存",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
							}
						});
					});
					
				}
			}]
		};
		
		ast.ast1949.api.spsvr.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadChangeDetail:function(data){
		this.findById("id").setValue(data["companySvrId"]);
		this.findById("companyId").setValue(data["companyId"]);
//		this.findById("membership").setValue(data["membershipCode"]);
		this.findById("domainZz91").setValue(data["domainZz91"]);
//		this.findById("categoryName").setValue(data.svr.category);
//		this.findById("integral").setValue(data["integral"]);
		if(typeof data["svr"]!="undefined"){
			if(data.svr.gmtPreStart!=null){
				this.findById("gmtPreStart").setValue(Ext.util.Format.date(new Date(data.svr.gmtPreStart.time),'Y-m-d'));
			}
			if(data.svr.gmtPreEnd!=null){
				this.findById("gmtPreEnd").setValue(Ext.util.Format.date(new Date(data.svr.gmtPreEnd.time),'Y-m-d'));
			}
			if(data.svr.gmtStart!=null){
				this.findById("gmtStart").setValue(Ext.util.Format.date(new Date(data.svr.gmtStart.time),'Y-m-d'));
			}
			if(data.svr.gmtEnd!=null){
				this.findById("gmtEnd").setValue(Ext.util.Format.date(new Date(data.svr.gmtEnd.time),'Y-m-d'));
			}
			if(data.svr.gmtSigned!=null){
				this.findById("gmtSigned").setValue(Ext.util.Format.date(new Date(data.svr.gmtSigned.time),'Y-m-d'));
			}
			this.findById("applyStatus").setValue(data.svr.applyStatus);
			this.findById("zstYear").setValue(data.svr.zstYear);
		}
		this.findById("remark").setValue(data.svr.remark);
	}
});