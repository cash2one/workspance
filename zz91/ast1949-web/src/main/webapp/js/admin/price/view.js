Ext.namespace("ast.ast1949.admin.price")

//定义变量
var Const =new function(){
	this.CATEGORY_TREE1 = "categorytree1";
	this.CATEGORY_TREE2 = "categorytree2";
	this.CATEGORY_TREE3 = "categorytree3";
	this.PRICE_GRID="pricegridpanel";
	this.PRICE_WIN="pricewin";
	this.PRICE_INFO_FORM="priceinfoform";
}

//GridPanel Start
ast.ast1949.admin.price.GridPanel = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.porductRecord;
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

		var _sm=new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("removeButton").enable();
	                    Ext.getCmp("editButton").enable();
	                    Ext.getCmp("recommend").enable();
	                    Ext.getCmp("copyButton").enable();
	                    Ext.getCmp("importButton").enable();
	                    Ext.getCmp("checkButton").enable();
	                    Ext.getCmp("unCheckButton").enable();
	                    
	                } else {
	                    Ext.getCmp("removeButton").disable();
	                    Ext.getCmp("editButton").disable();
	                    Ext.getCmp("recommend").disable();
	                    Ext.getCmp("copyButton").disable();
	                    Ext.getCmp("importButton").disable();
	                    Ext.getCmp("checkButton").disable();
	                    Ext.getCmp("unCheckButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header : "审核状态",
				sortable : false,
				dataIndex : "isChecked",
				width : 10,
				renderer:function (value,metadata,record,rowIndex,colIndex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
		},{
			header : "标题",
//			width : 60,
			sortable : false,
			dataIndex : "title",
			renderer:function(value,metadata,record,rowIndex,colndex,store){
				if(record!=null){
					var _id=record.get("id");
					var _isIssue=record.get("isIssue");
					var _url="";
//					if(_isIssue!="1"){
//						_url+="<span style='color:Red'>[草稿]&nbsp;</span>"
//					}
					_url+="<a href='http://jiage.zz91.com/detail/"+_id+".html' target='_blank'>"+value+"</a>";
					
					return _url;
				} else {
					return "";
				}
			}
		},{
			header : "主类别",
//			width : 30,
			sortable : false,
			dataIndex : "typeName"
			
		},{
			header : "流量",
			width : 30,
			sortable : false,
			dataIndex : "realClickNumber",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return value;
					}
					else{
						return 0;
					}
				}
		},{
			header : "UV",
			width : 20,
			sortable : false,
			dataIndex : "ip",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return value;
					}
					else{
						return 0;
					}
				}
		},{
			header : "排序时间",
//			width : 20,
			sortable : false,
			dataIndex : "gmtOrder",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i');
					}
					else{
						return "";
					}
				}
		},{
			header : "发布时间",
//			width : 20,
			sortable : false,
			dataIndex : "gmtCreated",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i');
					}
					else{
						return "";
					}
				}
		}]);

		var c = {
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
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
			})
		};

		ast.ast1949.admin.price.GridPanel.superclass.constructor.call(this,c);
	},
	//记录
	searchByChecked:function(){
		var grid = Ext.getCmp(Const.PRICE_GRID);
		var B = grid.store.baseParams;
		B = B||{};
		if(Ext.getCmp("uncheckBtn").getValue()){
			B["isChecked"]="1";
		} else {
			B["isChecked"]=null;
		}
		grid.store.baseParams = B;
		grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	porductRecord:Ext.data.Record.create([
		{name:"id",mapping:"price.id"},
		{name:"title",mapping:"price.title"},
//		{name:"tags",mapping:"price.tags"},
		{name:"isChecked",mapping:"price.isChecked"},
		{name:"realClickNumber",mapping:"price.realClickNumber"},
		{name:"ip",mapping:"ip"},
		{name:"gmtOrder",mapping:"price.gmtOrder"},
		{name:"gmtCreated",mapping:"price.gmtCreated"},
		{name:"isIssue",mapping:"price.isIssue"},
		"typeName"
		]),
	listUrl:Context.ROOT+Context.PATH+ "/admin/price/query.htm",
	mytoolbar:[
	{
		iconCls:"add",
		text:"添加",
		handler:function(btn){
			var url=Context.ROOT+Context.PATH+"/admin/price/edit.htm?ts="+Math.random();
			window.open(url);
		}
	},
	{
		text:"编辑",
		iconCls : 'edit',
		menu:[
			{
				id:"editButton",
				iconCls:"item-edit",
				text:"修改",
				disabled:true,
				handler:function(btn){
					var grid = Ext.getCmp(Const.PRICE_GRID);
					var row = grid.getSelections();
					//var selectedRecord = grid.getSelectionModel().getSelected();
					//if(row.length<0){
						//ast.ast1949.utils.Msg("","请选择要编辑的记录！");
					//} else {
						for(var i=0;i<row.length;i++){
						//var row = grid.getSelections();
						//var _cid=row[0].get("id");
							var url=Context.ROOT+Context.PATH+"/admin/price/edit.htm?priceId="+row[i].get("id")+"&ts="+Math.random();
							window.open(url);
						}
					//}
				}
			},
			{
				id:"copyButton",
				iconCls:"copy",
				text:"复制",
				disabled:true,
				handler:function(btn){
					var grid = Ext.getCmp(Const.PRICE_GRID);
		
					var row = grid.getSelections();
					var selectedRecord = grid.getSelectionModel().getSelected();
					if(row.length>1){
						ast.ast1949.utils.Msg("","最多只能选择一条记录！");
					} else {
						var row = grid.getSelections();
						var _cid=row[0].get("id");
						var url=Context.ROOT+Context.PATH+"/admin/price/edit.htm?id="+_cid+"&ts="+Math.random();
						window.open(url);
//						var _id=selectedRecord.get("id");
//					//	var _tages=selectedRecord.get("tags")
//						var url=Context.ROOT+Context.PATH+"/admin/price/edit.htm?id="+_id+"&ts="+Math.random();
//						window.open(url);
//					//	ast.ast1949.admin.price.CopyFormWin(selectedRecord.get("title"),selectedRecord.get("tags"));
					}
		
		
				}
			},
			{
				id:"recommend",
				iconCls:"add",
				text:"推荐",
				disabled:true,
				handler:function(btn){
					
					var grid = Ext.getCmp(Const.PRICE_GRID);
					var row = grid.getSelections();
					var _cid=row[0].get("id");
					
					var _url="price/priceDetails"+_cid+".htm";
					var _title=row[0].get("title");
					var url=Context.ROOT+Context.PATH+"/admin/dataindex/index.htm?url="+_url+"&title="+_title;
					window.open(url);
			
				}
			},
			{
				id:"removeButton",
				iconCls:"item-del",
				text:"删除",
				disabled: true,
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '你真的要删除所选记录?',function(btn){
						if(btn!="yes"){
							return ;
						}
		
						var grid = Ext.getCmp(Const.PRICE_GRID);
						var row = grid.getSelectionModel().getSelections();
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
		
						Ext.Ajax.request({
							url: Context.ROOT+Context.PATH+ "/admin/price/delete.htm",
							params:{"ids":_ids.join(",")},
							method : "post",
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									grid.getStore().reload();
								}else{
									ast.ast1949.utils.Msg("","删除失败！");
								}
							},
							failure:function(response,opt){
								st.ast1949.utils.Msg("","删除失败！");
							}
						});
					});
				}
			},{
				id:"importButton",
				iconCls:"copy",
				text:"导入数据",
				tooltip:"<a>从企业报价中获取报价信息。</a>",
				disabled:true,
				handler:function(btn){
					var grid = Ext.getCmp(Const.PRICE_GRID);
					var row = grid.getSelections();
					var selectedRecord = grid.getSelectionModel().getSelected();
					if(row.length>1){
						ast.ast1949.utils.Msg("","最多只能选择一条记录！");
					} else {
						var row = grid.getSelections();
						var _cid=row[0].get("id");
						var url=Context.ROOT+Context.PATH+"/admin/pricedata/view.htm?id="+_cid+"&ts="+Math.random();
						window.open(url);
					//	ast.ast1949.admin.price.EditFormWin(selectedRecord.get("id"));
					}
				}
			}
		]
		
	},{
		text:"审核",
		iconCls : 'doc_update',
		menu:[
			{
				id:"checkButton",
//				iconCls:"item-edit",
				text:"审核通过",
				disabled: true,
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '你真的要审核所选记录?',function(btn){
						if(btn!="yes"){
							return ;
						}
		
						var grid = Ext.getCmp(Const.PRICE_GRID);
						var row = grid.getSelectionModel().getSelections();
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
		
						Ext.Ajax.request({
							url: Context.ROOT+Context.PATH+ "/admin/price/check.htm",
							params:{"ids":_ids.join(","),"isChecked":"1"},
							method : "post",
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									ast.ast1949.utils.Msg("","审核成功！");
									grid.getStore().reload();
								}else{
									ast.ast1949.utils.Msg("","审核失败！");
								}
							},
							failure:function(response,opt){
								ast.ast1949.utils.Msg("","审核失败！");
							}
						});
					});
				}
			},
			{
				id:"unCheckButton",
//				iconCls:"item-edit",
				text:"取消审核",
				disabled: true,
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '你真的要取消审核所选记录?',function(btn){
						if(btn!="yes"){
							return ;
						}
		
						var grid = Ext.getCmp(Const.PRICE_GRID);
						var row = grid.getSelectionModel().getSelections();
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							_ids.push(_id);
						}
		
						Ext.Ajax.request({
							url: Context.ROOT+Context.PATH+ "/admin/price/check.htm",
							params:{"ids":_ids.join(","),"isChecked":"0"},
							method : "post",
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									ast.ast1949.utils.Msg("","审核成功！");
									grid.getStore().reload();
								}else{
									ast.ast1949.utils.Msg("","审核失败！");
								}
							},
							failure:function(response,opt){
								ast.ast1949.utils.Msg("","审核失败！");
							}
						});
					});
				}
			}
		]
	},{
		iconCls:"refresh",
		text:"刷新",
		handler:function(btn){
			var resultgrid = Ext.getCmp(Const.PRICE_GRID);
			resultgrid.store.baseParams = {};
			//定位到第一页
			resultgrid.store.reload({
				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		}
	},"->","导出数据开始时间：",{
    	id:"targetFrom",
    	xtype:"datefield",
    	format:"Y-m-d"
    },"导出数据结束时间：",{
    	id:"targetTo",
    	xtype:"datefield",
    	format:"Y-m-d"
    },{
    	iconCls:"add",
		text:"导出excel",
		handler:function(btn){
    		var grid = Ext.getCmp(Const.PRICE_GRID);
    		grid.exportPrice();
		}
	},"-",{
		xtype:"label",
		text:"标题："
	},{
		xtype:"textfield",
		id:"title",
		name:"title",
		width:160,
		listeners:{
			//失去焦点
			"blur":function(c){
				var val=Ext.get("title").dom.value;
				var grid = Ext.getCmp(Const.PRICE_GRID);
				var B = grid.store.baseParams;
				B = B||{};
				if(val!=""){
					B["searchTitle"] = c.getValue();
				}else{
					B["searchTitle"]=null;
				}
				grid.store.baseParams = B;
				grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			},
			//回车事件
			specialkey:function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var grid = Ext.getCmp(Const.PRICE_GRID);
					var B = grid.store.baseParams;
					B = B||{};
					if(Ext.get(field.getId()).dom.value!=""){
						B["searchTitle"] = field.getValue();
					}else{
						B["searchTitle"]=null;
					}
					grid.store.baseParams = B;
					grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}
	}," ",{
		xtype:"combo",
		id:"isChecked",
		mode:"local",
		emptyText:"审核状态",
		fieldLabel:"审核状态：",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		autoSelect:true,
		width:80,
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'已审核',value:1},
				{name:'未审核',value:0}
			]
		}),
		listeners:{
			"blur":function(field){
				var grid = Ext.getCmp(Const.PRICE_GRID);
				var B = grid.store.baseParams;
				    B = B||{};
				if(Ext.get(field.getId()).dom.value!=""){
					B["isChecked"] = field.getValue();
				}else{
					B["isChecked"]=undefined;
				}
				grid.store.baseParams = B;
				grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			},
		      specialkey:function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var grid = Ext.getCmp(Const.PRICE_GRID);
					var B = grid.store.baseParams;
					B = B||{};
					if(Ext.get(field.getId()).dom.value!=""){
						B["isChecked"] = field.getValue();
					}else{
						B["isChecked"]=null;
					}
					grid.store.baseParams = B;
					grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}
	}]
	,
	exportPrice:function(){
		var _store = Ext.getCmp(Const.PRICE_GRID).getStore();
		var grid = Ext.getCmp(Const.PRICE_GRID);
		var B = _store.baseParams;
		var from=Ext.get("targetFrom").dom.value;
		var to=Ext.get("targetTo").dom.value;
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
			if(btn!="yes"){
				return ;
			}else{
				Ext.Ajax.request({
					url: window.open(Context.ROOT+Context.PATH+ "/admin/price/exportData.htm?" + "from="+from+"&to="+to),
				});
			}
		});
	}
});

//重新绑定Grid数据
ast.ast1949.admin.price.resultGridReload=function (){
	var resultgrid = Ext.getCmp(Const.PRICE_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}
