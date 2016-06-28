Ext.namespace("ast.ast1949.phonecrm.phoneCs");

ast.ast1949.phonecrm.phoneCs.CSMAP={};

ast.ast1949.phonecrm.phoneCs.Fields=[
	{name:"company_id", mapping:"company.id"},
	{name:"starSys", mapping:"company.starSys"},
	{name:"star", mapping:"company.star"},
	{name:"numVisitMonth", mapping:"company.numVisitMonth"},
	{name:"gmt_visit", mapping:"company.gmtVisit"},
	{name:"domainZz91", mapping:"company.domainZz91"},
	{name:"companyName", mapping:"company.name"},
	{name:"gmt_end", mapping:"crmCompanySvr.gmtEnd"},
	{name:"num_login", mapping:"account.numLogin"},
	{name:"gmt_last_login", mapping:"account.gmtLastLogin"},
	{name:"mobile", mapping:"account.mobile"},
	{name:"account", mapping:"account.account"},
	{name:"email", mapping:"account.email"},
	{name:"gmt_next_visit_phone", mapping:"crmCs.gmtNextVisitPhone"},
	{name:"gmt_next_visit_email", mapping:"crmCs.gmtNextVisitEmail"},
	{name:"classifiedLabel", mapping:"classifiedLabel"},
	{name:"membershipLabel", mapping:"membershipLabel"},
	{name:"csAccount", mapping:"crmCs.csAccount"},
	{name:"areaLabel", mapping:"areaLabel"},
	{name:"areaProvinceLabel", mapping:"areaProvinceLabel"},
        {name:"pv", mapping:"pv"}
];

ast.ast1949.phonecrm.phoneCs.ACCOUNTFIELD=[
	{name:"authId",mapping:"id"},
	{name:"authAccount",mapping:"account"},
	{name:"authCompanyId",mapping:"companyId"},
	{name:"authUsername",mapping:"username"},
	{name:"authEmail",mapping:"email"}
];
ast.ast1949.phonecrm.phoneCs.REFRESHPRODUCTSFIELD = [
     {name : "id",mapping : "id"},
     {name : "companyId" , mapping : "companyId"},
     {name : "refreshDate" , mapping :  "refreshDate"},
     {name : "refreshRate" , mapping : "refreshRate"}
 ];
ast.ast1949.phonecrm.phoneCs.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/crm/cs/queryLdbCoreCompany.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phonecrm.phoneCs.Fields,
			url:_url,
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			width : 50,
			sortable : true,
			dataIndex : "company_id",
			id:"company_id",
			hidden:true
		},{
			header : "客服",
			width : 50,
			id:"csAccount",
			dataIndex : "csAccount",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.phonecrm.phoneCs.CSMAP[value] != "undefined"){
						return ast.ast1949.phonecrm.phoneCs.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		},{
			header : "VAP/ICD",
			width : 50,
			id:"ICDAccount",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
//				var sd = Ext.Ajax.request({
//					url: Context.ROOT+Context.PATH+"/crm/cs/getICDAccount.htm",
//					params:{"companyId":record.get("company_id")},
//					method: 'POST',
//					data:'json',
//					success: function (response) {
//						var jsonData = eval('('+response.responseText+')');
//						record.set("ICDAccount",jsonData.data);
//		            }
//		        });
//				return record.get("ICDAccount");
				return "<a href='http://bbs.asto.com.cn/getusername/?com_id="+record.get("company_id")+"' target='_blank'>查看</a>";
			}
		},{
			header : "客户类型",
			width:80,
			hidden:true,
			dataIndex : "classifiedLabel"
		},/*{
			header : "操作日志",
			width:80,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return "";
			}
		},*/{
			header : "客户等级",
			width:80,
			dataIndex : "membershipLabel"
		},/*{
			header : "星级(系统)",
			width:40,
			dataIndex : "starSys"
		},*/{
			header : "星级",
			width:40,
			dataIndex : "star"
		},{
			header : "本月联系",
			width:80,
			dataIndex : "numVisitMonth"
		},{
			header : "公司名称",
			width:250,
			dataIndex : "companyName",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				return "<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>登录</a> "+value
			}
		},{
			header:"账户信息",
			dataIndex:"account",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return value+"，"+record.get("email");
			}
		},{ 
			header : "服务到期时间",
			width:100,
			sortable : true,
			dataIndex : "gmt_end",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},{
			header : "pv",
			width:80,
			dataIndex : "pv"
                
		},{ 
			header : "天数",
			width:60,
			dataIndex : "gmt_end",
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					var endDate = Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					var nowDate = Ext.util.Format.date(new Date(), 'Y-m-d');
					return (new Date(endDate).getTime()-new Date(nowDate).getTime())/(24*60*60*1000)+"天 ";
				}else{
					return "";
				}
			}
		},{
			header : "登录次数",
			width:80,
			sortable : true,
			dataIndex : "num_login"
		},{
			header : "最后登录时间",
			dataIndex : "gmt_last_login",
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "手机",
			dataIndex : "mobile"
		},{
			header : "省份",
			dataIndex : "areaLabel",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return record.get("areaProvinceLabel")+" "+value;
			}
		}
