
Ext.namespace("ast.ast1949.admin.bbs.posts")

var _C = new function(){
	this.POSTS_INFO_FORM="POSTS_INFO_FORM";
	this.TAGS_GRID="tagsgrid";
	this.MY_TAGS_GRID="mytagsgrid";
	this.TAGS_GRID_WIN="TAGS_GRID_WIN";
	this.NEWS_MODULE_TREE_WIN="NEWS_MODULE_TREE_WIN";
}

Ext.onReady(function(){
	var _saveUrl=Context.ROOT+Context.PATH + "/admin/bbs/posts/update.htm";
	//判断是新建还是修改
	var _title = "";
	if(CONST.POST_ID>0) {
		_title="编辑帖子";
	} else {
		_title="新建帖子";
	}
	//判断是会员还是管理员的帖子
	var form=null;
	if(CONST.COMPANY_ID>0){
		_saveUrl=Context.ROOT+Context.PATH + "/admin/bbs/posts/updateMemberPost.htm";
		form = new ast.ast1949.admin.bbs.posts.MemberPostForm({
			title:_title,
			id:_C.POSTS_INFO_FORM,
			region:"center"
		});
	} else {
		form = new ast.ast1949.admin.bbs.posts.PostInfoForm({
			title:_title,
			id:_C.POSTS_INFO_FORM,
			region:"center"
		});
	}
	
	var mytagsgrid = new ast.ast1949.admin.bbs.posts.MyResultGrid({
//		title:"已关联标签",
		id:_C.MY_TAGS_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/bbs/tag/queryByPostId.htm?id="+CONST.POST_ID,
		region:'east',
//		width:230,
		autoScroll:true
	});
	
	var righttable = new Ext.Panel({
		title:  "已关联标签",
		region: "east",
		layout : "fit",
		collapsible:true,
		margins:  "0 0 2 2",
		cmargins: "0 2 2 2",
		split: true,
		width: 230,
//		height:450,
		maxSize: 240,
		minSize:120,
//		autoScroll:true,
		//"+Context.ROOT+Context.PATH+"/admin/charts/table.htm
		items:[mytagsgrid]
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[form,righttable]
	});
	
	if(CONST.POST_ID>0){
		//加载数据
		form.loadBbsPost(CONST.POST_ID);
		form.saveUrl=_saveUrl,
		Ext.getCmp("addTag").enable();
	}

})

//会员发帖表单
ast.ast1949.admin.bbs.posts.MemberPostForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var postCategoryCombo = ast.ast1949.admin.bbs.psotCategory.comboTree({
			fieldLabel	: "所属模块",
			id			: "combo-bbsPostCategoryId",
			name		: "combo-bbsPostCategoryId",
			hiddenName	: "bbsPostCategoryId",
			hiddenId	: "bbsPostCategoryId",
			readOnly	:true,
			allowBlank	:false,
			itemCls 	:"required",
			width		:"100",

			el:"bbsPostCategoryId-combo",
			rootData:Context.BBS_PSOT_CATEGORY.all
		});

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			autoScroll:true,
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
						xtype:"hidden",
						id:"companyId",
						name:"companyId"
					},{
						xtype:"textfield",
						fieldLabel:"帖子标题",
						name:"title",
						allowBlank:false,
						itemCls :"required"
					}]
				},
				{
					columnWidth:0.25,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[postCategoryCombo]
				},
				{
					columnWidth:0.25,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
							xtype:          'combo',
	                        mode:           'local',
	                        triggerAction:  'all',
	                        forceSelection: true,
	                        editable:       false,
	                        fieldLabel:     '帖子类型',
	                        hiddenName:     'postType',
	                        displayField:   'name',
	                        valueField:     'value',
	                        autoSelect:true,
	                        store:new Ext.data.JsonStore({
						        fields : ['name', 'value'],
						        data   : [
						        	{name:'普通帖子',value:'0'},
//						            {name : '差贴',  value: '0'},
//						            {name : '好帖',  value: '1'},
//						            {name : '牛贴',  value: '2'},
						            {name : '头条',  value: '3'},
						            {name : '最新动态',  value: '4'},
						            {name : '热门话题',  value: '5'}
						        ]
						    })
						}]
				},
				{
					columnWidth:0.25,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
							xtype:"textfield",
							fieldLabel:"所需积分",
							name:"integral",
							value:"0",
							allowBlank : true
						}]
				},
				{
					columnWidth:0.25,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
							xtype:"textfield",
							fieldLabel:"访问量",
							name:"visitedCount",
							value:"0",
							allowBlank : true			
						}]
				},
