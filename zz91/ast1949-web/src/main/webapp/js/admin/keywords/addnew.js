Ext.namespace("ast.ast1949.admin.keywords")

var _C = new function(){
	this.BOUGHT_KEYWORDS_GRID="BOUGHT_KEYWORDS_GRID";
	this.EXISTS_KEYWORDS_GRID="EXISTS_KEYWORDS_GRID";
	this.ADD_KEYWORDS_FORM="ADD_KEYWORDS_FORM";
}

Ext.onReady(function() {
	var form  =  new ast.ast1949.admin.keywords.editForm({
		id:_C.EXISTS_KEYWORDS_GRID
	})
	
	var exists = new ast.ast1949.admin.keywords.KeyWordsGrid({
		title:"已存在的关键字排名信息",
		id:_C.EXISTS_KEYWORDS_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/keywords/query.htm",
		autoScroll:true
	});
	
	var bought = new ast.ast1949.admin.keywords.KeyWordsGrid({
		title:"此供求已购买的关键字",
		id:_C.BOUGHT_KEYWORDS_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/keywords/query.htm",
		autoScroll:true
	});
	
	//更具供求编号加载此供求已购买的关键字
	bought.store.baseParams = {"productId":CONST.PRODUCT_ID};
	bought.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	
	var _form = {
		region : "north",
		layout : "fit",
		height:300,
		items : [form]
	};
	
	
	var _west = {
		title : "添加信息",
		region : "west",
		layout : "border",
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		collapsible:true,
		split: true,
		width: "30%",
		maxSize: 380,
		minSize:280,
		items:[_form,
		{title:"供求详情",region:"center",
		html:"<div id='pro'></div>"}
		]
	}
	
	var _main = {
		region:"north",
		height:350,
		layout:"fit",
		items:[exists]
	}
	
	var _south ={
		region:"center",
		layout:"fit",
		items:[bought]
	};
	
	var _center = {
		region : "center",
		layout : "border",
		items : [_main,_south]
	};
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[_west,_center]
	});
	//处理显示供求信息的方法
	Ext.Ajax.request({
		url:Context.ROOT+Context.PATH+"/admin/keywords/queryProductsByProductId.htm",
		params:{
			"productId":CONST.PRODUCT_ID
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			Ext.get("pro").dom.innerHTML=
 	    	 "        帐号:     "+obj.products.account+"<br>"+
 	    	 "       标题:      "+obj.products.title+"<br>"+
 	    	 "       制造情况:      "+obj.manufactureLabel+"<br>"+
 	    	 "      供/求:      "+obj.productsTypeLabel+"<br>"+
 	    	 "      地理位置：   "+obj.products.location+"<br>"+
			"       详细信息:      "+obj.products.details+"<br>";
		},
		failure:function(response,opt){
			Ext.MessageBox.show({
				title : Context.MSG_TITLE,
				msg : "发生错误,信息没有被审核",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	});
	
	jQuery.ajax({
		type:"post",
		url:Context.ROOT+Context.PATH+"/admin/keywords/queryProductsByProductId.htm?productId="+CONST.PRODUCT_ID,
		dataType:"json",
		success:function(response){
 	    	 var obj = eval(response);
 	    	 
    	}
	});
});

//关键字排名列表
ast.ast1949.admin.keywords.KeyWordsGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
//	                    Ext.getCmp("addResult").enable();
	                } else {
//	                    Ext.getCmp("addResult").disable();
	                }
	            }
	        }
		});

		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"审核",
				dataIndex : "isChecked",
				width:30,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"关键字",
				sortable:false,
				dataIndex:"name"
			},{
				header:"标王类别",
				sortable:false,
				dataIndex:"type",
				renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (value == "10431000") {
							return "金牌标王";
						} else if (value == "10431001") {
							return "银牌标王";
						} else if(value=="10431002"){
							return "铜牌标王";
						}else {
							return null;
						}
					}
			},{
				header:"过期",
				dataIndex : "expired",
				width:30,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value==1){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(value==0){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"开始时间",
				sortable:false,
				dataIndex:"startTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"结束时间",
				sortable:false,
				dataIndex:"endTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"购买时间",
				sortable:false,
				dataIndex:"buyTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"公司名称",
				sortable:false,
				dataIndex:"companyName"
			},{
				header:"供求信息",
				sortable:false,
				dataIndex:"productsTitle"
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			listeners:{
//				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.keywords.KeyWordsGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"isChecked",mapping:"productsKeywordsRank.isChecked"},
		{name:"name",mapping:"productsKeywordsRank.name"},
		{name:"type",mapping:"productsKeywordsRank.type"},
		{name:"expired",mapping:"expired"},
		{name:"startTime",mapping:"productsKeywordsRank.startTime"},
		{name:"endTime",mapping:"productsKeywordsRank.endTime"},
		{name:"buyTime",mapping:"productsKeywordsRank.buyTime"},
		{name:"companyName",mapping:"companyName"},
		{name:"productsTitle",mapping:"productsTitle"}
	]),
	mytoolbar:[]
//	,
//	buttonQuery:function(){
//		var tbar2=new Ext.Toolbar({
//			items:["-"]
//		});
//		
//		tbar2.render(this.tbar);
//	}
});

