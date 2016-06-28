Ext.namespace("ast.ast1949.admin.productsPic");
//定义一个添加,继承自父窗体类

ast.ast1949.admin.productsPic.EditFormWin = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}

	Ext.apply(this,_cfg);

	var _id = this["id"] || "";
	var _grid = this["grid"] || "";

	var title = "编辑图片管理信息";
	var url = Context.ROOT+Context.PATH + "/admin/productspic/update.htm?productId="+ProductsPic.productId;
	var isView = true;
	var notView = false;
	var win = new ast.ast1949.admin.productsPic.InfoFormWin({
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
	win.initFocus();

	var _store = new Ext.data.JsonStore({
		root:"records",
		fields:[{name:"id",mapping:"productsPicDO.id"},
		        {name:"albumName",mapping:"albumName"},
			{name:"productId",mapping:"productsPicDO.productId"},
			{name:"albumId",mapping:"productsPicDO.albumId"},
			{name:"picAddress",mapping:"productsPicDO.picAddress"},
			{name:"isDefault",mapping:"productsPicDO.isDefault"},
		    {name:"isCover",mapping:"productsPicDO.isCover"}],
		url : Context.ROOT+Context.PATH + "/admin/productspic/init.htm?id="+_id,
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
			msg : "图片信息已更新",
			buttons:Ext.MessageBox.OK,
			fn:closeMe,
			icon:Ext.MessageBox.INFO
		});
		closeMe();
	}

	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "图片信息更新失败",
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