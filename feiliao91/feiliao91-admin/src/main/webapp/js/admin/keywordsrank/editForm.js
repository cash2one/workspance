Ext.namespace("ast.ast1949.admin.keywordsrank");
//定义一个添加,继承自父窗体类

ast.ast1949.admin.keywordsrank.EditFormWin = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);

	var _id = this["id"] || "";
	var _grid = this["grid"] || "";

	var title = "修改信息";
	var url = Context.ROOT+Context.PATH + "/admin/keywordsrank/updateKeywordsRank.htm";
	var isView = true;
	var notView = false;
	var win = new ast.ast1949.admin.keywordsrank.InfoFormWin({
		title:title,
		view:isView,
		nView:notView,
		listeners:{
			"saveSuccess" : onSaveSuccess,
			"saveFailure" : onSaveFailure,
			"submitFailure" : onSubmitFailure
		}
	});

	win.show();
	win.maximize();
	win.initFocus();

	var _store = new Ext.data.JsonStore({
		root:"records",
		fields:[{name:"id",mapping:"id"},
				{name:"productId",mapping:"productId"},
		        {name:"name",mapping:"name"},
		        {name:"startTime",mapping:"startTime",
		        	convert:function(value) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}},
		        {name:"endTime",mapping:"endTime",
		        	convert:function(value) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}},
				{name:"gmtCreated",mapping:"gmtCreated",
		        	convert:function(value) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}},
		        {name:"gmtModified",mapping:"gmtModified",
		        	convert:function(value) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}},
		        {name:"isChecked",mapping:"isChecked"}
		        ],
		url : Context.ROOT+Context.PATH + "/admin/keywordsrank/queryKeywordsRankById.htm?id="+_id,
		autoLoad:true,
		listeners:{
			"datachanged": function(){
				var record = _store.getAt(0);
				if(record == null){
					Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载错误,请联系管理员!");
					win.close();
				}else{
					win.loadRecord(record);
				}
			}
		}
	});

	Ext.get("save").on("click",function(){
		win.submit(url);
	});

	Ext.get("cancel").on("click",function(){
		win.close();
	});

	function onSaveSuccess(_form,_action){
		var res= Ext.decode(_action.response.responseText);
		if(res.success){
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "修改成功",
				fn:closeMe,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "修改失败",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
		}
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "修改失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

	function onSubmitFailure(){
//		alert("system");
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "验证失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

	function closeMe(){
		_grid.getStore().reload();
		win.close();
	}

}