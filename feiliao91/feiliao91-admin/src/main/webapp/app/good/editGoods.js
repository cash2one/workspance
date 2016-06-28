Ext.namespace("ast.feiliao91.admin.goods");

var EDITGOODS = new function(){
	this.FORM_TAB="formtabpanel";
	this.TEMPLATE_WIN = "templatewin";
}

ast.feiliao91.admin.goods.goodsForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
        config=config||{};
        Ext.apply(this,config);
        var form = this;
        
        var c = {
			labelAlign : "right",
			labelWidth : 80,
			frame:true,
			items:[{
				id:"editgoods",
				layout:"column",
				frame:true,
				autoScroll:true,
				
				items:[{
					columnWidth:1,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                        xtype:"hidden",
                        id:"id",
                        name:"id"
                    },{
                        xtype:"hidden",
                        id:"companyId",
                        name:"companyId"
                    }]
				},{
					columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },items:[{
                    	 id : "title",
                         name : "title",
                         fieldLabel : "信息标题",
                         allowBlank : false,
                         itemCls:"required"
                    }
                    ]
				},{
					columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                        	xtype:"combotree",
                            fieldLabel:"供求类别",
                            allowBlank:false,
                            itemCls:"required",
                            id : "categoryProductsMainLabel",
                            name : "categoryProductsMainLabel",
                            hiddenName : "categoryProductsMainCode",
                            hiddenId : "categoryProductsMainCode",
                            tree:new ast.ast1949.admin.categoryproducts.treePanel({
                                rootData:"1000",
                                isAssist:'0'
                            }),
                            listeners:{
                                "change":function(field,newvalue,oldvalue){
                                    if(newvalue!=""){
                                    	var assist = Ext.getCmp("categoryProductsAssistLabel");
                                    }
                                }
                            }
                   }]
				},{
                    columnWidth:1,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                        xtype: 'label',
                        text: '交易信息',
                        margins: {top: 100,right: 0,bottom: 0,left: 100
                        }
                    }]
                },{
                	id : 'addLeft',
                    columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	xtype:"combo",
                        name:"isImport",
                        hiddenName:"isImport",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"进口属性",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
										{name:"国内",value:1},
										{name:"进口",value:0},				  
                                  ]
                            })
                    }
//                    ,{
//                    	fieldLabel : "价格",
//                        id: 'price',
//                        name : "price"
//                    }
//                    ,{
//                    	fieldLabel : "可售数量",
//                        id: 'quantity',
//                        name : "quantity"
//                    },
//                    ,{
//                    	fieldLabel : "运费",
//                        id: 'fare',
//                        name : "fare",
//                    }
                    ]
                },{
//                	id : 'addRight',
                    columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },items:[{
                    	xtype:"combo",
                        name:"provideCode",
                        hiddenName:"provideCode",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"供货方式",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
										{name:"现货",value:"1"},
										{name:"可订货生产",value:"0"},				  
                                  ]
                            })
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "价格",
                      id: 'price',
                      name : "price"
                  }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        readOnly:"true",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "单位",
                      id: 'priceUnit',
                      name : "priceUnit",
//                      value:"元",
                  }]
                },{
                    columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	xtype:"combo",
                        name:"hasTax",
                        hiddenName:"hasTax",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"是否含税",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
										{name:"含税",value:0},
										{name:"不含税",value:1},				  
                                  ]
                            })
                    }
