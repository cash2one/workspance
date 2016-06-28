Ext.namespace("ast.ast1949.admin.inquirySensitive")

ast.ast1949.admin.inquirySensitive.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config) {
		config = config||{};
		Ext.apply(this,config);
		
		var c = {
			layout : "fit",
			region : "center",
			frame : true,
			autoScroll : true,
			height : 150,
			items:[
				{
					xtype : "textarea",
					name : "keywords",
					allowBlank:false,
					anchor : "99%",
					blankText : "请您输入至少一个敏感字!"
				}
			],
			buttons : [
				{
					id:"save",
					text:"更新",
					handler:this.save,
					scope:this
				}
			]
		};
		
		ast.ast1949.admin.inquirySensitive.editForm.superclass.constructor.call(this,c);		
	},
	loadRecord:function(){
		var _fields=[
			{name:"keywords",mapping:"keywords"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + Context.PATH + "/admin/inquirysensitive/getSingleRecord.htm",
//			baseParams:{"id":id},
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
	saveUrl:Context.ROOT + Context.PATH + "/admin/inquirysensitive/update.htm",
	save:function(){
//		ast.ast1949.utils.Msg("","OK");
		var _url = this.saveUrl;
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				waitMsg:Context.SAVEMASK.msg,
				waitTitle:Context.WAITTITLE.title,
				success:this.onSuccess,
				failure:this.onFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg(Context.MSG_TITLE,"验证未通过！");
		}
	},
	onSuccess:function(form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存成功！");
	},
	onFailure:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存失败！");
	}
	
})
