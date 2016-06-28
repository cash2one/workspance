Ext.namespace("com.zz91.trade.supply");

var SUPPLY=new function(){
	this.EDIT_WIN="editwin";
	this.COLLECTION_GRID="collectiongrid";
	this.RESULT_GRID="resultgrid";
	this.SUPPLY_CATEGORY_FORM="supplyCategoryFormId";
}

com.zz91.trade.supply.GRIDFIELD=[
	{name:"id",mapping:"supply.id"},
	{name:"cid",mapping:"supply.cid"},
	{name:"uid",mapping:"supply.uid"},
	{name:"category_code",mapping:"supply.categoryCode"},
	{name:"category_name",mapping:"categoryName"},
	{name:"compName",mapping:"compName"},
	{name:"memberCode",mapping:"memberCode"},
	{name:"check_admin",mapping:"supply.checkAdmin"},
	{name:"check_status",mapping:"supply.checkStatus"},
	{name:"property_query",mapping:"supply.propertyQuery"},// 专业属性名字
	{name:"title",mapping:"supply.title"},
	{name:"details_query",mapping:"supply.detailsQuery"},// 简要信息
	{name:"gmt_publish",mapping:"supply.gmtPublish"},
	{name:"gmt_refresh",mapping:"supply.gmtRefresh"},
	{name:"gmt_check",mapping:"supply.gmtCheck"},  //审核时间
	{name:"del_status",mapping:"supply.delStatus"},
	{name:"rid",mapping:"rid"}

];

com.zz91.trade.supply.FILTER_RECOMMEND=[];

com.zz91.trade.supply.FILTER_SUBNETREC=[];

