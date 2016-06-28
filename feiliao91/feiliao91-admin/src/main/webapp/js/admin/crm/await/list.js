Ext.namespace("ast.ast1949.admin.crm.await")

/**
 * 待审核核心客户
 * @class ast.ast1949.admin.crm.await.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.crm.await.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var grid = this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners:{
				selectionchange: function(sm) {
	                if (sm.getCount()) {
						Ext.getCmp("assignButton").enable();
	                    Ext.getCmp("removeButton").enable();
	                    Ext.getCmp("doDealButton").enable();
	                    Ext.getCmp("cancelDealButton").enable();
	                } else {
	                	Ext.getCmp("assignButton").disable();
	                    Ext.getCmp("removeButton").disable();
	                    Ext.getCmp("doDealButton").disable();
	                    Ext.getCmp("cancelDealButton").disable();
	                }
	            }
			}
		});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header : "编号",
				width : 10,
				sortable : false,
				dataIndex : "orderId",
				hidden:true
			},
			{
				header : "处理状态",
				sortable : false,
				dataIndex : "status",
				disabled:false,
				width : 60,
				renderer:function (value,metadata,record,rowIndex,colIndex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
			},
			{
				header : "设置",
				sortable : false,
				dataIndex : "companyId",
				disabled:false,
				renderer:function(value,metadata,record,rowIndex,colIndex,store) {
					if(value!=null) {
						var cid=record.get("companyId");
						var sid=record.get("adminUserId");
						var sname=record.get("adminRealName");
						var oid=record.get("orderId");
						
						return "<a href='"+ Context.ROOT + Context.PATH +"/admin/crm/opens/apply.htm?oid="+oid+"&cid="+cid+"&sid="+sid+"&sname="+sname+"&st="+Math.random()+"' target='_blank'>设置</a>";
					} else {
						return "";
					}
				}
			},
			{
				header : "公司名称",
				sortable : false,
				dataIndex : "companyName",
				disabled:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					var str="";
					if(value!=null) {
						var selid=record.get("companyId");
						var account=record.get("account");
						
		        		if(selid>0){
		        			str="<a href='" + Context.ROOT+Context.PATH+"/admin/admincompany/edit.htm?st="+Math.random()+"&account="+account+"&companyId="+selid+"' target='_blank'>"+value+"</a>"
		        		}
					}
					
					return str;
				}
			},
			{
				header:"开通单",
				sortable:false,
				dataIndex:"orderId",
				disabled:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					var s="";
					if(value!=null) {
						s += "<a href='" + Context.ROOT + Context.PATH + "/admin/crm/await/view.htm?id=" +value+"&st="+Math.random()+"' target='_blank'>查看详情</a>";
					}
					
					return s;
				}
			},
			{
				header : "登入商城",
				sortable : false,
				dataIndex : "companyId",
				disabled:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='#' onclick='javascript:alert(\"暂时无法登录。\")'>登录</a>";
				}
			},
			{
				header : "邮箱",
				sortable : false,
				dataIndex : "email",
				disabled:false
			},
			{
				header : "电话",
				sortable : false,
				dataIndex : "tel",
				disabled:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return record.get("telCountryCode")+"-"+record.get("telAreaCode")+"-"+record.get("tel");
				}
			},
			{
				header : "手机",
				sortable : false,
				dataIndex : "mobile",
				disabled:false
			},
			{
				header : "省市地区",
				sortable : false,
				dataIndex : "areaCode",
				disabled:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null&&value!=""){
						return record.get("provinceName")+" "+record.get("cityName");
					} else {
						return "";
					}
				}
			},
			{
				header : "注册时间",
				sortable : false,
				dataIndex : "regtime",
				disabled:false,
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
				header : "服务人员",
				sortable : false,
				dataIndex : "adminRealName",
				disabled:false
			},
			{
				header : "销售人员",
				sortable : false,
				dataIndex : "",
				hidden:true,
				disabled:false,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					//TODO:本地读取销售人员信息
					return "本地读取..."
				}
			}
		]);
		
		var mybar = [
			new ast.ast1949.AdminUserCombo( {
				name : "adminuser_combo"
			}),
			{
				id:"assignButton",
				text : "分配客户",
				tooltip : "分配客户给相应的客服人员。",
				iconCls : "edit",
				scope : this,
				disabled:true,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					} else {
						var code=Ext.get("adminuser_combo").dom.value;
						if(code==null||code.length<=0)
						{
							ast.ast1949.utils.Msg("","请选定一个客服！");
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
										ast.ast1949.utils.Msg("","操作成功！");
										grid.getStore().reload();
									}else{
										ast.ast1949.utils.Msg("","操作失败!");
									}
								}
							});
						}
					}
				}
			},"-",
			{
				id:"removeButton",
				text : "删除",
				tooltip : "删除选中记录。",
				iconCls : "delete",
				scope : this,
				disabled:true,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					} else {
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("orderId");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/await/delete.htm?random="+Math.random()+"&ids="+_ids.join(','),
							method : "get",
							scope : this,
							callback : function(options,success,response){
							var a=Ext.decode(response.responseText);
								if(success){
									ast.ast1949.utils.Msg("","选定的记录已被删除!");//Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
									grid.getStore().reload();
								}else{
									ast.ast1949.utils.Msg("","所选记录删除失败!");
								}
							}
						});
					}
				}
			},
			{
				id:"doDealButton",
				text : "设为处理",
				tooltip : "设置为已处理。",
				iconCls : "doc_update",
				scope : this,
				disabled:true,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					} else {
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("orderId");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/await/deal.htm?random="+Math.random()+"&status=1&ids="+_ids.join(','),
							method : "get",
							scope : this,
							callback : function(options,success,response){
							var a=Ext.decode(response.responseText);
								if(success){
									ast.ast1949.utils.Msg("","选定的记录已被删除!");//Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
									grid.getStore().reload();
								}else{
									ast.ast1949.utils.Msg("","所选记录删除失败!");
								}
							}
						});
					}
				}				
			},
			{
				id:"cancelDealButton",
				text : "取消处理",
				tooltip : "设置为未处理",
				iconCls : "doc_update",
				scope : this,
				disabled:true,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					} else {
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("orderId");
							_ids.push(_id);
						}
						/*提交*/
						var conn = new Ext.data.Connection();
						conn.request({
							url: Context.ROOT+Context.PATH+ "/admin/crm/await/deal.htm?random="+Math.random()+"&status=0&ids="+_ids.join(','),
							method : "get",
							scope : this,
							callback : function(options,success,response){
							var a=Ext.decode(response.responseText);
								if(success){
									ast.ast1949.utils.Msg("","选定的记录已被删除!");//Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
									grid.getStore().reload();
								}else{
									ast.ast1949.utils.Msg("","所选记录删除失败!");
								}
							}
						});
					}
				}
			}, "->", "处理状态：", 
			{
				xtype:"checkbox",
				boxLabel:"已处理",
				id:"isExport",
//				checked:true,
				listeners:{
					"check":function(field,newvalue,oldvalue){
						grid.searchByExportStatus();
					}
				}
			},
			{
				xtype:"checkbox",
				boxLabel:"未处理",
				id:"unExport",
				checked:true,
				listeners:{
					"check":function(field,newvalue,oldvalue){
						grid.searchByExportStatus();
					}
				}
			}
		];
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:mybar,
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
			listeners:{
//				"render" : this.buttonQuery
			}
		};
		ast.ast1949.admin.crm.await.ResultGrid.superclass.constructor.call(this,c);
	},
	searchByExportStatus:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("unExport").getValue()){
			ary.push(0);
		}
		if(Ext.getCmp("isExport").getValue()){
			ary.push(1);
		}

		B["status"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"orderId",mapping:"orderId"},
		{name:"status",mapping:"status"},
		{name:"companyId",mapping:"companyId"},
		{name:"account",mapping:"account"},
		{name:"companyName",mapping:"companyName"},
		{name:"email",mapping:"email"},
		{name:"telCountryCode",mapping:"telCountryCode"},
		{name:"telAreaCode",mapping:"telAreaCode"},
		{name:"tel",mapping:"tel"},
		{name:"mobile",mapping:"mobile"},
		{name:"areaCode",mapping:"areaCode"},
		{name:"regtime",mapping:"regtime"},
		{name:"adminUserId",mapping:"adminUserId"},
		{name:"adminUserName",mapping:"adminUserName"},
		{name:"adminRealName",mapping:"adminRealName"},
		{name:"provinceName",mapping:"provinceName"},
		{name:"cityName",mapping:"cityName"},
		
		{name:"account",mapping:"account"}
		
	]),
	listUrl:Context.ROOT + Context.PATH + "/admin/crm/await/query.htm"
})