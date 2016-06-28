Ext.namespace("ast.ast1949.trust.crm");

ast.ast1949.trust.crm.Fields=[
	{name:"crmAccount", mapping:"trustCrm.crmAccount"},
	{name:"csAccount", mapping:"csAccount"},
	{name:"star", mapping:"trustCrm.star"},
	{name:"numVisitMonth", mapping:"numVisitMonth"},
	{name:"companyName", mapping:"companyName"},
	{name:"buyNum", mapping:"buyNum"},
	{name:"sellNum", mapping:"sellNum"},
	{name:"contact", mapping:"account.contact"},
	{name:"mobile", mapping:"account.mobile"},
	{name:"account", mapping:"account.account"},
	{name:"company_id", mapping:"trustCrm.companyId"},
	{name:"gmt_last_visit", mapping:"trustCrm.gmtLastVisit"},
	{name:"gmt_next_visit", mapping:"trustCrm.gmtNextVisit"},
	{name:"membershipCode", mapping:"membershipCode"}
	
];

ast.ast1949.trust.crm.Grid = Ext.extend(Ext.grid.GridPanel,{
	queryUrl:Context.ROOT +Context.PATH+  "/trust/crm/queryCrm.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _url=this.queryUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.trust.crm.Fields,
			url:_url,
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			width : 50,
			sortable : true,
			dataIndex : "company_id",
			id:"company_id",
			hidden:true
		},{
			header : "交易员",
			width : 50,
			dataIndex : "crmAccount",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.trust.crm.TRUSTMAP[value] != "undefined"){
						return ast.ast1949.trust.crm.TRUSTMAP[value];
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		},{
			header : "客服",
			width : 50,
			id:"csAccount",
			dataIndex : "csAccount",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				if(value!=null && value.length>0){
					if(typeof ast.ast1949.trust.crm.CSMAP[value] != "undefined"){
						return ast.ast1949.trust.crm.CSMAP[value];
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		},{
			header : "星级",
			width:40,
			dataIndex : "star"
		},{
			header : "本月联系",
			width:80,
			dataIndex : "numVisitMonth",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				return "<a href='"+Context.ROOT+Context.PATH+"/trust/myLog.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a> ";
			}
		},{
			header : "公司名称",
			width:250,
			dataIndex : "companyName",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				var val="";
				if(record.get("membershipCode")!="10051000"&&record.get("membershipCode")!=""){
					val="<img src='"+Context.ROOT+"/images/recycle"+record.get("membershipCode")+".gif' />";
				}
				value = "<a href='" +Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+record.get("company_id")+ "' target='_blank'>"+value +"</a>";
				return val+"<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>登录</a> "+value;
			}
		},{
			header:"采购单",
			dataIndex:"buyNum",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				return "<a href='"+Context.ROOT+Context.PATH+"/trust/mytrust.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a> "
			}
		},{
			header:"供货单",
			dataIndex:"sellNum",
			renderer:function(value,metadata,record,rowIndex,colIndex,store){
				return "<a href='"+Context.ROOT+Context.PATH+"/trust/mysupply.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a> "
			}
		},{ 
			header : "联系人",
			dataIndex : "contact"
		},{
			header : "手机",
			dataIndex : "mobile"
		},{
			header : "最后联系时间",
			dataIndex : "gmt_last_visit",
			sortable : true,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "下次电话时间",
			sortable : true,
			dataIndex : "gmt_next_visit",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		}]);
		
		var grid=this;
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				text:"操作",
				iconCls : 'edit',
				menu:[{
					text:"丢公海",
					iconCls:"item-info",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelected();
						var companyId=rows.get("company_id");
						Ext.MessageBox.confirm(Context.MSG_TITLE,"确定丢公海?",function(btn){
						if(btn!="yes"){
							return ;
						}else{
							var rows=grid.getSelectionModel().getSelections();
							for(var i=0;i<rows.length;i++){
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+ "/trust/crm/lost.htm",
									params:{
									"companyId":rows[i].get("company_id")
								},
									method:"post",
									type:"json",
									success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										ast.ast1949.utils.Msg(obj.data,"成功");
										_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
									}else{
										ast.ast1949.utils.Msg(obj.data,"失败");
									}
								}
								});
							}
						}
						});
					}
				},{
					text:"捞给自己",
					iconCls:"item-info",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelected();
						var companyId=rows.get("company_id");
						Ext.MessageBox.confirm(Context.MSG_TITLE,"客户名：'"+" "+rows.get("companyName")+"' 确定捞吗?",function(btn){
						if(btn!="yes"){
							return ;
						}else{
							var rows=grid.getSelectionModel().getSelections();
							for(var i=0;i<rows.length;i++){
								
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+ "/trust/crm/assignCrmAccount.htm",
									params:{
										"companyId":rows[i].get("company_id")
									},
									method:"post",
									type:"json",
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											ast.ast1949.utils.Msg(obj.data,"已成功");
											_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
										}else{
											ast.ast1949.utils.Msg(obj.data,"失败");
										}
									}
								});
							}
						}
						});
					}
				},{
					text:"丢废品池",
					iconCls:"item-info",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelected();
						var companyId=rows.get("company_id");
						Ext.MessageBox.confirm(Context.MSG_TITLE,"丢'"+" "+rows.get("companyName")+"' 客户至废品池?(PS:只有自己库中的客户拥有丢废品池权利)",function(btn){
						if(btn!="yes"){
							return ;
						}else{
							var rows=grid.getSelectionModel().getSelections();
							for(var i=0;i<rows.length;i++){
								Ext.Ajax.request({
									url:Context.ROOT+Context.PATH+ "/trust/crm/destory.htm",
									params:{
									"companyId":rows[i].get("company_id")
								},
									method:"post",
									type:"json",
									success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										ast.ast1949.utils.Msg(obj.data,"已成功");
										_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
									}else{
										ast.ast1949.utils.Msg(obj.data,"失败");
									}
								}
								});
							}
						}
						});
					}
				}
				]
			},"->",{
				xtype:"combo",
				triggerAction : "all",
				forceSelection : true,
				width:80,
				displayField : "name",
				valueField : "account",
				hiddenId:"crmAccount",
				hiddenName:"crmAccount",
				id:"cs",
				store:new Ext.data.JsonStore( {
					fields : [ "account", "name" ],
					autoLoad:true,
					url : Context.ROOT + Context.PATH+ "/trust/crm/queryTrustCs.htm"
				}),
				listeners:{
					"blur":function(field){
						if(field.getValue()==""){
							return false;
						}
						if(field.getValue()=="all"){
							field.setValue("");
						}
						var B=_store.baseParams;
						if(B["crmAccount"]==field.getValue()){
							return false;
						}
						B["crmAccount"]=field.getValue();
						_store.baseParams = B;
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
			},'-',{
                xtype:"checkbox",
                boxLabel:"只显示公海",
                handler:function(btn){
                    var B=_store.baseParams||{};
                    if(btn.getValue()){
                            B["isPublic"]=1;
                    }else{
                            B["isPublic"]=undefined;
                    }
                    _store.baseParams = B;
                    _store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
                }
			},{
				xtype:"checkbox",
				boxLabel:"只显示废品池",
				handler:function(btn){
					var B=_store.baseParams||{};
					if(btn.getValue()){
						B["isRubbish"]=1;
					}else{
						B["isRubbish"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
				}
			}],
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
			listeners:{
				"rowdblclick":function(g, rowIndex, e){
					var row=grid.getSelectionModel().getSelected();
					if(typeof(row)=="object"){
						window.open(Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+row.get("company_id"));
					}
				},
				"rowcontextmenu":function(g,rowIndex,e){
					if(!g.getSelectionModel().isSelected(rowIndex)){
						g.getSelectionModel().clearSelections();
						g.getSelectionModel().selectRow(rowIndex);
					}
					e.preventDefault();
					if(g.contextmenu!=null){
						g.contextmenu.showAt(e.getXY());
					}
				}
			}
		};
		
		ast.ast1949.trust.crm.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null,
	isToday:function(crmAccount){
		var store=Ext.getCmp("csgrid").getStore();
		var B=store.baseParams||{};
		B["isToday"]=1;
		B["crmAccount"]=crmAccount;
		store.baseParams = B;
		store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	},
	isNew:function(crmAccount){
		var store=Ext.getCmp("csgrid").getStore();
		var B=store.baseParams||{};
		B["isNew"]=1;
		B["crmAccount"]=crmAccount;
		store.baseParams = B;
		store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	},
	isFirst:function(crmAccount){
		var store=Ext.getCmp("csgrid").getStore();
		var B=store.baseParams||{};
		B["isFirst"]=1;
		B["crmAccount"]="";
		store.baseParams = B;
		store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	},
	isLost:function(crmAccount){
		var store=Ext.getCmp("csgrid").getStore();
		var B=store.baseParams||{};
		B["isLost"]=1;
		B["crmAccount"]=crmAccount;
		store.baseParams = B;
		store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	},
	isAll:function(crmAccount){
		var store=Ext.getCmp("csgrid").getStore();
		var B=store.baseParams||{};
		B["crmAccount"]=crmAccount;
		store.baseParams = B;
		store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
	}
});

/**
 * 管理分配客户信息
 * */
ast.ast1949.trust.crm.assign=function(grid, oldassignArr, cs, csname){
	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
	if(oldassignArr.length<=0){
		return false;
	}
	Ext.MessageBox.confirm(Context.MSG_TITLE, "您确定要将这 <b>"+oldassignArr.length+"</b> 个客户分配给 <b>"+csname+"</b> 吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<oldassignArr.length;i++){
        	
        	Ext.Ajax.request({
				url:Context.ROOT+Context.PATH+"/trust/crm/assign.htm",
				params:{
					"companyId":oldassignArr[i].companyId,
					"crmAccount":cs
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						ast.ast1949.utils.Msg("","已重新分配");
						grid.getStore().reload();
					}else{
						
					}
				},
				failure:function(response,opt){
					ast.ast1949.utils.Msg("","发生错误，客户没有被分配");
				}
			});
        }
    });
};
