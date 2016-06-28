Ext.namespace("ast.ast1949.admin.crm.activity");

ast.ast1949.admin.crm.activity.InfoFormWin = Ext.extend(Ext.Window,{
	_form:null,
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}

		Ext.apply(this,_cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";
		
		this._form = new Ext.form.FormPanel({
			region:"center",
			frame:true,
			bodyStyle:"padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
			width:"100%",
			items:[{
				xtype:"fieldset",
				layout:"column",
				autoHeight:true,
				title:"信息详细内容",
				items:[{
					columnWidth: 1,
					layout: "form",
					defaults:{
						disabled:_notView
					},
					items:[{
						xtype:"hidden",
						name:"id",
						dataIndex:"id"
					},new ast.ast1949.CategoryCombo({
						categoryCode : Context.CATEGORY["activityCategoryCode"],
						fieldLabel:"<span style='color:red'>名类型</span>",
						allowBlank:false,
						name:"categoryCode",
						tabIndex:1,
						anchor:"45%",
						blankText : "名类型不能为空"
					}),
//						{
//						xtype:"textfield",
//						fieldLabel:"<span style='color:red'>名类型</span>",
//						allowBlank:false,
//						name:"categoryCode",
//						tabIndex:1,
//						anchor:"95%",
//						blankText : "名类型不能为空"
//					},
						{
						xtype:"textfield",
						fieldLabel:"<span style='color:red'>公司名</span>",
						allowBlank:false,
						name:"companyName",
						tabIndex:1,
						anchor:"95%",
						blankText : "公司名不能为空"
					},{
						xtype:"textfield",
						fieldLabel:"<span style='color:red'>邮箱</span>",
						allowBlank:false,
						name:"email",
						tabIndex:1,
						anchor:"95%",
						blankText : "邮箱不能为空"
					},{
						xtype:"textfield",
						fieldLabel:"<span style='color:red'>联系人</span>",
						allowBlank:false,
						name:"contact",
						tabIndex:1,
						anchor:"95%",
						blankText : "联系人不能为空"
					},{
						xtype:"textfield",
						fieldLabel:"<span style='color:red'>电话</span>",
						allowBlank:false,
						name:"tel",
						tabIndex:1,
						anchor:"95%",
						blankText : "电话不能为空"
					},{
						xtype:"textfield",
						fieldLabel:"<span style='color:red'>手机</span>",
						allowBlank:false,
						name:"mobile",
						tabIndex:1,
						anchor:"95%",
						blankText : "手机不能为空"
					},{
						xtype:"textfield",
						fieldLabel:"地址",
						name:"address",
						tabIndex:1,
						anchor:"95%",
						blankText : "地址不能为空"
					}]
				}]
			}],
			buttons:[{
				id:"save",
				text:"保存",
				hidden:_notView
			},{
				id:"cancel",
				text:"取消",
				hidden:_notView
			},{
				id:"close",
				text:"关闭",
				hidden:_isView
			}]
		});
		
		ast.ast1949.admin.crm.activity.InfoFormWin.superclass.constructor.call(this,{
			title:_title,
			closeable:true,
			width:700,
			autoHeight:false,
			height:400,
			autoScroll : true,//自动显示滚动条
			maximizable:true,//窗口最大化
			modal:true,
			border:false,
			plain:true,
			layout:"form",
			items:[this._form]
		});
		this.addEvents("saveSuccess","saveFailure","submitFailure");
	},
	//提交表单
	submit:function(_url){
		if(this._form.getForm().isValid()){
			this._form.getForm().submit({
				url:_url,
				method:"post",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "验证未通过",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},

	loadRecord:function(_record){
		this._form.getForm().loadRecord(_record);
		//下拉树初始化


//		Ext.get("combo-basetype").dom.value=_record.get("baseTypeName");
//		Ext.get("combo-typeId").dom.value=_record.get("typeName");
//		Ext.get("combo-assistTypeId").dom.value=_record.get("assistTypeName");
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	},
	initFocus:function(){
//		var f = this._form.getForm().findField("email");
//		f.focus("",true);
	}


});
//The end of infoFormWin