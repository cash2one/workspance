Ext.namespace("com.zz91.trade.bw");

com.zz91.trade.bw.FORMFIELD=[
	{name:"id",mapping:"id"},
	{name:"targetId",mapping:"targetId"},
	{name:"cid",mapping:"cid"},
	{name:"title",mapping:"title"},
	{name:"photoCover",mapping:"photoCover"},
	{name:"detailsQuery",mapping:"detailsQuery"},
	{name:"areaCode",mapping:"areaCode"},
	{name:"provinceCode",mapping:"provinceCode"},
	{name:"priceNum",mapping:"priceNum"},
	{name:"priceUnits",mapping:"priceUnits"},
	{name:"start",mapping:"start"},
	{name:"end",mapping:"end"},
	{name:"keywords",mapping:"keywords"},
	{name:"type",mapping:"type"},
	{name:"status",mapping:"status"},
	{name:"creditFile",mapping:"creditFile"},
	{name:"cname",mapping:"cname"},
	{name:"gmtRegister",mapping:"gmtRegister"},
	{name:"memberCode",mapping:"memberCode"},
	{name:"domainTwo",mapping:"domainTwo"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.trade.bw.BWForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var form = this;
		
		var c = {
			labelAlign : "right",
			labelWidth : 100,
			frame:true,
			items:[{
				layout:"column",
				frame:true,
				autoScroll:true,
				items:[{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						fieldLabel : "编号",
						name:"id",
						readOnly:true
					},{
						fieldLabel : "公司编号",
						name:"cid",
						readOnly:true
					},{
						fieldLabel : "信息编号",
						name:"targetId",
						readOnly:true
					},{
						fieldLabel : "地区编号",
						name:"areaCode",
						readOnly:true
					},{
						fieldLabel : "省份编号",
						name:"provinceCode",
						readOnly:true
					},{
						name : "title",
						fieldLabel : "信息标题",
						readOnly:true
					},{
						fieldLabel:"购买关键字",
						name:"keywords",
						allowBlank : false,
						itemCls:"required"
					},{
						id: "type",
						name : "type",
						fieldLabel : "类型(0供应,1求购)",
						readOnly:true
					},{
						name : "photoCover",
						fieldLabel : "图片地址",
						readOnly:true
					},{
						xtype:"datefield",
						fieldLabel:"开始时间",
						id:"startStr",
						name:"startStr",
						format:"Y-m-d H:i:s",
						value:new Date(),
						allowBlank : false,
						itemCls:"required"
					}]
				},{
					columnWidth:.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						name : "cname",
						fieldLabel : "公司名称",
						readOnly:true
					},{
						fieldLabel:"价格",
						xtype : "numberfield",
						name:"priceNum",
						readOnly:true
					},{
						fieldLabel:"价格单位",
						name:"priceUnits",
						readOnly:true
					},{
						fieldLabel:"会员编号",
						name:"memberCode",
						readOnly:true
					},{
						fieldLabel:"注册时间",
						name:"regStr",
						id:"regStr",
						readOnly:true
					},{
						xtype:"checkbox",
						boxLabel:'前台显示(该选择决定该标王信息是否在前台显示)', 
						name:'status',
						inputValue:"1",
						allowBlank : false,
						itemCls:"required"
					},{
						fieldLabel:"荣誉证书数量",
						name:"creditFile",
						readOnly:true
					},{
						fieldLabel:"二级域名",
						name:"domainTwo",
						readOnly:true
					},{
						xtype:"datefield",
						fieldLabel:"结束时间",
						id:"endStr",
						name:"endStr",
						format:"Y-m-d H:i:s",
						value:new Date(),
						allowBlank : false,
						itemCls:"required"
					}]
				},{	
						columnWidth:1,
						layout:"form",
						defaults:{
							anchor:"99%",
							labelSeparator:""
						},
						items:[{
							xtype:"textarea",
							anchor:"99%",
							fieldLabel:"详细信息(纯文本)",
							name:"detailsQuery",
							height:300,
							readOnly:true
						}]
				}]
			}],
			buttons:[{
				text : "保存",
				iconCls:"saveas16",
				scope:this,
				handler:this.saveForm
			},{
				text : "关闭",
				iconCls:"close16",
				scope:this,
				handler:function(btn){
					window.close();
				}
			}]
		};

		com.zz91.trade.bw.BWForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+"/trade/tradesupply/createRecBw.htm",
	saveForm:function(){
		var _url = this.saveUrl;
		if (this.getForm().isValid()) {
			this.getForm().submit({
				url : _url,
				method : "post",
				success : function(_form,_action){
					var res = _action.result;
					if (res.success) {
						com.zz91.utils.Msg("信息提示","推荐成功");
					} else {
						com.zz91.utils.Msg("信息提示","推荐成功");
					}
				},
				failure : function(_form,_action){
					Ext.MessageBox.show({
						title : Context.MSG_TITLE,
						msg : "发生错误,信息没有被保存",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	},
	loadRecord:function(sid,type){
		var form = this;
		var store= new Ext.data.JsonStore({
			fields:com.zz91.trade.bw.FORMFIELD,
			url:Context.ROOT+"/trade/tradesupply/queryBwDetails.htm",
			baseParams:{"sid":sid},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						form.findById("type").setValue(type);
						form.findById("regStr").setValue(Ext.util.Format.date(new Date(record.get("gmtRegister").time), 'Y-m-d H:i:s'));
						form.findById("startStr").setValue(Ext.util.Format.date(new Date(), 'Y-m-d H:i:s'));
						form.findById("endStr").setValue(Ext.util.Format.date(new Date(), 'Y-m-d H:i:s'));
					}
				}
			}
		});
	}
	
});