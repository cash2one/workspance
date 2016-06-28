Ext.namespace("com.zz91.ep.cscrm");

var MYCOMP=new function(){
	this.SERVICE_NOTE_GRID="servicenotegrid";
	this.CONTACT_GRID="contactgrid";
	this.CONTACT_WIN="contactwin";
	this.CONTACT_FORM="contactform";
};

com.zz91.ep.cscrm.NOTEFIELD=[
	{name:"id",mapping:"id"},
	{name:"saleName",mapping:"saleName"},
	{name:"star",mapping:"star"},
	{name:"situation",mapping:"situation"},
	{name:"remark",mapping:"remark"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtNextContact",mapping:"gmtNextContact"},
	{name:"contactType",mapping:"contactType"}
];

com.zz91.ep.cscrm.CONTACTFIELD=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"email",mapping:"email"},
	{name:"name",mapping:"name"},
	{name:"sex",mapping:"sex"},
	{name:"mobile",mapping:"mobile"},
	{name:"phone_country",mapping:"phoneCountry"},
	{name:"phone_area",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"fax_country",mapping:"faxCountry"},
	{name:"fax_area",mapping:"faxArea"},
	{name:"fax",mapping:"fax"},
	{name:"contact",mapping:"contact"},
	{name:"position",mapping:"position"},
	{name:"is_key",mapping:"isKey"},
	{name:"remark",mapping:"remark"},
	{name:"sale_account",mapping:"saleAccount"},
	{name:"sale_name",mapping:"saleName"},
	{name:"sale_dept",mapping:"saleDept"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"gmt_modified",mapping:"gmtModified"}
];

com.zz91.ep.cscrm.FIELD=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"cname",mapping:"cname"},
	{name:"ctype",mapping:"ctype"},
	{name:"saleCompId",mapping:"saleCompId"},
	{name:"uid",mapping:"uid"},
	{name:"account",mapping:"account"},
	{name:"email",mapping:"email"},
	{name:"name",mapping:"name"},
	{name:"sex",mapping:"sex"},
	{name:"mobile",mapping:"mobile"},
	{name:"phoneCountry",mapping:"phoneCountry"},
	{name:"phoneArea",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"faxCountry",mapping:"faxCountry"},
	{name:"faxArea",mapping:"faxArea"},
	{name:"fax",mapping:"fax"},
	{name:"address",mapping:"address"},
	{name:"addressZip",mapping:"addressZip"},
	{name:"details", mapping:"details"},
	{name:"industryCode",mapping:"industryCode"},
	{name:"memberCode",mapping:"memberCode"},
	{name:"registerCode",mapping:"registerCode"},
	{name:"businessCode",mapping:"businessCode"},
	{name:"provinceCode",mapping:"provinceCode"},
	{name:"areaCode",mapping:"areaCode"},
	{name:"mainBuy",mapping:"mainBuy"},
	{name:"mainProductBuy",mapping:"mainProductBuy"},
	{name:"mainSupply",mapping:"mainSupply"},
	{name:"mainProductSupply",mapping:"mainProductSupply"},
	{name:"loginCount",mapping:"loginCount"},
	{name:"gmtLogin",mapping:"gmtLogin"},
	{name:"gmtRegister", mapping:"gmtRegister"},
	{name:"gmtInput",mapping:"gmtInput"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"},
	{name:"sysStar",mapping:"sysStar"},
	{name:"areaName",mapping:"areaName"},
	{name:"saleName",mapping:"saleName"},
	{name:"registerName",mapping:"registerName"},
	{name:"position",mapping:"position"},
	{name:"contact",mapping:"contact"}
];

