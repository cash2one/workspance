Ext.namespace("ast.ast1949.admin.adminCompany");

var COMPANY = new function() {
	this.RESULT_GRID="resultgrid";
}

ast.ast1949.admin.adminCompany.FILED=[
	{name:"id",mapping:"id"},
	{name:"name",mapping:"name"},
	{name:"regtime",mapping:"regtime"},
	{name:"business",mapping:"business"},
	{name:"numVisitMonth",mapping:"numVisitMonth"},
	{name:"gmtVisit",mapping:"gmtVisit"},
	{name:"domain",mapping:"domain"},
	{name:"address",mapping:"address"},
	{name:"businessType",mapping:"businessType"},
	{name:"membershipCode",mapping:"membershipCode"},
	{name:"zstYear",mapping:"zstYear"}
];


ast.ast1949.admin.adminCompany.resultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;
		
		var storeUrl = Context.ROOT + Context.PATH + "/crm/company/queryCompanyAdmin.htm";
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.adminCompany.FILED,
			url: storeUrl,
			autoLoad:true
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{
				header : "公司名",
				width : 200,
				sortable : false,
				dataIndex : "name"
			},{
				header : "主营产品",
				width : 200,
				sortable : false,
				dataIndex : "business"
			},{
				header : "再生通年限",
				width : 100,
				sortable : false,
				dataIndex : "zstYear"
			},{
				header : "本月联系次数",
				width : 100,
				sortable : false,
				dataIndex : "numVisitMonth"
			},{
				header : "地址",
				width : 250,
				sortable : false,
				dataIndex : "address"
			},{
				header : "最后联系时间",
				width : 100,
				sortable : false,
				dataIndex : "gmtVisit",
				renderer:function(value, metadata, record, rowIndex,colIndex, store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}	
			},{
				header : "注册时间",
				width : 130,
				sortable : true,
				dataIndex : "regtime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}]);
	
		var tbar = ["->","是否黑名单：",{
			xtype:"combo",
			mode:"local",
			width:80,
			triggerAction:"all",
			forceSelection: true,
			store:[
				    ["","全部"],
				    ["1","是"],
				    ["0","否"]
			],
			listeners:{
				"change":function(field){
					var B=_store.baseParams||{};
					if(field.getValue()!=""){
						B["isBlock"]=field.getValue();
					}else{
						B["isBlock"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}	
		},"注册来源：",{
			xtype:"combo",
			mode:"local",
			width:120,
			triggerAction:"all",
			forceSelection : true,
			displayField : "label",
			valueField : "code",
			hiddenName:"regfromCode",
			hiddenId:"regfromCode",
			store:new Ext.data.JsonStore({
				root : "records",
				fields : [ "label", "code" ],
				autoLoad:true,
				url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["regfromCode"]
			}),
			listeners:{
				"change":function(field,newvalue,oldvalule){
					var B=_store.baseParams||{};
					if(newvalue==""){
						B["regfromCode"]=undefined;
					}else{
						B["regfromCode"]=newvalue;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},"按类型：",{
			xtype:"combo",
			mode:"local",
			width:120,
			triggerAction:"all",
			store:[
			    ["","全部"],   
			    ["10051000","普通会员"],
			    ["10051001","再生通会员"],
			    ["10051002","品牌通会员"]
			],
			listeners:{
			"change":function(field,newvalue,oldvalule){
				var B=_store.baseParams||{};
				if(newvalue==""){
					B["membershipCode"]="";
				}else{
					B["membershipCode"]=newvalue;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
		}];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			tbar : tbar
		};
		ast.ast1949.admin.adminCompany.resultGrid.superclass.constructor.call(this,c);
	}
});

/**
 * 公司搜索表单
 * */
ast.ast1949.admin.adminCompany.searchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};
		var form=this;
		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 100,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "帐号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["account"] = null;
							}else{
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "公司名：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["name"] = null;
							}else{
								B["name"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "邮箱：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["email"] = null;
							}else{
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "手机：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["mobile"] = null;
							}else{
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "地址：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							B["address"] = newvalue;
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"主营行业：",
					displayField : "label",
					valueField : "code",
					store:new Ext.data.JsonStore( {
						root : "records",
						fields : [ "label", "code" ],
						autoLoad:false,
						url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["service"]
					}),
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue!="") {
								B["industryCode"] =  newvalue;
							}else{
								B["industryCode"] = null;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combotree",
					fieldLabel:"地区：",
					hiddenName : "search-areaCode",
					hiddenId : "search-areaCode",
					editable:true,
					tree:new Ext.tree.TreePanel({
						loader: new Ext.tree.TreeLoader({
							root : "records",
							fields : [ "label", "code" ],
							autoLoad: false,
							url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
							listeners:{
								beforeload:function(treeload,node){
									this.baseParams["parentCode"] = node.attributes["data"];
								}
							}
						}),
				   	 	root : new Ext.tree.AsyncTreeNode({text:'地区',data:Context.CATEGORY.areaCode})
					}),
					listeners:{
						"blur":function(field){
							if(Ext.get("search-areaCode").dom.value!=""){
								B["areaCode"] =  Ext.get("search-areaCode").dom.value;
							}else{
								B["areaCode"] = null;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					fieldLabel:"注册时间(始)：",
					format:"Y-m-d",
		        	listeners:{
						"change":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["gmtRegisterStart"] = field.getValue();
							}
							else{
								B["gmtRegisterStart"] = null;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"datefield",
					fieldLabel:"注册时间(末)：",
					format:"Y-m-d",
		        	listeners:{
						"change":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["gmtRegisterEnd"] = field.getValue();
							}
							else{
								B["gmtRegisterEnd"] = null;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};
		ast.ast1949.admin.adminCompany.searchForm.superclass.constructor.call(this,c);
	}
});
