Ext.namespace("ast.ast1949.bbs.score");

var USER =new function(){
	this.GRID="usergrid"
}

var SCORE=new function(){
	this.GRID="scoregrid";
}

ast.ast1949.bbs.score.userInfoFIELDS=[
	{name:"id",mapping:"bbsUserProfiler.companyId"},
	{name:"account",mapping:"bbsUserProfiler.account"},
	{name:"nickname",mapping:"bbsUserProfiler.nickname"},
	{name:"contact",mapping:"contact"},
	{name:"name",mapping:"name"},
	{name:"totalScore",mapping:"bbsUserProfiler.integral"},
	{name:"usedScore",mapping:"usedScore"},
	{name:"laveScore",mapping:"laveScore"},
	{name:"totalPost",mapping:"totalPost"},
	{name:"totalReply",mapping:"totalReply"},
	{name:"totalQA",mapping:"totalQA"},
	{name:"totalReplyQA",mapping:"totalReplyQA"}
];

// 用户列表
ast.ast1949.bbs.score.userinfoGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.bbs.score.userInfoFIELDS,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",
				hidden:true,
				dataIndex:"id"
			},{
				header:"帐号",
				width:150,
				dataIndex:"account",
				renderer:function(value, metadata, record, rowIndex,colIndex, store){
					return "<a target='_blank' href='"+Context.ROOT+Context.PATH+"/crm/company/detail.htm?companyId="+record.get("id")+"'>"+value+"</a>"
				}
			},{
				header:"昵称",
				dataIndex:"nickname"
			},{
				header:"联系人",
				dataIndex:"contact"
			},{
				header:"公司名",
				width:250,
				dataIndex:"name"
			},{
				header:"总积分",
				dataIndex:"totalScore"
			},{
				header:"已兑换积分",
				dataIndex:"usedScore"
			},{
				header:"剩余积分",
				dataIndex:"laveScore"
			},{
				header:"总发贴数",
				dataIndex:"totalPost"
			},{
				header:"总回贴数",
				dataIndex:"totalReply"
			},{
				header:"总提问数",
				dataIndex:"totalQA"
			},{
				header:"总回答数",
				dataIndex:"totalReplyQA"
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			autoExpandColumn:4,
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
			})
		};
		
		ast.ast1949.bbs.score.userinfoGrid.superclass.constructor.call(this,c);
		
	},
	
	listUrl:Context.ROOT+Context.PATH+"/bbs/score/queryUser.htm",
	
	mytoolbar:["->","积分开始时间:",{
		xtype : "datefield",
		format:"Y-m-d",
		name:"from",
		emptyText:"积分开始时间"
	},"积分结束时间:",{
		xtype : "datefield",
		format:"Y-m-d",
		name:"to",
		emptyText:"积分结束时间"
	},{
		iconCls:"add",
		text:"导出",
		handler:function(btn){
		}
	}]
});

ast.ast1949.bbs.score.queryPost = function(gridid,param){
	var _store = Ext.getCmp(USER.GRID).getStore();
	_store.baseParams = param;
	_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
}

ast.ast1949.bbs.score.scoreFIELDS=[
	{name:"id",mapping:"bbsScore.id"},
	{name:"bbsPostId",mapping:"bbsPostId"},
	{name:"title",mapping:"title"},
	{name:"content",mapping:"content"},
	{name:"visitedCount",mapping:"visitedCount"},
	{name:"replyCount",mapping:"replyCount"},
	{name:"score",mapping:"bbsScore.score"},
	{name:"remark",mapping:"bbsScore.remark"}
];

// 积分列表
ast.ast1949.bbs.score.scoreGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.bbs.score.scoreFIELDS,
			url:_url,
			autoLoad:false
		});
		
		var _cm=new Ext.grid.ColumnModel([{
				header:"编号",
				hidden:true,
				dataIndex:"id"
			},{
				header:"标题",
				width:250,
				dataIndex:"title",
				renderer:function(value, metadata, record, rowIndex,colIndex, store){
					return "<a target='_blank' href='http://huzhu.zz91.com/viewReply"+record.get("bbsPostId")+".htm'>"+value+"</a>"
				}
			},{
				header:"内容",
				width:600,
				dataIndex:"content"
			},{
				header:"浏览数",
				width:50,
				dataIndex:"visitedCount"
			},{
				header:"回复数",
				width:50,
				dataIndex:"replyCount"
			},{
				header:"所得积分",
				dataIndex:"score"
			},{
				header:"加减分理由",
				width:300,
				dataIndex:"remark"
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			cm:_cm,
			autoExpandColumn:4,
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
			})
		};
		
		ast.ast1949.bbs.score.scoreGrid.superclass.constructor.call(this,c);
		
	},
	
	listUrl:Context.ROOT+Context.PATH+"/bbs/score/queryScore.htm",
	
	mytoolbar:[{
		xtype:"combo",
		mode:"local",
		fieldLabel:"快速查询",
		emptyText:"积分类别默认：全部",
		triggerAction:"all",
		displayField:'name',
		valueField:'value',
		store:new Ext.data.JsonStore({
			fields : ['name', 'value'],
			data   : [
				{name:'全部',value:0},
				{name:'发贴',value:1},
				{name:'回帖',value:2},
				{name:'提问',value:3},
				{name:'回答',value:4},
				{name:'其他',value:5}
			]
		}),
		listeners:{
			"blur":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp(SCORE.GRID).getStore();
				var B = _store.baseParams;
				if(field.getValue() != ""){
					B["scoreType"] = field.getValue();
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
	}]
	
});

ast.ast1949.bbs.score.SearchForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};

		var c={
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 80,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "帐号：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["account"] = undefined;
							}else{
								B["account"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "昵称：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["nickname"] = undefined;
							}else{
								B["nickname"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "联系人：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["contact"] = undefined;
							}else{
								B["contact"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "公司名：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["name"] = undefined;
							}else{
								B["name"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				}]
			}],
			buttons:[{
				text:"按条件搜索",
				handler:function(btn){
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}]
		};

		ast.ast1949.bbs.score.SearchForm.superclass.constructor.call(this,c);

	}
});
