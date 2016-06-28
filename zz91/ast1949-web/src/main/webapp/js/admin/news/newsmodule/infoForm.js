Ext.namespace("ast.ast1949.admin.news.newsmodule");

ast.ast1949.admin.news.newsmodule.InfoFormPanel = Ext.extend(Ext.Panel,{
	_form:null,
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}

		Ext.apply(this,_cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";
		var _url=this["url"] ||"";

		var parentCombo = new Ext.form.ComboBox({
			fieldLabel	: "选择父节点",
			id			: "combo-parentId",
			name		: "parentLabel",
			hiddenName	: "parentId",
			hiddenId	: "parentId",
			mode		: "local",
			xtype		: "combo",
			readOnly	: true,
			selectOnFocus:true,
			triggerAction: "all",
			emptyText	: "父节点,不选表示根节点",
			anchor		: "95%",
			tabIndex	: 1,
			allowBlank	: true,
			store		: new Ext.data.SimpleStore({fields:[],data:[[]]}),
			tpl			: "<tpl for='.'><div style='height:280px' id='category-combo'></div></tpl>",
			onSelect	: Ext.emptyFn
		});

		var tree;
      	parentCombo.on("expand",function(){
      		if(tree==null){
	      		tree =  ast.ast1949.admin.news.newsmodule.treePanel({el:"category-combo",rootData:""});
	      		tree.getRootNode().disable();
				tree.on('click',function(node){
		          	parentCombo.setValue(node.text);
		          	parentCombo.collapse();
		          	Ext.get("parentId").dom.value= node.attributes["data"];
		      	});
      		}
      	});

		this._form = new Ext.form.FormPanel({
			region:"center",
			id:"news-module-form",
			frame:true,
			bodyStyle:"padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
			width:"100%",
			items:[
				{
				xtype:"fieldset",
				layout:"column",
				autoHeight:true,
				title:"模块信息",
				items:[
					{
					columnWidth: 1,
					layout: "form",
					defaults:{
						disabled:_notView
					},
					items:[{
						xtype:"hidden",
						name:"id",
						id:"id",
						dataIndex:"id"
					},parentCombo,{
						xtype:"textfield",
						fieldLabel:"<span style='color:red'>名称</sapn>",
						allowBlank:false,
						name:"name",
						id:"name",
						tabIndex:2,
						anchor:"95%",
						blankText : "名称不能为空"
					},{
						xtype:"textfield",
						fieldLabel:"跳转地址",
						allowBlank:true,
						name:"url",
						id:"url",
						tabIndex:3,
						anchor:"95%"
					},{
						xtype:"checkbox",
						fieldLabel:"审核",
						boxLabel:"是",
						id:"checked",
						name:"checked",
						inputValue:"1"
					}]
				}]
			}],
			buttons:[{
				id:"save",
				text:"确定",
				hidden:_notView
			},{
				id:"close",
				text:"关闭",
				hidden:_isView
			}]
		});

		ast.ast1949.admin.news.newsmodule.InfoFormPanel.superclass.constructor.call(this,{
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
				msg : "验证未通过,红色项为必填项！",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	loadRecord:function(_record){
		this._form.getForm().loadRecord(_record);
//		if(Ext.get("parentId").dom.value.length>0&&Ext.get("parentId").dom.value!="0"){
//			alert(Ext.get("parentId").dom.value.length);
//			Ext.getCmp("setting").disabled=true;
//		}
		
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	},
	initFocus:function(){
		var f = this._form.getForm().findField("name");
		f.focus(true,true);
	}
});