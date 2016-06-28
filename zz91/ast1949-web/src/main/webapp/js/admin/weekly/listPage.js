Ext.namespace("ast.ast1949.admin.weekly");

var _B = new function() {
	this.RESULT_GRID="resultgrid";
	this.EDIT_FORM="pageeditform";
	this.PAGE_EDIT_WIN="pageeditwin";
	this.PAGE_ADD_FORM="pageaddform";
	this.PAGE_ADD_WIN="pageaddwin";
}

ast.ast1949.admin.weekly.listPage=function(_cfg){
	if(_cfg==null){
		_cfg={};
	}
	var _roleList=_cfg["roleList"]||null;

	var queryWin = null;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "id",
		sortable : true,
		dataIndex : 'id',
		hidden:true
	},{
		header : "版号",
		sortable : true,
		dataIndex : "pageNum",
		width:50
	},{
		header : "版面名称",
		sortable:true,
		dataIndex : "name",
		width:80
	}]);

	var reader = ["id","name","pageNum","periodicalId"];

	var storeUrl = Context.ROOT +Context.PATH+ "/admin/weekly/listPage.htm";

	var grid = new ast.ast1949.StandardGridPanelNoPage({
		sm: sm,
		cm: cm,
		reader : reader,
		storeUrl : storeUrl,
		id:"pageGrid",
		tbar : new Ext.Toolbar({
			items:[{
				text:"添加",
				iconCls : "add",
				handler :function(){
                   ast.ast1949.admin.weekly.addFormWin1();
				}
			},{
			   text:"修改",
               iconCls:"edit",			
 			   handler:function(){
					var row = grid.getSelections();		
					if(row.length==0){
						return false;
					}
					else if(row.length>1){
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "最多只能选择一条记录！",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					} else {
						var _id=row[0].get("id");
						ast.ast1949.admin.weekly.editFormWin1(_id)
					}
 			   }
			  },{
				text:"删除",
				iconCls : "delete",
				handler :function(){		
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		 		else
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的'
							+ sm.getCount() + '条记录?', doDelete1);
				}
			}]
		})
	});

	return grid;
}

ast.ast1949.admin.weekly.editForm1=Ext.extend(Ext.form.FormPanel, {
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
					anchor:"85%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					id:"periodicalId",
					name:"periodicalId"
				},{
					xtype:"combo",
					id:"test",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"期刊名称",
					anchor:"65%",
					displayField : "name",
					valueField : "id",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "name", "id" ],
						autoLoad:false,
						url : Context.ROOT + Context.PATH+ "/admin/weekly/allWeeklyPeriodcal.htm"
					}),
					listeners:{
						"blur":function(field){
							Ext.get("periodicalId").dom.value=field.getValue();	
						}
					}	
				},{
					name:"pageNum",
					anchor:"65%",
					fieldLabel:"排序:"
				},{
					name:"name",
					fieldLabel:"版面名称:",
					anchor:"85%",
					allowBlank:false
				}]
			}],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(_B.PAGE_EDIT_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.weekly.editForm1.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"weeklyPageDO.id"},
			{name:"name",mapping:"weeklyPageDO.name"},
			{name:"periodicalId",mapping:"weeklyPageDO.periodicalId"},
			{name:"pageNum",mapping:"weeklyPageDO.pageNum"},"perdicalName"];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/weekly/initPage.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("test").dom.value=record.get("perdicalName");
//						Ext.get("id").dom.value="";
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/weekly/addPage.htm",
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
			
		Ext.getCmp("pageGrid").getStore().reload();
		Ext.getCmp(_B.PAGE_EDIT_WIN).close();
		//ast.ast1949.admin.weekly.resultGridReload();
	//
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

//编辑标签窗口
ast.ast1949.admin.weekly.addFormWin1=function(){	
	var form = new ast.ast1949.admin.weekly.editForm1({
		id:_B.PAGE_ADD_FORM,
		region:"center"
	});	
		var win = new Ext.Window({
		id:_B.PAGE_EDIT_WIN,
		title:"添加",
		width:"60%",
		modal:true,
		items:[form]
	});
	win.show();
};

//编辑标签窗口
ast.ast1949.admin.weekly.editFormWin1=function(id){
		
	var form = new ast.ast1949.admin.weekly.editForm1({
		id:_B.EDIT_FORM,
		region:"center",
		saveUrl:Context.ROOT+Context.PATH + "/admin/weekly/updatePage.htm"
	});
		var win = new Ext.Window({
		id:_B.PAGE_EDIT_WIN,
		title:"修改标签",
		width:"60%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};

//直接删除
	function doDelete1(_btn){
		if(_btn != "yes")
			return ;
		var grid = Ext.getCmp("pageGrid");
		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			_ids.push(_id);
		}
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/weekly/deletePage.htm?ids="+_ids.join(','),
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var a=Ext.decode(response.responseText);
				if(success){
					Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
					grid.getStore().reload();
				}else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
				}
			}
		});
	}
