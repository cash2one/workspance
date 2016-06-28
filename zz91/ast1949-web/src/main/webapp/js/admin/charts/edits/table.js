Ext.namespace("ast.ast1949.admin.charts.edits")

//定义变量
var TABLE = new function() {
	this.RESULT_GRID="resultgrid";
}

Ext.onReady(function() {
//	加载列表
	var resultgrid = new ast.ast1949.admin.charts.edits.ResultGrid({
		title:"走势图详细数据："+CONST.PARENT_NAME,
		id:TABLE.RESULT_GRID,
		region:'center',
		autoScroll:true
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[resultgrid]
	});

	resultgrid.on('afteredit', resultgrid.saveData);
	
});

//信息列表
ast.ast1949.admin.charts.edits.ResultGrid = Ext.extend(Ext.grid.EditorGridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =[
      		{name:"id",mapping:"chartCategory.id"},
    		{name:"name",mapping:"chartCategory.name"}
    	];
		var _url = this.listUrl;
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
		cmItems.push({header:"编号",dataIndex:"id",width:35,hidden:false,sortable:true});
		cmItems.push({header:"金属类别",dataIndex:"name",sortable:false});
		//动态列
		var colM = "";
		colM=CONST.PARENT_SETTING;//Ext.get("settings").dom.value;
		var colMArr = colM.split(";");
		var colLength = colMArr.length;
		for(var i=0; i<colLength; i++) {
			cmItems.push({header:colMArr[i],dataIndex:"col"+i,sortable:false,editor:new Ext.form.NumberField({allowBlank:false})});
			_fields.push({name:"col"+i,mapping:"colMap.col"+i});
		}
		var _cm = new Ext.grid.ColumnModel(cmItems);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:Ext.data.Record.create(_fields),
			url:_url,
			autoLoad:true
		});

		var c={
			clicksToEdit:1,
			store:_store,
			cm:_cm
		};
		
		ast.ast1949.admin.charts.edits.ResultGrid.superclass.constructor.call(this,c);
	},
	listUrl:Context.ROOT+Context.PATH+"/admin/charts/getCategoryList.htm?parentId="+CONST.PARENT_ID+"&chartInfoId="+CONST.CHARTID,
	saveData:function(e){
		if(e.originalValue==e.value){
			return ;
		}
		Ext.Ajax.request({
			url:Context.ROOT+Context.PATH+"/admin/charts/setChartDataValue.htm",
			params:{
				"chartInfoId":CONST.CHARTID,
				"chartCategoryId":e.record.get("id"),
				"name":e.grid.colModel.config[e.column].header,
				"value":e.record.get(e.field)
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				
				if(obj.success){
					ast.ast1949.utils.Msg("","数据已更新");
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
	},
});

//重新绑定Grid数据
ast.ast1949.admin.charts.edits.resultGridReload=function (){
	var resultgrid = Ext.getCmp(TABLE.RESULT_GRID);
	//定位到第一页
	resultgrid.store.reload({params:{"startIndex":0,"pageSize":100}});
}