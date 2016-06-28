Ext.namespace("com.zz91.ep.comp");

var COMP = new function(){
	this.COMP_FORM="compform";
	this.COMP_WIN = "compwindows";
	this.COMP_GRID = "compgrid";
}

com.zz91.ep.comp.FIELD=[
	{name:"id",mapping:"compProfile.id"},
	{name:"name",mapping:"compProfile.name"},
	{name:"details",mapping:"compProfile.details"},
	{name:"industryCode",mapping:"compProfile.industryCode"},
	{name:"serviceType",mapping:"serviceType"},
	{name:"mainBuy",mapping:"compProfile.mainBuy"},
	{name:"mainProductBuy",mapping:"compProfile.mainProductBuy"},
	{name:"mainSupply",mapping:"compProfile.mainSupply"},
	{name:"mainProductSupply",mapping:"compProfile.mainProductSupply"},
	{name:"memberCode",mapping:"compProfile.memberCode"},
	{name:"memberCodeBlock",mapping:"compProfile.memberCodeBlock"},
	{name:"registerCode",mapping:"compProfile.registerCode"},
	{name:"businessCode",mapping:"compProfile.businessCode"},
	{name:"areaCode",mapping:"compProfile.areaCode"},
	{name:"provinceCode",mapping:"compProfile.provinceCode"},
	{name:"legal",mapping:"compProfile.legal"},
	{name:"funds",mapping:"compProfile.funds"},
	{name:"mainBrand",mapping:"compProfile.mainBrand"},
	{name:"address",mapping:"compProfile.address"},
	{name:"addressZip",mapping:"compProfile.addressZip"},
	{name:"domain",mapping:"compProfile.domain"},
	{name:"domainTwo", mapping:"compProfile.domainTwo"},
	{name:"messageCount",mapping:"compProfile.messageCount"},
	{name:"viewCount",mapping:"compProfile.viewCount"},
	{name:"tags",mapping:"compProfile.tags"},
	{name:"detailsQuery", mapping:"compProfile.detailsQuery"},
	{name:"areaName",mapping:"areaName"},
	{name:"provinceName",mapping:"provinceName"},
	{name:"memberName",mapping:"memberName"},
	{name:"operator",mapping:"logInfo.operator"},
	{name:"operation",mapping:"logInfo.operation"},
	{name:"gmtLogTime",mapping:"logInfo.time"}
];

com.zz91.ep.comp.ACCOUNTGRIDFIELD=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"account",mapping:"account"},
	{name:"email",mapping:"email"},
	{name:"password",mapping:"password"},
	{name:"passwordClear",mapping:"passwordClear"},
	{name:"name",mapping:"name"},
	{name:"sex",mapping:"sex"},
	{name:"mobile",mapping:"mobile"},
	{name:"phoneCountry",mapping:"phoneCountry"},
	{name:"phoneArea",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"faxCountry",mapping:"faxCountry"},
	{name:"faxArea",mapping:"faxArea"},
	{name:"fax",mapping:"fax"},
	{name:"dept",mapping:"dept"},
	{name:"contact",mapping:"contact"},
	{name:"position",mapping:"position"},
	{name:"loginCount",mapping:"loginCount"},
	{name:"qq",mapping:"qq"},
	{name:"gmtLogin",mapping:"gmtLogin"}
]

com.zz91.ep.comp.LIKEFIELD=[
	{name:"id",mapping:"compProfile.id"},
	{name:"register_name",mapping:"registerSource"},
	{name:"cname",mapping:"compProfile.name"},
	{name:"mobile",mapping:"compAccount.mobile"},
	{name:"phone",mapping:"compAccount.phone"},
	{name:"phone_country",mapping:"compAccount.phoneCountry"},
	{name:"phone_area",mapping:"compAccount.phoneArea"},
	{name:"email",mapping:"compAccount.email"},
	{name:"gmt_register",mapping:"compAccount.gmtRegister"}
]

