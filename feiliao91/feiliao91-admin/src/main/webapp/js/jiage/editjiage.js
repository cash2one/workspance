Ext.namespace("ast.ast1949.trust.buy.detail");

var ACCOUNT = new function(){
	this.ACCOUNT_GRID = "accountgrid";
}
var EDITPRODUCTS = new function(){
    this.TEMPLATE_WIN = "templatewin";
}
ast.ast1949.trust.buy.detail.COMPANYFIELD=[
{name:"id",mapping:"id"},
{name:"title",mapping:"title"},
{name:"categoryName",mapping:"categoryName"},
{name:"category",mapping:"category"},
{name:"companyName",mapping:"company.name"},
{name:"membershipCode",mapping:"company.membershipCode"},
{name:"instruction",mapping:"instruction"},
{name:"checkReason",mapping:"checkReason"}
];


ast.ast1949.trust.buy.detail.BuyDetailForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
				autoScroll:true,
				frame : true,
				labelAlign : "right",
				labelWidth : 90,
				layout : "column",
				items:[{
					columnWidth:.5,
					layout : "form",
					defaults:{
						anchor:"100%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype : "hidden",
						name : "id",
						id : "id"
					},{
						fieldLabel : '标题',
						name : 'title'
					},{
						xtype:"combotree",
						fieldLabel:"产品",
						id:"categoryName",
						name:"categoryName",
						hiddenName:"category",
						hiddenId:"category",
						tree:new Ext.tree.TreePanel({
							loader: new Ext.tree.TreeLoader({
								root : "records",
								autoLoad: true,
								url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
								listeners:{
									beforeload:function(treeload,node){
										this.baseParams["parentCode"] = node.attributes["data"];
									}
								}
							}),
							root : new Ext.tree.AsyncTreeNode({text:'自主报价类别',data:"2008"})
						})
					}]
				},{
					columnWidth:.5,
					layout : "form",
					defaults:{
						anchor:"100%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						fieldLabel : '公司名称',
						name : 'companyName'
					},{
						xtype:"combo",
    					mode:"local",
    					readOnly:true,
    					triggerAction:"all",
    					hiddenName:"membershipCode",
    					hiddenId:"membershipCode",
    					fieldLabel:"会员类型",
    					store:[
    					["10051000","普通会员"],
    					["10051001","再生通会员"],
    					["10051003","来电宝"],
    					["100510021000","银牌品牌通会员"],
    					["100510021001","金牌品牌通会员"],
    					["100510021002","钻石品牌通会员"]
    					]
					}]
				},{
					xtype:"tabpanel",
                    columnWidth:1,
                    activeTab:0,
                    border:true,
                    defaults:{
                        height:300
                    },
                    items:[
                           new Ext.Panel({
                               layout:"fit",
                               title:"产品描述",
                               items:[{
                                   xtype:"htmleditor",
                                   name : "instruction",
                                   id : "instruction",
                                   height:154,
                                   width:"100%",
                                   listeners:{
                                       "render":function(cmp){
                                           cmp.getToolbar().add('->',{
                                               tooltip:"如果用户没有填写产品描述，您可以从这里选择描述的内容帮助用户补全",
                                               iconCls:"item-open",
                                               cls : "x-btn-icon",
                                               handler:function(btn){
                                                   ast.ast1949.admin.products.DescriptionWin("10341000","details");
                                               }
                                           });
                                       }
                                   }
                               }]
                           }),
                           new Ext.Panel({
                               title:"审核",
                               layout:"fit",
                               tbar:[
                                   "->","如果审核不通过，您可以从这里选择退回原因","-",{
                                   text:"审核模板",
                                   iconCls:"item-open",
                                   handler:function(btn){
                                	   ast.ast1949.trust.buy.detail.DescriptionWin("10341001","checkReason");
                                   }
                               }],
                               items:[{
                                   xtype:"textarea",
                                   name : "checkReason",
                                   id : "checkReason",
                                   height:154,
                                   width:"100%"
                               }]
                           }),
                           new ast.ast1949.admin.productsPic.imageView({
                           	layout:"fit",
                               title:"报价文档"
                           }),
                    ]
				}],
			buttonAlign:"center",
			buttons:[{
				text:"保存并审核",
				iconCls:"item-true",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/jiage/jiage/modifiedOffer.htm?checkStatus=1",
							method:"post",
							type:"json",
							success:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存成功！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							},
							failure:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/jiage/jiage/modifiedOffer.htm",
							method:"post",
							type:"json",
							success:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存成功！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							},
							failure:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			},{
				text:"审核不通过",
				iconCls:"item-false",
				handler:function(btn){
					if(form.getForm().isValid()){
						//提交前的初始化工作
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/jiage/jiage/modifiedOffer.htm?checkStatus=2",
							method:"post",
							type:"json",
							success:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存成功！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							},
							failure:function(){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "保存失败！",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		ast.ast1949.trust.buy.detail.BuyDetailForm.superclass.constructor.call(this,c);
		
	},
	fields:ast.ast1949.trust.buy.detail.COMPANYFIELD,
	buyId:0,
	loadTrustBuy:function(){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+"/zz91/jiage/jiage/init.htm?offerId="+form.buyId, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						Ext.get("categoryName").dom.value=record.get("categoryName");
						Ext.get("category").dom.value=record.get("category");
					}
				}
			}
		});
	}
});
ast.ast1949.trust.buy.detail.DescriptionWin = function(code,name){
    var grid = new ast.ast1949.trust.buy.detail.descriptionGrid({
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
ast.ast1949.trust.buy.detail.descriptionGrid = Ext.extend(Ext.grid.GridPanel,{
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

        ast.ast1949.trust.buy.detail.descriptionGrid.superclass.constructor.call(this,c);
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


