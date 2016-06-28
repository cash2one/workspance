Ext.namespace("com.zz91.ep.api.zht");

com.zz91.ep.api.zht.SvrApplyDetailForm=Ext.extend(Ext.form.FormPanel,{
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
					id:"gmtPreStart",
					readOnly:true
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
				},
//				{
//					xtype:"numberfield",
//					fieldLabel:"中环通年限",
//					allowBlank:false,
//					itemCls:"required",
//					id:"zstYear",
//					name:"zstYear"
//				},
				{
					xtype:"combo",
					allowBlank:false,
					itemCls:"required",
					mode:"local",
					triggerAction:"all",
					name:"membership",
					id:"membership",
					hiddenName:"memberCode",
					hiddenId:"memberCode",
					fieldLabel:"会员类型",
					store:[
						["10011000","普通会员"],
						["10011001","付费会员"],
					],
					listeners:{
						"blur":function(field){
							if(Ext.get("membership").dom.value==""){
								field.setValue("");
							}
						}
					}
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
					id:"gmtPreEnd",
					readOnly:true
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
					name:"saleCategory",
					id:"saleCategory",
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
					hiddenId:"saleCategoryId",
					hiddenName:"saleCategory",
					listeners:{
						"blur":function(field){
							if(Ext.get("saleCategory").dom.value==""){
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
					id:"domain",
					name:"domain",
					listeners:{
						"blur":function(field){
							var cid = Ext.get("cid").dom.value;
							if(field.getValue()!=""){
								Ext.Ajax.request({
									url:Context.ROOT+"/api/zht/validateDomain.htm",
									params:{"cid":cid, "domain":field.getValue()},
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
					height:135,
					fieldLabel:"备注",
					id:"remark",
					name:"remark"
				},{
					xtype:"hidden",
					name:"cid",
					id:"cid"
				}]
			}],
			buttons:[{
				text:"现在开通",
				scope:this,
				handler:function(btn){
					if (!this.getForm().isValid()) {
						com.zz91.ep.utils.Msg("","请先填写必要的信息");
						return false;
					}
					var info="";
					if(this.findById("membership").getValue()=="10011000"){
						info="<span style='color:red;' >仍然保持普通会员身份</span>，";
					}
						
					var form=this;
					Ext.MessageBox.confirm(Context.MSG_TITLE, "中环通开通服务关系会员的各种功能使用，请务必谨慎操作！<br />现在您确定信息都填写无误，"+info+"开通中环通服务吗？", function(btn){
						if(btn != "yes"){
							return false;
						}
						form.getForm().submit({
							url : Context.ROOT+"/api/zht/doOpen.htm",
							method : "post",
							//waitMsg:Context.SAVEMASK.msg,
							success : function(_form,_action){
								var res = _action.result;
								if (res.success) {
									com.zz91.utils.Msg("","服务已开通!请关闭页面！");
									//TODO 开通成功后要做的事
									parent.com.zz91.ep.api.callback();
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
		
		com.zz91.ep.api.zht.SvrApplyDetailForm.superclass.constructor.call(this,c);
	},
	loadOpenDetail:function(data){
		this.findById("id").setValue(data["companySvrId"]);
		this.findById("cid").setValue(data["cid"]);
		this.findById("membership").setValue(data["memberCode"]);
		this.findById("domain").setValue(data["domain"]);
		this.findById("saleCategory").setValue(0);
		if(data["gmtPreStart"]!=null){
			this.findById("gmtPreStart").setValue(Ext.util.Format.date(new Date(data["gmtPreStart"]),'Y-m-d'));
		}
		if(data["gmtPreEnd"]!=null){
			this.findById("gmtPreEnd").setValue(Ext.util.Format.date(new Date(data["gmtPreEnd"]),'Y-m-d'));
		}
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
			this.findById("remark").setValue(data.svr.remark);
		}
		this.findById("oldCsAccount").setValue(data["csAccount"]);
//		this.findById("integral").setValue(data["integral"]);
	}
});