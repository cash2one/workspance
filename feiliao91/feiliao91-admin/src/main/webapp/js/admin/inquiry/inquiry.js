
/**
 * Modify By Rolyer 2011.01.18
 * TODO:
 * 1、询盘导出为供求
 */
Ext.namespace("ast.ast1949.admin.inquiry");

/**
 * 询盘信息列表
 * @class ast.ast1949.admin.inquiry.ResultGrid
 * @extends Ext.grid.GridPanel
 */
ast.ast1949.admin.inquiry.ResultGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
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
		
		var _sm = new Ext.grid.CheckboxSelectionModel();
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header : "编号",
				width : 40,
				sortable : true,
				dataIndex : "id",
				hidden:true
			},{
				xtype : "hidden",
				header : "标题",
				width:120,
				sortable : false,
				dataIndex : "title",
				disabled:false
			},{
				header:"询盘类型",
				width:60,
				sortable:false,
				dataIndex:"be_inquired_type",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					var str="询盘回复";
					if(value=="1"){
						str="<a href='"+Context.ROOT + Context.PATH+"/crm/company/compInfoReadonly.htm?companyId="+record.get("be_inquired_id")+"' target='_blank'>对公司询盘</a>";
					}else if(value=="0"){
						str="<a href='http://trade.zz91.com/productdetails"+record.get("be_inquired_id")+".htm' target='_blank'>对供求询盘</a>";
					}
					return str;
				}
			},{
				header:"发送账号",
				width:70,
				sortable:false,
				dataIndex:"sender_account"
			},{
				header:"接收账号",
				sortable:false,
				dataIndex:"receiver_account",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value!=null) {
						return value;
					} else {
						return record.get("receiverId");
					}
				}
			},{
				header:"接收邮箱",
				sortable:false,
				dataIndex:"receiver_account",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value!=null) {
						Ext.Ajax.request({
							url:Context.ROOT + Context.PATH + "/admin/inquiry/queryInquiryEmailByAccount.htm",  
							params:{
								account:value
							},
							success: function(resp,opts) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.get("email-"+value+rowIndex).dom.innerText=respText.email;
							},
							failure: function(resp,opts) {   
								
							}
						});
						return "<div id='email-"+value+rowIndex+"'></div>";
					} else {
						return "没有找到email";
					}
				}
			},{
				header:"询盘内容",
				width:180,
				sortable:false,
				renderer:function(value, metadata, record, rowIndex, colIndex, store){
					return String.format("<div style='width:99%;white-space:normal;'>{0}</div>", record.get("content"));
				}
			},{
				header : "询盘时间",
				width : 80,
				sortable : true,
				dataIndex : "send_time",
				renderer : function(value, metadata, record, rowIndex,
						colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time),
								'Y-m-d H:i:s');
					}
				}
			}
		]);
		
		var mybar=this.toolbar;
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:mybar,
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
		
		ast.ast1949.admin.inquiry.ResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"title",mapping:"title"},
		{name:"content",mapping:"content"},
		{name:"sender_account",mapping:"senderAccount"},
		{name:"receiver_account",mapping:"receiverAccount"},
		{name:"be_inquired_type",mapping:"beInquiredType"},
		{name:"be_inquired_id",mapping:"beInquiredId"},
		{name:"send_time",mapping:"sendTime"}
	]),
	listUrl:Context.ROOT + Context.PATH + "/admin/inquiry/queryInquiry.htm",
	loadBySender:function(account){
		var B=this.getStore().baseParams||{};
		B["receiverAccount"]=null;
		B["senderAccount"]=account;
		B["isRubbish"]=null;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	loadByReceiver:function(account){
		var B=this.getStore().baseParams||{};
		B["receiverAccount"]=account;
		B["senderAccount"]=null;
		B["isRubbish"]="0";
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	showdetail:function(details){
		Ext.MessageBox.show({
			title:"询盘详细信息",
			msg : details,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	}
});
