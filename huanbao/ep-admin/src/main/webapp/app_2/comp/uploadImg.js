Ext.ns("com.zz91.ep.compImg");

var IMG = new function(){
	this.LOGO_GRID = "logogrid";
	this.BOOKLIST_GRID = "booklistgrid";
}

Ext.define("CompImgLogoModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"cid",mapping:"cid"},
		{name:"target_id",mapping:"targetId"},
		{name:"title",mapping:"title"},
		{name:"path",mapping:"path"},
		{name:"isDel",mapping:"isDel"},
		{name:"gmt_created",mapping:"gmtCreated"},
		{name:"gmt_modified",mapping:"gmtModified"}
	]
});

Ext.define("CompBookGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"cid",mapping:"cid"},
		{name:"file_name",mapping:"fileName"},
		{name:"check_status",mapping:"checkStatus"},
		{name:"check_person",mapping:"checkPerson"},
		{name:"picture",mapping:"picture"},
		{name:"gmt_created",mapping:"gmtCreated"},
		{name:"gmt_modified",mapping:"gmtModified"}
	]
});

Ext.define("com.zz91.ep.compImg.UploadForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			bodyPadding: 5,
			fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 80,
//		        msgTarget:"under",
		        labelSeparator:""
		    },
			items:[{
				xtype: 'container',
				anchor:"100%",
				layout:"column",
				items:[{
			    	xtype: 'container',
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
					items:[{
						fieldLabel : 'logo标题',
						name:"title",
						id:"title"
					},{
						fieldLabel : '证书名称',
						name:"fileName",
						id:"fileName"
					}]
				},{
			    	xtype: 'container',
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
					items:[{
						xtype:"hidden",
						fieldLabel : '目标Id',
						name:"targetId",
						id:"targetId"
					},{
						fieldLabel : '上传Logo',
						listeners:{
							"focus":function(field,e){
								var win=Ext.create("com.zz91.ep.util.UploadWin",{
									uploadUrl:Context.ROOT+"/comp/comp/uploadLogo.htm?title="+
									Ext.getCmp("title").getValue()+"&targetId="+Ext.getCmp("targetId").getValue(),
									callbackFn:function(form,action){
										field.setValue(action.result.data);
										Ext.getCmp(IMG.LOGO_GRID).getStore().load({params:{"targerId":Ext.get("tid").dom.value}});
										win.close();
									}
								});
								win.show();
							}
						}
					},{
						fieldLabel : '上传荣誉证书',
						listeners:{
							"focus":function(field,e){
								var win=Ext.create("com.zz91.ep.util.UploadWin",{
									uploadUrl:Context.ROOT+"/comp/comp/uploadCertificates.htm?title="+
									Ext.getCmp("fileName").getValue()+"&targetId="+Ext.getCmp("targetId").getValue(),
									callbackFn:function(form,action){
										field.setValue(action.result.data);
										Ext.getCmp(IMG.BOOKLIST_GRID).getStore().load({params:{"cid":Ext.get("tid").dom.value}});
										win.close();
									}
								});
								win.show();
							}
						}
					}]
				}]
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	loadTargertId:function(id){
		Ext.getCmp("targetId").setValue(id);
	}
});

Ext.define("com.zz91.ep.compImg.LogoGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CompImgLogoModel",
			remoteSort:false,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/comp/comp/queryLogo.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json'
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:false
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
		    	text:"编号",dataIndex:"id",hidden:true
			},{
		    	text:"cid",dataIndex:"cid",hidden:true
			},{
		    	text:"TargetID",dataIndex:"target_id",hidden:true
			},{
                header : "删除状态",
                dataIndex : "isDel",
                width : 100,
                sortable : true,
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
				header:"标题",dataIndex:"title",width:350,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if(record.get("path").length>0){
						return Ext.String.format("<img src='{0}' width='80' height='50' /><br />{1}",Context.IMG+Context.RESOURCES+record.get("path"),v);
					}
					return v;
				}
			},{
		    	text:"图片路径",dataIndex:"path",width:300
			},{
				text:"创建时间",dataIndex:"gmt_created", width:200,
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype: 'toolbar',
				dock:"top",
				items:[{
					xtype:"button",
					iconCls:"delete16",
					text:"删除",
					scope:this,
					handler:function(btn,e){
						this.deleteModel(this.getSelectionModel().getSelection());
					}
				},"-",{
                    xtype:"button",
                    text:"取消删除",
                    scope:this,
                    handler:function(btn,e){
                        this.cancelDeleteLogo(this.getSelectionModel().getSelection());
                    }
                }]
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	deleteModel:function(selections){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Msg.confirm(Context.MSG_TITLE,"您确定要删除所选文件？",function(o){
			if(o!="yes"){
				return ;
			}
			
			Ext.Array.each(selections, function(obj, idx, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/comp/comp/deleteLogo.htm",
					params: {
						id: obj.get("id")
						/*path: obj.get("path")*/
					},
					success: function(response){
						Ext.Msg.alert("提示","操作成功!");
						_this.getStore().load({params:{"targerId":Ext.get("tid").dom.value}});
					}
				});
			});
			
		});
	},
	cancelDeleteLogo:function(selections){
        if(selections.length<=0){
            return ;
        }
        var _this=this;
        Ext.Msg.confirm(Context.MSG_TITLE,"您确定要取消删除所选文件？",function(o){
            if(o!="yes"){
                return ;
            }
            
            Ext.Array.each(selections, function(obj, idx, countriesItSelf){
                Ext.Ajax.request({
                    url: Context.ROOT+"/comp/comp/cancelDeleteLogo.htm",
                    params: {
                        id: obj.get("id")
                       /* path: obj.get("path")*/
                    },
                    success: function(response){
                        Ext.Msg.alert("提示","操作成功!");
                        _this.getStore().load({params:{"targerId":Ext.get("tid").dom.value}});
                    }
                });
            });
            
        });
    },
	loadLogo:function(id){
		var _this=this;
		_this.getStore().load({params:{"targerId":id}});
	}
});