//		,{//用户开通的服务信息期限信息，只显示未过期的服务信息
//			header : "付费期及剩余天数",
//			dataIndex : "gmtStart"
//		}
		,{
			header : "最后联系时间",
			dataIndex : "gmt_visit",
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},{
			header : "下次电话时间",
			sortable : true,
			dataIndex : "gmt_next_visit_phone",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},/*{
			header : "下次邮件时间",
			sortable : true,
			dataIndex : "gmt_next_visit_email",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},*/{
			header : "域名",
			dataIndex : "domainZz91"
		}]);
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				text:"客户信息",
				iconCls:"item-open",
				handler:function(btn){
					var rows=grid.getSelectionModel().getSelections();
					ast.ast1949.phonecrm.phoneCs.showInfo(rows);
				}
			},{
				text:"操作",
				iconCls : 'edit',
				menu:[{
					text:"修改用户名(仅限主管、仅限一次)",
					iconCls:"item-info",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelected();
						ast.ast1949.phonecrm.phoneCs.updateCompanyAccount(rows.get("company_id"));
					}
				},{
					text:"开通400",
					iconCls:"add",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelected();
						ast.ast1949.phone.updatePhoneInfo(0,rows.get("company_id"))
					}
				},{
					text : "自动刷新时间设置",
					iconCls : "edit",
					handler : function (btn) {
						var rows=grid.getSelectionModel().getSelected();
						ast.ast1949.phonecrm.phoneCs.productsAutoRefresh(rows.get("company_id"));
					}
				}
				]
			},{
				text:"发送短信",
				iconCls:"item-info",
				handler:function(btn){
					var rows=grid.getSelectionModel().getSelections();
					var ids=new Array();
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].get("mobile"));
						rows[i].set("companyName",rows[i].get("companyName")+"<b style='color:red'>已发送</b>");
					}
					if(ids.length<=0){
						ast.ast1949.utils.Msg("请先选择一个客户");
						return false;
					}
					ast.ast1949.phonecrm.phoneCs.sendSMS(ids)
				}
			}
//			,{
//				text:"放入抽奖箱",
//				iconCls:"add",
//				handler:function(btn){
//					var rows=grid.getSelectionModel().getSelected();
//					window.open(Context.ROOT + Context.PATH+ "/crm/cs/addToLottery.htm?companyId="+rows.get("company_id"));
//				}
//			}
//			,{
//				xtype:"combo",
//				lazyInit:true,
//				mode:"local",
//				triggerAction:"all",
//				lazyRender:true,
//				store:new Ext.data.SimpleStore({
//					fields:['k','v'],
//					data:[[1,'我的'],[2,'公海']]
//				}),
//				valueField:"k",
//				displayField:"v",
//				id:"csFlag",
//				listeners:{
//					"blur":function(field){
//						if(Ext.get("csFlag").dom.value==""){
//							field.setValue("");
//						}
//						var B=_store.baseParams;
//						B["csFlag"]=field.getValue();
//						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//					}
//				}
//			}
			],
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
			listeners:{
				"rowdblclick":function(g, rowIndex, e){
					var row=grid.getSelectionModel().getSelected();
					if(typeof(row)=="object"){
						window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+row.get("company_id")+"&star="+row.get("star")+"&companyName="+encodeURI(row.get("companyName")));
					}
				},
				"rowcontextmenu":function(g,rowIndex,e){
					if(!g.getSelectionModel().isSelected(rowIndex)){
						g.getSelectionModel().clearSelections();
						g.getSelectionModel().selectRow(rowIndex);
					}
					e.preventDefault();
					if(g.contextmenu!=null){
						g.contextmenu.showAt(e.getXY());
					}
				}
			}
		};
		
		ast.ast1949.phonecrm.phoneCs.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null
});

