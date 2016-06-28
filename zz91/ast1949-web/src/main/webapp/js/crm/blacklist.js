Ext.namespace("ast.ast1949.crm.company");

ast.ast1949.crm.company.SEX=["男","女"];

ast.ast1949.crm.company.CSMAP={};

var ACCOUNT = new function(){
	this.ACCOUNT_GRID = "accountgrid";
}
ast.ast1949.crm.company.ACCOUNTFIELD=[
        {name:"id",mapping:"id"},
    	{name:"account",mapping:"account"},
    	{name:"companyId",mapping:"companyId"},
    	{name:"contact",mapping:"contact"},
    	{name:"isAdmin",mapping:"isAdmin"},
    	{name:"telCountryCode",mapping:"telCountryCode"},
    	{name:"telAreaCode",mapping:"telAreaCode"},
    	{name:"tel",mapping:"tel"},
    	{name:"mobile",mapping:"mobile"},
    	{name:"faxCountryCode",mapping:"faxCountryCode"},
    	{name:"faxAreaCode",mapping:"faxAreaCode"},
    	{name:"fax",mapping:"fax"},
    	{name:"email",mapping:"email"},
    	{name:"sex",mapping:"sex"},
    	{name:"position",mapping:"position"},
    	{name:"qq",mapping:"qq"},
    	{name:"msn",mapping:"msn"},
    	{name:"backEmail",mapping:"backEmail"},
    	{name:"isUseBackEmail",mapping:"isUseBackEmail"},
    	{name:"password",mapping:"password"},
    	{name:"numLogin",mapping:"numLogin"}
];
ast.ast1949.crm.company.COMPANYFIELD=[
		{name:"id",mapping:"company.id"},
		{name:"name",mapping:"company.name"},
		{name:"business",mapping:"company.business"},
		{name:"serviceCode",mapping:"company.serviceCode"},
		{name:"areaCode",mapping:"company.areaCode"},
		{name:"industryCode",mapping:"company.industryCode"},
		{name:"foreignCity",mapping:"company.foreignCity"},
		{name:"categoryGardenId",mapping:"company.categoryGardenId"},
		{name:"membershipCode",mapping:"company.membershipCode"},
		{name:"starSys",mapping:"company.starSys"},
		{name:"star",mapping:"company.star"},
		{name:"domainZz91",mapping:"company.domainZz91"},
		{name:"domain",mapping:"company.domain"},
		{name:"website",mapping:"company.website"},
		{name:"classifiedCode",mapping:"company.classifiedCode"},
		{name:"address",mapping:"company.address"},
		{name:"addressZip",mapping:"company.addressZip"},
		{name:"businessType",mapping:"company.businessType"},
		{name:"saleDetails",mapping:"company.saleDetails"},
		{name:"buyDetails",mapping:"company.buyDetails"},
		{name:"tags",mapping:"company.tags"},
		{name:"introduction",mapping:"company.introduction"},
		{name:"regfromCode",mapping:"company.regfromCode"},
		{name:"areaLabel", mpping:"areaLabel"},
		{name:"categoryGarden",mapping:"categoryGardenName"}                               
];

