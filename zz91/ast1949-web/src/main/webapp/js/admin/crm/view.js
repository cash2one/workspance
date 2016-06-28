Ext.namespace("ast.ast1949.admin.crm")

var _C = new function(){
	this.RESULT_GRID = "resultgrid";
	this.SEARCH_FORM = "searchform";
	this.TAB_NAV	= "tabnav";
	this.QUERIED	= "";
	this.BASEURL=Context.ROOT+Context.PATH+'/admin/crm/list.htm';
	
	this.IN_SERVICETIME=""
	this.OUT_SERVICETIME=""
	this.SEARCH_MEMBERSHIP_TYPE_CODE="";
}

Ext.onReady(function() {
	_C.QUERIED=Ext.get("queried").dom.value;
	
	_C.IN_SERVICETIME=Ext.get("inServiceTime").dom.value;
	_C.OUT_SERVICETIME=Ext.get("outServiceTime").dom.value;
	_C.SEARCH_MEMBERSHIP_TYPE_CODE=Ext.get("searchMembershipTypeCode").dom.value;
	
	var strUrl="";
	if(_C.QUERIED.length>0){
		strUrl+="?queried="+_C.QUERIED;
	}
	if(_C.IN_SERVICETIME.length>0){
		strUrl+="&intime="+_C.IN_SERVICETIME;
	}
	if(_C.OUT_SERVICETIME.length>0){
		strUrl+="&outtime="+_C.OUT_SERVICETIME;
	}
	if(_C.SEARCH_MEMBERSHIP_TYPE_CODE.length>0){
		strUrl+="&searchMembershipTypeCode="+_C.SEARCH_MEMBERSHIP_TYPE_CODE;
	}
	
	var resultgrid = new ast.ast1949.admin.crm.ResultGrid({
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/crm/list.htm"+strUrl,
		region:'center',
		autoScroll:true
	});

	resultgrid.on("rowdblclick",function(grid,rowindex,e){
		var selid=grid.getStore().getAt(rowindex).get("companyId");
		var account=grid.getStore().getAt(rowindex).get("account");
		if(selid>0){
			window.open(Context.ROOT+Context.PATH+"/admin/crm/showDetail.htm?id="+selid+"&account="+account+"&st="+Math.random());
		}
	});

	var quicksearchtab = new ast.ast1949.admin.crm.QuickSearchTab({
		region:'north'
	});

	var searchForm = new ast.ast1949.admin.crm.SearchForm({
		collapsed :true,
		region:"east"
	});

	var viewport = new Ext.Viewport({
		layout:'border',
		items:[quicksearchtab,resultgrid,searchForm]
	});
});

// TODO 快速搜索导航
ast.ast1949.admin.crm.QuickSearchTab = Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		var resultgrid = Ext.getCmp(_C.RESULT_GRID);
		var c={
			activeTab: 0,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			items:[{
				title:'所有客户',
				html:'<i>所有客户信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true"};
						//定位到第一页
						resultgrid.store.reload({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:"我的客户",
				html:"<i>我的客户信息列表</i>",
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true","showOnlyMyself":"true"};
						//定位到第一页
						resultgrid.getStore().load({
							params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
						});
					}
				}
			},{
				title:'垃圾客户',
				html:"<i>垃圾客户信息列表</i>",
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true","showOnlyMyself":"true","show":"byclassifiedcode"};
						//定位到第一页
						resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
//				html:"<img src='"+Ext.BLANK_IMAGE_URL+"' />",
				title:'三个月内到期',
				html:'<i>三个月内到期的客户信息列表</i>',
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true","showOnlyMyself":"true","show":"willexpire","afterMonth":3};
						//定位到第一页
						resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'今天已联系',
				html:"<i>今天已联系的客户信息列表</i>",
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true","showOnlyMyself":"true","show":"daylinked"};
						//定位到第一页
						resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'一个月内未联系',
				html:"<i>一个月内未联系的客户信息列表</i>",
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true","showOnlyMyself":"true","show":"monthunlinked"};
						//定位到第一页
						resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				title:'黑名单客户',
				html:"<i>黑名单客户信息列表</i>",
				listeners:{
					activate:function(tab){
						resultgrid.store.baseParams={};
						resultgrid.store.baseParams = {"enabled":"true","allocated":"true","showOnlyMyself":"true","show":"blacklist"};
						//定位到第一页
						resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}
			}]
		};

		ast.ast1949.admin.crm.QuickSearchTab.superclass.constructor.call(this,c);

	}
});

