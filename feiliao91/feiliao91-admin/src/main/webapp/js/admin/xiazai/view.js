Ext.namespace("ast.ast1949.admin.xiazai")

//定义变量
var Const =new function(){
	this.CATEGORY_TREE1 = "categorytree1";
	this.CATEGORY_TREE2 = "categorytree2";
	this.CATEGORY_TREE3 = "categorytree3";
	this.XAIZAI_GRID="xiazaigridpanel";
	this.XAIZAI_WIN="xiazaiwin";
	this.XAIZAI_INFO_FORM="xiazaiinfoform";
}

//GridPanel Start
ast.ast1949.admin.xiazai.GridPanel = Ext.extend(Ext.grid.GridPanel,{
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

//		var _sm=new Ext.grid.CheckboxSelectionModel({
//			listeners: {
//	            selectionchange: function(sm) {
//	                if (sm.getCount()) {
//	                    Ext.getCmp("removeButton").enable();
//	                    Ext.getCmp("editButton").enable();
//	                    Ext.getCmp("recommend").enable();
//	                    Ext.getCmp("copyButton").enable();
//	                    Ext.getCmp("importButton").enable();
//	                    Ext.getCmp("checkButton").enable();
//	                    Ext.getCmp("unCheckButton").enable();
//	                    
//	                } else {
//	                    Ext.getCmp("removeButton").disable();
//	                    Ext.getCmp("editButton").disable();
//	                    Ext.getCmp("recommend").disable();
//	                    Ext.getCmp("copyButton").disable();
//	                    Ext.getCmp("importButton").disable();
//	                    Ext.getCmp("checkButton").disable();
//	                    Ext.getCmp("unCheckButton").disable();
//	                }
//	            }
//	        }
//		});
		var _sm = new Ext.grid.CheckboxSelectionModel();
		
		var _cm=new Ext.grid.ColumnModel([_sm,
		{
			header : "标题",
//			width : 60,
			sortable : false,
			dataIndex : "title",
			renderer:function(value,metadata,record,rowIndex,colndex,store){
				if(record!=null){
					var _id=record.get("id");
					var _url="";
					_url+="<a href='http://www.zz91.com/xiazai/detail"+_id+".htm' target='_blank'>"+value+"</a>";
					return _url;
				} else {
					return "";
				}
			}
		},{
			header : "主类别",
//			width : 30,
			sortable : false,
			dataIndex : "code"
			
		},{
			header : "文件大小",
			sortable : false,
			dataIndex : "size",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return value + "Kb";
				}
		},{
			header : "语言",
			dataIndex : "language"
		},{
			header:"文件类型",
			dataIndex:"type"
		},{
			header:"浏览数",
			width:50,
			dataIndex:"viewCount"
		},{
			header:"下载次数",
			width:55,
			dataIndex:"downloadCount"
		},{
			header:"添加者",
			width:50,
			dataIndex:"created_by"
		},{
			header : "发布时间",
//			width : 20,
			sortable : false,
			dataIndex : "gmt_created",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
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

		ast.ast1949.admin.xiazai.GridPanel.superclass.constructor.call(this,c);
	},
	//记录
	searchByChecked:function(){
		var grid = Ext.getCmp(Const.XAIZAI_GRID);
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
		{name:"id",mapping:"id"},
		{name:"title",mapping:"title"},
		{name:"detail",mapping:"detail"},
		{name:"code",mapping:"code"},
		{name:"fileUrl",mapping:"fileUrl"},
		{name:"picCover",mapping:"picCover"},
		{name:"picThumb",mapping:"picThumb"},
		{name:"size",mapping:"size"},
		{name:"language",mapping:"language"},
		{name:"type",mapping:"type"},
		{name:"viewCount",mapping:"viewCount"},
		{name:"downloadCount",mapping:"downloadCount"},
		{name:"created_by",mapping:"createdBy"},
		{name:"gmt_created",mapping:"gmtCreated"}
		]),
	listUrl:Context.ROOT+Context.PATH+ "/admin/xiazai/queryList.htm",
	mytoolbar:[
	{
		iconCls:"add",
		text:"添加",
		handler:function(btn){
			var grid = Ext.getCmp(Const.XAIZAI_GRID);
			var B = grid.store.baseParams;
			var code = B["code"];
			if(code==null){
				code = "";
			}
			var url=Context.ROOT+Context.PATH+"/admin/xiazai/add.htm?code="+code;
			window.open(url);
		}
	},{
		id:"editButton",
		iconCls:"item-edit",
		text:"修改",
		handler:function(btn){
			var grid = Ext.getCmp(Const.XAIZAI_GRID);
			var row = grid.getSelections();
			var selectedRecord = grid.getSelectionModel().getSelected();
			if(row.length>1){
				ast.ast1949.utils.Msg("","最多只能选择一条记录！");
			} else {
				var row = grid.getSelections();
				var _cid=row[0].get("id");
				var url=Context.ROOT+Context.PATH+"/admin/xiazai/add.htm?id="+_cid+"&ts="+Math.random();
				window.open(url);
			}
		}
	},{
		id:"removeButton",
		iconCls:"item-del",
		text:"删除",
		handler:function(btn){
			Ext.MessageBox.confirm(Context.MSG_TITLE, '你真的要删除所选记录?',function(btn){
				if(btn!="yes"){
					return ;
				}
				var grid = Ext.getCmp(Const.XAIZAI_GRID);
				var row = grid.getSelections();
				var selectedRecord = grid.getSelectionModel().getSelected();
				if(row.length>1){
					ast.ast1949.utils.Msg("","最多只能选择一条记录！");
				} else {
					var row = grid.getSelections();
					var _id=row[0].get("id");
					Ext.Ajax.request({
						url: Context.ROOT+Context.PATH+ "/admin/xiazai/doDel.htm",
						params:{"id":_id},
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
							ast.ast1949.utils.Msg("","删除失败！");
						}
					});
				}
			});
		}
	}
//	,{
//		id:"removeButton",
//		iconCls:"item-open",
//		text:"Pdf转化为Swf",
//		handler:function(btn){
//			Ext.MessageBox.confirm(Context.MSG_TITLE, '点击确定转换此文件为swf。处理过程可能有延迟现象，请耐心等待至提示成功后，再进行其他操作。以免系统容易卡死。',function(btn){
//				if(btn!="yes"){
//					return ;
//				}
//				var grid = Ext.getCmp(Const.XAIZAI_GRID);
//				var row = grid.getSelections();
//				var selectedRecord = grid.getSelectionModel().getSelected();
//				if(row.length>1){
//					ast.ast1949.utils.Msg("","最多只能选择一条记录！");
//				} else {
//					var row = grid.getSelections();
//					var _id=row[0].get("id");
//					Ext.Ajax.request({
//						url: Context.ROOT+Context.PATH+ "/admin/xiazai/doSwf.htm",
//						params:{"id":_id},
//						timeout:60000,
//						method : "post",
//						success:function(response,opt){
//							var obj = Ext.decode(response.responseText);
//							if(obj.success){
//								ast.ast1949.utils.Msg("","成功！庆了个祝！");
//								grid.getStore().reload();
//							}else{
//								ast.ast1949.utils.Msg("","转换失败！");
//							}
//						},
//						failure:function(response,opt){
//							ast.ast1949.utils.Msg("","转换失败！");
//						}
//					});
//				}
//			});
//		}
//	}
	,{
		id:"copyButton",
		iconCls:"copy",
		text:"复制",
		handler:function(btn){
			var grid = Ext.getCmp(Const.XAIZAI_GRID);
			var row = grid.getSelections();
			var selectedRecord = grid.getSelectionModel().getSelected();
			if(row.length>1){
				ast.ast1949.utils.Msg("","最多只能选择一条记录！");
			} else {
				var row = grid.getSelections();
				var _cid=row[0].get("id");
				var url=Context.ROOT+Context.PATH+"/admin/xiazai/add.htm?cid="+_cid+"&ts="+Math.random();
				window.open(url);
			}
		}
	},"->","-",{
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
				var grid = Ext.getCmp(Const.XAIZAI_GRID);
				var B = grid.store.baseParams;
				B = B||{};
				if(val!=""){
					B["title"] = c.getValue();
				}else{
					B["title"]=null;
				}
				grid.store.baseParams = B;
				grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			},
			//回车事件
			specialkey:function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var grid = Ext.getCmp(Const.XAIZAI_GRID);
					var B = grid.store.baseParams;
					B = B||{};
					if(Ext.get(field.getId()).dom.value!=""){
						B["title"] = field.getValue();
					}else{
						B["title"]=null;
					}
					grid.store.baseParams = B;
					grid.store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}
	}
//	," ",{
//		xtype:"checkbox",
//		boxLabel:"只显示已审核",
//		id:"uncheckBtn",
////		checked:true,
//		listeners:{
//			"check":function(field,newvalue,oldvalue){
//				var grid = Ext.getCmp(Const.XAIZAI_GRID);
//				grid.searchByChecked();
//			}
//		}
//	}
	]
	,
	exportPrice:function(){
		var _store = Ext.getCmp(Const.XAIZAI_GRID).getStore();
		var grid = Ext.getCmp(Const.XAIZAI_GRID);
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
ast.ast1949.admin.xiazai.resultGridReload=function (){
	var resultgrid = Ext.getCmp(Const.XAIZAI_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}