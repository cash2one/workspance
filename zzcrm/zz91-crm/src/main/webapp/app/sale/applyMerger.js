Ext.namespace("com.zz91.ep.crm.apply");

var APPLY=new function(){
	this.APPLY_GRID="applygrid";
	this.APPLY_CHECK_GRID="applycheckgrid";
}

com.zz91.ep.crm.apply.ApplyField=[
	{name:"id",mapping:"id"},
	{name:"order_id",mapping:"orderId"},
	{name:"sale_name",mapping:"saleName"},
	{name:"gmt_created",mapping:"gmtCreated"}
];

com.zz91.ep.crm.apply.CheckField=[
	{name:"id",mapping:"id"},
	{name:"order_id",mapping:"orderId"},
	{name:"sale_name",mapping:"saleName"},
	{name:"name",mapping:"name"},
	{name:"old_name",mapping:"oldName"},
	{name:"mobile",mapping:"mobile"},
	{name:"phone",mapping:"phone"},
	{name:"contact",mapping:"contact"},
	{name:"address",mapping:"address"},
	{name:"email",mapping:"email"},
	{name:"cname",mapping:"cname"}
];

com.zz91.ep.crm.apply.ApplyGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:com.zz91.ep.crm.apply.ApplyField,
			url:Context.ROOT+"/sale/deptcompany/queryMergerApply.htm",
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				hidden:true,
				header:"编号", 
				dataIndex:"id",
				sortable:true
			},{
				hidden:true,
				header:"申请重复Id号", 
				dataIndex:"order_id",
				sortable:true
			},{
				header:"申请人", 
				dataIndex:"sale_name",
				width:80,
				sortable:true
			},{
				header:"申请时间", 
				dataIndex:"gmt_created",
				width:130,
				sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var c={
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
				bbar:com.zz91.utils.pageNav(_store)
			};
		
		com.zz91.ep.crm.apply.ApplyGrid.superclass.constructor.call(this,c);
	}
});

com.zz91.ep.crm.apply.CheckGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			fields:com.zz91.ep.crm.apply.CheckField,
			url:Context.ROOT+"/sale/deptcompany/queryApplyDetails.htm",
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				hidden:true,
				header:"公司编号ID", 
				dataIndex:"id",
				sortable:true
			},{
				hidden:true,
				header:"申请重复Id号", 
				dataIndex:"order_id",
				sortable:true
			},{
				header:"公司名称", 
				width:200,
				dataIndex:"cname",
				sortable:true
			},{
				header:"公司联系人", 
				dataIndex:"name",
				width:80,
				sortable:true
			},{
				header:"公司所属销售", 
				dataIndex:"old_name",
				width:90,
				sortable:true
			},{
				header:"联系人手机", 
				dataIndex:"mobile",
				sortable:true
			},{
				header:"联系人电话", 
				dataIndex:"phone",
				width:80,
				sortable:true
			},{
				header:"其他联系方式", 
				dataIndex:"contact",
				sortable:true
			},{
				header:"地址", 
				dataIndex:"address",
				width:190,
				sortable:true
			},{
				header:"邮箱", 
				dataIndex:"address",
				sortable:true
			},{
				hidden:true,
				header:"申请人", 
				dataIndex:"sale_name",
				sortable:true
			}
		]);
		
		var c={
				loadMask:Context.LOADMASK,
				store:_store,
				sm:_sm,
				cm:_cm,
				viewConfig: { 
					forceFit:true, 
					enableRowBody:true, 
					showPreview:true, 
					getRowClass : function(record, rowIndex, p, store){ 
						var cls="target-row";
						if (record.get("id")==record.get("order_id")){
							return cls;
						}
					}
				},
				tbar:new Ext.Toolbar({
					items:[{
						text:"审核通过",
						iconCls:"play16",
						handler:function(btn){
							grid.CheckStatus(1,_store);
						}
					},{
						text:"审核不通过",
						iconCls:"delete16",
						handler:function(btn){
							grid.CheckStatus(0,_store);
						}
					}]
				}),
				bbar:com.zz91.utils.pageNav(_store)
			};
		
		com.zz91.ep.crm.apply.CheckGrid.superclass.constructor.call(this,c);
	},
	CheckStatus:function(CheckStatus,store){
		var rows=this.getSelectionModel().getSelections();
		if(rows.length==0){
			com.zz91.utils.Msg("提示","未选择任何记录!");
			return ;
		}
		var applygrid=Ext.getCmp(APPLY.APPLY_GRID);
		if(CheckStatus==0){
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url:Context.ROOT+"/sale/deptcompany/updateCheckStatus.htm",
					params:{
						"id":rows[i].get("id"),
						"CheckStatus":CheckStatus
					},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							com.zz91.utils.Msg("","操作成功,公司未合并!");
							store.reload();
							applygrid.getStore().reload();
						}else{
							com.zz91.utils.Msg("","操作成功,公司未合并!");
							applygrid.getStore().reload();
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
		}else{
			var rows=this.getSelectionModel().getSelections();
			Ext.MessageBox.confirm(Context.MSG_TITLE,"确定合并公司?<br/>是:公海客户将从公海库移除," +
					"原所属销售的客户将从个人库移除;"+"<br/>"+"否:取消该操作!",function(btn){
				if(btn!="yes"){
					return ;
				}else{
					for(var i=0;i<rows.length;i++){
						Ext.Ajax.request({
							url:Context.ROOT+"/sale/deptcompany/updateCheckStatus.htm",
							params:{
								"id":rows[i].get("id"),
								"CheckStatus":CheckStatus,
								"targetId":rows[i].get("order_id")
							},
							success:function(response,opt){
								var obj = Ext.decode(response.responseText);
								if(obj.success){
									com.zz91.utils.Msg("","操作成功,公司已成功合并!");
									store.reload();
									applygrid.getStore().reload();
								}else{
									Ext.MessageBox.show({
										title:MESSAGE.title,
										msg : obj.data,
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
			});
			
		}
	}
});