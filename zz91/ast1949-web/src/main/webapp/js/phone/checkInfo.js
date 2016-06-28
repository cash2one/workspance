Ext.namespace("ast.ast1949.phone");
// 定义变量
var _C = new function() {
	this.PHONECHANGELOG_GRID = "phoneChangeLogGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}
ast.ast1949.phone.PHONECHANGELOGINFOFIELD=[
  	{name:"id",mapping:"phoneChangeLog.id"},
	{name:"changeType",mapping:"phoneChangeLog.changeType"},
	{name:"changeContent",mapping:"phoneChangeLog.changeContent"},
	{name:"companyId",mapping:"phoneChangeLog.companyId"},
	{name:"companyName",mapping:"companyName"},
	{name:"gmtCreated",mapping:"phoneChangeLog.gmtCreated"},
	{name:"checkStatus",mapping:"phoneChangeLog.checkStatus"},
	{name:"targetId",mapping:"phoneChangeLog.targetId"},
	{name:"account",mapping:"account"}
];
ast.ast1949.phone.PHONECHANGELOGFIELD=[
  	{name:"id",mapping:"id"},
	{name:"changeType",mapping:"changeType"},
	{name:"changeContent",mapping:"changeContent"},
	{name:"companyId",mapping:"companyId"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"checkStatus",mapping:"checkStatus"},
	{name:"targetId",mapping:"targetId"},
];
var changeTypeMap={
			"1":"公司简介",
			"2":"主营业务",
			"3":"产品详细"			
		};
ast.ast1949.phone.phoneChangeLogGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{    	
				header:"公司名称",
				width:100,
				dataIndex : "companyName"	
			},{    	
				header:"帐号",
				width:100,
				dataIndex : "account"	
			},{    	
				header:"修改类型",
				width:100,
				dataIndex : "changeType",
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
    					return changeTypeMap[value];
    				}
			},{    	
				header:"修改内容",
				width:300,
				dataIndex : "changeContent"	
			},{    	
				header:"目标id",
				hidden:true,
				width:100,
				dataIndex : "targetId"	
			},{    	
				header:"修改时间",
				width:200,
				dataIndex : "gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{    	
				header:"审核状态",
				width:70,
				dataIndex : "checkStatus",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
						if(value==1){
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
						}else if(value==2){
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
						}else{
							return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
						}
					}	
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryCheckInfo.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.PHONECHANGELOGINFOFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
				iconCls:"add",
				text:"审核通过",
				handler:function(btn){
					ast.ast1949.phone.check(1);	
				}
			},{
				iconCls:"delete",
				text:"审核不通过",
				handler:function(btn){
					ast.ast1949.phone.check(2);	
				}
			},{
				iconCls:"edit",
				text:"编辑",
				handler:function(btn){
					
					var grid = Ext.getCmp("phoneChangeLogGrid");
					var rows = grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						if(rows[0].get("checkStatus")==1){
							alert("审核通过的内容不能修改");	
						}else{
							ast.ast1949.phone.updateChangeContent(rows[0].get("id"));
							_store.reload();
						}
					}

				}
			},{
				xtype:"checkbox",
				boxLabel:"未审核",
				id:"uncheckBtn",
				listeners:{
					"check":function(field,newvalue,oldvalue){
						var _store = Ext.getCmp("phoneChangeLogGrid").getStore();
						var B = _store.baseParams;
							if(field.getValue()){
								B["checkStatus"] = 0;
							}else{
								B["checkStatus"] = undefined;
							}
					        _store.baseParams = B;
				                _store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
					
				}
		}];
		var c={
			id:"phoneChangeLogGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
			bbar: new Ext.PagingToolbar({
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
		ast.ast1949.phone.phoneChangeLogGrid.superclass.constructor.call(this,c);
	},
	

});

ast.ast1949.phone.check=function(checkStatus){
		var grid = Ext.getCmp("phoneChangeLogGrid");
		var url = "/phone/isCheck.htm";
		var rows = grid.getSelections();
		if(rows.length>1){
			ast.ast1949.utils.Msg("","每次只能选择一条记录");
		}else{  
			if(rows[0].get("checkStatus")==1||rows[0].get("checkStatus")==2){
			 	alert("该记录已被审核");
			}else{
				Ext.Ajax.request({
					url: Context.ROOT+Context.PATH+url,
					params:{
						"id":rows[0].get("id"),
						"checkStatus":checkStatus
					},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success==true){
							ast.ast1949.utils.Msg("","审核成功");
							grid.getStore().reload();
						}else{
							ast.ast1949.utils.Msg("","操作失败");
						}	
					
					},
					failure:function(response,opt){
						ast.ast1949.utils.Msg("","操作失败");
					}
				});
			}
		}
	}

//修改页面
ast.ast1949.phone.updateChangeContent = function(id) {
	var form = new ast.ast1949.phone.changeInfoForm({
			});

	var win = new Ext.Window({
				id :"_C.CHANGE_INFO_WIN",
				title : "修改的内容",
				width : "80%",
				modal : true,
				items : [form]
			});
	form.loadRecords(id);
	win.show();
};
// 修改订制页面表单
ast.ast1949.phone.changeInfoForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid:null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var form=this;
		var c = {
			labelAlign : "right",
			layout : "form",
			frame:true,
			items : [{
					xtype : "hidden",
					name : "id",
					id : "id"
				},{
					xtype : "hidden",
					name : "changeType",
					id : "changeType"
				},{
					layout : 'column',
					columnWidth : 1,
					layout : 'form',
					xtype:"htmleditor",
					fieldLabel : "修改的内容",
					name : "changeContent",
					labelSeparator:"",
					height:200,
					anchor : "99%"
							
					
				
			}],
			buttons : [{
						text : "确定",
						handler:function(btn){
					         if(form.getForm().isValid()){
							var grid = Ext.getCmp("phoneChangeLogGrid");
							form.getForm().submit({
								url:Context.ROOT+Context.PATH+"/phone/updateChangeContent.htm",
								method:"post",
								type:"json",
								success:function(){
									ast.ast1949.utils.Msg("","保存成功");
									Ext.getCmp("_C.CHANGE_INFO_WIN").close();
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
				
					}}, {
						text : "关闭",
						handler : function() {
							Ext.getCmp("_C.CHANGE_INFO_WIN").close();
						},
						scope : this
					}]
		};
		ast.ast1949.phone.changeInfoForm.superclass.constructor.call(this,c);
	},
	mystore : null,
	loadRecords : function(id) {
		var form = this;
		var store = new Ext.data.JsonStore({
					root : "records",
					fields :ast.ast1949.phone.PHONECHANGELOGFIELD,
					url : Context.ROOT + Context.PATH
							+ "/phone/queryPhoneChangeLogById.htm",
					baseParams : {
						"id" : id
					},
					autoLoad : true,
					listeners : {
						"datachanged" : function(s) {
							var record = s.getAt(0);
							if (record == null) {
								Ext.MessageBox.alert(Context.MSG_TITLE,
										"数据加载失败...");
							} else {
								form.getForm().loadRecord(record);
							}
						}
					}
				})

	},
	
});