ast.ast1949.crm.company.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{
				header : "公司名称",
				width : 200,
				sortable : false,
				dataIndex : "name",
				renderer:function(value,metadata,record,rowIndex,colIndex,store){
					return "<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>登录</a> "+value
				}
			},{
				header : "登录帐号",
				width : 200,
				sortable : false,
				dataIndex : "account"
			},{
				header : "会员类型",
				width : 100,
				sortable : false,
				dataIndex : "membershipLabel",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")!="10051000"){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					}else{
						val="普通会员";
					}
					val= val + value;
					return val;
				}
			},{
				header : "手机",
				width : 100,
				sortable : false,
				dataIndex : "mobile"
			},{
				header : "注册时间",
				width : 130,
				sortable : false,
				dataIndex : "c.regtime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header : "电话",
				width : 100,
				sortable : false,
				dataIndex : "tel"
			},{
				header : "地区",
				width : 90,
				sortable : false,
				dataIndex : "areaName"
			}]);

		// 字段信息
		var reader = [ {name:"id",mapping:"company.id"},
		   {name:"name",mapping:"company.name"},
		   {name:"c.regtime",mapping:"company.regtime"},
		   {name:"mobile",mapping:"account.mobile"},	               
		   {name:"account",mapping:"account.account"},
		   {name:"membershipLabel",mapping:"membershipLabel"},
		   {name:"membershipCode",mapping:"company.membershipCode"}];

		var storeUrl = Context.ROOT + Context.PATH + "/crm/company/queryBlackList.htm";
		//var storeUrl = Context.ROOT + Context.PATH + "/crm/companyaccount/queryAccount.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [{
			text:"修改",
			iconCls:"edit",
			handler:function(){
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
				else
					var row = grid.getSelections();
					window.open(Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+row[0].get("id"));
			}
		},"-",{
			text:"客服小记",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				if(rows.length>1){
					//询问是否要一次打开全部客户信息
					Ext.MessageBox.confirm(Context.MSG_TITLE, "同时打开多个客户信息可能会造成浏览器假死<br />您确定要打开"+rows.length+"个客户信息吗？", function(btn){
				        if(btn != "yes"){
				                return false;
				        }
				        for(var i=0;i<rows.length;i++){
				        	var star = rows[i].get("star");
				        	if(star==null){
				        		star = "";
				        	}
				        	window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("id")+"&star="+star+"&companyName="+encodeURI(rows[i].get("name")));
				        }
				    });
				}else{
					for(var i=0;i<rows.length;i++){
						var star = rows[i].get("star");
						if(star==null||star.length<0){
							star = "";
						}
						window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+rows[i].get("id")+"&star="+star+"&companyName="+encodeURI(rows[i].get("name")));
					}
				}
			}
		},"-",{
			text:"取消黑名单",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				if(rows.length>0){
					Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要将选中的客户取消黑名单吗？",function(btn){
						if(btn != "yes"){
							return false;
						}
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url: Context.ROOT+Context.PATH+ "/crm/company/updateIsBlock.htm?isBlock=0",
								params:{
									"companyId":rows[i].get("id")
								},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										ast.ast1949.utils.Msg("","已成功取消黑名单");
										_store.reload();
									}else{
										ast.ast1949.utils.Msg("","操作失败");
									}
								},
								failure:function(response,opt){
									ast.ast1949.utils.Msg("","操作失败");
								}
							});
						}
					})
				}
			}	
		},"->","-",{
			xtype:"checkbox",
			boxLabel:"高会",
			id:"gaohui",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
					grid.searchByMembership();
				}
			}
   		},"-",{
   			xtype:"checkbox",
			boxLabel:"普会",
			id:"puhui",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
					grid.searchByMembership();
				}
			}
   		},"-",{
   			xtype:"checkbox",
			boxLabel:"百度优化",
			id:"baidu",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(ACCOUNT.ACCOUNT_GRID);
					grid.searchByCrmCompanyService();
				}
			}
   		}];

		var c={
			id:ACCOUNT.ACCOUNT_GRID,
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:7,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
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
		ast.ast1949.crm.company.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null,
	searchByMembership:function(){
		var _store = Ext.getCmp(ACCOUNT.ACCOUNT_GRID).getStore();
		var B = _store.baseParams;
		if(Ext.getCmp("gaohui").getValue()){
//			alert("gaohui");
			B["membershipCode"] = "10051001";
		}
		if(!Ext.getCmp("gaohui").getValue()&&Ext.getCmp("puhui").getValue()){
//			alert("puhui");
			B["membershipCode"] = "10051000";
		}
		if(!Ext.getCmp("gaohui").getValue()&&!Ext.getCmp("puhui").getValue()){
			B["membershipCode"] =  undefined;
		}
		_store.baseParams = B;
		_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByCrmCompanyService:function(){
		var _store = Ext.getCmp(ACCOUNT.ACCOUNT_GRID).getStore();
		var B = _store.baseParams;
		if(Ext.getCmp("baidu").getValue()){
			B["crmCode"] = "10001002";
		}else{
			B["crmCode"] = undefined;
		}
		_store.baseParams = B;
		_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	}
});