//				{
//					columnWidth:0.25,
//					layout:"form",
//					defaults:{
//						anchor:"95%",
//						xtype:"textfield",
//						labelSeparator:""
//					},
//					items:[
////						{
////							xtype:"checkbox",
//////							fieldLabel:"是否热贴",
////							boxLabel:"热帖",
////							name:"isHotPost",
////							inputValue:"1"
////						}
//						]
//				},
				{
					columnWidth:0.25,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
							xtype:"checkbox",
//							fieldLabel:"是否审核",
							boxLabel:"审核通过",
							name:"checkStatus",
							inputValue:"1"
						}]
				},
//				{
//					columnWidth:0.25,
//					layout:"form",
//					defaults:{
//						anchor:"95%",
//						xtype:"textfield",
//						labelSeparator:""
//					},
//					items:[
////						{
////							xtype:"checkbox",
//////							fieldLabel:"是否显示",
////							boxLabel:"显示",
////							name:"isShow",
////							inputValue:"1"
////						}
//						]
//				},
				{
					columnWidth:0.25,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
							xtype:"checkbox",
//							fieldLabel:"是否删除",
							boxLabel:"删除",
							name:"isDel",
							inputValue:"1"
						}]
				},
				{
					columnWidth:1,
					layout:"form",
					defaults:{
						anchor:"95%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[
						{
							xtype: "ckeditor",
							fieldLabel: "详细内容",
							id: "content",
							name: "content",
							CKConfig: {
								toolbar:"Full",
								height : 300,
								width: "96%"
							}		
						}
					]
				}
			],
			buttons:[{
				text:"确定",
				id:"save",
				handler:this.save,
				scope:this
			}
//			,{
//				text:"重填",
//				handler:function(){
//					Ext.getCmp(_C.POSTS_INFO_FORM).getForm().reset();
//				},
//				scope:this
//			}
			]
		};
		
		ast.ast1949.admin.bbs.posts.MemberPostForm.superclass.constructor.call(this,c);
	},
	loadBbsPost:function(id){
		var _fields=[
			{name:"id",mapping:"bbsPost.id"},
			{name:"companyId",mapping:"bbsPost.companyId"},
			{name:"title",mapping:"bbsPost.title"},
			{name:"bbsPostCategoryId1",mapping:"bbsPost.bbsPostCategoryId"},
			{name:"visitedCount",mapping:"bbsPost.visitedCount"},
			{name:"tagsId",mapping:"bbsPost.tagsId"},
			{name:"checkStatus",mapping:"bbsPost.checkStatus"},
			{name:"isShow",mapping:"bbsPost.isShow"},
			{name:"isDel",mapping:"bbsPost.isDel"},
			{name:"isHotPost",mapping:"bbsPost.isHotPost"},
			{name:"postType",mapping:"bbsPost.postType"},
			{name:"content",mapping:"bbsPost.content"},
			{name:"integral",mapping:"bbsPost.integral"},
			{name:"bbsPostCategoryId",mapping:"bbsPostCategoryName"}
			
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/bbs/posts/getSingleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						
						Ext.get("bbsPostCategoryId").dom.value=record.get("bbsPostCategoryId1");
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/saveMemberPost.htm",
	//保存
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
			ast.ast1949.utils.Msg(Context.MSG_TITLE,"验证未通过！");
		}
	},
	onSaveSuccess:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存成功！");
		Ext.MessageBox.confirm(Context.MSG_TITLE, '保存成功！是否关闭本页面?', function(_btn) {
			if(_btn != "no") {
				window.close();
			}
		});
	},
	onSaveFailure:function (form, action){
		ast.ast1949.utils.Msg(Context.MSG_TITLE,"保存失败！");
	}
});
/**************************************************************************/
//发帖表单
ast.ast1949.admin.bbs.posts.PostInfoForm=Ext.extend(Ext.form.FormPanel, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _hiddenRelease = this["hiddenRelease"] || "";
		var _hiddenSave = this["hiddenSave"] || "";
		var _hiddenUpdate = this["hiddenUpdate"] || "";

		var postCategoryCombo = ast.ast1949.admin.bbs.psotCategory.comboTree({
			fieldLabel	: "<span style='color:red'>所属模块:</span>",
			id			: "combo-bbsPostCategoryId",
			name		: "combo-bbsPostCategoryId",
			hiddenName	: "bbsPostCategoryId",
			hiddenId	: "bbsPostCategoryId",
			emptyText	: "请选择...",

			readOnly	:true,
			allowBlank	:false,
			itemCls 	:"required",
			width		:"100",

			el:"bbsPostCategoryId-combo",
			rootData:Context.BBS_PSOT_CATEGORY.all
		});

		var c={
			labelAlign : "right",
			labelWidth : 60,
			layout:"column",
			bodyStyle:"padding:5px 0 0",
			frame:true,
			autoScroll:true,
			items:[
				{
					labelAlign : "right",
					labelWidth : 60,
					columnWidth:1,
					layout:"column",
					bodyStyle:"padding:5px 0 0",
					items:[
					{
						columnWidth:0.5,
						layout:"form",
						defaults:{
							anchor:"99%",
							xtype:"textfield",
							labelSeparator:""
						},
						items:[{
						xtype:"textfield",
						fieldLabel:"帖子标题",
						name:"title",
						allowBlank:false,
						itemCls :"required"
					}]
					},{
						columnWidth:0.5,
						layout:"form",
						defaults:{
							anchor:"99%",
							xtype:"textfield",
							labelSeparator:""
						},
						items:[{
						xtype:"textfield",
						fieldLabel:"首页标题",
						name:"indexTitle",
						allowBlank:true
					}]
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textfield",
						fieldLabel:"新闻模块",
						id:"moduleNames",
						name:"moduleNames",
						allowBlank:true,
						listeners:{
							'focus':{
								fn:function(){
									var ids = Ext.get("moduleIds").dom.value
									ast.ast1949.admin.bbs.posts.NewsModuleCheckBoxTreeWin(ids);
								}
							}
						}
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textfield",
						fieldLabel:"跳转链接",
						name:"url",
						allowBlank:true
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textfield",
						fieldLabel:"访问量",
						name:"visitedCount",
						value:"0",
						allowBlank : true			
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textfield",
						fieldLabel:"所需积分",
						name:"integral",
						value:"0",
						allowBlank : true
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"textfield",
						fieldLabel:"描述(SEO)",
						name:"seoDetails",
						allowBlank:true
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
                        xtype:          'combo',
                        mode:           'local',
                        triggerAction:  'all',
                        forceSelection: true,
                        editable:       false,
                        fieldLabel:     '字号',
//                        name:           'fontSize',
                        hiddenName:     'fontSize',
                        displayField:   'name',
                        valueField:     'value',
                        store:          new Ext.data.JsonStore({
                            fields : ['name', 'value'],
                            data   : [
                                {name : '12px',   value: '12'},
                                {name : '14px',  value: '14'}
                            ]
                        })
                    }]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
                    	xtype:          'combo',
                        mode:           'local',
                        triggerAction:  'all',
                        forceSelection: true,
                        editable:       false,
                        fieldLabel:     '颜色',
//                        name:           'color',
                        hiddenName:     'color',
                        displayField:   'name',
                        valueField:     'value',
                        store:          new Ext.data.JsonStore({
                            fields : ['name', 'value'],
                            data   : [
                                {name : '红',  value: 'Red'},
                                {name : '绿',  value: 'Green'},
                                {name : '蓝',  value: 'Blue'},
                                {name : '黄',  value: 'Yellow'}
                            ]
                        })
                    }]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:          'combo',
                        mode:           'local',
                        triggerAction:  'all',
                        forceSelection: true,
                        editable:       false,
                        fieldLabel:     '帖子类型',
//                        name:           'postType',
                        hiddenName:     'postType',
                        displayField:   'name',
                        valueField:     'value',
                        store:          new Ext.data.JsonStore({
                            fields : ['name', 'value'],
                            data   : [
                          			{name:'普通帖子',value:'0'},
//						            {name : '差贴',  value: '0'},
//						            {name : '好帖',  value: '1'},
//						            {name : '牛贴',  value: '2'},
						            {name : '头条',  value: '3'},
						            {name : '最新动态',  value: '4'},
						            {name : '热门话题',  value: '5'}
                            ]
                        })
					}]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[new ast.ast1949.CategoryCombo( {
						categoryCode : "1042",
						fieldLabel : "所属行业",
						name : "tradeId",
						allowBlank : true
//						allowBlank : true,
//						itemCls :"required"
					})]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[new ast.ast1949.CategoryCombo( {
						categoryCode : "1041",
						fieldLabel : "标注类别",
						name : "markCategoryCode",
						allowBlank : true
					})]
				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"checkbox",
