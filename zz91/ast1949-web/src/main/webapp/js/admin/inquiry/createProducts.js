Ext.namespace("ast.ast1949.admin.inquiry");

ast.ast1949.admin.inquiry.createProducts=function (_cfg){
	if(_cfg==null){
		_cfg={};
	}
	Ext.apply(this,_cfg);
	
	var _title=this["title"]||"";
	var _details=this["details"]||"";
	var _account=this["account"]||"";
	
	var productsForm=ast.ast1949.admin.products.productsForm();
	
	var title = Ext.getCmp("title");
	var details=Ext.getCmp("details");
	var account=Ext.getCmp("account");
	
	title.value=_title;
	details.value=_details;
	account.value=_account;

	var win=new Ext.Window({
		id:"createProductsWin",
//		title : "修改供求信息",
		closeable : true,
		height : 500,
		width:"90%",
		maximizable :true,
		modal : true,
		border : false,
		plain : true,
		layout : "border",
//		items:[{region:"center",title:"here is a test",html:"0.23"}]
		items : [productsForm] //,form
	});
	win.show();
	win.maximize();

	Ext.get("save").on("click",function(){
		if(productsForm.getForm().isValid()){
			var _url=Context.ROOT + Context.PATH + "/admin/inquiry/createProductsFromInquiry.htm";
			
			productsForm.getForm().submit({
				url:_url,
				method:"post",
				success:doSuccess,
				failure:doFailure
			});
		} else {
			Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "验证未通过！",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
		}
	});
	
	function doSuccess(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "发布成功",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
	}
	
	function doFailure(_form,_action){
		Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "发布失败",
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
	}

	Ext.get("close").on("click",function(){
		win.close();
	});
}



