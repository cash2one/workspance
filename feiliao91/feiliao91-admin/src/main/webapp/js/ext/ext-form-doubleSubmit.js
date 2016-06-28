Ext.apply(Ext.form.BasicForm.prototype, {
    	issubmit: false
    }); //给表单对象增加一属性
    Ext.override(Ext.form.BasicForm, {
    	doAction: function(action, options) {
    		if (typeof action == 'string') {
    			action = new Ext.form.Action.ACTION_TYPES[action](this, options);
    		}
    		if (this.fireEvent('beforeaction', this, action) !== false) {
    			this.beforeAction(action);
    			if (this.issubmit == false) { //判断是否是第一次提交
    				action.run.defer(100, action);
    				this.issubmit = true; //将值设置为true
    			}
    		}
    		return this;
    	},
    	afterAction: function(action, success) {
    		this.activeAction = null;
    		var o = action.options;
    		if (o.waitMsg) {
    			if (this.waitMsgTarget === true) {
    				this.el.unmask();
    			} else if (this.waitMsgTarget) {
    				this.waitMsgTarget.unmask();
    			} else {
    				Ext.MessageBox.updateProgress(1);
    				Ext.MessageBox.hide();
    			}
    		}
    		this.issubmit = false; //恢复初值
    		if (success) {
    			if (o.reset) {
    				this.reset();
    			}
    			Ext.callback(o.success, o.scope, [this, action]);
    			this.fireEvent('actioncomplete', this, action);
    		} else {
    			Ext.callback(o.failure, o.scope, [this, action]);
    			this.fireEvent('actionfailed', this, action);
    		}
    	}
    });