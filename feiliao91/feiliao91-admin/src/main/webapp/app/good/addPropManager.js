Ext.namespace("ast.ast1949.admin.productsproperty");

var PROPERTIES = new function(){
    this.TEMPLATE_WIN = "templatewin";
    this.FORM_TAB="formtabpanel";
    this.ADDPROP_GRID="propertygrid";
    this.PROPERTY_FORM="propertyform";
    this.PROPERTY_WIN="propertywin";
}



ast.ast1949.admin.productsproperty.properyGrid = Ext.extend(Ext.grid.EditorGridPanel,{
    constructor:function(config){
        config=config||{};
        Ext.apply(this,config);
        
        var _store = new Ext.data.JsonStore({
            root:"records",
            totalProperty: 'totalRecords',
            fields:[
                {name:"gid",mapping:"gid"},
                {name:"property",mapping:"property"},
                {name:"content",mapping:"content"},
            ],
            url:Context.ROOT +Context.PATH+  "/admin/good/loadMoreProp.htm?id="+goodsId,
            autoLoad:true
        });
        
        var _sm=new Ext.grid.CheckboxSelectionModel();
        
        var grid = this;
        
        var _cm=new Ext.grid.ColumnModel( [_sm,{
            header : "编号",
            width : 50,
            sortable : true,
            dataIndex : "gid",
            hidden:true
        },{
            header:"属性",
            dataIndex:"property",
            width:100,
            sortable:true,
            editor:new Ext.form.TextField()
        },{
            header:"内容",
            dataIndex:"content",
            width:100,
            editor:new Ext.form.TextField()
        }]);
        
            
        var c={
                id : PROPERTIES.ADDPROP_GRID,
                loadMask:Context.LOADMASK,
                store:_store,
                clicksToEdit:2,
                sm:_sm,
                cm:_cm,
                tbar:[{
                    text : '双击表格可编辑',
                    tooltip : '双击表格可编辑',
                    iconCls : 'edit',
                    handler: function(){
//                        var gird = Ext.getCmp(PROPERTIES.ADDPROP_GRID);
//                        grid.getSelectionModel().selectAll();
//                        var record = grid.getSelectionModel().getSelections();
//                        ast.ast1949.admin.productsproperty.editMyPropWin(record);
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
                              url:Context.ROOT+Context.PATH+"/admin/good/delProp.htm",
                              params:{"property":rows[i].data.property,"content":rows[i].data.content,"goodsId":rows[i].data.gid},
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
            	  text : '添加属性',
                  tooltip : '添加属性',
                  iconCls : 'add',
                  handler: function(){
//                      return false;
                	  ast.ast1949.admin.productsproperty.addMyPropWin(goodsId);
                  }
              }
                ],
                listeners:{
                	"beforeedit":function(e){
                		if(this.isSystemProp(e.record.data.gid,e.value)){
                			Ext.MessageBox.show({
                                title:MESSAGE.title,
                                msg : "系统属性名不能更改",
                                buttons:Ext.MessageBox.OK,
                                icon:Ext.MessageBox.ERROR
                            });
                			return false;
                		}
                	},
    				"afteredit":function(e){
    					this.updateProp(e.record.data.gid,e.row,e.column,e.value);
    				}
    			}
            };
        ast.ast1949.admin.productsproperty.properyGrid.superclass.constructor.call(this,c);
    },
    loadProp:function(){
        this.getStore().reload();
    },
    updateProp:function(goodsId,row,column,value){
    	var grid=this;
		Ext.Ajax.request({
			url:Context.ROOT +Context.PATH+"/admin/good/updateProp.htm",
			params:{"goodsId":goodsId,"row":row,"column":column,"value":value},
			success:function(response,opt){
				grid.getStore().reload();
			},
			failure:function(response,opt){
			}
		});
    },
    isSystemProp:function(goodsId,value){
    	//判断是否为系统属性（系统属性不可编辑，用户自定义属性可编辑）
    	Ext.Ajax.request({
			url:Context.ROOT +Context.PATH+"/admin/good/isSystemProp.htm",
			params:{"goodsId":goodsId,"propName":value},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(!obj.success){
					Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
				}
			},
			failure:function(response,opt){
				Ext.MessageBox.show({
                    title : Context.MSG_TITLE,
                    msg : "发生错误,信息没有被保存",
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.ERROR
                });
			}
		});
    }
});

ast.ast1949.admin.productsproperty.addPropertyForm = Ext.extend(Ext.form.FormPanel,{
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
                    name : "gid",
                    id:"gid"
                },{
                    fieldLabel : "属性名称",
                    name : "property",
                    id : "property"
                },{
                    fieldLabel : "属性值",
                    name : "content",
                    id:"content"
                }]
            }],
            buttons:[{
                text:"保存",
                scope:this,
                handler:function(btn){
                    
                    var url=Context.ROOT +Context.PATH+  "/admin/good/addProp.htm";
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
        
        ast.ast1949.admin.productsproperty.addPropertyForm.superclass.constructor.call(this,c);
    },
    loadPropRecord:function(goodsId){
        var form = this;
        form.findById("gid").setValue(goodsId);
    }
});

ast.ast1949.admin.productsproperty.addMyPropWin = function(goodsId){
    var form = new ast.ast1949.admin.productsproperty.addPropertyForm({
        id:PROPERTIES.PROPERTY_FORM,
        region:"center"
    });
    form.loadPropRecord(goodsId);
    var win = new Ext.Window({
            id:PROPERTIES.PROPERTY_WIN,
            title:"添加属性信息",
            width:300,
            autoHeight:true,
            modal:true,
            items:[form]
    });
    win.show();
}

