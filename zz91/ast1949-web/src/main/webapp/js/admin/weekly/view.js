Ext.namespace("ast.ast1949.admin.weekly");
//定义变量
var _A = new function() {
	this.RESULT_GRID="resultgrid";
	this.EDIT_FORM="editform";
	this.PER_EDIT_WIN="editwin";
	this.ADD_FORM="addform";
	this.ADD_WIN="addwin";
}
Ext.onReady(function(){
	
	var _list=ast.ast1949.admin.weekly.listPeriodical();
	var _listRight=ast.ast1949.admin.weekly.listPage({list:_listRight});
	var _listRight2=ast.ast1949.admin.weekly.listWeekly({list:_listRight});
	
		var _left = {
	    	title: "期刊信息",
	    	id : _A.RESULT_GRID,
			region : "west",
			layout : "fit",
			margins:  "0 0 2 2",
			cmargins: "0 2 2 2",
			collapsible:true,
			split: true,
			width: 300,
			maxSize: 380,
			minSize:280,
			items : [_list]
		} ;

		var _right = {
			title: "版面信息",
			region : "center",
			layout : "fit",
			margins:  "0 0 2 2",
			cmargins: "0 2 2 2",
			collapsible:true,
			split: true,
			width: 300,
			maxSize: 380,
			minSize:280,
			items : [_listRight]
		} ;
		var _right2 = {
			title: "文章信息",
			region : "east",
			layout : "fit",
			collapsible:true,
			split: true,
			width: 300,
			maxSize: 380,
			minSize:280,
			items : [_listRight2]
		} ;

		var viewport = new Ext.Viewport({
			layout : "border",
			border : true,
			items:[_left,_right,_right2]
		});
	
		_list.on("rowclick",function(grid, rowIndex, columnIndex, e) {
	        var record = grid.getStore().getAt(rowIndex);
			_listRight.getStore().reload({
				params:{periodicalId:record.get("id")}
			});
	    });
		_listRight.on("rowclick",function(grid, rowIndex, columnIndex, e) {
		    var record = grid.getStore().getAt(rowIndex);
			
		_listRight2.getStore().reload({
			params:{pageId:record.get("id")}
			
		});
	           
		});

    function editRole(_url,_params){
    	var conn = new Ext.data.Connection();
		conn.request({
			url: _url,
			method : "POST",
			params : _params,
			scope : this,
			callback : function(options,success,response){
				if(success){
					var res = Ext.decode(response.responseText);
					if(!res.success){
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : res.data,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}else{
						//_roleList.getStore().reload();
					}
				}else{
					Ext.MessageBox.show({
						title:Context.MSG_TITLE,
						msg : "发生错误,请联系管理员!",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			}
		});
    }
});

ast.ast1949.admin.weekly.listPeriodical=function(){
	var _store = new Ext.data.JsonStore({
		root:"records",
		remoteSort:false,
		totalProperty : 'totalRecords',
		fields:["id","name","periodicalNum","publishTime"],
		url: Context.ROOT+Context.PATH+"/admin/weekly/periodicalList.htm",
		autoLoad:true
	});
	var sm=new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		sm,
		{
		   	id:"id",
			dataIndex:"id",
			hidden:true
		},{
		 	id:"periodicalNum",
			header:"期刊号",
			dataIndex:"periodicalNum",
			sortable:true,
			width:30
		},{
			id:"name",
			header:"名称",
			dataIndex:"name",
			sortable:true,
			width:60,
			editor:new Ext.form.TextField({
				allowBlank:false,
				blankText :"名称"
			})
		},{
			header : "出版时间",
			sortable : false,
		    dataIndex : "publishTime",
		    width:40,
			renderer : function(value, metadata, record, rowIndex,
					colIndex, store) {
				if (value != null) {
					return Ext.util.Format.date(
							new Date(value.time), 'Y-m-d');
				}
			}
		}
	]);

	var grid = new Ext.grid.EditorGridPanel({
		id:"cmp_grid",
		cm:cm,
		store: _store,
		clicksToEdit:2,
		selModel: new Ext.grid.RowSelectionModel({
			moveEditorOnEnter :false,
			singleSelect:true   //配置是否单选模式
		}),
		viewConfig:{
			autoFill :true
		},
		bbar: new Ext.PagingToolbar({
			pageSize : Context.PAGE_SIZE,
			store : _store,
			beforePageText : '第',
			afterPageText : '页,共{0}页',
			paramNames : {start:"startIndex",limit:"pageSize"}
		}),
		tbar: new Ext.Toolbar({
			items:[{
				text:"添加",
				iconCls : "add",
				handler :function(btn){
			      ast.ast1949.admin.weekly.addFormWin();
				}
			},{
				text:"修改",
				iconCls : "edit",
				handler:function(btn){
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
						ast.ast1949.admin.weekly.editFormWin(_id)
					}
				}
			},{
			  	text:"删除",
				iconCls:"delete",
				handler :function(){
				var row = grid.getSelections();	
				if (row.length == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		 		else
					Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的'
							+ row.length + '条记录?', doDelete);
				}
			}]
		})
	});
	return grid;
}
//标签表单
ast.ast1949.admin.weekly.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);	
		var c={
			labelAlign : "right",
			labelWidth : 90,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"80%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					name:"name",
					fieldLabel:"期刊名称:",
					anchor : "80%",
					allowBlank:false
				},{
					name:"publishTime",
					xtype : "datefield",
					fieldLabel:"出版时间:",
					anchor : "65%",
					value:new Date(),
					format : 'Y-m-d'
				},{
					name:"periodicalNum",
					fieldLabel:"刊号:",
					anchor : "65%",			
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
					Ext.getCmp(_A.PER_EDIT_WIN).close();
				},
				scope:this
			}]
		};
		
		ast.ast1949.admin.weekly.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"name",mapping:"name"},
			{name:"publishTime",mapping:"publishTime",
			convert : function(value) {
				return Ext.util.Format.date(new Date(value.time),
					'Y-m-d');
			}},
			{name:"periodicalNum",mapping:"periodicalNum"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/weekly/init.htm",
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
	saveUrl:Context.ROOT+Context.PATH + "/admin/weekly/addPeriodcal.htm",
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
		Ext.getCmp("cmp_grid").getStore().reload();
		Ext.getCmp(_A.PER_EDIT_WIN).close();
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

	//添加
	ast.ast1949.admin.weekly.addFormWin=function(){	
	
		var form = new ast.ast1949.admin.weekly.editForm({
			id:_A.ADD_FORM,
			region:"center"
		});	
		var win = new Ext.Window({
			id:_A.PER_EDIT_WIN,
			title:"添加期刊",
			width:"55%",
			modal:true,
			items:[form]
		});
		win.show();
	};

	//修改
	ast.ast1949.admin.weekly.editFormWin=function(id){
		var form = new ast.ast1949.admin.weekly.editForm({
			id:_A.EDIT_FORM,
			region:"center",
			saveUrl:Context.ROOT+Context.PATH + "/admin/weekly/updatePeriodcal.htm"
			});
			var win = new Ext.Window({
			id:_A.PER_EDIT_WIN,
			title:"修改",
			width:"60%",
			modal:true,
			items:[form]
		});
		form.loadRecords(id);
		win.show();
	};

	//直接删除
	function doDelete(_btn){
		if(_btn != "yes")
				return ;			
		var grid = Ext.getCmp("cmp_grid");		
		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			_ids.push(_id);
		}
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/weekly/deletePeriodcal.htm?ids="+_ids.join(','),
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

