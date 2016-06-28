Ext.namespace("ast.ast1949.admin.adminCompany");

Ext.onReady(function(){
	var url = Context.ROOT+Context.PATH + "/admin/admincompany/addOrUpdate.htm";
	var _editCollectForm = new ast.ast1949.admin.adminCompany.editCollectFormPanel({});
	var _editCollectGridPanel = new ast.ast1949.admin.adminCompany.editCollectGridPanel({
		gridCollectForm : _editCollectForm
	});
	new Ext.Viewport({ 
		layout:"border", 
		items : [{
		        	 region:"west", 
		        	 layout : "fit",
		        	 width:500, 
		        	 title:"客户搜集详细信息",
		        	 items : [_editCollectForm.panel]
		         }, 
		         {
		        	 region:"center", 
		        	 layout : "fit",
		        	 title:"客户搜集",
		        	 items : [_editCollectGridPanel]
		         }] 
	}); 
	Ext.get("saveCollect").on("click",function(){
		_editCollectForm.panel.submit(url)
	});
	//根据mobile改变查询
//	Ext.get("mobile").on("change",function(){
//		_editCollectGridPanel.store.baseParams={
//				"mobile":Ext.get("mobile").dom.value,
//				"email":Ext.get("email").dom.value
//				};
//		_editCollectGridPanel.store.reload({
//			params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
//		})
//	});
	//根据email改变查询
//	Ext.get("email").on("change",function(){
//		_editCollectGridPanel.store.baseParams={"mobile":Ext.get("mobile").dom.value,
//				"email":Ext.get("email").dom.value};
//		_editCollectGridPanel.store.reload({
//			params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
//		})
//	});
	//点击查询触发事件
	Ext.get("searchByMobileAndMobile").on("click",function(){
		//_editCollectGridPanel.store.url = Context.ROOT+Context.PATH + "/admin/admincompany/query.htm";
		_editCollectGridPanel.store.baseParams={"mobile":Ext.get("mobile").dom.value,
				"email":Ext.get("email").dom.value};
		_editCollectGridPanel.store.load({
			params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
		})
	})
});