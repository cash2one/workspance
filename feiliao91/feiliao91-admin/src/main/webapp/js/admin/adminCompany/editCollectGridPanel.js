Ext.namespace("ast.ast1949.admin.adminCompany");

//var form = new ast.ast1949.admin.adminCompany.exhibitSearchForm({
//	title:"搜索公司信息",
//	collapsible: true,
//    animCollapse: false,
//    frame:true,
//    titleCollapse:true,
//    style: 'position:absolute;right:6;bottom:30;z-index:101',  //left:0;
//    height:300,
//    width:270,
//    targetGrid:grid
//});
//
//form.render(Ext.getBody());
//
//form.resizer = new Ext.Resizable(form.el, {
//    minHeight:50,
//    minWidth:100,
//    handles: "n",
//    pinned: true,
//    transparent:true,
//    resizeElement : function(){
//        var box = this.proxy.getBox();
//        this.proxy.hide();
//        form.setHeight(box.height);
//        return box;
//    }
//});

ast.ast1949.admin.adminCompany.editCollectGridPanel = function(_cfg){
	if(_cfg==null){
		_cfg = {};
	}
	Ext.apply(this,_cfg);
	var _gridCollectForm=_cfg["gridCollectForm"]||null;
	var isView = true;
	var notView = false;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([sm,{
		header : "公司名",
		sortable : false,
		dataIndex : "name"
	},{
		header :"注册时间",
		sortable : false,
		dataIndex : "regtime",
		renderer : function(value, metadata, record, rowIndex,colIndex, store) {
			if(value!=null){
				return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
			}
			else{
				return "";
			}
		}
	}
//	,{
//		header :"登入次数",
//		sortable:false,
//		dataIndex : "numLogin"
//	}
	]);
	var tbar=[{
			text:'搜集查找',
			iconCls:'view',
			handler:onView,
			scope:this
		},{
			text : '修改',
			tooltip : '修改',
			iconCls : 'edit',
			handler : function(btn){
				
			//	if (sm.getCount() == 0)
			//		Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
			//	else
					var row = grid.getSelections();
					var _cid=row[0].get("id");
					var _acc=row[0].get("account");
				
					var url=Context.ROOT+Context.PATH+"/admin/admincompany/edit.htm?companyId="+_cid+"&account="+_acc+"&ts="+Math.random();
					window.open(url);
					
	    	},
			scope : this
      }];
	
	var reader = [ {name:"id",mapping:"company.id"},
	               {name:"name",mapping:"company.name"},
	               {name:"regtime",mapping:"company.regtime"},	               
	               {name:"account",mapping:"companyContacts.account"}];
	
	//var reader = ["id","account","name","regtime","numLogin"];
	
	var storeUrl = Context.ROOT+Context.PATH + "/admin/admincompany/query.htm";
	var grid = new ast.ast1949.StandardGridPanel02({
		id:"companyCollectGrid",
		clicksToEdit:1,
		cm: cm,
		reader : reader,
		storeUrl: storeUrl, // 数据
		baseParams : {"dir":"DESC","sort":"id"},
		tbar:tbar
	});
//	grid.on('rowclick',function(grid,rowIndex,e){
//		var id = grid.getSelectionModel().getSelected().get("id");
//		var account = grid.getSelectionModel().getSelected().get("account");
//		_gridCollectForm.store.reload({
//			params:{"ids":id,"account":account}
//		});
//	});
	function onView(){
		var findCollect = new ast.ast1949.admin.adminCompany.FindCollectFormWin({});
		findCollect.show();
		Ext.get("searchCollect").on("click",function(){
			grid.store.baseParams = {"name":Ext.get("nameCollect").dom.value,
					"regfromCode":Ext.get("searchRegfromCode").dom.value,
					"email":Ext.get("emailCollect").dom.value,
					"mobile":Ext.get("mobileCollect").dom.value,
					"infoSourceCode":Ext.get("searchInfoSourceCode").dom.value
			};
			grid.store.reload({
				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
			});
			findCollect.close();
		});
		Ext.get("cancelCollect").on("click",function(){
			findCollect.close();
		})
	}
	return grid;
}

/**
 * 公司搜索表单
 * */
//ast.ast1949.admin.adminCompany.exhibitSearchForm = Ext.extend(Ext.form.FormPanel,{
//	targetGrid:null,
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		var _store = this.targetGrid.getStore();
//		var B = _store.baseParams;
//		B = B||{};
//
//		var c={
//			bodyStyle : "padding:2px 2px 0",
//			labelAlign : "right",
//			labelWidth : 80,
//			autoScroll:true,
//			layout : "column",
//			items:[{
//				columnWidth:1,
//				layout:"form",
//				defaults:{
//					anchor:"95%",
//					xtype:"textfield",
//					labelSeparator:""
//				},
//				items:[{
//					fieldLabel : "公司名：",
//					name : "name",
//					id : "search-name",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["name"] = undefined;
//							}else{
//								B["name"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "帐号：",
//					name : "account",
//					id : "search-account",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["account"] = undefined;
//							}else{
//								B["account"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "电话：",
//					name : "tel",
//					id : "search-tel",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["tel"] = undefined;
//							}else{
//								B["tel"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "手机号码：",
//					name : "mobile",
//					id : "search-title",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["mobile"] = undefined;
//							}else{
//								B["mobile"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					fieldLabel : "邮箱：",
//					name : "email",
//					id : "search-email",
//					listeners:{
//						"change":function(field,newvalue,oldvalue){
//							if(newvalue==""){
//								B["email"] = undefined;
//							}else{
//								B["email"] = newvalue;
//							}
//							_store.baseParams = B;
//						}
//					}
//				},{
//					xtype:"combotree",
//					fieldLabel:"地区：",
//					id : "combo-area",
//					name : "combo-area",
//					hiddenName : "search-areaCode",
//					hiddenId : "search-areaCode",
//					editable:true,
//					tree:new Ext.tree.TreePanel({
//						loader: new Ext.tree.TreeLoader({
//							root : "records",
//							fields : [ "label", "code" ],
//							autoLoad: false,
//							url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
//							listeners:{
//								beforeload:function(treeload,node){
//									this.baseParams["parentCode"] = node.attributes["data"];
//								}
//							}
//						}),
//				   	 	root : new Ext.tree.AsyncTreeNode({text:'地区',data:Context.CATEGORY.areaCode})
//					}),
//					listeners:{
//						"blur":function(field){
//							if(Ext.get("search-areaCode").dom.value!=""){
//								B["areaCode"] =  Ext.get("search-areaCode").dom.value;
//							}else{
//								B["areaCode"] = undefined;
//							}
//							_store.baseParams = B;
//						}
//					}
//				}],
//			buttons:[{
//				text:"按条件搜索",
//				handler:function(btn){
//					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//				}
//			}]
//		};
//
//		ast.ast1949.admin.adminCompany.exhibitSearchForm.superclass.constructor.call(this,c);
//
//	}
//});
