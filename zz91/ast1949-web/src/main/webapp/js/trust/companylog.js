Ext.namespace("ast.ast1949.trust");

ast.ast1949.trust.CompanyLogGrid=Ext.extend(Ext.grid.GridPanel,{
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
				{name:"companyName",mapping:"companyName"},
				{name:"companyId",mapping:"companyId"},
				{name:"trustAccount",mapping:"trustAccount"},
				{name:"trustType",mapping:"trustType"},
				{name:"star",mapping:"star"}
			],
			url:Context.ROOT +Context.PATH+  "/trust/queryCompanyLog.htm",
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
                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s')+"<br/>"+"公司:<a href='"+Context.ROOT+Context.PATH+"/trust/edit.htm?companyId="+record.get("companyId")+"' target='_blank'>"+record.get("companyName")+"</a>";
                }else{
                    return "";
                }
			}
		},{
			header:"小计类型",
			dataIndex:"content",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				return "公司小计";
			}
		},{
			header:"公司类型",
			dataIndex:"trustType",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				//0贸易商;1生产商;2供应商
				if(value==0){
					return "贸易商"; 
				}else if(value==1){
					return "生产商";
				}else if (value==2){
					return "供应商";
				}
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
			"->",{ 
				text:"查看本地CRM",
				handler:function(btn){
					if(config.companyId!=undefined && config.companyId>0){
						window.open("http://192.168.2.2/admin1/crmlocal/crm_tel_comp.asp?com_id="+config.companyId);
					}
				}
			}
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
		
		ast.ast1949.trust.CompanyLogGrid.superclass.constructor.call(this,c);
	},
	loadLogByCompanyId:function(companyId){
		this.getStore().baseParams["companyId"]=companyId;
		this.getStore().load({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}})
	}
});

ast.ast1949.trust.CompanyLogForm=Ext.extend(Ext.form.FormPanel,{
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
				},{
					xtype:"datefield",
					name:"gmtNextVisit",
					format:"Y-m-d H:i:s",
					fieldLabel:"下次电话时间",
					value:new Date
				}]
			},{
				columnWidth:.5,
				layout : "form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[
				       {
					xtype : "combo",
					mode:"local",
					triggerAction: 'all',
					hiddenName:"trustType",
					hiddenId:"trustType",
					fieldLabel : "公司类型",
					store:[
					       	["0","贸易商"],
					       	["1","生产商"],
					       	["2","供应商"]
					      ]
			},
				       {
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
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:Context.ROOT+Context.PATH+"/trust/saveCompanyLog.htm?companyId="+form.companyId,
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
		}
		
		ast.ast1949.trust.CompanyLogForm.superclass.constructor.call(this,c);
	}
});

