Ext.namespace("ast.ast1949.phone.mothcallfee");

ast.ast1949.phone.mothcallfee.INFOFIELD=[
	{name:"id",mapping:"phone.id"},
	{name:"company_id",mapping:"phone.companyId"},
	{name:"account",mapping:"phone.account"},
	{name:"tel",mapping:"phone.tel"},
	{name:"front_tel",mapping:"phone.frontTel"},
	{name:"keywords",mapping:"phone.keywords"},
	{name:"amount",mapping:"phone.amount"},
	{name:"balance",mapping:"phone.balance"},
	{name:"gmt_open",mapping:"phone.gmtOpen"},
	{name:"sumCallFee",mapping:"sumCallFee"},
	{name:"sumClickFee",mapping:"sumClickFee"},
	{name:"email",mapping:"companyAccount.email"},
	{name:"contact",mapping:"companyAccount.contact"},
	{name:"name",mapping:"company.name"},
	{name:"sumAllFee",mapping:"sumAllFee"},
	{name:"missCall",mapping:"missCall"},
	{name:"allPhone",mapping:"allPhone"},
	{name:"phoneRate",mapping:"phoneRate"},
	{name:"sumCallClickFee",mapping:"sumCallClickFee"},
	{name:"pv",mapping:"pv"},
	{name:"c.sumEveCallFee",mapping:"sumEveCallFee"},
	{name:"sort",mapping:"phone.sort"}
];
ast.ast1949.phone.mothcallfee.PHONEINFOFIELD=[
	{name:"id",mapping:"id"},
	{name:"company_id",mapping:"companyId"},
	{name:"account",mapping:"account"},
	{name:"tel",mapping:"tel"},
	{name:"front_tel",mapping:"frontTel"},
	{name:"keywords",mapping:"keywords"},
	{name:"amount",mapping:"amount"},
	{name:"mobile",mapping:"mobile"},
	{name:"gmt_open",mapping:"gmtOpen"},
	{name:"sort",mapping:"sort"},
	{name:"svrEnd",mapping:"svrEnd"}
];

