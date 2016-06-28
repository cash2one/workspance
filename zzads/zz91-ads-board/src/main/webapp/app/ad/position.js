Ext.namespace("com.zz91.ads.board.ad.position");

/**
 * 广告位类别树
 * @class com.zz91.ads.board.ad.position.TreePanel
 * @extends Ext.tree.TreePanel
 */
com.zz91.ads.board.ad.position.TreePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var treeLoad = new Ext.tree.TreeLoader({
			url:Context.ROOT + "/ad/position/child.htm",
			listeners:{
				beforeload:function(treeload,node){
					this.baseParams["id"] = node.attributes["data"];
				}
			}
		});
		
		var c={
			rootVisible:true,
			autoScroll:true,
			animate:true,
			split:true,
			root:{
				expanded:true,
				nodeType:"async",
				text:"所有位置",
				data:0
			},
			loader:treeLoad,
			tbar:[{
				text:"全部展开",
				scope:this,
				handler:function(btn){
					this.expandAll();
				}
			},{
				text:"全部折叠",
				scope:this,
				handler:function(btn){
					this.collapseAll();
				}
			}],
			contextMenu:this.contextmenu,
			listeners:{
				contextmenu:function(node,e){
					node.select();
					var c = node.getOwnerTree().contextMenu;
					if(c!=null){
						c.contextNode = node;
						c.showAt(e.getXY());
					}
				}
			}
		};
		com.zz91.ads.board.ad.position.TreePanel.superclass.constructor.call(this,c);
	},
	contextmenu:new Ext.menu.Menu({
		items:[{
			id:"cm-add",
			cls:"add16",
			text:"增加广告位",
			handler:function(btn){
				var _id=btn.parentMenu.contextNode.attributes.data;
				var _text=btn.parentMenu.contextNode.attributes.text;
				com.zz91.ads.board.ad.position.AddWin(_id,_text);
			}
		},{
            id: "cm-edit",
            cls:"edit16",
            text: "修改广告位",
            handler:function(btn){
            	if(btn.parentMenu.contextNode.attributes.data>0){
            		com.zz91.ads.board.ad.position.EditWin(
            			btn.parentMenu.contextNode.attributes.data
            		);
            	} else {
            		com.zz91.ads.board.utils.Msg("","此节点不可编辑！");
            	}
            }
        },{
        	id: "cm-del",
            cls:"delete16",
            text: "删除广告位",
            handler:function(btn){
        		if(btn.parentMenu.contextNode.attributes.data>0){
        			// 删除指定类别信息
        			var tree = Ext.getCmp(POSITION.TREE);
        			var node = tree.getSelectionModel().getSelectedNode();
        			Ext.MessageBox.confirm("系统提示","您确定要删除该分类及其子分类吗?", function(btn){
						if(btn != "yes"){
							return false;
						}
						Ext.Ajax.request({
							url:Context.ROOT + "/ad/position/delete.htm?st="+timestamp(),
							params:{"id":node.attributes.data},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									node.remove();
								}else{
									com.zz91.ads.board.utils.Msg("","类别没有被删除，可能服务器发生了一些错误！");
								}
							},
							failure:function(response,opt){
								com.zz91.ads.board.utils.Msg("","类别没有被删除，可能网络发生了一些错误！");
							}
						});
        			});
        		}
        	}
        },{
        	id: "cm-online-code",
        	cls:"network16",
        	text:"上线代码",
        	handler:function(btn){
        		if(btn.parentMenu.contextNode.attributes.data>0){
        			var tree = Ext.getCmp(POSITION.TREE);
        			var node = tree.getSelectionModel().getSelectedNode();
		        	Ext.Ajax.request({
		    			url:Context.ROOT+"/ad/check/buildOnlineCode.htm",
		    			params:{"pid":node.attributes.data},
		    			success:function(response,opt){
		    				var obj = Ext.decode(response.responseText);
		    				var win = new Ext.Window({
			    				title:"广告上线代码",
			    				width:480,
			    				autoHeight:true,
			    				modal:true, 
			    				items:[{
			    					xtype:"form",
			    					frame:true,
			    					layout:"fit",
			    					height:200,
			    					items:[{
			    						anchor:"99%",
			    						xtype:"textarea",
			    						value:obj.data
			    					}]
			    				}],
			    				buttons:[{
			    					text:"关闭",
			    					handler:function(btn){
			    						win.close();
			    					}
			    				}]
			    			});
			    			win.show();
		    				//Ext.MessageBox.alert("广告上线代码",obj.data);
		    			},
		    			failure:function(response,opt){
		    			}
		    		});
        		}
        }
        }]
	})
});

/**
 * 编辑表单
 * @class com.zz91.ads.board.ad.position.EditForm
 * @extends Ext.form.FormPanel
 */
