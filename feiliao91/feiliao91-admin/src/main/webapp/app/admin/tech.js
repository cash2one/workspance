Ext.ns("ast.ast1949.admin.tech");

Ext.define("TechGridModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"title",mapping:"title"},
		{name:"postTime",mapping:"postTime"},
		{name:"label",mapping:"label"},
		{name:"viewCount",mapping:"viewCount"}
	]
});

Ext.define("TechFormdModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"id"},
		{name:"title",mapping:"title"},
		{name:"tags",mapping:"tags"},
		{name:"postTime",mapping:"postTime"},
		{name:"categoryCode",mapping:"categoryCode"},
		{name:"label",mapping:"label"},
		{name:"viewCount",mapping:"viewCount"}
	],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/admin/tech/queryOneTech.htm"
		}
	}
});

Ext.define("ast.ast1949.admin.tech.MainGrid", {
	extend:"Ext.grid.Panel",
	initComponent:function(){
		var _store=Ext.create("Ext.data.Store",{
			model:"TechGridModel",
			remoteSort:true,
			pageSize:Context.PAGESIZE,
			proxy:{
				type:"ajax",
				url:Context.ROOT+"/zz91/admin/tech/query.htm",
				startParam:Context.START,
				limitParam:Context.LIMIT,
				simpleSortMode:true,
				reader: {
		            type: 'json',
		            root: 'records',
		            totalProperty:"totalRecords"
		        },
		        actionMethods:{
		        	reader:"post"
		        }
			},
			autoLoad:true
		});
		
		var _sm=Ext.create("Ext.selection.CheckboxModel",{});
		
		var _cm=[
			{text:"编号",dataIndex:"id",width:30,hidden:true},
			{text:"标题",dataIndex:"title",width:400,sortable:false},
			{text:"类型",dataIndex:"label",sortable:false},
			{text:"点击量",dataIndex:"viewCount",sortable:true},
			{text:"创建时间",dataIndex:"postTime", width:200, renderer:function(v){
				if(v!=null){
					return Ext.Date.format(new Date(v.time),"Y-m-d H:i:s");
				}
				return "";
			}}
		];
		
		var c={
			id:"techresultgrid",
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
					text:"添加",
					handler:this.createModel
				},{
					xtype:"button",
					iconCls:"edit16",
					text:"修改",
					scope:this,
					handler:this.editModel
				},{
					xtype:"button",
					iconCls:"delete16",
					text:"删除",
					scope:this,
					handler:function(btn,e){
						this.deleteModel(this.getSelectionModel().getSelection());
					}
				},"->",{
					xtype:"combo",
					emptyText:"请选择类别筛选",
					name:"categoryCode",
					displayField:'name',
					valueField:'value',
					store:Ext.create('Ext.data.Store', {
						fields: ['name', 'value'],
						data : [
							{name:'废金属',value:'1000'},
							{name:'废铜',value:'10001000'},
							{name:'废铝',value:'10001001'},
							{name:'废钢铁',value:'10001002'},
							{name:'其他废金属',value:'10001003'},
							{name:'行业标准',value:'10001004'},
							{name:'加工工艺',value:'10001005'},
							{name:'产品图库',value:'10001006'},
							{name:'废塑料',value:'1001'},
							{name:'行业知识',value:'10011000'},
							{name:'助剂改性',value:'10011001'},
							{name:'工艺配方',value:'10011002'},
							{name:'机械设备',value:'10011003'},
							{name:'加工技术',value:'10011004'},
							{name:'产品图库',value:'10011005'},
							{name:'故障分析',value:'10011006'},
							{name:'废纸与橡胶',value:'1002'},
							{name:'废纸鉴别',value:'10021000'},
							{name:'废纸知识',value:'10021001'},
							{name:'废纸技术',value:'10021002'},
							{name:'其他废料',value:'1003'},
							{name:'其它废料鉴别',value:'10031000'},
							{name:'其它废料知识',value:'10031001'},
							{name:'其它废料技术',value:'10031002'}
						]
					}),
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
		Ext.Msg.confirm(Context.MSG_TITLE,"展会信息删除后将无法恢复，您确定要删除这些展会信息吗？",function(o){
			if(o!="yes"){
				return ;
			}
			Ext.Array.each(selections, function(obj, index, countriesItSelf){
				Ext.Ajax.request({
					url: Context.ROOT+"/zz91/admin/tech/delete.htm",
					params: {
						id: obj.getId()
					},
					success: function(response){
						_this.getStore().load();
					}
				});
			});
		});
	},
	createModel:function(btn,e){
		window.open(Context.ROOT+"/zz91/admin/tech/detail.htm")
	},
	editModel:function(btn,e){
		var rowModel=this.getSelectionModel().getLastSelected();
		window.open(Context.ROOT+"/zz91/admin/tech/detail.htm?id="+rowModel.getId())
	}
});

Ext.define("ast.ast1949.admin.tech.MainForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			bodyPadding: 5,
			fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 60,
//		        msgTarget:"under",
		        labelSeparator:""
		    },
		    layout:"anchor",
			items:[{
				xtype:"hidden",
				id:"id",
				name:"id"
			},{
				xtype:"textfield",
				name:"title",
				anchor:"100%",
				allowBlank:false,
				formItemCls:"x-form-item required",
				fieldLabel:"标题"
			},{
				xtype: 'container',
				anchor:"100%",
				layout:"column",
				border:0,
				items:[{
					xtype: 'container',
					columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
				    items:[{
				    	xtype:"textfield",
						name:"tags",
						anchor:"100%",
						fieldLabel:"标签"
					},{
						xtype:"datefield",
						anchor:"100%",
				    	format:"Y-m-d H:i:s",
						fieldLabel : '发布时间',
						name : 'postTime',
						id : 'postTime',
						allowBlank:false,
						formItemCls:"x-form-item required"
					}
					]
			    },{
			    	xtype: 'container',
			    	columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
					items:[{
						xtype:"textfield",
						name:"viewCount",
						anchor:"100%",
						fieldLabel:"点击量"
					},{
						xtype:"combo",
						fieldLabel : '类别',
						name:"categoryCode",
						allowBlank:false,
						formItemCls:"x-form-item required",
						displayField:'name',
						valueField:'value',
						store:Ext.create('Ext.data.Store', {
							fields: ['name', 'value'],
							data : [
								{name:'废金属',value:'1000'},
								{name:'废铜',value:'10001000'},
								{name:'废铝',value:'10001001'},
								{name:'废钢铁',value:'10001002'},
								{name:'其他废金属',value:'10001003'},
								{name:'行业标准',value:'10001004'},
								{name:'加工工艺',value:'10001005'},
								{name:'产品图库',value:'10001006'},
								{name:'废塑料',value:'1001'},
								{name:'行业知识',value:'10011000'},
								{name:'助剂改性',value:'10011001'},
								{name:'工艺配方',value:'10011002'},
								{name:'机械设备',value:'10011003'},
								{name:'加工技术',value:'10011004'},
								{name:'产品图库',value:'10011005'},
								{name:'故障分析',value:'10011006'},
								{name:'废纸与橡胶',value:'1002'},
								{name:'废纸鉴别',value:'10021000'},
								{name:'废纸知识',value:'10021001'},
								{name:'废纸技术',value:'10021002'},
								{name:'其他废料',value:'1003'},
								{name:'其它废料鉴别',value:'10031000'},
								{name:'其它废料知识',value:'10031001'},
								{name:'其它废料技术',value:'10031002'}
							]
						})
					}]
				}]
			}],
			buttons:[{
				scale:"large",
				xtype:"button",
				text:"马上保存",
				iconCls:"saveas32",
				handler:this.saveModel
			},{
				scale:"large",
				xtype:"button",
				text:"关闭",
				iconCls:"close32",
				handler:function(){
					window.close();
				}
			}]
		};
		
		Ext.applyIf(this,c);
        
        this.callParent();
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('TechFormdModel').load(id, { // load user with ID of "1"
			success: function(model) {
				_this.loadRecord(model);
				Ext.getCmp("postTime").setValue(Ext.Date.format(new Date(model.data.postTime.time),"Y-m-d"));
			}
		});
	},
	saveModel:function(btn,e){
	}
});