com.zz91.ep.comp.Form=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			layout:"column",
			frame:true,
			labelAlign : "right",
			labelWidth : 100,
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
					name:"id",
					id:"id"
				},{
					fieldLabel:"公司名称",
					name:"name",
					itemCls:"required",
					allowBlank:false
				},{
				 	xtype: "combo",
	                fieldLabel:"行业",
	                id:"industryName",
	                name:"industryName",
	                hiddenName: "industryCode",
	                editable: false,
	                triggerAction: "all",
	                anchor: '99%',
	                store: new Ext.data.Store({
	                    autoLoad: true,
	                    url: Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["comp_industry"],
	                    reader: new Ext.data.JsonReader({
	                        fields: [
	                                 {name:"code",mapping:"key"},
	                                 {name:"name",mapping:"name"}
	                        ]
	                    }),
	                }),
	                displayField: "name",
	                valueField: "code"
				},{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenName:"serviceType",
					hiddenId:"serviceType",
					editable: false,
					itemCls:"required",
					allowBlank:false,
					fieldLabel:"服务类型",
					store:[["0","供应商"],["1","求购商"],["2","全部"],["3","都不是"]]
				},{
					fieldLabel:"主营产品(采购)",
					name:"mainProductBuy"
				},{
					fieldLabel:"主营产品(供应)",
					name:"mainProductSupply"
				},{
					xtype:"hidden",
					id:"memberCode",
					name:"memberCode"
				},{
					fieldLabel:"会员类型",
					allowBlank:false,
					editable: false,
					id:"memberName",
					name:"memberName",
					itemCls:"required",
					readOnly:true,
					listeners:{
						"focus":function(field){
							var initValue=Ext.getCmp("memberCode").getValue();
							com.zz91.ep.comp.choiceMemberCategory(initValue,function(node,e){
								Ext.getCmp("memberName").setValue(node.text);
								Ext.getCmp("memberCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							})
						}
					}
				},{
					xtype:"combo",
					mode:"local",
					id:"memberCodeId",
					triggerAction:"all",
					hiddenName:"memberCodeBlock",
					hiddenId:"memberCodeBlock",
					fieldLabel:"冻结类型",
					editable: false,
					valueField:"code",
					displayField:"name",
					store:new Ext.data.JsonStore({
						autoLoad : true,
						url : Context.ROOT+"/combo/member.htm?parentCode="+COMBO.member_block,
						fields : ["code","name"]
					})
				},{
                    fieldLabel:"主要产品品牌",
                    name:"mainBrand"
                },{
                    fieldLabel:"法人",
                    name:"legal"
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
				 	xtype: "combo",
	                fieldLabel:"业务类型",
	                id:"businessName",
	                name:"businessName",
	                hiddenName: "businessCode",
	                editable: false,
	                forceSelection: true,
	                triggerAction: "all",
	                anchor: '99%',
	                store: new Ext.data.Store({
	                    autoLoad: true,
	                    url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["company_industry_code"],
	                    reader: new Ext.data.JsonReader({
	                        fields: ["value","name"]
	                    }),
	                }),
	                displayField: "name",
	                valueField: "value"
				},{
					xtype:"combo",
					id:"provinceName",
					hiddenId:"provinceCode",
					hiddenName:"provinceCode",
					fieldLabel:"省份",
					valueField:"code",
					displayField:"name",
					anchor : "95%",
					forceSelection : true,
					editable :false,
					emptyText :'请选择...',
					store:new Ext.data.JsonStore({
						autoLoad : true,
						url :Context.ROOT+"/combo/getAreaCode.htm?parentCode="+COMBO.areaCode["parentCode"],
						fields:["name","code"]
					}),
					mode:"local",
					triggerAction:"all",
					listeners :{
						"select" :function(){
							Ext.getCmp("areaName").setValue("");
							Ext.getCmp("areaName").store.reload({
								params:{"parentCode":Ext.get("provinceCode").dom.value}
							});
						}
					}
				},{
					xtype:"combo",
					mode:'local',
					triggerAction:'all',
					id : "areaName",
					hiddenId:"areaCode",
					hiddenName:"areaCode",
					fieldLabel:"城市",
					valueField:"code",
					displayField:"name",
					anchor : "95%",
					forceSelection : true,
					editable :false,
					emptyText :'请选择...',
					store:new Ext.data.JsonStore({
						autoLoad : false,
						url : Context.ROOT+"/combo/getAreaCode.htm",
						fields:["name","code"]
					})
				},{
                    fieldLabel:"公司地址",
                    name:"address",
                    allowBlank:false,
                    itemCls:"required"
                },{
                    fieldLabel:"邮编",
                    name:"addressZip"
                },{
                    xtype:"combo",
                    mode:"local",
                    editable: false,
                    triggerAction:"all",
                    hiddenId:"registerCode",
                    hiddenName:"registerCode",
                    fieldLabel:"注册来源",
                    valueField:"value",
                    displayField:"name",
                    store:new Ext.data.JsonStore({
                        autoLoad : true,
                        url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["register_type"],
                        fields:["value","name"],
                    }),
                    listeners:{
                        "change":function(field,newValue,oldValue){
                            grid.getStore().baseParams["registerCode"]=newValue;
                            grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
                        }
                    }
                },{
                    fieldLabel:"顶级域名",
                    name:"domain"
                },{
					fieldLabel:"注册资金",
					name:"funds",
				}]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
					xtype:"hidden",
					anchor:"99%",
					fieldLabel:"详细信息",
					id:"detailsQuery",
					name:"detailsQuery"
				},{
					xtype:"htmleditor",
					anchor:"99%",
					fieldLabel:"公司介绍",
					name:"details",
					id:"details",
					allowBlank:false,
					itemCls:"required"
				}]
			}],
			buttons:[{
				iconCls:"saveas16",
				text:"保存",
				handler:function(){
					var url=Context.ROOT+"/comp/comp/createProfile.htm";
					if(form.findById("id").getValue()>0){
						url=Context.ROOT+"/comp/comp/updateProfile.htm";
					}
					if(form.getForm().isValid()){
				    	form.getForm().submit({
			                url:url,
			                method:"post",
			                type:"json",
			                success:function(_form,_action){
				    		    Ext.getCmp('detailsQuery').setValue(_action.result.data);
								com.zz91.utils.Msg(MESSAGE.title, MESSAGE.saveSuccess);
								if(Ext.getCmp('memberCodeId').getValue() != ""){
									Ext.getCmp('bt').show();
								}
								form.reset();
							},
							failure:function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
			            });
				    }else{
			            Ext.MessageBox.show({
			                title:MESSAGE.title,
			                msg : MESSAGE.unValidate,
			                buttons:Ext.MessageBox.OK,
			                icon:Ext.MessageBox.ERROR
			            });
				    }
				}
			},{
				text:"刷新",
				iconCls:"refresh16",
				handler:function(btn){
					form.loadCompany();
				}
			},{
				text:"解除冻结",
				id:"bt",
				iconCls:"play16",
				hidden:true,
				handler:function(btn){
				    com.zz91.ep.comp.okwincompcode(Ext.getCmp("id").getValue());
					form.loadCompany();
				}
			}]
		};
		
		com.zz91.ep.comp.Form.superclass.constructor.call(this,c);
	},
	loadCompany:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : com.zz91.ep.comp.FIELD,
			url : Context.ROOT+"/comp/comp/queryFullProfile.htm?id="+id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
		                //判断是否是冻结账户
						if(record.get("memberCodeBlock") != ""){
							Ext.getCmp('bt').show();
		                 };
						form.findById("gmtLogTime").setValue(Ext.util.Format.date(new Date(record.get("gmtLogTime").time), 'Y-m-d H:i:s'));
						Ext.get("areaCode").dom.value=record.get("areaCode");
						Ext.get("provinceCode").dom.value=record.get("provinceCode");
						Ext.getCmp("areaName").store.reload({
							params:{"parentCode":record.get("provinceCode")}
						});
					}
				}
			}
		});
	}
	
});

