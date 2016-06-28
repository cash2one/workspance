Ext.namespace("ast.ast1949.admin");

Ext.onReady(function(){
//	alert(1)
	ast.ast1949.admin.appInit();
});

ast.ast1949.admin.appInit = function () {
	viewInit([{"data":"","id":"","text":"zz91后台管理"}]);
//	
//	Ext.Ajax.request({
//		url: Context.ROOT + Context.PATH + "/admin/mymenu.htm",
//		method: "post",
//		params:{"parentCode":""},
//		success:function(response){
//			var viewport_items = Ext.decode(response.responseText);
//			
////			treeInits(viewport_items);
//		},
//		failure:function(response){
//			Ext.MessageBox.alert("系统提示","系统加载错误,请与管理员联系!"+response);  //"+Context.ROOT+"/manager/support.html
////			window.location.href = Context.ROOT+Context.PATH+"/admin/login.htm";
////			var viewport_items = [{title:"加载错误",html:"<a href='#' onclick=\"Ext.get('content').dom.src='"+Context.ROOT+"/manager/support.html';\">技术支持</a>"}];
////			menuInit(viewport_items);
//		}
//	});
}

/**加载版面**/
function viewInit (item){
//	var itemC = new Ext.Panel({
//		region: "center",
//		layout: "fit",
//		border:true,
//		autoScroll:false,
//		margins: "0 2 2 0",
//		cls: "empty",
//		items : [{
//			layout:"fit",
//			bodyStyle:"padding: 0px",
//			items: [{
//				autoScroll:false,
//				border:false,
//				html:"<iframe id = 'content'  frameborder = 'no' scrolling = 'auto' style = 'width:100%;height:100%' src ='"+Context.ROOT+Context.PATH+"/admin/welcome.htm'   ></iframe>"
//			}]
//		}]
//	});
	
	 var center=new Ext.TabPanel({
	 	id:"center-Panel",
    	region:'center',
        margins:'0 5 5 0',
        resizeTabs: true,
        minTabWidth: 100,
        tabWidth: 100,
        enableTabScroll: true,
        activeTab: 0,
        script:true,
        items:{
        	id:'welcome-panel',
        	title: '我的桌面',
        	iconCls:"item-me",
        	closable: true,
            autoScroll:true,
            layout : 'fit',
			html : '<iframe src="' + Context.ROOT+Context.PATH+'/admin/welcome.htm" frameBorder=0 scrolling="auto" style = "width:100%;height:100%"></iframe>'
        }
    });

	var menu = new Ext.Panel({
		region: "north",
		margins : "2 2 2 2",
		height :0,
		layout : "column",
		border:false,
		tbar:["<span style='font-weight:bold;font-size:16px;color:#006600;'>杭州阿思拓信息科技有限公司 - 再生资源交易网(zz91.com) 后台管理</b>",
			'->',{
			text:"注销",
			iconCls:"item-exit",
			tooltip:"注销登录,并回到登录页面",
			handler:function(){
				window.location.href = Context.ROOT+Context.PATH+"/admin/logout.htm";
			}
		}]
	});

	var _items=[];
	var len=item.length;
	for(var i=0;i<len;i++){
		var _item={
			title:item[i].text,
			autoScroll:true,
			items:[treeInit(item[i].id,item[i].text,item[i].data)]
		};
		_items.push(_item);
	}

	var accordion = new Ext.Panel({
		title:  '系统导航',
		region: 'west',
		collapsible:true,
		margins:  "0 0 0 0",
		cmargins: "0 0 0 0",
		split: true,
		width: 210,
		maxSize: 240,
		minSize:120,
		layout: 'accordion',
//		collapseMode:"mini",
		animate:true,
		items:_items
	});

	var viewport = new Ext.Viewport({
		layout : "border",
		border : true,
		items:[menu,accordion,center]
	});

}

