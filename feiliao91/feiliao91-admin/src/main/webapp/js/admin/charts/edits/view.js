Ext.namespace("ast.ast1949.admin.charts.edits")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.charts.edits.ResultGrid({
		title:"走势图列表",
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/charts/getCategoryList.htm?parentId="+CONST.PARENT_ID,
		region:'center',
		autoScroll:true
//		,
//		listeners:{
//			"afteredit":function(e){
//				alert(4375483);
//				if(e.originalValue==e.value){
//					return ;
//				}
//				Ext.Ajax.request({
//					url:Context.ROOT+Context.PATH+"/admin/charts/doUpdate.htm",
//					params:{
//						"id":e.record.get("id"),
//						"name":e.record.get("name"),
//						"orders":e.record.get("orders")
//					},
//					success:function(response,opt){
//						var obj = Ext.decode(response.responseText);
//						if(obj.success){
//							Ext.getCmp(_C.RESULT_GRID).getStore().reload();
//						}else{
//							Ext.MessageBox.show({
//								title:Context.MSG_TITLE,
//								msg : "发生错误,数据没有被更新",
//								buttons:Ext.MessageBox.OK,
//								icon:Ext.MessageBox.ERROR
//							});
//						}
//					},
//					failure:function(response,opt){
//						Ext.MessageBox.show({
//							title:Context.MSG_TITLE,
//							msg : "发生错误,数据没有被更新",
//							buttons:Ext.MessageBox.OK,
//							icon:Ext.MessageBox.ERROR
//						});
//					}
//				});
//			}
//		}
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[resultgrid]
	});

	resultgrid.on('afteredit', afterEdit, this );
	
});

function afterEdit(e) {
	
	if(e.originalValue==e.value){
		return ;
	}
	Ext.Ajax.request({
		url:Context.ROOT+Context.PATH+"/admin/charts/setChartDataValue.htm",
		params:{
			"chartInfoId":1,//TODO:获取走势图编号
			"chartCategoryId":e.record.get("id"),
			"name":e.grid.colModel.config[e.column].header,
			"value":e.record.get(e.field)
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			
			if(obj.success){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "数据更新成功",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.INFO
				});
//				Ext.getCmp(_C.RESULT_GRID).getStore().reload();
			}else{
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "发生错误,数据没有被更新",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			}
		},
		failure:function(response,opt){
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "发生错误,数据没有被更新",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	});
};

//信息列表
ast.ast1949.admin.charts.edits.ResultGrid = Ext.extend(Ext.grid.EditorGridPanel,{
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
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("editButton").enable();
	                    Ext.getCmp("deleteButton").enable();
	                } else {
	                    Ext.getCmp("editButton").disable();
	                    Ext.getCmp("deleteButton").disable();
	                }
	            }
	        }
		});
		
		/**
		 * 动态构造ColumnModel
		 */
		var cmItems = [];
		var cmConfig = {};
		
		cmItems.push(new Ext.grid.RowNumberer());
		//基础列
//		cmItems.push(_sm);
		cmItems.push({header:"编号",dataIndex:"id",hidden:true,sortable:true});
		cmItems.push({header:"金属类别",dataIndex:"name",sortable:false});
		//动态列
		var colM = "";
		colM=CONST.PARENT_SETTING;//Ext.get("settings").dom.value;
		
		var colMArr = colM.split(";");
		var colLength = colMArr.length;
		for(var i=0; i<colLength; i++) {
			cmItems.push({header:colMArr[i],dataIndex:"valve"+i,sortable:false,editor:new Ext.form.NumberField({allowBlank:false})});
		}
		
		var _cm = new Ext.grid.ColumnModel(cmItems);

//		var array=new Array();
//		var obj={};
//		obj.dataIndex="id";
//		array.push(obj);
//		//
//		cm=new Ext.grid.ColumnModel(array);
		
//		var _cm=new Ext.grid.ColumnModel([
//			{
//				id:"edit-id",
//				header:"编号",
//				dataIndex:"id",
//				sortable:true,
//				hidden:true
//			},{
//				header:"金属类别",
//				dataIndex:"name",
//				sortable:false
//			},{
//				id:"edit-name-1",
//				header:"分段一",
//				dataIndex:"name1",
//				sortable:false,
//				editor:new Ext.form.TextField({
//					allowBlank:false
//				})
//			},{
//				id:"edit-name-2",
//				header:"分段二",
//				dataIndex:"name2",
//				sortable:false,
//				editor:new Ext.form.TextField({
//					allowBlank:false
//				})
//			},{
//				id:"edit-name-3",
//				header:"分段三",
//				dataIndex:"name3",
//				sortable:false,
//				editor:new Ext.form.TextField({
//					allowBlank:false
//				})
//			}
//		]);
		
		var c={
			iconCls:"icon-grid",
			clicksToEdit:1,
			viewConfig:{
//				autoFill:true
			},
			store:_store,
//			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
//			bbar:new Ext.PagingToolbar({
//				pageSize : Context.PAGE_SIZE,
//				store : _store,
//				displayInfo: true,
//				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
//				emptyMsg : '没有可显示的记录',
//				beforePageText : '第',
//				afterPageText : '页,共{0}页',
//				paramNames : {start:"startIndex",limit:"pageSize"}
//			}),
			listeners:{
				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.charts.edits.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"chartCategory.id"},
		{name:"name",mapping:"chartCategory.name"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/charts/getCategoryList.htm?parentId="+CONST.PARENT_ID,
	mytoolbar:["->",{
		xtype:"combo",
		id:"PERIODICAL_LIST",
		value:CONST.PARENT_NAME,
		triggerAction:"all",
		typeAhead: true,
		mode: "remote",
		value:"请选择...",
		forceSelection :true,
		store:new Ext.data.JsonStore({
			autoLoad	: false,
			root		: "records",
			url			: Context.ROOT+Context.PATH+"/admin/charts/getCategoryList.htm",
			fields		: [{name:"id",mapping:"chartCategory.id"},{name:"name",mapping:"chartCategory.name"}]
		}),
		valueField:"id",
		displayField:"name",
		listeners :{
			"change":function(field,newValue,oldValue){
				window.location.href=Context.ROOT+Context.PATH+"/admin/charts/view.htm?id="+newValue;
//				var store= Ext.getCmp(_C.RESULT_GRID).getStore();
//				store.baseParams={"parentId":newValue};
//				store.reload();
			}
		}
//		onSelect:function(value){
////			alert(value)
//		}
	}],
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:[{
						iconCls:"add",
						text:"添加",
						handler:function(btn){
							window.open();
							//ast.ast1949.admin.bbs.tag.addFormWin();
						}
					},"-",{
						iconCls:"edit",
						id:"editButton",
						text:"修改",
						disabled:true,
						handler:function(btn){
							var grid = Ext.getCmp(_C.RESULT_GRID);
					
							var row = grid.getSelections();
							if(row.length>1){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "最多只能选择一条记录！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							} else {
								var _id=row[0].get("id");
								alert(_id)
								//ast.ast1949.admin.bbs.tag.editFormWin(_id)
							}
						}
					},"-",{
						iconCls:"delete",
						id:"deleteButton",
						text:"删除",
						disabled:true,
						handler:function(btn){
							Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
						}
					}]
		});
		
		tbar2.render(this.tbar);
	}
});

//重新绑定Grid数据
ast.ast1949.admin.charts.edits.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":100}});
}