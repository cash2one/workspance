/*
 * 短信验证码
 */
Ext.namespace("ast.feiliao91.admin.sms")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

ast.feiliao91.admin.sms.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			}
		,{
				header:"公司名称",
	            sortable:true,
	            width:150,
	            dataIndex:"companyName",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value){
	                    return value;
	                }else{
	                    return "";
	                }
				}
			},
			{
				header:"目标名称",
	            sortable:true,
	            width:150,
	            dataIndex:"targetName",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	var _url="";
	            	if(value!=null){ 
	                    return value;
	                }else{
	                    return "";
	                }
				}
			},{
				header:"验证码",
	            sortable:true,
	            width:150,
	            dataIndex:"vcode",
			},{
				header:"验证类型",
	            sortable:true,
	            dataIndex:"targetType",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value=="1000"){
	                    return "手机注册验证";
	                }else if(value=="1001"){
	                    return "邮箱认证";
	                }
				}
			},{
				header:"是否验证通过",
	            sortable:true,
	            dataIndex:"isValidate",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value=="0"){
	                    return "未验证";
	                }else if(value=="1"){
	                    return "已通过";
	                }
				}
			},{
				header:"发送时间",
	            sortable:true,
	            width:130,
	            dataIndex:"gmtCreated",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
			},{
				header:"是否注册成功",
	            sortable:true,
	            dataIndex:"isRegisterSucc",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value==0){
	                    return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.False.gif" />';
	                }else if(value==1){
	                    return '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.True.gif" />';
	                }
				}
			},{
				header:"注册成功时间",
	            sortable:true,
	            width:130,
	            dataIndex:"registerSuccTime",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value){
	            		return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	            	}else{
	            		return "";
	            	}
	            }
			},{
				header:"注册用时",
	            sortable:true,
	            width:130,
	            dataIndex:"registerUsedTime",
	            renderer:function(value,m,record,ridx,cidx,store,view){
	            	if(value){
	            		return ast.feiliao91.admin.sms.convertTime(value);
	            	}else{
	            		return "";
	            	}
	            }
			}
		]);
		
		var con={
				iconCls:"icon-grid",
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
				tbar:this.mytoolbar,
				bbar:new Ext.PagingToolbar({
					pageSize : Context.PAGE_SIZE,
					store : _store,
					displayInfo: true,
					displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
					emptyMsg : '没有可显示的记录',
					beforePageText : '第',
					afterPageText : '页,共{0}页',
					paramNames : {start:"startIndex",limit:"pageSize"}
				}),
			};
		ast.feiliao91.admin.sms.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"companyValidate.id"},
		{name:"companyName",mapping:"companyName"},
		{name:"targetName",mapping:"companyValidate.targetName"},
		{name:"vcode",mapping:"companyValidate.vcode"},
		{name:"targetType",mapping:"companyValidate.targetType"},
		{name:"isValidate",mapping:"companyValidate.isValidate"},
		{name:"gmtCreated",mapping:"companyValidate.gmtCreated"},//创建时间
		{name:"isRegisterSucc",mapping:"isRegisterSucc"},//是否注册成功标志
		{name:"registerSuccTime",mapping:"registerSuccTime"},//注册成功时间
		{name:"registerUsedTime",mapping:"registerUsedTime"},//注册用时
	]),
	mytoolbar:["-",
				"公司名称",{
		   	   		xtype:"textfield",
		   	   		id:"compName",
		   	   		name:"compName",
		   	   		width:100
		   	   	},"-","目标号",{
			   	   	xtype:"textfield",
		   	   		id:"targetNameSearch",
		   	   		name:"targetNameSearch",
		   	   		width:100
		   	   	},"-",{	
		   	   		header:"查询",
		   	   		iconCls:"query",
		   	   		handler:function(){
		   		   		var grid = Ext.getCmp(_C.RESULT_GRID);
		   		   		var B=grid.store.baseParams;
		   		   		B=B||{};
		   		   		B["compName"] = Ext.get("compName").dom.value;
		   		   		B["targetName"] = Ext.get("targetNameSearch").dom.value;
		   		   		grid.store.baseParams = B;
		   	   			grid.store.reload({
		   	   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
		   	   			})
		   	   		}
		   	   	},
	           "->","-",
	           {
		   			xtype:"checkbox",
					boxLabel:"分组",
					id:"isGroupBy",
					listeners:{
						"check":function(field,newvalue,oldvalue){
							var grid = Ext.getCmp(_C.RESULT_GRID);
							grid.searchByIsGroupBy();
						}
					}
		   		}
	           ],
	listUrl:Context.ROOT+Context.PATH+"/admin/sms/queryList.htm",
	searchByIsGroupBy:function(){
		var B=this.getStore().baseParams||{};
		if(Ext.getCmp("isGroupBy").getValue()){
			B["isGroupBy"]=1;
		}else{
			B["isGroupBy"]=null;
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	}
});

ast.feiliao91.admin.sms.convertTime = function(second){
	if(second<60){
		return second+"秒"
	}else if(second<3600){
		sec = second%60;
		min = parseInt(second/60);
		return min+"分"+sec+"秒";
	}else{
		return "大于1小时";
	}
}