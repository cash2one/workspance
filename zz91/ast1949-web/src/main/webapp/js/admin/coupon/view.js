Ext.namespace("ast.ast1949.admin.companyCoupon");
// 定义变量
var _C = new function() {
	this.companyCoupon_GRID = "CompanyCouponGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}
ast.ast1949.admin.companyCoupon.CompanyCouponInFOFIELD=[
  	{name:"id",mapping:"companyCoupon.id"},
	{name:"companyId",mapping:"companyCoupon.companyId"},
	{name:"serviceName",mapping:"companyCoupon.serviceName"},
	{name:"price",mapping:"companyCoupon.price"},
	{name:"gmtCreated",mapping:"companyCoupon.gmtCreated"},
	{name:"gmtModified",mapping:"companyCoupon.gmtModified"},
	{name:"status",mapping:"companyCoupon.status"},
	{name:"reducePrice",mapping:"companyCoupon.reducePrice"},
	{name:"companyName",mapping:"companyName"},
	{name:"email",mapping:"email"},
	{name:"mobile",mapping:"mobile"},
];
ast.ast1949.admin.companyCoupon.CompanyCouponFIELD=[
  	{name:"id",mapping:"id"},
	{name:"companyId",mapping:"companyId"},
	{name:"serviceName",mapping:"serviceName"},
	{name:"price",mapping:"price"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"},
	{name:"status",mapping:"status"},
	{name:"reducePrice",mapping:"reducePrice"},
];
var changeStatusMap={
			"1":"激活",
			"2":"关闭",
			"3":"付款成功",
			"4":"待支付"
						
		};
ast.ast1949.admin.companyCoupon.companyCouponGrid=Ext.extend(Ext.grid.GridPanel,{
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
				header:"公司ID",
				hidden:true,
				width:100,
				dataIndex : "companyId"	
			},{    	
				header:"公司名称",
				width:100,
				dataIndex : "companyName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					if(record.get("companyId")!="0"){
						var val="<a href='" + Context.ROOT + Context.PATH + 
							"/crm/cs/detail.htm?companyId=" + 
							record.get("companyId") + "' target='_blank'>" + 
							value + "</a>";
						890715
					}else{
						var val=""
					}
					return val;
				}	
			},{    	
				header:"公司邮箱",
				width:100,
				dataIndex : "email"	
			},{    	
				header:"手机号",
				width:100,
				dataIndex : "mobile"	
			},{    	
				header:"优惠劵服务名",
				width:300,
				dataIndex : "serviceName"	
			},{    	
				header:"原始价格",
				width:100,
				dataIndex :"price"	
			},{    	
				header:"优惠费用",
				width:100,
				dataIndex : "reducePrice"	
			},{    	
				header:"状态",
				width:100,
				dataIndex : "status",
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
    					return changeStatusMap[value];
    				}	
			},{    	
				header:"创建时间",
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
				header:"修改时间",
				width:200,
				dataIndex : "gmtModified",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/companycoupon/queryCompanyCoupon.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.admin.companyCoupon.CompanyCouponInFOFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [
				{
				iconCls:"edit",
				text:"报名",
				handler:function(btn){
					
					var grid = Ext.getCmp("companyCouponGrid");
					var rows = grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
							ast.ast1949.admin.companyCoupon.updateChangeContent(rows[0].get("id"));
							_store.reload();
					}

				}
			},"-",{
				iconCls:"add",
				text:"已付款客户激活",
				handler:function(btn){
					var grid = Ext.getCmp("companyCouponGrid");
					var rows = grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{
						Ext.MessageBox.confirm(Context.MSG_TITLE,"你确定要将选中的客户优惠激活吗？",function(btn){
							if(btn != "yes"){
								return false;
							}
							for(var i=0;i<rows.length;i++){
								Ext.Ajax.request({
									url: Context.ROOT+Context.PATH+ "/admin/companycoupon/openCoupon.htm",
									params:{
										"id":rows[i].get("id")
									},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											ast.ast1949.utils.Msg("","已激活");
											_store.reload();
										}else{
											ast.ast1949.utils.Msg("","操作失败");
										}
									},
									failure:function(response,opt){
										ast.ast1949.utils.Msg("","操作失败");
									}
								});
							}
						})
//						ast.ast1949.admin.companyCoupon.updateChangeContent(rows[0].get("id"));
					}
				}
			},"->","email:",{
				xtype:"textfield",
				fieldLabel:"email搜索",
				listeners:{
					"blur":function(field){
						var B=_store.baseParams||{}
						if(field.getValue()!=""){
							B["email"] = field.getValue();
						}else{
							B["email"]=undefined;
						}
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
			}
		];
		var c={
			id:"companyCouponGrid",	
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
		ast.ast1949.admin.companyCoupon.companyCouponGrid.superclass.constructor.call(this,c);
	},
	

});


//修改页面
ast.ast1949.admin.companyCoupon.updateChangeContent = function(id) {
	var form = new ast.ast1949.admin.companyCoupon.changeInfoForm({
			});

	var win = new Ext.Window({
				id :"_C.CHANGE_INFO_WIN",
				title : "修改的内容",
				width : "30%",
				modal : true,
				items : [form]
			});
	form.loadRecords(id);
	win.show();
};
// 修改订制页面表单
ast.ast1949.admin.companyCoupon.changeInfoForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid:null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var form=this;
		var c = {
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"90%",
				xtype:"textfield",
				labelSeparator:""
			},
			items : [{
					xtype : "hidden",
					name : "id",
					id : "id"
				},{
					fieldLabel:"优惠劵服务名",
					name:"serviceName",
					id:"serviceName",
				},{
					fieldLabel:"原始价格",
					name:"price",
					id:"price",
				},{
					fieldLabel:"优惠费用",
					name:"reducePrice",
					id:"reducePrice",
				}//,{	
					//xtype:"combo",
					//mode:"local",
					//readOnly:true,
					//triggerAction:"all",
					//hiddenName:"status",
					//hiddenId:"status",
					//fieldLabel:"状态",
					//store:[
					//["1","激活"],
					//["2","关闭"],
					//["3","付款成功"]
					
					//]
					
				//}
				],
			buttons : [{
						text : "确定",
						handler:function(btn){
					         if(form.getForm().isValid()){
							var grid = Ext.getCmp("companyCouponGrid");
							form.getForm().submit({
								url:Context.ROOT+Context.PATH+"/admin/companycoupon/updateCompanyCoupon.htm",
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
		ast.ast1949.admin.companyCoupon.changeInfoForm.superclass.constructor.call(this,c);
	},
	mystore : null,
	loadRecords : function(id) {
		var form = this;
		var store = new Ext.data.JsonStore({

					root : "records",
					fields :ast.ast1949.admin.companyCoupon.CompanyCouponFIELD,
					url : Context.ROOT + Context.PATH
							+ "/admin/companycoupon/queryCompanyCouponById.htm",
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
