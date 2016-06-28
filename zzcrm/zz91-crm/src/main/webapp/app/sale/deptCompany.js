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
	{name:"sale_account",mapping:"saleAccount"},
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
	{name:"regist_status",mapping:"registStatus"},
	{name:"color",mapping:"color"},
	{name:"gmt_block",mapping:"gmtBlock"},
	{name:"receiveTime",mapping:"receiveTime"},
	{name:"sendTime",mapping:"sendTime"},
	{name:"memberCode",mapping:"memberCode"}
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
				url:Context.ROOT + "/sale/deptcompany/queryComp.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header:"操作",
			dataIndex:"id",
			width:40,
			sortable:true,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var ds=record.get("disable_status");
				var account=record.get("sale_account");
				var url = "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?id="+value+"&disable_status="+ds+"&account="+account+"' target='_blank'>联系</a>";
				return url ;
			}
		},{
			header:"cid",
			dataIndex:"cid",
			hidden:true
		},{
			header:"会员编号",
			dataIndex:"memberCode",
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
			header:"接收询盘",
			width:60,
			dataIndex:"receiveTime",
			sortable:true,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = "";
				if(value){
					returnvalue="<font style='color:red;'>有</font>"
				}else{
					returnvalue="无"
				}
				return returnvalue;
			}
		},{
			header:"发送询盘",
			width:60,
			dataIndex:"sendTime",
			sortable:true,
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
			header:"销售账号",
			dataIndex:"sale_account",
			width:70,
			sortable:true,
			hidden:true
		},{
			header:"销售人员",
			dataIndex:"sale_name",
			width:70,
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
			header:"所属行业",
			dataIndex:"industry_name",
			width:90,
			sortable:true
		},{
			header:"联系人",
			dataIndex:"name",
			width:70,
			sortable:true
		},{
			header:"邮箱",
			dataIndex:"email",
			width:70,
			sortable:true
		},{
			header:"账号",
			dataIndex:"account",
			sortable:true,
			hidden:true
		},{
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
			header:"自动公海天数",
			dataIndex:"day",
			width:100,
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
			header : "自动公海时间",
			dataIndex : "gmt_block",
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
			header : "公司名称",
			dataIndex : "cname",
			sortable : true,
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
			header : "最近联系时间",
			dataIndex : "gmt_contact",
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
			header : "登录次数",
			dataIndex : "login_count",
			width:60,
			sortable : true
		},{
			header:"座机区号",
			dataIndex:"phone_area",
			width:70,
			sortable:true
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
				items:[{
					text:"确定放入废品池",
					id:"put-waste",
					iconCls:"down16",
					handler:function(btn){
						validBox();
						grid.disableStatus(_store,1);
					}
				},{
					text:"取消放入废品池",
					id:"cancel-waste",
					iconCls:"up16",
					handler:function(btn){
						validBox();
						grid.disableStatus(_store,0);
					}
				},{
					text:"放入公海",
					id:"put-sea",
					iconCls:"delete16",
					handler:function(btn){
						validBox();
						var row=grid.getSelectionModel().getSelections();
						for(var i=0;i<row.length;i++){
							if (row[i].get("disable_status")==1){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : "当前客户为废品池状态,不能放入公海!",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
								return null;
							}else{
								grid.autoBlock(_store);
							}
						}
					}
				},{
					text:"审核",
					id:"play",
					iconCls:"play16",
					handler:function(btn){
						validBox();
						grid.check(_store);
					}
				},"->",{
					xtype:"checkbox",
					boxLabel:"只显示已审核客户",
					inputValue:"1",
					handler:function(btn){
							var B=_store.baseParams||{};
							if(btn.getValue()){
								B["registStatus"]=undefined;
							}else{
								B["registStatus"]="2";
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}});
						}
				},"->",{
					xtype:"numberfield",
					width:100,
					emptyText:"联系次数(数值)",
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
					valueField:"value",
					displayField:"name",
					editable: false,
					width:100,
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
					hiddenId:"contactStatus",
					hiddenName:"contactStatus",
					emptyText:"筛选条件",
					valueField:"value",
					displayField:"name",
					editable: false,
					width:100,
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
				},"->",{
					xtype:"textfield",
					width:100,
					emptyText:"销售人员搜索",
					listeners:{
						"change":function(field,newValue,oldValue){
							grid.getStore().baseParams["saleName"]=newValue;
							grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}
				]
			}),
			bbar:com.zz91.utils.pageNav(_store),
			listeners:{
				"rowdblclick":function(g, rowIndex, e){
					var row=grid.getSelectionModel().getSelected();
					if(typeof(row)=="object"){
						window.open(Context.ROOT+"/sale/mycompany/contactDetails.htm?id="+row.get("id"));
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
	autoBlock:function(store){
		var rows=this.getSelectionModel().getSelections();
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定放入公海?",function(btn){
			if(btn!="yes"){
				return ;
			}
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/deptcompany/updateAutoBlack.htm",
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
	disableStatus:function(store,flag){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(rows[i].get("disable_status") != 1){
				com.zz91.utils.Msg("提示","当前客户不为废品池状态,客户["+rows[i].get("cname")+"]操作无效!");
			} else {
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/deptcompany/checkStatusAndUpdateCtype.htm",
					params:{"id":rows[i].get("id"),"flag":flag},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							store.reload();
							com.zz91.utils.Msg("提示",MESSAGE.operateSuccess);
						}else{
							Ext.MessageBox.show({
								title:MESSAGE.title,
								msg : "操作失败!",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
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
	},
	check:function(store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(rows[i].get("regist_status") == 1){
				com.zz91.utils.Msg("提示","当前客户已审核,对客户["+rows[i].get("cname")+"]操作无效!");
			} else {
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/deptcompany/checkRegistStatus.htm",
					params:{"id":rows[i].get("id"),"memberCode":rows[i].get("memberCode")},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							store.reload();
							com.zz91.utils.Msg("提示",MESSAGE.operateSuccess);
						}else{
							Ext.MessageBox.show({
								title:MESSAGE.title,
								msg : "操作失败!",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
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
					"oldSaleName":assignArr[i].oldSaleName
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