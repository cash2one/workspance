Ext.namespace("ast.ast1949.spot");

ast.ast1949.spot.SendPromotion=function(cfg){
	var form = new ast.ast1949.spot.PromotionFormForAdd({
		region : "center",
		saveUrl : Context.ROOT + Context.PATH + "/spot/addToPromotion.htm"
	});
	//初始化
	form.findById("spotId").setValue(cfg.spotId||"");
	form.findById("productId").setValue(cfg.productId||"");
//	form.findById("promotionsPrice").setValue(cfg.promotionsPrice||"");
//	form.findById("expiredTime").setValue(cfg.expiredTime||"");
	var win = new Ext.Window({
		id : "spotpromotionaddformwin",
		title : "推荐到",
		width : 500,
		modal : true,
		items : [form]
	});
	win.show();
}

ast.ast1949.spot.PromotionFormForAdd=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				id:"spotId",
				name:"spotId"
			},{
				xtype:"hidden",
				id:"productId",
				name:"productId"
			},{
				name:"promotionsPrice",
				id:"promotionsPrice",
				fieldLabel:"促销价格(单位：元)",
				itemCls:"required",
				allowBlank : false
			},{
				xtype : "datefield",
				fieldLabel : "过期时间",
				name : "expiredTime",
				allowBlank:false,
				format : 'Y-m-d H:i:s',
				value:new Date()
			}],
			
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp("spotpromotionaddformwin").close();
				}
			}]
		};
		
		ast.ast1949.spot.PromotionFormForAdd.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/spot/addToPromotion.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过！");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","操作成功！");
		Ext.getCmp("spotpromotionaddformwin").close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","添加失败！");
		Ext.getCmp("spotpromotionaddformwin").close();
	}
});

// 竞拍
ast.ast1949.spot.SendAuction=function(cfg){
	var form = new ast.ast1949.spot.AuctionFormForAdd({
		region : "center",
		saveUrl : Context.ROOT + Context.PATH + "/spot/addToAuction.htm"
	});
	//初始化
	form.findById("spotId").setValue(cfg.spotId||"");
	form.findById("productId").setValue(cfg.productId||"");
//	form.findById("promotionsPrice").setValue(cfg.promotionsPrice||"");
//	form.findById("expiredTime").setValue(cfg.expiredTime||"");
	var win = new Ext.Window({
		id : "spotauctionaddformwin",
		title : "推荐到",
		width : 500,
		modal : true,
		items : [form]
	});
	win.show();
}

ast.ast1949.spot.AuctionFormForAdd=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				id:"spotId",
				name:"spotId"
			},{
				xtype:"hidden",
				id:"productId",
				name:"productId"
			},{
				name:"startPrice",
				id:"startPrice",
				fieldLabel:"起拍价格(单位：元)",
				itemCls:"required",
				allowBlank : false
			},{
				name:"upPrice",
				id:"upPrice",
				fieldLabel:"涨价幅度(单位：元)",
				itemCls:"required",
				allowBlank : false
			},{
				xtype : "datefield",
				fieldLabel : "过期时间",
				name : "expiredTime",
				allowBlank:false,
				format : 'Y-m-d H:i:s',
				value:new Date()
			}],
			
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp("spotauctionaddformwin").close();
				}
			}]
		};
		
		ast.ast1949.spot.AuctionFormForAdd.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/spot/addToAuction.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过！");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","操作成功！");
		Ext.getCmp("spotauctionaddformwin").close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","添加失败！");
		Ext.getCmp("spotauctionaddformwin").close();
	}
});