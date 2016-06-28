Ext.namespace("ast.ast1949.admin.keywordsrank");

ast.ast1949.admin.keywordsrank.listKeywordSrank = function(_cfg){
	if(_cfg==null){
		_cfg={};
	}
	Ext.apply(this, _cfg);
	var _productId = this["productId"] || "";
	var _productTitle = this["productTitle"] || "";
//	alert(_productTitle);
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "搜索关键字",
		sortable : false,
		dataIndex : "name"
	},{
		header : "开始时间",
		sortable : false,
		dataIndex : "startTime",
		renderer : function (value,metadata,record,rowIndex,colIndex,store){
		return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
		}
	},{
		header : "结束时间",
		sortable : false,
		dataIndex : "endTime",
		renderer : function (value,metadata,record,rowIndex,colIndex,store){
		return Ext.util.Format.date(new Date(value.time),'Y-m-d h:m:s');
		}
	},{
		header : "审核",
		width : 40,
		sortable : false,
		dataIndex : "isChecked",
		renderer : function (value,metadata,record,rowIndex,colIndex,store){
			if(value==1){
				return "审核";
			}else{
				return "未审核"
			}
		},
		editor: new Ext.form.ComboBox({
			        store: new Ext.data.JsonStore({
		        		root:"isChecked",
				        fields: ['k', 'v'],
				        data : ast.ast1949.statesdata.common
				    }),
		        displayField:'v',
		        valueField:"k",
		        typeAhead: true,
		        mode: 'local',
		        forceSelection: true,
		        triggerAction: 'all',
		        selectOnFocus:true,
		
		        hiddenId:"isChecked",
		        hiddenName:"isChecked",
		        emptyText:"",
		        width: 120
		    })
	}
	]);
	var reader = ["id","productId","name","startTime",
	              "endTime","isChecked"];

	var storeUrl = Context.ROOT 
		+Context.PATH+ "/admin/keywordsrank/selectKeywordsRankByProductId.htm?id="+_productId;
	var tbarName = "关键字匹配列表";
	
	var _store = new Ext.data.JsonStore({
		root:"records",
		remoteSort:false,
		fields:reader,
		url: storeUrl,
		autoLoad:true
	});
	var grid = new Ext.grid.EditorGridPanel({
		id:"listKeywordSrank",
		sm: sm,
		cm: cm,
		store: _store,
		clicksToEdit:1,
		height:350,
		anchor : "95%",
		frame:true,
		selModel: new Ext.grid.RowSelectionModel({
			moveEditorOnEnter :false,
			singleSelect:true   //配置是否单选模式
		}),
		viewConfig:{
			autoFill :true
		},
		tbar : new Ext.Toolbar({
			items:[tbarName,"-",{
				text:"删除",
				iconCls : "delete",
				handler : onDelete
			}]
		})
	});
	grid.on("afteredit",function(event){
		var id=event.record .get("id");
		var val = event.value;
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT
				+Context.PATH+ "/admin/keywordsrank/updateKeywordsRank.htm?id="+id+"&val="+val,
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var res= Ext.decode(response.responseText);
				if(res.success){
					Ext.MessageBox.alert(Context.MSG_TITLE,"记录修改成功!");
					grid.getStore().reload();
				}else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录修改失败!");
				}
			}
		});
	});
	
	function onDelete(){
		// 实际情况需要判断选定记录
		if (sm.getCount() == 0)
			Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
		else
			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的' + sm.getCount()
									+ '条记录?', function(btn){
				if(btn=="yes")
					doDelete();
			});
	}
	function doDelete(){
		var row = grid.getSelections();
		var _ids = new Array();
		for (var i=0,len = row.length;i<len;i++){
			var _id=row[i].get("id");
			_ids.push(_id);
		}

		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT
				+Context.PATH + "/admin/keywordsrank/deleteKeywordsRank.htm?ids="+_ids.join(','),
			method : "get",
			scope : this,
			callback : function(options,success,response){
			var res= Ext.decode(response.responseText);
				if(res.success){
					grid.getStore().reload();
				}else{
					Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
				}
			}
		});
	}
	return grid;
}