//						checked:true,
//						fieldLabel:"是否审核",
						boxLabel:"审核通过",
						name:"checkStatus",
						inputValue:"1"
					}]
				},
//				{
//					columnWidth:0.2,
//					layout:"form",
//					defaults:{
//						anchor:"99%",
//						xtype:"textfield",
//						labelSeparator:""
//					},
//					items:[
////						{
////						xtype:"checkbox",
//////						checked:true,
//////						fieldLabel:"是否显示",
////						boxLabel:"显示",
////						name:"isShow",
////						inputValue:"1"
////					}
//					]
//				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"checkbox",
//						fieldLabel:"是否删除",
						boxLabel:"删除",
						name:"isDel",
						inputValue:"1"
					}]
				},
//				{
//					columnWidth:0.2,
//					layout:"form",
//					defaults:{
//						anchor:"99%",
//						xtype:"textfield",
//						labelSeparator:""
//					},
//					items:[
////						{
////						xtype:"checkbox",
//////						fieldLabel:"是否热贴",
////						boxLabel:"热帖",
////						name:"isHotPost",
////						inputValue:"1"
////					}
//					]
//				},
				{
					columnWidth:0.2,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"checkbox",
//						fieldLabel:"是否加粗",
						boxLabel:"加粗",
						name:"bold",
						inputValue:"1"
					}]
				},
				{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[
					{
						xtype:"hidden",
						id:"id",
						name:"id"
					},{
						xtype:"hidden",
						id:"newsId",
						name:"newsId"
					},{
						xtype:"hidden",
						id:"moduleIds",
						name:"moduleIds"
					},postCategoryCombo,{
						xtype : "datefield",
						fieldLabel : "发布时间",
						allowBlank : true,
						format : 'Y-m-d H:i',
						value:new Date(),
						name : "sendPostTime"
//						value:new Date()			
					}
				]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"95%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[
					{
						xtype : "datefield",
						fieldLabel : "排序时间",
						allowBlank : true,
						format : 'Y-m-d H:i',
						name : "gmtOrder",
						value:new Date()
					}
				]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[
					{
						xtype: "ckeditor",
						fieldLabel: "详细内容",
						id: "content",
						name: "content",
						CKConfig: { //CKEditor的基本配置，详情配置请结合实际需要。
							/* Enter your CKEditor config paramaters here or define a custom CKEditor config file. 
							 * e.g: Defualt Full
							 * toolbar:'Basic' //Full
							 * toolbar: [
							 *		[ 'Styles' , 'Format' ],
							 *		[ 'Bold' , 'Italic' , '-' , 'NumberedList' , 'BulletedList' , '-' , 'Link' , '-' , 'About' ]
							 *	]
							 *	 
							 * */
							//customConfig : '/ckeditor/config.js',
							toolbar:"Full",
							height : 300,
							width: "96%"
						}
					}
				]
			}],
			buttons:[{
				text:"确定",
				id:"save",
				handler:this.save,
				scope:this
			}
//			,{
//				text:"重填",
//				handler:function(){
//					Ext.getCmp(_C.POSTS_INFO_FORM).getForm().reset();
//				},
//				scope:this
//			}
			]
		};
		
		ast.ast1949.admin.bbs.posts.PostInfoForm.superclass.constructor.call(this,c);
	},
	mystore:null,
	loadBbsPost:function(id){
		var _fields=[
			
			{name:"title",mapping:"bbsPost.title"},
			{name:"bbsPostCategoryId1",mapping:"bbsPost.bbsPostCategoryId"},
			{name:"visitedCount",mapping:"bbsPost.visitedCount"},
			{name:"tagsId",mapping:"bbsPost.tagsId"},
			{name:"checkStatus",mapping:"bbsPost.checkStatus"},
			{name:"isShow",mapping:"bbsPost.isShow"},
			{name:"isDel",mapping:"bbsPost.isDel"},
			{name:"isHotPost",mapping:"bbsPost.isHotPost"},
			{name:"postType",mapping:"bbsPost.postType"},
			{name:"content",mapping:"bbsPost.content"},
			{name:"url",mapping:"bbsPost.url"},
			{name:"integral",mapping:"bbsPost.integral"},
			
			{name:"indexTitle",mapping:"news.indexTitle"},
			{name:"markCategoryCode",mapping:"news.markCategoryCode", convert:function(value){
				if(value=="0") {
					return ""
				} else {
					return value;
				}
			}},
			
			{name:"seoDetails",mapping:"news.seoDetails"},
			
			{name:"sendPostTime",mapping:"bbsPost.postTime",convert : function(value) {
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d H:i');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d H:i');
				}
			}},
			
			{name:"gmtOrder",mapping:"news.gmtOrder",convert : function(value) {
				if(value!=null) {
					return Ext.util.Format.date(new Date(value.time),
					'Y-m-d H:i');
				} else {
					return Ext.util.Format.date(new Date(),'Y-m-d H:i');
				}
			}},
			
			{name:"color",mapping:"titleStyle.color"},
			{name:"fontSize",mapping:"titleStyle.fontSize"},
			{name:"bold",mapping:"titleStyle.bold"},

			{name:"newsId",mapping:"newsId"},
			{name:"moduleNames",mapping:"moduleNames"},
			{name:"moduleIds",mapping:"moduleIds"},
			
			{name:"tradeNames",mapping:"tradeNames"},
			{name:"tradeId",mapping:"tradeId"},
			
			{name:"bbsPostCategoryId",mapping:"bbsPostCategoryName"},
			{name:"bbsTagsName",mapping:"bbsTagsName"},
			{name:"bbsTagsId",mapping:"bbsTagsId"},
			
			{name:"id",mapping:"bbsPost.id"}
		];
		var form = this;
		var store=new Ext.data.JsonStore({
			root : "records",
			fields : _fields,
			url : Context.ROOT+Context.PATH+ "/admin/bbs/posts/getSingleRecord.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
						
						
						Ext.get("bbsPostCategoryId").dom.value=record.get("bbsPostCategoryId1");
					}
				}
			}
		})
	},
	saveUrl:Context.ROOT+Context.PATH + "/admin/bbs/posts/save.htm",
	//保存
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
			ast.ast1949.utils.Msg("系统提示：","验证未通过！");
