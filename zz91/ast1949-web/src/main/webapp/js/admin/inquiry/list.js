
/**
 * Modify By Rolyer 2011.01.18
 * TODO:
 * 1、询盘导出为供求
 */
Ext.namespace("ast.ast1949.admin.inquiry");

/**
 * 询盘信息列表
 * @class ast.ast1949.admin.inquiry.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.inquiry.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
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
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners:{
				//TODO:
			}
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header : "编号",
				width : 40,
				sortable : false,
				dataIndex : "id",
				hidden:true
			},{
				xtype : "hidden",
				header : "标题",
				sortable : false,
				dataIndex : "title",
				disabled:false
			},{
				header:"询盘对象",
				width:100,
				sortable:false,
				dataIndex:"products",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(record!=null){
//						0:供求信息 1:公司 2: 询盘 3:企业报价
						if(record.get("beInquiredType")=="0"){
							product = "供求信息";
						}
						else if(record.get("beInquiredType")=="1") {
							product="<a href='"+Context.ROOT + Context.PATH+"/admin/admincompany/edit.htm?companyId="+record.get("beInquiredId")+"&account=&ts="+timestamp()+"' target='_blank'>公司详情</a>";//
						}
						else if(record.get("beInquiredType")=="2"){
							product = "询盘信息";
						}
						else if(record.get("beInquiredType")=="3"){
							product = "企业报价";
						}
						else {
							if(value!==null){
								var product="<a href='"+Page_Context.FRONT_SERVER+"/trade/productdetails"+record.get("pid")+".htm' target='_blank'>"+value.title+"</a>";
							}else{
								product="";
							}
						}
					}
					return product;
				}
			}
//			,{
//				header:"审核状态",
//				width:120,
//				sortable:false,
//				dataIndex:"exportStatus",
//				renderer:function(value,metadata,record,rowIndex,colndex,store){
//					return "状态："+record.get("exportStatusName")+"<br/>处理人："+record.get("exportPerson");
//				}
//			}
//			,{
//				header:"发送人",
//				width:100,
//				sortable:false,
//				dataIndex:"senderName",
//				renderer:function(value,metadata,record,rowIndex,colndex,store){
//					if(value!=null) {
//						return value;
//					} else {
//						return record.get("senderId");
//					}
//				}
//			}
			,{
				header:"发送人ID",
				width:100,
				sortable:false,
				dataIndex:"senderId",
				hidden:true
			},{
				header:"发送人(帐号)",
				width:100,
				sortable:false,
				dataIndex:"senderAccount"
			},{
				header:"接收人(帐号)",
				width:100,
				sortable:false,
				dataIndex:"receiverAccount",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value!=null) {
						return value;
					} else {
						return record.get("receiverId");
					}
				}
			},{
				header:"询盘内容",
				width:360,
				sortable:false,
				dataIndex:"content"
			},{
				header : "询盘时间",
				width : 80,
				sortable : false,
				dataIndex : "sendTime",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d');
					}
				}
			}
		]);
		
		var mybar=[
			{
				text : '导出',
				tooltip : '将一条询盘信息导出成供求信息',
				iconCls : 'add',
				scope : this,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					}else if (row.length  > 1){
						ast.ast1949.utils.Msg("","最多只能编辑一条记录。");
					}else {
						doExportToProducts(grid);
					}
				}
			},"-",
			{
				text : '设置已审核',
				iconCls : 'edit',
				tooltip : '将选定询盘信息的处理状态变更为已审核',
				scope : this,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					}else {
						Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要修改所选记录吗?', 
						function(_btn){
							if(_btn=="yes"){
								doChangeExportStatus(grid,2);
							}
						});
					}
				}
			},
			{
				text : '取消审核',
				iconCls : 'edit',
				tooltip : '取消选定询盘信息的审核状态',
				scope : this,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					}else {
						Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要修改所选记录吗?', 
						function(_btn){
							if(_btn=="yes"){
								doChangeExportStatus(grid,0);
							}
						});
					}
				}
			},"-",
			{
				text : '永久删除',
				iconCls : 'delete',
				scope : this,
				handler:function(btn){
					var row = grid.getSelections();
					if (row.length == 0){
						ast.ast1949.utils.Msg("","请选定一条记录。");
					}else {
						Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要删除所选的"+row.length+"条记录吗?", 
						function(_btn){
							if(_btn=="yes"){
								doDelete(grid);
							}
						});
					}
				}
			},"->","审核状态：",
			{
				xtype:"checkbox",
				boxLabel:"不审核",
				id:"unExport",
				checked:true,
				listeners:{
					"check":function(field,newvalue,oldvalue){
						grid.searchByExportStatus();
					}
				}
			},
			{
				xtype:"checkbox",
				boxLabel:"审核中",
				id:"inExport",
				checked:true,
				listeners:{
					"check":function(field,newvalue,oldvalue){
						grid.searchByExportStatus();
					}
				}
			},
			{
				xtype:"checkbox",
				boxLabel:"已审核",
				id:"beExport",
				listeners:{
					"check":function(field,newvalue,oldvalue){
						grid.searchByExportStatus();
					}
				}
			}
//			{
//				xtype:"combo",
//		        store: new Ext.data.JsonStore({
//		        		root:"exportStatus",
//				        fields: ['k', 'v'],
//				        data : ast.ast1949.statesdata.inquiry
//				    }),
//				fieldLabel:"(导出)处理状态",
//		        displayField:'v',
//		        valueField:"k",
//		        mode: 'local',
//		        forceSelection: true,
//		        triggerAction: 'all',
//		        selectOnFocus:true,
//				
////				        emptyText:"请选处理状态",
//		        width: 100,
//		        value:"0",
//		        listeners:{
//					"blur":function(c){
//						var _store = grid.getStore();
//						var B = _store.baseParams;
//						B = B||{};
//						
//						if(Ext.get(c.getId()).dom.value!=""){
//							B["exportStatus"] = c.value;
//						}else{
//							B["exportStatus"] = null;
//						}
//						_store.baseParams = B;
//						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//					}
//				}
//			}
		]
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
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
		
		ast.ast1949.admin.inquiry.ResultGrid.superclass.constructor.call(this,c);
	},
	searchByExportStatus:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("unExport").getValue()){
			ary.push(0);
		}
		if(Ext.getCmp("inExport").getValue()){
			ary.push(1);
		}
		if(Ext.getCmp("beExport").getValue()){
			ary.push(2);
		}

		B["exportStatus"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"inquiry.id"},
		{name:"title",mapping:"inquiry.title"},
		{name:"content",mapping:"inquiry.content"},
		{name:"groupId",mapping:"inquiry.groupId"},
		"groupName",
		{name:"beInquiredType",mapping:"inquiry.beInquiredType"},
		"beInquiredTypeName",
		{name:"beInquiredId",mapping:"inquiry.beInquiredId"},
		{name:"inquiredType",mapping:"inquiry.inquiredType"},
		"inquiredTypeName",
		{name:"inquiredId",mapping:"inquiry.inquiredId"},
		{name:"batchSendType",mapping:"inquiry.batchSendType"},
		"batchSendTypeName",
		{name:"exportStatus",mapping:"inquiry.exportStatus"},
		"exportStatusName",
		{name:"exportPerson",mapping:"inquiry.exportPerson"},
		{name:"isRubbish",mapping:"inquiry.isRubbish"},
		{name:"sendTime",mapping:"inquiry.sendTime"},
		{name:"account",mapping:"inquiry.account"},
		{name:"receiverId",mapping:"inquiry.receiverId"},
		{name:"senderId",mapping:"inquiry.senderId"},
		{name:"senderEmail",mapping:"senderEmail"},
		{name:"senderAccount",mapping:"inquiry.senderAccount"},
		{name:"receiverAccount",mapping:"inquiry.receiverAccount"},
		"products",
		"senderName",
		"receiverName",
		"pid"
	]),
	listUrl:Context.ROOT + Context.PATH + "/admin/inquiry/listInquiry.htm"
})

/**
 * 高级搜索
 * @class ast.ast1949.admin.inquiry.searchForm
 * @extends Ext.form.FormPanel
 */
