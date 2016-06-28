Ext.namespace("com.zz91.ep.crm");

var MYCOMP = new function(){
	this.MYCOMP_GRID = "mycompgrid";
}

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
	{name:"color",mapping:"color"},
	{name:"regist_status",mapping:"registStatus"},
	{name:"receiveTime",mapping:"receiveTime"},
	{name:"sendTime",mapping:"sendTime"}
 ];

com.zz91.ep.crm.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totals',
				remoteSort:true,
				fields:com.zz91.ep.crm.Field,
				url:Context.ROOT + "/sale/mycompany/queryComp.htm",
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
				var url = "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?id="+value+"&disable_status="+ds+"&account="+account+"' target='_blank'>联系</a>";
				return url ;
			}
		},{
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
				items:[{
					text:"放入公海",
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
					text:"设置为重点",
					iconCls:"fav16",
					handler:function(btn){
						validBox();
						grid.setStress(1,_store);
					}
				},{
					text:"取消重点",
					iconCls:"pause16",
					handler:function(btn){
						validBox();
						grid.setStress(0,_store);
					}
				},{
					text:"放入废品池",
					iconCls:"down16",
					handler:function(btn){
						validBox();
						grid.disableStatus(_store);
					}
				},{
					text:"设为非销售",
					iconCls:"down16",
					handler:function(btn){
						validBox();
						grid.saleStatus(_store);
					}
				},{
					text:"放入未激活",
					iconCls:"down16",
					handler:function(btn){
						validBox();
						grid.noActive(_store);
					}
				},{
					text:"放入到款确认单",
					iconCls:"down16",
					handler:function(btn){
						window.open(Context.EPADMIN+"/crm/open/apply.htm");
					}
				},"->",{
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
			viewConfig: { 
	            showPreview:true, 
	            getRowClass : function(record, rowIndex, p, store){ 
	                if(record.get("color")>=0 && record.get("color")<=3){
	                    return 'color-row'; 
	                }
	            } 
	        }
		};
		com.zz91.ep.crm.Grid.superclass.constructor.call(this,c);
	},
	autoBlock:function(store){
		var rows=this.getSelectionModel().getSelections();
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定放入公海?",function(btn){
			if(btn!="yes"){
				return ;
			}
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/mycompany/updateAutoBlack.htm",
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
	setStress:function(companyType,store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			if(companyType==rows[i].get("company_type")){
				continue;
			};
			
			Ext.Ajax.request({
				url:Context.ROOT+"/sale/mycompany/updateCompanyType.htm",
				params:{"id":rows[i].get("sale_comp_id"),"companyType":companyType},
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
	},
	disableStatus:function(store){
		var rows=this.getSelectionModel().getSelections();
		for(var i=0;i<rows.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/sale/mycompany/updateDisableStatus.htm",
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
	},
	updateCtype:function(store){
		var rows=this.getSelectionModel().getSelections();
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定放入重复库?",function(btn){
			if(btn!="yes"){
				return ;
			}
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/mycompany/updateRepeatCtypeById.htm",
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
	saleStatus:function(store){
		var rows=this.getSelectionModel().getSelections();
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定设为非销售客户?",function(btn){
			if(btn!="yes"){
				return ;
			}
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/mycompany/updateSaleStatusById.htm",
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
	noActive:function(store){
		var rows=this.getSelectionModel().getSelections();
		Ext.MessageBox.confirm(Context.MSG_TITLE,"确定放入未激活客户库?",function(btn){
			if(btn!="yes"){
				return ;
			}
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/mycompany/putNoActiveBox.htm",
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
	//下次联系开始时间,下次联系结束时间,公司类型,拖单/毁单,联系次数,次数查询状态,查询条件(总联系次数),公海次数比较值
	loadCompany:function(nextStart,nextEnd,companyType,dragDestryStatus,contactCount,contactStatus,contactFlag,blockCount,ctype){
		if (nextStart!=null){
			this.getStore().baseParams["gmtNextContactStart"]=nextStart;
		}
		if (nextEnd!=null){
			this.getStore().baseParams["gmtNextContactEnd"]=nextEnd;
		}
		if (companyType!=null){
			this.getStore().baseParams["companyType"]=companyType;
		}
		if (dragDestryStatus!=null){
			this.getStore().baseParams["dragDestryStatus"]=dragDestryStatus;
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
		if(blockCount!=null){
			this.getStore().baseParams["blockCount"]=blockCount;
		}
		if(ctype!=null){
			this.getStore().baseParams["ctype"]=ctype;
		}
	}
});

