Ext.ns("com.zz91.ep.compNews");

Ext.define("CompNewsGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"compNews.id"},
		{name:"cid",mapping:"compNews.cid"},
		{name:"title",mapping:"compNews.title"},
		{name:"category_code",mapping:"compNews.categoryCode"},
		{name:"pause_status",mapping:"compNews.pauseStatus"},
		{name:"check_status",mapping:"compNews.checkStatus"},
		{name:"delete_status",mapping:"compNews.deleteStatus"},
		{name:"check_person",mapping:"compNews.checkPerson"},
		{name:"gmt_publish",mapping:"compNews.gmtPublish"},
		{name:"gmt_created",mapping:"compNews.gmtCreated"},
		{name:"gmt_modified",mapping:"compNews.gmtModified"},
		{name:"comp_name",mapping:"compName"},
		{name:"memberCode",mapping:"memberCode"}
	]
});

Ext.define("com.zz91.ep.compNews.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CompNewsGridModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/comp/comp/queryCompNews.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totals"
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[{
		    	text:"编号",dataIndex:"id",hidden:true
			},{
		    	text:"cid",dataIndex:"cid",hidden:true
			},{
		    	text:"会员编号",dataIndex:"memberCode",hidden:true
			},{
				header:"公司名称",dataIndex:"comp_name",width:200,
				renderer:function(v,m,record,ridx,cidx,store,view){
					var id=record.get("cid");
					var memberCode=record.get("memberCode");
					var v3="";
					if (v=="" || v==null) {
						v="公司名称暂无"
					}
					if (memberCode=="10011001"){
						v3 = "<img src='"+Context.ROOT+"/images/huanbao/zht.png' />";
					}
					var v1 = "<a href='"+Context.ROOT+"/comp/comp/details.htm?id="+id+"' target='_blank'>"+v+"</a>";					
					var v2="<a href='"+Context.WEB+"/esite/index"+id+".htm' target='_blank' title='浏览前台页面'><img src='"+Context.ROOT+"/themes/boomy/web16.png' /></a>";
					return v3+v2+v1;
				}
			},{
				header:"标题",dataIndex:"title",width:300,
				renderer:function(v,m,record,ridx,cidx,store,view){
					var id=record.get("id");
					var url = "<a href='"+Context.ROOT+"/comp/comp/content.htm?id="+id+"' target='_blank'>"+v+"</a>";					
					return url ;
				}
			},{
				header:"文章类别",dataIndex:"category_code",width:60,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if (v=="1000"){
						return "公司动态";
					}
					if (v=="1001"){
						return "技术文章";
					}
					if (v=="1002"){
						return "成功案例";
					}
				}
			},{
				text:"发布状态",dataIndex:"pause_status",width:60,sortable:true,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if(v=="0"){
						return "未发布";
					}
					if(v=="1"){
						return "已发布";
					}
				}
			},{
				text:"审核状态",dataIndex:"check_status",width:80,sortable:true,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if(v=="0"){
						return "未审核";
					}
					if(v=="1"){
						return "审核通过";
					}
					if(v=="2"){
						return "审核不通过";
					}
				}
			},{
				text:"删除状态",dataIndex:"delete_status",width:60,sortable:true,
				renderer:function(v,m,record,ridx,cidx,store,view){
					if(v=="0"){
						return "已删除";
					}
					if(v=="1"){
						return "未删除";
					}
				}
			},{
				text:"审核人",dataIndex:"check_person",width:80,sortable:true
			},{
				text:"发布时间",dataIndex:"gmt_publish",width:130, 
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					} 
					return "";
				}
			},{
				text:"创建时间",dataIndex:"gmt_created", width:130,
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			},{
				text:"最后修改时间",dataIndex:"gmt_modified",width:130,
				renderer:function(v){
					if(v!=null){
						return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
					}
					return "";
				}
			}];
		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			sortableColumns:false,
			dockedItems:[{
				xtype:"pagingtoolbar",
				store:_store,
				dock:"bottom",
				displayInfo:true
			},{
				xtype: 'toolbar',
				dock:"top",
				items:[{
					xtype:"button",
					iconCls:"add16",
					text:"审核通过",
					scope:this,
					handler:function(btn,e){
						this.checkStatus(this.getSelectionModel().getSelection(),1);
					}
				},{
					xtype:"button",
					iconCls:"edit16",
					text:"审核不通过",
					scope:this,
					handler:function(btn,e){
						this.checkStatus(this.getSelectionModel().getSelection(),2);
					}
				},{
					xtype:"button",
					iconCls:"delete16",
					text:"删除文章",
					scope:this,
					handler:function(btn,e){
						this.deleteModel(this.getSelectionModel().getSelection());
					}
				},"->",{
					xtype:"textfield",
					width:100,
					emptyText:"文章标题搜索",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("title",nv);
							this.up("grid").getStore().load();
						}
					}
				},'-',{
					xtype:"combo",
					emptyText:"审核状态",
					width:90,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["0","未审核"],["1","审核通过"],["2","审核不通过"]]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("check",nv);
							this.up("grid").getStore().load();
						}
					}
				},'-',{
					xtype:"combo",
					emptyText:"删除状态",
					width:90,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["0","已删除"],["1","未删除"]]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("delete",nv);
							this.up("grid").getStore().load();
						}
					}
				},'-',{
					xtype:"combo",
					emptyText:"发布状态",
					width:90,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["0","未发布"],["1","已发布"]]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("pause",nv);
							this.up("grid").getStore().load();
						}
					}
				},'-',{
					xtype:"combo",
					emptyText:"文章类型",
					width:90,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[["1000","公司动态"],["1001","技术文章"],["1002","成功案例"]]
					}),
					valueField:"k",
					displayField:"v",
					listeners:{
						"change":function(field,nv,ov,e){
							this.up("grid").getStore().setExtraParam("categoryCode",nv);
							this.up("grid").getStore().load();
						}
					}
				}]
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	deleteModel:function(selections){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Msg.confirm(Context.MSG_TITLE,"您确定要删除文章？",function(o){
			if(o!="yes"){
				return ;
			}
			
			Ext.Array.each(selections, function(obj, idx, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/comp/comp/deleteCompNews.htm",
					params: {
						id: obj.get("id"),
						cid:obj.get("cid")
					},
					success: function(response){
						Ext.Msg.alert("提示","操作成功!");
						_this.getStore().load();
					}
				});
			});
			
		});
	},
	checkStatus:function(selections,status){
		if(selections.length<=0){
			return ;
		}
		var _this=this;
		Ext.Array.each(selections, function(obj, idx, countriesItSelf){
			Ext.Ajax.request({
				url: Context.ROOT+"/comp/comp/updateCheckStatus.htm",
				params: {
					id: obj.get("id"),
					status:status
				},
				success: function(response){
					Ext.Msg.alert("提示","操作成功!");
					_this.getStore().load();
				}
			});
		});
	},
	
	updateCategoryCode:function(selections){
		
		if(selections.length<=0){
			return ;
		}
		
		var form = new com.zz91.ep.compNews.compCategoryForm({
			region:"center",
			saveUrl:Context.ROOT+"/comp/compcategory/updateCompCategory.htm"
		});
		
		
		var win = new Ext.Window({
			title:"修改公司类型",
			width:400,
			autoHeight:true,
			modal:true,
		});
		win.show();
		
	}
});

