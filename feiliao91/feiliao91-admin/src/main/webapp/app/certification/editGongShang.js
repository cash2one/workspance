Ext.namespace("ast.feiliao91.admin.certification.gongshang");

/*var ATTEST = new function(){
	this.ATTEST_GRID = "attestgrid";
}*/
ast.feiliao91.admin.certification.gongshang.ATTESTFIELD=[
        {name:"id",mapping:"id"},
    	{name:"attestType",mapping:"attestType"},
    	{name:"companyName",mapping:"companyName"},
    	{name:"registerNum",mapping:"registerNum"},
    	{name:"registerAddress",mapping:"registerAddress"},
    	{name:"registerCapital",mapping:"registerCapital"},
    	{name:"legal",mapping:"legal"},
    	{name:"serviceType",mapping:"serviceType"},
    	{name:"industry",mapping:"industry"},
    	{name:"business",mapping:"business"},
    	{name:"organization",mapping:"organization"},
    	{name:"applicant",mapping:"applicant"},
    	{name:"checkStatus",mapping:"checkStatus"},
    	{name:"establishTimeStr",mapping:"establishTimeStr"},
    	{name:"startTimeStr",mapping:"startTimeStr"},
    	{name:"endTimeStr",mapping:"endTimeStr"},
    	{name:"inspectionTimeStr",mapping:"inspectionTimeStr"},
    	{name:"picAddress",mapping:"picAddress"},
    	{name:"picAddressId",mapping:"picAddressId"}// 图片ID
];

ast.feiliao91.admin.certification.gongshang.AttestForm=Ext.extend(Ext.form.FormPanel,{
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
						id:"registerAddress",
						name:"registerAddress",
						fieldLabel:"注册地址"
					},{
						id:"serviceType",
						name:"serviceType",
						fieldLabel:"公司类型"
					},{
						xtype:"datefield",
						fieldLabel:"成立时间",
						id:"establishTime",
						name:"establishTimeStr",
						format:"Y-m-d"
					},{
						xtype:"panel",
						layout: 'form',
						style:'margin-left:10px;margin-bottom:5px',
						 labelSeparator:"",
						items:[{
							layout:"column",
							items:[{
								columnWidth: .2,
								style:'margin-left:25px;',
								html: "营业期限"
								},{
								columnWidth:.3,
								/*style:'margin-left:-20%;',*/
								anchor:"100%",
								labelSeparator:"",
								xtype:"datefield",
								id:"startTime",
								name:"startTimeStr",
								format:"Y-m-d"
							},{
								columnWidth: .1,
								style:'margin-left:25px;',
								html: "至"
								},{
								columnWidth:.3,
								anchor:"100%",
								style:'margin-right:-5px;margin-left:5px;',
								labelSeparator:"",
								xtype:"datefield",
								id:"endTime",
								name:"endTimeStr",
								format:"Y-m-d "
							}]
						}]
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
								html: "注册号"
								},{
								columnWidth:.5,
								style:'margin-left:-10%;',
								anchor:"100%",
								xtype:"textfield",
								labelSeparator:"",
								id:"registerNum",
								name:"registerNum"
							}]
						}]
					},{
						xtype:"panel",
						style:'margin-left:30px;margin-bottom:5px',
						 labelSeparator:"",
						items:[{
							layout:"column",
							items:[{
								columnWidth: .2,
								html: "法定代表人"
								},{
								columnWidth:.5,
								style:'margin-left:-10%;',
								anchor:"100%",
								xtype:"textfield",
								labelSeparator:"",
								id:"legal",
								name:"legal"
							}]
						}]
				},{
					xtype:"panel",
					style:'margin-left:30px;margin-bottom:5px',
					 labelSeparator:"",
					items:[{
						layout:"column",
						items:[{
							columnWidth: .2,
							html: "注册资本"
							},{
							columnWidth:.5,
							style:'margin-left:-10%;',
							anchor:"100%",
							xtype:"textfield",
							labelSeparator:"",
							id:"registerCapital",
							name:"registerCapital"
						}]
					}]
				},{
					xtype:"panel",
					style:'margin-left:30px;margin-bottom:5px',
					 labelSeparator:"",
					items:[{
						layout:"column",
						items:[{
							columnWidth: .2,
							html: " 登记机关"
							},{
							columnWidth:.5,
							style:'margin-left:-10%;',
							anchor:"100%",
							xtype:"textfield",
							labelSeparator:"",
							id:"organization",
							name:"organization"
						}]
					}]
				},{
						xtype:"panel",
						style:'margin-left:30px;margin-bottom:5px',
						 labelSeparator:"",
						items:[{
							layout:"column",
							items:[{
								columnWidth: .1,
								html: " 年检时间"
								},{
								columnWidth:.3,
								/*style:'margin-left:-2%;',*/
								anchor:"100%",
								labelSeparator:"",
								xtype:"datefield",
								id:"inspectionTime",
								name:"inspectionTimeStr",
								format:"Y-m-d"
							}]
						}]
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
	                	ast.feiliao91.admin.certification.gongshang.auditCheckStatus(2);
	                }
	            },{
	                id : "saveandrub",
	                iconCls:"item-false",
	                text : "审核不通过",
	                scope:this,
	                handler:function(btn){
	                	this.saveForm();
	                	ast.feiliao91.admin.certification.gongshang.auditCheckStatus(3);
	                }
	            }]
		};
		ast.feiliao91.admin.certification.gongshang.AttestForm.superclass.constructor.call(this,c);
	},
	
	fields:ast.feiliao91.admin.certification.gongshang.ATTESTFIELD,
	loadGongShangAttest:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : form.fields,
			url : Context.ROOT+Context.PATH+"/admin/certification/initGongShang.htm?id=" +id, 
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
//								val=val+head+srcArray[i]+"'/>";
							}
							form.findById("picUrl").setValue(val);
						}
						
						if (record.get("establishTimeStr")!=null){
							form.findById("establishTime").setValue(Ext.util.Format.date(new Date(record.get("establishTimeStr")), 'Y-m-d'));
						}
						// 初始化审核状态
						var checktab=Ext.getCmp("AttestGongShangForm").getComponent(0);
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
	saveUrl:Context.ROOT+Context.PATH+"/admin/certification/updateGongShang.htm",
    saveForm:function(){
        var _url = this.saveUrl;
        if (this.getForm().isValid()) {
            this.getForm().submit({
                url : _url,
                method : "post",
               /* params:{"inquiryId":this.inquiryData.id},*/
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
ast.feiliao91.admin.certification.gongshang.auditCheckStatus = function(checkStatus){
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
            	var checktab=Ext.getCmp("AttestGongShangForm").getComponent(0);
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