com.zz91.trade.supply.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			url:Context.ROOT+"/trade/tradesupply/queryTradeSupply.htm",
			autoLoad:true,
			fields:com.zz91.trade.supply.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"审核状态",width:60, dataIndex:"check_status",sortable:false,
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
				hidden:true,
				header:"公司编号",
				dataIndex:"cid"
			},{
				hidden:true,header:"memberCode",dataIndex:"memberCode"
			},{
				hidden:true,header:"rid",dataIndex:"rid"
			},{
				header:"标题", width:360, dataIndex:"title",sortable:false,
				renderer: function(value, metadata, record, rowIndex,colIndex, store) {
					var id=record.get("id");
					var v1="<a href='"+Context.ROOT+"/trade/tradesupply/edit.htm?id="+id+"' target='_blank'>"+value+"</a>";
					var v2="<a href='"+Context.ESITE+"/detail"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v2+v1;
				}
			},{
				header:"类别", width:120, dataIndex:"category_name",sortable:false
			},{
                header:"公司", width:200, dataIndex:"compName",sortable:false,
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
				}
			},{
				header:"刷新时间", width:130, dataIndex:"gmt_refresh",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
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
            text:"供求推荐",
            iconCls:"fav16",
            menu:{
		        items:[{
        		    text:"推荐信息",
                    handler:function(){
                        com.zz91.trade.supply.recommend("0",Ext.getCmp(SUPPLY.COLLECTION_GRID));
                    }
		        },{
		            text:"取消推荐",
		            handler:function(){
		                // 得到表格对象
		                var grid=Ext.getCmp(SUPPLY.COLLECTION_GRID);
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
		        }
	        ]}
        });
		recommendMenu.push({
            text:"标王推荐",
            iconCls:"favb16",
            handler:function(btn){
                var rows=grid.getSelectionModel().getSelections();
                window.open(Context.ROOT+"/trade/tradesupply/recBw.htm?sid="+rows[0].get("id")+"&type=0");
            }
        });
		
		var subRecTradeMenu=grid.subRecTradeMenu;
		if(subRecTradeMenu==null){
			subRecTradeMenu=new Array();
		}
		
		subRecTradeMenu.push({
			text:"取消推荐",
			handler:function(){
				// 得到表格对象
				var grid=Ext.getCmp(SUPPLY.COLLECTION_GRID);
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT+"/trade/traderecommend/cancelSubnetRecommend.htm",
						params:{"id":rows[i].get("id")},
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
		recommendMenu.push({
            text:"子网推荐",
            iconCls:"fav16",
            menu:subRecTradeMenu
        });
		var c={
		    id : SUPPLY.COLLECTION_GRID,
			loadMask:Context.LOADMASK,
			store:_store,
			// stripeRows: true,//斑马线效果
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"审核",
					iconCls:"next16",
				    menu:[{
			            text:"通过",
			            handler:function(btn){
			                var grid = Ext.getCmp(SUPPLY.COLLECTION_GRID);
			                grid.check(1,"",_store);
			            }
			        },{
			            text:"不通过",
			            handler:function(btn){
			                Ext.Msg.prompt('未通过原因', '请输入未通过的原因:', function(btn, text){
			                   if (btn == 'ok'){
			                        var grid = Ext.getCmp(SUPPLY.COLLECTION_GRID);
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
                                    window.open(Context.ROOT+"/trade/tradesupply/edit.htm?id="+rows[i].get("id"));
                                }
                            }
                        },
                        {
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
                          },
                          {
                              text:"编辑类别",
                              iconCls:"foldermove16",
                              handler:function(btn){
                                  var rows=grid.getSelectionModel().getSelections();
                                  var adArr=new Array();
                                  for(var i=0;i<rows.length;i++){
                                      adArr.push(rows[i].get("id"))
                                  }
                                  com.zz91.trade.supply.editSupplyCategoryWin(adArr,function(){
                                      _store.reload();
                                  });
                              }
                          },
                          {
                              text:"刷新供应",
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
                },
// {
// text:"更新搜索引擎(部分)",
// iconCls:"refresh16",
// handler:function(){
// com.zz91.trade.supply.refreshPartSearch();
// }
// },{
// text:"更新搜索引擎(全部)",
// iconCls:"refresh16",
// handler:function(){
// com.zz91.trade.supply.refreshAllSearch();
// }
// },
				{
					text:"推荐为",
					iconCls:"fav16",
					menu:recommendMenu,
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
			                var _store = Ext.getCmp(SUPPLY.COLLECTION_GRID).getStore();
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
					    /*id : "title",*/
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
					    /*id : "checkAdmin",*/
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
					    /*id : "compName",*/
						xtype:"textfield",
						width:150,
						emptyText:"公司名称搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var B=grid.getStore().baseParams;
								B=B||{};
								B["compName"]=newValue;
								grid.getStore().baseParams=B;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width: 150,
						emptyText:"审核状态搜索",
						/*id : "checkStatus",*/
						hiddenId:"check_status",
						hiddenName:"check_status",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
                        valueField:"k",
                        displayField:"v",
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","未审核"],["1","审核通过"],["2","审核不通过"],["3","全部"]]
						}),
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
						/*id : "delStatus",*/
						hiddenId:"del_status",
						hiddenName:"del_status",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
                        valueField:"k",
                        displayField:"v",
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","未删除"],["1","已删除"],["","全部"]]
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["delStatus"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width: 150,
						emptyText:"公司是否冻结",
						/*id : "codeBlock",*/
						hiddenId:"code_block",
						hiddenName:"code_block",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
                        valueField:"k",
                        displayField:"v",
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["","未冻结"],["10001002","已冻结"]]
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["codeBlock"]=newValue;
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
	                       data : com.zz91.trade.supply.FILTER_RECOMMEND
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["recommendType"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:150,
						emptyText:"子网推荐",
						hiddenId:"subRecommend",
                        hiddenName:"subRecommend",
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.trade.supply.FILTER_SUBNETREC
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["subRecommend"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					}]
				},{
                    text:"确认搜索",
                    iconCls:"websearch16",
                    handler:function(btn){
				   /* var result_grid = Ext.getCmp(SUPPLY.COLLECTION_GRID);
                    
                    result_grid.store.baseParams = {
                            "title" : Ext.getCmp("title").getValue(),
                            "checkAdmin": Ext.getCmp("checkAdmin").getValue(),
                            "compName":Ext.getCmp("compName").getValue(),
                            "checkStatus":Ext.getCmp("checkStatus").getValue(),
                            "infoComeFrom":Ext.get("infoComeFrom").getValue(),
                            "delStatus":Ext.get("delStatus").getValue(),
                            "codeBlock":Ext.get("codeBlock").getValue(),
                            "recommendType":Ext.get("recommendType").getValue(),
                            "subRecommend":Ext.get("subRecommend").getValue(),
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
		
		com.zz91.trade.supply.grid.superclass.constructor.call(this,c);
	},
	searchByCompany:function(companyId){
	    Ext.getCmp("queryCid").setValue(companyId);
        var B=this.getStore().baseParams||{};
        B["cid"] = companyId;
        this.getStore().baseParams = B;
        this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
    },
	check:function(checkStatus,msg,store){
	    var grid=Ext.getCmp(SUPPLY.COLLECTION_GRID);
	    // 获取选中行的ID
	    var rows=grid.getSelectionModel().getSelections();
	    //var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradesupply/updateCheckStatus.htm",
				params:{"id":rows[i].get("id"),"checkStatus":checkStatus,"unpassReson":msg},
				success:function(response,opt){
                    var obj = Ext.decode(response.responseText);
                    if(obj.success){
                        com.zz91.utils.Msg("","操作成功");
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
				url:Context.ROOT+"/trade/tradesupply/updateDelStatus.htm",
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
				url:Context.ROOT+"/trade/tradesupply/insertHideInfo.htm",
				params:{"id":rows[i].get("id"),"targetType":"1"},
				success:function(response,opt){ 
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","隐藏供应信息成功");
						  store.reload();
						//  store.load({params:{"targetType":"1"}});
					}else{
						com.zz91.utils.Msg("","隐藏供应信息失败");
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
				url:Context.ROOT+"/trade/tradesupply/deleteHideInfo.htm",
				params:{"id":rows[i].get("id"),"targetType":"1"},
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
				url:Context.ROOT+"/trade/tradesupply/refreshSupply.htm",
				params:{"id":rows[i].get("id")},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","刷新供应信息成功!");
						store.reload();
					}else{
						com.zz91.utils.Msg("","刷新供应信息失败!");
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
	recommendMenu:null,
	subRecTradeMenu:null
});

com.zz91.trade.supply.recommend=function(type,grid){
	// 获取选中行的ID
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

com.zz91.trade.supply.subRecommend=function(type){
	// 得到表格对象
	var grid=Ext.getCmp(SUPPLY.COLLECTION_GRID);
	// 获取选中行的ID
	var rows=grid.getSelectionModel().getSelections();
	for(var i=0;i<rows.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/trade/traderecommend/subTradeRecommend.htm",
			params:{"id":rows[i].get("id"),"type":type},
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

com.zz91.trade.supply.refreshAllSearch=function(){
	
	Ext.Msg.confirm("确认","你确定要更新吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradesupply/refreshAllSearch.htm",
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						if (obj.data==null) {
							com.zz91.utils.Msg("","更新全部搜索引擎成功");
						} else {
							com.zz91.utils.Msg("",obj.data);
						}
					}else{
						com.zz91.utils.Msg("","更新全部搜索引擎失败");
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
	});
}
com.zz91.trade.supply.refreshPartSearch=function(){
	Ext.Msg.confirm("确认","你确定要更新吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
			Ext.Ajax.request({
				url:Context.ROOT+"/trade/tradesupply/refreshPartSearch.htm",
				success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					if (obj.data==null) {
						com.zz91.utils.Msg("","更新部分搜索引擎成功");
					} else {
						com.zz91.utils.Msg("",obj.data);
					}
				}else{
					com.zz91.utils.Msg("","更新部分搜索引擎失败");
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
	});
}


com.zz91.trade.supply.editSupplyCategoryWin=function(adArr, callback){
	var tree = new com.zz91.trade.supply.category.TreePanel({
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
		        url:Context.ROOT+"/trade/tradesupply/editSupplyCategory.htm",
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
