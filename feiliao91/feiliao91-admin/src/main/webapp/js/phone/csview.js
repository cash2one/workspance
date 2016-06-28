Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.INFOFIELD=[
	{name:"id",mapping:"phone.id"},
	{name:"company_id",mapping:"phone.companyId"},
	{name:"account",mapping:"phone.account"},
	{name:"tel",mapping:"phone.tel"},
	{name:"front_tel",mapping:"phone.frontTel"},
	{name:"keywords",mapping:"phone.keywords"},
	{name:"amount",mapping:"phone.amount"},
	{name:"balance",mapping:"phone.balance"},
	{name:"gmt_open",mapping:"phone.gmtOpen"},
	{name:"sumCallFee",mapping:"sumCallFee"},
	{name:"sumClickFee",mapping:"sumClickFee"},
	{name:"email",mapping:"companyAccount.email"},
	{name:"contact",mapping:"companyAccount.contact"},
	{name:"name",mapping:"company.name"},
	{name:"star",mapping:"company.star"}
];

ast.ast1949.phone.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header:"生意管家",
				width:80,
				dataIndex : "contact",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					
					var v1 = "<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>"+value+"</a> "
				//	var v2 = "<a href='http://192.168.2.2/admin1/crmlocal/crm_cominfoedit.asp?idprod="+value+"' target='_blank'>crm库</a> "
				//	var v3 = "<a href='http://www.zz91.com/ppc/index"+value+".htm' target='_blank'>门市部</a> "
					return v1;
				}
			},{
				header:"crm库",
				width:120,
				dataIndex : "email",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					var v1 = "<a href='"+Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+record.get("company_id")+"&star="+record.get("star")+"&companyName="+encodeURI(record.get("name"))+"' target='_blank'>"+value+"</a> "
					return v1;
				}
			},{
				header:"门市部",
				width:80,
				dataIndex : "name",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					if(value!=null){
						var v1 = "<a href='http://www.zz91.com/ppc/index"+encodeURIComponent(record.get("company_id"))+".htm' target='_blank'>"+value+"</a> "
					}else{
						var v1 = "<a href='http://www.zz91.com/ppc/index"+encodeURIComponent(record.get("company_id"))+".htm' target='_blank'>个体经营</a> "
					}
					
					return v1;
				}
			},{
				header : "公司ID",
				name : "company_id",
				id : "company_id",
				hidden:true,
				sortable:true,
				dataIndex:"company_id"
			},{
				header : "展示号码",
				width : 100,
				sortable : false,
				dataIndex : "front_tel"
			},{
				header : "主营业务",
				width : 250,
				sortable : false,
				dataIndex : "keywords"
			},{
				header : "开通时间",
				width : 140,
				sortable : false,
				dataIndex : "gmt_open",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryAllPhone.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.INFOFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [
//		{
//			text:"添加新号码",
//			iconCls:"add",
//			handler:function(){
//				ast.ast1949.phone.updatePhoneInfo(0);
//			}
//		}
//		,"-",{
//			text:"修改",
//			iconCls:"edit",
//			handler:function(){
//				var row=grid.getSelectionModel().getSelected();
//				ast.ast1949.phone.updatePhoneInfo(row.get("id"))
//			}
//		},"-",{
//			text:"删除",
//			iconCls:"delete",
//			handler:function(){
//				var row=grid.getSelectionModel().getSelected();
//				grid.deleteById(row.get("id"));
//			}
//		}
//		,"-",{
//			text:"询盘群发",
//			iconCls:"copy",
//			handler:function(){
//				var row=grid.getSelectionModel().getSelected();
//				if(row==null){
//					alert("请选择一家公司");
//					return false;
//				}
//				var url = Context.ROOT+Context.PATH+"/phone/batchInquiry.htm?companyId="+row.get("company_id")+"&keyword="+encodeURI(row.get("keywords"));
//				window.open(url);
//			}
//		}
		];

		var c={
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
		ast.ast1949.phone.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null,
	deleteById:function(id){
		var grid = Ext.getCmp("phoneGrid");
		var row = grid.getSelectionModel().getSelected();
		Ext.Ajax.request({
			url: Context.ROOT+Context.PATH+ "/phone/deleteById.htm",
			params:{
				"id":row.get("id"),
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","信息已成功更新");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","操作失败");
				}
			},
			failure:function(response,opt){
				com.zz91.utils.Msg("","操作失败");
			}
		});
	}
});

ast.ast1949.phone.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
							alert(1);
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

		ast.ast1949.phone.SearchForm.superclass.constructor.call(this,c);

	}
});

//账户信息修改
ast.ast1949.phone.updatePhoneInfo=function(id,companyId){
	var form=new ast.ast1949.phone.UpdatePhoneInfoForm({
	});
	
	form.loadInit(id,companyId);
	
	var win = new Ext.Window({
		id:"updatephonewin",
		title:"400帐号开通/修改",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.phone.UpdatePhoneInfoForm = Ext.extend(Ext.form.FormPanel,{
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
				fieldLabel:"公司ID",
				name:"companyId",
				id:"company_id",
				readOnly:true
			},{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"公司帐号",
				name:"account",
				id:"account",
				readOnly:true
			},{
				fieldLabel:"400电话号码",
				name:"tel",
				id:	"tel"
			},{
				fieldLabel:"展示400号码",
				name:"frontTel",
				id:	"front_tel"
			},{
				fieldLabel:"总金额",
				name:"amount",
				id:	"amount"
			},{
				fieldLabel:"匹配关键字",
				name:"keywords",
				id:"keywords"
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("phoneGrid");
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/phone/add.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updatephonewin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
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
		
		ast.ast1949.phone.UpdatePhoneInfoForm.superclass.constructor.call(this,c);
	},
	loadInit:function(id,companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		var _url = "";
		if(companyId!=undefined){
			_url = "&companyId="+companyId;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.phone.INFOFIELD,
			url : Context.ROOT+Context.PATH+"/phone/init.htm?id="+id+_url, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});


ast.ast1949.phone.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
					fieldLabel : "400号码：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["tel"] = undefined;
							}else{
								B["tel"] = newvalue;
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
				}
				,{
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
					fieldLabel : "主营业务：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["keywords"] = undefined;
							}else{
								B["keywords"] = newvalue;
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
		
		ast.ast1949.phone.SearchForm.superclass.constructor.call(this,c);

	}
});