/**
 * 分配客户
 * */
ast.ast1949.crm.company.reassign=function(grid,oldassignArr,cs, csname){
	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
	if(oldassignArr.length<=0){
		return false;
	}
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这 <b>"+oldassignArr.length+"</b> 个客户分配给 <b>"+csname+"</b> 吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<oldassignArr.length;i++){
        	//TODO 分配客户
        	Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/crm/companyaccount/reassign.htm",
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

ast.ast1949.crm.company.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 80,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "公司名：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["name"] = undefined;
							}else{
								B["name"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "帐号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["account"] = undefined;
							}else{
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "手机号码：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["mobile"] = undefined;
							}else{
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "邮箱：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["email"] = undefined;
							}else{
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"黑名单原因：",
					displayField : "content",
					valueField : "id",
					id : "reason",
					store:new Ext.data.JsonStore( {
						fields : [ "content", "id" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
					}),
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue!="") {
								B["reason"] =  Ext.get("reason").dom.value;;
							}else{
								B["reason"] = null;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};

		ast.ast1949.crm.company.SearchForm.superclass.constructor.call(this,c);

	}
});

// 黑名单列表 点击出现
ast.ast1949.crm.company.blacklistGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm=new Ext.grid.ColumnModel([sm,{
			header:"公司ID",
			dataIndex:"target_id",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return value;
			}
		},{
			header:"原因备注",
			dataIndex:"remark",
			width:300,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return value;
				}
			}
		},{
			header:"操作日期",
			dataIndex:"gmt_created",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}
			}
		},{
			header:"操作人",
			dataIndex:"operator",
			renderer:function(value,metadata,record,rowindex,colindex,store){
				return value;
			}
		}]);
		
		var reader=[
			{name:"id",mapping:"id"},
			{name:"target_id",mapping:"targetId"},
			{name:"operator",mapping:"operator"},
			{name:"operation",mapping:"operation"},
			{name:"remark",mapping:"remark"},
			{name:"gmt_created",mapping:"gmtCreated"}
		];
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:reader,
			url:Context.ROOT + Context.PATH + "/log/logOperationForBlacklist.htm",
			autoLoad:false
		});
		
		var c={
			loadMask:Context.LOADMASK,
			sm:sm,
			cm:cm,
			store:_store,
			bbar: new Ext.PagingToolbar({
				pageSize : 5,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
//		var c={
//				loadMask:Context.LOADMASK,
//				sm : sm,
//				autoExpandColumn:7,
//				cm : cm,
//				iconCls : "icon-grid",
//				store:_store,
//				tbar : tbar,
//				bbar: new Ext.PagingToolbar({
//					pageSize : Context.PAGE_SIZE,
//					store : _store,
//					displayInfo: true,
//					displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
//					emptyMsg : '没有可显示的记录',
//					beforePageText : '第',
//					afterPageText : '页,共{0}页',
//					paramNames : {start:"startIndex",limit:"pageSize"}
//				}),
//				listeners:{
//					"rowcontextmenu":function(g,rowIndex,e){
//						if(!g.getSelectionModel().isSelected(rowIndex)){
//							g.getSelectionModel().clearSelections();
//							g.getSelectionModel().selectRow(rowIndex);
//						}
//						e.preventDefault();
//						if(g.contextmenu!=null){
//							g.contextmenu.showAt(e.getXY());
//						}
//					}
//				}
//			};
		
		ast.ast1949.crm.company.blacklistGrid.superclass.constructor.call(this,c);
	},
	loadByCompany:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
	}
});