Ext.namespace("ast.ast1949.admin.adminCompany");

ast.ast1949.admin.adminCompany.FindCollectFormWin = Ext.extend(Ext.Window,{
	constructor : function(_cfg) {
		if(_cfg==null){
			_cfg={};
		}
		Ext.apply(this,_cfg);
		var _title = "搜集查询";
		var _isView = this["view"] || true;
		var _notView = this["nView"] || false;
		this._form=new Ext.form.FormPanel({
			region : "center",
			frame : true,
			bodyStyle : "padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
			width : "100%",
			items:[{
				xtype : "fieldset",
				layout : "column",
				autoHeight : true,
				items:[{
					columnWidth : .5,
					layout:"form",
					defaults:{
						disabled:_notView
					},
					items:[{
							xtype : "textfield",
							fieldLabel : "公司名",
							id : "nameCollect",
							name : "nameCollect",
							tabIndex : 1,
							anchor : "95%"
						},{
							xtype : "textfield",
							fieldLabel : "邮箱",
							allowBlank : true,
							id : "emailCollect",
							name : "emailCollect",
							anchor:"95%"
						},{
							xtype : "textfield",
							fieldLabel : "手机号",
							allowBlank : true,
							id : "mobileCollect",
							name : "mobileCollect",
							anchor:"95%"
						}]
				},{
					columnWidth : .5,
					layout:"form",
					defaults:{
						disabled:_notView
					},
					items:[{
						xtype:"combotree",
						fieldLabel:"注册来源",
						id : "seachRegfromCodeName",
						hiddenName:"searchRegfromCode",
						anchor : "95%",
						allowBlank:false,
						tree:new Ext.tree.TreePanel({
							loader: new Ext.tree.TreeLoader({
								root : "records",
								autoLoad: false,
								url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
								listeners:{
									beforeload:function(treeload,node){
										this.baseParams["parentCode"] = node.attributes["data"];
									}
								}
							}),
					   	 	root : new Ext.tree.AsyncTreeNode({text:'全部区域',data:Context.CATEGORY.regfromCode})
//							,
//					   	 	animate:true,
//					   	 	autoScroll : true,
//					   	 	enableDD:true,
//			                containerScroll: true,
//			                dropConfig: {appendOnly:true}
						})
					},
//						new ast.ast1949.CategoryCombo( {
//							categoryCode : Context.CATEGORY["regfromCode"],
//							fieldLabel : "注册来源",
//							id : "searchRegfromCode",
//							name : "searchRegfromCode"}),
					    new ast.ast1949.CategoryCombo( {
							categoryCode : Context.CATEGORY["infoSourceCode"],
							fieldLabel : "信息来源",
							id : "searchInfoSourceCode",
							name : "searchInfoSourceCode"})
						]
				}]
			}],
			buttons:[{
				id : "searchCollect",
				text : "查找",
				hidden : _notView
			}, {
				id : "cancelCollect",
				text : "取消",
				hidden : _notView
			}]
		});
		ast.ast1949.admin.adminCompany.FindCollectFormWin.superclass.constructor.call(
				this,{
					title : _title,
					closeable : true,
					width : "80%",
					autoHeight : true,
					height:250,
					autoScroll : true,
//					maximizable:true,
					modal : true,
					border : false,
					plain : true,
					layout : "form",
					items : [ this._form ]
				})
	}
});