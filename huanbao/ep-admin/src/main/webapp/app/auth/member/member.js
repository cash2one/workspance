Ext.namespace("com.zz91.ep.member");

var MEMBER=new function(){
	this.EDITWIN="righteditwin";
	this.TREE="membertree";
}

/**
 * 修改会员类型所对应的权限
 */
com.zz91.ep.member.UpdateMemberRight=function(memberCode, rightId, checked){
	Ext.Ajax.request({
        url:Context.ROOT+"/auth/crmmember/updateCrmMemberRight.htm",
        params:{
            "memberCode":memberCode,
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
 * 增加会员类型的窗口
 */
com.zz91.ep.member.AddFormWin=function(){
	var form = new com.zz91.ep.member.FormPanel();
    form.saveUrl = Context.ROOT+"/auth/crmmember/createCrmMember.htm";
    form.initParentForAdd();
    
    var win = new Ext.Window({
        id:MEMBER.EDITWIN,
        title:"添加会员类型信息",
        width:380,
        autoHeight:true,
        modal:true,
        items:[form]
    });
    win.show();
}

/**
 * 编辑会员类型的窗口
 */
com.zz91.ep.member.EditFormWin=function(id){
	var tree = Ext.getCmp(MEMBER.TREE);
    var node = tree.getSelectionModel().getSelectedNode();
    if(tree.getRootNode() == node){
            return false;
    }
    var form = new com.zz91.ep.member.FormPanel();
    form.saveUrl = Context.ROOT+"/auth/crmmember/updateCrmMember.htm";
    form.initParentForUpdate();
    form.loadMember();
    var win = new Ext.Window({
        id:MEMBER.EDITWIN,
        title:"修改会员类型信息",
        width:380,
        autoHeight:true,
        modal:true,
        items:[form]
    });
    win.show();
}

/**
 * 会员类型表单面板
 */
com.zz91.ep.member.FormPanel=Ext.extend(Ext.form.FormPanel,{
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
                fieldLabel:"上级会员类型"
            },{
                id:"code",
                name:"code",
                readOnly:true,
                fieldLabel:"会员类型code"
            },{
                id:"name",
                name:"name",
                fieldLabel:"会员类型名称",
                itemCls:"required",
                allowBlank:false
            },{
                xtype:"textarea",
                id:"details",
                name:"details",
                fieldLabel:"详细信息"
            }
            ],
            buttons:[{
                text:"保存",
                handler:this.saveForm,
                scope:this
            },{
                text:"关闭",
                handler:function(){
                    Ext.getCmp(MEMBER.EDITWIN).close();
                }
            }]
	    };
	    com.zz91.ep.member.FormPanel.superclass.constructor.call(this,c);
	},
	/*为添加会员类型做初始化*/
	initParentForAdd:function(){
	    var tree = Ext.getCmp(MEMBER.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parentCode").setValue(node.attributes.data);
	    this.findById("parent").setValue(node.text);
	},
	/*为编辑会员类型做初始化*/
	initParentForUpdate:function(){
	    var tree = Ext.getCmp(MEMBER.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parent").setValue(node.parentNode.text);
	},
	saveUrl:Context.ROOT+"/auth/crmmember/createCrmMember.htm",
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
	/*表单提交成功提示信息*/
	onSaveSuccess:function(_form,_action){
	    var tree = Ext.getCmp(MEMBER.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    if(this.findById("id").getValue() > 0){
            node.setText(this.findById("name").getValue()); 
	    }else{
            node.leaf= false;
            tree.getLoader().load(node,function(){
                node.expand();
            });
	    }
	    Ext.getCmp(MEMBER.EDITWIN).close();
	},
	/*表单提交失败提示信息*/
	onSaveFailure:function(_form,_action){
	    Ext.MessageBox.show({
            title:MESSAGE.title,
            msg : MESSAGE.saveFailure,
            buttons:Ext.MessageBox.OK,
            icon:Ext.MessageBox.ERROR
	    });
	},
	/*为表单编辑载入会员类型信息*/
	loadMember:function(){
	    var tree = Ext.getCmp(MEMBER.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    
	    var _fields = ["id","name","code","details"];
	    var form = this;
	    var _store = new Ext.data.JsonStore({
            root : "records",
            fields : _fields,
            url : Context.ROOT + "/auth/crmmember/queryOneCrmMember.htm?memberCode="+node.attributes.data,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    if (record == null) {
                        Ext.MessageBox.alert(MESSAGE.title,MESSAGE.loadError);
                        Ext.getCmp(MEMBER.EDITWIN).close();
                    } else {
                        form.getForm().loadRecord(record);
                    }
                }
            }
	    });
	    
	}

}); 