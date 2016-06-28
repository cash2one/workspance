Ext.namespace("com.zz91.trade.buy");

var BUY=new function(){
	this.COLLECTION_GRID="collectiongrid";
	this.SENDMESSAGE_WIN="sendmessagewin";
	this.SUPPLY_GRID="supplygrid";
}

com.zz91.trade.buy.GRIDFIELD=[
	{name:"id",mapping:"tradeBuy.id"},
	{name:"cid",mapping:"tradeBuy.cid"},
	{name:"uid",mapping:"tradeBuy.uid"},
	{name:"category_code",mapping:"tradeBuy.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"compName",mapping:"compName"},
	{name:"memberCode",mapping:"memberCode"},
	{name:"check_admin",mapping:"tradeBuy.checkAdmin"},
	{name:"check_status",mapping:"tradeBuy.checkStatus"},
	{name:"title",mapping:"tradeBuy.title"},
	{name:"gmt_publish",mapping:"tradeBuy.gmtPublish"},
	{name:"gmt_refresh",mapping:"tradeBuy.gmtRefresh"},
	{name:"gmt_check",mapping:"tradeBuy.gmtCheck"},  //审核时间
	{name:"del_status",mapping:"tradeBuy.delStatus"},
	{name:"rid",mapping:"rid"}
];

com.zz91.trade.buy.SUPPLYFIELD=[
	{name:"id",mapping:"supply.id"},
	{name:"uid",mapping:"supply.uid"},
	{name:"cid",mapping:"supply.cid"},
	{name:"title",mapping:"supply.title"},
	{name:"compName",mapping:"compName"},
	{name:"send_time",mapping:"sendTime"},
	{name:"receive_time",mapping:"receiveTime"},
	{name:"register_source",mapping:"registerSource"},
	{name:"login_count",mapping:"loginCount"},
	{name:"memberCode",mapping:"memberCode"}
];

com.zz91.trade.buy.FILTER_RECOMMEND=[];

com.zz91.trade.buy.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/trade/tradebuy/queryTradeBuy.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.trade.buy.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"审核状态",width:60, dataIndex:"check_status",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value==1) {
						returnvalue="通过";
					}
					if(value==0) {
						returnvalue="未审核";
					}
					if(value==2) {
						returnvalue="不通过";
					}
					return returnvalue;
				}
			},{
				hidden:true,
				header:"编号",
				dataIndex:"id"
			},{
				hidden:true,header:"公司编号",dataIndex:"cid"
			},{
				hidden:true,header:"uid",dataIndex:"uid"
			},{
				hidden:true,header:"rid",dataIndex:"rid"
			},{
				hidden:true,header:"会员编号",dataIndex:"memberCode"
			},{
				header:"标题", width:360, dataIndex:"title",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var id=record.get("id");
					var v1="<a href='"+Context.ROOT+"/trade/tradebuy/edit.htm?id="+id+"' target='_blank'>"+value+"</a>";
					var v2="<a href='"+Context.TRADE+"/buy/detail"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v2+v1;
				}
			},{
			    hidden:true,header:"类别", width:120, dataIndex:"category_name",sortable:true
			},{
                header:"公司", width:200, dataIndex:"compName",sortable:true,
                renderer: function(value, metadata, record, rowIndex,colIndex, store) {
                    var memberCode=record.get("memberCode");
                    var v1="";
                    var v2=value;
                    if (memberCode=="10011001"){
                        v1 = "<img src='"+Context.ROOT+"/images/huanbao/zht.png' />";
                    }
                    if (value==""){
                        v2 = "<front style='color:green'>公司名称未填写</front>";
                    } else {
                        v2= "<a href='" + Context.ROOT+ 
                                "/comp/comp/details.htm?id=" + 
                                record.get("cid") + "' target='_blank'>" + 
                                value + "</a>";
                    }
                    return v1+v2;
                }
            },{
				header:"发布时间", width:130, dataIndex:"gmt_publish",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header:"刷新时间", width:130, dataIndex:"gmt_refresh",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
                header:"审核人", width:100, dataIndex:"check_admin",sortable:false
            },{
                header:"审核时间", width:130, dataIndex:"gmt_check",sortable:true,
                renderer : function(value, metadata, record, rowIndex,colIndex, store) {
                    if(value!=null){
                        return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
                    }
                }
            }
		]);
		
		var grid=this;
		
		var recommendMenu=grid.recommendMenu;
		if(recommendMenu==null){
			recommendMenu=new Array();
		}
		
		recommendMenu.push({
			text:"取消推荐",
			handler:function(){
				//得到表格对象
				var grid=Ext.getCmp(BUY.COLLECTION_GRID);
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT+"/trade/traderecommend/cancelRecommend.htm",
						params:{"targetId":rows[i].get("id"),"rid":rows[i].get("rid")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								com.zz91.utils.Msg("","取消推荐成功");
								grid.getStore().reload();
							}else{
								com.zz91.utils.Msg("","取消推荐失败");
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
			}
		});
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
				    text:"审核",
                    iconCls:"next16",
                    menu:[{
                        text:"通过",
                        handler:function(btn){
                            var grid = Ext.getCmp(BUY.COLLECTION_GRID);
                            grid.check(1,"",_store);
                        }
                    },{
                        text:"不通过",
                        handler:function(btn){
                            Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
                                if (btn == 'ok'){
                                    var grid = Ext.getCmp(BUY.COLLECTION_GRID);
                                    grid.check(2,text,_store);
                                }
                            }, this,true,"供求信息产品名称填写不明确：建议您一条信息只填写一种类型的发布与废料相关的产品、设备、服务等具体产品，并且只须填写产品名称或服务即可，如PP颗粒（多产品或标题过长都将非常不利于您的信息在ZZ91或百度谷歌等各大搜索引擎被搜索到），这样可以大大提升您信息在ZZ91或百度、谷歌等各大搜索引擎被查看到的机率。");
                        }
                    }]
				},{
                    text:"编辑",
                    iconCls:"foldermove16",
                    menu:[{
                            text:"批量修改",
                            iconCls:"foldermove16",
                            handler:function(btn){
                                var rows=grid.getSelectionModel().getSelections();
                                for(var i=0;i<rows.length;i++){
                                    window.open(Context.ROOT+"/trade/tradebuy/edit.htm?id="+rows[i].get("id"));
                                }
                            }
                        },{
                                text:"标记删除",
                                iconCls:"delete16",
                                handler:function(btn){
                                grid.delStatus(1,_store);
                                }
                          },
                          {
                              text:"取消删除",
                              iconCls:"play16",
                              handler:function(btn){
                              grid.delStatus(0,_store);
                              }
                          },{
                              text:"编辑类别",
                              iconCls:"foldermove16",
                              handler:function(btn){
                                  var rows=grid.getSelectionModel().getSelections();
                                  var adArr=new Array();
                                  for(var i=0;i<rows.length;i++){
                                      adArr.push(rows[i].get("id"))
                                  }
                                  com.zz91.trade.buy.editBuyCategoryWin(adArr,function(){
                                      _store.reload();
                                  });
                              }
                          },
                          {
                              text:"刷新求购",
                              iconCls:"refresh16",
                              handler:function(btn){
                              grid.refresh(_store);
                              }
                          },
                          {
                              text:"隐藏供应",
                              iconCls:"pause16",
                              handler:function(btn){
                               grid.hide(id,_store);
                              }
                          },
                          {
                              text:"显示供应",
                              iconCls:"play16",
                              handler:function(btn){
                               grid.showLine(id,_store);
                              }
                          }
                    ]
                },{
					text:"推荐为",
					iconCls:"fav16",
					menu:recommendMenu,
				},{
					text:"发送询盘",
					iconCls:"chat16",
					handler:function(btn){
						grid.message(_store);
					}
				},"->",{
                    xtype:"hidden",
                    fieldLabel:"cid",
                    name:"queryCid",
                    id:"queryCid"
                },{
                    xtype:"checkbox",
                    boxLabel:"<span style='color:red'>高会</span>",
                    listeners:{
                        "check":function(field,newvalue,oldvalue){
                            var _store = Ext.getCmp(BUY.COLLECTION_GRID).getStore();
                            var B = _store.baseParams;
                            if(field.getValue()){
                                B["memberCode"] = "10011001";
                            }else{
                                B["memberCode"] = "10011000";
                            }
                            _store.baseParams = B;
                            _store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
                        }
                    }
                },"-",{
                    xtype:"combo",
                    id:"queryDate",
                    mode:"local",
                    emptyText:"查询方式",
                    fieldLabel:"查询方式：",
                    triggerAction:"all",
                    displayField:'name',
                    valueField:'value',
                    autoSelect:true,
                    width:80,
                    store:new Ext.data.JsonStore( {
                        fields : [ "name", "value" ],
                        data   : [
                                  {name:"发布时间",value:"0"},
                                  {name:"刷新时间",value:"1"},
                                  {name:"审核时间",value:"2"}
                              ]
                        }),
                    listeners:{
                        "change":function(field,newValue,oldValue){
                            grid.getStore().baseParams["queryType"]=newValue;
                            /*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
                        }
                    }
                },{
                    id:"startDate",
                    xtype : "datefield",
                    format:"Y-m-d",
                    name:"from",
                    listeners:{
                        "change":function(field,newValue,oldValue){
                            grid.getStore().baseParams["from"]=newValue;
                            /*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
                        }
                    }
                },{
                    id:"endDate",
                    xtype : "datefield",
                    format:"Y-m-d",
                    name:"to",
                    listeners:{
                        "change":function(field,newValue,oldValue){
                            grid.getStore().baseParams["to"]=newValue;
                            /*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
                        }
                    }
                },"-",{
					text:"选择搜索条件",
					menu:[{
					    id : "title",
						xtype:"textfield",
						width:150,
						emptyText:"搜索标题",
						listeners:{
							"change":function(field,newValue,oldValue){
								var B=grid.getStore().baseParams;
								B=B||{};
								B["title"]=newValue;
								grid.getStore().baseParams=B;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
					    id : "checkAdmin",
						xtype:"textfield",
						width:150,
						emptyText:"审核人帐号搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var B=grid.getStore().baseParams;
								B=B||{};
								B["checkAdmin"]=newValue;
								grid.getStore().baseParams=B;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
					    id : "compName",
                        xtype:"textfield",
                        width:150,
                        emptyText:"公司名称搜索",
                        listeners:{
                            "change":function(field,newValue,oldValue){
                                var B=grid.getStore().baseParams;
                                B=B||{};
                                B["compName"]=newValue;
                                grid.getStore().baseParams=B;
                              /*  grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
                            }
                        }
                    },{
						xtype:"combo",
						width: 150,
						emptyText:"审核状态搜索",
						hiddenId:"check_status",
						hiddenName:"check_status",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","未审核"],["1","审核通过"],["2","审核不通过"],["3","全部"]]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["checkStatus"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						width: 150,
						triggerAction:"all",
						hiddenId:"regComeFrom",
						hiddenName:"regComeFrom",
						emptyText:"注册来源",
						valueField:"value",
						displayField:"name",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["register_type"],
							fields:["value","name"],
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["regComeFrom"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						width: 150,
						triggerAction:"all",
						hiddenId:"infoComeFrom",
						hiddenName:"infoComeFrom",
						emptyText:"信息来源",
						valueField:"value",
						displayField:"name",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["register_type"],
							fields:["value","name"],
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["infoComeFrom"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width: 150,
						emptyText:"删除状态搜索",
						hiddenId:"del_status",
						hiddenName:"del_status",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","未删除"],["1","已删除"],["","全部"]]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["delStatus"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:150,
						emptyText:"推荐类型搜索",
						hiddenId:"recommendType",
                        hiddenName:"recommendType",
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.trade.buy.FILTER_RECOMMEND
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["recommendType"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					}]
				},{
                    text:"确认搜索",
                    iconCls:"websearch16",
                    handler:function(btn){
				    
    				   /* var result_grid = Ext.getCmp(BUY.COLLECTION_GRID);
    				    
                        result_grid.store.baseParams = {
                                "title" : Ext.getCmp("title").getValue(),
                                "checkAdmin": Ext.getCmp("checkAdmin").getValue(),
                                "compName":Ext.getCmp("compName").getValue(),
                                "checkStatus":Ext.get("check_status").dom.value,
                                "regComeFrom":Ext.get("regComeFrom").dom.value,
                                "infoComeFrom":Ext.get("infoComeFrom").dom.value,
                                "delStatus":Ext.get("del_status").dom.value,
                                "recommendType":Ext.get("recommendType").dom.value,
                                "cid":Ext.getCmp("queryCid").getValue(),
                                "queryType":Ext.getCmp("queryDate").getValue(),
                                "from":Ext.get("startDate").dom.value,
                                "to":Ext.get("endDate").dom.value
                            };
                        result_grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
				        grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
                    }
                }]
			}),
			bbar:com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.trade.buy.grid.superclass.constructor.call(this,c);
	},
	searchByCompany:function(companyId){
	    Ext.getCmp("queryCid").setValue(companyId);
        var B=this.getStore().baseParams||{};
        B["cid"] = companyId;
        this.getStore().baseParams = B;
        this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
    },
	check:function(checkStatus,msg,store){
	    var grid=Ext.getCmp(BUY.COLLECTION_GRID);
        // 获取选中行的ID
        var rows=grid.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradebuy/updateCheckStatus.htm",
				params:{"id":rows[i].get("id"),"checkStatus":checkStatus,"unpassReson":msg},
				success:function(response,opt){
                    var obj = Ext.decode(response.responseText);
                    if(obj.success){
                        com.zz91.utils.Msg("","操作成功")
                        store.reload();
                    }else{
                        com.zz91.utils.Msg("","操作失败");
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
	},
	delStatus:function(delStatus,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(delStatus==rows[i].get("del_status")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradebuy/updateDelStatus.htm",
				params:{"id":rows[i].get("id"),"delStatus":delStatus},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
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
	},
	hide:function(id,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(id==rows[i].get("id")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradebuy/insertHideInfo.htm",
				params:{"id":rows[i].get("id"),"targetType":"2"},
				success:function(response,opt){ 
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","隐藏求购信息成功");
						  store.reload();
						//  store.load({params:{"targetType":"1"}});
					}else{
						com.zz91.utils.Msg("","隐藏求购信息失败");
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
	},
	
	showLine:function(id,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(id==rows[i].get("id")){
				continue;
			}
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradebuy/deleteHideInfo.htm",
				params:{"id":rows[i].get("id"),"targetType":"2"},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","恢复显示供应信息成功");
						  store.reload();
					}else{
						com.zz91.utils.Msg("","恢复显示供应信息失败");
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
	},
	refresh:function(store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradebuy/refreshBuy.htm",
				params:{"id":rows[i].get("id")},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","刷新求购信息成功!");
						store.reload();
					}else{
						com.zz91.utils.Msg("","刷新求购信息失败!");
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
	},
	message:function(delStatus,store){
		var rows=this.getSelectionModel().getSelections();
		if(rows.length>1){
			com.zz91.utils.Msg("","请选择一条记录操作!");
		}else{
			if(rows[0].get("del_status")==1 || rows[0].get("check_status")==0 || rows[0].get("check_status")==2){
				com.zz91.utils.Msg("","所选记录非正常!");
			}else{
				com.zz91.trade.buy.MessageWin(rows[0].get("cid"),rows[0].get("title"));
			}
		}
	},
	recommendMenu:null
});

com.zz91.trade.buy.recommend=function(type){
	//得到表格对象
	var grid=Ext.getCmp(BUY.COLLECTION_GRID);
	//获取选中行的ID
	var rows=grid.getSelectionModel().getSelections();
	for(var i=0;i<rows.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/trade/traderecommend/recommendTrade.htm",
			params:{"targetId":rows[i].get("id"),"cid":rows[i].get("cid"),"type":type},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					com.zz91.utils.Msg(MESSAGE.title, "信息推荐成功");
					grid.getStore().reload();
				}else{
					com.zz91.utils.Msg(MESSAGE.title, "信息推荐失败");
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
}


com.zz91.trade.buy.editBuyCategoryWin=function(adArr, callback){
	var tree = new com.zz91.trade.buy.category.TreePanel({
		height:400,
		autoScroll:true,
		layout:"fit",
		region:"center",
		contextmenu:null
	});
	
	var win = new Ext.Window({
		title:"选择要移动到的类别",
		modal:true,
		width:300,
		items:[tree]
	});
	win.show();
	
	tree.on('dblclick',function(node,e){
		var categoryCode=node.attributes["data"];
		var success=0;
		var failure=0;
		for(var i=0;i<adArr.length;i++){
			Ext.Ajax.request({
		        url:Context.ROOT+"/trade/tradebuy/editBuyCategory.htm",
		        params:{"id":adArr[i],"categoryCode":categoryCode},
		        success:function(response,opt){
		            var obj = Ext.decode(response.responseText);
		            if(obj.success){
		            	success++;
		            	
		            }else{
		            	failure++;
		            }
		            if((success+failure) == adArr.length){
		            	com.zz91.utils.Msg(Context.MSG_TITLE,"共移动类别"+adArr.length+"个，其中成功移动"+success+"个，失败"+failure+"个。");
		            	win.close();
		            	callback();
		            }
		        },
		        failure:function(response,opt){
					failure++;
		        }
		    });
		}
	});
}

//发送询盘
com.zz91.trade.buy.MessageWin=function(cid,title){
	var messageForm = new com.zz91.trade.buy.MessageForm({
		saveUrl:Context.ROOT+"/message/sendMessage.htm?cid="+cid,
		region:"center"
	});
	
	var supplyGrid=new com.zz91.trade.buy.supplyGrid({
		id:BUY.SUPPLY_GRID,
		height:200
	});
	
	supplyGrid.getTopToolbar().findById("key").setValue(title);
	
	var win = new Ext.Window({
		id:BUY.SENDMESSAGE_WIN,
		title:"发送询盘",
		width:700,
		height:350,
		modal:true,
		items:[supplyGrid,messageForm]
	});
	
	win.show();
}

com.zz91.trade.buy.MessageForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var c={
			frame:true,
			layout:"form",
			labelAlign : "left",
			labelWidth : 52,
			defaults:{
				columnWidth:1,
				xtype:"textfield",
				anchor:"99%",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				fieldLabel:"留言标题:",
				name:"title",
				id:"title"
			},{
				fieldLabel:"留言内容:",
				xtype:"textarea",
				emptyText :"请在这里填写您留言信息内容...",
				name:"details",
				id:"details",
				itemCls:"required",
				allowBlank:false
			}],
			buttons:[{
				text:"发送信息",
				scope:this,
				handler:function(){
					var form=this;
					var rows=Ext.getCmp(BUY.SUPPLY_GRID).getSelectionModel().getSelections();
					for(var i=0;i<rows.length;i++){
						Ext.Ajax.request({
							url:this.saveUrl,
							params:{
								"targetId":rows[i].get("id"),
								"targetUid":rows[i].get("uid"),
								"targetCid":rows[i].get("cid"),
								"targetType":"0",
								"title":"我对 "+rows[i].get("title")+" 很感兴趣",
								"details":this.findById("details").getValue()
							},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									com.zz91.utils.Msg(MESSAGE.title, "询盘成功!");
									Ext.getCmp(BUY.SENDMESSAGE_WIN).close();
								}else{
									com.zz91.utils.Msg(MESSAGE.title, "询盘成功!");
								}
							},
							failure:function(response,opt){
								com.zz91.utils.Msg(MESSAGE.title, MESSAGE.submitFailure);
							}
						});
					}
				}
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp(BUY.SENDMESSAGE_WIN).close();
				}
			}]
		};
		
		com.zz91.trade.buy.MessageForm.superclass.constructor.call(this,c);
	},
	saveUrl:""
});


com.zz91.trade.buy.supplyGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/trade/tradebuy/querySimplySupply.htm",
			autoLoad:false,
			fields:com.zz91.trade.buy.SUPPLYFIELD
		});
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				hidden:true,header:"id",dataIndex:"id"
			},{
				hidden:true,header:"uid",dataIndex:"uid"
			},{
				hidden:true,header:"cid",dataIndex:"cid"
			},{
				header:"会员编号", dataIndex:"memberCode",hidden:true
			},{
				header:"公司", width:180, dataIndex:"compName",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var memberCode=record.get("memberCode");
					var v2="";
					if (value=="" || value==null){
						value="公司名称暂无";
					}
					if (memberCode=="10011001"){
						v2 = "<img src='"+Context.ROOT+"/images/huanbao/zht.png' />";
					}
					return v2+value;
				}
			},{
				header:"标题", width:100, dataIndex:"title",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var id=record.get("id");
					var v1="<a href='"+Context.ROOT+"/trade/tradesupply/edit.htm?id="+id+"' target='_blank'>"+value+"</a>";
					var v2="<a href='"+Context.ESITE+"/detail"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v2+v1;
				}
			},{
				header:"总登录次数", width:120, dataIndex:"login_count",sortable:true
			},{
				header:"信息来源", width:100, dataIndex:"register_source",sortable:true,
				
			},{
				header:"接收询盘", width:80, dataIndex:"receive_time",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value){
						returnvalue="<font style='color:red;'>有</font>"
					}else{
						returnvalue="无"
					}
					return returnvalue;
				}
			},{
				header:"发送询盘", width:80, dataIndex:"send_time",sortable:true,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var returnvalue = value;
					if(value){
						returnvalue="<font style='color:red;'>有</font>"
					}else{
						returnvalue="无"
					}
					return returnvalue;
				}
			}
		]);
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:["信息标题:",{
					id:"key",
					xtype:"textfield",
					width:100,
					allowBlank : false,
					itemCls:"required",
					listeners:{
						"blur":function(textfield){
							var _store = Ext.getCmp(BUY.SUPPLY_GRID).getStore();
							_store.baseParams["keywords"]=textfield.getValue();
							
						}
					}
				},"公司名称：",{
					id:"companyName",
					xtype:"textfield",
					width:100,
					listeners:{
						"blur":function(textfield){
							var _store = Ext.getCmp(BUY.SUPPLY_GRID).getStore();
							_store.baseParams["compName"]=textfield.getValue();
							
						}
					}
				},"总登录次数：",{
					id:"num",
					xtype:"textfield",
					width:100,
					listeners:{
						"blur":function(textfield){
							var _store = Ext.getCmp(BUY.SUPPLY_GRID).getStore();
							_store.baseParams["loginCount"]=textfield.getValue();
							
						}
					}
				},{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
						var _store = Ext.getCmp(BUY.SUPPLY_GRID).getStore();
						if (Ext.getCmp("key").getValue()==""){
							com.zz91.utils.Msg("","信息标题不能为空!!!")
						}else if(Ext.getCmp("key").getValue().length==1&&
								Ext.getCmp("key").getValue()!='泵'&&
								Ext.getCmp("key").getValue()!='阀'){
							com.zz91.utils.Msg("","信息标题除泵，阀以外最少输入2个字符!!!");
						}else if(Ext.getCmp("key").getValue().length==2&&
								(Ext.getCmp("key").getValue()=='供应'||Ext.getCmp("key").getValue()=='求购')){
							com.zz91.utils.Msg("","信息标题不能输入‘供应‘，’求购‘，信息量太广!!!")
						}else{
							_store.reload();
						}
					}
				}]
			}),
//			bbar:com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.trade.buy.supplyGrid.superclass.constructor.call(this,c);
	}
});