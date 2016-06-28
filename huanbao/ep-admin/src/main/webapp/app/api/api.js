Ext.namespace("com.zz91.ep.api");

com.zz91.ep.api.callback=null;

com.zz91.ep.api.WIN="apiwindowid";

com.zz91.ep.api.showOpenWin=function(crmSvrId, callbackData){
	callbackData=callbackData||{};
	
	var url="/api/svrapi/goOpenApi.htm?crmSvrId="+crmSvrId+"&cid="+callbackData["cid"]+"&companySvrId="+callbackData["companySvrId"];
	
	var win = new Ext.Window({
		id:com.zz91.ep.api.WIN,
		title:"服务开通",
		width:680,
		height:380,
		modal:true,
		autoScroll:true,
		html:'<iframe src="' + Context.ROOT+url+'" frameBorder=0 scrolling="auto" style = "width:100%;height:99%"></iframe>',
		buttons:[{
			text:"关闭窗口",
			iconCls:"item-exit",
			handler:function(){
				win.close();
			}
		}]
	});
	
	win.show();
}

com.zz91.ep.api.showCloseWin=function(crmSvrId, callbackData){
	callbackData=callbackData||{};
	
	var url="/api/svrapi/goCloseApi.htm?crmSvrId="+crmSvrId+"&cid="+callbackData["cid"]+"&companySvrId="+callbackData["companySvrId"];
	
	var win = new Ext.Window({
		id:com.zz91.ep.api.WIN,
		title:"服务关闭",
		width:680,
		height:200,
		modal:true,
		autoScroll:true,
		html:'<iframe src="' + Context.ROOT+url+'" frameBorder=0 scrolling="auto" style = "width:100%;height:99%"></iframe>',
		buttons:[{
			text:"关闭窗口",
			iconCls:"item-exit",
			handler:function(){
				win.close();
			}
		}]
	});
	
	win.show();
}

com.zz91.ep.api.closeWin=function(){
	Ext.getCmp(com.zz91.ep.api.WIN).close();
}
