Ext.namespace("ast.ast1949.admin.yuanliao");
/**
 * 用于提供搜索输入框的表单
 * */
ast.ast1949.admin.yuanliao.searchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
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
						xtype:"combo",
						id:"sourceTypeCode",
						mode:"local",
						emptyText:"请选择...",
						fieldLabel:"发布来源：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						autoSelect:true,
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'pc端生意管家',value:'0'},
								{name:'pc端快速发布',value:'2'},
								{name:'后台发布',value:'1'}
							]
						}),
						listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["sourceTypeCode"] = field.getValue();
								}else{
									B["sourceTypeCode"]=undefined;
								}
								_store.baseParams = B;
							}
						}
				},{
						xtype:"combo",
						id:"sort",
						mode:"local",
						emptyText:"请选择...",
						fieldLabel:"审核状态：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						autoSelect:true,
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'审核时间',value:'check_time'},
								{name:'发布时间',value:'post_time'},
								{name:'刷新时间',value:'refresh_time'}
							]
						}),
						listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["sort"] = field.getValue();
								}else{
									B["sort"]=undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype : "datefield",
						format:"Y-m-d",
						name:"startTime",
						fieldLabel:"时间(始)",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									_store.baseParams["startTime"]= Ext.util.Format.date(field.getValue(), 'Y-m-d 00:00:00');//H:m:s
								}else{
									_store.baseParams["startTime"]=null;
								}
							}
						}
					},{
						xtype : "datefield",
						format:"Y-m-d",
						name : "endTime",
						fieldLabel:"时间(尾)",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									_store.baseParams["endTime"]= Ext.util.Format.date(field.getValue(), 'Y-m-d 23:59:59');
								}else{
									_store.baseParams["endTime"]=null;
								}
							}
						}
					},{
						xtype:"textfield",
						fieldLabel:"关键字搜索",
						listeners:{
							"blur":function(field){
								if(field.getValue()!=""){
									B["keyword"] = field.getValue();
								}else{
									B["keyword"]=undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						id:"yuanliaoTypeCode",
						mode:"local",
						fieldLabel:"供/求",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						autoSelect:true,
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'供应',value:'10331000'},
								{name:'求购',value:'10331001'}
							]
						}),
						listeners:{
							"blur":function(field){
								if(Ext.get(field.getId()).dom.value!=""){
									B["yuanliaoTypeCode"] = field.getValue();
								}else{
									B["yuanliaoTypeCode"]=undefined;
								}
								_store.baseParams = B;
							}
						}
					}
				]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};

		ast.ast1949.admin.yuanliao.searchForm.superclass.constructor.call(this,c);

	}
});

/**
 * 用于显示各种条件查询后的供求信息列表
 * */
