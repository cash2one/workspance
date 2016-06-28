Ext.namespace("ast.ast1949.exhibit.previouExhibitors");
// 定义变量
var _C = new function() {
	this.PREVIOUEXHIBITORS_GRID = "previouExhibitorsGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.exhibit.previouExhibitors.PREVIOUEXHIBITORSFIELD=[
  	{name:"id",mapping:"id"},
	{name:"companyName",mapping:"companyName"},
	{name:"exhibitNum",mapping:"exhibitNum"},
	{name:"exhibitTime",mapping:"exhibitTime"},
	{name:"website",mapping:"website"},
	{name:"sort",mapping:"sort"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

ast.ast1949.exhibit.previouExhibitors.previouExhibitorsGrid=Ext.extend(Ext.grid.GridPanel,{
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
				header:"展会时间",
				width:200,
				dataIndex : "exhibitTime"
			},{    	
				header:"展位号",
				width:100,
				dataIndex : "exhibitNum"	
			},{    	
				header:"排序",
				width:100,
				dataIndex : "sort"	
			},{    	
				header:"企业名称",
				width:100,
				dataIndex : "companyName"	
			}
			]);
		var storeUrl = Context.ROOT + Context.PATH + "/admin/exhibit/queryList.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.exhibit.previouExhibitors.PREVIOUEXHIBITORSFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
				iconCls:"edit",
				text:"编辑",
				handler:function(btn){
					var grid = Ext.getCmp("previouExhibitorsGrid");
					var rows = grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						ast.ast1949.exhibit.previouExhibitors.updateChangeContent(rows[0].get("id"));
						_store.reload();
					}
					
				}
			},{
				iconCls:"add",
				text:"添加",
				id:'seo',
				name:'seo',
				listeners:{
					"click":function(field,e){
						
						var form = new ast.ast1949.exhibit.previouExhibitors.changeInfoForm({
						});

						var win = new Ext.Window({
						id :"_C.CHANGE_INFO_WIN",
						title : "修改的内容",
						width : "50%",
						modal : true,
						items : [form]
						});
						win.show();
					}
				}
			},{
				iconCls:"delete",
				text:"删除",
				handler:function(btn){
					var grid= Ext.getCmp("previouExhibitorsGrid");
					var rows=grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						
						Ext.MessageBox.confirm(Context.MSG_TITLE,"确定删除改展商",function(btn){
							if(btn!="yes"){
								return ;
							}else{
								ast.ast1949.exhibit.delPreviouExhibitors(rows[0].get("id"));
								_store.reload();
							}
						});	
					}
					
				}
			}];
		var c={
			id:"previouExhibitorsGrid",	
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
		ast.ast1949.exhibit.previouExhibitors.previouExhibitorsGrid.superclass.constructor.call(this,c);
	},
	

});



//修改页面
ast.ast1949.exhibit.previouExhibitors.updateChangeContent = function(id) {
	var form = new ast.ast1949.exhibit.previouExhibitors.changeInfoForm({
			});

	var win = new Ext.Window({
				id :"_C.CHANGE_INFO_WIN",
				title : "修改的内容",
				width : "50%",
				modal : true,
				items : [form]
			});
	win.show();
	form.loadRecords(id);
	
};
// 修改订制页面表单
ast.ast1949.exhibit.previouExhibitors.changeInfoForm = Ext.extend(Ext.form.FormPanel, {
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
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items : [{
					xtype : "hidden",
					name : "id",
					id : "id"
				},{
					fieldLabel:"展会时间",
					xtype:"numberfield",
					id:"exhibitTime",
				},{
					fieldLabel:"展位号",
					name:"exhibitNum",
					id:"exhibitNum",
				},{
					fieldLabel:"排序",
					xtype:"numberfield",
					name:"sort",
					id:"sort",
				},{	
					fieldLabel:"公司名称",
					name:"companyName",
					id:"companyName",
				},{	
					fieldLabel:"公司链接",
					name:"website",
					id:"website",
				}
				],
			buttons : [{
						text : "确定",
						handler:function(btn){
					         if(form.getForm().isValid()){
							var grid = Ext.getCmp("previouExhibitorsGrid");
							form.getForm().submit({
								url:Context.ROOT+Context.PATH+"/admin/exhibit/updatePreviouExhibitors.htm",
								method:"post",
								type:"json",
								success:function(f,action){
									ast.ast1949.utils.Msg("",action.result.data);
									Ext.getCmp("_C.CHANGE_INFO_WIN").close();
									grid.getStore().reload();	
								},
								failure:function(form,action){
									ast.ast1949.utils.Msg("",action.result.data);
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
		ast.ast1949.exhibit.previouExhibitors.changeInfoForm.superclass.constructor.call(this,c);
	},
	mystore : null,
	loadRecords : function(id) {
		var form = this;
		var store = new Ext.data.JsonStore({
					root : "records",
					fields :ast.ast1949.exhibit.previouExhibitors.PREVIOUEXHIBITORSFIELD,
					url : Context.ROOT + Context.PATH
							+ "/admin/exhibit/queryPreviouExhibitorsById.htm",
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

//删除关键字
ast.ast1949.exhibit.delPreviouExhibitors=function(id){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/admin/exhibit/delPreviouExhibtors.htm",
		params:{
		"id":id
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