com.zz91.ep.cscrm.Form=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
	config = config||{};
	Ext.apply(this,config);
	var form=this;
	var c={
		labelAlign : "right",
		labelWidth : 90,
		height:428,
		frame:true,
		autoScroll:true,
		items:[{
			layout:"column",
//			frame:true,
			items:[{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						name:"cid",
						id:"cid"
					},{
						fieldLabel:"公司名称",
						name:"cname",
						id:"cname",
						itemCls:"required",
						allowBlank:false
					}]
				},{
					columnWidth:.25,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel:"联系人",
						name:"name",
						itemCls:"required",
						allowBlank:false
					}]
				},{
					columnWidth:.25,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"sex",
						hiddenId:"sex",
						editable: false,
						itemCls:"required",
						allowBlank:false,
						fieldLabel:"先生/女士",
						store:[
						 ["0","先生"],
						 ["1","女士"]
						]
					}]
				},{
					columnWidth:.25,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						name:"id",
						id:"logCid"
					},{
						xtype:"hidden",
						fieldLabel:"销售信息id",
						name:"saleCompId",
						id:"saleCompId"
					},{
						fieldLabel:"地址",
						name:"address",
						allowBlank:false,
						itemCls:"required"
					},{
					 	xtype: "combo",
		                fieldLabel:"行业",
		                hiddenName: "industryCode",
		                editable: false,
		                triggerAction: "all",
		                anchor: '99%',
		                store: new Ext.data.Store({
		                    autoLoad: true,
		                    url: Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["comp_industry"],
		                    reader: new Ext.data.JsonReader({
		                        fields: [
		                                 {name:"code",mapping:"value"},
		                                 {name:"name",mapping:"name"}
		                        ]
		                    }),
		                }),
		                displayField: "name",
		                valueField: "code"
					},{
					 	xtype: "combo",
		                fieldLabel:"业务类型",
		                hiddenName: "businessCode",
		                editable: false,
		                forceSelection: true,
		                triggerAction: "all",
		                anchor: '99%',
		                store: new Ext.data.Store({
		                    autoLoad: true,
		                    url : Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["company_industry_code"],
		                    reader: new Ext.data.JsonReader({
		                        fields: [{name:"name",mapping:"name"},
		 								{name:"code",mapping:"value"}]
		                    }),
		                }),
		                displayField: "name",
		                valueField: "code"
					},{
					 	xtype: "combo",
		                fieldLabel:"注册来源",
		                hiddenName: "registerCode",
		                editable: false,
		                forceSelection: true,
		                triggerAction: "all",
		                anchor: '99%',
		                readOnly:true,
		                store: new Ext.data.Store({
		                    autoLoad: true,
		                    url: Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["register_type"],
		                    reader: new Ext.data.JsonReader({
		                        fields: [
		                                 {name:"code3",mapping:"value"},
		                                 {name:"name3",mapping:"name"}
		                        ]
		                    }),
		                }),
		                displayField: "name3",
		                valueField: "code3"
					},{
						fieldLabel:"总登录次数",
						name:"loginCount",
						readOnly:true
					},{
						xtype:"checkbox",
						boxLabel:"已匹配",
						name:"match",
						inputValue:"1"
					}]
				},{
					columnWidth:.25,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel:"邮箱",
						name:"email",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"其他联系方式",
						name:"contact"
					},{
						xtype:"datefield",
						fieldLabel:"最近登录时间",
						name:"gmtLoginStr",
						id:"gmtLoginStr",
						format:"Y-m-d H:i:s",
						value:new Date(),
						readOnly:true
					},{
						xtype:"datefield",
						fieldLabel:"注册时间",
						name:"gmtRegisterStr",
						id:"gmtRegisterStr",
						format:"Y-m-d H:i:s",
						value:new Date(),
						readOnly:true
					},{
						fieldLabel:"职位",
						name:"position"
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"matchDegree",
						hiddenId:"matchDegree",
						editable: false,
						value:"0",
						fieldLabel:"匹配程度",
						store:[
						 ["1","紧急匹配"],
						 ["2","一般匹配"],
						 ["0","取消匹配"]
						]
					}]
				},{
					columnWidth:.25,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel:"手机",
						name:"mobile",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"电话",
						name:"phone",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"电话所在国家",
						name:"phoneCountry"
					},{
						fieldLabel:"电话所在地区",
						name:"phoneArea"
					},{
						fieldLabel:"传真所在国家",
						name:"faxCountry"
					},{
						fieldLabel:"传真所在地区",
						name:"faxArea"
					}]
				},{
					columnWidth:.25,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"mainBuy",
						hiddenId:"mainBuy",
						editable: false,
						itemCls:"required",
						allowBlank:false,
						fieldLabel:"采购商",
						store:[
						["1","采购商"],
						["0","非采购商"]
						]
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"mainSupply",
						hiddenId:"mainSupply",
						itemCls:"required",
						editable: false,
						allowBlank:false,
						fieldLabel:"供应商",
						store:[
						["1","供应商"],
						["0","非供应商"]
						]
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenId:"provinceCode",
						hiddenName:"provinceCode",
						fieldLabel:"省份",
						valueField:"code1",
						displayField:"name1",
						anchor : "99%",
						forceSelection : true,
						editable :false,
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url :Context.ROOT+"/combox/queryAreaCode.htm?parentCode="+COMBO.areaCode["parentCode"],
							fields:[{name:"name1",mapping:"name"},
									{name:"code1",mapping:"code"}]
						}),
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
						valueField:"code2",
						displayField:"name2",
						anchor : "99%",
						forceSelection : true,
						editable :false,
						emptyText :'请选择...',
						store:new Ext.data.JsonStore({
							autoLoad : false,
							url :Context.ROOT+"/combox/queryAreaCode.htm",
							fields:[{name:"name2",mapping:"name"},
									{name:"code2",mapping:"code"}]
						})
					},{
						fieldLabel:"邮编",
						name:"addressZip"
					},{
						fieldLabel:"传真",
						name:"fax"
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
						fieldLabel:"主营产品(采购)",
						name:"mainProductBuy",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"主营产品(供应)",
						name:"mainProductSupply",
						itemCls:"required",
						allowBlank:false
					},{
						xtype:"htmleditor",
						fieldLabel:"公司介绍",
						name:"details",
						allowBlank:false,
						itemCls:"required",
						height:100
					}]
				}],
				buttons:[{
					id:"see-service",
					text:"查看服务记录",
					iconCls:"find16",
					handler:function(btn){
					}
				},{
					id:"refresh",
					text:"查看广告使用",
					iconCls:"find16",
					handler:function(btn){
					}
				},{
					id:"see-note",
					text:"查看变更日志",
					iconCls:"find16",
					handler:function(btn){
						window.location=Context.ROOT+"/system/log/index.htm?id="+Ext.get("logCid").dom.value;
					}
				},{
					id:"see-supply",
					text:"查看供求信息",
					iconCls:"find16",
					handler:function(btn){
						window.location=Context.EPADMIN+"/crmlink/crmlinktradebuy/trade.htm?id="+Ext.get("cid").dom.value;
					}
				},{
					id:"see-message",
					text:"查看询盘",
					iconCls:"find16",
					handler:function(btn){
						window.location=Context.EPADMIN+"/crmlink/crmlinktradebuy/crmMessage.htm?id="+Ext.get("cid").dom.value;
					}
				},{
					id:"on-save",
					iconCls:"saveas16",
					text:"保存",
					scope:this,
					handler:function(){
						var url=Context.ROOT+"/csale/csmycompany/updateCrmCompany.htm";
						if(form.getForm().isValid()){
					    	form.getForm().submit({
				                url:url,
				                method:"post",
				                type:"json",
				                success:function(_form,_action){
									com.zz91.utils.Msg(MESSAGE.title, MESSAGE.saveSuccess);
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
				}]
			}]
		};
		com.zz91.ep.cscrm.Form.superclass.constructor.call(this,c);
	},
	loadCompany:function(companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields :com.zz91.ep.cscrm.FIELD,
			url : Context.ROOT+"/csale/cscompany/queryCompDetails.htm?id="+companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else { 
						form.getForm().loadRecord(record);
						Ext.get("areaCode").dom.value=record.get("areaCode");
						form.findById("gmtLoginStr").setValue(Ext.util.Format.date(new Date(record.get("gmtLogin").time), 'Y-m-d H:i:s'));
						form.findById("gmtRegisterStr").setValue(Ext.util.Format.date(new Date(record.get("gmtRegister").time), 'Y-m-d H:i:s'));
						Ext.getCmp("areaName").store.reload({
							params:{"parentCode":record.get("provinceCode")}
						});
					}
				}
			}
		});
	}
});

