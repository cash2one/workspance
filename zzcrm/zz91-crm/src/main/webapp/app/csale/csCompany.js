Ext.namespace("com.zz91.ep.cscrm");

var MYCOMP = new function(){
	this.MYCOMP_GRID = "mycompgrid";
}

com.zz91.ep.cscrm.Field=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"uid",mapping:"uid"},
	{name:"sale_comp_id",mapping:"saleCompId"},
	{name:"account",mapping:"account"},
	{name:"email",mapping:"email"},
	{name:"disable_status",mapping:"disableStatus"},
	{name:"register_name",mapping:"registerName"},
	{name:"star",mapping:"star"},
	{name:"name",mapping:"name"},
	{name:"company_type",mapping:"companyType"},
	{name:"cname",mapping:"cname"},
	{name:"contact_count",mapping:"contactCount"},
	{name:"contact_able_count",mapping:"contactAbleCount"},
	{name:"mobile",mapping:"mobile"},
	{name:"phone_area",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"gmt_contact",mapping:"gmtContact"},
	{name:"gmt_next_contact",mapping:"gmtNextContact"},
	{name:"sale_account",mapping:"saleAccount"},
	{name:"sale_name",mapping:"saleName"},
	{name:"login_count",mapping:"loginCount"},
	{name:"gmt_login",mapping:"gmtLogin"},
	{name:"gmt_register",mapping:"gmtRegister"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"sys_star",mapping:"sysStar"},
	{name:"address",mapping:"address"},
	{name:"industry_name",mapping:"industryName"},
	{name:"province_name",mapping:"provinceName"},
	{name:"area_name",mapping:"areaName"},
	{name:"day",mapping:"day"},
	{name:"regist_status",mapping:"registStatus"},
	{name:"ctype",mapping:"ctype"}
//	{name:"success_status",mapping:"successStatus"},
//	{name:"contact_disable_count",mapping:"contactDisableCount"},
//	{name:"phone_country",mapping:"phoneCountry"},
//	{name:"sale_dept",mapping:"saleDept"},
//	{name:"disable_contact",mapping:"disableContact"},
//	{name:"fax_country",mapping:"faxCountry"},
//	{name:"fax_area",mapping:"faxArea"},
//	{name:"fax",mapping:"fax"},
//	{name:"address_zip",mapping:"addressZip"},
//	{name:"sex",mapping:"sex"},
//	{name:"business_name",mapping:"businessName"},
//	{name:"drag_order_count",mapping:"dragOrderCount"},
//	{name:"destroy_order_count",mapping:"destroyOrderCount"},
 ];

com.zz91.ep.cscrm.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				fields:com.zz91.ep.cscrm.Field,
				url:Context.ROOT + "/csale/cscompany/queryComp.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header:"操作",
			width:40,
			dataIndex:"id",
			sortable:true,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var ds=record.get("disable_status");
				var account=record.get("sale_account");
				var url = "<a href='"+Context.ROOT+"/csale/cscompany/contactDetails.htm?id="+value+"&disable_status="+ds+"&account="+account+"' target='_blank'>联系</a>";
				return url ;
			}
		},{
			header:"cid",
			dataIndex:"cid",
			hidden:true
		},{
			header:"所在库",
			dataIndex:"ctype",
			hidden:true
		},{
			header:"关系ID",
			dataIndex:"sale_comp_id",
			hidden:true
		}
