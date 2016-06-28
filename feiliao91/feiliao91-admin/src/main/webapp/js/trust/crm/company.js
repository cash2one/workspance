Ext.ns("ast.ast1949.admin.company");

Ext.define("CompanyGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"company.id"},
		{name:"name",mapping:"company.name"},
		{name:"membershipCode",mapping:"company.membershipCode"},
		{name:"regtime",mapping:"company.regtime"},
		{name:"membershipLabel",mapping:"membershipLabel"},
		{name:"mobile",mapping:"account.mobile"},
		{name:"contact",mapping:"account.contact"},
		{name:"account",mapping:"account.account"}
	]
});

Ext.define("ast.ast1949.admin.company.AdminRegistForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			border: 0,
			bodyPadding: 5,
	    	fieldDefaults: {
				labelAlign: 'right',
				labelWidth: 100
			},
			autoScroll:true,
		    layout:"anchor",
		    items : [{
		    	xtype:"container",
				layout:"column",
				anchor:"100%",
				items:[{
					xtype:"container",
					layout:"anchor",
					columnWidth: .5,
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						formItemCls:"x-form-item required",
						allowBlank:false,
						fieldLabel:"公司名称",
						name:"name",
						id:"name"
					}
//					,{
//						fieldLabel:"参与活动",
//						name:"activeFlag",
//						id:"activeFlag",
//						listeners:{
//							"focus":function(){
//								
//								var tree=Ext.create("ast.ast1949.util.Tree",{
//									rootCode:"2003",
//									rootexpanded:true,
//									selType:"checkboxmodel",
//									multiSelect:true
//								});
//								
//								var win=Ext.create("Ext.Window",{
//									width:250,
//									height:350,
//									modal:true,
//									layout:"fit",
//									items:[tree],
//									buttons:[{
//										text:"选择",
//										iconCls:"accept16",
//										handler:function(btn,e){
//											//tree选中项
//											var model=tree.getSelectionModel().getSelection();
//											var codeArr=new Array();
//											var labelArr=new Array();
//											for(var i=0;i<model.length;i++){
//												codeArr.push(model[i].getId());
//												labelArr.push(model[i].data.text);
//											}
//											Ext.getCmp("activeFlag").setValue(labelArr.join(","));
//											Ext.getCmp("activeFlagCode").setValue(codeArr.join(","));
//											
//											this.up("window").close();
//										}
//									},{
//										text:"关闭",
//										iconCls:"close16",
//										handler:function(btn,e){
//											this.up("window").close();
//										}
//									}]
//								});
//								
//								win.show();
//							}
//						}
//					}
					,{
						xtype:"hidden",
						name:"companyId",
						id:"companyId"
					},{
						xtype:"hidden",
						name:"trustId",
						id:"trustId"
					},{
						xtype:"hidden",
						name:"activeFlagCode",
						id:"activeFlagCode"
					},Ext.create("ast.ast1949.util.CategoryCombo",{
						rootCode:"1000",
						fieldLabel:"主营行业",
						name:"industryCode",
						id:"industryCode"
					}),Ext.create("ast.ast1949.util.CategoryCombo",{
						rootCode:"1020",
						fieldLabel:"服务类型",
						name:"serviceCode",
						id:"serviceCode"
					}),{
						fieldLabel:"地区",
						name:"areaLabel",
						id:"areaLabel",
						listeners:{
							"focus":function(){
								var initCode=Ext.getCmp("areaCode").getValue();
								var win=Ext.create("ast.ast1949.util.TreeSelectorWin",{
									title:"选择地区",
									modal:true,
									rootCode:"1001",
									initCode:initCode,
									width:300,
									height:450,
									callbackFn:function(node){
										Ext.getCmp("areaCode").setValue(node.getId());
										Ext.getCmp("areaLabel").setValue(node.data.text);
										win.close();
									}
								});
								win.initTree(4);
								win.show();
								
							}
						}
					},{
						xtype:"hidden",
						name:"areaCode",
						id:"areaCode"
					},Ext.create("ast.ast1949.util.CategoryCombo",{
						formItemCls:"x-form-item required",
//						allowBlank:false,
						rootCode:"1003",
						fieldLabel:"注册来源",
						name:"regfromCode",
						id:"regfromCode"
					}),{
						fieldLabel:"主营业务",
						name:"business",
						id:"business"
					},{
						xtype:"combo",
						fieldLabel : "主营方向",
						name:"businessType",
						mode:"local",
						triggerAction : "all",
						store:[[0,"全部"],[1,"供应"],[2,"求购"]]
					},{
						fieldLabel:"主营方向(供应)",
						name:"saleDetails",
						id:"saleDetails"
					},{
						fieldLabel:"主营方向(求购)",
						name:"buyDetails",
						id:"buyDetails"
					},{
						fieldLabel:"地址",
						name:"address",
						id:"address"
					},{
						fieldLabel:"邮编",
						name:"addressZip",
						id:"addressZip"
					}]
				},{
					xtype:"container",
					layout:"anchor",
					columnWidth: .5,
					defaults:{
						anchor:'100%',
						xtype : 'textfield'
					},
					items:[{
						formItemCls:"x-form-item required",
						allowBlank:false,
						fieldLabel:"手机号",
						name:"mobile",
						id:"mobile",
						listeners:{
							"blur":function(){
								Ext.Ajax.request({
									url:Context.ROOT+"/zz91/admin/admincompany/initInfoByMobile.htm",
									async: false,
									params:{
										mobile:Ext.getCmp("mobile").getValue()
									},  
									success: function(resp,opts) {
//										var dto = Ext.util.JSON.decode(resp.responseText);
										var dto = Ext.decode(resp.responseText);
										if (dto.account!=null&&dto.company!=null) {
											Ext.getCmp("email").setValue(dto.account.email);
											
											Ext.getCmp("account").setValue(dto.account.account);
											Ext.getCmp("contact").setValue(dto.account.contact);
											Ext.getCmp("tel").setValue(dto.account.tel);
											Ext.getCmp("fax").setValue(dto.account.fax);
											Ext.getCmp("qq").setValue(dto.account.qq);
											Ext.getCmp("industryCode").setValue(dto.company.industryCode);
											Ext.getCmp("serviceCode").setValue(dto.company.serviceCode);
											Ext.getCmp("areaCode").setValue(dto.company.areaCode);
											Ext.getCmp("areaLabel").setValue(dto.areaLabel);
											Ext.getCmp("regfromCode").setValue(dto.company.regfromCode);
											Ext.getCmp("business").setValue(dto.company.business);
											Ext.getCmp("saleDetails").setValue(dto.company.saleDetails);
											Ext.getCmp("buyDetails").setValue(dto.company.buyDetails);
											Ext.getCmp("address").setValue(dto.company.address);
											Ext.getCmp("addressZip").setValue(dto.company.addressZip);
											Ext.getCmp("sex").setValue(dto.account.sex);
											Ext.getCmp("msn").setValue(dto.account.msn);
											Ext.getCmp("details").setValue(dto.company.details);
											Ext.getCmp("name").setValue(dto.company.name);
											Ext.getCmp("companyId").setValue(dto.account.companyId);
										}
									}
								});
//								if(Ext.getCmp("mobile").getValue()!="" && Ext.getCmp("email").getValue()==""){
//									Ext.getCmp("email").setValue(Ext.getCmp("mobile").getValue()+"@zz91.com");
//								}
//								if(Ext.getCmp("mobile").getValue()!="" && Ext.getCmp("account").getValue()==""){
//									Ext.getCmp("account").setValue("zz91_"+Ext.getCmp("mobile").getValue());
//								}
							}
						}
					},{
						formItemCls:"x-form-item required",
						allowBlank:false,
						fieldLabel:"账号",
						name:"account",
						id:"account",
					},{
						formItemCls:"x-form-item required",
						allowBlank:false,
						fieldLabel:"Email",
						name:"email",
						id:"email",
						listeners:{
							"blur":function(){
								Ext.Ajax.request({
									url:Context.ROOT+"/zz91/admin/admincompany/initInfoByEmail.htm",
									async: false,
									params:{
										email:Ext.getCmp("email").getValue()
									},  
									success: function(resp,opts) {
										var dto = Ext.decode(resp.responseText);
										if (dto.account!=null&&dto.company!=null) {
											Ext.getCmp("mobile").setValue(dto.account.mobile);
											
											Ext.getCmp("account").setValue(dto.account.account);
											Ext.getCmp("contact").setValue(dto.account.contact);
											Ext.getCmp("tel").setValue(dto.account.tel);
											Ext.getCmp("fax").setValue(dto.account.fax);
											Ext.getCmp("qq").setValue(dto.account.qq);
											Ext.getCmp("industryCode").setValue(dto.company.industryCode);
											Ext.getCmp("serviceCode").setValue(dto.company.serviceCode);
											Ext.getCmp("areaCode").setValue(dto.company.areaCode);
											Ext.getCmp("areaLabel").setValue(dto.areaLabel);
											Ext.getCmp("regfromCode").setValue(dto.company.regfromCode);
											Ext.getCmp("business").setValue(dto.company.business);
											Ext.getCmp("saleDetails").setValue(dto.company.saleDetails);
											Ext.getCmp("buyDetails").setValue(dto.company.buyDetails);
											Ext.getCmp("address").setValue(dto.company.address);
											Ext.getCmp("addressZip").setValue(dto.company.addressZip);
											Ext.getCmp("sex").setValue(dto.account.sex);
											Ext.getCmp("msn").setValue(dto.account.msn);
											Ext.getCmp("details").setValue(dto.company.details);
											Ext.getCmp("name").setValue(dto.company.name);
											Ext.getCmp("companyId").setValue(dto.account.companyId);
										}
									}
								});
							}
						}
					},{
						fieldLabel:"联系人",
						name:"contact",
						id:"contact"
					},{
						fieldLabel:"电话",
						name:"tel",
						id:"tel"
					},{
						fieldLabel:"传真",
						name:"fax",
						id:"fax"
					},{
						xtype:"combo",
						fieldLabel : "性别",
						name:"sex",
						id:"sex",
						mode:"local",
						triggerAction : "all",
						store:[[0,"男"],[1,"女"]]
					},{
						fieldLabel:"职位",
						name:"position",
						id:"position"
					},{
						fieldLabel:"qq",
						name:"qq",
						id:"qq"
					},{
						fieldLabel:"MSN",
						name:"msn",
						id:"msn"
					}]
				}]
		    },{
		    	xtype:"textarea",
		    	name:"details",
		    	id:"details",
		    	fieldLabel:"公司简介",
		    	height:250,
		    	anchor:"100%"
		    }],
			buttons:[{
				xtype:"button",
				text:"注册并绑定",
				iconCls:"saveas32",
				scale:"large",
				handler:this.saveModel
			},{
				xtype:"button",
				text:"重置",
				iconCls:"file32",
				scale:"large",
				handler:function(){
					this.up("form").getForm().reset();
				}
			},{
				xtype:"button",
				text:"关闭",
				iconCls:"close32",
				scale:"large",
				handler:function(){
					window.close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	saveModel:function(btn,e){
		var form=this.up("form");
		
		if(form.getForm().isValid()){
			form.getForm().submit({
				url:Context.ROOT+"/zz91/admin/admincompany/doRegistByAdminForTrust.htm",
				success: function(f, action) {
//					if(action.result.success){
						Ext.Msg.alert(Context.MSG_TITLE, action.result.data);
						form.getForm().reset();
//					}else{
//						Ext.Msg.alert(Context.MSG_TITLE, action.result.data);
//					}
					//错误处理
					
				},
				failure: function(f, action) {
					Ext.Msg.alert(Context.MSG_TITLE, action.result.data);
//					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，信息没有更新！");
				}
			});
		}
	},
	setTrustId:function(trustId){
		Ext.getCmp("trustId").setValue(trustId);
	}
});

Ext.define("ast.ast1949.admin.company.MainGrid", {
	extend:"Ext.grid.Panel",
	queryUrl:Context.ROOT +Context.PATH+ "/trust/crm/relateCompanyBySearch.htm",
	advanceSearchForm:null,
	config:{
		queryUrl:null,
		myToolbar:null,
		advanceSearchForm:null
	},
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CompanyGridModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				actionMethods:{read:"POST"},
				url:this.getQueryUrl(),
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        }
			},
			autoLoad:false
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[
			{text:"编号",dataIndex:"id",width:50,hidden:true},
			{header:"公司名称",dataIndex:"name",width:250,renderer:function(v,m,record,ridx,cidx,store,view){
					var val="";
					if(record.get("membershipCode")=="10051003"){
						val="<img src='"+Context.ROOT+"/images/ldblogo.jpg'width='20px'height='20px' />";
					}else if(record.get("membershipCode")!="10051000"){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					}
					
					var blockStyle="";
					if(record.get("isBlock")=="1"){
						blockStyle="color:#666;text-decoration:line-through;";
					}
					
					var tmp="{0} <span style='{2}' >{1}</span>";
					var url="";
					if(record.get("domainZz91")!=""){
						tmp="{0}<span style='{2}' > <a href='{3}' target='_blank'>{1}</a></span>";
						url="http://"+record.get("domainZz91")+".zz91.com";
					}
					return Ext.String.format(tmp,val,v,blockStyle,url);
				}
			},
			{text:"登录帐号",dataIndex:"account"},
			{text:"联系人",dataIndex:"contact"},
			{text:"手机",dataIndex:"mobile"},
			{text:"注册时间",width:150,dataIndex:"regtime",renderer:function(v,m,record,ridx,cidx,store,view){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}
		];
		
		var c={
				tbar:["手机",{
					xtype:"textfield",
					emptyText:"请输入手机号码",
					id:"mobileSearch",
					listeners:{
						"change":function(field,nv,ov){
							this.up("grid").getStore().setExtraParam("mobile", nv);
//							this.up("grid").getStore().load();
						}
					}
				},"公司名",{
					xtype:"textfield",
					emptyText:"请输入公司名",
					id:"nameSearch",
					listeners:{
						"change":function(field,nv,ov){
							this.up("grid").getStore().setExtraParam("name", nv);
//							this.up("grid").getStore().load();
						}
					}
				},{
					iconCls:"configure16",
					text:"查询",
					handler:function(){
						this.up("grid").getStore().setExtraParam("startIndex", 0);
						this.up("grid").getStore().setExtraParam("pageSize", Context.PAGE_SIZE);
						this.up("grid").getStore().load();
					}
				},{
					iconCls:"configure16",
					text:"关联帐号",
					handler:function(){
						var row = this.up("grid").getSelectionModel().getSelection();
						var companyId = row[0].data.id;
						if(companyId ==undefined ){
							alert("请选择一条公司记录进行关联")
						}
						Ext.Ajax.request({
							url:Context.ROOT +Context.PATH+ "/trust/crm/relateByCompany.htm" ,
							async: false,
							params:{
								companyId:companyId,
								trustId:this.up("grid").trustId
							},
							success: function(resp,opts) {
								var dto = Ext.decode(resp.responseText);
								if(dto.success){
									alert("关联帐号成功,点击确定，页面将自动关闭");
									window.close();
//									Ext.getCmp("relateGrid").getStore().load();
								}
							}
						});
					}
				}],
			store:_store,
			columns:_cm,
			selModel:_sm,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			}]
		};
		
		//设置工具栏
		if(this.getMyToolbar()!=null){
			c["tbar"]=this.getMyToolbar();
		}
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	initByTrustId:function(mobile,name){
		Ext.getCmp("mobileSearch").setValue(mobile);
		Ext.getCmp("nameSearch").setValue(name);
		this.getStore().setExtraParam("mobile", mobile);
		this.getStore().setExtraParam("name", name);
		this.getStore().load();
	},
	trustId:0
});
