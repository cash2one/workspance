Ext.namespace("com.zz91.ep.svr");

var SVR=new function(){
	this.EDITWIN="righteditwin";
	this.TREE="svrtree";
}

/**
 * 更新服务对应的权限信息
 */
com.zz91.ep.svr.UpdateSvrRight=function(crmSvrId, rightId, checked){
	Ext.Ajax.request({
        url:Context.ROOT+"/auth/crmsvr/updateCrmSvrRight.htm",
        params:{
            "crmSvrId":crmSvrId,
            "crmRightId":rightId,
            "checked":checked
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
                com.zz91.ep.utils.Msg(MESSAGE.picTitleInfo,MESSAGE.saveSuccess);
            }else{
                Ext.MessageBox.show({
                    title:MESSAGE.title,
                    msg : MESSAGE.saveFailure,
                    buttons:Ext.MessageBox.OK,
                    icon:Ext.MessageBox.ERROR
                });
            }
        },
        failure:function(response,opt){
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.submitFailure,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
        }
    });
}

/**
 * 添加服务的窗口
 */
com.zz91.ep.svr.AddFormWin=function(){
	var form = new com.zz91.ep.svr.FormPanel();
    form.saveUrl = Context.ROOT+"/auth/crmsvr/createCrmSvr.htm";
    form.initParentForAdd();
    
    var win = new Ext.Window({
        id:SVR.EDITWIN,
        title:"添加服务类型信息",
        width:380,
        autoHeight:true,
        modal:true,
        items:[form]
    });
    win.show();
}

/**
 * 编辑服务的窗口
 */
com.zz91.ep.svr.EditFormWin=function(id){
	var tree = Ext.getCmp(SVR.TREE);
    var node = tree.getSelectionModel().getSelectedNode();
    if(tree.getRootNode() == node){
            return false;
    }
    var form = new com.zz91.ep.svr.FormPanel();
    form.saveUrl = Context.ROOT+"/auth/crmsvr/updateCrmSvr.htm";
    form.initParentForUpdate();
    form.loadSvr();
    var win = new Ext.Window({
        id:SVR.EDITWIN,
        title:"修改服务类型信息",
        width:380,
        autoHeight:true,
        modal:true,
        items:[form]
    });
    win.show();
}

/**
 * 服务表单面板
 */
com.zz91.ep.svr.FormPanel=Ext.extend(Ext.form.FormPanel,{
    constructor:function(config){
	    config = config || {};
	    Ext.apply(this,config);
	    
	    var c = {
            labelAlign : "right",
            labelWidth : 80,
            region:"center",
            layout:"form",
            bodyStyle:'padding:5px 0 0',
            frame:true,
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
                id:"parentCode",
                name:"parentCode"
            },{
                id:"parent",
                readOnly:true,
                fieldLabel:"上级服务类型"
            },{
                id:"code",
                name:"code",
                readOnly:true,
                fieldLabel:"服务类型code"
            },{
                id:"name",
                name:"name",
                fieldLabel:"服务类型名称",
                itemCls:"required",
                allowBlank:false
            },{
                id:"unitPrice",
                name:"unitPrice",
                fieldLabel:"服务单价",
                itemCls:"required",
                allowBlank:false
            },{
                id:"units",
                name:"units",
                fieldLabel:"服务单价单位",
                itemCls:"required",
                allowBlank:false
            },{
                xtype:"textarea",
                id:"details",
                name:"details",
                fieldLabel:"服务详细信息"
            }
            ],
            buttons:[{
                text:"保存",
                handler:this.saveForm,
                scope:this
            },{
                text:"关闭",
                handler:function(){
                    Ext.getCmp(SVR.EDITWIN).close();
                }
            }]
	    };
	    com.zz91.ep.svr.FormPanel.superclass.constructor.call(this,c);
	},
	/*为添加服务类型做初始化*/
	initParentForAdd:function(){
	    var tree = Ext.getCmp(SVR.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parentCode").setValue(node.attributes.data);
	    this.findById("parent").setValue(node.text);
	},
	/*为修改服务类型做初始化*/
	initParentForUpdate:function(){
	    var tree = Ext.getCmp(SVR.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parent").setValue(node.parentNode.text);
	},
	saveUrl:Context.ROOT+"/auth/crmsvr/createCrmSvr.htm",
	/*表单提交*/
	saveForm:function(btn){
	    if(this.getForm().isValid()){
            this.getForm().submit({
                url:this.saveUrl,
                method:"post",
                type:"json",
                success:this.onSaveSuccess,
                failure:this.onSaveFailure,
                scope:this
            });
	    }else{
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.unValidate,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
	    }
	},
	/*提交成功提示信息*/
	onSaveSuccess:function(_form,_action){
	    var tree = Ext.getCmp(SVR.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    if(this.findById("id").getValue() > 0){
            node.setText(this.findById("name").getValue()); 
	    }else{
            node.leaf= false;
            tree.getLoader().load(node,function(){
                node.expand();
            });
	    }
	    Ext.getCmp(SVR.EDITWIN).close();
	},
	/*提交失败提示信息*/
	onSaveFailure:function(_form,_action){
	    Ext.MessageBox.show({
            title:MESSAGE.title,
            msg : MESSAGE.saveFailure,
            buttons:Ext.MessageBox.OK,
            icon:Ext.MessageBox.ERROR
	    });
	},
	/*为修改载入服务信息*/
	loadSvr:function(){
	    var tree = Ext.getCmp(SVR.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    
	    var _fields = ["id","name","code","unitPrice","units","details"];
	    var form = this;
	    var _store = new Ext.data.JsonStore({
            root : "records",
            fields : _fields,
            url : Context.ROOT + "/auth/crmsvr/queryOneCrmSvr.htm?code="+node.attributes.data,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    if (record == null) {
                        Ext.MessageBox.alert(MESSAGE.title,MESSAGE.loadError);
                        Ext.getCmp(SVR.EDITWIN).close();
                    } else {
                        form.getForm().loadRecord(record);
                    }
                }
            }
	    });
	    
	}

}); 