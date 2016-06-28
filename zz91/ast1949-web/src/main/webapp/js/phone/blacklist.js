Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.BLACKLISTFIELD=[
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"id",mapping:"id"},
	{name:"phone",mapping:"phone"},
	{name:"phoneLogId",mapping:"phoneLogId"},
	{name:"checkPerson",mapping:"checkPerson"},
	{name:"blackReason",mapping:"blackReason"}
 ];

ast.ast1949.phone.blacklistGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,
			{
				header : "id",
				hidden: true,
				dataIndex:"id"
			},{
				header : "黑名单电话",
				width:200,
				sortable:false,
				dataIndex:"phone"
			},{
				header : "添加时间",
				width:150,
				sortable:false,
				dataIndex:"gmtCreated",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "总计:";
					}
				}

			},{
				header : "对应的通话记录ID",
				width:120,
				sortable:false,
				dataIndex:"phoneLogId",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null && value!=0){
						return value;
					}
					else{
						return "";
					}
				}
			},{
				header : "操作人",
				width:120,
				sortable:false,
				dataIndex:"checkPerson"
			},{
				header : "拉黑原因",
				width:200,
				sortable:false,
				dataIndex:"blackReason",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value=="1"){
						return "卖药的";
					}
					if(value=="2"){
						return "打广告的";
					}
					if(value=="3"){
						return "跨行业的";
					}
					if(value=="4"){
						return "海运推广";
					}
					if(value=="5"){
						return "同行网站";
					}
					if(value=="6"){
						return "号码拨错";
					}
					if(value=="7"){
						return "拨打落地号";
					}
					else{
						return "";
					}
				}
			}		
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryBlacklist.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.BLACKLISTFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [{
				iconCls:"add",
				text:"添加新黑名单",
				handler:function(btn){
					ast.ast1949.phone.addOne();
				}
			},{
				iconCls:"delete",
				text:"删除",
				handler:function(btn){
					ast.ast1949.phone.delOne();
					_store.reload();
				}
			},{
				iconCls:"edit",
				text:"开启黑名单功能",
				handler:function(btn){
					ast.ast1949.phone.openBlacklist();
				}
			},"-","查找号码：",{
            	id:"phone",
            	xtype:"textfield",
            	format:"Y-m-d"
            },{
            	text:"查询",
            	iconCls:"query",
            	handler:function(btn){
            		var phone=Ext.getCmp("phone").getValue();
            		_store.baseParams["phone"]=phone;
            		_store.reload();
            	}
            }
		];

		var c={
			id:"blacklistGrid",	
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
		ast.ast1949.phone.blacklistGrid.superclass.constructor.call(this,c);
	}
});

//添加新黑名单
ast.ast1949.phone.addOne=function(){
	var form=new ast.ast1949.phone.addOneForm({
	});
	
	var win = new Ext.Window({
		id:"addOnewin",
		title:"添加黑名单",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

//删除
ast.ast1949.phone.delOne=function(){
	var grid = Ext.getCmp("blacklistGrid");
	var rows = grid.getSelections();
	var ids = "";
	for(var i=0;i<rows.length;i++){
		ids = ids + "," + rows[i].get("id")
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/phone/batchDelBlackList.htm",
		params:{
		"ids":ids
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			ast.ast1949.utils.Msg("",obj.data)
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
}

//开通黑名单状态
ast.ast1949.phone.openBlacklist=function(){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/phone/batchUpdateStatus.htm",
		success:function(response,opt){
			ast.ast1949.utils.Msg("","操作成功")
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
}

ast.ast1949.phone.addOneForm = Ext.extend(Ext.form.FormPanel,{
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
				fieldLabel:"号码可用英文逗号分隔添加多个",
				name:"phone"
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("blacklistGrid");
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/phone/batchAddBlackList.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("addOnewin").close();
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
		
		ast.ast1949.phone.addOneForm.superclass.constructor.call(this,c);
	}
});