com.zz91.ep.cscrm.phoneNoteForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;

		var c={
				labelAlign : "right",
				labelWidth : 90,
				layout:"column",
				frame:true,
				items:[{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						name : "id",
						id : "id",
					},{
						xtype:"hidden",
						fieldLabel : "公司Id",
						name:"cid",
						id:"compId"
					},{
						xtype:"hidden",
						fieldLabel : "上次星级",
						name:"starOld",
						id:"starOld",
						value:"0"
					},{
						xtype:"hidden",
						fieldLabel : "通话类型",
						name:"callType",
						id:"callType",
						value:"0"
					},{
						xtype:"datefield",
						itemCls:"required",
						allowBlank:false,
						id:"date",
						fieldLabel:"下次联系时间",
						name:"gmtNextContactStr",
						id:"gmtNextContactStr",
						format:'Y-m-d'
					}]
				},{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"combo", //组件类型
						mode:"local", //下拉列表的读取模式，remote读取远程数据，local读取本地数据
						triggerAction:"all", //设置单击触发按钮时执行的默认操作，有效值包括all和query，默认为query，如果设置为all则会这行allQuery中设置的查询
						hiddenName:"nextContact", //隐藏字段的名字
						hiddenId:"nextContact", //隐藏的id
						value:"0", //初始化组合框中的值
						editable: false, //是否可编辑
						allowBlank:false,
						itemCls:"required",
						fieldLabel:"下次联系方式",//字段标签说明
						store:[
							["0","电话"],
							["1","邮件"],
							["2","短信"]
						]
					}]
				},{
					columnWidth:0.6,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"radiogroup",
						fieldLabel:"联系类型",
						allowBlank:false,
						itemCls:"required",
						blankText:"该项为必选项，请选择一项！",
						columns:6,
						items:[
						       {boxLabel:"增值/续签",name:"contactType",anchor:"100",inputValue:3,checked:true},
						       {boxLabel:"无",name:"contactType",anchor:"100",inputValue:0}
						]
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:"" //字段标签与字段之间的分隔符
					},//
					items:[{
						xtype:"radiogroup",
						fieldLabel:"联系情况",
						allowBlank:false,
						itemCls:"required",
						blankText:"该项为必选项，请选择一项！",
						columns:6,
						items:[{boxLabel:"有效联系",name:"situation",anchor:"100",inputValue:0},
						       {boxLabel:"无进展",name:"situation",anchor:"100",inputValue:1},
						       {boxLabel:"无人接听",name:"situation",anchor:"100",inputValue:2},
						       {boxLabel:"号码错误",name:"situation",anchor:"100",inputValue:3},
						       {boxLabel:"停机",name:"situation",anchor:"100",inputValue:4},
						       {boxLabel:"关机",name:"situation",anchor:"100",inputValue:5}]
					}]
				},{
					columnWidth:.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						labelSeparator:"",
						xtype:"textarea"
					},
					items:[{
							xtype:"combo",
							fieldLabel:"高会服务星级",
							mode:"local",
							triggerAction:"all",
							hiddenName:"serviceStar",
							hiddenId:"serviceStar",
							editable: false,
							allowBlank:false,
							itemCls:"required",
							value:"0",
							store:[
								["0",""],
								["1","一星"],
								["2","二星"],
								["3","三星"],
								["4","四星"],
								["5","五星"]
							]
						},{
							xtype:"radiogroup",
							id:"radio",
							columns:2,
							items:[
								{boxLabel:"拖单",name:"flag",anchor:"100",inputValue:0},
								{boxLabel:"毁单",name:"flag",anchor:"100",inputValue:1}
							]
						}]
				},{
					columnWidth:.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						labelSeparator:"",
						xtype:"textarea"
					},
					items:[{
						xtype:"combo",
						fieldLabel:"来源途径",
						mode:"local",
						triggerAction:"all",
						hiddenName:"source",
						hiddenId:"source",
						value:"0",
						store:[
					       ["0",""],
					       ["1","网络来源为主"],
					       ["2","线下老客户为主"],
					       ["3","网络线下两者都有"]
						]
					}]
				},{
					columnWidth:.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						labelSeparator:"",
						xtype:"textarea"
					},
					items:[{
							xtype:"combo",
							fieldLabel:"网络成熟度",
							mode:"local",
							triggerAction:"all",
							hiddenName:"maturity",
							hiddenId:"maturity",
							value:"0",
							store:[
							   ["0",""],
						       ["1","网络不太懂，未做过推广"],
						       ["2","了解网络但未推广"],
							   ["3","了解网络已在其它付费推广"],
							   ["4","有受过伤，未在推广"]
							]
						}]
				},{
					columnWidth:.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						labelSeparator:"",
						xtype:"textarea"
					},
					items:[{
						xtype:"combo",
						fieldLabel:"网络推广时间",
						mode:"local",
						triggerAction:"all",
						hiddenName:"promote",
						hiddenId:"promote",
						value:"0",
						store:[
							["0",""],
							["1","短期3个月内"],
							["2","长期（3个月以上or有计划未定时间）"],
							["3","无（直接拒绝）"],
							["4","有效果就合作"]
						]
					}]
				},{
					columnWidth:.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						labelSeparator:"",
						xtype:"textarea"
					},
					items:[{
						xtype:"combo",
						fieldLabel:"决策人",
						mode:"local",
						triggerAction:"all",
						hiddenName:"kp",
						hiddenId:"kp",
						value:"0",
						store:[
						   ["0",""],
					       ["1","KP"],
					       ["2","KP推动者"],
						   ["3","不是KP"],
						]
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"99%",
						labelSeparator:"",
						xtype:"textarea"
					},
					items:[{
						fieldLabel : "小记信息",
						name : "remark"
				}]
				}],
				buttons:[{
					text:"保存",
					scope:this,
					iconCls:"saveas16",
					handler:function(btn){
						if (Ext.getCmp("compId").getValue()==""){
							com.zz91.utils.Msg("","对空记录，操作无效！");
							return false;
						}
						var url=this.saveUrl;
						
						if (this.getForm().isValid()) {
							this.getForm().submit({
								url : url,
								method : "post",
								type:"json",
								success : function(_form,_action){
									com.zz91.utils.Msg("","信息已保存");
									Ext.getCmp(MYCOMP.SERVICE_NOTE_GRID).getStore().reload();
								},
								failure : function(_form,_action){
								}
							});
						} else {
							Ext.MessageBox.show({
								title:MESSAGE.title,
								msg : MESSAGE.unValidate,
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					}
				}]
			};
		
		com.zz91.ep.cscrm.phoneNoteForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+ "/csale/cscompany/createCrmLog.htm",
	initCidAndStar:function(cid){
		this.findById("compId").setValue(cid);
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		var _render=[{name:"serviceStar",mapping:"serviceStar"},
		             {name:"source",mapping:"source"},
		             {name:"maturity",mapping:"maturity"},
		             {name:"promote",mapping:"promote"},
		             {name:"kp",mapping:"kp"},
		             {name:"id",mapping:"id"}];
		form.store = new Ext.data.JsonStore({
			fields :_render,
			url : Context.ROOT+"/csale/cscompany/queryStarById.htm?id="+cid, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else { 
						form.getForm().loadRecord(record);
						Ext.getCmp("starOld").setValue(record.get("serviceStar"));
						if(record.get("star")==5){
							Ext.getCmp("radio").setVisible(true);
						}else{
							Ext.getCmp("radio").setVisible(false);
						}
					}
				}
			}
		});
	}
});

