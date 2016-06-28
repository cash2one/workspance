Ext.ns("ast.feiliao91.admin.good");

Ext.define("GoodModel",{
	extend:"Ext.data.Model",
	fields:[
		        {name:"gid",mapping:"goods.id"},
		        {name:"title",mapping:"goods.title"},
		        {name:"companyName",mapping:"companyInfo.name"},
		        {name:"refreshTime",mapping:"goods.refreshTime"},
		        {name:"gmtModified",mapping:"goods.gmtModified"},
		        {name:"check_status",mapping:"goods.checkStatus"},
		        {name:"is_del",mapping:"goods.isDel"},
		        {name:"check_person",mapping:"goods.checkPerson"},
		        {name:"check_time",mapping:"goods.checkTime"}
	        ]
});

Ext.define("ast.feiliao91.admin.good.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"GoodModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/admin/good/queryList.htm",
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
				dataIndex:"gid",
				width:50,
				hidden:true
			},{
				header:"审核状态",
				dataIndex:"check_status",
				width:60,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var status=record.get("check_status");
					if(status=="1"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="2"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"删除状态",
				dataIndex:"is_del",
				width:60,
				renderer:function(value,m,record,ridx,cidx,store,view){
					var status=record.get("is_del");
					if(status=="0"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="1"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}
				}
			},{
				header:"标题",
				dataIndex:"title",
				width:250,
				renderer:function(value,m,record,ridx,cidx,store,view){
					return value;
				}
			},{
				text:"公司名称",
				dataIndex:"companyName",
				width:350,
				renderer:function(value){
					return value;
				}
			},{
				text:"发布时间",
				dataIndex:"refreshTime",
				renderer:function(value){
						if(value!=null){
							return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
						}
						return "";
				 }
		    },{
				text:"审核人",
				dataIndex:"check_person"
		    },{
				text:"审核时间",
				dataIndex:"check_time",
				renderer:function(value){
					if(value!=null){
						return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
					}
					return "";
				}
		    },{
				text:"更新时间",
				dataIndex:"gmtModified",
				renderer:function(value){
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
				text:"审核",
				menu:[{
					text:"审核通过",
					handler:function(btn,e){
						ast.feiliao91.admin.good.updateStatus(1);
					}
				},{
					text:"审核不通过",
					handler:function(btn,e){
						ast.feiliao91.admin.good.updateStatus(2);
					}
				}]
			}],
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
ast.feiliao91.admin.good.updateStatus = function(checkStatus){
	var grid = Ext.getCmp("GOOD.GRID");
	var row = grid.getSelectionModel().getSelection();
	var _ids = new Array();
	if(row.length==0){
		Ext.MessageBox.alert(Context.MSG_TITLE,"请选中你要审核的产品信息");
		return false;
	}
	for (var i=0,len = row.length;i<len;i++){
		_ids.push(row[i].get("gid"));
	}
	Ext.Ajax.request({
		url: Context.ROOT+"/admin/good/updateStatus.htm",
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
