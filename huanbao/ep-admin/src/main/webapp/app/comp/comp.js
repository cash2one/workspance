Ext.namespace("com.zz91.ep.comp");

var COMP = new function(){
	this.COMP_GRID = "compgrid";
	this.WEEKLY_WIN = "weeklywin";
}
 
com.zz91.ep.comp.Field=[
	{name:"id",mapping:"compProfile.id"},
	{name:"name",mapping:"compProfile.name"},
	{name:"mainProductSupply",mapping:"compProfile.mainProductSupply"},
	{name:"areaCode",mapping:"compProfile.areaCode"},
	{name:"provinceCode",mapping:"compProfile.provinceCode"},
	{name:"address",mapping:"compProfile.address"},
	{name:"address_zip",mapping:"compProfile.addressZip"},
	{name:"domain",mapping:"compProfile.domain"},
	{name:"domainTwo",mapping:"compProfile.domainTwo"},
	{name:"memberCode",mapping:"compProfile.memberCode"},
	{name:"memberCodeBlock",mapping:"compProfile.memberCodeBlock"},
	{name:"registerCode",mapping:"registerCode"},
	{name:"account",mapping:"compAccount.account"},
    {name:"passwordClear",mapping:"compAccount.passwordClear"},
	{name:"contacts",mapping:"compAccount.name"},
	{name:"mobile",mapping:"compAccount.mobile"},
	{name:"fax",mapping:"compAccount.fax"},
	{name:"phone",mapping:"compAccount.phone"},
	{name:"phone_area",mapping:"compAccount.phoneArea"},
	{name:"gmt_register",mapping:"compAccount.gmtRegister"},
	{name:"businessCode",mapping:"compProfile.businessCode"},
	{name:"industryCode",mapping:"compProfile.industryCode"},
	{name:"del_status",mapping:"compProfile.delStatus"},
	{name:"gmt_login",mapping:"compAccount.gmtLogin"},
	{name:"login_count",mapping:"compAccount.loginCount"},
	{name:"email",mapping:"compAccount.email"},
	{name:"account",mapping:"compAccount.account"},
	{name:"sendTime",mapping:"compProfile.sendTime"},
	{name:"receiveTime",mapping:"compProfile.receiveTime"}
];

com.zz91.ep.comp.FILTER_RECOMMEND=[];

com.zz91.ep.comp.FILTER_SUBREC=[];

