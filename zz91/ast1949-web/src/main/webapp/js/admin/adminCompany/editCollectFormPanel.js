Ext.namespace("ast.ast1949.admin.adminCompany");
//定义一个添加,编辑的父窗体类,继承自Window

ast.ast1949.admin.adminCompany.editCollectFormPanel = function(_cfg){
	if(_cfg == null){
		_cfg = {};
	}
	
	var main_url = Context.ROOT+Context.PATH + "/admin/admincompany/collectList.htm";
	var isView = true;
	var notView = false;
	var panel = new ast.ast1949.admin.adminCompany.InfoCollectFormPanel({
		view:isView,
		nView:notView,
		listeners:{
			"saveSuccess" : onSaveSuccess,
			"saveFailure" : onSaveFailure,
			"submitFailure" : onSubmitFailure
		}
	});
	var _store = new Ext.data.JsonStore({
		root:"records",
		fields:[{name:"id",mapping:"company.id"},
		        {name:"name",mapping:"company.name"},
		        {name:"business",mapping:"company.business"},
		        {name:"levelCode",mapping:"company.levelCode"},
		        {name:"serviceCode",mapping:"company.serviceCode"},
		        {name:"membershipCode",mapping:"company.membershipCode"},
		        {name:"industryCode",mapping:"company.industryCode"},
		        {name:"introduction",mapping:"company.introduction"},
		        {name:"account",mapping:"companyContacts.account"},
		        {name:"companyId",mapping:"companyContacts.companyId"},
		        {name:"defaultContact",mapping:"companyContacts.defaultContact"},
		        {name:"telCountryCode",mapping:"companyContacts.telCountryCode"},
		        {name:"telAreaCode",mapping:"companyContacts.telAreaCode"},
		        {name:"tel",mapping:"companyContacts.tel"},
		        {name:"mobile",mapping:"companyContacts.mobile"},
		        {name:"faxCountryCode",mapping:"companyContacts.faxCountryCode"},
		        {name:"faxAreaCode",mapping:"companyContacts.faxAreaCode"},
		        {name:"fax",mapping:"companyContacts.fax"},
		        {name:"email",mapping:"companyContacts.email"},
		        {name:"website",mapping:"companyContacts.website"},
		        {name:"contact",mapping:"companyContacts.contact"},
		        {name:"sex",mapping:"companyContacts.sex"},
		        {name:"position",mapping:"companyContacts.position"},
		        {name:"address",mapping:"companyContacts.address"},
		        {name:"zip",mapping:"companyContacts.zip"},
		        {name:"qq",mapping:"companyContacts.qq"},
		        {name:"msn",mapping:"companyContacts.msn"},
		        {name:"hiddenContacts",mapping:"companyContacts.hiddenContacts"},
		        
		        {name:"regfromCode",mapping:"company.regfromCode"},
		        {name:"regfromCodeName",mapping:"regfromCodeName"},
		        {name:"areaCode",mapping:"areaCodeName"},
		        {name:"areaCode1",mapping:"company.areaCode"},
		        {name:"categoryGardenId",mapping:"categoryGardenName"},
		        {name:"categoryGardenId1",mapping:"company.categoryGardenId"},
		        {name:"gardenTypeCode",mapping:"gardenTypeCode"},
		        {name:"gardenTypeCode1",mapping:"categoryLabel"}],
		url : Context.ROOT+Context.PATH + "/admin/admincompany/queryById.htm",
		autoLoad:false,
		listeners:{
			"datachanged": function(){
				var record = _store.getAt(0);
				if(record == null){
					Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载错误,请联系管理员!");
				}else{
					panel.loadRecord(record);
				}
			}
		}
	});

	function onSaveSuccess(_form,_action){
		var res= Ext.decode(_action.response.responseText);
//		alert(res.data)
		if(parseInt(res.data)>0){
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "修改成功，影响行数："+res.data,
				fn:hrefMe,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
		}else{
			Ext.MessageBox.show({
				title:Context.MSG_TITLE,
				msg : "添加成功",
				fn:hrefMe,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.INFO
			});
		}
	}
	function onSaveFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "添加失败(手机号,Email或账号已存在)!",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
	function onSubmitFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "验证失败",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
	function hrefMe(){
		panel._form.getForm().reset();
		Ext.getCmp("companyCollectGrid").store.baseParams = {"email":"","mobile":""};
		Ext.getCmp("companyCollectGrid").getStore().load({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	}
	return {panel:panel,store:_store};
}