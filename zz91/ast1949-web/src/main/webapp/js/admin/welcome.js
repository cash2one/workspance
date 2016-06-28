Ext.namespace("ast.ast1949.admin");

ast.ast1949.admin.recreateHtmlPage = function(path){
	Ext.Ajax.request({
		url: Context.ROOT + Context.PATH + "/admin/webmaster/recreateHtmlPage.htm",
		method: "post",
		params:{"path":path},
		success:function(response){
			var res= Ext.decode(response.responseText);
			if(res.success){
				//首页已重新生成
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "首页已重新生成",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.INFO
				});
			}else{
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "发生错误，页面没有重新生成",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			}
		},
		failure:function(response){
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "发生错误,请联系管理员!",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	});
}