/**
 * 重新分配客户信息
 * */
ast.ast1949.phonecrm.phoneCs.reassign=function(grid, oldassignArr, cs, csname){
	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
	if(oldassignArr.length<=0){
		return false;
	}
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这 <b>"+oldassignArr.length+"</b> 个客户分配给 <b>"+csname+"</b> 吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<oldassignArr.length;i++){
        	
        	Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/crm/cs/reassign.htm",
				params:{
					"companyId":oldassignArr[i].companyId,
					"oldCsAccount":oldassignArr[i].csAccount,
					"csAccount":cs
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","已重新分配");
						grid.getStore().reload();
					}else{
						
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","发生错误，客户没有被分配");
				}
			});
        }
    });
};

/**
 * 放置废品池
 * */
ast.ast1949.phonecrm.phoneCs.updateOutbusiness=function(grid, companyId,type){
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这个客户放置到废品池里吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
    	Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/crm/cs/updateOutbusiness.htm",
			params:{
				"companyId":companyId,
				"type":type
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","操作成功");
					grid.getStore().reload();
				}else{
					
				}
			},
			failure:function(response,opt){
				ast.ast1949.utils.Msg("","发生错误，客户没有被分配");
			}
		});
    });
};

/**
 * 从公海捞客户
 * */
ast.ast1949.phonecrm.phoneCs.reassignByHighSea=function(grid, oldassignArr, cs, csname){
	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
	if(oldassignArr.length<=0){
		return false;
	}
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这 <b>"+oldassignArr.length+"</b> 个客户分配到自己的客户库里吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<oldassignArr.length;i++){
        	if(oldassignArr[i].csAccount!=""){
        		alert("该客户("+oldassignArr[i].companyId+")目前仍在他人"+oldassignArr[i].csAccount+"库中");
        		continue;
        	}
        	Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/crm/cs/reassign.htm",
				params:{
					"companyId":oldassignArr[i].companyId,
					"oldCsAccount":oldassignArr[i].csAccount,
					"csAccount":cs
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","已重新分配");
						grid.getStore().reload();
					}else{
						
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","发生错误，客户没有被分配");
				}
			});
        }
    });
};

/**
 * 丢公海
 * */
ast.ast1949.phonecrm.phoneCs.dropUser=function(grid, ids){
	//一次可以丢一组用户，但需要最后确认
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将选中的"+ids.length+"个客户丢到公海吗？<br />注意：只有自己的客户才可以被丢到公海", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<ids.length;i++){
        	//TODO 丢公海
        	Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/crm/cs/dropUser.htm",
				params:{
					"companyId":ids[i]
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","已丢公海");
						grid.getStore().reload();
					}else{
						//可能是非自己的客户，不能被丢公海
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","发生错误，信息没有被丢公海");
				}
			});
        }
    });
}

/**
 * 发送短信
 * */
