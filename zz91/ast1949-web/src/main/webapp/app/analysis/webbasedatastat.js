Ext.ns("ast.ast1949.analysis.webbasedatastat");

Ext.define("BbsPostModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"post.id"},
		{name:"company_id",mapping:"post.companyId"},
		{name:"title",mapping:"post.title"},
		{name:"account",mapping:"post.account"},
		{name:"visited_count",mapping:"post.visitedCount"},
		{name:"reply_count",mapping:"post.replyCount"},
		{name:"check_status",mapping:"post.checkStatus"},
		{name:"reply_time",mapping:"post.replyTime"},
		{name:"post_time",mapping:"post.postTime"},
		{name:"check_time",mapping:"post.checkTime"},
		{name:"is_del",mapping:"post.isDel"},
		{name:"companyName",mapping:"company.name"},
		"membershipLabel"
	]
});

Ext.define("BbsPostFormModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:"id",mapping:"post.id"},
		{name:"title",mapping:"post.title"},
		{name:"account",mapping:"post.account"},
		{name:"visitedCount",mapping:"post.visitedCount"},
		{name:"replyCount",mapping:"post.replyCount"},
		{name:"replyTime",mapping:"post.replyTime"},
		{name:"postTime",mapping:"post.postTime"},
		{name:"integral",mapping:"post.integral"},
		{name:"tags",mapping:"post.tags"},
		{name:"bbsPostCategoryId",mapping:"post.bbsPostCategoryId"},
		{name:"postType",mapping:"post.postType"},
		{name:"postTypeName",mapping:"postTypeName"}
		],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/bbs/post/queryOnePost.htm"
		}
	}
});

Ext.define("BbsPostTypeModel",{
	extend: 'Ext.data.Model',
	fields:[{name:"id",mapping:"data"},{name:"text",mapping:"text"},{name:"leaf",mapping:"leaf"},{name:"data",mapping:"id"}]
});

Ext.define("ast.ast1949.bbs.post.MainForm",{
	extend:"Ext.form.Panel",
	initComponent:function(){
		var c={
			bodyPadding: 5,
			fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 60,
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
				fieldLabel:"贴子标题"
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
						xtype:"hidden",
						name:"postType",
						id:"postType"
					},{
						fieldLabel : '贴子类型',
						name : 'postTypeName',
						id : 'postTypeName',
						listeners:{
							"focus":function(field,e){
								var win=Ext.create("ast.ast1949.util.TreeSelectorWin",{
									height:500,
									width:300,
									modal:true,
									title:"选择贴子类型",
									treeModel:"BbsPostTypeModel",
									queryUrl:Context.ROOT+"/zz91/bbs/post/queryTypeChild.htm",
									callbackFn:function(nodeInterface){
//										alert(nodeInterface.data.data+nodeInterface.data.text)
										Ext.getCmp("postType").setValue(nodeInterface.data.data);
										Ext.getCmp("postTypeName").setValue(nodeInterface.data["text"]);
										this.close();
									}
								});
								win.show();
								win.initTree();
							}
						}
					},{
						xtype:"numberfield",
						fieldLabel : '查看次数',
						name : 'visitedCount'
					},{
						xtype:"datefield",
						format:"Y-m-d H:i:s",
						fieldLabel : '发布时间',
						name : 'postTime',
						id : 'postTime',
						allowBlank:false,
						formItemCls:"x-form-item required"
					},{
						xtype:"numberfield",
						fieldLabel : '查看积分',
						name : 'integral'
					}]
				},{
					xtype: 'container',
					columnWidth: .5,
					layout:"anchor",
					defaults:{
						anchor:'99%',
						xtype : 'textfield'
					},
					items:[{
						xtype:"combo",
						fieldLabel : '版块',
						name:"bbsPostCategoryId",
						allowBlank:false,
						formItemCls:"x-form-item required",
						displayField:'name',
						valueField:'value',
						store:Ext.create('Ext.data.Store', {
							fields: ['name', 'value'],
							data : [
								{name:'废料动态',value:'1'},
								{name:'行业知识',value:'2'},
								{name:'江湖风云',value:'3'},
								{name:'zz91动态',value:'4'}
							]
						})
					},{
						xtype:"numberfield",
						fieldLabel : '回复次数',
						name : 'replyCount'
					},{
						xtype:"datefield",
						format:"Y-m-d H:i:s",
						fieldLabel : '回复时间',
						name : 'replyTime',
						id : 'replyTime'
					},{
						fieldLabel : '标签',
						name : 'tags'
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
	initCreate:function(){
		Ext.getCmp("postTime").setValue(new Date());
	},
	loadModel:function(id){
		var _this=this;
		Ext.ModelMgr.getModel('BbsPostFormModel').load(id, { // load user with ID of "1"
			success: function(model) {
				_this.loadRecord(model);
				Ext.getCmp("postTime").setValue(Ext.Date.format(new Date(model.data.postTime.time),"Y-m-d H:i:s"));
				if(model.data.replyTime!=null){
					Ext.getCmp("replyTime").setValue(Ext.Date.format(new Date(model.data.replyTime.time),"Y-m-d H:i:s"));
				}
			}
		});
	},
	saveModel:function(btn,e){
		var form =this.up("form");
		if(form.getForm().isValid()){
			var _url=Context.ROOT+"/zz91/bbs/post/createPost.htm";
			if(form.getEditFlag()){
				_url=Context.ROOT+"/zz91/bbs/post/updatePost.htm";
			}
			//保存
			form.submit({
				url:_url,
				success: function(f, action) {
					if(action.result.success){
						if(form.getEditFlag()){
							Ext.Msg.alert(Context.MSG_TITLE, "贴子基本信息已更新！");
						}else{
							var tabs=form.up("tabpanel");
							var tab=tabs.add({
								id:"content-"+action.result.data,
								title:"贴子详细信息",
								html : '<iframe src="' + Context.ROOT+'/zz91/bbs/post/content.htm?id='+action.result.data+'" frameBorder=0 scrolling="auto" style = "width:100%;height:100%"></iframe>'
							});
							tabs.setActiveTab(tab);
						}
					}
				},
				failure: function(form, action) {
					Ext.Msg.alert(Context.MSG_TITLE, "发生错误，信息没有更新！");
				}
			});
		}
	},
	editFlag:false,
	config:{
		editFlag:false
	}
});
