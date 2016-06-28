Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.PHONELOGFIELD=[
	{name:"id",mapping:"phoneLog.id"},
	{name:"startTime",mapping:"phoneLog.startTime"},
	{name:"endTime",mapping:"phoneLog.endTime"},
	{name:"callerId",mapping:"phoneLog.callerId"},
	{name:"callFee",mapping:"phoneLog.callFee"},
	{name:"diffMinute",mapping:"diffMinute"},
	{name:"countcallFee",mapping:"countcallFee"},
	{name:"address",mapping:"phoneLog.address"},
	{name:"isBlack",mapping:"isBlack"}
 ];

ast.ast1949.phone.phoneLogGrid = Ext.extend(Ext.grid.GridPanel,{
	
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header : "开始通话时间",
				name : "startTime",
				id : "startTime",
				width:150,
				sortable:false,
				dataIndex:"startTime",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
//					console.log(value);
//					console.log(metadata);
//					console.log(record);
//					console.log(rowIndex);
//					console.log(colIndex);
//					console.log(store);
					if(value!=null){
						if(record.data.isBlack==1){
							//变颜色
							var val = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
							return '<div style="background:#E63F00;">'+val+'</div>';
						}else{
							return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
						}
					}else{
						return "总计:";
					}
				}
			},{
				header : "结束通话时间",
				name : "endTime",
				id : "endTime",
				width:150,
				sortable:false,
				dataIndex:"endTime",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						if(record.data.isBlack==1){
							//变颜色
							var val = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
							return '<div style="background:#E63F00;">'+val+'</div>';
						}else{
							return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
						}
					}
					else{
						return "";
					}
				}
				
			},{
				header : "通话时长",
				width : 100,
				sortable : false,
				dataIndex : "diffMinute",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value.length>0){
						if(record.data.isBlack==1){
							//变颜色
							var val = value+"分钟";
							return '<div style="background:#E63F00;">'+val+'</div>';
						}else{
							return value+"分钟";
						}
					}else{
						return "";
					}
				}
			},{
				header : "来电号码",
				width : 200,
				sortable : false,
				dataIndex : "callerId",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(record.data.isBlack==1){
						//变颜色
						return '<div style="background:#E63F00;">'+value+'</div>';
					}else{
						return value;
					}
				}
			},{
				header : "通话费用",
				width : 200,
				sortable : false,
				dataIndex : "callFee",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				   if(value!=null){
					   if(record.data.isBlack==1){
							//变颜色
						   var val = value+"元";
							return '<div style="background:#E63F00;">'+val+'</div>';
						}else{
							return value + "元";   
						}
				   }
				} 
			},{
				header : "来电地区",
				width : 200,
				sortable : false,
				dataIndex : "address",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(record.data.isBlack==1){
						//变颜色
						return '<div style="background:#E63F00;">'+value+'</div>';
					}else{
						return value;
					}
				}
			}			
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryphoneLog.htm";
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.PHONELOGFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [
		    "查看时间：",{
            	id:"targetFrom",
            	xtype:"datefield",
            	format:"Y-m-d"
            },"至",{
            	id:"targetTo",
            	xtype:"datefield",
            	format:"Y-m-d"
            },"消费明细",{
				xtype:"combo",
				mode:"local",
				triggerAction:"all",
				id:"callSnCode",
				emptyText:"按消费明细搜索",
				allowBlank:false,
				itemCls:"required",
				store:[
					["2","月租明细"],
					["1","通话消费明细"],
					["3","已接电话"],
					["4","未接电话"],
					[,"全部"] 
				]
			},{
			    id:"companyId",
            	text:"公司id",
            	hidden:true,
            	xtype:"button"
			},{
            	text:"查询",
            	iconCls:"query",
            	handler:function(btn){
            		//TODO 查找数据
            		var targetFrom=Ext.getCmp("targetFrom").getValue();
            		var targetTo=Ext.getCmp("targetTo").getValue();
            		var callSnCode=Ext.getCmp("callSnCode").getValue();
            			_store.baseParams["from"]=targetFrom;
            			_store.baseParams["to"]=targetTo;
            			_store.baseParams["callSnCode"]=callSnCode;
            			_store.reload();
            	
            	}
            },"-",{
            	iconCls:"add",
				text:"导出excel",
				handler:function(btn){
					var from=Ext.get("targetFrom").getValue();
					var to=Ext.get("targetTo").getValue();
					var companyId=Ext.getCmp("companyId").text;
					if(!companyId){
						companyId=0;
					}
					Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
						if(btn!="yes"){
							return ;
						}else{
							Ext.Ajax.request({
								url: window.open(Context.ROOT+Context.PATH+ "/phone/exportPhoneLog.htm?"+"companyId="+companyId+"&from="+from+"&to="+to),
							});
						}
					});
				}
            },"->","-","拉黑原因：",{
            	xtype:"combo",
				mode:"local",
				triggerAction:"all",
				id:"blackReason",
				emptyText:"标注为无效电话",
				allowBlank:false,
				itemCls:"required",
				store:[
					["1","卖药的"],
					["2","打广告的"],
					["3","跨行业的"],
					["4","海运推广"],
					["5","同行网站"], 
					["6","号码拨错"], 
					["7","拨打落地号"], 
				]
            },{
            	text:"确认",
            	iconCls:"item-true",
            	handler:function(btn){
            		var row = grid.getSelections();
            		var _ids = new Array();
            		var callers = new Array();
            		var blackReason = Ext.getCmp("blackReason");
            		if(row.length==0){
            			Ext.MessageBox.alert(Context.MSG_TITLE,"请选择你要设置为无效的通话记录");
            			return false;
            		}
            		if(blackReason.getValue()==""){
            			Ext.MessageBox.alert(Context.MSG_TITLE,"请选择无效原因");
            			return false;
            		}
            		for (var i=0,len = row.length;i<len;i++){
            			_ids.push(row[i].get("id"));
            			callers.push(row[i].get("callerId"));
            		}
            		Ext.Ajax.request({
            			url: Context.ROOT+Context.PATH+"/phone/insertPhoneBlackList.htm",
            			params: {
            				ids: _ids.join(","),
            				callers:callers.join(","),
            				blackReason:blackReason.getValue()
            			},
            			success: function(response,opt){
            				var obj = Ext.decode(response.responseText);
            				if(obj.success){
            					Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
            					grid.getStore().load();
            				}else{
            					Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
            				}
            			}
            		});
            	}
            }
		
