Ext.namespace("ast.ast1949.admin.products");
/**
 * 用于显示各种条件查询后的供求信息列表
 * */
ast.ast1949.admin.products.resultGrid = Ext.extend(Ext.grid.GridPanel,{
	readOnly:false,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _this=this;

		var sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel([sm,{
				header : "编号",
				width : 60,
				hidden:true,
				dataIndex : "id"
			},{
				header:"审核状态",
				dataIndex : "checkStatus",
				width:100,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var status=record.get("checkStatus");
					
					if(status=="1"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
					}else if(status=="2"){
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
					}else{
						return '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
					}
				}
			},{
				header:"标题",
				dataIndex : "title",
				width:150,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					return "<a href='http://jiage.zz91.com/preview/"+record.get("id")+".html' target='_blank'>"+value+"</a>";
				}
			},{
				header:"产品",
				dataIndex : "categoryName",
				width:100
			}, {
				header : "公司",
				width : 150,
				dataIndex : "companyTitle",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var _id = record.get("companyId");
					return "<a href='"+Context.ROOT+Context.PATH+"/jiage/jiage/detail.htm?companyId="+_id+"' target='_blank'>"+value+"</a>";
				}
			}, {
				header : "发布时间",
				width : 150,
				dataIndex : "gmtCreated",
				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
					if (value != null) {
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			}, {
				header:"审核人",
				sortable:true,
				width:100,
				dataIndex:"checkPerson"
			}]
		);
				// 字段信息
		var reader = [{name:"id",mapping:"id"},
			  {name:"checkStatus",mapping:"checkStatus"},
			  {name:"title",mapping:"title"},
			  {name:"categoryName",mapping:"categoryName"},
			  {name:"companyId",mapping:"companyId"},
			  {name:"companyTitle",mapping:"company.name"},
			  {name:"checkPerson",mapping:"checkPerson"},
			  {name:"gmtCreated",mapping:"gmtCreated"}
			 ];
		var storeUrl = Context.ROOT + Context.PATH + "/jiage/jiage/queryPriceOffer.htm";

		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:reader,
			url: storeUrl,
			autoLoad:false
		});
		var tbar = this.toolbar;
		var c={
			id:"productsresultgrid",
			loadMask:Context.LOADMASK,
			sm : sm,
			cm : cm,
			iconCls : "icon-grid",
			store:_store,
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
			tbar : tbar
		};

		ast.ast1949.admin.products.resultGrid.superclass.constructor.call(this,c);
	},
	initJiage:function(a){
		if(a){
			var _store = Ext.getCmp("productsresultgrid").getStore();
			_store.baseParams["companyId"]=a;
			_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
		}else{
			this.getStore().reload();
		}
	},
	toolbar:[{
		text:"编辑",
		id:"editTools",
		iconCls : 'edit',
		handler:function(btn){
			var grid = Ext.getCmp("productsresultgrid");
			var rows = grid.getSelections();
			if(rows.length>1){
				Ext.MessageBox.show({
					title:Context.MSG_TITLE,
					msg : "最多只能选择一条记录！",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
				});
			} else {
				var _pid=rows[0].get("id");
				var url=Context.ROOT+Context.PATH+"/jiage/jiage/edit.htm?offerId="+_pid;
				window.open(url);
			}
		}
	},"-",{
		text:"审核",
		id:"checkTools",
		menu:[{
			text:"通过",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkOffer(1);
			}
		},{
			text:"不通过",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.checkOffer(2);
			}
		}]
		},"-",{
			text:"删除",
			handler:function(btn){
				var grid = Ext.getCmp("productsresultgrid");
				grid.delOffer();
			}
		},"->",{
		xtype:"checkbox",
		boxLabel:"普会",
		listeners:{
			"check":function(field,newvalue,oldvalue){
				var _store = Ext.getCmp("productsresultgrid").getStore();
				var B = _store.baseParams;
				if(field.getValue()){
					B["menberShip"] = "0";
				}else{
					B["menberShip"] = undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
			}
		}
		},"-",{
			xtype:"checkbox",
			boxLabel:"<span style='color:red'>高会</span>",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var _store = Ext.getCmp("productsresultgrid").getStore();
					var B = _store.baseParams;
					if(field.getValue()){
						B["menberShip"] = "1";
					}else{
						B["menberShip"] = undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},"-",{
			xtype:"checkbox",
			boxLabel:"退回",
			id:"uncheckBtn",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var _store = Ext.getCmp("productsresultgrid").getStore();
					var B = _store.baseParams;
					if(field.getValue()){
						B["checkStatus"] = "2";
					}else{
						B["checkStatus"] = undefined;
					}
					_store.baseParams = B;
					_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
				}
			}
		},"-","开始时间",{
			id:"from",
			xtype:"datefield",
			format:"Y-m-d"
		},"结束时间",{
			id:"to",
			xtype:"datefield",
			format:"Y-m-d"
		},"-",{
			xtype:"combo",
			id:"isDel",
			mode:"local",
			emptyText:"审核状态",
			fieldLabel:"审核状态：",
			triggerAction:"all",
			displayField:'name',
			valueField:'value',
			autoSelect:true,
			width:80,
			store:new Ext.data.JsonStore({
				fields : ['name', 'value'],
				data   : [
					{name:'已审核',value:1},
					{name:'未审核',value:0},
					{name:'已删除',value:3},
					{name:'全部',value:4}
				]
			})
		},{
			text:"查询",
        	iconCls:"query",
        	handler:function(btn){
        		var _store = Ext.getCmp("productsresultgrid").getStore();
        		var svrFrom=Ext.getCmp("from").getValue();
        		var svrTo=Ext.getCmp("to").getValue();
        		var svrdel=Ext.getCmp("isDel").getValue();
        		_store.baseParams["from"]=svrFrom;
    			_store.baseParams["to"]=svrTo;
    			_store.baseParams["checkStatus"]=svrdel;
    			_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
        		
        	}
		}],
		checkOffer:function(checkStatus,checkReason){
			var grid = Ext.getCmp("productsresultgrid");
			var rows = grid.getSelections();
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url: Context.ROOT+Context.PATH+ "/jiage/jiage/checkPriceOffer.htm",
					params:{
						"offerId":rows[i].get("id"),"checkStatus":checkStatus,"checkReason":checkReason
					},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							ast.ast1949.utils.Msg("","信息已成功更新")
							grid.getStore().reload();
						}else{
							ast.ast1949.utils.Msg("","操作失败(已推荐)");
						}
					},
					failure:function(response,opt){
						ast.ast1949.utils.Msg("","操作失败");
					}
				});
			}
		},
		delOffer:function(){
			var grid = Ext.getCmp("productsresultgrid");
			var rows = grid.getSelections();
			for(var i=0;i<rows.length;i++){
				Ext.Ajax.request({
					url: Context.ROOT+Context.PATH+ "/jiage/jiage/delPriceOffer.htm",
					params:{
						"offerId":rows[i].get("id")
					},
					success:function(response,opt){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							ast.ast1949.utils.Msg("","信息已成功更新")
							grid.getStore().reload();
						}else{
							ast.ast1949.utils.Msg("","操作失败");
						}
					},
					failure:function(response,opt){
						ast.ast1949.utils.Msg("","操作失败");
					}
				});
			}
		},
});

