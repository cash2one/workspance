Ext.namespace("ast.ast1949.trust");

ast.ast1949.trust.LogGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:[
				{name:"id",mapping:"id"},
				{name:"gmtCreated",mapping:"gmtCreated"},
				{name:"content",mapping:"content"},
				{name:"gmtNextVisit",mapping:"gmtNextVisit"},
				{name:"situation",mapping:"situation"},
				{name:"trustType",mapping:"trustType"},
				{name:"offerCompanyName",mapping:"offerCompanyName"},
				{name:"offerCompanyId",mapping:"offerCompanyId"},
				{name:"trustAccount",mapping:"trustAccount"},
				{name:"trustType",mapping:"trustType"},
				{name:"star",mapping:"star"}
			],
			url:Context.ROOT +Context.PATH+  "/trust/queryBuyLog.htm",
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var grid = this;
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "编号",
			width : 50,
			sortable : true,
			dataIndex : "id",
			hidden:true
		},{
			header:"联系时间",
			dataIndex:"gmtCreated",
			width:200,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
                if(value!=null){
                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s')+"<br/>"+"供货商:<a href='"+Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+record.get("offerCompanyId")+"' target='_blank'>"+record.get("offerCompanyName")+"</a>";
                }else{
                    return "";
                }
			}
		},{
			header:"小计类型",
			dataIndex:"content",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				return "采购单小计";
			}
		},{
			header:"联系情况",
			dataIndex:"situation",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value==1){
					return "有效联系";
				}else{
					return "无效联系";
				}
			}
		},{
			header:"交易员",
			dataIndex:"trustAccount",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				return value;
			}
		},{
			header:"星级",
			dataIndex:"star",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value>0){
					return value+"星";
				}
				return value;
			}
		},{
			header:"联系内容",
			dataIndex:"content",
			width:500,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var table="<table border='0' cellpadding='3' cellspacing='1' bgcolor='#7386a5' style='width:99%;white-space:normal;'>";
				table = table + "<tr><td width='50' bgcolor='#ced7e7'>记录</td>";
				table=table+"<td bgcolor='#ffffff'>"+value+"</td></tr>";
				table=table+"</table>";
				return table;
			}
		}
		]);
		
		var _tbar=config.tbar||[
			{
				xtype:"combo",
				triggerAction : "all",
				forceSelection : true,
				width:200,
				emptyText : "请选择供货商",
				displayField : "name",
				valueField : "id",
				hiddenId:"offerCompanyId",
				hiddenName:"offerCompanyId",
				id:"offerCompany",
				store:new Ext.data.JsonStore( {
					fields : [ "id", "name" ],
					autoLoad:true,
					url : Context.ROOT+Context.PATH + "/trust/queryOffer.htm?buyId="+config.buyId
				}),
				listeners:{
					"blur":function(field){
						if(Ext.get("offerCompany").dom.value==""){
							field.setValue("");
						}
						var B=_store.baseParams;
						if(B["offerCompanyId"]==field.getValue()){
							return false;
						}
						B["offerCompanyId"]=field.getValue();
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
			},{
				xtype:"combo",
				triggerAction : "all",
				forceSelection : true,
				width:200,
				emptyText : "采购单号",
				displayField : "buyNo",
				valueField : "id",
				id:"buyNoSelectId",
				store:new Ext.data.JsonStore( {
					fields : [ "id", "buyNo" ],
					autoLoad:true,
					url : Context.ROOT+Context.PATH + "/trust/queryBuyNo.htm?companyId="+config.companyId
				}),
				listeners:{
					"blur":function(field){
						if(Ext.get("buyNoSelectId").dom.value==""){
							field.setValue("");
						}
						var B=_store.baseParams;
						if(B["buyId"]==field.getValue()){
							return false;
						}
						B["buyId"]=field.getValue();
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
					}
				}
			},"->",{ 
				text:"查看本地CRM",
				handler:function(btn){
					if(config.companyId!=undefined && config.companyId>0){
						window.open("http://192.168.2.2/admin1/crmlocal/crm_tel_comp.asp?com_id="+config.companyId);
					}
				}
			}
//			,'-',{
//                xtype:"checkbox",
//                boxLabel:"只显示公海",
//                handler:function(btn){
//                    var B=_store.baseParams||{};
//                    if(btn.getValue()){
//                            B["csFlag"]="Y";
//                    }else{
//                            B["csFlag"]="N";
//                    }
//                    _store.baseParams = B;
//                    _store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
//                }
//            }
		]
			
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
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
			tbar:_tbar
		};
		
		ast.ast1949.trust.LogGrid.superclass.constructor.call(this,c);
	},
	loadLog:function(buyId){
		this.topToolbar[1].hidden=true;
		this.getStore().baseParams["buyId"]=buyId;
		this.getStore().load({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}})
	},
	loadLogByCompanyId:function(companyId){
		this.companyId = companyId;
		this.getStore().baseParams["companyId"]=companyId;
		this.getStore().load({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}})
	}
});

ast.ast1949.trust.LogForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var form=this;
		
		var c={
			autoScroll:true,
			labelAlign : "right",
			labelWidth : 90,
			frame : true,
			layout : "column",
			items:[{
				columnWidth:.5,
				layout : "form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype : "combo",
					id : "ofCompanyId",
					name:"ofCompanyId",
					hiddenName:"offerCompanyId",
					hiddenId:"offerCompanyId",
					displayField:"name",
					valueField : "id",
					fieldLabel : "供货商",
					triggerAction: 'all',
					forceSelection : true,
					editable :false,
					store:new Ext.data.JsonStore({
						autoLoad:true,
						url:Context.ROOT+Context.PATH + "/trust/queryOffer.htm?buyId="+form.buyId,
						fields:["id","name"]
					}),
					listeners:{
						"expand":function(combo){
						}
					}
			},{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenName:"situation",
					hiddenId:"situation",
					fieldLabel:"联系情况",
					store:[
					       	["1","有效小计"],
					       	["0","无效小计"]
					      ]
				}]
			},{
				columnWidth:.5,
				layout : "form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					name:"gmtNextVisit",
					format:"Y-m-d H:i:s",
					fieldLabel:"下次电话时间",
					value:new Date
				},{
					xtype:"combo",
					mode:"local",
					triggerAction:"all",
					hiddenName:"star",
					hiddenId:"star",
					fieldLabel:"星级",
					store:[
					       	["1","1星"],
					       	["2","2星"],
					       	["3","3星"],
					    	["4","4星"],
					    	["5","5星"]
					      ]
				}]
			}
			,{
				columnWidth:1,
				layout:"form",
				items:[{
					xtype:"textarea",
					name:"content",
					id:"content",
					fieldLabel:"小记内容",
					width:"99%"
				}
				]
			}
			],
			buttonAlign:"right",
			buttons:[{
				text:"保存",
				iconCls:"item-true",
				handler:function(btn){
					form.getForm().submit({
						url:Context.ROOT+Context.PATH+"/trust/saveLog.htm?buyId="+form.buyId,
						method:"post",
						type:"json",
						success:function(){
							//刷新日志表格
							Ext.getCmp("loggrid").getStore().reload();
							Ext.get("content").dom.value='';
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "保存成功！",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.INFO
							});
						},
						failure:function(){
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "保存失败！",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});
				}
			}]
		}
		
		ast.ast1949.trust.LogForm.superclass.constructor.call(this,c);
	}
});

