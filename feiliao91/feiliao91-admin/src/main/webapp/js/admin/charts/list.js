Ext.namespace("ast.ast1949.admin.charts")

//定义变量
var _C = new function() {
	this.RESULT_GRID="resultgrid";
	this.CHARTS_ADD_FORM="chartsaddform";
	this.CHARTS_EDIT_WIN="chartseditwin";
}

Ext.onReady(function() {
	//加载列表
	var resultgrid = new ast.ast1949.admin.charts.ResultGrid({
		title:"走势图列表",
		id:_C.RESULT_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/charts/getChartsInfoList.htm",
		region:'center',
		autoScroll:true
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[resultgrid]
	});
});

//信息列表
ast.ast1949.admin.charts.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id"
			},{
				header:"标题",
				sortable:false,
				dataIndex:"title"
			},{
				header:"列别名称",
				sortable:false,
				dataIndex:"categoryName"
			},{
				header:"报价时间",
				sortable:false,
				dataIndex:"gmtDate",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
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
			}),
			listeners:{
				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.charts.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"chartsInfo.id"},
		{name:"title",mapping:"chartsInfo.title"},
		{name:"gmtDate",mapping:"chartsInfo.gmtDate"},
		{name:"categoryName",mapping:"categoryName"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/charts/getChartsInfoList.htm",
	mytoolbar:[{
		xtype:"label",
		text:"名称："
	},{
		xtype:"textfield",
		id:"name",
		name:"name",
		width:160
	},{
		xtype:"label",
		text:"类别："
	},{
		xtype:"combo",
		id:"PERIODICAL_LIST",
//		value:CONST.PARENT_NAME,
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
		displayField:"name"
	},"-",{
		iconCls:"query",
		text:"搜索",
		handler:function(btn){
			var resultgrid = Ext.getCmp(_C.RESULT_GRID);

			resultgrid.store.baseParams={};
			resultgrid.store.baseParams = {"name":Ext.get("name").dom.value};
			//定位到第一页
			resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		}
	}],
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:[{
						iconCls:"add",
						text:"添加",
						handler:function(btn){
							ast.ast1949.admin.charts.addFormWin();
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
//								alert(_id)
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

//标签表单
ast.ast1949.admin.charts.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
//					xtype:"textarea",
					name:"title",
					fieldLabel:"标题:",
					allowBlank:true
				},{
					xtype:"combo",
					fieldLabel : "报价类别",
					itemCls :"required",
					allowBlank : false,
//					id:"chartCategoryId",
					name:"chartCategoryId",
//					value:CONST.PARENT_NAME,
					triggerAction:"all",
					typeAhead: true,
					mode: "remote",
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
							document.getElementById("edittable").src=Context.ROOT+Context.PATH+"/admin/charts/table.htm?id="+newValue;
//							alert(document.getElementById("edittable").src);
						}
					}
				},{
					xtype : "datefield",
					fieldLabel : "报价时间",
					allowBlank : false,
					itemCls :"required",
					format : 'Y-m-d H:i:s',
					name : "gmtDate",
					tabIndex : 3,
					anchor : "95%",
					value:new Date(),
					blankText : "报价时间不能为空"
				},{
					xtype:"panel",
					id:"contentpanel",
					html:'<iframe id="edittable" src="'+Context.ROOT+Context.PATH+'/admin/charts/table.htm" frameBorder="0" marginHeight="0" marginWidth="0" scrolling="No" width="700" height="260"></iframe>'
				}]
			}],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(_C.CHARTS_EDIT_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.charts.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"name",mapping:"name"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/charts/getSingleRecord.htm",
			baseParams:{"id":id},
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
		})
	},
	
	saveUrl:Context.ROOT+Context.PATH + "/admin/charts/add.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		ast.ast1949.admin.charts.resultGridReload();
		Ext.getCmp(_C.CHARTS_EDIT_WIN).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

});

//添加标签窗口
ast.ast1949.admin.charts.addFormWin=function(){
		
	var form = new ast.ast1949.admin.charts.editForm({
		id:_C.CHARTS_ADD_FORM,
		region:"center"
	});
	
		var win = new Ext.Window({
		id:_C.CHARTS_EDIT_WIN,
		title:"添加走势图",
		width:"80%",
		modal:true,
//		autoHeight:true,
//		maximizable:true,
		items:[form]
	});
	win.show();
};

//重新绑定Grid数据
ast.ast1949.admin.charts.resultGridReload=function (){
	var resultgrid = Ext.getCmp(_C.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}