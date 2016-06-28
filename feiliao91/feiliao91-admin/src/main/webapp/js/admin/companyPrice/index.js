Ext.namespace("ast.ast1949.admin.companyPrice");

var _C = new function() {
	this.RESULT_GRID="resultgrid";
}

ast.ast1949.admin.companyPrice.FIELD=[
	{name : "id",mapping : "companyPriceDO.id"}, 
	{name : "productId",mapping : "companyPriceDO.productId"},
	{name : "companyId",mapping : "companyPriceDO.companyId"},
	{name : 'title',mapping : 'companyPriceDO.title'}, 
	{name : 'minPrice',mapping : 'companyPriceDO.minPrice'}, 
	{name : 'maxPrice',mapping : 'companyPriceDO.maxPrice'}, 
	{name : 'priceUnit',mapping : 'companyPriceDO.priceUnit'},
	{name : 'refreshTime',mapping : 'companyPriceDO.refreshTime'},
	{name : 'postTime',mapping : 'companyPriceDO.postTime'},
	{name : 'categoryCompanyPriceCode',mapping : 'companyPriceDO.categoryCompanyPriceCode'},
	{name : 'isChecked',mapping : 'companyPriceDO.isChecked'},
	{name : 'companyName',mapping : 'companyName'},
	{name : 'membershipCode',mapping : 'membershipCode'}
];

ast.ast1949.admin.companyPrice.UPDATEFIELD=[
	{name : "id",mapping : "companyPriceDO.id"},
	{name : "title",mapping : "companyPriceDO.title"},
	{name : "price",mapping : "companyPriceDO.price"},
	{name : "priceUnit",mapping : "companyPriceDO.priceUnit"},
	{name : "minPrice",mapping : "companyPriceDO.minPrice"},
	{name : "maxPrice",mapping : "companyPriceDO.maxPrice"},
	{name : "details",mapping : "companyPriceDO.details"},
	{name : "categoryCompanyPriceCode",mapping : "companyPriceDO.categoryCompanyPriceCode"},
	{name : "areaCode",mapping : "companyPriceDO.areaCode"},
	{name : "companyName",mapping : "companyName"},
	{name : "categoryName",mapping : "categoryName"},
	{name : "areaName",mapping : "areaName"}
];

