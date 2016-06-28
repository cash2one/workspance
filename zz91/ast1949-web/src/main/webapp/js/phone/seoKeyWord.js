Ext.namespace("ast.ast1949.phone.seoKeyWord");
// 定义变量
var _C = new function() {
	this.PHONESEOKEYWORD_GRID = "phoneSeoKeyWordGrid";
	this.CHANGEINFO_FORM = "changeInfoForm";
	this.CHANGE_INFO_WIN = "changeInfoFormwin";
}

ast.ast1949.phone.seoKeyWord.PHONESEOKEYWORDSFIELD=[
  	{name:"id",mapping:"id"},
	{name:"title",mapping:"title"},
	{name:"pinYin",mapping:"pinYin"},
	{name:"clickCount",mapping:"clickCount"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

ast.ast1949.phone.seoKeyWord.phoneSeoKeyWordGrid=Ext.extend(Ext.grid.GridPanel,{
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
				header:"关键字",
				width:100,
				dataIndex : "title"	
			},{    	
				header:"关键字拼音",
				width:100,
				dataIndex : "pinYin"	
			},{    	
				header:"点击次数",
				width:100,
				dataIndex : "clickCount"	
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
		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryList.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.seoKeyWord.PHONESEOKEYWORDSFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [{
				iconCls:"edit",
				text:"编辑",
				handler:function(btn){
					var grid = Ext.getCmp("phoneSeoKeyWordGrid");
					var rows = grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						ast.ast1949.phone.seoKeyWord.updateChangeContent(rows[0].get("id"));
						_store.reload();
					}
					
				}
			},{
				iconCls:"add",
				text:"添加关键字",
				id:'seo',
				name:'seo',
				listeners:{
					"click":function(field,e){
						
						var form = new ast.ast1949.phone.seoKeyWord.changeInfoForm({
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
				text:"删除关键字",
				handler:function(btn){
					var grid= Ext.getCmp("phoneSeoKeyWordGrid");
					var rows=grid.getSelections();
					if(rows.length>1){
						ast.ast1949.utils.Msg("","每次只能选择一条记录");
					}else{  
						
						Ext.MessageBox.confirm(Context.MSG_TITLE,"确定删除该关键字",function(btn){
							if(btn!="yes"){
								return ;
							}else{
								ast.ast1949.phone.delSeoKeyWords(rows[0].get("id"));
								_store.reload();
							}
						});	
					}
					
				}
			}];
		var c={
			id:"phoneSeoKeyWordGrid",	
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
		ast.ast1949.phone.seoKeyWord.phoneSeoKeyWordGrid.superclass.constructor.call(this,c);
	},
	

});



//修改页面
ast.ast1949.phone.seoKeyWord.updateChangeContent = function(id) {
	var form = new ast.ast1949.phone.seoKeyWord.changeInfoForm({
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
ast.ast1949.phone.seoKeyWord.changeInfoForm = Ext.extend(Ext.form.FormPanel, {
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
					fieldLabel:"seo关键字",
					name:"title",
					id:"title",
				},{
					fieldLabel:"关键字拼音",
					name:"pinYin",
					id:"pinYin",
				},{	
					xtype:"numberfield",
					fieldLabel:"点击次数",
					name:"clickCount",
					id:"clickCount",
				}
				],
			buttons : [{
						text : "确定",
						handler:function(btn){
					         if(form.getForm().isValid()){
							var grid = Ext.getCmp("phoneSeoKeyWordGrid");
							form.getForm().submit({
								url:Context.ROOT+Context.PATH+"/phone/updateSeoKeyWordContent.htm",
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
		ast.ast1949.phone.seoKeyWord.changeInfoForm.superclass.constructor.call(this,c);
	},
	mystore : null,
	loadRecords : function(id) {
		var form = this;
		var store = new Ext.data.JsonStore({
					root : "records",
					fields :ast.ast1949.phone.seoKeyWord.PHONESEOKEYWORDSFIELD,
					url : Context.ROOT + Context.PATH
							+ "/phone/queryPhoneSeoKeyWordById.htm",
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
ast.ast1949.phone.delSeoKeyWords=function(id){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/phone/delSeoKeyWords.htm",
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
