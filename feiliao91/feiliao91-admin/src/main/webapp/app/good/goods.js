/*
 * 商品列表
 */
Ext.namespace("ast.feiliao91.admin.goods")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//所有商品列表
ast.feiliao91.admin.goods.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header:"审核状态",
				dataIndex : "checkStatus",
				width:60,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var status=record.get("checkStatus");
					
					if(status=="1"){
						return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="2"){
						return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"删除状态",
				dataIndex:"isDel",
				width:60,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var status=record.get("isDel");
					if(status=="0"){
						return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="1"){
						return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.False.gif" />';
					}
				}
			},{
				header:"上架状态",
				dataIndex:"isSell",
				width:60,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var status=record.get("isSell");
					if(status=="0"){
						return '未上架';
					}else if(status=="1"){
						return '已上架';
					}
				}
			},{
				header:"标题",
				dataIndex:"title",
				width:250,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						var webstr="<a href='http://www.taozaisheng.com/goods"+record.get("id")+".htm' target='_blank' >";
						webstr=webstr+"<img src='"+Context.ROOT+Context.PATH+"/css/admin/icons/web16.png' /></a>";
						_url=Context.ROOT+Context.PATH+"/admin/good/edit.htm?goodsId="+record.get("id")+"&companyId="+record.get("companyId");
						return webstr+"&nbsp;&nbsp;&nbsp;<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
					}
					return "";
				}
			},{
				header:"公司名称",
				dataIndex:"companyName",
				width:250,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						_url=Context.ROOT+Context.PATH+"/admin/company/detail.htm?companyId="+record.get("companyId");
						return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
					}
					return "";
				}
			},{
				header:"发布时间",
				width:135,
				sortable:true,
				dataIndex:"gmt_modified",
				renderer:function(value){
						if(value!=null){
							return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
						}
						return "";
				 }
		    },{
		    	header:"刷新时间",
		    	sortable:true,
				dataIndex:"refresh_time",
				width:135,
				renderer:function(value){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					return "";
				}
			},{
		    	header:"审核人",
				dataIndex:"checkPerson"
		    },{
		    	header:"审核时间",
		    	sortable:true,
				dataIndex:"check_time",
				width:135,
				renderer:function(value){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					return "";
				}
		    }
		]);
		
		var con={
				iconCls:"icon-grid",
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
				tbar:[{
					text:"审核",
					menu:[{
						text:"审核通过",
						handler:function(btn,e){
							ast.feiliao91.admin.goods.updateStatus(1);
						}
					},{
						text:"审核不通过",
						handler:function(btn,e){
							ast.feiliao91.admin.goods.updateStatus(2);
						}
					}]
				}],
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
			};
		ast.feiliao91.admin.goods.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
   		{name:"id",mapping:"goods.id"},
		{name:"title",mapping:"goods.title"},
		{name:"companyName",mapping:"companyInfo.name"},
		{name:"companyId",mapping:"companyInfo.id"},
		{name:"refresh_time",mapping:"goods.refreshTime"},
		{name:"gmt_modified",mapping:"goods.gmtModified"},
		{name:"checkStatus",mapping:"goods.checkStatus"},
		{name:"isDel",mapping:"goods.isDel"},
		{name:"checkPerson",mapping:"goods.checkPerson"},
		{name:"check_time",mapping:"goods.checkTime"},
		{name:"isSell",mapping:"goods.isSell"}//上下架状态
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/good/queryList.htm",
	//根据companyId搜索
	searchByCompany:function(companyId){
		var B=this.getStore().baseParams||{};
		B["companyId"] = companyId;
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
});

ast.feiliao91.admin.goods.updateStatus = function(checkStatus){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	var row = grid.getSelections();
	var _ids = new Array();
	if(row.length==0){
		Ext.MessageBox.alert(Context.MSG_TITLE,"请选中你要审核的产品信息");
		return false;
	}
	for (var i=0,len = row.length;i<len;i++){
		_ids.push(row[i].get("id"));
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/admin/good/updateCheckStatus.htm",
		params: {
			ids: _ids.join(","),
			checkStatus:checkStatus
		},
		success: function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
				grid.store.reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
			}
		}
	});
}

//右下部搜索框
ast.feiliao91.admin.goods.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
						fieldLabel : "产品名：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["title"] = null;
								}else{
									B["title"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						fieldLabel:"审核状态：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
							    {name:'通过',value:"1"},
							    {name:'退回',value:"2"},
							    {name:'未审核',value:"0"},
							    {name:'全部',value:""},
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=undefined){
									B["checkStatus"] = field.getValue();
								}else{
									B["checkStatus"] = undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						fieldLabel:"上架状态：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'已上架',value:"1"},
								{name:'已下架',value:"0"},
								{name:'全部',value:""},
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=null){
									B["isSell"] = field.getValue();
								}else{
									B["isSell"] = null;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						fieldLabel:"时间类型：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'发布时间',value:"gmt_modified"},
								{name:'刷新时间',value:"refresh_time"},
								{name:'审核时间',value:"check_time"}
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=undefined){
									B["dateType"] = field.getValue();
								}else{
									B["dateType"] = undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"datefield",
						id : "regFrom",
						name:"regFrom",
						format:"Y-m-d",
						fieldLabel : "时间(始)：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["from"] = undefined;
								}else{
									B["from"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"datefield",
						id : "regTo",
						name:"regTo",
						format:"Y-m-d",
						fieldLabel : "时间(终)：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["to"] = undefined;
								}else{
									B["to"] = newvalue;
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
		
		ast.feiliao91.admin.goods.SearchForm.superclass.constructor.call(this,c);
	}
});