ast.ast1949.phone.mothcallfee.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header:"生意管家",
				width:70,
				dataIndex : "contact",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					if(record.get("company_id")!=0){
						var v1 = "<a href='"+Context.ROOT+Context.PATH+"/crm/company/adminmyrc.htm?account="+encodeURIComponent(record.get("account"))+"' target='_blank'>"+value+"</a> "
					}else{
						var v1="";
					}
					if (record.get("isLave")=="1") {
						v1 = v1 + "<span style='color:red'>(余额为零)</span>"
					}
					return v1;
				}
			},{
				header:"crm库",
				width:90,
				dataIndex : "email",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					if(record.get("company_id")!=0){
						var v1 = "<a href='http://192.168.2.2/admin1/crmlocal/crm_cominfoedit.asp?idprod="+encodeURIComponent(record.get("company_id"))+"' target='_blank'>"+value+"</a> "
					}else{
						var v1="";
					}
					
					return v1;
				}
			},{
				header:"门市部",
				width:80,
				dataIndex : "name",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					if(record.get("company_id")==0){
						 var v1="";
					}else if(value!=null){
						var v1 = "<a href='http://www.zz91.com/ppc/index"+encodeURIComponent(record.get("company_id"))+".htm' target='_blank'>"+value+"</a> "
					}else{
						var v1 = "<a href='http://www.zz91.com/ppc/index"+encodeURIComponent(record.get("company_id"))+".htm' target='_blank'>个体经营</a> "
					}
					return v1;
				}
			},{
				header : "公司ID",
				name : "company_id",
				id : "company_id",
				hidden:true,
				dataIndex:"company_id"
			},{
				header : "展示号码",
				width : 120,
				dataIndex : "front_tel",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
				    if(record.get("company_id")!=0){
				    		var v1 ="<a href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+record.get("company_id")+"' target='_blank'>[管理]</a>"+value;
				    	}	
					return v1;
				}
				
			},{
				header : "主营业务",
				width : 220,
				dataIndex : "keywords"
			},{
				header : "总金额",
				width : 70,
				sortable : true,
				dataIndex : "amount",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					return  value + "元";
				}
			},{
				header : "通话明细",
				width : 70,
				dataIndex : "sumCallFee",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
				
				    if(value!=null){
				    	if(record.get("company_id")!=0){
				    		var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/phoneLog.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a>元";
				    	}else{
				    		var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/phoneLog.htm?companyId="+record.get("company_id")+"' target='_blank'>明细</a>";
				    	//	var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/allPhoneLogFee.htm' target='_blank'>"+value+"</a>元";
				    	}	
						
					}else{
						var v1 = "(空)";
					}
					return v1;
				}
			},{
				header : "查看明细",
				width : 70,
				dataIndex : "sumClickFee",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					
					if(value!=null){
						
						if(record.get("company_id")!=0){
							var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/clickLog.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a>元";
						}else{
							var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/clickLog.htm?companyId="+record.get("company_id")+"' target='_blank'>点击明细</a>";
							//var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/allClickLog.htm' target='_blank'>"+value+"</a>元";
						}
					}else{
						var v1 = "(空)"; 
					}
					return v1;
				}
			},{
				header : "未接明细",
				width : 70,
				dataIndex : "sumCallClickFee",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					if(value!=""){
						
						if(record.get("company_id")!=0){
							var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/callClickLog.htm?companyId="+record.get("company_id")+"' target='_blank'>"+value+"</a>元";
						}else{
							var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/callClickLog.htm?companyId="+record.get("company_id")+"' target='_blank'>未接明细</a>";
						}
					}else{
						var v1 = "(空)"; 
					}
					return v1;
				}
			},{
				header : "总计",
				width : 100,
				dataIndex : "sumAllFee",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					if(value!=null){
						if(record.get("company_id")!=0){
							var v1=value+"元";
						}else{
							var v1=value+"元"+"<a href='"+Context.ROOT+Context.PATH+"/phone/phoneCost.htm' target='_blank'>成本</a>";
						}
						
							
					}else{
						var v1="(空)";
					}
					return v1;
				}
			},{
				header : "上月电话消费",
				width : 80,
				sortable : true,
				dataIndex : "c.sumEveCallFee",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					return  value + "元";
				}
			},{
			  	 header : "未接电话",
			  	 width : 60,
			  	 sortable : true,
			  	 dataIndex : "missCall"
		    },{
			  	 header : "电话总量",
			  	 width : 60,
			  	 sortable :true,
			  	 dataIndex : "allPhone"
			},{
			  	 header : "接听率",
			  	 width : 60,
			  	 sortable : true,
			  	 dataIndex :"phoneRate",
			  	 renderer: function(value,metadata,record,rowIndex,colIndex,store){
			  	     var val=value*100;
					 return  val.toFixed(2)+"%";
				}
			},{
				header : "pv",
				width : 70,
				dataIndex : "pv",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					if(record.get("company_id")!=0){
						var  v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/ppcFlow.htm?cid="+record.get("company_id")+"' target='_blank'>"+value+"</a>";
					}else{
						if(record.get("company_id")!=0 && value==0){
							var  v1 =value;
						}else{
							var v1 = "<a href='"+Context.ROOT+Context.PATH+"/phone/ppcFlow.htm?cid="+record.get("company_id")+"' target='_blank'>pv明细</a>";;
						}
						
					}
					return v1;
				}
			},{
				header : "开通时间",
				width : 140,
				sortable : true,
				dataIndex : "gmt_open",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			},{
				header : "排序",
				sortable : true,
				dataIndex : "sort"
			}
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryPhoneCallFee.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.mothcallfee.INFOFIELD,
			url: storeUrl,
			autoLoad:true
		});
		var tbar = [
//		{
//			text:"添加新号码",
//			iconCls:"add",
//			handler:function(){
//				ast.ast1949.phone.mothcallfee.updatePhoneInfo(0);
//			}
//		}
		{
			text:"写小计",
			iconCls:"add",
			handler:function(){
//				ast.ast1949.phone.mothcallfee.addCRMLog(row.get("id"));
				var row=grid.getSelectionModel().getSelected();
				window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+row.get("company_id"));
			}
		}
		,"-",{
			text:"修改",
			iconCls:"edit",
			handler:function(){
				var row=grid.getSelectionModel().getSelected();
				ast.ast1949.phone.mothcallfee.updatePhoneInfo(row.get("id"))
			}
		},"-",{
			text:"询盘群发",
			iconCls:"copy",
			handler:function(){
				var row=grid.getSelectionModel().getSelected();
				if(row==null){
					alert("请选择一家公司");
					return false;
				}
				var url = Context.ROOT+Context.PATH+"/phone/batchInquiry.htm?companyId="+row.get("company_id")+"&keyword="+encodeURI(row.get("keywords"));
				window.open(url);
			}
		},"-",{
			iconCls:"add",
			text:"导出excel",
			handler:function(btn){
    			var row=grid.getSelectionModel().getSelected();
				var from=Ext.get("targetFrom").getValue();
				var to=Ext.get("targetTo").getValue();
				Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
					if(btn!="yes"){
						return ;
					}else{
						Ext.Ajax.request({
							url: window.open(Context.ROOT+Context.PATH+ "/phone/exportData.htm?"+"from="+from+"&to="+to),
						});
					}
				});
			}
		},"->","查看月电话时间消费时间：",{
            	id:"targetFrom",
            	xtype:"datefield",
            	format:"Y-m-d"
            },"至",{
            	id:"targetTo",
            	xtype:"datefield",
            	format:"Y-m-d",
            	},{
            	text:"查询",
            	iconCls:"query",
            	handler:function(btn){
            		//TODO 查找数据
            		var targetFrom=Ext.getCmp("targetFrom").getValue();
            		var targetTo=Ext.getCmp("targetTo").getValue();
            			_store.baseParams["from"]=targetFrom;
            			_store.baseParams["to"]=targetTo;
            			_store.reload();
            	
            	} 	
		}];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:7,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
			tbar : tbar,
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
						window.open(Context.ROOT+Context.PATH+"/crm/cs/detail.htm?companyId="+row.get("company_id"));
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
		ast.ast1949.phone.mothcallfee.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null,
	deleteById:function(id){
		var grid = Ext.getCmp("phoneGrid");
		var row = grid.getSelectionModel().getSelected();
		Ext.Ajax.request({
			url: Context.ROOT+Context.PATH+ "/phone/deleteById.htm",
			params:{
				"id":row.get("id"),
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					ast.ast1949.utils.Msg("","信息已成功更新");
					grid.getStore().reload();
				}else{
					ast.ast1949.utils.Msg("","操作失败");
				}
			},
			failure:function(response,opt){
				com.zz91.utils.Msg("","操作失败");
			}
		});
	}
});
/**
 * 重新分配客户信息
 * */
