Ext.ns("ast.ast1949.bbs.post");

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
		{name:"bbsPostAssistId",mapping:"post.bbsPostAssistId"},
		{name:"categoryName",mapping:"categoryName"},
		{name:"category",mapping:"category"},
		{name:"postType",mapping:"post.postType"},
		{name:"postTypeName",mapping:"postTypeName"},
		{name:"url",mapping:"post.url"}
		],
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/bbs/post/queryOnePost.htm"
		}
	}
});
Ext.define("BbsPostCategoryModel",{
	extend:"Ext.data.Model",
	fields:["id","parentId","name"],
	idProperty:"idcode",
	proxy:{
		type:"ajax",
		api:{
			read:Context.ROOT+"/zz91/bbs/bbscategory/querySimpleCategoryById.htm"
		}
	}
});
Ext.define("BbsPostTypeModel",{
	extend: 'Ext.data.Model',
	fields:[{name:"id",mapping:"data"},{name:"text",mapping:"text"},{name:"leaf",mapping:"leaf"},{name:"data",mapping:"id"}]
});
Ext.define("BbsPostCategoryModel",{
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
					},{
						fieldLabel : 'url链接',
						name : 'url'
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
						xtype:"hidden",
						id:"bbsPostAssistId",
						name:"bbsPostAssistId"
					},{
						xtype:"hidden",
						id:"category",
						name:"category"
					},{
						fieldLabel : '版块',
						name : 'categoryName',
						id : 'categoryName',
						listeners:{
							"focus":function(field,e){
								
								var win=Ext.create("ast.ast1949.util.TreeSelectorWin",{
									height:500,
									width:300,
									modal:true,
									title:"选择版块类别",
									treeModel:"BbsPostCategoryModel",
									queryUrl:Context.ROOT+"/zz91/bbs/bbscategory/categoryTreeNode.htm",
									callbackFn:function(nodeInterface){
//										alert(nodeInterface.data.data+nodeInterface.data.text)
										Ext.getCmp("bbsPostAssistId").setValue(nodeInterface.getId());
										Ext.getCmp("categoryName").setValue(nodeInterface.data["text"]);
										this.close();
									}
								});
								win.show();
								win.initTree();
							}
						}
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
				xtype:"button",
				text:"马上保存",
				iconCls:"saveas32",
				scale:"large",
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
							Ext.Msg.alert(Context.MSG_TITLE, "贴子添加成功！");
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