ast.ast1949.phonecrm.phoneCs.sendSMS=function(ids){
	//一次可以丢一组用户，但需要最后确认
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将选中的"+ids.length+"个客户发送短信吗？<br />", function(btn){
        if(btn != "yes"){
        	return false;
        }
        // 组装各个电话号码 以 ‘,’ 为分隔
        var mobiles = "";
        for(var i=0;i<ids.length;i++){
        	mobiles += ids[i]+",";
        }
        var form = new ast.ast1949.phonecrm.phoneCs.SMSForm({
    		id:"SMS_FORM",
    		region:"center"
    	});
    	
    	var win = new Ext.Window({
    		id:"SMS_WIN",
    		title:"发送短信",
    		width:"70%",
    		modal:true,
    		items:[form]
    	});
    	form.loadRecords(mobiles);
    	win.show();
    	
    });
}

ast.ast1949.phonecrm.phoneCs.SMSForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _this=this;
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[
				{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
//						disabled:"true",
						id:"mobiles",
						name:"mobiles",
						fieldLabel:"<b>客户电话</b>",
						size:20
					},{
						xtype:"textarea",
						name:"content",
						fieldLabel:"短信内容<br/>PS:一条短信字数<br/>最多不超过60个",
						allowBlank:true,
						enableKeyEvents: true,
						listeners: {
							keyup: function(src, evt){
								var length = 60 - src.getValue().length+"个";
								_this.findById("totalLength").setValue(length);
							}
						}
					},{
						readOnly:true,
						id:"totalLength",
						fieldLabel:"剩余发送文字"
					}]
				}
			],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp("SMS_WIN").close();
				},
				scope:this
			}
			]
		};
		
		ast.ast1949.phonecrm.phoneCs.SMSForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(mobiles){
	var _fields=[
			{name:"mobiles",mapping:"mobiles"},
			{name:"content",mapping:"content"},
		];
		var form = this;
		form.findById("mobiles").setValue(mobiles);
	},
	
	saveUrl:Context.ROOT+Context.PATH + "/admin/feedback/sms.htm",
	save:function(){
		var _url = this.saveUrl;
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","发送成功！");
		Ext.getCmp("SMS_WIN").close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","发送失败！");
	}
});

/**
 * 显示客户信息，在新页面打开
 * */
ast.ast1949.phonecrm.phoneCs.showInfo=function(rows){
	if(rows.length>1){
		//询问是否要一次打开全部客户信息
		Ext.MessageBox.confirm(Context.MSG_TITLE, "同时打开多个客户信息可能会造成浏览器假死<br />您确定要打开"+rows.length+"个客户信息吗？", function(btn){
	        if(btn != "yes"){
	                return false;
	        }
	        for(var i=0;i<rows.length;i++){
	        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("company_id")+"&star="+rows[i].get("star")+"&companyName="+encodeURI(rows[i].get("companyName")));
	        }
	    });
	}else{
		for(var i=0;i<rows.length;i++){
        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("company_id")+"&star="+rows[i].get("star")+"&companyName="+encodeURI(rows[i].get("companyName")));
        }
	}
}