//                    ,{
//                    	fieldLabel : "最小起订量",
//                        id: 'number',
//                        name : "number"
//                    },{
//                    	fieldLabel : "货物所在地",
//                        id: 'area',
//                        name : "area"
//                    }
                    ]
                },{
                	//hello
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "可售数量",
                      id: 'quantity',
                      name : "quantity"
                  }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        readOnly:"true",
                        labelSeparator:""
                    },
                    items:[
//                           {
//                    	fieldLabel : "单位",
//                      id: 'unit',
//                      name : "unit",
//                      value:"元",
//                  }
					{
                    	xtype:"combo",
                        name:"unit",
                        hiddenName:"unit",
                        triggerAction : "all",
//                        forceSelection : true,
                        fieldLabel:"单位",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
//                        forceSelection: true,
//                        selectOnFocus:true,
//                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
										{name:"吨",value:"吨"},
										{name:"千克",value:"千克"},				  
                                  ]
                            }),
                        listeners:{
                            "change":function(field,newvalue,oldvalue){
                                //改变最小起订量的单位
                            	var minNumber = Ext.getCmp("minNumber");
                            	minNumber.setValue(newvalue);
                            	//改变价格为（元/单位）
                            	var priceUnit = Ext.getCmp("priceUnit");
                            	priceUnit.setValue("元/"+newvalue);
                            }
                        }
                    }
                ]
                },{
                	//hello
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "最小起订量",
                      id: 'number',
                      name : "number"
                  }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        readOnly:"true",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "单位",
                      id: 'minNumber',
                      name : "minNumber",
