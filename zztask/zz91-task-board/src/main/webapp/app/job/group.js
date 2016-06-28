Ext.namespace("com.zz91.task.board.job.group");

com.zz91.task.board.job.group.INFORMATION=[
	{name:"id",mapping:"id"},
	{name:"groupName",mapping:"groupName"},
	{name:"isDel",mapping:"isDel"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.task.board.job.group.grid= Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
			        header:"分组名称",
				width:120,
				dataIndex : "groupName"
			},{
				header:"删除状态",
				width:70,
				dataIndex : "isDel",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					if(value==1){
					    var v1="是";
					}else if(value==0){
					    var v1="否";
					}
					return v1;
				}
			
			},{
				header : "创建时间",
				width : 140,
				dataIndex : "gmtCreated",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			},{
				header : "更新时间",
				width : 140,
				dataIndex : "gmtModified",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}									
			]);

		var storeUrl = Context.ROOT + "/job/definition/queryAllGroup.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totals',
			remoteSort:true,
			fields:com.zz91.task.board.job.group.INFORMATION,
			url: storeUrl,
			autoLoad:false
		});
		
		var tbar = [
		    {
		       text:"添加分组",
		       iconCls:"add16",
 		       handler:function(){
			  com.zz91.task.board.job.group.updateGroupInfo();
		       }
		    },"-",{
		       text:"删除分组",
		       iconCls:"delete16",
                        handler:function(){
				var rows=grid.getSelectionModel().getSelected();
                                if(!rows){
				   alert("请选择您要删除的分组");
				}
				var id=rows.get("id");
				Ext.MessageBox.confirm(Context.MSG_TITLE,"确定要删除 '"+" "+rows.get("groupName")+"' 的分组?",function(btn){
					if(btn!="yes"){
						return ;
					}else{
					Ext.Ajax.request({
						url:Context.ROOT+"/job/definition/deleteGroup.htm",
						params:{
							"id":id
						 },
						method:"post",
						type:"json",
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
						        if(obj.success){
						             alert("删除成功");
							         grid.getStore().reload(); 
							    }else{
							       alert("删除失败");
							    }
						}
					});
				}
				});
		       }
		    },"-",{
		       text:"修改分组",
		       iconCls:"edit16",
		       handler:function(){
                    var rows=grid.getSelectionModel().getSelected();
                    if(!rows){
				   		alert("请选择您要修改的分组");
				    }
					com.zz91.task.board.job.group.updateGroupInfo(rows.get("id"));
			   }
			},"-",{
				xtype:"textfield",
				width:120,
				id:"search-title"
			},{
            	text:"查询",
            	iconCls:"favb16",
            	handler:function(btn){
            		//TODO 查找数据
            		var title=Ext.getCmp("search-title").getValue();
            		_store.baseParams["groupName"]=title;
            		_store.reload();
            	
            	}
		}];

		var c={
			id:"groupGrid",	
			loadMask:MESSAGE.loading,
			sm : sm,
			cm : cm,
			store:_store,
			tbar : tbar,
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
			
		};
		com.zz91.task.board.job.group.grid.superclass.constructor.call(this,c);
	},
	loadGroup:function(){
		this.getStore().reload();
	},

	
})
//分组信息添加
com.zz91.task.board.job.group.updateGroupInfo=function(id){
	var form=new com.zz91.task.board.job.group.updateGroupInfoForm({
	});
	if(id){
		form.loadInit(id);
        }
	
	var win = new Ext.Window({
		id:"updateGroupswin",
		title:"添加分组/修改分组",
		width:300,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.task.board.job.group.updateGroupInfoForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				header:"编号",
				name:"id",
				hidden:true,
				id:"id"
			 },{
				fieldLabel:"分组名称",
				name:"groupName",
				id:"groupName"
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("groupGrid");
						var rows=grid.getSelectionModel().getSelected();
						var url=Context.ROOT+"/job/definition/addGroup.htm";	
						form.getForm().submit({
							url: url,
							method:"post",
							type:"json",
							success:function(response,opt){
                                                                var obj =opt.result ;
								Ext.MessageBox.alert(opt.result.data);
								Ext.getCmp("updateGroupswin").close();
								grid.getStore().reload();  
							},
							failure:function(response,opt){
 								var obj =opt.result ;
                                Ext.getCmp("updateGroupswin").close();
								Ext.MessageBox.alert("保存失败");
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		com.zz91.task.board.job.group.updateGroupInfoForm.superclass.constructor.call(this,c);
		},
	loadTags:function(){
	},
	loadInit:function(id){
		var form=this;
		form.store = new Ext.data.JsonStore({
			fields : com.zz91.task.board.job.group.INFORMATION,
			url : Context.ROOT+"/job/definition/init.htm?id="+id, 
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		});
	}
	
});







