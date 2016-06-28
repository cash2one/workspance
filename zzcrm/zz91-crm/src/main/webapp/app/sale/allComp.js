Ext.namespace("com.zz91.ep.crm");

var ALLCOMP = new function(){
	this.ALLCOMP_GRID = "allcompgrid";
}

com.zz91.ep.crm.allCompField=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"uid",mapping:"uid"},
	{name:"sale_comp_id",mapping:"saleCompId"},
	{name:"email",mapping:"email"},
	{name:"disable_status",mapping:"disableStatus"},
	{name:"register_name",mapping:"registerName"},
	{name:"star",mapping:"star"},
	{name:"name",mapping:"name"},
	{name:"company_type",mapping:"companyType"},
	{name:"cname",mapping:"cname"},
	{name:"mobile",mapping:"mobile"},
	{name:"phone_area",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"gmt_contact",mapping:"gmtContact"},
	{name:"gmt_next_contact",mapping:"gmtNextContact"},
	{name:"contact_able_count",mapping:"contactAbleCount"},
	{name:"sale_name",mapping:"saleName"},
	{name:"login_count",mapping:"loginCount"},
	{name:"gmt_login",mapping:"gmtLogin"},
	{name:"gmt_register",mapping:"gmtRegister"},
	{name:"sys_star",mapping:"sysStar"},
	{name:"address",mapping:"address"},
	{name:"industry_name",mapping:"industryName"},
	{name:"province_name",mapping:"provinceName"},
	{name:"area_name",mapping:"areaName"},
	{name:"day",mapping:"day"},
	{name:"color",mapping:"color"},
	{name:"regist_status",mapping:"registStatus"}
 ];

