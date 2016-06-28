/*
 * 公司列表
 */
Ext.namespace("ast.feiliao91.admin.company")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//所有公司列表
ast.feiliao91.admin.company.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
			autoLoad:true
		});
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
		    header:"编号",
			dataIndex:"id",
			width:50,
			hidden:true
		},{
			header:"公司名称",
			dataIndex:"name",
			width:250,
			renderer:function(value,m,record,ridx,cidx,store,view){
				if(value!=null){
					_url=Context.ROOT+Context.PATH+"/admin/company/detail.htm?companyId="+record.get("id");
					return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
				}
				return "";
			}
		},{
			header:"密码",
			dataIndex:"password"
		},{
			header:"登录帐号",
			width:150,
			dataIndex:"account"
		},{
			header:"手机号码",
			width:150,
			dataIndex:"mobile"
		},{
	    	header:"地区",
			dataIndex:"address"
	    },{
	    	header:"注册时间",
	    	sortable:true,
			dataIndex:"gmt_reg",
			width:130,
			renderer:function(value){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				return "";
			}
	    },{
	    	header:"最后登录时间",
	    	sortable:true,
			dataIndex:"gmt_last_login",
			width:130,
			renderer:function(value){
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				return "";
			}
		}
		]);
		
		var con={
				iconCls:"icon-grid",
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
//				tbar:[{
//					text:"审核",
//					menu:[{
//						text:"审核通过",
//						handler:function(btn,e){
//							ast.feiliao91.admin.goods.updateStatus(1);
//						}
//					},{
//						text:"审核不通过",
//						handler:function(btn,e){
//							ast.feiliao91.admin.goods.updateStatus(2);
//						}
//					}]
//				}],
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
			};
		ast.feiliao91.admin.company.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
   		{name:"id",mapping:"companyInfo.id"},
		        {name:"name",mapping:"companyInfo.name"},
		        {name:"password",mapping:"companyAccount.password"},
		        {name:"account",mapping:"companyAccount.account"},
		        {name:"mobile",mapping:"companyAccount.mobile"},
		        {name:"address",mapping:"address"},
		        {name:"gmt_reg",mapping:"companyInfo.gmtReg"},
		        {name:"gmt_last_login",mapping:"companyAccount.gmtLastLogin"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/company/queryCompanyList.htm",
});

ast.feiliao91.admin.company.updateStatus = function(checkStatus){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	var row = grid.getSelections();
	var _ids = new Array();
	if(row.length==0){
		Ext.MessageBox.alert(Context.MSG_TITLE,"请选中你要审核的产品信息");
		return false;
	}
	for (var i=0,len = row.length;i<len;i++){
		_ids.push(row[i].get("id"));
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/admin/good/updateStatus.htm",
		params: {
			ids: _ids.join(","),
			checkStatus:checkStatus
		},
		success: function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
				grid.getStore().load();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
			}
		}
	});
}

//右下部搜索框
ast.feiliao91.admin.company.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};
		
		var c={
				bodyStyle : "padding:2px 2px 0",
				labelAlign : "right",
				labelWidth : 80,
				autoScroll:true,
				layout : "column",
				items:[{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel : "公司名：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["companyName"] = null;
								}else{
									B["companyName"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
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
						xtype:"combo",
						mode:"local",
						fieldLabel:"公司类型：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
							    {name:'废塑料加工厂',value:"10091000"},
							    {name:'塑料造粒厂',value:"10091001"},
							    {name:'塑料贸易商',value:"10091002"},
							    {name:'塑料改性',value:"10091003"},
							    {name:'塑料制品加工厂',value:"10091004"}
							]
						}),
//						clearValue : function() {     
//					        if (this.passField) {     
//					            this.passField.value = '';     
//					        }     
//					        this.setRawValue('');     
//					    },
						listeners:{
							"blur":function(field,newvalue,oldvalue){
//								console.log(field.getValue());
								if(field.getValue()!=undefined){
									B["companyCode"] = field.getValue();
								}else{
									B["companyCode"] = undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combotree",
						triggerAction : "all",
						forceSelection : true,
						fieldLabel:"地区",
						id:"diqu",
						displayField : "label",
						valueField : "code",
						hiddenId:"search-areaCode",
						hiddenName:"search-areaCode",
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
									B["area"] =  Ext.get("search-areaCode").dom.value;
								}else{
									B["area"] = undefined;
								}
								_store.baseParams = B;
							}
						}
						
					},{
						xtype:"combo",
						mode:"local",
						fieldLabel:"时间类型：",
						triggerAction:"all",
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
							fields : ['name', 'value'],
							data   : [
								{name:'注册时间',value:"ca.gmt_created"},
								{name:'最后登录时间',value:"ca.gmt_last_login"},
							]
						}),
						listeners:{
							"blur":function(field,newvalue,oldvalue){
								if(field.getValue()!=undefined){
									B["dateType"] = field.getValue();
								}else{
									B["dateType"] = undefined;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"datefield",
						id : "regFrom",
						name:"regFrom",
						format:"Y-m-d",
						fieldLabel : "时间(始)：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["from"] = undefined;
								}else{
									B["from"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"datefield",
						id : "regTo",
						name:"regTo",
						format:"Y-m-d",
						fieldLabel : "时间(终)：",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["to"] = undefined;
								}else{
									B["to"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					}]
				}],
				buttons:[{
					text:"按条件搜索",
					handler:function(btn){
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				},{
					text:"清除地区内容",
					handler:function(btn){
						Ext.getCmp("diqu").setValue({
							text:"",
                            attributes:{code:"12324"}
                            	});
						B["area"]="";
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}]
		};
		
		ast.feiliao91.admin.company.SearchForm.superclass.constructor.call(this,c);
	}
});