com.zz91.ep.cscrm.phoneNoteGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields: com.zz91.ep.cscrm.NOTEFIELD,
			url:Context.ROOT + "/csale/cscompany/queryCrmLog.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			dataIndex : "id",
			hidden:true
		},{
			header : "联系时间",
			dataIndex:"gmtCreated",
			width:150,
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		},{
			header : "下次联系时间",
			dataIndex:"gmtNextContact",
			width:150,
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		},{
			header : "客户星级",
			dataIndex:"star",
			width:100,
			sortable : true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				var name=record.get("saleName");
				if (name==null || name==""){
					returnvalue="";
				}
				return returnvalue;
			}
		},{
			header : "联系类型",
			dataIndex:"contactType",
			sortable : true,
			width:200,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = "";
				if(value==3) {
					returnvalue="增值/续签";
				}
				return returnvalue;
			}
		},{
			header : "联系情况",
			dataIndex:"situation",
			sortable : true,
			width:200,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				var name=record.get("remark");
				if(value==0) {
					if (name==null || name==""){
						returnvalue="";
					}else{
						returnvalue="有效联系";
					}
				}
				if(value==1) {
					returnvalue="无进展";
				}
				if(value==2) {
					returnvalue="无人接听";
				}
				if(value==3) {
					returnvalue="号码错误";
				}
				if(value==4) {
					returnvalue="停机";
				}
				if(value==5) {
					returnvalue="关机";
				}
				return returnvalue;
			}
		},{
			header : "联系内容",
			dataIndex:"remark",
			sortable : true,
			width:500,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var table="<table border='0' cellspacing='1' bgcolor='#7386a5' style='width:99%;white-space:normal;'>";
				if(typeof(value)!="undefined" && value!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>小记信息</td>";
					table=table+"<td bgcolor='#ffffff' style='font-size:15px;'>"+value+"</td></tr>";
				}
				table=table+"</table>";
				return table;
			}
		},{
			header : "销售",
			dataIndex : "saleName",
			sortable : false,
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			viewConfig: { 
	            forceFit:true, 
	            enableRowBody:true, 
	            showPreview:true, 
	            getRowClass : function(record, rowIndex, p, store){ 
	                var cls="sea-row";
	                if (record.get("saleName")==0){
	                	return cls;
	                }
	            } 
	        }
		};
		
		com.zz91.ep.cscrm.phoneNoteGrid.superclass.constructor.call(this,c);
	},
	loadNote:function(cid,callType){
		// 载入销售小记信息
		if (cid>0){
			this.getStore().reload({params:{"cid":cid,"callType":callType}});
		}
	}
});

