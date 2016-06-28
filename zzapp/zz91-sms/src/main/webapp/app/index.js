Ext.namespace("com.zz91.common");

Ext.onReady(function(){
	var west=new Ext.tree.TreePanel({
        id:'forum-tree',
        region:'west',
        title:'工作平台',
        split:true,
        width: 220,
        minSize: 175,
        maxSize: 400,
        collapsible: true,
        margins:'2,0,2,2',
        cmargins:'2,2,2,2',
        collapseMode:'mini',
        dataUrl:Context.ROOT+'/mymenu.htm',
        rootVisible:true,
        lines:false,
        autoScroll:true,
        root: new Ext.tree.AsyncTreeNode({
          	text: '系统导航',
          	expanded:true
      	})
    });
    
    var center=new Ext.TabPanel({
    	region:'center',
        margins:'0 5 5 0',
        resizeTabs: true,
        minTabWidth: 100,
        tabWidth: 100,
        enableTabScroll: true,
        activeTab: 0,
        items:[{
        	id:'welcome-panel',
        	iconCls:"home16",
        	title: '我的桌面',
        	closable: true,
        	autoScroll:true,
         	layout : 'fit',
         	html:'<iframe src="' + Context.ROOT+ '/welcome.htm" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe>'
        }]
    });
	
	var north={
		region:'north',
		height:0,
		bbar:[{
        	iconCls:"web16",
        	text:"推荐用Chrome访问我",
        	tooltip:"chrome或者firefox3+都不错，打倒IE6 >.<",
        	handler:function(btn){
        		window.open("http://www.google.com/chrome/index.html?hl=zh_cn&brand=CHMA&utm_campaign=zh_cn&utm_source=zh_cn-ha-apac-zh_cn-bk&utm_medium=ha");
        	}
        },'->',{ //new Ext.ux.ThemeChange(),
			iconCls:'userid16',
			text:'<b>'+Ext.get("logineduser").dom.value+'</b>',
			handler:function(btn){
//				caiban.utils.Msg(MESSAGE.title, "个人信息暂不能被查看和修改！");
			}
		},{
			text:'退出',
			iconCls:'close16',
			handler:function(btn){
				window.location.href = Context.ROOT+"/logout.htm";
			}
		}],
		html:''
	};
	
	
	west.on('click',function(node, e){
		if(node.isLeaf()){
			e.stopEvent();
			var id = 'contents-' + node.attributes.data;
	        var tab = this.getComponent(id);
	        if(tab){
	            center.setActiveTab(tab);
	        }else{
	            var p = center.add(new Ext.Panel({
	                id: id,
	                title:node.text,
	                closable: true,
	                autoScroll:true,
	                layout : 'fit', 
					html : '<iframe src="' + Context.ROOT+ node.attributes.url + '" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe> '
	            }));
	            center.setActiveTab(p);
	        }
		}
	});
	
	west.getLoader().on("beforeload",function(treeLoader,node){
		this.baseParams.parentCode= node.attributes.data;  //根据data返回的JSON数据获取
	},west.getLoader());
	
	var viewport=new Ext.Viewport({
		layout:'border',
		items:[west,center,north]
	});
	
});
