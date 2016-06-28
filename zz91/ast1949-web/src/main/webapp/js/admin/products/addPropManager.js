Ext.namespace("ast.ast1949.admin.productsproperty");

var PROPERTIES = new function(){
    this.TEMPLATE_WIN = "templatewin";
    this.FORM_TAB="formtabpanel";
    this.ADDPROP_GRID="propertygrid";
    this.PROPERTY_FORM="propertyform";
    this.PROPERTY_WIN="propertywin";
}



ast.ast1949.admin.productsproperty.properyGrid = Ext.extend(Ext.grid.GridPanel,{
    constructor:function(config){
        config=config||{};
        Ext.apply(this,config);
        
        var _store = new Ext.data.JsonStore({
            root:"records",
            totalProperty: 'totalRecords',
            fields:[
                {name:"id",mapping:"id"},
                {name:"pid",mapping:"pid"},
                {name:"property",mapping:"property"},
                {name:"content",mapping:"content"},
                {name:"isDel",mapping:"isDel"},
                {name:"gmtCreated",mapping:"gmtCreated"},
                {name:"gmtModified",mapping:"gmtModified"} 
            ],
            url:Context.ROOT +Context.PATH+  "/admin/products/loadMoreProp.htm?id="+productId,
            autoLoad:true
        });
        
        var _sm=new Ext.grid.CheckboxSelectionModel();
        
        var grid = this;
        
        var _cm=new Ext.grid.ColumnModel( [_sm,{
            header : "编号",
            width : 50,
            sortable : true,
            dataIndex : "id",
            hidden:true
        },{
            header:"属性",
            dataIndex:"property",
            width:100,
            
        },{
            header:"内容",
            dataIndex:"content",
            width:100,
            
        },{
            header:"删除状态",
            dataIndex:"isDel",
            sortable : true,
            width:150,
            renderer:function(value, metadata, record, rowIndex, colIndex, store){
                var returnValue = value;
                if(value==0){
                    returnValue="未删除";
                }
                if(value==1){
                    returnValue="已删除";
                }
                return returnValue;
        
            }
        },{
            header:"生成时间",
            dataIndex:"gmtCreated",
            width:150,
            renderer:function(value, metadata, record, rowIndex, colIndex, store){
                var timeStr = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
                return timeStr;
            }
        },{//显示时间和联系的公司名称
            header:"修改时间",
            dataIndex:"gmtModified",
            width:150,
            renderer:function(value, metadata, record, rowIndex, colIndex, store){
                var modifyTime = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
                return modifyTime;
            }
        }]);
        
            
        var c={
                id : PROPERTIES.ADDPROP_GRID,
                loadMask:Context.LOADMASK,
                store:_store,
                sm:_sm,
                cm:_cm,
                tbar:[{
                    text : '编辑',
                    tooltip : '编辑',
                    iconCls : 'edit',
                    handler: function(){
                        var gird = Ext.getCmp(PROPERTIES.ADDPROP_GRID);
                        var record = grid.getSelectionModel().getSelections();
                        if (record.length == 0){
                            Ext.MessageBox.show({
                                title: Context.MSG_TITLE,
                                msg : "请至少选定一条记录",
                                buttons:Ext.MessageBox.OK,
                                icon:Ext.MessageBox.WARNING
                            });
                        }else if (record.length  > 1){
                            Ext.MessageBox.show({
                                title: Context.MSG_TITLE,
                                msg : "最多只能编辑一条记录",
                                buttons:Ext.MessageBox.OK,
                                icon:Ext.MessageBox.WARNING
                            });
                        } else {
                            record = grid.getSelectionModel().getSelected();
                            ast.ast1949.admin.productsproperty.editPropWin(record);
                        }
                        
                    }
                },"-",{
                    text : '删除',
                    tooltip : '删除',
                    iconCls:"item-false",
                    
                    handler:function(){
                    
                        var gird = Ext.getCmp(PROPERTIES.ADDPROP_GRID);
                        var rows = grid.getSelectionModel().getSelections();
                        if(rows.length == 0){
                            Ext.MessageBox.show({
                                title: Context.MSG_TITLE,
                                msg : "请至少选定一条记录",
                                buttons:Ext.MessageBox.OK,
                                icon:Ext.MessageBox.WARNING
                            });
                            return ;
                        }
                        for(var i=0;i<rows.length;i++){
                            Ext.Ajax.request({
                                url: Context.ROOT+Context.PATH+ "/admin/products/updatePropIsDel.htm?id="+rows[i].get("id"),
                                success:function(response,opt){
                                    var obj = Ext.decode(response.responseText);
                                    if(obj.success){
                                        Ext.MessageBox.show({
                                            title : Context.MSG_TITLE,
                                            msg : "信息删除成功！",
                                            buttons : Ext.MessageBox.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                       
                                        grid.getStore().reload();
                                    }else{
                                        Ext.MessageBox.show({
                                            title: Context.MSG_TITLE,
                                            msg : "信息删除失败！",
                                            buttons:Ext.MessageBox.OK,
                                            icon:Ext.MessageBox.WARNING
                                        });
                                    }
                                },
                                failure:function(response,opt){
                                    Ext.MessageBox.show({
                                        title: Context.MSG_TITLE,
                                        msg : "删除操作失败！",
                                        buttons:Ext.MessageBox.OK,
                                        icon:Ext.MessageBox.WARNING
                                    });
                                }
                            });
                        }
                    }
                },"-",{
                    text : '取消删除',
                    tooltip : '取消删除',
                    
                    handler:function(){
                    
                        var gird = Ext.getCmp(PROPERTIES.ADDPROP_GRID);
                        var rows = grid.getSelectionModel().getSelections();
                        if(rows.length == 0){
                            Ext.MessageBox.show({
                                title: Context.MSG_TITLE,
                                msg : "请至少选定一条记录",
                                buttons:Ext.MessageBox.OK,
                                icon:Ext.MessageBox.WARNING
                            });
                            return ;
                        }
                        for(var i=0;i<rows.length;i++){
                            Ext.Ajax.request({
                                url: Context.ROOT+Context.PATH+ "/admin/products/resetPropIsDel.htm?id="+rows[i].get("id"),
                                success:function(response,opt){
                                    var obj = Ext.decode(response.responseText);
                                    if(obj.success){
                                        Ext.MessageBox.show({
                                            title : Context.MSG_TITLE,
                                            msg : "信息取消删除成功！",
                                            buttons : Ext.MessageBox.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                       
                                        grid.getStore().reload();
                                    }else{
                                        Ext.MessageBox.show({
                                            title: Context.MSG_TITLE,
                                            msg : "信息取消删除失败！",
                                            buttons:Ext.MessageBox.OK,
                                            icon:Ext.MessageBox.WARNING
                                        });
                                    }
                                },
                                failure:function(response,opt){
                                    Ext.MessageBox.show({
                                        title: Context.MSG_TITLE,
                                        msg : "取消删除操作失败！",
                                        buttons:Ext.MessageBox.OK,
                                        icon:Ext.MessageBox.WARNING
                                    });
                                }
                            });
                        }
                    }
                }]
            };
        
        ast.ast1949.admin.productsproperty.properyGrid.superclass.constructor.call(this,c);
    },
    loadProp:function(){
        this.getStore().reload();
    }
});

ast.ast1949.admin.productsproperty.editPropertyForm = Ext.extend(Ext.form.FormPanel,{
    constructor:function(config){
        config = config||{};
        Ext.apply(this,config);
        
        var form=this;
        
        var c={
            region:"center",
            layout:"column",
            bodyStyle:'padding:5px 0 0',
            frame:true,
            labelAlign : "right",
            labelWidth : 90,
            items:[{
                columnWidth:1,
                layout:"form",
                defaults:{
                    anchor:"100%",
                    xtype:"textfield",
                    labelSeparator:""
                },
                items:[{
                    xtype:"hidden",
                    fieldLabel : "编号",
                    name : "id",
                    id:"id"
                },{
                    fieldLabel : "属性名称",
                    name : "property",
                    id : "property"
                },{
                    fieldLabel : "属性值",
                    name : "content",
                    id:"content"
                },{
                    xtype:"hidden",
                    fieldLabel : "pid",
                    name : "pid",
                    id:"pid"
                }]
            }],
            buttons:[{
                text:"保存",
                scope:this,
                handler:function(btn){
                    
                    var url=Context.ROOT +Context.PATH+  "/admin/products/updateOneOtherProp.htm";
                    if (this.getForm().isValid()) {
                        this.getForm().submit({
                            url : url,
                            method : "post",
                            type:"json",
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
                                    Ext.getCmp(PROPERTIES.PROPERTY_WIN).close();
                                    Ext.getCmp(PROPERTIES.ADDPROP_GRID).getStore().reload();
                                    
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
                    } else {
                        Ext.MessageBox.show({
                            title:MESSAGE.title,
                            msg : MESSAGE.submitFailure,
                            buttons:Ext.MessageBox.OK,
                            icon:Ext.MessageBox.ERROR
                        });
                    }
                    
                }
            },{
                text:"关闭",
                handler:function(btn){
                    Ext.getCmp(PROPERTIES.PROPERTY_WIN).close();
                }
            }]
        };
        
        ast.ast1949.admin.productsproperty.editPropertyForm.superclass.constructor.call(this,c);
    },
    loadPropRecord:function(record){
        var form = this;
        form.findById("id").setValue(record.get("id"));
        form.findById("property").setValue(record.get("property"));
        form.findById("content").setValue(record.get("content"));
        
    }
});

ast.ast1949.admin.productsproperty.editPropWin = function(record){
    var form = new ast.ast1949.admin.productsproperty.editPropertyForm({
        id:PROPERTIES.PROPERTY_FORM,
        region:"center"
    });
    
    form.loadPropRecord(record);
    
    var win = new Ext.Window({
            id:PROPERTIES.PROPERTY_WIN,
            title:"修改属性信息",
            width:300,
            autoHeight:true,
            modal:true,
            items:[form]
    });
    win.show();
}