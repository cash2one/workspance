Ext.namespace("ast.ast1949.api");

ast.ast1949.api.callback=null;

ast.ast1949.api.WIN="apiwindowid";

ast.ast1949.api.showOpenWin=function(svrCode, callbackData){
	callbackData=callbackData||{};
	
	var url="/api/api/goOpenApi.htm?svrCode="+svrCode+"&companyId="+callbackData["companyId"]+"&companySvrId="+callbackData["companySvrId"];
	
	var win = new Ext.Window({
		id:ast.ast1949.api.WIN,
		title:"服务开通",
		width:700,
		height:430,
		modal:true,
		autoScroll:true,
		html:'<iframe src="' + Context.ROOT+Context.PATH+url+'" frameBorder=0 scrolling="auto" style = "width:100%;height:99%"></iframe>',
		buttons:[{
			text:"关闭",
			iconCls:"item-exit",
			handler:function(){
				win.close();
			}
		}]
	});
	
	win.show();
}

ast.ast1949.api.showChangeWin=function(svrCode, callbackData){
	callbackData=callbackData||{};
	
	var url="/api/api/goChangeApi.htm?svrCode="+svrCode+"&companyId="+callbackData["companyId"]+"&companySvrId="+callbackData["companySvrId"];
	
	var win = new Ext.Window({
		id:ast.ast1949.api.WIN,
		title:"服务开通",
		width:680,
		height:380,
		modal:true,
		autoScroll:true,
		html:'<iframe src="' + Context.ROOT+Context.PATH+url+'" frameBorder=0 scrolling="auto" style = "width:100%;height:99%"></iframe>',
		buttons:[{
			text:"关闭",
			iconCls:"item-exit",
			handler:function(){
				win.close();
			}
		}]
	});
	
	win.show();
}

ast.ast1949.api.showCloseWin=function(svrCode, callbackData){
	callbackData=callbackData||{};
	
	var url="/api/api/goCloseApi.htm?svrCode="+svrCode+"&companyId="+callbackData["companyId"]+"&companySvrId="+callbackData["companySvrId"];
	
	var win = new Ext.Window({
		id:ast.ast1949.api.WIN,
		title:"服务开通",
		width:680,
		height:380,
		modal:true,
		autoScroll:true,
		html:'<iframe src="' + Context.ROOT+Context.PATH+url+'" frameBorder=0 scrolling="auto" style = "width:100%;height:99%"></iframe>',
		buttons:[{
			text:"关闭",
			iconCls:"item-exit",
			handler:function(){
				win.close();
			}
		}]
	});
	
	win.show();
}

ast.ast1949.api.closeWin=function(){
	Ext.getCmp(ast.ast1949.api.WIN).close();
}