// TODO 高级搜索表单
ast.ast1949.admin.crm.SearchForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var c={
			title:'高级搜索',
			width:300,
			collapsible:true,
			split:true,
	        width: 300,
	        minSize: 180,
	        maxSize: 350,
	        margins:'0 0 0 0',
	        labelAlign : "right",
			labelWidth : 50,
			layout:"form",
			frame:true,
			split:true,
			autoScroll:true,
			defaults:{
				anchor:"92%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[
				{fieldLabel:"Email"},
				{fieldLabel:"公司名称"},
				{fieldLabel:"联系人"},
				{fieldLabel:"手机"},
				{fieldLabel:"主营业务"},
				{fieldLabel:"行业"},
				{fieldLabel:"二级域名"},
				{fieldLabel:"用户名"},
				{fieldLabel:"地址"},
				{fieldLabel:"客服"},
				{fieldLabel:"电话类型"},
				{
					xtype:"checkbox",
					boxLabel:"未解决或解决中的客户"
				},{
					xtype:"checkbox",
					boxLabel:"短信允许"
				},{
					xtype:"checkbox",
					boxLabel:"隐藏客户名单"
				},
				{
					fieldLabel:"下次联系"
				},{
					xtype:"datefield",
					fieldLabel:"开通时间"
				},{
					xtype:"datefield",
					fieldLabel:"-"
				},{
					xtype:"datefield",
					fieldLabel:"到期时间"
				},{
					xtype:"datefield",
					fieldLabel:"-"
				},
				{fieldLabel:"单号"},
				{fieldLabel:"地区"},
				{fieldLabel:"星级"}
			],
			buttons:[{
				text:'搜索'
			}]
		};

		ast.ast1949.admin.crm.SearchForm.superclass.constructor.call(this,c);

	}
});

// TODO 搜索结果列表,带分页
ast.ast1949.admin.crm.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.crmRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});

		var _sm = new Ext.grid.CheckboxSelectionModel();
