//Create By Rolyer 2010.01.27
Ext.namespace("ast.ast1949.admin.categoryproducts");

ast.ast1949.admin.categoryproducts.categoryproductsTree=function(_cfg){
	if(_cfg==null){
		_cfg={};
	}

	//属性值

	//基本属性
	var _fieldLabel = _cfg["fieldLabel"] || "";
	var _id = _cfg["id"] || "";
	var _name = _cfg["name"] || "";
	var _hiddenName = _cfg["hiddenName"] || "";
	var _hiddenId = _cfg["hiddenId"] || "";
	var _emptyText = _cfg["emptyText"] || "";

	var _readOnly= _cfg["readOnly"] || "";
	var _allowBlank=_cfg["allowBlank"]||"";
	var _width=_cfg["width"]||"";

	//数据相关
	var _el = _cfg["el"] || "";
	var _rootData = _cfg["rootData"] || "";
	var _anchor = _cfg["anchor"]||"95%";
	var _tabIndex = _cfg["tabIndex"]||1;


	var baseTypeCombo = new Ext.form.ComboBox({
		fieldLabel	: _fieldLabel,
		id			: _id,
		name		: _name,
		hiddenName	: _hiddenName,
		anchor		: _anchor,
		hiddenId	: _hiddenId,
		mode		: "local",
		xtype		: "combo",
		readOnly	: _readOnly,
		selectOnFocus:true,
		triggerAction: "all",
		emptyText	: _emptyText,
		tabIndex	: _tabIndex,
		width		: _width,
		allowBlank	: _allowBlank,
		store		: new Ext.data.SimpleStore({fields:[],data:[[]]}),
		tpl			: "<tpl for='.'><div style='height:180px' id='"+_el+"'></div></tpl>",
		onSelect	: Ext.emptyFn
	});

	var tree;
	baseTypeCombo.on("expand",function(){
  		if(tree==null){
  			tree =  ast.ast1949.admin.categoryproducts.treePanel({el:_el,rootData:_rootData});
  			tree.getRootNode().disable();
  			//点击时的事件，赋值
  			tree.on('click',function(node){
  				baseTypeCombo.setValue(node.text);
  				baseTypeCombo.collapse();
	          	Ext.get(_hiddenId).dom.value= node.attributes["data"];
	      	});
  		}
  	});

	return baseTypeCombo;//返回对象
}