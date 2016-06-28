Ext.namespace("ast.ast1949.admin.companyPrice");
// 定义一个添加,继承自父窗体类

ast.ast1949.admin.companyPrice.EditFormWin = function(_cfg) {
	if (_cfg == null) {
		_cfg = {};
	}

	Ext.apply(this, _cfg);

	var _id = this["id"] || "";
	var _grid = this["grid"] || "";

	var title = "修改企业报价";
	var url = Context.ROOT + Context.PATH + "/admin/companyprice/update.htm";
	var isView = true;
	var notView = false;
	var win = new ast.ast1949.admin.companyPrice.InfoFormWin({
				title : title,
				view : isView,
				nView : notView,
				listeners : {
					"saveSuccess" : onSaveSuccess,
					"saveFailure" : onSaveFailure,
					"submitFailure" : onSubmitFailure
				}
			});

	win.show();
	win.initFocus();

	var _store = new Ext.data.JsonStore({
				root : "records",
				fields : [{
							name : "id",
							mapping : "companyPriceDO.id"
						}, {
							name : "title",
							mapping : "companyPriceDO.title"
						}, {
							name : "price",
							mapping : "companyPriceDO.price"
						}, {
							name : "priceUnit",
							mapping : "companyPriceDO.priceUnit"
						}, {
							name : "minPrice",
							mapping : "companyPriceDO.minPrice"
						}, {
							name : "maxPrice",
							mapping : "companyPriceDO.maxPrice"
						}, {
							name : "details",
							mapping : "companyPriceDO.details"
						}, {
							name : "categoryCompanyPriceCode",
							mapping : "companyPriceDO.categoryCompanyPriceCode"
						}, {
							name : "areaCode",
							mapping : "companyPriceDO.areaCode"
						}, {
							name : "companyName",
							mapping : "companyName"
						}, {
							name : "categoryName",
							mapping : "categoryName"
						}, {
							name : "areaName",
							mapping : "areaName"
						}, {
							name : "validTime",
							mapping : "validTime"
						}, {
							name : "refreshTime",
							mapping : "companyPriceDO.refreshTime",
							convert : function(value) {
								return Ext.util.Format.date(
										new Date(value.time), 'Y-m-d');
							}
						}],
				url : Context.ROOT + Context.PATH
						+ "/admin/companyprice/init.htm?id=" + _id,
				autoLoad : true,
				listeners : {
					"datachanged" : function() {
						var record = _store.getAt(0);
						if (record == null) {
							Ext.MessageBox.alert(Context.MSG_TITLE,
									"数据加载错误,请联系管理员!");
							win.close();
						} else {
							win.loadRecord(record);
							// Ext.get("categoryCompanyPriceCode").dom.value=record.get("categoryCompanyPriceCode1");
							// Ext.get("areaCode").dom.value=record.get("areaCode1");

						}
					}
				}
			});

	Ext.get("save").on("click", function() {
				win.submit(url);
			});

	Ext.get("cancel").on("click", function() {
				win.close();
			});

	function onSaveSuccess() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "修改成功",
					buttons : Ext.MessageBox.OK,
					fn : closeMe,
					icon : Ext.MessageBox.INFO
				});
		closeMe();
	}

	function onSaveFailure() {
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "修改失败",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}

	function onSubmitFailure() {
		// alert("system");
		Ext.MessageBox.show({
					title : Context.MSG_TITLE,
					msg : "验证失败",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}

	function closeMe() {
		_grid.getStore().reload();
		win.close();
	}

}