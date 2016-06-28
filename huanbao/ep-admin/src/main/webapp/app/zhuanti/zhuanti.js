Ext.namespace("com.zz91.ep.zhuanti");

var ZHUANTI=new function(){
	this.COLLECTION_GRID="collectiongrid";
	this.FILE_GRID="filegrid";
	this.EDIT_WIN="zhuantiAdd";
}

com.zz91.ep.zhuanti.GRIDFIELD=[
	{name:"id",mapping:"zhuanti.id"},
	{name:"title",mapping:"zhuanti.title"},
	{name:"category",mapping:"zhuanti.category"},
	{name:"recommandStatus",mapping:"zhuanti.recommandStatus"},
	{name:"attentionStatus",mapping:"zhuanti.attentionStatus"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"gmtPublish",mapping:"zhuanti.gmtPublish"},
	{name:"targetUrl",mapping:"zhuanti.targetUrl"}
];

com.zz91.ep.zhuanti.FORMFIELD=[
	{name:"id",mapping:"zhuanti.id"},
	{name:"title",mapping:"zhuanti.title"},
	{name:"category",mapping:"zhuanti.category"},
	{name:"recommandStatus",mapping:"zhuanti.recommandStatus"},
	{name:"attentionStatus",mapping:"zhuanti.attentionStatus"},
	{name:"categoryName",mapping:"categoryName"},
	{name:"gmtPublish",mapping:"zhuanti.gmtPublish"},
	{name:"targetUrl",mapping:"zhuanti.targetUrl"},
	{name:"tags",mapping:"zhuanti.tags"},
	{name:"photoPreview",mapping:"zhuanti.photoPreview"},
	{name:"review",mapping:"zhuanti.review"},
];


com.zz91.ep.zhuanti.FILTER_RECOMMEND=[];

com.zz91.ep.zhuanti.grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/news/zhuanti/queryZhuanti.htm",
			autoLoad:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.ep.zhuanti.GRIDFIELD
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,
			{
				header:"id",dataIndex:"id",sortable:true,hidden:true
			},{
				header:"推荐", width:60, dataIndex:"recommandStatus",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var returnValue = value;
				if(value=="N"){
					returnValue="未推荐";
				}
				if(value=="Y"){
					returnValue="已推荐";
				}
				return returnValue;
				}
			},{
				header:"关注", width:60, dataIndex:"attentionStatus",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				var returnValue = value;
				if(value=="N"){
					returnValue="未关注";
				}
				if(value=="Y"){
					returnValue="已关注";
				}
				return returnValue;
				}
			},{
				header:"标题", width:260, dataIndex:"title",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					return "<a href='"+record.get("targetUrl")+"' target='_blank'>"+value+"</a>";
				}
			},{
				header:"类别", width:100, dataIndex:"categoryName",sortable:true
			},{
				header:"发布时间", width:150,dataIndex:"gmtPublish",sortable:true,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			}
		]);
		
		var grid=this;
		
		var _bbar=com.zz91.utils.pageNav(_store);
		
		
		var c={
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					id:"cm-add",
					text:"添加",
					iconCls:"add16",
					handler:function(btn){
					
					com.zz91.ep.zhuanti.AddWin();
					}
				},{
					id:"cm-edit",
					text:"修改",
					iconCls:"edit16",
					handler:function(btn){
						var rows=grid.getSelectionModel().getSelections();
						com.zz91.ep.zhuanti.EditWin(rows[0].get("id"));
					}
				},{
					text:"删除",
					iconCls:"delete16",
					handler:function(){
						var row=grid.getSelectionModel().getSelections();
						var idArr=new Array();
						for(var i=0;i<row.length;i++){
							idArr.push(row[i].get("id"));
						}
						com.zz91.ep.zhuanti.deleteCollection(idArr, _store);
					}
				},{
					text:"推荐/取消",
					handler:function(){
						var row=grid.getSelectionModel().getSelections();
						
						com.zz91.ep.zhuanti.recommend(row, _store);
					}
				},{
					text:"关注/取消",
					handler:function(){
						var row=grid.getSelectionModel().getSelections();
						
						com.zz91.ep.zhuanti.attention(row, _store);
					}
				},"->",{
						xtype:"checkbox",
						boxLabel:'推荐', 
						listeners:{
							"check":function(){
								if(this.getValue()){
									grid.getStore().baseParams["recommandStatus"]="Y";
								}else{
									grid.getStore().baseParams["recommandStatus"]="";
								}
								
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype:"checkbox",
						boxLabel:'关注', 
						listeners:{
							"check":function(){
								if(this.getValue()){
									grid.getStore().baseParams["attentionStatus"]="Y";
								}else{
									grid.getStore().baseParams["attentionStatus"]="";
								}
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						xtype:"combo",
						width:100,
						emptyText:"按类别",
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.ep.zhuanti.FILTER_RECOMMEND
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
								grid.getStore().baseParams["category"]=newValue;
								grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
							}
						}
					},"-",{
						text:"确认搜索",
						iconCls:"websearch16",
						handler:function(btn){
						}
					}
				]
			}),
			bbar:_bbar
		};
		
		com.zz91.ep.zhuanti.grid.superclass.constructor.call(this,c);
	}
});