//			Ext.MessageBox.show({
//				title:Context.MSG_TITLE,
//				msg : "验证未通过",
//				buttons:Ext.MessageBox.OK,
//				icon:Ext.MessageBox.ERROR
//			});
		}
	},
	onSaveSuccess:function (form, action){
//		var obj = eval(action);
//		alert(obj.result.data);
		ast.ast1949.utils.Msg("系统提示：","保存成功！");
		Ext.MessageBox.confirm(Context.MSG_TITLE, '保存成功！是否关闭本页面?', function(_btn) {
			if(_btn != "no") {
				window.close();
			}
		});
	},
	onSaveFailure:function (form, action){
		ast.ast1949.utils.Msg("系统提示：","保存失败！");
//		Ext.MessageBox.show({
//			title:Context.MSG_TITLE,
//			msg : "保存失败！",
//			buttons:Ext.MessageBox.OK,
//			icon:Ext.MessageBox.ERROR
//		});
	}
});

//标签列表
ast.ast1949.admin.bbs.posts.MyResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("deleteButton").enable();
	                } else {
	                    Ext.getCmp("deleteButton").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				width:25
			},{
				header:"名称",
				sortable:false,
				dataIndex:"name",
				width:50
			},{
				header:"属于管理员",
				sortable:false,
				dataIndex:"isAdmin",
				width:50,
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value.length>0&&value=="1"){
						return "是";
					} else {
						return "否";
					}
				}
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			loadMask:Context.LOADMASK,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
//			bbar:new Ext.PagingToolbar({
//				pageSize : Context.PAGE_SIZE,
//				store : _store,
//				displayInfo: true,
//				displayMsg: '第{0}-{1} 条记录,共{2}条',
//				emptyMsg : '没有可显示的记录',
//				beforePageText : '第',
//				afterPageText : '页,共{0}页',
//				paramNames : {start:"startIndex",limit:"pageSize"}
//			}),
			listeners:{
				//"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.bbs.posts.MyResultGrid.superclass.constructor.call(this,c);
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"bbsTags.id"},
		{name:"name",mapping:"bbsTags.name"},
		{name:"isAdmin",mapping:"bbsTags.isAdmin"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/posts/list.htm",
	mytoolbar:new Ext.Toolbar({
		items:[{
					iconCls:"add",
					id:"addTag",
					text:"新增关联",
					disabled:true,
					handler:function(btn){
						var id=Ext.get("postId").dom.value;
						if(id<=0){
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "请先在左侧添加帖子！",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						} else {
							ast.ast1949.admin.bbs.posts.addResultWin();
						}
	//							ast.ast1949.admin.bbs.tag.addFormWin();
					}
				},"-",{
					iconCls:"delete",
					id:"deleteButton",
					text:"删除关联",
					disabled:true,
					handler:function(btn){
						Ext.MessageBox.confirm(Context.MSG_TITLE, '您确定要删除所选记录吗?', doDelete);
					}
				},{
					iconCls:"refresh",
					text:"刷新",
					handler:function(btn){
						var resultgrid = Ext.getCmp(_C.MY_TAGS_GRID);

						resultgrid.store.baseParams={};
						//定位到第一页
						resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
					}
				}]
	})
});