com.zz91.ep.crm.allCompGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.crm.allCompField,
				url:Context.ROOT + "/sale/common/searchComp.htm",
				autoLoad:true
		});
		
		var grid = this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header:"cid",
			dataIndex:"cid",
			hidden:true
		},{
			header:"关系ID",
			dataIndex:"sale_comp_id",
			hidden:true
		},{
			header : "日志",
			dataIndex:"id",
			width:40,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var url = "<a href='"+Context.ROOT+"/system/log/index.htm?id="+value+"' target='_blank'>日志</a>";
				return url ;
			}
		},{
			header:"自动公海天数",
			dataIndex:"day",
			width:85,
			sortable:false,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var url;
				if (value==0){
					url = "<front style='color:red'>今天掉公海</front>";
				}
				else if(value<0){
					url = "<front style='color:red'>已掉入公海</front>";
				}
				else{
					url = "<front style='color:red'>"+value+"天后掉公海</front>";
					
				}
				return url ;
			}
		},{
			header : "公司名称",
			sortable : true,
			dataIndex : "cname",
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var id=record.get("id");
				var returnValue=value;
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				var url = "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?visbile=5&id="+id+"' target='_blank'>"+returnValue+"</a>";
				return url ;
			}
		},{
			header:"系统星级",
			dataIndex:"sys_star",
			width:60,
			sortable:true,
			hidden:true
		},{
			header:"星级",
			dataIndex:"star",
			width:40,
			sortable:true
		},{
			header:"联系人",
			dataIndex:"name",
			width:70,
			sortable:true
		},{
			header : "手机",
			width : 100,
			dataIndex : "mobile",
			sortable : true
		},{
			header:"座机区号",
			dataIndex:"phone_area",
			width:90,
			sortable:true
		},{
			header : "座机",
			width:80,
			dataIndex : "phone",
			sortable : true
		},{
			header:"邮箱",
			dataIndex:"email",
			width:70,
			sortable:true
		},{
			header:"地址",
			dataIndex:"address",
			width:70,
			sortable:true
		},{
			header : "注册时间",
			sortable : true,
			dataIndex : "gmt_register",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "登录次数",
			width:60,
			dataIndex : "login_count",
			sortable : true
		},{
			header : "最近登录时间",
			sortable : true,
			dataIndex : "gmt_login",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "最近联系时间",
			sortable : true,
			id:"gmtContact",
			dataIndex : "gmt_contact",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header : "下次联系时间",
			sortable : true,
			dataIndex : "gmt_next_contact",
			id:"gmtNextContact",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		},{
			header :"有效联系次数",
			width:85,
			dataIndex:"contact_able_count",
			sortable : true
			
		},{
			header:"省份",
			dataIndex:"province_name",
			width:50,
			sortable:true
		},{
			header:"地区",
			dataIndex:"area_name",
			width:50,
			sortable:true
		},{
			header:"客户来源",
			dataIndex:"register_name",
			width:70,
			sortable:true
		},{
			header:"所属行业",
			dataIndex:"industry_name",
			width:90,
			sortable:true
		},{
			header:"客户类型",
			dataIndex:"company_type",
			width:60,
			sortable:true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==0) {
					returnvalue="普通客户";
				}
				if(value==1) {
					returnvalue="重点客户";
				}
				return returnvalue;
			}
		},{
			header:"废品池",
			dataIndex:"disable_status",
			width:50,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				if(value==0) {
					return '<img src ="'+Context.ROOT+'/themes/icons/Item.True.gif" />';
				}
				if(value==1) {
					return '<img src ="'+Context.ROOT+'/themes/icons/Item.False.gif" />';
				}
			}
		},{
			header:"审核状态",
			dataIndex:"regist_status",
			width:70,
			sortable:true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==0) {
					returnvalue="未审核";
				}
				if(value==1) {
					returnvalue="已审核";
				}
				return returnvalue;
			}
		},{
			header:"销售人员",
			dataIndex:"sale_name",
			width:70,
			sortable:true
		}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					id:"put-box",
					text:"放入个人库",
					iconCls:"down16",
					disabled:true,
					handler:function(b){
						var grid = Ext.getCmp(ALLCOMP.ALLCOMP_GRID);
						var row = grid.getSelectionModel().getSelections();
						var selectedRecord = grid.getSelectionModel().getSelected();
						if(typeof(selectedRecord) == "undefined"){
							Ext.MessageBox.show({
								title: Context.MSG_TITLE,
								msg : "请至少选定一条记录!",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.WARNING
							});
							return ;
						}
						Ext.MessageBox.confirm(Context.MSG_TITLE,"确定放入个人库?",function(btn){
							if(btn!="yes"){
								return ;
							}
							
							for(var i=0;i<row.length;i++){
								Ext.Ajax.request({
									url:Context.ROOT+"/sale/common/createCrmSaleComp.htm",
									params:{
										"id":row[i].get("id"),
										"gmtContact":row[i].get("gmt_contact")==null?0:row[i].get("gmt_contact").time,
										"gmtNextContact":row[i].get("gmt_next_contact")==null?0:row[i].get("gmt_next_contact").time
										
									},
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											com.zz91.utils.Msg(MESSAGE.title, MESSAGE.operateSuccess);
											grid.getStore().reload();
										}else{
											com.zz91.utils.Msg(MESSAGE.title, obj.data);
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
						});
					}
				},{
					id:"put-sea",
					text:"放入公海",
					iconCls:"delete16",
					disabled:true,
					handler:function(btn){
						grid.autoBlock(_store);
					}
				}
//				,{
//					text:"信息导出",
//					iconCls:"down16",
//					handler:function(btn){
//						grid.exportComp();
//					}
//				}
				,"->",{
					xtype:"textfield",
					width:100,
					emptyText:"销售人员搜索",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["saleName"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}]
			}),
			bbar:com.zz91.utils.pageNav(_store),
			viewConfig: { 
	            getRowClass : function(record, rowIndex, p, store){ 
	                if(record.get("color")>=0 && record.get("color")<=3){
	                    return 'color-row'; 
	                }
	            } 
	        }
		};
		
		com.zz91.ep.crm.allCompGrid.superclass.constructor.call(this,c);
	},
	exportComp:function(){
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定导出数据?",function(btn){
			if(btn!="yes"){
				return ;
			}else{
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/common/exportData.htm",
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							com.zz91.utils.Msg("提示","数据已导出");
						}else{
							com.zz91.utils.Msg("提示","数据已导出");
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
		});
	},
	autoBlock:function(store){
		var rows=this.getSelectionModel().getSelections();
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定放入公海?",function(btn){
			if(btn!="yes"){
				return ;
			}
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/common/updateAutoBlack.htm",
					params:{"id":rows[i].get("id")},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							store.reload();
							com.zz91.utils.Msg("提示",MESSAGE.operateSuccess);
						}else{
							com.zz91.utils.Msg("提示",MESSAGE.operateSuccess);
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
		});
	},
	loadComp:function(loginCount,ctype,status,disableStatus,saleStatus,repeatId,star){
		if (loginCount!=null){
			this.getStore().baseParams["loginCount"]=loginCount;
		}
		if (ctype!=null){
			this.getStore().baseParams["ctype"]=ctype;
		}
		if (status!=null){
			this.getStore().baseParams["status"]=status;
		}
		if (disableStatus!=null){
			this.getStore().baseParams["disableStatus"]=disableStatus;
		}
		if (saleStatus!=null){
			this.getStore().baseParams["saleStatus"]=saleStatus;
		}
		if (repeatId!=null){
			this.getStore().baseParams["repeatId"]=repeatId;
		}
		if (star!=null){
			this.getStore().baseParams["star"]=star;
		}
	}
});