com.zz91.ep.cscrm.addContactWin = function(cid){
	
	var form=new com.zz91.ep.cscrm.contactForm({
		id:MYCOMP.CONTACT_FORM,
		saveUrl:Context.ROOT+ "/csale/cscompany/createContact.htm",
		region:"center",
	});
	
	form.initCid(cid);
	
	var win = new Ext.Window({
			id:MYCOMP.CONTACT_WIN ,
			title:"添加联系人信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
};

com.zz91.ep.cscrm.contactForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			frame:true,
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					name : "id",
					id : "id",
					hidden:true
				},{
					xtype:"hidden",
					fieldLabel : "公司Id",
					name:"cid",
					id:"cid",
					allowBlank : false,
					itemCls :"required"
				},{
					fieldLabel:"联系人",
					name : "name",
					allowBlank : false,
					itemCls :"required"
				},{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenId:"sex",
					hiddenName:"sex",
					allowBlank:false,
					itemCls:"required",
					editable:false,
					fieldLabel:"性别",
					store:[
					["0","男"],
					["1","女"]
					]
				},{
					fieldLabel : "座机",
					name : "phone"
				},{
					fieldLabel : "座机所在国家",
					name : "phoneCountry"
				},{
					fieldLabel : "座机所在地区",
					name : "phoneArea"
				},{
					fieldLabel : "其他联系方式",
					name : "contact"
				},{
					xtype:"checkbox",
					name:"isKey",
					fieldLabel:"关键联系人",
					inputValue:1
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "手机",
					name : "mobile",
					id : "mobile",
					allowBlank : false,
					itemCls :"required"
				},{
					xtype:"textfield",
					fieldLabel : "邮箱",
					name : "email",
					allowBlank : false,
					itemCls :"required"
				},{
					fieldLabel : "传真",
					name : "fax"
				},{
					fieldLabel : "传真所在国家",
					name : "faxCountry",
				},{
					fieldLabel : "传真所在地区",
					name : "faxArea"
				},{
					fieldLabel : "职位",
					name : "position"
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
					fieldLabel : "备注",
					name : "remark"
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
								Ext.getCmp(MYCOMP.CONTACT_GRID).getStore().reload();
								Ext.getCmp(MYCOMP.CONTACT_WIN).close();
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
							msg : MESSAGE.unValidate,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp(MYCOMP.CONTACT_WIN).close();
				}
			}]
		};
		
		com.zz91.ep.cscrm.contactForm.superclass.constructor.call(this,c);
	},
	saveUrl:"",
	initCid:function(cid){
		this.findById("cid").setValue(cid);
	}
});