//		{
//			text:"添加新号码",
//			iconCls:"add",
//			handler:function(){
//				ast.ast1949.phone.updatePhoneInfo(0);
//			}
//		},"-",{
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
//		},"-",{
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
			id:"phoneLogGrid",
			features:[{
				ftype: "groupingsummary",
				groupHeaderTpl: '{name}',
				hideGroupedHeader: false,
				enableGroupingMenu: false
			}],
			
			loadMask:Context.LOADMASK,
			sm : sm,
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
			
		};
		ast.ast1949.phone.phoneLogGrid.superclass.constructor.call(this,c);
	},
	loadPhoneLogRecord:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
		var B=this.getStore().baseParams;
		B=B||{};
		B["companyId"]=companyId;
		this.getStore().baseParams=B;
		this.getStore().reload({});
	},
	initDate:function(from, to,companyId){
		if(from==null || to==null){
			from=new Date();
			to=new Date();
		}
		Ext.getCmp("targetFrom").setValue(from);
		Ext.getCmp("targetTo").setValue(to);
		Ext.getCmp("companyId").setText(companyId);
	},
	
});
ast.ast1949.phone.PHONERATEFIELD=[
	{name:"stringFellName",mapping:"stringFellName"},
	{name:"allFee",mapping:"allFee"}
];

ast.ast1949.phone.phoneLogRateGrid = Ext.extend(Ext.grid.GridPanel,{
	
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				
				id : "stringFellName",
				width:150,
				sortable:false,
				dataIndex:"stringFellName",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					return value;
				}
			},{
				id : "stringFellName",
				width:150,
				sortable:false,
				dataIndex:"allFee",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(record.get("stringFellName")=="接通率"){
					    var val=value*100;
					   	return val.toFixed(2)+"%";
					}else{
						return value;
					}
				}
				
			}			
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryCallPhoneRate.htm";
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.PHONERATEFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [
		    "查看时间：",{
            	id:"phoneTargetFrom",
            	xtype:"datefield",
            	format:"Y-m-d"
            },"至",{
            	id:"phoneTargetTo",
            	xtype:"datefield",
            	format:"Y-m-d"
            },{
            	text:"接通率查询",
            	iconCls:"query",
            	handler:function(btn){
            		//TODO 查找数据
            		var targetFrom=Ext.getCmp("phoneTargetFrom").getValue();
            		var targetTo=Ext.getCmp("phoneTargetTo").getValue();
            			_store.baseParams["from"]=targetFrom;
            			_store.baseParams["to"]=targetTo;
            			_store.reload();
            	
            	}
            }
		

		];

		var c={
			id:"phoneLogsGrid",
			features:[{
				ftype: "groupingsummary",
				groupHeaderTpl: '{name}',
				hideGroupedHeader: false,
				enableGroupingMenu: false
			}],
			
			loadMask:Context.LOADMASK,
			sm : sm,
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
			
		};
		ast.ast1949.phone.phoneLogRateGrid.superclass.constructor.call(this,c);
	},
	loadPhoneRateRecord:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
		var B=this.getStore().baseParams;
		B=B||{};
		B["companyId"]=companyId;
		this.getStore().baseParams=B;
		this.getStore().reload({});
	},
	initDate:function(from, to){
		if(from==null || to==null){
			from=new Date();
			to=new Date();
		}
		Ext.getCmp("phoneTargetFrom").setValue(from);
		Ext.getCmp("phoneTargetTo").setValue(to);
	},
});



