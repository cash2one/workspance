Ext.namespace("com.zz91.ep.crmLog");

com.zz91.ep.crmLog.NOTEFIELD=[
	{name:"id",mapping:"id"},
	{name:"saleDept",mapping:"saleDept"},
	{name:"saleAccount",mapping:"saleAccount"},
	{name:"saleName",mapping:"saleName"},
	{name:"cid",mapping:"cid"},
	{name:"star",mapping:"star"},
	{name:"situation",mapping:"situation"},
	{name:"remark",mapping:"remark"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"},
	{name:"cname",mapping:"cname"},
]

com.zz91.ep.crmLog.phoneNoteGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root : "records",
			totalProperty:'totals',
			fields: com.zz91.ep.crmLog.NOTEFIELD,
			url:Context.ROOT + "/sale/mycompany/queryCrmLogByToday.htm",
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			dataIndex : "id",
			hidden:true
		},{
			header : "公司Id",
			dataIndex : "cid",
			hidden:true
		},{
			header : "公司名称",
			dataIndex : "cname",
			width:230,
			sortable : true,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var cid=record.get("cid");
				var returnValue=value;
				if (returnValue=="" || returnValue==null) {
					returnValue="公司名称暂无"
				}
				var url = "<a href='"+Context.ROOT+"/sale/mycompany/contactDetails.htm?visbile=5&id="+cid+"' target='_blank'>"+returnValue+"</a>";
				return url ;
			}
		},{
			header : "联系时间",
			dataIndex:"gmtCreated",
			width:150,
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
			header : "销售",
			dataIndex : "saleName",
			sortable : false
		},{
			header : "客户星级",
			dataIndex:"star",
			width:100,
			sortable : true
		},{
			header : "联系情况",
			dataIndex:"situation",
			sortable : true,
			width:200,
			renderer: function(value, metadata, record, rowIndex,colIndex, store) {
				var returnvalue = value;
				if(value==0) {
					returnvalue="有效联系";
				}
				if(value==1) {
					returnvalue="无进展";
				}
				if(value==2) {
					returnvalue="无人接听";
				}
				if(value==3) {
					returnvalue="号码错误";
				}
				if(value==4) {
					returnvalue="停机";
				}
				if(value==5) {
					returnvalue="关机";
				}
				return returnvalue;
			}
		},{
			header : "联系内容",
			dataIndex:"remark",
			sortable : true,
			width:500,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var table="<table border='0' cellspacing='1' bgcolor='#7386a5' style='width:99%;white-space:normal;'>";
				if(typeof(value)!="undefined" && value!=""){
					table=table+"<tr><td width='50' bgcolor='#ced7e7'>小记信息</td>";
					table=table+"<td bgcolor='#ffffff' style='font-size:15px;'>"+value+"</td></tr>";
				}
				table=table+"</table>";
				return table;
			}
		}]);
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			bbar:com.zz91.utils.pageNav(_store)
		};
		
		com.zz91.ep.crmLog.phoneNoteGrid.superclass.constructor.call(this,c);
	},
	loadNoteGrid:function(account,tdate,disable,star,type){
		// 载入小记信息
		this.getStore().baseParams["account"]=account;
		this.getStore().baseParams["tdate"]=tdate;
		this.getStore().baseParams["disable"]=disable;
		this.getStore().baseParams["star"]=star;	
		this.getStore().baseParams["type"]=type;
	}
});