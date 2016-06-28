Ext.ns("ast.feiliao91.admin.company");

Ext.define("CompanyModel",{
	extend:"Ext.data.Model",
	fields:[
		        {name:"id",mapping:"companyInfo.id"},
		        {name:"name",mapping:"companyInfo.name"},
		        {name:"password",mapping:"companyAccount.password"},
		        {name:"account",mapping:"companyAccount.account"},
		        {name:"mobile",mapping:"companyAccount.mobile"},
		        {name:"tel",mapping:"companyAccount.tel"},
		        {name:"address",mapping:"address"},
		        {name:"reg_time",mapping:"companyInfo.gmtCreated"},
		        {name:"last_login_time",mapping:"companyAccount.gmtLastLogin"}
	        ]
});

Ext.define("ast.feiliao91.admin.company.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		
		var _store=Ext.create("Ext.data.Store",{
			model:"CompanyModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/admin/company/queryCompanyList.htm",
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
				hidden:true
			},{
				header:"公司名称",
				dataIndex:"name",
				width:180,
				renderer:function(value,m,record,ridx,cidx,store,view){
					if(value!=null){
						_url=Context.ROOT+Context.PATH+"/admin/company/detail.htm?companyId="+record.get("id");
						return "<a href=\'"+_url+"\' target='_blank'>"+value+"</a>";
					}
					return "";
				}
			},{
				header:"密码",
				dataIndex:"password"
			},{
				header:"登录帐号",
				dataIndex:"account"
			},{
				text:"手机号码",
				dataIndex:"mobile"
			},{
				text:"电话号码",
				dataIndex:"tel"
		    },{
				text:"地区",
				dataIndex:"address"
		    },{
				text:"注册时间",
				dataIndex:"reg_time",
				width:130,
				renderer:function(value){
					if(value!=null){
						return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
					}
					return "";
				}
		    },{
				text:"最后登录时间",
				dataIndex:"last_login_time",
				width:130,
				renderer:function(value){
					if(value!=null){
						return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
					}
					return "";
				}
//		    },{
//				text:"更新时间",
//				dataIndex:"gmtModified",
//				renderer:function(value){
//					if(value!=null){
//						return Ext.Date.format(new Date(value.time),"Y-m-d H:i:s");
//					}
//					return "";
//				}
			}
		];		
		var c={
			store:_store,
			columns:_cm,
			selModel:_sm,
			tbar:[{
				text:"编辑",
				menu:[]
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
