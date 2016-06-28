Ext.namespace("ast.ast1949.admin.crm.activity");
//定义一个添加,继承自父窗体类

ast.ast1949.admin.crm.activity.EditFormWin = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);

	var _id = this["id"] || "";
	var _grid = this["grid"] || "";

	var title = "编辑信息";
	var url = Context.ROOT +Context.PATH+ "/admin/crm/activity/update.htm";
	var isView = true;
	var notView = false;
	var win = new ast.ast1949.admin.crm.activity.InfoFormWin({
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
		fields:[{name:"id",mapping:"activityApply.id"},
				{name:"categoryCode",mapping:"activityApply.categoryCode"},
				{name:"email",mapping:"activityApply.email"},
				{name:"companyName",mapping:"activityApply.companyName"},
				{name:"contact",mapping:"activityApply.contact"},
				{name:"tel",mapping:"activityApply.tel"},
				{name:"mobile",mapping:"activityApply.mobile"},
				{name:"address",mapping:"activityApply.address"},
				{name:"gmtCreated",mapping:"activityApply.gmtCreated"},
				{name:"gmtModified",mapping:"activityApply.gmtModified"}],
		url : Context.ROOT+Context.PATH+ "/admin/crm/activity/getSingleRecord.htm?id="+_id,
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

	function onSaveSuccess(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "信息已更新",
			buttons:Ext.MessageBox.OK,
			fn:closeMe,
			icon:Ext.MessageBox.INFO
		});
		closeMe();
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "信息更新失败",
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