Ext.define("com.zz91.ep.compImg.ListGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CompBookGridModel",
			remoteSort:false,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/comp/comp/queryBookList.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
//		            root: 'records',
//		            totalProperty:"totals"
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:false
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
		    	text:"编号",dataIndex:"id",hidden:true
			},{
		    	text:"cid",dataIndex:"cid",hidden:true
			},{
				header:"标题",dataIndex:"file_name",width:350,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if(record.get("picture").length>0){
						return Ext.String.format("<img src='{0}' width='80' height='50' /><br />{1}",Context.IMG+Context.RESOURCES+record.get("picture"),v);
					}
					return v;
				}
			},{
				text:"审核状态",dataIndex:"check_status",sortable:true,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if(v=="0"){
						return "未审核";
					}
					if(v=="1"){
						return "审核通过";
					}
					if(v=="2"){
						return "审核不通过";
					}
				}
			},{
				header:"证书路径",dataIndex:"picture",width:350,sortable:true,
			},{
				header:"审核人",dataIndex:"check_person",sortable:true,
			},{
				text:"创建时间",dataIndex:"gmt_created", width:150,
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			},{
				text:"最后修改时间",dataIndex:"gmt_modified",width:150,
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype:"pagingtoolbar",
				dock:"bottom",
				store:_store,
				displayInfo:true
			},{
				xtype: 'toolbar',
				dock:"top",
				items:[{
					xtype:"button",
					iconCls:"add16",
					text:"审核通过",
					scope:this,
					handler:function(btn,e){
						this.checkStatus(this.getSelectionModel().getSelection(),1);
					}
				},{
					xtype:"button",
					iconCls:"edit16",
					text:"审核不通过",
					scope:this,
					handler:function(btn,e){
						this.checkStatus(this.getSelectionModel().getSelection(),2);
					}
				},{
					xtype:"button",
					iconCls:"delete16",
					text:"删除",
					scope:this,
					handler:function(btn,e){
						this.deleteModel(this.getSelectionModel().getSelection());
					}
				},'-',{
					xtype:"combo",
					emptyText:"审核状态",
					width:90,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["0","未审核"],["1","审核通过"],["2","审核不通过"]]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("check",nv);
							this.up("grid").getStore().setExtraParam("cid",Ext.get("tid").dom.value);
							this.up("grid").getStore().load();
						}
					}
				}]
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	deleteModel:function(selections){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Msg.confirm(Context.MSG_TITLE,"您确定要删除所选文件？",function(o){
			if(o!="yes"){
				return ;
			}
			
			Ext.Array.each(selections, function(obj, idx, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/comp/comp/deleteBookList.htm",
					params: {
						id: obj.get("id"),
						cid:obj.get("cid"),
						path:obj.get("picture")
					},
					success: function(response){
						Ext.Msg.alert("提示","操作成功!");
						_this.getStore().load({params:{"cid":Ext.get("tid").dom.value}});
					}
				});
			});
			
		});
	},
	checkStatus:function(selections,status){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Array.each(selections, function(obj, idx, countriesItSelf){
			Ext.Ajax.request({
				url: Context.ROOT+"/comp/comp/updateBookCheckStatus.htm",
				params: {
					id: obj.get("id"),
					cid: obj.get("cid"),
					status:status
				},
				success: function(response){
					Ext.Msg.alert("提示","操作成功!");
					_this.getStore().load({params:{"cid":Ext.get("tid").dom.value}});
				}
			});
		});
	},
	loadBook:function(id){
		var _this=this;
		_this.getStore().load({params:{"cid":id}});
	}
});