com.zz91.ep.comp.okwincompcode=function(id){
	Ext.Ajax.request({
		url:Context.ROOT+"/comp/comp/updateCompCode.htm",
		params:{
			"id":id
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "操作成功，帐号已成功解冻",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO,
					fn : function(){
					  Ext.getCmp('bt').hide();
				    }
				});
			}else{
				Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "操作失败,帐号解冻失败",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.INFO
				});
			}
		},
		failure:function(response,opt){
			Ext.MessageBox.show({
				title : Context.MSG_TITLE,
				msg : "操作失败,帐号解冻失败",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	});	
}


com.zz91.ep.comp.CompanyAccountGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			fields:com.zz91.ep.comp.ACCOUNTGRIDFIELD,
			url:Context.ROOT + "/comp/comp/queryCompanyAccount.htm",
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			sortable : true,
			dataIndex : "id",
			hidden:true
		},{
			header : "公司编号",
			sortable : true,
			dataIndex : "cid",
			hidden:true
		},{
			header : "账号",
			dataIndex:"account"
		},{
			header : "明文密码",
			dataIndex:"passwordClear",
			width:60
		},{
			header : "联系人",
			dataIndex:"name",
			width:50
		},{
			header : "性别",
			dataIndex:"sex",
			width:40,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				var returnValue = value;
				if(value==0){
					returnValue="男";
				}
				if(value==1){
					returnValue="女";
				}
				return returnValue;
			}
		},{
			header : "email",
			dataIndex:"email"
		},{
			header : "手机",
			dataIndex:"mobile",
			width:80
		},{
			header : "电话号码",
			dataIndex:"phone",
			width:60
		},{
			header : "传真",
			dataIndex:"fax",
			width:60
		},{
			header : "职位",
			dataIndex:"position",
			width:50
		},{
			header : "部门",
			dataIndex:"dept",
			width:50
		},{
			header : "登录次数",
			dataIndex:"loginCount",
			width:60
		},{
			header : "最近登录时间",
			dataIndex:"gmtLogin",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:m:s');
				}
				else{
					return "";
				}
			}
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				text : '编辑',
				tooltip : '编辑',
				iconCls : 'edit16',
				handler : function(btn){
					var row = Ext.getCmp(COMP.COMP_GRID).getSelectionModel().getSelected();
					if(row!=null){
						com.zz91.ep.comp.editAccountWin(row.get("cid"));
					}
				}
			}]
		};
		
		com.zz91.ep.comp.CompanyAccountGrid.superclass.constructor.call(this,c);
	},
	loadAccount:function(companyId){
		this.getStore().reload({params:{"id":companyId}});
	}
});

