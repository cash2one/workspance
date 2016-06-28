Ext.namespace("com.zz91.ads.board.ad.exacttype");

var EXACTTYPE = new function(){
	this.EDIT_WIN="EDIT_WIN";
}

/**
 * 条件列表
 * @class com.zz91.ads.board.ad.exacttype.Grid
 * @extends Ext.grid.GridPanel
 */
com.zz91.ads.board.ad.exacttype.Grid = Ext.extend(Ext.grid.GridPanel, {
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.listRecord;
		var _url = this.listUrl;

		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:_fields,
			url:_url
//			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel({
			listeners: {
				selectionchange: function(sm) {
	                if (sm.getCount()) {
//						Ext.getCmp("editButton").enable();
						Ext.getCmp("delButton").enable();
	                } else {
//	                	Ext.getCmp("editButton").disable();
	                	Ext.getCmp("delButton").disable();
					}
				} 
			}
		});
		
		var _cm=new Ext.grid.ColumnModel([_sm, 
			{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				sortable:false
			},{
				header:"名称",
				dataIndex:"name",
				sortable:false
			},{
				header:"备注",
				dataIndex:"remark",
				sortable:false
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: "显示第 {0} - {1} 条记录,共 {2} 条",
				emptyMsg : "没有可显示的记录",
				beforePageText : "第",
				afterPageText : "页,共{0}页",
				paramNames : {start:"start",limit:"limit"}
			})
		};
		com.zz91.ads.board.ad.exacttype.Grid.superclass.constructor.call(this,c);
	},
	mytoolbar:[
		{
			iconCls:"add16",
			text:"添加关联",
			handler:function(btn){
				var tree = Ext.getCmp(POSITION.TREE);
	            var node = tree.getSelectionModel().getSelectedNode();
	            if(node==null || node.attributes["data"]==""){
	            	return false;
	            }
				com.zz91.ads.board.ad.exacttype.addFormWin();
			}
		},{
			id:"delButton",
			iconCls:"delete16",
			text:"删除关联",
			handler:function(btn){
				var grid=Ext.getCmp(POSITION.EXACTTYPE);
				var sm=grid.getSelectionModel();
                var submitIds=sm.getCount();
                if ( submitIds== 0){
                    Ext.MessageBox.show({
                        title:MESSAGE.title,
                        msg : MESSAGE.needOneRecord,
                        buttons:Ext.MessageBox.OK,
                        icon:Ext.MessageBox.WARNING
                    });
                } else{
                    Ext.MessageBox.confirm(MESSAGE.title, MESSAGE.confirmDelete, function(btn){
                        if(btn != "yes"){
                                return false;
                        }
                        var tree = Ext.getCmp(POSITION.TREE);
                        var node = tree.getSelectionModel().getSelectedNode();
                        
                        var row = sm.getSelections();
                        for (var i=0,len = row.length;i<len;i++){
                           var _id=row[i].get("id");
//                                _ids.push(_id);
                            //提交
                           Ext.Ajax.request({
								url: Context.ROOT + "/ad/exacttype/delRelated.htm?st="+timestamp(),
//									params:{"items":_ids.join(",")},
								params:{"adPositionId":node.attributes["data"],"exactTypeId":_id},
								method : "post",
								success:function(response,opt){
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										com.zz91.ads.board.utils.Msg("","删除成功！");
										Ext.getCmp(POSITION.EXACTTYPE).getStore().reload();
									}else{
										com.zz91.ads.board.utils.Msg("","删除失败！");
									}
								},
								failure:function(response,opt){
									com.zz91.ads.board.utils.Msg("","删除失败！");
								}
							});
                        }
                    });
                }
			}
		}
		
	],
	listRecord:Ext.data.Record.create([
		{name:"id",mapping:"id"},
		{name:"name",mapping:"exactName"},
		{name:"key",mapping:"javaKey"},
		{name:"remark",mapping:"remark"}
	]),
	listUrl:Context.ROOT+"/ad/exacttype/query.htm"
});

com.zz91.ads.board.ad.exacttype.addFormWin = function() {
	var grid = new com.zz91.ads.board.ad.exacttype.Grid({
		id:"ALL_EXACTTYPE_GRID",
		listUrl:Context.ROOT+"/ad/exacttype/query.htm?st="+timestamp(),
		height:300,
		mytoolbar:[
			{
				iconCls:"add16",
				text:"关联此项",
				handler:function(btn){
					var grid=Ext.getCmp("ALL_EXACTTYPE_GRID");
					var sm=grid.getSelectionModel();
	                var submitIds=sm.getCount();
	                if ( submitIds== 0){
                        Ext.MessageBox.show({
                            title:MESSAGE.title,
                            msg : MESSAGE.needOneRecord,
                            buttons:Ext.MessageBox.OK,
                            icon:Ext.MessageBox.WARNING
                        });
	                } else{
	                	var tree = Ext.getCmp(POSITION.TREE);
                        var node = tree.getSelectionModel().getSelectedNode();
                       
                        Ext.MessageBox.confirm(MESSAGE.title, "确定要关联所有选中项吗？", function(btn){
                            if(btn != "yes"){
                                    return false;
                            }
                            
                            var row = sm.getSelections();
                            for (var i=0,len = row.length;i<len;i++){
                               var _id=row[i].get("id");
                               
	                           Ext.Ajax.request({
									url: Context.ROOT + "/ad/exacttype/addRelated.htm",
									params:{"adPositionId":node.attributes["data"],"exactTypeId":_id},
									method : "post",
									success:function(response,opt){
										var obj = Ext.decode(response.responseText);
										if(obj.success){
											com.zz91.ads.board.utils.Msg("","关联成功！");
											Ext.getCmp(EXACTTYPE.EDIT_WIN).close();
											Ext.getCmp(POSITION.EXACTTYPE).getStore().reload();
										}else{
											com.zz91.ads.board.utils.Msg("","关联失败！");
										}
									},
									failure:function(response,opt){
										com.zz91.ads.board.utils.Msg("","关联失败！");
									}
								});
                            }
                        });
	                }
				}
			}
		]
	})
	//加载数据
	grid.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
	//显示窗口
	var win = new Ext.Window({
		id:EXACTTYPE.EDIT_WIN,
		title:"所有精确条件",
		width:500,
		autoHeight:true,
		modal:true,
		items:[grid]
	});
	win.show();
}