//已关联标签列表
ast.ast1949.admin.bbs.posts.TagsGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({
			listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    Ext.getCmp("addResult").enable();
	                } else {
	                    Ext.getCmp("addResult").disable();
	                }
	            }
	        }
		});
		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				width:"25",
				sortable:false,
				dataIndex:"id"
			},{
				header:"名称",
				width:"50",
				sortable:false,
				dataIndex:"name"
			},{
				header:"属于管理员",
				width:"25",
				sortable:false,
				dataIndex:"isAdmin",
				renderer:function(value,metadata,record,rowIndex,colndex,store){
					if(value.length>0&&value=="1"){
						return "是";
					} else {
						return "否";
					}
				}
			}
		]);
		
		var c={
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			store:_store,
			loadMask:Context.LOADMASK,
			sm:_sm,
			cm:_cm,
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
			}),
			listeners:{
				"render" : this.buttonQuery
			}
		};
		
		ast.ast1949.admin.bbs.posts.TagsGrid.superclass.constructor.call(this,c);
	},
	searchByIsAdmin:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("isAdminBtn").getValue()){
			ary.push(1);
		}else{
			ary.push(0);
		}
		B["isAdmin"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"bbsTags.id"},
		{name:"name",mapping:"bbsTags.name"},
		{name:"isAdmin",mapping:"bbsTags.isAdmin"}
	]),
	listUrl:Context.ROOT+Context.PATH+"/bbs/posts/list.htm",
	mytoolbar:[{
		xtype:"label",
		text:"名称"
	},{
		xtype:"textfield",
		id:"name",
		name:"name",
		width:160
	},{
		iconCls:"query",
		text:"搜索",
		handler:function(btn){
			var resultgrid = Ext.getCmp(_C.TAGS_GRID);

			resultgrid.store.baseParams={};
			resultgrid.store.baseParams = {"name":Ext.get("name").dom.value};
			//定位到第一页
			resultgrid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
		}
	}],
	buttonQuery:function(){
		var tbar2=new Ext.Toolbar({
			items:[{
						iconCls:"add",
						id:"addResult",
						text:"关联",
						disabled:true,
						handler:function(btn){
							var postId=Ext.get("postId").dom.value;
							if(postId<=0) {
								Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "请先在左侧添加帖子！",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
								});
							} else {
								var grid = Ext.getCmp(_C.TAGS_GRID);
								var mygrid = Ext.getCmp(_C.MY_TAGS_GRID);
								
								var row = grid.getSelections();
								var _ids = new Array();
								for (var i=0,len = row.length;i<len;i++){
									var _id=row[i].get("id");
									_ids.push(_id);
								}
								/*提交*/
								var conn = new Ext.data.Connection();
								conn.request({
									url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/addRelate.htm?random="+Math.random()+"&postId="+postId+"&ids="+_ids.join(','),
									method : "get",
									scope : this,
									callback : function(options,success,response){
									var a=Ext.decode(response.responseText);
										if(success){
											Ext.MessageBox.alert(Context.MSG_TITLE,"所选标签已成功关联!");
											
											mygrid.getStore().reload();
											Ext.getCmp(_C.TAGS_GRID_WIN).close();
										}else{
											Ext.MessageBox.alert(Context.MSG_TITLE,"关联失败!");
										}
									}
								});
							}
//							ast.ast1949.admin.bbs.tag.addFormWin();
						}
					},"->",{
						xtype:"checkbox",
						boxLabel:"只显示管理员",
						id:"isAdminBtn",
						checked:false,
						listeners:{
							"check":function(field,newvalue,oldvalue){
								var taggrid=Ext.getCmp(_C.TAGS_GRID);
								taggrid.searchByIsAdmin();
							}
						}
					}]
		});
		
		tbar2.render(this.tbar);
	}
});

