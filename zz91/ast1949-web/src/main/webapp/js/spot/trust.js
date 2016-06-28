Ext.namespace("ast.ast1949.spot.trust");

ast.ast1949.spot.trust.INFOFIELD=[
    {name:"id",mapping:"spotTrust.id"},
    {name:"mobile",mapping:"spotTrust.mobile"},
    {name:"title",mapping:"spotTrust.title"},
    {name:"useful",mapping:"spotTrust.useful"},
    {name:"contact",mapping:"spotTrust.contact"},
	{name:"quantity",mapping:"spotTrust.quantity"},
	{name:"area",mapping:"spotTrust.area"},
	{name:"isChecked",mapping:"spotTrust.isChecked"},
	{name:"isDelete",mapping:"spotTrust.isDelete"},
	{name:"gmtCreated",mapping:"spotTrust.gmtCreated"}
];

ast.ast1949.spot.trust.FIELD=[
	{name:"id",mapping:"id"},
	{name:"mobile",mapping:"mobile"},
	{name:"title",mapping:"title"},
	{name:"useful",mapping:"useful"},
	{name:"contact",mapping:"contact"},
	{name:"quantity",mapping:"quantity"},
	{name:"area",mapping:"area"}
];

ast.ast1949.spot.trust.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var grid=this;

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				herder:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
				header : "标题",
				name : "title",
				id : "title",
				width:80,
				sortable:false,
				dataIndex:"title"
			}
			,{
				header : "使用于",
				name : "useful",
				id : "useful",
				width:80,
				sortable:false,
				dataIndex:"useful"
			},{
				header : "手机",
				width : 200,
				sortable : false,
				dataIndex : "mobile"
			},{
				header : "联系人",
				width : 150,
				sortable : false,
				dataIndex : "contact"
			},{
				header : "数量",
				width : 150,
				sortable : false,
				dataIndex : "quantity"
			},{
				header : "地区",
				width : 150,
				sortable : false,
				dataIndex : "area"
			},{
				header : "审核状态",
				width : 100,
				sortable : false,
				dataIndex : "isChecked",
				renderer:function(value,metadata,record,rowIndex,colIndex,store){
					var str = "";
					if(value=="0"){
						str = "审核中...";
					}else if(value=="1"){
						str = "审核成功";
					}else if(value=="2"){
						str = "退回";
					}
					return str
				}
			},{
				header : "删除状态",
				width : 100,
				sortable : false,
				dataIndex : "isDelete",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					var str = "";
					if(value=="0"){
						str = "未删除";
					}else if(value=="1"){
						str = "删除";
					}
					return str
				}
			},{
				header : "创建时间",
				width : 128,
				sortable : false,
				dataIndex : "gmtCreated",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
					else{
						return "";
					}
				}
			}]);

		var storeUrl = Context.ROOT + Context.PATH + "/spot/queryTrust.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.spot.trust.INFOFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [
		{
			text:"添加",
			iconCls:"add",
			handler:function(btn){
				var rows=grid.getSelectionModel().getSelected();
				ast.ast1949.spot.trust.updateTrustInfo(0);
			}
		},{
			text:"编辑",
			iconCls:"edit",
			handler:function(btn){
				var rows=grid.getSelectionModel().getSelected();
				ast.ast1949.spot.trust.updateTrustInfo(rows.get("id"));
			}
		},"-",{
			text:"审核通过",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/spot/updateCheckedForTrust.htm",
						params:{
							"id":rows[i].get("id"),
							"isChecked":1
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","审核成功");
								_store.reload();
							}else{
								ast.ast1949.utils.Msg("","操作失败");
							}
						}
					})
				}
			}
		},{
			text:"退回",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/spot/updateCheckedForTrust.htm",
						params:{
							"id":rows[i].get("id"),
							"isChecked":2
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","退回成功");
								_store.reload();
							}else{
								ast.ast1949.utils.Msg("","操作失败");
							}
						}
					})
				}
			}
		},"-",{
			text:"删除",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/spot/updateDeleteForTrust.htm",
						params:{
							"id":rows[i].get("id"),
							"isDelete":1
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","操作成功");
								_store.reload();
							}else{
								ast.ast1949.utils.Msg("","操作失败");
							}
						}
					})
				}
			}
		},{
			text:"取消删除",
			handler:function(){
				var rows=grid.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					Ext.Ajax.request({
						url:Context.ROOT + Context.PATH + "/spot/updateDeleteForTrust.htm",
						params:{
							"id":rows[i].get("id"),
							"isDelete":0
						},
						success:function(response,opt){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								ast.ast1949.utils.Msg("","操作成功");
								_store.reload();
							}else{
								ast.ast1949.utils.Msg("","操作失败");
							}
						}
					})
				}
			}
		},"->",{
			xtype:"label",
			text:"手机："
		},{
			xtype:"textfield",
			fieldLabel : "手机号码：",
			listeners:{
				"blur":function(field){
					var B = _store.baseParams;
					if(Ext.get(field.getId()).dom.value!=""){
						B["mobile"] = field.getValue();
					}else{
						B["mobile"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},"-",{
			xtype:"combo",
			id:"selectTime",
			mode:"local",
			emptyText:"审核状态",
			fieldLabel:"审核状态：",
			triggerAction:"all",
			displayField:'name',
			valueField:'value',
			autoSelect:true,
			store:new Ext.data.JsonStore({
				fields : ['name', 'value'],
				data   : [
					{name:'未审核',value:'0'},
					{name:'通过',value:'1'},
					{name:'退回',value:'2'}
				]
			}),
			listeners:{
				"blur":function(field){
					var B = _store.baseParams;
					if(Ext.get(field.getId()).dom.value!=""){
						B["isChecked"] = field.getValue();
					}else{
						B["isChecked"]=undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		}];

		var c={
			loadMask:Context.LOADMASK,
			sm : sm,
			autoExpandColumn:7,
			cm : cm,
			iconCls : "icon-grid",
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
			listeners:{
				"rowcontextmenu":function(g,rowIndex,e){
					if(!g.getSelectionModel().isSelected(rowIndex)){
						g.getSelectionModel().clearSelections();
						g.getSelectionModel().selectRow(rowIndex);
					}
					e.preventDefault();
					if(g.contextmenu!=null){
						g.contextmenu.showAt(e.getXY());
					}
				}
			}
		};
		ast.ast1949.spot.trust.Grid.superclass.constructor.call(this,c);
	},
	loadDefault:null,
	contextmenu:null
});

ast.ast1949.spot.trust.SearchForm = Ext.extend(Ext.form.FormPanel,{
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
				},{
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
					fieldLabel : "手机号码：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["mobile"] = undefined;
							}else{
								B["mobile"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					fieldLabel : "邮箱：",
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue==""){
								B["email"] = undefined;
							}else{
								B["email"] = newvalue;
							}
							_store.baseParams = B;
						}
					}
				},{
					xtype:"combo",
					triggerAction : "all",
					forceSelection : true,
					fieldLabel:"黑名单原因：",
					displayField : "content",
					valueField : "id",
					id : "reason",
					store:new Ext.data.JsonStore( {
						fields : [ "content", "id" ],
						autoLoad:true,
						url : Context.ROOT + Context.PATH + "/admin/descriptiontemplate/queryList.htm?templateCode=10341002"
					}),
					listeners:{
						"change":function(field,newvalue,oldvalue){
							if(newvalue!="") {
								B["reason"] =  Ext.get("reason").dom.value;;
							}else{
								B["reason"] = null;
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

		ast.ast1949.spot.trust.SearchForm.superclass.constructor.call(this,c);

	}
});


//账户信息修改
ast.ast1949.spot.trust.updateTrustInfo=function(id){
	
	var form=new ast.ast1949.spot.trust.UpdateTrustInfoForm({
	});
	
	form.lodInit(id);
	
	var win = new Ext.Window({
		id:"updatetrustwin",
		title:"委托信息",
		width:500,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.spot.trust.UpdateTrustInfoForm = Ext.extend(Ext.form.FormPanel,{
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
				fieldLabel:"标题",
				name:"title",
				id:"title"
			},{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"联系人",
				name:"contact",
				id:"contact"
			},{
				fieldLabel:"手机",
				name:"mobile",
				id:	"mobile"
			},{
				fieldLabel:"数量",
				name:"quantity",
				id:	"quantity"
			},{
				fieldLabel:"地区",
				name:"area",
				id:"area"
			},{
				fieldLabel:"使用于",
				name:"useful",
				id:"useful"
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("trustGrid");
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/spot/updateTrustInfo.htm",
							method:"post",
							type:"json",
							success:function(){
								ast.ast1949.utils.Msg("","保存成功");
								Ext.getCmp("updatetrustwin").close();
								grid.getStore().reload();	
							},
							failure:function(){
								ast.ast1949.utils.Msg("","保存失败");
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
		
		ast.ast1949.spot.trust.UpdateTrustInfoForm.superclass.constructor.call(this,c);
	},
	lodInit:function(id){
		var form=this;
		if(form.store!=null){
			form.store.reload();
			return ;
		}
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.spot.trust.FIELD,
			url : Context.ROOT+Context.PATH+"/spot/queryTrustById.htm?id="+id, 
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
