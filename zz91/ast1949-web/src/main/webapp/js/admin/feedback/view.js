Ext.namespace("ast.ast1949.admin.feedback")

ast.ast1949.admin.feedback.CATEGORY={"1":"myrc问题反馈","2":"积分商城留言","3":"CRM客服留言","4":"PVB竞拍留言"};
ast.ast1949.admin.feedback.CHECKSTATUS={"0":"新留言","1":"已处理","2":"不处理"};
/**
 * 信息列表
 * @class ast.ast1949.admin.feedback.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.feedback.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
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
			autoLoad:false
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({
			listeners: {
				selectionchange: function(sm) {
	                if (sm.getCount()) {
						Ext.getCmp("reply").enable();
						Ext.getCmp("notcheck").enable();
						Ext.getCmp("delete").enable();
	                } else {
	                	Ext.getCmp("reply").disable();
						Ext.getCmp("notcheck").disable();
						Ext.getCmp("delete").disable();
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
				header:"客户(公司、联系人)",
				dataIndex:"name",
				width:180,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					var _contact=record.get("contact");
					if(_contact!=null){
						return value + " ("+_contact+")"
					}
					else{
						return value;
					}
				}
			},{
				header:"联系方式",
				dataIndex:"tel",
				width:100,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					var _tel=record.get("tel");
					var _telAreaCode=record.get("telAreaCode");
					var _telCountryCode=record.get("telCountryCode");
					var _mobile=record.get("mobile");
					var val="";
					
					if(_telCountryCode!=null&&_telCountryCode.length>0) {
						val+=_telCountryCode+"-";
					}
					if(_telAreaCode!=null&&_telAreaCode.length>0) {
						val+=_telAreaCode+"-";
					}
					if(_tel!=null&&_tel.length>0) {
						val+=_tel;
					}
					if(_mobile!=null&&_mobile.length>0) {
						val+="<br/>"+_mobile;
					}
					
					return val;
				}
			},{
				header:"标题",
				dataIndex:"title",
				width:100,
				sortable:false
			},{
				header:"内容",
				dataIndex:"content",
				width:200,
				sortable:false
			},{
				header:"回复内容",
				dataIndex:"replyContent",
				width:200,
				sortable:false
			},{
				header:"反馈时间",
				dataIndex:"gmtCreated",
				width:100,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"回复时间",
				dataIndex:"gmtReply",
				width:100,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			},{
				header:"处理人",
				dataIndex:"checkPerson",
				width:100,
				sortable:false
			},{
				header:"处理状态",
				dataIndex:"checkStatus",
				width:100,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return ast.ast1949.admin.feedback.CHECKSTATUS[value];
					}else{
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
			autoExpandColumn:5,
			cm:_cm,
			tbar:[
				{
					id:"reply",
					iconCls:"add",
					text:"现在回复",
					disabled:true,
					handler:function(btn){
						var row = grid.getSelections();
						var selectedRecord = grid.getSelectionModel().getSelected();
						if(row.length>1){
							ast.ast1949.utils.Msg("","最多只能选择一条记录！");
						} else {
							var row = grid.getSelections();
							var _id=row[0].get("id");
							ast.ast1949.admin.feedback.ReplyWin(_id);
						}
					}
				},
				{
					id:"notcheck",
					iconCls:"item-edit",
					text:"不处理",
					disabled:true,
					handler:function(btn){
						notReply();
					}
				},{
					iconCls:"delete",
					id:"delete",
					text:"删除",
					disabled:true,
					handler:function(btn){
						Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
					}
				},"->",{
					xtype:          'combo',
					id:"category",
					hiddenId:"category",
                    mode:           'local',
                    triggerAction:  'all',
                    forceSelection: true,
                    displayField:   'name',
                    valueField:     'value',
                    width:100,
                    store:          new Ext.data.JsonStore({
                        fields : ['name', 'value'],
                        data   : [
                        	{name : "全部类别",  value: ''},
                            {name : ast.ast1949.admin.feedback.CATEGORY["1"],  value: '1'},
                            {name : ast.ast1949.admin.feedback.CATEGORY["2"],  value: '2'},
                            {name : ast.ast1949.admin.feedback.CATEGORY["3"],  value: '3'},
                            {name : ast.ast1949.admin.feedback.CATEGORY["4"],  value: '4'}
                        ]
                    }),
					listeners:{
						"blur":function(c){
							var v=Ext.get("category").dom.value;
							if(v==""){
								c.setValue("");
							}
							var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID)
							var B=grid.getStore().baseParams||{};
							B["category"] = c.getValue();
							grid.getStore().baseParams = B;
							grid.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
						}
					}
				},"-",{
					xtype:          'combo',
					id:"checkstatus",
					hiddenId:"hiddencheckstatus",
                    mode:           'local',
                    triggerAction:  'all',
                    forceSelection: true,
                    displayField:   'name',
                    valueField:     'value',
                    width:100,
                    store:          new Ext.data.JsonStore({
                        fields : ['name', 'value'],
                        data   : [
                        	{name : '全部状态',   value: ''},
                        	{name : ast.ast1949.admin.feedback.CHECKSTATUS["0"],   value: '0'},
                        	{name : ast.ast1949.admin.feedback.CHECKSTATUS["1"],   value: '1'},
                        	{name : ast.ast1949.admin.feedback.CHECKSTATUS["2"],   value: '2'}
                        ]
                    }),	
					listeners:{
						"blur":function(c){
							var v=Ext.get("checkstatus").dom.value;
							if(v==""){
								c.setValue("");
							}
							var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID)
							var B=grid.getStore().baseParams||{};
							B["checkStatus"] = c.getValue();
							grid.getStore().baseParams = B;
							grid.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
						}
					}
				},"-",{
                    xtype:"checkbox",
                    boxLabel:"只显示我的",
                    handler:function(btn){
                        var B=_store.baseParams||{};
//                                      
                        if(btn.getValue()){
                                B["onlyme"]="Y";
                        }else{
                                B["onlyme"]="N";
                        }
                        _store.baseParams = B;
                        _store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
                    }
                }
			],
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
		
		ast.ast1949.admin.feedback.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"feedback.id"},
		{name:"title",mapping:"feedback.title"},
		{name:"content",mapping:"feedback.content"},
		{name:"replyContent",mapping:"feedback.replyContent"},
		{name:"gmtCreated",mapping:"feedback.gmtCreated"},
		{name:"gmtReply",mapping:"feedback.gmtReply"},
		{name:"checkStatus",mapping:"feedback.checkStatus"},
		{name:"checkPerson",mapping:"feedback.checkPerson"},
		{name:"name",mapping:"company.name"},
		{name:"contact",mapping:"contact.contact"},
		{name:"tel",mapping:"contact.tel"},
		{name:"telAreaCode",mapping:"contact.telAreaCode"},
		{name:"telCountryCode",mapping:"contact.telCountryCode"},
		{name:"mobile",mapping:"contact.mobile"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/admin/feedback/query.htm",
	//render后调用
	loadNewFeedback:function(){
		Ext.getCmp("checkstatus").setValue("0");
		Ext.getCmp("category").setValue("");
		this.getStore().baseParams["checkStatus"]="0";
		this.getStore().reload();
	}
});

/**
 * 
 * @class ast.ast1949.admin.feedback.ReplyForm
 * @extends Ext.form.FormPanel
 */