com.zz91.ep.cscrm.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields:com.zz91.ep.cscrm.CONTACTFIELD,
			url:Context.ROOT + "/csale/cscompany/queryContact.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			dataIndex : "id",
			hidden:true
		},{
			header : "公司Id",
			dataIndex : "cid",
			hidden:true
		},{
			header : "联系人",
			dataIndex : "name",
			sortable : true,
			width:70
		},{
			header : "性别",
			dataIndex:"sex",
			width:40,
			sortable : true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==0) {
					returnvalue="男";
				}
				if(value==1) {
					returnvalue="女";
				}
				return returnvalue;
			}
		},{
			header : "职位",
			dataIndex:"position",
			sortable : true,
			width:75
		},{
			header : "手机",
			dataIndex:"mobile",
			sortable : true,
			width:80,
		},{
			header : "座机",
			dataIndex:"phone",
			sortable : true,
			width:80
		},{
			header : "座机所在国家",
			dataIndex:"phone_country",
			sortable : true,
			width:85
		},{
			header : "座机所在地区",
			dataIndex:"phone_area",
			sortable : true,
			width:85
		},{
			header : "email",
			dataIndex:"email",
			sortable : true
		},{
			header : "传真",
			dataIndex:"fax",
			sortable : true,
			width:80
		},{
			header : "传真所在国家",
			dataIndex:"fax_country",
			sortable : true,
			width:85
		},{
			header : "传真所在地区",
			dataIndex:"fax_area",
			sortable : true,
			width:85
		},{
			header : "其他联系方式",
			dataIndex:"contact",
			sortable : true
		},{
			header : "关键联系人",
			dataIndex:"is_key",
			width:70,
			sortable : true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==0) {
					returnvalue="不是";
				}
				if(value==1) {
					returnvalue="是";
				}
				return returnvalue;
			}
		},{
			header : "追加者",
			dataIndex:"sale_name",
			sortable : true,
			width:50
		},{
			header : "追加时间",
			dataIndex:"gmt_created",
			width:130,
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		},{
			header : "备注",
			dataIndex:"remark",
			sortable : true,
			width:180
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				id:"add-contact",
				text : '新增联系人',
				tooltip : '新增联系人',
				iconCls : 'add16',
				handler : function(btn){
					com.zz91.ep.cscrm.addContactWin(Ext.get("logCid").dom.value);
				}
			}]
		};
		
		com.zz91.ep.cscrm.Grid.superclass.constructor.call(this,c);
	},
	loadContactGrid:function(id){
		// 载入联系人信息
		if(id>0){
			this.getStore().reload({params:{"cid":id}});
		}
	}
})