ast.ast1949.admin.companyPrice.ResultGrid =Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var grid=this;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.companyPrice.FIELD,
			url:Context.ROOT + Context.PATH + "/admin/companyprice/query.htm",
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
            {
				header : "来源",
				sortable : false,
				dataIndex : "productId",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != 0) {
						return "供求推荐";
					} else {
						return "myrc发布";
					}
				}
			}, {
				header : "产品名称",
				width : 150,
				sortable : false,
				dataIndex : "title",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var webstr="<a href='http://price.zz91.com/companyprice/priceDetails.htm?id="+record.get("id")+"' target='_blank' >";
					webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
					var title=value;
					if(record.get("productId")>0){
						title=" <a href='"+
						Context.ROOT+Context.PATH+
						"/admin/products/edit.htm?productid="+record.get("productId")+
						"&companyid="+record.get("companyId")+
						"' target='_blank'>"+value+"</a>";
					}
					return webstr+title;
				}
			}, {
				header : "产品价格",
				sortable : false,
				dataIndex : "minPrice",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (record.get("maxPrice")>0) {
						return value+" ~ "+record.get("maxPrice");
					}
					return value;
				}
			}, {
				header : "单位",
				sortable : false,
				dataIndex : "priceUnit"
			}, {
				header : "公司名称",
				sortable : false,
				dataIndex : "companyName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")!="10051000"&&record.get("membershipCode")!="10051003"){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					}
					val= val + "<a href='" + Context.ROOT + Context.PATH + 
							"/crm/company/detail.htm?companyId=" + 
							record.get("companyId") + "' target='_blank'>" + 
							value + "</a>";;
					return val;
				}
			}, {
				header : "刷新时间",
				sortable : false,
				dataIndex : "refreshTime",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(
								new Date(value.time), 'Y-m-d');
					}
				}
			}, {
				header : "发布时间",
				sortable : false,
				dataIndex : "postTime",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(
								new Date(value.time), 'Y-m-d');
					}
				}
			}, {
				header : "审核状态",
				sortable : false,
				dataIndex : "isChecked",
				renderer:function (value,metadata,record,rowIndex,colIndex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
			}
		]);
		
		var _tbar=config.tbar||[{
	    	id:"edit",
			text : '修改',
			tooltip : '修改一条记录',
			iconCls : 'edit',
			handler:function(btn){
	    		var grid = Ext.getCmp(_C.RESULT_GRID); 
				var rows=grid.getSelectionModel().getSelected();
				ast.ast1949.admin.companyPrice.UpdateCompanyPrice(rows.get("id"));
			}
		}, {
			id:"delete",
			text : '删除',
			tooltip : '删除一条记录',
			iconCls : 'delete',
			handler : function(){
				var grid = Ext.getCmp(_C.RESULT_GRID);
				grid.deleteRecord();
			}	
		}, {
			text : '审核',
			tolltip : '审核',
			iconCls : 'edit',
			handler : function(){
				var grid = Ext.getCmp(_C.RESULT_GRID);
				grid.checkStatus(1,_store);
			}
		}, {
			text : '取消审核',
			tolltip : '取消审核',
			iconCls : 'edit',
			handler : function(){
				var grid = Ext.getCmp(_C.RESULT_GRID);
				grid.checkStatus(0,_store);
			}
		},{
			text:"移动报价",
			iconCls : 'query',
			handler:function(btn){
				var grid = Ext.getCmp(_C.RESULT_GRID);
				var rows=grid.getSelectionModel().getSelections();
				var adArr=new Array();
				for(var i=0;i<rows.length;i++){
					adArr.push(rows[i].get("id"))
				}
				ast.ast1949.admin.companyPrice.moveCompanyPriceWin(adArr,function(){
					grid.store.reload();
				});
			}
		},"->",{
			xtype:"combo",
			id:"timeType",
			mode:"local",
			emptyText:"时间类型",
			triggerAction:"all",
			displayField:'name',
			valueField:'value',
			autoSelect:true,
			width:80,
			store:new Ext.data.JsonStore({
				fields : ['name', 'value'],
				data   : [
					{name:'默认',value:""},
					{name:'刷新时间',value:"refresh_time"},
					{name:'发布时间',value:"post_time"}
				]
			})
		},"from:",{
        	id:"from",
        	xtype:"datefield",
        	format:"Y-m-d"
        },"to:",{
        	id:"to",
        	xtype:"datefield",
        	format:"Y-m-d"
        },{
        	text:"查询",
        	iconCls:"query",
        	handler:function(btn){
        		var targetFrom=Ext.getCmp("from").getValue();
        		var targetTo=Ext.getCmp("to").getValue();
        		var timeType=Ext.getCmp("timeType").getValue();
        		if(from!="" && to!="" && timeType != ""){
        			var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
					var B = _store.baseParams;
					B = B||{};
        			B["from"]=targetFrom;
        			B["to"]=targetTo;
        			B["timeType"]=timeType;
        			_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
        		}
        	}
        },"名称",{
			xtype : "textfield",
			width : 100,
			listeners:{
				"blur":function(field){
					var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
					var B = _store.baseParams;
					B = B||{};
					if(field.getValue()!=""){
						B["title"] = field.getValue();
					}else{
						B["title"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},"",{
			xtype:"checkbox",
			boxLabel:"未审核",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
					var B = _store.baseParams;
					if(field.getValue()){
						B["isChecked"] = "0";
					}else{
						B["isChecked"] = "";
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"高会",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
					var B = _store.baseParams;
					if(field.getValue()){
						B["isVip"] = "1";
					}else{
						B["isVip"] = "";
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"普会",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
					var B = _store.baseParams;
					if(field.getValue()){
						B["isVip"] = "2";
					}else{
						B["isVip"] = "";
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}]
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:_tbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
		ast.ast1949.admin.companyPrice.ResultGrid.superclass.constructor.call(this,c);
	},
	loadCompanyPriceDO:function(id){
		this.getStore().reload({params:{"id":id}});
	},
	checkStatus:function(isChecked,store){
		var grid = Ext.getCmp(_C.RESULT_GRID);
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/companyprice/check.htm",
				params:{
					"ids":rows[i].get("id"),
					"isChecked":isChecked
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","信息已成功更新")
						store.reload();
					}else{
						com.zz91.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","操作失败");
				}
			});
		}
	},
	deleteRecord:function(){
		var grid = Ext.getCmp(_C.RESULT_GRID);
		var rows = grid.getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/companyprice/delete.htm",
				params:{
					"ids":rows[i].get("id")
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","所选记录已成功删除")
						grid.getStore().reload();
					}else{
						com.zz91.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","操作失败");
				}
			});
		}
	}
});

//报价类别修改
ast.ast1949.admin.companyPrice.moveCompanyPriceWin=function(adArr, callback){
	var tree = new ast.ast1949.admin.categoryCompanyPrice.treePanel({
		height:400,
		autoScroll:true,
		layout:"fit",
		region:"center",
		contextmenu:null
	});
	
	var win = new Ext.Window({
		id:"movepricewin",
		title:"选择要移动到的位置",
		modal:true,
		width:300,
		items:[tree]
	});
	win.show();
	
	tree.on('dblclick',function(node,e){
		var grid = Ext.getCmp(_C.RESULT_GRID);
		var categoryCode=node.attributes["data"];
		for(var i=0;i<adArr.length;i++){
			Ext.Ajax.request({
				url: Context.ROOT+Context.PATH+ "/admin/companyprice/move.htm",
		        params:{
					"id":adArr[i],
					"categoryCode":categoryCode
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","所选记录移动成功")
						grid.getStore().reload();
					}else{
						com.zz91.utils.Msg("","操作失败");
					}
				},
				failure:function(response,opt){
					com.zz91.utils.Msg("","操作失败");
				}
		    });
		}
		Ext.getCmp("movepricewin").close();
	});
}