ast.ast1949.phone.mothcallfee.reassign=function(grid, oldassignArr, cs, csname){
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
				url:Context.ROOT+Context.PATH+"/phone/reassignLdb.htm",
				params:{
					"companyId":oldassignArr[i].companyId,
					"csAccount":cs
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

//账户信息修改
ast.ast1949.phone.mothcallfee.updatePhoneInfo=function(id,companyId){
	var form=new ast.ast1949.phone.mothcallfee.UpdatePhoneInfoForm({
	});
	
	form.loadInit(id,companyId);
	
	var win = new Ext.Window({
		id:"updatephonewin",
		title:"400帐号开通/修改",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.phone.mothcallfee.UpdatePhoneInfoForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				fieldLabel:"公司ID",
				name:"companyId",
				id:"company_id",
				readOnly:true
			},{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"公司帐号",
				name:"account",
				id:"account",
				readOnly:true
			},{
				fieldLabel:"400号码(输入)",
				name:"tel",
				id:	"tel"
			},{
				xtype : "combo",
				fieldLabel:"400号码库(选用)",
				hiddenName:"selectTel",
				hiddenId:"selectTel",
				displayField:"tel",
				valueField : "tel",
				fieldLabel : "可选400号码",
				triggerAction: 'all',
				forceSelection : true,
				editable :false,
				store:new Ext.data.JsonStore({
					autoLoad:true,
					url:Context.ROOT+Context.PATH + "/phone/queryPhoneLibaryForOpen.htm",
					fields:["tel","tel"]
				})
			},{
				fieldLabel:"展示400号码",
				name:"frontTel",
				id:	"front_tel"
			},{
				fieldLabel:"总金额",
				name:"amount",
				id:	"amount"
			},{
				fieldLabel:"匹配关键字",
				name:"keywords",
				id:"keywords"
			},{
				fieldLabel:"排序",
				name:"sort",
				id:"sort"
			},{
				fieldLabel:"绑定号码",
				name:"mobile",
				allowBlank : false,
				itemCls:"required"
			},{
				xtype:"datefield",
				fieldLabel:"服务费到期时间",
				format:"Y-m-d",
				name : "svrEnd"
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("phoneGrid");
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/phone/add.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updatephonewin").close();
								grid.getStore().reload();
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		ast.ast1949.phone.mothcallfee.UpdatePhoneInfoForm.superclass.constructor.call(this,c);
	},
	loadInit:function(id,companyId){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		var _url = "";
		if(companyId!=undefined){
			_url = "&companyId="+companyId;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.phone.mothcallfee.PHONEINFOFIELD,
			url : Context.ROOT+Context.PATH+"/phone/init.htm?id="+id+_url, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
});