//		var expander = new Ext.grid.RowExpander({
//	        tpl : new Ext.Template(
//	            '<p><b>账号:</b> {account}</p>',
//	        	'<p><b>联系人:</b> {contact}</p>',
//	        	'<p><b>性别:</b> {contact}</p>',
//	        	'<p><b>职位:</b> {sex}</p>',
//	            '<p><b>电话号码:</b> {telephone}</p>',
//	            '<p><b>手机号码:</b> {mobile}</p>',
//	            '<p><b>传真:</b> {faxcode}</p>',
//	            '<p><b>邮箱:</b> {email}</p>',
//	            '<p><b>网址:</b> {website}</p>',
//	            '<p><b>邮编:</b> {zip}</p>',
//	            '<p><b>QQ:</b> {qq}</p>',
//	            '<p><b>MSN:</b> {msn}</p>'
//	        )
//	    });

		var _cm=new Ext.grid.ColumnModel([_sm,
		{
			header:"编号",
			sortable:false,
			dataIndex:"companyId",
			hidden:true
		},{
			header:"设置",
			sortable:false,
			dataIndex:"companyId",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					var cid=record.get("companyId");
					var sid=0;//record.get("adminUserId");
					var sname=record.get("adminRealName");
					var oid=0;//record.get("orderId");
					
					return "<a href='"+ Context.ROOT + Context.PATH +"/admin/crm/opens/apply.htm?oid="+oid
					+"&cid="+cid+"&sid="+sid+"&sname="+sname+"&st="+Math.random()+"' target='_blank'>设置</a>";
					
				}
				else{
					return "";
				}
			}
			
		},{
			header:"星级（等级）",
			sortable:false,
			dataIndex:"salesRank"
		},{
			header:"会员类型",
			sortable:false,
			dataIndex:"membershipLabel",
			renderer : function(value, metadata, record, rowIndex, colndex, store) {
				var val="";
				if(record.get("membershipCode")!="10051000"){
					val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
				}
				val= val + value;
				return val;
			}
		},{
			header:"公司名称",
			sortable:false,
			dataIndex:"companyName",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					///admin/admincompany/edit.htm?companyId=627461&account=mytest01&ts=0.3631719632539898
					return "<a href='"+Context.ROOT+Context.PATH+"/admin/admincompany/edit.htm?companyId="+record.get("companyId")
					+"&account="+record.get("account")
					+"&st="+Math.random()+"' target='_blank'>" + value + "</a>";
					
				}
				else{
					return "";
				}
			}
			
		},{
			header:"邮箱",
			sortable:false,
			dataIndex:"email"
		},{
			header:"电话",
			sortable:false,
			dataIndex:"tel"
		},{
			header:"手机",
			sortable:false,
			dataIndex:"mobile"
		},{
			header:"省市",
			sortable:false,
			dataIndex:"areaCode",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					
					return record.get("province")+""+record.get("city");
				}
				else{
					return "";
				}
			}
		},{
			header:"注册时间",
			sortable:false,
			dataIndex:"regtime",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},
		{
			header:"到期时间",
			sortable:false,
			dataIndex:"dateEnd",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},
		{
			header:"最后联系时间",
			sortable:false,
			dataIndex:"contactLastTime",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},{
			header:"服务记录",
			sortable:false,
			dataIndex:"companyId",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return "查看";
				}
				else{
					return "";
				}
			}
		},
		{
			header:"登录次数",
			sortable:false,
			dataIndex:"numLogin"
		},
		{
			header:"最近登录",
			sortable:false,
			dataIndex:"gmtLogin",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},{
			header:"登录商城",
			sortable:false,
			dataIndex:"companyId",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return "登录";
				}
				else{
					return "";
				}
			}
		},{
			header:"服务人员",
			sortable:false,
			dataIndex:"adminRealName"
		},{
			header:"销售人员",
			sortable:false,
			dataIndex:"adminRealName"		
		}
		
		]);

		var c={
			iconCls:"icon-grid",
			viewConfig:{
				// TODO 设置表格自适应
				autoFill:true
//				froceFit:true
			},
			store:_store,
//			sm:_sm,
			cm:_cm,
//			plugins: expander,
			tbar:this.mytoolbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			listeners : {
				"render" : this.buttonQuery
			}
		};

		ast.ast1949.admin.crm.ResultGrid.superclass.constructor.call(this,c);

