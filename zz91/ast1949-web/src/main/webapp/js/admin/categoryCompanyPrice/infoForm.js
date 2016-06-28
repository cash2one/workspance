Ext.namespace("ast.ast1949.admin.categoryCompanyPrice");
//定义一个添加,编辑的父窗体类,继承自Window

ast.ast1949.admin.categoryCompanyPrice.InfoFormPanel = Ext.extend(Ext.Panel,{
	_form:null,
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}

		Ext.apply(this,_cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";

		var parentCombo = new Ext.form.ComboBox({
			fieldLabel	: "选择父类别",
			id			: "combo-preCode",
			name		: "parentLabel",
			hiddenName	: "preCode",
			hiddenId	: "preCode",
			mode		: "local",
			xtype		: "combo",
			readOnly	: true,
			selectOnFocus:true,
			triggerAction: "all",
			emptyText	: "选择父类别",
			anchor		: "55%",
			tabIndex	: 1,
			allowBlank	: true,
			store		: new Ext.data.SimpleStore({fields:[],data:[[]]}),
			tpl			: "<tpl for='.'><div style='height:280px' id='category-combo'></div></tpl>",
			onSelect	: Ext.emptyFn
		});

		var tree;
      	parentCombo.on("expand",function(){
      		
      		if(tree==null){
      		
	      		tree =  ast.ast1949.admin.categoryCompanyPrice.treePanel({el:"category-combo",rootData:""});
	      		tree.getRootNode().disable();
				tree.on('click',function(node){
		          	parentCombo.setValue(node.text);
		          	parentCombo.collapse();
		          	Ext.get("preCode").dom.value= node.attributes["data"];
	//	          	tree.destroy();
		      	});
      		}
      	});

		this._form = new Ext.form.FormPanel({
			region:"center",
			id:"category-form",
			frame:true,
			bodyStyle:"padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
			width:"100%",
			items:[{
				xtype:"fieldset",
				layout:"column",
				autoHeight:true,
				title:"类别基本信息",
				items:[{
					columnWidth: 1,
					layout: "form",
					defaults:{
						disabled:_notView
					},
					items:[{
						xtype:"hidden",
						name:"id",
						id:"categoryId",
						dataIndex:"id"
					},parentCombo,{
						xtype:"textfield",
						fieldLabel:"类别名称",
						allowBlank:false,
						name:"label",
						id:"label",
						tabIndex:1,
						anchor:"55%",
						blankText : "类别名称不能为空"
					}]
				}]
			}],
			buttons:[{
				id:"save",
				text:"保存",
				hidden:_notView
			}
//			,{
//				id:"cancel",
//				text:"取消",
//				hidden:_notView
//			}
			,{
				id:"close",
				text:"关闭",
				hidden:_isView
			}]
		});

		ast.ast1949.admin.categoryCompanyPrice.InfoFormPanel.superclass.constructor.call(this,{
			title:_title,
			closeable:true,
			width:700,
			autoHeight:true,
			modal:true,
			border:false,
			plain:true,
			layout:"form",
			items:[this._form]
		});
		this.addEvents("saveSuccess","saveFailure","submitFailure");
	},
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
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
//		alert(_form.getValues());
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	},
	initFocus:function(){
		var f = this._form.getForm().findField("label");
		f.focus(true,true);
	}
});