Ext.namespace("ast.ast1949.crm.cslog");

ast.ast1949.crm.cslog.LogGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:[
				{name:"trustAccount",mapping:"trustAccount"},
				{name:"companyId",mapping:"companyId"},
				{name:"targetId",mapping:"targetId"},
				{name:"targetName",mapping:"targetName"},
				{name:"gmtLastLogin",mapping:"gmtLastLogin"},
				{name:"content",mapping:"content"},
				{name:"gmtVisit",mapping:"gmtVisit"},
				{name:"star",mapping:"star"}
			],
			url:Context.ROOT +Context.PATH+  "/trust/crm/queryCsLogOfCompany.htm",
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var grid = this;
		
		var _cm=new Ext.grid.ColumnModel( [//_sm,
		{//显示时间和联系的公司名称
			header:"联系时间",
			dataIndex:"gmtVisit",
			width:150,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var timeStr = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				var targetName = record.get("targetName");
				var flag=Ext.getCmp("csLog").getStore().baseParams["flag"]
				if("C"===flag){
					return timeStr+"<br />"+"<a href='"+Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+record.get("targetId")+"&companyName="+targetName+"' target='_blank'>"+targetName+"</a>";
				}else{
					return timeStr+"<br />"+"<a href='"+Context.ROOT+Context.PATH+"/trust/edit.htm?id="+record.get("targetId")+"&companyId="+record.get("companyId")+"&companyName="+targetName+"' target='_blank'>"+targetName+"</a>";
				}
			}
		},{//客服姓名
			header:"联系人",
			dataIndex:"trustAccount",
			width:80
		},{//客户等级，联系客户时的客户等级
			header:"星级",
			dataIndex:"star",
			width:50,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return value+"星";
			}
		},{//最近登录时间
			header:"最近登录时间",
			dataIndex:"gmtLastLogin",
			width:125,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
			    if(value!=null){
					var timeStr = Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}else{
					var timeStr='';
				}
				return timeStr;
			}
			
		},{//联系内容
			header:"联系内容",
			dataIndex:"content",
			width:500,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var table="<table border='0' cellpadding='3' cellspacing='1' bgcolor='#7386a5' style='width:99%;white-space:normal;'>";
				table = table + "<tr><td width='50' bgcolor='#ced7e7'>记录</td>";
				table=table+"<td bgcolor='#ffffff'>"+value+"</td></tr>";
				table=table+"</table>";
				return table;
			}
		}]);
		
		var _tbar=config.tbar||[]
			
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
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
			tbar:_tbar
		};
		
		ast.ast1949.crm.cslog.LogGrid.superclass.constructor.call(this,c);
	},
	companyName:"",
	loadLog:function(companyId){
		this.getStore().baseParams["companyId"]=companyId;
		this.getStore().load({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}})
	}
});

