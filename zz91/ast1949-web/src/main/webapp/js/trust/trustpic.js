Ext.namespace("ast.ast1949.trust.pic");
var PIC=new function(){
	this.GRID="picgrid";
}
ast.ast1949.trust.pic.FIELDS=[
 	{
 		name : "id",
 		mapping : "id"
 	}, {
 		name : "tradeId",
 		mapping : "tradeId"
 	}, {
 		name : "name",
 		mapping : "name"
 	},{
 		name : "isDefault",
 		mapping : "isDefault"
 	},{
 		name : "picAddress",
 		mapping : "picAddress"
 	},{
 		name : "picId",
 		mapping : "picId"
 	}];
ast.ast1949.trust.pic.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.trust.pic.FIELDS,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
				header:"编号",
	            hidden:true,
	            dataIndex:"id"
	        },{
	            header:"名称",
	            align : 'center',
	            sortable:false,
	            width:150,
	            dataIndex:"name"
	        },{
	        	header:"预览",
	        	align : 'center',
	        	sortable:false,
	        	width:80,
	        	dataIndex:"picAddress",
	        	renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	        		if(value!=null){
						return "<a href='http://img3.zz91.com/500x500"+value+"' target='_blank'>图片</a>";
					}
					else{
						return "";
					}
	            }
	        },{
	            header:"修改",
	            align : 'center',
	            sortable:true,
	            width:280,
	            dataIndex:"isDefault",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	        		var addMadePic = "<button class='addMadePic'>"+"添加已处理的图片"+"</button>";
	        		if(record.get("picId")>0){
	        			addMadePic = "";
	        		}
	            	if(value==1){
	            		return "<button class='edits'>"+"修改"+"</button>"+"  "+"<button class='del'>"+"删除"+"</button>"+"  "
	        		       +"<button class='top'>"+"取消置顶"+"</button>" +addMadePic;
	            	}else{
	            		return "<button class='edits'>"+"修改"+"</button>"+"  "+"<button class='del'>"+"删除"+"</button>"+"  "
	        		       +"<button class='top'>"+"置顶"+"</button>" + addMadePic;
	            	}
	        		
	            }
	         }]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
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
			})
		};
		
		ast.ast1949.trust.pic.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/trust/queryTrustPic.htm",
	
	mytoolbar:[{
		xtype : 'button',
		text:"上传图片",
		handler:function(btn){
				var grid = Ext.getCmp(POST.GRID);
				var record=grid.getSelectionModel().getSelected();
				var _id=record.data.id;
				com.zz91.trust.upload.UploadConfig.uploadURL=Context.ROOT+"/zz91/trust/doUpload.htm?tradeId="+_id
				var win = new com.zz91.trust.upload.UploadWin({
					title:"上传内容图片"
				});
				com.zz91.trust.upload.UploadConfig.uploadSuccess=function(form,action){
					Ext.Msg.alert(Context.MSG_TITLE,"图片上传成功！");
					Ext.getCmp(PIC.GRID).store.reload();
					win.close();
				}
			win.show();
		}
	}],
	loadTrustPic:function(id){
		var _store = Ext.getCmp(PIC.GRID).store;
		var B = _store.baseParams;
		B = B||{};
		B["tradeId"] =id;
		_store.baseParams = B;
	}
});