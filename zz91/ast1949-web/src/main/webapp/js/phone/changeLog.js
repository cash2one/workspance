Ext.namespace("ast.ast1949.phone");

ast.ast1949.phone.CHANGELOGFIELD=[
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"telFrom",mapping:"telFrom"},
	{name:"telTo",mapping:"telTo"},
	{name:"status",mapping:"status"},
	{name:"id",mapping:"id"},
	{name:"operator",mapping:"operator"}
 ];

ast.ast1949.phone.changeLogGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var grid=this;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
			sm,{
				header : "id",
				width:100,
				sortable:false,
				hidden:true,
				dataIndex:"id"
			},{
				header : "创建时间",
				width:150,
				sortable:true,
				dataIndex:"gmt_created",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
					}
				}
			},{
				header : "原号码",
				sortable:false,
				dataIndex:"telFrom"
			},{
				header : "新号码",
				width:100,
				sortable:false,
				dataIndex:"telTo"
			},{
				header : "操作人",
				width:100,
				sortable:false,
				dataIndex:"operator"
			},{
				header : "状态",
				width:100,
				sortable:false,
				dataIndex:"status",
				renderer : function(value,metadata, record, rowIndex,colIndex, store) {
					var str = "";
					if(value==0){
						str = "待启动";
					}
					if(value==1){
						str = "<span style='color:red'>启动中</span>";
					}
					if(value==2){
						str = "<span style='color:green'>导入成功</span>";
					}
					return str;
				}
			}
			]);

		var storeUrl = Context.ROOT + Context.PATH + "/phone/queryChangeLog.htm";
		
		var _store= new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:ast.ast1949.phone.CHANGELOGFIELD,
			url: storeUrl,
			autoLoad:true
		});
		
		var tbar = [
		    {
            	text:"启动",
            	iconCls:"edit",
            	handler:function(btn){
		    		var row=grid.getSelectionModel().getSelected();
		    		var id = row.get("id");
		    		if(id==undefined){
		    			return false;
		    		}
		    		Ext.Ajax.request({
		    			url: Context.ROOT+Context.PATH+ "/phone/startChange.htm",
		    			params: {"id": id},
		    			method: 'GET',
		    			success: function (response, options) {
		    				var result = Ext.util.JSON.decode(response.responseText);
		    				if(result.success){
			    				newwin=window.open("http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=phone_charge&start="+new Date().add(Date.DAY, 1).format("Y-m-d")+" 00:00:00")
	    	            		setTimeout(function(){
	    	            			newwin.close();
	    	            		},3000);
			    				_store.reload();
			    			}
		    			},
		    			failure: function (response, options) {
		    				Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
		    			}
		    		});
            	}
            },"-","原号码：",{
            	xtype:"textfield",
            	id:"searchTelForm"
            },"新号码：",{
            	xtype:"textfield",
            	id:"searchTelTo"
            },{
            	text:"搜索",
            	iconCls:"query",
            	handler:function(btn){
            		var searchTelForm=Ext.getCmp("searchTelForm").getValue();
            		var searchTelTo=Ext.getCmp("searchTelTo").getValue();
        			_store.baseParams["telFrom"]=searchTelForm;
        			_store.baseParams["telTo"]=searchTelTo;
        			_store.reload();
            	}
            },"->","原号码：",{
            	xtype:"textfield",
            	id:"addTelForm"
            },"新号码：",{
            	xtype:"textfield",
            	id:"addTelTo"
            },{
            	text:"添加",
            	iconCls:"add",
            	handler:function(btn){
            		var from = Ext.getCmp("addTelForm").getValue();
            		var to = Ext.getCmp("addTelTo").getValue();
	            	Ext.Ajax.request({
		    			url: Context.ROOT+Context.PATH+ "/phone/addNumberChange.htm",
		    			params: {"from": from,"to":to},
		    			method: 'GET',
		    			success: function (response, options) {
		    				Ext.MessageBox.alert('成功', "成功");
		    				_store.reload();
		    			},failure: function (response, options) {
		    				Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
		    			}
		    		});
            	}
            }
		];

		var c={
			id:"changeLogGrid",	
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
		ast.ast1949.phone.changeLogGrid.superclass.constructor.call(this,c);
	},
	loadClickLogRecord:function(companyId){
		this.getStore().reload({params:{"companyId":companyId}});
		var B=this.getStore().baseParams;
		B=B||{};
		B["companyId"]=companyId;
		this.getStore().baseParams=B;
		this.getStore().reload({});
	}
//	,
//	initDate:function(from, to){
//		if(from==null || to==null){
//			from=new Date();
//			to=new Date();
//		}
//		Ext.getCmp("targetFrom").setValue(from);
//		Ext.getCmp("targetTo").setValue(to);
//	}
});


