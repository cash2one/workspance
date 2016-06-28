Ext.namespace('com.zz91.sms.template');

var TEMPLATE = new function(){
	this.TEMPLATE_GRID="templategrid";
	this.ADD_FORM="addform";
	this.EDIT_FORM="editform";
	this.EDIT_WIN="editwin";
}

com.zz91.sms.template.Field=[
	{name:"id",mapping:"id"},
	{name:"code",mapping:"code"},
	{name:"titles",mapping:"titles"},
	{name:"content",mapping:"content"},
	{name:"signed",mapping:"signed"},
];

com.zz91.sms.template.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				fields:com.zz91.sms.template.Field,
				url:Context.ROOT +  "/template/queryTemplate.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( 
				[ _sm,{
			header : "模板代号",
			sortable : false,
			dataIndex : "code"
		},{
			header :"模板标题",
			sortable : false,
			dataIndex:"titles"
		},{
			header:"模板内容",
			sortable:false,
			dataIndex:"content"
		},{
			header:"短信签名",
			sortable:false,
			dataIndex:"signed"
		}]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar: com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.sms.template.Grid.superclass.constructor.call(this,c);
	},
	mytoolbar:[
	{
		text : '新增',
		iconCls : 'add16',
		handler : function(btn){
			com.zz91.sms.template.addFormWin();
		}
	},'-',{
		text : '修改',
		iconCls : 'edit16',
		handler : function(btn){
			var sm = Ext.getCmp('templategrid').getSelectionModel();
			var row = sm.getSelections();
			var selectedRecord = Ext.getCmp('templategrid').getSelectionModel().getSelected();
			if(row.length>1){
				com.zz91.utils.Msg("","最多只能选择一条记录！");
			} else {
				var row = sm.getSelections();
				var _id=row[0].get("id");
				com.zz91.sms.template.editFormWin(_id);
			}
		}
	},'-',{
		text : '删除',
		iconCls : 'delete16',
		handler : function(btn){
		
		var row = Ext.getCmp('templategrid').getSelectionModel().getSelections();
		
		if (row.length > 0) {
			Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + row.length + '条记录?', function(_btn){
				if (_btn != "yes")
					return;
				for (var i = 0, len = row.length; i < len; i++) {
					Ext.Ajax.request({
						url:Context.ROOT +  "/template/delete.htm",
						params:{"id":row[i].get("id")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								com.zz91.utils.Msg("","删除记录成功");
								Ext.getCmp('templategrid').getStore().reload();
							}else{
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						},
						failure:function(response,opt){
							Ext.MessageBox.show({
								title:MESSAGE.title,
								msg : MESSAGE.submitFailure,
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});
				}
			});
		}
	}
	}]
	
});



/**
 * 编辑表单
 */
com.zz91.sms.template.editForm=Ext.extend(Ext.form.FormPanel, {
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
					name:"code",
					fieldLabel:"模板代号",
					itemCls :"required",
					blankText : "模板代号不能为空",
					allowBlank : false
				},{
					name:"titles",
					fieldLabel:"模板标题"
				},{
					name:"signed",
					fieldLabel:"短信签名"
				},{
					xtype:"textarea",
					name:"content",
					fieldLabel:"模板内容",
					allowBlank : true
				}]
				}
			],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(TEMPLATE.EDIT_WIN).close();
				},
				scope:this
			}
			]
		};
		
		com.zz91.sms.template.editForm.superclass.constructor.call(this,c);
	},
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"code",mapping:"code"},
			{name:"titles",mapping:"titles"},
			{name:"signed",mapping:"signed"},
			{name:"content",mapping:"content"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			fields : _fields,
			url : Context.ROOT + "/template/queryById.htm?st="+timestamp(),
			baseParams:{"id":id},
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
		})
	},
	saveUrl:Context.ROOT + "/template/add.htm?st="+timestamp(),
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
			com.zz91.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		com.zz91.utils.Msg("","保存成功！");
		Ext.getCmp('templategrid').getStore().reload();
		
		Ext.getCmp(TEMPLATE.EDIT_WIN).close();
	},
	onSaveFailure:function (){
		com.zz91.utils.Msg("","保存失败！");
	}
});

/**
 *添加窗口 
 */
com.zz91.sms.template.addFormWin = function(){
	var grid = new com.zz91.sms.template.editForm({
		id:TEMPLATE.ADD_FORM,
		saveUrl:Context.ROOT + "/template/add.htm?st="+timestamp(),
		region:"center"
	});
	
	var win = new Ext.Window({
		id:TEMPLATE.EDIT_WIN,
		title:"添加短信模板",
		width:"60%",
		modal:true,
		items:[grid]
	});
	win.show();
};

/**
 * 编辑窗口
 */
com.zz91.sms.template.editFormWin = function(id){
	var form = new com.zz91.sms.template.editForm({
		id:TEMPLATE.EDIT_FORM,
		saveUrl:Context.ROOT + "/template/update.htm?st="+timestamp(),
		region:"center"
	});
	
	var win = new Ext.Window({
		id:TEMPLATE.EDIT_WIN,
		title:"编辑短信模板",
		width:"60%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id);
	win.show();
};

