Ext.namespace("com.zz91.ep.crm");

var DEPTCOMP=function(){
	this.COMP_GRID="compgrid";
};

com.zz91.ep.crm.Field=[
	{name:"id",mapping:"id"},
	{name:"cid",mapping:"cid"},
	{name:"uid",mapping:"uid"},
	{name:"sale_comp_id",mapping:"saleCompId"},
	{name:"account",mapping:"account"},
	{name:"email",mapping:"email"},
	{name:"qq",mapping:"fax"},
	{name:"disable_status",mapping:"disableStatus"},
	{name:"register_name",mapping:"registerName"},
	{name:"sys_star",mapping:"sysStar"},
	{name:"star",mapping:"star"},
	{name:"name",mapping:"name"},
	{name:"cname",mapping:"cname"},
	{name:"mobile",mapping:"mobile"},
	{name:"phone_area",mapping:"phoneArea"},
	{name:"phone",mapping:"phone"},
	{name:"gmt_login",mapping:"gmtLogin"},
	{name:"gmt_register",mapping:"gmtRegister"},
	{name:"address",mapping:"address"},
	{name:"industry_name",mapping:"industryName"},
	{name:"province_name",mapping:"provinceName"},
	{name:"area_name",mapping:"areaName"},
	{name:"regist_status",mapping:"registStatus"},
	{name:"member_code",mapping:"memberCode"},
	{name:"input_account",mapping:"inputAccount"},
	{name:"ctype",mapping:"ctype"}
];

com.zz91.ep.crm.deptGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.crm.Field,
				url:Context.ROOT + "/sale/deptcompany/queryHunMaComp.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				header:"cid",
				dataIndex:"cid",
				hidden:true
			},{
				header:"会员编号",
				dataIndex:"member_code",
				hidden:true
			},{
				header:"关系ID",
				dataIndex:"sale_comp_id",
				hidden:true
			},{
				header:"审核状态",
				dataIndex:"regist_status",
				width:60,
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
				header:"客户来源",
				dataIndex:"register_name",
				width:70,
				sortable:true
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
				header:"所属行业",
				dataIndex:"industry_name",
				width:60,
				sortable:true
			},{
				header:"联系人",
				dataIndex:"name",
				width:60,
				sortable:true
			},{
				header:"qq",
				dataIndex:"qq",
				width:70,
				sortable:true
			},{
				header:"账号",
				dataIndex:"account",
				sortable:true,
				hidden:true
			},{
				hidden:true,
				header:"系统星级",
				dataIndex:"sys_star",
				width:70,
				sortable:true
			},{
				header:"星级",
				dataIndex:"star",
				width:50,
				sortable:true
			},{
				header : "公司名称",
				width:120,
				dataIndex : "cname",
				sortable : true
			},{
				header : "手机",
				dataIndex : "mobile",
				width : 100,
				sortable : true
			},{
				header : "座机",
				dataIndex : "phone",
				width:80,
				sortable : true
			},{
				header:"地址",
				dataIndex:"address",
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
				header : "注册时间",
				dataIndex : "gmt_register",
				sortable : true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header:"座机区号",
				dataIndex:"phone_area",
				width:70,
				sortable:true
			},{
				header:"录入者",
				dataIndex:"input_account",
				width:70,
				sortable:true
			},{
				header:"所在库",
				dataIndex:"ctype",
				width:70,
				hidden:true
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:["-",{
					xtype:"textfield",
					width:100,
					emptyText:"QQ(前匹配)",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["fax"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"-",{
					xtype:"textfield",
					width:160,
					emptyText:"公司名称(前匹配)",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["cname"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"-",{
					xtype:"textfield",
					width:100,
					emptyText:"联系人(全匹配)",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["name"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"-",{
					xtype:"textfield",
					width:100,
					emptyText:"电话或手机",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["phone"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"-",{
					xtype:"textfield",
					width:100,
					emptyText:"导入者(前匹配)",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["inputAccount"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},"-",{
					text:"确认搜索",
					iconCls:"websearch16",
					handler:function(btn){
					}
				},"->",{
					xtype:"checkbox",
					boxLabel:"显示高会",
					handler:function(btn){
							var B=_store.baseParams||{};
							if(btn.getValue()){
								B["memberCode"]="10011001";
							}else{
								B["memberCode"]=undefined;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
				},"-",{
					xtype:"checkbox",
					boxLabel:"显示普会",
					handler:function(btn){
							var B=_store.baseParams||{};
							if(btn.getValue()){
								B["memberCode"]="10011000";
							}else{
								B["memberCode"]=undefined;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
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
			},
			viewConfig: { 
	            getRowClass : function(record, rowIndex, p, store){ 
	                if(record.get("color")>=0 && record.get("color")<=3){
	                    return 'color-row'; 
	                }
	            } 
	        }
		};
		
		com.zz91.ep.crm.deptGrid.superclass.constructor.call(this,c);
	},
	loadCompany:function(star,companyType,disableStatus,registerCode,gmtLastContactEnd,registStatus,ctype,autoBlock,status,nextEnd){
		if (star!=null){
			this.getStore().baseParams["star"]=star;
		}
		if (companyType!=null){
			this.getStore().baseParams["companyType"]=companyType;
		}
		if (disableStatus!=null){
			this.getStore().baseParams["disableStatus"]=disableStatus;
			Ext.getCmp("put-sea").setVisible(false);
		}
		if (registerCode!=null){
			this.getStore().baseParams["registerCode"]=registerCode;
			if (registerCode==0){
				Ext.getCmp("put-sea").setVisible(false);
			}
		}
		if (gmtLastContactEnd!=null){
			this.getStore().baseParams["gmtLastContactEnd"]=gmtLastContactEnd;
		}
		if (registStatus!=null){
			this.getStore().baseParams["registStatus"]=registStatus;
		}
		if (ctype!=null){
			this.getStore().baseParams["ctype"]=ctype;
		}
		if (autoBlock!=null){
			this.getStore().baseParams["autoBlock"]=autoBlock;
			Ext.getCmp("put-sea").setVisible(false);
		}
		if (status!=null){
			this.getStore().baseParams["status"]=status;
		}
		if (nextEnd!=null){
			this.getStore().baseParams["gmtNextContactEnd"]=nextEnd;
		}
	}
});

/**
 * 重新分配客户信息
 * */
com.zz91.ep.crm.reassign=function(grid, assignArr, csname){
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
				url:Context.ROOT+"/sale/deptcompany/reassign.htm",
				params:{
					"id":assignArr[i].saleCompId,
					"cid":assignArr[i].cid,
					"companyType":assignArr[i].companyType,
					"saleAccount":assignArr[i].saleAccount,
					"saleDept":assignArr[i].saleDept,
					"saleName":assignArr[i].saleName,
					"oldSaleName":assignArr[i].oldSaleName,
					"flag":0
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