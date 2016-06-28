Ext.namespace("ast.ast1949.admin.inquiry")

ast.ast1949.admin.inquiry.SimpleProductFrom=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var c = {
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[
				{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
//						{
//							xtype:"hidden",
//							id:"id",
//							name:"id"
//						},
						{
							id:"protitle",
							name:"title",
							fieldLabel:"供求标题",
							readOnly:true
						},{
							xtype:"htmleditor",
							name:"details",
							fieldLabel:"供求描述",
							readOnly:true
						}
					]
				}
			]
		}
		
		ast.ast1949.admin.inquiry.SimpleProductFrom.superclass.constructor.call(this,c);
	},
	loadRecord:function(id){
		var _fields=[
//			{name:"id",mapping:"id"},
			{name:"title",mapping:"title"},
			{name:"details",mapping:"details"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + Context.PATH + "/admin/inquiry/getSimpleProductRecord.htm",
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
	}
})