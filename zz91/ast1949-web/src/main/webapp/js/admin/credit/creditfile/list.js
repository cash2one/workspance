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
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("checkButton").enable();
	                    Ext.getCmp("unCheckButton").enable();
//	                    Ext.getCmp("removeButton").enable();
	                } else {
	                    Ext.getCmp("checkButton").disable();
	                    Ext.getCmp("unCheckButton").disable();
//	                    Ext.getCmp("removeButton").disable();
	                }
	            }
	        }
		});
//		var expander = new Ext.grid.RowExpander({
//			tpl : new Ext.Template(
//				"<p><b>证书类别:</b>{categoryCode}</p>",
//				"<p><b>证书编号:</b>{fileNumber}</p>",
//				"<p><b>发证机构电话:</b>{tel}</p>",
//				"<p><b>发证机构网址:</b>{website}</p>",
//				"<p><b>证书介绍:</b>{introduction}</p>",
//				"<p><b>图片:</b><img src='"+CreditFileContext.RESOURCE_URL+"/"+CreditFileContext.UPLOAD_MODEL+"/"+"{picName}'/></p>"
//			),
//			listeners:{
//				"beforeexpand":function(expand,record,body,rowindex){
//					if(!record.get("isgetdata")){
//						$.ajax({
//							type:"GET",
//							url:Context.ROOT+Context.PATH+'/admin/credit/creditfile/queryById.htm',
//							data:"id="+record.get("id"),
//							async:false,
//							dataType:'json',
//							success:function(msg){
//								if(msg){
//									record.set("categoryCode",msg.categoryLabel);
//									record.set("fileNumber",msg.creditFile.fileNumber);
//									record.set("tel",msg.creditFile.tel);
//									record.set("website",msg.creditFile.website);
//									record.set("introduction",msg.creditFile.introduction);
//									record.set("picName",msg.creditFile.picName==""?"noimage.gif":msg.creditFile.picName);
//								}
//								record.set("isgetdata",true);
//							}
//						});
//					}
//				}
//			}
//		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
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
			},{
				header:"公司",
				sortable:false,
				width:150,
				dataIndex:"companyName"
			}, {
				header:"证书名称",
				sortable:false,
				width:150,
				dataIndex:"fileName"
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
				header:"证书类别",
				sortable:false,
				width:75,
				dataIndex:"categoryName"
			},{
				header:"证书编号",
				sortable:false,
				width:75,
				dataIndex:"fileNumber"
			},{
				header:"电话",
				sortable:false,
				width:100,
				dataIndex:"tel"
			},{
				header:"网址",
				sortable:false,
				width:150,
				dataIndex:"website"
			},{
				header:"证书介绍",
				sortable:false,
				width:75,
				dataIndex:"introduction"
			
			},{
				header:"图片",
				sortable:false,
				width:75,
				dataIndex:"picName",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return "<a href='"+CreditFileContext.RESOURCE_URL+"/"+value+"' target='_blank'>查看</a>";
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
			autoExpandColumn:12,
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
			listeners:{
				"render":this.buttonQuery
			}
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
   	    {name:"tel",mapping:"creditFile.tel"},
   	    {name:"website",mapping:"creditFile.website"},
   	    {name:"picName",mapping:"creditFile.picName"},
   	    {name:"introduction",mapping:"creditFile.introduction"},
   	    {name:"categoryName",mapping:"categoryName"},
   	    {name:"checkStatus",mapping:"creditFile.checkStatus"}
   	]),
   	mytoolbar:[
   		"证书编号：",
	   	{
	   		xtype:"textfield",
	   		id:"fileNumber",
	   		name:"fileNumber",
	   		width:100
	   	},"证书名称：",{
	   		xtype:"textfield",
	   		id:"fileName",
	   		name:"fileName",
	   		width:100
	   	},"公司：",{
	   		xtype:"textfield",
	   		id:"companyName",
	   		name:"companyName",
	   		width:100
	   	},"证书类别",{
			xtype:"combo",
			id:"search-creditfileCategoryCode_combo",
			name:"search-creditfileCategoryCode",
			triggerAction : "all",
			forceSelection : true,
			displayField : "label",
			valueField : "code",
			width:80,
			store:new Ext.data.JsonStore( {
				root : "records",
				fields : [ "label", "code" ],
				autoLoad:false,
				url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["creditfileCategoryCode"],
				listeners :{
				  load:function(){
				  }
				}
			}),
			listeners:{
				"blur":function(field){
	   				var _store=Ext.getCmp(_C.RESULT_GRID).getStore();
					var B = _store.baseParams;
					B = B||{};
					if(Ext.get(field.getId()).dom.value!=""){
						B["categoryCode"] = field.getValue();
					}else{
						B["categoryCode"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},"-",{
	   		text:"查询",
	   		iconCls:"query",
	   		handler:function(){
		   		var grid = Ext.getCmp(_C.RESULT_GRID);
		   		var B=grid.store.baseParams;
		   		B=B||{};
		   		B["companyName"] = Ext.get("companyName").dom.value;
		   		B["fileName"] = Ext.get("fileName").dom.value;
		   		B["fileNumber"] = Ext.get("fileNumber").dom.value;
		   		grid.store.baseParams = B;
	   			grid.store.reload({
	   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
	   			})
	   		}
	   	},"->","审核状态：",
		{
			xtype:"checkbox",
			boxLabel:"已审核",
			id:"beChecked",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		},
		{
			xtype:"checkbox",
			boxLabel:"待审核",
			id:"unChecked",
			checked:true,
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		},
		{
			xtype:"checkbox",
			boxLabel:"不通过",
			id:"noChecked",
			checked:true,
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		}
	],
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
   	buttonQuery:function(){
		var tbar2 =new Ext.Toolbar({
			items:[
				{
					iconCls:"edit",
					id:"checkButton",
					text:"通过审核",
					disabled:true,
					handler:function(btn){
						checkButton(1);
					}
				},{
					iconCls:"edit",
					id:"unCheckButton",
					text:"取消审核",
					disabled:true,
					handler:function(btn){
							checkButton(2)
					}
				}
			]
		});
		tbar2.render(this.tbar);
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

