Ext.namespace("ast.ast1949.admin.yuanliao");

var EDITPRODUCTS = new function(){
    this.TEMPLATE_WIN = "templatewin";
    this.FORM_TAB="formtabpanel";
    this.ADDPROP_GRID="apppropertygrid";
    this.PROPERTY_FORM="propertyform";
    this.PROPERTY_WIN="propertywin";
}

ast.ast1949.admin.yuanliao.productsForm = Ext.extend(Ext.form.FormPanel,{
    constructor:function(config){
        config=config||{};
        Ext.apply(this,config);
        var form = this;
        
        
        var c = {
            labelAlign : "right",
            labelWidth : 80,
            frame:true,
            items:[{
                id:"editproducts",
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
                    },{
                    	xtype: 'label',
                        text: '基本属性',
                        margins: {top: 100,right: 0,bottom: 0,left: 100}
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
                        id : "title",
                        name : "title",
                        fieldLabel : "信息标题",
                        allowBlank : false,
                        itemCls:"required"
                    },{
                        xtype:"combotree",
                        fieldLabel:"供求类别",
                        allowBlank:false,
                        itemCls:"required",
                        id : "categoryYuanliaoCodeLabel",
                        name : "categoryYuanliaoCodeLabel",
                        hiddenName : "categoryYuanliaoCode",
                        hiddenId : "categoryYuanliaoCode",
                        tree:new ast.ast1949.admin.yuanliao.treePanel({
                            rootData:""
                        }),
                        listeners:{
                            "change":function(field,newvalue,oldvalue){
                                if(newvalue!=""){
                                    var assist = Ext.getCmp("maincode");
                                    assist.getStore().reload({
                                        params:{
                                            "code":this.getNode().attributes.data,
                                        }
                                    });
                                }
                            }
                        }
                    },{
                        id : "tags",
                        name : "tags",
                        fieldLabel : "产品标签"
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
                        name:"sourceTypeCode",
                        hiddenName:"sourceTypeCode",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"发布来源",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
                                      {name:"pc端生意管家",value:"0"},
                                      {name:"pc端快速发布",value:"2"},
                                  ]
                            })
                    },{
                    	 xtype:"combo",
                         name:"yuanliaoTypeCode",
                         hiddenName:"yuanliaoTypeCode",
                         triggerAction : "all",
                         forceSelection : true,
                         fieldLabel:"供求类型",
                         displayField : "name",
                         valueField : "value",
                         mode : 'local', 
                         forceSelection: true,
                         selectOnFocus:true,
                         readOnly : true,
                         store:new Ext.data.JsonStore( {
                             fields : [ "name", "value" ],
                             data   : [
                                       {name:"供应",value:"10331000"},
                                       {name:"求购",value:"10331001"},
                                   ]
                             })
                    },{
                        id : "account",
                        name : "account",
                        fieldLabel : "发布者(账户)",
                        readOnly : true
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
                        name:"salesMode",
                        hiddenName:"salesMode",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"销售方式",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
                                      {name:"品牌销售",value:"0"},
                                      {name:"自产销售",value:"1"},
                                  ]
                            })
                    },{
                    	 xtype:"combo",
                    	 id:"maincode",
                    	 name:"categoryMainDesc",
                         hiddenName:"categoryMainDesc",
                         triggerAction : "all",
                         forceSelection : true,
                         fieldLabel:"厂家（产地）",
                         displayField : "label",
                         valueField : "code",
                         readOnly : true,
                         store:new Ext.data.JsonStore( {
                             root : "records",
                             fields : [ "label", "code" ],
                             autoLoad:true,
                             url : Context.ROOT + Context.PATH+ "/yuanliao/queryCategoryYuanliao.htm?code="+this.mainCode
                         })
                    },{
                    	fieldLabel : "加工级别",
                    	id : "processLabel",
                    	name : "processLabel"
                    },{
                    	fieldLabel : "用途级别",
                    	id : "usefulLabel",
                    	name : "usefulLabel"
                    },{
                        fieldLabel : "密度",
                        id : "density",
                        name : "density"
                    },{
                        fieldLabel : "拉伸强度",
                        id : "tensile",
                        name : "tensile"
                    }]
                },{
                    id : 'addRight',
                    columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[{
                    	 xtype:"combo",
                    	 name:"type",
                         hiddenName:"type",
                         triggerAction : "all",
                         forceSelection : true,
                         fieldLabel:"产品类型",
                         displayField : "label",
                         valueField : "code",
                         readOnly : true,
                         store:new Ext.data.JsonStore( {
                             root : "records",
                             fields : [ "label", "code" ],
                             autoLoad:true,
                             url : Context.ROOT + Context.PATH+ "/yuanliao/queryCategory.htm?code="+this.type
                         })
                    },{
                        fieldLabel : "牌号",
                        id : "tradeMark",
                        name : "tradeMark"
                    },{
                    	fieldLabel : "加工级别",
                    	id : "charaLabel",
                    	name : "charaLabel"
                    },{
                        fieldLabel : "颜色",
                        id : "color",
                        name : "color"
                    },{
                        fieldLabel : "硬度",
                        id : "hardness",
                        name : "hardness"
                    },{
                    	fieldLabel : "弯曲强度",
                        id : "bending",
                        name : "bending"
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
                        text: '交易条件',
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
                    	xtype : "numberfield",
                    	fieldLabel : "供应数量",
                        id : "quantity",
                        name : "quantity"
                    },{
                    	xtype:"combo",
                        name:"provideStatus",
                        hiddenName:"provideStatus",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"供货情况",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
                                      {name:"长期供应",value:"0"},
                                      {name:"不定期供应",value:"1"},
                                  ]
                            })
                    },{
                    	fieldLabel : "现货所在地",
                        id : "address",
                        name : "address",
                        readOnly : true
                    },{
                        fieldLabel:"交易说明",
                        id:"tradeIntro",
                        name:"tradeIntro"
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
                        name:"unit",
                        hiddenName:"unit",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"计量单位",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
                                      {name:"吨",value:"吨"},
                                      {name:"千克",value:"千克"},
                                      {name:"斤",value:"斤"}
                                  ]
                            })
                        
                    },{
                    	xtype:"combo",
                        name:"goodsTypeCode",
                        hiddenName:"goodsTypeCode",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"供货类型",
                        displayField : "name",
                        valueField : "value",
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        readOnly : true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
                                      {name:"期货",value:"10361000"},
                                      {name:"现货",value:"10361001"},
                                  ]
                            })
                    },{
                        fieldLabel : "发货时间",
                        id : "sendTime",
                        name : "sendTime"
                    },{
                        xtype:"combo",
                        name : "postlimittime",
                        hiddenName:"postlimittime",
                        triggerAction : "all",
                        forceSelection : true,
                        fieldLabel:"信息有效期",
                        displayField : "name",
                        valueField : "value",
                        readOnly : true,
                        mode : 'local', 
                        forceSelection: true,
                        selectOnFocus:true,
                        store:new Ext.data.JsonStore( {
                            fields : [ "name", "value" ],
                            data   : [
                                      {name:"12个月",value:"365"},
                                      {name:"3个月",value:"90"},
                                      {name:"1个月",value:"30"},
                                      {name:"20天",value:"20"},
                                      {name:"10天",value:"10"}
                                  ]
                            })
                    }]
                },{
                    xtype:"tabpanel",
                    id:EDITPRODUCTS.FORM_TAB,
                    columnWidth:1,
                    activeTab:0,
                    border:true,
                    defaults:{
                        height:200
                    },
                    items:[
                        
                        new Ext.Panel({
                            layout:"fit",
                            title:"产品描述",
                            items:[{
                                xtype:"htmleditor",
                                name : "description",
                                id : "description",
                                height:154,
                                width:"100%",
                                listeners:{
                                    "render":function(cmp){
                                        cmp.getToolbar().add('->',{
                                            tooltip:"如果用户没有填写产品描述，您可以从这里选择描述的内容帮助用户补全",
                                            iconCls:"item-open",
                                            cls : "x-btn-icon",
                                            handler:function(btn){
                                                ast.ast1949.admin.yuanliao.DescriptionWin("10341000","details");
                                            }
                                        });
                                    }
                                }
                            }]
                        }),
                        new Ext.Panel({
                            title:"产品报价",
                            layout:"column",
                            autoScroll:true,
                            tbar:[{
                                text:"发布成企业报价",
                                iconCls:"item-add",
                                handler:function(btn){
                                    Ext.Ajax.request({
                                        url:Context.ROOT+Context.PATH+"/yuanliao/saveCompanyPrice.htm",
                                        params:{
                                            "productId":productId
                                        },
                                        success:function(response,opt){
                                            var obj = Ext.decode(response.responseText);
                                            if(obj.success){
                                                btn.setText("查看企业报价");
                                                btn.setIconClass("view");
                                                btn.setHandler(function(){
                                                    window.open("http://price.zz91.com/companyprice/priceDetails.htm?productId="+productId);
                                                });
                                                Ext.MessageBox.show({
                                                    title : Context.MSG_TITLE,
                                                    msg : "已被发布成企业报价",
                                                    buttons : Ext.MessageBox.OK,
                                                    icon : Ext.MessageBox.INFO
                                                });
                                            }else{
                                                Ext.MessageBox.show({
                                                    title : Context.MSG_TITLE,
                                                    msg : "发生错误,信息没有被审核(公司没有填写地区或该供求已经推荐过等等)",
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
                            }],
                            items:[{
                                layout:"form",
                                columnWidth:.5,
                                height:"auto",
                                defaults:{
                                    anchor:"95%",
                                    xtype:"textfield",
                                    labelSeparator:""
                                },
                                items:[{
                                    xtype : "numberfield",
                                    fieldLabel : "产品价格",
                                    id : "price",
                                    name : "price"
                                },{
                                    xtype : "textfield",
                                    fieldLabel : "单位",
                                    id : "priceUnit",
                                    name : "priceUnit"
                                },{
                                    xtype:"numberfield",
                                    fieldLabel:"价格范围从",
                                    id:"minPrice",
                                    name:"minPrice"
                                },{
                                    xtype:"numberfield",
                                    fieldLabel:"到",
                                    id:"maxPrice",
                                    name:"maxPrice"
                                }]
                            }]
                        }),
                        new Ext.Panel({
                            title:"审核",
                            iconCls:"item-info",
                            layout:"fit",
                            tbar:[{
                                text:"通过",
                                iconCls:"item-true",
                                handler:function(btn){
                                    ast.ast1949.admin.yuanliao.auditCheckStatus("1");
                                }
                            },{
                                text:"不通过",
                                iconCls:"item-false",
                                handler:function(btn){
                                    ast.ast1949.admin.yuanliao.auditCheckStatus("2");
                                }
                            },
			"->","如果审核不通过，您可以从这里选择退回原因","-",{
                                text:"审核模板",
                                iconCls:"item-open",
                                handler:function(btn){
                                    ast.ast1949.admin.yuanliao.DescriptionWin("10341001","unpassReason");
                                }
                            }],
                            items:[{
                                xtype:"textarea",
                                name : "unpassReason",
                                id : "unpassReason",
                                height:154,
                                width:"100%"
                            }]
                        }),
                        new ast.ast1949.yuanliao.yuanLiaoPic.imageView({
                        	layout:"fit",
                            title:"相关图片"
                        }),
                        new Ext.Panel({title:"询盘"})
                    ]
                }]
            }],
            buttons:[{
                id : "onlysave",
                text : "仅保存",
                scope:this,
                hidden:this.newProductFlag,
                handler:this.saveForm
            },{
                id : "saveandcheck",
                iconCls:"item-true",
                text : "保存并审核",
                scope:this,
                hidden:this.newProductFlag,
                handler:function(btn){
                    this.saveForm();
                    ast.ast1949.admin.yuanliao.auditCheckStatus("1");
                }
            },
		{
                id : "savenewandcheck",
                iconCls:"item-true",
                text : "保存",
                scope:this,
                hidden:!this.newProductFlag,
                handler:this.saveNewProduct
            }]
        };
        
        ast.ast1949.admin.yuanliao.productsForm.superclass.constructor.call(this,c);
    },
    
    newProductFlag:false,
    saveUrl:Context.ROOT+Context.PATH+"/yuanliao/editYuanliao.htm",
    saveNewProduct:function(btn){
        
        var _url = this.saveUrl;
        if (this.getForm().isValid()) {
            this.getForm().submit({
                url : _url,
                method : "post",
                params:{"inquiryId":this.inquiryData.id},
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
    saveForm:function(){
        /*alert(Ext.getCmp('grade').getValue());*/
        var _url = this.saveUrl;
        if (this.getForm().isValid()) {
            this.getForm().submit({
                url : _url,
                method : "post",
                /*params:{"grade":Ext.getCmp('grade').getValue()},*/
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
    saveFormForRub:function(){
        var _url = this.saveUrl;
        if (this.getForm().isValid()) {
            this.getForm().submit({
                url : _url,
                method : "post",
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
                        // 保存成功 更新状态
                        ast.ast1949.admin.yuanliao.auditCheckStatus("2");
                        ast.ast1949.admin.yuanliao.updateForRub();
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
    loadOneRecord:function(id){
        var reader = [{
            name : "id",
            mapping : "yuanliao.id"
        }, {
            name : "account",
            mapping : "yuanliao.account"
        },// 发布帐号名
        {
            name : "yuanliaoTypeCode",
            mapping : "yuanliao.yuanliaoTypeCode"
        },// 供求类型
        {
            name : "categoryYuanliaoCode",
            mapping : "yuanliao.categoryYuanliaoCode"
        },// 主类别
        {
            name : "categoryYuanliaoCodeLabel",
            mapping : "categoryYuanliaoCodeLabel"
        },// 主类别名称
        {
        	name : "postlimittime",
        	mapping : "postlimittime"
        },//有效时间
        {
            name : "categoryMainDesc",
            mapping : "yuanliao.categoryMainDesc"
        },// 辅助类别
        {
            name : "sourceTypeCode",
            mapping : "yuanliao.sourceTypeCode"
        },// 信息来源
        {
            name : "title",
            mapping : "yuanliao.title"
        },// 标题
        {
        	name : "sendTime",
        	mapping : "yuanliao.sendTime"
        	
        },//发货时间
        {
        	name : "goodsTypeCode",
        	mapping : "yuanliao.goodsTypeCode"
        	
        },//供货类型
        {
            name : "description",
            mapping : "yuanliao.description"
        },// 详细描述
        {
            name : "checkStatus",
            mapping : "yuanliao.checkStatus"
        },// 审核
        // 审核未通过原因
        {
            name : "unpassReason",
            mapping : "yuanliao.unpassReason"
        },
        {
            name : "provideStatus",
            mapping : "yuanliao.provideStatus"
        },// 长期供货
        {
            name : "quantity",
            mapping : "yuanliao.quantity"
        },// 供货总量
        {
            name : "price",
            mapping : "yuanliao.price"
        },// 产品价格
        {
        	name : "minPrice",
        	mapping : "yuanliao.minPrice"
        },//价格最小值
        {
        	name : "maxPrice",
        	mapping : "yuanliao.maxPrice"
        },
        {
            name:"priceUnit",
            mapping:"yuanliao.priceUnit"
        },//价格单位
        {
            name : "unit",
            mapping : "yuanliao.unit"
        },// 计量单位
        {
            name : "tradeMark",
            mapping : "yuanliao.tradeMark"
        },// 牌号
        {
            name : "address",
            mapping : "address"
        },// 货源地
        {
            name : "processLabel",
            mapping : "processLabel"
        },//加工级别code
        {
            name:"charaLabel",
            mapping:"charaLabel"
        },//特性级别code
        {
            name:"usefulLabel",
            mapping:"usefulLabel"
        },//用途级别code
        {
            name : "type",
            mapping : "yuanliao.type"
        },// 类型code
        {
            name : "typeLabel",
            mapping : "typeLabel"
        },// 类型名称
        {
            name : "color",
            mapping : "yuanliao.color"
        },// 颜色
        {
            name : "density",
            mapping : "yuanliao.density"
        },//密度
        {
            name : "hardness",
            mapping : "yuanliao.hardness"
        },//硬度
        {
            name : "tensile",
            mapping : "yuanliao.tensile"
        },//拉伸强度
        {
            name : "bending",
            mapping : "yuanliao.bending"
        },//弯曲强度
        {
            name : "tags",
            mapping : "yuanliao.tags"
        },// 现货所在地
        {
            name : "salesMode",
            mapping : "yuanliao.salesMode"
        },// 销售方式
        {
        	name : "tradeIntro",
        	mapping : "yuanliao.tradeIntro"
        	
        }//交易说明
        ];
        
        var form = this;
        var _store = new Ext.data.JsonStore({
            root : "records",
            fields : reader,
            url : Context.ROOT + Context.PATH + "/yuanliao/init.htm?id=" + id,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    
                    if (record == null) {
                        Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
                    } else {
                        form.getForm().loadRecord(record); 
                        var checktab=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(2);
                        if(record.get("checkStatus")=="1"){
                        	checktab.setIconClass("item-true");
                        }
                        if(record.get("checkStatus")=="2"){
                        	checktab.setIconClass("item-false");
                        }
                        if(record.get("categoryYuanliaoCode")){
                        	form.findById("categoryYuanliaoCodeLabel").setValue({
                        		text:record.get("categoryYuanliaoCodeLabel"),
                        		attributes:{data:record.get("categoryYuanliaoCode")}
                        	});
                        }
                    }
                    
                }
            }
        });
    },
    loadImage:function(id){
        // TODO 载入图片信息
        var imageview = this.findById("images-view");
        if(id>0){
            imageview.imageStore.load({
                params:{"yuanliaoId":id}
            });
        }
    }
});
ast.ast1949.admin.yuanliao.DescriptionWin = function(code,name){
    var grid = new ast.ast1949.admin.yuanliao.descriptionGrid({
        queryUrl:Context.ROOT + Context.PATH + "/admin/descriptiontemplate/query.htm?templateCode="+code,
        region:"center",
        height:300,
        setSelectedTemplate:function(btn){
            var sels = grid.getSelections();
            if (sels.length > 0) {
                Ext.getCmp(name).setValue(Ext.getCmp(name).getValue() + sels[0].get("content"));
                Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN).close();
            }
        },
        listeners:{
            "rowdblclick":function(g,rowindex,e){
                var sels = grid.getSelections();
                Ext.getCmp(name).setValue(Ext.getCmp(name).getValue() + sels[0].get("content"));
                Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN).close();
            }
        }
    });
    win = new Ext.Window({
        id:EDITPRODUCTS.TEMPLATE_WIN,
        title:"选择模板",
        width:600,
        autoHeight:true,
        modal:true,
        items:[grid]
    });
    win.show();
}

/**
 * 描述模板使用的grid
 * templateCode:模板类别
 * */
ast.ast1949.admin.yuanliao.descriptionGrid = Ext.extend(Ext.grid.GridPanel,{
    queryUrl:Context.ROOT + Context.PATH + "/admin/descriptiontemplate/query.htm",
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
            width : 300,
            sortable : false,
            dataIndex : "content"
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
            }
        }]

        var c={
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

        ast.ast1949.admin.yuanliao.descriptionGrid.superclass.constructor.call(this,c);
    },
    fieldsRecord:[{
        name : "id",
        mapping : "descriptionTemplateDO",
        convert : function(v) {
            return v.id;
        }
    }, {
        name : "content",
        mapping : "descriptionTemplateDO",
        convert : function(v) {
            return v.content;
        }
    }, {
        name : "gmtCreated",
        mapping : "descriptionTemplateDO",
        convert : function(v) {
            return v.gmtCreated;
        }
    }, {
        name : "gmtModified",
        mapping : "descriptionTemplateDO",
        convert : function(v) {
            return v.gmtModified;
        }
    }, "templateName"]
});

ast.ast1949.admin.yuanliao.auditCheckStatus = function(checkStatus){
    Ext.Ajax.request({
        url:Context.ROOT+Context.PATH+"/yuanliao/updateCheckStatus.htm",
        params:{
            "checkStatus":checkStatus,
            "id":Ext.getCmp("id").getValue(),
            "unpassReason":Ext.getCmp("unpassReason").getValue()
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
                var checktab=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(2);
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