com.zz91.ads.board.ad.position.EditForm = Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,c);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
						{
							xtype:"hidden",
							id:"id",
							name:"id"
						},{
							xtype:"hidden",
							name:"deleted",
							value:"N"
						},{
							id:"name",
							name:"name",
							allowBlank:false,
							itemCls:"required",
							fieldLabel:"广告位名称"
						},{
							name:"requestUrl",
							fieldLabel:"广告位所在的URL",
							allowBlank:true
						},{
							xtype:"numberfield",
							name:"sequence",
							fieldLabel:"排序",
							value:"0",
							allowBlank:false
						},{
							xtype:"numberfield",
							name:"width",
							fieldLabel:"width",
							value:"0"
						},{
							xtype:"numberfield",
							name:"maxAd",
							fieldLabel:"最大广告数",
							value:"1",
							allowBlank:false
						}
					]
				},{
					columnWidth:0.5,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id : "parentId",
						name : "parentId"
					},{
						readOnly:true,
						fieldLabel:"上级广告位",
						id : "parentName",
						name : "parentName"
					},{
						xtype:          "combo",
                        mode:           "local",
                        triggerAction:  "all",
                        forceSelection: true,
                        allowBlank:false,
                        editable:       false,
                        itemCls:"required",
                        fieldLabel:     "投放（显示）方式",
                        hiddenName:"deliveryStyleId",
                        displayField:   "name",
                        valueField:     "id",
                        store:new Ext.data.JsonStore({
							autoLoad	: true,
							root		: "records",
							url			: Context.ROOT + "/ad/position/queryDeliveryStyle.htm?st="+timestamp(),
							fields		: ["name","id"]
						})
					},{
						xtype:          "combo",
	                    mode:           "local",
	                    triggerAction:  "all",
	                    forceSelection: true,
	                    editable:       false,
	                    fieldLabel:     "付费类型",
	                    hiddenName:     "paymentType",
	                    displayField:   "name",
	                    valueField:     "value",
	                    store:          new Ext.data.JsonStore({
	                        fields : ["name", "value"],
	                        data   : [
	                            {name : POSITION.PAYMENT_TYPE[0],   value: "0"},
	                            {name : POSITION.PAYMENT_TYPE[1],   value: "1"},
	                            {name : POSITION.PAYMENT_TYPE[2],   value: "2"},
	                            {name : POSITION.PAYMENT_TYPE[3],   value: "3"}
	                        ]
	                    })
					},{
						xtype:"numberfield",
						name:"height",
						fieldLabel:"height",
						value:"0",
						allowBlank:false
					},{
						xtype:"checkbox",
						fieldLabel:"是否精确投放",
						name:"hasExactAd",
						inputValue:"1"
					}]
				}
			],
			buttons:[{
				text:"保存",
				scope:this,
				handler:this.save
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(POSITION.EDIT_WIN).close();
				}
			}]
		};
		
		com.zz91.ads.board.ad.position.EditForm.superclass.constructor.call(this,c);
	},
	reload:function(id){
		var _fields=[
			{name:"id",mapping:"adPosition.id"},
			{name:"name",mapping:"adPosition.name"},
			{name:"parentId",mapping:"adPosition.parentId"},
			{name:"requestUrl",mapping:"adPosition.requestUrl"},
			{name:"deliveryStyleId",mapping:"adPosition.deliveryStyleId"},
			{name:"sequence",mapping:"adPosition.sequence"},
			{name:"paymentType",mapping:"adPosition.paymentType"},
			{name:"width",mapping:"adPosition.width"},
			{name:"height",mapping:"adPosition.height"},
			{name:"maxAd",mapping:"adPosition.maxAd"},
			{name:"hasExactAd",mapping:"adPosition.hasExactAd"},
			"parentName"
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT + "/ad/position/queryById.htm?st="+timestamp(),
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						//设置父类别
						var _pid=record.get("parentId");
						var _pname=record.get("parentName");
						
						if(_pid!=null){
							if(_pid>0){
								Ext.get("combo-parent").dom.value=_pname;
							}
							Ext.get("parentId").dom.value=_pid;
							
						}
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT + "/ad/position/add.htm?st="+timestamp(),
	//保存
	save:function(){
		var form=this;
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:function (_form, _action){
					//Ext.getCmp(POSITION.TREE).expandAll();
					
					var tree = Ext.getCmp(POSITION.TREE);
				    var node = tree.getSelectionModel().getSelectedNode();
				    if(form.findById("id").getValue() > 0){
			            node.setText(form.findById("name").getValue()); 
				    }else{
			            node.leaf= false;
			            tree.getLoader().load(node,function(){
			                node.expand();
			            });
				    }
				    
				    Ext.getCmp(POSITION.EDIT_WIN).close()
				    com.zz91.ads.board.utils.Msg(Context.MSG_TITLE,"保存成功！");
				},
				failure:function (form, action){
					com.zz91.ads.board.utils.Msg(Context.MSG_TITLE,"保存失败！");
				}
			});
		}else{
			com.zz91.ads.board.utils.Msg(Context.MSG_TITLE,"请仔细检查是否已经填好了！");
		}
	},
	initAddPosition:function(id,text){
		this.findById("parentId").setValue(id);
		this.findById("parentName").setValue(text);
	}
});

/**
 * 添加
 * @param {} id
 * @param {} text
 */
com.zz91.ads.board.ad.position.AddWin = function(id,text){
	var form = new com.zz91.ads.board.ad.position.EditForm();
	
	form.saveUrl = Context.ROOT + "/ad/position/add.htm";
	form.initAddPosition(id,text);
	
	var win = new Ext.Window({
		id:POSITION.EDIT_WIN,
		title:"增加广告位",
		width:700,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

/**
 * 编辑
 * @param {} id
 */
com.zz91.ads.board.ad.position.EditWin = function(id){
	var form = new com.zz91.ads.board.ad.position.EditForm();
	form.saveUrl = Context.ROOT + "/ad/position/update.htm";
	
	var win = new Ext.Window({
		id:POSITION.EDIT_WIN,
		title:"编辑广告位",
		width:700,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	form.reload(id);
	win.show();
}