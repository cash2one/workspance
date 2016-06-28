Ext.namespace("ast.ast1949.admin");

var LOGIN = new function() {
	this.LOGINWINDOW = "loginwindow";
}

Ext.define("ast.ast1949.admin.LoginForm", {
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
				name : 'account',
				id : 'account',
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
		this.child("#account").focus(true,100);
	}
});


/**
 * 用户登录动作 
 * form:登录的表单对象 
 * onSuccess:登录成功后要做的事
 */
ast.ast1949.admin.UserLogin = function(form, onSuccess) {
	if(form.getForm().isValid()){
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
				form.child("#password").setValue("");
			}
		});
	}
}

ast.ast1949.admin.UserLoginWin = function(doSuccess) {
	var form = Ext.create("ast.ast1949.admin.LoginForm",{
		region:"center"
	});

	form.child("#account").on("specialkey",function(field,e){
		if (e.getKey() == e.ENTER) {
        	ast.ast1949.admin.UserLogin(form, doSuccess);
        }
	});
	form.child("#password").on("specialkey",function(field,e){
		if (e.getKey() == e.ENTER) {
        	ast.ast1949.admin.UserLogin(form, doSuccess);
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
//		keys : [ {
//			key : [ 10, 13 ],
//			fn : function() {
//				ast.ast1949.admin.UserLogin(form, doSuccess);
//			}
//		} ],
//		listeners:{
//			afterrender:function(field,e){
//				alert(e.getKey())
//				if(e.getKey()==e.ENTER){
//					ast.ast1949.admin.UserLogin(form, doSuccess);
//				}
//			}
//		},
		buttons : [ {
			text : '登录',
			handler : function(btn) {
				ast.ast1949.admin.UserLogin(form, doSuccess);
			}
		} ]
	});
	
//	var win = new Ext.Window( {
//		id : LOGIN.LOGINWINDOW,
//		layout : 'border',
//		iconCls : "item-key",
//		width : 300,
//		height : 140,
//		closable : false,
//		title : "zz91.com - 后台管理登录",
//		modal : true,
//		items : [ form ],
//		keys : [ {
//			key : [ 10, 13 ],
//			fn : function() {
//				ast.ast1949.admin.UserLogin(form, doSuccess);
//			}
//		} ],
//		buttons : [ {
//			text : '登录',
//			handler : function(btn) {
//				ast.ast1949.admin.UserLogin(form, doSuccess);
//			}
//		} ]
//	});

	win.show();

	form.initFocus();
}
