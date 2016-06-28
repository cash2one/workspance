Ext.namespace("ast.ast1949.admin.score.summary")

ast.ast1949.admin.score.summary.TabContent = function(url){
	return '<iframe src="' + Context.ROOT + Context.PATH + url + '"' + '" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe> ';
}

ast.ast1949.admin.score.summary.EditFormTab = Ext.extend(Ext.TabPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var c={
//			activeTab: 1,
			frame:false,
			bodyBorder:false,
			border:false,
			enableTabScroll:true,
			defaults:{
				closable: true
			},	
			items:[
				{
					title:'变更操作',
					layout:"border",
					items:[
						new ast.ast1949.admin.score.summary.editForm({
							id:PAGE_CONST.CHANGE_FROM,
							region:"center"
						})
					]
				},{
					title:'客户信息',
					html: ast.ast1949.admin.score.summary.TabContent("/admin/admincompany/edit.htm?companyId="+PAGE_CONST.COMPANY_ID+"&st="+Math.random())
					
				}
			]
		};
		ast.ast1949.admin.score.summary.EditFormTab.superclass.constructor.call(this,c);
	}
});

ast.ast1949.admin.score.summary.editForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[
				{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
					{
						xtype:"hidden",
						id:"relatedId",
						name:"relatedId",
						value:PAGE_CONST.RELATED_ID
					},
					{
						xtype:"hidden",
						id:"companyId",
						name:"companyId",
						value:PAGE_CONST.COMPANY_ID
					},
					{
						xtype:"hidden",
						id:"rulesCode",
						name:"rulesCode"
					},
					{
						
						xtype:          "combo",
                        mode:           "local",
                        triggerAction:  "all",
                        forceSelection: true,
                        editable:       true,
                        fieldLabel:     "变更项目",
                        hiddenName:"name",
                        displayField:   "name",
                        valueField:     "rulesCode",
                        store:new Ext.data.JsonStore({
							autoLoad	: true,
							root		: "records",
							url			: Context.ROOT+Context.PATH+"/admin/score/rules/queryRulesByPreCode.htm?preCode="+PAGE_CONST.PRE_CODE,
							fields		: ["name","rulesCode"]
						}),
						itemCls :"required",
						blankText : "变更项目不能为空",
						allowBlank:false,
					    listeners:{
					    	"focus":function(e){
					    		this.setValue("");
					    	},
							"blur":function(c){
								Ext.get("rulesCode").dom.value=c.value;
								this.setValue(this.el.dom.value)
							}
						}
					}]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"90%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
					{
						xtype:"numberfield",
						name:"score",
						fieldLabel:"积分",
						itemCls :"required",
						blankText : "请输入积分",
						allowBlank:false
					}]
				},{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
						{
							xtype:"textarea",
							name:"remark",
							fieldLabel:"备注信息",
							allowBlank:true
						}
					]
				}
			],
			buttons:[
			{
				text:"保存",
				handler:this.save,
				scope:this
			}]
		};
		
		ast.ast1949.admin.score.summary.editForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/score/summary/getSingleById.htm",
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
	
	saveUrl:Context.ROOT+Context.PATH + "/admin/score/change/save.htm",
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
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","保存成功！");
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","保存失败！");
	}
});
