Ext.namespace("com.zz91.tags");

TAGS=new function(){
	this.CREATETAGS="createtagswin";
	this.TAGSGRID="tagsgrid";
}

com.zz91.tags.FIELD=[
	{name:"id",mapping:"id"},
	{name:"tags",mapping:"tags"},
	{name:"keywords",mapping:"keywords"},
	{name:"tags_encode",mapping:"tagsEncode"},
	{name:"click_count",mapping:"clickCount"},
	{name:"search_count",mapping:"searchCount"},
	{name:"cited_count",mapping:"citedCount"}
]



com.zz91.tags.grid=Ext.extend(Ext.grid.EditorGridPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _store=new Ext.data.JsonStore({
			url:Context.ROOT+"/tags/tags/queryTags.htm",
			autoLoad:true,
			remoteSort:true,
			root:"records",
			totalProperty:"totals",
			fields:com.zz91.tags.FIELD
		});
		
		var _cm=new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			_sm,{
				header:"ID",
				id:"edit-id", 
				width:200, 
				dataIndex:"id",
				hidden:true
			},{
				header:"标签",
				id:"edit-tags", 
				width:200, 
				dataIndex:"tags",
				sortable:true,
				editor:{xtype:"textfield", allowBlank:false}
			},{
				header:"关键字",
				id:"edit-keywords", 
				width:200, 
				dataIndex:"keywords",
				editor:{xtype:"textfield", allowBlank:false}
			},
			{header:"URL标签", width:300, dataIndex:"tags_encode",sortable:true},
			{header:"被单击", width:80, dataIndex:"click_count",sortable:true},
			{header:"被搜索", width:80, dataIndex:"search_count",sortable:true},
			{header:"被创建", width:80, dataIndex:"cited_count",sortable:true}		
		]);
		
		var c={
//			id:"tagsGrid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:new Ext.Toolbar({
				items:[{
					text:"添加",
					iconCls:"add16",
					handler:function(){
						com.zz91.tags.createTagsWin();
					}
				},{
					text:"删除",
					iconCls:"delete16",
					scope:this,
					handler:function(){
						var row=this.getSelectionModel().getSelections();
						var tags = new Array();
						for(var i=0;i<row.length;i++){
							tags.push(row[i].get("tags"))
						}
						com.zz91.tags.deleteTags(tags,_store);
					}
				},"->",{
					xtype:"textfield",
					listeners:{
						"blur":function(field){
							var B=_store.baseParams||{};
							if(field.getValue()==""){
								B["tags"]=null;
							}else{
								B["tags"]=field.getValue();
							}
							_store.baseParams=B;
							_store.reload({"start":0});
						}
					}
				}]
			}),
			bbar:new Ext.PagingToolbar({
				pageSize:Context.PAGE_SIZE,
				store:_store,
				displayInfo:true,
				displayMsg:"显示第{0}--{1}跳记录, 共{2}条记录",
				emptyMsg:"没有记录",
				beforePageText:"第",
				afterPageText:"页，共{0}页",
				pramaNames:{start:"start",limit:"limit"}
			})
		};
		
		com.zz91.tags.grid.superclass.constructor.call(this,c);
		
		 this.on("afteredit",function(e){
            var _url;
            if(e.record.get("id")>0){
                _url=Context.ROOT+"/tags/tags/updateTags.htm";
            }else{
                _url=Context.ROOT+"/tags/tags/createTags.htm";
            }
            this.saveTags(e.record,_url);
        });
	},
	saveTags:function(record, url){
		Ext.Ajax.request({
            url:url,
            params:{
                "id":record.get("id"),
                "tags":record.get("tags"),
                "keywords":record.get("keywords")
            },
            scope:this,
            success:function(response,opt){
                var obj = Ext.decode(response.responseText);
                if(obj.success){
                    this.getStore().reload();
                }else{
                    Ext.MessageBox.show({
                        title:MESSAGE.title,
                        msg : MESSAGE.saveFailure,
                        buttons:Ext.MessageBox.OK,
                        icon:Ext.MessageBox.ERROR
                    });
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

com.zz91.tags.TagsForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign:"right",
			labelWidth:80,
			frame:true,
			layout:"form",
//			defaults:{
//				xtype:"textfield",
//				anchor:"99%"
//			},
			items:[{
				xtype:"textfield",
				anchor:"99%",
				fieldLabel:"标签",
				allowBlank:false,
				itemCls:"required",
				name:"tags"
			},{
				xtype:"textfield",
				anchor:"99%",
				fieldLabel:"关键字",
				allowBlank:false,
				itemCls:"required",
				name:"keywords"
			}]
			,
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(){
					if(this.getForm().isValid()){
						this.getForm().submit({
							url:Context.ROOT+"/tags/tags/createTags.htm",
							method:"post",
							type:"json",
							success:function(){
								com.zz91.utils.Msg("","保存成功");
								Ext.getCmp(TAGS.CREATETAGS).close();
								var grid=Ext.getCmp(TAGS.TAGSGRID);
								grid.getStore().reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
							},
							failure:function(){
								com.zz91.utils.Msg("","保存失败");
							}
						})
					}
				}
			},{
				text:"取消",
				handler:function(){
					Ext.getCmp(TAGS.CREATETAGS).close();
				}
			}]
		};
		
		com.zz91.tags.TagsForm.superclass.constructor.call(this,c);
	}
})

com.zz91.tags.createTagsWin = function(){
	var form = new com.zz91.tags.TagsForm({
	});
	
	var win = new Ext.Window({
		title:"添加标签信息",
		id:TAGS.CREATETAGS,
		width:450,
		modal:true, 
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.tags.deleteTags = function(idArr, store){
	Ext.Msg.confirm("确认","你确定要删除吗？",function(btn){
		if(btn!="yes"){
			return false;
		}
		for(var i=0;i<idArr.length;i++){
			Ext.Ajax.request({
				url:Context.ROOT+"/tags/tags/deleteTags.htm",
				params:{"tags":idArr[i]},
				success:function(response,opt){
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						store.reload();
					}else{
						com.zz91.utils.Msg("","删除成功");
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