Ext.namespace("com.zz91.ads.board.login");

var LOGIN = new function() {
	this.LOGINWINDOW = "loginwindow";
}

Ext.define("com.zz91.ads.board.login.LoginForm", {
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
		    border: 0,
		    bodyPadding: 5,
			fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 60,
		        msgTarget:"under"
		    },
			layout:{
				type:"vbox",
				align:"stretch"
			},
			defaults:{
				anchor:'99%',
				xtype : 'textfield'
			},
			items : [ {
				fieldLabel : '用户名',
				name : 'username',
				id : 'username',
				allowBlank : false
			}, {
				inputType : 'password',
				fieldLabel : '密码',
				name : 'password',
				id : 'password',
				allowBlank : false
			} ]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	initFocus:function(){
		this.child("#username").focus(true,100);
	}
});


/**
 * 用户登录动作 
 * form:登录的表单对象 
 * onSuccess:登录成功后要做的事
 */
com.zz91.ads.board.login.UserLogin = function(form, onSuccess) {
	if(form.getForm().isValid()){
		form.getForm().submit( {
			url : Context.ROOT + "/authorize.htm",
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
				form.child("#password").setValue("");
			}
		});
	}
}

com.zz91.ads.board.login.UserLoginWin = function(doSuccess) {
	var form = Ext.create("com.zz91.ads.board.login.LoginForm",{
		region:"center"
	});

	form.child("#username").on("specialkey",function(field,e){
		if (e.getKey() == e.ENTER) {
        	com.zz91.ads.board.login.UserLogin(form, doSuccess);
        }
	});
	form.child("#password").on("specialkey",function(field,e){
		if (e.getKey() == e.ENTER) {
        	com.zz91.ads.board.login.UserLogin(form, doSuccess);
        }
	});
	
	var win= Ext.create("Ext.Window",{
		id : LOGIN.LOGINWINDOW,
		layout : 'border',
		iconCls : "lock16",
		width : 300,
		height : 140,
		closable : false,
		title : "zz91.com - 后台管理登录",
		modal : true,
		items : [ form ],
		buttons : [ {
			text : '登录',
			handler : function(btn) {
				com.zz91.ads.board.login.UserLogin(form, doSuccess);
			}
		} ]
	});
	
	win.show();

	form.initFocus();
}