ast.ast1949.admin.feedback.ReplyForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 100,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			items:[
				{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						id:"id",
						name:"id"
					},{
						name:"title",
						fieldLabel:"标题",
						allowBlank:true
					},{
						xtype:"textarea",
						name:"content",
						fieldLabel:"留言内容",
						allowBlank:true
					},{
						xtype:"textarea",
						name:"replyContent",
						fieldLabel:"回复内容",
						allowBlank:true
					}]
				}
			],
			buttons:[{
				text:"确定",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp("REPLY_WIN").close();
				},
				scope:this
			}
			]
		};
		
		ast.ast1949.admin.feedback.ReplyForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadRecords:function(id){
	var _fields=[
			{name:"id",mapping:"id"},
			{name:"title",mapping:"title"},
			{name:"content",mapping:"content"},
			{name:"replyContent",mapping:"replyContent"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/feedback/querySimpleFeedbackById.htm",
			baseParams:{"id":id},
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
		})
	},
	
	saveUrl:Context.ROOT+Context.PATH + "/admin/feedback/reply.htm",
	save:function(){
		var _url = this.saveUrl;
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:_url,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			ast.ast1949.utils.Msg("","验证未通过");
		}
	},
	onSaveSuccess:function (){
		ast.ast1949.utils.Msg("","保存成功！");
		
		var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID);
		grid.getStore().reload();
		
		Ext.getCmp(PAGE_CONST.REPLY_WIN).close();
	},
	onSaveFailure:function (){
		ast.ast1949.utils.Msg("","保存失败！");
	}
});

/**
 * 
 */
ast.ast1949.admin.feedback.ReplyWin=function(id){
	var form = new ast.ast1949.admin.feedback.ReplyForm({
		id:PAGE_CONST.REPLY_FORM,
		region:"center"
	});
	
	var win = new Ext.Window({
		id:PAGE_CONST.REPLY_WIN,
		title:"回复留言",
		width:"70%",
		modal:true,
		items:[form]
	});
	form.loadRecords(id)
	win.show();
};

/**
 * 
 */
function notReply(){
	
	var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID);
	var row = grid.getSelections();
	
	var sccess=0;
	var step=0;
	for (var i=0,len = row.length;i<len;i++){
		step++;
		var _id=row[i].get("id");
		
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/feedback/notReply.htm?id=" + _id + "&st=" + Math.random(),
			method : "get",
			scope : this,
			callback : function(options,success,response){
				var res= Ext.decode(response.responseText);
				if(res.success){
					sccess++;
					if(step==row.length) {
						if(sccess==row.length) {
							ast.ast1949.utils.Msg("","选定的记录已成功更新!");
							grid.getStore().reload();
						} else {
							ast.ast1949.utils.Msg("","所选记录更新失败!");
						}
					}
				}
			}
		});
	}
};

/**
 * 删除
 * @param {} _btn
 */
function doDelete(_btn){
	if(_btn != "yes")
		return ;
	var grid = Ext.getCmp(PAGE_CONST.RESULT_GRID);
	var row = grid.getSelections();
	
	var sccess=0;
	var step=0;
	for (var i=0,len = row.length;i<len;i++){
		step++;
		var _id=row[i].get("id");
		
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/feedback/delete.htm?id=" + _id + "&st=" + Math.random(),
			method : "get",
			scope : this,
			callback : function(options,success,response){
				var res= Ext.decode(response.responseText);
				if(res.success){
					sccess++;
					if(step==row.length) {
						if(sccess==row.length) {
							ast.ast1949.utils.Msg("","选定的记录已被删除!");
							grid.getStore().reload();
						} else {
							ast.ast1949.utils.Msg("","所选记录删除失败!");
						}
					}
				}
			}
		});
	}
};

