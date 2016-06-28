Ext.namespace("ast.ast1949.crm.phoneCostSvr");

ast.ast1949.crm.phoneCostSvr.FIELD = [
    {name:"id",mapping:"id"},
    {name:"companyId",mapping:"companyId"},
  	{name:"clickFee",mapping:"clickFee"},
  	{name:"telFee",mapping:"telFee"}
];

ast.ast1949.crm.phoneCostSvr.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header : "公司Id",
				sortable : false,
				dataIndex : "company_id"
			},{
				header : "开通日期",
				width : 200,
				sortable : false,
				dataIndex : "gmt_created",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
					}
				}
			},{
				header : "点击费用",
				sortable : false,
				dataIndex : "clickFee",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					return value+"（元/点击）";
				}
			},{
				header : "电话费用",
				sortable : false,
				dataIndex : "telFee",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					return value+"（元/分钟）";
				}
			},{
				header : "充值金额",
				sortable : false,
				dataIndex : "fee",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					return value+"(元)";
				}
			},{
				header : "余额",
				sortable : false,
				dataIndex : "lave",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					return value+"(元)";
				}
			},{
				header : "为零日期",
				sortable : false,
				dataIndex : "gmt_zero",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
					}
				}
			},{
				header : "开始消费日期",
				sortable : false,
				dataIndex : "gmt_renew",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time),'Y-m-d H:i:s');
					}
				}
			},{
				header : "是否欠费",
				sortable : false,
				dataIndex : "is_lack",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(value!="0"){
						val="欠费";
					}else{
						val="激活";
					}
					return val;
				}
			}]);

		// 字段信息
		var reader = [{name:"id",mapping:"id"},
		   {name:"company_id",mapping:"companyId"},
		   {name:"gmt_created",mapping:"gmtCreated"},
		   {name:"clickFee",mapping:"clickFee"},
		   {name:"telFee",mapping:"telFee"},
		   {name:"fee",mapping:"fee"},
		   {name:"lave",mapping:"lave"},
		   {name:"is_lack",mapping:"isLack"},
		   {name:"gmt_zero",mapping:"gmtZero"},
		   {name:"gmt_renew",mapping:"gmtRenew"}
		   ];

		var storeUrl = Context.ROOT + Context.PATH + "/api/ldb/queryPhoneCostSvr.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [{
			text:"修改",
			iconCls:"edit",
			handler:function(){
				if (sm.getCount() == 0)
					Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
				else
					var row = grid.getSelections();
					ast.ast1949.crm.phoneCostSvr.UpdateSvr(row[0].get("id"));
//					window.open(Context.ROOT+Context.PATH+"/api/ldb/editCostSvr.htm?companyId="+row[0].get("id"));
			}
		}];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:7,
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
			}),
			listeners:{
				"rowcontextmenu":function(g,rowIndex,e){
					if(!g.getSelectionModel().isSelected(rowIndex)){
						g.getSelectionModel().clearSelections();
						g.getSelectionModel().selectRow(rowIndex);
					}
					e.preventDefault();
					if(g.contextmenu!=null){
						g.contextmenu.showAt(e.getXY());
					}
				}
			}
		};
		ast.ast1949.crm.phoneCostSvr.Grid.superclass.constructor.call(this,c);
	},
	loadByCompany:function(companyId){
//		this.getStore().reload({params:{"companyId":companyId}});
		var B=this.getStore().baseParams;
		B=B||{};
		B["companyId"]=companyId;
		this.getStore().baseParams=B;
		this.getStore().reload({});
	}
});

//服务信息修改
ast.ast1949.crm.phoneCostSvr.UpdateSvr=function(id){
	
	var form=new ast.ast1949.crm.phoneCostSvr.UpdateSvrForm({
	});
	
	form.loadSvr(id);
	
	var win = new Ext.Window({
		id:"updateSvrwin",
		title:"服务信息",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.crm.phoneCostSvr.UpdateSvrForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"公司Id",
				readOnly:true,
				name:"companyId",
				id:"companyId"
			},{
				fieldLabel:"点击费用",
				name:"clickFee",
				id:"clickFee",
				itemCls:"required",
				allowBlank:false	
			},{
				fieldLabel:"通话费用",
				name:"telFee",
				id:"telFee",
				itemCls:"required",
				allowBlank:false
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("phoneSvrGrid");
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/api/ldb/updateSvr.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updateSvrwin").close();
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
			}]
		};
		
		ast.ast1949.crm.phoneCostSvr.UpdateSvrForm.superclass.constructor.call(this,c);
	},
	loadSvr:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.crm.phoneCostSvr.FIELD,
			url : Context.ROOT+Context.PATH+"/api/ldb/queryCostSvrById.htm?id="+id, 
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