com.zz91.ep.zhuanti.AddWin = function(){
	var form = new com.zz91.ep.zhuanti.EditForm();

	form.saveUrl = Context.ROOT + "/news/zhuanti/createZhuanti.htm";
	
	var win = new Ext.Window({
		id:ZHUANTI.EDIT_WIN,
		title:"增加专题",
		width:600,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.ep.zhuanti.EditWin = function(id){
	if(!id){return false;}
	
	var form = new com.zz91.ep.zhuanti.EditForm({});
	
	form.saveUrl = Context.ROOT + "/news/zhuanti/updateZhuanti.htm";
	form.loadRecord(id);
	
	var win = new Ext.Window({
		id:ZHUANTI.EDIT_WIN,
		title:"修改专题",
		width:600,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
};

com.zz91.ep.zhuanti.deleteCollection=function(idArr, store){
	
	Ext.Msg.confirm("确认","你确定要删除吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
		for(var i=0;i<idArr.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/news/zhuanti/deleteZhuanti.htm",
				params:{"id":idArr[i]},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
					}else{
						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
					}
				},
				failure:function(response,opt){
					Ext.MessageBox.show({
						title:MESSAGE.title,
						msg : MESSAGE.submitFailure,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			});	
		}
	});
}

com.zz91.ep.zhuanti.recommend=function(row, store){
	
		for(var i=0;i<row.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/news/zhuanti/recommend.htm",
				params:{
				"id":row[i].get("id"),
				"recommandStatus":row[i].get("recommandStatus")=="Y"?"N":"Y"
				},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
						com.zz91.utils.Msg("","推荐成功!");
					}else{
						com.zz91.utils.Msg("","推荐失败!");
					}
				},
				failure:function(response,opt){
					Ext.MessageBox.show({
						title:MESSAGE.title,
						msg : MESSAGE.submitFailure,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			});	
		}
}

com.zz91.ep.zhuanti.attention=function(row, store){
	
	for(var i=0;i<row.length;i++){
		Ext.Ajax.request({
			url:Context.ROOT+"/news/zhuanti/attention.htm",
			params:{
			"id":row[i].get("id"),
			"attentionStatus":row[i].get("attentionStatus")=="Y"?"N":"Y"
			},
			success:function(response,opt){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					store.reload();
					com.zz91.utils.Msg("","关注成功!");
				}else{
					com.zz91.utils.Msg("","关注失败!");
				}
			},
			failure:function(response,opt){
				Ext.MessageBox.show({
					title:MESSAGE.title,
					msg : MESSAGE.submitFailure,
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			}
		});	
	}
}

com.zz91.ep.zhuanti.EditForm=Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+"/news/zhuanti/createZhuanti.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);

		var form=this;
		
		var c={
				labelAlign:"right",
				labelWidth:80,
				frame:true,
				layout:"column",
				items:[{
					columnWidth:.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						fieldLabel:"id",
						id:"id"
					},{
						fieldLabel:"标题",
						name:"title",
						id:"title",
						itemCls:"required",
						allowBlank:false
					},{
						fieldLabel:"URL",
						name:"targetUrl",
						id:"targetUrl",
						itemCls:"required",
						allowBlank:false
					},{
	                	xtype:"textfield",
	                	id:"filePath",
	                	fieldLabel: '专题图片',
	                	name:"photoPreview",
	                	listeners:{
	                		"focus":function(c){
	                			if(Ext.get("id").dom.value==""){
	                				Ext.MessageBox.show({
										title:MESSAGE.title,
										msg : "请先保存，然后打开编辑页面，进行上传操作！",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
									});
	                				return ;
	                			}else{
	                				com.zz91.ep.photo.UploadConfig.uploadURL=Context.ROOT+"/news/zhuanti/uploadFile.htm?id="+Ext.get("id").dom.value;
	                				var win = new com.zz91.ep.photo.UploadWin({
	                					title:"允许格式(doc,docx,xls,pdf,jpg,jpeg,gif,bmp,png)"
	                				});
	                				com.zz91.ep.photo.UploadConfig.uploadSuccess=function(f,o){
	                					if(o.result.success){
	                						win.close();
	                						com.zz91.utils.Msg(MESSAGE.title, "文件已上传!");
	                						form.findById("filePath").setValue(o.result.data);
	                						Ext.getCmp(ZHUANTI.FILE_GRID).getStore().reload();
	                					}else{
	                						com.zz91.utils.Msg(MESSAGE.title, o.result.data);
	                					}
	                				}
	                				win.show();
	                			}
	                		}
	                	}
	                },{
						xtype:"checkbox",
						boxLabel:'推荐', 
						name:'recommandStatus',
						inputValue:"Y",
						id:"recommandStatus"
					}]
				},{
					columnWidth:.5,
					layout:"form",
					defaults:{
						anchor:"99%",
						xtype:"textfield",
						labelSeparator:""
					},
					items:[{
						xtype:"hidden",
						name:"category",
						id:"category"
					},{
						fieldLabel:"专题类别",
						xtype:"combo",
						width:100,
						mode:'local',
						triggerAction:'all',
						forceSelection:true,
						displayField:'name',
						valueField:'value',
						name:"categoryName",
						itemCls:"required",
						allowBlank:false,
						store:new Ext.data.JsonStore({
	                       fields : ['name', 'value'],
	                       data : com.zz91.ep.zhuanti.FILTER_RECOMMEND
	                   }),
	                   listeners:{
							"change":function(field,newValue,oldValue){
							form.findById("category").setValue(newValue);
							}
						}
					},{
						fieldLabel:"标签",
						name:"tags"
					},{
						xtype:"datefield",
						fieldLabel:"发布时间",
						id:"gmtPublishStr",
						name:"gmtPublishStr",
						format:"Y-m-d H:i:s",
						allowBlank:false
					},{
						xtype:"checkbox",
						boxLabel:'关注', 
						name:'attentionStatus',
						inputValue:"Y",
						id:"attentionStatus"
					}]
				},{
					columnWidth:1,
					layout:"form",
					items:[{
						xtype:"textarea",
						anchor:"99%",
						fieldLabel:"文章导读",
						name:"review",
						itemCls:"required",
						allowBlank:false
					}]
				}],
				buttons:[{
					text:"保存",
					scope:this,
					handler:function(){
						var form=this;
						if(this.getForm().isValid()){
							var _url=this.saveUrl;
							this.getForm().submit({
								url:_url,
								method:"post",
								type:"json",
								success:function(){
									Ext.getCmp(ZHUANTI.COLLECTION_GRID).getStore().reload();
									Ext.getCmp(ZHUANTI.EDIT_WIN).close();
									com.zz91.utils.Msg("",MESSAGE.saveSuccess);
								},
								failure:function(){
									com.zz91.utils.Msg("","保存失败");
								}
							});
						}
					}
				},{
					text:"取消",
					handler:function(){
						Ext.getCmp(ZHUANTI.EDIT_WIN).close();
					}
				}]
			};
		
		com.zz91.ep.zhuanti.EditForm.superclass.constructor.call(this,c);
	},
	loadRecord:function(id){
		var _fields=com.zz91.ep.zhuanti.FORMFIELD;
		var form = this;
		var store=new Ext.data.JsonStore({
			fields : _fields,
			url : Context.ROOT + "/news/zhuanti/queryZhuantiDetail.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function(s) {
				var record = s.getAt(0);
				if (record == null) {
					Ext.MessageBox.alert(Context.MSG_TITLE,MESSAGE.loadError);
				} else {
					form.getForm().loadRecord(record);
					
					if(record.get("attentionStatus")=="Y"){
						form.findById("attentionStatus").setValue(true)
					}
					if(record.get("recommandStatus")=="Y"){
						form.findById("recommandStatus").setValue(true)
					}
					form.findById("gmtPublishStr").setValue(Ext.util.Format.date(new Date(record.get("gmtPublish").time), 'Y-m-d H:i:s'));
				}
				}
			}
		});
	}
});
