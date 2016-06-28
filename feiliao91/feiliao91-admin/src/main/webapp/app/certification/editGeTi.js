Ext.namespace("ast.feiliao91.admin.certification.geti");

/*var ATTEST = new function(){
	this.ATTEST_GRID = "attestgrid";
}*/

ast.feiliao91.admin.certification.geti.ATTESTFIELD=[
//        {name:"id",mapping:"id"},
//    	{name:"attestType",mapping:"attestType"},
//    	{name:"companyName",mapping:"companyName"},
//    	{name:"companyAddr",mapping:"companyAddr"},
//    	{name:"industry",mapping:"industry"},
//    	{name:"serviceType",mapping:"serviceType"},
//    	{name:"applicant",mapping:"applicant"},
//    	{name:"idNumber",mapping:"idNumber"},
//    	{name:"showIdNumber",mapping:"showIdNumber"},
//    	{name:"contact",mapping:"contact"},
//    	{name:"sex",mapping:"sex"},
//    	{name:"tel",mapping:"tel"},
//    	{name:"mobile",mapping:"mobile"},
//    	{name:"business",mapping:"business"},
//    	{name:"checkStatus",mapping:"checkStatus"},
//    	{name:"gmtCreated",mapping:"gmtCreated"},
//    	{name:"gmtModified",mapping:"gmtModified"},
//    	{name:"picAddress",mapping:"picAddress"}
		{name:"id",mapping:"id"},
    	{name:"attestType",mapping:"attestType"},//认证类型
    	{name:"companyName",mapping:"companyName"},//公司名
    	{name:"companyAddress",mapping:"companyAddress"},//公司地址
    	{name:"idCard",mapping:"idCard"},//身份证
    	{name:"name",mapping:"name"},//联系人
    	{name:"maxsex",mapping:"maxsex"},//性别
    	{name:"code",mapping:"code"},// 电话处理
    	{name:"phone",mapping:"phone"},// 电话处理
    	{name:"codePhone",mapping:"codePhone"},// 电话处理
    	{name:"mobile",mapping:"mobile"},//手机
    	{name:"operation",mapping:"operation"},// 主营业务
    	{name:"serviceType",mapping:"serviceType"},// 服务类型
    	{name:"business",mapping:"business"},// 经营范围
    	{name:"applicant",mapping:"applicant"},// 申请人
    	{name:"picAddress",mapping:"picAddress"},// 图片
    	{name:"picAddressId",mapping:"picAddressId"},// 图片ID
    	{name:"checkStatus",mapping:"checkStatus"}
];