//企业报价修改
ast.ast1949.admin.companyPrice.UpdateCompanyPrice=function(id){
	
	var form=new ast.ast1949.admin.companyPrice.UpdateCompanyPriceForm({
	});
	
	form.loadCompanyPrice(id);
	
	var win = new Ext.Window({
		id:"updatepricewin",
		title:"报价信息",
		width:650,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

//报价信息修改表单
ast.ast1949.admin.companyPrice.UpdateCompanyPriceForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : 'right',
			fileUpload : true,
			frame : true,
			layout : 'form',
			labelWidth : 80,
			items : [{

				layout : 'column',
				items : [{
					columnWidth : .5,
					layout : 'form',
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items : [{
								xtype:"hidden",
								name:"id",
								id:"id"
							},{
								xtype:"hidden",
								name:"categoryCompanyPriceCode",
								id:"categoryCompanyPriceCode"
							},{
								xtype:"hidden",
								name:"areaCode",
								id:"areaCode"
							}, {
								fieldLabel : "公司名称",
								name : "companyName",
								id:"companyName",
								tabIndex : 1,
								readOnly : true
							}, {
								fieldLabel : "产品价格",
								name : "price",
								id : "price",
								tabIndex : 1
							}, {
								fieldLabel : "价格最小值",
								name : "minPrice",
								id : "minPrice",
								tabIndex : 1,
							},{
								fieldLabel:"所属类别",
								allowBlank:false,
								editable: false,
								id:"categoryName",
								name:"categoryName",
								readOnly:true,
								listeners:{
									"focus":function(field){
										var initValue=Ext.getCmp("categoryCompanyPriceCode").getValue();
										ast.ast1949.admin.companyPrice.choiceCategory(initValue,function(node,event){
											//var node=tree.getSelectionModel().getSelectedNode();
											Ext.getCmp("categoryName").setValue(node.text);
											Ext.getCmp("categoryCompanyPriceCode").setValue(node.attributes["data"]);
											node.getOwnerTree().ownerCt.close();
										})
									}
								}
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items : [{
								fieldLabel : "产品名称",
								name : "title",
								tabIndex : 1
							}, {
								fieldLabel : "价格单位",
								allowBlank : false,
								itemCls:"required",
								name : "priceUnit",
								tabIndex : 1
							}, {
								fieldLabel : "价格最大值",
								name : "maxPrice",
								tabIndex : 1,
							}, {
								fieldLabel:"地区",
								name : "areaName",
								id : "areaName",
								readOnly:true,
								listeners:{
									"focus":function(field){
										var initValue=Ext.getCmp("areaCode").getValue();
										ast.ast1949.admin.companyPrice.choiceArea(initValue,function(node,event){
											Ext.getCmp("areaName").setValue(node.text);
											Ext.getCmp("areaCode").setValue(node.attributes["data"]);
											node.getOwnerTree().ownerCt.close();
										})
									}
								}
							}]
				}]

			}, {
				xtype : 'textarea',
				id : 'details',
				fieldLabel : '产品描述',
				height : 160,
				anchor : '99%'
			}],	
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp(_C.RESULT_GRID);
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/admin/companyprice/update.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updatepricewin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"保存并审核通过",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp(_C.RESULT_GRID);
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/admin/companyprice/updateAndPass.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updatepricewin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"删除",
				handler:function(btn){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					form.getForm().submit({
						url:Context.ROOT+Context.PATH+"/admin/companyprice/deleteById.htm",
						method:"post",
						type:"json",
						success:function(){
							ast.ast1949.utils.Msg("","删除成功");
							Ext.getCmp("updatepricewin").close();
							grid.getStore().reload();
						},
						failure:function(){
							ast.ast1949.utils.Msg("","删除失败");
						}
					});
				}
			}]
		};
		
		ast.ast1949.admin.companyPrice.UpdateCompanyPriceForm.superclass.constructor.call(this,c);
	},
	loadCompanyPrice:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.admin.companyPrice.UPDATEFIELD,
			url : Context.ROOT+Context.PATH+"/admin/companyprice/queryOneRecord.htm?id=" + id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						
					}
				}
			}
		});
	}
});

//企业报价类别树
ast.ast1949.admin.companyPrice.choiceCategory=function(initValue,callback){
	
	var companyPriceTree = new ast.ast1949.admin.categoryCompanyPrice.treePanel({
		id:"categorytree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	companyPriceTree.on("click",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[companyPriceTree]
	});
	
	win.show();
}

//地区类别树
ast.ast1949.admin.companyPrice.choiceArea=function(initValue,callback){
	
	var provincesTree = new ast.ast1949.admin.category.provincesTreePanel({
		id:"areatree",
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	provincesTree.on("click",callback);
	
	var win = new Ext.Window({
		title:"选择类别",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[provincesTree]
	});
	
	win.show();
}