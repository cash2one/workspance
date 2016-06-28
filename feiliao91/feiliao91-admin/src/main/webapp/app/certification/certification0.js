Ext.ns("ast.feiliao91.admin.certification");
//console.log
Ext.define("CertificationModel",{
	extend:"Ext.data.Model",
	fields:[
	        	{name:"id",mapping:"companyInfo.id"},
	        	{name:"status",mapping:"companyInfo.creditStatus"},
	        	{name:"compName",mapping:"companyInfo.name"},
	        	{name:"gmtReg",mapping:"companyInfo.gmtReg"},
	        	{name:"gmtModified",mapping:"companyInfo.gmtModified"},
	        	{name:"Bus",mapping:"companyName"},//认证公司名
	        ]
});

Ext.define("ast.feiliao91.admin.certification.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CertificationModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/admin/certification/queryAllCertification.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				sortParam:"pagesort",
				simpleSortMode:false,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
				header:"编号",
				dataIndex:"id",
				width:50,
				hidden:false
			},{
				header:"审核状态",
				dataIndex:"status",
				width:100,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var str="";
					if(value!=null){
						if(value==2){
							//已认证
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
						}else if(value==0){
							//未认证
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
						}else if(value==3){
							//认证不通过
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
						}else{
							//认证中
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Warning.Small.gif" />';
						}
					}
					return str;
				}
			},
			{
				header:"认证公司",
				dataIndex:"Bus",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var title=" <a href='"+Context.ROOT+"/admin/certification/edit.htm?id="+record.get("id")+"' target='_blank'>"+value+"</a>";
					return title;
				}
			},
			{
				header:"公司名称",
				dataIndex:"compName",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			},
			{
				header:"注册时间",
				dataIndex:"gmtReg",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
					}
					return "";
				}
			},
			{
				header:"修改时间",
				dataIndex:"gmtModified",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}
		];
		
		var c={
				store:_store,
				columns:_cm,
				selModel:_sm,
				tbar:[{
					text:"编辑",
					iconCls:"edit",
					handler:function(btn){
						var grid = Ext.getCmp();
					}
				},"-",{
					text:"认证通过",
//					iconCls:"item-true",
					handler:function(btn,e){
						ast.feiliao91.admin.certification.updateStatus(2);
					}
				},"-",{
					text:"认证不通过",
					handler:function(btn,e){
						ast.feiliao91.admin.certification.updateStatus(3);
					}
				},"-",{
					text:"认证中",
					handler:function(btn,e){
						ast.feiliao91.admin.certification.updateStatus(1);
					}
				},"-","公司名称",{
					xtype:"textfield",
					id:"compName",
			   		name:"compName",
			   		width:100
				},"-",{
					text:"查询",
			   		iconCls:"query",
			   		handler:function(){
			   			var grid = Ext.getCmp("CERTIFICATION.GRID");
			   			console.log(grid);
			   			var B=grid.store.baseParams;
			   			console.log(B);
				   		B=B||{};
				   		B["compName"] = Ext.get("compName").dom.value;
				   		grid.store.baseParams = B;
			   			grid.store.reload({
			   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
			   			})
			   		}
				},
				],
				dockedItems:[{
					xtype:"pagingtoolbar",
					store:_store,
					dock:"bottom",
					displayInfo:true
				}]
			};
		
		Ext.applyIf(this,c);
		this.callParent();
	}
});
//更新状态函数
ast.feiliao91.admin.certification.updateStatus = function(checkStatus){
	var grid = Ext.getCmp("CERTIFICATION.GRID");
	var row = grid.getSelectionModel().getSelection();
	var _ids = new Array();
	if(row.length==0){
		Ext.MessageBox.alert(Context.MSG_TITLE,"请选中你要审核的产品信息");
		return false;
	}
	for (var i=0,len = row.length;i<len;i++){
		_ids.push(row[i].get("id"));
	}
	Ext.Ajax.request({
		url: Context.ROOT+"/admin/certification/updateStatus.htm",
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