//		this.plugins.on("beforeexpand",function(expand,record,body,rowindex){
//			// TODO AJAX获取详细的公司信息
//			// 判断一下是否已经有数据了
//			if(!record.get("isgetdata")){
//				$.ajax({
//				   type: "GET",
//				   url: Context.ROOT+Context.PATH+'/admin/crm/getTheFirstCompanyContactsByCompanyId.htm',
//				   data: "id="+record.get("id"),
//				   async: false,
//				   dataType:'json',
//				   success: function(msg){
//						if(msg.data){
//							record.set("account",msg.data.account);
//							record.set("contact",msg.data.contact);
//							record.set("position",msg.data.position);
//							record.set("sex",msg.data.sex=="1"?" 男":"女");
//							record.set("telephone",msg.data.telCountryCode+"-"+msg.data.telAreaCode+"-"+msg.data.tel);
//							record.set("faxcode",msg.data.faxCountryCode+"-"+msg.data.faxAreaCode+"-"+msg.data.fax);						
//							record.set("email",msg.data.email);
//							record.set("website",msg.data.website);
//							record.set("zip",msg.data.zip);
//							record.set("qq",msg.data.qq);
//							record.set("msn",msg.data.msn);
//						}
//						record.set("isgetdata",true);
//				   }
//				});
//			}
////			record.set("companyname","公司"+rowindex);
//		});

	},
	crmRecord:Ext.data.Record.create([
		{name:"companyId",mapping:"companyId"},
		{name:"companyName",mapping:"companyName"},
		{name:"membershipCode",mapping:"membershipCode"},
		{name:"membershipLabel",mapping:"membershipLabel"},
		{name:"salesRank",mapping:"salesRank"},
		{name:"servicesRank",mapping:"servicesRank"},
		{name:"email",mapping:"email"},
		{name:"telCountryCode",mapping:"telCountryCode"},
		{name:"telAreaCode",mapping:"telAreaCode"},
		{name:"tel",mapping:"tel"},
		{name:"mobile",mapping:"mobile"},
		{name:"areaCode",mapping:"areaCode"},
		{name:"regtime",mapping:"regtime"},
		{name:"dateEnd",mapping:"dateEnd"},
		{name:"contactLastTime",mapping:"contactLastTime"},
		{name:"numLogin",mapping:"numLogin"},
		{name:"gmtLogin",mapping:"gmtLogin"},
		{name:"province",mapping:"province"},
		{name:"city",mapping:"city"},
		{name:"adminRealName",mapping:"adminRealName"},
		{name:"account",mapping:"account"}
	]),
	// TODO 默认列表连接
	listUrl:Context.ROOT+Context.PATH+"/crm/listcrm.htm",
	mytoolbar:[{
				iconCls:"icon-grid",
				text:"展开全部",
				enableToggle :true,
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getStore().getCount();

					if(this.pressed){
						for (var i=0;i<row;i++){
							grid.plugins.expandRow(i);
						}
//						this.pressed=false;
					}else{
						for (var i=0;i<row;i++){
							grid.plugins.collapseRow(i);
						}
//						this.pressed=true;
					}
				}
			},"-",{
				text:"放入我的客户",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("companyId");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/assign.htm?rm="+Math.random()+"&uid="+Ext.get("companyAccessGrade").dom.value+"&cid="+_ids.join(','),
							method : "get",
							scope : this,
							callback : function(options,success,response){
								var a=Ext.decode(response.responseText);
								if(a.success){
									Ext.MessageBox.alert(Context.MSG_TITLE,"操作成功！");
									grid.getStore().reload();
								}else{
									Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
								}
							}
						});
					}
				}
			},{
				text:"放到公海",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("companyId");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							
							url: Context.ROOT+Context.PATH+ "/admin/crm/intoHighSeas.htm?rm="+Math.random()+"&ids="+_ids.join(','),
							method : "get",
							scope : this,
							callback : function(options,success,response){
								var a=Ext.decode(response.responseText);
								if(a.success){
									Ext.MessageBox.alert(Context.MSG_TITLE,"操作成功！");
									grid.getStore().reload();
								}else{
									Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
								}
							}
						});
					}
				}
			},{
				text:"设置为重点",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("companyId");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/setToFocus.htm?rm="+Math.random()+"&cid="+_ids.join(','),
							method : "get",
							scope : this,
							callback : function(options,success,response){
								var a=Ext.decode(response.responseText);
								if(a.success){
									Ext.MessageBox.alert(Context.MSG_TITLE,"操作成功！");
									grid.getStore().reload();
								}else{
									Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
								}
							}
						});
					}
				}
			},{
				text:"列为黑名单",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					} else if (row.length > 1){
						Ext.Msg.alert(Context.MST_TITLE,"最多只能选择一个客户！");
					} else {
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("companyId");
							_ids.push(_id);
						}
						/*
						 * ================  StatusBar Window example  =======================
						 */
						var win = new Ext.Window({
						    title: '设置黑名单',
						    width: 400,
						    minWidth: 350,
						    height: 150,
						    modal: true,
						    layout:"fit",
						    
						    items:[{
						    	xtype:"form",
						    	id:"blanklistFrom",
						    	frame:true,
						    	layout:"form",
						    	items:[{
						    		xtype:"hidden",
						    		name:"companyId",
						    		value:_ids
						    	},{
						    		xtype:"textarea",
						    		name:"reason",
						    		anchor : "99%",
						    		fieldLabel:"原因"
						    	}],
						    	buttons:[{
						    		text:"提交",
						    		handler:function(btn){
						    			/**/
						    			var form=Ext.getCmp("blanklistFrom");
						    			if(form.getForm().isValid()){
											var _url=Context.ROOT+Context.PATH+ "/admin/crm/setBlankList.htm?rm="+Math.random();//+"&id="+_ids.join(',');
												form.getForm().submit({
													url:_url,
													method:"post",
													success:doSuccess,
													failure:doFailure
												});
										} else {
											Ext.MessageBox.show({
												title:Context.MSG_TITLE,
												msg : "请输入原因！",
												buttons:Ext.MessageBox.OK,
												icon:Ext.MessageBox.ERROR
												});
										}
						    			
										function doSuccess(_form,_action){
											win.hide();
											Ext.MessageBox.show({
												title:Context.MSG_TITLE,
												msg : "更新成功",
												buttons:Ext.MessageBox.OK,
												icon:Ext.MessageBox.INFO
											});
											
										}
										
										function doFailure(_form,_action){
											Ext.MessageBox.show({
												title:Context.MSG_TITLE,
												msg : "更新失败",
												buttons:Ext.MessageBox.OK,
												icon:Ext.MessageBox.ERROR
											});
											
											ast.ast1949.admin.inquirySensitive.loadRecord(form);
										}
						    		}
						    	}]
						    }]
						});
						
						win.show();
					}
				}
			}],
	buttonQuery:function(){
		var tbar2 = new Ext.Toolbar({
			items : [new ast.ast1949.AdminUserCombo( {
				name : "adminuser_combo"
			}),{
				text:"分配客户",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var code=Ext.get("adminuser_combo").dom.value;
						if(code==null||code.length<=0)
						{
							Ext.Msg.alert(Context.MST_TITLE,"请选定一个客服！");
						} else {
							var _ids= new Array();
							for (var i=0,len = row.length;i<len;i++){
								var _id=row[i].get("companyId");
								_ids.push(_id);
							}
							/*提交*/
							var conn = new Ext.data.Connection();
							conn.request({
								url: Context.ROOT+Context.PATH+ "/admin/crm/assign.htm?rm="+Math.random()+"&uid="+code+"&cid="+_ids.join(','),
								method : "get",
								scope : this,
								callback : function(options,success,response){
									var a=Ext.decode(response.responseText);
									if(a.success){
										Ext.MessageBox.alert(Context.MSG_TITLE,"操作成功！");
										grid.getStore().reload();
									}else{
										Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
									}
								}
							});
						}
						
					}
				}
			},"-",new ast.ast1949.CategoryCombo( {
				categoryCode : Context.CATEGORY["companyAdminUser"],
				name : "companyAccessGrade"
			}),{
				text:"客户归类",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var code=Ext.get("companyAccessGrade").dom.value;
						if(code==null||code.length<=0)
						{
							Ext.Msg.alert(Context.MST_TITLE,"请选定一个客户类别！");
						} else {
						
							var _ids= new Array();
							for (var i=0,len = row.length;i<len;i++){
								var _id=row[i].get("companyId");
								_ids.push(_id);
							}
							/*提交*/
							var conn = new Ext.data.Connection();
							conn.request({
								url: Context.ROOT+Context.PATH+ "/admin/crm/classify.htm?rm="+Math.random()+"&code="+code+"&ids="+_ids.join(','),
								method : "get",
								scope : this,
								callback : function(options,success,response){
									var a=Ext.decode(response.responseText);
									if(a.success){
										Ext.MessageBox.alert(Context.MSG_TITLE,"操作成功！");
										grid.getStore().reload();
									}else{
										Ext.MessageBox.alert(Context.MSG_TITLE,"操作失败!");
									}
								}
							});
							
						}
						
					}
				}
			}]
		});
		tbar2.render(this.tbar);
	}
});