Ext.namespace("ast.ast1949.admin.crm");

Ext.onReady(function(){

//	alert(Ext.get("node-selected").dom.value)
	var _url = Context.ROOT + "/zz91/admin/crm/editCustomerCategory.htm";

	var _editPanel = ast.ast1949.admin.crm.editPanel({
		id: Ext.get("node-selected").dom.value
	});
	var _center = {
		region : "center",
		layout : "fit",
		items : [_editPanel.panel]
	} ;

	var _left = new Ext.Panel({
		title:  '类别<span style="color:red">【点击右键更多操作】</span>',
		region: 'west',
		collapsible:true,
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		split: true,
		width: 210,
		maxSize: 240,
		minSize:120,
		layout: 'fit',
		items:[{html:"<div id='customercategory-tree'></div>"}]
	});

	var viewport = new Ext.Viewport({
		layout : "border",
		border : true,
		items:[_left,_center]
	});

	var _tree = ast.ast1949.admin.crm.treePanel({el:"customercategory-tree",rootData:""});
	_editPanel._tree=_tree;

	//左侧树点击后加载右边表单数据
	_tree.on("click",function(node,event){
		
		var pnode = node.parentNode ;
		var _id=node.id;
		_id = _id.split("-")[1];
		_editPanel.store.load({params:{"id":_id}});
		
		if(pnode!=null){
			var f=_editPanel.panel._form;
			f.findById("combo-parentId").setValue(pnode.text);
			Ext.get("parentId").dom.value = pnode.attributes["data"];
		}else{
			var f=_editPanel.panel._form;
			f.findById("combo-parentId").setValue("请选择父节点");
			Ext.get("parentId").dom.value = "";
		}
    });

	//右健广告组定义
    var contextmenu = new Ext.menu.Menu({
    	id:"rightMenu",
    	items:[
    		{
    		id:"menu1",
    		text:"添加",
    		cls: "item-add",
    		handler:function(menu){
    			var f=_editPanel.panel._form;

				resumeForm(f);

				_editPanel.store.load();

    		}
    	},{
    		id:"menu3",
    		text:"删除",
    		cls:"item-del",
    		handler:function(){
				Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除该分类及其子分类吗?', doDelete);
    		}
    	},{
    		id:"menu2",
    		text:"修改",
    		cls:"item-edit",
    		handler:function(){
				var _id=Ext.get("node-selected-id").dom.value;
				_id = _id.split("-")[1];
				_editPanel.store.load({params:{"id":_id}});
    		}
    	}]

    });

    //右健广告组
    _tree.on("contextmenu",function(node,event){
//    	alert("context click")
    	event.preventDefault();
    	contextmenu.showAt(event.getXY());
    	node.select();
    	Ext.get("node-selected").dom.value= node.attributes["data"];
    	Ext.get("node-selected-id").dom.value= node.id;
    	Ext.get("node-selected-text").dom.value= node.text;

    });

    function doDelete(_btn){
    	if(_btn != "yes")
			return ;

		var nodeId = Ext.get("node-selected-id").dom.value;
		var _id = nodeId.split("-")[1];

    	var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT + Context.PATH + "/admin/crm/delCustomerCategory.htm?id="+_id,
			method : "get",
			scope : this,
			callback : function(options,success,response){
				if(success){
					
					Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "删除成功",
					buttons:Ext.MessageBox.OK,
					fn:function(){},
					icon:Ext.MessageBox.INFO
					});
					
					var node = _tree.getNodeById(Ext.get("node-selected-id").dom.value);
					node.remove();
				}
			}
		});
    }

    //更新广告组
    Ext.get("save").on("click",function(){
    	_editPanel.panel.submit(_url);
    });


    function resumeForm(f){
		f.findById("combo-parentId").setValue(Ext.get("node-selected-text").dom.value);
		f.findById("id").setValue(0);
		f.findById("name").setValue("");
		
		Ext.get("parentId").dom.value=Ext.get("node-selected").dom.value;
    }
});