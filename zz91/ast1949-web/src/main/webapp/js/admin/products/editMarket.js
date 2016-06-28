Ext.namespace("ast.ast1949.admin.products.market");

var EDITPRODUCTS = new function(){
    this.TEMPLATE_WIN = "templatewin";
    this.FORM_TAB="formtabpanel";
    this.ADDPROP_GRID="apppropertygrid";
    this.PROPERTY_FORM="propertyform";
    this.PROPERTY_WIN="propertywin";
}

ast.ast1949.admin.products.market.productsForm = Ext.extend(Ext.form.FormPanel,{
    constructor:function(config){
        config=config||{};
        Ext.apply(this,config);
        var form = this;
        
        
        var c = {
            labelAlign : "right",
            labelWidth : 60,
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
                    }]
                },{
                    columnWidth:0.5,
                    layout:"form",
                    defaults:{
                        anchor:"95%",
                        xtype:"textfield",
                        labelSeparator:""
                    },
                    items:[ {
                        id : "name",
                        name : "name",
                        fieldLabel : "市场名称",
                        allowBlank : false,
                        itemCls:"required"
                    
                    },{
                        id : "industry",
                        name : "industry",
                        fieldLabel : "所属行业"
                    },{
                        id : "business",
                        name : "business",
                        fieldLabel : "主营业务"
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
                        id : "area",
                        name : "area",
                        fieldLabel : "市场所在地"
                    },{
                        id : "category",
                        name : "category",
                        fieldLabel : "行业类别",
                     
                    },{
                        id : "address",
                        name : "address",
                        fieldLabel : "详细地址"
                     
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
                            title:"市场简介",

                            items:[{
                                xtype:"textarea",
                                name : "introduction",
                                id : "introduction",
                                height:154,
                                width:"100%"
                            }]
                        }), 
			 new ast.ast1949.admin.productsPic.imageView({
                        	layout:"fit",
                            title:"市场图片"
                        }),
                      
                    ]
                }]
            }],
            buttons:[{
                id : "saveandcheck",
                iconCls:"item-true",
                text : "保存并审核",
                scope:this,
                hidden:this.newProductFlag,
                handler:function(btn){
                    this.saveForm(1);
                }
            },
	   {
                id : "saveandrub",
                iconCls:"item-false",
                text : "不通过",
                scope:this,
                hidden:this.newProductFlag,
                handler:function(btn){
                   this.saveForm(2);
		  
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
        
        ast.ast1949.admin.products.market.productsForm.superclass.constructor.call(this,c);
    },
    
    newProductFlag:false,
    saveUrl:Context.ROOT+Context.PATH+"/admin/products/updateMarket.htm",
    saveNewProduct:function(btn){
//      alert(this.inquiryData.id)
        
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
    saveForm:function(delStatus){
        /*alert(Ext.getCmp('grade').getValue());*/
        var _url = this.saveUrl;
        if (this.getForm().isValid()) {
            this.getForm().submit({
                url : _url,
                method : "post",
                params:{"checkStatus": delStatus},
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
                        ast.ast1949.admin.products.market.auditdelStatus("2");
                        ast.ast1949.admin.products.market.updateForRub();
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
        var reader = [{name:"id",mapping:"id"},
	{name:"name",mapping:"name"},
	{name:"area",mapping:"area"},
	{name:"industry",mapping:"industry"},
	{name:"business",mapping:"business"},
	{name:"category",mapping:"category"},
	{name:"introduction",mapping:"introduction"},
	{name:"address",mapping:"address"}
        
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
            url : Context.ROOT + Context.PATH + "/admin/products/queryMarketById.htm?id=" + id,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    
                    if (record == null) {
                        Ext.MessageBox.alert(Context.MSG_TITLE, "数据加载错误,请联系管理员!");
                    } else {
                        form.getForm().loadRecord(record);
                      
                        Ext.MessageBox.hide();
                        
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
                params:{"marketId":id}
            });

        }
    }
   
});
ast.ast1949.admin.products.market.DescriptionWin = function(code,name){
    var grid = new ast.ast1949.admin.products.market.descriptionGrid({
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
//
//  grid.on("rowdblclick",function(g,rowindex,e){
//
//  });

//  var win = Ext.getCmp(EDITPRODUCTS.TEMPLATE_WIN+code);
//  if(typeof(win)=="undefined"){
    win = new Ext.Window({
        id:EDITPRODUCTS.TEMPLATE_WIN,
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
ast.ast1949.admin.products.market.descriptionGrid = Ext.extend(Ext.grid.GridPanel,{
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

        ast.ast1949.admin.products.market.descriptionGrid.superclass.constructor.call(this,c);
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

ast.ast1949.admin.products.market.auditdelStatus = function(delStatus){

    Ext.Ajax.request({
        url:Context.ROOT+Context.PATH+"/admin/products/updateMarketDelStatus.htm",
        params:{
            "isDel":delStatus,
            "id":Ext.getCmp("id").getValue()
          
        },
        success:function(response,opt){
	     var obj = Ext.decode(response.responseText);
	     
            if(obj.success){
		 var checktab=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(2);
		
                
		 Ext.MessageBox.show({
                    title : Context.MSG_TITLE,
                    msg : "信息已审核为不通过",
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

ast.ast1949.admin.products.market.refuseForSample = function(){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+ "/admin/products/delYP.htm",
		params:{
			"productId":productId
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				ast.ast1949.utils.Msg("","取消样品已成功")
				window.location.reload();
			}else{
				ast.ast1949.utils.Msg("","操作失败");
			}
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
};

ast.ast1949.admin.products.market.refuseForSampleWithReason = function(){
	var unpassReason = Ext.get("sampleReason").dom.value;
	if(unpassReason==null||unpassReason==""){
		return false;
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+ "/admin/products/delYP.htm",
		params:{
			"productId":productId,
			"sampleReason":unpassReason
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				ast.ast1949.utils.Msg("","退回样品已成功")
				window.location.reload();
			}else{
				ast.ast1949.utils.Msg("","操作失败");
			}
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
};

ast.ast1949.admin.products.market.passForSample = function(){
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+ "/admin/products/passYP.htm",
		params:{
			"productId":productId
		},
		success:function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				ast.ast1949.utils.Msg("","样品已通过")
				window.location.reload();
			}else{
				ast.ast1949.utils.Msg("","操作失败");
			}
		},
		failure:function(response,opt){
			ast.ast1949.utils.Msg("","操作失败");
		}
	});
};

ast.ast1949.admin.products.market.updateForRub = function(status){
    var url = "/admin/products/addProductToRub.htm";
    Ext.Ajax.request({
        url:Context.ROOT+Context.PATH+url,
        params:{
            "productId":productId
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            ast.ast1949.utils.Msg("",obj.data)
        },
        failure:function(response,opt){
            var obj = Ext.decode(response.responseText);
            ast.ast1949.utils.Msg("","操作失败")
        }
    });
}

ast.ast1949.admin.products.market.updateBtnToViewCompanyPrice = function(){
    var tbar=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(1).getTopToolbar();
//  var tbar=Ext.getCmp(EDITPRODUCTS.FORM_TAB).getComponent(1).getTopToolbar()[0];
//  alert(tbar)
//  tbar.text="查看企业报价";
//  tbar.iconCls="view";
//  tbar.handler=function(btn){
//      window.open(Context.FRONT_SERVER+"/companyprice/priceDetails.htm?productId="+productId);
//  }
}