function treeInit(id,title,d){
	var root = new Ext.tree.AsyncTreeNode({
		text:title,
		draggable:false,
		id:id,
		expended:true
	});
	var treeLoad= new Ext.tree.TreeLoader({
		url: Context.ROOT+Context.PATH+"/admin/mymenu.htm",
		baseParams:{"parentId":d}
	});
	var tree = new Ext.tree.TreePanel({
		layout:"fit",
		region:"center",
		root:root,
//		height:"100%",
		margins:"2 2 2 2",
		border:false,
//		autoScroll:true,
//		bodyBorder:false,
		rootVisible:false,
		animate:true,
		lines:true,
		loader:treeLoad
	});

	treeLoad.on("beforeload",function(treeLoader,node){
		if(typeof(node.attributes.data) != "undefined"){
			this.baseParams.parentCode=node.attributes.data;
		}
	},treeLoad);

	tree.setRootNode(root);
	root.expand();

	tree.on("click",function(node,e) {
		//alert(node.attributes.url)
		var url=node.attributes.url;
		if(url!=null && url!="#" && typeof(url) !="undefined" && url.length>0){
			//Ext.get("content").dom.src = Context.ROOT+Context.PATH+node.attributes.url;
			var center=Ext.getCmp("center-Panel");
			e.stopEvent();
			var id = 'contents-' + node.attributes.data;
	        var tab = center.getComponent(id);
	        if(tab){
	            center.setActiveTab(tab);
	        }else{
	            var p = center.add(new Ext.Panel({
	                id: id,
	                title:node.text,
	                closable: true,
	                autoScroll:true,
	                layout : 'fit', 
					html : '<iframe src="' + Context.ROOT+Context.PATH+node.attributes.url + '"'
						+ '" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe> '
	            }));
	            center.setActiveTab(p);
	        }
			
		}
	});

	return tree;
}

ast.ast1949.admin.changePassword = Ext.extend(Ext.Window,{
	_form:null,
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}

		Ext.apply(this,_cfg);
		this._form = new Ext.form.FormPanel({
			region:"center",
			layout:"column",
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			width:"100%",
			items:[{
					columnWidth: 1,
					layout: "form",
					items:[{
						xtype:"textfield",
						inputType	: "password",
						fieldLabel:"原密码",
						allowBlank:false,
						name:"pwd",
						id:"pwd",
						tabIndex:1,
						anchor:"95%",
						blankText : "原密码不能为空"
					},{
						xtype:"textfield",
						inputType	: "password",
						fieldLabel:"新密码",
						allowBlank:false,
						name:"newpwd",
						id:"newpwd",
						tabIndex:2,
						anchor:"95%",
						blankText : "新密码不能为空"
					},{
						xtype:"textfield",
						inputType	: "password",
						fieldLabel:"确认新密码",
						allowBlank:false,
						name:"newpwd2",
						id:"newpwd2",
						tabIndex:3,
						anchor:"95%",
						blankText : "确认新密码"
					}
				]
			}],
			buttons:[{
				id:"changePwdBtn",
				text:"修改密码"

			},{
				id:"changePwdCancelBtn",
				text:"关闭",
				handler:function(){
					Ext.getCmp("changePwdWindow").close();
				}
			}]
		});

		ast.ast1949.admin.changePassword.superclass.constructor.call(this,{
			title:"修改登录密码",
			id:"changePwdWindow",
			closeable:true,
			width:300,
			modal:true,
			border:false,
			plain:true,
			items:[this._form]
		});
		this.addEvents("saveSuccess","saveFailure","submitFailure","submitForm");
	},
	submit:function(_url){
		if(this._form.getForm().isValid()){
			this._form.getForm().submit({
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
	loadRecord:function(_record){
		this._form.getForm().loadRecord(_record);
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	},
	initFocus:function(){
//		var f = this._form.getForm().findField("user.password");
//		f.focus(true,true);
	}
});
