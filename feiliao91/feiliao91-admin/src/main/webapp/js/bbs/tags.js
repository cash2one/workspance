Ext.namespace("ast.ast1949.bbs.tags");

ast.ast1949.bbs.tags.INFORMATION=[
	{name:"id",mapping:"id"},
	{name:"category",mapping:"category"},
	{name:"tagName",mapping:"tagName"},
	{name:"noticeCount",mapping:"noticeCount"},
	{name:"articleCount",mapping:"articleCount"},
	{name:"isDel",mapping:"isDel"},
	{name:"mark",mapping:"mark"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

ast.ast1949.bbs.tags.grid= Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header:"编号",
				sortable : true,
				hidden:true,
				name : "id",
				id : "id",
				dataIndex:"id"
			},{
			    header:"标签名称",
				width:90,
				dataIndex : "tagName"
			},{
				header:"标签类别",
				width:70,
				dataIndex : "category",
				renderer : function(value, metaData, record, rowIndex, colIndex, store) {
					if(value==1){
					    var v1="废料问答";
					}else if(value==2){
					    var v1="社区";
					}else{
                        var v1="废料学院";
					}
					return v1;
				}
			},{
				header:"文章数",
				width:80,
				dataIndex : "articleCount"
			},{
				header : "关注数",
				width:80,
				dataIndex:"noticeCount"
			},{
				header : "是否推荐",
				width : 100,
				dataIndex : "mark",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
				  if(!value){
				      var v1="否";
				  }else{
				  	  var v1="是";
				  }
					return v1;
				}
			},{
				header : "创建时间",
				width : 140,
				dataIndex : "gmtCreated",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			},{
				header : "更新时间",
				width : 140,
				dataIndex : "gmtModified",
				renderer: function(value,metadata,record,rowIndex,colIndex,store){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
			}									
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/bbs/tags/queryBbsTags.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.bbs.tags.INFORMATION,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [
		    {
		       text:"添加标签",
		       iconCls:"add",
		       handler:function(){
					ast.ast1949.bbs.tags.updateTagsInfo();
			   }
		    },"-",{
		       text:"删除标签",
		       iconCls:"delete",
		       handler:function(){
		    	   var sm=grid.getSelectionModel();
		    	   var submitIds=sm.getCount();
		    	   if ( submitIds== 0){
		    		   ast.ast1949.utils.Msg('',"请选择您要删除的标签，至少一项");
		    	   } else{
		    		   Ext.MessageBox.confirm(Context.MSG_TITLE,"确定要删除?",function(btn){
		    			   if(btn!="yes"){
		    				   return ;
		    			   }else{
		    				   var row = sm.getSelections();
	                           var _ids = new Array();
	                           for (var i=0,len = row.length;i<len;i++){
	                        	   var id=row[i].get("id");
	                        	   Ext.Ajax.request({
			    					   url:Context.ROOT+Context.PATH+ "/bbs/tags/deleteTag.htm",
			    					   params:{
			    						   "id":id
			    					   },
			    					   method:"post",
			    					   type:"json",
			    					   success:function(response,opt){
			    				            var obj = Ext.decode(response.responseText);
			    				            if(obj.success){
			    				            	Ext.getCmp("tagsGrid").getStore().reload();
			    				            }
			    				        },
			    				        failure:function(response,opt){
			    				        }
			    				   });
			    			   } 
		    			   }
				});
		       }
		       }
		    },"-",{
		       text:"修改标签",
		       iconCls:"edit",
		       handler:function(){
                                        var rows=grid.getSelectionModel().getSelected();
					ast.ast1949.bbs.tags.updateTagsInfo(rows.get("id"));
			   }
			},"-",{
		       text:"推荐",
		       handler:function(){
					var rows=grid.getSelectionModel().getSelected();
					ast.ast1949.bbs.tags.recomTagsInfo(rows.get("id"));
			   }
			},"-",{
			  text:"筛选",
			  id:"filter-config",
			  menu:[{
			          text:"废料问答",
			          handler:function(btn){
				          Ext.getCmp("filter-config").setText("废料问答");
					  var url=Context.ROOT + Context.PATH + "/bbs/tags/queryBbsTags.htm?categoryId=1";
					  _store.proxy = new Ext.data.HttpProxy({url:url});
				          _store.reload()       
			          }
		            },{
			          text:"社区",
			          handler:function(btn){
				          Ext.getCmp("filter-config").setText("社区");
				          var url=Context.ROOT + Context.PATH + "/bbs/tags/queryBbsTags.htm?categoryId=2";
					  _store.proxy = new Ext.data.HttpProxy({url:url});
				          _store.reload()       
			         }
		           },{
			         text:"废料学院",
			         handler:function(btn){
				        Ext.getCmp("filter-config").setText("废料学院");
				        var url=Context.ROOT + Context.PATH + "/bbs/tags/queryBbsTags.htm?categoryId=3";
					_store.proxy = new Ext.data.HttpProxy({url:url});
				        _store.reload()       
			         }
			       },{
			          text:"全部",
			          handler:function(btn){
				          Ext.getCmp("filter-config").setText("全部");
					  var url=Context.ROOT + Context.PATH + "/bbs/tags/queryBbsTags.htm";
					  _store.proxy = new Ext.data.HttpProxy({url:url});
				          _store.reload()   
				         
			         }
		          }]
		}];

		var c={
			id:"tagsGrid",	
			loadMask:Context.LOADMASK,
			sm : sm,
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
			
		};
		ast.ast1949.bbs.tags.grid.superclass.constructor.call(this,c);
	},
	loadTags:function(){
		this.getStore().reload();
	},
});
//标签信息添加
ast.ast1949.bbs.tags.updateTagsInfo=function(id){
	var form=new ast.ast1949.bbs.tags.UpdateTagsInfoForm({
	});
	if(id){
		form.loadInit(id);
        }
	
	var win = new Ext.Window({
		id:"updateTagswin",
		title:"添加标签/修改标签",
		width:300,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.bbs.tags.UpdateTagsInfoForm = Ext.extend(Ext.form.FormPanel,{
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
				header:"编号",
 				hidden:true,
				name:"id",
				id:"id"
				
                        },{
				fieldLabel:"标签名称",
				name:"tagName",
				id:"tagName"
			},{
				xtype:"combo",
                                name:"categoryCombo",
				id:"categoryCombo",
				mode:"local",
		     		emptyText:"请选择...",
				fieldLabel:"标签类别",
				hiddenName:'category',
				triggerAction:"all",
				displayField:'name',
				valueField:'value',
				autoSelect:true,
			    store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'废料问答',value:'1'},
						{name:'社区',value:'2'},
						{name:'废料学院',value:'3'}
					]
				}),
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("tagsGrid");
						var rows=grid.getSelectionModel().getSelected();
						var url=Context.ROOT+Context.PATH+"/bbs/tags/addTag.htm";	
						form.getForm().submit({
							url: url,
							method:"post",
							type:"json",
							success:function(response,opt){
                                                                var obj =opt.result ;
								ast.ast1949.utils.Msg(obj.data,"保存成功");
								Ext.getCmp("updateTagswin").close();
								grid.getStore().reload();  
							},
							failure:function(response,opt){
 								var obj =opt.result ;
								ast.ast1949.utils.Msg(obj.data,"保存失败");
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
		
		ast.ast1949.bbs.tags.UpdateTagsInfoForm.superclass.constructor.call(this,c);
	},
	loadTags:function(){
	},
	loadInit:function(id){
		var form=this;
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.bbs.tags.INFORMATION,
			url : Context.ROOT+Context.PATH+"/bbs/tags/init.htm?id="+id, 
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
//推荐标签
ast.ast1949.bbs.tags.recomTagsInfo=function(id){
	var form=new ast.ast1949.bbs.tags.RecomTagsInfoForm({
	});
	if(id){
		form.loadInit(id);
        }
	
	var win = new Ext.Window({
		id:"updateTagswin",
		title:"标签推荐",
		width:300,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.bbs.tags.RecomTagsInfoForm = Ext.extend(Ext.form.FormPanel,{
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
				header:"编号",
 				hidden:true,
				name:"id",
				id:"id"
				
                        },{
				fieldLabel:"标签名称",
				name:"tagName",
				id:"tagName",
				readOnly:true
			 },{
				xtype:"combo",
                                name:"categoryCombo",
				id:"categoryCombo",
				mode:"local",
		     		emptyText:"请选择...",
				fieldLabel:"标签类别",
				hiddenName:'category',
				triggerAction:"all",
				displayField:'name',
				valueField:'value',
				autoSelect:false,
			    	store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'废料问答',value:'1'},
						{name:'社区',value:'2'},
						{name:'废料学院',value:'3'}
					]
				}),

			 },{
				fieldLabel:"文章数",
				name:"articleCount",
				id:"articleCount"
			 },{
				fieldLabel:"关注数",
				name:"noticeCount",
				id:"noticeCount"
			},{
				xtype:"combo",
                                name:"isdelCombo",
				id:"isdelCombo",
				mode:"local",
		     		emptyText:"请选择...",
				fieldLabel:"推荐",
				hiddenName:'mark',
				triggerAction:"all",
				displayField:'name',
				valueField:'value',
				autoSelect:true,
			    store:new Ext.data.JsonStore({
					fields : ['name', 'value'],
					data   : [
						{name:'是',value:'1'},
						{name:'否',value:''}
					]
				}),
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp("tagsGrid");
						var rows=grid.getSelectionModel().getSelected();
						var url=Context.ROOT+Context.PATH+"/bbs/tags/recom.htm";	
						form.getForm().submit({
							url: url,
							method:"post",
							type:"json",
							success:function(response,opt){
                                                                var obj =opt.result ;
								ast.ast1949.utils.Msg(obj.data,"保存成功");
								Ext.getCmp("updateTagswin").close();
								grid.getStore().reload();  
							},
							failure:function(response,opt){
 								var obj =opt.result ;
								ast.ast1949.utils.Msg(obj.data,"保存失败");
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
		
		ast.ast1949.bbs.tags.UpdateTagsInfoForm.superclass.constructor.call(this,c);
	},
	loadTags:function(){
	},
	loadInit:function(id){
		var form=this;
		form.store = new Ext.data.JsonStore({
			fields : ast.ast1949.bbs.tags.INFORMATION,
			url : Context.ROOT+Context.PATH+"/bbs/tags/init.htm?id="+id, 
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