ast.ast1949.phonecrm.phoneCs.SearchForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			labelWidth : 80,
			frame:true,
			layout:"form",
			defaults:{
				anchor:"100%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				id : "contact",
				name : "contact",
				fieldLabel : "联系人"
			},{
				id : "email",
				name : "email",
				fieldLabel : "email"
			},{
				id : "companyName",
				name : "companyName",
				fieldLabel : "公司名称"
			},{
				id : "mobile",
				name : "mobile",
				fieldLabel : "手机"
			},{
				xtype:"datefield",
				id : "svrEndFromStr",
				name : "svrEndFromStr",
				format:"Y-m-d",
				fieldLabel : "到期时间(始)"
			},{
				xtype:"datefield",
				id : "svrEndToStr",
				name:"svrEndToStr",
				format:"Y-m-d",
				fieldLabel: "到期时间(末)"
			},{
				xtype:"datefield",
				id : "nextVisitPhoneFromStr",
				name:"nextVisitPhoneFromStr",
				format:"Y-m-d",
				fieldLabel: "下次联系时间(始)"
			},{
				xtype:"datefield",
				id : "nextVisitPhoneToStr",
				name:"nextVisitPhoneToStr",
				format:"Y-m-d",
				fieldLabel: "下次联系时间(末)"
			},{
				xtype:"combotree",
				fieldLabel:"地区",
				id : "areaLabel",
				hiddenName:"areaCode",
				hiddenId:"areaCode",
				editable:true,
				tree:new Ext.tree.TreePanel({
					loader: new Ext.tree.TreeLoader({
						root : "records",
						autoLoad: false,
						url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
						listeners:{
							beforeload:function(treeload,node){
								this.baseParams["parentCode"] = node.attributes["data"];
							}
						}
					}),
			   	 	root : new Ext.tree.AsyncTreeNode({text:'全部省市',data:Context.CATEGORY.areaCode})
				}),
				listeners:{
					"blur":function(field){
						if(Ext.get("areaLabel").dom.value==""){
							Ext.get("areaCode").dom.value="";
						}
					}
				}
			},{
				xtype:"combo",
				triggerAction : "all",
				forceSelection : true,
				fieldLabel:"主营行业",
				displayField : "label",
				valueField : "code",
				hiddenId:"industryCode",
				hiddenName:"industryCode",
				id:"industry",
				store:new Ext.data.JsonStore( {
					root : "records",
					fields : [ "label", "code" ],
					autoLoad:true,
					url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
				}),
				listeners:{
					"blur":function(field){
						if(Ext.get("industry").dom.value==""){
							field.setValue("");
						}
					}
				}
			}],
			buttons:[{
				text:"按查询条件搜索",
				handler:function(btn){
					var store=Ext.getCmp(config.targetGrid).getStore();
					var B=store.baseParams;
					B=B||{};
					B["contcat"]=form.findById("contact").getValue();
					B["email"]=form.findById("email").getValue();
					B["companyName"]=form.findById("companyName").getValue();
					B["mobile"]=form.findById("mobile").getValue();
					B["svrEndFromStr"]=form.findById("svrEndFromStr").getValue();
					B["svrEndToStr"]=form.findById("svrEndToStr").getValue();
					B["nextVisitPhoneFromStr"]=form.findById("nextVisitPhoneFromStr").getValue();
					B["nextVisitPhoneToStr"]=form.findById("nextVisitPhoneToStr").getValue();
//					B["csAccount"]=form.findById("cs").getValue();
					B["areaCode"]=Ext.get("areaCode").dom.value;
					B["industryCode"]=form.findById("industry").getValue();
					store.baseParams=B;
					store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};
		
		ast.ast1949.phonecrm.phoneCs.SearchForm.superclass.constructor.call(this,c);
	},
	initForm:null
});

