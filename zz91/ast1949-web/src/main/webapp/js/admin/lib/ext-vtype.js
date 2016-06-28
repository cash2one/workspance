Ext.apply(Ext.form.VTypes, {
     'account': function(){
         var re = /^[0-9a-zA-Z_]{4,20}$/;
         return function(v){
             return re.test(v);
         }
     }(),
     'accountText' : '账户必需由4到20个数字,字母或_组成'
});

Ext.apply(Ext.form.VTypes, {
     'password': function(){
         var re = /^[0-9a-zA-Z_]{4,20}$/;
         return function(v){
             return re.test(v);
         }
     }(),
     'passwordText' : '密码必需由4到20个数字,字母或_组成'
});

Ext.apply(Ext.form.VTypes, {
     'email': function(){
         var re = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
         return function(v){
             return re.test(v);
         }
     }(),
     'emailText' : 'Email格式错了, 例: zz91@zz91.com'
});