com.zz91.ep.comp.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.comp.Field,
				url:Context.ROOT +  "/comp/comp/queryComp.htm",
				autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			hidden:true,
			header : "编号",
			dataIndex : "id"
		},{
			hidden:true,
			header : "会员编号",
			dataIndex : "memberCode"
		},{
			header : "公司",
			sortable : true,
			width : 210,
			dataIndex : "name",
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var id=record.get("id");
				var memberCode=record.get("memberCode");
				var returnValue=value;
                var v4 = "<a href='"+Context.ROOT+"/comp/comp/adminLogin.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>登录</a> ";
                
				var v3="";
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				if (memberCode=="10011001"){
					v3 = "<img src='"+Context.ROOT+"/images/huanbao/zht.png' />";
				}
				
				var v1 = "<a href='"+Context.ROOT+"/comp/comp/details.htm?id="+id+"' target='_blank'>"+returnValue+"</a>";
				var v2="<a href='"+Context.WWW+"/esite/index"+id+".htm' target='_blank' title='浏览前台页面'>" +
						"<img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
				return v4+v3+v2+v1 ;
			}
		},{
            header :"账号",
            width : 100,
            dataIndex:"account"
        },{
            header : "密码",
            width : 100,
            dataIndex : "passwordClear"
        },{
			header :"联系人",
			sortable : true,
			width : 60,
			dataIndex:"contacts"
		},{
			header : "手机",
			sortable : true,
			width : 100,
			dataIndex : "mobile"
		},{
            header : "电话区号",
            width : 60,
            sortable : true,
            dataIndex : "phone_area"
        },{
            header : "电话",
            width : 130,
            sortable : true,
            dataIndex : "phone"
        },{
            header : "地址",
            width : 300,
            sortable : true,
            dataIndex : "address"
        },{
			header : "邮箱",
			sortable : true,
			width : 200,
			dataIndex : "email"
		},{
			hidden:true,
			header : "冻结类型Code",
			sortable : false,
			dataIndex : "memberCodeBlock"
		},{
			header : "注册时间",
			width : 130,
			sortable: true,
			dataIndex : "gmt_register",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		},{
			header : "最后登录时间",
			sortable: true,
			width : 130,
			dataIndex : "gmt_login",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}
		},{
            header : "总登录次数",
            sortable: true,
            width:70,
            dataIndex : "login_count"
        }
		]);
		var grid = this;
		var recommendMenu=grid.recommendMenu;
		if(recommendMenu==null){
			recommendMenu=new Array();
		}
		var recommendCompMenu=grid.recommendCompMenu;
        if(recommendCompMenu==null){
            recommendCompMenu=new Array();
        }
        recommendCompMenu.push({
			text:"取消推荐",
			handler:function(){
				//得到表格对象
				var grid=Ext.getCmp(COMP.COMP_GRID);
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT+"/comp/comp/cancelRecommendCompCategory.htm",
						params:{"id":rows[i].get("id")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								com.zz91.utils.Msg("","取消推荐成功");
								store.reload();
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
		
		var subRecMenu=grid.subRecMenu;
		if(subRecMenu==null){
			subRecMenu=new Array();
		}
		
		subRecMenu.push({
			text:"取消推荐",
			handler:function(){
				//得到表格对象
				var grid=Ext.getCmp(COMP.COMP_GRID);
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT+"/comp/comp/cancelSubRecommend.htm",
						params:{"cid":rows[i].get("id")},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								com.zz91.utils.Msg("","取消推荐成功");
								store.reload();
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
            text:"公司推荐",
            iconCls:"fav16",
            menu:recommendCompMenu
        });
		recommendMenu.push({
            text:"子网推荐",
            iconCls:"fav16",
            menu:subRecMenu
        });
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"推荐为",
					iconCls:"fav16",
					menu:recommendMenu,
				},{
					text:"标记删除",
					iconCls:"delete16",
					handler:function(btn){
                                                
						grid.memberCodeBlock("1",_store);
					}
				},{
					text:"取消删除",
					iconCls:"play16",
					handler:function(btn){
                                                
						grid.memberCodeBlock("0",_store);
					}
				},
//				{
//					text:"更新搜索引擎(部分)",
//					iconCls:"refresh16",
//					handler:function(){
//					com.zz91.ep.comp.refreshPartSearch();
//					}
//				},{
//					text:"更新搜索引擎(全部)",
//					iconCls:"refresh16",
//					handler:function(){
//					com.zz91.ep.comp.refreshAllSearch();
//					}
//				},
				{
					text:"发送周报",
					iconCls:"textfile16",
					handler:function(){
					com.zz91.ep.comp.WeeklyWin();
					}
				},"->",{
                    xtype:"checkbox",
                    boxLabel:"<span style='color:red'>高会</span>",
                    listeners:{
                        "check":function(field,newvalue,oldvalue){
                            var _store = Ext.getCmp(COMP.COMP_GRID).getStore();
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
                    /*id:"queryDate",*/
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
                                  {name:"注册时间",value:"0"}
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
					    /*id : "email",*/
						xtype:"textfield",
						width:200,
						emptyText:"通过email或账号进行搜索",
						listeners:{
							"change":function(field,newValue,oldValue){
								var C=grid.getStore().baseParams;
								C=C||{};
								C["email"]=newValue;
								grid.getStore().baseParams=C;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype : "textfield",
						/*id : "search-name",*/
						width:200,
						emptyText:"按公司名称搜索",
						listeners:{
                            "change":function(field){
                                var _store = Ext.getCmp(COMP.COMP_GRID).getStore();
                                var B = _store.baseParams;
                                B = B||{};
                                if(field.getValue()!=""){
                                    B["name"] = field.getValue();
                                }else{
                                    B["name"]=undefined;
                                }
                                _store.baseParams = B;
                               /* _store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
                            }
                        }
					},{
						xtype:"combo",
						width:200,
						emptyText:"会员类型搜寻",
						hiddenId:"memberCode",
						hiddenName:"memberCode",
						mode:"local",
						triggerAction:"all",
						lazyRender:true,
						valueField:"code",
						displayField:"name",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/combo/queryMembersByCode.htm?parentCode="+COMBO.member,
							fields:["code","name"],
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["memberCode"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						width:200,
						triggerAction:"all",
						hiddenId:"memberCodeBlock",
						hiddenName:"memberCodeBlock",
						emptyText:"冻结类型搜寻",
						valueField:"code",
						displayField:"name",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/combo/queryMembersByCode.htm?parentCode="+COMBO.member_block,
							fields:["code","name"],
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["memberCodeBlock"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						width:200,
						triggerAction:"all",
						hiddenId:"chainId",
						hiddenName:"chainId",
						emptyText:"产业链搜索",
						valueField:"id",
						displayField:"categoryName",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/combo/getCategoryName.htm",
							fields:["id","categoryName"],
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["chainId"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:200,
						mode:"local",
						triggerAction:"all",
						hiddenId:"delStatus",
						hiddenName:"delStatus",
						emptyText:"删除状态搜索",
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
						width:200,
						mode:"local",
						triggerAction:"all",
						hiddenId:"registerCode",
						hiddenName:"registerCode",
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
								grid.getStore().baseParams["registerCode"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
					    /*id : "phone",*/
						xtype:"textfield",
						width:200,
						emptyText:"按手机或电话进行搜索",
						listeners:{
                            "change":function(field,newValue,oldValue){
                                var C=grid.getStore().baseParams;
                                C=C||{};
                                C["phone"]=newValue;
                                grid.getStore().baseParams=C;
                                grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
                            }
                        }
					},{
						xtype:"combo",
						width:200,
						mode:"local",
						triggerAction:"all",
						hiddenId:"industryCode",
						hiddenName:"industryCode",
						emptyText:"行业类型搜索",
						valueField:"industryValue",
						displayField:"industryName",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["comp_industry"],
							fields:[
								{name:"industryValue",mapping:"value"},
								{name:"industryName",mapping:"name"}
							],
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["industryCode"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:200,
						mode:"local",
						triggerAction:"all",
						hiddenId:"businessCode",
						hiddenName:"businessCode",
						emptyText:"业务类型搜索",
						valueField:"businessValue",
						displayField:"businessName",
						store:new Ext.data.JsonStore({
							autoLoad : true,
							url : Context.ROOT+"/param/param/paramByTypes.htm?types="+COMBO.paramTypes["company_industry_code"],
							fields:[
								{name:"businessValue",mapping:"value"},
								{name:"businessName",mapping:"name"}
							]
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["businessCode"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:200,
						mode:"local",
						triggerAction:"all",
						hiddenId:"serviceType",
						hiddenName:"serviceType",
						emptyText:"服务类型",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[["0","供应商"],["1","求购商"],["2","全部"],["3","都不是"]]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["serviceType"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:200,
						emptyText:"选择推荐类型",
						hiddenId:"recommendCode",
                        hiddenName:"recommendCode",
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.ep.comp.FILTER_RECOMMEND
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["recommendCode"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						width:200,
						emptyText:"子网推荐",
						hiddenId:"subnetCategory",
                        hiddenName:"subnetCategory",
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.ep.comp.FILTER_SUBREC
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["subnetCategory"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						width:200,
						triggerAction:"all",
						emptyText:"询盘信息查询",
						hiddenId:"messagetime",
                        hiddenName:"messagetime",
						lazyRender:true,
						valueField:"k",
                        displayField:"v",
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[
							      ["0","接收询盘信息"],
							      ["1","发送询盘信息"]]
						}),
						
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["messagetime"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						width:200,
						triggerAction:"all",
						hiddenId:"otherSearch",
						hiddenName:"otherSearch",
						emptyText:"其他筛选条件",
						lazyRender:true,
						valueField:"k",
                        displayField:"v",
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[
							      ["0","注册未完成"],
							      ["1","注册未登陆"],
							      ["2","注册有登陆但未发布"],
							      ["3","总登陆次数>=1"],
							      ["4","最近30天登录，登陆次数>1,<=4"],
							      ["5","最近30天登录，登陆次数>4"],
							      ["6","最近30天登录，登陆次数>=1"]]
						}),
						
						listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["otherSearch"]=newValue;
								/*grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
							}
						}
					}]
				},{
                    text:"确认搜索",
                    iconCls:"websearch16",
                    handler:function(btn){
				       /* var result_grid = Ext.getCmp(COMP.COMP_GRID);
                        result_grid.store.baseParams = {
                            "from":Ext.get("startDate").dom.value,
                            "to":Ext.get("endDate").dom.value,
                            "email" : Ext.getCmp("email").getValue(),
                            "name": Ext.getCmp("search-name").getValue(),
                            "memberCode":Ext.get("memberCode").dom.value,
                            "memberCodeBlock":Ext.get("memberCodeBlock").dom.value,
                            "chainId":Ext.get("chainId").dom.value,
                            "delStatus":Ext.get("delStatus").dom.value,
                            "registerCode":Ext.get("registerCode").dom.value,
                            "phone":Ext.getCmp("phone").getValue(),
                            "industryCode":Ext.get("industryCode").dom.value,
                            "businessCode":Ext.get("businessCode").dom.value,
                            "serviceType":Ext.get("serviceType").dom.value,
                            "recommendCode":Ext.get("recommendCode").dom.value,
                            "subnetCategory":Ext.get("subnetCategory").dom.value,
                            "messagetime":Ext.get("messagetime").dom.value,
                            "otherSearch":Ext.get("otherSearch").dom.value
                            };
                        result_grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});*/
				        grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
                    }
                }]
			}),
			bbar: com.zz91.utils.pageNav(_store)
		}
		com.zz91.ep.comp.Grid.superclass.constructor.call(this,c);
	},
	memberCodeBlock:function(code,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/comp/comp/updateDelStatusAndMemberCodeBlock.htm",
				params:{"id":rows[i].get("id"),"code":code},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("","操作成功!");
						store.reload();
					}else{
						com.zz91.utils.Msg("","操作失败!");
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
recommendCompMenu:null,
subRecMenu:null
});

com.zz91.ep.comp.recommend=function(type){
	//得到表格对象
	var grid=Ext.getCmp(COMP.COMP_GRID);
	//获取选中行的ID
	var rows=grid.getSelectionModel().getSelections();
	for(var i=0;i<rows.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/comp/comp/recommendCompCategory.htm",
			params:{"id":rows[i].get("id"),"type":type},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					com.zz91.utils.Msg(MESSAGE.title, "公司推荐成功");
					store.reload();
				}else{
					com.zz91.utils.Msg(MESSAGE.title, "公司推荐失败");
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

com.zz91.ep.comp.subRecommend=function(type){
	//得到表格对象
	var grid=Ext.getCmp(COMP.COMP_GRID);
	//获取选中行的ID
	var rows=grid.getSelectionModel().getSelections();
	for(var i=0;i<rows.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/comp/comp/subRecommend.htm",
			params:{"id":rows[i].get("id"),"type":type},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					com.zz91.utils.Msg(MESSAGE.title, "公司推荐成功");
					store.reload();
				}else{
					com.zz91.utils.Msg(MESSAGE.title, "公司推荐失败");
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

com.zz91.ep.comp.refreshAllSearch=function(){
	
	Ext.Msg.confirm("确认","你确定要更新吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
			Ext.Ajax.request({
				url:Context.ROOT+"/comp/comp/refreshAllSearch.htm",
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
com.zz91.ep.comp.refreshPartSearch=function(){
	Ext.Msg.confirm("确认","你确定要更新吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
			Ext.Ajax.request({
				url:Context.ROOT+"/comp/comp/refreshPartSearch.htm",
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

//发送周报
com.zz91.ep.comp.WeeklyWin=function(){
	var weeklyForm = new com.zz91.ep.comp.WeeklyForm({
		saveUrl:Context.ROOT+"/comp/comp/sendWeekly.htm",
		region:"center"
	});
	
	var win = new Ext.Window({
		id:COMP.WEEKLY_WIN,
		title:"发送周报",
		width:600,
		height:350,
		modal:true,
		items:[weeklyForm]
	});
	
	win.show();
}

com.zz91.ep.comp.WeeklyForm=Ext.extend(Ext.form.FormPanel,{
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
				xtype:"combo",
				fieldLabel:"周期:",
				triggerAction:"all",
				displayField:"v",
				id:"week",
				valueField:"k",
				anchor:"80%",
				editable:false,
				itemCls:"required",
				allowBlank:false,
				store:{
					xtype:"jsonstore",
					url:Context.ROOT+"/data/week.js",
					fields:["k","v"]
				}
			},{
				fieldLabel:"留言内容:",
				xtype:"textarea",
				emptyText :"请在这里填写您留言信息内容...",
				name:"details",
				id:"details",
				itemCls:"required",
				allowBlank:false,
				height:200
			}],
			buttons:[{
				text:"发送周报",
				scope:this,
				handler:function(){
					var form=this;
					var rows=Ext.getCmp(COMP.COMP_GRID).getSelectionModel().getSelections();
					if(form.getForm().isValid()){
						for(var i=0;i<rows.length;i++){
							Ext.Ajax.request({
								url:this.saveUrl,
								params:{
									"cid":rows[i].get("id"),
									"email":rows[i].get("email"),
									"lastLogin":rows[i].get("gmt_login").time,
									"contact":rows[i].get("contacts"),
									"loginCount":rows[i].get("login_count"),
									"account":rows[i].get("account"),
									"details":this.findById("details").getValue(),
									"week":this.findById("week").getValue()
								},
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										Ext.getCmp(COMP.WEEKLY_WIN).close();
										com.zz91.utils.Msg(MESSAGE.title, "周报发送成功!");
										 
									}else{
										com.zz91.utils.Msg(MESSAGE.title, "周报发送成功!");
									}
								},
								failure:function(response,opt){
									com.zz91.utils.Msg(MESSAGE.title, MESSAGE.submitFailure);
								}
							});
						}
					}
				}
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp(COMP.WEEKLY_WIN).close();
				}
			}]
		};
		
		com.zz91.ep.comp.WeeklyForm.superclass.constructor.call(this,c);
	},
	saveUrl:""
});
