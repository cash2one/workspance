/*
 * 证书荣誉
 */
Ext.namespace("ast.ast1949.admin.credit.creditfile")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//证书荣誉列表
ast.ast1949.admin.credit.creditfile.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
		
		var _sm = new Ext.grid.CheckboxSelectionModel({	});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header:"公司ID",
				sortable:false,
				dataIndex:"companyId",
				hidden:true
			},{
				header:"审核状态",
				sortable:true,
				dataIndex:"checkStatus",
				width:35,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var str="";
					if(value!=null){
						if(value=="1"){
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
						}else if(value=="2"){
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
						}else{
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
						}
					}
					
					return str;
				}
			}, {
				header:"证书名称",
				sortable:false,
				width:150,
				dataIndex:"fileName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var webstr="<a href='"+Context.ROOT+Context.PATH+"/admin/credit/creditfile/goEsite.htm?companyId="+record.get("companyId")+"' target='_blank' >";
					webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/credit/creditfile/edit.htm?id="+record.get("id")+"&companyId=" +record.get("companyId")+"' target='_blank'>"+value+"</a>";
					return webstr+title;
				}
			}, {
				header:"发证机构",
				sortable:false,
				dataIndex:"organization"
			}, {
				header:"生效日期",
				sortable:false,
				dataIndex:"startTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}, {
				header:"截止日期",
				sortable:false,
				dataIndex:"endTime",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"图片",
				sortable:false,
				width:75,
				dataIndex:"picName",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return "<a href='http://img3.zz91.com/500x500/"+value+"' target='_blank'>查看</a>";
					}
					else{
						return "";
					}
				}
			},{
				header:"公司名称",
				sortable:false,
				width:150,
				dataIndex:"companyName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")=="10051003"){
						val="<img src='"+Context.ROOT+"/images/ldblogo.jpg'width='20px'height='20px' />";
					}else if(record.get("membershipCode")!="10051000"&&record.get("membershipCode")!=""){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					}
					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/credit/creditfile/edit.htm?activeFlg=1&id="+record.get("id")+"&companyId=" +record.get("companyId")+"' target='_blank'>"+value+"</a>";
					return val+title;
				}
			}, {
				header:"发布时间",
				sortable:true,
				dataIndex:"gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}, {
				header:"刷新时间",
				sortable:true,
				dataIndex:"gmtModified",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
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
//			plugins : expander,
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
		/*	listeners:{
				"render":this.buttonQuery
			}*/
		};
		ast.ast1949.admin.credit.creditfile.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
   	    {name:"id",mapping:"creditFile.id"},
   	    {name:"companyId",mapping:"creditFile.companyId"},
   	    {name:"companyName",mapping:"companyName"},
   	    {name:"fileName",mapping:"creditFile.fileName"},
   	    {name:"organization",mapping:"creditFile.organization"},
   	    {name:"startTime",mapping:"creditFile.startTime"},
   	    {name:"endTime",mapping:"creditFile.endTime"},
   	    {name:"fileNumber",mapping:"creditFile.fileNumber"},
   	  /*  {name:"tel",mapping:"creditFile.tel"},
   	    {name:"website",mapping:"creditFile.website"},*/
   	    {name:"picName",mapping:"creditFile.picName"},
   	  /*  {name:"introduction",mapping:"creditFile.introduction"},
   	    {name:"categoryName",mapping:"categoryName"},*/
   	    {name:"checkStatus",mapping:"creditFile.checkStatus"},
	   	{name:"gmtCreated",mapping:"creditFile.gmtCreated"},
	   	{name:"gmtModified",mapping:"creditFile.gmtModified"},
	   	{name:"membershipCode",mapping:"membershipCode"}
   	]),
   	mytoolbar:[{
		text:"编辑",
		iconCls:"edit",
		handler:function(btn){
			var grid = Ext.getCmp(_C.RESULT_GRID);
			var row = grid.getSelections();
			for(var i=0;i<row.length;i++){
				window.open(Context.ROOT+Context.PATH+"/admin/credit/creditfile/edit.htm?id="+row[i].get("id")+"&companyId=" +row[i].get("companyId"));
			}
		}
	},"-",{
			iconCls:"item-true",
			id:"checkButton",
			text:"通过审核",
			handler:function(btn){
				checkButton(1);
			}
		},{
			iconCls:"item-false",
			id:"unCheckButton",
			text:"审核不通过",
			handler:function(btn){
					checkButton(2)
			}
		},"-",
   		"证书名称",{
	   		xtype:"textfield",
	   		id:"fileName",
	   		name:"fileName",
	   		width:100
	   	},"公司名称",{
	   		xtype:"textfield",
	   		id:"companyName",
	   		name:"companyName",
	   		width:100
	   	},"-",{
	   		text:"查询",
	   		iconCls:"query",
	   		handler:function(){
		   		var grid = Ext.getCmp(_C.RESULT_GRID);
		   		var B=grid.store.baseParams;
		   		B=B||{};
		   		B["companyName"] = Ext.get("companyName").dom.value;
		   		B["fileName"] = Ext.get("fileName").dom.value;
		   		grid.store.baseParams = B;
	   			grid.store.reload({
	   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
	   			})
	   		}
	   	},"->",{
			xtype:"checkbox",
			boxLabel:"高会",
			id:"isVip",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchForMembership();
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"普会",
			id:"noVip",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchForMembership();
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"未审核",
			id:"unChecked",
			checked:true,
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"通过",
			id:"beChecked",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"不通过",
			id:"noChecked",
			/*checked:true,*/
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		}
	],
	searchForMembership:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("isVip").getValue()){
			B["isVip"] = 1;
		}else{
			B["isVip"] = undefined;
		}
		if(Ext.getCmp("noVip").getValue()){
			B["isNoVip"] = 1;
		}else{
			B["isNoVip"] = undefined;
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByCheckStatus:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("beChecked").getValue()){
			ary.push(1);
		}
		if(Ext.getCmp("unChecked").getValue()){
			ary.push(0);
		}
		if(Ext.getCmp("noChecked").getValue()){
			ary.push(2);
		}

		B["checkStatus"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByFile:function(companyId){
		var B=this.getStore().baseParams||{};
		B["companyId"] = companyId;
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	}
});

//审核
function checkButton(status){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	
	var success=0;
	var step=0;
	for (var i=0,len = row.length;i<len;i++){
		step++;
		var _id=row[i].get("id");
		var _cid=row[i].get("companyId");
		var _status=row[i].get("checkStatus");
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/credit/creditfile/updateCheckStatus.htm?st="+ Math.random() +
			"&ids=" + _id +
			"&cids=" + _cid +
			"&currents=" + _status +
			"&checkStatus=" + status,
			method : "get",
			scope : this,
			callback : function(options,success,response){
				var res= Ext.decode(response.responseText);
				if(res.success){
					success++;
				}
				if(step==row.length&&success==row.length) {
					ast.ast1949.utils.Msg("","所选记录已成功更新！");
					grid.getStore().reload();
				} else {
					grid.getStore().reload();
				}
			}
		});
	}
};

