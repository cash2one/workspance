Ext.namespace("ast.ast1949");

ast.ast1949.FromEmailCombo = Ext.extend(Ext.form.ComboBox,{
	constructor:function(_cfg){
		if(_cfg == null)
			_cfg = {};
		Ext.apply(this, _cfg);

		var _fieldLabel = this["fieldLabel"] ||'';
		var _name = this["name"] ||'';
		var _isAutoLoad = this["isAutoLoad"] || true;
		var _anchor = this["anchor"] || "100%"
		var _store = new Ext.data.JsonStore( {
			root : "records",
			fields : [ "fromEmail", "fromEmail" ],
			autoLoad:_isAutoLoad,
			url : Context.ROOT + Context.PATH
					+ "/admin/mail/queryFromEmail.htm",
			listeners :{
			  load:function(){
			//	  Ext.getCmp(_name+"_combo").setValue(Ext.getDom(_name).value);
			  }
			}
		});

		ast.ast1949.FromEmailCombo.superclass.constructor.call(this,{
			id:_name+"_combo",
			displayField : "fromEmail",
			valueField : "fromEmail",
			hiddenName:_name,
			hiddenId:_name,
			fieldLabel:_fieldLabel,
			triggerAction : "all",
			forceSelection : true,
			allowBlank:false,
			blankText : "请选择",
			store:_store,
			anchor:_anchor
		});
	}
});