//                      value:"元",
                  }]
                },{
                	columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                      fieldLabel : "运费",
                      id: 'fare',
                      allowBlank:false,
                      name : "fare"
                  }]
                },{
                	columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "货物所在地",
                      id: 'area',
                      name : "area"
                  }]
                },{
                    columnWidth:1,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                        xtype: 'label',
                        text: '产品属性',
                        margins: {top: 100,right: 0,bottom: 0,left: 100
                        }
                    }]
                },{
                    columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "级别",
                        id: 'level',
                        name : "level"
                    },{
                    	fieldLabel : "形态",
                        id: 'form',
                        name : "form"
                    }
                    ]
                },{
                	columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "颜色",
                        id: 'color',
                        name : "color"
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
	                    xtype:"combo",
	                    id:"useLevelCode_combo",
	                    hiddenName:"useLevel",
	                    triggerAction : "all",
	                    forceSelection : true,
	                    fieldLabel:"用途级别",
	                    displayField : "label",
	                    valueField : "code",
	                    store:new Ext.data.JsonStore( {
	                        root : "records",
	                        fields : [ "label", "code" ],
	                        autoLoad:true,
	                        url : Context.ROOT + Context.PATH+ "/admin/good/getThreeLevelProp.htm?type=1&code="+mainCode
	                    })
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "说明",
                        id: 'useIntro',
                        name : "useIntro"
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
	                    xtype:"combo",
	                    id:"processLevelCode_combo",
	                    hiddenName:"processLevel",
	                    triggerAction : "all",
	                    forceSelection : true,
	                    fieldLabel:"加工级别",
	                    displayField : "label",
	                    valueField : "code",
	                    store:new Ext.data.JsonStore( {
	                        root : "records",
	                        fields : [ "label", "code" ],
	                        autoLoad:true,
	                        url : Context.ROOT + Context.PATH+ "/admin/good/getThreeLevelProp.htm?type=2&code="+mainCode
	                    })
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "说明",
                        id: 'processIntro',
                        name : "processIntro"
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
	                    xtype:"combo",
	                    id:"charLevelCode_combo",
	                    hiddenName:"charLevel",
	                    triggerAction : "all",
	                    forceSelection : true,
	                    fieldLabel:"特性级别",
	                    displayField : "label",
	                    valueField : "code",
	                    store:new Ext.data.JsonStore( {
	                        root : "records",
	                        fields : [ "label", "code" ],
	                        autoLoad:true,
	                        url : Context.ROOT + Context.PATH+ "/admin/good/getThreeLevelProp.htm?type=3&code="+mainCode
	                    })
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "说明",
                        id: 'charIntro',
                        name : "charIntro"
                    }]
                },{
                	columnWidth:0.25,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        labelSeparator:""
                    },
                    items:[{
                    	fieldLabel : "信息有效期至",
                        id: 'expireTimeStr',
                        xtype:"datefield",
                        format:"Y-m-d H:i:s"
                    }]
                },{
					xtype:"tabpanel",
                    id:EDITGOODS.FORM_TAB,
                    columnWidth:1,
                    activeTab:0,
                    border:true,
                    defaults:{
                        height:350
                    },
                    items:[
							new Ext.Panel({
							    layout:"fit",
							    title:"产品描述",
							    items:[{
							        xtype:"htmleditor",
							        name : "detail",
							        id : "detail",
							        height:154,
							        width:"100%",
							        listeners:{
							            "render":function(cmp){
							                cmp.getToolbar().add('->',{
							                    tooltip:"如果用户没有填写产品描述，您可以从这里选择描述的内容帮助用户补全",
							                    iconCls:"item-open",
							                    cls : "x-btn-icon",
							                    handler:function(btn){
							                    	ast.feiliao91.admin.goods.DescriptionWin("1010","detail");
							                    }
							                });
							            }
							        }
							    }]
							}),
							new ast.ast1949.admin.productsproperty.properyGrid({
	                            title:"增加的属性",
	                            layout:"fit",
	                        }),
	                        new ast.ast1949.admin.productsPic.imageView({
	                        	layout:"fit",
	                            title:"相关图片"
	                        }),
	                        new Ext.Panel({
	                            title:"审核",
	                            iconCls:"item-info",
	                            layout:"fit",
	                            tbar:[{
	                                text:"通过",
	                                iconCls:"item-true",
	                                handler:function(btn){
	                                	ast.feiliao91.admin.goods.auditCheckStatus("1");
	                                }
	                            },{
	                                text:"不通过",
	                                iconCls:"item-false",
	                                handler:function(btn){
	                                	ast.feiliao91.admin.goods.auditCheckStatus("2");
	                                }
	                            },
				"->","如果审核不通过，您可以从这里选择退回原因","-",{
	                                text:"审核模板",
	                                iconCls:"item-open",
	                                handler:function(btn){
	                                	ast.feiliao91.admin.goods.DescriptionWin("1011","unpassReason");
	                                }
	                            }],
	                            items:[{
	                                xtype:"textarea",
	                                name : "unpassReason",
	                                id : "unpassReason",
	                                height:154,
	                                width:"100%"
	                            }]
	                        })
                           ]
				}]
			}],
			 buttons:[{
	                id : "onlysave",
	                text : "仅保存",
	                scope:this,
	                handler:function(btn){
	                    this.saveForm();
	                }
	            },{
	                id : "saveandcheck",
	                iconCls:"item-true",
	                text : "保存并审核",
	                scope:this,
	                handler:function(btn){
	                    this.saveForm();
	                    ast.feiliao91.admin.goods.auditCheckStatus("1");
	                }
	            }
	            ]
        };

        ast.feiliao91.admin.goods.goodsForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/good/updateGoods.htm",
	saveForm:function(){
        var _url = this.saveUrl;
        if (this.getForm().isValid()) {
            this.getForm().submit({
                url : _url,
                method : "post",
                params:{"expireTimeStr":ast.feiliao91.admin.goods.dateToStr(Ext.getCmp('expireTimeStr').getValue())},
                waitMsg:Context.SAVEMASK.msg,
                success : function(_form,_action){
                    var res = _action.result;
                    if (res.success) {
                        Ext.MessageBox.show({
                            title : Context.MSG_TITLE,
                            msg : "信息已被保存,您可以关闭窗口了",
                            buttons : Ext.MessageBox.OK,
                            icon : Ext.MessageBox.INFO
                        });
                    } else {
                        Ext.MessageBox.show({
                            title : Context.MSG_TITLE,
                            msg : "发生错误,信息没有被保存",
                            buttons : Ext.MessageBox.OK,
                            icon : Ext.MessageBox.INFO
                        });
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
	loadOneRecord:function(id,mainCode){
		var reader = [{
			name : "id",
            mapping : "goods.id"
		},{
			name : "title",
            mapping : "goods.title"
		},{
			//类别code
			name : "categoryProductsMainCode",
            mapping : "goods.mainCategory"
		},{
			//类别label
			name : "categoryProductsMainLabel",
			mapping : "goodsCategoryLabel"
		},{
			//进口属性
			name : "isImport",
			mapping : "goods.isImport"
		},{
			//价格
			name : "price",
			mapping : "goods.price"
		},{
			//可售数量
			name : "quantity",
			mapping : "goods.quantity"
		},{
			name : "unit",
			mapping : "goods.unit"
		},{
			//运费
			name : "fare",
			mapping : "goods.fare"
		},{
			//供货方式
			name : "provideCode",
			mapping : "goods.provideCode"
		},{
			//是否含税
			name : "hasTax",
			mapping : "goods.hasTax"
		},{
			//最小起订量
			name : "number",
			mapping : "goods.number"
		},{
			//货物所在地
			name : "area",
			mapping : "area"
		},{
			//级别
			name : "level",
			mapping : "goods.level"
		},{
			//形态
			name : "form",
			mapping : "goods.form"
		},{
			//颜色
			name : "color",
			mapping : "goods.color"
		},{
			//用途级别Code
			name : "useLevel",
			mapping : "goods.useLevel"
		},{//        var fareStatus =  ast.feiliao91.admin.goods.getFare(Ext.getCmp('fare').getValue());
//	        console.log(fareStatus);
//	        return false;
//	        console.log(ast.feiliao91.admin.goods.dateToStr(Ext.getCmp('expireTimeStr').getValue()));
			//用途级别Label
			name : "useLabel",
			mapping : "useLabel"
		},{
			//用途级别说明
			name : "useIntro",
			mapping : "goods.useIntro"
		},{
			//加工级别Code
			name : "processLevel",
			mapping : "goods.processLevel"
		},{
			//加工级别Label
			name : "processLabel",
			mapping : "processLabel"
		},{
			//加工级别说明
			name : "processIntro",
			mapping : "goods.processIntro"
		},{
			//特性级别Code
			name : "charLevel",
			mapping : "goods.charLevel"
		},{
			//特性级别Label
			name : "charaLabel",
			mapping : "charaLabel"
		},{
			//特性级别说明
			name : "charIntro",
			mapping : "goods.charIntro"
		},{	
			//信息有效期
			name : "expireTime",
			mapping : "goods.expireTime"
		},{
			//详细描述
			name : "detail",
            mapping : "detail"
		},{
			//审核状态
			name : "checkStatus",
            mapping : "goods.checkStatus"
		}
		];
		
		Ext.MessageBox.show({
            title : Context.MSG_TITLE,
            msg : Context.LOADMASK.msg,
            icon : Ext.MessageBox.INFO,
            closable:false,
            width:350,
            wait:true,
            waitConfig: {interval:300}
        });
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			root : "records",
            fields : reader,
            url : Context.ROOT + Context.PATH + "/admin/good/init.htm?id=" + id,
            autoLoad : true,
            listeners : {
            	"datachanged" : function() {
					var record = _store.getAt(0);
					if (record == null) {
					    Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
					} else {
					    form.getForm().loadRecord(record);
					    //主类别初始设置
					    form.findById("categoryProductsMainLabel").setValue({
                            text:record.get("categoryProductsMainLabel"),
                            attributes:{data:record.get("categoryProductsMainCode")}
                        });
//					    //用途级别初始设置
//					    form.findById("useLabel").setValue({
//                            text:record.get("useLabel"),
//                            attributes:{data:record.get("useLevelCode")}
//                        });
//					    if(!form.findById("useLevelCode_combo").getValue()){
//					    	var useLevelCode_combo = form.findById("useLevelCode_combo");
//					    	useLevelCode_combo.getStore().reload({
//                                params:{
//                                    "parentCode":Ext.get("categoryProductsMainCode").dom.value,
//                                }
//                            });
//					    }
//					    //加工级别初始设置
//					    form.findById("processLabel").setValue({
//                            text:record.get("processLabel"),
//                            attributes:{data:record.get("processLevelCode")}
//                        });
//					    //特性级别初始设置
//					    form.findById("charaLabel").setValue({
//                            text:record.get("charaLabel"),
//                            attributes:{data:record.get("charLevelCode")}
//                        });
					    //单位初始化
					    var unit = record.get("unit");
					    if(unit=="吨"){
					    	form.findById("minNumber").setValue("吨");
					    	form.findById("priceUnit").setValue("元/吨");
					    }
					    if(unit=="千克"){
					    	form.findById("minNumber").setValue("千克");
					    	form.findById("priceUnit").setValue("元/千克");
					    }
					    //运费显示
					    var fare = record.get("fare");
					    if(fare=="0"){
					    	form.findById("fare").setValue("包邮");
					    }else if(fare=="-1"){
					    	form.findById("fare").setValue("商议后调整");
					    }else{
					    	form.findById("fare").setValue(fare);
					    }
					    //信息有效期初始化
					    if (record.get("expireTime")!=null){
							form.findById("expireTimeStr").setValue(Ext.util.Format.date(new Date(record.get("expireTime").time), 'Y-m-d H:i:s'));
						}
					    //审核标志初始化
					    var checktab=Ext.getCmp(EDITGOODS.FORM_TAB).getComponent(3);
					    var ck = record.get("checkStatus");
					    if(ck==0){
					    	checktab.setIconClass("item-info");
					    }else if(ck==1){
					    	checktab.setIconClass("item-true");
					    }else{
					    	checktab.setIconClass("item-false");
					    }
					    Ext.MessageBox.hide();
					}
            	}
            }
		});
	},
    loadImage:function(id,companyId){
        // TODO 载入图片信息
        var imageview = this.findById("images-view");
        if(id>0){
            imageview.imageStore.load({
                params:{"goodsId":id,"companyId":companyId}
            });
//          imageview.deleteImageButton.enable();
        }
    }
});

//更新审核
ast.feiliao91.admin.goods.auditCheckStatus = function(checkStatus){
	var _ids = new Array();
	_ids.push(Ext.getCmp("id").getValue());
    Ext.Ajax.request({
        url:Context.ROOT+Context.PATH+"/admin/good/updateCheckStatus.htm",
        params:{
            "checkStatus":checkStatus,
            ids: _ids.join(","),
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
                var checktab=Ext.getCmp(EDITGOODS.FORM_TAB).getComponent(3);
                if(checkStatus=="1"){
                    checktab.setIconClass("item-true");
                }
                
                if(checkStatus=="2"){
                    checktab.setIconClass("item-false");
                }
                
                Ext.MessageBox.show({
                    title : Context.MSG_TITLE,
                    msg : "信息已审核",
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.INFO
                });
            }else{
                Ext.MessageBox.show({
                    title : Context.MSG_TITLE,
                    msg : "发生错误,信息没有被审核",
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.INFO
                });
            }
        },
        failure:function(response,opt){
            Ext.MessageBox.show({
                title : Context.MSG_TITLE,
                msg : "发生错误,信息没有被审核",
                buttons : Ext.MessageBox.OK,
                icon : Ext.MessageBox.INFO
            });
        }
    });
}

ast.feiliao91.admin.goods.dateToStr = function(datetime){ 
	 var year = datetime.getFullYear();
	 var month = datetime.getMonth()+1;//js从0开始取 
	 var date = datetime.getDate(); 
	 var hour = datetime.getHours(); 
	 var minutes = datetime.getMinutes(); 
	 var second = datetime.getSeconds();
	 
	 if(month<10){
	  month = "0" + month;
	 }
	 if(date<10){
	  date = "0" + date;
	 }
	 if(hour <10){
	  hour = "0" + hour;
	 }
	 if(minutes <10){
	  minutes = "0" + minutes;
	 }
	 if(second <10){
	  second = "0" + second ;
	 }
	 
	 var time = year+"-"+month+"-"+date+" "+hour+":"+minutes+":"+second; //2009-06-12 17:18:05
	 return time;
}

/**
 * 模板弹窗
 */
ast.feiliao91.admin.goods.DescriptionWin = function(code,name){
    var grid = new ast.feiliao91.admin.goods.descriptionGrid({
        queryUrl:Context.ROOT + Context.PATH + "/admin/category/getChildListByMem.htm?parentCode="+code,
        region:"center",
        height:300,
        setSelectedTemplate:function(btn){
            var sels = grid.getSelections();
            if (sels.length > 0) {
                Ext.getCmp(name).setValue(Ext.getCmp(name).getValue() + sels[0].get("label"));
                Ext.getCmp(EDITGOODS.TEMPLATE_WIN).close();
            }
        },
        listeners:{
            "rowdblclick":function(g,rowindex,e){
                var sels = grid.getSelections();
                Ext.getCmp(name).setValue(Ext.getCmp(name).getValue() + sels[0].get("label"));
                Ext.getCmp(EDITGOODS.TEMPLATE_WIN).close();
            }
        }
    });
//
//  grid.on("rowdblclick",function(g,rowindex,e){
//
//  });

//  var win = Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN+code);
//  if(typeof(win)=="undefined"){
    win = new Ext.Window({
        id:EDITGOODS.TEMPLATE_WIN,
        title:"选择模板",
        width:600,
        autoHeight:true,
        modal:true,
        items:[grid]
    });
//  }
    win.show();
}

/**
 * 描述模板使用的grid
 * templateCode:模板类别
 * */
ast.feiliao91.admin.goods.descriptionGrid = Ext.extend(Ext.grid.GridPanel,{
//    queryUrl:Context.ROOT + Context.PATH + "/admin/descriptiontemplate/query.htm",
    constructor:function(config){
        config = config||{};
        Ext.apply(this,config);

        var _fields = this.fieldsRecord;
        var _url = this.queryUrl;
        var _store = new Ext.data.JsonStore({
            root:"records",
            totalProperty:"totals",
            remoteSort:true,
            fields:_fields,
            url:_url,
            autoLoad:true
        });

        var _sm=new Ext.grid.CheckboxSelectionModel();
        var _cm = new Ext.grid.ColumnModel([_sm, {
            header : "描述模板内容",
            width : 400,
            sortable : false,
            dataIndex : "label"
        }, {
            header : "模板类别",
            width : 100,
            sortable : false,
            dataIndex : "templateName"
        }]);

        var tbar = [{
            text : '选择',
            tooltip : '选择一个描述模板',
            iconCls : 'add',
            handler : function(btn) {
//              var g = Ext.getCmp("descriptionTemplateGrid");
//              var sels = g.getSelections();
//              if (sels.length != 1) {
//                  Ext.Msg.alert(Context.MSG_TITLE, "请至少选定一条记录");
//              } else {
//                  Ext.get("details").dom.value = Ext.get("details").dom.value + sels[0].get("content");
//                  Ext.getCmp("descriptionTemplateWin").close();
//              }
            }
        }]

        var c={
//          viewConfig:{
//              autoFill:true
//          },
            store:_store,
            sm:_sm,
            cm:_cm,
            tbar:[{
                text : '选择',
                tooltip : '选择一个描述模板',
                iconCls : 'add',
                handler:this.setSelectedTemplate
            }]
        };

        ast.feiliao91.admin.goods.descriptionGrid.superclass.constructor.call(this,c);
    },
    fieldsRecord:[{
        name : "label",
        mapping : "label",
    },"templateName",
    ]
});