ast.feiliao91.admin.certification.geti.AttestForm=Ext.extend(Ext.form.FormPanel,{
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
						xtype:"combo",
						mode:"local",
						readOnly:true,
						/*triggerAction:"all",*/
						hiddenName:"attestType",
						hiddenId:"attestType",
						fieldLabel:"认证类型",
						store:[["1","工商管理注册"],["0","个体"]]
					},{
						fieldLabel : "公司名称",
						name : "companyName",
						id:"companyName"
					},{
						fieldLabel : "公司地址",
						name : "companyAddress",
						id:"companyAddress"
					},{
						fieldLabel : "主营行业",
						name : "operation",
						id:"operation"
					},{
						fieldLabel : "服务类型",
						name : "serviceType",
						id:"serviceType"
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
						id:"applicant",
						name:"applicant",
						fieldLabel:"申请人",
					},{
						xtype:"panel",
						style:'margin-left:30px;margin-bottom:5px',
						 labelSeparator:"",
						items:[{
							layout:"column",
							items:[{
								columnWidth: .2,
								html: "身份证号码"
								},{
								columnWidth:.5,
								style:'margin-left:-10%;',
								anchor:"100%",
								xtype:"textfield",
								labelSeparator:"",
								id:"idCard",
								name:"idCard"
							}]
						}]
				},{
					xtype:"panel",
					layout: 'form',
					style:'margin-left:30px;margin-bottom:5px',
					 labelSeparator:"",
					items:[{
						layout:"column",
						items:[{
							columnWidth: .2,
							style:'margin-left:25px;',
							html: "联系人"
							},{
							columnWidth:.5,
							style:'margin-left:-10%;',
							anchor:"100%",
							xtype:"textfield",
							labelSeparator:"",
							id:"name",
							name:"name"
						},{
							columnWidth:.2,
							anchor:"100%",
							style:'margin-right:-5px;margin-left:5px;',
							labelSeparator:"",
							xtype:"combo",
							name:"maxsex",
							hiddenName:"maxsex",
							mode:"local",
							triggerAction : "all",
							store:[[1,"女"],[0,"男"]]
						}]
					}]
			},{//code，phone，codePhone
					xtype:"panel",
					style:'margin-left:30px;margin-bottom:5px',
					labelSeparator:"",
					items:[{
						layout:"column",
						items:[{
									columnWidth: .2,
									style:'margin-left:25px;',
									html: "电话"
								},
								{
									columnWidth:.2,
									style:'margin-left:-10%;',
									anchor:"100%",
									xtype:"textfield",
									labelSeparator:"",
									id:"code",
									name:"code"
								},
								{
									columnWidth: .1,
									style:'margin-left:10px;',
									html: "-"
								},
								{
									columnWidth:.2,
									//style:'margin-left:10%;',
									anchor:"100%",
									xtype:"textfield",
									labelSeparator:"",
									id:"phone",
									name:"phone"
								},
								{
									columnWidth: .1,
									style:'margin-left:10px;',
									html: "-"
								},
								{
									columnWidth:.3,
									//style:'margin-left:10%;',
									anchor:"100%",
									xtype:"textfield",
									labelSeparator:"",
									id:"codePhone",
									name:"codePhone"
								}
							]
					}]
					},{
						id:"mobile",
						name:"mobile",
						fieldLabel:"手机",
						}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"textarea",
						fieldLabel : "经营范围",
						name : "business",
						labelSeparator:"",
						height:200,
						anchor : "99%"
					}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"htmleditor",
						name:"picAddress",
			            height : 400,
			            anchor : "99%",
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
	                	 ast.feiliao91.admin.certification.geti.auditCheckStatus(2);
	                }
	            },{
	                id : "saveandrub",
	                iconCls:"item-false",
	                text : "审核不通过",
	                scope:this,
	                handler:function(btn){
	                	ast.feiliao91.admin.certification.geti.auditCheckStatus(3);
	                }
	            }]
		};
		ast.feiliao91.admin.certification.geti.AttestForm.superclass.constructor.call(this,c);
	},
	
	fields:ast.feiliao91.admin.certification.geti.ATTESTFIELD,
	loadGeTiAttest:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+Context.PATH+"/admin/certification/initGeTi.htm?id=" +id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						if (record.get("picAddress")!=null && record.get("picAddress") !="") {
							var val= "";
							var head = "<img src=\"http://img1.taozaisheng.com";
							var srcArray= new Array();
							var srcIdArray= new Array();
							var allPic=record.get("picAddress");
							var allPicId=record.get("picAddressId");
							srcArray=allPic.split(",");
							srcIdArray=allPicId.split(",");
							for(i=0;i<srcArray.length;i++){
								val=val+head+srcArray[i]+"\" "+"picaddressid=\""+srcIdArray[i]+"\" />";
							}
							form.findById("picUrl").setValue(val);
						}
						// 初始化审核状态
						var checktab=Ext.getCmp("AttestGeTiForm").getComponent(0);
						var checkstatus="0";
						checkstatus=record.get("checkStatus");
						if(checkstatus=="0"){
							checktab.setIconClass("item-info");
						}
						if(checkstatus=="1"){
							checktab.setIconClass("item-warning");
						}
						if(checkstatus=="2"){
							checktab.setIconClass("item-true");
						}
						if(checkstatus=="3"){
							checktab.setIconClass("item-false");
						}
					}
				}
			}
		});
	},
	saveUrl:Context.ROOT+Context.PATH+"/admin/certification/updateGeTi.htm",
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
ast.feiliao91.admin.certification.geti.auditCheckStatus = function(checkStatus){
	var _ids = new Array();
	_ids.push(Ext.getCmp("id").getValue());
    Ext.Ajax.request({
        url:Context.ROOT+Context.PATH+ "/admin/certification/updateStatus.htm",
        params: {
			ids: _ids.join(","),
			checkStatus:checkStatus
		},
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
            	var checktab=Ext.getCmp("AttestGeTiForm").getComponent(0);
            	if(checkStatus==2){
					checktab.setIconClass("item-true");
				}
				if(checkStatus==3){
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

