Ext.namespace("com.zz91.ep.crm");

var SEACOMP = new function(){
	this.SEACOMP_GRID = "seacompgrid";
}

com.zz91.ep.crm.seaField=[
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
	{name:"contact_able_count",mapping:"contactAbleCount"},
	{name:"mobile",mapping:"mobile"},
	{name:"phone_area",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"gmt_contact",mapping:"gmtContact"},
	{name:"gmt_next_contact",mapping:"gmtNextContact"},
	{name:"sale_name",mapping:"saleName"},
	{name:"login_count",mapping:"loginCount"},
	{name:"gmt_login",mapping:"gmtLogin"},
	{name:"gmt_register",mapping:"gmtRegister"},
	{name:"sys_star",mapping:"sysStar"},
	{name:"address",mapping:"address"},
	{name:"industry_name",mapping:"industryName"},
	{name:"province_name",mapping:"provinceName"},
	{name:"area_name",mapping:"areaName"},
	{name:"color",mapping:"color"},
	{name:"regist_status",mapping:"registStatus"}
//	{name:"account",mapping:"account"},
//	{name:"sale_account",mapping:"saleAccount"},
	
//	{name:"success_status",mapping:"successStatus"},
//	{name:"contact_count",mapping:"contactCount"},
//	{name:"contact_disable_count",mapping:"contactDisableCount"},
//	{name:"phone_country",mapping:"phoneCountry"},
//	{name:"sale_dept",mapping:"saleDept"},
//	{name:"gmt_created",mapping:"gmtCreated"},
//	{name:"disable_contact",mapping:"disableContact"},
//	{name:"fax_country",mapping:"faxCountry"},
//	{name:"fax_area",mapping:"faxArea"},
//	{name:"fax",mapping:"fax"},
//	{name:"address_zip",mapping:"addressZip"},
//	{name:"sex",mapping:"sex"},
//	{name:"business_name",mapping:"businessName"},
//	{name:"drag_order_count",mapping:"dragOrderCount"},
//	{name:"destroy_order_count",mapping:"destroyOrderCount"}
 ];

com.zz91.ep.crm.seaCompGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.crm.seaField,
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
			header:"日志",
			dataIndex:"id",
			width:40,
			sortable:true,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var url = "<a href='"+Context.ROOT+"/system/log/index.htm?id="+value+"' target='_blank'>日志</a>";
				return url ;
			}
		},{
			header : "公司名称",
			dataIndex : "cname",
			sortable : true,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var cid=record.get("id");
				var returnValue=value;
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				var url = "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?visbile=5&id="+cid+"' target='_blank'>"+returnValue+"</a>";
				return url ;
			}
		},{
			header:"系统星级",
			dataIndex:"sys_star",
			width:70,
			sortable:true,
			hidden:true
		},{
			header:"星级",
			dataIndex:"star",
			width:50,
			sortable:true
		},{
			header : "注册时间",
			dataIndex : "gmt_register",
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
			header : "登录次数",
			dataIndex : "login_count",
			width:60,
			sortable : true
		},{
			header : "最近登录时间",
			dataIndex : "gmt_login",
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
			header : "最近联系时间",
			dataIndex : "gmt_contact",
			id:"gmtContact",
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
			header : "下次联系时间",
			dataIndex : "gmt_next_contact",
			id:"gmtNextContact",
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
			header :"有效联系次数",
			dataIndex:"contact_able_count",
			width:85,
			sortable : true
		},{
			header:"地址",
			dataIndex:"address",
			width:70,
			sortable:true
		},{
			header:"所属行业",
			dataIndex:"industry_name",
			width:70,
			sortable:true
		},{
			header:"联系人",
			dataIndex:"name",
			width:70,
			sortable:true
		},{
			header : "手机",
			dataIndex : "mobile",
			width : 100,
			sortable : true
		},{
			header:"座机区号",
			dataIndex:"phone_area",
			width:70,
			sortable:true
		},{
			header : "座机",
			dataIndex : "phone",
			width:80,
			sortable : true
		},{
			header:"邮箱",
			dataIndex:"email",
			width:70,
			sortable:true
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
			width:70
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
					text:"放入个人库",
					iconCls:"down16",
					handler:function(b){
						var grid = Ext.getCmp(SEACOMP.SEACOMP_GRID);
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
				},"->",{
					xtype:"textfield",
					width:100,
					emptyText:"销售人员搜索",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["clSaleName"]=newValue;
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
		
		com.zz91.ep.crm.seaCompGrid.superclass.constructor.call(this,c);
	},
	//公司库类型
	loadCompany:function(ctype){
		if (ctype!=null){
			this.getStore().baseParams["ctype"]=ctype;
		}
	}
});