//		,{
//			header : "日志",
//			dataIndex:"id",
//			width:40,
//			renderer:function(value, metaData, record, rowIndex, colIndex, store){
//				var url = "<a href='"+Context.ROOT+"/system/log/index.htm?id="+value+"' target='_blank'>日志</a>";
//				return url ;
//			}
//		}
		,{
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
				var cid=record.get("cid");
				var returnValue=value;
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				var url = "<a href='"+Context.ESITE+"/index"+cid+".htm' target='_blank'>"+returnValue+"</a>";
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
		},{
			header:"销售账号",
			dataIndex:"sale_account",
			width:70,
			sortable:true,
			hidden:true
		}
		]);
		
		var validBox=function(){
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
		};
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:["->",{
					xtype:"checkbox",
					boxLabel:"只显示重点客户",
					inputValue:1,
					handler:function(btn){
							var B=_store.baseParams||{};
							if(btn.getValue()){
								B["companyType"]="1";
							}else{
								B["companyType"]=undefined;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
				},"->",{
					xtype:"checkbox",
					boxLabel:"未联系客户",
					inputValue:1,
					handler:function(btn){
						var B=_store.baseParams||{};
						if (btn.getValue()){
							B["contactStatus"]="1";
							B["contactFlag"]="0";
							B["contactCount"]="0";
						}else{
							B["contactStatus"]="0";
						}
						_store.baseParams = B;
						_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
				},"->",{
					xtype:"numberfield",
					emptyText:"联系次数(数值)",
					width:100,
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["contactCount"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenId:"contactFlag",
					hiddenName:"contactFlag",
					emptyText:"次数查询条件",
					width:100,
					valueField:"value",
					displayField:"name",
					editable: false,
					store:new Ext.data.JsonStore({
						fields:["name","value"],
						 data   : [
						{name:"等于",value:"0"},
						{name:"大于",value:"1"},
						{name:"小于",value:"2"}
						]
					}),
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["contactFlag"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"->",{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenId:"contact_status",
					hiddenName:"contact_status",
					emptyText:"筛选条件",
					width:100,
					valueField:"value",
					displayField:"name",
					editable: false,
					store:new Ext.data.JsonStore({
						fields:["name","value"],
						 data   : [
						{name:"不筛选联系次数",value:"0"},
						{name:"总联系次数",value:"1"},
						{name:"有效联系次数",value:"2"},
						{name:"无效联系次数",value:"3"}
						]
					}),
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["contactStatus"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}
				]
			}),
			bbar:com.zz91.utils.pageNav(_store),
			listeners:{
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
		
		com.zz91.ep.cscrm.Grid.superclass.constructor.call(this,c);
	},
	loadCompany:function(gmtLastContactEnd,nextStart,nextEnd,contactCount,contactStatus,contactFlag,ctype){
		if (gmtLastContactEnd!=null){
			this.getStore().baseParams["gmtLastContactEnd"]=gmtLastContactEnd;
		}
		if (nextStart!=null){
			this.getStore().baseParams["gmtNextContactStart"]=nextStart;
		}
		if (nextEnd!=null){
			this.getStore().baseParams["gmtNextContactEnd"]=nextEnd;
		}
		if (contactCount!=null){
			this.getStore().baseParams["contactCount"]=contactCount;
		}
		if (contactStatus!=null){
			this.getStore().baseParams["contactStatus"]=contactStatus;
		}
		if (contactFlag!=null){
			this.getStore().baseParams["contactFlag"]=contactFlag;
		}
		if (ctype!=null){
			this.getStore().baseParams["ctype"]=ctype;
		}
	}
});

/**
 * 分配高会给客服人员
 * */
com.zz91.ep.cscrm.assign=function(grid, assignArr, csname){
	//一次可以分配多个客户给一个用户，需要最后确认，如确定要将x位客户分配给某某某吗
	if(assignArr.length<=0){
		return false;
	}
	Ext.MessageBox.confirm("信息提示", "您确定要将这 <b>"+assignArr.length+"</b> 个客户分配给 <b>"+csname+"</b> 吗？", function(btn){
        if(btn != "yes"){
                return false;
        }
        for(var i=0;i<assignArr.length;i++){
        	//TODO 分配客户
        	Ext.Ajax.request({
				url:Context.ROOT+"/csale/cscompany/assign.htm",
				params:{
					"id":assignArr[i].saleCompId,
					"cid":assignArr[i].cid,
					"companyType":assignArr[i].companyType,
					"saleAccount":assignArr[i].saleAccount,
					"saleDept":assignArr[i].saleDept,
					"saleName":assignArr[i].saleName,
					"cname":assignArr[i].cname,
					"ctype":assignArr[i].ctype
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						com.zz91.utils.Msg("",MESSAGE.operateSuccess);
						grid.getStore().reload();
					}else{
						//可能是非自己的客户，不能被丢公海
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
};