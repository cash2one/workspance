/*
 * 客户评价
 */
Ext.namespace("ast.ast1949.admin.credit.creditcustomervote")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//客户评价列表
ast.ast1949.admin.credit.creditcustomervote.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("checkButton").enable();
	                    Ext.getCmp("unCheckButton").enable();
	                    Ext.getCmp("removeButton").enable();
	                } else {
	                    Ext.getCmp("checkButton").disable();
	                    Ext.getCmp("unCheckButton").disable();
	                    Ext.getCmp("removeButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			}, {
				header:"审核状态",
				sortable:true,
				dataIndex:"checkStatus",
				width:15,
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
				header:"被评价方",
				sortable:false,
				dataIndex:"toCompanyName"
			}, {
				header:"状态",
				sortable:false,
				dataIndex:"status",
				renderer : function (value,metadata,record,rowIndex,colIndex,store){
					if(value=="0"){
						return "好评";
					}
					else if(value=="1"){
						return "中评"
					}else if(value=="2"){
						return "差评"
					}else{
						return null;
					}
				}
			}, {
				header:"评价内容",
				sortable:false,
				dataIndex:"content"
			},{
				header:"回复内容",
				sortable:false,
				dataIndex:"replyContent",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					 if(value==""){
				 		 return "未作回复";
					 }else{
						return value;
					 }
				}
			}, {
				header:"评价方",
				sortable:false,
				dataIndex:"fromCompanyName"
			}, {
				header:"评价者邮箱",
				sortable:false,
				dataIndex:"fromEmail"
			}
		]);
		var con={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
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
			listeners:{
				"render":this.buttonQuery
			}
		};
		ast.ast1949.admin.credit.creditcustomervote.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
   	    {name:"id",mapping:"vote.id"},
   	    {name:"toName",mapping:"vote.toName"},
   	    {name:"status",mapping:"vote.status"},
   	    {name:"checkStatus",mapping:"vote.checkStatus"},
   	    {name:"content",mapping:"vote.content"},
   	    {name:"fromName",mapping:"vote.fromName"},
   	    {name:"replyContent",mapping:"vote.replyContent"},
   	    {name:"fromEmail",mapping:"fromEmail"},
   	    {name:"toCompanyId",mapping:"vote.toCompanyId"},
   	    {name:"fromCompanyId",mapping:"vote.fromCompanyId"},
   	    {name:"fromCompanyName",mapping:"fromCompanyName"},
   	    {name:"toCompanyName",mapping:"toCompanyName"}
   	]),
   	mytoolbar:["被评价方：",{
   		xtype:"textfield",
   		id:"toName",
   		width:160
   	},"评价方：",{
   		xtype:"textfield",
   		id:"fromName",
   		width:160
   	},"-",{
   		text:"查询",
   		iconCls:"query",
   		handler:function(){
	   		var grid = Ext.getCmp(_C.RESULT_GRID);
	   		var B=grid.store.baseParams;
	   		B=B||{};
	   		B["toCompanyName"] = Ext.get("toName").dom.value;
	   		B["fromCompanyName"] = Ext.get("fromName").dom.value;
	   		grid.store.baseParams=B;
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
			items:[{
				iconCls:"add",
				id:"checkButton",
				text:"通过审核",
				disabled:true,
				handler:function(btn){
					checkButton(1);
				}
			},"-",{
				iconCls:"edit",
				id:"unCheckButton",
				text:"取消审核",
				disabled:true,
				handler:function(btn){
					checkButton(2);
				}
			},"-",{
				iconCls:"delete",
				id:"removeButton",
				text:"删除记录",
				disabled:true,
				handler:function(btn){
					Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要将选中的记录设置为删除吗?', doDelete);
				}
			}]
		});
		tbar2.render(this.tbar);
	}
});
//审核 checkStatus
function checkButton(val){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	var _cids = new Array();
	var _status = new Array();
	var _currents = new Array();
	
	//String ids, String checkStatus, String cids, String status
	
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
		
		var _cid=row[i].get("toCompanyId");
		_cids.push(_cid);
		
		var _s=row[i].get("status");
		_status.push(_s);
		
		var _current=row[i].get("checkStatus");
		_currents.push(_current);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/credit/creditcustomervote/updateCheckStatus.htm?random=" + Math.random() +
		"&ids=" +_ids + "&cids=" + _cids +
		"&status=" + _status +
		"&currents=" + _currents +
		"&checkStatus=" + val,
		method : "get",
		scope : this,
		callback : function(options,success,response){
			var res= Ext.decode(response.responseText);
			if(res.success){
				ast.ast1949.utils.Msg("","选定记录已成功更新!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选操作失败!");
			}
		}
	});
};

//删除 
function doDelete(_btn){
	if(_btn != "yes")
		return ;
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	var _ids = new Array();
	var _cids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
		
		var _cid=row[i].get("fromCompanyId");
		_cids.push(_cid);
	}
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/credit/creditcustomervote/delete.htm?random="+Math.random() + 
		"&ids="+_ids + "&cids=" +_cids,
		method : "get",
		scope : this,
		callback : function(options,success,response){
			var res= Ext.decode(response.responseText);
			if(res.success){
				ast.ast1949.utils.Msg("","选定的记录已被删除!");
				grid.getStore().reload();
			}else{
				ast.ast1949.utils.Msg("","所选记录删除失败!");
			}
		}
	});
};

