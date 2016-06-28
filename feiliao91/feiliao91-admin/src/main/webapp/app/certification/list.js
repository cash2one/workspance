/*
 * 认证信息
 */
Ext.namespace("ast.feiliao91.admin.certification")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//认证信息列表
ast.feiliao91.admin.certification.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header:"审核状态",
				sortable:true,
				dataIndex:"status",
				width:100,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var str="";
					if(value!=null){
						if(value==2){
							//已认证
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.True.gif" />';
						}else if(value==0){
							//未认证
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.Info.Small.gif" />';
						}else if(value==3){
							//认证不通过
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.False.gif" />';
						}else{
							//认证中
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.Warning.Small.gif" />';
						}
					}
					return str;
				}
			},{
				header:"认证公司",
				dataIndex:"Bus",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/certification/editGongShang.htm?id="+record.get("id")+"' target='_blank'>"+value+"</a>";
					return title;
				}
			},{
				header:"认证个体",
				dataIndex:"One",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/certification/editGeTi.htm?id="+record.get("id")+"' target='_blank'>"+value+"</a>";
					return title;
				}
			},{
				header:"公司名称",
				dataIndex:"compName",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			}, {
				header:"注册时间",
				dataIndex:"gmtReg",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					return "";
				}
			}, {
				header:"修改时间",
				dataIndex:"gmtModified",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
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
			};
		ast.feiliao91.admin.certification.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"companyInfo.id"},
		{name:"status",mapping:"companyInfo.creditStatus"},
		{name:"compName",mapping:"companyInfo.name"},
		{name:"gmtReg",mapping:"companyInfo.gmtReg"},
		{name:"gmtModified",mapping:"companyInfo.gmtModified"},
		{name:"Bus",mapping:"companyName"},//认证公司名
		{name:"One",mapping:"oneName"}//认证个体名
	]),
	mytoolbar:[
//	   		{
//	   			text:"编辑",
//	   			iconCls:"edit",
//	   			handler:function(btn){
//	   				var grid = Ext.getCmp(_C.RESULT_GRID);
//	   				var row = grid.getSelections();
//	   				for(var i=0;i<row.length;i++){
//	   					window.open(Context.ROOT+Context.PATH
//	   						+"/admin/credit/creditbaseinfo/edit.htm?id="+row[i].get("id"));
//	   				}
//	   			}
//	   		},
	   		"-",{
	   			text:"删除",
	   			handler:function(btn){
	   				ast.feiliao91.admin.certification.updateDelStatus(1);
	   			}
	   		},"-",
	   		{
	   			iconCls:"item-true",
	   			id:"checkButton",
	   			text:"通过认证",
	   			handler:function(btn){
	   				ast.feiliao91.admin.certification.updateStatus(2);
	   			}
	   		},"-",{
	   			iconCls:"item-false",
	   			id:"unCheckButton",
	   			text:"认证不通过",
	   			handler:function(btn){
	   				ast.feiliao91.admin.certification.updateStatus(3);
	   			}
	   		},"-",{
	   			iconCls:"item-warning",
	   			id:"waitCheckButton",
	   			text:"认证中",
	   			handler:function(btn){
	   				ast.feiliao91.admin.certification.updateStatus(1);
	   			}
	   		},"-","->",
	   		"-",{
	   			xtype:"checkbox",
				boxLabel:"已删除的",
				id:"isDelStatus",
				listeners:{
					"check":function(field,newvalue,oldvalue){
						var grid = Ext.getCmp(_C.RESULT_GRID);
						grid.searchByIsDelStatus();
					}
				}
	   		},"-",
	      	"公司名称",{
	   	   		xtype:"textfield",
	   	   		id:"compName",
	   	   		name:"compName",
	   	   		width:100
	   	   	},"-",{
		   	   	xtype:"combo",
				itemCls:"required",
				width:100,
				id:"creditStatusCombo",
				name:"creditStatusCombo",
				mode:"local",
				emptyText:"选择认证状态",
				triggerAction:"all",
				forceSelection: true,
				displayField:'name',
				valueField:'value',
				autoSelect:true,
				store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'全部',value:null},
						{name:'待审核',value:1},
						{name:'通过',value:2},
						{name:'未通过',value:3}
					]
				})
	   	   	},"-",{
	   	   		text:"查询",
	   	   		iconCls:"query",
	   	   		handler:function(){
	   		   		var grid = Ext.getCmp(_C.RESULT_GRID);
	   		   		var B=grid.store.baseParams;
	   		   		B=B||{};
	   		   		B["creditStatus"] = Ext.getCmp("creditStatusCombo").value;
	   		   		B["companyName"] = Ext.get("compName").dom.value;
	   		   		grid.store.baseParams = B;
	   	   			grid.store.reload({
	   	   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
	   	   			})
	   	   		}
	   	   	}
//				listeners:{
//					"blur":function(field){
//						var _store = Ext.getCmp("productsresultgrid").getStore();
//						var B = _store.baseParams;
//						B = B||{};
//						if(Ext.get(field.getId()).dom.value!=""){
//							B["isPause"] = field.getValue();
//						}else{
//							B["isPause"]=undefined;
//						}
//						_store.baseParams = B;
//						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
//					}
//				}
//	   	   	}
//	   	   	"-",{
//				xtype:"checkbox",
//				boxLabel:"待审核",
//				id:"unChecked",
//				listeners:{
//					"check":function(field,newvalue,oldvalue){
//						var grid = Ext.getCmp(_C.RESULT_GRID);
//						grid.searchByCheckStatus();
//					}
//				}
//			},
//			{
//				xtype:"checkbox",
//				boxLabel:"通过",
//				id:"beChecked",
//				listeners:{
//					"check":function(field,newvalue,oldvalue){
//						var grid = Ext.getCmp(_C.RESULT_GRID);
//						grid.searchByCheckStatus();
//					}
//				}
//			},
//			{
//				xtype:"checkbox",
//				boxLabel:"不通过",
//				id:"noChecked",
//				/*checked:true,*/
//				listeners:{
//					"check":function(field,newvalue,oldvalue){
//						var grid = Ext.getCmp(_C.RESULT_GRID);
//						grid.searchByCheckStatus();
//					}
//				}
//			}
	   	],
	   	searchByIsDelStatus:function(){
			var B=this.getStore().baseParams||{};
			var ary = new Array();
			if(Ext.getCmp("isDelStatus").getValue()){
				B["isDel"]=1;
			}else{
				B["isDel"]=0;
			}
//			B["isDel"] = ary.join(",");
			this.getStore().baseParams = B;
			this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
		}
});

//更新状态函数
ast.feiliao91.admin.certification.updateStatus = function(checkStatus){
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
		url: Context.ROOT+Context.PATH+"/admin/certification/updateStatus.htm",
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

ast.feiliao91.admin.certification.updateDelStatus = function(checkStatus){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	var row = grid.getSelections();
	var _ids = new Array();
	if(row.length==0){
		Ext.MessageBox.alert(Context.MSG_TITLE,"请选中你要删除的公司信息");
		return false;
	}
	for (var i=0,len = row.length;i<len;i++){
		_ids.push(row[i].get("id"));
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/admin/certification/updateDelStatus.htm",
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