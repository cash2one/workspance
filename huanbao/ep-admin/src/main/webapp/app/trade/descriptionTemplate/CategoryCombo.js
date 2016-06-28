Ext.namespace("com.zz91");

com.zz91.CategoryCombo = Ext.extend(Ext.form.ComboBox,{
	constructor:function(_cfg){
		if(_cfg == null)
			_cfg = {};
		Ext.apply(this, _cfg);

		var _anchor=this["anchor"]|| "95%";
		var _fieldLabel = this["fieldLabel"] ||'';
		var _name = this["name"] ||'';
		var _isAutoLoad = this["isAutoLoad"] || true;
		var _allowBlank = this["allowBlank"] || false;
		
		var _store = new Ext.data.JsonStore( {
			root : "records",
			fields : [ "name", "value" ],
			autoLoad:_isAutoLoad,
			url : Context.ROOT + "/param/param/listParamByTypes.htm?types=description_template",
			listeners :{
			  load:function(){
				  //Ext.getCmp(_name+"_combo").setValue(Ext.getDom(_name).value);
			  }
			}
		});

		com.zz91.CategoryCombo.superclass.constructor.call(this,{
			id:_name+"_combo",
			displayField : "name",
			valueField : "value",
			hiddenName:_name,
			hiddenId:_name,
			fieldLabel:_fieldLabel,
			triggerAction : "all",
			forceSelection : true,
			allowBlank:_allowBlank,
			blankText : "请选择",
			store:_store,
			anchor: _anchor
		});
	}
});