ast.ast1949.admin.yuanliao.resultGrid = Ext.extend(Ext.grid.GridPanel,{
	readOnly:false,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _this=this;

		var sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel([sm,{
				header : "编号",
				hidden:true,
				dataIndex : "id"
			},{
				header : "隐藏",
				hidden:true,
				dataIndex : "hide"
			},{
				header:"审核状态",
				dataIndex : "checkStatus",
				width:70,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var status=record.get("checkStatus");
					if(status=="1"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="2"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}			
			},{
				header:"供/求",
				dataIndex : "yuanliaoTypeLabel",
				width:50,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value=="10331000"){
						return "供应";
					}else{
						return "求购";
					}
				}
			},{
				header : "供求标题",
				width : 300,
				dataIndex : "title",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var webstr="<a href='http://slyl.zz91.com/detail/"+record.get("id")+".html' target='_blank' >";
					webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
					var title=value;
					if(!_this.readOnly){
						title=" <a href='"+
							Context.ROOT+Context.PATH+
							"/yuanliao/edit.htm?id="+record.get("id")+
							"&companyId="+record.get("companyId")+
							"&account="+record.get("account")+
							"' target='_blank'>"+value+"</a>";
					}
					return webstr+title;
				}
			},{
				header : "公司名称",
				width : 180,
				dataIndex : "companyName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")!="10051000"){
						val="<img src='"+Context.ROOT+"/images/recycle"+record.get("membershipCode")+".gif' />";
					}
					if(_this.readOnly){
						val= val + value;
					}else{
						val= val + "<a href='" + Context.ROOT + Context.PATH + 
							"/crm/company/detail.htm?companyId=" + 
							record.get("companyId") + "' target='_blank'>" + 
							value + "</a>";
					}
					return val;
				}
			},{
				header : "发布时间",
				sortable : true,
				width:80,
				dataIndex : "post_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			},{
				header:"刷新时间",
				sortable:true,
				width:80,
				dataIndex:"refresh_time",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
				}
			},{
				header : "审核时间",
				width:100,
				sortable : true,
				dataIndex : "check_time",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header : "审核人",
				sortable : true,
				width:80,
				dataIndex : "check_person"
			}]
		);
				// 字段信息
		var reader = [
			  {name:"id",mapping:"yuanliao.id"},
			  {name:"isDel",mapping:"yuanliao.isDel"},
			  {name:"refresh_time",mapping:"yuanliao.refreshTime"},
			  {name:"title",mapping:"yuanliao.title"},
			  {name:"companyId",mapping:"yuanliao.companyId"},
			  {name:"account",mapping:"yuanliao.account"},
			  {name:"check_person",mapping:"yuanliao.checkPerson"},
			  {name:"checkStatus",mapping:"yuanliao.checkStatus"},
			  {name:"check_time",mapping:"yuanliao.checkTime"},
			  {name:"post_time",mapping:"yuanliao.postTime"},
			  {name:"isPause",mapping:"yuanliao.isPause"},
			  {name:"isVip",mapping:"yuanliao.isVip"},
			  {name:"yuanliaoTypeLabel",mapping:"yuanliao.yuanliaoTypeCode"},
			  {name:"categoryYuanliaoCode",mapping:"yuanliao.categoryYuanliaoCode"},
			  {name:"companyName",mapping:"company.name"},
			  {name:"membershipCode",mapping:"company.membershipCode"},
			  {name:"isExpire",mapping:"isExpire"}
			  ];

		var storeUrl = Context.ROOT + Context.PATH + "/yuanliao/pageYuanliaoByAdmin.htm";

		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:false
		});
		var tbar = this.toolbar;
		var c={
			id:"yuanliaoresultgrid",
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:10,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
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
			tbar : tbar
		};

		ast.ast1949.admin.yuanliao.resultGrid.superclass.constructor.call(this,c);
	},
	searchByCheckStatus:function(companyId){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(companyId){
			Ext.getCmp("uncheckBtn").setValue(false);
			B["companyId"] = companyId;
			Ext.get("ext-gen49").setVisible(false);
		}
		if(Ext.getCmp("uncheckBtn").getValue()){
			ary.push(0);
		}
		if(Ext.getCmp("checkedBtn").getValue()){
			ary.push(1);
		}
		if(Ext.getCmp("rejectedBtn").getValue()){
			ary.push(2);
		}
		if(ary.length>0){
			B["checkArray"] = ary.join(",");
		}else{
			B["checkArray"] = undefined;
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByAutoCheck:function(){
		var B=this.getStore().baseParams||{};
		if(Ext.getCmp("autoPassBtn").getValue()){
			B["checkPerson"] = "zz91-auto-check";
		}else{
			B["checkPerson"] = "";
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByCompany:function(companyId){
		var B=this.getStore().baseParams||{};
		B["companyId"] = companyId;
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	toolbar:[
	"-",{
			text:"编辑",
			id:"editTools",
			iconCls : 'edit',
			menu:[{
				text:"编辑选中供求",
				handler:function(btn){
					var grid = Ext.getCmp("yuanliaoresultgrid");
					var row = grid.getSelections();
					for(var i=0;i<row.length;i++){
						window.open(Context.ROOT+Context.PATH
							+"/yuanliao/edit.htm?id="+row[i].get("id")
							+"&companyId="+row[i].get("companyId")
							+"&account="+row[i].get("account"));
					}
				}
			},{
				text:"(删除信息)恢复操作",
				handler:function(btn){
					var grid = Ext.getCmp("yuanliaoresultgrid");
					var rows = grid.getSelections();
					var rowid = rows[0].get("isDel");
					if(rowid=='1'){
						grid.check(null,0,null);
					}else if(rowid=='0'){
						grid.check(null,1,null);
					}
				}
			},{
				text:"刷新",
				handler:function(btn){
					Ext.MessageBox.confirm('提示', '确定要刷新选中的塑料原料的供求', function(e){
						if(e=="yes"){
							var grid = Ext.getCmp("yuanliaoresultgrid");
							grid.refresh();
						}
					}); 
				}
			}]
	},"-",{
    		text:"审核",
			id:"checkTools",
			menu:[{
				text:"通过",
				handler:function(btn){
					var grid = Ext.getCmp("yuanliaoresultgrid");
					grid.check(1);
				}
			},{
				text:"不通过",
				handler:function(btn){
					Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
						if (btn == 'ok'){
							var grid = Ext.getCmp("yuanliaoresultgrid");
							grid.check(2,null,text);
						}
					}, this,true,"供求信息产品名称填写不明确：建议您一条信息只填写一种类型的发布与废料相关的产品、设备、服务等具体产品，并且只须填写产品名称或服务即可，如PP颗粒（多产品或标题过长都将非常不利于您的信息在ZZ91或百度谷歌等各大搜索引擎被搜索到），这样可以大大提升您信息在ZZ91或百度、谷歌等各大搜索引擎被查看到的机率。");
				}
			}]
	},"->",{
		xtype:"checkbox",
		boxLabel:"<span style='color:red'>高会</span>",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp("yuanliaoresultgrid").getStore();
				var B = _store.baseParams;
				if(field.getValue()){
					B["isVip"] = '1';
				}else{
					B["isVip"] = '0';
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},"-",{
		xtype:"checkbox",
		boxLabel:"未审核",
		id:"uncheckBtn",
		checked:true,
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("yuanliaoresultgrid");
				grid.searchByCheckStatus();
			}
		}
	},"-",{
		xtype:"checkbox",
		boxLabel:"通过",
		id:"checkedBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("yuanliaoresultgrid");
				grid.searchByCheckStatus();
			}
		}
	}," ",{
		xtype:"checkbox",
		boxLabel:"退回",
		id:"rejectedBtn",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var grid = Ext.getCmp("yuanliaoresultgrid");
				grid.searchByCheckStatus();
			}
		}	
	},"-",{
		xtype:"combo",
		id:"isDel",
		mode:"local",
		emptyText:"删除状态",
		fieldLabel:"删除状态：",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		autoSelect:true,
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'已删除',value:1},
				{name:'未删除',value:0}
			]
		}),
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("yuanliaoresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isDel"] = field.getValue();
				}else{
					B["isDel"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},{
		xtype:"combo",
		id:"isPause",
		mode:"local",
		emptyText:"发布状态",
		fieldLabel:"发布状态：",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		autoSelect:true,
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'暂不发布',value:1},
				{name:'发布',value:0}
			]
		}),
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("yuanliaoresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isPause"] = field.getValue();
				}else{
					B["isPause"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	},{
		xtype:"combo",
		id:"isExpire",
		mode:"local",
		emptyText:"过期状态",
		fieldLabel:"过期状态：",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		autoSelect:true,
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'已过期',value:1},
				{name:'未过期',value:0}
			]
		}),
		listeners:{
			"blur":function(field){
				var _store = Ext.getCmp("yuanliaoresultgrid").getStore();
				var B = _store.baseParams;
				B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isExpire"] = field.getValue();
				}else{
					B["isExpire"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}],
	check:function(checkStatus,isDel,msg){
		var grid = Ext.getCmp("yuanliaoresultgrid");
		var rows = grid.getSelections();
		
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/yuanliao/updateCheckStatus.htm",
				params:{
					"id":rows[i].get("id"),
					"checkStatus":checkStatus,
					"unpassReason":msg,
					"isDel":isDel
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("",obj.data);
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","操作失败");
				}
			});
		}
	},
	refresh:function(){
		var grid = Ext.getCmp("yuanliaoresultgrid");
		var rows = grid.getSelections();
		for(var i = 0; i<rows.length; i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/yuanliao/refresh.htm",
				params:{
					"id":rows[i].get("id")
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功刷新")
						grid.getStore().reload();
					}else{
						ast.ast1949.utils.Msg("",obj.data);
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","操作失败");
				}
			});
		}
	}
});