com.zz91.ep.comp.editForm = Ext.extend(Ext.form.FormPanel,{
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
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "账号",
					allowBlank : false,
					itemCls :"required",
					name : "account",
					id : "account"
				}, {
					fieldLabel : "联系人",
					allowBlank : false,
					itemCls :"required",
					name : "name",
					id:"name"
				}, {
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenName:"sex",
					hiddenId:"sex",
					fieldLabel:"性别",
					allowBlank:false,
					itemCls:"required",
					store:[
						["0","男"],
						["1","女"]
					]
				},{
					fieldLabel : "email",
					allowBlank : false,
					itemCls :"required",
					name : "email",
					id:"email"
				},{
					fieldLabel : "职位",
					name : "position",
					id:"position"
				},{
					fieldLabel : "电话国家号",
					name : "phoneCountry",
					id:"phoneCountry"
				},{
					fieldLabel : "电话区号",
					name : "phoneArea",
					id:"phoneArea"
				}]
			},{
				columnWidth:0.5,
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
					fieldLabel : "明文密码",
					allowBlank : false,
					itemCls :"required",
					name : "passwordClear",
					id : "passwordClear"
				},{
					fieldLabel : "电话",
					id : "phone",
					name : "phone",
				},{
					fieldLabel : "传真",
					name : "fax",
					id : "fax",
				},{
					fieldLabel : "手机",
					allowBlank : false,
					name : "mobile",
					id : "mobile"
				},{
					fieldLabel : "部门",
					name : "dept",
					id:"dept"
				},{
					fieldLabel : "传真所在国家",
					name : "faxCountry",
					id:"faxCountry"
				},{
					fieldLabel : "传真所在地区",
					name : "faxArea",
					id:"faxArea"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				items:[{
				anchor:"100%",
				labelSeparator:"",
				xtype:"textfield",
				fieldLabel : "QQ号",
				name : "qq",
				id:"qq"
				}]
			},{
				columnWidth:1,
				layout:"form",
				items:[{
				anchor:"100%",
				labelSeparator:"",
				xtype:"textfield",
				fieldLabel : "其他联系方式",
				name : "contact",
				id:"contact"
				}]
			}
			],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					var url=this.saveUrl;
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.utils.Msg("","信息已保存");
								Ext.getCmp(COMP.COMP_WIN).close();
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
				text:"关闭",
				handler:function(btn){
					Ext.getCmp(COMP.COMP_WIN).close();
				}
			}]
		};
		
		com.zz91.ep.comp.editForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+ "/comp/comp/updateAccount.htm",
	loadOneRecord:function(id){
		var form = this;
		var _store = new Ext.data.JsonStore({
			fields : com.zz91.ep.comp.ACCOUNTGRIDFIELD,
			url : Context.ROOT + "/comp/comp/queryCompanyAccount.htm",
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

com.zz91.ep.comp.editAccountWin = function(cid){
	var form = new com.zz91.ep.comp.editForm({
		id:COMP.COMP_FORM,
		region:"center"
	});
	
	form.loadOneRecord(cid);
	
	var win = new Ext.Window({
			id:COMP.COMP_WIN,
			title:"修改账户信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.ep.comp.choiceAreaCategory=function(init,callback){
	
	var tree = new com.zz91.ep.common.area.Tree({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择地区类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
}

com.zz91.ep.comp.choiceMemberCategory=function(init,callback){
	
	var tree = new com.zz91.ep.member.Tree({
		id:"testtree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	tree.on("dblclick",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[tree]
	});
	
	win.show();
}

com.zz91.ep.comp.likeCompGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			fields:com.zz91.ep.comp.LIKEFIELD,
			url:Context.ROOT + "/comp/comp/queryLikeCompany.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "公司ID",
			dataIndex : "id",
			hidden:true
		},{
			header : "公司来源",
			dataIndex : "register_name",
			width:80,
			sortable : false
		},{
			header : "公司名称",
			dataIndex:"cname",
			width:180,
			id:"ableName",
			sortable : true,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var id=record.get("id");
				var returnValue=value;
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				var url = "<a href='"+Context.ROOT+"/comp/comp/details.htm?id="+id+"' target='_blank'>"+returnValue+"</a>";
				return url ;
			}
		},{
			header : "手机",
			dataIndex:"mobile",
			width:80,
			sortable : true
		},{
			header : "座机",
			dataIndex:"phone",
			width:100,
			sortable : true
		},{
			header : "座机所在国家",
			dataIndex:"phone_country",
			width:90,
			sortable : true
		},{
			header : "座机所在地区",
			dataIndex:"phone_area",
			width:90,
			sortable : true,
		},{
			header : "email",
			dataIndex:"email",
			width:150,
			sortable : true
		},{
			header : "注册时间",
			dataIndex:"gmt_register",
			width:140,
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm
		};
		
		com.zz91.ep.comp.likeCompGrid.superclass.constructor.call(this,c);
	},
	loadComp:function(cid){
		// 载入相同或相似公司信息
		if (cid>0){
			this.getStore().reload({
				params:{"id":cid}
			});
		}
	}
});