//用户名信息修改
ast.ast1949.phonecrm.phoneCs.updateCompanyAccount=function(companyId){
	var form=new ast.ast1949.phonecrm.phoneCs.UpdateCompanyAccountForm({
	});
	
	form.loadCompanyAccount(companyId);
	
	var win = new Ext.Window({
		id:"updateaccountwin",
		title:"账户信息",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.phonecrm.phoneCs.UpdateCompanyAccountForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				fieldLabel:"账户",
				name:"authUsername",
				id:"authUsername",
				readOnly:true
			},{
				xtype:"hidden",
				name:"authId",
				id:"authId"
			},{
				xtype:"hidden",
				name:"authCompanyId",
				id:"authCompanyId"
			},{
				fieldLabel:"用户名",
				name:"authAccount",
				id:"authAccount"
			},{
				fieldLabel:"email",
				name:"authEmail",
				id:"authEmail",
				readOnly:true
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp(ast.ast1949.phonecrm.phoneCs.ACCOUNTFIELD);
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/crm/cs/updateAuthUser.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功!");
								Ext.getCmp("updateaccountwin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","修改信息失败");
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
		};
		
		ast.ast1949.phonecrm.phoneCs.UpdateCompanyAccountForm.superclass.constructor.call(this,c);
	},
	loadCompanyAccount:function(companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.phonecrm.phoneCs.ACCOUNTFIELD,
			url : Context.ROOT+Context.PATH+"/crm/cs/queryAuthUser.htm?companyId="+companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (s == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});

//自动刷新供求设置
ast.ast1949.phonecrm.phoneCs.productsAutoRefresh=function(companyId){
	var form=new ast.ast1949.phonecrm.phoneCs.productsAutoRefreshForm({
	});
	
	form.loadRefreshDate(companyId);
	
	var win = new Ext.Window({
		id:"setRefreshDatewin",
		title:"自动刷新设置信息",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}
ast.ast1949.phonecrm.phoneCs.productsAutoRefreshForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var form = this;
		
		var c = {
				labelAlign : "right",
				layout : "form",
				frame : true,
				defaults : {
					anchor : "95%",
					xtype : "textfield",
					autoHeight : true, 
					labelSeparator : ""
				},
				items : [{
					xtype:"hidden",
					name:"id",
					id:"id"
				},{
					xtype:"hidden",
					id : "companyId",
					name : "companyId"
				},{
					 xtype: 'radiogroup',
					 fieldLabel:'刷新频率', 
					 name:"refreshRate" ,
			        columns: 2,
			        vertical: true,
					items:[{ 
					boxLabel:"1天", 
					name:"refreshRate" ,
					inputValue : "1"
					},{ 
					boxLabel:"2天", 
					name:"refreshRate" ,
					inputValue : "2"
					},{ 
						boxLabel:"3天", 
						name:"refreshRate" ,
						inputValue : "3"
					},{ 
						boxLabel:"7天", 
						name:"refreshRate" ,
						inputValue : "7"
					},{ 
							boxLabel:"一个月", 
							name:"refreshRate" ,
							inputValue : "30"
						}] 
				},{
					id : "refreshDate",
					name : "refreshDate",
					fieldLabel: '刷新日期',  
				}],
				buttons : [{
					text : "保存",
					iconCls:"item-true",
					handler : function (btn) {
						if(form.getForm().isValid()){
							var grid = Ext.getCmp(ast.ast1949.phonecrm.phoneCs.REFRESHPRODUCTSFIELD);
							form.getForm().submit({
								url : Context.ROOT + Context.PATH + "/crm/cs/insertOrUpdate.htm",
								method : "post",
								type : "json",
								success : function () {
									ast.ast1949.utils.Msg("","保存成功!");
									Ext.getCmp("setRefreshDatewin").close();
									grid.getStore().reload();	
								},
								failure:function(){
									ast.ast1949.utils.Msg("","设置刷新时间信息失败");
								}
							});
						} else {
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "验证未通过",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					}
				},{
						text:"关闭",
						iconCls:"item-false",
						handler:function(btn){
							form.updateEmptyDate(Ext.getCmp("id").getValue());
						}
				}]
				
		};
		ast.ast1949.phonecrm.phoneCs.productsAutoRefreshForm.superclass.constructor.call(this,c);
	},
	loadRefreshDate:function(companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.findById("companyId").setValue(companyId);
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.phonecrm.phoneCs.REFRESHPRODUCTSFIELD,
			url : Context.ROOT+Context.PATH+"/crm/cs/init.htm?companyId="+companyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (s == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	},
	updateEmptyDate:function(id){
		if (id != null && id != "" ) {
			Ext.Msg.confirm('提示信息','你确定要关闭刷新吗?关闭后该客户的供求信息将停止自动刷新！',function(btn){ 
		         if(btn == 'yes') {
					Ext.Ajax.request({
						url: Context.ROOT+Context.PATH+ "/crm/cs/closeRefreshDate.htm",
						params:{
							"id":id
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								var grid = Ext.getCmp(ast.ast1949.phonecrm.phoneCs.REFRESHPRODUCTSFIELD);
								ast.ast1949.utils.Msg("","关闭刷新操作成功！");
								Ext.getCmp("setRefreshDatewin").close();
								grid.getStore().reload();	
							}else{
								ast.ast1949.utils.Msg("","关闭刷新操作失败！");
							}
						},
						failure:function(response,opt){
							ast.ast1949.utils.Msg("","操作失败");
						}
					});
		         }
			});
		} else {
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "该客户没有设置过自动刷新时间 ，无法做关闭操作！",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	}
});
