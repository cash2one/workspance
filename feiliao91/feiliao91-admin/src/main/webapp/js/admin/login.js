Ext.namespace("ast.ast1949.admin");

var LOGIN = new function() {
	this.LOGINWINDOW = "loginwindow";
}

ast.ast1949.admin.LoginForm = Ext.extend(Ext.form.FormPanel, {
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var c = {
			layout : 'form',
			frame : true,
			labelAlign : 'right',
			labelWidth : 60,
			items : [ {
				xtype : 'textfield',
				fieldLabel : '用户名',
				name : 'account',
				id : 'account',
				allowBlank : false,
				anchor : '95%'
			}, {
				xtype : 'textfield',
				inputType : 'password',
				fieldLabel : '密码',
				name : 'password',
				id : 'password',
				allowBlank : false,
				anchor : '95%'
			} ]
		};

		ast.ast1949.admin.LoginForm.superclass.constructor.call(this, c);

	},
	initFocus : function() {
		this.findById("account").focus(true, 100);
	}
});

/**
 * 用户登录动作 
 * form:登录的表单对象 
 * onSuccess:登录成功后要做的事
 */
ast.ast1949.admin.UserLogin = function(form, onSuccess) {
	form.getForm().submit( {
		url : Context.ROOT + Context.PATH + "/admin/login.htm",
		method : "post",
		type : "json",
		waitMsg : "正在验证用户名和密码,请耐心等待!",
		success : onSuccess,
		failure : function(_form, _action) {
			Ext.MessageBox.show( {
				title : Context.MSG_TITLE,
				msg : _action.result.data,
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
		}
	});
}

ast.ast1949.admin.UserLoginWin = function(doSuccess) {
	var form = new ast.ast1949.admin.LoginForm( {
		region : "center"
	});

	var win = new Ext.Window( {
		id : LOGIN.LOGINWINDOW,
		layout : 'border',
		iconCls : "item-key",
		width : 300,
		height : 140,
		closable : false,
		title : "zz91.com - 后台管理登录",
		modal : true,
		items : [ form ],
		keys : [ {
			key : [ 10, 13 ],
			fn : function() {
				ast.ast1949.admin.UserLogin(form, doSuccess);
			}
		} ],
		buttons : [ {
			text : '登录',
			handler : function(btn) {
				ast.ast1949.admin.UserLogin(form, doSuccess);
			}
		} ]
	});

	win.show();

	form.initFocus();
}