//表单
ast.ast1949.admin.keywords.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					xtype:"hidden",
					name:"productId",
					value:CONST.PRODUCT_ID
				},{
					xtype:"hidden",
					name:"companyId",
					value:CONST.COMPANY_ID
				},{
					xtype:"hidden",
					name:"applyAccount",
					value:CONST.ACCOUNT
				},{
					id:"name",
					name:"name",
					fieldLabel:"关键字名称",
					allowBlank : false,
					itemCls :"required",
					blankText : "关键字名称不能为空",
					listeners:{
						//change : ( Ext.form.Field this, Mixed newValue, Mixed oldValue )
						change:function(f,newv,oldv) {
							ast.ast1949.admin.keywords.GridRefresh();
						}
					}
				},{
					xtype : "combo",
					id:"type",
					name:"type",
					fieldLabel:"标王类别",
					itemCls :"required",
					listeners:{
						//change : ( Ext.form.Field this, Mixed newValue, Mixed oldValue )
						change:function(f,newv,oldv) {
							ast.ast1949.admin.keywords.GridRefresh();
						}
					},
					triggerAction : "all",
					forceSelection : true,
					displayField : "label",
					valueField : "code",
					store : new Ext.data.JsonStore({
					root : "records",
					fields : ["label", "code"],
					autoLoad : false,
					url : Context.ROOT
							+ Context.PATH
							+ "/admin/category/selectCategoiesByCode.htm?preCode="
							+ Context.CATEGORY.typeCode,
					listeners : {
						load : function() {
						}
					}
				})
				},{
					id:"startTime",
					name : "startTime",
					xtype : "datefield",
					fieldLabel : "开始时间",
					allowBlank : false,
					itemCls :"required",
					format : 'Y-m-d H:i:s',
					tabIndex : 3,
					anchor : "95%",
					value:new Date(),
					blankText : "开始时间不能为空"
				},{
					id : "endTime",
					name : "endTime",
					xtype : "datefield",
					fieldLabel : "结束时间",
					allowBlank : false,
					itemCls :"required",
					format : 'Y-m-d H:i:s',
					tabIndex : 3,
					anchor : "95%",
//					value:new Date(),
					blankText : "结束时间不能为空"
				},{
					name : "buyTime",
					xtype : "datefield",
					fieldLabel : "购买时间",
					allowBlank : false,
					itemCls :"required",
					format : 'Y-m-d H:i:s',
					tabIndex : 3,
					anchor : "95%",
					value:new Date(),
					blankText : "购买时间不能为空"
				},{
					xtype:"checkbox",
					checked:true,
					fieldLabel:"是否审核",
					boxLabel:"是",
					name:"isChecked",
					inputValue:"1"
				}]
			}],
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			}]
		};
		
		ast.ast1949.admin.keywords.editForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/keywords/save.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
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
	onSaveSuccess:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作成功！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
		ast.ast1949.admin.keywords.resultGridReload();
		Ext.getCmp(_C.TAG_EDIT_WIN).close();
	},
	onSaveFailure:function (){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "操作失败！",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}

});

/**
 * 刷新右侧列表
 */
ast.ast1949.admin.keywords.GridRefresh = function (){
	//TODO:获取用户填写的信息，再刷新“已存在的关键字排名信息”列表
	var up = Ext.getCmp(_C.EXISTS_KEYWORDS_GRID);
	
	var _name = Ext.get("name").dom.value;
	var _type = Ext.get("type").dom.value;
	
	up.store.baseParams = {"name":_name,"type":_type};
	up.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
}
