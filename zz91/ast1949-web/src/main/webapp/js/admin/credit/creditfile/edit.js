Ext.namespace("ast.ast1949.admin.credit.editfile");

/*var ATTEST = new function(){
	this.ATTEST_GRID = "attestgrid";
}*/
ast.ast1949.admin.credit.editfile.FILEFIELD=[
            {name:"id",mapping:"id"},
     	    {name:"companyId",mapping:"companyId"},
     	    {name:"fileName",mapping:"fileName"},
     	    {name:"organization",mapping:"organization"},
     	    {name:"startTime",mapping:"startTime"},
     	    {name:"endTime",mapping:"endTime"},
     	    {name:"picName",mapping:"picName"},
     	    {name:"checkStatus",mapping:"checkStatus"},
];

ast.ast1949.admin.credit.editfile.fileForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c = {
				autoScroll:true,
				frame:true,
				labelAlign:"right",
				labelWidth:90,
				layout:"column",
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
						xtype : "hidden",
						name : "companyId",
						id : "companyId"
					},{
						fieldLabel : "证书名称",
						name : "fileName",
						id:"fileName"
					},{
						fieldLabel : "发证机构",
						name : "organization",
						id:"organization"
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
						fieldLabel:"生效日期",
						xtype:"datefield",
						id:"startTimeStr",
						name:"startTime",
						format:"Y-m-d H:i:s"
					},{
						fieldLabel:"截至日期",
						xtype:"datefield",
						id:"endTimeStr",
						name:"endTime",
						format:"Y-m-d H:i:s"
					}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"htmleditor",
						name:"picName",
			             height : 500,
			             width:1000,
			             id : 'picUrl',
			             fieldLabel :'证书图片',
					}]
				}],
				buttonAlign:"center",
				buttons:[/*{
						id : "check",
					  text:"审核",
                      iconCls:"item-info",
                      layout:"fit"
				},*/{
	                id : "onlysave",
	                text : "保存",
	                scope:this,
	                handler:this.saveForm
	            },{
	                id : "saveandcheck",
	                iconCls:"item-true",
	                text : "保存并审核",
	                scope:this,
	                handler:function(btn){
	                	 this.saveForm();
	                    ast.ast1949.admin.credit.editfile.auditCheckStatus("1");
	                }
	            },{
	                id : "saveandrub",
	                iconCls:"item-false",
	                text : "审核不通过",
	                scope:this,
	                handler:function(btn){
	                	this.saveForm();
	                	ast.ast1949.admin.credit.editfile.auditCheckStatus("2");
	                }
	            }]
		};
		ast.ast1949.admin.credit.editfile.fileForm.superclass.constructor.call(this,c);
	},
	
	fields:ast.ast1949.admin.credit.editfile.FILEFIELD,
	loadCreditFile:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+Context.PATH+"/admin/credit/creditfile/init.htm?id=" +id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						if (record.get("picName")!=null && record.get("picName") !="") {
							form.findById("picUrl").setValue("<img src='http://img3.zz91.com/500x500/"+record.get('picName')+"'/>");
						}
						if (record.get("startTime")!=null){
							form.findById("startTimeStr").setValue(Ext.util.Format.date(new Date(record.get("startTime").time), 'Y-m-d H:i:s'));
						}
						if (record.get("endTime")!=null){
							form.findById("endTimeStr").setValue(Ext.util.Format.date(new Date(record.get("endTime").time), 'Y-m-d H:i:s'));
						}
						 // 初始化审核状态
						 var checktab=Ext.getCmp("AttestFileForm").getComponent(0);
						 var checkstatus="0";
                        checkstatus=record.get("checkStatus");
                        if(checkstatus=="1"){
                            checktab.setIconClass("item-true");
                        }
                        if(checkstatus=="2"){
                            checktab.setIconClass("item-false");
                        }
					}
				}
			}
		});
	},
saveUrl:Context.ROOT+Context.PATH+"/admin/credit/creditfile/updateFile.htm",
    saveForm:function(){
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
    }
})
	
ast.ast1949.admin.credit.editfile.auditCheckStatus = function(checkStatus){
    Ext.Ajax.request({
        url:Context.ROOT+Context.PATH+ "/admin/credit/creditfile/updateCheckStatus.htm?st="+ Math.random() +"&ids=" + Ext.getCmp("id").getValue()+"&cids=" + Ext.getCmp("companyId").getValue()+"&checkStatus=" + checkStatus,
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
            	 var checktab=Ext.getCmp("AttestFileForm").getComponent(0);
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
