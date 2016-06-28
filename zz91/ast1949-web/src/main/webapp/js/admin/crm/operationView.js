Ext.namespace("ast.ast1949.admin.crm.operationView")

var _C=new function(){
	this.RESULT_GRID = "resultgrid";
	this.QUERIED="";
}

Ext.onReady(function(){
	
	_C.QUERIED=Ext.get("queried").dom.value;
	
	var strUrl="";
	if(_C.QUERIED.length>0){
		strUrl+="?queried="+_C.QUERIED;
	}
	
	var resultgrid = new ast.ast1949.admin.crm.operationView.ResultGrid({
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/crm/query.htm"+strUrl,
		region:'center',
		autoScroll:true
	});

	//	resultgrid.on("rowdblclick",function(grid,rowindex,e){
//		var selid=grid.getStore().getAt(rowindex).get("id");
//		if(selid>0){
//			window.open(Context.ROOT+Context.PATH+"/admin/crm/showDetail.htm?id="+selid);
//		}
//	});
	
	var quicksearchtab = new ast.ast1949.admin.crm.operationView.QuickSearchTab({
		region:'north'
	});

	var searchForm = new ast.ast1949.admin.crm.operationView.SearchForm({
		collapsed :true,
		region:"east"
	});

	var viewport = new Ext.Viewport({
		layout:"border",
		items:[quicksearchtab,resultgrid,searchForm]
	});
});

//快速搜索导航
ast.ast1949.admin.crm.operationView.QuickSearchTab=Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		var resultgrid=Ext.getCmp(_C.RESULT_GRID);
		var c={
			activeTab: 0,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			items:[
				{
					title:'所有客户',
					html:'<i>所有客户信息列表</i>',
					listeners:{
						activate:function(tab){
							resultgrid.store.baseParams = {};
							//定位到第一页
							resultgrid.store.reload({
									params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
								});
							}
					}
				},{
					title:"我的客户",
					html:"",
					listeners:{
						activate:function(tab){
							resultgrid.store.baseParams = {"show":"me"};
							//定位到第一页
							resultgrid.store.reload({
									params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
								});
						}
					}
				}]
		};
		
		ast.ast1949.admin.crm.operationView.QuickSearchTab.superclass.constructor.call(this,c);
	}
});

//搜索表单
ast.ast1949.admin.crm.operationView.SearchForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		var config =config || {};
		Ext.apply(this,config);
		
		var c={
			title:'客户筛选',
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
			items:[{fieldLabel:"公司名称"}],
			buttons:[{text:"GO"}]
		};
		
		ast.ast1949.admin.crm.operationView.SearchForm.superclass.constructor.call(this,c);
	}
});

//Grid
ast.ast1949.admin.crm.operationView.ResultGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config =config || {};
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
		
		var expander = new Ext.grid.RowExpander({
	        tpl : new Ext.Template(
	            '<p><b>账号:</b> {account}</p>',
	        	'<p><b>联系人:</b> {contact}</p>',
	        	'<p><b>性别:</b> {contact}</p>',
	        	'<p><b>职位:</b> {sex}</p>',
	            '<p><b>电话号码:</b> {telephone}</p>',
	            '<p><b>手机号码:</b> {mobile}</p>',
	            '<p><b>传真:</b> {faxcode}</p>',
	            '<p><b>邮箱:</b> {email}</p>',
	            '<p><b>网址:</b> {website}</p>',
	            '<p><b>邮编:</b> {zip}</p>',
	            '<p><b>QQ:</b> {qq}</p>',
	            '<p><b>MSN:</b> {msn}</p>'
	        )
	    });
	    
	    var _cm=new Ext.grid.ColumnModel([expander,{
			header:'编号',
			sortable:false,
			dataIndex:'id'
		},{
			header:'公司名',
			sortable:false,
			dataIndex:'name',
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
					
					return "<a href='show.htm?id="+record.get("id")+"' target='_blank'>" + value + "</a>";
       			}
		},{
			header:'注册时间',
			sortable:false,
			dataIndex:'regtime',
			renderer : function (value,metadata,record,rowIndex,colIndex,store){
					if(value!=null&&value.time.length>0){
						return Ext.util.Format.date(new Date(value.time),'Y-m-d');
					} else {
						return "";
					}
					//return Ext.util.Format.date(new Date(value.time),'Y-m-d');
       			}
		}]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
//			sm:_sm,
			cm:_cm,
			plugins: expander,
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
		
		ast.ast1949.admin.crm.operationView.ResultGrid.superclass.constructor.call(this,c);
		
		this.plugins.on("beforeexpand",function(expand,record,body,rowindex){
			// TODO AJAX获取详细的公司信息
			if(!record.get("isgetdata")){
				$.ajax({
				   type: "GET",
				   url: Context.ROOT+Context.PATH+'/admin/crm/getTheFirstCompanyContactsByCompanyId.htm',
				   data: "id="+record.get("id"),
				   async: false,
				   dataType:'json',
				   success: function(msg){
						if(msg.data){
							record.set("account",msg.data.account);
							record.set("contact",msg.data.contact);
							record.set("position",msg.data.position);
							record.set("sex",msg.data.sex=="1"?" 男":"女");
							record.set("telephone",msg.data.telCountryCode+"-"+msg.data.telAreaCode+"-"+msg.data.tel);
							record.set("faxcode",msg.data.faxCountryCode+"-"+msg.data.faxAreaCode+"-"+msg.data.fax);						
							record.set("email",msg.data.email);
							record.set("website",msg.data.website);
							record.set("zip",msg.data.zip);
							record.set("qq",msg.data.qq);
							record.set("msn",msg.data.msn);
						}
						record.set("isgetdata",true);
				   }
				});
			}
//			record.set("companyname","公司"+rowindex);
		});
	},
	crmRecord:Ext.data.Record.create([{
		name: 'id',
		mapping:'company.id',
		type: 'int'
	},{
		name: 'name',
		mapping:'company.name',
		type: 'string'
	},{
		name:"regtime",
		mapping:'company.regtime'
	},{
		name: 'isgetdata',
		type: 'boolean'
	}]),
	
	// TODO 默认列表连接
	//listUrl:Context.ROOT+Context.PATH+"/crm/listcrm.htm",
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
							var _id=row[i].get("id");
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
			},{
				text:"取消黑名单",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/cancelBlankList.htm?rm="+Math.random()+"&ids="+_ids.join(','),
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
				text:"放入我的客户",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/join.htm?rm="+Math.random()+"&cid="+_ids.join(','),
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
				text:" 取消放入我的客户",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					var row = grid.getSelectionModel().getSelections();
					if(row.length == 0){
						Ext.Msg.alert(Context.MST_TITLE,"请至少选定一个客户！");
					}else{
						var _ids= new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/cancelJoin.htm?rm="+Math.random()+"&cid="+_ids.join(','),
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
			}],
	buttonQuery:function(){
		var tbar2 = new Ext.Toolbar({
			items:[new ast.ast1949.CategoryCombo( {
				categoryCode : Context.CATEGORY["companyAdminUser"],
				name : "companyAccessGrade"
			}),{
				text:"归类客户",
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
								var _id=row[i].get("id");
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