//添加关联
ast.ast1949.admin.bbs.posts.addResultWin = function() {

	var tagsgrid = new ast.ast1949.admin.bbs.posts.TagsGrid({
//		title:"标签列表",
		id:_C.TAGS_GRID,
		listUrl:Context.ROOT+Context.PATH+"/admin/bbs/tag/list.htm",
		region:'center',
		autoScroll:true
	});
	
	var win = new Ext.Window({
		id:_C.TAGS_GRID_WIN,
		title:"新增标签",
		width:450,
		height:500,
		modal:true,
		layout:'border',
//		autoHeight:true,
//		maximizable:true,
		items:[tagsgrid]
	});
	win.show();
}

ast.ast1949.admin.bbs.posts.NewsModuleCheckBoxTreeWin = function(id) {

	var tree =new ast.ast1949.admin.news.newsmodule.checkBoxTreePanel({
//		title:"主类别",
		ids:id,
		root:{
			nodeType:'async',
			text:"所有新闻模块",
			data:0
		}
	});
	tree.on('checkchange', function(node, checked) {   
		node.expand();   
		node.attributes.checked = checked;
		//选中的叶子节点
//		node.eachChild(function(child) {   
//			child.ui.toggleCheck(checked);   
//			child.attributes.checked = checked;
////			alert(child);
//			child.fireEvent('checkchange', child, checked);   
//		});
		
		//显示被选中的节点
		Ext.get("moduleIds").dom.value = tree.getChecked("id");
		Ext.get("moduleNames").dom.value = tree.getChecked("text");
	}, tree);
	
	tree.expandAll();
	
	
	var treepanel = new Ext.Panel({
		width:200,
		layour:"fit",
		region:"west",
		autoScroll:true,
		items:[tree]
	});
	
	var win = new Ext.Window({
		id:_C.NEWS_MODULE_TREE_WIN,
		title:"新闻模块",
		width:450,
		height:450,
		modal:true,
		layout:'fit',
		items:[treepanel]
		
	});
	
	win.show();
}

//删除关联
function doDelete(_btn){
	if(_btn != "yes")
			return ;
			
	var grid = Ext.getCmp(_C.MY_TAGS_GRID);
	var postId=Ext.get("postId").dom.value;;
	
	var row = grid.getSelections();
	var _ids = new Array();
	for (var i=0,len = row.length;i<len;i++){
		var _id=row[i].get("id");
		_ids.push(_id);
	}
	/*提交*/
	var conn = new Ext.data.Connection();
	conn.request({
		url: Context.ROOT+Context.PATH+ "/admin/bbs/posts/deleteRelate.htm?random="+Math.random()+"&postId="+postId+"&ids="+_ids.join(','),
		method : "get",
		callback : function(options,success,response){
		var a=Ext.decode(response.responseText);
			if(success){
//				Ext.MessageBox.alert(Context.MSG_TITLE,"选定的记录已被删除!");
				grid.getStore().reload();
			}else{
				Ext.MessageBox.alert(Context.MSG_TITLE,"所选记录删除失败!");
			}
		}
	});
}