ast.ast1949.admin.inquiry.searchForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};
		
		var c = {
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
				items:[
					{
						fieldLabel : "发送者(email)",
						id : "senderAccount",
						name:"senderAccount",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["senderAccount"] = undefined;
								}else{
									B["senderAccount"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						fieldLabel : "接收者(email)",
						id : "receiverAccount",
						name:"receiverAccount",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["receiverAccount"] = undefined;
								}else{
									B["receiverAccount"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"datefield",
						fieldLabel : "开始时间",
						id:"sendTimeStart",
						name:"sendTimeStart",
						format : 'Y-m-d',
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["sendTimeStart"] = null;
								}else{
									B["sendTimeStart"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"datefield",
						fieldLabel : "结束时间",
						id:"sendTimeEnd",
						name:"sendTimeEnd",
						format : 'Y-m-d',
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["sendTimeEnd"] = null;
								}else{
									B["sendTimeEnd"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},
					{
						xtype:"combo",
				        store: new Ext.data.JsonStore({
				        		root:"beInquiredType",
						        fields: ['k', 'v'],
						        data : ast.ast1949.statesdata.inquiry
						    }),
						fieldLabel:"被询盘对象",
				        displayField:'v',
				        valueField:"k",
				        mode: 'local',
				        forceSelection: true,
				        triggerAction: 'all',
				        selectOnFocus:true,
				        width: 100,
				        listeners:{
							"blur":function(c){
								if(Ext.get(c.getId()).dom.value!=""){
									B["beInquiredType"] = c.value;									
								}else{
									B["beInquiredType"] = null;
								}
								_store.baseParams = B;
							}
						}
				    },
				    {
				    	xtype:          "combo",
                        mode:           "local",
                        triggerAction:  "all",
                        forceSelection: true,
                        editable:       true,
                        fieldLabel:     "几天之内",
                        hiddenName:     "numbers",
                        displayField:   "name",
                        valueField:     "value",
//                        autoSelect:true,
                        store:new Ext.data.JsonStore({
					        fields : ["name", "value"],
					        data   : [
//					        	{name:"3",value:"3"},
					        	{name:"15",value:"15"}
//					        	{name:"30",value:"30"}
					        ]
					    }),
					    listeners:{
							"blur":function(c){
								this.setValue(this.el.dom.value)
								if(Ext.get(c.getId()).dom.value!=""){
									B["numbers"] = c.value;
								}else{
									B["numbers"] = null;
								}
								_store.baseParams = B;
							}
						}
				    },
				    {
				    	xtype:          "combo",
                        mode:           "local",
                        triggerAction:  "all",
                        forceSelection: true,
                        editable:       true,
                        fieldLabel:     "是否发布供求",
                        hiddenName:     "isPublished",
                        displayField:   "name",
                        valueField:     "value",
                        autoSelect:true,
                        store:new Ext.data.JsonStore({
					        fields : ["name", "value"],
					        data   : [
					        	{name:"不限制",value:""},
					        	{name:"未发布",value:"false"},
					        	{name:"已发布",value:"true"}
					        ]
					    }),
					    listeners:{
							"blur":function(c){
								if(Ext.get(c.getId()).dom.value!=""){
									B["isPublished"] = c.value;
								}else{
									B["isPublished"] = null;
								}
								_store.baseParams = B;
							}
						}
				    }
//				    {
//						xtype:"combo",
//				        store: new Ext.data.JsonStore({
//				        		root:"inquiredType",
//						        fields: ['k', 'v'],
//						        data : ast.ast1949.statesdata.inquiry
//						    }),
//						fieldLabel:"询盘来源",
//				        displayField:'v',
//				        valueField:"k",
//				        typeAhead: true,
//				        mode: 'local',
//				        forceSelection: true,
//				        triggerAction: 'all',
//				        selectOnFocus:true,
//						
//				        hiddenId:"inquiredType",
//				        hiddenName:"inquiredType",
//				        emptyText:"请选询盘来源",
//				        width: 100,
//				        listeners:{
//							"blur":function(c){
//								if(c.value==""){
//									B["inquiredType"] = undefined;
//								}else{
//									B["inquiredType"] = c.value;
//								}
//								_store.baseParams = B;
//							}
//						}
//				    },
//				    {
//						xtype:"combo",
//				        store: new Ext.data.JsonStore({
//				        		root:"batchSendType",
//						        fields: ['k', 'v'],
//						        data : ast.ast1949.statesdata.inquiry
//						    }),
//						fieldLabel:"群发标记",
//				        displayField:'v',
//				        valueField:"k",
//				        typeAhead: true,
//				        mode: 'local',
//				        forceSelection: true,
//				        triggerAction: 'all',
//				        selectOnFocus:true,
//						
//				        hiddenId:"batchSendType",
//				        hiddenName:"batchSendType",
//				        emptyText:"请选群发标记",
//				        width: 100,
//				        listeners:{
//							"blur":function(c){
//								if(c.value==""){
//									B["batchSendType"] = undefined;
//								}else{
//									B["batchSendType"] = c.value;
//								}
//								_store.baseParams = B;
//							}
//						}
//				    }
				]
			}],
			buttons:[{
				text:"搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		}
		ast.ast1949.admin.inquiry.searchForm.superclass.constructor.call(this,c);
	}
})

/**
 * 将询盘导出为供求
 * @param {} grid
 * @return {Boolean}
 */
function doExportToProducts(grid){
	var row=grid.getSelections();
	for(var i=0,len=row.length;i<len;i++){
		var _id=row[i].get("id");
		var _pid=row[i].get("pid");
		var _contactsId=row[i].get("senderId");
//		var _title=row[i].get("title");
		var _details=row[i].get("content");
		var _senderEmail=row[i].get("senderEmail");
	}

	if(_senderEmail.length<=0){
		Ext.Msg.alert(Context.MSG_TITLE, "错误：所选信息的发送人为空，无法导出为供求！");
		return false;
	}
	
	window.open(Context.ROOT+Context.PATH
						+"/admin/inquiry/edit.htm?"
						+"inquiryId="+_id
						+"&productsId="+_pid
						+"&contactsId="+_contactsId
						+"&account="+_senderEmail
						+"&ts="+Math.random());
	//更新状态为处理中
	doChangeExportStatus(grid,"1");
}

/**
 * 删除询盘
 * @param {} grid
 */
function doDelete(grid){
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/inquiry/delete.htm?random="+Math.random()+"&ids="+_ids.join(','),
		method : "get",
		scope : this,
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
				ast.ast1949.utils.Msg("","选定的记录已被删除!");
				grid.getStore().reload();
			}else{
				ast.ast1949.utils.Msg("","所选记录删除失败!");
			}
		}
	});
}
	
/**
 * 更新处理状态
 * @param {} grid
 * @param {} status 0 未处理；1 处理中；2 已处理。
 */
function doChangeExportStatus(grid,status){

	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}
	//提交
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/inquiry/changeExportStatus.htm?tp="+timestamp()+"&exportStatus="+status+"&inquiryArray="+_ids.join(','),
		method : "get",
		scope : this,
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
				ast.ast1949.utils.Msg("","设置成功!");
				grid.getStore().reload();
			}else{
				ast.ast1949.utils.Msg("","设置失败!");
			}
		}
	});
}
