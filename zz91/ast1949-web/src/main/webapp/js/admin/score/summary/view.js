Ext.namespace("ast.ast1949.admin.score.summary")

/**
 * 用户积分列表
 * @class ast.ast1949.admin.score.summary.SummaryGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.score.summary.SummaryGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({
			listeners: {
				selectionchange: function(sm) {
	                if (sm.getCount()) {
//						Ext.getCmp("removeButton").enable();
						if(sm.getCount()==1) {
							var row = grid.getSelections();
							if(row.length==1){
								var _id=row[0].get("companyId");
								if(_id!=null) {
									PAGE_CONST.COMPANY_ID=_id;
									var cgrid=Ext.getCmp(PAGE_CONST.CHANGE_GRID);
									var B=cgrid.getStore().baseParams||{};
									B["companyId"] = _id;
									cgrid.getStore().baseParams = B;
									cgrid.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
								}
							}
						}
	                } else {
//	                	Ext.getCmp("removeButton").disable();
					}
				} 
			}
		});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				width:50,
				sortable:false
			},{
				header:"公司名称",
				dataIndex:"name",
				width:250,
				sortable:false
			},{
				header:"邮箱",
				dataIndex:"email",
				width:200,
				sortable:false
			},{
				header:"总积分",
				dataIndex:"score",
				width:80,
				sortable:false
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
//			viewConfig:{
//				autoFill:true
//			},
			loadMask:Context.LOADMASK,
			store:_store,
			autoExpandColumn:4,
			sm:_sm,
			cm:_cm,
			tbar:["公司名称：",
				{
					xtype:"textfield",
					id:"name",
					name:"name",
					width:160
				},"邮箱：",{
					xtype:"textfield",
					id:"email",
					name:"email",
					width:160
				},"-",{
					text:"查询",
			   		iconCls:"query",
			   		handler:function(){
//				   		var grid = Ext.getCmp(_C.RESULT_GRID);
				   		var B=grid.store.baseParams;
				   		B=B||{};
				   		B["name"] = Ext.get("name").dom.value;
				   		B["email"] = Ext.get("email").dom.value;
				   		grid.store.baseParams = B;
			   			grid.store.reload({
			   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
			   			})
			   		}
				},"->",{
					xtype:"checkbox",
					boxLabel:"再生通客户",
					id:"isRecycled",
					listeners:{
						"check":function(field,newvalue,oldvalue){
							grid.doSearch();
						}
					}
				}
			],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
		ast.ast1949.admin.score.summary.SummaryGrid.superclass.constructor.call(this,c);
	},
	doSearch:function(){
		var B=this.getStore().baseParams||{};
		if(Ext.getCmp("isRecycled").getValue()){
			B["membershipCode"] = "10051001";
		} else {
			B["membershipCode"] = null;
		}
		//B["membershipCode"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"summary.id"},
		{name:"companyId",mapping:"summary.companyId"},
		{name:"score",mapping:"summary.score"},
		{name:"name",mapping:"company.name"},
		{name:"email",mapping:"contact",
			convert:function(value){
				if(value!=null) {
					return value.email;
				} else {
					return "";
				}
			}
		}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/score/summary/query.htm"
})

/**
 * 积分变更列表
 * @class ast.ast1949.admin.score.summary.ChangeGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.score.summary.ChangeGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({
			listeners: {
				selectionchange: function(sm) {
	                if (sm.getCount()) {
//						Ext.getCmp("editButton").enable();
	                } else {
//	                	Ext.getCmp("editButton").disable();
					}
				} 
			}
		});
		
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				width:50,
				sortable:false
			},{
				header:"变更项目",
				dataIndex:"name",
				width:250,
				sortable:false
			},{
				header:"积分",
				dataIndex:"score",
				width:80,
				sortable:false
			},{
				header:"时间",
				dataIndex:"gmtCreated",
				sortable:false,
				width:100,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
//			viewConfig:{
//				autoFill:true
//			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[
				{
					id:"editButton",
					iconCls:"item-edit",
					text:"变更",
//					disabled:true,
					handler:function(btn){
						if(PAGE_CONST.COMPANY_ID==null||PAGE_CONST.COMPANY_ID=="") {
							ast.ast1949.utils.Msg("","请在左侧选择一个您要变更的公司！");
						} else {
							var _id="";//row[0].get("id");
							var _cid=PAGE_CONST.COMPANY_ID;//row[0].get("companyId");
							var _relatedId=0;//row[0].get("relatedId");
							
							var url=Context.ROOT+Context.PATH+"/admin/score/summary/edit.htm?id="+_id+"&companyId="+_cid+"&relatedId="+_relatedId+"&ts="+Math.random();
							window.open(url)
						}
						
//						var row = grid.getSelections();
//						var selectedRecord = grid.getSelectionModel().getSelected();
//						if(row.length>1){
//							ast.ast1949.utils.Msg("","最多只能选择一条记录！");
//						} else {
//							var row = grid.getSelections();
//							var _id="";//row[0].get("id");
//							var _cid=PAGE_CONST.COMPANY_ID;//row[0].get("companyId");
//							var _relatedId=0;//row[0].get("relatedId");
//							
//							var url=Context.ROOT+Context.PATH+"/admin/score/summary/edit.htm?id="+_id+"&companyId="+_cid+"&relatedId="+_relatedId+"&ts="+Math.random();
//							window.open(url)
//						}
					}
				},"->",{
					xtype:"checkbox",
					boxLabel:"加分项",
					id:"isPuls",
					listeners:{
						"check":function(field,newvalue,oldvalue){
							grid.doSearch();
						}
					}
				},{
					xtype:"checkbox",
					boxLabel:"减分项",
					id:"noPuls",
					listeners:{
						"check":function(field,newvalue,oldvalue){
							grid.doSearch();
						}
					}
				}
			],
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			})
		};
		
		ast.ast1949.admin.score.summary.ChangeGrid.superclass.constructor.call(this,c);
	},
	doSearch:function(){
		var B=this.getStore().baseParams||{};
		if(Ext.getCmp("isPuls").getValue()&&Ext.getCmp("noPuls").getValue()){
			B["isPlus"] = null;
		} else {
			if(Ext.getCmp("isPuls").getValue()){
				B["isPlus"] = "true";
			}
			else if(Ext.getCmp("noPuls").getValue()){
				B["isPlus"] = "false";
			} else {
				B["isPlus"] = null;
			}
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"name",mapping:"name"},
		{name:"relatedId",mapping:"relatedId"},
		{name:"companyId",mapping:"companyId"},
		{name:"score",mapping:"score"},
		{name:"remark",mapping:"remark"},
		{name:"gmtCreated",mapping:"gmtCreated"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/score/change/queryByCompanyId.htm"
})
