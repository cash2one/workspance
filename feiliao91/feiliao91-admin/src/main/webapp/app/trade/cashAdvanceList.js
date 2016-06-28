/*
 * 提现记录
 */
Ext.namespace("ast.feiliao91.admin.trade.cashAdvanceList")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//提现记录列表
ast.feiliao91.admin.trade.cashAdvanceList.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
			},{
				header:"状态",
				sortable:true,
				dataIndex:"status",
				width:50,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var str="";
					if(value!=null){
						if(value==0){
							//申请中
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.Info.Small.gif" />';
						}else if(value==1){
							//已提现
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.True.gif" />';
						}else{
							//已退回
							str = '<img src ="'+Context.ROOT+Context.PATH+'/css/admin/icons/Item.False.gif" />';
						}
					}
					return str;
				}
			},{
				header:"用户帐号",
				dataIndex:"account",
				width:100,
				renderer:function(value,m,record,ridx,cidx,store,view){
//					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/certification/editGongShang.htm?id="+record.get("companyId")+"' target='_blank'>"+value+"</a>";
					return value;
				}
			},{
				header:"联系人",
				dataIndex:"linkman",
				width:100,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			},{
				header:"手机",
				dataIndex:"mobile",
				width:120,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			},{
				header:"银行名",
				dataIndex:"bank",
				width:190,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			},{
				header:"开户行",
				dataIndex:"bankName",
				width:190,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			}, {
				header:"银行卡号",
				dataIndex:"bankAccount",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			}, {
				header:"提现金额",
				dataIndex:"money",
				width:100,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			}, {
				header:"申请时间",
				dataIndex:"gmtCreated",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					return "";
				}
			},{
				header:"操作人",
				dataIndex:"checkPerson",
				width:100,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
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
		ast.feiliao91.admin.trade.cashAdvanceList.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"cashAdvance.id"},
		{name:"companyId",mapping:"cashAdvance.companyId"},
		{name:"account",mapping:"companyAccount.account"},
		{name:"linkman",mapping:"cashAdvance.linkman"},
		{name:"mobile",mapping:"cashAdvance.mobile"},
		{name:"bank",mapping:"cashAdvance.bank"},
		{name:"bankName",mapping:"cashAdvance.bankName"},
		{name:"bankAccount",mapping:"cashAdvance.bankAccount"},
		{name:"money",mapping:"cashAdvance.money"},
		{name:"status",mapping:"cashAdvance.status"},
		{name:"checkPerson",mapping:"cashAdvance.checkPerson"},
		{name:"gmtCreated",mapping:"cashAdvance.gmtCreated"},
	]),
	mytoolbar:[
	   		
	   		{
	   			iconCls:"item-true",
	   			id:"checkButton",
	   			text:"已打款",
	   			handler:function(btn){
	   				ast.feiliao91.admin.trade.cashAdvanceList.updateStatus(1);
	   			}
	   		},"-",{
	   			iconCls:"item-false",
	   			id:"unCheckButton",
	   			text:"已退回",
	   			handler:function(btn){
	   				ast.feiliao91.admin.trade.cashAdvanceList.updateStatus(2);
	   			}
	   		},"-",
	      	"公司帐号",{
	   	   		xtype:"textfield",
	   	   		id:"compName",
	   	   		name:"compName",
	   	   		width:100
	   	   	},"-",{
	   	   		text:"查询",
	   	   		iconCls:"query",
	   	   		handler:function(){
	   		   		var grid = Ext.getCmp(_C.RESULT_GRID);
	   		   		var B=grid.store.baseParams;
	   		   		B=B||{};
	   		   		B["companyName"] = Ext.get("compName").dom.value;
	   		   		grid.store.baseParams = B;
	   	   			grid.store.reload({
	   	   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
	   	   			})
	   	   		}
	   	   	},"->",{
				xtype:"checkbox",
				boxLabel:"待审核",
				id:"unChecked",
				listeners:{
					"check":function(field,newvalue,oldvalue){
						var grid = Ext.getCmp(_C.RESULT_GRID);
						grid.searchByCheckStatus();
					}
				}
			},
			{
				xtype:"checkbox",
				boxLabel:"通过",
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
	   	searchByCheckStatus:function(){
			var B=this.getStore().baseParams||{};
			var ary = new Array();
			if(Ext.getCmp("beChecked").getValue()){
				//通过
				ary.push(2);
			}
			if(Ext.getCmp("unChecked").getValue()){
				//未审核
				ary.push(1);
			}
			if(Ext.getCmp("noChecked").getValue()){
				//不通过
				ary.push(3);
			}
			B["creditStatus"] = ary.join(",");
			this.getStore().baseParams = B;
			this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
		}
});

//更新状态函数
ast.feiliao91.admin.trade.cashAdvanceList.updateStatus = function(checkStatus){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	var row = grid.getSelections();
	var _ids = new Array();
	if(row.length==0){
		Ext.MessageBox.alert(Context.MSG_TITLE,"请选中你要审核的产品信息");
		return false;
	}
	for (var i=0,len = row.length;i<len;i++){
		_ids.push(row[i].get("id"));
	}
	Ext.Ajax.request({
		url: Context.ROOT+Context.PATH+"/admin/trade/updateCashAdvanceStatus.htm",
		params: {
			ids: _ids.join(","),
			checkStatus:checkStatus
		},
		success: function(response,opt){
			var obj = Ext.decode(response.responseText);
			if(obj.success){
				Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
				grid.getStore().load();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,obj.data);
			}
		}
	});
}