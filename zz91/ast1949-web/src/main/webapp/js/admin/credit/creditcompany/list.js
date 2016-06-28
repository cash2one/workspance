/*
 * 资信参考人
 */
Ext.namespace("ast.ast1949.admin.credit.creditcompany")

//资信参考人列表
ast.ast1949.admin.credit.creditcompany.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
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
	                    Ext.getCmp("removeButton").enable();
	                    Ext.getCmp("beCheckButton").enable();
	                    Ext.getCmp("unCheckButton").enable();
	                } else {
	                    Ext.getCmp("removeButton").disable();
	                    Ext.getCmp("beCheckButton").disable();
	                    Ext.getCmp("unCheckButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				width:8,
				sortable:false,
				dataIndex:"id",
				hidden:true
			}, {
				header:"审核状态",
				sortable:true,
				dataIndex:"checkStatus",
				width:8,
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
				header:"姓名",
				width:15,
				sortable:false,
				dataIndex:"name"
			}, {
				header:"所在公司",
				width:25,
				sortable:false,
				dataIndex:"companyName"
			}, {
				header:"联系地址",
				width:30,
				sortable:false,
				dataIndex:"address"
			}, {
				header:"电话",
				width:20,
				sortable:false,
				dataIndex:"tel"
			}, {
				header:"传真",
				width:20,
				sortable:false,
				dataIndex:"fax"
			}, {
				header:"电子邮箱",
				width:20,
				sortable:false,
				dataIndex:"email"
			}, {
				header:"关系",
				width:45,
				sortable:false,
				dataIndex:"details"
			}, {
				header:"资信公司",
				width:25,
				sortable:false,
				dataIndex:"creditCompanyName"
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
			tbar:["邮箱：",
				{
			   		xtype:"textfield",
			   		id:"email",
			   		width:160
			   	},"所在公司：",{
			   		xtype:"textfield",
			   		id:"companyName",
			   		width:160
			   	},"资信公司：",{
			   		xtype:"textfield",
			   		id:"creditCompanyName",
			   		width:160
			   	},"-",{
			   		text:"查询",
			   		iconCls:"query",
			   		handler:function(btn){
				   		var B=grid.store.baseParams;
				   		B=B||{};
				   		B["email"] = Ext.get("email").dom.value;
				   		B["creditCompanyName"] = Ext.get("creditCompanyName").dom.value;
				   		B["companyName"] = Ext.get("companyName").dom.value;
				   		
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
							grid.searchByCheckStatus();
						}
					}
				},
				{
					xtype:"checkbox",
					boxLabel:"未审核",
					id:"unChecked",
					checked:true,
					listeners:{
						"check":function(field,newvalue,oldvalue){
							grid.searchByCheckStatus();
						}
					}
				},
				{
					xtype:"checkbox",
					boxLabel:"不通过",
					id:"noChecked",
					listeners:{
						"check":function(field,newvalue,oldvalue){
							grid.searchByCheckStatus();
						}
					}
				}],
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
		ast.ast1949.admin.credit.creditcompany.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
   	    {name:"id",mapping:"creditReference.id"},
   	    {name:"name",mapping:"creditReference.name"},
   	    {name:"address",mapping:"creditReference.address"},
   	    {name:"tel",mapping:"creditReference.tel"},
   	    {name:"fax",mapping:"creditReference.fax"},
   	    {name:"email",mapping:"creditReference.email"},
	   	{name:"details",mapping:"creditReference.details"},
	   	{name:"companyName",mapping:"creditReference.companyName"},
	   	{name:"checkStatus",mapping:"creditReference.checkStatus"},
	   	{name:"companyId",mapping:"creditReference.companyId"},
	   	{name:"creditCompanyName",mapping:"creditCompanyName"}
   	]),
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
				id:"beCheckButton",
				text:"通过审核",
				iconCls:"edit",
				disabled:true,
				handler:function(btn){
					doUpdateStatus(1);
				}
			},{
				id:"unCheckButton",
				text:"取消审核",
				iconCls:"edit",
				disabled:true,
				handler:function(btn){
					doUpdateStatus(0);
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

//审核信息
function doUpdateStatus(status){
	var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID);
	
	var row = grid.getSelections();
//	var _ids = new Array();
//	var _cids = new Array();
//	var _currentStatus = new Array();
	var success=0;
	var step=0;
	for (var i=0,len = row.length;i<len;i++){
		step++;
		var _id=row[i].get("id");
//		_ids.push(_id);
		var _cid=row[i].get("companyId");
//		_cids.push(_cid);
		var _status=row[i].get("checkStatus");
//		_currentStatus.push(_status);
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/credit/creditcompany/update.htm?st="
			+ Math.random() + "&ids=" + _id +"&cids=" + _cid
			+ "&currents="+ _status
			+ "&checkStatus=" + status,
			method : "get",
			scope : this,
			callback : function(options,success,response){
				var res = Ext.decode(response.responseText);
				if(res.success){
					success++;
				}
				if(step==row.length&&success==row.length){
					ast.ast1949.utils.Msg("","选定的记录已成功更新！");
					grid.getStore().reload();
				} else {
					grid.getStore().reload();
				}
			}
		});
	}
};

//设置删除
function doDelete(_btn){
	if(_btn != "yes")
		return ;
	var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID);
	
	var row = grid.getSelections();
	var success=0;
	var step=0;
	for (var i=0,len = row.length;i<len;i++){
		step++;
		var _id=row[i].get("id");
		
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/credit/creditcompany/delete.htm?st="
			+ Math.random() + "&ids=" + _id,
			method : "get",
			scope : this,
			callback : function(options,success,response){
				var res= Ext.decode(response.responseText);
				if(res.success){
					success++;
					if(step==row.length&&success==row.length) {
						ast.ast1949.utils.Msg("","选定的记录已被删除！");
						grid.getStore().reload();
					} else {
						grid.getStore().reload();
					}
				}
			}
		});
	}
};